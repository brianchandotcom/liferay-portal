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

package com.liferay.portal.settings.authentication.openid.connect.web.internal.portlet.action.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.security.sso.openid.connect.constants.OpenIdConnectConstants;
import com.liferay.portal.settings.authentication.openid.connect.web.internal.constants.constants.PortalSettingsOpenIdConnectConstants;
import com.liferay.portal.settings.portlet.action.BasePortalSettingsFormMVCActionCommand;
import com.liferay.portal.settings.web.constants.PortalSettingsPortletKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Edward C. Han
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + PortalSettingsPortletKeys.PORTAL_SETTINGS,
		"mvc.command.name=/portal_settings/openid_connect"
	},
	service = MVCActionCommand.class
)
public class PortalSettingsOpenIdConnectFormMVCActionCommand
	extends BasePortalSettingsFormMVCActionCommand {

	@Override
	protected void doValidateForm(
		ActionRequest actionRequest, ActionResponse actionResponse) {
	}

	@Override
	protected String getParameterNamespace() {
		return PortalSettingsOpenIdConnectConstants.FORM_PARAMETER_NAMESPACE;
	}

	@Override
	protected String getSettingsId() {
		return OpenIdConnectConstants.SERVICE_NAME;
	}

}