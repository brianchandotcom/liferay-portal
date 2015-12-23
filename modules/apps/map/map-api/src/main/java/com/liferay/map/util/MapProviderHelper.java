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

package com.liferay.map.util;

import com.liferay.map.constants.MapProviderWebKeys;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalService;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(immediate = true, service = MapProviderHelper.class)
public class MapProviderHelper {

	public String getMapProviderKey(long companyId) {
		PortletPreferences companyPortletPreferences =
			PrefsPropsUtil.getPreferences(companyId);

		return companyPortletPreferences.getValue(
			MapProviderWebKeys.MAP_PROVIDER_KEY, null);
	}

	public String getMapProviderKey(long companyId, long groupId) {
		String companyMapProviderKey = getMapProviderKey(companyId);

		Group group = groupLocalService.fetchGroup(groupId);

		if (group == null) {
			return companyMapProviderKey;
		}
		else {
			if (group.isStagingGroup()) {
				group = group.getLiveGroup();
			}

			return GetterUtil.getString(
				group.getTypeSettingsProperty(
					MapProviderWebKeys.MAP_PROVIDER_KEY),
				companyMapProviderKey);
		}
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		this.groupLocalService = groupLocalService;
	}

	protected GroupLocalService groupLocalService;

}