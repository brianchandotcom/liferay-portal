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

package com.liferay.portal.kernel.service;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.PortletPreferencesIds;
import com.liferay.portal.kernel.util.StringUtil;

import javax.portlet.PortletPreferences;

/**
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class ServiceContextUtil {

	public static Object deserialize(JSONObject jsonObject) {
		ServiceContext serviceContext = new ServiceContext();

		// Theme display

		serviceContext.setCompanyId(jsonObject.getLong("companyId"));
		serviceContext.setLayoutFullURL(jsonObject.getString("layoutFullURL"));
		serviceContext.setLayoutURL(jsonObject.getString("layoutURL"));
		serviceContext.setPathMain(jsonObject.getString("pathMain"));
		serviceContext.setPlid(jsonObject.getLong("plid"));
		serviceContext.setPortalURL(jsonObject.getString("portalURL"));
		serviceContext.setScopeGroupId(jsonObject.getLong("scopeGroupId"));
		serviceContext.setUserDisplayURL(
			jsonObject.getString("userDisplayURL"));
		serviceContext.setUserId(jsonObject.getLong("userId"));

		// Permissions

		String[] groupPermissions = StringUtil.split(
			jsonObject.getString("groupPermissions"));
		String[] guestPermissions = StringUtil.split(
			jsonObject.getString("guestPermissions"));

		serviceContext.setAddGroupPermissions(
			jsonObject.getBoolean("addGroupPermissions"));
		serviceContext.setAddGuestPermissions(
			jsonObject.getBoolean("addGuestPermissions"));
		serviceContext.setGroupPermissions(groupPermissions);
		serviceContext.setGuestPermissions(guestPermissions);

		// Asset

		long[] assetCategoryIds = StringUtil.split(
			jsonObject.getString("assetCategoryIds"), 0L);
		String[] assetTagNames = StringUtil.split(
			jsonObject.getString("assetTagNames"));

		serviceContext.setAssetCategoryIds(assetCategoryIds);
		serviceContext.setAssetTagNames(assetTagNames);

		// Workflow

		serviceContext.setWorkflowAction(jsonObject.getInt("workflowAction"));

		return serviceContext;
	}

	public static PortletPreferences getPortletPreferences(
		ServiceContext serviceContext) {

		if (serviceContext == null) {
			return null;
		}

		PortletPreferencesIds portletPreferencesIds =
			serviceContext.getPortletPreferencesIds();

		if (portletPreferencesIds == null) {
			return null;
		}

		return PortletPreferencesLocalServiceUtil.getPreferences(
			portletPreferencesIds.getCompanyId(),
			portletPreferencesIds.getOwnerId(),
			portletPreferencesIds.getOwnerType(),
			portletPreferencesIds.getPlid(),
			portletPreferencesIds.getPortletId());
	}

}