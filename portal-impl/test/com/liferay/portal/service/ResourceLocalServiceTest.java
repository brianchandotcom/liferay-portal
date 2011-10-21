/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.util.TestPropsValues;

/**
 * @author Brian Wing Shun Chan
 */
public class ResourceLocalServiceTest extends BaseServiceTestCase {

	@Override
	public void setUp() throws Exception {
		super.setUp();

		_userIds = new long[THREAD_COUNT];

		for (int i = 0 ; i < THREAD_COUNT; i++) {
			User user = addUser(
				"ResourceLocalServiceTest" + (i + 1), false,
				new long[] {TestPropsValues.getGroupId()});

			_userIds[i] = user.getUserId();
		}
	}

	public void testAddResourcesConcurrently() throws Exception {
		DoAsUserThread[] doAsUserThreads = new DoAsUserThread[THREAD_COUNT];

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

		assertTrue(
			"Only " + successCount + " out of " + THREAD_COUNT +
				" threads added resources successfully",
			successCount == THREAD_COUNT);
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