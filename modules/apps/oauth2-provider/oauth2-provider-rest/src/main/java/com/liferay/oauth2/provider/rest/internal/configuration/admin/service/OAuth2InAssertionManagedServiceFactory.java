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

package com.liferay.oauth2.provider.rest.internal.configuration.admin.service;

import com.liferay.oauth2.provider.rest.internal.configuration.OAuth2InAssertionConfiguration;
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

import org.apache.cxf.rs.security.jose.jwk.JsonWebKey;
import org.apache.cxf.rs.security.jose.jwk.JsonWebKeys;
import org.apache.cxf.rs.security.jose.jwk.JwkUtils;
import org.apache.cxf.rs.security.jose.jwk.PublicKeyUse;
import org.apache.cxf.rs.security.jose.jws.JwsSignatureVerifier;
import org.apache.cxf.rs.security.jose.jws.JwsUtils;

import org.osgi.framework.Constants;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedServiceFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Arthur Chan
 */
@Component(
	immediate = true,
	property = Constants.SERVICE_PID + "=com.liferay.oauth2.provider.rest.internal.configuration.OAuth2InAssertionConfiguration",
	service = {
		ManagedServiceFactory.class,
		OAuth2InAssertionManagedServiceFactory.class
	}
)
public class OAuth2InAssertionManagedServiceFactory
	implements ManagedServiceFactory {

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

	public JwsSignatureVerifier getJWSSignatureVerifier(
			long companyId, String issuer, String kid)
		throws IllegalArgumentException {

		StringBundler sb = new StringBundler(12);

		Map<String, Map<String, JwsSignatureVerifier>> jwsSignatureVerifiers =
			_jwsSignatureVerifiers.getOrDefault(
				companyId, _jwsSignatureVerifiers.get(CompanyConstants.SYSTEM));

		if (jwsSignatureVerifiers == null) {
			sb.append("No JWS signature keys in company: ");
			sb.append(companyId);

			throw new IllegalArgumentException(sb.toString());
		}

		Map<String, JwsSignatureVerifier> kidsJWSSignatureVerifiers =
			jwsSignatureVerifiers.get(issuer);

		if (kidsJWSSignatureVerifiers == null) {
			sb.append("No JWS signature keys for issuer: ");
			sb.append(issuer);
			sb.append(", in company: ");
			sb.append(companyId);

			throw new IllegalArgumentException(sb.toString());
		}

		if (!kidsJWSSignatureVerifiers.containsKey(kid)) {
			sb.append("No JWS signature key of kid: ");
			sb.append(kid);
			sb.append(", for issuer: ");
			sb.append(issuer);
			sb.append(", in company: ");
			sb.append(companyId);

			throw new IllegalArgumentException(sb.toString());
		}

		return kidsJWSSignatureVerifiers.get(kid);
	}

	@Override
	public String getName() {
		return StringPool.BLANK;
	}

	public String getUserAuthType(long companyId, String issuer)
		throws IllegalArgumentException {

		StringBundler sb = new StringBundler(6);

		Map<String, String> userAuthTypes = _userAuthTypes.getOrDefault(
			companyId, _userAuthTypes.get(CompanyConstants.SYSTEM));

		if (userAuthTypes == null) {
			sb.append("No user auth types in company: ");
			sb.append(companyId);

			throw new IllegalArgumentException(sb.toString());
		}

		if (!userAuthTypes.containsKey(issuer)) {
			sb.append("No user auth type for issuer: ");
			sb.append(issuer);
			sb.append(", in company: ");
			sb.append(companyId);

			throw new IllegalArgumentException(sb.toString());
		}

		return userAuthTypes.get(issuer);
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
	protected void activate() {
		_jwsSignatureVerifiers.put(
			CompanyConstants.SYSTEM, Collections.emptyMap());
		_userAuthTypes.put(CompanyConstants.SYSTEM, Collections.emptyMap());
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

			Map<String, JwsSignatureVerifier> kidsJWSSignatureVerifiers =
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

				if (kidsJWSSignatureVerifiers.containsKey(
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

				kidsJWSSignatureVerifiers.put(
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
		OAuth2InAssertionManagedServiceFactory.class);

	private final Map<String, Dictionary<String, ?>>
		_configurationPidsProperties = Collections.synchronizedMap(
			new LinkedHashMap<>());
	private final Map<Long, Map<String, Map<String, JwsSignatureVerifier>>>
		_jwsSignatureVerifiers = Collections.synchronizedMap(
			new LinkedHashMap<>());
	private final Map<Long, Map<String, String>> _userAuthTypes =
		Collections.synchronizedMap(new LinkedHashMap<>());

}