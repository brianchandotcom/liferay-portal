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

package com.liferay.frontend.js.web.internal.servlet.taglib;

import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.frontend.js.web.internal.configuration.CustomDialogsSettingsConfiguration;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.aui.JSFragment;
import com.liferay.portal.kernel.servlet.taglib.aui.ScriptData;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Diego Nascimento
 */
@Component(
	configurationPid = "com.liferay.frontend.js.web.internal.configuration.CustomDialogsSettingsConfiguration",
	service = DynamicInclude.class
)
public class CustomDialogsBottomJSPDynamicInclude implements DynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			Group group = themeDisplay.getScopeGroup();

			CustomDialogsSettingsConfiguration
				customDialogsSettingsConfiguration =
					_configurationProvider.getGroupConfiguration(
						CustomDialogsSettingsConfiguration.class,
						group.getGroupId());

			ScriptData scriptData = new ScriptData();

			scriptData.append(
				_portal.getPortletId(httpServletRequest),
				new JSFragment(
					StringBundler.concat(
						"Liferay.CustomDialogs = {enabled: ",
						customDialogsSettingsConfiguration.enabled(), "};")));

			scriptData.writeTo(httpServletResponse.getWriter());
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	@Override
	public void register(
		DynamicInclude.DynamicIncludeRegistry dynamicIncludeRegistry) {

		dynamicIncludeRegistry.register("/html/common/themes/bottom.jsp#post");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CustomDialogsBottomJSPDynamicInclude.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private NPMResolver _npmResolver;

	@Reference
	private Portal _portal;

}