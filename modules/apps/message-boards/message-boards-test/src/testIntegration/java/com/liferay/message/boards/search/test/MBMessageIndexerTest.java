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

package com.liferay.message.boards.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.message.boards.constants.MBCategoryConstants;
import com.liferay.message.boards.constants.MBMessageConstants;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBMessageLocalServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.comment.CommentManagerUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.InputStream;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

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
public class MBMessageIndexerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_indexer = IndexerRegistryUtil.getIndexer(MBMessage.class);

		_company1 = CompanyTestUtil.addCompany();

		_user1 = UserTestUtil.getAdminUser(_company1.getCompanyId());
	}

	@Test
	public void testNotReindexGroupNotContainingMBMessages() throws Exception {
		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					_LOG_NAME, Level.DEBUG)) {

			GroupTestUtil.addGroup(
				_company1.getCompanyId(), _user1.getUserId(),
				GroupConstants.DEFAULT_PARENT_GROUP_ID);

			_indexer.reindex(
				new String[] {String.valueOf(_company1.getCompanyId())});

			List<LoggingEvent> loggingEvents =
				captureAppender.getLoggingEvents();

			Assert.assertEquals(
				loggingEvents.toString(), 0, loggingEvents.size());
		}
	}

	@Test
	public void testReindexGroupContainingMBDiscussion() throws Exception {
		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					_LOG_NAME, Level.DEBUG)) {

			Group group = GroupTestUtil.addGroup(
				_company1.getCompanyId(), _user1.getUserId(),
				GroupConstants.DEFAULT_PARENT_GROUP_ID);

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					group.getGroupId(), _user1.getUserId());

			CommentManagerUtil.addComment(
				_user1.getUserId(), group.getGroupId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomLong(),
				RandomTestUtil.randomString(), s -> serviceContext);

			_indexer.reindex(
				new String[] {String.valueOf(_company1.getCompanyId())});

			List<LoggingEvent> loggingEvents =
				captureAppender.getLoggingEvents();

			Assert.assertEquals(
				loggingEvents.toString(), 2, loggingEvents.size());

			LoggingEvent loggingEvent = loggingEvents.get(0);

			Assert.assertEquals(
				StringBundler.concat(
					"Reindexing message boards messages for message board ",
					"category ID ", MBCategoryConstants.DISCUSSION_CATEGORY_ID,
					" and group ID ", group.getGroupId()),
				loggingEvent.getMessage());
		}
	}

	@Test
	public void testReindexGroupContainingMBMessage() throws Exception {
		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					_LOG_NAME, Level.DEBUG)) {

			Group group = GroupTestUtil.addGroup(
				_company1.getCompanyId(), _user1.getUserId(),
				GroupConstants.DEFAULT_PARENT_GROUP_ID);

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					group.getGroupId(), _user1.getUserId());

			List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
				Collections.emptyList();

			MBMessageLocalServiceUtil.addMessage(
				_user1.getUserId(), RandomTestUtil.randomString(),
				group.getGroupId(),
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, 0,
				MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				MBMessageConstants.DEFAULT_FORMAT, inputStreamOVPs, false, 0.0,
				false, serviceContext);

			_indexer.reindex(
				new String[] {String.valueOf(_company1.getCompanyId())});

			List<LoggingEvent> loggingEvents =
				captureAppender.getLoggingEvents();

			Assert.assertEquals(
				loggingEvents.toString(), 1, loggingEvents.size());

			LoggingEvent loggingEvent = loggingEvents.get(0);

			Assert.assertEquals(
				StringBundler.concat(
					"Reindexing message boards messages for message board ",
					"category ID ",
					MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
					" and group ID ", group.getGroupId()),
				loggingEvent.getMessage());
		}
	}

	private static final String _LOG_NAME =
		"com.liferay.message.boards.internal.search.spi.model.index." +
			"contributor.MBMessageModelIndexerWriterContributor";

	@DeleteAfterTestRun
	private Company _company1;

	private Indexer<MBMessage> _indexer;
	private User _user1;

}