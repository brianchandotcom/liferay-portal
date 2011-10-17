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
import com.liferay.portlet.social.model.SocialActivityCounterDefinition;
import com.liferay.portlet.social.model.SocialActivityDefinition;
import com.liferay.portlet.social.service.base.SocialActivitySettingServiceBaseImpl;

import java.util.List;

/**
 * @author Zsolt Berentey
 */
public class SocialActivitySettingServiceImpl
	extends SocialActivitySettingServiceBaseImpl {

	public SocialActivityDefinition getActivityDefinition(
			long groupId, String className, int activityType)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (!permissionChecker.isGroupAdmin(groupId)) {
			throw new PrincipalException();
		}

		return socialActivitySettingLocalService.getActivityDefinition(
			groupId, className, activityType);
	}

	public List<SocialActivityDefinition> getActivityDefinitions(
			long groupId, String className)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (!permissionChecker.isGroupAdmin(groupId)) {
			throw new PrincipalException();
		}

		return socialActivitySettingLocalService.getActivityDefinitions(
			groupId, className);
	}

	public JSONArray getJSONActivityDefinitions(
			long groupId, String className)
		throws PortalException, SystemException {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<SocialActivityDefinition> activityDefinitions =
			socialActivitySettingLocalService.getActivityDefinitions(
				groupId, className);

		for (SocialActivityDefinition activityDefinition :
				activityDefinitions) {

			JSONObject activityDefinitionJSONObject =
				JSONFactoryUtil.createJSONObject(
					JSONFactoryUtil.looseSerialize(activityDefinition));

			JSONArray activityCounterDefinitionsJSONArray =
				JSONFactoryUtil.createJSONArray();

			for (SocialActivityCounterDefinition activityCounterDefinition :
					activityDefinition.getActivityCounterDefinitions()) {

				JSONObject activityCounterDefinitionJSONObject =
					JSONFactoryUtil.createJSONObject(
						JSONFactoryUtil.looseSerialize(
							activityCounterDefinition));

				activityCounterDefinitionsJSONArray.put(
					activityCounterDefinitionJSONObject);
			}

			activityDefinitionJSONObject.put(
				"counters", activityCounterDefinitionsJSONArray);

			jsonArray.put(activityDefinitionJSONObject);
		}

		return jsonArray;
	}

	public void updateActivitySetting(
			long groupId, String className, boolean enabled)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (!permissionChecker.isGroupAdmin(groupId)) {
			throw new PrincipalException();
		}

		socialActivitySettingLocalService.updateActivitySetting(
			groupId, className, enabled);
	}

	public void updateActivitySettings(
			long groupId, String className, int activityType,
			List<SocialActivityCounterDefinition> counters)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (!permissionChecker.isGroupAdmin(groupId)) {
			throw new PrincipalException();
		}

		socialActivitySettingLocalService.updateActivitySettings(
			groupId, className, activityType, counters);
	}

}