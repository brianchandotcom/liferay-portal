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
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.search.configuration.ReindexerConfiguration;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.reindexer.Reindexer;
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
import java.util.Dictionary;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.StopWatch;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author André de Oliveira
 */
@RunWith(Arquillian.class)
public class UserReindexerPerformanceOfLargeUserGroupInManySitesTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		if (_STRESS_MODE_10_MIN_TO_RUN_ALL_TESTS) {
			_groupsCount = 5;
			_usersCount = 100;
		}
		else {
			_groupsCount = 2;
			_usersCount = 3;
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

	@After
	public void tearDown() {
		userSearchFixture.tearDown();
	}

	@Test
	public void testAddUsersOnly() throws Exception {
		addUsers(_usersCount);
	}

	@Test
	public void testAddUsersThenReindex() throws Exception {
		List<User> users = addUsers(_usersCount);

		reindex(users);
	}

	@Test
	public void testBulkSynchronous() throws Exception {
		doTest(
			HashMapBuilder.put(
				"nonbulkIndexingOverride", "false"
			).put(
				"synchronousExecutionOverride", "true"
			).build());
	}

	@Test
	public void testNonbulkSynchronous() throws Exception {
		doTest(
			HashMapBuilder.put(
				"nonbulkIndexingOverride", "true"
			).put(
				"synchronousExecutionOverride", "true"
			).build());
	}

	protected Group addGroup() {
		return groupSearchFixture.addGroup(new GroupBlueprint());
	}

	protected void addGroups(int groupsCount, List<Group> groups) {
		for (int i = 0; i < groupsCount; i++) {
			groups.add(addGroup());
		}
	}

	protected void addGroupUserGroup(Group group, UserGroup userGroup) {
		_userGroupLocalService.addGroupUserGroup(group.getGroupId(), userGroup);
	}

	protected User addUser() {
		return userSearchFixture.addUser(
			userSearchFixture.getTestUserBlueprintBuilder());
	}

	protected UserGroup addUserGroup() {
		return userGroupSearchFixture.addUserGroup(
			UserGroupSearchFixture.getTestUserGroupBlueprintBuilder());
	}

	protected void addUserGroupUsers(long userGroupId, List<User> users) {
		try {
			_userLocalService.addUserGroupUsers(userGroupId, users);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	protected List<User> addUsers(int count) {
		List<User> users = new ArrayList<>();

		addUsers(count, users);

		return users;
	}

	protected void addUsers(int count, List<User> users) {
		for (int i = 1; i <= count; i++) {
			users.add(addUser());
		}
	}

	protected void doTest() {
		Map<String, String> timesMap = new LinkedHashMap<>();

		measure(timesMap, "full test", () -> doTestTiming(timesMap));

		if (_REPORT_TIMES_AND_FAIL) {
			throw new RuntimeException(_getTimesReport(timesMap));
		}
	}

	protected void doTest(Map<String, String> map) throws Exception {
		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					ReindexerConfiguration.class.getName(),
					_toDictionary(map))) {

			doTest();
		}
	}

	protected void doTestTiming(Map<String, String> timesMap) {
		UserGroup userGroup = addUserGroup();

		long userGroupId = userGroup.getUserGroupId();

		List<User> users = new ArrayList<>();

		measure(timesMap, "addUsers", () -> addUsers(_usersCount, users));

		measure(
			timesMap, "addUserGroupUsers",
			() -> addUserGroupUsers(userGroupId, users));

		measure(
			timesMap, "reindex after addUserGroupUsers", () -> reindex(users));

		List<Group> groups = new ArrayList<>();

		measure(timesMap, "addGroups", () -> addGroups(_groupsCount, groups));

		for (Group group : groups) {
			measure(
				timesMap, "addGroupUserGroup " + group.getGroupId(),
				() -> addGroupUserGroup(group, userGroup));
		}

		measure(
			timesMap, "reindex after addGroupUserGroup", () -> reindex(users));

		SearchResponse searchResponse = searchUsersInAllGroups(
			groups, getTestUserId());

		DocumentsAssert.assertValuesIgnoreRelevance(
			searchResponse.getRequestString(), searchResponse.getDocuments(),
			Field.USER_ID,
			TransformUtil.transform(
				users, user -> String.valueOf(user.getUserId())));
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

	protected void measure(
		Map<String, String> map, String name, Runnable runnable) {

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		runnable.run();

		map.put(name, stopWatch.toString());
	}

	protected void reindex(List<User> users) {
		User user = users.get(0);

		_reindexer.reindex(
			user.getCompanyId(), _CLASS_NAME,
			TransformUtil.transformToLongArray(users, User::getUserId));
	}

	protected SearchResponse searchUsersInAllGroups(
		List<Group> groups, long testUserId) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		groups.forEach(
			group -> booleanQuery.addMustQueryClauses(
				_queries.term(Field.GROUP_ID, group.getGroupId())));

		if (testUserId != 0) {
			booleanQuery.addMustNotQueryClauses(
				_queries.term(Field.USER_ID, testUserId));
		}

		Group group = groups.get(0);

		return _searcher.search(
			getSearchRequestBuilder(
				group.getCompanyId()
			).emptySearchEnabled(
				true
			).fields(
				Field.USER_ID
			).modelIndexerClasses(
				User.class
			).query(
				booleanQuery
			).build());
	}

	protected GroupSearchFixture groupSearchFixture;
	protected OrganizationSearchFixture organizationSearchFixture;
	protected UserGroupSearchFixture userGroupSearchFixture;
	protected UserSearchFixture userSearchFixture;

	private String _getTimesReport(Map<String, String> map) {
		StringBundler sb = new StringBundler((2 * map.size()) + 1);

		sb.append(StringPool.NEW_LINE);

		for (Map.Entry<String, String> entry : map.entrySet()) {
			sb.append(String.valueOf(entry));
			sb.append(StringPool.NEW_LINE);
		}

		return sb.toString();
	}

	private Dictionary<String, Object> _toDictionary(Map<String, String> map) {
		return new HashMapDictionary<>(new HashMap<String, Object>(map));
	}

	private static final String _CLASS_NAME = User.class.getName();

	private static final boolean _REPORT_TIMES_AND_FAIL = false;

	private static final boolean _STRESS_MODE_10_MIN_TO_RUN_ALL_TESTS = false;

	@Inject
	private static OrganizationLocalService _organizationLocalService;

	@Inject
	private static Queries _queries;

	@Inject
	private static Reindexer _reindexer;

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

	@DeleteAfterTestRun
	private List<Group> _groups;

	private int _groupsCount;

	@DeleteAfterTestRun
	private List<Organization> _organizations;

	@DeleteAfterTestRun
	private List<UserGroup> _userGroups;

	@DeleteAfterTestRun
	private List<User> _users;

	private int _usersCount;

}