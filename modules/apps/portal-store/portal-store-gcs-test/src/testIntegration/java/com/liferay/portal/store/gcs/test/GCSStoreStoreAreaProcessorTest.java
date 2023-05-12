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

package com.liferay.portal.store.gcs.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.document.library.kernel.store.StoreArea;
import com.liferay.document.library.kernel.store.StoreAreaProcessor;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.AssumeTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.time.Duration;

import java.util.Arrays;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Adolfo Pérez
 */
@FeatureFlags("LPS-174816")
@RunWith(Arquillian.class)
public class GCSStoreStoreAreaProcessorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new AssumeTestRule("assume"), new LiferayIntegrationTestRule());

	public static void assume() {
		String gcsStoreClassName = "com.liferay.portal.store.gcs.GCSStore";
		String dlStoreImpl = PropsUtil.get(PropsKeys.DL_STORE_IMPL);

		Assume.assumeTrue(
			StringBundler.concat(
				"Property \"", PropsKeys.DL_STORE_IMPL, "\" is not set to \"",
				gcsStoreClassName, "\""),
			dlStoreImpl.equals(gcsStoreClassName));
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
		_configuration = _configurationAdmin.getConfiguration(
			"com.liferay.portal.store.gcs.configuration.GCSStoreConfiguration",
			StringPool.QUESTION);

		ConfigurationTestUtil.saveConfiguration(
			_configuration,
			HashMapDictionaryBuilder.<String, Object>put(
				"aes256Key", ""
			).put(
				"bucketName", "test"
			).put(
				"initialRetryDelay", "400"
			).put(
				"initialRPCTimeout", "120000"
			).put(
				"maxRetryAttempts", "5"
			).put(
				"maxRetryDelay", "10000"
			).put(
				"maxRPCTimeout", "600000"
			).put(
				"retryDelayMultiplier", "1.5"
			).put(
				"retryJitter", "false"
			).put(
				"rpcTimeoutMultiplier", "1.0"
			).put(
				"serviceAccountKey", ""
			).build());
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		ConfigurationTestUtil.deleteConfiguration(_configuration);
	}

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testCleanupDeletedStoreAreaKeepsRecentFiles() throws Exception {
		StoreArea.withStoreArea(
			StoreArea.DELETED,
			() -> {
				String fileName = StringUtil.randomString();

				_store.addFile(
					_group.getCompanyId(), _group.getGroupId(), fileName,
					Store.VERSION_DEFAULT,
					new UnsyncByteArrayInputStream(new byte[0]));

				Assert.assertTrue(
					_store.hasFile(
						_group.getCompanyId(), _group.getGroupId(), fileName,
						Store.VERSION_DEFAULT));

				StoreAreaProcessor storeAreaProcessor =
					(StoreAreaProcessor)_store;

				storeAreaProcessor.cleanUpDeletedStoreArea(
					_group.getCompanyId(), 1, Duration.ofDays(1),
					StringPool.BLANK);

				Assert.assertTrue(
					_store.hasFile(
						_group.getCompanyId(), _group.getGroupId(), fileName,
						Store.VERSION_DEFAULT));
			});
	}

	@Test
	public void testCleanupDeletedStoreAreaRemovesFilesIncrementally()
		throws Exception {

		StoreArea.withStoreArea(
			StoreArea.DELETED,
			() -> {
				for (String fileName : RandomTestUtil.randomStrings(4)) {
					_store.addFile(
						_group.getCompanyId(), _group.getGroupId(), fileName,
						Store.VERSION_DEFAULT,
						new UnsyncByteArrayInputStream(new byte[0]));
				}

				StoreAreaProcessor storeAreaProcessor =
					(StoreAreaProcessor)_store;

				int runCount = 0;

				String startOffset = StringPool.BLANK;

				do {
					startOffset = storeAreaProcessor.cleanUpDeletedStoreArea(
						_group.getCompanyId(), 1, Duration.ofDays(-1),
						startOffset);

					runCount++;
				}
				while (Validator.isNotNull(startOffset));

				Assert.assertTrue(runCount > 1);

				String[] fileNames = _store.getFileNames(
					_group.getCompanyId(), _group.getGroupId(),
					StringPool.BLANK);

				Assert.assertEquals(
					Arrays.toString(fileNames), 0, fileNames.length);
			});
	}

	@Test
	public void testCleanupDeletedStoreAreaRemovesOldFiles() throws Exception {
		StoreArea.withStoreArea(
			StoreArea.DELETED,
			() -> {
				String fileName = StringUtil.randomString();

				_store.addFile(
					_group.getCompanyId(), _group.getGroupId(), fileName,
					Store.VERSION_DEFAULT,
					new UnsyncByteArrayInputStream(new byte[0]));

				Assert.assertTrue(
					_store.hasFile(
						_group.getCompanyId(), _group.getGroupId(), fileName,
						Store.VERSION_DEFAULT));

				StoreAreaProcessor storeAreaProcessor =
					(StoreAreaProcessor)_store;

				storeAreaProcessor.cleanUpDeletedStoreArea(
					_group.getCompanyId(), 1, Duration.ofDays(-1),
					StringPool.BLANK);

				Assert.assertFalse(
					_store.hasFile(
						_group.getCompanyId(), _group.getGroupId(), fileName,
						Store.VERSION_DEFAULT));
			});
	}

	private static Configuration _configuration;

	@Inject
	private static ConfigurationAdmin _configurationAdmin;

	@DeleteAfterTestRun
	private Group _group;

	@Inject(
		filter = "store.type=com.liferay.portal.store.gcs.GCSStore",
		type = Store.class
	)
	private Store _store;

}