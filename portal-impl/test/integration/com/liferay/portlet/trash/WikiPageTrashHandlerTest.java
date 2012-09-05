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
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.service.TrashEntryLocalServiceUtil;
import com.liferay.portlet.wiki.BaseWikiTrashHandlerTestCase;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageServiceUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class WikiPageTrashHandlerTest extends BaseWikiTrashHandlerTestCase {

	@Before
	public void setUp() {
		FinderCacheUtil.clearCache();
	}

	@Test
	@Transactional
	public void testTrashAndDelete() throws Exception {
		trashWikiPage(true);
	}

	@Test
	@Transactional
	public void testTrashAndRestoreApproved() throws Exception {
		trashWikiPage(false);
	}

	protected void trashWikiPage(boolean delete) throws Exception {
		Group group = ServiceTestUtil.addGroup(
			"WikiPageTrashHandlerTest#testGroup");

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		serviceContext.setScopeGroupId(group.getGroupId());

		String titleWikiNode = ServiceTestUtil.randomString();

		WikiNode wikiNode = addWikiNode(titleWikiNode, serviceContext);

		String titleWikiPage = ServiceTestUtil.randomString();

		long nodeId = wikiNode.getNodeId();

		int initialWikiPagesCount = getWikiPagesNotInTrashCount(nodeId);
		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());
		int initialWikiPagesSearchCount = searchCount(
			WikiPage.class.getName(), group.getGroupId());
		int initialTrashEntriesSearchCount = searchTrashEntriesCount(
			titleWikiPage, serviceContext);

		WikiPage wikiPage = addWikiPage(nodeId, titleWikiPage, serviceContext);

		int oldStatus = wikiPage.getStatus();

		Assert.assertEquals(
			initialWikiPagesCount + 1, getWikiPagesNotInTrashCount(nodeId));
		Assert.assertEquals(
			initialTrashEntriesCount, getTrashEntriesCount(group.getGroupId()));

		Assert.assertTrue(
			isAssetEntryVisible(
				WikiPage.class.getName(), wikiPage.getResourcePrimKey()));

		Assert.assertEquals(
			initialTrashEntriesSearchCount,
			searchTrashEntriesCount(titleWikiPage, serviceContext));

		WikiPageServiceUtil.moveEntryToTrash(
			wikiPage.getNodeId(), wikiPage.getTitle());

		TrashEntry trashEntry = TrashEntryLocalServiceUtil.getEntry(
			WikiPage.class.getName(), wikiPage.getResourcePrimKey());

		Assert.assertEquals(
			wikiPage.getResourcePrimKey(), trashEntry.getClassPK());
		Assert.assertEquals(
			WorkflowConstants.STATUS_IN_TRASH, wikiPage.getStatus());
		Assert.assertEquals(
			initialWikiPagesCount, getWikiPagesNotInTrashCount(nodeId));
		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			getTrashEntriesCount(group.getGroupId()));
		Assert.assertFalse(
			isAssetEntryVisible(
				WikiPage.class.getName(), wikiPage.getResourcePrimKey()));
		Assert.assertEquals(
			initialWikiPagesSearchCount,
			searchCount(WikiPage.class.getName(), group.getGroupId()));
		Assert.assertEquals(
			initialTrashEntriesSearchCount + 1,
			searchTrashEntriesCount(titleWikiPage, serviceContext));

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			wikiPage.getModelClassName());

		if (delete) {
			trashHandler.deleteTrashEntry(wikiPage.getResourcePrimKey());

			Assert.assertEquals(
				initialWikiPagesCount, getWikiPagesNotInTrashCount(nodeId));
			Assert.assertNull(
				fetchAssetEntry(
					WikiPage.class.getName(), wikiPage.getResourcePrimKey()));
			Assert.assertEquals(
				initialWikiPagesSearchCount,
				searchCount(WikiPage.class.getName(), group.getGroupId()));
			Assert.assertEquals(
				initialTrashEntriesSearchCount,
				searchTrashEntriesCount(titleWikiPage, serviceContext));
		}
		else {
			trashHandler.restoreTrashEntry(wikiPage.getResourcePrimKey());

			wikiPage = WikiPageServiceUtil.getPage(
				wikiPage.getNodeId(), wikiPage.getTitle());

			Assert.assertEquals(oldStatus, wikiPage.getStatus());
			Assert.assertEquals(
				initialWikiPagesCount + 1, getWikiPagesNotInTrashCount(nodeId));

			Assert.assertTrue(isAssetEntryVisible(
				WikiPage.class.getName(), wikiPage.getResourcePrimKey()));
			Assert.assertEquals(
				initialWikiPagesSearchCount + 1,
				searchCount(WikiPage.class.getName(), group.getGroupId()));

			Assert.assertEquals(
				initialTrashEntriesSearchCount,
				searchTrashEntriesCount(titleWikiPage, serviceContext));

			trashHandler.deleteTrashEntry(wikiPage.getResourcePrimKey());
		}
	}

}