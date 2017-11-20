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

package com.liferay.invitation.invite.members.web.internal.portlet.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.CustomSQLParam;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.comparator.UserFirstNameComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.IOException;

import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Hugo Huijser
 */
@RunWith(Arquillian.class)
@Sync
public class InviteMembersPortletTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testGetAvailableUsers() throws Exception {
		List<User> availableUsers = _getAvailableUsers(
			_group.getCompanyId(), _group.getGroupId());

		Assert.assertEquals(
			availableUsers.toString(), 1, availableUsers.size());

		User user = UserTestUtil.addUser();

		availableUsers = _getAvailableUsers(
			_group.getCompanyId(), _group.getGroupId());

		Assert.assertEquals(
			availableUsers.toString(), 2, availableUsers.size());

		UserLocalServiceUtil.addGroupUser(
			_group.getGroupId(), user.getUserId());

		availableUsers = _getAvailableUsers(
			_group.getCompanyId(), _group.getGroupId());

		Assert.assertEquals(
			availableUsers.toString(), 1, availableUsers.size());

		if (availableUsers.size() == 1) {
			Assert.assertEquals("asdf1", 1, 2);
		}
		else if (availableUsers.size() == 2) {
			Assert.assertEquals("asdf2", 1, 3);
		}
	}

	private List<User> _getAvailableUsers(long companyId, long groupId)
		throws Exception {

		LinkedHashMap usersParams = new LinkedHashMap();

		usersParams.put("usersInvited", new CustomSQLParam(_getSQL(), groupId));

		return UserLocalServiceUtil.search(
			companyId, null, WorkflowConstants.STATUS_ANY, usersParams,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new UserFirstNameComparator(true));
	}

	private String _getSQL() {
		StringBundler sb = new StringBundler();

		sb.append("WHERE\n");
		sb.append("User_.userId NOT IN\n");
		sb.append("(\n");
		sb.append("SELECT\n");
		sb.append("DISTINCT Users_Groups.userId\n");
		sb.append("FROM\n");
		sb.append("Users_Groups\n");
		sb.append("WHERE\n");
		sb.append("Users_Groups.groupId = ?\n");
		sb.append(")\n");

		return _transform(sb.toString());
	}

	private String _transform(String sql) {
		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(sql))) {

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				line = line.trim();

				if (line.startsWith(StringPool.CLOSE_PARENTHESIS)) {
					sb.setIndex(sb.index() - 1);
				}

				sb.append(line);

				if (!line.endsWith(StringPool.OPEN_PARENTHESIS)) {
					sb.append(StringPool.SPACE);
				}
			}
		}
		catch (IOException ioe) {
			return sql;
		}

		return sb.toString();
	}

	@DeleteAfterTestRun
	private Group _group;

}