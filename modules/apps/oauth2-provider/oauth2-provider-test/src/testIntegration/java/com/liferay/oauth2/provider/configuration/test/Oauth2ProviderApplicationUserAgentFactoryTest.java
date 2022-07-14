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
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Dictionary;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
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

	@Before
	public void setUp() {
		_bundle = FrameworkUtil.getBundle(
			Oauth2ProviderApplicationUserAgentFactoryTest.class);

		_bundleContext = _bundle.getBundleContext();
	}

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

		DynamicQuery dynamicQuery = _queryOAuthApplication(
			externalReferenceCode);

		List<OAuth2Application> oAuth2Applications =
			_oAuth2ApplicationLocalService.dynamicQuery(dynamicQuery);

		while (latch.getCount() > 0) {
			if (!oAuth2Applications.isEmpty()) {
				return oAuth2Applications.get(0);
			}

			oAuth2Applications = _oAuth2ApplicationLocalService.dynamicQuery(
				dynamicQuery);

			latch.countDown();
			latch.await(10, TimeUnit.MILLISECONDS);
		}

		return null;
	}

	private DynamicQuery _queryOAuthApplication(String externalReferenceCode)
		throws Exception {

		DynamicQuery dynamicQuery =
			_oAuth2ApplicationLocalService.dynamicQuery();

		Property companyIdProperty = PropertyFactoryUtil.forName("companyId");

		dynamicQuery.add(companyIdProperty.eq(TestPropsValues.getCompanyId()));

		Property nameProperty = PropertyFactoryUtil.forName("name");

		dynamicQuery.add(nameProperty.eq(externalReferenceCode));

		return dynamicQuery;
	}

	@Inject
	private static ConfigurationAdmin _configurationAdmin;

	@Inject
	private static OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

	private Bundle _bundle;
	private BundleContext _bundleContext;

}