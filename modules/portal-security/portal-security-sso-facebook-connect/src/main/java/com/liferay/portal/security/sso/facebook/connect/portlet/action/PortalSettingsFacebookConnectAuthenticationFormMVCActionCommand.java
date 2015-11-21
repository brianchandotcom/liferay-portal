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

package com.liferay.portal.security.sso.facebook.connect.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.sso.facebook.connect.constants.FacebookConnectConstants;
import com.liferay.portal.settings.portlet.action.BasePortalSettingsFormMVCActionCommand;
import com.liferay.portal.settings.web.constants.PortalSettingsPortletKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Tomas Polesovsky
 * @author Stian Sigvartsen
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + PortalSettingsPortletKeys.PORTAL_SETTINGS,
		"mvc.command.name=/portal_settings/edit_company_facebook_connect_configuration"
	},
	service = MVCActionCommand.class
)
public class PortalSettingsFacebookConnectAuthenticationFormMVCActionCommand
	extends BasePortalSettingsFormMVCActionCommand {

	@Override
	protected void doValidateForm(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		boolean facebookEnabled = ParamUtil.getBoolean(
			actionRequest, "facebook--enabled");

		if (!facebookEnabled) {
			return;
		}

		String facebookGraphURL = ParamUtil.getString(
			actionRequest, "facebook--graphURL");
		String facebookOauthAuthURL = ParamUtil.getString(
			actionRequest, "facebook--oauthAuthURL");
		String facebookOauthRedirectURL = ParamUtil.getString(
			actionRequest, "facebook--oauthRedirectURL");
		String facebookOauthTokenURL = ParamUtil.getString(
			actionRequest, "facebook--oauthTokenURL");

		if (Validator.isNotNull(facebookGraphURL) &&
			!Validator.isUrl(facebookGraphURL)) {

			SessionErrors.add(actionRequest, "facebookConnectGraphURLInvalid");
		}

		if (Validator.isNotNull(facebookOauthAuthURL) &&
			!Validator.isUrl(facebookOauthAuthURL)) {

			SessionErrors.add(
				actionRequest, "facebookConnectOauthAuthURLInvalid");
		}

		if (Validator.isNotNull(facebookOauthRedirectURL) &&
			!Validator.isUrl(facebookOauthRedirectURL)) {

			SessionErrors.add(
				actionRequest, "facebookConnectOauthRedirectURLInvalid");
		}

		if (Validator.isNotNull(facebookOauthTokenURL) &&
			!Validator.isUrl(facebookOauthTokenURL)) {

			SessionErrors.add(
				actionRequest, "facebookConnectOauthTokenURLInvalid");
		}
	}

	@Override
	protected String getSettingsId() {
		return FacebookConnectConstants.SERVICE_NAME;
	}

	@Override
	protected String getParameterNamespace() {
		return "facebook--";
	}

}