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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousMailExecutionTestListener;
import com.liferay.portal.util.BaseSubscriptionTestCase;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.util.WikiTestUtil;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 * @author Roberto Díaz
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousMailExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class WikiSubscriptionTest extends BaseSubscriptionTestCase {

	@Ignore
	@Override
	@Test
	public void testSubscriptionBaseModelWhenInRootContainerModel() {
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
	public void testSubscriptionRootContainerModelWhenInRootContainerModel() {
	}

	@Ignore
	@Override
	@Test
	public void testSubscriptionRootContainerModelWhenInSubcontainerModel() {
	}

	@Override
	protected long addBaseModel(long containerModelId) throws Exception {
		WikiPage page = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), group.getGroupId(), containerModelId,
			ServiceTestUtil.randomString(), true);

		return page.getResourcePrimKey();
	}

	@Override
	protected long addContainerModel(long containerModelId) throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		WikiNode node = WikiNodeLocalServiceUtil.addNode(
			TestPropsValues.getUserId(), ServiceTestUtil.randomString(),
			StringPool.BLANK, serviceContext);

		return node.getNodeId();
	}

	@Override
	protected void addSubscriptionBaseModel(long baseModelId) throws Exception {
		WikiPage page = WikiPageLocalServiceUtil.getPage(baseModelId);

		WikiPageLocalServiceUtil.subscribePage(
			TestPropsValues.getUserId(), page.getNodeId(), page.getTitle());
	}

	@Override
	protected void addSubscriptionContainerModel(long containerModelId)
		throws Exception {

		WikiNodeLocalServiceUtil.subscribeNode(
			TestPropsValues.getUserId(), containerModelId);
	}

	@Override
	protected long updateEntry(long baseModelId) throws Exception {
		WikiPage page = WikiTestUtil.updatePage(
			WikiPageLocalServiceUtil.getPage(baseModelId, true));

		return page.getResourcePrimKey();
	}

}