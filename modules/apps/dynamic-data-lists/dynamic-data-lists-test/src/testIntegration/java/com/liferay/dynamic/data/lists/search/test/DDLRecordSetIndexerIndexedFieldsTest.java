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

package com.liferay.dynamic.data.lists.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.test.util.FieldValuesAssert;
import com.liferay.portal.search.test.util.IndexedFieldsFixture;
import com.liferay.portal.search.test.util.IndexerFixture;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;
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
 * @author Luan Maoski
 * @author Lucas Marques
 */
@RunWith(Arquillian.class)
public class DDLRecordSetIndexerIndexedFieldsTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		setUpUserSearchFixture();

		setUpDdlRecordSetFixture();

		setUpIndexedFieldsFixture();

		setUpIndexerFixture();
	}

	@Test
	public void testIndexedFields() throws Exception {
		DDLRecordSet ddlRecortSet = ddlRecordSetFixture.getDdlRecordSet();

		String searchTerm = user.getFullName();

		Document document = ddlRecordSetIndexerFixture.searchOnlyOne(
			searchTerm);

		indexedFieldsFixture.postProcessDocument(document);

		FieldValuesAssert.assertFieldValues(
			_expectedFieldValues(ddlRecortSet), document, searchTerm);
	}

	protected void setUpDdlRecordSetFixture() throws Exception {
		ddlRecordSetFixture = new DDLRecordSetFixture(group, user);

		_ddlRecordSets = ddlRecordSetFixture.getDdlRecordSets();
	}

	protected void setUpIndexedFieldsFixture() {
		indexedFieldsFixture = new IndexedFieldsFixture(
			resourcePermissionLocalService, searchEngineHelper);
	}

	protected void setUpIndexerFixture() {
		ddlRecordSetIndexerFixture = new IndexerFixture<>(DDLRecordSet.class);
	}

	protected void setUpUserSearchFixture() throws Exception {
		userSearchFixture = new UserSearchFixture();

		userSearchFixture.setUp();

		_groups = userSearchFixture.getGroups();

		_users = userSearchFixture.getUsers();

		group = userSearchFixture.addGroup();

		user = userSearchFixture.addUser(RandomTestUtil.randomString(), group);
	}

	protected DDLRecordSetFixture ddlRecordSetFixture;
	protected IndexerFixture<DDLRecordSet> ddlRecordSetIndexerFixture;
	protected Group group;
	protected IndexedFieldsFixture indexedFieldsFixture;

	@Inject
	protected ResourcePermissionLocalService resourcePermissionLocalService;

	@Inject
	protected SearchEngineHelper searchEngineHelper;

	protected User user;
	protected UserSearchFixture userSearchFixture;

	private Map<String, String> _expectedFieldValues(DDLRecordSet ddlRecortSet)
		throws Exception {

		Map<String, String> map = new HashMap<>();

		map.put(Field.COMPANY_ID, String.valueOf(ddlRecortSet.getCompanyId()));

		map.put(Field.ENTRY_CLASS_NAME, DDLRecordSet.class.getName());

		map.put(
			Field.ENTRY_CLASS_PK,
			String.valueOf(ddlRecortSet.getRecordSetId()));

		map.put(Field.GROUP_ID, String.valueOf(ddlRecortSet.getGroupId()));

		map.put(
			Field.SCOPE_GROUP_ID, String.valueOf(ddlRecortSet.getGroupId()));

		map.put(Field.STAGING_GROUP, String.valueOf(group.isStagingGroup()));

		map.put(Field.USER_ID, String.valueOf(ddlRecortSet.getUserId()));

		map.put(
			Field.USER_NAME, StringUtil.lowerCase(ddlRecortSet.getUserName()));

		indexedFieldsFixture.populateUID(
			DDLRecordSet.class.getName(), ddlRecortSet.getRecordSetId(), map);
		_populateDates(ddlRecortSet, map);
		_populateRoles(ddlRecortSet, map);

		return map;
	}

	private void _populateDates(
		DDLRecordSet ddlRecortSet, Map<String, String> map) {

		indexedFieldsFixture.populateDate(
			Field.CREATE_DATE, ddlRecortSet.getCreateDate(), map);
		indexedFieldsFixture.populateDate(
			Field.MODIFIED_DATE, ddlRecortSet.getModifiedDate(), map);
	}

	private void _populateRoles(
			DDLRecordSet ddlRecortSet, Map<String, String> map)
		throws Exception {

		indexedFieldsFixture.populateRoleIdFields(
			ddlRecortSet.getCompanyId(), DDLRecordSet.class.getName(),
			ddlRecortSet.getRecordSetId(), ddlRecortSet.getGroupId(), null,
			map);
	}

	@DeleteAfterTestRun
	private List<DDLRecordSet> _ddlRecordSets;

	@DeleteAfterTestRun
	private List<Group> _groups;

	@DeleteAfterTestRun
	private List<User> _users;

}