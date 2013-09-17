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

package com.liferay.portlet.wiki.service;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.SubscriptionLocalServiceUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.util.BaseSubscriptionTestCase;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.model.WikiPageConstants;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class WikiSubscriptionTest extends BaseSubscriptionTestCase {

	public long addContainer(long containerId) throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		WikiNode node = WikiNodeLocalServiceUtil.addNode(
			TestPropsValues.getUserId(), ServiceTestUtil.randomString(),
			StringPool.BLANK, serviceContext);

		return node.getNodeId();
	}

	public long addEntry(long containerId) throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		serviceContext.setCommand(Constants.ADD);

		WikiPage page = WikiPageLocalServiceUtil.addPage(
			TestPropsValues.getUserId(), containerId,
			ServiceTestUtil.randomString(), WikiPageConstants.VERSION_DEFAULT,
			ServiceTestUtil.randomString(50), ServiceTestUtil.randomString(),
			false, WikiPageConstants.DEFAULT_FORMAT, true, StringPool.BLANK,
			StringPool.BLANK, serviceContext);

		return page.getResourcePrimKey();
	}

	public void addSubscriptionContainer(long containerId) throws Exception {
		SubscriptionLocalServiceUtil.addSubscription(
			TestPropsValues.getUserId(), group.getGroupId(),
			WikiNode.class.getName(), containerId);
	}

	public void addSubscriptionEntry(long entryId) throws Exception {
		SubscriptionLocalServiceUtil.addSubscription(
			TestPropsValues.getUserId(), group.getGroupId(),
			WikiPage.class.getName(), entryId);
	}

	@Ignore
	@Override
	@Test
	public void testSubscriptionContainerWhenAddEntryInRootContainer()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testSubscriptionContainerWhenAddEntryInSubContainer()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testSubscriptionEntryWhenAddEntryInRootContainer()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testSubscriptionRootContainerWhenAddEntryInContainer()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testSubscriptionRootContainerWhenAddEntryInRootContainer()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testSubscriptionRootContainerWhenAddEntryInSubContainer()
		throws Exception {
	}

	public long updateEntry(long entryId) throws Exception {
		WikiPage oldPage = WikiPageLocalServiceUtil.getPage(entryId, true);

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		serviceContext.setCommand(Constants.ADD);

		WikiPage page = WikiPageLocalServiceUtil.updatePage(
			TestPropsValues.getUserId(), oldPage.getNodeId(),
			oldPage.getTitle(), oldPage.getVersion(),
			ServiceTestUtil.randomString(50), ServiceTestUtil.randomString(),
			false, WikiPageConstants.DEFAULT_FORMAT, StringPool.BLANK,
			StringPool.BLANK, serviceContext);

		return page.getResourcePrimKey();
	}

}