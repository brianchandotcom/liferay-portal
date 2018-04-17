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

package com.liferay.saml.opensaml.integration.internal.metadata;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.opensaml.integration.internal.provider.CachingChainingMetadataProvider;
import com.liferay.saml.opensaml.integration.internal.util.OpenSamlUtil;
import com.liferay.saml.opensaml.integration.metadata.MetadataManager;
import com.liferay.saml.persistence.model.SamlIdpSpConnection;
import com.liferay.saml.persistence.model.SamlSpIdpConnection;
import com.liferay.saml.persistence.service.SamlIdpSpConnectionLocalService;
import com.liferay.saml.persistence.service.SamlSpIdpConnectionLocalService;
import com.liferay.saml.runtime.SamlException;
import com.liferay.saml.runtime.configuration.SamlProviderConfiguration;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.runtime.metadata.LocalEntityManager;
import com.liferay.saml.util.SamlHttpRequestUtil;

import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.xml.security.utils.Base64;

import org.opensaml.Configuration;
import org.opensaml.common.binding.security.SAMLProtocolMessageXMLSignatureSecurityPolicyRule;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.binding.security.SAML2HTTPPostSimpleSignRule;
import org.opensaml.saml2.binding.security.SAML2HTTPRedirectDeflateSignatureRule;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.IDPSSODescriptor;
import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.opensaml.security.MetadataCredentialResolver;
import org.opensaml.ws.security.SecurityPolicy;
import org.opensaml.ws.security.SecurityPolicyResolver;
import org.opensaml.ws.security.SecurityPolicyRule;
import org.opensaml.ws.security.provider.BasicSecurityPolicy;
import org.opensaml.ws.security.provider.HTTPRule;
import org.opensaml.ws.security.provider.MandatoryAuthenticatedMessageRule;
import org.opensaml.ws.security.provider.MandatoryIssuerRule;
import org.opensaml.ws.security.provider.StaticSecurityPolicyResolver;
import org.opensaml.xml.parse.ParserPool;
import org.opensaml.xml.security.CriteriaSet;
import org.opensaml.xml.security.SecurityConfiguration;
import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.credential.CredentialResolver;
import org.opensaml.xml.security.criteria.EntityIDCriteria;
import org.opensaml.xml.security.keyinfo.KeyInfoCredentialResolver;
import org.opensaml.xml.security.x509.X509Credential;
import org.opensaml.xml.signature.SignatureTrustEngine;
import org.opensaml.xml.signature.impl.ChainingSignatureTrustEngine;
import org.opensaml.xml.signature.impl.ExplicitKeySignatureTrustEngine;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Mika Koivisto
 */
@Component(
	immediate = true,
	service = {
		LocalEntityManager.class, MetadataManager.class,
		SamlHttpRequestUtil.class
	}
)
public class MetadataManagerImpl
	implements LocalEntityManager, MetadataManager, SamlHttpRequestUtil {

	@Override
	public int getAssertionLifetime(String entityId) {
		long companyId = CompanyThreadLocal.getCompanyId();

		try {
			SamlIdpSpConnection samlIdpSpConnection =
				_samlIdpSpConnectionLocalService.getSamlIdpSpConnection(
					companyId, entityId);

			return samlIdpSpConnection.getAssertionLifetime();
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		SamlProviderConfiguration samlProviderConfiguration =
			_samlProviderConfigurationHelper.getSamlProviderConfiguration();

		return samlProviderConfiguration.defaultAssertionLifetime();
	}

	@Override
	public String[] getAttributeNames(String entityId) {
		long companyId = CompanyThreadLocal.getCompanyId();

		try {
			SamlIdpSpConnection samlIdpSpConnection =
				_samlIdpSpConnectionLocalService.getSamlIdpSpConnection(
					companyId, entityId);

			return StringUtil.splitLines(
				samlIdpSpConnection.getAttributeNames());
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return null;
	}

	@Override
	public long getClockSkew() {
		return getSamlProviderConfiguration().clockSkew();
	}

	@Override
	public String getDefaultIdpEntityId() {
		return getSamlProviderConfiguration().defaultIdPEntityId();
	}

	@Override
	public String getEncodedLocalEntityCertificate() throws SamlException {
		try {
			X509Certificate x509Certificate = getLocalEntityCertificate();

			if (x509Certificate == null) {
				return null;
			}

			return Base64.encode(x509Certificate.getEncoded(), 76);
		}
		catch (CertificateEncodingException cee) {
			throw new SamlException(cee);
		}
	}

	@Override
	public EntityDescriptor getEntityDescriptor(HttpServletRequest request)
		throws SamlException {

		try {
			if (_samlProviderConfigurationHelper.isRoleIdp()) {
				return MetadataGeneratorUtil.buildIdpEntityDescriptor(
					request, getLocalEntityId(), isWantAuthnRequestSigned(),
					isSignMetadata(), isSSLRequired(), getSigningCredential());
			}
			else if (_samlProviderConfigurationHelper.isRoleSp()) {
				return MetadataGeneratorUtil.buildSpEntityDescriptor(
					request, getLocalEntityId(), isSignAuthnRequest(),
					isSignMetadata(), isSSLRequired(), isWantAssertionsSigned(),
					getSigningCredential());
			}

			return null;
		}
		catch (Exception e) {
			throw new SamlException(e);
		}
	}

	@Override
	public String getEntityDescriptorString(HttpServletRequest request)
		throws SamlException {

		try {
			EntityDescriptor entityDescriptor = getEntityDescriptor(request);

			return OpenSamlUtil.marshall(entityDescriptor);
		}
		catch (Exception e) {
			throw new SamlException(e);
		}
	}

	@Override
	public X509Certificate getLocalEntityCertificate() throws SamlException {
		X509Credential x509Credential = (X509Credential)getSigningCredential();

		X509Certificate x509Certificate = null;

		if (x509Credential != null) {
			x509Certificate = x509Credential.getEntityCertificate();
		}

		return x509Certificate;
	}

	@Override
	public String getLocalEntityId() {
		return getSamlProviderConfiguration().entityId();
	}

	@Override
	public MetadataProvider getMetadataProvider() throws SamlException {
		return _cachingChainingMetadataProvider;
	}

	@Override
	public String getNameIdAttribute(String entityId) {
		long companyId = CompanyThreadLocal.getCompanyId();

		String nameIdAttributeName = StringPool.BLANK;

		try {
			SamlIdpSpConnection samlIdpSpConnection =
				_samlIdpSpConnectionLocalService.getSamlIdpSpConnection(
					companyId, entityId);

			nameIdAttributeName = samlIdpSpConnection.getNameIdAttribute();
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		if (Validator.isNotNull(nameIdAttributeName)) {
			return nameIdAttributeName;
		}

		return "emailAddress";
	}

	@Override
	public String getNameIdFormat(String entityId) {
		long companyId = CompanyThreadLocal.getCompanyId();

		if (_samlProviderConfigurationHelper.isRoleIdp()) {
			try {
				SamlIdpSpConnection samlIdpSpConnection =
					_samlIdpSpConnectionLocalService.getSamlIdpSpConnection(
						companyId, entityId);

				return samlIdpSpConnection.getNameIdFormat();
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(e, e);
				}
			}
		}
		else if (_samlProviderConfigurationHelper.isRoleSp()) {
			try {
				SamlSpIdpConnection samlSpIdpConnection =
					_samlSpIdpConnectionLocalService.getSamlSpIdpConnection(
						companyId, entityId);

				return samlSpIdpConnection.getNameIdFormat();
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(e, e);
				}
			}
		}

		return null;
	}

	@Override
	public String getRequestPath(HttpServletRequest request) {
		String requestURI = request.getRequestURI();

		String contextPath = request.getContextPath();

		if (Validator.isNotNull(contextPath) &&
			!contextPath.equals(StringPool.SLASH)) {

			requestURI = requestURI.substring(contextPath.length());
		}

		return _http.removePathParameters(requestURI);
	}

	@Override
	public SecurityPolicyResolver getSecurityPolicyResolver(
			String communicationProfileId, boolean requireSignature)
		throws SamlException {

		SecurityPolicy securityPolicy = new BasicSecurityPolicy();

		List<SecurityPolicyRule> securityPolicyRules =
			securityPolicy.getPolicyRules();

		if (requireSignature) {
			SignatureTrustEngine signatureTrustEngine =
				getSignatureTrustEngine();

			if (communicationProfileId.equals(
					SAMLConstants.SAML2_REDIRECT_BINDING_URI)) {

				SecurityPolicyRule securityPolicyRule =
					new SAML2HTTPRedirectDeflateSignatureRule(
						signatureTrustEngine);

				securityPolicyRules.add(securityPolicyRule);
			}
			else if (communicationProfileId.equals(
						SAMLConstants.SAML2_POST_SIMPLE_SIGN_BINDING_URI)) {

				SecurityConfiguration securityConfiguration =
					Configuration.getGlobalSecurityConfiguration();

				KeyInfoCredentialResolver keyInfoCredentialResolver =
					securityConfiguration.getDefaultKeyInfoCredentialResolver();

				SecurityPolicyRule securityPolicyRule =
					new SAML2HTTPPostSimpleSignRule(
						signatureTrustEngine, _parserPool,
						keyInfoCredentialResolver);

				securityPolicyRules.add(securityPolicyRule);
			}
			else {
				SecurityPolicyRule securityPolicyRule =
					new SAMLProtocolMessageXMLSignatureSecurityPolicyRule(
						signatureTrustEngine);

				securityPolicyRules.add(securityPolicyRule);
			}

			MandatoryAuthenticatedMessageRule
				mandatoryAuthenticatedMessageRule =
					new MandatoryAuthenticatedMessageRule();

			securityPolicyRules.add(mandatoryAuthenticatedMessageRule);
		}

		HTTPRule httpRule = new HTTPRule(null, null, isSSLRequired());

		securityPolicyRules.add(httpRule);

		MandatoryIssuerRule mandatoryIssuerRule = new MandatoryIssuerRule();

		securityPolicyRules.add(mandatoryIssuerRule);

		StaticSecurityPolicyResolver securityPolicyResolver =
			new StaticSecurityPolicyResolver(securityPolicy);

		return securityPolicyResolver;
	}

	@Override
	public SignatureTrustEngine getSignatureTrustEngine() throws SamlException {
		ChainingSignatureTrustEngine chainingSignatureTrustEngine =
			new ChainingSignatureTrustEngine();

		List<SignatureTrustEngine> signatureTrustEngines =
			chainingSignatureTrustEngine.getChain();

		MetadataCredentialResolver metadataCredentialResolver =
			new MetadataCredentialResolver(getMetadataProvider());

		SecurityConfiguration securityConfiguration =
			Configuration.getGlobalSecurityConfiguration();

		KeyInfoCredentialResolver keyInfoCredentialResolver =
			securityConfiguration.getDefaultKeyInfoCredentialResolver();

		SignatureTrustEngine signatureTrustEngine =
			new ExplicitKeySignatureTrustEngine(
				metadataCredentialResolver, keyInfoCredentialResolver);

		signatureTrustEngines.add(signatureTrustEngine);

		signatureTrustEngine = new ExplicitKeySignatureTrustEngine(
			_credentialResolver, keyInfoCredentialResolver);

		signatureTrustEngines.add(signatureTrustEngine);

		return chainingSignatureTrustEngine;
	}

	@Override
	public Credential getSigningCredential() throws SamlException {
		try {
			CriteriaSet criteriaSet = new CriteriaSet();

			String entityId = getLocalEntityId();

			if (Validator.isNull(entityId)) {
				return null;
			}

			EntityIDCriteria entityIDCriteria = new EntityIDCriteria(entityId);

			criteriaSet.add(entityIDCriteria);

			return _credentialResolver.resolveSingle(criteriaSet);
		}
		catch (SecurityException se) {
			throw new SamlException(se);
		}
	}

	@Override
	public String getUserAttributeMappings(String entityId) {
		long companyId = CompanyThreadLocal.getCompanyId();

		try {
			SamlSpIdpConnection samlSpIdpConnection =
				_samlSpIdpConnectionLocalService.getSamlSpIdpConnection(
					companyId, entityId);

			return samlSpIdpConnection.getUserAttributeMappings();
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return null;
	}

	@Override
	public boolean hasDefaultIdpRole() {
		String defaultIdpEntityId = getDefaultIdpEntityId();

		try {
			MetadataProvider metadataProvider = getMetadataProvider();

			if (Validator.isNull(defaultIdpEntityId) ||
				(metadataProvider.getRole(
					defaultIdpEntityId, IDPSSODescriptor.DEFAULT_ELEMENT_NAME,
					SAMLConstants.SAML20P_NS) == null)) {

				return false;
			}

			return true;
		}
		catch (MetadataProviderException | SamlException e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Error retrieving metadata information", e);
			}

			return false;
		}
	}

	@Override
	public boolean isAttributesEnabled(String entityId) {
		long companyId = CompanyThreadLocal.getCompanyId();

		try {
			SamlIdpSpConnection samlIdpSpConnection =
				_samlIdpSpConnectionLocalService.getSamlIdpSpConnection(
					companyId, entityId);

			return samlIdpSpConnection.isAttributesEnabled();
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return false;
	}

	@Override
	public boolean isAttributesNamespaceEnabled(String entityId) {
		long companyId = CompanyThreadLocal.getCompanyId();

		try {
			SamlIdpSpConnection samlIdpSpConnection =
				_samlIdpSpConnectionLocalService.getSamlIdpSpConnection(
					companyId, entityId);

			return samlIdpSpConnection.isAttributesNamespaceEnabled();
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return false;
	}

	@Reference(unbind = "-")
	public void setCredentialResolver(CredentialResolver credentialResolver) {
		_credentialResolver = credentialResolver;
	}

	@Reference(unbind = "-")
	public void setHttp(Http http) {
		_http = http;
	}

	@Reference(
		cardinality = ReferenceCardinality.AT_LEAST_ONE,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "unsetMetadataProvider"
	)
	public void setMetadataProvider(MetadataProvider metadataProvider) {
		if (_log.isDebugEnabled()) {
			_log.debug("Adding metadata provider " + metadataProvider);
		}

		_cachingChainingMetadataProvider.addMetadataProvider(metadataProvider);
	}

	@Reference(unbind = "-")
	public void setParserPool(ParserPool parserPool) {
		_parserPool = parserPool;
	}

	@Reference(unbind = "-")
	public void setSamlProviderConfigurationHelper(
		SamlProviderConfigurationHelper samlProviderConfigurationHelper) {

		_samlProviderConfigurationHelper = samlProviderConfigurationHelper;
	}

	@Deactivate
	public void shutdown() {
		_cachingChainingMetadataProvider.destroy();
	}

	public void unsetMetadataProvider(MetadataProvider metadataProvider) {
		if (_log.isDebugEnabled()) {
			_log.debug("Removing metadata provider " + metadataProvider);
		}

		_cachingChainingMetadataProvider.removeMetadataProvider(
			metadataProvider);
	}

	protected SamlProviderConfiguration getSamlProviderConfiguration() {
		return _samlProviderConfigurationHelper.getSamlProviderConfiguration();
	}

	protected boolean isSignAuthnRequest() {
		return getSamlProviderConfiguration().signAuthnRequest();
	}

	protected boolean isSignMetadata() {
		return getSamlProviderConfiguration().signMetadata();
	}

	protected boolean isSSLRequired() {
		return getSamlProviderConfiguration().sslRequired();
	}

	protected boolean isWantAssertionsSigned() {
		return getSamlProviderConfiguration().assertionSignatureRequired();
	}

	protected boolean isWantAuthnRequestSigned() {
		return getSamlProviderConfiguration().authnRequestSignatureRequired();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MetadataManagerImpl.class);

	private final CachingChainingMetadataProvider
		_cachingChainingMetadataProvider =
			new CachingChainingMetadataProvider();
	private CredentialResolver _credentialResolver;
	private Http _http;
	private ParserPool _parserPool;

	@Reference
	private SamlIdpSpConnectionLocalService _samlIdpSpConnectionLocalService;

	private SamlProviderConfigurationHelper _samlProviderConfigurationHelper;

	@Reference
	private SamlSpIdpConnectionLocalService _samlSpIdpConnectionLocalService;

}