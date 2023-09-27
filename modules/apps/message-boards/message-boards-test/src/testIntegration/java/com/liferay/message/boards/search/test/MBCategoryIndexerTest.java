/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.service.MBCategoryLocalServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
@Sync
public class MBCategoryIndexerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_indexer = IndexerRegistryUtil.getIndexer(MBCategory.class);
	}

	@Test
	public void testNotReindexGroupNotContainingMBCategories()
		throws Exception {

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_LOG_NAME, LoggerTestUtil.DEBUG)) {

			_group = GroupTestUtil.addGroup(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
				GroupConstants.DEFAULT_PARENT_GROUP_ID);

			_indexer.reindex(
				new String[] {String.valueOf(TestPropsValues.getCompanyId())});

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 0, logEntries.size());
		}
	}

	@Test
	public void testReindexGroupContainingMBCategories() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_LOG_NAME, LoggerTestUtil.DEBUG)) {

			_group = GroupTestUtil.addGroup(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
				GroupConstants.DEFAULT_PARENT_GROUP_ID);

			MBCategory mbCategory = MBCategoryLocalServiceUtil.addCategory(
				TestPropsValues.getUserId(), 0, RandomTestUtil.randomString(),
				RandomTestUtil.randomString(),
				ServiceContextTestUtil.getServiceContext(
					_group.getGroupId(), TestPropsValues.getUserId()));

			_indexer.reindex(
				new String[] {String.valueOf(TestPropsValues.getCompanyId())});

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				StringBundler.concat(
					"Reindexing message boards categories for category ID ",
					mbCategory.getCategoryId(), " and group ID ",
					_group.getGroupId()),
				logEntry.getMessage());
		}
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	private static final String _LOG_NAME =
		"com.liferay.message.boards.internal.search.spi.model.index." +
			"contributor.MBCategoryModelIndexerWriterContributor";

	@DeleteAfterTestRun
	private Group _group;

	private Indexer<MBCategory> _indexer;

}