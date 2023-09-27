/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.store.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.store.DLStoreRequest;
import com.liferay.document.library.kernel.store.DLStoreUtil;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.document.library.kernel.store.StoreArea;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.AssumeTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Assume;
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
public class DLStoreStoreAreaTest {

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

	@Test
	public void testAddFile() throws Exception {
		String fileName = StringUtil.randomString();

		_addFile(fileName, Store.VERSION_DEFAULT);

		Assert.assertTrue(
			DLStoreUtil.hasFile(
				TestPropsValues.getCompanyId(), TestPropsValues.getGroupId(),
				fileName, Store.VERSION_DEFAULT));

		StoreArea.withStoreArea(
			StoreArea.NEW,
			() -> _assertHasStoreFile(fileName, Store.VERSION_DEFAULT));
	}

	@Test
	public void testDeleteDirectory() throws Exception {
		String fileName = StringUtil.randomString();

		_addFile(fileName, Store.VERSION_DEFAULT);

		DLStoreUtil.deleteDirectory(
			TestPropsValues.getCompanyId(), TestPropsValues.getGroupId(),
			StringPool.BLANK);

		Assert.assertFalse(
			DLStoreUtil.hasFile(
				TestPropsValues.getCompanyId(), TestPropsValues.getGroupId(),
				fileName, Store.VERSION_DEFAULT));

		StoreArea.withStoreArea(
			StoreArea.DELETED,
			() -> _assertHasStoreFile(fileName, Store.VERSION_DEFAULT));
	}

	@Test
	public void testDeleteFile() throws Exception {
		String fileName = StringUtil.randomString();

		_addFile(fileName, Store.VERSION_DEFAULT);

		DLStoreUtil.deleteFile(
			TestPropsValues.getCompanyId(), TestPropsValues.getGroupId(),
			fileName, Store.VERSION_DEFAULT);

		Assert.assertFalse(
			DLStoreUtil.hasFile(
				TestPropsValues.getCompanyId(), TestPropsValues.getGroupId(),
				fileName, Store.VERSION_DEFAULT));

		StoreArea.withStoreArea(
			StoreArea.DELETED,
			() -> _assertHasStoreFile(fileName, Store.VERSION_DEFAULT));
	}

	@Test
	public void testDeleteFileWithMultipleVersions() throws Exception {
		String fileName = StringUtil.randomString();

		_addFile(fileName, Store.VERSION_DEFAULT);
		_addFile(fileName, "2.0");

		DLStoreUtil.deleteDirectory(
			TestPropsValues.getCompanyId(), TestPropsValues.getGroupId(),
			fileName);

		Assert.assertFalse(
			DLStoreUtil.hasFile(
				TestPropsValues.getCompanyId(), TestPropsValues.getGroupId(),
				fileName, Store.VERSION_DEFAULT));
		Assert.assertFalse(
			DLStoreUtil.hasFile(
				TestPropsValues.getCompanyId(), TestPropsValues.getGroupId(),
				fileName, "2.0"));

		StoreArea.withStoreArea(
			StoreArea.DELETED,
			() -> {
				_assertHasStoreFile(fileName, Store.VERSION_DEFAULT);
				_assertHasStoreFile(fileName, "2.0");
			});
	}

	@Test
	public void testUpdateFile() throws Exception {
		String fileName = StringUtil.randomString();

		_addFile(fileName, Store.VERSION_DEFAULT);

		Company company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		long newRepositoryId = company.getGroupId();

		DLStoreUtil.updateFile(
			TestPropsValues.getCompanyId(), TestPropsValues.getGroupId(),
			newRepositoryId, fileName);

		Assert.assertFalse(
			DLStoreUtil.hasFile(
				TestPropsValues.getCompanyId(), TestPropsValues.getGroupId(),
				fileName, Store.VERSION_DEFAULT));
		Assert.assertTrue(
			DLStoreUtil.hasFile(
				TestPropsValues.getCompanyId(), newRepositoryId, fileName,
				Store.VERSION_DEFAULT));

		StoreArea.withStoreArea(
			StoreArea.DELETED,
			() -> _assertHasStoreFile(fileName, Store.VERSION_DEFAULT));
	}

	@Test
	public void testUpdateFileVersion() throws Exception {
		String fileName = StringUtil.randomString();

		_addFile(fileName, Store.VERSION_DEFAULT);

		DLStoreUtil.updateFileVersion(
			TestPropsValues.getCompanyId(), TestPropsValues.getGroupId(),
			fileName, Store.VERSION_DEFAULT, "2.0");

		Assert.assertFalse(
			DLStoreUtil.hasFile(
				TestPropsValues.getCompanyId(), TestPropsValues.getGroupId(),
				fileName, Store.VERSION_DEFAULT));
		Assert.assertTrue(
			DLStoreUtil.hasFile(
				TestPropsValues.getCompanyId(), TestPropsValues.getGroupId(),
				fileName, "2.0"));

		StoreArea.withStoreArea(
			StoreArea.DELETED,
			() -> _assertHasStoreFile(fileName, Store.VERSION_DEFAULT));
		StoreArea.withStoreArea(
			StoreArea.NEW, () -> _assertHasStoreFile(fileName, "2.0"));
	}

	private void _addFile(String fileName, String version) throws Exception {
		DLStoreUtil.addFile(
			DLStoreRequest.builder(
				TestPropsValues.getCompanyId(), TestPropsValues.getGroupId(),
				fileName
			).versionLabel(
				version
			).build(),
			new byte[0]);
	}

	private void _assertHasStoreFile(String fileName, String version)
		throws Exception {

		Assert.assertTrue(
			_store.hasFile(
				TestPropsValues.getCompanyId(), TestPropsValues.getGroupId(),
				fileName, version));
	}

	private static Configuration _configuration;

	@Inject
	private static ConfigurationAdmin _configurationAdmin;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject(
		filter = "store.type=com.liferay.portal.store.gcs.GCSStore",
		type = Store.class
	)
	private Store _store;

}