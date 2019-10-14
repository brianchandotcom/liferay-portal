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

package com.liferay.portal.settings.authentication.token.web.internal.portlet.action;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.sso.token.constants.TokenConstants;
import com.liferay.portal.settings.authentication.token.web.internal.constants.PortalSettingsTokenConstants;
import com.liferay.portal.settings.portlet.action.PortalSettingsFormContributor;
import com.liferay.portal.settings.portlet.action.PortalSettingsParameterUtil;

import java.util.Optional;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Stian Sigvartsen
 */
@Component(service = PortalSettingsFormContributor.class)
public class TokenPortalSettingsFormContributor
	implements PortalSettingsFormContributor {

	@Override
	public Optional<String> getDeleteMVCActionCommandNameOptional() {
		return Optional.of("/portal_settings/token_delete");
	}

	@Override
	public String getParameterNamespace() {
		return PortalSettingsTokenConstants.FORM_PARAMETER_NAMESPACE;
	}

	@Override
	public Optional<String> getSaveMVCActionCommandNameOptional() {
		return Optional.of("/portal_settings/token");
	}

	@Override
	public String getSettingsId() {
		return TokenConstants.SERVICE_NAME;
	}

	@Override
	public void validateForm(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		boolean tokenEnabled = PortalSettingsParameterUtil.getBoolean(
			actionRequest, this, "enabled");

		if (!tokenEnabled) {
			return;
		}

		String logoutRedirectURL = PortalSettingsParameterUtil.getString(
			actionRequest, this, "logoutRedirectURL");

		if (Validator.isNotNull(logoutRedirectURL) &&
			!Validator.isUrl(logoutRedirectURL)) {

			SessionErrors.add(actionRequest, "logoutRedirectURLInvalid");
		}
	}

}