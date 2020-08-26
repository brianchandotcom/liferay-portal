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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for DispatchTrigger. This utility wraps
 * <code>com.liferay.dispatch.service.impl.DispatchTriggerServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see DispatchTriggerService
 * @generated
 */
public class DispatchTriggerServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.dispatch.service.impl.DispatchTriggerServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.dispatch.model.DispatchTrigger addDispatchTrigger(
			long userId, String name, String type,
			com.liferay.portal.kernel.util.UnicodeProperties
				typeSettingsUnicodeProperties)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addDispatchTrigger(
			userId, name, type, typeSettingsUnicodeProperties);
	}

	public static void deleteDispatchTrigger(long dispatchTriggerId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteDispatchTrigger(dispatchTriggerId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.dispatch.model.DispatchTrigger
			updateDispatchTrigger(
				long dispatchTriggerId, boolean active, String cronExpression,
				int endDateMonth, int endDateDay, int endDateYear,
				int endDateHour, int endDateMinute, boolean neverEnd,
				int startDateMonth, int startDateDay, int startDateYear,
				int startDateHour, int startDateMinute)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateDispatchTrigger(
			dispatchTriggerId, active, cronExpression, endDateMonth, endDateDay,
			endDateYear, endDateHour, endDateMinute, neverEnd, startDateMonth,
			startDateDay, startDateYear, startDateHour, startDateMinute);
	}

	public static com.liferay.dispatch.model.DispatchTrigger
			updateDispatchTrigger(
				long dispatchTriggerId, String name,
				com.liferay.portal.kernel.util.UnicodeProperties
					typeSettingsUnicodeProperties)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateDispatchTrigger(
			dispatchTriggerId, name, typeSettingsUnicodeProperties);
	}

	public static DispatchTriggerService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<DispatchTriggerService, DispatchTriggerService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(DispatchTriggerService.class);

		ServiceTracker<DispatchTriggerService, DispatchTriggerService>
			serviceTracker =
				new ServiceTracker
					<DispatchTriggerService, DispatchTriggerService>(
						bundle.getBundleContext(), DispatchTriggerService.class,
						null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}