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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.opensaml.core.config.ConfigurationService;
import org.opensaml.xmlsec.SignatureSigningConfiguration;
import org.opensaml.xmlsec.config.DefaultSecurityConfigurationBootstrap;
import org.opensaml.xmlsec.impl.BasicSignatureSigningConfiguration;

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
	public void activate(Map<String, Object> properties) {
		BasicSignatureSigningConfiguration basicSignatureSigningConfiguration =
			DefaultSecurityConfigurationBootstrap.
				buildDefaultSignatureSigningConfiguration();

		Object blacklistedAlgorithmsObject = properties.get(
			"blacklisted.algorithms");

		Collection<String> blacklistedAlgorithms = new ArrayList<>(
			basicSignatureSigningConfiguration.getBlacklistedAlgorithms());

		if (blacklistedAlgorithmsObject instanceof String[]) {
			Collections.addAll(
				blacklistedAlgorithms, (String[])blacklistedAlgorithmsObject);

			basicSignatureSigningConfiguration.setBlacklistedAlgorithms(
				blacklistedAlgorithms);
		}

		ConfigurationService.register(
			SignatureSigningConfiguration.class,
			basicSignatureSigningConfiguration);
	}

	@Reference
	private OpenSamlBootstrap _openSamlBootstrap;

}