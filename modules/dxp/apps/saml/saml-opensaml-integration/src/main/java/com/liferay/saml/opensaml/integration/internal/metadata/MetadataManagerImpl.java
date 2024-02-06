/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.internal.metadata;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.runtime.SamlException;
import com.liferay.saml.runtime.configuration.SamlProviderConfiguration;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.runtime.metadata.LocalEntityManager;

import javax.servlet.http.HttpServletRequest;

import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;

import org.opensaml.core.criterion.EntityIdCriterion;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.CredentialResolver;
import org.opensaml.security.credential.UsageType;
import org.opensaml.security.criteria.UsageCriterion;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 */
@Component(service = MetadataManager.class)
public class MetadataManagerImpl implements MetadataManager {

	@Override
	public EntityDescriptor getEntityDescriptor(
			HttpServletRequest httpServletRequest)
		throws SamlException {

		Credential encryptionCredential = null;

		try {
			encryptionCredential = _getEncryptionCredential();
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
					_isSignMetadata(), _getSigningCredential(),
					encryptionCredential);
			}
			else if (_samlProviderConfigurationHelper.isRoleSp()) {
				return MetadataGeneratorUtil.buildSpEntityDescriptor(
					portalURL, localEntityId, _isSignAuthnRequest(),
					_isSignMetadata(), _isWantAssertionsSigned(),
					_getSigningCredential(), encryptionCredential);
			}

			return null;
		}
		catch (Exception exception) {
			throw new SamlException(exception);
		}
	}

	private Credential _getEncryptionCredential() throws SamlException {
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

	private SamlProviderConfiguration _getSamlProviderConfiguration() {
		return _samlProviderConfigurationHelper.getSamlProviderConfiguration();
	}

	private Credential _getSigningCredential() throws SamlException {
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

	@Reference
	private CredentialResolver _credentialResolver;

	@Reference
	private LocalEntityManager _localEntityManager;

	@Reference
	private Portal _portal;

	@Reference
	private SamlProviderConfigurationHelper _samlProviderConfigurationHelper;

}