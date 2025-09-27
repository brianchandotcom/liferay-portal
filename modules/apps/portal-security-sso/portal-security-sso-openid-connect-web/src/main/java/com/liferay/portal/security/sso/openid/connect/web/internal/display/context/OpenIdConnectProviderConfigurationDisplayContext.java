/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.openid.connect.web.internal.display.context;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import java.io.IOException;

import java.util.Dictionary;

import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Christian Moura
 */
public class OpenIdConnectProviderConfigurationDisplayContext {

	public OpenIdConnectProviderConfigurationDisplayContext(
			ConfigurationAdmin configurationAdmin, String pid)
		throws InvalidSyntaxException, IOException {

		Configuration[] configurations = configurationAdmin.listConfigurations(
			StringBundler.concat(
				"(&(companyId=", CompanyThreadLocal.getCompanyId(),
				")(service.pid=", pid, "))"));

		if (configurations != null) {
			_properties = configurations[0].getProperties();
		}
		else {
			String[] defaultValue = {StringPool.BLANK};

			_properties = HashMapDictionaryBuilder.<String, Object>put(
				"customAuthorizationRequestParameters", defaultValue
			).put(
				"customTokenRequestParameters", defaultValue
			).put(
				"discoveryEndpointCacheInMillis", 36000
			).put(
				"idTokenSigningAlgValues", new String[] {"RS256"}
			).put(
				"scopes", "openid email profile"
			).put(
				"subjectTypes", defaultValue
			).put(
				"tokenConnectionTimeout", 1000
			).build();
		}

		_customAuthorizationRequestParameters = GetterUtil.getStringValues(
			_properties.get("customAuthorizationRequestParameters"));

		_customAuthorizationRequestParametersIndexes = _createIndexes(
			_customAuthorizationRequestParameters.length);

		_customTokenRequestParameters = GetterUtil.getStringValues(
			_properties.get("customTokenRequestParameters"));

		_customTokenRequestParametersIndexes = _createIndexes(
			_customTokenRequestParameters.length);

		_idTokenSigningAlgValues = GetterUtil.getStringValues(
			_properties.get("idTokenSigningAlgValues"));

		_idTokenSigningAlgValuesIndexes = _createIndexes(
			_idTokenSigningAlgValues.length);

		_subjectTypes = GetterUtil.getStringValues(
			_properties.get("subjectTypes"));

		_subjectTypesIndexes = _createIndexes(_subjectTypes.length);
	}

	public String getAuthorizationEndpoint() {
		return GetterUtil.getString(_properties.get("authorizationEndpoint"));
	}

	public String[] getCustomAuthorizationRequestParameters() {
		return _customAuthorizationRequestParameters;
	}

	public int[] getCustomAuthorizationRequestParametersIndexes() {
		return _customAuthorizationRequestParametersIndexes;
	}

	public String[] getCustomTokenRequestParameters() {
		return _customTokenRequestParameters;
	}

	public int[] getCustomTokenRequestParametersIndexes() {
		return _customTokenRequestParametersIndexes;
	}

	public String getDiscoveryEndpoint() {
		return GetterUtil.getString(_properties.get("discoveryEndpoint"));
	}

	public long getDiscoveryEndpointCacheInMillis() {
		return GetterUtil.getLong(
			_properties.get("discoveryEndpointCacheInMillis"));
	}

	public String[] getIdTokenSigningAlgValues() {
		return _idTokenSigningAlgValues;
	}

	public int[] getIdTokenSigningAlgValuesIndexes() {
		return _idTokenSigningAlgValuesIndexes;
	}

	public String getIssuerURL() {
		return GetterUtil.getString(_properties.get("issuerURL"));
	}

	public String getJwksURI() {
		return GetterUtil.getString(_properties.get("jwksURI"));
	}

	public String getOpenIdConnectClientId() {
		return GetterUtil.getString(_properties.get("openIdConnectClientId"));
	}

	public String getOpenIdConnectClientSecret() {
		return GetterUtil.getString(
			_properties.get("openIdConnectClientSecret"));
	}

	public String getProviderName() {
		return GetterUtil.getString(_properties.get("providerName"));
	}

	public String getRegisteredIdTokenSigningAlg() {
		return GetterUtil.getString(
			_properties.get("registeredIdTokenSigningAlg"));
	}

	public String getScopes() {
		return GetterUtil.getString(_properties.get("scopes"));
	}

	public String[] getSubjectTypes() {
		return _subjectTypes;
	}

	public int[] getSubjectTypesIndexes() {
		return _subjectTypesIndexes;
	}

	public int getTokenConnectionTimeout() {
		return GetterUtil.getInteger(_properties.get("tokenConnectionTimeout"));
	}

	public String getTokenEndpoint() {
		return GetterUtil.getString(_properties.get("tokenEndpoint"));
	}

	public String getUserInfoEndpoint() {
		return GetterUtil.getString(_properties.get("userInfoEndpoint"));
	}

	private int[] _createIndexes(int size) {
		int[] indexes = new int[size];

		for (int i = 0; i < size; i++) {
			indexes[i] = i;
		}

		return indexes;
	}

	private final String[] _customAuthorizationRequestParameters;
	private final int[] _customAuthorizationRequestParametersIndexes;
	private final String[] _customTokenRequestParameters;
	private final int[] _customTokenRequestParametersIndexes;
	private final String[] _idTokenSigningAlgValues;
	private final int[] _idTokenSigningAlgValuesIndexes;
	private final Dictionary<String, Object> _properties;
	private final String[] _subjectTypes;
	private final int[] _subjectTypesIndexes;

}