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

package com.liferay.saml.opensaml.integration.internal.credential;

import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.persistence.model.SamlSpIdpConnection;
import com.liferay.saml.persistence.service.SamlSpIdpConnectionLocalService;
import com.liferay.saml.runtime.SamlException;
import com.liferay.saml.runtime.configuration.SamlProviderConfiguration;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.runtime.credential.KeyStoreManager;
import com.liferay.saml.runtime.exception.CredentialAuthException;
import com.liferay.saml.runtime.exception.CredentialException;
import com.liferay.saml.runtime.exception.EntityIdException;
import com.liferay.saml.runtime.metadata.LocalEntityManager;

import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;

import org.apache.xml.security.utils.Base64;

import org.opensaml.core.criterion.EntityIdCriterion;
import org.opensaml.security.credential.BasicCredential;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.CredentialResolver;
import org.opensaml.security.credential.UsageType;
import org.opensaml.security.credential.impl.AbstractCredentialResolver;
import org.opensaml.security.criteria.UsageCriterion;
import org.opensaml.security.x509.BasicX509Credential;
import org.opensaml.security.x509.X509Credential;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 * @author Stian Sigvartsen
 */
@Component(
	configurationPid = "com.liferay.saml.runtime.configuration.SamlKeyStoreManagerConfiguration",
	immediate = true,
	service = {CredentialResolver.class, LocalEntityManager.class}
)
public class KeyStoreCredentialResolver
	extends AbstractCredentialResolver implements LocalEntityManager {

	public void authenticateLocalEntityCertificate(
			String certificateKeyPassword, CertificateUsage certificateUsage,
			String entityId)
		throws CredentialAuthException, CredentialException {

		KeyStore.Entry entry = null;

		if (certificateUsage == CertificateUsage.ENCRYPTION) {
			entry = _getKeyStoreEntry(
				_getAlias(entityId, UsageType.ENCRYPTION),
				certificateKeyPassword);
		}
		else {
			entry = _getKeyStoreEntry(
				_getAlias(entityId, UsageType.SIGNING), certificateKeyPassword);
		}

		if (entry == null) {
			throw new CredentialException("Certificate not found");
		}
	}

	@Override
	public void deleteLocalEntityCertificate(CertificateUsage certificateUsage)
		throws KeyStoreException {

		KeyStore keyStore = _keyStoreManager.getKeyStore();

		keyStore.deleteEntry(
			_getAlias(getLocalEntityId(), _getUsageType(certificateUsage)));

		try {
			_keyStoreManager.saveKeyStore(keyStore);
		}
		catch (Exception exception) {
			throw new KeyStoreException(exception);
		}
	}

	@Override
	public String getEncodedLocalEntityCertificate(
			CertificateUsage certificateUsage)
		throws SamlException {

		try {
			X509Certificate x509Certificate = getLocalEntityCertificate(
				certificateUsage);

			if (x509Certificate == null) {
				return null;
			}

			return Base64.encode(x509Certificate.getEncoded(), 76);
		}
		catch (CertificateEncodingException certificateEncodingException) {
			throw new SamlException(certificateEncodingException);
		}
	}

	@Override
	public X509Certificate getLocalEntityCertificate(
			CertificateUsage certificateUsage)
		throws SamlException {

		UsageType usageType = _getUsageType(certificateUsage);

		if (usageType == null) {
			return null;
		}

		String entityId = getLocalEntityId();

		if (Validator.isBlank(entityId)) {
			throw new SamlException(
				new EntityIdException("An Entity ID must be configured"));
		}

		UsageCriterion usageCriterion = new UsageCriterion(usageType);

		try {
			X509Credential x509Credential = (X509Credential)resolveSingle(
				new CriteriaSet(
					new EntityIdCriterion(entityId), usageCriterion));

			if (x509Credential == null) {
				return null;
			}

			return x509Credential.getEntityCertificate();
		}
		catch (ResolverException resolverException) {
			throw new SamlException(resolverException);
		}
	}

	@Override
	public String getLocalEntityId() {
		return _getSamlProviderConfiguration().entityId();
	}

	@Override
	public boolean hasDefaultIdpRole() {
		List<SamlSpIdpConnection> samlSpIdpConnections =
			_samlSpIdpConnectionLocalService.getSamlSpIdpConnections(
				CompanyThreadLocal.getCompanyId());

		if (samlSpIdpConnections.isEmpty()) {
			return false;
		}

		return true;
	}

	@Override
	public Iterable<Credential> resolve(CriteriaSet criteriaSet)
		throws SecurityException {

		_checkCriteriaRequirements(criteriaSet);

		EntityIdCriterion entityIDCriterion = criteriaSet.get(
			EntityIdCriterion.class);

		String entityId = entityIDCriterion.getEntityId();

		SamlProviderConfiguration samlProviderConfiguration =
			_samlProviderConfigurationHelper.getSamlProviderConfiguration();

		UsageCriterion usageCriterion = criteriaSet.get(UsageCriterion.class);

		UsageType usageType = UsageType.UNSPECIFIED;

		if (usageCriterion != null) {
			usageType = usageCriterion.getUsage();
		}

		String keyStoreCredentialPassword = null;

		if (entityId.equals(samlProviderConfiguration.entityId())) {
			if (usageType == UsageType.ENCRYPTION) {
				keyStoreCredentialPassword =
					samlProviderConfiguration.
						keyStoreEncryptionCredentialPassword();
			}
			else {
				keyStoreCredentialPassword =
					samlProviderConfiguration.keyStoreCredentialPassword();
			}
		}

		KeyStore.Entry entry = _getKeyStoreEntry(
			_getAlias(entityId, usageType), keyStoreCredentialPassword);

		if (entry == null) {
			return Collections.emptySet();
		}

		Credential credential = _buildCredential(entry, entityId, usageType);

		return Collections.singleton(credential);
	}

	@Override
	public void storeLocalEntityCertificate(
			PrivateKey privateKey, String certificateKeyPassword,
			X509Certificate x509Certificate, CertificateUsage certificateUsage)
		throws Exception {

		KeyStore keyStore = _keyStoreManager.getKeyStore();

		keyStore.setEntry(
			_getAlias(getLocalEntityId(), _getUsageType(certificateUsage)),
			new KeyStore.PrivateKeyEntry(
				privateKey, new Certificate[] {x509Certificate}),
			new KeyStore.PasswordProtection(
				certificateKeyPassword.toCharArray()));

		_keyStoreManager.saveKeyStore(keyStore);
	}

	private Credential _buildCredential(
		KeyStore.Entry entry, String entityId, UsageType usage) {

		if (entry instanceof KeyStore.PrivateKeyEntry) {
			return _processPrivateKeyEntry(
				(KeyStore.PrivateKeyEntry)entry, entityId, usage);
		}
		else if (entry instanceof KeyStore.SecretKeyEntry) {
			return _processSecretKeyEntry(
				(KeyStore.SecretKeyEntry)entry, entityId, usage);
		}
		else if (entry instanceof KeyStore.TrustedCertificateEntry) {
			return _processTrustedCertificateEntry(
				(KeyStore.TrustedCertificateEntry)entry, entityId, usage);
		}

		return null;
	}

	private void _checkCriteriaRequirements(CriteriaSet criteriaSet) {
		EntityIdCriterion entityIdCriterion = criteriaSet.get(
			EntityIdCriterion.class);

		if (entityIdCriterion == null) {
			throw new IllegalArgumentException(
				"No entity ID criterion was available in criteria set");
		}
	}

	private String _getAlias(String entityId, UsageType usageType) {
		if (usageType.equals(UsageType.SIGNING)) {
			return entityId;
		}
		else if (usageType.equals(UsageType.ENCRYPTION)) {
			return entityId + "-encryption";
		}

		return entityId;
	}

	private <T> T _getCauseThrowable(
		Throwable throwable, Class<T> exceptionType) {

		if (throwable == null) {
			return null;
		}

		Throwable causeThrowable = throwable.getCause();

		while (causeThrowable != null) {
			if (exceptionType.isInstance(causeThrowable)) {
				return (T)causeThrowable;
			}

			causeThrowable = causeThrowable.getCause();
		}

		return null;
	}

	private KeyStore.Entry _getKeyStoreEntry(
			String alias, String certificateKeyPassword)
		throws CredentialAuthException {

		KeyStore.PasswordProtection keyStorePasswordProtection = null;

		if (certificateKeyPassword != null) {
			keyStorePasswordProtection = new KeyStore.PasswordProtection(
				certificateKeyPassword.toCharArray());
		}

		try {
			KeyStore keyStore = _keyStoreManager.getKeyStore();

			return keyStore.getEntry(alias, keyStorePasswordProtection);
		}
		catch (GeneralSecurityException generalSecurityException) {
			Class<? extends KeyStoreManager> clazz =
				_keyStoreManager.getClass();
			long companyId = CompanyThreadLocal.getCompanyId();

			if (generalSecurityException instanceof KeyStoreException) {
				UnrecoverableKeyException unrecoverableKeyException =
					_getCauseThrowable(
						generalSecurityException,
						UnrecoverableKeyException.class);

				if (unrecoverableKeyException != null) {
					throw new CredentialAuthException.InvalidKeyStorePassword(
						String.format(
							"Company %s used an incorrect password to access " +
								"the KeyStore provided by %s",
							companyId, clazz.getSimpleName()),
						unrecoverableKeyException);
				}

				throw new CredentialAuthException.InvalidKeyStore(
					String.format(
						"Company %s could not load the SAML KeyStore " +
							"provided by %s",
						companyId, clazz.getSimpleName()),
					generalSecurityException);
			}

			if (generalSecurityException instanceof UnrecoverableKeyException) {
				throw new CredentialAuthException.InvalidCredentialPassword(
					String.format(
						"Company %s used an incorrect key credential " +
							"password to an entry in the SAML KeyStore " +
								"provided by %s",
						companyId, clazz.getSimpleName()),
					(UnrecoverableKeyException)generalSecurityException);
			}

			throw new CredentialAuthException(
				String.format(
					"Unknown Exception occurred for company %s using %s",
					companyId, clazz.getSimpleName()),
				generalSecurityException);
		}
	}

	private SamlProviderConfiguration _getSamlProviderConfiguration() {
		return _samlProviderConfigurationHelper.getSamlProviderConfiguration();
	}

	private UsageType _getUsageType(CertificateUsage certificateUsage) {
		UsageType usageType = null;

		if (certificateUsage == CertificateUsage.ENCRYPTION) {
			usageType = UsageType.ENCRYPTION;
		}
		else if (certificateUsage == CertificateUsage.SIGNING) {
			usageType = UsageType.SIGNING;
		}

		return usageType;
	}

	private Credential _processPrivateKeyEntry(
		KeyStore.PrivateKeyEntry privateKeyEntry, String entityId,
		UsageType usageType) {

		BasicX509Credential basicX509Credential = new BasicX509Credential(
			(X509Certificate)privateKeyEntry.getCertificate());

		basicX509Credential.setEntityCertificateChain(
			Arrays.asList(
				(X509Certificate[])privateKeyEntry.getCertificateChain()));
		basicX509Credential.setEntityId(entityId);
		basicX509Credential.setPrivateKey(privateKeyEntry.getPrivateKey());
		basicX509Credential.setUsageType(usageType);

		return basicX509Credential;
	}

	private Credential _processSecretKeyEntry(
		KeyStore.SecretKeyEntry secretKeyEntry, String entityId,
		UsageType usageType) {

		BasicCredential basicCredential = new BasicCredential(
			secretKeyEntry.getSecretKey());

		basicCredential.setEntityId(entityId);
		basicCredential.setUsageType(usageType);

		return basicCredential;
	}

	private Credential _processTrustedCertificateEntry(
		KeyStore.TrustedCertificateEntry trustedCertificateEntry,
		String entityId, UsageType usageType) {

		X509Certificate x509Certificate =
			(X509Certificate)trustedCertificateEntry.getTrustedCertificate();

		BasicX509Credential basicX509Credential = new BasicX509Credential(
			x509Certificate);

		basicX509Credential.setEntityCertificateChain(
			Arrays.asList(x509Certificate));

		basicX509Credential.setEntityId(entityId);
		basicX509Credential.setUsageType(usageType);

		return basicX509Credential;
	}

	@Reference(name = "KeyStoreManager", target = "(default=true)")
	private KeyStoreManager _keyStoreManager;

	@Reference
	private SamlProviderConfigurationHelper _samlProviderConfigurationHelper;

	@Reference
	private SamlSpIdpConnectionLocalService _samlSpIdpConnectionLocalService;

}