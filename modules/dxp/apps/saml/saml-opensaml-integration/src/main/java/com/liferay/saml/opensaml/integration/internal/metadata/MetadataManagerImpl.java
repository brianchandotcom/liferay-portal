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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.opensaml.integration.internal.bootstrap.SecurityConfigurationBootstrap;
import com.liferay.saml.opensaml.integration.internal.provider.CachingChainingMetadataResolver;
import com.liferay.saml.opensaml.integration.internal.util.OpenSamlUtil;
import com.liferay.saml.persistence.model.SamlIdpSpConnection;
import com.liferay.saml.persistence.model.SamlSpIdpConnection;
import com.liferay.saml.persistence.service.SamlIdpSpConnectionLocalService;
import com.liferay.saml.persistence.service.SamlSpIdpConnectionLocalService;
import com.liferay.saml.runtime.SamlException;
import com.liferay.saml.runtime.configuration.SamlProviderConfiguration;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.runtime.metadata.LocalEntityManager;
import com.liferay.saml.util.SamlHttpRequestUtil;

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

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicyOption;
import org.osgi.service.component.annotations.ReferenceScope;

/**
 * @author Mika Koivisto
 */
@Component(
	immediate = true,
	service = {MetadataManager.class, SamlHttpRequestUtil.class}
)
public class MetadataManagerImpl
	implements MetadataManager, SamlHttpRequestUtil {

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
				_log.debug(exception, exception);
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
				_log.debug(exception, exception);
			}
		}

		return null;
	}

	@Override
	public long getClockSkew() {
		return getSamlProviderConfiguration().clockSkew();
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
				isSSLRequired() | _portal.isSecure(httpServletRequest));
			String localEntityId = _localEntityManager.getLocalEntityId();

			if (_samlProviderConfigurationHelper.isRoleIdp()) {
				return MetadataGeneratorUtil.buildIdpEntityDescriptor(
					portalURL, localEntityId, isWantAuthnRequestSigned(),
					isSignMetadata(), getSigningCredential(),
					encryptionCredential);
			}
			else if (_samlProviderConfigurationHelper.isRoleSp()) {
				return MetadataGeneratorUtil.buildSpEntityDescriptor(
					portalURL, localEntityId, isSignAuthnRequest(),
					isSignMetadata(), isWantAssertionsSigned(),
					getSigningCredential(), encryptionCredential);
			}

			return null;
		}
		catch (Exception exception) {
			throw new SamlException(exception);
		}
	}

	@Override
	public String getEntityDescriptorString(
			HttpServletRequest httpServletRequest)
		throws SamlException {

		try {
			return OpenSamlUtil.marshall(
				getEntityDescriptor(httpServletRequest));
		}
		catch (Exception exception) {
			throw new SamlException(exception);
		}
	}

	@Override
	public MetadataCredentialResolver getMetadataCredentialResolver() {
		return _metadataCredentialResolver;
	}

	@Override
	public MetadataResolver getMetadataResolver() {
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
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
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
					_log.debug(exception, exception);
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
					_log.debug(exception, exception);
				}
			}
		}

		return null;
	}

	@Override
	public String getRequestPath(HttpServletRequest httpServletRequest) {
		String requestURI = httpServletRequest.getRequestURI();

		String contextPath = httpServletRequest.getContextPath();

		if (Validator.isNotNull(contextPath) &&
			!contextPath.equals(StringPool.SLASH)) {

			requestURI = requestURI.substring(contextPath.length());
		}

		return _http.removePathParameters(requestURI);
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

		httpRequestValidationHandler.setHttpServletRequest(httpServletRequest);
		httpRequestValidationHandler.setRequireSecured(isSSLRequired());

		messageHandlers.add(httpRequestValidationHandler);

		basicMessageHandlerChain.setHandlers(messageHandlers);

		return basicMessageHandlerChain;
	}

	@Override
	public SignatureTrustEngine getSignatureTrustEngine() throws SamlException {
		return _chainingSignatureTrustEngine;
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
				_log.debug(exception, exception);
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
				_log.debug(exception, exception);
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
				_log.debug(exception, exception);
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

	@Reference(unbind = "-")
	public void setLocalEntityManager(LocalEntityManager localEntityManager) {
		_localEntityManager = localEntityManager;
	}

	@Reference(
		cardinality = ReferenceCardinality.AT_LEAST_ONE,
		policyOption = ReferencePolicyOption.GREEDY,
		scope = ReferenceScope.PROTOTYPE_REQUIRED
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
	public void setPortal(Portal portal) {
		_portal = portal;
	}

	@Reference(unbind = "-")
	public void setSamlProviderConfigurationHelper(
		SamlProviderConfigurationHelper samlProviderConfigurationHelper) {

		_samlProviderConfigurationHelper = samlProviderConfigurationHelper;
	}

	public void unsetMetadataResolver(MetadataResolver metadataResolver) {
		if (_log.isDebugEnabled()) {
			_log.debug("Removing metadata resolver " + metadataResolver);
		}

		_cachingChainingMetadataResolver.removeMetadataResolver(
			metadataResolver);
	}

	@Activate
	protected void activate() throws ComponentInitializationException {
		_cachingChainingMetadataResolver.setId(
			CachingChainingMetadataResolver.class.getName());
		_cachingChainingMetadataResolver.setParserPool(_parserPool);

		_cachingChainingMetadataResolver.initialize();

		_predicateRoleDescriptorResolver.initialize();

		KeyInfoCredentialResolver keyInfoCredentialResolver =
			DefaultSecurityConfigurationBootstrap.
				buildBasicInlineKeyInfoCredentialResolver();

		_metadataCredentialResolver = new MetadataCredentialResolver();

		_metadataCredentialResolver.setKeyInfoCredentialResolver(
			keyInfoCredentialResolver);
		_metadataCredentialResolver.setRoleDescriptorResolver(
			_predicateRoleDescriptorResolver);

		_metadataCredentialResolver.initialize();

		List<SignatureTrustEngine> signatureTrustEngines = new ArrayList<>();

		SignatureTrustEngine signatureTrustEngine =
			new ExplicitKeySignatureTrustEngine(
				_metadataCredentialResolver, keyInfoCredentialResolver);

		signatureTrustEngines.add(signatureTrustEngine);

		signatureTrustEngine = new ExplicitKeySignatureTrustEngine(
			_credentialResolver, keyInfoCredentialResolver);

		signatureTrustEngines.add(signatureTrustEngine);

		_chainingSignatureTrustEngine = new ChainingSignatureTrustEngine(
			signatureTrustEngines);
	}

	@Deactivate
	protected void deactivate() {
		_predicateRoleDescriptorResolver.destroy();

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
	private ChainingSignatureTrustEngine _chainingSignatureTrustEngine;
	private CredentialResolver _credentialResolver;
	private Http _http;
	private LocalEntityManager _localEntityManager;
	private MetadataCredentialResolver _metadataCredentialResolver;
	private ParserPool _parserPool;
	private Portal _portal;
	private final PredicateRoleDescriptorResolver
		_predicateRoleDescriptorResolver = new PredicateRoleDescriptorResolver(
			_cachingChainingMetadataResolver);

	@Reference
	private SamlIdpSpConnectionLocalService _samlIdpSpConnectionLocalService;

	private SamlProviderConfigurationHelper _samlProviderConfigurationHelper;

	@Reference
	private SamlSpIdpConnectionLocalService _samlSpIdpConnectionLocalService;

	@Reference
	private SecurityConfigurationBootstrap _securityConfigurationBootstrap;

}