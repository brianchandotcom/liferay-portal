/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service;

import com.liferay.portal.NoSuchResourceException;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.DoAsUserThread;
import com.liferay.portal.test.EnvironmentConfigTestListener;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.TestPropsValues;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners = {EnvironmentConfigTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class ResourceLocalServiceTest {

	@Before
	public void setUp() throws Exception {
		_userIds = new long[ServiceTestUtil.THREAD_COUNT];

		for (int i = 0 ; i < ServiceTestUtil.THREAD_COUNT; i++) {
			User user = ServiceTestUtil.addUser(
				"ResourceLocalServiceTest" + (i + 1), false,
				new long[] {TestPropsValues.getGroupId()});

			_userIds[i] = user.getUserId();
		}
	}

	@Test
	public void testAddResourcesConcurrently() throws Exception {
		DoAsUserThread[] doAsUserThreads =
			new DoAsUserThread[ServiceTestUtil.THREAD_COUNT];

		for (int i = 0; i < doAsUserThreads.length; i++) {
			doAsUserThreads[i] = new AddResources(_userIds[i]);
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
			"Only " + successCount + " out of " + ServiceTestUtil.THREAD_COUNT +
				" threads added resources successfully",
			successCount == ServiceTestUtil.THREAD_COUNT);
	}

	private long[] _userIds;

	private class AddResources extends DoAsUserThread {

		public AddResources(long userId) {
			super(userId);
		}

		@Override
		public boolean isSuccess() {
			return true;
		}

		@Override
		protected void doRun() throws Exception {
			try {
				ResourceLocalServiceUtil.getResource(
					TestPropsValues.getCompanyId(), Layout.class.getName(),
					ResourceConstants.SCOPE_INDIVIDUAL,
					String.valueOf(TestPropsValues.getPlid()));
			}
			catch (NoSuchResourceException nsre) {
				boolean addGroupPermission = true;
				boolean addGuestPermission = true;

				ResourceLocalServiceUtil.addResources(
					TestPropsValues.getCompanyId(),
					TestPropsValues.getGroupId(), 0, Layout.class.getName(),
					TestPropsValues.getPlid(), false, addGroupPermission,
					addGuestPermission);
			}
		}

	}

}