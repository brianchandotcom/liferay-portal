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

package com.liferay.knowledge.base.web.internal.portlet.action;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.knowledge.base.configuration.KBServiceConfigurationProvider;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia García
 */
@Component(
	property = {
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.INSTANCE_SETTINGS,
		"mvc.command.name=/instance_settings/edit_kb_article_expiration_date_configuration"
	},
	service = MVCActionCommand.class
)
public class EditKBArticleExpirationDateConfigurationMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			_kbServiceConfigurationProvider.updateExpirationDateConfiguration(
				ParamUtil.getInteger(actionRequest, "checkInterval"),
				themeDisplay.getCompanyId(),
				ParamUtil.getInteger(
					actionRequest, "expirationDateNotificationDateWeeks"));
		}
		catch (Exception exception) {
			SessionErrors.add(actionRequest, exception.getClass());

			actionResponse.sendRedirect(
				ParamUtil.getString(actionRequest, "redirect"));
		}
	}

	@Reference
	private KBServiceConfigurationProvider _kbServiceConfigurationProvider;

}