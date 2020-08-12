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

package com.liferay.users.admin.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilderFactory;
import com.liferay.portal.search.model.uid.UIDFactory;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.search.test.util.FieldValuesAssert;
import com.liferay.portal.search.test.util.IndexedFieldsFixture;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.users.admin.test.util.search.GroupBlueprint;
import com.liferay.users.admin.test.util.search.GroupSearchFixture;
import com.liferay.users.admin.test.util.search.OrganizationBlueprint.OrganizationBlueprintBuilder;
import com.liferay.users.admin.test.util.search.OrganizationSearchFixture;
import com.liferay.users.admin.test.util.search.UserGroupSearchFixture;
import com.liferay.users.admin.test.util.search.UserSearchFixture;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author André de Oliveira
 */
@RunWith(Arquillian.class)
public class UserIndexerIndexedFieldsByAssociationTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		groupSearchFixture = new GroupSearchFixture();

		organizationSearchFixture = new OrganizationSearchFixture(
			_organizationLocalService);

		userGroupSearchFixture = new UserGroupSearchFixture(
			_userGroupLocalService);

		userSearchFixture = new UserSearchFixture(
			_userLocalService, groupSearchFixture, organizationSearchFixture,
			userGroupSearchFixture);

		userSearchFixture.setUp();

		_addresses = userSearchFixture.getAddresses();

		_groups = groupSearchFixture.getGroups();

		_organizations = organizationSearchFixture.getOrganizations();

		_users = userSearchFixture.getUsers();

		_userGroups = userGroupSearchFixture.getUserGroups();

		indexedFieldsFixture = new IndexedFieldsFixture(
			_resourcePermissionLocalService, _searchEngineHelper, _uidFactory,
			_documentBuilderFactory);
	}

	@Test
	public void testAssociationsThatDoNotIndexGroupIdFields() {
		String[] fieldNames = {
			_CT_COLLECTION_ID, Field.GROUP_ID, Field.SCOPE_GROUP_ID, Field.UID
		};

		UserGroup userGroup = addUserGroup();

		long userGroupId = userGroup.getUserGroupId();

		User user = addUser();

		final Map<String, String> map = getBaseFieldValuesMap(user);

		indexedFieldsFixture.populateUID(user, map);

		assertFieldValues(user, fieldNames, map);

		_userLocalService.addUserGroupUser(userGroupId, user);

		assertFieldValues(user, fieldNames, map);

		Group group = addGroup();

		_groupLocalService.addUserGroupGroup(userGroupId, group);

		assertFieldValues(user, fieldNames, map);
	}

	@Test
	public void testAssociationsThatIndexMoreFields() {
		String[] fieldNames = {
			"ancestorOrganizationIds", Field.COMPANY_ID, _CT_COLLECTION_ID,
			Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK, Field.GROUP_ID,
			"groupIds", "organizationIds", "organizationCount",
			Field.SCOPE_GROUP_ID, Field.UID, "userGroupIds", Field.USER_ID
		};

		UserGroup userGroup = addUserGroup();

		User user = addUser();

		final Map<String, String> map1 = HashMapBuilder.putAll(
			getBaseFieldValuesMap(user)
		).put(
			Field.USER_ID, String.valueOf(user.getPrimaryKeyObj())
		).put(
			"organizationCount", "0"
		).build();

		indexedFieldsFixture.populateUID(user, map1);

		assertFieldValues(user, fieldNames, map1);

		OrganizationBlueprintBuilder organizationBlueprintBuilder =
			OrganizationSearchFixture.getTestOrganizationBlueprintBuilder();

		Organization organization = organizationSearchFixture.addOrganization(
			organizationBlueprintBuilder.build());

		long organizationId = organization.getOrganizationId();

		_userLocalService.addOrganizationUser(organizationId, user);

		final Map<String, String> map2 = HashMapBuilder.putAll(
			map1
		).put(
			"organizationCount", "1"
		).put(
			"organizationIds", String.valueOf(organizationId)
		).build();

		assertFieldValues(user, fieldNames, map2);

		long userGroupId = userGroup.getUserGroupId();

		_userLocalService.addUserGroupUser(userGroupId, user);

		final Map<String, String> map3 = HashMapBuilder.putAll(
			map2
		).put(
			"userGroupIds", String.valueOf(userGroupId)
		).build();

		assertFieldValues(user, fieldNames, map3);

		Group group = addGroup();

		long groupId = group.getGroupId();

		_userGroupLocalService.addGroupUserGroup(groupId, userGroup);

		assertFieldValues(
			user, fieldNames,
			HashMapBuilder.putAll(
				map3
			).put(
				Field.GROUP_ID, String.valueOf(groupId)
			).put(
				Field.SCOPE_GROUP_ID, String.valueOf(groupId)
			).build());
	}

	@Test
	public void testNewGroupsIncludeTestUser() throws Exception {
		Group group = addGroup();

		SearchRequestBuilder searchRequestBuilder = getSearchRequestBuilder(
			group.getCompanyId()
		).fields(
			_CT_COLLECTION_ID, Field.GROUP_ID, Field.UID, Field.USER_ID
		).modelIndexerClasses(
			User.class
		);

		SearchResponse searchResponse1 = _searcher.search(
			searchRequestBuilder.query(
				_queries.term(Field.USER_ID, TestPropsValues.getUserId())
			).build());

		Stream<Document> stream = searchResponse1.getDocumentsStream();

		Document document = stream.findAny(
		).get();

		List<Long> groupIds = document.getLongs(Field.GROUP_ID);

		long groupId = group.getGroupId();

		if (!groupIds.contains(groupId)) {
			DocumentsAssert.assertValuesIgnoreRelevance(
				searchResponse1.getRequestString(),
				searchResponse1.getDocumentsStream(), Field.GROUP_ID,
				_toSingletonListString(_toSortedListString(groupIds.stream())));
		}

		SearchResponse searchResponse2 = _searcher.search(
			searchRequestBuilder.query(
				_queries.term(Field.GROUP_ID, groupId)
			).build());

		DocumentsAssert.assertValuesIgnoreRelevance(
			searchResponse2.getRequestString(),
			searchResponse2.getDocumentsStream(), Field.GROUP_ID,
			_toSingletonListString(_toSortedListString(groupIds.stream())));
	}

	protected Group addGroup() {
		return groupSearchFixture.addGroup(new GroupBlueprint());
	}

	protected User addUser() {
		return userSearchFixture.addUser(
			userSearchFixture.getTestUserBlueprintBuilder());
	}

	protected UserGroup addUserGroup() {
		return userGroupSearchFixture.addUserGroup(
			UserGroupSearchFixture.getTestUserGroupBlueprintBuilder());
	}

	protected void assertFieldValues(
		User user, String[] fieldNames, Map<String, String> map) {

		FieldValuesAssert.assertFieldValues(
			String.valueOf(user), searchUser(user, fieldNames), map);
	}

	protected Map<String, String> getBaseFieldValuesMap(User user) {
		return HashMapBuilder.put(
			Field.COMPANY_ID, String.valueOf(user.getCompanyId())
		).put(
			Field.ENTRY_CLASS_NAME, user.getModelClassName()
		).put(
			Field.ENTRY_CLASS_PK, String.valueOf(user.getPrimaryKeyObj())
		).build();
	}

	protected SearchRequestBuilder getSearchRequestBuilder(long companyId) {
		return _searchRequestBuilderFactory.builder(
		).companyId(
			companyId
		);
	}

	protected Document searchUser(User user, String[] fieldNames) {
		SearchResponse searchResponse = _searcher.search(
			getSearchRequestBuilder(
				user.getCompanyId()
			).fields(
				fieldNames
			).modelIndexerClasses(
				user.getModelClass()
			).query(
				_queries.term(Field.ENTRY_CLASS_PK, user.getPrimaryKeyObj())
			).build());

		Stream<Document> stream = searchResponse.getDocumentsStream();

		Document document = stream.findFirst(
		).get();

		return indexedFieldsFixture.postProcessDocument(document);
	}

	protected GroupSearchFixture groupSearchFixture;
	protected IndexedFieldsFixture indexedFieldsFixture;
	protected OrganizationSearchFixture organizationSearchFixture;
	protected UserGroupSearchFixture userGroupSearchFixture;
	protected UserSearchFixture userSearchFixture;

	private static String _toListString(Stream<?> stream) {
		return stream.map(
			String::valueOf
		).collect(
			Collectors.joining(
				StringPool.COMMA_AND_SPACE, StringPool.OPEN_BRACKET,
				StringPool.CLOSE_BRACKET)
		);
	}

	private static String _toSingletonListString(String string) {
		return String.valueOf(Collections.singletonList(string));
	}

	private static String _toSortedListString(Stream<?> stream) {
		return _toListString(
			stream.map(
				String::valueOf
			).sorted());
	}

	private static final String _CT_COLLECTION_ID = "ctCollectionId";

	@Inject
	private static DocumentBuilderFactory _documentBuilderFactory;

	@Inject
	private static GroupLocalService _groupLocalService;

	@Inject
	private static OrganizationLocalService _organizationLocalService;

	@Inject
	private static Queries _queries;

	@Inject
	private static ResourcePermissionLocalService
		_resourcePermissionLocalService;

	@Inject
	private static SearchEngineHelper _searchEngineHelper;

	@Inject
	private static Searcher _searcher;

	@Inject
	private static SearchRequestBuilderFactory _searchRequestBuilderFactory;

	@Inject
	private static UIDFactory _uidFactory;

	@Inject
	private static UserGroupLocalService _userGroupLocalService;

	@Inject
	private static UserLocalService _userLocalService;

	@DeleteAfterTestRun
	private List<Address> _addresses = new ArrayList<>();

	@DeleteAfterTestRun
	private List<Group> _groups;

	@DeleteAfterTestRun
	private List<Organization> _organizations;

	@DeleteAfterTestRun
	private List<UserGroup> _userGroups;

	@DeleteAfterTestRun
	private List<User> _users;

}