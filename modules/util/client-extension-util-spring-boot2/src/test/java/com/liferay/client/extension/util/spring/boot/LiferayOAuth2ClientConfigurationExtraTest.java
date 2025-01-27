/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.util.spring.boot;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Gregory Amerson
 */
@ContextConfiguration(
	classes = {
		LiferayOAuth2ClientConfiguration.class,
		LiferayOAuth2ClientConfigurationExtra.class,
		LiferayWebMvcConfigurer.class
	}
)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("LiferayOAuth2ClientConfigurationExtraTest.properties")
@WebMvcTest
public class LiferayOAuth2ClientConfigurationExtraTest {

	@Test
	public void testExtraClientRegistrations() {
		InMemoryClientRegistrationRepository
			inMemoryClientRegistrationRepository =
				(InMemoryClientRegistrationRepository)
					_clientRegistrationRepository;

		Assert.assertNotNull(
			inMemoryClientRegistrationRepository.findByRegistrationId("extra"));

		List<ClientRegistration> clientRegistrations = new ArrayList<>();

		inMemoryClientRegistrationRepository.forEach(clientRegistrations::add);

		Assert.assertEquals(
			clientRegistrations.toString(), 3, clientRegistrations.size());

		InMemoryReactiveClientRegistrationRepository
			inMemoryReactiveClientRegistrationRepository =
				(InMemoryReactiveClientRegistrationRepository)
					_reactiveClientRegistrationRepository;

		Assert.assertNotNull(
			inMemoryReactiveClientRegistrationRepository.findByRegistrationId(
				"extra"
			).block());

		clientRegistrations = new ArrayList<>();

		inMemoryClientRegistrationRepository.forEach(clientRegistrations::add);

		Assert.assertEquals(
			clientRegistrations.toString(), 3, clientRegistrations.size());
	}

	@Autowired
	private ClientRegistrationRepository _clientRegistrationRepository;

	@Autowired
	private ReactiveClientRegistrationRepository
		_reactiveClientRegistrationRepository;

}