/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.service;

import com.liferay.calendar.model.CalendarResource;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Map;

/**
 * Provides the remote service utility for CalendarResource. This utility wraps
 * <code>com.liferay.calendar.service.impl.CalendarResourceServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Eduardo Lundgren
 * @see CalendarResourceService
 * @generated
 */
public class CalendarResourceServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.calendar.service.impl.CalendarResourceServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CalendarResource addCalendarResource(
			long groupId, long classNameId, long classPK, String classUuid,
			String code, Map<java.util.Locale, String> nameMap,
			Map<java.util.Locale, String> descriptionMap, boolean active,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCalendarResource(
			groupId, classNameId, classPK, classUuid, code, nameMap,
			descriptionMap, active, serviceContext);
	}

	public static CalendarResource deleteCalendarResource(
			long calendarResourceId)
		throws PortalException {

		return getService().deleteCalendarResource(calendarResourceId);
	}

	public static CalendarResource fetchCalendarResource(
			long classNameId, long classPK)
		throws PortalException {

		return getService().fetchCalendarResource(classNameId, classPK);
	}

	public static CalendarResource getCalendarResource(long calendarResourceId)
		throws PortalException {

		return getService().getCalendarResource(calendarResourceId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static List<CalendarResource> search(
		long companyId, long[] groupIds, long[] classNameIds, String keywords,
		boolean active, boolean andOperator, int start, int end,
		OrderByComparator<CalendarResource> orderByComparator) {

		return getService().search(
			companyId, groupIds, classNameIds, keywords, active, andOperator,
			start, end, orderByComparator);
	}

	public static List<CalendarResource> search(
		long companyId, long[] groupIds, long[] classNameIds, String code,
		String name, String description, boolean active, boolean andOperator,
		int start, int end,
		OrderByComparator<CalendarResource> orderByComparator) {

		return getService().search(
			companyId, groupIds, classNameIds, code, name, description, active,
			andOperator, start, end, orderByComparator);
	}

	public static int searchCount(
		long companyId, long[] groupIds, long[] classNameIds, String keywords,
		boolean active) {

		return getService().searchCount(
			companyId, groupIds, classNameIds, keywords, active);
	}

	public static int searchCount(
		long companyId, long[] groupIds, long[] classNameIds, String code,
		String name, String description, boolean active, boolean andOperator) {

		return getService().searchCount(
			companyId, groupIds, classNameIds, code, name, description, active,
			andOperator);
	}

	public static CalendarResource updateCalendarResource(
			long calendarResourceId, Map<java.util.Locale, String> nameMap,
			Map<java.util.Locale, String> descriptionMap, boolean active,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCalendarResource(
			calendarResourceId, nameMap, descriptionMap, active,
			serviceContext);
	}

	public static CalendarResourceService getService() {
		return _service;
	}

	public static void setService(CalendarResourceService service) {
		_service = service;
	}

	private static volatile CalendarResourceService _service;

}