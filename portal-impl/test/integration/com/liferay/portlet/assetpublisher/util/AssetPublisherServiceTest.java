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
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.util.JournalTestUtil;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
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

	@BeforeClass
	public static void setUpClass() throws Exception {
		_permissionChecker = PermissionCheckerFactoryUtil.create(
			TestPropsValues.getUser());
	}

	@Before
	public void setUp() throws Exception {
		AssetVocabulary assetVocabulary =
			AssetVocabularyLocalServiceUtil.fetchGroupVocabulary(
				TestPropsValues.getGroupId(), "Spanish_Teams");

		if (assetVocabulary != null) {
			AssetVocabularyLocalServiceUtil.deleteAssetVocabulary(
				assetVocabulary.getVocabularyId());
		}
	}

	@After
	public void tearDown() throws Exception {
		_expectedEntries = new ArrayList<AssetEntry>();

		if (_vocabulary != null) {
			AssetVocabularyLocalServiceUtil.deleteAssetVocabulary(
				_vocabulary.getVocabularyId());
		}

		_vocabulary = null;
	}

	@Test
	@Transactional
	public void testGetAssetEntries() throws Exception {
		addJournalArticles(false, false);

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
		addJournalArticles(true, false);

		long[] allAssetCategoryIds =
			new long[] {_categoryIds[0], _categoryIds[1], _categoryIds[2]};

		List<AssetEntry> entries = AssetPublisherUtil.getAssetEntries(
			new MockPortletRequest(), new MockPortletPreferences(),
			_permissionChecker, new long[] {TestPropsValues.getGroupId()},
			_assetEntryXmls, false, false, allAssetCategoryIds, _NO_TAGS);

		Assert.assertEquals(1, entries.size());
		Assert.assertEquals(_expectedEntries, entries);
	}

	@Test
	@Transactional
	public void testGetAssetEntriesFilteredByCategoriesAndTags()
		throws Exception {

		addJournalArticles(true, true);

		long[] allAssetCategoyIds = new long[] {
			_categoryIds[0], _categoryIds[1], _categoryIds[2], _categoryIds[3]};

		String[] allAssetTagNames = new String[] {_tagNames[0], _tagNames[1]};

		List<AssetEntry> entries = AssetPublisherUtil.getAssetEntries(
			new MockPortletRequest(), new MockPortletPreferences(),
			_permissionChecker, new long[] {TestPropsValues.getGroupId()},
			_assetEntryXmls, false, false, allAssetCategoyIds,
			allAssetTagNames);

		Assert.assertEquals(1, entries.size());
		Assert.assertEquals(_expectedEntries, entries);
	}

	@Test
	@Transactional
	public void testGetAssetEntriesFilteredByTags() throws Exception {
		addJournalArticles(false, true);

		String[] allAssetTagNames = new String[] {_tagNames[0], _tagNames[1]};

		List<AssetEntry> entries = AssetPublisherUtil.getAssetEntries(
			new MockPortletRequest(), new MockPortletPreferences(),
			_permissionChecker, new long[] {TestPropsValues.getGroupId()},
			_assetEntryXmls, false, false, _NO_CATEGORIES, allAssetTagNames);

		Assert.assertEquals(1, entries.size());
		Assert.assertEquals(_expectedEntries, entries);
	}

	protected void _addArticles() throws Exception {
		_addArticles(_NO_CATEGORIES, _NO_TAGS, 5, true);
	}

	protected void _addArticles(
			long[] categoryIds, String[] tagNames, int number, boolean expected)
		throws Exception {

		for (int i = 0; i < number; i++) {
			JournalArticle article = JournalTestUtil.addArticle(
				TestPropsValues.getGroupId(), ServiceTestUtil.randomString(),
				ServiceTestUtil.randomString(100));

			JournalArticleLocalServiceUtil.updateAsset(
				TestPropsValues.getUserId(), article, categoryIds, tagNames,
				null);

			StringBuilder sb = new StringBuilder(6);

			AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
				JournalArticle.class.getName(), article.getResourcePrimKey());

			if (expected) {
				_expectedEntries.add(assetEntry);
			}

			sb .append("<?xml version=\"1.0\"?><asset-entry>");
			sb.append("<asset-entry-type>");
			sb.append(JournalArticle.class.getName());
			sb.append("</asset-entry-type><asset-entry-uuid>");
			sb.append(assetEntry.getClassUuid());
			sb.append("</asset-entry-uuid></asset-entry>");

			_assetEntryXmls = ArrayUtil.append(_assetEntryXmls, sb.toString());
		}
	}

	protected void _addCategorizedArticles() throws Exception {
		_addArticles(new long[] {_categoryIds[0]}, _NO_TAGS, 1, false);
		_addArticles(new long[] {_categoryIds[1]}, _NO_TAGS, 2, false);
		_addArticles(new long[] {_categoryIds[2]}, _NO_TAGS, 1, false);
		_addArticles(new long[] {_categoryIds[3]}, _NO_TAGS, 2, false);
		_addArticles(new long[] {_categoryIds[4]}, _NO_TAGS, 1, false);

		_addArticles(
			new long[] {_categoryIds[0], _categoryIds[1], _categoryIds[2]},
			_NO_TAGS, 1, true);
	}

	protected void addJournalArticles(boolean hasCategories, boolean hasTags)
		throws Exception {

		if (hasCategories && hasTags) {
			_addVocabulary();

			_addTags();

			_addCategorizedAndTaggedArticles();
		}
		else if (hasCategories) {
			_addVocabulary();

			_addCategorizedArticles();
		}
		else if (hasTags) {
			_addTags();

			_addTaggedArticles();
		}
		else {
			_addArticles();
		}
	}

	private void _addCategories(long vocabularyId) throws Exception {
		for (String categoryName : _categoryNames) {
			AssetCategory category = AssetCategoryLocalServiceUtil.addCategory(
				TestPropsValues.getUserId(), categoryName, vocabularyId,
				ServiceTestUtil.getServiceContext());

			_categoryIds = ArrayUtil.append(
				_categoryIds, category.getCategoryId());
		}
	}

	private void _addCategorizedAndTaggedArticles() throws Exception {
		_addArticles(
			new long[] {_categoryIds[0]}, new String[] {_tagNames[0]}, 1,
			false);
		_addArticles(
			new long[] {_categoryIds[1]}, new String[] {_tagNames[1]}, 2,
			false);
		_addArticles(
			new long[] {_categoryIds[2]}, new String[] {_tagNames[2]}, 1,
			false);
		_addArticles(
			new long[] {_categoryIds[3]}, new String[] {_tagNames[1]}, 2,
			false);
		_addArticles(
			new long[] {
				_categoryIds[0], _categoryIds[1], _categoryIds[2]},
			new String[] {_tagNames[0], _tagNames[1]}, 1, false);
		_addArticles(
			new long[] {
				_categoryIds[0], _categoryIds[1], _categoryIds[2],
				_categoryIds[3]},
			new String[] {_tagNames[0]}, 1, false);
		_addArticles(
			new long[] {
				_categoryIds[0], _categoryIds[1], _categoryIds[2],
				_categoryIds[3]},
			new String[] {_tagNames[0], _tagNames[1]}, 1, true);
	}

	private void _addTaggedArticles() throws Exception {
		_addArticles(_NO_CATEGORIES, new String[] {_tagNames[0]}, 1, false);
		_addArticles(_NO_CATEGORIES, new String[] {_tagNames[1]}, 2, false);
		_addArticles(_NO_CATEGORIES, new String[] {_tagNames[2]}, 1, false);
		_addArticles(
			_NO_CATEGORIES, new String[] {_tagNames[0], _tagNames[1]}, 1, true);
	}

	private void _addTags() throws Exception {
		for (String tagName : _tagNames) {
			AssetTagLocalServiceUtil.addTag(
				TestPropsValues.getUserId(), tagName, new String[] {},
				ServiceTestUtil.getServiceContext());
		}
	}

	private void _addVocabulary() throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			TestPropsValues.getGroupId());

		serviceContext.setAddGroupPermissions(false);
		serviceContext.setAddGuestPermissions(false);

		_vocabulary = AssetVocabularyLocalServiceUtil.addVocabulary(
			TestPropsValues.getUserId(), "Spanish_Teams",
			ServiceTestUtil.getServiceContext(TestPropsValues.getGroupId()));

		_addCategories(_vocabulary.getVocabularyId());
	}

	private static final long[] _NO_CATEGORIES = new long[] {};

	private static final String[] _NO_TAGS = new String[] {};

	private static PermissionChecker _permissionChecker;

	private String[] _assetEntryXmls = new String[] {};
	private long[] _categoryIds = new long[] {};
	private String[] _categoryNames =
		{"Athletic", "Barcelona", "RealMadrid", "Sevilla", "Sporting"};
	private List<AssetEntry> _expectedEntries = new ArrayList<AssetEntry>();
	private String[] _tagNames = {"basketball", "football", "tennis"};
	private AssetVocabulary _vocabulary;

}