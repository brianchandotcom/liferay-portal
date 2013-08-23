/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.assetpublisher.util;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.util.JournalTestUtil;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.portlet.MockPortletPreferences;
import org.springframework.mock.web.portlet.MockPortletRequest;

/**
 * @author Roberto Díaz
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class AssetPublisherServiceTest {

	@Before
	public void setUp() throws Exception {
		_initialAssetEntries = addAssetEntries(
			_NO_CATEGORY_IDS, _NO_TAG_NAMES, 5, true);

		_permissionChecker = PermissionCheckerFactoryUtil.create(
			TestPropsValues.getUser());
	}

	@Test
	@Transactional
	public void testGetAssetEntries() throws Exception {
		_expectedAssetEntries = _initialAssetEntries;

		List<AssetEntry> assetEntries = AssetPublisherUtil.getAssetEntries(
			new MockPortletRequest(), new MockPortletPreferences(),
			_permissionChecker, new long[] {TestPropsValues.getGroupId()},
			_assetEntryXmls, false, false);

		Assert.assertEquals(_expectedAssetEntries, assetEntries);
	}

	@Test
	@Transactional
	public void testGetAssetEntriesFilteredByCategories() throws Exception {
		addVocabulary();

		long[] allCategoryIds =
			new long[] {_categoryIds[0], _categoryIds[1], _categoryIds[2]};

		_expectedAssetEntries = addAssetEntries(
			allCategoryIds, _NO_TAG_NAMES, 2, true);

		List<AssetEntry> assetEntries = AssetPublisherUtil.getAssetEntries(
			new MockPortletRequest(), new MockPortletPreferences(),
			_permissionChecker, new long[] {TestPropsValues.getGroupId()},
			_assetEntryXmls, false, false);

		Assert.assertEquals(7, assetEntries.size());

		List<AssetEntry> filteredAsssetEntries =
			AssetPublisherUtil.getAssetEntries(
				new MockPortletRequest(), new MockPortletPreferences(),
				_permissionChecker, new long[] {TestPropsValues.getGroupId()},
				allCategoryIds, _assetEntryXmls, _NO_TAG_NAMES, false, false);

		Assert.assertEquals(_expectedAssetEntries, filteredAsssetEntries);
	}

	@Test
	@Transactional
	public void testGetAssetEntriesFilteredByCategoriesAndTags()
		throws Exception {

		addVocabulary();

		long[] allCategoyIds = new long[] {
			_categoryIds[0], _categoryIds[1], _categoryIds[2], _categoryIds[3]};

		String[] allTagNames = new String[] {_TAG_NAMES[0], _TAG_NAMES[1]};

		_expectedAssetEntries = addAssetEntries(
			allCategoyIds, allTagNames, 2, true);

		List<AssetEntry> assetEntries = AssetPublisherUtil.getAssetEntries(
			new MockPortletRequest(), new MockPortletPreferences(),
			_permissionChecker, new long[] {TestPropsValues.getGroupId()},
			_assetEntryXmls, false, false);

		Assert.assertEquals(7, assetEntries.size());

		List<AssetEntry> filteredAssetEntries =
			AssetPublisherUtil.getAssetEntries(
				new MockPortletRequest(), new MockPortletPreferences(),
				_permissionChecker, new long[] {TestPropsValues.getGroupId()},
				allCategoyIds, _assetEntryXmls, allTagNames, false, false);

		Assert.assertEquals(_expectedAssetEntries, filteredAssetEntries);
	}

	@Test
	@Transactional
	public void testGetAssetEntriesFilteredByTags() throws Exception {
		String[] allTagNames = new String[] {_TAG_NAMES[0], _TAG_NAMES[1]};

		_expectedAssetEntries = addAssetEntries(
			_NO_CATEGORY_IDS, allTagNames, 2, true);

		List<AssetEntry> assetEntries = AssetPublisherUtil.getAssetEntries(
			new MockPortletRequest(), new MockPortletPreferences(),
			_permissionChecker, new long[] {TestPropsValues.getGroupId()},
			_assetEntryXmls, false, false);

		Assert.assertEquals(7, assetEntries.size());

		List<AssetEntry> filteredAssetEntries =
			AssetPublisherUtil.getAssetEntries(
				new MockPortletRequest(), new MockPortletPreferences(),
				_permissionChecker, new long[] {TestPropsValues.getGroupId()},
				_NO_CATEGORY_IDS, _assetEntryXmls, allTagNames, false, false);

		Assert.assertEquals(_expectedAssetEntries, filteredAssetEntries);
	}

	protected List<AssetEntry> addAssetEntries(
			long[] categoryIds, String[] tagNames, int number,
			boolean manualMode)
		throws Exception {

		List<AssetEntry> assetEntries = new ArrayList<AssetEntry>();

		for (int i = 0; i < number; i++) {
			JournalArticle article = JournalTestUtil.addArticle(
				TestPropsValues.getGroupId(), ServiceTestUtil.randomString(),
				ServiceTestUtil.randomString(100));

			JournalArticleLocalServiceUtil.updateAsset(
				TestPropsValues.getUserId(), article, categoryIds, tagNames,
				null);

			AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
				JournalArticle.class.getName(), article.getResourcePrimKey());

			assetEntries.add(assetEntry);

			if (manualMode) {
				_formatXml(assetEntry);
			}
		}

		return assetEntries;
	}

	protected void addCategories(long vocabularyId) throws Exception {
		for (String categoryName : _CATEGORY_NAMES) {
			AssetCategory category = AssetCategoryLocalServiceUtil.addCategory(
				TestPropsValues.getUserId(), categoryName, vocabularyId,
				ServiceTestUtil.getServiceContext());

			_categoryIds = ArrayUtil.append(
				_categoryIds, category.getCategoryId());
		}
	}

	protected void addVocabulary() throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			TestPropsValues.getGroupId());

		serviceContext.setAddGroupPermissions(false);
		serviceContext.setAddGuestPermissions(false);

		AssetVocabulary vocabulary =
			AssetVocabularyLocalServiceUtil.addVocabulary(
				TestPropsValues.getUserId(), ServiceTestUtil.randomString(),
				ServiceTestUtil.getServiceContext(
					TestPropsValues.getGroupId()));

		addCategories(vocabulary.getVocabularyId());
	}

	private void _formatXml(AssetEntry assetEntry) {
		StringBuilder sb = new StringBuilder(6);

		sb.append("<?xml version=\"1.0\"?><asset-entry>");
		sb.append("<asset-entry-type>");
		sb.append(JournalArticle.class.getName());
		sb.append("</asset-entry-type><asset-entry-uuid>");
		sb.append(assetEntry.getClassUuid());
		sb.append("</asset-entry-uuid></asset-entry>");

		_assetEntryXmls = ArrayUtil.append(_assetEntryXmls, sb.toString());
	}

	private static final String[] _CATEGORY_NAMES =
		{"Athletic", "Barcelona", "RealMadrid", "Sevilla", "Sporting"};

	private static final long[] _NO_CATEGORY_IDS = new long[0];

	private static final String[] _NO_TAG_NAMES = new String[0];

	private static final String[] _TAG_NAMES =
		{"basketball", "football", "tennis"};

	private String[] _assetEntryXmls = new String[0];
	private long[] _categoryIds = new long[0];
	private List<AssetEntry> _expectedAssetEntries =
		new ArrayList<AssetEntry>();
	private List<AssetEntry> _initialAssetEntries = new ArrayList<AssetEntry>();
	private PermissionChecker _permissionChecker;

}