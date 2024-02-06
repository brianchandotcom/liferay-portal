/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.internal.metadata;

import com.liferay.petra.concurrent.DCLSingleton;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.opensaml.integration.internal.bootstrap.ParserPoolUtil;
import com.liferay.saml.opensaml.integration.internal.provider.CachingChainingMetadataResolver;
import com.liferay.saml.opensaml.integration.internal.provider.DBMetadataResolver;
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
import org.opensaml.saml.metadata.resolver.MetadataResolver;
import org.opensaml.saml.metadata.resolver.impl.PredicateRoleDescriptorResolver;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.security.impl.MetadataCredentialResolver;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.CredentialResolver;
import org.opensaml.security.credential.UsageType;
import org.opensaml.security.criteria.UsageCriterion;
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