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

package com.liferay.social.activity.web.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.comparator.ModelResourceComparator;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.social.model.SocialActivityDefinition;
import com.liferay.portlet.social.model.SocialActivitySetting;
import com.liferay.portlet.social.service.SocialActivitySettingService;
import com.liferay.portlet.social.util.SocialConfigurationUtil;
import com.liferay.social.activity.web.constants.SocialActivityPortletKeys;
import com.liferay.social.activity.web.constants.SocialActivityWebKeys;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SocialActivityPortletKeys.SOCIAL_ACTIVITY,
		"mvc.command.name=/", "mvc.command.name=/social_activity/view"
	},
	service = MVCRenderCommand.class
)
public class ViewMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			renderRequest.setAttribute(
				SocialActivityWebKeys.SOCIAL_ACTIVITY_SETTINGS_MAP,
				getActivitySettingsMap(themeDisplay));
		}
		catch (Exception e) {
			if (e instanceof PrincipalException) {
				SessionErrors.add(renderRequest, e.getClass());

				return "/error.jsp";
			}
			else {
				throw new PortletException(e);
			}
		}

		return "/view.jsp";
	}

	protected Map<String, Boolean> getActivitySettingsMap(
			ThemeDisplay themeDisplay)
		throws Exception {

		Map<String, Boolean> activitySettingsMap = new LinkedHashMap<>();

		List<SocialActivitySetting> activitySettings =
			_socialActivitySettingService.getActivitySettings(
				themeDisplay.getSiteGroupIdOrLiveGroupId());

		String[] modelNames = SocialConfigurationUtil.getActivityModelNames();

		Comparator<String> comparator = new ModelResourceComparator(
			themeDisplay.getLocale());

		Arrays.sort(modelNames, comparator);

		for (String modelName : modelNames) {
			List<SocialActivityDefinition> activityDefinitions =
				_socialActivitySettingService.getActivityDefinitions(
					themeDisplay.getScopeGroupId(), modelName);

			for (SocialActivityDefinition activityDefinition :
					activityDefinitions) {

				if (activityDefinition.isCountersEnabled()) {
					activitySettingsMap.put(modelName, false);

					break;
				}
			}
		}

		for (SocialActivitySetting activitySetting : activitySettings) {
			String name = activitySetting.getName();

			if (name.equals("enabled") &&
				activitySettingsMap.containsKey(
					activitySetting.getClassName())) {

				activitySettingsMap.put(
					activitySetting.getClassName(),
					GetterUtil.getBoolean(activitySetting.getValue()));
			}
		}

		return activitySettingsMap;
	}

	@Reference(unbind = "-")
	protected void setSocialActivitySettingService(
		SocialActivitySettingService socialActivitySettingService) {

		_socialActivitySettingService = socialActivitySettingService;
	}

	private volatile SocialActivitySettingService _socialActivitySettingService;

}