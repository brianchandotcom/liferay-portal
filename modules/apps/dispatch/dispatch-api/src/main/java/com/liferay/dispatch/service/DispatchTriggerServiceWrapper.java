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

package com.liferay.dispatch.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DispatchTriggerService}.
 *
 * @author Matija Petanjek
 * @see DispatchTriggerService
 * @generated
 */
public class DispatchTriggerServiceWrapper
	implements DispatchTriggerService, ServiceWrapper<DispatchTriggerService> {

	public DispatchTriggerServiceWrapper() {
		this(null);
	}

	public DispatchTriggerServiceWrapper(
		DispatchTriggerService dispatchTriggerService) {

		_dispatchTriggerService = dispatchTriggerService;
	}

	@Override
	public com.liferay.dispatch.model.DispatchTrigger addDispatchTrigger(
			String externalReferenceCode, long userId,
			String dispatchTaskExecutorType,
			com.liferay.portal.kernel.util.UnicodeProperties
				dispatchTaskSettingsUnicodeProperties,
			String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dispatchTriggerService.addDispatchTrigger(
			externalReferenceCode, userId, dispatchTaskExecutorType,
			dispatchTaskSettingsUnicodeProperties, name);
	}

	@Override
	public void deleteDispatchTrigger(long dispatchTriggerId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_dispatchTriggerService.deleteDispatchTrigger(dispatchTriggerId);
	}

	@Override
	public com.liferay.dispatch.model.DispatchTrigger getDispatchTrigger(
			long dispatchTriggerId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dispatchTriggerService.getDispatchTrigger(dispatchTriggerId);
	}

	@Override
	public java.util.List<com.liferay.dispatch.model.DispatchTrigger>
			getDispatchTriggers(int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dispatchTriggerService.getDispatchTriggers(start, end);
	}

	@Override
	public int getDispatchTriggersCount()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dispatchTriggerService.getDispatchTriggersCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _dispatchTriggerService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.dispatch.model.DispatchTrigger updateDispatchTrigger(
			long dispatchTriggerId, boolean active, String cronExpression,
			com.liferay.dispatch.executor.DispatchTaskClusterMode
				dispatchTaskClusterMode,
			int endDateMonth, int endDateDay, int endDateYear, int endDateHour,
			int endDateMinute, boolean neverEnd, boolean overlapAllowed,
			int startDateMonth, int startDateDay, int startDateYear,
			int startDateHour, int startDateMinute, String timeZoneId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dispatchTriggerService.updateDispatchTrigger(
			dispatchTriggerId, active, cronExpression, dispatchTaskClusterMode,
			endDateMonth, endDateDay, endDateYear, endDateHour, endDateMinute,
			neverEnd, overlapAllowed, startDateMonth, startDateDay,
			startDateYear, startDateHour, startDateMinute, timeZoneId);
	}

	@Override
	public com.liferay.dispatch.model.DispatchTrigger updateDispatchTrigger(
			long dispatchTriggerId,
			com.liferay.portal.kernel.util.UnicodeProperties
				dispatchTaskSettingsUnicodeProperties,
			String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dispatchTriggerService.updateDispatchTrigger(
			dispatchTriggerId, dispatchTaskSettingsUnicodeProperties, name);
	}

	@Override
	public DispatchTriggerService getWrappedService() {
		return _dispatchTriggerService;
	}

	@Override
	public void setWrappedService(
		DispatchTriggerService dispatchTriggerService) {

		_dispatchTriggerService = dispatchTriggerService;
	}

	private DispatchTriggerService _dispatchTriggerService;

}