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
import com.liferay.oauth2.provider.rest.internal.configuration.OAuth2InAssertionConfiguration;
import com.liferay.oauth2.provider.rest.internal.endpoint.constants.OAuth2ProviderRESTEndpointConstants;
import com.liferay.oauth2.provider.rest.internal.endpoint.liferay.LiferayOAuthDataProvider;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.cxf.jaxrs.utils.JAXRSUtils;
import org.apache.cxf.message.Message;
import org.apache.cxf.rs.security.jose.jwk.JsonWebKey;
import org.apache.cxf.rs.security.jose.jwk.JsonWebKeys;
import org.apache.cxf.rs.security.jose.jwk.JwkUtils;
import org.apache.cxf.rs.security.jose.jwk.PublicKeyUse;
import org.apache.cxf.rs.security.jose.jws.JwsHeaders;
import org.apache.cxf.rs.security.jose.jws.JwsJwtCompactConsumer;
import org.apache.cxf.rs.security.jose.jws.JwsSignatureVerifier;
import org.apache.cxf.rs.security.jose.jws.JwsUtils;
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

import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedServiceFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Arthur Chan
 */
@Component(
	configurationPid = "com.liferay.oauth2.provider.configuration.OAuth2ProviderConfiguration",
	property = org.osgi.framework.Constants.SERVICE_PID + "=com.liferay.oauth2.provider.rest.internal.configuration.OAuth2InAssertionConfiguration",
	service = {AccessTokenGrantHandler.class, ManagedServiceFactory.class}
)
public class LiferayJWTBearerGrantHandler
	extends BaseAccessTokenGrantHandler implements ManagedServiceFactory {

	@Override
	public void deleted(String pid) {
		Dictionary<String, ?> properties = _configurationPidsProperties.remove(
			pid);

		long companyId = GetterUtil.getLong(properties.get("companyId"));

		if (companyId == CompanyConstants.SYSTEM) {
			_rebuild();
		}
		else {
			_rebuild(companyId);
		}
	}

	@Override
	public String getName() {
		return StringPool.BLANK;
	}

	@Override
	public void updated(String pid, Dictionary<String, ?> properties)
		throws ConfigurationException {

		Dictionary<String, ?> oldProperties = _configurationPidsProperties.put(
			pid, properties);

		long companyId = GetterUtil.getLong(
			properties.get("companyId"), CompanyConstants.SYSTEM);

		if (companyId == CompanyConstants.SYSTEM) {
			_rebuild();

			return;
		}

		if (oldProperties != null) {
			long oldCompanyId = GetterUtil.getLong(
				oldProperties.get("companyId"));

			if (oldCompanyId == CompanyConstants.SYSTEM) {
				_rebuild();

				return;
			}

			if (oldCompanyId != companyId) {
				_rebuild(oldCompanyId);
			}
		}

		_rebuild(companyId);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_oAuth2ProviderConfiguration = ConfigurableUtil.createConfigurable(
			OAuth2ProviderConfiguration.class, properties);

		_jwsSignatureVerifiers.put(
			CompanyConstants.SYSTEM, Collections.emptyMap());
		_userAuthTypes.put(CompanyConstants.SYSTEM, Collections.emptyMap());
	}

	@Override
	protected AccessTokenGrantHandler getAccessTokenGrantHandler() {
		CustomJWTBearerGrantHandler customJWTBearerGrantHandler =
			new CustomJWTBearerGrantHandler();

		customJWTBearerGrantHandler.setDataProvider(_liferayOAuthDataProvider);

		return customJWTBearerGrantHandler;
	}

	protected Map<String, Map<String, JwsSignatureVerifier>>
		getJwsSignatureVerifiers(long companyId) {

		return _jwsSignatureVerifiers.getOrDefault(
			companyId, _jwsSignatureVerifiers.get(CompanyConstants.SYSTEM));
	}

	protected Map<String, String> getUserAuthTypes(long companyId) {
		return _userAuthTypes.getOrDefault(
			companyId, _userAuthTypes.get(CompanyConstants.SYSTEM));
	}

	@Override
	protected boolean hasPermission(
		Client client, MultivaluedMap<String, String> params) {

		if (params.getFirst(Constants.CLIENT_GRANT_ASSERTION_PARAM) != null) {
			return true;
		}

		return false;
	}

	@Override
	protected boolean isGrantHandlerEnabled() {
		return _oAuth2ProviderConfiguration.allowJWTBearerGrant();
	}

	private <U, V> void _addDefaults(Map<U, V> map, Map<U, V> defaultsMap) {
		if (defaultsMap != null) {
			defaultsMap.forEach(map::putIfAbsent);
		}
	}

	private void _rebuild() {
		_rebuild(CompanyConstants.SYSTEM);

		for (Long key : _jwsSignatureVerifiers.keySet()) {
			if (key == CompanyConstants.SYSTEM) {
				continue;
			}

			_rebuild(key);
		}
	}

	private void _rebuild(long companyId) {
		Map<String, Map<String, JwsSignatureVerifier>> jwsSignatureVerifiers =
			new HashMap<>();
		Map<String, String> userAuthTypes = new HashMap<>();

		for (Dictionary<String, ?> properties :
				_configurationPidsProperties.values()) {

			if (companyId != GetterUtil.getLong(properties.get("companyId"))) {
				continue;
			}

			OAuth2InAssertionConfiguration oAuth2InAssertionConfiguration =
				ConfigurableUtil.createConfigurable(
					OAuth2InAssertionConfiguration.class, properties);

			String issuer = oAuth2InAssertionConfiguration.issuer();

			if (jwsSignatureVerifiers.containsKey(issuer)) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"Duplicate issuer name ", issuer, " will be ",
							"discarded. Check your OAuth configuration."));
				}

				continue;
			}

			jwsSignatureVerifiers.put(issuer, new HashMap<>());

			userAuthTypes.put(
				issuer, oAuth2InAssertionConfiguration.userAuthType());

			Map<String, JwsSignatureVerifier> kidsJwsSignatureVerifiers =
				jwsSignatureVerifiers.get(issuer);

			JsonWebKeys jsonWebKeys = JwkUtils.readJwkSet(
				oAuth2InAssertionConfiguration.signatureJSONWebKeySet());

			for (JsonWebKey jsonWebKey : jsonWebKeys.getKeys()) {
				PublicKeyUse publicKeyUse = jsonWebKey.getPublicKeyUse();

				if ((publicKeyUse != null) &&
					(publicKeyUse.compareTo(PublicKeyUse.ENCRYPT) == 0)) {

					if (_log.isInfoEnabled()) {
						_log.info("Encryption key " + jsonWebKey.getKeyId());
					}

					continue;
				}

				if (kidsJwsSignatureVerifiers.containsKey(
						jsonWebKey.getKeyId())) {

					if (_log.isWarnEnabled()) {
						_log.warn(
							StringBundler.concat(
								"Duplicate assertion signature key ",
								jsonWebKey.getKeyId(),
								" will be discarded. Check your OAuth ",
								"configuration."));
					}

					continue;
				}

				kidsJwsSignatureVerifiers.put(
					jsonWebKey.getKeyId(),
					JwsUtils.getSignatureVerifier(jsonWebKey));
			}
		}

		if (companyId != CompanyConstants.SYSTEM) {
			_addDefaults(
				jwsSignatureVerifiers,
				_jwsSignatureVerifiers.get(CompanyConstants.SYSTEM));
			_addDefaults(
				userAuthTypes, _userAuthTypes.get(CompanyConstants.SYSTEM));
		}

		_jwsSignatureVerifiers.put(companyId, jwsSignatureVerifiers);
		_userAuthTypes.put(companyId, userAuthTypes);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LiferayJWTBearerGrantHandler.class);

	private final Map<String, Dictionary<String, ?>>
		_configurationPidsProperties = Collections.synchronizedMap(
			new LinkedHashMap<>());
	private final Map<Long, Map<String, Map<String, JwsSignatureVerifier>>>
		_jwsSignatureVerifiers = Collections.synchronizedMap(
			new LinkedHashMap<>());

	@Reference
	private LiferayOAuthDataProvider _liferayOAuthDataProvider;

	private OAuth2ProviderConfiguration _oAuth2ProviderConfiguration;
	private final Map<Long, Map<String, String>> _userAuthTypes =
		Collections.synchronizedMap(new LinkedHashMap<>());

	private class CustomJWTBearerGrantHandler extends JwtBearerGrantHandler {

		@Override
		public ServerAccessToken createAccessToken(
				Client client, MultivaluedMap<String, String> params)
			throws OAuthServiceException {

			String assertion = params.getFirst(
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
						params.getFirst(OAuthConstants.SCOPE)));
			}
			catch (Exception exception) {
				throw new OAuthServiceException(exception);
			}
		}

		private UserSubject _createUserSubject(
			long companyId, String issuer, String subject) {

			Map<String, String> userAuthTypes = getUserAuthTypes(companyId);

			String userAuthType = userAuthTypes.get(issuer);

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

			Map<String, Map<String, JwsSignatureVerifier>>
				jwsSignatureVerifiers = getJwsSignatureVerifiers(companyId);

			Map<String, JwsSignatureVerifier> kidsJWSSignatureVerifiers =
				jwsSignatureVerifiers.get(jwtClaims.getIssuer());

			if ((kidsJWSSignatureVerifiers == null) ||
				!kidsJWSSignatureVerifiers.containsKey(jwsHeaders.getKeyId())) {

				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"No in assertion configuration for ",
							jwtClaims.getIssuer(), " with key ID ",
							jwsHeaders.getKeyId()));
				}

				throw new OAuthServiceException(OAuthConstants.INVALID_GRANT);
			}

			setJwsVerifier(
				kidsJWSSignatureVerifiers.get(jwsHeaders.getKeyId()));

			Message message = JAXRSUtils.getCurrentMessage();

			String audience = getAudience();

			String expectedAudience = (String)message.get(Message.REQUEST_URL);

			if (Validator.isNotNull(audience) &&
				!audience.equals(expectedAudience)) {

				throw new IllegalStateException(
					"Already initialized for the audience " + audience);
			}

			setAudience(expectedAudience);
		}

	}

}