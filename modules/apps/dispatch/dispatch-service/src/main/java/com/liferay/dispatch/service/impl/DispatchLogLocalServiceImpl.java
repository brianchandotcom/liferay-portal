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

import com.liferay.dispatch.model.DispatchLog;
import com.liferay.dispatch.service.base.DispatchLogLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "model.class.name=com.liferay.dispatch.model.DispatchLog",
	service = AopService.class
)
public class DispatchLogLocalServiceImpl
	extends DispatchLogLocalServiceBaseImpl {

	@Override
	public DispatchLog addDispatchLog(
			long userId, long dispatchTriggerId, String error, String output,
			int status, Date startDate, Date endDate)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		DispatchLog dispatchLog = dispatchLogPersistence.create(
			counterLocalService.increment());

		dispatchLog.setCompanyId(user.getCompanyId());
		dispatchLog.setUserId(user.getUserId());
		dispatchLog.setUserName(user.getFullName());
		dispatchLog.setDispatchTriggerId(dispatchTriggerId);
		dispatchLog.setEndDate(endDate);
		dispatchLog.setError(error);
		dispatchLog.setOutput(output);
		dispatchLog.setStartDate(startDate);
		dispatchLog.setStatus(status);

		return dispatchLogPersistence.update(dispatchLog);
	}

	@Override
	public void deleteDispatchLogs(long dispatchTriggerId) {
		dispatchLogPersistence.removeByDispatchTriggerId(dispatchTriggerId);
	}

	@Override
	public List<DispatchLog> getDispatchLogs(
		long dispatchTriggerId, int start, int end) {

		return dispatchLogPersistence.findByDispatchTriggerId(
			dispatchTriggerId, start, end);
	}

	@Override
	public int getDispatchLogsCount(long dispatchTriggerId) {
		return dispatchLogPersistence.countByDispatchTriggerId(
			dispatchTriggerId);
	}

	@Override
	public DispatchLog updateDispatchLog(
			long dispatchLogId, String error, String output, int status,
			Date endDate)
		throws PortalException {

		DispatchLog dispatchLog = dispatchLogPersistence.findByPrimaryKey(
			dispatchLogId);

		dispatchLog.setEndDate(endDate);
		dispatchLog.setError(error);
		dispatchLog.setOutput(output);
		dispatchLog.setStatus(status);

		return dispatchLogPersistence.update(dispatchLog);
	}

}