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
		_jwtBearerGrantHandler = new CustomJWTBearerGrantHandler();

		_jwtBearerGrantHandler.setDataProvider(_liferayOAuthDataProvider);

		_oAuth2ProviderConfiguration = ConfigurableUtil.createConfigurable(
			OAuth2ProviderConfiguration.class, properties);

		_jwsSignatureVerifiers.put(
			CompanyConstants.SYSTEM, Collections.emptyMap());
		_userAuthTypes.put(CompanyConstants.SYSTEM, Collections.emptyMap());
	}

	@Override
	protected AccessTokenGrantHandler getAccessTokenGrantHandler() {
		return _jwtBearerGrantHandler;
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

	private void _rebuild() {
		_rebuildHelper(CompanyConstants.SYSTEM);

		for (Long key : _jwsSignatureVerifiers.keySet()) {
			if (key == CompanyConstants.SYSTEM) {
				continue;
			}

			_rebuild(key);
		}
	}

	private void _rebuild(long companyId) {
		_rebuildHelper(companyId);

		// Merge signature verifiers from system settings

		Map<String, Map<String, JwsSignatureVerifier>> jwsSignatureVerifiers =
			_jwsSignatureVerifiers.get(companyId);

		Map<String, Map<String, JwsSignatureVerifier>>
			systemJWSSignatureVerifiers = _jwsSignatureVerifiers.get(
				CompanyConstants.SYSTEM);

		for (Map.Entry<String, Map<String, JwsSignatureVerifier>> entry :
				systemJWSSignatureVerifiers.entrySet()) {

			if (jwsSignatureVerifiers.containsKey(entry.getKey())) {
				continue;
			}

			jwsSignatureVerifiers.put(entry.getKey(), entry.getValue());
		}

		// Merge user auth types from system settings

		Map<String, String> userAuthTypes = _userAuthTypes.get(companyId);

		Map<String, String> systemUserAuthTypes = _userAuthTypes.get(
			CompanyConstants.SYSTEM);

		for (Map.Entry<String, String> entry : systemUserAuthTypes.entrySet()) {
			if (userAuthTypes.containsKey(entry.getKey())) {
				continue;
			}

			userAuthTypes.put(entry.getKey(), entry.getValue());
		}
	}

	private void _rebuildHelper(long companyId) {

		_jwsSignatureVerifiers.put(companyId, new HashMap<>());

		_userAuthTypes.put(companyId, new HashMap<>());

		Map<String, Map<String, JwsSignatureVerifier>> jwsSignatureVerifiers =
			_jwsSignatureVerifiers.get(companyId);

		Map<String, String> userAuthTypes = _userAuthTypes.get(companyId);

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
							"Duplicated issuer name {", issuer, "} will be ",
							"discarded. Please check your OAuth In-assertion",
							"configuration."));
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
						_log.info(
							StringBundler.concat(
								"There is an encryption key {",
								jsonWebKey.getKeyId(),
								"} in your OAuth In-assertion configuration."));
					}

					continue;
				}

				if (kidsJwsSignatureVerifiers.containsKey(
						jsonWebKey.getKeyId())) {

					if (_log.isWarnEnabled()) {
						_log.warn(
							StringBundler.concat(
								"Duplicated Assertion signature key {",
								jsonWebKey.getKeyId(),
								"} will be discarded. Please check your OAuth ",
								"In-assertion configuration."));
					}

					continue;
				}

				kidsJwsSignatureVerifiers.put(
					jsonWebKey.getKeyId(),
					JwsUtils.getSignatureVerifier(jsonWebKey));
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LiferayJWTBearerGrantHandler.class);

	private final Map<String, Dictionary<String, ?>>
		_configurationPidsProperties = Collections.synchronizedMap(
			new LinkedHashMap<>());
	private final Map<Long, Map<String, Map<String, JwsSignatureVerifier>>>
		_jwsSignatureVerifiers = Collections.synchronizedMap(
			new LinkedHashMap<>());
	private CustomJWTBearerGrantHandler _jwtBearerGrantHandler;

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
				JwsJwtCompactConsumer jwsReader = getJwsReader(assertion);

				JwtToken jwtToken = jwsReader.getJwtToken();

				JwsHeaders jwsHeaders = jwtToken.getJwsHeaders();

				JwtClaims jwtClaims = jwtToken.getClaims();

				setJwsSignatureVerifier(companyId, jwsHeaders, jwtClaims);

				validateSignature(
					new JwsHeaders(jwsHeaders),
					jwsReader.getUnsignedEncodedSequence(),
					jwsReader.getDecodedSignature());

				validateClaims(client, jwtClaims);

				return doCreateAccessToken(
					client,
					createUserSubject(
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

		public UserSubject createUserSubject(
			long companyId, String issuer, String subject) {

			Map<String, String> userAuthTypes = _userAuthTypes.getOrDefault(
				companyId, _userAuthTypes.get(CompanyConstants.SYSTEM));

			String userAuthType = userAuthTypes.get(issuer);

			UserSubject userSubject = new UserSubject(StringPool.BLANK);

			if (userAuthType.equals(CompanyConstants.AUTH_TYPE_ID)) {

				// To be compatible with existing design

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

		public void setJwsSignatureVerifier(
			long companyId, JwsHeaders jwsHeaders, JwtClaims jwtClaims) {

			Map<String, Map<String, JwsSignatureVerifier>>
				jwsSignatureVerifiers = _jwsSignatureVerifiers.getOrDefault(
					companyId,
					_jwsSignatureVerifiers.get(CompanyConstants.SYSTEM));

			Map<String, JwsSignatureVerifier> kidsJWSSignatureVerifiers =
				jwsSignatureVerifiers.get(jwtClaims.getIssuer());

			if ((kidsJWSSignatureVerifiers == null) ||
				!kidsJWSSignatureVerifiers.containsKey(jwsHeaders.getKeyId())) {

				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"No such In assertion configuration for: ",
							jwtClaims.getIssuer(), " with Key Id ",
							jwsHeaders.getKeyId()));
				}

				throw new OAuthServiceException(OAuthConstants.INVALID_GRANT);
			}

			setJwsVerifier(
				kidsJWSSignatureVerifiers.get(jwsHeaders.getKeyId()));

			Message message = JAXRSUtils.getCurrentMessage();

			setAudience((String)message.get(Message.REQUEST_URL));
		}

	}

}