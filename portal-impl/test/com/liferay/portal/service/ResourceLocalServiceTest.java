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
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.DoAsUserThread;
import com.liferay.portal.util.TestPropsValues;

/**
 * @author Brian Wing Shun Chan
 */
public class ResourceLocalServiceTest extends BaseServiceTestCase {

	private static int threadCount = 5;

	@Override
	public void setUp() throws Exception {
		super.setUp();

		Group group = GroupLocalServiceUtil.getGroup(
			TestPropsValues.getCompanyId(), GroupConstants.GUEST);

		long plid = LayoutLocalServiceUtil.getDefaultPlid(group.getGroupId());

		_layout = LayoutLocalServiceUtil.getLayout(plid);

		long[] groupIds = new long[] {group.getGroupId()};

		_userIds = new long[threadCount];

		for (int i = 0 ; i < threadCount; i++) {
			User user = addUser(
				"rlst" + (i + 1), false, groupIds);

			_userIds[i] = user.getUserId();
		}
	}

	public void testAddResourcesConcurrently() throws Exception {
		DoAsUserThread[] doAsUserThreads = new DoAsUserThread[threadCount];

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
			"Only " + successCount + " out of " + threadCount +
				" threads added resources successfully",
			successCount == threadCount);
	}

	private Layout _layout;
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
					_layout.getCompanyId(), Layout.class.getName(),
					ResourceConstants.SCOPE_INDIVIDUAL,
					String.valueOf(_layout.getPlid()));
			}
			catch (NoSuchResourceException nsre) {
				boolean addGroupPermission = true;
				boolean addGuestPermission = true;

				if (_layout.isPrivateLayout()) {
					addGuestPermission = false;
				}

				ResourceLocalServiceUtil.addResources(
					_layout.getCompanyId(), _layout.getGroupId(), 0,
					Layout.class.getName(), _layout.getPlid(), false,
					addGroupPermission, addGuestPermission);
			}
		}

	}

}