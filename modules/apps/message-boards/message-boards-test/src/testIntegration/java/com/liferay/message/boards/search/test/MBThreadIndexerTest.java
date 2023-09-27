/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.message.boards.constants.MBCategoryConstants;
import com.liferay.message.boards.constants.MBMessageConstants;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBMessageLocalServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.comment.CommentManagerUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.InputStream;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@RunWith(Arquillian.class)
@Sync
public class MBThreadIndexerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_indexer = IndexerRegistryUtil.getIndexer(MBThread.class);
	}

	@Test
	public void testNotReindexGroupNotContainingMBMessages() throws Exception {
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
	public void testReindexGroupContainingMBDiscussion() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_LOG_NAME, LoggerTestUtil.DEBUG)) {

			_group = GroupTestUtil.addGroup(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
				GroupConstants.DEFAULT_PARENT_GROUP_ID);

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					_group.getGroupId(), TestPropsValues.getUserId());

			CommentManagerUtil.addComment(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomLong(),
				RandomTestUtil.randomString(), s -> serviceContext);

			_indexer.reindex(
				new String[] {String.valueOf(TestPropsValues.getCompanyId())});

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				StringBundler.concat(
					"Reindexing message boards threads for message board ",
					"category ID ", MBCategoryConstants.DISCUSSION_CATEGORY_ID,
					" and group ID ", _group.getGroupId()),
				logEntry.getMessage());
		}
	}

	@Test
	public void testReindexGroupContainingMBMessage() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_LOG_NAME, LoggerTestUtil.DEBUG)) {

			_group = GroupTestUtil.addGroup(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
				GroupConstants.DEFAULT_PARENT_GROUP_ID);

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					_group.getGroupId(), TestPropsValues.getUserId());

			List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
				Collections.emptyList();

			MBMessageLocalServiceUtil.addMessage(
				TestPropsValues.getUserId(), RandomTestUtil.randomString(),
				_group.getGroupId(),
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, 0,
				MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				MBMessageConstants.DEFAULT_FORMAT, inputStreamOVPs, false, 0.0,
				false, serviceContext);

			_indexer.reindex(
				new String[] {String.valueOf(TestPropsValues.getCompanyId())});

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				StringBundler.concat(
					"Reindexing message boards threads for message board ",
					"category ID ",
					MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
					" and group ID ", _group.getGroupId()),
				logEntry.getMessage());
		}
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	private static final String _LOG_NAME =
		"com.liferay.message.boards.internal.search.spi.model.index." +
			"contributor.MBThreadModelIndexerWriterContributor";

	@DeleteAfterTestRun
	private Group _group;

	private Indexer<MBThread> _indexer;

}