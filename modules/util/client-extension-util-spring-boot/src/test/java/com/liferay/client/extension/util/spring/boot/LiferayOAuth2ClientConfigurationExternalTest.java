/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.util.spring.boot;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Gregory Amerson
 */
@ContextConfiguration(
	classes = {
		LiferayOAuth2AccessTokenManager.class,
		LiferayOAuth2ClientConfiguration.class
	}
)
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("LiferayOAuth2ClientConfigurationExternalTest.properties")
public class LiferayOAuth2ClientConfigurationExternalTest {

	@Test
	public void testFindDefaultHeadlessServer() {
		ClientRegistrationRepository clientRegistrationRepository =
			_liferayOAuth2ClientConfiguration.clientRegistrationRepository();

		ClientRegistration clientRegistration =
			clientRegistrationRepository.findByRegistrationId(
				"foo-bar-headless-server");

		Assert.assertEquals("123456789", clientRegistration.getClientId());
		Assert.assertEquals("Shibboleth", clientRegistration.getClientSecret());

		ClientRegistration.ProviderDetails providerDetails =
			clientRegistration.getProviderDetails();

		Assert.assertEquals(
			"http://localhost:8080/o/oauth2/token",
			providerDetails.getTokenUri());
	}

	@Test
	public void testFindExternalHeadlessServer() {
		ClientRegistrationRepository clientRegistrationRepository =
			_liferayOAuth2ClientConfiguration.clientRegistrationRepository();

		ClientRegistration clientRegistration =
			clientRegistrationRepository.findByRegistrationId(
				"external-headless-server");

		Assert.assertEquals("987654321", clientRegistration.getClientId());
		Assert.assertEquals("htelobbihS", clientRegistration.getClientSecret());

		ClientRegistration.ProviderDetails providerDetails =
			clientRegistration.getProviderDetails();

		Assert.assertEquals(
			"https://external-one-headless-server.com/oauth2/token",
			providerDetails.getTokenUri());
	}

	@Autowired
	private LiferayOAuth2ClientConfiguration _liferayOAuth2ClientConfiguration;

}