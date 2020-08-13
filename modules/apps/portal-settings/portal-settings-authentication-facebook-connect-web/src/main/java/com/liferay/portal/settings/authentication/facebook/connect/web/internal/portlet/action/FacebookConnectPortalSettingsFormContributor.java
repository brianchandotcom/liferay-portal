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

package com.liferay.portal.settings.authentication.facebook.connect.web.internal.portlet.action;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.sso.facebook.connect.constants.FacebookConnectConstants;
import com.liferay.portal.settings.authentication.facebook.connect.web.internal.constants.PortalSettingsFacebookConnectConstants;
import com.liferay.portal.settings.portlet.action.PortalSettingsFormContributor;
import com.liferay.portal.settings.portlet.action.PortalSettingsParameterUtil;

import java.util.Optional;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Tomas Polesovsky
 * @author Stian Sigvartsen
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
@Component(immediate = true, service = PortalSettingsFormContributor.class)
@Deprecated
public class FacebookConnectPortalSettingsFormContributor
	implements PortalSettingsFormContributor {

	@Override
	public Optional<String> getDeleteMVCActionCommandNameOptional() {
		return Optional.of("/portal_settings/facebook_connect_delete");
	}

	@Override
	public String getParameterNamespace() {
		return PortalSettingsFacebookConnectConstants.FORM_PARAMETER_NAMESPACE;
	}

	@Override
	public Optional<String> getSaveMVCActionCommandNameOptional() {
		return Optional.of("/portal_settings/facebook_connect");
	}

	@Override
	public String getSettingsId() {
		return FacebookConnectConstants.SERVICE_NAME;
	}

	@Override
	public void validateForm(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		boolean facebookEnabled = PortalSettingsParameterUtil.getBoolean(
			actionRequest, this, "enabled");

		if (!facebookEnabled) {
			return;
		}

		String facebookGraphURL = PortalSettingsParameterUtil.getString(
			actionRequest, this, "graphURL");
		String facebookOauthAuthURL = PortalSettingsParameterUtil.getString(
			actionRequest, this, "oauthAuthURL");
		String facebookOauthRedirectURL = PortalSettingsParameterUtil.getString(
			actionRequest, this, "oauthRedirectURL");
		String facebookOauthTokenURL = PortalSettingsParameterUtil.getString(
			actionRequest, this, "oauthTokenURL");

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

}