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

package com.liferay.calendar.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CalendarImporter. This utility wraps
 * {@link com.liferay.calendar.service.impl.CalendarImporterLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Eduardo Lundgren
 * @see CalendarImporterLocalService
 * @see com.liferay.calendar.service.base.CalendarImporterLocalServiceBaseImpl
 * @see com.liferay.calendar.service.impl.CalendarImporterLocalServiceImpl
 * @generated
 */
@ProviderType
public class CalendarImporterLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.calendar.service.impl.CalendarImporterLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static void importCalEvent(
		com.liferay.portlet.calendar.model.CalEvent calEvent)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().importCalEvent(calEvent);
	}

	public static void importCalEvents()
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().importCalEvents();
	}

	public static void importRolePermissions() {
		getService().importRolePermissions();
	}

	public static CalendarImporterLocalService getService() {
		return _serviceTracker.getService();
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(CalendarImporterLocalService service) {
	}

	private static ServiceTracker<CalendarImporterLocalService, CalendarImporterLocalService> _serviceTracker =
		ServiceTrackerFactory.open(CalendarImporterLocalService.class);
}