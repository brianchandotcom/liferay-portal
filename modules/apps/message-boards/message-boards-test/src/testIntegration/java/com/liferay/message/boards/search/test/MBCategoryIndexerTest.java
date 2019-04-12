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
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.service.MBCategoryLocalServiceUtil;
import com.liferay.petra.string.StringBundler;
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
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

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
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class MBCategoryIndexerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_indexer = IndexerRegistryUtil.getIndexer(MBCategory.class);

		_company = CompanyTestUtil.addCompany();

		_user = UserTestUtil.getAdminUser(_company.getCompanyId());
	}

	@Test
	public void testNotReindexGroupNotContainingMBCategories()
		throws Exception {

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					_LOG_NAME, Level.DEBUG)) {

			GroupTestUtil.addGroup(
				_company.getCompanyId(), _user.getUserId(),
				GroupConstants.DEFAULT_PARENT_GROUP_ID);

			_indexer.reindex(
				new String[] {String.valueOf(_company.getCompanyId())});

			List<LoggingEvent> loggingEvents =
				captureAppender.getLoggingEvents();

			Assert.assertEquals(
				loggingEvents.toString(), 0, loggingEvents.size());
		}
	}

	@Test
	public void testReindexGroupContainingMBCategories() throws Exception {
		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					_LOG_NAME, Level.DEBUG)) {

			Group group = GroupTestUtil.addGroup(
				_company.getCompanyId(), _user.getUserId(),
				GroupConstants.DEFAULT_PARENT_GROUP_ID);

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					group.getGroupId(), _user.getUserId());

			MBCategory mbCategory = MBCategoryLocalServiceUtil.addCategory(
				_user.getUserId(), 0, RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), serviceContext);

			_indexer.reindex(
				new String[] {String.valueOf(_company.getCompanyId())});

			List<LoggingEvent> loggingEvents =
				captureAppender.getLoggingEvents();

			Assert.assertEquals(
				loggingEvents.toString(), 1, loggingEvents.size());

			LoggingEvent loggingEvent = loggingEvents.get(0);

			Assert.assertEquals(
				StringBundler.concat(
					"Reindexing message boards categories for category ID ",
					mbCategory.getCategoryId(), " and group ID ",
					group.getGroupId()),
				loggingEvent.getMessage());
		}
	}

	private static final String _LOG_NAME =
		"com.liferay.message.boards.internal.search.spi.model.index." +
			"contributor.MBCategoryModelIndexerWriterContributor";

	@DeleteAfterTestRun
	private Company _company;

	private Indexer<MBCategory> _indexer;
	private User _user;

}