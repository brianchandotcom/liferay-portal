/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.openid.connect.internal.catapult;

import com.liferay.oauth.client.persistence.model.OAuthClientEntry;
import com.liferay.oauth.client.persistence.service.OAuthClientEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.catapult.PortalCatapultHeaderContributor;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.sso.openid.connect.internal.util.OpenIdConnectRequestParametersUtil;
import com.liferay.portal.security.sso.openid.connect.persistence.model.OpenIdConnectSession;
import com.liferay.portal.security.sso.openid.connect.persistence.service.OpenIdConnectSessionLocalService;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.util.JSONObjectUtils;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge García Jiménez
 */
@Component(service = PortalCatapultHeaderContributor.class)
public class OpenIdConnectPortalCatapultHeaderContributor
	implements PortalCatapultHeaderContributor {

	@Override
	public void contribute(
		long companyId, Map<String, String> headers, String homePageURL,
		String location, List<String> oAuth2ApplicationFeatures, long userId) {

		if (!FeatureFlagManagerUtil.isEnabled(companyId, "LPD-108193") ||
			!oAuth2ApplicationFeatures.contains("upstream.token.forwarding")) {

			return;
		}

		OpenIdConnectSession openIdConnectSession =
			_openIdConnectSessionLocalService.
				fetchCurrentOpenIdConnectSession();

		if ((openIdConnectSession == null) ||
			(openIdConnectSession.getCompanyId() != companyId) ||
			(openIdConnectSession.getUserId() != userId) ||
			!_isAllowedByOAuthClientEntry(openIdConnectSession) ||
			!_isFreshAccessToken(openIdConnectSession) ||
			!_isSameSecureOrigin(homePageURL, location)) {

			return;
		}

		String idToken = openIdConnectSession.getIdToken();

		if (Validator.isNull(idToken) || !_isFreshIdToken(idToken)) {
			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Forwarding the upstream ID token to " + location);
		}

		headers.put("X-Upstream-ID-Token", idToken);
	}

	private String _getOrigin(String url) {
		if (Validator.isNull(url)) {
			return null;
		}

		try {
			URI uri = new URI(url);

			String host = uri.getHost();
			String scheme = uri.getScheme();

			if (Validator.isNull(host) || Validator.isNull(scheme)) {
				return null;
			}

			int port = uri.getPort();

			if (port == -1) {
				if (StringUtil.equalsIgnoreCase(scheme, Http.HTTPS)) {
					port = Http.HTTPS_PORT;
				}
				else {
					port = Http.HTTP_PORT;
				}
			}

			return StringBundler.concat(
				StringUtil.toLowerCase(scheme), StringPool.COLON,
				StringUtil.toLowerCase(host), StringPool.COLON, port);
		}
		catch (URISyntaxException uriSyntaxException) {
			if (_log.isDebugEnabled()) {
				_log.debug(uriSyntaxException);
			}

			return null;
		}
	}

	private boolean _isAllowedByOAuthClientEntry(
		OpenIdConnectSession openIdConnectSession) {

		OAuthClientEntry oAuthClientEntry =
			_oAuthClientEntryLocalService.fetchOAuthClientEntry(
				openIdConnectSession.getCompanyId(),
				openIdConnectSession.getAuthServerWellKnownURI(),
				openIdConnectSession.getClientId());

		if (oAuthClientEntry == null) {
			return false;
		}

		try {
			return OpenIdConnectRequestParametersUtil.
				isUpstreamTokenForwardingAllowed(
					JSONObjectUtils.parse(
						oAuthClientEntry.getTokenRequestParametersJSON()));
		}
		catch (ParseException parseException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Unable to read the token request parameters of OAuth ",
						"client entry ",
						oAuthClientEntry.getOAuthClientEntryId()),
					parseException);
			}

			return false;
		}
	}

	private boolean _isFreshAccessToken(
		OpenIdConnectSession openIdConnectSession) {

		Date accessTokenExpirationDate =
			openIdConnectSession.getAccessTokenExpirationDate();

		if (accessTokenExpirationDate == null) {
			return false;
		}

		if (accessTokenExpirationDate.after(
				new Date(
					System.currentTimeMillis() + _TOKEN_EXPIRATION_OFFSET))) {

			return true;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Not forwarding the upstream ID token because the access " +
					"token is about to expire");
		}

		return false;
	}

	private boolean _isFreshIdToken(String idToken) {
		try {
			JWT jwt = JWTParser.parse(idToken);

			JWTClaimsSet jwtClaimsSet = jwt.getJWTClaimsSet();

			Date expirationTime = jwtClaimsSet.getExpirationTime();

			if (expirationTime == null) {
				return false;
			}

			return expirationTime.after(
				new Date(
					System.currentTimeMillis() + _TOKEN_EXPIRATION_OFFSET));
		}
		catch (java.text.ParseException parseException) {
			if (_log.isDebugEnabled()) {
				_log.debug(parseException);
			}

			return false;
		}
	}

	private boolean _isSameSecureOrigin(String homePageURL, String location) {
		if (!HttpComponentsUtil.isSecure(location)) {
			return false;
		}

		String origin = _getOrigin(location);

		if (Validator.isNull(origin)) {
			return false;
		}

		return origin.equals(_getOrigin(homePageURL));
	}

	private static final long _TOKEN_EXPIRATION_OFFSET = 30 * Time.SECOND;

	private static final Log _log = LogFactoryUtil.getLog(
		OpenIdConnectPortalCatapultHeaderContributor.class);

	@Reference
	private OAuthClientEntryLocalService _oAuthClientEntryLocalService;

	@Reference
	private OpenIdConnectSessionLocalService _openIdConnectSessionLocalService;

}