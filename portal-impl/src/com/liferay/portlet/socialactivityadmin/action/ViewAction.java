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

package com.liferay.portlet.socialactivityadmin.action;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.comparator.ModelResourceComparator;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.social.model.SocialActivityCounterConstants;
import com.liferay.portlet.social.model.SocialActivityCounterDefinition;
import com.liferay.portlet.social.model.SocialActivityDefinition;
import com.liferay.portlet.social.model.SocialActivitySetting;
import com.liferay.portlet.social.service.SocialActivitySettingLocalServiceUtil;
import com.liferay.portlet.social.util.SocialConfigurationUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
* @author Zsolt Szabó
*/
public class ViewAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (cmd.equals("updateSettings")) {
			updateSettings(actionRequest);
		}

		sendRedirect(actionRequest, actionResponse);
	}

	@Override
	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Map<String, Boolean> activitySettingsMap =
			new LinkedHashMap<String, Boolean>();

		List<SocialActivitySetting> activitySettings =
			SocialActivitySettingLocalServiceUtil.getActivitySettings(
				themeDisplay.getScopeGroupIdOrLiveGroupId());

		String[] modelNames = SocialConfigurationUtil.getActivityModelNames();

		Comparator<String> comparator = new ModelResourceComparator(
			themeDisplay.getLocale());

		Arrays.sort(modelNames, comparator);

		for (String className : modelNames) {
			activitySettingsMap.put(className, false);
		}

		for (SocialActivitySetting activitySetting : activitySettings) {
			if (activitySetting.getName().equals("enabled") &&
				activitySettingsMap.containsKey(
					activitySetting.getClassName())) {

				activitySettingsMap.put(
					activitySetting.getClassName(), GetterUtil.getBoolean(
						activitySetting.getValue(), false));
			}
		}

		renderRequest.setAttribute(
			"social-activity-setting-mapping", activitySettingsMap);

		return mapping.findForward("portlet.social_activity_admin.view");
	}

	protected void updateSettings(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = themeDisplay.getScopeGroupIdOrLiveGroupId();

		String jsonSettings =
			ParamUtil.getString(actionRequest, "jsonSettings");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(jsonSettings);

		JSONArray actions = jsonObject.getJSONArray("actions");

		String modelName = jsonObject.getString("modelName");

		for (int i = 0; i < actions.length(); i++) {
			JSONObject action = actions.getJSONObject(i);

			int activityType = action.getInt("activityType");

			SocialActivityDefinition activityDefinition =
				SocialActivitySettingLocalServiceUtil.getActivityDefinition(
					groupId, modelName, activityType);

			if (Validator.isNotNull(activityDefinition)) {
				List<SocialActivityCounterDefinition> counters =
					new ArrayList<SocialActivityCounterDefinition>();

				counters.add(
					updateCounter(
						action, activityDefinition,
						SocialActivityCounterConstants.NAME_PARTICIPATION));

				counters.add(
					updateCounter(
						action, activityDefinition,
						SocialActivityCounterConstants.NAME_CONTRIBUTION));

				counters.add(
					updateCounter(
						action, activityDefinition,
						SocialActivityCounterConstants.NAME_POPULARITY));

				SocialActivitySettingLocalServiceUtil.updateActivitySettings(
					groupId, modelName, activityType, counters);
			}
		}
	}

	private SocialActivityCounterDefinition updateCounter(
		JSONObject action, SocialActivityDefinition activityDefinition,
		String counterName) {

		SocialActivityCounterDefinition counter =
			activityDefinition.getActivityCounterDefinition(counterName);

		if (Validator.isNull(counter)) {
			counter = new SocialActivityCounterDefinition();

			counter.setName(counterName);
		}

		if (counterName.equals(
				SocialActivityCounterConstants.NAME_PARTICIPATION)) {

			counter.setOwnerType(SocialActivityCounterConstants.TYPE_ACTOR);
		}
		else if (counterName.equals(
					SocialActivityCounterConstants.NAME_CONTRIBUTION)) {

			counter.setOwnerType(SocialActivityCounterConstants.TYPE_CREATOR);
		}
		else {
			counter.setOwnerType(SocialActivityCounterConstants.TYPE_ASSET);

			counterName = SocialActivityCounterConstants.NAME_CONTRIBUTION;
		}

		counter.setIncrement(action.getInt(counterName + "Value"));
		counter.setLimitValue(action.getInt(counterName + "LimitValue"));
		counter.setLimitPeriod(action.getInt(counterName + "LimitPeriod"));

		return counter;
	}

}