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

package com.liferay.social.activity.web.internal.portlet.action;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.social.activity.web.internal.constants.SocialActivityPortletKeys;
import com.liferay.social.kernel.model.SocialActivityCounterConstants;
import com.liferay.social.kernel.model.SocialActivityCounterDefinition;
import com.liferay.social.kernel.model.SocialActivityDefinition;
import com.liferay.social.kernel.service.SocialActivitySettingService;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SocialActivityPortletKeys.SOCIAL_ACTIVITY,
		"mvc.command.name=/social_activity/edit_activity_settings"
	},
	service = MVCActionCommand.class
)
public class EditActivitySettingsMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.UPDATE)) {
				updateActivitySettings(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof PrincipalException) {
				SessionErrors.add(actionRequest, e.getClass());
			}
			else {
				throw e;
			}
		}
	}

	@Reference(unbind = "-")
	protected void setSocialActivitySettingService(
		SocialActivitySettingService socialActivitySettingService) {

		_socialActivitySettingService = socialActivitySettingService;
	}

	protected SocialActivityCounterDefinition updateActivityCounterDefinition(
		JSONObject actionJSONObject,
		SocialActivityDefinition activityDefinition,
		String activityCounterName) {

		SocialActivityCounterDefinition activityCounterDefinition =
			activityDefinition.getActivityCounterDefinition(
				activityCounterName);

		if (activityCounterDefinition == null) {
			activityCounterDefinition = new SocialActivityCounterDefinition();

			activityCounterDefinition.setName(activityCounterName);
		}

		if (activityCounterName.equals(
				SocialActivityCounterConstants.NAME_CONTRIBUTION)) {

			activityCounterDefinition.setOwnerType(
				SocialActivityCounterConstants.TYPE_CREATOR);
		}
		else if (activityCounterName.equals(
					SocialActivityCounterConstants.NAME_PARTICIPATION)) {

			activityCounterDefinition.setOwnerType(
				SocialActivityCounterConstants.TYPE_ACTOR);
		}
		else if (activityCounterName.equals(
					SocialActivityCounterConstants.NAME_POPULARITY)) {

			activityCounterDefinition.setOwnerType(
				SocialActivityCounterConstants.TYPE_ASSET);

			activityCounterName =
				SocialActivityCounterConstants.NAME_CONTRIBUTION;
		}
		else {
			throw new IllegalArgumentException();
		}

		activityCounterDefinition.setEnabled(
			actionJSONObject.getBoolean("active"));
		activityCounterDefinition.setIncrement(
			actionJSONObject.getInt(activityCounterName + "Increment"));
		activityCounterDefinition.setLimitPeriod(
			actionJSONObject.getInt(activityCounterName + "LimitPeriod"));
		activityCounterDefinition.setLimitValue(
			actionJSONObject.getInt(activityCounterName + "LimitValue"));

		return activityCounterDefinition;
	}

	protected void updateActivitySettings(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String settingsJSON = ParamUtil.getString(
			actionRequest, "settingsJSON");

		JSONObject settingsJSONObject = JSONFactoryUtil.createJSONObject(
			settingsJSON);

		JSONArray actionsJSONArray = settingsJSONObject.getJSONArray("actions");

		String modelName = settingsJSONObject.getString("modelName");

		for (int i = 0; i < actionsJSONArray.length(); i++) {
			JSONObject actionJSONObject = actionsJSONArray.getJSONObject(i);

			int activityType = actionJSONObject.getInt("activityType");

			SocialActivityDefinition activityDefinition =
				_socialActivitySettingService.getActivityDefinition(
					themeDisplay.getSiteGroupIdOrLiveGroupId(), modelName,
					activityType);

			if (activityDefinition == null) {
				continue;
			}

			List<SocialActivityCounterDefinition> activityCounterDefinitions =
				new ArrayList<>();

			activityCounterDefinitions.add(
				updateActivityCounterDefinition(
					actionJSONObject, activityDefinition,
					SocialActivityCounterConstants.NAME_CONTRIBUTION));

			activityCounterDefinitions.add(
				updateActivityCounterDefinition(
					actionJSONObject, activityDefinition,
					SocialActivityCounterConstants.NAME_PARTICIPATION));

			activityCounterDefinitions.add(
				updateActivityCounterDefinition(
					actionJSONObject, activityDefinition,
					SocialActivityCounterConstants.NAME_POPULARITY));

			_socialActivitySettingService.updateActivitySettings(
				themeDisplay.getSiteGroupIdOrLiveGroupId(), modelName,
				activityType, activityCounterDefinitions);
		}
	}

	private SocialActivitySettingService _socialActivitySettingService;

}