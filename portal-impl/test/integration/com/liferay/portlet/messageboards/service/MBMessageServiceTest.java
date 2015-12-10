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

package com.liferay.portlet.messageboards.service;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.DoAsUserThread;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageConstants;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

import org.hibernate.util.JDBCExceptionReporter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Alexander Chow
 */
@Sync
public class MBMessageServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE,
			MainServletTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		String name = "Test Category";
		String description = "This is a test category.";
		String displayStyle = MBCategoryConstants.DEFAULT_DISPLAY_STYLE;
		String emailAddress = null;
		String inProtocol = null;
		String inServerName = null;
		int inServerPort = 0;
		boolean inUseSSL = false;
		String inUserName = null;
		String inPassword = null;
		int inReadInterval = 0;
		String outEmailAddress = null;
		boolean outCustom = false;
		String outServerName = null;
		int outServerPort = 0;
		boolean outUseSSL = false;
		String outUserName = null;
		String outPassword = null;
		boolean allowAnonymous = false;
		boolean mailingListActive = false;

		_group = GroupTestUtil.addGroup();

		for (int i = 0; i < ServiceTestUtil.THREAD_COUNT; i++) {
			UserTestUtil.addUser(_group.getGroupId());
		}

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setGroupPermissions(
			new String[] {ActionKeys.ADD_MESSAGE, ActionKeys.VIEW});
		serviceContext.setGuestPermissions(
			new String[] {ActionKeys.ADD_MESSAGE, ActionKeys.VIEW});

		_category = MBCategoryServiceUtil.addCategory(
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, name, description,
			displayStyle, emailAddress, inProtocol, inServerName, inServerPort,
			inUseSSL, inUserName, inPassword, inReadInterval, outEmailAddress,
			outCustom, outServerName, outServerPort, outUseSSL, outUserName,
			outPassword, allowAnonymous, mailingListActive, serviceContext);

		_userIds = UserLocalServiceUtil.getGroupUserIds(_group.getGroupId());
	}

	@Test
	public void testAddMessagesConcurrently() throws Exception {
		DoAsUserThread[] doAsUserThreads = new DoAsUserThread[_userIds.length];

		for (int i = 0; i < doAsUserThreads.length; i++) {
			String subject = "Test Message " + i;

			doAsUserThreads[i] = new AddMessageThread(_userIds[i], subject);
		}

		try (CaptureAppender captureAppender1 =
				Log4JLoggerTestUtil.configureLog4JLogger(
					BasePersistenceImpl.class.getName(), Level.ERROR);
				CaptureAppender captureAppender2 =
					Log4JLoggerTestUtil.configureLog4JLogger(
						DoAsUserThread.class.getName(), Level.ERROR);
				CaptureAppender captureAppender3 =
					Log4JLoggerTestUtil.configureLog4JLogger(
						JDBCExceptionReporter.class.getName(), Level.ERROR)) {

			for (DoAsUserThread doAsUserThread : doAsUserThreads) {
				doAsUserThread.start();
			}

			for (DoAsUserThread doAsUserThread : doAsUserThreads) {
				doAsUserThread.join();
			}

			DB db = DBFactoryUtil.getDB();

			if (db.getDBType() == DBType.SYBASE) {
				for (LoggingEvent loggingEvent :
						captureAppender1.getLoggingEvents()) {

					String message = loggingEvent.getRenderedMessage();

					Assert.assertTrue(
						message.startsWith("Caught unexpected exception"));
				}

				for (LoggingEvent loggingEvent :
						captureAppender2.getLoggingEvents()) {

					String message = loggingEvent.getRenderedMessage();

					StringBundler sb = new StringBundler();

					sb.append("com.liferay.portal.kernel.exception.");
					sb.append("SystemException:");

					Assert.assertTrue(message.startsWith(sb.toString()));
				}

				for (LoggingEvent loggingEvent :
						captureAppender3.getLoggingEvents()) {

					String message = loggingEvent.getRenderedMessage();

					Assert.assertTrue(message.contains("Your server command"));
					Assert.assertTrue(
						message.contains(
							"encountered a deadlock situation. Please re-run " +
								"your command."));
				}
			}
		}

		int successCount = 0;

		for (DoAsUserThread doAsUserThread : doAsUserThreads) {
			if (doAsUserThread.isSuccess()) {
				successCount++;
			}
		}

		Assert.assertTrue(
			"Only " + successCount + " out of " + _userIds.length +
				" threads added messages successfully",
			successCount == _userIds.length);
	}

	private MBCategory _category;

	@DeleteAfterTestRun
	private Group _group;

	private long[] _userIds;

	private class AddMessageThread extends DoAsUserThread {

		public AddMessageThread(long userId, String subject) {
			super(userId, ServiceTestUtil.RETRY_COUNT);

			_subject = subject;
		}

		@Override
		protected void doRun() throws Exception {
			String body = "This is a test message.";
			List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
				new ArrayList<>();
			boolean anonymous = false;
			double priority = 0.0;
			boolean allowPingbacks = false;

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAddGroupPermissions(true);
			serviceContext.setAddGuestPermissions(true);

			MBMessage mbMessage = MBMessageServiceUtil.addMessage(
				_category.getGroupId(), _category.getCategoryId(), _subject,
				body, MBMessageConstants.DEFAULT_FORMAT, inputStreamOVPs,
				anonymous, priority, allowPingbacks, serviceContext);

			MBMessageLocalServiceUtil.deleteMessage(mbMessage);
		}

		private final String _subject;

	}

}