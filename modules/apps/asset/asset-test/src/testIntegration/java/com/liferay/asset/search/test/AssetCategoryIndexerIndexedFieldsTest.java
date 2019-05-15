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

package com.liferay.asset.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.test.util.FieldValuesAssert;
import com.liferay.portal.search.test.util.IndexedFieldsFixture;
import com.liferay.portal.search.test.util.IndexerFixture;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.users.admin.test.util.search.UserSearchFixture;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Igor Fabiano Nazar
 * @author Luan Maoski
 * @author Lucas Marques
 */
@RunWith(Arquillian.class)
public class AssetCategoryIndexerIndexedFieldsTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		setUpUserSearchFixture();

		setUpAssetCategoryFixture();

		setUpAssetCategoryIndexerFixture();

		setUpIndexedFieldsFixture();
	}

	@Test
	public void testIndexedFields() throws Exception {
		Locale locale = LocaleUtil.JAPAN;

		setTestLocale(locale);

		AssetCategory assetCategory = assetCategoryFixture.createAssetCategory(
			"新しい商品");

		String searchTerm = "新しい";

		Document document = assetCategoryIndexerFixture.searchOnlyOne(
			searchTerm, locale);

		indexedFieldsFixture.postProcessDocument(document);

		FieldValuesAssert.assertFieldValues(
			_expectedFieldValues(assetCategory), document, searchTerm);
	}

	protected void setTestLocale(Locale locale) throws Exception {
		assetCategoryFixture.updateDisplaySettings(locale);

		LocaleThreadLocal.setDefaultLocale(locale);
	}

	protected void setUpAssetCategoryFixture() throws Exception {
		assetCategoryFixture = new AssetCategoryFixture(_group);

		_assetCategories = assetCategoryFixture.getAssetCategories();
		_assetVocabularies = assetCategoryFixture.getAssetVocabularies();
	}

	protected void setUpAssetCategoryIndexerFixture() {
		assetCategoryIndexerFixture = new IndexerFixture<>(AssetCategory.class);
	}

	protected void setUpIndexedFieldsFixture() {
		indexedFieldsFixture = new IndexedFieldsFixture(
			resourcePermissionLocalService, searchEngineHelper);
	}

	protected void setUpUserSearchFixture() throws Exception {
		userSearchFixture = new UserSearchFixture();

		userSearchFixture.setUp();

		_group = userSearchFixture.addGroup();

		_groups = userSearchFixture.getGroups();

		_users = userSearchFixture.getUsers();
	}

	protected AssetCategoryFixture assetCategoryFixture;
	protected IndexerFixture<AssetCategory> assetCategoryIndexerFixture;
	protected IndexedFieldsFixture indexedFieldsFixture;

	@Inject
	protected ResourcePermissionLocalService resourcePermissionLocalService;

	@Inject
	protected SearchEngineHelper searchEngineHelper;

	protected UserSearchFixture userSearchFixture;

	private Map<String, String> _expectedFieldValues(
			AssetCategory assetCategory)
		throws Exception {

		Map<String, String> map = new HashMap<>();

		map.put(
			Field.ASSET_CATEGORY_ID,
			String.valueOf(assetCategory.getCategoryId()));
		map.put(
			Field.ASSET_CATEGORY_TITLE,
			StringUtil.lowerCase(assetCategory.getName()));
		map.put(
			Field.ASSET_VOCABULARY_ID,
			String.valueOf(assetCategory.getVocabularyId()));
		map.put(Field.COMPANY_ID, String.valueOf(assetCategory.getCompanyId()));
		map.put(Field.ENTRY_CLASS_NAME, AssetCategory.class.getName());
		map.put(
			Field.ENTRY_CLASS_PK,
			String.valueOf(assetCategory.getCategoryId()));
		map.put(Field.GROUP_ID, String.valueOf(assetCategory.getGroupId()));
		map.put(Field.NAME, assetCategory.getName());
		map.put(
			Field.SCOPE_GROUP_ID, String.valueOf(assetCategory.getGroupId()));
		map.put(Field.STAGING_GROUP, String.valueOf(_group.isStagingGroup()));
		map.put(Field.TITLE, assetCategory.getName());
		map.put(Field.USER_ID, String.valueOf(assetCategory.getUserId()));
		map.put(
			Field.USER_NAME, StringUtil.lowerCase(assetCategory.getUserName()));
		map.put(
			"assetCategoryTitle_ja_JP",
			StringUtil.lowerCase(assetCategory.getName()));
		map.put(
			"leftCategoryId",
			String.valueOf(assetCategory.getLeftCategoryId()));
		map.put("name_sortable", StringUtil.lowerCase(assetCategory.getName()));
		map.put(
			"parentCategoryId",
			String.valueOf(assetCategory.getParentCategoryId()));
		map.put("title_ja_JP", assetCategory.getName());
		map.put(
			"title_sortable", StringUtil.lowerCase(assetCategory.getName()));

		indexedFieldsFixture.populateUID(
			AssetCategory.class.getName(), assetCategory.getCategoryId(), map);

		_populateDates(assetCategory, map);
		_populateRoles(assetCategory, map);
		_populateTitles(assetCategory.getName(), map);

		return map;
	}

	private void _populateDates(
		AssetCategory assetCategory, Map<String, String> map) {

		indexedFieldsFixture.populateDate(
			Field.CREATE_DATE, assetCategory.getCreateDate(), map);
		indexedFieldsFixture.populateDate(
			Field.MODIFIED_DATE, assetCategory.getModifiedDate(), map);
	}

	private void _populateRoles(
			AssetCategory assetCategory, Map<String, String> map)
		throws Exception {

		indexedFieldsFixture.populateRoleIdFields(
			assetCategory.getCompanyId(), AssetCategory.class.getName(),
			assetCategory.getCategoryId(), assetCategory.getGroupId(), null,
			map);
	}

	private void _populateTitles(String title, Map<String, String> map) {
		map.put("localized_title", title);

		for (Locale locale : LanguageUtil.getAvailableLocales()) {
			StringBundler sb = new StringBundler(5);

			sb.append("localized_title_");
			sb.append(locale.getLanguage());
			sb.append("_");
			sb.append(locale.getCountry());

			map.put(sb.toString(), title);

			sb.append("_sortable");

			map.put(sb.toString(), title);
		}
	}

	@DeleteAfterTestRun
	private List<AssetCategory> _assetCategories;

	@DeleteAfterTestRun
	private List<AssetVocabulary> _assetVocabularies;

	private Group _group;

	@DeleteAfterTestRun
	private List<Group> _groups;

	@DeleteAfterTestRun
	private List<User> _users;

}