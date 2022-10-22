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

package com.liferay.site.initializer.extender.web.internal.portlet.action;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.initializer.SiteInitializerRegistry;
import com.liferay.site.initializer.extender.web.internal.constants.SiteInitializerExtenderUpdatePortletKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author José Abelenda
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SiteInitializerExtenderUpdatePortletKeys.SITE_INITIALIZER_EXTENDER_UPDATE,
		"mvc.command.name=/site_initializer_extender/synchronize_site_initializer_extender"
	},
	service = MVCActionCommand.class
)
public class SynchronizeSiteInitializerExtenderMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Group group = _groupLocalService.getGroup(
			themeDisplay.getScopeGroupId());

		UnicodeProperties unicodeProperties = group.getTypeSettingsProperties();

		String siteInitializerKey = unicodeProperties.get("siteInitializerKey");

		if (Validator.isNull(siteInitializerKey)) {
			SessionErrors.add(actionRequest, "siteInitializerKeyIsNull");
			hideDefaultErrorMessage(actionRequest);

			return;
		}

		SiteInitializer siteInitializer =
			_siteInitializerRegistry.getSiteInitializer(siteInitializerKey);

		if (siteInitializer == null) {
			SessionErrors.add(
				actionRequest, "siteInitializerIsNotDeployed",
				siteInitializerKey);
			hideDefaultErrorMessage(actionRequest);

			return;
		}

		siteInitializer.initialize(themeDisplay.getScopeGroupId());
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private SiteInitializerRegistry _siteInitializerRegistry;

}