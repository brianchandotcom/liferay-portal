/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portlet.social.model.SocialActivityCounterDefinition;
import com.liferay.portlet.social.model.SocialActivityDefinition;
import com.liferay.portlet.social.model.SocialActivitySetting;
import com.liferay.portlet.social.service.base.SocialActivitySettingServiceBaseImpl;
import com.liferay.portlet.social.service.permission.SocialActivityPermission;
import com.liferay.portlet.social.util.comparator.SocialActivityDefinitionNameComparator;

import java.util.Collections;
import java.util.List;

/**
 * @author Zsolt Berentey
 */
public class SocialActivitySettingServiceImpl
	extends SocialActivitySettingServiceBaseImpl {

	@Override
	public SocialActivityDefinition getActivityDefinition(
			long groupId, String className, int activityType)
		throws PortalException, SystemException {

		SocialActivityPermission.check(
			getPermissionChecker(), groupId, ActionKeys.VIEW);

		return socialActivitySettingLocalService.getActivityDefinition(
			groupId, className, activityType);
	}

	@Override
	public List<SocialActivityDefinition> getActivityDefinitions(
			long groupId, String className)
		throws PortalException, SystemException {

		SocialActivityPermission.check(
			getPermissionChecker(), groupId, ActionKeys.VIEW);

		return socialActivitySettingLocalService.getActivityDefinitions(
			groupId, className);
	}

	@Override
	public List<SocialActivitySetting> getActivitySettings(long groupId)
		throws PortalException, SystemException {

		SocialActivityPermission.check(
			getPermissionChecker(), groupId, ActionKeys.VIEW);

		return socialActivitySettingLocalService.getActivitySettings(groupId);
	}

	@Override
	public JSONArray getJSONActivityDefinitions(long groupId, String className)
		throws PortalException, SystemException {

		SocialActivityPermission.check(
			getPermissionChecker(), groupId, ActionKeys.VIEW);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<SocialActivityDefinition> activityDefinitions =
			socialActivitySettingLocalService.getActivityDefinitions(
				groupId, className);

		Collections.sort(
			activityDefinitions,
			new SocialActivityDefinitionNameComparator(
				LocaleUtil.getMostRelevantLocale()));

		for (SocialActivityDefinition activityDefinition :
				activityDefinitions) {

			if (!activityDefinition.isCountersEnabled()) {
				continue;
			}

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

	@Override
	public void updateActivitySetting(
			long groupId, String className, boolean enabled)
		throws PortalException, SystemException {

		SocialActivityPermission.check(
			getPermissionChecker(), groupId, ActionKeys.CONFIGURATION);

		socialActivitySettingLocalService.updateActivitySetting(
			groupId, className, enabled);
	}

	@Override
	public void updateActivitySetting(
			long groupId, String className, int activityType,
			SocialActivityCounterDefinition activityCounterDefinition)
		throws PortalException, SystemException {

		SocialActivityPermission.check(
			getPermissionChecker(), groupId, ActionKeys.CONFIGURATION);

		socialActivitySettingLocalService.updateActivitySetting(
			groupId, className, activityType, activityCounterDefinition);
	}

	@Override
	public void updateActivitySettings(
			long groupId, String className, int activityType,
			List<SocialActivityCounterDefinition> activityCounterDefinitions)
		throws PortalException, SystemException {

		SocialActivityPermission.check(
			getPermissionChecker(), groupId, ActionKeys.CONFIGURATION);

		socialActivitySettingLocalService.updateActivitySettings(
			groupId, className, activityType, activityCounterDefinitions);
	}

}