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

import org.junit.After;
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
		_initalEntries = addManualAssetEntries(_NO_CATEGORIES, _NO_TAGS, 5);

		_permissionChecker = PermissionCheckerFactoryUtil.create(
			TestPropsValues.getUser());
	}

	@After
	public void tearDown() {
		_assetEntryXmls = null;
		_categoryIds = null;
		_expectedEntries = new ArrayList<AssetEntry>();
		_initalEntries = new ArrayList<AssetEntry>();
		_permissionChecker = null;
	}

	@Test
	@Transactional
	public void testGetAssetEntries() throws Exception {
		_expectedEntries = _initalEntries;

		List<AssetEntry> entries = AssetPublisherUtil.getAssetEntries(
			new MockPortletRequest(), new MockPortletPreferences(),
			_permissionChecker, new long[] {TestPropsValues.getGroupId()},
			_assetEntryXmls, false, false);

		Assert.assertEquals(5, entries.size());
		Assert.assertEquals(_expectedEntries, entries);
	}

	@Test
	@Transactional
	public void testGetAssetEntriesFilteredByCategories() throws Exception {
		addVocabulary();

		long[] allAssetCategoryIds =
			new long[] {_categoryIds[0], _categoryIds[1], _categoryIds[2]};

		_expectedEntries = addManualAssetEntries(
			allAssetCategoryIds, _NO_TAGS, 2);

		List<AssetEntry> entries = AssetPublisherUtil.getAssetEntries(
			new MockPortletRequest(), new MockPortletPreferences(),
			_permissionChecker, new long[] {TestPropsValues.getGroupId()},
			_assetEntryXmls, false, false);

		Assert.assertEquals(7, entries.size());

		List<AssetEntry> filteredEntries = AssetPublisherUtil.getAssetEntries(
			new MockPortletRequest(), new MockPortletPreferences(),
			_permissionChecker, new long[] {TestPropsValues.getGroupId()},
			allAssetCategoryIds, _assetEntryXmls, _NO_TAGS, false, false);

		Assert.assertEquals(2, filteredEntries.size());
		Assert.assertEquals(_expectedEntries, filteredEntries);
	}

	@Test
	@Transactional
	public void testGetAssetEntriesFilteredByCategoriesAndTags()
		throws Exception {

		addVocabulary();

		long[] allAssetCategoyIds = new long[] {
			_categoryIds[0], _categoryIds[1], _categoryIds[2], _categoryIds[3]};

		String[] allAssetTagNames = new String[] {_tagNames[0], _tagNames[1]};

		_expectedEntries = addManualAssetEntries(
			allAssetCategoyIds, allAssetTagNames, 2);

		List<AssetEntry> entries = AssetPublisherUtil.getAssetEntries(
			new MockPortletRequest(), new MockPortletPreferences(),
			_permissionChecker, new long[] {TestPropsValues.getGroupId()},
			_assetEntryXmls, false, false);

		Assert.assertEquals(7, entries.size());

		List<AssetEntry> filteredEntries = AssetPublisherUtil.getAssetEntries(
			new MockPortletRequest(), new MockPortletPreferences(),
			_permissionChecker, new long[] {TestPropsValues.getGroupId()},
			allAssetCategoyIds, _assetEntryXmls, allAssetTagNames, false,
			false);

		Assert.assertEquals(2, filteredEntries.size());
		Assert.assertEquals(_expectedEntries, filteredEntries);
	}

	@Test
	@Transactional
	public void testGetAssetEntriesFilteredByTags() throws Exception {
		String[] allAssetTagNames = new String[] {_tagNames[0], _tagNames[1]};

		_expectedEntries = addManualAssetEntries(
			_NO_CATEGORIES, allAssetTagNames, 2);

		List<AssetEntry> entries = AssetPublisherUtil.getAssetEntries(
			new MockPortletRequest(), new MockPortletPreferences(),
			_permissionChecker, new long[] {TestPropsValues.getGroupId()},
			_assetEntryXmls, false, false);

		Assert.assertEquals(7, entries.size());

		List<AssetEntry> filteredEntries = AssetPublisherUtil.getAssetEntries(
			new MockPortletRequest(), new MockPortletPreferences(),
			_permissionChecker, new long[] {TestPropsValues.getGroupId()},
			_NO_CATEGORIES, _assetEntryXmls, allAssetTagNames, false, false);

		Assert.assertEquals(2, filteredEntries.size());
		Assert.assertEquals(_expectedEntries, filteredEntries);
	}

	protected void addCategories(long vocabularyId) throws Exception {
		for (String categoryName : _categoryNames) {
			AssetCategory category = AssetCategoryLocalServiceUtil.addCategory(
				TestPropsValues.getUserId(), categoryName, vocabularyId,
				ServiceTestUtil.getServiceContext());

			_categoryIds = ArrayUtil.append(
				_categoryIds, category.getCategoryId());
		}
	}

	protected List<AssetEntry> addManualAssetEntries(
			long[] categoryIds, String[] tagNames, int number)
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

			_formatXml(assetEntry);
		}

		return assetEntries;
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

		sb .append("<?xml version=\"1.0\"?><asset-entry>");
		sb.append("<asset-entry-type>");
		sb.append(JournalArticle.class.getName());
		sb.append("</asset-entry-type><asset-entry-uuid>");
		sb.append(assetEntry.getClassUuid());
		sb.append("</asset-entry-uuid></asset-entry>");

		_assetEntryXmls = ArrayUtil.append(_assetEntryXmls, sb.toString());
	}

	private static final long[] _NO_CATEGORIES = new long[] {};

	private static final String[] _NO_TAGS = new String[] {};

	private String[] _assetEntryXmls = new String[] {};
	private long[] _categoryIds = new long[] {};
	private String[] _categoryNames =
		{"Athletic", "Barcelona", "RealMadrid", "Sevilla", "Sporting"};
	private List<AssetEntry> _expectedEntries = new ArrayList<AssetEntry>();
	private List<AssetEntry> _initalEntries = new ArrayList<AssetEntry>();
	private PermissionChecker _permissionChecker;
	private String[] _tagNames = {"basketball", "football", "tennis"};

}