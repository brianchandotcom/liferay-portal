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

package com.liferay.saml.saas.internal.portlet.action;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.constants.SamlPortletKeys;
import com.liferay.saml.persistence.model.SamlSpIdpConnection;
import com.liferay.saml.persistence.service.SamlSpIdpConnectionLocalService;
import com.liferay.saml.runtime.configuration.SamlConfiguration;
import com.liferay.saml.runtime.configuration.SamlProviderConfiguration;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.runtime.credential.KeyStoreManager;
import com.liferay.saml.saas.internal.configuration.SamlSaasConfiguration;
import com.liferay.saml.saas.internal.constants.JSONKeys;
import com.liferay.saml.saas.internal.util.SymmetricEncryptor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marta Medio
 */
@Component(
	configurationPid = "com.liferay.saml.runtime.configuration.SamlConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	property = {
		"javax.portlet.name=" + SamlPortletKeys.SAML_ADMIN,
		"mvc.command.name=/admin/saas/saml/export"
	},
	service = MVCActionCommand.class
)
public class ExportSamlSaasMVCActionCommand extends BaseMVCActionCommand {

	@Activate
	protected void activate(Map<String, Object> properties) {
		_samlConfiguration = ConfigurableUtil.createConfigurable(
			SamlConfiguration.class, properties);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long companyId = _portal.getCompanyId(actionRequest);

		SamlSaasConfiguration samlSaasConfiguration =
			ConfigurationProviderUtil.getCompanyConfiguration(
				SamlSaasConfiguration.class, companyId);

		if (samlSaasConfiguration.productionEnvironment() ||
			Validator.isBlank(samlSaasConfiguration.preSharedKey()) ||
			Validator.isBlank(
				samlSaasConfiguration.targetInstanceImportURL())) {

			return;
		}

		try {
			Client client = _clientBuilder.build();

			WebTarget webTarget = client.target(
				UriBuilder.fromUri(
					samlSaasConfiguration.targetInstanceImportURL()));

			String json = webTarget.request(
				MediaType.APPLICATION_JSON
			).post(
				Entity.entity(
					_getEncryptedJSONPayload(
						companyId, samlSaasConfiguration.preSharedKey()),
					MediaType.TEXT_PLAIN),
				String.class
			);

			if (json != null) {
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(json);

				if (JSONKeys.RESULT_ERROR.equals(
						jsonObject.get(JSONKeys.RESULT))) {

					SessionErrors.add(actionRequest, "exportError");
				}
			}
		}
		catch (Exception exception) {
			_log.error("Unable to export SAML configuration data", exception);

			SessionErrors.add(actionRequest, "exportError");
		}
	}

	private String _getEncryptedJSONPayload(long companyId, String preSharedKey)
		throws Exception {

		JSONObject jsonObject;

		try {
			jsonObject = JSONUtil.put(JSONKeys.SAML_KEYSTORE, _getKeyStore());
		}
		catch (Exception exception) {
			_log.error(
				"Unable to export the SAML KeyStore for companyId " + companyId,
				exception);

			throw exception;
		}

		jsonObject.put(
			JSONKeys.SAML_PROVIDER_CONFIGURATION,
			_getSamlProviderConfiguration()
		).put(
			JSONKeys.SAML_SP_IDP_CONNECTIONS, _getSpIdpConnections(companyId)
		);

		try {
			return SymmetricEncryptor.encryptData(
				preSharedKey, jsonObject.toString());
		}
		catch (Exception exception) {
			_log.error("Unable to encrypt the JSON payload", exception);

			throw exception;
		}
	}

	private String _getKeyStore()
		throws CertificateException, IOException, KeyStoreException,
			   NoSuchAlgorithmException {

		KeyStore keyStore = _keyStoreManager.getKeyStore();
		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();
		String keyStorePassword = _samlConfiguration.keyStorePassword();

		keyStore.store(byteArrayOutputStream, keyStorePassword.toCharArray());

		return Base64.encode(byteArrayOutputStream.toByteArray());
	}

	private JSONObject _getSamlProviderConfiguration() {
		SamlProviderConfiguration samlProviderConfiguration =
			_samlProviderConfigurationHelper.getSamlProviderConfiguration();

		return JSONUtil.put(
			"saml.entity.id", samlProviderConfiguration.entityId()
		).put(
			"saml.idp.assertion.lifetime",
			samlProviderConfiguration.defaultAssertionLifetime()
		).put(
			"saml.idp.authn.request.signature.required",
			samlProviderConfiguration.authnRequestSignatureRequired()
		).put(
			"saml.idp.session.maximum.age",
			samlProviderConfiguration.sessionMaximumAge()
		).put(
			"saml.idp.session.timeout",
			samlProviderConfiguration.sessionTimeout()
		).put(
			"saml.keystore.credential.password",
			samlProviderConfiguration.keyStoreCredentialPassword()
		).put(
			"saml.keystore.encryption.credential.password",
			samlProviderConfiguration.keyStoreEncryptionCredentialPassword()
		).put(
			"saml.role", samlProviderConfiguration.role()
		).put(
			"saml.sign.metadata", samlProviderConfiguration.signMetadata()
		).put(
			"saml.sp.allow.showing.the.login.portlet",
			samlProviderConfiguration.allowShowingTheLoginPortlet()
		).put(
			"saml.sp.assertion.signature.required",
			samlProviderConfiguration.assertionSignatureRequired()
		).put(
			"saml.sp.clock.skew", samlProviderConfiguration.clockSkew()
		).put(
			"saml.sp.ldap.import.enabled",
			samlProviderConfiguration.ldapImportEnabled()
		).put(
			"saml.sp.sign.authn.request",
			samlProviderConfiguration.signAuthnRequest()
		).put(
			"saml.ssl.required", samlProviderConfiguration.sslRequired()
		);
	}

	private JSONObject _getSpIdpConnectionExpandoValues(
		SamlSpIdpConnection samlSpIdpConnection) {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		ExpandoBridge expandoBridge = samlSpIdpConnection.getExpandoBridge();

		Enumeration<String> enumeration = expandoBridge.getAttributeNames();

		while (enumeration.hasMoreElements()) {
			String name = enumeration.nextElement();

			jsonObject.put(name, expandoBridge.getAttribute(name, false));
		}

		return jsonObject;
	}

	private JSONArray _getSpIdpConnections(long companyId) {
		JSONArray samlSpIdpConnectionsJsonArray =
			JSONFactoryUtil.createJSONArray();

		List<SamlSpIdpConnection> samlSpIdpConnectionsList =
			_samlSpIdpConnectionLocalService.getSamlSpIdpConnections(companyId);

		for (SamlSpIdpConnection samlSpIdpConnection :
				samlSpIdpConnectionsList) {

			samlSpIdpConnectionsJsonArray.put(
				JSONUtil.put(
					JSONKeys.EXPANDO_VALUES,
					_getSpIdpConnectionExpandoValues(samlSpIdpConnection)
				).put(
					"assertionSignatureRequired",
					samlSpIdpConnection.isAssertionSignatureRequired()
				).put(
					"clockSkew", samlSpIdpConnection.getClockSkew()
				).put(
					"enabled", samlSpIdpConnection.isEnabled()
				).put(
					"forceAuthn", samlSpIdpConnection.isForceAuthn()
				).put(
					"ldapImportEnabled",
					samlSpIdpConnection.isLdapImportEnabled()
				).put(
					"metadataUrl", samlSpIdpConnection.getMetadataUrl()
				).put(
					"metadataXml", samlSpIdpConnection.getMetadataXml()
				).put(
					"name", samlSpIdpConnection.getName()
				).put(
					"nameIdFormat", samlSpIdpConnection.getNameIdFormat()
				).put(
					"samlIdpEntityId", samlSpIdpConnection.getSamlIdpEntityId()
				).put(
					"samlSpIdpConnectionId",
					samlSpIdpConnection.getSamlSpIdpConnectionId()
				).put(
					"signAuthnRequest", samlSpIdpConnection.isSignAuthnRequest()
				).put(
					"unknownUsersAreStrangers",
					samlSpIdpConnection.isUnknownUsersAreStrangers()
				).put(
					"userAttributeMappings",
					samlSpIdpConnection.getUserAttributeMappings()
				));
		}

		return samlSpIdpConnectionsJsonArray;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExportSamlSaasMVCActionCommand.class);

	@Reference
	private ClientBuilder _clientBuilder;

	@Reference(name = "KeyStoreManager")
	private KeyStoreManager _keyStoreManager;

	@Reference
	private Portal _portal;

	private SamlConfiguration _samlConfiguration;

	@Reference
	private SamlProviderConfigurationHelper _samlProviderConfigurationHelper;

	@Reference
	private SamlSpIdpConnectionLocalService _samlSpIdpConnectionLocalService;

}