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

package com.liferay.saml.opensaml.integration.internal.metadata;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.opensaml.integration.internal.bootstrap.SecurityConfigurationBootstrap;
import com.liferay.saml.opensaml.integration.internal.provider.CachingChainingMetadataResolver;
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

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import net.shibboleth.utilities.java.support.xml.ParserPool;

import org.apache.xml.security.utils.Base64;

import org.opensaml.core.config.ConfigurationService;
import org.opensaml.core.criterion.EntityIdCriterion;
import org.opensaml.messaging.handler.MessageHandler;
import org.opensaml.messaging.handler.impl.BasicMessageHandlerChain;
import org.opensaml.messaging.handler.impl.CheckMandatoryAuthentication;
import org.opensaml.messaging.handler.impl.CheckMandatoryIssuer;
import org.opensaml.messaging.handler.impl.HTTPRequestValidationHandler;
import org.opensaml.saml.common.binding.security.impl.SAMLProtocolMessageXMLSignatureSecurityHandler;
import org.opensaml.saml.common.messaging.context.navigate.SAMLMessageContextAuthenticationFunction;
import org.opensaml.saml.common.messaging.context.navigate.SAMLMessageContextIssuerFunction;
import org.opensaml.saml.common.xml.SAMLConstants;
import org.opensaml.saml.criterion.EntityRoleCriterion;
import org.opensaml.saml.criterion.ProtocolCriterion;
import org.opensaml.saml.metadata.resolver.MetadataResolver;
import org.opensaml.saml.metadata.resolver.impl.PredicateRoleDescriptorResolver;
import org.opensaml.saml.saml2.binding.security.impl.SAML2HTTPPostSimpleSignSecurityHandler;
import org.opensaml.saml.saml2.binding.security.impl.SAML2HTTPRedirectDeflateSignatureSecurityHandler;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.IDPSSODescriptor;
import org.opensaml.saml.security.impl.MetadataCredentialResolver;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.CredentialResolver;
import org.opensaml.security.x509.X509Credential;
import org.opensaml.xmlsec.DecryptionConfiguration;
import org.opensaml.xmlsec.SecurityConfigurationSupport;
import org.opensaml.xmlsec.SignatureValidationConfiguration;
import org.opensaml.xmlsec.config.DefaultSecurityConfigurationBootstrap;
import org.opensaml.xmlsec.impl.BasicSignatureValidationConfiguration;
import org.opensaml.xmlsec.keyinfo.KeyInfoCredentialResolver;
import org.opensaml.xmlsec.signature.support.SignatureTrustEngine;
import org.opensaml.xmlsec.signature.support.impl.ChainingSignatureTrustEngine;
import org.opensaml.xmlsec.signature.support.impl.ExplicitKeySignatureTrustEngine;

import org.osgi.service.component.annotations.Activate;
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

	@Activate
	public void activate()
		throws ComponentInitializationException, SamlException {

		_cachingChainingMetadataResolver.setId(
			CachingChainingMetadataResolver.class.getName());
		_cachingChainingMetadataResolver.setParserPool(_parserPool);

		_cachingChainingMetadataResolver.initialize();

		SignatureValidationConfiguration signatureValidationConfiguration =
			ConfigurationService.get(SignatureValidationConfiguration.class);

		if (signatureValidationConfiguration instanceof
				BasicSignatureValidationConfiguration) {

			BasicSignatureValidationConfiguration
				basicSignatureValidationConfiguration =
					(BasicSignatureValidationConfiguration)
						signatureValidationConfiguration;

			basicSignatureValidationConfiguration.setSignatureTrustEngine(
				getSignatureTrustEngine());
		}
	}

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
	public MetadataResolver getMetadataResolver() throws SamlException {
		return _cachingChainingMetadataResolver;
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
	public MessageHandler<?> getSecurityMessageHandler(
		HttpServletRequest request, String communicationProfileId,
		boolean requireSignature) {

		BasicMessageHandlerChain<Object> basicMessageHandlerChain =
			new BasicMessageHandlerChain<>();

		List<MessageHandler<Object>> messageHandlers = new ArrayList<>();

		if (requireSignature) {
			if (communicationProfileId.equals(
					SAMLConstants.SAML2_REDIRECT_BINDING_URI)) {

				SAML2HTTPRedirectDeflateSignatureSecurityHandler
					saml2HTTPRedirectDeflateSignatureSecurityHandler =
						new SAML2HTTPRedirectDeflateSignatureSecurityHandler();

				saml2HTTPRedirectDeflateSignatureSecurityHandler.
					setHttpServletRequest(request);

				messageHandlers.add(
					saml2HTTPRedirectDeflateSignatureSecurityHandler);
			}
			else if (communicationProfileId.equals(
						SAMLConstants.SAML2_POST_SIMPLE_SIGN_BINDING_URI)) {

				DecryptionConfiguration decryptionConfiguration =
					SecurityConfigurationSupport.
						getGlobalDecryptionConfiguration();

				KeyInfoCredentialResolver keyInfoCredentialResolver =
					decryptionConfiguration.getDataKeyInfoCredentialResolver();

				SAML2HTTPPostSimpleSignSecurityHandler
					saml2HTTPPostSimpleSignSecurityHandler =
						new SAML2HTTPPostSimpleSignSecurityHandler();

				saml2HTTPPostSimpleSignSecurityHandler.setKeyInfoResolver(
					keyInfoCredentialResolver);
				saml2HTTPPostSimpleSignSecurityHandler.setParser(_parserPool);

				messageHandlers.add(saml2HTTPPostSimpleSignSecurityHandler);
			}
			else {
				messageHandlers.add(
					new SAMLProtocolMessageXMLSignatureSecurityHandler());
			}

			CheckMandatoryAuthentication checkMandatoryAuthentication =
				new CheckMandatoryAuthentication();

			checkMandatoryAuthentication.setAuthenticationLookupStrategy(
				new SAMLMessageContextAuthenticationFunction());

			messageHandlers.add(checkMandatoryAuthentication);
		}

		CheckMandatoryIssuer checkMandatoryIssuer = new CheckMandatoryIssuer();

		checkMandatoryIssuer.setIssuerLookupStrategy(
			new SAMLMessageContextIssuerFunction());

		messageHandlers.add(checkMandatoryIssuer);

		HTTPRequestValidationHandler httpRequestValidationHandler =
			new HTTPRequestValidationHandler();

		httpRequestValidationHandler.setHttpServletRequest(request);
		httpRequestValidationHandler.setRequireSecured(isSSLRequired());

		messageHandlers.add(httpRequestValidationHandler);

		basicMessageHandlerChain.setHandlers(messageHandlers);

		return basicMessageHandlerChain;
	}

	@Override
	public SignatureTrustEngine getSignatureTrustEngine() throws SamlException {
		List<SignatureTrustEngine> signatureTrustEngines = new ArrayList<>();

		MetadataCredentialResolver metadataCredentialResolver =
			new MetadataCredentialResolver();

		KeyInfoCredentialResolver keyInfoCredentialResolver =
			DefaultSecurityConfigurationBootstrap.
				buildBasicInlineKeyInfoCredentialResolver();

		metadataCredentialResolver.setKeyInfoCredentialResolver(
			keyInfoCredentialResolver);

		PredicateRoleDescriptorResolver predicateRoleDescriptorResolver =
			new PredicateRoleDescriptorResolver(getMetadataResolver());

		metadataCredentialResolver.setRoleDescriptorResolver(
			predicateRoleDescriptorResolver);

		try {
			metadataCredentialResolver.initialize();
			predicateRoleDescriptorResolver.initialize();
		}
		catch (ComponentInitializationException cie) {
			throw new SamlException(cie);
		}

		SignatureTrustEngine signatureTrustEngine =
			new ExplicitKeySignatureTrustEngine(
				metadataCredentialResolver, keyInfoCredentialResolver);

		signatureTrustEngines.add(signatureTrustEngine);

		signatureTrustEngine = new ExplicitKeySignatureTrustEngine(
			_credentialResolver, keyInfoCredentialResolver);

		signatureTrustEngines.add(signatureTrustEngine);

		return new ChainingSignatureTrustEngine(signatureTrustEngines);
	}

	@Override
	public Credential getSigningCredential() throws SamlException {
		try {
			String entityId = getLocalEntityId();

			if (Validator.isNull(entityId)) {
				return null;
			}

			CriteriaSet criteriaSet = new CriteriaSet();

			EntityIdCriterion entityIdCriterion = new EntityIdCriterion(
				entityId);

			criteriaSet.add(entityIdCriterion);

			return _credentialResolver.resolveSingle(criteriaSet);
		}
		catch (ResolverException re) {
			throw new SamlException(re);
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

		if (Validator.isNull(defaultIdpEntityId)) {
			return false;
		}

		try {
			MetadataResolver metadataResolver = getMetadataResolver();

			EntityDescriptor entityDescriptor = metadataResolver.resolveSingle(
				new CriteriaSet(
					new EntityIdCriterion(defaultIdpEntityId),
					new EntityRoleCriterion(
						IDPSSODescriptor.DEFAULT_ELEMENT_NAME),
					new ProtocolCriterion(SAMLConstants.SAML20P_NS)));

			if (entityDescriptor == null) {
				return false;
			}

			return true;
		}
		catch (ResolverException | SamlException e) {
			String message =
				"Error retrieving metadata information: " + e.getMessage();

			if (_log.isDebugEnabled()) {
				_log.debug(message, e);
			}
			else if (_log.isWarnEnabled()) {
				_log.warn(message);
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
		unbind = "unsetMetadataResolver"
	)
	public void setMetadataResolver(MetadataResolver metadataResolver) {
		if (_log.isDebugEnabled()) {
			_log.debug("Adding metadata resolver " + metadataResolver);
		}

		_cachingChainingMetadataResolver.addMetadataResolver(metadataResolver);
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
		_cachingChainingMetadataResolver.destroy();
	}

	public void unsetMetadataResolver(MetadataResolver metadataResolver) {
		if (_log.isDebugEnabled()) {
			_log.debug("Removing metadata resolver " + metadataResolver);
		}

		_cachingChainingMetadataResolver.removeMetadataResolver(
			metadataResolver);
	}

	@Deactivate
	protected void deactivate() {
		_cachingChainingMetadataResolver.destroy();
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

	private final CachingChainingMetadataResolver
		_cachingChainingMetadataResolver =
			new CachingChainingMetadataResolver();
	private CredentialResolver _credentialResolver;
	private Http _http;
	private ParserPool _parserPool;

	@Reference
	private SamlIdpSpConnectionLocalService _samlIdpSpConnectionLocalService;

	private SamlProviderConfigurationHelper _samlProviderConfigurationHelper;

	@Reference
	private SamlSpIdpConnectionLocalService _samlSpIdpConnectionLocalService;

	@Reference
	private SecurityConfigurationBootstrap _securityComponent;

}