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

package com.liferay.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.constants.PlacesProviderWebKeys;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;

import javax.portlet.PortletPreferences;

/**
 * @author Rodrigo Paulino
 */
public class PlacesProviderUtil {

	public static String getPlacesProviderKey(long companyId) {
		PortletPreferences companyPortletPreferences =
			PrefsPropsUtil.getPreferences(companyId);

		return companyPortletPreferences.getValue(
			PlacesProviderWebKeys.PLACES_PROVIDER_KEY, null);
	}

	public static String getPlacesProviderKey(
		long companyId, long groupId, GroupLocalService groupLocalService) {

		String defaultPlacesProviderKey = getPlacesProviderKey(companyId);

		Group group = groupLocalService.fetchGroup(groupId);

		if (group == null) {
			return defaultPlacesProviderKey;
		}

		if (group.isStagingGroup()) {
			group = group.getLiveGroup();
		}

		return GetterUtil.getString(
			group.getTypeSettingsProperty(
				PlacesProviderWebKeys.PLACES_PROVIDER_KEY),
			defaultPlacesProviderKey);
	}

}