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
		Dictionary<String, ?> properties = _configurationPidsProperties.remove(
			pid);

		long companyId = GetterUtil.getLong(properties.get("companyId"));

		if (companyId == CompanyConstants.SYSTEM) {
			_deleteOAuthClientEntries(
				GetterUtil.getString(properties.get("providerName")),
				properties);
		}
		else {
			_deleteOAuthClientEntry(
				companyId, GetterUtil.getString(properties.get("providerName")),
				properties);
		}
	}

	@Override
	public String getName() {
		return "OpenId Connect Provider Managed Service Factory";
	}

	public long getOAuthClientEntryId(long companyId, String providerName) {
		Map<String, Long> providerNameOAuthClientEntryIds =
			_companyIdProviderNameOAuthClientEntryIds.get(companyId);

		if (providerNameOAuthClientEntryIds == null) {
			providerNameOAuthClientEntryIds =
				_companyIdProviderNameOAuthClientEntryIds.get(
					CompanyConstants.SYSTEM);
		}

		if (providerNameOAuthClientEntryIds == null) {
			return 0;
		}

		Long oAuthClientEntryId = providerNameOAuthClientEntryIds.get(
			providerName);

		if (oAuthClientEntryId == null) {
			return 0;
		}

		return oAuthClientEntryId;
	}

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		_configurationPidsProperties.forEach(
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
		Dictionary<String, ?> oldProperties = _configurationPidsProperties.put(
			pid, properties);

		long companyId = GetterUtil.getLong(properties.get("companyId"));

		String oldProviderName = (oldProperties != null) ?
			GetterUtil.getString(oldProperties.get("providerName")) : "";

		if (companyId == CompanyConstants.SYSTEM) {
			_updateOAuthClientEntries(oldProviderName, properties);

			return;
		}

		_updateOAuthClientEntry(companyId, oldProviderName, properties);
	}

	private String _deleteOAuthClientASLocalMetadata(
			Dictionary<String, ?> properties)
		throws Exception {

		String discoveryEndPoint = GetterUtil.getString(
			properties.get("discoveryEndPoint"));

		if (Validator.isNotNull(discoveryEndPoint)) {
			return discoveryEndPoint;
		}

		discoveryEndPoint = _generateLocalWellKnownURI(
			GetterUtil.getString(properties.get("issuerURL")),
			GetterUtil.getString(properties.get("tokenEndPoint")));

		_oAuthClientASLocalMetadataLocalService.
			deleteOAuthClientASLocalMetadata(discoveryEndPoint);

		return discoveryEndPoint;
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

		Map<String, Long> providerNameOAuthClientEntryIds =
			_companyIdProviderNameOAuthClientEntryIds.get(companyId);

		if (providerNameOAuthClientEntryIds != null) {
			providerNameOAuthClientEntryIds.remove(oldProviderName);
		}

		try {
			String authServerWellKnownURI = _deleteOAuthClientASLocalMetadata(
				properties);

			String openIdConnectClientId = GetterUtil.getString(
				properties.get("openIdConnectClientId"));

			_oAuthClientEntryLocalService.deleteOAuthClientEntry(
				companyId, authServerWellKnownURI, openIdConnectClientId);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to deleted converted Entry", exception);
			}
		}
	}

	private String _generateAuthRequestParametersJSON(
		Dictionary<String, ?> properties, String parametersName) {

		JSONObject requestParametersJSONObject =
			_generateRequestParametersJSONObject(properties, parametersName);

		requestParametersJSONObject.put("response_type", "code");

		return requestParametersJSONObject.toString();
	}

	private String _generateInfoJSON(Dictionary<String, ?> properties) {
		JSONObject infoJSONObject = JSONFactoryUtil.createJSONObject();

		String openIdConnectClientId = GetterUtil.getString(
			properties.get("openIdConnectClientId"));

		if (Validator.isNotNull(openIdConnectClientId)) {
			infoJSONObject.put("client_id", openIdConnectClientId);
		}

		String openIdConnectClientSecret = GetterUtil.getString(
			properties.get("openIdConnectClientSecret"));

		if (Validator.isNotNull(openIdConnectClientSecret)) {
			infoJSONObject.put("client_secret", openIdConnectClientSecret);
		}

		String providerName = GetterUtil.getString(
			properties.get("providerName"));

		if (Validator.isNotNull(providerName)) {
			infoJSONObject.put("client_name", "client to " + providerName);
		}

		String scopes = GetterUtil.getString(properties.get("scopes"));

		if (Validator.isNotNull(scopes)) {
			infoJSONObject.put("scope", scopes);
		}

		String registeredIdTokenSigningAlg = GetterUtil.getString(
			properties.get("registeredIdTokenSigningAlg"));

		if (Validator.isNotNull(registeredIdTokenSigningAlg)) {
			infoJSONObject.put(
				"id_token_signed_response_alg", registeredIdTokenSigningAlg);
		}

		infoJSONObject.put(
			"grant_types",
			JSONFactoryUtil.createJSONArray(
				new String[] {"authorization_code", "refresh_token"})
		).put(
			"response_types",
			JSONFactoryUtil.createJSONArray(new String[] {"code"})
		);

		return infoJSONObject.toString();
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
		JSONObject metadataJSONObject = JSONFactoryUtil.createJSONObject();

		String authorizationEndPoint = GetterUtil.getString(
			properties.get("authorizationEndPoint"));

		if (Validator.isNotNull(authorizationEndPoint)) {
			metadataJSONObject.put(
				"authorization_endpoint", authorizationEndPoint);
		}

		String[] idTokenSigningAlgValues = GetterUtil.getStringValues(
			properties.get("idTokenSigningAlgValues"));

		if (idTokenSigningAlgValues.length > 0) {
			metadataJSONObject.put(
				"id_token_signing_alg_values_supported",
				JSONFactoryUtil.createJSONArray(idTokenSigningAlgValues));
		}

		String issuerURL = GetterUtil.getString(properties.get("issuerURL"));

		if (Validator.isNotNull(issuerURL)) {
			metadataJSONObject.put("issuer", issuerURL);
		}

		String jwksURI = GetterUtil.getString(properties.get("jwksURI"));

		if (Validator.isNotNull(jwksURI)) {
			metadataJSONObject.put("jwks_uri", jwksURI);
		}

		String scopes = GetterUtil.getString(properties.get("scopes"));

		if (Validator.isNotNull(scopes)) {
			String[] scopesArray = scopes.split(" ");

			metadataJSONObject.put(
				"scopes_supported",
				JSONFactoryUtil.createJSONArray(scopesArray));
		}

		String[] subjectTypes = GetterUtil.getStringValues(
			properties.get("subjectTypes"));

		if (subjectTypes.length > 0) {
			metadataJSONObject.put(
				"subject_types_supported",
				JSONFactoryUtil.createJSONArray(subjectTypes));
		}

		String tokenEndPoint = GetterUtil.getString(
			properties.get("tokenEndPoint"));

		if (Validator.isNotNull(tokenEndPoint)) {
			metadataJSONObject.put("token_endpoint", tokenEndPoint);
		}

		String userInfoEndPoint = GetterUtil.getString(
			properties.get("userInfoEndPoint"));

		if (Validator.isNotNull(userInfoEndPoint)) {
			metadataJSONObject.put("userinfo_endpoint", userInfoEndPoint);
		}

		return metadataJSONObject.toString();
	}

	private JSONObject _generateRequestParametersJSONObject(
		Dictionary<String, ?> properties, String parametersName) {

		JSONObject requestParametersJSONObject =
			JSONFactoryUtil.createJSONObject();

		String scopes = GetterUtil.getString(properties.get("scopes"));

		if (Validator.isNotNull(scopes)) {
			requestParametersJSONObject.put("scope", scopes);
		}

		String[] parameters = GetterUtil.getStringValues(
			properties.get(parametersName));

		if (parameters.length < 1) {
			return requestParametersJSONObject;
		}

		for (String parameter : parameters) {
			String[] pair = parameter.split("=");

			if (pair.length != 2) {
				if (_log.isDebugEnabled()) {
					_log.debug("Parameter: " + parameter + " is not valid");
				}
			}
			else if (pair[0].equals("resource")) {
				JSONArray valuesJSONArray =
					requestParametersJSONObject.getJSONArray(pair[0]);

				if (valuesJSONArray != null) {
					for (String value : pair[1].split(" ")) {
						valuesJSONArray.put(value);
					}
				}
				else {
					requestParametersJSONObject.put(
						pair[0],
						JSONFactoryUtil.createJSONArray(pair[1].split(" ")));
				}
			}
			else if (pair[0].equals("scope")) {
				requestParametersJSONObject.put("scope", pair[1]);
			}
			else {
				JSONObject customRequestParametersJSONObject =
					requestParametersJSONObject.getJSONObject(
						"custom_request_parameters");

				if (customRequestParametersJSONObject == null) {
					requestParametersJSONObject.put(
						"custom_request_parameters",
						JSONFactoryUtil.createJSONObject());

					customRequestParametersJSONObject =
						requestParametersJSONObject.getJSONObject(
							"custom_request_parameters");
				}

				JSONArray valuesJSONArray =
					customRequestParametersJSONObject.getJSONArray(pair[0]);

				if (valuesJSONArray != null) {
					for (String value : pair[1].split(" ")) {
						valuesJSONArray.put(value);
					}
				}
				else {
					customRequestParametersJSONObject.put(
						pair[0],
						JSONFactoryUtil.createJSONArray(pair[1].split(" ")));
				}
			}
		}

		return requestParametersJSONObject;
	}

	private String _generateTokenRequestParametersJSON(
		Dictionary<String, ?> properties, String parametersName) {

		JSONObject requestParametersJSONObject =
			_generateRequestParametersJSONObject(properties, parametersName);

		requestParametersJSONObject.put("grant_type", "authorization_code");

		return requestParametersJSONObject.toString();
	}

	private void _updateCompanyIdProviderNameOAuthClientEntryIds(
		long companyId, String oldProviderName, String providerName,
		long oAuthClientEntryId) {

		Map<String, Long> providerNameOAuthClientEntryIds =
			_companyIdProviderNameOAuthClientEntryIds.get(companyId);

		if (providerNameOAuthClientEntryIds == null) {
			providerNameOAuthClientEntryIds = new HashMap<>();

			_companyIdProviderNameOAuthClientEntryIds.put(
				companyId, providerNameOAuthClientEntryIds);
		}

		providerNameOAuthClientEntryIds.remove(oldProviderName);

		providerNameOAuthClientEntryIds.put(providerName, oAuthClientEntryId);
	}

	private String _updateOAuthClientASLocalMetadata(
			long defaultUserId, Dictionary<String, ?> properties)
		throws Exception {

		String discoveryEndPoint = GetterUtil.getString(
			properties.get("discoveryEndPoint"));

		if (Validator.isNotNull(discoveryEndPoint)) {
			return discoveryEndPoint;
		}

		discoveryEndPoint = _generateLocalWellKnownURI(
			GetterUtil.getString(properties.get("issuerURL")),
			GetterUtil.getString(properties.get("tokenEndPoint")));

		OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
			_oAuthClientASLocalMetadataLocalService.
				fetchOAuthClientASLocalMetadata(discoveryEndPoint);

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

		return discoveryEndPoint;
	}

	private void _updateOAuthClientEntries(
		String oldProviderName, Dictionary<String, ?> properties) {

		try {
			_companyLocalService.forEachCompanyId(
				companyId -> _updateOAuthClientEntry(
					companyId, oldProviderName, properties));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}
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
					"Unable to find user for company: " + companyId,
					portalException);
			}
		}

		try {
			String authServerWellKnownURI = _updateOAuthClientASLocalMetadata(
				defaultUserId, properties);

			String openIdConnectClientId = GetterUtil.getString(
				properties.get("openIdConnectClientId"));

			OAuthClientEntry oAuthClientEntry =
				_oAuthClientEntryLocalService.fetchOAuthClientEntry(
					companyId, authServerWellKnownURI, openIdConnectClientId);

			if (oAuthClientEntry == null) {
				oAuthClientEntry =
					_oAuthClientEntryLocalService.addOAuthClientEntry(
						defaultUserId,
						_generateAuthRequestParametersJSON(
							properties, "customAuthorizationRequestParameters"),
						authServerWellKnownURI, _generateInfoJSON(properties),
						_generateTokenRequestParametersJSON(
							properties, "customTokenRequestParameters"));
			}
			else {
				oAuthClientEntry =
					_oAuthClientEntryLocalService.updateOAuthClientEntry(
						oAuthClientEntry.getOAuthClientEntryId(),
						_generateAuthRequestParametersJSON(
							properties, "customAuthorizationRequestParameters"),
						authServerWellKnownURI, _generateInfoJSON(properties),
						_generateTokenRequestParametersJSON(
							properties, "customTokenRequestParameters"));
			}

			_updateCompanyIdProviderNameOAuthClientEntryIds(
				companyId, oldProviderName,
				GetterUtil.getString(properties.get("providerName")),
				oAuthClientEntry.getOAuthClientEntryId());
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to convert OIDC configuration", exception);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OpenIdConnectProviderManagedServiceFactory.class);

	private final Map<Long, Map<String, Long>>
		_companyIdProviderNameOAuthClientEntryIds = new ConcurrentHashMap<>();

	@Reference
	private CompanyLocalService _companyLocalService;

	private final Map<String, Dictionary<String, ?>>
		_configurationPidsProperties = new ConcurrentHashMap<>();

	@Reference
	private OAuthClientASLocalMetadataLocalService
		_oAuthClientASLocalMetadataLocalService;

	@Reference
	private OAuthClientEntryLocalService _oAuthClientEntryLocalService;

	@Reference
	private UserLocalService _userLocalService;

}