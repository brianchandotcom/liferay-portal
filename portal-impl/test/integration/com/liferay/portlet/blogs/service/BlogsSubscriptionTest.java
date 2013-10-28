/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.blogs.service;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.SubscriptionLocalServiceUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.util.BaseSubscriptionTestCase;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.blogs.model.BlogsEntry;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class BlogsSubscriptionTest extends BaseSubscriptionTestCase {

	@Override
	public long addBaseModel(long containerModelId) throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		serviceContext.setCommand(Constants.ADD);
		serviceContext.setLayoutFullURL("http://localhost");

		BlogsEntry entry = BlogsEntryLocalServiceUtil.addEntry(
			TestPropsValues.getUserId(), ServiceTestUtil.randomString(),
			StringPool.BLANK, ServiceTestUtil.randomString(), 1, 1, 2012, 12, 0,
			false, false, new String[0], false, StringPool.BLANK,
			StringPool.BLANK, null, serviceContext);

		return entry.getEntryId();
	}

	@Override
	public long addContainerModel(long containerModelId) throws Exception {
		return 0;
	}

	@Override
	public void addSubscriptionBaseModel(long baseModelId) {
	}

	@Override
	public void addSubscriptionContainerModel(long containerModelId)
		throws Exception {

		SubscriptionLocalServiceUtil.addSubscription(
			TestPropsValues.getUserId(), group.getGroupId(),
			BlogsEntry.class.getName(), group.getGroupId());
	}

	@Ignore
	@Override
	@Test
	public void testSubscriptionBaseModelWhenInContainerModel() {
	}

	@Ignore
	@Override
	@Test
	public void testSubscriptionBaseModelWhenInRootContainerModel() {
	}

	@Ignore
	@Override
	@Test
	public void testSubscriptionContainerModelWhenInContainerModel() {
	}

	@Ignore
	@Override
	@Test
	public void testSubscriptionContainerModelWhenInRootContainerModel() {
	}

	@Ignore
	@Override
	@Test
	public void testSubscriptionContainerModelWhenInSubcontainerModel() {
	}

	@Ignore
	@Override
	@Test
	public void testSubscriptionRootContainerModelWhenInContainerModel() {
	}

	@Ignore
	@Override
	@Test
	public void testSubscriptionRootContainerModelWhenInSubcontainerModel() {
	}

	@Override
	public long updateEntry(long baseModelId) {
		return 0;
	}

}