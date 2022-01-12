/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.dispatch.service.impl;

import com.liferay.dispatch.exception.DispatchLogStartDateException;
import com.liferay.dispatch.exception.DispatchLogStatusException;
import com.liferay.dispatch.executor.DispatchTaskStatus;
import com.liferay.dispatch.model.DispatchLog;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.model.impl.DispatchLogModelImpl;
import com.liferay.dispatch.service.base.DispatchLogLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 * @author Igor Beslic
 */
@Component(
	property = "model.class.name=com.liferay.dispatch.model.DispatchLog",
	service = AopService.class
)
public class DispatchLogLocalServiceImpl
	extends DispatchLogLocalServiceBaseImpl {

	@Override
	public DispatchLog addDispatchLog(
			long userId, long dispatchTriggerId, Date endDate, String error,
			String output, Date startDate,
			DispatchTaskStatus dispatchTaskStatus)
		throws PortalException {

		_checkDispatchLogPeriod(startDate, endDate);
		_checkDispatchTaskStatus(dispatchTaskStatus);

		DispatchTrigger dispatchTrigger =
			dispatchTriggerPersistence.findByPrimaryKey(dispatchTriggerId);

		User user = userLocalService.getUser(userId);

		DispatchLog dispatchLog = dispatchLogPersistence.create(
			counterLocalService.increment());

		dispatchLog.setCompanyId(user.getCompanyId());
		dispatchLog.setUserId(user.getUserId());
		dispatchLog.setUserName(user.getFullName());
		dispatchLog.setDispatchTriggerId(
			dispatchTrigger.getDispatchTriggerId());
		dispatchLog.setEndDate(endDate);
		dispatchLog.setError(error);
		dispatchLog.setOutput(output);
		dispatchLog.setStartDate(startDate);
		dispatchLog.setStatus(dispatchTaskStatus.getStatus());

		return dispatchLogPersistence.update(dispatchLog);
	}

	@Override
	public DispatchLog deleteDispatchLog(long dispatchLogId)
		throws PortalException {

		DispatchLog dispatchLog = dispatchLogPersistence.findByPrimaryKey(
			dispatchLogId);

		if (DispatchTaskStatus.valueOf(dispatchLog.getStatus()) ==
				DispatchTaskStatus.IN_PROGRESS) {

			throw new DispatchLogStatusException(
				"Dispatch log cannot be deleted while task is in progress");
		}

		return dispatchLogPersistence.remove(dispatchLogId);
	}

	@Override
	public void deleteDispatchLogs(long dispatchTriggerId) {
		dispatchLogPersistence.removeByDispatchTriggerId(dispatchTriggerId);
	}

	@Override
	public DispatchLog fetchLatestDispatchLog(long dispatchTriggerId) {
		return dispatchLogPersistence.fetchByDispatchTriggerId_First(
			dispatchTriggerId, null);
	}

	@Override
	public DispatchLog fetchLatestDispatchLog(
		long dispatchTriggerId, DispatchTaskStatus dispatchTaskStatus) {

		return dispatchLogPersistence.fetchByDTI_S_First(
			dispatchTriggerId, dispatchTaskStatus.getStatus(),
			OrderByComparatorFactoryUtil.create(
				DispatchLogModelImpl.TABLE_NAME, "startDate", "false"));
	}

	@Override
	public List<DispatchLog> getDispatchLogs(
		long dispatchTriggerId, int start, int end) {

		return dispatchLogPersistence.findByDispatchTriggerId(
			dispatchTriggerId, start, end);
	}

	@Override
	public List<DispatchLog> getDispatchLogs(
		long dispatchTriggerId, int start, int end,
		OrderByComparator<DispatchLog> orderByComparator) {

		return dispatchLogPersistence.findByDispatchTriggerId(
			dispatchTriggerId, start, end, orderByComparator);
	}

	@Override
	public int getDispatchLogsCount(long dispatchTriggerId) {
		return dispatchLogPersistence.countByDispatchTriggerId(
			dispatchTriggerId);
	}

	@Override
	public DispatchLog updateDispatchLog(
			long dispatchLogId, Date endDate, String error, String output,
			DispatchTaskStatus dispatchTaskStatus)
		throws PortalException {

		DispatchLog dispatchLog = dispatchLogPersistence.findByPrimaryKey(
			dispatchLogId);

		_checkDispatchLogPeriod(dispatchLog.getStartDate(), endDate);

		_checkDispatchTaskStatus(dispatchTaskStatus);

		dispatchLog.setEndDate(endDate);
		dispatchLog.setError(error);
		dispatchLog.setOutput(output);
		dispatchLog.setStatus(dispatchTaskStatus.getStatus());

		return dispatchLogPersistence.update(dispatchLog);
	}

	private void _checkDispatchLogPeriod(Date startDate, Date endDate)
		throws PortalException {

		if (startDate == null) {
			throw new DispatchLogStartDateException("Start date is required");
		}

		if (endDate == null) {
			return;
		}

		if (startDate.after(endDate)) {
			throw new DispatchLogStartDateException(
				"Start date must precede end date");
		}
	}

	private void _checkDispatchTaskStatus(DispatchTaskStatus dispatchTaskStatus)
		throws PortalException {

		if (dispatchTaskStatus == null) {
			throw new DispatchLogStatusException(
				"Dispatch task status is required");
		}
	}

}