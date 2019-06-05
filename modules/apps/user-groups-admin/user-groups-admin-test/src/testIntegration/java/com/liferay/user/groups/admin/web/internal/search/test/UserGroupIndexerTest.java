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

package com.liferay.user.groups.admin.web.internal.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.UserGroupTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.users.admin.kernel.util.UsersAdmin;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author André de Oliveira
 */
@RunWith(Arquillian.class)
public class UserGroupIndexerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_indexer = _indexerRegistry.getIndexer(UserGroup.class);
	}

	@Test
	public void testSearchUserGroups() throws Exception {
		_role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		long companyId = _role.getCompanyId();

		int count = _userGroupLocalService.searchCount(
			companyId, null, new LinkedHashMap<String, Object>());

		UserGroup userGroup = addUserGroup();

		addUserGroup();

		GroupLocalServiceUtil.addRoleGroup(
			_role.getRoleId(), userGroup.getGroupId());

		Hits hits = search(companyId);

		Assert.assertEquals(hits.toString(), count + 2, hits.getLength());
	}

	protected UserGroup addUserGroup() throws Exception {
		UserGroup userGroup = UserGroupTestUtil.addUserGroup();

		_userGroups.add(userGroup);

		return userGroup;
	}

	protected SearchContext getSearchContext(long companyId) {
		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);

		return searchContext;
	}

	protected Hits search(long companyId) throws Exception {
		SearchContext searchContext = getSearchContext(companyId);

		return _indexer.search(searchContext);
	}

	@Inject
	private static IndexerRegistry _indexerRegistry;

	@Inject
	private static UserGroupLocalService _userGroupLocalService;

	@Inject
	private static UsersAdmin _usersAdmin;

	private Indexer<UserGroup> _indexer;

	@DeleteAfterTestRun
	private Role _role;

	@DeleteAfterTestRun
	private final List<UserGroup> _userGroups = new LinkedList<>();

}