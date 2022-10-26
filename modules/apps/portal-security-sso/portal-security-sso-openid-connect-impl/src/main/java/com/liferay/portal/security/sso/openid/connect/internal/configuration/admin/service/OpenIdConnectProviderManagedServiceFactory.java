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

package com.liferay.portal.security.sso.openid.connect.internal.configuration.admin.service;

import com.liferay.oauth.client.persistence.constants.OAuthClientEntryConstants;
import com.liferay.oauth.client.persistence.model.OAuthClientASLocalMetadata;
import com.liferay.oauth.client.persistence.model.OAuthClientEntry;
import com.liferay.oauth.client.persistence.service.OAuthClientASLocalMetadataLocalService;
import com.liferay.oauth.client.persistence.service.OAuthClientEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.net.URI;

import java.security.MessageDigest;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Constants;
import org.osgi.service.cm.ManagedServiceFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * A workaround to convert OIDC provider configuration during grace period. An
 * upgrade will replace this workaround when grace period ends. Also helps
 * backward compatible with a popular custom code during grace period.
 *
 * @author     Arthur Chan
 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
 * @review
 */
@Component(
	immediate = true,
	property = Constants.SERVICE_PID + "=com.liferay.portal.security.sso.openid.connect.internal.configuration.OpenIdConnectProviderConfiguration",
	service = {
		ManagedServiceFactory.class,
		OpenIdConnectProviderManagedServiceFactory.class,
		PortalInstanceLifecycleListener.class
	}
)
@Deprecated
public class OpenIdConnectProviderManagedServiceFactory
	extends BasePortalInstanceLifecycleListener
	implements ManagedServiceFactory {

	@Override
	public void deleted(String pid) {
		Dictionary<String, ?> properties = _properties.remove(pid);

		long companyId = GetterUtil.getLong(properties.get("companyId"));

		if (companyId == CompanyConstants.SYSTEM) {
			_deleteOAuthClientEntries(
				_getPropertyAsString("providerName", properties), properties);
		}
		else {
			_deleteOAuthClientEntry(
				companyId, _getPropertyAsString("providerName", properties),
				properties);
		}
	}

	@Override
	public String getName() {
		return "OpenId Connect Provider Managed Service Factory";
	}

	public long getOAuthClientEntryId(long companyId, String providerName) {
		Map<String, Long> oAuthClientEntryIds = _oAuthClientEntryIds.get(
			companyId);

		if (oAuthClientEntryIds == null) {
			oAuthClientEntryIds = _oAuthClientEntryIds.get(
				CompanyConstants.SYSTEM);
		}

		if (oAuthClientEntryIds == null) {
			return 0;
		}

		Long oAuthClientEntryId = oAuthClientEntryIds.get(providerName);

		if (oAuthClientEntryId == null) {
			return 0;
		}

		return oAuthClientEntryId;
	}

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		_properties.forEach(
			(pid, properties) -> {
				if (GetterUtil.getLong(properties.get("companyId")) ==
						CompanyConstants.SYSTEM) {

					_updateOAuthClientEntry(
						company.getCompanyId(), "", properties);
				}
			});
	}

	@Override
	public void updated(String pid, Dictionary<String, ?> properties) {
		long companyId = GetterUtil.getLong(properties.get("companyId"));

		Dictionary<String, ?> oldProperties = _properties.put(pid, properties);

		String oldProviderName = (oldProperties != null) ?
			_getPropertyAsString("providerName", oldProperties) : "";

		if (companyId == CompanyConstants.SYSTEM) {
			try {
				_companyLocalService.forEachCompanyId(
					curCompanyId -> _updateOAuthClientEntry(
						curCompanyId, oldProviderName, properties));
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}
			}

			return;
		}

		_updateOAuthClientEntry(companyId, oldProviderName, properties);
	}

	private String _deleteOAuthClientASLocalMetadata(
			Dictionary<String, ?> properties)
		throws Exception {

		String discoveryEndPoint = _getPropertyAsString(
			"discoveryEndPoint", properties);

		if (Validator.isNotNull(discoveryEndPoint)) {
			return discoveryEndPoint;
		}

		String localWellKnownURI = _generateLocalWellKnownURI(
			_getPropertyAsString("issuerURL", properties),
			_getPropertyAsString("tokenEndPoint", properties));

		_oAuthClientASLocalMetadataLocalService.
			deleteOAuthClientASLocalMetadata(localWellKnownURI);

		return localWellKnownURI;
	}

	private void _deleteOAuthClientEntries(
		String oldProviderName, Dictionary<String, ?> properties) {

		try {
			_companyLocalService.forEachCompanyId(
				companyId -> _deleteOAuthClientEntry(
					companyId, oldProviderName, properties));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}
	}

	private void _deleteOAuthClientEntry(
		long companyId, String oldProviderName,
		Dictionary<String, ?> properties) {

		Map<String, Long> oAuthClientEntryIds = _oAuthClientEntryIds.get(
			companyId);

		if (oAuthClientEntryIds != null) {
			oAuthClientEntryIds.remove(oldProviderName);
		}

		try {
			String authServerWellKnownURI = _deleteOAuthClientASLocalMetadata(
				properties);

			_oAuthClientEntryLocalService.deleteOAuthClientEntry(
				companyId, authServerWellKnownURI,
				_getPropertyAsString("openIdConnectClientId", properties));
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}
	}

	private String _generateAuthRequestParametersJSON(
		Dictionary<String, ?> properties) {

		JSONObject requestParametersJSONObject =
			_generateRequestParametersJSONObject(
				"customAuthorizationRequestParameters", properties);

		return requestParametersJSONObject.put(
			"response_type", "code"
		).toString();
	}

	private String _generateClientName(Dictionary<String, ?> properties) {
		String providerName = _getPropertyAsString("providerName", properties);

		if (Validator.isNull(providerName)) {
			return null;
		}

		return "Client to " + providerName;
	}

	private String _generateInfoJSON(Dictionary<String, ?> properties) {
		return JSONUtil.put(
			"client_id",
			_getPropertyAsString("openIdConnectClientId", properties)
		).put(
			"client_name", _generateClientName(properties)
		).put(
			"client_secret",
			_getPropertyAsString("openIdConnectClientSecret", properties)
		).put(
			"grant_types",
			JSONUtil.putAll("authorization_code", "refresh_token")
		).put(
			"id_token_signed_response_alg",
			_getPropertyAsString("registeredIdTokenSigningAlg", properties)
		).put(
			"response_types", JSONUtil.put("code")
		).put(
			"scope", _getPropertyAsString("scopes", properties)
		).toString();
	}

	private String _generateLocalWellKnownURI(
			String issuer, String tokenEndPoint)
		throws Exception {

		URI issuerURI = URI.create(issuer);
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");

		return StringBundler.concat(
			issuerURI.getScheme(), "://", issuerURI.getAuthority(),
			"/.well-known/openid-configuration", issuerURI.getPath(), '/',
			Base64.encodeToURL(messageDigest.digest(tokenEndPoint.getBytes())),
			"/local");
	}

	private String _generateMetadataJSON(Dictionary<String, ?> properties) {
		return JSONUtil.put(
			"authorization_endpoint",
			_getPropertyAsString("authorizationEndPoint", properties)
		).put(
			"id_token_signing_alg_values_supported",
			_getPropertyAsJSONArray("idTokenSigningAlgValues", properties)
		).put(
			"issuer", _getPropertyAsString("issuerURL", properties)
		).put(
			"jwks_uri", _getPropertyAsString("jwksURI", properties)
		).put(
			"scopes_supported", _getPropertyAsJSONArray("scopes", properties)
		).put(
			"subject_types_supported",
			_getPropertyAsJSONArray("subjectTypes", properties)
		).put(
			"token_endpoint", _getPropertyAsString("tokenEndPoint", properties)
		).put(
			"userinfo_endpoint",
			_getPropertyAsString("userInfoEndPoint", properties)
		).toString();
	}

	private JSONObject _generateRequestParametersJSONObject(
		String key, Dictionary<String, ?> properties) {

		JSONObject requestParametersJSONObject = JSONUtil.put(
			"scope", _getPropertyAsString("scopes", properties));

		String[] parameters = GetterUtil.getStringValues(properties.get(key));

		if (parameters.length < 1) {
			return requestParametersJSONObject;
		}

		for (String parameter : parameters) {
			String[] parameterArray = parameter.split("=");

			if (parameterArray.length != 2) {
				if (_log.isDebugEnabled()) {
					_log.debug("Parameter: " + parameter + " is not valid");
				}
			}
			else if (parameterArray[0].equals("resource")) {
				JSONArray valuesJSONArray =
					requestParametersJSONObject.getJSONArray(parameterArray[0]);

				if (valuesJSONArray != null) {
					for (String value : parameterArray[1].split(" ")) {
						valuesJSONArray.put(value);
					}
				}
				else {
					requestParametersJSONObject.put(
						parameterArray[0],
						JSONUtil.putAll(
							(Object[])parameterArray[1].split(" ")));
				}
			}
			else {
				JSONObject customRequestParametersJSONObject =
					requestParametersJSONObject.getJSONObject(
						"custom_request_parameters");

				if (customRequestParametersJSONObject == null) {
					customRequestParametersJSONObject =
						JSONFactoryUtil.createJSONObject();

					requestParametersJSONObject.put(
						"custom_request_parameters",
						customRequestParametersJSONObject);
				}

				JSONArray valuesJSONArray =
					customRequestParametersJSONObject.getJSONArray(
						parameterArray[0]);

				if (valuesJSONArray != null) {
					for (String value : parameterArray[1].split(" ")) {
						valuesJSONArray.put(value);
					}
				}
				else {
					customRequestParametersJSONObject.put(
						parameterArray[0],
						JSONUtil.putAll(
							(Object[])parameterArray[1].split(" ")));
				}
			}
		}

		return requestParametersJSONObject;
	}

	private String _generateTokenRequestParametersJSON(
		Dictionary<String, ?> properties) {

		JSONObject requestParametersJSONObject =
			_generateRequestParametersJSONObject(
				"customTokenRequestParameters", properties);

		return requestParametersJSONObject.put(
			"grant_type", "authorization_code"
		).toString();
	}

	private JSONArray _getPropertyAsJSONArray(
		String key, Dictionary<String, ?> properties) {

		if (properties.get(key) == null) {
			return null;
		}

		String[] values = null;

		if (key.equals("scopes")) {
			String scopes = _getPropertyAsString("scopes", properties);

			values = scopes.split(" ");
		}
		else {
			values = GetterUtil.getStringValues(properties.get(key));
		}

		if (values.length < 1) {
			return null;
		}

		return JSONUtil.putAll((Object[])values);
	}

	private String _getPropertyAsString(
		String key, Dictionary<String, ?> properties) {

		String value = (String)properties.get(key);

		if ((value == null) || value.equals("")) {
			return null;
		}

		return value;
	}

	private String _updateOAuthClientASLocalMetadata(
			long defaultUserId, Dictionary<String, ?> properties)
		throws Exception {

		String discoveryEndPoint = _getPropertyAsString(
			"discoveryEndPoint", properties);

		if (Validator.isNotNull(discoveryEndPoint)) {
			return discoveryEndPoint;
		}

		String localWellKnownURI = _generateLocalWellKnownURI(
			_getPropertyAsString("issuerURL", properties),
			_getPropertyAsString("tokenEndPoint", properties));

		OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
			_oAuthClientASLocalMetadataLocalService.
				fetchOAuthClientASLocalMetadata(localWellKnownURI);

		if (oAuthClientASLocalMetadata == null) {
			_oAuthClientASLocalMetadataLocalService.
				addOAuthClientASLocalMetadata(
					defaultUserId, _generateMetadataJSON(properties),
					"openid-configuration");
		}
		else {
			_oAuthClientASLocalMetadataLocalService.
				updateOAuthClientASLocalMetadata(
					oAuthClientASLocalMetadata.
						getOAuthClientASLocalMetadataId(),
					_generateMetadataJSON(properties), "openid-configuration");
		}

		return localWellKnownURI;
	}

	private void _updateOAuthClientEntry(
		long companyId, String oldProviderName,
		Dictionary<String, ?> properties) {

		long defaultUserId = 0;

		try {
			defaultUserId = _userLocalService.getDefaultUserId(companyId);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to get default user ID for company " + companyId,
					portalException);
			}
		}

		try {
			String authServerWellKnownURI = _updateOAuthClientASLocalMetadata(
				defaultUserId, properties);

			OAuthClientEntry oAuthClientEntry =
				_oAuthClientEntryLocalService.fetchOAuthClientEntry(
					companyId, authServerWellKnownURI,
					_getPropertyAsString("openIdConnectClientId", properties));

			if (oAuthClientEntry == null) {
				oAuthClientEntry =
					_oAuthClientEntryLocalService.addOAuthClientEntry(
						defaultUserId,
						_generateAuthRequestParametersJSON(properties),
						authServerWellKnownURI, _generateInfoJSON(properties),
						_generateTokenRequestParametersJSON(properties),
						OAuthClientEntryConstants.USER_INFO_MAPPER_JSON);
			}
			else {
				oAuthClientEntry =
					_oAuthClientEntryLocalService.updateOAuthClientEntry(
						oAuthClientEntry.getOAuthClientEntryId(),
						_generateAuthRequestParametersJSON(properties),
						authServerWellKnownURI, _generateInfoJSON(properties),
						_generateTokenRequestParametersJSON(properties),
						oAuthClientEntry.getUserInfoMapperJSON());
			}

			Map<String, Long> oAuthClientEntryIds = _oAuthClientEntryIds.get(
				companyId);

			if (oAuthClientEntryIds == null) {
				oAuthClientEntryIds = new HashMap<>();

				_oAuthClientEntryIds.put(companyId, oAuthClientEntryIds);
			}

			oAuthClientEntryIds.remove(oldProviderName);

			oAuthClientEntryIds.put(
				_getPropertyAsString("providerName", properties),
				oAuthClientEntry.getOAuthClientEntryId());
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to update OAuth client entry", exception);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OpenIdConnectProviderManagedServiceFactory.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private OAuthClientASLocalMetadataLocalService
		_oAuthClientASLocalMetadataLocalService;

	private final Map<Long, Map<String, Long>> _oAuthClientEntryIds =
		new ConcurrentHashMap<>();

	@Reference
	private OAuthClientEntryLocalService _oAuthClientEntryLocalService;

	private final Map<String, Dictionary<String, ?>> _properties =
		new ConcurrentHashMap<>();

	@Reference
	private UserLocalService _userLocalService;

}