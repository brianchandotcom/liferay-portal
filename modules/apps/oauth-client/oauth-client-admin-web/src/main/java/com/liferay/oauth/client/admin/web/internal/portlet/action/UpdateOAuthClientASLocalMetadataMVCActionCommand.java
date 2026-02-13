/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.admin.web.internal.portlet.action;

import com.liferay.oauth.client.admin.web.internal.constants.OAuthClientAdminPortletKeys;
import com.liferay.oauth.client.persistence.exception.OAuthClientASLocalMetadataIssuerException;
import com.liferay.oauth.client.persistence.exception.OAuthClientASLocalMetadataLocalWellKnownURIException;
import com.liferay.oauth.client.persistence.model.OAuthClientASLocalMetadata;
import com.liferay.oauth.client.persistence.service.OAuthClientASLocalMetadataService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import jakarta.portlet.ActionRequest;
import jakarta.portlet.ActionResponse;

import java.net.MalformedURLException;
import java.net.URL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Arthur Chan
 * @author Alvaro Saugar
 */
@Component(
	property = {
		"jakarta.portlet.name=" + OAuthClientAdminPortletKeys.OAUTH_CLIENT_ADMIN,
		"mvc.command.name=/oauth_client_admin/update_oauth_client_as_local_metadata"
	},
	service = MVCActionCommand.class
)
public class UpdateOAuthClientASLocalMetadataMVCActionCommand
	implements MVCActionCommand {

	@Override
	public boolean processAction(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		try {
			if (!FeatureFlagManagerUtil.isEnabled(
					CompanyThreadLocal.getCompanyId(), "LPD-63415")) {

				String localWellKnownURI = ParamUtil.getString(
					actionRequest, "localWellKnownURI");
				String metadataJSON = ParamUtil.getString(
					actionRequest, "metadataJSON");

				if (Validator.isNull(localWellKnownURI)) {
					_oAuthClientASLocalMetadataService.
						addOAuthClientASLocalMetadata(
							metadataJSON, "openid-configuration");
				}
				else {
					OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
						_oAuthClientASLocalMetadataService.
							getOAuthClientASLocalMetadata(localWellKnownURI);

					_oAuthClientASLocalMetadataService.
						updateOAuthClientASLocalMetadata(
							oAuthClientASLocalMetadata.
								getOAuthClientASLocalMetadataId(),
							metadataJSON, "openid-configuration");
				}

				return true;
			}

			String authorizationEndpoint = ParamUtil.getString(
				actionRequest, "authorizationEndpoint");

			_validateHttpsUrl(authorizationEndpoint, false);

			String issuer = ParamUtil.getString(actionRequest, "issuer");

			_validateHttpsUrl(issuer, true);

			String jwksURI = ParamUtil.getString(actionRequest, "jwksURI");

			_validateHttpsUrl(jwksURI, false);

			String tokenEndpoint = ParamUtil.getString(
				actionRequest, "tokenEndpoint");

			_validateHttpsUrl(tokenEndpoint, false);

			String userInfoEndpoint = ParamUtil.getString(
				actionRequest, "userInfoEndpoint");

			_validateHttpsUrl(userInfoEndpoint, false);

			boolean enabledLocalWellKnown = ParamUtil.getBoolean(
				actionRequest, "enabledLocalWellKnown");

			String supportedGrantTypes = ParamUtil.getString(
				actionRequest, "supportedGrantTypes");
			String supportedScopes = ParamUtil.getString(
				actionRequest, "supportedScopes");
			String supportedSubjectTypes = ParamUtil.getString(
				actionRequest, "supportedSubjectTypes");

			long oAuthClientASLocalMetadataId = ParamUtil.getLong(
				actionRequest, "oAuthClientASLocalMetadataId");

			OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
				_oAuthClientASLocalMetadataService.
					fetchOAuthClientASLocalMetadata(
						oAuthClientASLocalMetadataId);

			if (oAuthClientASLocalMetadata == null) {
				_oAuthClientASLocalMetadataService.
					addOAuthClientASLocalMetadata(
						authorizationEndpoint, issuer, jwksURI,
						enabledLocalWellKnown,
						StringUtil.split(supportedGrantTypes, StringPool.COMMA),
						StringUtil.split(supportedScopes, StringPool.COMMA),
						StringUtil.split(
							supportedSubjectTypes, StringPool.COMMA),
						tokenEndpoint, userInfoEndpoint);
			}
			else {
				_oAuthClientASLocalMetadataService.
					updateOAuthClientASLocalMetadata(
						oAuthClientASLocalMetadata.
							getOAuthClientASLocalMetadataId(),
						authorizationEndpoint, issuer, jwksURI,
						enabledLocalWellKnown,
						StringUtil.split(supportedGrantTypes, StringPool.COMMA),
						StringUtil.split(supportedScopes, StringPool.COMMA),
						StringUtil.split(
							supportedSubjectTypes, StringPool.COMMA),
						tokenEndpoint, userInfoEndpoint);
			}

			return true;
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			Class<?> clazz = portalException.getClass();

			SessionErrors.add(actionRequest, clazz.getName(), portalException);

			return false;
		}
	}

	private void _validateHttpsUrl(String urlString, boolean required)
		throws PortalException {

		if (Validator.isNull(urlString)) {
			if (required) {
				throw new OAuthClientASLocalMetadataIssuerException();
			}

			return;
		}

		try {
			URL url = new URL(urlString);

			if (!Http.HTTPS.equalsIgnoreCase(url.getProtocol())) {
				throw new OAuthClientASLocalMetadataLocalWellKnownURIException();
			}
		}
		catch (MalformedURLException malformedURLException) {
			throw new OAuthClientASLocalMetadataLocalWellKnownURIException(
				urlString, malformedURLException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpdateOAuthClientASLocalMetadataMVCActionCommand.class);

	@Reference
	private OAuthClientASLocalMetadataService
		_oAuthClientASLocalMetadataService;

}