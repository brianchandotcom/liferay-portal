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
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portlet.trash.BaseTrashHandlerTestCase;
import com.liferay.portlet.trash.service.TrashVersionLocalServiceUtil;
import com.liferay.portlet.trash.util.TrashUtil;
import com.liferay.portlet.wiki.asset.WikiPageAssetRenderer;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.portlet.wiki.util.test.WikiTestUtil;

import org.junit.Assert;
import org.junit.Before;
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

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		ServiceContext serviceContext = ServiceContextTestUtil.getServiceContext(
			group.getGroupId());

		_node = (WikiNode)getParentBaseModel(group, serviceContext);
	}

	@Test
	@Transactional
	public void testMoveExplicitlyChildPageAndParentPageWithRedirectPageToTrash()
		throws Exception {

		WikiPage[] pages =
			WikiTestUtil.addTrashedParentPageWithRedirectPage(
				group.getGroupId(), _node.getNodeId(), true, false);

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
	@Transactional
	public void testMoveExplicitlyChildPageWithChildPageAndParentPageToTrash()
		throws Exception {

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(_node);
		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		WikiPage[] pages =
			WikiTestUtil.addTrashedParentPageWithChildPageAndGrandchildPage(
				group.getGroupId(), _node.getNodeId(), true, true);

		WikiPage parentPage = pages[0];
		WikiPage childPage = pages[1];
		WikiPage grandchildPage = pages[2];

		Assert.assertTrue(parentPage.isInTrashExplicitly());
		Assert.assertTrue(childPage.isInTrashExplicitly());
		Assert.assertTrue(grandchildPage.isInTrashImplicitly());
		Assert.assertEquals(
			childPage.getTitle(), grandchildPage.getParentTitle());
		Assert.assertEquals(
			initialBaseModelsCount, getNotInTrashBaseModelsCount(_node));
		Assert.assertEquals(
			initialTrashEntriesCount + 2,
			getTrashEntriesCount(group.getGroupId()));
	}

	@Test
	@Transactional
	public void testMoveExplicitlyChildPageWithChildPageToTrash()
		throws Exception {

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(_node);
		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		WikiPage[] pages =
			WikiTestUtil.addTrashedParentPageWithChildPageAndGrandchildPage(
				group.getGroupId(), _node.getNodeId(), true, false);

		WikiPage parentPage = pages[0];
		WikiPage childPage = pages[1];
		WikiPage grandchildPage = pages[2];

		Assert.assertFalse(parentPage.isInTrash());
		Assert.assertTrue(childPage.isInTrashExplicitly());
		Assert.assertTrue(grandchildPage.isInTrashImplicitly());
		Assert.assertEquals(
			childPage.getTitle(), grandchildPage.getParentTitle());
		Assert.assertEquals(
			initialBaseModelsCount + 1, getNotInTrashBaseModelsCount(_node));
		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			getTrashEntriesCount(group.getGroupId()));
	}

	@Test
	@Transactional
	public void testMoveExplicitlyPageAndRedirectPageToTrash()
		throws Exception {

		WikiPage[] pages = WikiTestUtil.addTrashedPageWithRedirectPage(
			group.getGroupId(), _node.getNodeId(), true);

		WikiPage page = pages[0];
		WikiPage redirectPage = pages[1];

		Assert.assertTrue(page.isInTrashExplicitly());
		Assert.assertTrue(redirectPage.isInTrashExplicitly());
		Assert.assertEquals(redirectPage.getRedirectTitle(), page.getTitle());
	}

	@Test
	@Transactional
	public void testMoveExplicitlyParentPageAndChildPageAndRedirectPageToTrash()
		throws Exception {

		WikiPage[] pages =
			WikiTestUtil.addTrashedParentPageWithRedirectPage(
				group.getGroupId(), _node.getNodeId(), true, true);

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
	@Transactional
	public void testMoveExplicitlyParentPageAndChildPagePageWithChildToTrash()
		throws Exception {

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(_node);
		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		WikiPage[] pages =
			WikiTestUtil.addTrashedParentPageWithChildPageAndGrandchildPage(
				group.getGroupId(), _node.getNodeId(), false, true);

		WikiPage parentPage = pages[0];
		WikiPage childPage = pages[1];
		WikiPage grandchildPage = pages[2];

		Assert.assertTrue(parentPage.isInTrashExplicitly());
		Assert.assertTrue(childPage.isInTrashImplicitly());
		Assert.assertEquals(parentPage.getTitle(), childPage.getParentTitle());
		Assert.assertTrue(grandchildPage.isInTrashImplicitly());
		Assert.assertEquals(
			childPage.getTitle(), grandchildPage.getParentTitle());
		Assert.assertEquals(
			initialBaseModelsCount, getNotInTrashBaseModelsCount(_node));
		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			getTrashEntriesCount(group.getGroupId()));
	}

	@Test
	@Transactional
	public void testMoveExplicitlyParentPageAndChildPageToTrash()
		throws Exception {

		WikiPage[] pages = WikiTestUtil.addTrashedPageWithChildPage(
			group.getGroupId(), _node.getNodeId(), true);

		WikiPage parentPage = pages[0];
		WikiPage childPage = pages[1];

		Assert.assertTrue(parentPage.isInTrashExplicitly());
		Assert.assertTrue(childPage.isInTrashExplicitly());
		Assert.assertEquals(childPage.getParentTitle(), parentPage.getTitle());
	}

	@Test
	@Transactional
	public void testMoveExplicitlyParentPageAndRedirectPageToTrash()
		throws Exception {

		WikiPage[] pages =
			WikiTestUtil.addTrashedParentPageWithRedirectPage(
				group.getGroupId(), _node.getNodeId(), false, true);

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
	@Transactional
	public void testMoveInitialParentPageToTrash() throws Exception {
		int initialBaseModelsCount = getNotInTrashBaseModelsCount(_node);
		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		WikiPage[] pages = WikiTestUtil.addPageWithChangedParentPage(
			group.getGroupId(), _node.getNodeId());

		WikiPage page = pages[0];
		WikiPage finalParentPage = pages[1];
		WikiPage initialParentPage = pages[2];

		WikiPageLocalServiceUtil.movePageToTrash(
			TestPropsValues.getUserId(), initialParentPage);

		page = WikiPageLocalServiceUtil.getPageByPageId(page.getPageId());
		finalParentPage = WikiPageLocalServiceUtil.getPageByPageId(
			finalParentPage.getPageId());
		initialParentPage = WikiPageLocalServiceUtil.getPageByPageId(
			initialParentPage.getPageId());

		Assert.assertFalse(page.isInTrash());
		Assert.assertFalse(finalParentPage.isInTrash());
		Assert.assertTrue(initialParentPage.isInTrashExplicitly());
		Assert.assertEquals(finalParentPage.getTitle(), page.getParentTitle());
		Assert.assertEquals(
			initialBaseModelsCount + 2, getNotInTrashBaseModelsCount(_node));
		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			getTrashEntriesCount(group.getGroupId()));
		Assert.assertEquals(page.getParentTitle(), finalParentPage.getTitle());
	}

	@Test
	@Transactional
	public void testMovePageWithRedirectPageToTrash() throws Exception {
		WikiPage[] pages = WikiTestUtil.addTrashedPageWithRedirectPage(
			group.getGroupId(), _node.getNodeId(), false);

		WikiPage page = pages[0];
		WikiPage redirectPage = pages[1];

		Assert.assertTrue(page.isInTrashExplicitly());
		Assert.assertTrue(redirectPage.isInTrashImplicitly());
		Assert.assertEquals(redirectPage.getRedirectTitle(), page.getTitle());
	}

	@Test
	@Transactional
	public void testMoveParentPageToTrash() throws Exception {
		WikiPage[] pages = WikiTestUtil.addTrashedPageWithChildPage(
			group.getGroupId(), _node.getNodeId(), false);

		WikiPage parentPage = pages[0];
		WikiPage childPage = pages[1];

		Assert.assertTrue(parentPage.isInTrashExplicitly());
		Assert.assertTrue(childPage.isInTrashImplicitly());
		Assert.assertEquals(childPage.getParentTitle(), parentPage.getTitle());
	}

	@Test
	@Transactional
	public void testMoveParentPageWithRedirectAndChildPageAndgrandchildPageToTrash()
		throws Exception {

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(_node);
		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		WikiPage[] pages =
			WikiTestUtil.addMovedParentPageWithChildPageAndGrandchildPage(
				group.getGroupId(), _node.getNodeId());

		WikiPage parentPage = pages[0];
		WikiPage redirectPage = pages[1];
		WikiPage childPage = pages[2];
		WikiPage grandchildPage = pages[3];

		moveBaseModelToTrash(redirectPage.getPrimaryKey());

		parentPage = WikiPageLocalServiceUtil.getPageByPageId(
			parentPage.getPageId());
		redirectPage = WikiPageLocalServiceUtil.getPageByPageId(
			redirectPage.getPageId());
		childPage = WikiPageLocalServiceUtil.getPageByPageId(
			childPage.getPageId());
		grandchildPage = WikiPageLocalServiceUtil.getPageByPageId(
			grandchildPage.getPageId());

		Assert.assertFalse(parentPage.isInTrash());
		Assert.assertTrue(redirectPage.isInTrashExplicitly());
		Assert.assertEquals(
			parentPage.getTitle(), redirectPage.getRedirectTitle());
		Assert.assertFalse(childPage.isInTrash());
		Assert.assertEquals(parentPage.getTitle(), childPage.getParentTitle());
		Assert.assertFalse(grandchildPage.isInTrash());
		Assert.assertEquals(
			childPage.getTitle(), grandchildPage.getParentTitle());
		Assert.assertEquals(
			initialBaseModelsCount + 3, getNotInTrashBaseModelsCount(_node));
		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			getTrashEntriesCount(group.getGroupId()));
	}

	@Test
	@Transactional
	public void testMoveParentPageWithRedirectPageToTrash() throws Exception {
		WikiPage[] pages = WikiTestUtil.addTrashedParentPageWithRedirectPage(
			group.getGroupId(), _node.getNodeId(), false, false);

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
	public void testRestoreExplicitlyTrashedChildPageAndPageWithRedirectFromTrash()
		throws Exception {

		WikiPage[] pages = WikiTestUtil.addTrashedParentPageWithRedirectPage(
			group.getGroupId(), _node.getNodeId(), true, false);

		WikiPage parentPage = pages[0];
		WikiPage childPage = pages[1];

		restoreTrashEntry(parentPage);
		restoreTrashEntry(childPage);

		parentPage = WikiPageLocalServiceUtil.getPageByPageId(
			parentPage.getPageId());
		childPage = WikiPageLocalServiceUtil.getPageByPageId(
			childPage.getPageId());

		Assert.assertFalse(childPage.isInTrash());
		Assert.assertEquals(childPage.getParentTitle(), parentPage.getTitle());
	}

	@Test
	@Transactional
	public void testRestoreExplicitlyTrashedChildPageAndParentPageWithRedirectPageFromTrash()
		throws Exception {

		WikiPage[] pages = WikiTestUtil.addTrashedParentPageWithRedirectPage(
			group.getGroupId(), _node.getNodeId(), true, true);

		WikiPage parentPage = pages[0];
		WikiPage childPage = pages[1];

		restoreTrashEntry(parentPage);
		restoreTrashEntry(childPage);

		parentPage = WikiPageLocalServiceUtil.getPageByPageId(
			parentPage.getPageId());
		childPage = WikiPageLocalServiceUtil.getPageByPageId(
			childPage.getPageId());

		Assert.assertFalse(childPage.isInTrash());
		Assert.assertEquals(childPage.getParentTitle(), parentPage.getTitle());
	}

	@Test
	@Transactional
	public void testRestoreExplicitlyTrashedChildPageWithChildPageFromTrash()
		throws Exception {

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(_node);
		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		WikiPage[] pages =
			WikiTestUtil.addTrashedParentPageWithChildPageAndGrandchildPage(
				group.getGroupId(), _node.getNodeId(), true, false);

		WikiPage parentPage = pages[0];
		WikiPage childPage = pages[1];
		WikiPage grandchildPage = pages[2];

		restoreTrashEntry(childPage);

		parentPage = (WikiPage)getBaseModel(parentPage.getPrimaryKey());
		childPage = (WikiPage)getBaseModel(childPage.getPrimaryKey());
		grandchildPage = (WikiPage)getBaseModel(grandchildPage.getPrimaryKey());

		Assert.assertFalse(parentPage.isInTrash());
		Assert.assertFalse(childPage.isInTrash());
		Assert.assertEquals(parentPage.getTitle(), childPage.getParentTitle());
		Assert.assertFalse(grandchildPage.isInTrash());
		Assert.assertEquals(
			childPage.getTitle(), grandchildPage.getParentTitle());
		Assert.assertEquals(
			initialBaseModelsCount + 3, getNotInTrashBaseModelsCount(_node));
		Assert.assertEquals(
			initialTrashEntriesCount, getTrashEntriesCount(group.getGroupId()));
	}

	@Test
	@Transactional
	public void testRestoreExplicitlyTrashedChildPageWithTrashedParentFromTrash()
		throws Exception {

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(_node);
		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		WikiPage[] pages =
			WikiTestUtil.addTrashedParentPageWithChildPageAndGrandchildPage(
				group.getGroupId(), _node.getNodeId(), true, true);

		WikiPage parentPage = pages[0];
		WikiPage childPage = pages[1];
		WikiPage grandchildPage = pages[2];

		restoreTrashEntry(childPage);

		parentPage = (WikiPage)getBaseModel(parentPage.getPrimaryKey());
		childPage = (WikiPage)getBaseModel(childPage.getPrimaryKey());
		grandchildPage = (WikiPage)getBaseModel(grandchildPage.getPrimaryKey());

		Assert.assertTrue(parentPage.isInTrashExplicitly());
		Assert.assertFalse(childPage.isInTrash());
		Assert.assertEquals(StringPool.BLANK, childPage.getParentTitle());
		Assert.assertFalse(grandchildPage.isInTrash());
		Assert.assertEquals(
			childPage.getTitle(), grandchildPage.getParentTitle());
		Assert.assertEquals(
			initialBaseModelsCount + 2, getNotInTrashBaseModelsCount(_node));
		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			getTrashEntriesCount(group.getGroupId()));
	}

	@Test
	@Transactional
	public void testRestoreExplicitlyTrashedPageWithRedirectPageFromTrash()
		throws Exception {

		WikiPage[] pages = WikiTestUtil.addTrashedPageWithRedirectPage(
			group.getGroupId(), _node.getNodeId(), true);

		WikiPage page = pages[0];
		WikiPage redirectPage = pages[1];

		restoreTrashEntry(page);

		page = WikiPageLocalServiceUtil.getPageByPageId(page.getPageId());
		redirectPage = WikiPageLocalServiceUtil.getPageByPageId(
			redirectPage.getPageId());

		Assert.assertFalse(page.isInTrash());
		Assert.assertTrue(redirectPage.isInTrashExplicitly());
		Assert.assertEquals(redirectPage.getRedirectTitle(), page.getTitle());
	}

	@Test
	@Transactional
	public void testRestoreExplicitlyTrashedParentPageAndChildPageAndRedirectPageFromTrash()
		throws Exception {

		WikiPage[] pages = WikiTestUtil.addTrashedParentPageWithRedirectPage(
			group.getGroupId(), _node.getNodeId(), true, true);

		WikiPage parentPage = pages[0];
		WikiPage childPage = pages[1];
		WikiPage redirectPage = pages[2];

		restoreTrashEntry(parentPage);
		restoreTrashEntry(childPage);
		restoreTrashEntry(redirectPage);

		parentPage = WikiPageLocalServiceUtil.getPageByPageId(
			parentPage.getPageId());
		childPage = WikiPageLocalServiceUtil.getPageByPageId(
			childPage.getPageId());
		redirectPage = WikiPageLocalServiceUtil.getPageByPageId(
			redirectPage.getPageId());

		Assert.assertFalse(redirectPage.isInTrash());
		Assert.assertEquals(
			redirectPage.getRedirectTitle(), parentPage.getTitle());
	}

	@Test
	@Transactional
	public void testRestoreExplicitlyTrashedParentPageAndChildPageFromTrash()
		throws Exception {

		WikiPage[] pages = WikiTestUtil.addTrashedPageWithChildPage(
			group.getGroupId(), _node.getNodeId(), true);

		WikiPage page = pages[0];
		WikiPage childPage = pages[1];

		restoreTrashEntry(page);
		restoreTrashEntry(childPage);

		page = WikiPageLocalServiceUtil.getPageByPageId(page.getPageId());
		childPage = WikiPageLocalServiceUtil.getPageByPageId(
			childPage.getPageId());

		Assert.assertFalse(childPage.isInTrash());
		Assert.assertEquals(childPage.getParentTitle(), page.getTitle());
	}

	@Test
	@Transactional
	public void testRestoreExplicitlyTrashedParentPageAndRedirectFromTrash()
		throws Exception {

		WikiPage[] pages = WikiTestUtil.addTrashedParentPageWithRedirectPage(
			group.getGroupId(), _node.getNodeId(), false, true);

		WikiPage parentPage = pages[0];
		WikiPage redirectPage = pages[2];

		restoreTrashEntry(parentPage);
		restoreTrashEntry(redirectPage);

		parentPage = WikiPageLocalServiceUtil.getPageByPageId(
			parentPage.getPageId());
		redirectPage = WikiPageLocalServiceUtil.getPageByPageId(
			redirectPage.getPageId());

		Assert.assertFalse(redirectPage.isInTrash());
		Assert.assertEquals(
			redirectPage.getRedirectTitle(), parentPage.getTitle());
	}

	@Test
	@Transactional
	public void testRestoreExplicitlyTrashedParentPageFromTrash()
		throws Exception {

		WikiPage[] pages = WikiTestUtil.addTrashedPageWithChildPage(
			group.getGroupId(), _node.getNodeId(), false);

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
	public void testRestoreExplicitlyTrashedParentPageWitExplicitlyTrashedChildPageFromTrash()
		throws Exception {

		WikiPage[] pages = WikiTestUtil.addTrashedPageWithChildPage(
			group.getGroupId(), _node.getNodeId(), true);

		WikiPage parentPage = pages[0];
		WikiPage childPage = pages[1];

		restoreTrashEntry(parentPage);

		parentPage = WikiPageLocalServiceUtil.getPageByPageId(
			parentPage.getPageId());
		childPage = WikiPageLocalServiceUtil.getPageByPageId(
			childPage.getPageId());

		Assert.assertFalse(parentPage.isInTrash());
		Assert.assertTrue(childPage.isInTrashExplicitly());
		Assert.assertEquals(childPage.getParentTitle(), parentPage.getTitle());
	}

	@Test
	@Transactional
	public void testRestoreExplicitlyTrashedParentPageWithChildPageAndgrandchildPageFromTrash()
		throws Exception {

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(_node);
		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		WikiPage[] pages =
			WikiTestUtil.addTrashedParentPageWithChildPageAndGrandchildPage(
				group.getGroupId(), _node.getNodeId(), false, true);

		WikiPage parentPage = pages[0];
		WikiPage childPage = pages[1];
		WikiPage grandchildPage = pages[2];

		restoreTrashEntry(parentPage);

		parentPage = (WikiPage)getBaseModel(parentPage.getPrimaryKey());
		childPage = (WikiPage)getBaseModel(childPage.getPrimaryKey());
		grandchildPage = (WikiPage)getBaseModel(grandchildPage.getPrimaryKey());

		Assert.assertFalse(parentPage.isInTrash());
		Assert.assertFalse(childPage.isInTrash());
		Assert.assertEquals(parentPage.getTitle(), childPage.getParentTitle());
		Assert.assertFalse(grandchildPage.isInTrash());
		Assert.assertEquals(
			childPage.getTitle(), grandchildPage.getParentTitle());
		Assert.assertEquals(
			initialBaseModelsCount + 3, getNotInTrashBaseModelsCount(_node));
		Assert.assertEquals(
			initialTrashEntriesCount, getTrashEntriesCount(group.getGroupId()));
	}

	@Test
	@Transactional
	public void testRestoreExplicitlyTrashedParentPageWithRedirectPageFromTrash()
		throws Exception {

		WikiPage[] pages = WikiTestUtil.addTrashedParentPageWithRedirectPage(
			group.getGroupId(), _node.getNodeId(), false, true);

		WikiPage parentPage = pages[0];
		WikiPage childPage = pages[1];
		WikiPage redirectPage = pages[2];

		restoreTrashEntry(parentPage);

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
	}

	@Test
	@Transactional
	public void testRestoreExplicitlyTrashedParentPageWithRedirectPageToTrash()
		throws Exception {

		WikiPage[] pages = WikiTestUtil.addTrashedParentPageWithRedirectPage(
			group.getGroupId(), _node.getNodeId(), true, true);

		WikiPage parentPage = pages[0];
		WikiPage childPage = pages[1];
		WikiPage redirectPage = pages[2];

		restoreTrashEntry(parentPage);

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
	}

	@Test
	@Transactional
	public void testRestoreExplicitlyTrashedRedirectPageWithRestoredPageFromTrash()
		throws Exception {

		WikiPage[] pages = WikiTestUtil.addTrashedPageWithRedirectPage(
			group.getGroupId(), _node.getNodeId(), true);

		WikiPage page = pages[0];
		WikiPage redirectPage = pages[1];

		restoreTrashEntry(page);
		restoreTrashEntry(redirectPage);

		page = WikiPageLocalServiceUtil.getPageByPageId(page.getPageId());
		redirectPage = WikiPageLocalServiceUtil.getPageByPageId(
			redirectPage.getPageId());

		Assert.assertFalse(redirectPage.isInTrash());
		Assert.assertEquals(redirectPage.getRedirectTitle(), page.getTitle());
	}

	@Test
	@Transactional
	public void testRestoreParentPageWithExplicitlyTrashedRrdirectPageFromTrash()
		throws Exception {

		WikiPage[] pages = WikiTestUtil.addTrashedParentPageWithRedirectPage(
			group.getGroupId(), _node.getNodeId(), true, false);

		WikiPage parentPage = pages[0];
		WikiPage childPage = pages[1];
		WikiPage redirectPage = pages[2];

		restoreTrashEntry(parentPage);

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
	}

	@Test
	@Transactional
	public void testRestoreRedirectPageWithParentPageFromTrash()
		throws Exception {

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(_node);
		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		WikiPage[] pages =
			WikiTestUtil.addMovedParentPageWithChildPageAndGrandchildPage(
				group.getGroupId(), _node.getNodeId());

		WikiPage parentPage = pages[0];
		WikiPage redirectPage = pages[1];
		WikiPage childPage = pages[2];
		WikiPage grandchildPage = pages[3];

		moveBaseModelToTrash(redirectPage.getPrimaryKey());

		restoreTrashEntry(redirectPage);

		parentPage = WikiPageLocalServiceUtil.getPageByPageId(
			parentPage.getPageId());
		redirectPage = WikiPageLocalServiceUtil.getPageByPageId(
			redirectPage.getPageId());
		childPage = WikiPageLocalServiceUtil.getPageByPageId(
			childPage.getPageId());
		grandchildPage = WikiPageLocalServiceUtil.getPageByPageId(
			grandchildPage.getPageId());

		Assert.assertFalse(parentPage.isInTrash());
		Assert.assertFalse(redirectPage.isInTrashExplicitly());
		Assert.assertEquals(
			parentPage.getTitle(), redirectPage.getRedirectTitle());
		Assert.assertFalse(childPage.isInTrash());
		Assert.assertEquals(parentPage.getTitle(), childPage.getParentTitle());
		Assert.assertFalse(grandchildPage.isInTrash());
		Assert.assertEquals(
			childPage.getTitle(), grandchildPage.getParentTitle());
		Assert.assertEquals(
			initialBaseModelsCount + 4, getNotInTrashBaseModelsCount(_node));
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

	@Test
	@Transactional
	public void testTrashVersionCreationWhenMovingToTrash()
		throws Exception {

		int initialVersionPagesCount =
			TrashVersionLocalServiceUtil.getTrashVersionsCount();

		WikiTestUtil.addTrashedParentPageWithRedirectPage(
			group.getGroupId(), _node.getNodeId(), false, false);

		Assert.assertEquals(
			initialVersionPagesCount + 2,
			TrashVersionLocalServiceUtil.getTrashVersionsCount());
	}

	@Test
	@Transactional
	public void testTrashVersionDeletionWhenRestoringFromTrash()
		throws Exception {

		int initialVersionPagesCount =
			TrashVersionLocalServiceUtil.getTrashVersionsCount();

		WikiPage[] pages = WikiTestUtil.addTrashedParentPageWithRedirectPage(
			group.getGroupId(), _node.getNodeId(), false, false);

		WikiPage parentPage = pages[0];

		restoreTrashEntry(parentPage);

		Assert.assertEquals(
			initialVersionPagesCount,
			TrashVersionLocalServiceUtil.getTrashVersionsCount());
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

		title += RandomTestUtil.randomString(
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
			RandomTestUtil.randomString(_NODE_NAME_MAX_LENGTH),
			RandomTestUtil.randomString(), serviceContext);
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
			page.getVersion(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), false, page.getFormat(),
			page.getParentTitle(), page.getRedirectTitle(), serviceContext);
	}

	private static final int _NODE_NAME_MAX_LENGTH = 75;

	private static final int _PAGE_TITLE_MAX_LENGTH = 255;

	private WikiNode _node;

}