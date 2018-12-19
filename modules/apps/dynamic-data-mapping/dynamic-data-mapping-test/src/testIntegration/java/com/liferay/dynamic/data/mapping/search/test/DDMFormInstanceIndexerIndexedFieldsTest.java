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

package com.liferay.dynamic.data.mapping.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.util.DDMIndexer;
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
public class DDMFormInstanceIndexerIndexedFieldsTest {

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

		setUpDDMFormInstanceFixture();

		setUpDDMFormInstanceIndexerFixture();

		setUpIndexedFieldsFixture();
	}

	@Test
	public void testIndexedFields() throws Exception {
		DDMFormInstance ddmFormInstance =
			ddmFormInstanceFixture.createAnDDMFormInstance();

		String searchTerm = user.getFullName();

		Document document = ddmFormInstanceIndexerFixture.searchOnlyOne(
			searchTerm);

		indexedFieldsFixture.postProcessDocument(document);

		Map<String, String> expected = _expectedFieldValues(ddmFormInstance);

		FieldValuesAssert.assertFieldValues(expected, document, searchTerm);
	}

	protected void setUpDDMFormInstanceFixture() {
		ddmFormInstanceFixture = new DDMFormInstanceFixture(group, user);

		_ddmFormInstances = ddmFormInstanceFixture.getDdmFormInstances();
	}

	protected void setUpDDMFormInstanceIndexerFixture() {
		ddmFormInstanceIndexerFixture = new IndexerFixture<>(
			DDMFormInstance.class);
	}

	protected void setUpIndexedFieldsFixture() {
		indexedFieldsFixture = new IndexedFieldsFixture(
			resourcePermissionLocalService, searchEngineHelper);
	}

	protected void setUpUserSearchFixture() throws Exception {
		userSearchFixture = new UserSearchFixture();

		userSearchFixture.setUp();

		_groups = userSearchFixture.getGroups();

		_users = userSearchFixture.getUsers();

		group = userSearchFixture.addGroup();

		user = userSearchFixture.addUser(RandomTestUtil.randomString(), group);
	}

	protected DDMFormInstanceFixture ddmFormInstanceFixture;
	protected IndexerFixture<DDMFormInstance> ddmFormInstanceIndexerFixture;

	@Inject
	protected DDMIndexer ddmIndexer;

	protected Group group;
	protected IndexedFieldsFixture indexedFieldsFixture;

	@Inject
	protected ResourcePermissionLocalService resourcePermissionLocalService;

	@Inject
	protected SearchEngineHelper searchEngineHelper;

	protected User user;
	protected UserSearchFixture userSearchFixture;

	private Map<String, String> _expectedFieldValues(
			DDMFormInstance ddmFormInstance)
		throws Exception {

		Map<String, String> map = new HashMap<>();

		map.put(
			Field.COMPANY_ID, String.valueOf(ddmFormInstance.getCompanyId()));

		map.put(Field.ENTRY_CLASS_NAME, DDMFormInstance.class.getName());

		map.put(
			Field.ENTRY_CLASS_PK,
			String.valueOf(ddmFormInstance.getFormInstanceId()));

		map.put(Field.GROUP_ID, String.valueOf(ddmFormInstance.getGroupId()));

		map.put(
			Field.SCOPE_GROUP_ID, String.valueOf(ddmFormInstance.getGroupId()));

		map.put(Field.USER_ID, String.valueOf(ddmFormInstance.getUserId()));

		map.put(
			Field.USER_NAME,
			StringUtil.lowerCase(ddmFormInstance.getUserName()));

		map.put(Field.STAGING_GROUP, String.valueOf(group.isStagingGroup()));

		indexedFieldsFixture.populateUID(
			DDMFormInstance.class.getName(),
			ddmFormInstance.getFormInstanceId(), map);

		_populateDates(ddmFormInstance, map);

		_populateRoles(ddmFormInstance, map);

		return map;
	}

	private void _populateDates(
		DDMFormInstance ddmFormInstance, Map<String, String> map) {

		indexedFieldsFixture.populateDate(
			Field.CREATE_DATE, ddmFormInstance.getCreateDate(), map);
		indexedFieldsFixture.populateDate(
			Field.MODIFIED_DATE, ddmFormInstance.getModifiedDate(), map);
	}

	private void _populateRoles(
			DDMFormInstance ddmFormInstance, Map<String, String> map)
		throws Exception {

		indexedFieldsFixture.populateRoleIdFields(
			ddmFormInstance.getCompanyId(), DDMFormInstance.class.getName(),
			ddmFormInstance.getFormInstanceId(), ddmFormInstance.getGroupId(),
			null, map);
	}

	@DeleteAfterTestRun
	private List<DDMFormInstance> _ddmFormInstances;

	@DeleteAfterTestRun
	private List<Group> _groups;

	@DeleteAfterTestRun
	private List<User> _users;

}