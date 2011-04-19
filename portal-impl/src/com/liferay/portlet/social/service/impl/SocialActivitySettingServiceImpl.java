/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.social.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.social.model.SocialActivityCounter;
import com.liferay.portlet.social.model.SocialActivityDefinition;
import com.liferay.portlet.social.service.base.SocialActivitySettingServiceBaseImpl;

import java.util.List;

/**
 * @author Zsolt Berentey
 */
public class SocialActivitySettingServiceImpl
	extends SocialActivitySettingServiceBaseImpl {

	public SocialActivityDefinition getActivityDefinition(
			long groupId, String className, int activityKey)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (permissionChecker.isOmniadmin() ||
			permissionChecker.isCompanyAdmin() ||
			permissionChecker.isGroupAdmin(groupId)) {

			return socialActivitySettingLocalService.getActivityDefinition(
				groupId, className, activityKey);
		}

		throw new PrincipalException();
	}

	public List<SocialActivityDefinition> getActivityDefinitions(
			long groupId, String className)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (permissionChecker.isOmniadmin() ||
			permissionChecker.isCompanyAdmin() ||
			permissionChecker.isGroupAdmin(groupId)) {

			return socialActivitySettingLocalService.getActivityDefinitions(
				groupId, className);
		}

		throw new PrincipalException();
	}

	public JSONArray getJSONActivityDefinitions(
			long groupId, String className)
		throws PortalException, SystemException {

		List<SocialActivityDefinition> activityDefinitions =
			socialActivitySettingLocalService.getActivityDefinitions(
				groupId, className);

		JSONArray jsonActivityDefinitions = JSONFactoryUtil.createJSONArray();

		for (SocialActivityDefinition activityDefinition :
				activityDefinitions) {

			JSONObject jsonActivityDefinition =
				JSONFactoryUtil.createJSONObject(
					JSONFactoryUtil.looseSerialize(activityDefinition));

			JSONArray jsonCounters = JSONFactoryUtil.createJSONArray();

			for (SocialActivityCounter activityCounter :
					activityDefinition.getCounters()) {

				JSONObject jsonCounter =
					JSONFactoryUtil.createJSONObject(
						JSONFactoryUtil.looseSerialize(activityCounter));

				jsonCounters.put(jsonCounter);
			}

			jsonActivityDefinition.put("counters", jsonCounters);

			jsonActivityDefinitions.put(jsonActivityDefinition);
		}

		return jsonActivityDefinitions;
	}

	public void updateActivitySettings(
			long groupId, String className, int activityKey,
			List<SocialActivityCounter> counters)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (permissionChecker.isOmniadmin() ||
			permissionChecker.isCompanyAdmin() ||
			permissionChecker.isGroupAdmin(groupId)) {

			socialActivitySettingLocalService.updateActivitySettings(
				groupId, className, activityKey, counters);

			return;
		}

		throw new PrincipalException();
	}

	public void updateGroupSetting(
			long groupId, String className, boolean enabled)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (permissionChecker.isOmniadmin() ||
			permissionChecker.isCompanyAdmin() ||
			permissionChecker.isGroupAdmin(groupId)) {

			socialActivitySettingLocalService.updateGroupSetting(
				groupId, className, enabled);

			return;
		}

		throw new PrincipalException();
	}

}