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

package com.liferay.wiki.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.wiki.service.WikiPageServiceUtil;
import com.liferay.wiki.util.test.WikiTestUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Andrew Betts
 */
@RunWith(Arquillian.class)
public class WikiPageFinderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_node = WikiTestUtil.addNode(_group.getGroupId());

		WikiTestUtil.addPage(_group.getGroupId(), _node.getNodeId(), true);

		_user = UserTestUtil.addGroupUser(_group, RoleConstants.USER);

		WikiTestUtil.addPage(
			_user.getUserId(), _group.getGroupId(), _node.getNodeId(),
			RandomTestUtil.randomString(), true);

		WikiPage userDraft = WikiTestUtil.addPage(
			_user.getUserId(), _group.getGroupId(), _node.getNodeId(),
			RandomTestUtil.randomString(), false);

		userDraft.setHead(true);

		WikiPageLocalServiceUtil.updateWikiPage(userDraft);
	}

	@Test
	public void testQueryByG_N_H_SApprovedStatus() throws Exception {
		ServiceTestUtil.setUser(TestPropsValues.getUser());

		int count = WikiPageServiceUtil.getPagesCount(
			_group.getGroupId(), _node.getNodeId(), true, _user.getUserId(),
			false, WorkflowConstants.STATUS_APPROVED);

		List<WikiPage> pages = WikiPageServiceUtil.getPages(
			_group.getGroupId(), _node.getNodeId(), true, _user.getUserId(),
			false, WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		Assert.assertEquals(1, count);
		Assert.assertEquals(pages.toString(), count, pages.size());
	}

	@Test
	public void testQueryByG_N_H_SApprovedStatusIncludeOwner()
		throws Exception {

		ServiceTestUtil.setUser(TestPropsValues.getUser());

		int count = WikiPageServiceUtil.getPagesCount(
			_group.getGroupId(), _node.getNodeId(), true,
			TestPropsValues.getUserId(), true,
			WorkflowConstants.STATUS_APPROVED);

		List<WikiPage> pages = WikiPageServiceUtil.getPages(
			_group.getGroupId(), _node.getNodeId(), true,
			TestPropsValues.getUserId(), true,
			WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		Assert.assertEquals(2, count);
		Assert.assertEquals(pages.toString(), count, pages.size());
	}

	@Test
	public void testQueryByG_N_H_SDraftStatusIncludeOwner() throws Exception {
		ServiceTestUtil.setUser(TestPropsValues.getUser());

		int count = WikiPageServiceUtil.getPagesCount(
			_group.getGroupId(), _node.getNodeId(), true, _user.getUserId(),
			true, WorkflowConstants.STATUS_DRAFT);

		List<WikiPage> pages = WikiPageServiceUtil.getPages(
			_group.getGroupId(), _node.getNodeId(), true, _user.getUserId(),
			true, WorkflowConstants.STATUS_DRAFT, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		Assert.assertEquals(2, count);
		Assert.assertEquals(pages.toString(), count, pages.size());
	}

	@DeleteAfterTestRun
	private Group _group;

	private WikiNode _node;

	@DeleteAfterTestRun
	private User _user;

}