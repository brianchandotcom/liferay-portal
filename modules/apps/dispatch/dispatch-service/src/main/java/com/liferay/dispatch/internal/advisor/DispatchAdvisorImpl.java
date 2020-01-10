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

package com.liferay.dispatch.internal.advisor;

import com.liferay.dispatch.advisor.Dispatch;
import com.liferay.dispatch.advisor.DispatchAdvisor;
import com.liferay.dispatch.constants.DispatchConstants;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;

import java.util.Date;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true, service = DispatchAdvisor.class)
public class DispatchAdvisorImpl implements DispatchAdvisor {

	public DispatchAdvisorImpl() {
	}

	protected DispatchAdvisorImpl(
		SchedulerEngineHelper schedulerEngineHelper,
		TriggerFactory triggerFactory) {

		_schedulerEngineHelper = schedulerEngineHelper;
		_triggerFactory = triggerFactory;
	}

	@Override
	public void addDispatch(
			long dispatchTriggerId, String cronExpression, Date startDate,
			Date endDate) {

		deleteDispatch(dispatchTriggerId);

		Trigger trigger = _triggerFactory.createTrigger(
			_getJobName(dispatchTriggerId), _getGroupName(dispatchTriggerId),
			startDate, endDate, cronExpression);

		try {
			_schedulerEngineHelper.schedule(
				trigger, StorageType.PERSISTED, null,
				DispatchConstants.EXECUTOR_DESTINATION_NAME,
				_getPayload(dispatchTriggerId),
				1000);

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Dispatch scheduled for dispatch trigger ID " +
						dispatchTriggerId);
			}
		}
		catch (SchedulerException se) {
			_log.error(
				"Unable to schedule dispatch for dispatch trigger ID " +
					dispatchTriggerId,
				se);
		}
	}

	@Override
	public void deleteDispatch(long dispatchTriggerId) {
		try {
			_schedulerEngineHelper.delete(
				_getJobName(dispatchTriggerId),
				_getGroupName(dispatchTriggerId), StorageType.PERSISTED);
		}
		catch (SchedulerException se) {
			_log.error(
				"Unable to delete scheduled dispatch for dispatch trigger ID " +
					dispatchTriggerId,
				se);
		}
	}

	@Override
	public Optional<Date> getNextFireDate(long dispatchTriggerId) {
		Date nextFireDate = null;

		try {
			nextFireDate = _schedulerEngineHelper.getNextFireTime(
				_getJobName(dispatchTriggerId),
				_getGroupName(dispatchTriggerId), StorageType.PERSISTED);
		}
		catch (SchedulerException se) {
			_log.error(se, se);
		}

		return Optional.ofNullable(nextFireDate);
	}

	@Override
	public Optional<Date> getPreviousFireDate(long dispatchTriggerId) {
		Date nextFireDate = null;

		try {
			nextFireDate = _schedulerEngineHelper.getPreviousFireTime(
				_getJobName(dispatchTriggerId),
				_getGroupName(dispatchTriggerId), StorageType.PERSISTED);
		}
		catch (SchedulerException se) {
			_log.error(se, se);
		}

		return Optional.ofNullable(nextFireDate);
	}

	@Override
	public Optional<Dispatch> getDispatch(long dispatchTriggerId) {
		try {
			SchedulerResponse schedulerResponse =
				_schedulerEngineHelper.getScheduledJob(
					_getJobName(dispatchTriggerId),
					_getGroupName(dispatchTriggerId), StorageType.PERSISTED);

			if (schedulerResponse == null) {
				return Optional.empty();
			}

			StorageType storageType = schedulerResponse.getStorageType();

			return Optional.of(
				new Dispatch(
					dispatchTriggerId, schedulerResponse.getGroupName(),
					schedulerResponse.getJobName(), storageType.name()));
		}
		catch (SchedulerException se) {
			_log.error(
				"Unable to resolve dispatch object for dispatch trigger ID " +
					dispatchTriggerId,
				se);
		}

		return Optional.empty();
	}

	private String _getGroupName(long dispatchTriggerId) {
		return String.format("DISPATCH_GROUP_%07d", dispatchTriggerId);
	}

	private String _getJobName(long dispatchTriggerId) {
		return String.format("DISPATCH_JOB_%07d", dispatchTriggerId);
	}

	private String _getPayload(long dispatchTriggerId) {
return String.format("{\"dispatchTriggerId\"= %d}", dispatchTriggerId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DispatchAdvisorImpl.class);

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

	@Reference
	private TriggerFactory _triggerFactory;

}