/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.internal.metadata;

import com.liferay.petra.concurrent.DCLSingleton;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.opensaml.integration.internal.bootstrap.ParserPoolUtil;
import com.liferay.saml.opensaml.integration.internal.provider.CachingChainingMetadataResolver;
import com.liferay.saml.opensaml.integration.internal.provider.DBMetadataResolver;
import com.liferay.saml.persistence.model.SamlIdpSpConnection;
import com.liferay.saml.persistence.model.SamlSpIdpConnection;
import com.liferay.saml.persistence.service.SamlIdpSpConnectionLocalService;
import com.liferay.saml.persistence.service.SamlSpIdpConnectionLocalService;
import com.liferay.saml.runtime.SamlException;
import com.liferay.saml.runtime.configuration.SamlProviderConfiguration;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.runtime.metadata.LocalEntityManager;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import net.shibboleth.utilities.java.support.xml.ParserPool;

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
import org.opensaml.saml.metadata.resolver.MetadataResolver;
import org.opensaml.saml.metadata.resolver.impl.PredicateRoleDescriptorResolver;
import org.opensaml.saml.saml2.binding.security.impl.SAML2HTTPPostSimpleSignSecurityHandler;
import org.opensaml.saml.saml2.binding.security.impl.SAML2HTTPRedirectDeflateSignatureSecurityHandler;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.security.impl.MetadataCredentialResolver;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.CredentialResolver;
import org.opensaml.security.credential.UsageType;
import org.opensaml.security.criteria.UsageCriterion;
import org.opensaml.xmlsec.DecryptionConfiguration;
import org.opensaml.xmlsec.SecurityConfigurationSupport;
import org.opensaml.xmlsec.config.DefaultSecurityConfigurationBootstrap;
import org.opensaml.xmlsec.keyinfo.KeyInfoCredentialResolver;
import org.opensaml.xmlsec.signature.support.SignatureTrustEngine;
import org.opensaml.xmlsec.signature.support.impl.ChainingSignatureTrustEngine;
import org.opensaml.xmlsec.signature.support.impl.ExplicitKeySignatureTrustEngine;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 */
@Component(service = MetadataManager.class)
public class MetadataManagerImpl implements MetadataManager {

	@Override
	public int getAssertionLifetime(String entityId) {
		long companyId = CompanyThreadLocal.getCompanyId();

		try {
			SamlIdpSpConnection samlIdpSpConnection =
				_samlIdpSpConnectionLocalService.getSamlIdpSpConnection(
					companyId, entityId);

			return samlIdpSpConnection.getAssertionLifetime();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
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
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return null;
	}

	@Override
	public long getClockSkew() {
		return _getSamlProviderConfiguration().clockSkew();
	}

	@Override
	public Credential getEncryptionCredential() throws SamlException {
		try {
			String entityId = _localEntityManager.getLocalEntityId();

			if (Validator.isNull(entityId)) {
				return null;
			}

			return _credentialResolver.resolveSingle(
				new CriteriaSet(
					new EntityIdCriterion(entityId),
					new UsageCriterion(UsageType.ENCRYPTION)));
		}
		catch (ResolverException resolverException) {
			throw new SamlException(resolverException);
		}
	}

	@Override
	public EntityDescriptor getEntityDescriptor(
			HttpServletRequest httpServletRequest)
		throws SamlException {

		Credential encryptionCredential = null;

		try {
			encryptionCredential = getEncryptionCredential();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to get encryption credential: " +
						exception.getMessage(),
					exception);
			}
		}

		try {
			String portalURL = _portal.getPortalURL(
				httpServletRequest,
				_isSSLRequired() || _portal.isSecure(httpServletRequest));
			String localEntityId = _localEntityManager.getLocalEntityId();

			if (_samlProviderConfigurationHelper.isRoleIdp()) {
				return MetadataGeneratorUtil.buildIdpEntityDescriptor(
					portalURL, localEntityId, _isWantAuthnRequestSigned(),
					_isSignMetadata(), getSigningCredential(),
					encryptionCredential);
			}
			else if (_samlProviderConfigurationHelper.isRoleSp()) {
				return MetadataGeneratorUtil.buildSpEntityDescriptor(
					portalURL, localEntityId, _isSignAuthnRequest(),
					_isSignMetadata(), _isWantAssertionsSigned(),
					getSigningCredential(), encryptionCredential);
			}

			return null;
		}
		catch (Exception exception) {
			throw new SamlException(exception);
		}
	}

	@Override
	public MetadataCredentialResolver getMetadataCredentialResolver() {
		return _metadataCredentialResolverDCLSingleton.getSingleton(
			this::_createMetadataCredentialResolver);
	}

	@Override
	public MetadataResolver getMetadataResolver() {
		return _cachingChainingMetadataResolverDCLSingleton.getSingleton(
			this::_createCachingChainingMetadataResolver);
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
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
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
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
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
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}
			}
		}

		return null;
	}

	@Override
	public MessageHandler<?> getSecurityMessageHandler(
		HttpServletRequest httpServletRequest, String communicationProfileId,
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
					setHttpServletRequest(httpServletRequest);

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
				saml2HTTPPostSimpleSignSecurityHandler.setParser(
					ParserPoolUtil.getParserPool());

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

		httpRequestValidationHandler.setHttpServletRequest(httpServletRequest);
		httpRequestValidationHandler.setRequireSecured(_isSSLRequired());

		messageHandlers.add(httpRequestValidationHandler);

		basicMessageHandlerChain.setHandlers(messageHandlers);

		return basicMessageHandlerChain;
	}

	@Override
	public SignatureTrustEngine getSignatureTrustEngine() throws SamlException {
		return _chainingSignatureTrustEngineDCLSingleton.getSingleton(
			this::_createChainingSignatureTrustEngine);
	}

	@Override
	public Credential getSigningCredential() throws SamlException {
		try {
			String entityId = _localEntityManager.getLocalEntityId();

			if (Validator.isNull(entityId)) {
				return null;
			}

			return _credentialResolver.resolveSingle(
				new CriteriaSet(
					new EntityIdCriterion(entityId),
					new UsageCriterion(UsageType.SIGNING)));
		}
		catch (ResolverException resolverException) {
			throw new SamlException(resolverException);
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
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return null;
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
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
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
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return false;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Deactivate
	protected void deactivate() {
		_predicateRoleDescriptorResolverDCLSingleton.destroy(
			PredicateRoleDescriptorResolver::destroy);

		_cachingChainingMetadataResolverDCLSingleton.destroy(
			CachingChainingMetadataResolver::destroy);
	}

	private CachingChainingMetadataResolver
		_createCachingChainingMetadataResolver() {

		CachingChainingMetadataResolver cachingChainingMetadataResolver =
			new CachingChainingMetadataResolver(_bundleContext);

		ParserPool parserPool = ParserPoolUtil.getParserPool();

		cachingChainingMetadataResolver.addMetadataResolver(
			new DBMetadataResolver(
				parserPool, _samlIdpSpConnectionLocalService,
				_samlProviderConfigurationHelper,
				_samlSpIdpConnectionLocalService));

		cachingChainingMetadataResolver.setId(
			CachingChainingMetadataResolver.class.getName());
		cachingChainingMetadataResolver.setParserPool(parserPool);

		try {
			cachingChainingMetadataResolver.initialize();
		}
		catch (ComponentInitializationException
					componentInitializationException) {

			throw new RuntimeException(componentInitializationException);
		}

		return cachingChainingMetadataResolver;
	}

	private ChainingSignatureTrustEngine _createChainingSignatureTrustEngine() {
		List<SignatureTrustEngine> signatureTrustEngines = new ArrayList<>();

		MetadataCredentialResolver metadataCredentialResolver =
			_metadataCredentialResolverDCLSingleton.getSingleton(
				this::_createMetadataCredentialResolver);

		KeyInfoCredentialResolver keyInfoCredentialResolver =
			metadataCredentialResolver.getKeyInfoCredentialResolver();

		SignatureTrustEngine signatureTrustEngine =
			new ExplicitKeySignatureTrustEngine(
				metadataCredentialResolver, keyInfoCredentialResolver);

		signatureTrustEngines.add(signatureTrustEngine);

		signatureTrustEngine = new ExplicitKeySignatureTrustEngine(
			_credentialResolver, keyInfoCredentialResolver);

		signatureTrustEngines.add(signatureTrustEngine);

		return new ChainingSignatureTrustEngine(signatureTrustEngines);
	}

	private MetadataCredentialResolver _createMetadataCredentialResolver() {
		MetadataCredentialResolver metadataCredentialResolver =
			new MetadataCredentialResolver();

		metadataCredentialResolver.setKeyInfoCredentialResolver(
			DefaultSecurityConfigurationBootstrap.
				buildBasicInlineKeyInfoCredentialResolver());
		metadataCredentialResolver.setRoleDescriptorResolver(
			_predicateRoleDescriptorResolverDCLSingleton.getSingleton(
				this::_createPredicateRoleDescriptorResolver));

		try {
			metadataCredentialResolver.initialize();
		}
		catch (ComponentInitializationException
					componentInitializationException) {

			throw new RuntimeException(componentInitializationException);
		}

		return metadataCredentialResolver;
	}

	private PredicateRoleDescriptorResolver
		_createPredicateRoleDescriptorResolver() {

		PredicateRoleDescriptorResolver predicateRoleDescriptorResolver =
			new PredicateRoleDescriptorResolver(
				_cachingChainingMetadataResolverDCLSingleton.getSingleton(
					this::_createCachingChainingMetadataResolver));

		try {
			predicateRoleDescriptorResolver.initialize();
		}
		catch (ComponentInitializationException
					componentInitializationException) {

			throw new RuntimeException(componentInitializationException);
		}

		return predicateRoleDescriptorResolver;
	}

	private SamlProviderConfiguration _getSamlProviderConfiguration() {
		return _samlProviderConfigurationHelper.getSamlProviderConfiguration();
	}

	private boolean _isSignAuthnRequest() {
		return _getSamlProviderConfiguration().signAuthnRequest();
	}

	private boolean _isSignMetadata() {
		return _getSamlProviderConfiguration().signMetadata();
	}

	private boolean _isSSLRequired() {
		return _getSamlProviderConfiguration().sslRequired();
	}

	private boolean _isWantAssertionsSigned() {
		return _getSamlProviderConfiguration().assertionSignatureRequired();
	}

	private boolean _isWantAuthnRequestSigned() {
		return _getSamlProviderConfiguration().authnRequestSignatureRequired();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MetadataManagerImpl.class);

	private BundleContext _bundleContext;
	private final DCLSingleton<CachingChainingMetadataResolver>
		_cachingChainingMetadataResolverDCLSingleton = new DCLSingleton<>();
	private final DCLSingleton<ChainingSignatureTrustEngine>
		_chainingSignatureTrustEngineDCLSingleton = new DCLSingleton<>();

	@Reference
	private CredentialResolver _credentialResolver;

	@Reference
	private LocalEntityManager _localEntityManager;

	private final DCLSingleton<MetadataCredentialResolver>
		_metadataCredentialResolverDCLSingleton = new DCLSingleton<>();

	@Reference
	private Portal _portal;

	private final DCLSingleton<PredicateRoleDescriptorResolver>
		_predicateRoleDescriptorResolverDCLSingleton = new DCLSingleton<>();

	@Reference
	private SamlIdpSpConnectionLocalService _samlIdpSpConnectionLocalService;

	@Reference
	private SamlProviderConfigurationHelper _samlProviderConfigurationHelper;

	@Reference
	private SamlSpIdpConnectionLocalService _samlSpIdpConnectionLocalService;

}