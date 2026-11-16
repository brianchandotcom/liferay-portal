/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.storage.helper.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.store.DLStore;
import com.liferay.document.library.kernel.store.DLStoreRequest;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.site.constants.SitemapConstants;
import com.liferay.site.storage.helper.SitemapStorageHelper;

import java.nio.charset.StandardCharsets;

import java.util.Arrays;
import java.util.Collections;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Cheryl Tang
 */
@RunWith(Arquillian.class)
public class SitemapStorageHelperTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_originalMaxTotalSize = ReflectionTestUtil.getAndSetFieldValue(
			_sitemapStorageHelper, "_maxTotalSize", 0L);

		ReflectionTestUtil.invoke(
			_sitemapStorageHelper, "_moveSitemapFilesToDLStore",
			new Class<?>[0]);

		_group1 = GroupTestUtil.addGroup();
		_group2 = GroupTestUtil.addGroup();
		_pageLayoutChildSitemapKey1 =
			SitemapConstants.INDEX_MODE_PAGE_LAYOUT + StringPool.SLASH +
				PortalUUIDUtil.generate();
		_pageLayoutChildSitemapKey2 =
			SitemapConstants.INDEX_MODE_PAGE_LAYOUT + StringPool.SLASH +
				PortalUUIDUtil.generate();
	}

	@After
	public void tearDown() throws Exception {
		ReflectionTestUtil.invoke(
			_sitemapStorageHelper, "_moveSitemapFilesToDLStore",
			new Class<?>[0]);

		ReflectionTestUtil.setFieldValue(
			_sitemapStorageHelper, "_maxTotalSize", _originalMaxTotalSize);

		_sitemapStorageHelper.deleteSitemaps(TestPropsValues.getCompanyId());
	}

	@Test
	public void testDeleteSitemaps() throws Exception {
		long companyId = TestPropsValues.getCompanyId();

		_storeSitemapFiles(companyId, _group1.getGroupId());
		_storeSitemapFiles(companyId, _group2.getGroupId());

		_sitemapStorageHelper.storeLastRegenerateSitemapDateFile(companyId);

		_sitemapStorageHelper.deleteSitemaps(companyId);

		Assert.assertFalse(_sitemapStorageHelper.hasSitemapFiles(companyId));
		Assert.assertNull(
			_sitemapStorageHelper.getLastRegenerateSitemapDate(companyId));
	}

	@Test
	public void testDeleteSitemapsByChildSitemapKey() throws Exception {
		long companyId = TestPropsValues.getCompanyId();

		_storeSitemapFiles(companyId, _group1.getGroupId());

		_sitemapStorageHelper.storeSitemapFile(
			companyId, _group1.getGroupId(), _pageLayoutChildSitemapKey2, 1,
			RandomTestUtil.randomString());

		_sitemapStorageHelper.deleteSitemaps(
			companyId, _group1.getGroupId(), _pageLayoutChildSitemapKey1);

		for (int page = 1; page <= 2; page++) {
			Assert.assertTrue(
				_sitemapStorageHelper.hasSitemapFile(
					companyId, _group1.getGroupId(),
					SitemapConstants.ASSET_TYPE_KEY_WEB_CONTENT, page));
			Assert.assertFalse(
				_sitemapStorageHelper.hasSitemapFile(
					companyId, _group1.getGroupId(),
					_pageLayoutChildSitemapKey1, page));
		}

		Assert.assertTrue(
			_sitemapStorageHelper.hasSitemapFile(
				companyId, _group1.getGroupId(), _pageLayoutChildSitemapKey2,
				1));
	}

	@Test
	public void testDeleteSitemapsByGroup() throws Exception {
		long companyId = TestPropsValues.getCompanyId();

		_storeSitemapFiles(companyId, _group1.getGroupId());
		_storeSitemapFiles(companyId, _group2.getGroupId());

		_sitemapStorageHelper.deleteSitemaps(companyId, _group1.getGroupId());

		_assertHasSitemapFiles(false, companyId, _group1.getGroupId());
		_assertHasSitemapFiles(true, companyId, _group2.getGroupId());
	}

	@Test
	public void testGetPageCountsByChildSitemapKey() throws Exception {
		long companyId = TestPropsValues.getCompanyId();

		_storeSitemapFiles(companyId, _group1.getGroupId());

		_sitemapStorageHelper.storeSitemapFile(
			companyId, _group1.getGroupId(), _pageLayoutChildSitemapKey2, 1,
			RandomTestUtil.randomString());

		Assert.assertEquals(
			HashMapBuilder.put(
				_pageLayoutChildSitemapKey1, 2
			).put(
				_pageLayoutChildSitemapKey2, 1
			).put(
				SitemapConstants.ASSET_TYPE_KEY_WEB_CONTENT, 2
			).build(),
			_sitemapStorageHelper.getPageCountsByChildSitemapKey(
				companyId, _group1.getGroupId()));
		Assert.assertEquals(
			Collections.emptyMap(),
			_sitemapStorageHelper.getPageCountsByChildSitemapKey(
				companyId, _group2.getGroupId()));
	}

	@Test
	public void testMoveSitemapFilesToPortalCache() throws Exception {
		ReflectionTestUtil.setFieldValue(
			_sitemapStorageHelper, "_maxTotalSize", _originalMaxTotalSize);

		long companyId = TestPropsValues.getCompanyId();

		_storeSitemapFiles(companyId, _group1.getGroupId());

		String xml = StringUtil.read(
			_sitemapStorageHelper.getSitemapInputStream(
				companyId, _group1.getGroupId()));

		_sitemapStorageHelper.storeLastRegenerateSitemapDateFile(companyId);

		_assertStorage(companyId, false);

		long storageMetadataSize = _getStorageMetadataSize(companyId);

		_sitemapStorageHelper.moveSitemapFilesToPortalCache();

		_assertStorage(companyId, true);
		Assert.assertEquals(
			storageMetadataSize, _getStorageMetadataSize(companyId));
		_assertHasSitemapFiles(true, companyId, _group1.getGroupId());
		Assert.assertEquals(
			xml,
			StringUtil.read(
				_sitemapStorageHelper.getSitemapInputStream(
					companyId, _group1.getGroupId())));

		_storeSitemapFiles(companyId, _group2.getGroupId());

		Assert.assertEquals(
			HashMapBuilder.put(
				_pageLayoutChildSitemapKey1, 2
			).put(
				SitemapConstants.ASSET_TYPE_KEY_WEB_CONTENT, 2
			).build(),
			_sitemapStorageHelper.getPageCountsByChildSitemapKey(
				companyId, _group2.getGroupId()));

		_sitemapStorageHelper.deleteSitemaps(companyId, _group2.getGroupId());

		_assertHasSitemapFiles(false, companyId, _group2.getGroupId());

		_sitemapStorageHelper.storeLastRegenerateSitemapDateFile(companyId);

		_assertStorage(companyId, true);
		Assert.assertEquals(
			storageMetadataSize, _getStorageMetadataSize(companyId));

		ReflectionTestUtil.setFieldValue(
			_sitemapStorageHelper, "_maxTotalSize", 1L);

		_storeSitemapFiles(companyId, _group2.getGroupId());

		_sitemapStorageHelper.storeLastRegenerateSitemapDateFile(companyId);

		_assertStorage(companyId, false);
		_assertHasSitemapFiles(true, companyId, _group1.getGroupId());
		Assert.assertEquals(
			xml,
			StringUtil.read(
				_sitemapStorageHelper.getSitemapInputStream(
					companyId, _group1.getGroupId())));

		_sitemapStorageHelper.moveSitemapFilesToPortalCache();

		_assertStorage(companyId, false);

		_sitemapStorageHelper.deleteSitemaps(companyId, _group1.getGroupId());

		_sitemapStorageHelper.storeLastRegenerateSitemapDateFile(companyId);

		_assertStorage(companyId, false);
	}

	@Test
	public void testMoveSitemapFilesToPortalCacheWithStaleSize()
		throws Exception {

		long companyId = TestPropsValues.getCompanyId();

		String fileName = StringBundler.concat(
			"sitemaps/", _group1.getGroupId(), StringPool.SLASH,
			SitemapConstants.ASSET_TYPE_KEY_WEB_CONTENT, "/1.xml");

		String xml = RandomTestUtil.randomString(100);

		byte[] bytes = xml.getBytes(StandardCharsets.UTF_8);

		DLStoreRequest dlStoreRequest = DLStoreRequest.builder(
			companyId, CompanyConstants.SYSTEM, fileName
		).size(
			bytes.length
		).versionLabel(
			Store.VERSION_DEFAULT
		).build();

		_dlStore.addFile(dlStoreRequest, bytes);

		ReflectionTestUtil.setFieldValue(
			_sitemapStorageHelper, "_maxTotalSize", 50L);

		_sitemapStorageHelper.moveSitemapFilesToPortalCache();

		_assertStorage(companyId, false);
		Assert.assertEquals(100, _getStorageMetadataSize(companyId));

		ReflectionTestUtil.setFieldValue(
			_sitemapStorageHelper, "_maxTotalSize", _originalMaxTotalSize);

		_sitemapStorageHelper.moveSitemapFilesToPortalCache();

		_assertStorage(companyId, true);

		PortalCache<?, ?> portalCache = ReflectionTestUtil.getFieldValue(
			_sitemapStorageHelper, "_portalCache");

		portalCache.removeAll();

		ReflectionTestUtil.setFieldValue(
			_sitemapStorageHelper, "_maxTotalSize", 150L);

		_sitemapStorageHelper.storeSitemapFile(
			companyId, _group2.getGroupId(), RandomTestUtil.randomString(60));

		_sitemapStorageHelper.storeLastRegenerateSitemapDateFile(companyId);

		Assert.assertTrue(
			_sitemapStorageHelper.hasSitemapFile(
				companyId, _group2.getGroupId()));

		_assertStorage(companyId, true);
		Assert.assertEquals(60, _getStorageMetadataSize(companyId));
	}

	private void _assertHasSitemapFiles(
			boolean expected, long companyId, long groupId)
		throws Exception {

		Assert.assertEquals(
			expected, _sitemapStorageHelper.hasSitemapFile(companyId, groupId));

		for (int page = 1; page <= 2; page++) {
			Assert.assertEquals(
				expected,
				_sitemapStorageHelper.hasSitemapFile(
					companyId, groupId,
					SitemapConstants.ASSET_TYPE_KEY_WEB_CONTENT, page));
			Assert.assertEquals(
				expected,
				_sitemapStorageHelper.hasSitemapFile(
					companyId, groupId, _pageLayoutChildSitemapKey1, page));
		}
	}

	private void _assertStorage(long companyId, boolean portalCacheStorage)
		throws Exception {

		Assert.assertEquals(
			portalCacheStorage, _sitemapStorageHelper.isPortalCacheStorage());

		String[] fileNames = _dlStore.getFileNames(
			companyId, CompanyConstants.SYSTEM, "sitemaps");

		if (portalCacheStorage) {
			Assert.assertEquals(
				Arrays.toString(fileNames), 0, fileNames.length);

			return;
		}

		long size = 0;

		for (String fileName : fileNames) {
			size += _dlStore.getFileSize(
				companyId, CompanyConstants.SYSTEM, fileName);
		}

		Assert.assertEquals(size, _getStorageMetadataSize(companyId));
	}

	private long _getStorageMetadataSize(long companyId) throws Exception {
		JSONObject storageMetadataJSONObject = _jsonFactory.createJSONObject(
			StringUtil.read(
				_dlStore.getFileAsStream(
					_portal.getDefaultCompanyId(), CompanyConstants.SYSTEM,
					"sitemap-metadata/storage", Store.VERSION_DEFAULT)));

		JSONObject sizesJSONObject = storageMetadataJSONObject.getJSONObject(
			"sizes");

		return sizesJSONObject.getLong(String.valueOf(companyId));
	}

	private void _storeSitemapFiles(long companyId, long groupId)
		throws Exception {

		_sitemapStorageHelper.storeSitemapFile(
			companyId, groupId, RandomTestUtil.randomString());

		for (int page = 1; page <= 2; page++) {
			_sitemapStorageHelper.storeSitemapFile(
				companyId, groupId, SitemapConstants.ASSET_TYPE_KEY_WEB_CONTENT,
				page, RandomTestUtil.randomString());
			_sitemapStorageHelper.storeSitemapFile(
				companyId, groupId, _pageLayoutChildSitemapKey1, page,
				RandomTestUtil.randomString());
		}

		_assertHasSitemapFiles(true, companyId, groupId);
	}

	@Inject
	private DLStore _dlStore;

	@DeleteAfterTestRun
	private Group _group1;

	@DeleteAfterTestRun
	private Group _group2;

	@Inject
	private JSONFactory _jsonFactory;

	private long _originalMaxTotalSize;
	private String _pageLayoutChildSitemapKey1;
	private String _pageLayoutChildSitemapKey2;

	@Inject
	private Portal _portal;

	@Inject
	private SitemapStorageHelper _sitemapStorageHelper;

}