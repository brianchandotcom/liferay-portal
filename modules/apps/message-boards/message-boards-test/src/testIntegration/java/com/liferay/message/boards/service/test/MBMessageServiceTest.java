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

package com.liferay.message.boards.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.message.boards.constants.MBCategoryConstants;
import com.liferay.message.boards.constants.MBMessageConstants;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBBanLocalServiceUtil;
import com.liferay.message.boards.service.MBCategoryServiceUtil;
import com.liferay.message.boards.service.MBMessageLocalServiceUtil;
import com.liferay.message.boards.service.MBMessageServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.service.permission.ModelPermissionsFactory;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.test.context.ContextUserReplace;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.security.permission.DoAsUserThread;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

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
import org.junit.runner.RunWith;

/**
 * @author Alexander Chow
 */
@RunWith(Arquillian.class)
public class MBMessageServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

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

		_users = new User[ServiceTestUtil.THREAD_COUNT];

		for (int i = 0; i < ServiceTestUtil.THREAD_COUNT; i++) {
			User user = UserTestUtil.addUser(_group.getGroupId());

			_users[i] = user;
		}

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		ModelPermissions modelPermissions = ModelPermissionsFactory.create(
			new String[] {ActionKeys.ADD_MESSAGE, ActionKeys.VIEW},
			new String[] {ActionKeys.ADD_MESSAGE, ActionKeys.VIEW});

		serviceContext.setModelPermissions(modelPermissions);

		_category = MBCategoryServiceUtil.addCategory(
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, name, description,
			displayStyle, emailAddress, inProtocol, inServerName, inServerPort,
			inUseSSL, inUserName, inPassword, inReadInterval, outEmailAddress,
			outCustom, outServerName, outServerPort, outUseSSL, outUserName,
			outPassword, allowAnonymous, mailingListActive, serviceContext);
	}

	@Test
	public void testAddMessagesConcurrently() throws Exception {
		DoAsUserThread[] doAsUserThreads = new DoAsUserThread[_users.length];

		for (int i = 0; i < doAsUserThreads.length; i++) {
			String subject = "Test Message " + i;

			doAsUserThreads[i] = new AddMessageThread(
				_users[i].getUserId(), subject);
		}

		try (CaptureAppender captureAppender1 =
				Log4JLoggerTestUtil.configureLog4JLogger(
					BasePersistenceImpl.class.getName(), Level.ERROR);
			CaptureAppender captureAppender2 =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"com.liferay.portal.spring.transaction." +
						"DefaultTransactionExecutor",
					Level.ERROR);
			CaptureAppender captureAppender3 =
				Log4JLoggerTestUtil.configureLog4JLogger(
					DoAsUserThread.class.getName(), Level.ERROR);
			CaptureAppender captureAppender4 =
				Log4JLoggerTestUtil.configureLog4JLogger(
					JDBCExceptionReporter.class.getName(), Level.ERROR);
			CaptureAppender captureAppender5 =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"com.liferay.portal.messaging.internal." +
						"SynchronousDestination",
					Level.ERROR)) {

			for (DoAsUserThread doAsUserThread : doAsUserThreads) {
				doAsUserThread.start();
			}

			for (DoAsUserThread doAsUserThread : doAsUserThreads) {
				doAsUserThread.join();
			}

			DB db = DBManagerUtil.getDB();

			if (db.getDBType() == DBType.HYPERSONIC) {
				for (LoggingEvent loggingEvent :
						captureAppender2.getLoggingEvents()) {

					String message = loggingEvent.getRenderedMessage();

					Assert.assertTrue(
						message.startsWith(
							"Application exception overridden by commit " +
								"exception"));
				}

				for (LoggingEvent loggingEvent :
						captureAppender5.getLoggingEvents()) {

					String message = loggingEvent.getRenderedMessage();

					Assert.assertTrue(
						message.startsWith("Unable to process message"));
				}
			}
			else if (db.getDBType() == DBType.SYBASE) {
				for (LoggingEvent loggingEvent :
						captureAppender1.getLoggingEvents()) {

					String message = loggingEvent.getRenderedMessage();

					Assert.assertTrue(
						message.startsWith("Caught unexpected exception"));
				}

				for (LoggingEvent loggingEvent :
						captureAppender3.getLoggingEvents()) {

					String message = loggingEvent.getRenderedMessage();

					StringBundler sb = new StringBundler(2);

					sb.append("com.liferay.portal.kernel.exception.");
					sb.append("SystemException:");

					Assert.assertTrue(message.startsWith(sb.toString()));
				}

				for (LoggingEvent loggingEvent :
						captureAppender4.getLoggingEvents()) {

					String message = loggingEvent.getRenderedMessage();

					Assert.assertTrue(
						message, message.contains("Your server command"));
					Assert.assertTrue(
						message,
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
			StringBundler.concat(
				"Only ", successCount, " out of ", _users.length,
				" threads added messages successfully"),
			successCount == _users.length);
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testBannedUserCannotAddMessageInCategory() throws Exception {
		User user = _users[0];

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, TestPropsValues.getUserId());

		MBBanLocalServiceUtil.addBan(
			TestPropsValues.getUserId(), user.getUserId(), serviceContext);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			MBMessageServiceUtil.addMessage(
				_group.getGroupId(), _category.getCategoryId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				MBMessageConstants.DEFAULT_FORMAT, new ArrayList<>(), false,
				0.0, false, serviceContext);
		}
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testBannedUserCannotAddRootMessage() throws Exception {
		User user = _users[0];

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, TestPropsValues.getUserId());

		MBBanLocalServiceUtil.addBan(
			TestPropsValues.getUserId(), user.getUserId(), serviceContext);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			MBMessageServiceUtil.addMessage(
				_group.getGroupId(),
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				MBMessageConstants.DEFAULT_FORMAT, new ArrayList<>(), false,
				0.0, false, serviceContext);
		}
	}

	private MBCategory _category;

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private User[] _users;

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