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
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery.PerformActionMethod;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.search.document.DocumentBuilderFactory;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.users.admin.test.util.search.GroupBlueprint;
import com.liferay.users.admin.test.util.search.GroupSearchFixture;
import com.liferay.users.admin.test.util.search.OrganizationSearchFixture;
import com.liferay.users.admin.test.util.search.UserGroupSearchFixture;
import com.liferay.users.admin.test.util.search.UserSearchFixture;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
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
public class UserGroupCascadeReindexUsersTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		if (_STRESS_MODE_10_MIN_TO_RUN_ALL_TESTS) {
			_groupCount = 5;
			_userCount = 100;
		}
		else {
			_groupCount = 2;
			_userCount = 3;
		}

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
	}

	@Test
	public void testAddUsersOnly() throws Exception {
		addUsers(_userCount);
	}

	@Test
	public void testAddUsersThenRetrieveBulk() throws Exception {
		List<User> users = addUsers(_userCount);

		doTraverseWithActionableDynamicQuery(
			users,
			user -> {
			});
	}

	@Test
	public void testAddUsersThenRetrieveNonbulk() throws Exception {
		List<User> users = addUsers(_userCount);

		doTraverseWithIndividualFetches(
			users,
			user -> {
			});
	}

	@Test
	public void testAddUsersThenTranslateBulk() throws Exception {
		List<User> users = addUsers(_userCount);

		doTraverseWithActionableDynamicQuery(users, this::_translate);
	}

	@Test
	public void testAddUsersThenTranslateNonbulk() {
		List<User> users = addUsers(_userCount);

		doTraverseWithIndividualFetches(users, this::_translate);
	}

	@Test
	public void testPerformanceOfMultiSiteLargeUserGroup() throws Exception {
		UserGroup userGroup = addUserGroup();

		long userGroupId = userGroup.getUserGroupId();

		List<User> users = addUsers(_userCount);

		_userLocalService.addUserGroupUsers(userGroupId, users);

		SearchResponse searchResponse = searchUsersInUserGroup(userGroup);

		DocumentsAssert.assertValues(
			searchResponse.getRequestString(),
			searchResponse.getDocumentsStream(), "userGroupIds",
			_repeat(String.valueOf(userGroupId), users.size()));

		List<Group> groups = addGroups(_groupCount);

		for (Group group : groups) {
			_userGroupLocalService.addGroupUserGroup(
				group.getGroupId(), userGroup);
		}

		searchResponse = searchUsersInGroup(groups.get(0));

		DocumentsAssert.assertValues(
			searchResponse.getRequestString(),
			searchResponse.getDocumentsStream(), Field.GROUP_ID,
			_repeat(_getAllGroupIdsString(groups), users.size()));
	}

	protected Group addGroup() {
		return groupSearchFixture.addGroup(new GroupBlueprint());
	}

	protected List<Group> addGroups(int groupCount) {
		return Stream.generate(
			this::addGroup
		).limit(
			groupCount
		).collect(
			Collectors.toList()
		);
	}

	protected User addUser() {
		return userSearchFixture.addUser(
			userSearchFixture.getTestUserBlueprintBuilder());
	}

	protected UserGroup addUserGroup() {
		return userGroupSearchFixture.addUserGroup(
			UserGroupSearchFixture.getTestUserGroupBlueprintBuilder());
	}

	protected List<User> addUsers(int count) {
		return Stream.generate(
			this::addUser
		).limit(
			count
		).collect(
			Collectors.toList()
		);
	}

	protected void doTraverseWithActionableDynamicQuery(
			List<User> users, PerformActionMethod<User> performActionMethod)
		throws Exception {

		long[] userIds = _getAllUserIds(users);

		User user = users.get(0);

		long companyId = user.getCompanyId();

		ActionableDynamicQuery actionableDynamicQuery =
			_userLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property userId = PropertyFactoryUtil.forName("userId");

				dynamicQuery.add(userId.in(userIds));
			});
		actionableDynamicQuery.setCompanyId(companyId);
		actionableDynamicQuery.setPerformActionMethod(performActionMethod);

		actionableDynamicQuery.performActions();
	}

	protected void doTraverseWithIndividualFetches(
		List<User> users, Consumer<User> consumer) {

		long[] userIds = _getAllUserIds(users);

		for (long userId : userIds) {
			consumer.accept(_userLocalService.fetchUser(userId));
		}
	}

	protected SearchRequestBuilder getSearchRequestBuilder(long companyId) {
		return _searchRequestBuilderFactory.builder(
		).withSearchContext(
			searchContext -> searchContext.setCompanyId(companyId)
		);
	}

	protected long getTestUserId() {
		try {
			return TestPropsValues.getUserId();
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	protected SearchResponse searchUsersInGroup(Group group) {
		BooleanQuery booleanQuery = _queries.booleanQuery();

		booleanQuery.addMustQueryClauses(
			_queries.term(Field.GROUP_ID, group.getGroupId()));

		booleanQuery.addMustNotQueryClauses(
			_queries.term(Field.USER_ID, getTestUserId()));

		return _searcher.search(
			getSearchRequestBuilder(
				group.getCompanyId()
			).fields(
				Field.GROUP_ID
			).modelIndexerClasses(
				User.class
			).query(
				booleanQuery
			).build());
	}

	protected SearchResponse searchUsersInUserGroup(UserGroup userGroup) {
		return _searcher.search(
			getSearchRequestBuilder(
				userGroup.getCompanyId()
			).fields(
				"userGroupIds"
			).modelIndexerClasses(
				User.class
			).query(
				_queries.term("userGroupIds", userGroup.getUserGroupId())
			).build());
	}

	protected GroupSearchFixture groupSearchFixture;
	protected OrganizationSearchFixture organizationSearchFixture;
	protected UserGroupSearchFixture userGroupSearchFixture;
	protected UserSearchFixture userSearchFixture;

	private String _getAllGroupIdsString(List<Group> groups) {
		Stream<Group> stream = groups.stream();

		return _toSortedListString(stream.map(Group::getGroupId));
	}

	private long[] _getAllUserIds(List<User> users) {
		Stream<User> stream = users.stream();

		return stream.mapToLong(
			User::getUserId
		).toArray();
	}

	private Document _getDocument(Indexer<User> indexer, User user) {
		try {
			return indexer.getDocument(user);
		}
		catch (SearchException searchException) {
			throw new RuntimeException(searchException);
		}
	}

	private String _repeat(String s, int times) {
		return _toListString(
			Stream.generate(
				() -> s
			).limit(
				times
			));
	}

	private String _toListString(Stream<?> stream) {
		return stream.map(
			String::valueOf
		).collect(
			Collectors.joining(
				StringPool.COMMA_AND_SPACE, StringPool.OPEN_BRACKET,
				StringPool.CLOSE_BRACKET)
		);
	}

	private String _toSortedListString(Stream<?> stream) {
		return _toListString(
			stream.map(
				String::valueOf
			).sorted());
	}

	private void _translate(User user) {
		if (!user.isDefaultUser()) {
			_getDocument(_indexer, user);
		}
	}

	private static final boolean _STRESS_MODE_10_MIN_TO_RUN_ALL_TESTS = false;

	@Inject
	private static DocumentBuilderFactory _documentBuilderFactory;

	@Inject(filter = "indexer.class.name=com.liferay.portal.kernel.model.User")
	private static Indexer<User> _indexer;

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
	private static UserGroupLocalService _userGroupLocalService;

	@Inject
	private static UserLocalService _userLocalService;

	@DeleteAfterTestRun
	private List<Address> _addresses = new ArrayList<>();

	private int _groupCount;

	@DeleteAfterTestRun
	private List<Group> _groups;

	@DeleteAfterTestRun
	private List<Organization> _organizations;

	private int _userCount;

	@DeleteAfterTestRun
	private List<UserGroup> _userGroups;

	@DeleteAfterTestRun
	private List<User> _users;

}