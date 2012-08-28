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
import com.liferay.portlet.wiki.service.WikiNodeServiceUtil;

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
public class WikiNodeTrashHandlerTest extends BaseWikiTrashHandlerTestCase {

	@Before
	public void setUp() {
		FinderCacheUtil.clearCache();
	}

	@Test
	@Transactional
	public void testTrashAndDelete() throws Exception {
		trashWikiNode(true);
	}

	@Test
	@Transactional
	public void testTrashAndRestoreApproved() throws Exception {
		trashWikiNode(false);
	}

	protected void trashWikiNode(boolean delete) throws Exception {
		Group group = ServiceTestUtil.addGroup(
			"WikiNodeTrashHandlerTest#testGroup");

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		serviceContext.setScopeGroupId(group.getGroupId());

		String titleWikiNode = ServiceTestUtil.randomString();

		int initialWikiNodesSearchCount = searchCount(
			WikiNode.class.getName(), group.getGroupId());
		int initialTrashEntriesSearchCount = searchTrashEntriesCount(
			titleWikiNode, serviceContext);

		WikiNode wikiNode = addWikiNode(titleWikiNode, serviceContext);

		long nodeId = wikiNode.getNodeId();

		int initialWikiPagesCount = getWikiPagesNotInTrashCount(nodeId);

		String titleWikiPage = ServiceTestUtil.randomString();

		WikiPage wikiPage = addWikiPage(nodeId, titleWikiPage, serviceContext);

		int oldStatus = wikiPage.getStatus();

		WikiNodeServiceUtil.moveEntryToTrash(wikiNode.getNodeId());

		TrashEntry trashEntry = TrashEntryLocalServiceUtil.getEntry(
			WikiNode.class.getName(), wikiNode.getNodeId());

		Assert.assertEquals(wikiNode.getNodeId(), trashEntry.getClassPK());
		Assert.assertEquals(
			WorkflowConstants.STATUS_IN_TRASH, wikiNode.getStatus());
		Assert.assertEquals(
			initialWikiPagesCount, getWikiPagesNotInTrashCount(nodeId));
		Assert.assertFalse(isAssetEntryVisible(
			WikiPage.class.getName(), wikiPage.getResourcePrimKey()));
		Assert.assertEquals(
			initialWikiNodesSearchCount + 1,
				searchCount(WikiNode.class.getName(), group.getGroupId()));

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			wikiNode.getModelClassName());

		if (delete) {
			trashHandler.deleteTrashEntry(wikiNode.getNodeId());

			Assert.assertEquals(
				initialWikiPagesCount, getWikiPagesNotInTrashCount(nodeId));
			Assert.assertEquals(
				initialWikiNodesSearchCount,
				searchCount(WikiNode.class.getName(), group.getGroupId()));
			Assert.assertEquals(
				initialTrashEntriesSearchCount,
				searchTrashEntriesCount(titleWikiNode, serviceContext));
		}
		else {
			trashHandler.restoreTrashEntry(wikiNode.getNodeId());

			wikiNode = WikiNodeServiceUtil.getNode(wikiNode.getNodeId());

			Assert.assertEquals(oldStatus, wikiNode.getStatus());
			Assert.assertEquals(
				initialWikiPagesCount + 1, getWikiPagesNotInTrashCount(nodeId));

			Assert.assertTrue(isAssetEntryVisible(
				WikiPage.class.getName(), wikiPage.getResourcePrimKey()));

			Assert.assertEquals(
				initialTrashEntriesSearchCount,
				searchTrashEntriesCount(titleWikiNode, serviceContext));

			Assert.assertEquals(
				initialWikiNodesSearchCount,
				searchCount(WikiNode.class.getName(), group.getGroupId()));

			trashHandler.deleteTrashEntry(wikiNode.getNodeId());
		}
	}

}