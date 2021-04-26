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

package com.liferay.portal.store.azure.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.AssumeTestRule;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.store.test.util.BaseStoreTestCase;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Dictionary;

import org.junit.AfterClass;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Adolfo Pérez
 */
@RunWith(Arquillian.class)
public class AzureStoreTest extends BaseStoreTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new AssumeTestRule("assume"), new LiferayIntegrationTestRule());

	public static void assume() {
		String dlStoreImpl = PropsUtil.get(PropsKeys.DL_STORE_IMPL);

		String azureStoreClassName =
			"com.liferay.portal.store.azure.AzureStore";

		Assume.assumeTrue(
			StringBundler.concat(
				"Property \"", PropsKeys.DL_STORE_IMPL, "\" is not set to \"",
				azureStoreClassName, "\""),
			dlStoreImpl.equals(azureStoreClassName));
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
		_configuration = _configurationAdmin.getConfiguration(
			"com.liferay.portal.store.azure.configuration." +
				"AzureStoreConfiguration",
			StringPool.QUESTION);

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("connectionString", "");
		properties.put("containerName", "");
		properties.put("encryptionScope", "");
		properties.put("httpLoggingEnabled", "false");

		ConfigurationTestUtil.saveConfiguration(_configuration, properties);
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		ConfigurationTestUtil.deleteConfiguration(_configuration);
	}

	@Override
	protected Store getStore() {
		return _store;
	}

	private static Configuration _configuration;

	@Inject
	private static ConfigurationAdmin _configurationAdmin;

	@Inject(
		filter = "store.type=com.liferay.portal.store.azure.AzureStore",
		type = Store.class
	)
	private Store _store;

}