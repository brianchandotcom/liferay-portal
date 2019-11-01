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

package com.liferay.saml.opensaml.integration.internal.bootstrap;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;

import org.opensaml.core.config.ConfigurationService;
import org.opensaml.xmlsec.DecryptionConfiguration;
import org.opensaml.xmlsec.EncryptionConfiguration;
import org.opensaml.xmlsec.SignatureSigningConfiguration;
import org.opensaml.xmlsec.SignatureValidationConfiguration;
import org.opensaml.xmlsec.config.DefaultSecurityConfigurationBootstrap;
import org.opensaml.xmlsec.impl.BasicDecryptionConfiguration;
import org.opensaml.xmlsec.impl.BasicEncryptionConfiguration;
import org.opensaml.xmlsec.impl.BasicSignatureSigningConfiguration;
import org.opensaml.xmlsec.impl.BasicSignatureValidationConfiguration;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Sierra Andrés
 */
@Component(service = SecurityConfigurationBootstrap.class)
public class SecurityConfigurationBootstrap {

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		BasicDecryptionConfiguration basicDecryptionConfiguration =
			DefaultSecurityConfigurationBootstrap.
				buildDefaultDecryptionConfiguration();
		BasicEncryptionConfiguration basicEncryptionConfiguration =
			DefaultSecurityConfigurationBootstrap.
				buildDefaultEncryptionConfiguration();
		BasicSignatureSigningConfiguration basicSignatureSigningConfiguration =
			DefaultSecurityConfigurationBootstrap.
				buildDefaultSignatureSigningConfiguration();
		BasicSignatureValidationConfiguration
			basicSignatureValidationConfiguration =
				DefaultSecurityConfigurationBootstrap.
					buildDefaultSignatureValidationConfiguration();

		Object blacklistedAlgorithmsObject = properties.get(
			"blacklisted.algorithms");

		if (blacklistedAlgorithmsObject instanceof String[]) {
			basicDecryptionConfiguration.setBlacklistedAlgorithms(
				_combine(
					basicDecryptionConfiguration.getBlacklistedAlgorithms(),
					(String[])blacklistedAlgorithmsObject));

			basicEncryptionConfiguration.setBlacklistedAlgorithms(
				_combine(
					basicEncryptionConfiguration.getBlacklistedAlgorithms(),
					(String[])blacklistedAlgorithmsObject));

			basicSignatureSigningConfiguration.setBlacklistedAlgorithms(
				_combine(
					basicSignatureSigningConfiguration.
						getBlacklistedAlgorithms(),
					(String[])blacklistedAlgorithmsObject));

			basicSignatureValidationConfiguration.setBlacklistedAlgorithms(
				_combine(
					basicSignatureValidationConfiguration.
						getBlacklistedAlgorithms(),
					(String[])blacklistedAlgorithmsObject));
		}

		ConfigurationService.register(
			DecryptionConfiguration.class, basicDecryptionConfiguration);
		ConfigurationService.register(
			EncryptionConfiguration.class, basicEncryptionConfiguration);
		ConfigurationService.register(
			SignatureSigningConfiguration.class,
			basicSignatureSigningConfiguration);
		ConfigurationService.register(
			SignatureValidationConfiguration.class,
			basicSignatureValidationConfiguration);
	}

	private Collection<String> _combine(
		Collection<String> collection, String... strings) {

		Collection<String> combinedCollection = new HashSet<>(collection);

		Collections.addAll(combinedCollection, strings);

		return combinedCollection;
	}

	@Reference
	private OpenSamlBootstrap _openSamlBootstrap;

}