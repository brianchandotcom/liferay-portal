/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.test.util.MBTestUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.test.util.IndexerFixture;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.search.test.util.SummaryFixture;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adam Brandizzi
 */
@RunWith(Arquillian.class)
@Sync
public class MBMessageIndexerSummaryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		indexerFixture = new IndexerFixture<>(MBMessage.class);

		summaryFixture = new SummaryFixture<>(
			MBMessage.class,
			_groupLocalService.getGroup(TestPropsValues.getGroupId()),
			LocaleUtil.US, TestPropsValues.getUser());
	}

	@Test
	public void testSummarySearchFullSubject() throws Exception {
		message = MBTestUtil.addMessage(
			"MB Thread Message Subject", "MB Thread Message Body");

		Document document = indexerFixture.searchOnlyOne(
			"MB Thread Message Subject");

		summaryFixture.assertSummary(
			"MB Thread Message Subject", "MB Thread Message Body", document);
	}

	@Test
	public void testSummarySearchFullTitle() throws Exception {
		message = MBTestUtil.addMessage(
			"MB Thread Message Subject", "MB Thread Message Body");

		Document document = indexerFixture.searchOnlyOne(
			"MB Thread Message Title");

		summaryFixture.assertSummary(
			"MB Thread Message Subject", "MB Thread Message Body", document);
	}

	@Test
	public void testSummarySearchWords() throws Exception {
		message = MBTestUtil.addMessage(
			"MB Thread Message Subject", "MB Thread Message Body");

		Document document = indexerFixture.searchOnlyOne("MB");

		summaryFixture.assertSummary(
			"MB Thread Message Subject", "MB Thread Message Body", document);

		document = indexerFixture.searchOnlyOne("Thread");

		summaryFixture.assertSummary(
			"MB Thread Message Subject", "MB Thread Message Body", document);

		document = indexerFixture.searchOnlyOne("Message");

		summaryFixture.assertSummary(
			"MB Thread Message Subject", "MB Thread Message Body", document);

		document = indexerFixture.searchOnlyOne("Subject");

		summaryFixture.assertSummary(
			"MB Thread Message Subject", "MB Thread Message Body", document);

		document = indexerFixture.searchOnlyOne("Body");

		summaryFixture.assertSummary(
			"MB Thread Message Subject", "MB Thread Message Body", document);
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected IndexerFixture<MBMessage> indexerFixture;

	@DeleteAfterTestRun
	protected MBMessage message;

	protected SummaryFixture<MBMessage> summaryFixture;

	@Inject
	private GroupLocalService _groupLocalService;

}