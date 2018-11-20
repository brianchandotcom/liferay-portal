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

import com.liferay.saml.runtime.configuration.SamlProviderConfiguration;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.runtime.credential.KeyStoreManager;

import java.security.KeyStore;
import java.security.cert.X509Certificate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.shibboleth.utilities.java.support.resolver.CriteriaSet;

import org.opensaml.core.criterion.EntityIdCriterion;
import org.opensaml.security.credential.BasicCredential;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.CredentialResolver;
import org.opensaml.security.credential.UsageType;
import org.opensaml.security.credential.impl.AbstractCredentialResolver;
import org.opensaml.security.criteria.UsageCriterion;
import org.opensaml.security.x509.BasicX509Credential;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 */
@Component(
	configurationPid = "com.liferay.saml.runtime.configuration.SamlKeyStoreManagerConfiguration",
	immediate = true, service = CredentialResolver.class
)
public class KeyStoreCredentialResolver extends AbstractCredentialResolver {

	@Reference(
		name = "KeyStoreManager", target = "(default=true)", unbind = "-"
	)
	public void setKeyStoreManager(KeyStoreManager keyStoreManager) {
		_keyStoreManager = keyStoreManager;
	}

	@Reference(unbind = "-")
	public void setSamlProviderConfigurationHelper(
		SamlProviderConfigurationHelper samlProviderConfigurationHelper) {

		_samlProviderConfigurationHelper = samlProviderConfigurationHelper;
	}

	protected Credential buildCredential(
		KeyStore.Entry entry, String entityId, UsageType usage) {

		if (entry instanceof KeyStore.PrivateKeyEntry) {
			return processPrivateKeyEntry(
				(KeyStore.PrivateKeyEntry)entry, entityId, usage);
		}
		else if (entry instanceof KeyStore.SecretKeyEntry) {
			return processSecretKeyEntry(
				(KeyStore.SecretKeyEntry)entry, entityId, usage);
		}
		else if (entry instanceof KeyStore.TrustedCertificateEntry) {
			return processTrustedCertificateEntry(
				(KeyStore.TrustedCertificateEntry)entry, entityId, usage);
		}

		return null;
	}

	protected void checkCriteriaRequirements(CriteriaSet criteriaSet) {
		EntityIdCriterion entityIdCriterion = criteriaSet.get(
			EntityIdCriterion.class);

		if (entityIdCriterion == null) {
			throw new IllegalArgumentException(
				"No entity ID criterion was available in criteria set");
		}
	}

	protected Credential processPrivateKeyEntry(
		KeyStore.PrivateKeyEntry privateKeyEntry, String entityId,
		UsageType usageType) {

		BasicX509Credential basicX509Credential = new BasicX509Credential();

		basicX509Credential.setEntityCertificate(
			(X509Certificate)privateKeyEntry.getCertificate());
		basicX509Credential.setEntityCertificateChain(
			Arrays.asList(
				(X509Certificate[])privateKeyEntry.getCertificateChain()));
		basicX509Credential.setEntityId(entityId);
		basicX509Credential.setPrivateKey(privateKeyEntry.getPrivateKey());
		basicX509Credential.setUsageType(usageType);

		return basicX509Credential;
	}

	protected Credential processSecretKeyEntry(
		KeyStore.SecretKeyEntry secretKeyEntry, String entityId,
		UsageType usageType) {

		BasicCredential basicCredential = new BasicCredential();

		basicCredential.setEntityId(entityId);
		basicCredential.setSecretKey(secretKeyEntry.getSecretKey());
		basicCredential.setUsageType(usageType);

		return basicCredential;
	}

	protected Credential processTrustedCertificateEntry(
		KeyStore.TrustedCertificateEntry trustedCertificateEntry,
		String entityId, UsageType usageType) {

		BasicX509Credential basicX509Credential = new BasicX509Credential();

		X509Certificate x509Certificate =
			(X509Certificate)trustedCertificateEntry.getTrustedCertificate();

		basicX509Credential.setEntityCertificate(x509Certificate);

		List<X509Certificate> x509Certificates = new ArrayList<>();

		x509Certificates.add(x509Certificate);

		basicX509Credential.setEntityCertificateChain(x509Certificates);

		basicX509Credential.setEntityId(entityId);
		basicX509Credential.setUsageType(usageType);

		return basicX509Credential;
	}

	@Override
	protected Iterable<Credential> resolveFromSource(CriteriaSet criteriaSet)
		throws SecurityException {

		try {
			checkCriteriaRequirements(criteriaSet);

			EntityIDCriteria entityIDCriteria = criteriaSet.get(
				EntityIDCriteria.class);

			String entityId = entityIDCriteria.getEntityID();

			KeyStore.PasswordProtection keyStorePasswordProtection = null;

			SamlProviderConfiguration samlProviderConfiguration =
				_samlProviderConfigurationHelper.getSamlProviderConfiguration();

			if (entityId.equals(samlProviderConfiguration.entityId())) {
				String keyStoreCredentialPassword =
					samlProviderConfiguration.keyStoreCredentialPassword();

				if (keyStoreCredentialPassword != null) {
					keyStorePasswordProtection =
						new KeyStore.PasswordProtection(
							keyStoreCredentialPassword.toCharArray());
				}
			}

			KeyStore keyStore = _keyStoreManager.getKeyStore();

			KeyStore.Entry entry = keyStore.getEntry(
				entityId, keyStorePasswordProtection);

			if (entry == null) {
				return Collections.emptySet();
			}

			UsageType usageType = UsageType.UNSPECIFIED;

			UsageCriteria usageCriteria = criteriaSet.get(UsageCriteria.class);

			if (usageCriteria != null) {
				usageType = usageCriteria.getUsage();
			}

			Credential credential = buildCredential(entry, entityId, usageType);

			return Collections.singleton(credential);
		}
		catch (RuntimeException re) {
			throw new SecurityException(re);
		}
		catch (Exception e) {
			throw new SecurityException(e);
		}
	}

	private KeyStoreManager _keyStoreManager;
	private SamlProviderConfigurationHelper _samlProviderConfigurationHelper;

}