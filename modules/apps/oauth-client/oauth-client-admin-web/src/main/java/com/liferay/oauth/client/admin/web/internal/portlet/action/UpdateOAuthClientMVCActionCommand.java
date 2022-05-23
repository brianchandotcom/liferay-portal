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

package com.liferay.oauth.client.admin.web.internal.portlet.action;

import com.liferay.oauth.client.admin.web.internal.constants.OAuthClientAdminPortletKeys;
import com.liferay.oauth.client.persistence.model.OAuthClientEntry;
import com.liferay.oauth.client.persistence.service.OAuthClientEntryService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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
 * @author Arthur Chan
 */
@Component(
	property = {
		"javax.portlet.name=" + OAuthClientAdminPortletKeys.OAUTH_CLIENT_ADMIN,
		"mvc.command.name=/oauth_client_admin/update_o_auth_client"
	},
	service = MVCActionCommand.class
)
public class UpdateOAuthClientMVCActionCommand implements MVCActionCommand {

	@Override
	public boolean processAction(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String authServerWellKnownURI = ParamUtil.getString(
			actionRequest, "authServerWellKnownURI");

		String infoJSON = ParamUtil.getString(actionRequest, "infoJSON");

		long oAuthClientEntryId = ParamUtil.getLong(
			actionRequest, "oAuthClientEntryId");

		String parametersJSON = ParamUtil.getString(
			actionRequest, "parametersJSON");

		try {
			OAuthClientEntry oAuthClientEntry = null;

			if (oAuthClientEntryId > 0) {
				oAuthClientEntry =
					_oAuthClientEntryService.updateOAuthClientEntry(
						oAuthClientEntryId, authServerWellKnownURI, infoJSON,
						parametersJSON);
			}
			else {
				oAuthClientEntry = _oAuthClientEntryService.addOAuthClientEntry(
					themeDisplay.getUserId(), authServerWellKnownURI, infoJSON,
					parametersJSON);
			}

			actionResponse.setRenderParameter(
				"authServerWellKnownURI",
				oAuthClientEntry.getAuthServerWellKnownURI());

			actionResponse.setRenderParameter(
				"clientId", oAuthClientEntry.getClientId());
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.error(portalException);
			}

			Class<?> exceptionClass = portalException.getClass();

			SessionErrors.add(
				actionRequest, "authServerWellKnownURI",
				authServerWellKnownURI);

			SessionErrors.add(actionRequest, "infoJSON", infoJSON);

			SessionErrors.add(
				actionRequest, "oAuthClientEntryId", oAuthClientEntryId);

			SessionErrors.add(actionRequest, "parametersJSON", parametersJSON);

			SessionErrors.add(
				actionRequest, exceptionClass.getName(), portalException);
		}

		actionResponse.setRenderParameter(
			"redirect", ParamUtil.getString(actionRequest, "backURL"));

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpdateOAuthClientMVCActionCommand.class);

	@Reference
	private OAuthClientEntryService _oAuthClientEntryService;

}