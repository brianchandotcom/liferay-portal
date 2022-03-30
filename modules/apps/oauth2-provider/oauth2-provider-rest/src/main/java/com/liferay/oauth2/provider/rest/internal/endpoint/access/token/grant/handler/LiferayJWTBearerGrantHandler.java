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

package com.liferay.oauth2.provider.rest.internal.endpoint.access.token.grant.handler;

import com.liferay.oauth2.provider.configuration.OAuth2ProviderConfiguration;
import com.liferay.oauth2.provider.rest.internal.configuration.admin.service.OAuth2InAssertionManagedServiceFactory;
import com.liferay.oauth2.provider.rest.internal.endpoint.constants.OAuth2ProviderRESTEndpointConstants;
import com.liferay.oauth2.provider.rest.internal.endpoint.liferay.LiferayOAuthDataProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.cxf.rs.security.jose.jws.JwsHeaders;
import org.apache.cxf.rs.security.jose.jws.JwsJwtCompactConsumer;
import org.apache.cxf.rs.security.jose.jws.JwsSignatureVerifier;
import org.apache.cxf.rs.security.jose.jwt.JwtClaims;
import org.apache.cxf.rs.security.jose.jwt.JwtToken;
import org.apache.cxf.rs.security.oauth2.common.Client;
import org.apache.cxf.rs.security.oauth2.common.ServerAccessToken;
import org.apache.cxf.rs.security.oauth2.common.UserSubject;
import org.apache.cxf.rs.security.oauth2.grants.jwt.Constants;
import org.apache.cxf.rs.security.oauth2.grants.jwt.JwtBearerGrantHandler;
import org.apache.cxf.rs.security.oauth2.provider.AccessTokenGrantHandler;
import org.apache.cxf.rs.security.oauth2.provider.OAuthServiceException;
import org.apache.cxf.rs.security.oauth2.utils.OAuthConstants;
import org.apache.cxf.rs.security.oauth2.utils.OAuthUtils;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Arthur Chan
 */
@Component(
	configurationPid = "com.liferay.oauth2.provider.configuration.OAuth2ProviderConfiguration",
	service = AccessTokenGrantHandler.class
)
public class LiferayJWTBearerGrantHandler extends BaseAccessTokenGrantHandler {

	@Activate
	protected void activate(Map<String, Object> properties) {
		_oAuth2ProviderConfiguration = ConfigurableUtil.createConfigurable(
			OAuth2ProviderConfiguration.class, properties);
	}

	@Override
	protected AccessTokenGrantHandler getAccessTokenGrantHandler() {
		CustomJWTBearerGrantHandler customJWTBearerGrantHandler =
			new CustomJWTBearerGrantHandler();

		customJWTBearerGrantHandler.setDataProvider(_liferayOAuthDataProvider);

		return customJWTBearerGrantHandler;
	}

	@Override
	protected boolean hasPermission(
		Client client, MultivaluedMap<String, String> multivaluedMap) {

		if (multivaluedMap.getFirst(Constants.CLIENT_GRANT_ASSERTION_PARAM) !=
				null) {

			return true;
		}

		return false;
	}

	@Override
	protected boolean isGrantHandlerEnabled() {
		return _oAuth2ProviderConfiguration.allowJWTBearerGrant();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LiferayJWTBearerGrantHandler.class);

	@Reference
	private LiferayOAuthDataProvider _liferayOAuthDataProvider;

	@Reference
	private OAuth2InAssertionManagedServiceFactory
		_oAuth2InAssertionManagedServiceFactory;

	private OAuth2ProviderConfiguration _oAuth2ProviderConfiguration;

	private class CustomJWTBearerGrantHandler extends JwtBearerGrantHandler {

		@Override
		public ServerAccessToken createAccessToken(
				Client client, MultivaluedMap<String, String> multivaluedMap)
			throws OAuthServiceException {

			String assertion = multivaluedMap.getFirst(
				Constants.CLIENT_GRANT_ASSERTION_PARAM);

			Map<String, String> clientProperties = client.getProperties();

			long companyId = GetterUtil.getLong(
				clientProperties.get(
					OAuth2ProviderRESTEndpointConstants.
						PROPERTY_KEY_COMPANY_ID));

			try {
				JwsJwtCompactConsumer jwsJwtCompactConsumer = getJwsReader(
					assertion);

				JwtToken jwtToken = jwsJwtCompactConsumer.getJwtToken();

				JwtClaims jwtClaims = jwtToken.getClaims();
				JwsHeaders jwsHeaders = jwtToken.getJwsHeaders();

				_initGrantHandler(companyId, jwtClaims, jwsHeaders);

				validateSignature(
					new JwsHeaders(jwsHeaders),
					jwsJwtCompactConsumer.getUnsignedEncodedSequence(),
					jwsJwtCompactConsumer.getDecodedSignature());

				validateClaims(client, jwtClaims);

				return doCreateAccessToken(
					client,
					_createUserSubject(
						companyId, jwtClaims.getIssuer(),
						jwtClaims.getSubject()),
					Constants.JWT_BEARER_GRANT,
					OAuthUtils.parseScope(
						multivaluedMap.getFirst(OAuthConstants.SCOPE)));
			}
			catch (Exception exception) {
				throw new OAuthServiceException(exception);
			}
		}

		private UserSubject _createUserSubject(
			long companyId, String issuer, String subject) {

			String userAuthType = null;

			try {
				userAuthType =
					_oAuth2InAssertionManagedServiceFactory.getUserAuthType(
						companyId, issuer);
			}
			catch (IllegalArgumentException illegalArgumentException) {
				if (_log.isWarnEnabled()) {
					_log.warn(illegalArgumentException);
				}

				throw new OAuthServiceException(OAuthConstants.INVALID_GRANT);
			}

			UserSubject userSubject = new UserSubject(StringPool.BLANK);

			if (userAuthType.equals(CompanyConstants.AUTH_TYPE_ID)) {

				// Compatibility with existing design

				userSubject.setId(subject);

				return userSubject;
			}

			Map<String, String> properties = userSubject.getProperties();

			properties.put(
				OAuth2ProviderRESTEndpointConstants.PROPERTY_KEY_COMPANY_ID,
				String.valueOf(companyId));
			properties.put(userAuthType, subject);

			return userSubject;
		}

		private void _initGrantHandler(
			long companyId, JwtClaims jwtClaims, JwsHeaders jwsHeaders) {

			JwsSignatureVerifier jwsSignatureVerifier = null;

			try {
				jwsSignatureVerifier =
					_oAuth2InAssertionManagedServiceFactory.
						getJWSSignatureVerifier(
							companyId, jwtClaims.getIssuer(),
							jwsHeaders.getKeyId());
			}
			catch (IllegalArgumentException illegalArgumentException) {
				if (_log.isWarnEnabled()) {
					_log.warn(illegalArgumentException);
				}

				throw new OAuthServiceException(OAuthConstants.INVALID_GRANT);
			}

			setJwsVerifier(jwsSignatureVerifier);
		}

	}

}