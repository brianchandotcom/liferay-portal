/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.admin.web.internal.portlet.action;

import com.liferay.oauth.client.admin.web.internal.constants.OAuthClientAdminPortletKeys;
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

import java.net.URL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Arthur Chan
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

				_processActionNoOauthAuthorizationServer(actionRequest);
			}
			else {
				String authorizationEndpoint = ParamUtil.getString(
					actionRequest, "authorization_endpoint");

				_validateOptionalHttps(authorizationEndpoint);

				Boolean enabledLocalWellKnown = ParamUtil.getBoolean(
					actionRequest, "enabled");

				String issuer = ParamUtil.getString(actionRequest, "issuer");

				_validateRequiredHttps(issuer);

				String jwksUri = ParamUtil.getString(actionRequest, "jwks_uri");

				_validateOptionalHttps(jwksUri);

				String supportedGrantTypes = ParamUtil.getString(
					actionRequest, "supported-grant-types");
				String supportedScopes = ParamUtil.getString(
					actionRequest, "supported-scopes");
				String supportedSubjectTypes = ParamUtil.getString(
					actionRequest, "supported_subject_types");

				String tokenEndpoint = ParamUtil.getString(
					actionRequest, "token_endpoint");

				_validateOptionalHttps(tokenEndpoint);

				String userInfoEndpoint = ParamUtil.getString(
					actionRequest, "userinfo_endpoint");

				_validateOptionalHttps(userInfoEndpoint);

				Long oAuthClientASLocalMetadataId = ParamUtil.getLong(
					actionRequest, "oAuthClientASLocalMetadataId");

				OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
					_oAuthClientASLocalMetadataService.
						fetchOAuthClientASLocalMetadata(
							oAuthClientASLocalMetadataId);

				if (oAuthClientASLocalMetadata == null) {
					_oAuthClientASLocalMetadataService.
						addOAuthClientASLocalMetadata(
							authorizationEndpoint, issuer, jwksUri,
							enabledLocalWellKnown,
							StringUtil.split(
								supportedGrantTypes, StringPool.COMMA),
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
							authorizationEndpoint, issuer, jwksUri,
							enabledLocalWellKnown,
							StringUtil.split(
								supportedGrantTypes, StringPool.COMMA),
							StringUtil.split(supportedScopes, StringPool.COMMA),
							StringUtil.split(
								supportedSubjectTypes, StringPool.COMMA),
							tokenEndpoint, userInfoEndpoint);
				}
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

	private void _processActionNoOauthAuthorizationServer(
			ActionRequest actionRequest)
		throws PortalException {

		String localWellKnownURI = ParamUtil.getString(
			actionRequest, "localWellKnownURI");
		String metadataJSON = ParamUtil.getString(
			actionRequest, "metadataJSON");

		if (Validator.isNull(localWellKnownURI)) {
			_oAuthClientASLocalMetadataService.addOAuthClientASLocalMetadata(
				metadataJSON, "openid-configuration");
		}
		else {
			OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
				_oAuthClientASLocalMetadataService.
					getOAuthClientASLocalMetadata(localWellKnownURI);

			_oAuthClientASLocalMetadataService.updateOAuthClientASLocalMetadata(
				oAuthClientASLocalMetadata.getOAuthClientASLocalMetadataId(),
				metadataJSON, "openid-configuration");
		}
	}

	private void _validateHttpsUrl(String url) throws PortalException {
		try {
			URL parsed = new URL(url);

			if (!Http.HTTPS.equalsIgnoreCase(parsed.getProtocol())) {
				throw new OAuthClientASLocalMetadataLocalWellKnownURIException();
			}
		}
		catch (Exception exception) {
			throw new OAuthClientASLocalMetadataLocalWellKnownURIException(
				url, exception);
		}
	}

	private void _validateOptionalHttps(String url) throws PortalException {
		if (Validator.isNotNull(url)) {
			_validateHttpsUrl(url);
		}
	}

	private void _validateRequiredHttps(String url) throws PortalException {
		if (Validator.isNull(url)) {
			throw new OAuthClientASLocalMetadataLocalWellKnownURIException();
		}

		_validateHttpsUrl(url);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpdateOAuthClientASLocalMetadataMVCActionCommand.class);

	@Reference
	private OAuthClientASLocalMetadataService
		_oAuthClientASLocalMetadataService;

}