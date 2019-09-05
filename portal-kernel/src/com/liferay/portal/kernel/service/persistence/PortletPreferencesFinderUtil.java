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

package com.liferay.portal.kernel.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class PortletPreferencesFinderUtil {

	public static long countByO_O_P(
		long ownerId, int ownerType, String portletId,
		boolean excludeDefaultPreferences) {

		return getFinder().countByO_O_P(
			ownerId, ownerType, portletId, excludeDefaultPreferences);
	}

	public static long countByO_O_P_P_P(
		long ownerId, int ownerType, long plid, String portletId,
		boolean excludeDefaultPreferences) {

		return getFinder().countByO_O_P_P_P(
			ownerId, ownerType, plid, portletId, excludeDefaultPreferences);
	}

	public static java.util.Map
		<java.io.Serializable,
		 com.liferay.portal.kernel.model.PortletPreferences> fetchByPrimaryKeys(
			java.util.Set<java.io.Serializable> primaryKeys) {

		return getFinder().fetchByPrimaryKeys(primaryKeys);
	}

	public static java.util.List
		<com.liferay.portal.kernel.model.PortletPreferences> findByPortletId(
			String portletId) {

		return getFinder().findByPortletId(portletId);
	}

	public static java.util.List
		<com.liferay.portal.kernel.model.PortletPreferences> findByC_G_O_O_P_P(
			long companyId, long groupId, long ownerId, int ownerType,
			String portletId, boolean privateLayout) {

		return getFinder().findByC_G_O_O_P_P(
			companyId, groupId, ownerId, ownerType, portletId, privateLayout);
	}

	public static PortletPreferencesFinder getFinder() {
		if (_finder == null) {
			_finder = (PortletPreferencesFinder)PortalBeanLocatorUtil.locate(
				PortletPreferencesFinder.class.getName());
		}

		return _finder;
	}

	public void setFinder(PortletPreferencesFinder finder) {
		_finder = finder;
	}

	private static PortletPreferencesFinder _finder;

}