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
 * @author Matija Petanjek
 * @see DispatchTriggerService
 * @generated
 */
public class DispatchTriggerServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.dispatch.service.impl.DispatchTriggerServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @param userId
	 * @param name
	 * @param dispatchTaskExecutorType
	 * @param dispatchTaskSettingsUnicodeProperties
	 * @return
	 * @throws PortalException
	 * @deprecated As of Cavanaugh (7.4.x), use {@link #addDispatchTrigger(long,
	 String, UnicodeProperties, String)}
	 */
	@Deprecated
	public static com.liferay.dispatch.model.DispatchTrigger addDispatchTrigger(
			long userId, String name, String dispatchTaskExecutorType,
			com.liferay.portal.kernel.util.UnicodeProperties
				dispatchTaskSettingsUnicodeProperties)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addDispatchTrigger(
			userId, name, dispatchTaskExecutorType,
			dispatchTaskSettingsUnicodeProperties);
	}

	public static com.liferay.dispatch.model.DispatchTrigger addDispatchTrigger(
			long userId, String dispatchTaskExecutorType,
			com.liferay.portal.kernel.util.UnicodeProperties
				dispatchTaskSettingsUnicodeProperties,
			String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addDispatchTrigger(
			userId, dispatchTaskExecutorType,
			dispatchTaskSettingsUnicodeProperties, name);
	}

	public static void deleteDispatchTrigger(long dispatchTriggerId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteDispatchTrigger(dispatchTriggerId);
	}

	public static java.util.List<com.liferay.dispatch.model.DispatchTrigger>
			getDispatchTriggers(int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getDispatchTriggers(start, end);
	}

	public static int getDispatchTriggersCount()
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getDispatchTriggersCount();
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
				com.liferay.dispatch.executor.DispatchTaskClusterMode
					dispatchTaskClusterMode,
				int endDateMonth, int endDateDay, int endDateYear,
				int endDateHour, int endDateMinute, boolean neverEnd,
				boolean overlapAllowed, int startDateMonth, int startDateDay,
				int startDateYear, int startDateHour, int startDateMinute)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateDispatchTrigger(
			dispatchTriggerId, active, cronExpression, dispatchTaskClusterMode,
			endDateMonth, endDateDay, endDateYear, endDateHour, endDateMinute,
			neverEnd, overlapAllowed, startDateMonth, startDateDay,
			startDateYear, startDateHour, startDateMinute);
	}

	/**
	 * @param dispatchTriggerId
	 * @param active
	 * @param cronExpression
	 * @param endDateMonth
	 * @param endDateDay
	 * @param endDateYear
	 * @param endDateHour
	 * @param endDateMinute
	 * @param neverEnd
	 * @param overlapAllowed
	 * @param startDateMonth
	 * @param startDateDay
	 * @param startDateYear
	 * @param startDateHour
	 * @param startDateMinute
	 * @param dispatchTaskClusterMode
	 * @return
	 * @throws PortalException
	 * @deprecated As of Cavanaugh (7.4.x), use {@link
	 #updateDispatchTrigger(long, boolean, String,
	 DispatchTaskClusterMode, int, int, int, int, int, boolean,
	 boolean, int, int, int, int, int)}
	 */
	@Deprecated
	public static com.liferay.dispatch.model.DispatchTrigger
			updateDispatchTrigger(
				long dispatchTriggerId, boolean active, String cronExpression,
				int endDateMonth, int endDateDay, int endDateYear,
				int endDateHour, int endDateMinute, boolean neverEnd,
				boolean overlapAllowed, int startDateMonth, int startDateDay,
				int startDateYear, int startDateHour, int startDateMinute,
				com.liferay.dispatch.executor.DispatchTaskClusterMode
					dispatchTaskClusterMode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateDispatchTrigger(
			dispatchTriggerId, active, cronExpression, endDateMonth, endDateDay,
			endDateYear, endDateHour, endDateMinute, neverEnd, overlapAllowed,
			startDateMonth, startDateDay, startDateYear, startDateHour,
			startDateMinute, dispatchTaskClusterMode);
	}

	/**
	 * @param dispatchTriggerId
	 * @param name
	 * @param dispatchTaskSettingsUnicodeProperties
	 * @return
	 * @throws PortalException
	 * @deprecated As of Cavanaugh (7.4.x), use {@link
	 #updateDispatchTrigger(long, UnicodeProperties, String)}
	 */
	@Deprecated
	public static com.liferay.dispatch.model.DispatchTrigger
			updateDispatchTrigger(
				long dispatchTriggerId, String name,
				com.liferay.portal.kernel.util.UnicodeProperties
					dispatchTaskSettingsUnicodeProperties)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateDispatchTrigger(
			dispatchTriggerId, name, dispatchTaskSettingsUnicodeProperties);
	}

	public static com.liferay.dispatch.model.DispatchTrigger
			updateDispatchTrigger(
				long dispatchTriggerId,
				com.liferay.portal.kernel.util.UnicodeProperties
					dispatchTaskSettingsUnicodeProperties,
				String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateDispatchTrigger(
			dispatchTriggerId, dispatchTaskSettingsUnicodeProperties, name);
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