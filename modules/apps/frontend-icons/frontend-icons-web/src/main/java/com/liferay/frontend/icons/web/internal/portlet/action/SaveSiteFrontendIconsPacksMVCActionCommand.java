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

package com.liferay.frontend.icons.web.internal.portlet.action;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.frontend.icons.web.internal.configuration.FrontendIconsPacksConfiguration;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryce Osterhaus
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.SITE_SETTINGS,
		"mvc.command.name=/site_settings/save_site_frontend_icons_packs"
	},
	service = MVCActionCommand.class
)
public class SaveSiteFrontendIconsPacksMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String[] selectedIconPacks = ParamUtil.getStringValues(
			actionRequest, "selectedIconPacks");

		for (int i = 0; i < selectedIconPacks.length; i++) {
			selectedIconPacks[i] = StringUtil.toUpperCase(selectedIconPacks[i]);
		}

		try {
			_configurationProvider.saveGroupConfiguration(
				FrontendIconsPacksConfiguration.class,
				themeDisplay.getSiteGroupId(),
				HashMapDictionaryBuilder.<String, Object>put(
					"selectedIconPacks", selectedIconPacks
				).build());
		}
		catch (ConfigurationException configurationException) {
			_log.error(
				"Unable to save group frontend icon packs configuration",
				configurationException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SaveSiteFrontendIconsPacksMVCActionCommand.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

}