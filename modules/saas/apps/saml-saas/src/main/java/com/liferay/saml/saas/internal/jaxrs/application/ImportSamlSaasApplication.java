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

package com.liferay.saml.saas.internal.jaxrs.application;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.persistence.model.SamlSpIdpConnection;
import com.liferay.saml.persistence.service.SamlSpIdpConnectionLocalService;
import com.liferay.saml.runtime.configuration.SamlConfiguration;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.runtime.credential.KeyStoreManager;
import com.liferay.saml.saas.internal.configuration.SamlSaasConfiguration;
import com.liferay.saml.saas.internal.util.SymmetricEncryptor;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

import java.security.KeyStore;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marta Medio
 */
@Component(
	configurationPid = "com.liferay.saml.runtime.configuration.SamlConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL,
	property = {
		"liferay.auth.verifier=false", "liferay.oauth2=false",
		"osgi.jaxrs.application.base=/saml-saas-import",
		"osgi.jaxrs.name=Liferay.Saas.SamlImport.Application"
	},
	service = Application.class
)
public class ImportSamlSaasApplication extends Application {

	@Override
	public Set<Object> getSingletons() {
		return Collections.singleton(this);
	}

	@Consumes(MediaType.TEXT_PLAIN)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String importSamlConfiguration(
		String data, @Context HttpServletRequest httpServletRequest) {

		try {
			SamlSaasConfiguration samlSaasConfiguration =
				ConfigurationProviderUtil.getCompanyConfiguration(
					SamlSaasConfiguration.class,
					_portal.getCompanyId(httpServletRequest));

			if (!samlSaasConfiguration.productionEnvironment()) {
				_log.error(
					"Instance must be configured as a SAML SaaS production " +
						"environment to receive configuration data imports");

				throw new WebApplicationException(Response.Status.NOT_FOUND);
			}

			if (Validator.isBlank(samlSaasConfiguration.preSharedKey())) {
				_log.error(
					"Instance must be configured with a pre-shared key to " +
						"decrypt configuration data imports");

				throw new WebApplicationException(Response.Status.NOT_FOUND);
			}

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				SymmetricEncryptor.decryptData(
					samlSaasConfiguration.preSharedKey(), data));

			_generateSamlProviderConfiguration(
				(JSONObject)jsonObject.get("samlProviderConfiguration"));

			TransactionInvokerUtil.invoke(
				_transactionConfig,
				() -> {
					_generateSamlSpIdpConnections(
						httpServletRequest,
						(JSONArray)jsonObject.get("samlSpIdpConnections"));

					return null;
				});

			_generateKeystore((String)jsonObject.get("samlKeystore"));
		}
		catch (Exception exception) {
			_log.error("Unable to import SAML configuration data", exception);

			return JSONUtil.put(
				"result", "resultError"
			).toString();
		}
		catch (Throwable throwable) {
			ReflectionUtil.throwException(throwable);
		}

		return JSONUtil.put(
			"result", "resultSuccess"
		).toString();
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_samlConfiguration = ConfigurableUtil.createConfigurable(
			SamlConfiguration.class, properties);
	}

	private void _generateKeystore(String keyStoreBase64) throws Exception {
		KeyStore keyStore = _keyStoreManager.getKeyStore();

		String keyStorePassword = _samlConfiguration.keyStorePassword();

		keyStore.load(
			new ByteArrayInputStream(Base64.decode(keyStoreBase64)),
			keyStorePassword.toCharArray());

		_keyStoreManager.saveKeyStore(keyStore);
	}

	private void _generateSamlProviderConfiguration(
			JSONObject samlProviderConfigurationJsonObject)
		throws Exception {

		UnicodeProperties unicodeProperties = new UnicodeProperties();

		unicodeProperties.put(
			"saml.entity.id",
			String.valueOf(
				samlProviderConfigurationJsonObject.get("saml.entity.id")));
		unicodeProperties.put(
			"saml.idp.assertion.lifetime",
			String.valueOf(
				samlProviderConfigurationJsonObject.get(
					"saml.idp.assertion.lifetime")));
		unicodeProperties.put(
			"saml.idp.authn.request.signature.required",
			String.valueOf(
				samlProviderConfigurationJsonObject.get(
					"saml.idp.authn.request.signature.required")));
		unicodeProperties.put(
			"saml.idp.session.maximum.age",
			String.valueOf(
				samlProviderConfigurationJsonObject.get(
					"saml.idp.session.maximum.age")));
		unicodeProperties.put(
			"saml.idp.session.timeout",
			String.valueOf(
				samlProviderConfigurationJsonObject.get(
					"saml.idp.session.timeout")));
		unicodeProperties.put(
			"saml.keystore.credential.password",
			String.valueOf(
				samlProviderConfigurationJsonObject.get(
					"saml.keystore.credential.password")));
		unicodeProperties.put(
			"saml.keystore.encryption.credential.password",
			String.valueOf(
				samlProviderConfigurationJsonObject.get(
					"saml.keystore.encryption.credential.password")));
		unicodeProperties.put(
			"saml.role",
			String.valueOf(
				samlProviderConfigurationJsonObject.get("saml.role")));
		unicodeProperties.put(
			"saml.sign.metadata",
			String.valueOf(
				samlProviderConfigurationJsonObject.get("saml.sign.metadata")));
		unicodeProperties.put(
			"saml.sp.allow.showing.the.login.portlet",
			String.valueOf(
				samlProviderConfigurationJsonObject.get(
					"saml.sp.allow.showing.the.login.portlet")));
		unicodeProperties.put(
			"saml.sp.assertion.signature.required",
			String.valueOf(
				samlProviderConfigurationJsonObject.get(
					"saml.sp.assertion.signature.required")));
		unicodeProperties.put(
			"saml.sp.clock.skew",
			String.valueOf(
				samlProviderConfigurationJsonObject.get("saml.sp.clock.skew")));
		unicodeProperties.put(
			"saml.sp.ldap.import.enabled",
			String.valueOf(
				samlProviderConfigurationJsonObject.get(
					"saml.sp.ldap.import.enabled")));
		unicodeProperties.put(
			"saml.sp.sign.authn.request",
			String.valueOf(
				samlProviderConfigurationJsonObject.get(
					"saml.sp.sign.authn.request")));
		unicodeProperties.put(
			"saml.ssl.required",
			String.valueOf(
				samlProviderConfigurationJsonObject.get("saml.ssl.required")));

		_samlProviderConfigurationHelper.updateProperties(unicodeProperties);
	}

	private void _generateSamlSpIdpConnections(
			HttpServletRequest httpServletRequest,
			JSONArray samlSpIdConnectionsJsonArray)
		throws PortalException {

		List<SamlSpIdpConnection> samlSpIdpConnections =
			_samlSpIdpConnectionLocalService.getSamlSpIdpConnections(
				_portal.getCompanyId(httpServletRequest));

		for (SamlSpIdpConnection samlSpIdpConnection : samlSpIdpConnections) {
			_samlSpIdpConnectionLocalService.deleteSamlSpIdpConnection(
				samlSpIdpConnection.getSamlSpIdpConnectionId());
		}

		for (JSONObject samlSpIdpConnectionJsonObject :
				(Iterable<JSONObject>)samlSpIdConnectionsJsonArray) {

			String samlIdpEntityId = GetterUtil.getString(
				samlSpIdpConnectionJsonObject.get("samlIdpEntityId"));
			boolean assertionSignatureRequired = GetterUtil.getBoolean(
				samlSpIdpConnectionJsonObject.get(
					"assertionSignatureRequired"));
			long clockSkew = GetterUtil.getLong(
				samlSpIdpConnectionJsonObject.get("clockSkew"));
			boolean enabled = GetterUtil.getBoolean(
				samlSpIdpConnectionJsonObject.get("enabled"));
			boolean forceAuthn = GetterUtil.getBoolean(
				samlSpIdpConnectionJsonObject.get("forceAuthn"));
			boolean ldapImportEnabled = GetterUtil.getBoolean(
				samlSpIdpConnectionJsonObject.get("ldapImportEnabled"));
			String metadataUrl = GetterUtil.getString(
				samlSpIdpConnectionJsonObject.get("metadataUrl"));
			String metadataXml = GetterUtil.getString(
				samlSpIdpConnectionJsonObject.get("metadataXml"));
			String name = GetterUtil.getString(
				samlSpIdpConnectionJsonObject.get("name"));
			String nameIdFormat = GetterUtil.getString(
				samlSpIdpConnectionJsonObject.get("nameIdFormat"));
			boolean signAuthnRequest = GetterUtil.getBoolean(
				samlSpIdpConnectionJsonObject.get("signAuthnRequest"));
			boolean unknownUsersAreStrangers = GetterUtil.getBoolean(
				samlSpIdpConnectionJsonObject.get("unknownUsersAreStrangers"));
			String userAttributeMappings = GetterUtil.getString(
				samlSpIdpConnectionJsonObject.get("userAttributeMappings"));

			long samlSpIdpConnectionId = _counterLocalService.increment(
				SamlSpIdpConnection.class.getName());

			SamlSpIdpConnection samlSpIdpConnection =
				_samlSpIdpConnectionLocalService.createSamlSpIdpConnection(
					samlSpIdpConnectionId);

			samlSpIdpConnection.setCompanyId(
				_portal.getCompanyId(httpServletRequest));
			samlSpIdpConnection.setSamlIdpEntityId(samlIdpEntityId);
			samlSpIdpConnection.setAssertionSignatureRequired(
				assertionSignatureRequired);
			samlSpIdpConnection.setClockSkew(clockSkew);
			samlSpIdpConnection.setEnabled(enabled);
			samlSpIdpConnection.setForceAuthn(forceAuthn);
			samlSpIdpConnection.setLdapImportEnabled(ldapImportEnabled);
			samlSpIdpConnection.setMetadataUrl(metadataUrl);
			samlSpIdpConnection.setMetadataXml(metadataXml);
			samlSpIdpConnection.setMetadataUpdatedDate(new Date());
			samlSpIdpConnection.setUnknownUsersAreStrangers(
				unknownUsersAreStrangers);
			samlSpIdpConnection.setSamlIdpEntityId(samlIdpEntityId);
			samlSpIdpConnection.setName(name);
			samlSpIdpConnection.setNameIdFormat(nameIdFormat);
			samlSpIdpConnection.setSignAuthnRequest(signAuthnRequest);
			samlSpIdpConnection.setUserAttributeMappings(userAttributeMappings);

			JSONObject expandoValuesJsonObject =
				samlSpIdpConnectionJsonObject.getJSONObject("expandoValues");
			ExpandoBridge expandoBridge =
				samlSpIdpConnection.getExpandoBridge();

			for (String key : expandoValuesJsonObject.keySet()) {
				expandoBridge.setAttribute(
					key, (Serializable)expandoValuesJsonObject.get(key), false);
			}

			_samlSpIdpConnectionLocalService.updateSamlSpIdpConnection(
				samlSpIdpConnection);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ImportSamlSaasApplication.class);

	private static final TransactionConfig _transactionConfig;

	static {
		TransactionConfig.Builder builder = new TransactionConfig.Builder();

		builder.setPropagation(Propagation.REQUIRES_NEW);
		builder.setRollbackForClasses(Exception.class);

		_transactionConfig = builder.build();
	}

	@Reference
	private CounterLocalService _counterLocalService;

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