/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.wiki.trash;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.trash.BaseTrashHandlerTestCase;
import com.liferay.portlet.trash.util.TrashUtil;
import com.liferay.portlet.wiki.asset.WikiPageAssetRenderer;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.portlet.wiki.util.WikiTestUtil;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 * @author Roberto Díaz
 */
@ExecutionTestListeners(listeners = {
	MainServletExecutionTestListener.class,
	SynchronousDestinationExecutionTestListener.class
})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class WikiPageTrashHandlerTest extends BaseTrashHandlerTestCase {

	@Test
	@Transactional
	public void testExplicitlyMoveChildWithChildAndParentWithChildToTrash()
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		WikiNode node = (WikiNode)getParentBaseModel(group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(node);
		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		WikiPage[] pages =
			WikiTestUtil.addParentPageWithChildPagesAndGrandChild(
				group.getGroupId(), node.getNodeId(), true, true);

		WikiPage parentPage = pages[0];
		WikiPage childPage = pages[1];
		WikiPage grandChildPage = pages[2];

		Assert.assertTrue(parentPage.isInTrashExplicitly());
		Assert.assertTrue(childPage.isInTrashExplicitly());
		Assert.assertTrue(grandChildPage.isInTrashImplicitly());
		Assert.assertEquals(
			childPage.getTitle(), grandChildPage.getParentTitle());
		Assert.assertEquals(
			initialBaseModelsCount, getNotInTrashBaseModelsCount(node));
		Assert.assertEquals(
			initialTrashEntriesCount + 2,
			getTrashEntriesCount(group.getGroupId()));
	}

	@Test
	@Transactional
	public void testExplicitMoveChildWithChildToTrash() throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		WikiNode node = (WikiNode)getParentBaseModel(group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(node);
		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		WikiPage[] pages =
			WikiTestUtil.addParentPageWithChildPagesAndGrandChild(
				group.getGroupId(), node.getNodeId(), true, false);

		WikiPage parentPage = pages[0];
		WikiPage childPage = pages[1];
		WikiPage grandChildPage = pages[2];

		Assert.assertFalse(parentPage.isInTrash());
		Assert.assertTrue(childPage.isInTrashExplicitly());
		Assert.assertTrue(grandChildPage.isInTrashImplicitly());
		Assert.assertEquals(
			childPage.getTitle(), grandChildPage.getParentTitle());
		Assert.assertEquals(
			initialBaseModelsCount + 1, getNotInTrashBaseModelsCount(node));
		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			getTrashEntriesCount(group.getGroupId()));
	}

	@Test
	@Transactional
	public void testExplicitMoveParentPageWithChildPagesAndGrandChildToTrash()
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		WikiNode node = (WikiNode)getParentBaseModel(group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(node);
		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		WikiPage[] pages =
			WikiTestUtil.addParentPageWithChildPagesAndGrandChild(
				group.getGroupId(), node.getNodeId(), false, true);

		WikiPage parentPage = pages[0];
		WikiPage childPage = pages[1];
		WikiPage grandChildPage = pages[2];

		Assert.assertTrue(parentPage.isInTrashExplicitly());
		Assert.assertTrue(childPage.isInTrashImplicitly());
		Assert.assertEquals(parentPage.getTitle(), childPage.getParentTitle());
		Assert.assertTrue(grandChildPage.isInTrashImplicitly());
		Assert.assertEquals(
			childPage.getTitle(), grandChildPage.getParentTitle());
		Assert.assertEquals(
			initialBaseModelsCount, getNotInTrashBaseModelsCount(node));
		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			getTrashEntriesCount(group.getGroupId()));
	}

	@Test
	public void testMoveExplicitlyChildAndParentPageWithRedirectPageToTrash()
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		WikiNode node = (WikiNode)getParentBaseModel(group, serviceContext);

		WikiPage[] pages =
			WikiTestUtil.addTrashedParentPageWithTrashedRedirectPage(
				group.getGroupId(), node.getNodeId(), true, false);

		WikiPage parentPage = pages[0];
		WikiPage childPage = pages[1];
		WikiPage redirectPage = pages[2];

		Assert.assertTrue(parentPage.isInTrashExplicitly());
		Assert.assertTrue(childPage.isInTrashExplicitly());
		Assert.assertEquals(childPage.getParentTitle(), parentPage.getTitle());
		Assert.assertTrue(redirectPage.isInTrashImplicitly());
		Assert.assertEquals(
			redirectPage.getRedirectTitle(), parentPage.getTitle());
	}

	@Test
	public void testMoveExplicitlyPageAndRedirectPageToTrash()
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		WikiNode node = (WikiNode)getParentBaseModel(group, serviceContext);

		WikiPage[] pages = WikiTestUtil.addTrashedPageWithTrashedRedirectPage(
			group.getGroupId(), node.getNodeId(), true);

		WikiPage page = pages[0];
		WikiPage redirectPage = pages[1];

		Assert.assertTrue(page.isInTrashExplicitly());
		Assert.assertTrue(redirectPage.isInTrashExplicitly());
		Assert.assertEquals(redirectPage.getRedirectTitle(), page.getTitle());
	}

	@Test
	public void testMoveExplicitlyParentAndChildAndRedirectPageToTrash()
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		WikiNode node = (WikiNode)getParentBaseModel(group, serviceContext);

		WikiPage[] pages =
			WikiTestUtil.addTrashedParentPageWithTrashedRedirectPage(
				group.getGroupId(), node.getNodeId(), true, true);

		WikiPage parentPage = pages[0];
		WikiPage childPage = pages[1];
		WikiPage redirectPage = pages[2];

		Assert.assertTrue(parentPage.isInTrashExplicitly());
		Assert.assertTrue(childPage.isInTrashExplicitly());
		Assert.assertEquals(childPage.getParentTitle(), parentPage.getTitle());
		Assert.assertTrue(redirectPage.isInTrashExplicitly());
		Assert.assertEquals(
			redirectPage.getRedirectTitle(), parentPage.getTitle());
	}

	@Test
	public void testMoveExplicitlyParentAndChildPageToTrash() throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		WikiNode node = (WikiNode)getParentBaseModel(group, serviceContext);

		WikiPage[] pages = WikiTestUtil.addTrashedPageWithTrashedChildPage(
			group.getGroupId(), node.getNodeId(), true);

		WikiPage parentPage = pages[0];
		WikiPage childPage = pages[1];

		Assert.assertTrue(parentPage.isInTrashExplicitly());
		Assert.assertTrue(childPage.isInTrashExplicitly());
		Assert.assertEquals(childPage.getParentTitle(), parentPage.getTitle());
	}

	@Test
	public void testMoveExplicitlyRedirectAndParentPageToTrash()
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		WikiNode node = (WikiNode)getParentBaseModel(group, serviceContext);

		WikiPage[] pages =
			WikiTestUtil.addTrashedParentPageWithTrashedRedirectPage(
				group.getGroupId(), node.getNodeId(), false, true);

		WikiPage parentPage = pages[0];
		WikiPage childPage = pages[1];
		WikiPage redirectPage = pages[2];

		Assert.assertTrue(parentPage.isInTrashExplicitly());
		Assert.assertTrue(childPage.isInTrashImplicitly());
		Assert.assertEquals(childPage.getParentTitle(), parentPage.getTitle());
		Assert.assertTrue(redirectPage.isInTrashExplicitly());
		Assert.assertEquals(
			redirectPage.getRedirectTitle(), parentPage.getTitle());
	}

	@Test
	public void testMovePageWithRedirectPageToTrash() throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		WikiNode node = (WikiNode)getParentBaseModel(group, serviceContext);

		WikiPage[] pages = WikiTestUtil.addTrashedPageWithTrashedRedirectPage(
			group.getGroupId(), node.getNodeId(), false);

		WikiPage page = pages[0];
		WikiPage redirectPage = pages[1];

		Assert.assertTrue(page.isInTrashExplicitly());
		Assert.assertTrue(redirectPage.isInTrashImplicitly());
		Assert.assertEquals(redirectPage.getRedirectTitle(), page.getTitle());
	}

	@Test
	public void testMoveParentPageToTrash() throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		WikiNode node = (WikiNode)getParentBaseModel(group, serviceContext);

		WikiPage[] pages = WikiTestUtil.addTrashedPageWithTrashedChildPage(
			group.getGroupId(), node.getNodeId(), false);

		WikiPage parentPage = pages[0];
		WikiPage childPage = pages[1];

		Assert.assertTrue(parentPage.isInTrashExplicitly());
		Assert.assertTrue(childPage.isInTrashImplicitly());
		Assert.assertEquals(childPage.getParentTitle(), parentPage.getTitle());
	}

	@Test
	@Transactional
	public void testMoveParentPageWithChangedInitialParentChildToTrash()
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		WikiNode node = (WikiNode)getParentBaseModel(group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(node);
		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		WikiPage[] pages =
			WikiTestUtil.addTrashedChildPageWithChangedParent(
				group.getGroupId(), node.getNodeId());

		WikiPage childPage = pages[0];
		WikiPage finalParentPage = pages[1];
		WikiPage initialParentPage = pages[2];

		Assert.assertFalse(childPage.isInTrash());
		Assert.assertFalse(finalParentPage.isInTrash());
		Assert.assertTrue(initialParentPage.isInTrashExplicitly());
		Assert.assertEquals(
			finalParentPage.getTitle(), childPage.getParentTitle());
		Assert.assertEquals(
			initialBaseModelsCount + 2, getNotInTrashBaseModelsCount(node));
		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			getTrashEntriesCount(group.getGroupId()));
		Assert.assertEquals(
			childPage.getParentTitle(), finalParentPage.getTitle());
	}

	@Test
	@Transactional
	public void testMoveParentPageWithRedirectAndChildPagesToTrash()
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		WikiNode node = (WikiNode)getParentBaseModel(group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(node);
		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		WikiPage[] pages = WikiTestUtil.addMovedPageWithChildAndGrandChild(
			group.getGroupId(), node.getNodeId());

		WikiPage page = pages[0];
		WikiPage redirectPage = pages[1];
		WikiPage childPage = pages[2];
		WikiPage grandChildPage = pages[3];

		moveBaseModelToTrash(redirectPage.getPrimaryKey());

		redirectPage = WikiPageLocalServiceUtil.getPageByPageId(
			redirectPage.getPageId());

		Assert.assertFalse(page.isInTrash());
		Assert.assertTrue(redirectPage.isInTrashExplicitly());
		Assert.assertEquals(page.getTitle(), redirectPage.getRedirectTitle());
		Assert.assertFalse(childPage.isInTrash());
		Assert.assertEquals(page.getTitle(), childPage.getParentTitle());
		Assert.assertFalse(grandChildPage.isInTrash());
		Assert.assertEquals(
			childPage.getTitle(), grandChildPage.getParentTitle());
		Assert.assertEquals(
			initialBaseModelsCount + 3, getNotInTrashBaseModelsCount(node));
		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			getTrashEntriesCount(group.getGroupId()));
	}

	@Test
	public void testMoveParentPageWithRedirectPageToTrash() throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		WikiNode node = (WikiNode)getParentBaseModel(group, serviceContext);

		WikiPage[] pages =
			WikiTestUtil.addTrashedParentPageWithTrashedRedirectPage(
				group.getGroupId(), node.getNodeId(), false, false);

		WikiPage parentPage = pages[0];
		WikiPage childPage = pages[1];
		WikiPage redirectPage = pages[2];

		Assert.assertTrue(parentPage.isInTrashExplicitly());
		Assert.assertTrue(childPage.isInTrashImplicitly());
		Assert.assertEquals(childPage.getParentTitle(), parentPage.getTitle());
		Assert.assertTrue(redirectPage.isInTrashImplicitly());
		Assert.assertEquals(
			redirectPage.getRedirectTitle(), parentPage.getTitle());
	}

	@Test
	@Transactional
	public void testRestoreExplicitlyTrashedChildWithTrashedParentFromTrash()
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		WikiNode node = (WikiNode)getParentBaseModel(group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(node);
		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		WikiPage[] pages =
			WikiTestUtil.addParentPageWithChildPagesAndGrandChild(
				group.getGroupId(), node.getNodeId(), true, true);

		WikiPage parentPage = pages[0];
		WikiPage childPage = pages[1];
		WikiPage grandChildPage = pages[2];

		restoreTrashEntry(childPage);

		parentPage = (WikiPage)getBaseModel(parentPage.getPrimaryKey());
		childPage = (WikiPage)getBaseModel(childPage.getPrimaryKey());
		grandChildPage = (WikiPage)getBaseModel(grandChildPage.getPrimaryKey());

		Assert.assertTrue(parentPage.isInTrashExplicitly());
		Assert.assertFalse(childPage.isInTrash());
		Assert.assertEquals(StringPool.BLANK, childPage.getParentTitle());
		Assert.assertFalse(grandChildPage.isInTrash());
		Assert.assertEquals(
			childPage.getTitle(), grandChildPage.getParentTitle());
		Assert.assertEquals(
			initialBaseModelsCount + 2, getNotInTrashBaseModelsCount(node));
		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			getTrashEntriesCount(group.getGroupId()));
	}

	@Test
	public void testRestoreExplicitlyTrashedPageAndRedirectPageFromTrash()
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		WikiNode node = (WikiNode)getParentBaseModel(group, serviceContext);

		WikiPage[] pages = WikiTestUtil.addTrashedPageWithTrashedRedirectPage(
			group.getGroupId(), node.getNodeId(), true);

		WikiPage page = pages[0];
		WikiPage redirectPage = pages[1];

		WikiPageLocalServiceUtil.restorePageFromTrash(
			TestPropsValues.getUserId(), page);

		page = WikiPageLocalServiceUtil.getPageByPageId(page.getPageId());
		redirectPage = WikiPageLocalServiceUtil.getPageByPageId(
			redirectPage.getPageId());

		Assert.assertFalse(page.isInTrash());
		Assert.assertTrue(redirectPage.isInTrashExplicitly());
		Assert.assertEquals(redirectPage.getRedirectTitle(), page.getTitle());

		WikiPageLocalServiceUtil.restorePageFromTrash(
			TestPropsValues.getUserId(), redirectPage);

		redirectPage = WikiPageLocalServiceUtil.getPageByPageId(
			redirectPage.getPageId());

		Assert.assertFalse(redirectPage.isInTrash());
		Assert.assertEquals(redirectPage.getRedirectTitle(), page.getTitle());
	}

	@Test
	public void
	testRestoreExplicitlyTrashedParentAndChildAndRedirectPageToTrash()
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		WikiNode node = (WikiNode)getParentBaseModel(group, serviceContext);

		WikiPage[] pages =
			WikiTestUtil.addTrashedParentPageWithTrashedRedirectPage(
				group.getGroupId(), node.getNodeId(), true, true);

		WikiPage parentPage = pages[0];
		WikiPage childPage = pages[1];
		WikiPage redirectPage = pages[2];

		WikiPageLocalServiceUtil.restorePageFromTrash(
			TestPropsValues.getUserId(), parentPage);

		parentPage = WikiPageLocalServiceUtil.getPageByPageId(
			parentPage.getPageId());
		childPage = WikiPageLocalServiceUtil.getPageByPageId(
			childPage.getPageId());
		redirectPage = WikiPageLocalServiceUtil.getPageByPageId(
			redirectPage.getPageId());

		Assert.assertFalse(parentPage.isInTrash());
		Assert.assertTrue(childPage.isInTrashExplicitly());
		Assert.assertEquals(childPage.getParentTitle(), parentPage.getTitle());
		Assert.assertTrue(redirectPage.isInTrashExplicitly());
		Assert.assertEquals(
			redirectPage.getRedirectTitle(), parentPage.getTitle());

		restoreTrashEntry(childPage);

		childPage = WikiPageLocalServiceUtil.getPageByPageId(
			childPage.getPageId());

		Assert.assertFalse(childPage.isInTrash());
		Assert.assertEquals(childPage.getParentTitle(), parentPage.getTitle());

		WikiPageLocalServiceUtil.restorePageFromTrash(
			TestPropsValues.getUserId(), redirectPage);

		redirectPage = WikiPageLocalServiceUtil.getPageByPageId(
			redirectPage.getPageId());

		Assert.assertFalse(redirectPage.isInTrash());
		Assert.assertEquals(
			redirectPage.getRedirectTitle(), parentPage.getTitle());
	}

	@Test
	public void testRestoreExplicitlyTrashedParentAndChildPageFromTrash()
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		WikiNode node = (WikiNode)getParentBaseModel(group, serviceContext);

		WikiPage[] pages = WikiTestUtil.addTrashedPageWithTrashedChildPage(
			group.getGroupId(), node.getNodeId(), true);

		WikiPage page = pages[0];
		WikiPage childPage = pages[1];

		restoreTrashEntry(page);

		page = WikiPageLocalServiceUtil.getPageByPageId(page.getPageId());
		childPage = WikiPageLocalServiceUtil.getPageByPageId(
			childPage.getPageId());

		Assert.assertFalse(page.isInTrash());
		Assert.assertTrue(childPage.isInTrashExplicitly());
		Assert.assertEquals(childPage.getParentTitle(), page.getTitle());

		WikiPageLocalServiceUtil.restorePageFromTrash(
			TestPropsValues.getUserId(), childPage);

		childPage = WikiPageLocalServiceUtil.getPageByPageId(
			childPage.getPageId());

		Assert.assertFalse(childPage.isInTrash());
		Assert.assertEquals(childPage.getParentTitle(), page.getTitle());
	}

	@Test
	public void testRestoreExplicitlyTrashedRedirectAndParentPageFromTrash()
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		WikiNode node = (WikiNode)getParentBaseModel(group, serviceContext);

		WikiPage[] pages =
			WikiTestUtil.addTrashedParentPageWithTrashedRedirectPage(
				group.getGroupId(), node.getNodeId(), false, true);

		WikiPage parentPage = pages[0];
		WikiPage childPage = pages[1];
		WikiPage redirectPage = pages[2];

		WikiPageLocalServiceUtil.restorePageFromTrash(
			TestPropsValues.getUserId(), parentPage);

		parentPage = WikiPageLocalServiceUtil.getPageByPageId(
			parentPage.getPageId());
		childPage = WikiPageLocalServiceUtil.getPageByPageId(
			childPage.getPageId());
		redirectPage = WikiPageLocalServiceUtil.getPageByPageId(
			redirectPage.getPageId());

		Assert.assertFalse(parentPage.isInTrash());
		Assert.assertFalse(childPage.isInTrash());
		Assert.assertEquals(childPage.getParentTitle(), parentPage.getTitle());
		Assert.assertTrue(redirectPage.isInTrashExplicitly());
		Assert.assertEquals(
			redirectPage.getRedirectTitle(), parentPage.getTitle());

		restoreTrashEntry(redirectPage);

		redirectPage = WikiPageLocalServiceUtil.getPageByPageId(
			redirectPage.getPageId());

		Assert.assertFalse(redirectPage.isInTrash());
		Assert.assertEquals(
			redirectPage.getRedirectTitle(), parentPage.getTitle());
	}

	@Test
	@Transactional
	public void testRestoreExplicitTrashedChildWithChildFromTrash()
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		WikiNode node = (WikiNode)getParentBaseModel(group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(node);
		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		WikiPage[] pages =
			WikiTestUtil.addParentPageWithChildPagesAndGrandChild(
				group.getGroupId(), node.getNodeId(), true, false);

		WikiPage parentPage = pages[0];
		WikiPage childPage = pages[1];
		WikiPage grandChildPage = pages[2];

		restoreTrashEntry(childPage);

		parentPage = (WikiPage)getBaseModel(parentPage.getPrimaryKey());
		childPage = (WikiPage)getBaseModel(childPage.getPrimaryKey());
		grandChildPage = (WikiPage)getBaseModel(grandChildPage.getPrimaryKey());

		Assert.assertFalse(parentPage.isInTrash());
		Assert.assertFalse(childPage.isInTrash());
		Assert.assertEquals(parentPage.getTitle(), childPage.getParentTitle());
		Assert.assertFalse(grandChildPage.isInTrash());
		Assert.assertEquals(
			childPage.getTitle(), grandChildPage.getParentTitle());
		Assert.assertEquals(
			initialBaseModelsCount + 3, getNotInTrashBaseModelsCount(node));
		Assert.assertEquals(
			initialTrashEntriesCount, getTrashEntriesCount(group.getGroupId()));
	}

	@Test
	public void
	testRestorePageWithRedirectAndExplicitlyTrashedChildPageFromTrash()
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		WikiNode node = (WikiNode)getParentBaseModel(group, serviceContext);

		WikiPage[] pages =
			WikiTestUtil.addTrashedParentPageWithTrashedRedirectPage(
				group.getGroupId(), node.getNodeId(), true, false);

		WikiPage parentPage = pages[0];
		WikiPage childPage = pages[1];
		WikiPage redirectPage = pages[2];

		WikiPageLocalServiceUtil.restorePageFromTrash(
			TestPropsValues.getUserId(), parentPage);

		parentPage = WikiPageLocalServiceUtil.getPageByPageId(
			parentPage.getPageId());
		childPage = WikiPageLocalServiceUtil.getPageByPageId(
			childPage.getPageId());
		redirectPage = WikiPageLocalServiceUtil.getPageByPageId(
			redirectPage.getPageId());

		Assert.assertFalse(parentPage.isInTrash());
		Assert.assertTrue(childPage.isInTrashExplicitly());
		Assert.assertEquals(childPage.getParentTitle(), parentPage.getTitle());
		Assert.assertFalse(redirectPage.isInTrash());
		Assert.assertEquals(
			redirectPage.getRedirectTitle(), parentPage.getTitle());

		restoreTrashEntry(childPage);

		childPage = WikiPageLocalServiceUtil.getPageByPageId(
			childPage.getPageId());

		Assert.assertFalse(childPage.isInTrash());
		Assert.assertEquals(childPage.getParentTitle(), parentPage.getTitle());
	}

	@Test
	public void testRestorePageWithRedirectPageFromTrash() throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		WikiNode node = (WikiNode)getParentBaseModel(group, serviceContext);

		WikiPage[] pages = WikiTestUtil.addTrashedPageWithTrashedRedirectPage(
			group.getGroupId(), node.getNodeId(), false);

		WikiPage page = pages[0];
		WikiPage redirectPage = pages[1];

		restoreTrashEntry(page);

		page = WikiPageLocalServiceUtil.getPageByPageId(page.getPageId());
		redirectPage = WikiPageLocalServiceUtil.getPageByPageId(
			redirectPage.getPageId());

		Assert.assertFalse(page.isInTrash());
		Assert.assertFalse(redirectPage.isInTrash());
		Assert.assertEquals(redirectPage.getRedirectTitle(), page.getTitle());
	}

	@Test
	public void testRestoreParentPageFromTrash() throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		WikiNode node = (WikiNode)getParentBaseModel(group, serviceContext);

		WikiPage[] pages = WikiTestUtil.addTrashedPageWithTrashedChildPage(
			group.getGroupId(), node.getNodeId(), false);

		WikiPage parentPage = pages[0];
		WikiPage childPage = pages[1];

		restoreTrashEntry(parentPage);

		parentPage = WikiPageLocalServiceUtil.getPageByPageId(
			parentPage.getPageId());
		childPage = WikiPageLocalServiceUtil.getPageByPageId(
			childPage.getPageId());

		Assert.assertFalse(parentPage.isInTrash());
		Assert.assertFalse(childPage.isInTrash());
		Assert.assertEquals(childPage.getParentTitle(), parentPage.getTitle());
	}

	@Test
	@Transactional
	public void testRestoreParentPageWithRedirectAndChildPagesFromTrash()
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		WikiNode node = (WikiNode)getParentBaseModel(group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(node);
		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		WikiPage[] pages =
			WikiTestUtil.addMovedPageWithChildAndGrandChild(
				group.getGroupId(), node.getNodeId());

		WikiPage parentPage = pages[0];
		WikiPage redirectPage = pages[1];
		WikiPage childPage = pages[2];
		WikiPage grandChildPage = pages[3];

		moveBaseModelToTrash(redirectPage.getPrimaryKey());

		restoreTrashEntry(redirectPage);

		Assert.assertFalse(parentPage.isInTrash());
		Assert.assertFalse(redirectPage.isInTrashExplicitly());
		Assert.assertEquals(
			parentPage.getTitle(), redirectPage.getRedirectTitle());
		Assert.assertFalse(childPage.isInTrash());
		Assert.assertEquals(parentPage.getTitle(), childPage.getParentTitle());
		Assert.assertFalse(grandChildPage.isInTrash());
		Assert.assertEquals(
			childPage.getTitle(), grandChildPage.getParentTitle());
		Assert.assertEquals(
			initialBaseModelsCount + 4, getNotInTrashBaseModelsCount(node));
		Assert.assertEquals(
			initialTrashEntriesCount, getTrashEntriesCount(group.getGroupId()));
	}

	@Test
	@Transactional
	public void
		testRestoreTrashedParentPageWithChildPagesAndGrandChildFromTrash()
			throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		WikiNode node = (WikiNode)getParentBaseModel(group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(node);
		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		WikiPage[] pages =
			WikiTestUtil.addParentPageWithChildPagesAndGrandChild(
				group.getGroupId(), node.getNodeId(), false, true);

		WikiPage parentPage = pages[0];
		WikiPage childPage = pages[1];
		WikiPage grandChildPage = pages[2];

		restoreTrashEntry(parentPage);

		parentPage = (WikiPage)getBaseModel(parentPage.getPrimaryKey());
		childPage = (WikiPage)getBaseModel(childPage.getPrimaryKey());
		grandChildPage = (WikiPage)getBaseModel(grandChildPage.getPrimaryKey());

		Assert.assertFalse(parentPage.isInTrash());
		Assert.assertFalse(childPage.isInTrash());
		Assert.assertEquals(parentPage.getTitle(), childPage.getParentTitle());
		Assert.assertFalse(grandChildPage.isInTrash());
		Assert.assertEquals(
			childPage.getTitle(), grandChildPage.getParentTitle());
		Assert.assertEquals(
			initialBaseModelsCount + 3, getNotInTrashBaseModelsCount(node));
		Assert.assertEquals(
			initialTrashEntriesCount, getTrashEntriesCount(group.getGroupId()));
	}

	@Ignore()
	@Override
	@Test
	public void testTrashAndDeleteDraft() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testTrashAndRestoreDraft() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testTrashGrandparentBaseModelAndRestoreParentModel()
		throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testTrashIsRestorableBaseModel() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testTrashIsRestorableBaseModelWithParent1() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testTrashIsRestorableBaseModelWithParent2() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testTrashIsRestorableBaseModelWithParent3() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testTrashIsRestorableBaseModelWithParent4() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testTrashMoveBaseModel() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testTrashMyBaseModel() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testTrashRecentBaseModel() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testTrashVersionParentBaseModel() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testTrashVersionParentBaseModelAndRestore() throws Exception {
	}

	@Override
	protected BaseModel<?> addBaseModelWithWorkflow(
			BaseModel<?> parentBaseModel, boolean approved,
			ServiceContext serviceContext)
		throws Exception {

		serviceContext = (ServiceContext)serviceContext.clone();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		String title = getSearchKeywords();

		title += ServiceTestUtil.randomString(
			_PAGE_TITLE_MAX_LENGTH - title.length());

		return WikiTestUtil.addPage(
			TestPropsValues.getUserId(), serviceContext.getScopeGroupId(),
			(Long)parentBaseModel.getPrimaryKeyObj(), title, approved);
	}

	@Override
	protected Long getAssetClassPK(ClassedModel classedModel) {
		return WikiPageAssetRenderer.getClassPK((WikiPage)classedModel);
	}

	@Override
	protected BaseModel<?> getBaseModel(long primaryKey) throws Exception {
		return WikiPageLocalServiceUtil.getPageByPageId(primaryKey);
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return WikiPage.class;
	}

	@Override
	protected String getBaseModelName(ClassedModel classedModel) {
		WikiPage page = (WikiPage)classedModel;

		return page.getTitle();
	}

	@Override
	protected int getNotInTrashBaseModelsCount(BaseModel<?> parentBaseModel)
		throws Exception {

		return WikiPageLocalServiceUtil.getPagesCount(
			(Long)parentBaseModel.getPrimaryKeyObj(), true,
			WorkflowConstants.STATUS_ANY);
	}

	@Override
	protected BaseModel<?> getParentBaseModel(
			Group group, ServiceContext serviceContext)
		throws Exception {

		serviceContext = (ServiceContext)serviceContext.clone();

		serviceContext.setWorkflowAction(WorkflowConstants.STATUS_APPROVED);

		return WikiNodeLocalServiceUtil.addNode(
			TestPropsValues.getUserId(),
			ServiceTestUtil.randomString(_NODE_NAME_MAX_LENGTH),
			ServiceTestUtil.randomString(), serviceContext);
	}

	@Override
	protected Class<?> getParentBaseModelClass() {
		return WikiNode.class;
	}

	@Override
	protected String getSearchKeywords() {
		return "Title";
	}

	@Override
	protected long getTrashEntryClassPK(ClassedModel classedModel) {
		WikiPage page = (WikiPage)classedModel;

		return page.getResourcePrimKey();
	}

	@Override
	protected String getUniqueTitle(BaseModel<?> baseModel) {
		WikiPage page = (WikiPage)baseModel;

		String title = page.getTitle();

		return TrashUtil.getOriginalTitle(title);
	}

	@Override
	protected boolean isBaseModelMoveableFromTrash() {
		return false;
	}

	@Override
	protected void moveBaseModelToTrash(long primaryKey) throws Exception {
		WikiPage page = WikiPageLocalServiceUtil.getPageByPageId(primaryKey);

		WikiPageLocalServiceUtil.movePageToTrash(
			TestPropsValues.getUserId(), page.getNodeId(), page.getTitle());
	}

	@Override
	protected void moveParentBaseModelToTrash(long primaryKey)
		throws Exception {

		WikiNodeLocalServiceUtil.moveNodeToTrash(
			TestPropsValues.getUserId(), primaryKey);
	}

	protected void restoreTrashEntry(WikiPage childPage) throws Exception {
		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		trashHandler.restoreTrashEntry(
			TestPropsValues.getUserId(), getTrashEntryClassPK(childPage));
	}

	@Override
	protected BaseModel<?> updateBaseModel(
			long primaryKey, ServiceContext serviceContext)
		throws Exception {

		WikiPage page = WikiPageLocalServiceUtil.getPageByPageId(primaryKey);

		serviceContext = (ServiceContext)serviceContext.clone();

		return WikiPageLocalServiceUtil.updatePage(
			TestPropsValues.getUserId(), page.getNodeId(), page.getTitle(),
			page.getVersion(), ServiceTestUtil.randomString(),
			ServiceTestUtil.randomString(), false, page.getFormat(),
			page.getParentTitle(), page.getRedirectTitle(), serviceContext);
	}

	private static final int _NODE_NAME_MAX_LENGTH = 75;

	private static final int _PAGE_TITLE_MAX_LENGTH = 255;

}