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

package com.liferay.portlet.journal.service;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.SubscriptionLocalServiceUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.util.BaseSubscriptionTestCase;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.util.JournalTestUtil;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zsolt Berentey
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class JournalSubscriptionTest extends BaseSubscriptionTestCase {

	@Override
	public long addBaseModel(long containerModelId) throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		serviceContext.setCommand(Constants.ADD);

		JournalArticle article = JournalTestUtil.addArticle(
			group.getGroupId(), ServiceTestUtil.randomString(),
			ServiceTestUtil.randomString(), serviceContext);

		return article.getResourcePrimKey();
	}

	@Override
	public long addContainerModel(long containerModelId) throws Exception {
		JournalFolder folder = JournalTestUtil.addFolder(
			group.getGroupId(), containerModelId,
			ServiceTestUtil.randomString());

		return folder.getFolderId();
	}

	@Override
	public void addSubscriptionBaseModel(long baseModelId) throws Exception {
		SubscriptionLocalServiceUtil.addSubscription(
			TestPropsValues.getUserId(), group.getGroupId(),
			JournalArticle.class.getName(), baseModelId);
	}

	@Override
	public void addSubscriptionContainerModel(long containerModelId)
		throws Exception {

		long classPK = containerModelId;

		if (containerModelId == DEFAULT_PARENT_CONTAINER_MODEL_ID) {
			classPK = group.getGroupId();
		}

		SubscriptionLocalServiceUtil.addSubscription(
			TestPropsValues.getUserId(), group.getGroupId(),
			JournalFolder.class.getName(), classPK);
	}

	@Ignore
	@Override
	@Test
	public void testSubscriptionContainerModelWhenInContainerModel() {
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
	public long updateEntry(long baseModelId) throws Exception {
		JournalArticle article =
			JournalArticleLocalServiceUtil.getLatestArticle(
				baseModelId, WorkflowConstants.STATUS_APPROVED, true);

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		serviceContext.setCommand(Constants.UPDATE);

		article = JournalTestUtil.updateArticle(
			article, ServiceTestUtil.randomString(),
			ServiceTestUtil.randomString(), serviceContext);

		return article.getResourcePrimKey();
	}

}