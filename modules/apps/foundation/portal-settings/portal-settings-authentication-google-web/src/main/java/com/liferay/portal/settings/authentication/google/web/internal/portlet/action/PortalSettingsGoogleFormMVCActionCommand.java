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

package com.liferay.portal.settings.authentication.google.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.sso.google.constants.GoogleConstants;
import com.liferay.portal.settings.authentication.google.web.internal.portlet.constants.PortalSettingsGoogleConstants;
import com.liferay.portal.settings.portlet.action.BasePortalSettingsFormMVCActionCommand;
import com.liferay.portal.settings.web.constants.PortalSettingsPortletKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Stian Sigvartsen
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + PortalSettingsPortletKeys.PORTAL_SETTINGS,
		"mvc.command.name=/portal_settings/google"
	},
	service = MVCActionCommand.class
)
public class PortalSettingsGoogleFormMVCActionCommand
	extends BasePortalSettingsFormMVCActionCommand {

	@Override
	protected void doValidateForm(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		boolean googleEnabled = getBoolean(actionRequest, "enabled");

		if (!googleEnabled) {
			return;
		}

		String googleClientId = getString(actionRequest, "clientId");
		String googleClientSecret = getString(actionRequest, "clientSecret");

		if (Validator.isNull(googleClientId)) {
			SessionErrors.add(actionRequest, "googleClientIdInvalid");
		}

		if (Validator.isNull(googleClientSecret)) {
			SessionErrors.add(actionRequest, "googleClientSecretInvalid");
		}
	}

	@Override
	protected String getParameterNamespace() {
		return PortalSettingsGoogleConstants.FORM_PARAMETER_NAMESPACE;
	}

	@Override
	protected String getSettingsId() {
		return GoogleConstants.SERVICE_NAME;
	}

}