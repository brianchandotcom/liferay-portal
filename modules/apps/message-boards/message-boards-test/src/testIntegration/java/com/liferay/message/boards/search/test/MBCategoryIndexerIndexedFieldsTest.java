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

package com.liferay.message.boards.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
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
import java.util.Map;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class MBCategoryIndexerIndexedFieldsTest {

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
		setUpIndexedFieldsFixture();
		setUpMBCategoryIndexerFixture();
		setUpMBFixture();
	}

	@Test
	public void testIndexedFields() throws Exception {
		MBCategory mbCategory = mbFixture.createMBCategory();

		String searchTerm = mbCategory.getUserName();

		Document document = mbCategoryIndexerFixture.searchOnlyOne(
			_user.getUserId(), searchTerm, LocaleUtil.US);

		indexedFieldsFixture.postProcessDocument(document);

		FieldValuesAssert.assertFieldValues(
			_expectedFieldValues(mbCategory), document, searchTerm);
	}

	protected void setUpIndexedFieldsFixture() {
		indexedFieldsFixture = new IndexedFieldsFixture(
			resourcePermissionLocalService, searchEngineHelper);
	}

	protected void setUpMBCategoryIndexerFixture() {
		mbCategoryIndexerFixture = new IndexerFixture<>(MBCategory.class);
	}

	protected void setUpMBFixture() {
		mbFixture = new MBFixture(_group, _user);

		_mbCategories = mbFixture.getMbCategories();
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

	protected IndexedFieldsFixture indexedFieldsFixture;
	protected IndexerFixture<MBCategory> mbCategoryIndexerFixture;
	protected MBFixture mbFixture;

	@Inject
	protected ResourcePermissionLocalService resourcePermissionLocalService;

	@Inject
	protected SearchEngineHelper searchEngineHelper;

	protected UserSearchFixture userSearchFixture;

	private Map<String, String> _expectedFieldValues(MBCategory mbCategory)
		throws Exception {

		Map<String, String> map = new HashMap<>();

		map.put(
			Field.ASSET_PARENT_CATEGORY_ID,
			String.valueOf(mbCategory.getParentCategoryId()));
		map.put(Field.COMPANY_ID, String.valueOf(mbCategory.getCompanyId()));
		map.put(Field.ENTRY_CLASS_NAME, MBCategory.class.getName());
		map.put(
			Field.ENTRY_CLASS_PK, String.valueOf(mbCategory.getCategoryId()));
		map.put(Field.GROUP_ID, String.valueOf(mbCategory.getGroupId()));
		map.put(Field.SCOPE_GROUP_ID, String.valueOf(mbCategory.getGroupId()));
		map.put(Field.STAGING_GROUP, String.valueOf(_group.isStagingGroup()));
		map.put(Field.STATUS, String.valueOf(mbCategory.getStatus()));
		map.put(Field.USER_ID, String.valueOf(mbCategory.getUserId()));
		map.put(
			Field.USER_NAME, StringUtil.lowerCase(mbCategory.getUserName()));

		indexedFieldsFixture.populateUID(
			MBCategory.class.getName(), mbCategory.getCategoryId(), map);

		_populateDates(mbCategory, map);
		_populateNameContent(mbCategory, map);
		_populateRoles(mbCategory, map);

		return map;
	}

	private void _populateDates(
		MBCategory mbCategory, Map<String, String> map) {

		indexedFieldsFixture.populateDate(
			Field.MODIFIED_DATE, mbCategory.getModifiedDate(), map);
		indexedFieldsFixture.populateDate(
			Field.CREATE_DATE, mbCategory.getCreateDate(), map);
	}

	private void _populateNameContent(
		MBCategory mbCategory, Map<String, String> map) {

		String name = mbCategory.getName();

		map.put(Field.DESCRIPTION, mbCategory.getDescription());
		map.put(Field.NAME, name);
		map.put(Field.NAME.concat("_sortable"), StringUtil.toLowerCase(name));
	}

	private void _populateRoles(MBCategory mbCategory, Map<String, String> map)
		throws Exception {

		indexedFieldsFixture.populateRoleIdFields(
			mbCategory.getCompanyId(), MBCategory.class.getName(),
			mbCategory.getCategoryId(), mbCategory.getGroupId(), null, map);
	}

	private Group _group;

	@DeleteAfterTestRun
	private List<Group> _groups;

	@DeleteAfterTestRun
	private List<MBCategory> _mbCategories;

	private User _user;

	@DeleteAfterTestRun
	private List<User> _users;

}