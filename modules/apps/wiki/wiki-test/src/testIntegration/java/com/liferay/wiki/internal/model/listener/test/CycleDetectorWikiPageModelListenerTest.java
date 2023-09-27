/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.internal.model.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.wiki.constants.WikiPageConstants;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.wiki.test.util.WikiTestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Tomas Polesovsky
 */
@RunWith(Arquillian.class)
public class CycleDetectorWikiPageModelListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		UserTestUtil.setUser(TestPropsValues.getUser());

		_group = GroupTestUtil.addGroup();

		_node = WikiTestUtil.addNode(_group.getGroupId());
	}

	@Test
	public void testCycle() throws Exception {
		WikiPage wikiPage1 = WikiPageLocalServiceUtil.addPage(
			TestPropsValues.getUserId(), _node.getNodeId(), "Title1",
			StringPool.BLANK, StringPool.BLANK, true, new ServiceContext());

		WikiPage wikiPage2 = WikiPageLocalServiceUtil.addPage(
			TestPropsValues.getUserId(), _node.getNodeId(), "Title2",
			StringPool.BLANK, StringPool.BLANK, true, new ServiceContext());

		WikiPage wikiPage3 = WikiPageLocalServiceUtil.addPage(
			TestPropsValues.getUserId(), _node.getNodeId(), "Title3",
			StringPool.BLANK, StringPool.BLANK, true, new ServiceContext());

		try {
			wikiPage1.setParentTitle("Title2");

			wikiPage1 = WikiPageLocalServiceUtil.updateWikiPage(wikiPage1);

			wikiPage2.setParentTitle("Title3");

			WikiPageLocalServiceUtil.updateWikiPage(wikiPage2);

			wikiPage3.setParentTitle("Title1");

			wikiPage3 = WikiPageLocalServiceUtil.updateWikiPage(wikiPage3);

			Assert.fail();
		}
		catch (RuntimeException runtimeException) {
			Assert.assertEquals(
				"Unable to update wiki page Title3 because a cycle was " +
					"detected",
				runtimeException.getMessage());
		}

		try {
			wikiPage3.setParentTitle("Other");

			WikiPageLocalServiceUtil.updateWikiPage(wikiPage3);

			wikiPage1.setTitle("Other");

			WikiPageLocalServiceUtil.updateWikiPage(wikiPage1);

			Assert.fail();
		}
		catch (RuntimeException runtimeException) {
			Assert.assertEquals(
				"Unable to update wiki page Other because a cycle was detected",
				runtimeException.getMessage());
		}
	}

	@Test
	public void testSelfReferencingWikiPage() throws Exception {
		String title = "Cycling Page";

		String parentTitle = title;

		try {
			WikiPageLocalServiceUtil.addPage(
				TestPropsValues.getUserId(), _node.getNodeId(), title,
				WikiPageConstants.VERSION_DEFAULT, StringPool.BLANK,
				StringPool.BLANK, false, "creole", false, parentTitle,
				StringPool.BLANK, new ServiceContext());

			Assert.fail();
		}
		catch (RuntimeException runtimeException) {
			Assert.assertEquals(
				"Unable to create wiki page " + title +
					" because a cycle was detected",
				runtimeException.getMessage());
		}
	}

	@Test
	public void testSelfReferencingWikiPageDelayedSet() throws Exception {
		WikiPage wikiPage1 = WikiPageLocalServiceUtil.addPage(
			TestPropsValues.getUserId(), _node.getNodeId(), "Title",
			StringPool.BLANK, StringPool.BLANK, true, new ServiceContext());

		try {
			wikiPage1.setParentTitle("Title");

			wikiPage1 = WikiPageLocalServiceUtil.updateWikiPage(wikiPage1);

			Assert.fail();
		}
		catch (RuntimeException runtimeException) {
			Assert.assertEquals(
				"Unable to update wiki page Title because a cycle was detected",
				runtimeException.getMessage());
		}

		try {
			wikiPage1.setParentTitle("Other Title");

			wikiPage1 = WikiPageLocalServiceUtil.updateWikiPage(wikiPage1);

			wikiPage1.setTitle("Other Title");

			WikiPageLocalServiceUtil.updateWikiPage(wikiPage1);

			Assert.fail();
		}
		catch (RuntimeException runtimeException) {
			Assert.assertEquals(
				"Unable to update wiki page Other Title because a cycle was " +
					"detected",
				runtimeException.getMessage());
		}
	}

	@DeleteAfterTestRun
	private Group _group;

	private WikiNode _node;

}