/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.saml.admin.rest.internal.resource.v1_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.saml.admin.rest.dto.v1_0.Idp;
import com.liferay.saml.admin.rest.dto.v1_0.SamlProvider;
import com.liferay.saml.admin.rest.dto.v1_0.Sp;
import com.liferay.saml.admin.rest.resource.v1_0.SamlProviderResource;
import com.liferay.saml.constants.SamlProviderConfigurationKeys;
import com.liferay.saml.runtime.configuration.SamlConfiguration;
import com.liferay.saml.runtime.configuration.SamlProviderConfiguration;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.runtime.exception.CredentialException;
import com.liferay.saml.runtime.exception.EntityIdException;
import com.liferay.saml.runtime.metadata.LocalEntityManager;
import com.liferay.saml.util.PortletPropsKeys;

import java.io.Serializable;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ManagedServiceFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Stian Sigvartsen
 */
@Component(
	configurationPid = "com.liferay.saml.runtime.configuration.SamlConfiguration",
	properties = "OSGI-INF/liferay/rest/v1_0/saml-provider.properties",
	scope = ServiceScope.PROTOTYPE, service = SamlProviderResource.class
)
public class SamlProviderResourceImpl extends BaseSamlProviderResourceImpl {

	@Override
	public SamlProvider getSamlProvider() throws Exception {
		_checkPermission();

		SamlProviderConfiguration samlProviderConfiguration =
			_samlProviderConfigurationHelper.getSamlProviderConfiguration();

		SamlProvider samlProvider = new SamlProvider();

		samlProvider.setEnabled(samlProviderConfiguration.enabled());
		samlProvider.setEntityId(samlProviderConfiguration.entityId());
		samlProvider.setSignMetadata(samlProviderConfiguration.signMetadata());
		samlProvider.setSslRequired(samlProviderConfiguration.sslRequired());

		String role = samlProviderConfiguration.role();

		if (SamlProviderConfigurationKeys.SAML_ROLE_SP.equals(role)) {
			samlProvider.setRole(SamlProvider.Role.SP);
			samlProvider.setSp(_getSp(samlProviderConfiguration));
		}
		else if (SamlProviderConfigurationKeys.SAML_ROLE_IDP.equals(role)) {
			samlProvider.setIdp(_getIdp(samlProviderConfiguration));
			samlProvider.setRole(SamlProvider.Role.IDP);
		}

		return samlProvider;
	}

	@Override
	public SamlProvider patchSamlProvider(SamlProvider samlProvider)
		throws Exception {

		_checkPermission();

		return _updateSamlProvider(
			samlProvider,
			_samlProviderConfigurationHelper.getSamlProviderConfiguration());
	}

	@Override
	public SamlProvider postSamlProvider(SamlProvider samlProvider)
		throws Exception {

		_checkPermission();

		return _updateSamlProvider(
			samlProvider, _systemSamlProviderConfiguration);
	}

	@Override
	public Page<SamlProvider> read(
			Filter filter, Pagination pagination, Sort[] sorts,
			Map<String, Serializable> parameters, String search)
		throws Exception {

		_checkPermission();

		return Page.of(Collections.singleton(getSamlProvider()));
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_samlConfiguration = ConfigurableUtil.createConfigurable(
			SamlConfiguration.class, properties);

		_serviceRegistration = bundleContext.registerService(
			ManagedServiceFactory.class,
			new SystemConfigurationManagedServiceFactory(),
			HashMapDictionaryBuilder.put(
				Constants.SERVICE_PID,
				"com.liferay.saml.runtime.configuration." +
					"SamlProviderConfiguration"
			).build());
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}

	private void _authenticateLocalEntityCertificate(
			String certificateKeyPassword,
			LocalEntityManager.CertificateUsage certificateUsage,
			String entityId)
		throws Exception {

		try {
			_localEntityManager.authenticateLocalEntityCertificate(
				certificateKeyPassword, certificateUsage, entityId);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}

			throw new CredentialException(
				StringBundler.concat(
					"Error occurred when authenticating the ",
					certificateUsage.name(), " certificate. Please verify ",
					"that the SAML KeyStore contains a certificate for the ",
					"Entity ID and that it is protected by the provided key ",
					"credential password"));
		}
	}

	private void _checkPermission() throws Exception {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (!permissionChecker.isCompanyAdmin(
				CompanyThreadLocal.getCompanyId())) {

			throw new PrincipalException.MustBeCompanyAdmin(
				permissionChecker.getUserId());
		}
	}

	private Idp _getIdp(SamlProviderConfiguration samlProviderConfiguration)
		throws Exception {

		Idp idp = new Idp();

		idp.setAuthnRequestSignatureRequired(
			samlProviderConfiguration.authnRequestSignatureRequired());
		idp.setDefaultAssertionLifetime(
			samlProviderConfiguration.defaultAssertionLifetime());
		idp.setSessionMaximumAge(samlProviderConfiguration.sessionMaximumAge());
		idp.setSessionTimeout(samlProviderConfiguration.sessionTimeout());

		return idp;
	}

	private Sp _getSp(SamlProviderConfiguration samlProviderConfiguration)
		throws Exception {

		Sp sp = new Sp();

		sp.setAllowShowingTheLoginPortlet(
			samlProviderConfiguration.allowShowingTheLoginPortlet());
		sp.setAssertionSignatureRequired(
			samlProviderConfiguration.assertionSignatureRequired());
		sp.setClockSkew(samlProviderConfiguration.clockSkew());
		sp.setLdapImportEnabled(samlProviderConfiguration.ldapImportEnabled());
		sp.setSignAuthnRequest(samlProviderConfiguration.signAuthnRequest());

		return sp;
	}

	private boolean _isValidRole(String role) {
		if (Validator.isBlank(role)) {
			return false;
		}

		if (role.equals(SamlProviderConfigurationKeys.SAML_ROLE_SP) ||
			role.equals(SamlProviderConfigurationKeys.SAML_ROLE_IDP)) {

			return true;
		}

		return false;
	}

	private void _setIdpProperties(
		Idp idp, UnicodeProperties unicodeProperties,
		SamlProviderConfiguration samlProviderConfiguration) {

		_setProperty(
			unicodeProperties, PortletPropsKeys.SAML_IDP_ASSERTION_LIFETIME,
			_toNullableString(idp.getDefaultAssertionLifetime()),
			samlProviderConfiguration::defaultAssertionLifetime);
		_setProperty(
			unicodeProperties,
			PortletPropsKeys.SAML_IDP_AUTHN_REQUEST_SIGNATURE_REQUIRED,
			_toNullableString(idp.getAuthnRequestSignatureRequired()),
			samlProviderConfiguration::authnRequestSignatureRequired);
		_setProperty(
			unicodeProperties, PortletPropsKeys.SAML_IDP_SESSION_MAXIMUM_AGE,
			_toNullableString(idp.getSessionMaximumAge()),
			samlProviderConfiguration::sessionMaximumAge);
		_setProperty(
			unicodeProperties, PortletPropsKeys.SAML_IDP_SESSION_TIMEOUT,
			_toNullableString(idp.getSessionTimeout()),
			samlProviderConfiguration::sessionTimeout);

		unicodeProperties.put(
			PortletPropsKeys.SAML_ROLE,
			SamlProviderConfigurationKeys.SAML_ROLE_IDP);
	}

	private void _setProperty(
		UnicodeProperties unicodeProperties, String key, String value,
		Supplier<Object> defaultSupplier) {

		if (value == null) {
			unicodeProperties.put(
				key, _toNullableString(defaultSupplier.get()));

			return;
		}

		unicodeProperties.put(key, value);
	}

	private void _setSamlProviderProperties(
		SamlProvider samlProvider,
		SamlProviderConfiguration samlProviderConfiguration,
		UnicodeProperties unicodeProperties) {

		_setProperty(
			unicodeProperties, PortletPropsKeys.SAML_ENABLED,
			_toNullableString(samlProvider.getEnabled()),
			samlProviderConfiguration::enabled);

		_setProperty(
			unicodeProperties, PortletPropsKeys.SAML_ENTITY_ID,
			samlProvider.getEntityId(), samlProviderConfiguration::entityId);

		_setProperty(
			unicodeProperties,
			PortletPropsKeys.SAML_KEYSTORE_CREDENTIAL_PASSWORD,
			_toNullableString(samlProvider.getKeyStoreCredentialPassword()),
			samlProviderConfiguration::keyStoreCredentialPassword);

		_setProperty(
			unicodeProperties, PortletPropsKeys.SAML_SIGN_METADATA,
			_toNullableString(samlProvider.getSignMetadata()),
			samlProviderConfiguration::signMetadata);

		_setProperty(
			unicodeProperties, PortletPropsKeys.SAML_SSL_REQUIRED,
			_toNullableString(samlProvider.getSslRequired()),
			samlProviderConfiguration::sslRequired);
	}

	private void _setSpProperties(
			String entityId, Sp sp, UnicodeProperties unicodeProperties,
			SamlProviderConfiguration samlProviderConfiguration)
		throws Exception {

		if (sp.getKeyStoreEncryptionCredentialPassword() != null) {
			_authenticateLocalEntityCertificate(
				sp.getKeyStoreEncryptionCredentialPassword(),
				LocalEntityManager.CertificateUsage.ENCRYPTION, entityId);
		}

		_setProperty(
			unicodeProperties,
			PortletPropsKeys.SAML_SP_ALLOW_SHOWING_THE_LOGIN_PORTLET,
			_toNullableString(sp.getAllowShowingTheLoginPortlet()),
			samlProviderConfiguration::allowShowingTheLoginPortlet);
		_setProperty(
			unicodeProperties,
			PortletPropsKeys.SAML_SP_ASSERTION_SIGNATURE_REQUIRED,
			_toNullableString(sp.getAssertionSignatureRequired()),
			samlProviderConfiguration::assertionSignatureRequired);
		_setProperty(
			unicodeProperties, PortletPropsKeys.SAML_SP_CLOCK_SKEW,
			_toNullableString(sp.getClockSkew()),
			samlProviderConfiguration::clockSkew);
		_setProperty(
			unicodeProperties, PortletPropsKeys.SAML_SP_LDAP_IMPORT_ENABLED,
			_toNullableString(sp.getLdapImportEnabled()),
			samlProviderConfiguration::ldapImportEnabled);
		_setProperty(
			unicodeProperties,
			PortletPropsKeys.SAML_KEYSTORE_ENCRYPTION_CREDENTIAL_PASSWORD,
			_toNullableString(sp.getKeyStoreEncryptionCredentialPassword()),
			samlProviderConfiguration::keyStoreCredentialPassword);
		_setProperty(
			unicodeProperties, PortletPropsKeys.SAML_SP_SIGN_AUTHN_REQUEST,
			_toNullableString(sp.getSignAuthnRequest()),
			samlProviderConfiguration::signAuthnRequest);

		unicodeProperties.put(
			PortletPropsKeys.SAML_ROLE,
			SamlProviderConfigurationKeys.SAML_ROLE_SP);
	}

	private String _toNullableString(Object value) {
		if (value == null) {
			return null;
		}

		return String.valueOf(value);
	}

	private SamlProvider _updateSamlProvider(
			SamlProvider samlProvider,
			SamlProviderConfiguration samlProviderConfiguration)
		throws Exception {

		UnicodeProperties unicodeProperties = UnicodePropertiesBuilder.create(
			false
		).build();

		_setSamlProviderProperties(
			samlProvider, samlProviderConfiguration, unicodeProperties);

		String entityId = samlProvider.getEntityId();

		if (Validator.isNotNull(entityId)) {
			if (entityId.length() > 1024) {
				throw new EntityIdException(
					"EntityID too long (Max 1024 characters)");
			}
		}
		else {
			entityId = samlProviderConfiguration.entityId();
		}

		if (GetterUtil.getBoolean(samlProvider.getEnabled()) ||
			!Validator.isBlank(samlProvider.getKeyStoreCredentialPassword())) {

			_authenticateLocalEntityCertificate(
				GetterUtil.getString(
					samlProvider.getKeyStoreCredentialPassword(),
					samlProviderConfiguration.keyStoreCredentialPassword()),
				LocalEntityManager.CertificateUsage.SIGNING, entityId);
		}

		if (samlProvider.getIdp() != null) {
			if (!_validateIdpRoleSelection(
					samlProvider.getEnabled(), samlProviderConfiguration)) {

				throw new ConfigurationException(
					"The Identity Provider role has been disabled. It can be " +
						"re-enabled in system settings.");
			}

			if (samlProvider.getSp() != null) {
				throw new ConfigurationException(
					"Can only configure one of sp & idp roles");
			}

			_setIdpProperties(
				samlProvider.getIdp(), unicodeProperties,
				samlProviderConfiguration);
		}
		else if (samlProvider.getSp() != null) {
			_setSpProperties(
				entityId, samlProvider.getSp(), unicodeProperties,
				samlProviderConfiguration);
		}

		if (GetterUtil.getBoolean(samlProvider.getEnabled()) &&
			!_isValidRole(
				GetterUtil.get(
					unicodeProperties.get(PortletPropsKeys.SAML_ROLE),
					samlProviderConfiguration.role()))) {

			throw new ConfigurationException(
				"Cannot enable the provider without configuring its role");
		}

		_samlProviderConfigurationHelper.updateProperties(unicodeProperties);

		return getSamlProvider();
	}

	private boolean _validateIdpRoleSelection(
		boolean enabled, SamlProviderConfiguration samlProviderConfiguration) {

		if (_samlConfiguration.idpRoleConfigurationEnabled()) {
			return true;
		}

		String role = samlProviderConfiguration.role();

		if (Validator.isNull(role) ||
			!role.equals(SamlProviderConfigurationKeys.SAML_ROLE_IDP)) {

			return false;
		}

		if (!samlProviderConfiguration.enabled() && enabled) {
			return false;
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SamlProviderResourceImpl.class);

	@Reference
	private LocalEntityManager _localEntityManager;

	private SamlConfiguration _samlConfiguration;

	@Reference
	private SamlProviderConfigurationHelper _samlProviderConfigurationHelper;

	private ServiceRegistration<?> _serviceRegistration;
	private final Dictionary<String, ?> _systemProperties =
		new HashMapDictionary<>();
	private SamlProviderConfiguration _systemSamlProviderConfiguration =
		ConfigurableUtil.createConfigurable(
			SamlProviderConfiguration.class, Collections.emptyMap());

	private class SystemConfigurationManagedServiceFactory
		implements ManagedServiceFactory {

		@Override
		public void deleted(String pid) {
			if ((_systemProperties != null) &&
				Objects.equals(
					_systemProperties.get(Constants.SERVICE_PID), pid)) {

				_systemSamlProviderConfiguration =
					ConfigurableUtil.createConfigurable(
						SamlProviderConfiguration.class,
						Collections.emptyMap());
			}
		}

		@Override
		public String getName() {
			return SystemConfigurationManagedServiceFactory.class.getName();
		}

		@Override
		public void updated(String pid, Dictionary<String, ?> properties) {
			if (GetterUtil.getLong(properties.get("companyId")) ==
					CompanyConstants.SYSTEM) {

				_systemSamlProviderConfiguration =
					ConfigurableUtil.createConfigurable(
						SamlProviderConfiguration.class, properties);
			}
		}

	}

}