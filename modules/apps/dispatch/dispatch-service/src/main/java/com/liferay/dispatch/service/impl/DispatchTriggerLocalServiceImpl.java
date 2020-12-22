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

import com.liferay.dispatch.exception.DispatchTriggerEndDateException;
import com.liferay.dispatch.exception.DispatchTriggerNameException;
import com.liferay.dispatch.exception.DispatchTriggerStartDateException;
import com.liferay.dispatch.exception.DuplicateDispatchTriggerException;
import com.liferay.dispatch.executor.DispatchTaskClusterMode;
import com.liferay.dispatch.internal.helper.DispatchTriggerHelper;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.service.base.DispatchTriggerLocalServiceBaseImpl;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalRunMode;
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

	/**
	 * @param      userId
	 * @param      name
	 * @param      system
	 * @param      taskExecutorType
	 * @param      taskSettingsUnicodeProperties
	 * @return
	 *
	 * @throws     PortalException
	 * @deprecated As of Cavanaugh (7.4.x), use {@link #addDispatchTrigger(long,
	 *             String, UnicodeProperties, String, boolean)}
	 */
	@Deprecated
	@Override
	public DispatchTrigger addDispatchTrigger(
			long userId, String name, boolean system, String taskExecutorType,
			UnicodeProperties taskSettingsUnicodeProperties)
		throws PortalException {

		return addDispatchTrigger(
			userId, taskExecutorType, taskSettingsUnicodeProperties, name,
			system);
	}

	@Override
	public DispatchTrigger addDispatchTrigger(
			long userId, String dispatchTaskExecutorType,
			UnicodeProperties dispatchTaskSettingsUnicodeProperties,
			String name, boolean system)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		validate(0, user.getCompanyId(), name);

		DispatchTrigger dispatchTrigger = dispatchTriggerPersistence.create(
			counterLocalService.increment());

		dispatchTrigger.setCompanyId(user.getCompanyId());
		dispatchTrigger.setUserId(user.getUserId());
		dispatchTrigger.setUserName(user.getFullName());
		dispatchTrigger.setDispatchTaskExecutorType(dispatchTaskExecutorType);
		dispatchTrigger.setDispatchTaskSettingsUnicodeProperties(
			dispatchTaskSettingsUnicodeProperties);
		dispatchTrigger.setName(name);
		dispatchTrigger.setSystem(system);

		dispatchTrigger = dispatchTriggerPersistence.update(dispatchTrigger);

		resourceLocalService.addResources(
			user.getCompanyId(), GroupConstants.DEFAULT_LIVE_GROUP_ID,
			user.getUserId(), DispatchTrigger.class.getName(),
			dispatchTrigger.getDispatchTriggerId(), false, true, true);

		return dispatchTrigger;
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public DispatchTrigger deleteDispatchTrigger(
			DispatchTrigger dispatchTrigger)
		throws PortalException {

		if (dispatchTrigger.isSystem() && !PortalRunMode.isTestMode()) {
			return dispatchTrigger;
		}

		dispatchLogPersistence.removeByDispatchTriggerId(
			dispatchTrigger.getDispatchTriggerId());

		dispatchTriggerPersistence.remove(dispatchTrigger);

		resourceLocalService.deleteResource(
			dispatchTrigger, ResourceConstants.SCOPE_INDIVIDUAL);

		DispatchTaskClusterMode dispatchTaskClusterMode =
			DispatchTaskClusterMode.valueOf(
				dispatchTrigger.getDispatchTaskClusterMode());

		_dispatchTriggerHelper.deleteSchedulerJob(
			dispatchTrigger.getDispatchTriggerId(),
			dispatchTaskClusterMode.getStorageType());

		return dispatchTrigger;
	}

	@Override
	public DispatchTrigger deleteDispatchTrigger(long dispatchTriggerId)
		throws PortalException {

		return deleteDispatchTrigger(
			dispatchTriggerPersistence.findByPrimaryKey(dispatchTriggerId));
	}

	@Override
	public DispatchTrigger fetchDispatchTrigger(long companyId, String name) {
		return dispatchTriggerPersistence.fetchByC_N(companyId, name);
	}

	@Override
	public Date fetchPreviousFireDate(long dispatchTriggerId) {
		DispatchTrigger dispatchTrigger =
			dispatchTriggerPersistence.fetchByPrimaryKey(dispatchTriggerId);

		if (dispatchTrigger == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to fetch dispatch trigger ID " + dispatchTriggerId);
			}

			return null;
		}

		DispatchTaskClusterMode dispatchTaskClusterMode =
			DispatchTaskClusterMode.valueOf(
				dispatchTrigger.getDispatchTaskClusterMode());

		try {
			return _dispatchTriggerHelper.getPreviousFireDate(
				dispatchTriggerId, dispatchTaskClusterMode.getStorageType());
		}
		catch (SchedulerException schedulerException) {
			if (_log.isWarnEnabled()) {
				StringBundler sb = new StringBundler(3);

				sb.append("Unable to fetch previous fire date for dispatch ");
				sb.append("trigger ID ");
				sb.append(dispatchTriggerId);

				_log.warn(sb.toString(), schedulerException);
			}
		}

		return null;
	}

	@Override
	public DispatchTrigger getDispatchTrigger(long dispatchTriggerId)
		throws PortalException {

		return dispatchTriggerPersistence.findByPrimaryKey(dispatchTriggerId);
	}

	@Override
	public List<DispatchTrigger> getDispatchTriggers(
		boolean active, DispatchTaskClusterMode dispatchTaskClusterMode) {

		return dispatchTriggerPersistence.findByA_DTCM(
			active, dispatchTaskClusterMode.getMode());
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
	public Date getNextFireDate(long dispatchTriggerId) throws PortalException {
		DispatchTrigger dispatchTrigger =
			dispatchTriggerPersistence.findByPrimaryKey(dispatchTriggerId);

		DispatchTaskClusterMode dispatchTaskClusterMode =
			DispatchTaskClusterMode.valueOf(
				dispatchTrigger.getDispatchTaskClusterMode());

		try {
			return _dispatchTriggerHelper.getNextFireDate(
				dispatchTriggerId, dispatchTaskClusterMode.getStorageType());
		}
		catch (SchedulerException schedulerException) {
			_log.error(schedulerException, schedulerException);
		}

		return null;
	}

	@Override
	public Date getPreviousFireDate(long dispatchTriggerId)
		throws PortalException {

		DispatchTrigger dispatchTrigger =
			dispatchTriggerPersistence.findByPrimaryKey(dispatchTriggerId);

		DispatchTaskClusterMode dispatchTaskClusterMode =
			DispatchTaskClusterMode.valueOf(
				dispatchTrigger.getDispatchTaskClusterMode());

		return _dispatchTriggerHelper.getPreviousFireDate(
			dispatchTriggerId, dispatchTaskClusterMode.getStorageType());
	}

	@Override
	public List<DispatchTrigger> getUserDispatchTriggers(
		long companyId, long userId, int start, int end) {

		return dispatchTriggerPersistence.findByC_U(
			companyId, userId, start, end);
	}

	@Override
	public int getUserDispatchTriggersCount(long companyId, long userId) {
		return dispatchTriggerPersistence.countByC_U(companyId, userId);
	}

	@Override
	public DispatchTrigger updateDispatchTrigger(
			long dispatchTriggerId, boolean active, String cronExpression,
			DispatchTaskClusterMode dispatchTaskClusterMode, int endDateMonth,
			int endDateDay, int endDateYear, int endDateHour, int endDateMinute,
			boolean neverEnd, boolean overlapAllowed, int startDateMonth,
			int startDateDay, int startDateYear, int startDateHour,
			int startDateMinute)
		throws PortalException {

		DispatchTrigger dispatchTrigger =
			dispatchTriggerPersistence.fetchByPrimaryKey(dispatchTriggerId);

		dispatchTrigger.setActive(active);
		dispatchTrigger.setCronExpression(cronExpression);

		if (neverEnd) {
			dispatchTrigger.setEndDate(null);
		}
		else {
			dispatchTrigger.setEndDate(
				_portal.getDate(
					endDateMonth, endDateDay, endDateYear, endDateHour,
					endDateMinute, DispatchTriggerEndDateException.class));
		}

		dispatchTrigger.setOverlapAllowed(overlapAllowed);

		dispatchTrigger.setStartDate(
			_portal.getDate(
				startDateMonth, startDateDay, startDateYear, startDateHour,
				startDateMinute, DispatchTriggerStartDateException.class));

		dispatchTrigger.setDispatchTaskClusterMode(
			dispatchTaskClusterMode.getMode());

		dispatchTrigger = dispatchTriggerPersistence.update(dispatchTrigger);

		_dispatchTriggerHelper.deleteSchedulerJob(
			dispatchTriggerId, dispatchTaskClusterMode.getStorageType());

		if (active) {
			_dispatchTriggerHelper.addSchedulerJob(
				dispatchTriggerId, cronExpression,
				dispatchTrigger.getStartDate(), dispatchTrigger.getEndDate(),
				dispatchTaskClusterMode.getStorageType());
		}

		return dispatchTrigger;
	}

	/**
	 * @param      dispatchTriggerId
	 * @param      active
	 * @param      cronExpression
	 * @param      endDateMonth
	 * @param      endDateDay
	 * @param      endDateYear
	 * @param      endDateHour
	 * @param      endDateMinute
	 * @param      neverEnd
	 * @param      overlapAllowed
	 * @param      startDateMonth
	 * @param      startDateDay
	 * @param      startDateYear
	 * @param      startDateHour
	 * @param      startDateMinute
	 * @param      dispatchTaskClusterMode
	 * @return
	 *
	 * @throws     PortalException
	 * @deprecated As of Cavanaugh (7.4.x), use {@link
	 *             #updateDispatchTrigger(long, boolean, String,
	 *             DispatchTaskClusterMode, int, int, int, int, int, boolean,
	 *             boolean, int, int, int, int, int)}
	 */
	@Deprecated
	@Override
	public DispatchTrigger updateDispatchTrigger(
			long dispatchTriggerId, boolean active, String cronExpression,
			int endDateMonth, int endDateDay, int endDateYear, int endDateHour,
			int endDateMinute, boolean neverEnd, boolean overlapAllowed,
			int startDateMonth, int startDateDay, int startDateYear,
			int startDateHour, int startDateMinute,
			DispatchTaskClusterMode dispatchTaskClusterMode)
		throws PortalException {

		return updateDispatchTrigger(
			dispatchTriggerId, active, cronExpression, dispatchTaskClusterMode,
			endDateMonth, endDateDay, endDateYear, endDateHour, endDateMinute,
			neverEnd, overlapAllowed, startDateMonth, startDateDay,
			startDateYear, startDateHour, startDateMinute);
	}

	/**
	 * @param      dispatchTriggerId
	 * @param      name
	 * @param      dispatchTaskSettingsUnicodeProperties
	 * @return
	 *
	 * @throws     PortalException
	 * @deprecated As of Cavanaugh (7.4.x), use {@link
	 *             #updateDispatchTrigger(long, UnicodeProperties, String)}
	 */
	@Deprecated
	@Override
	public DispatchTrigger updateDispatchTrigger(
			long dispatchTriggerId, String name,
			UnicodeProperties dispatchTaskSettingsUnicodeProperties)
		throws PortalException {

		return updateDispatchTrigger(
			dispatchTriggerId, dispatchTaskSettingsUnicodeProperties, name);
	}

	@Override
	public DispatchTrigger updateDispatchTrigger(
			long dispatchTriggerId,
			UnicodeProperties taskSettingsUnicodeProperties, String name)
		throws PortalException {

		DispatchTrigger dispatchTrigger =
			dispatchTriggerPersistence.findByPrimaryKey(dispatchTriggerId);

		validate(dispatchTriggerId, dispatchTrigger.getCompanyId(), name);

		dispatchTrigger.setName(name);
		dispatchTrigger.setDispatchTaskSettingsUnicodeProperties(
			taskSettingsUnicodeProperties);

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

	private static final Log _log = LogFactoryUtil.getLog(
		DispatchTriggerLocalServiceImpl.class);

	@Reference
	private DispatchTriggerHelper _dispatchTriggerHelper;

	@Reference
	private Portal _portal;

}