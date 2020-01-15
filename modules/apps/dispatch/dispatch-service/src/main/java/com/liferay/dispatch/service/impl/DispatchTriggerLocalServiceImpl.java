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

import com.liferay.dispatch.constants.DispatchConstants;
import com.liferay.dispatch.exception.DispatchTriggerEndDateException;
import com.liferay.dispatch.exception.DispatchTriggerNameException;
import com.liferay.dispatch.exception.DispatchTriggerStartDateException;
import com.liferay.dispatch.exception.DuplicateDispatchTriggerException;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.service.base.DispatchTriggerLocalServiceBaseImpl;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 * @author Igor Beslic
 */
@Component(
	property = "model.class.name=com.liferay.dispatch.model.DispatchTrigger",
	service = AopService.class
)
public class DispatchTriggerLocalServiceImpl
	extends DispatchTriggerLocalServiceBaseImpl {

	@Override
	public DispatchTrigger addDispatchTrigger(
			long userId, String name, boolean system, String type,
			UnicodeProperties typeSettingsProperties)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		validate(0, user.getCompanyId(), name);

		DispatchTrigger dispatchTrigger = dispatchTriggerPersistence.create(
			counterLocalService.increment());

		dispatchTrigger.setCompanyId(user.getCompanyId());
		dispatchTrigger.setUserId(user.getUserId());
		dispatchTrigger.setUserName(user.getFullName());
		dispatchTrigger.setName(name);
		dispatchTrigger.setSystem(system);
		dispatchTrigger.setType(type);
		dispatchTrigger.setTypeSettingsProperties(typeSettingsProperties);

		dispatchTrigger = dispatchTriggerPersistence.update(dispatchTrigger);

		resourceLocalService.addResources(
			user.getCompanyId(), 0, user.getUserId(),
			DispatchTrigger.class.getName(),
			dispatchTrigger.getDispatchTriggerId(), false, true, true);

		return dispatchTrigger;
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public DispatchTrigger deleteDispatchTrigger(
			DispatchTrigger dispatchTrigger)
		throws PortalException {

		if (dispatchTrigger.isSystem()) {
			return dispatchTrigger;
		}

		dispatchLogPersistence.removeByDispatchTriggerId(
			dispatchTrigger.getDispatchTriggerId());

		dispatchTriggerPersistence.remove(dispatchTrigger);

		resourceLocalService.deleteResource(
			dispatchTrigger, ResourceConstants.SCOPE_INDIVIDUAL);

		_deleteSchedulerJob(dispatchTrigger.getDispatchTriggerId());

		return dispatchTrigger;
	}

	@Override
	public DispatchTrigger deleteDispatchTrigger(long dispatchTriggerId)
		throws PortalException {

		return dispatchTriggerPersistence.remove(dispatchTriggerId);
	}

	@Override
	public DispatchTrigger fetchDispatchTrigger(long companyId, String name) {
		return dispatchTriggerPersistence.fetchByC_N(companyId, name);
	}

	@Override
	public DispatchTrigger getDispatchTrigger(long dispatchTriggerId)
		throws PortalException {

		return dispatchTriggerPersistence.findByPrimaryKey(dispatchTriggerId);
	}

	@Override
	public List<DispatchTrigger> getDispatchTriggers(
		long companyId, int start, int end) {

		return dispatchTriggerPersistence.findByCompanyId(
			companyId, start, end);
	}

	@Override
	public int getDispatchTriggersCount(long companyId) {
		return dispatchTriggerPersistence.countByCompanyId(companyId);
	}

	@Override
	public Date getNextFireDate(long dispatchTriggerId) {
		try {
			return _schedulerEngineHelper.getNextFireTime(
				_getJobName(dispatchTriggerId),
				_getGroupName(dispatchTriggerId), StorageType.PERSISTED);
		}
		catch (SchedulerException schedulerException) {
			_log.error(schedulerException, schedulerException);
		}

		return null;
	}

	@Override
	public Date getPreviousFireDate(long dispatchTriggerId) {
		try {
			return _schedulerEngineHelper.getPreviousFireTime(
				_getJobName(dispatchTriggerId),
				_getGroupName(dispatchTriggerId), StorageType.PERSISTED);
		}
		catch (SchedulerException schedulerException) {
			_log.error(schedulerException, schedulerException);
		}

		return null;
	}

	@Override
	public DispatchTrigger updateDispatchTrigger(
			long dispatchTriggerId, boolean active, String cronExpression,
			int endDateMonth, int endDateDay, int endDateYear, int endDateHour,
			int endDateMinute, boolean neverEnd, int startDateMonth,
			int startDateDay, int startDateYear, int startDateHour,
			int startDateMinute)
		throws PortalException {

		DispatchTrigger dispatchTrigger =
			dispatchTriggerPersistence.fetchByPrimaryKey(dispatchTriggerId);

		dispatchTrigger.setActive(active);
		dispatchTrigger.setCronExpression(cronExpression);

		if (!neverEnd) {
			dispatchTrigger.setEndDate(
				_portal.getDate(
					endDateMonth, endDateDay, endDateYear, endDateHour,
					endDateMinute, DispatchTriggerEndDateException.class));
		}

		dispatchTrigger.setStartDate(
			_portal.getDate(
				startDateMonth, startDateDay, startDateYear, startDateHour,
				startDateMinute, DispatchTriggerStartDateException.class));

		dispatchTrigger = dispatchTriggerPersistence.update(dispatchTrigger);

		_deleteSchedulerJob(dispatchTriggerId);

		if (active) {
			_addSchedulerJob(
				dispatchTriggerId, cronExpression,
				dispatchTrigger.getStartDate(), dispatchTrigger.getEndDate());
		}

		return dispatchTrigger;
	}

	@Override
	public DispatchTrigger updateDispatchTrigger(
			long dispatchTriggerId, String name,
			UnicodeProperties typeSettingsProperties)
		throws PortalException {

		DispatchTrigger dispatchTrigger =
			dispatchTriggerPersistence.findByPrimaryKey(dispatchTriggerId);

		validate(dispatchTriggerId, dispatchTrigger.getCompanyId(), name);

		dispatchTrigger.setName(name);
		dispatchTrigger.setTypeSettingsProperties(typeSettingsProperties);

		return dispatchTriggerPersistence.update(dispatchTrigger);
	}

	protected void validate(long dispatchTriggerId, long companyId, String name)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new DispatchTriggerNameException(
				"Dispatch trigger name is null for company ID " + companyId);
		}

		DispatchTrigger dispatchTrigger = dispatchTriggerPersistence.fetchByC_N(
			companyId, name);

		if (dispatchTrigger == null) {
			return;
		}

		if ((dispatchTriggerId > 0) &&
			(dispatchTrigger.getDispatchTriggerId() == dispatchTriggerId)) {

			return;
		}

		throw new DuplicateDispatchTriggerException(
			StringBundler.concat(
				"Dispatch trigger name \"", name,
				"\" already exists for company ID ", companyId));
	}

	private void _addSchedulerJob(
		long dispatchTriggerId, String cronExpression, Date startDate,
		Date endDate) {

		Trigger trigger = _triggerFactory.createTrigger(
			_getJobName(dispatchTriggerId), _getGroupName(dispatchTriggerId),
			startDate, endDate, cronExpression);

		try {
			_schedulerEngineHelper.schedule(
				trigger, StorageType.PERSISTED, null,
				DispatchConstants.EXECUTOR_DESTINATION_NAME,
				_getPayload(dispatchTriggerId), 1000);

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Scheduler entry created for dispatch trigger " +
						dispatchTriggerId);
			}
		}
		catch (SchedulerException schedulerException) {
			_log.error(
				"Unable to create scheduler entry for dispatch trigger " +
					dispatchTriggerId,
				schedulerException);
		}
	}

	private void _deleteSchedulerJob(long dispatchTriggerId) {
		try {
			_schedulerEngineHelper.delete(
				_getJobName(dispatchTriggerId),
				_getGroupName(dispatchTriggerId), StorageType.PERSISTED);
		}
		catch (SchedulerException schedulerException) {
			_log.error(
				"Unable to delete scheduler entry for dispatch trigger " +
					dispatchTriggerId,
				schedulerException);
		}
	}

	private String _getGroupName(long dispatchTriggerId) {
		return String.format("DISPATCH_GROUP_%07d", dispatchTriggerId);
	}

	private String _getJobName(long dispatchTriggerId) {
		return String.format("DISPATCH_JOB_%07d", dispatchTriggerId);
	}

	private String _getPayload(long dispatchTriggerId) {
		return String.format("{\"dispatchTriggerId\"=%d}", dispatchTriggerId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DispatchTriggerLocalServiceImpl.class);

	@Reference
	private Portal _portal;

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

	@Reference
	private TriggerFactory _triggerFactory;

}