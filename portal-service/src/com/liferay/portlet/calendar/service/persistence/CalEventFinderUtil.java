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

package com.liferay.portlet.calendar.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Brian Wing Shun Chan
 * @deprecated As of 7.0.0, with no direct replacement
 * @generated
 */
@Deprecated
@ProviderType
public class CalEventFinderUtil {
	public static int countByG_SD_T(long groupId, java.util.Date startDateGT,
		java.util.Date startDateLT, boolean timeZoneSensitive,
		java.lang.String[] types) {
		return getFinder()
				   .countByG_SD_T(groupId, startDateGT, startDateLT,
			timeZoneSensitive, types);
	}

	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByFutureReminders() {
		return getFinder().findByFutureReminders();
	}

	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByNoAssets() {
		return getFinder().findByNoAssets();
	}

	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByG_SD_T(
		long groupId, java.util.Date startDateGT, java.util.Date startDateLT,
		boolean timeZoneSensitive, java.lang.String[] types) {
		return getFinder()
				   .findByG_SD_T(groupId, startDateGT, startDateLT,
			timeZoneSensitive, types);
	}

	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByG_SD_T(
		long groupId, java.util.Date startDateGT, java.util.Date startDateLT,
		boolean timeZoneSensitive, java.lang.String[] types, int start, int end) {
		return getFinder()
				   .findByG_SD_T(groupId, startDateGT, startDateLT,
			timeZoneSensitive, types, start, end);
	}

	public static CalEventFinder getFinder() {
		if (_finder == null) {
			_finder = (CalEventFinder)PortalBeanLocatorUtil.locate(CalEventFinder.class.getName());

			ReferenceRegistry.registerReference(CalEventFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(CalEventFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(CalEventFinderUtil.class, "_finder");
	}

	private static CalEventFinder _finder;
}