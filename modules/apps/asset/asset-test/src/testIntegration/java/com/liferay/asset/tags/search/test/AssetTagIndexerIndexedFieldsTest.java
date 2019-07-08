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

package com.liferay.asset.tags.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
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
import java.util.Map;

import org.junit.Assume;
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
public class AssetTagIndexerIndexedFieldsTest {

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

		setUpAssetTagFixture();

		setUpAssetTagIndexerFixture();

		setUpIndexedFieldsFixture();
	}

	@Test
	public void testIndexedFields() throws Exception {
		Assume.assumeFalse(
			isNumberSortableImplementedAsDoubleForSearchEngine());

		AssetTag assetTag = assetTagFixture.createAssetTag();

		String searchTerm = assetTag.getUserName();

		Document document = assetTagIndexerFixture.searchOnlyOne(searchTerm);

		indexedFieldsFixture.postProcessDocument(document);

		FieldValuesAssert.assertFieldValues(
			_expectedFieldValues(assetTag), document, searchTerm);
	}

	protected boolean isNumberSortableImplementedAsDoubleForSearchEngine() {
		SearchEngine searchEngine = searchEngineHelper.getSearchEngine(
			searchEngineHelper.getDefaultSearchEngineId());

		String vendor = searchEngine.getVendor();

		if (vendor.equals("Solr")) {
			return true;
		}

		return false;
	}

	protected void setUpAssetTagFixture() throws Exception {
		assetTagFixture = new AssetTagFixture(_group, _user);

		_assetTags = assetTagFixture.getAssetTags();
	}

	protected void setUpAssetTagIndexerFixture() {
		assetTagIndexerFixture = new IndexerFixture<>(AssetTag.class);
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

		_user = userSearchFixture.addUser(
			RandomTestUtil.randomString(), _group);

		_users = userSearchFixture.getUsers();
	}

	protected AssetTagFixture assetTagFixture;
	protected IndexerFixture<AssetTag> assetTagIndexerFixture;
	protected IndexedFieldsFixture indexedFieldsFixture;

	@Inject
	protected ResourcePermissionLocalService resourcePermissionLocalService;

	@Inject
	protected SearchEngineHelper searchEngineHelper;

	protected UserSearchFixture userSearchFixture;

	private Map<String, String> _expectedFieldValues(AssetTag assetTag)
		throws Exception {

		Map<String, String> map = new HashMap<>();

		map.put(Field.COMPANY_ID, String.valueOf(assetTag.getCompanyId()));
		map.put(Field.ENTRY_CLASS_NAME, AssetTag.class.getName());
		map.put(Field.ENTRY_CLASS_PK, String.valueOf(assetTag.getTagId()));
		map.put(Field.GROUP_ID, String.valueOf(assetTag.getGroupId()));
		map.put(Field.NAME, assetTag.getName());
		map.put(Field.SCOPE_GROUP_ID, String.valueOf(assetTag.getGroupId()));
		map.put(Field.STAGING_GROUP, String.valueOf(_group.isStagingGroup()));
		map.put(Field.USER_ID, String.valueOf(assetTag.getUserId()));
		map.put(Field.USER_NAME, StringUtil.lowerCase(assetTag.getUserName()));
		map.put("assetCount", String.valueOf(assetTag.getAssetCount()));
		map.put(
			"assetCount_Number_sortable",
			String.valueOf(assetTag.getAssetCount()));
		map.put("name_String_sortable", assetTag.getName());

		indexedFieldsFixture.populateUID(
			AssetTag.class.getName(), assetTag.getTagId(), map);

		_populateDates(assetTag, map);
		_populateRoles(assetTag, map);

		return map;
	}

	private void _populateDates(AssetTag assetTag, Map<String, String> map) {
		indexedFieldsFixture.populateDate(
			Field.CREATE_DATE, assetTag.getCreateDate(), map);
		indexedFieldsFixture.populateDate(
			Field.MODIFIED_DATE, assetTag.getModifiedDate(), map);
	}

	private void _populateRoles(AssetTag assetTag, Map<String, String> map)
		throws Exception {

		indexedFieldsFixture.populateRoleIdFields(
			assetTag.getCompanyId(), AssetTag.class.getName(),
			assetTag.getTagId(), assetTag.getGroupId(), null, map);
	}

	@DeleteAfterTestRun
	private List<AssetTag> _assetTags;

	private Group _group;

	@DeleteAfterTestRun
	private List<Group> _groups;

	private User _user;

	@DeleteAfterTestRun
	private List<User> _users;

}