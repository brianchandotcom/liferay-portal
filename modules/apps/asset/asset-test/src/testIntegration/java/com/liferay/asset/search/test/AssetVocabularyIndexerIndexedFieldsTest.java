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

import org.junit.After;
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
public class AssetVocabularyIndexerIndexedFieldsTest {

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

		setUpAssetVocabularyFixture();

		setUpAssetVocabularyIndexerFixture();

		setUpIndexedFieldsFixture();

		_defaultLocale = LocaleThreadLocal.getDefaultLocale();
	}

	@After
	public void tearDown() throws Exception {
		LocaleThreadLocal.setDefaultLocale(_defaultLocale);
	}

	@Test
	public void testIndexedFields() throws Exception {
		Locale locale = LocaleUtil.JAPAN;

		setTestLocale(locale);

		AssetVocabulary assetVocabulary =
			assetVocabularyFixture.createAssetVocabulary("新しい商品");

		String searchTerm = "新しい";

		Document document = assetVocabularyIndexerFixture.searchOnlyOne(
			searchTerm, locale);

		indexedFieldsFixture.postProcessDocument(document);

		FieldValuesAssert.assertFieldValues(
			_expectedFieldValues(assetVocabulary), document, searchTerm);
	}

	protected void setTestLocale(Locale locale) throws Exception {
		assetVocabularyFixture.updateDisplaySettings(locale);

		LocaleThreadLocal.setDefaultLocale(locale);
	}

	protected void setUpAssetVocabularyFixture() throws Exception {
		assetVocabularyFixture = new AssetVocabularyFixture(_group);

		_assetVocabularies = assetVocabularyFixture.getAssetVocabularies();
	}

	protected void setUpAssetVocabularyIndexerFixture() {
		assetVocabularyIndexerFixture = new IndexerFixture<>(
			AssetVocabulary.class);
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

	protected AssetVocabularyFixture assetVocabularyFixture;
	protected IndexerFixture<AssetVocabulary> assetVocabularyIndexerFixture;
	protected IndexedFieldsFixture indexedFieldsFixture;

	@Inject
	protected ResourcePermissionLocalService resourcePermissionLocalService;

	@Inject
	protected SearchEngineHelper searchEngineHelper;

	protected UserSearchFixture userSearchFixture;

	private Map<String, String> _expectedFieldValues(
			AssetVocabulary assetVocabulary)
		throws Exception {

		Map<String, String> map = new HashMap<>();

		map.put(
			Field.ASSET_VOCABULARY_ID,
			String.valueOf(assetVocabulary.getVocabularyId()));
		map.put(
			Field.COMPANY_ID, String.valueOf(assetVocabulary.getCompanyId()));
		map.put(Field.ENTRY_CLASS_NAME, AssetVocabulary.class.getName());
		map.put(
			Field.ENTRY_CLASS_PK,
			String.valueOf(assetVocabulary.getVocabularyId()));
		map.put(Field.GROUP_ID, String.valueOf(assetVocabulary.getGroupId()));
		map.put(Field.NAME, assetVocabulary.getName());
		map.put(
			Field.SCOPE_GROUP_ID, String.valueOf(assetVocabulary.getGroupId()));
		map.put(Field.STAGING_GROUP, String.valueOf(_group.isStagingGroup()));
		map.put(Field.TITLE, assetVocabulary.getName());
		map.put(Field.USER_ID, String.valueOf(assetVocabulary.getUserId()));
		map.put(
			Field.USER_NAME,
			StringUtil.lowerCase(assetVocabulary.getUserName()));
		map.put(
			"name_sortable", StringUtil.lowerCase(assetVocabulary.getName()));
		map.put("title_ja_JP", assetVocabulary.getName());
		map.put(
			"title_sortable", StringUtil.lowerCase(assetVocabulary.getName()));

		indexedFieldsFixture.populateUID(
			AssetVocabulary.class.getName(), assetVocabulary.getVocabularyId(),
			map);

		_populateDates(assetVocabulary, map);
		_populateRoles(assetVocabulary, map);
		_populateLocalizedTitles(assetVocabulary.getTitle(), map);

		return map;
	}

	private void _populateDates(
		AssetVocabulary assetVocabulary, Map<String, String> map) {

		indexedFieldsFixture.populateDate(
			Field.CREATE_DATE, assetVocabulary.getCreateDate(), map);
		indexedFieldsFixture.populateDate(
			Field.MODIFIED_DATE, assetVocabulary.getModifiedDate(), map);
	}

	private void _populateLocalizedTitles(
		String title, Map<String, String> map) {

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

	private void _populateRoles(
			AssetVocabulary assetVocabulary, Map<String, String> map)
		throws Exception {

		indexedFieldsFixture.populateRoleIdFields(
			assetVocabulary.getCompanyId(), AssetVocabulary.class.getName(),
			assetVocabulary.getVocabularyId(), assetVocabulary.getGroupId(),
			null, map);
	}

	@DeleteAfterTestRun
	private List<AssetVocabulary> _assetVocabularies;

	private Locale _defaultLocale;
	private Group _group;

	@DeleteAfterTestRun
	private List<Group> _groups;

	@DeleteAfterTestRun
	private List<User> _users;

}