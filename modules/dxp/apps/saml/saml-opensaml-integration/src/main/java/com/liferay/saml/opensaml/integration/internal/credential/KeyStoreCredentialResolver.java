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

package com.liferay.saml.opensaml.integration.internal.credential;

import com.liferay.saml.runtime.configuration.SamlProviderConfiguration;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.runtime.credential.KeyStoreManager;

import java.security.KeyStore;
import java.security.KeyStore.Entry;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.KeyStore.SecretKeyEntry;
import java.security.KeyStore.TrustedCertificateEntry;
import java.security.cert.X509Certificate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.opensaml.xml.security.CriteriaSet;
import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.security.credential.AbstractCriteriaFilteringCredentialResolver;
import org.opensaml.xml.security.credential.BasicCredential;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.credential.CredentialResolver;
import org.opensaml.xml.security.credential.UsageType;
import org.opensaml.xml.security.criteria.EntityIDCriteria;
import org.opensaml.xml.security.criteria.UsageCriteria;
import org.opensaml.xml.security.x509.BasicX509Credential;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 */
@Component(
	configurationPid = "com.liferay.saml.runtime.configuration.SamlKeyStoreManagerConfiguration",
	immediate = true, service = CredentialResolver.class
)
public class KeyStoreCredentialResolver
	extends AbstractCriteriaFilteringCredentialResolver {

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
		Entry entry, String entityId, UsageType usage) {

		if (entry instanceof PrivateKeyEntry) {
			return processPrivateKeyEntry(
				(PrivateKeyEntry)entry, entityId, usage);
		}
		else if (entry instanceof SecretKeyEntry) {
			return processSecretKeyEntry(
				(SecretKeyEntry)entry, entityId, usage);
		}
		else if (entry instanceof TrustedCertificateEntry) {
			return processTrustedCertificateEntry(
				(TrustedCertificateEntry)entry, entityId, usage);
		}

		return null;
	}

	protected void checkCriteriaRequirements(CriteriaSet criteriaSet) {
		EntityIDCriteria entityCriteria = criteriaSet.get(
			EntityIDCriteria.class);

		if (entityCriteria == null) {
			throw new IllegalArgumentException(
				"No entity ID criteria was available in criteria set");
		}
	}

	protected Credential processPrivateKeyEntry(
		PrivateKeyEntry privateKeyEntry, String entityId, UsageType usageType) {

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
		SecretKeyEntry secretKeyEntry, String entityId, UsageType usageType) {

		BasicCredential basicCredential = new BasicCredential();

		basicCredential.setEntityId(entityId);
		basicCredential.setSecretKey(secretKeyEntry.getSecretKey());
		basicCredential.setUsageType(usageType);

		return basicCredential;
	}

	protected Credential processTrustedCertificateEntry(
		TrustedCertificateEntry trustedCertificateEntry, String entityId,
		UsageType usageType) {

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

			Entry entry = keyStore.getEntry(
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