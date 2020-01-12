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

import com.liferay.dispatch.advisor.DispatchJobAdvisor;
import com.liferay.dispatch.exception.DispatchTriggerEndDateException;
import com.liferay.dispatch.exception.DispatchTriggerNameException;
import com.liferay.dispatch.exception.DispatchTriggerStartDateException;
import com.liferay.dispatch.exception.DuplicateDispatchTriggerException;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.service.base.DispatchTriggerLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
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

		dispatchTriggerPersistence.update(dispatchTrigger);

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

		_dispatchAdvisor.deleteDispatchJob(
			dispatchTrigger.getDispatchTriggerId());

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

		if (active) {
			_dispatchAdvisor.submitDispatchJob(
				dispatchTriggerId, cronExpression,
				dispatchTrigger.getStartDate(), dispatchTrigger.getEndDate());
		}
		else {
			_dispatchAdvisor.deleteDispatchJob(dispatchTriggerId);
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
				"\" already exists for company ID ",
				String.valueOf(companyId)));
	}

	@Reference
	private DispatchJobAdvisor _dispatchAdvisor;

	@Reference
	private Portal _portal;

}