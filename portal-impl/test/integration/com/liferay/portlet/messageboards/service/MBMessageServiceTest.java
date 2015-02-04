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

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.DoAsUserThread;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageConstants;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Alexander Chow
 */
public class MBMessageServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

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
			UserTestUtil.addUser(
				RandomTestUtil.randomString(), _group.getGroupId());
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

		for (DoAsUserThread doAsUserThread : doAsUserThreads) {
			doAsUserThread.start();
		}

		for (DoAsUserThread doAsUserThread : doAsUserThreads) {
			doAsUserThread.join();
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
			super(userId);

			_subject = subject;
		}

		@Override
		public boolean isSuccess() {
			return true;
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