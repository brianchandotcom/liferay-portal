/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.wiki.service.WikiPageServiceUtil;
import com.liferay.wiki.test.util.WikiTestUtil;

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
		UserTestUtil.setUser(TestPropsValues.getUser());

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

		UserTestUtil.setUser(TestPropsValues.getUser());

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
		UserTestUtil.setUser(TestPropsValues.getUser());

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