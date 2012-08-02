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

package com.liferay.portlet.trash;

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageConstants;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBMessageServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadServiceUtil;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.model.TrashEntryList;
import com.liferay.portlet.trash.service.TrashEntryLocalServiceUtil;
import com.liferay.portlet.trash.service.TrashEntryServiceUtil;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zsolt Berentey
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class MBThreadTrashHandlerTest {

	@Before
	public void setUp() {
		FinderCacheUtil.clearCache();
	}

	@Test
	@Transactional
	public void testTrashAndDelete() throws Exception {
		testTrash(true);
	}

	@Test
	@Transactional
	public void testTrashAndRestore() throws Exception {
		testTrash(false);
	}

	protected MBThread addThread(
			Group group, MBCategory category, ServiceContext serviceContext)
		throws Exception {

		long groupId = group.getGroupId();
		long categoryId = MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID;
		String subject = "Subject";
		String body = "Body";
		String format = MBMessageConstants.DEFAULT_FORMAT;
		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			new ArrayList<ObjectValuePair<String, InputStream>>(5);
		boolean anonymous = false;
		double priority = 0.0;
		boolean allowPingbacks = false;

		if (category != null) {
			categoryId = category.getCategoryId();
		}

		MBMessage message = MBMessageServiceUtil.addMessage(
			groupId, categoryId, subject, body, format, inputStreamOVPs,
			anonymous, priority, allowPingbacks, serviceContext);

		return message.getThread();
	}

	protected AssetEntry fetchAssetEntry(long threadId) throws Exception {
		return AssetEntryLocalServiceUtil.fetchEntry(
			MBThread.class.getName(), threadId);
	}

	protected int getThreadsNotInTrashCount(long groupId) throws Exception {
		QueryDefinition queryDefinition = new QueryDefinition(
			WorkflowConstants.STATUS_ANY);

		return MBThreadLocalServiceUtil.getGroupThreadsCount(
			groupId, queryDefinition);
	}

	protected int getTrashEntriesCount(long groupId) throws Exception {
		TrashEntryList trashEntryList = TrashEntryServiceUtil.getEntries(
			groupId);

		return trashEntryList.getCount();
	}

	protected long getUserId() {
		return GetterUtil.getLong(PrincipalThreadLocal.getName());
	}

	protected boolean isAssetEntryVisible(MBThread thread) throws Exception {
		AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
			MBMessage.class.getName(), thread.getRootMessageId());

		return assetEntry.isVisible();
	}

	protected int searchThreadCount(long groupId) throws Exception {
		Thread.sleep(1000 * TestPropsValues.JUNIT_DELAY_FACTOR);

		Indexer indexer = IndexerRegistryUtil.getIndexer(MBMessage.class);

		SearchContext searchContext = ServiceTestUtil.getSearchContext();

		searchContext.setGroupIds(new long[] {groupId});

		Hits results = indexer.search(searchContext);

		return results.getLength();
	}

	protected int searchTrashEntriesCount(
			String keywords, ServiceContext serviceContext)
		throws Exception {

		Thread.sleep(1000 * TestPropsValues.JUNIT_DELAY_FACTOR);

		Hits results = TrashEntryServiceUtil.search(
			serviceContext.getCompanyId(), serviceContext.getScopeGroupId(),
			serviceContext.getUserId(), keywords, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		return results.getLength();
	}

	protected void testTrash(boolean delete) throws Exception {
		Group group = ServiceTestUtil.addGroup(
			"MBThreadTrashHandlerTest#testGroup");

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		serviceContext.setScopeGroupId(group.getGroupId());

		int initialThreadsCount = getThreadsNotInTrashCount(group.getGroupId());
		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());
		int initialThreadsSearchCount = searchThreadCount(group.getGroupId());
		int initialTrashEntriesSearchCount = searchTrashEntriesCount(
			"Subject", serviceContext);

		MBThread thread = addThread(group, null, serviceContext);

		int oldStatus = thread.getStatus();

		Assert.assertEquals(
			initialThreadsCount + 1,
			getThreadsNotInTrashCount(group.getGroupId()));
		Assert.assertEquals(
			initialTrashEntriesCount, getTrashEntriesCount(group.getGroupId()));

		Assert.assertTrue(isAssetEntryVisible(thread));
		Assert.assertEquals(
			initialThreadsSearchCount + 1,
			searchThreadCount(group.getGroupId()));
		Assert.assertEquals(
			initialTrashEntriesSearchCount,
			searchTrashEntriesCount("Subject", serviceContext));

		thread = MBThreadServiceUtil.moveThreadToTrash(thread.getThreadId());

		TrashEntry trashEntry = TrashEntryLocalServiceUtil.getEntry(
			MBThread.class.getName(), thread.getThreadId());

		Assert.assertEquals(thread.getThreadId(), trashEntry.getClassPK());
		Assert.assertEquals(
			WorkflowConstants.STATUS_IN_TRASH, thread.getStatus());
		Assert.assertEquals(
			initialThreadsCount, getThreadsNotInTrashCount(group.getGroupId()));
		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			getTrashEntriesCount(group.getGroupId()));
		Assert.assertFalse(isAssetEntryVisible(thread));
		Assert.assertEquals(
			initialThreadsSearchCount, searchThreadCount(group.getGroupId()));
		Assert.assertEquals(
			initialTrashEntriesSearchCount + 1,
			searchTrashEntriesCount("Subject", serviceContext));

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			thread.getModelClassName());

		if (delete) {
			trashHandler.deleteTrashEntry(thread.getThreadId());

			Assert.assertEquals(
				initialThreadsCount,
				getThreadsNotInTrashCount(group.getGroupId()));
			Assert.assertNull(fetchAssetEntry(thread.getThreadId()));
			Assert.assertEquals(
				initialThreadsSearchCount,
				searchThreadCount(group.getGroupId()));
			Assert.assertEquals(
				initialTrashEntriesSearchCount,
				searchTrashEntriesCount("Subject", serviceContext));
		}
		else {
			trashHandler.restoreTrashEntry(thread.getThreadId());

			thread = MBThreadLocalServiceUtil.getThread(thread.getThreadId());

			Assert.assertEquals(oldStatus, thread.getStatus());
			Assert.assertEquals(
				initialThreadsCount + 1,
				getThreadsNotInTrashCount(group.getGroupId()));

			Assert.assertTrue(isAssetEntryVisible(thread));
			Assert.assertEquals(
				initialThreadsSearchCount + 1,
				searchThreadCount(group.getGroupId()));
			Assert.assertEquals(
				initialTrashEntriesSearchCount,
				searchTrashEntriesCount("Subject", serviceContext));

			trashHandler.deleteTrashEntry(thread.getThreadId());
		}
	}

}