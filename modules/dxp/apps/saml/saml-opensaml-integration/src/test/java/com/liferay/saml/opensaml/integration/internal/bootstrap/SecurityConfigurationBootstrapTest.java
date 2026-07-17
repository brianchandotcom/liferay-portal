/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.internal.bootstrap;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.saml.opensaml.integration.internal.util.ConfigurationServiceBootstrapUtil;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.opensaml.xmlsec.config.DefaultSecurityConfigurationBootstrap;
import org.opensaml.xmlsec.impl.BasicSignatureSigningConfiguration;
import org.opensaml.xmlsec.impl.BasicSignatureValidationConfiguration;
import org.opensaml.xmlsec.signature.support.SignatureConstants;

/**
 * @author Jorge García Jiménez
 */
public class SecurityConfigurationBootstrapTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		Class.forName(ConfigurationServiceBootstrapUtil.class.getName());
	}

	@Test
	public void testBlacklistFIPSDisallowedAlgorithms() {
		BasicSignatureSigningConfiguration basicSignatureSigningConfiguration =
			DefaultSecurityConfigurationBootstrap.
				buildDefaultSignatureSigningConfiguration();
		BasicSignatureValidationConfiguration
			basicSignatureValidationConfiguration =
				DefaultSecurityConfigurationBootstrap.
					buildDefaultSignatureValidationConfiguration();

		_blacklistFIPSDisallowedAlgorithms(
			basicSignatureSigningConfiguration,
			basicSignatureValidationConfiguration);

		_assertBlacklisted(
			basicSignatureSigningConfiguration.getBlacklistedAlgorithms());
		_assertBlacklisted(
			basicSignatureValidationConfiguration.getBlacklistedAlgorithms());
	}

	private void _assertBlacklisted(Collection<String> blacklistedAlgorithms) {
		Assert.assertTrue(
			blacklistedAlgorithms.contains(
				SignatureConstants.ALGO_ID_DIGEST_SHA1));
		Assert.assertTrue(
			blacklistedAlgorithms.contains(
				SignatureConstants.ALGO_ID_SIGNATURE_RSA_SHA1));
	}

	private void _blacklistFIPSDisallowedAlgorithms(
		BasicSignatureSigningConfiguration basicSignatureSigningConfiguration,
		BasicSignatureValidationConfiguration
			basicSignatureValidationConfiguration) {

		ReflectionTestUtil.invoke(
			new SecurityConfigurationBootstrap(),
			"_blacklistFIPSDisallowedAlgorithms",
			new Class<?>[] {
				BasicSignatureSigningConfiguration.class,
				BasicSignatureValidationConfiguration.class
			},
			basicSignatureSigningConfiguration,
			basicSignatureValidationConfiguration);
	}

}