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
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

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
	service = MVCRenderCommand.class
)
public class UpdateOAuthClientMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String authServerWellKnownURI = ParamUtil.getString(
			renderRequest, "authServerWellKnownURI");

		String clientId = ParamUtil.getString(renderRequest, "clientId");

		String infoJSON = _INFO_TEMPLATE;

		String parametersJSON = _PARAMETERS_TEMPLATE;

		if (Validator.isNotNull(authServerWellKnownURI) &&
			Validator.isNotNull(clientId)) {

			try {
				OAuthClientEntry oAuthClientEntry =
					_oAuthClientEntryService.getOAuthClientEntry(
						themeDisplay.getCompanyId(), authServerWellKnownURI,
						clientId);

				infoJSON = oAuthClientEntry.getInfoJSON();

				parametersJSON = oAuthClientEntry.getParametersJSON();

				renderRequest.setAttribute(
					"authServerWellKnownURI",
					oAuthClientEntry.getAuthServerWellKnownURI());

				renderRequest.setAttribute(
					"oAuthClientEntryId",
					oAuthClientEntry.getOAuthClientEntryId());
			}
			catch (PortalException portalException) {
				if (_log.isDebugEnabled()) {
					_log.debug(portalException);
				}
			}
		}

		renderRequest.setAttribute("infoJSON", infoJSON);

		renderRequest.setAttribute("parametersJSON", parametersJSON);

		return "/admin/update_oauth_client.jsp";
	}

	private static final String _INFO_TEMPLATE = StringBundler.concat(
		"{\"client_id\":\"\",\"client_secret\":\"\",",
		"\"token_endpoint_auth_method\":\"client_secret_basic\",",
		"\"redirect_uris\":[\"\",\"\"],\"client_name\":\"example_client\",",
		"\"grant_types\":[\"authorization_code\"],",
		"\"scope\":\"openid email profile\",\"subject_type\":\"public\",",
		"\"id_token_signed_response_alg\":\"RS256\"}");

	private static final String _PARAMETERS_TEMPLATE = StringBundler.concat(
		"{\"authorization_request_parameters\":{\"resource\":[\"resource1\",\"",
		"resource2\"]},\"token_request_parameters\":{\"audience\":\"audience1",
		"\",\"resource\":[\"resource1\",\"resource2\"]}}");

	private static final Log _log = LogFactoryUtil.getLog(
		UpdateOAuthClientMVCRenderCommand.class);

	@Reference
	private OAuthClientEntryService _oAuthClientEntryService;

}