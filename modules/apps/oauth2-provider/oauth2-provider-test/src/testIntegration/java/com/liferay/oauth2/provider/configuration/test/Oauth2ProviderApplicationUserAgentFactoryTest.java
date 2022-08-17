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

package com.liferay.oauth2.provider.configuration.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.oauth2.provider.configuration.OAuth2ProviderApplicationUserAgentConfiguration;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Dictionary;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Raymond Augé
 */
@RunWith(Arquillian.class)
public class Oauth2ProviderApplicationUserAgentFactoryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testCreateUserAgentApplicationUsingConfiguration()
		throws Exception {

		String externalReferenceCode = "foo";

		try (ConfigurationHolder configurationHolder1 = new ConfigurationHolder(
				() -> _configurationAdmin.getFactoryConfiguration(
					OAuth2ProviderApplicationUserAgentConfiguration.class.
						getName(),
					externalReferenceCode, "?"))) {

			configurationHolder1.update(
				HashMapDictionaryBuilder.<String, Object>put(
					"_portalK8sConfigMapModifier.cardinality.minimum", 0
				).put(
					"companyId", TestPropsValues.getCompanyId()
				).put(
					"homePageURL", "http://foo.me"
				).build());

			OAuth2Application oAuth2Application = _fetchOAuthApplication(
				externalReferenceCode);

			Assert.assertNotNull(oAuth2Application);

			Assert.assertEquals(
				externalReferenceCode, oAuth2Application.getName());
		}

		Thread.sleep(200);

		OAuth2Application oAuth2Application = _fetchOAuthApplication(
			externalReferenceCode);

		Assert.assertNull(oAuth2Application);
	}

	public static class ConfigurationHolder
		extends ClosableHolder<Configuration> {

		public ConfigurationHolder(
				UnsafeSupplier<Configuration, Exception> onInitUnsafeSupplier)
			throws Exception {

			super(
				configuration -> configuration.delete(), onInitUnsafeSupplier);
		}

		public Dictionary<String, Object> getProperties() throws Exception {
			Configuration configuration = get();

			return configuration.getProcessedProperties(null);
		}

		public void update(Dictionary<String, Object> properties)
			throws Exception {

			Configuration configuration = get();

			configuration.update(properties);
		}

	}

	private OAuth2Application _fetchOAuthApplication(
			String externalReferenceCode)
		throws Exception {

		CountDownLatch latch = new CountDownLatch(50);

		OAuth2Application oAuth2Application = null;

		while ((latch.getCount() > 0) && (oAuth2Application == null)) {
			try {
				oAuth2Application =
					_oAuth2ApplicationLocalService.
						getOAuth2ApplicationByExternalReferenceCode(
							TestPropsValues.getCompanyId(),
							externalReferenceCode);
			}
			catch (Exception exception) {

				// Ignore this scenario

			}

			latch.countDown();
			latch.await(10, TimeUnit.MILLISECONDS);
		}

		return oAuth2Application;
	}

	@Inject
	private static ConfigurationAdmin _configurationAdmin;

	@Inject
	private static OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

}