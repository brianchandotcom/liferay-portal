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

package com.liferay.dynamic.data.mapping.data.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderException;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderRequest;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponse;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderTracker;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Rafael Praxedes
 */
@RunWith(Arquillian.class)
public class DDMDataProviderTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		setUpTestDDMDataProvider();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_serviceRegistration.unregister();
	}

	@Test
	public void testGetDDMDataProviderByInstanceId() {
		DDMDataProvider testDataProvider =
			_ddmDataProviderTracker.getDDMDataProviderByInstanceId("test");

		Assert.assertNotNull(testDataProvider);
	}

	@Test
	public void testInvokeDataProvider() throws Exception {
		DDMDataProvider testDataProvider =
			_ddmDataProviderTracker.getDDMDataProviderByInstanceId("test");

		DDMDataProviderRequest.Builder builder =
			DDMDataProviderRequest.Builder.newBuilder();

		DDMDataProviderRequest ddmDataProviderRequest = builder.build();

		DDMDataProviderResponse ddmDataProviderResponse =
			testDataProvider.getData(ddmDataProviderRequest);

		Optional<List<KeyValuePair>> keyValuePairsOptional =
			ddmDataProviderResponse.getOutputOptional(
				"Default-Output", List.class);

		Assert.assertTrue(keyValuePairsOptional.isPresent());

		List<KeyValuePair> keyValuePairs = keyValuePairsOptional.get();

		Assert.assertEquals(keyValuePairs.toString(), 2, keyValuePairs.size());
	}

	protected static void setUpTestDDMDataProvider() {
		Bundle bundle = FrameworkUtil.getBundle(DDMDataProviderTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceRegistration = bundleContext.registerService(
			DDMDataProvider.class, new DDMTestDataProvider(),
			MapUtil.singletonDictionary(
				"ddm.data.provider.instance.id", "test"));
	}

	@Inject
	private static DDMDataProviderTracker _ddmDataProviderTracker;

	private static ServiceRegistration<DDMDataProvider> _serviceRegistration;

	private static class DDMTestDataProvider implements DDMDataProvider {

		@Override
		public DDMDataProviderResponse getData(
				DDMDataProviderRequest ddmDataProviderRequest)
			throws DDMDataProviderException {

			DDMDataProviderResponse.Builder builder =
				DDMDataProviderResponse.Builder.newBuilder();

			List<KeyValuePair> keyValuePairs = new ArrayList<>();

			keyValuePairs.add(new KeyValuePair("1", "A"));
			keyValuePairs.add(new KeyValuePair("2", "B"));

			return builder.withOutput(
				"Default-Output", keyValuePairs
			).build();
		}

		@Override
		public Class<?> getSettings() {
			return null;
		}

	}

}