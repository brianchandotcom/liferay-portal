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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.test.listeners.MainServletExecutionTestListener;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portlet.trash.service.TrashEntryLocalServiceUtil;
import com.liferay.portlet.trash.service.TrashVersionLocalServiceUtil;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.portlet.wiki.util.test.WikiTestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Roberto Díaz
 */
@ExecutionTestListeners(listeners = {
	MainServletExecutionTestListener.class,
	SynchronousDestinationExecutionTestListener.class
})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class WikiPageDependentsTrashHandlerTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_node = WikiPageTrashHandlerTestUtil.getParentBaseModel(serviceContext);
	}

	@Test
	public void testAddPageWithSameTitleAsImplicitlyDeletedPageVersion()
		throws Exception {

		WikiPages pages = givenPageWithChildPage();

		WikiPage childPage = pages.getChildPage();

		movePageToTrash(pages.getPage());

		WikiPage newPage = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _group.getGroupId(), _node.getNodeId(),
			childPage.getTitle(), true);

		Assert.assertNotNull(newPage);
	}

	@Test
	public void
			testMoveExplicitlyChildPageAndParentPageWithRedirectPageToTrash()
		throws Exception {

		WikiPages pages = givenPageWithChildAndRedirectPage();

		WikiPage trashedChildPage = movePageToTrash(pages.getChildPage());
		WikiPage trashedRedirectPage = movePageToTrash(pages.getRedirectPage());
		WikiPage trashedPage = movePageToTrash(pages.getPage());

		trashedChildPage = getUpdatedPage(trashedChildPage);
		trashedRedirectPage = getUpdatedPage(trashedRedirectPage);

		Assert.assertTrue(trashedPage.isInTrashExplicitly());
		Assert.assertTrue(trashedChildPage.isInTrashExplicitly());
		Assert.assertEquals(
			trashedChildPage.getParentTitle(), trashedPage.getTitle());
		Assert.assertTrue(trashedRedirectPage.isInTrashExplicitly());
		Assert.assertEquals(
			trashedRedirectPage.getRedirectTitle(), trashedPage.getTitle());
	}

	@Test
	public void testMoveExplicitlyChildPageWithChildPageAndParentPageToTrash()
		throws Exception {

		int initialBaseModelsCount =
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node);
		int initialTrashEntriesCount =
			TrashEntryLocalServiceUtil.getEntriesCount(_group.getGroupId());

		WikiPage[] pages = addTrashedParentPageWithChildPageAndGrandchildPage(
			_group.getGroupId(), _node.getNodeId(), true, true);

		WikiPage parentPage = pages[0];
		WikiPage childPage = pages[1];
		WikiPage grandchildPage = pages[2];

		Assert.assertTrue(parentPage.isInTrashExplicitly());
		Assert.assertTrue(childPage.isInTrashExplicitly());
		Assert.assertTrue(grandchildPage.isInTrashImplicitly());
		Assert.assertEquals(
			childPage.getTitle(), grandchildPage.getParentTitle());
		Assert.assertEquals(
			initialBaseModelsCount, WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node));
		Assert.assertEquals(
			initialTrashEntriesCount + 2,
			TrashEntryLocalServiceUtil.getEntriesCount(_group.getGroupId()));
	}

	@Test
	public void testMoveExplicitlyChildPageWithChildPageToTrash()
		throws Exception {

		int initialBaseModelsCount =
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node);
		int initialTrashEntriesCount =
			TrashEntryLocalServiceUtil.getEntriesCount(_group.getGroupId());

		WikiPage[] pages = addTrashedParentPageWithChildPageAndGrandchildPage(
			_group.getGroupId(), _node.getNodeId(), true, false);

		WikiPage parentPage = pages[0];
		WikiPage childPage = pages[1];
		WikiPage grandchildPage = pages[2];

		Assert.assertFalse(parentPage.isInTrash());
		Assert.assertTrue(childPage.isInTrashExplicitly());
		Assert.assertTrue(grandchildPage.isInTrashImplicitly());
		Assert.assertEquals(
			childPage.getTitle(), grandchildPage.getParentTitle());
		Assert.assertEquals(
			initialBaseModelsCount + 1,
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node));
		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			TrashEntryLocalServiceUtil.getEntriesCount(_group.getGroupId()));
	}

	@Test
	public void testMoveExplicitlyPageAndRedirectPageToTrash()
		throws Exception {

		WikiPage[] pages = WikiTestUtil.addRenamedTrashedPage(
			_group.getGroupId(), _node.getNodeId(), true);

		WikiPage page = pages[0];
		WikiPage redirectPage = pages[1];

		Assert.assertTrue(page.isInTrashExplicitly());
		Assert.assertTrue(redirectPage.isInTrashExplicitly());
		Assert.assertEquals(redirectPage.getRedirectTitle(), page.getTitle());
	}

	@Test
	public void testMoveExplicitlyParentPageAndChildPageAndRedirectPageToTrash()
		throws Exception {

		WikiPages pages = givenPageWithChildAndRedirectPage();

		WikiPage trashedChildPage = movePageToTrash(pages.getChildPage());
		WikiPage trashedPage = movePageToTrash(pages.getPage());

		trashedChildPage = getUpdatedPage(trashedChildPage);
		WikiPage trashedRedirectPage = getUpdatedPage(pages.getRedirectPage());

		Assert.assertTrue(trashedPage.isInTrashExplicitly());
		Assert.assertTrue(trashedChildPage.isInTrashExplicitly());
		Assert.assertEquals(
			trashedChildPage.getParentTitle(), trashedPage.getTitle());
		Assert.assertTrue(trashedRedirectPage.isInTrashImplicitly());
		Assert.assertEquals(
			trashedRedirectPage.getRedirectTitle(), trashedPage.getTitle());
	}

	@Test
	public void testMoveExplicitlyParentPageAndChildPagePageWithChildToTrash()
		throws Exception {

		int initialBaseModelsCount =
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node);
		int initialTrashEntriesCount =
			TrashEntryLocalServiceUtil.getEntriesCount(_group.getGroupId());

		WikiPage[] pages = addTrashedParentPageWithChildPageAndGrandchildPage(
			_group.getGroupId(), _node.getNodeId(), false, true);

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
			initialBaseModelsCount,
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node));
		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			TrashEntryLocalServiceUtil.getEntriesCount(_group.getGroupId()));
	}

	@Test
	public void testMoveExplicitlyParentPageAndChildPageToTrash()
		throws Exception {

		WikiPage[] pages = WikiTestUtil.addTrashedPageWithChildPage(
			_group.getGroupId(), _node.getNodeId(), true);

		WikiPage parentPage = pages[0];
		WikiPage childPage = pages[1];

		Assert.assertTrue(parentPage.isInTrashExplicitly());
		Assert.assertTrue(childPage.isInTrashExplicitly());
		Assert.assertEquals(childPage.getParentTitle(), parentPage.getTitle());
	}

	@Test
	public void testMoveExplicitlyParentPageAndRedirectPageToTrash()
		throws Exception {

		WikiPages pages = givenPageWithChildAndRedirectPage();

		WikiPage trashedRedirectPage = movePageToTrash(pages.getRedirectPage());
		WikiPage trashedPage = movePageToTrash(pages.getPage());

		WikiPage trashedChildPage = getUpdatedPage(pages.getChildPage());
		trashedRedirectPage = getUpdatedPage(trashedRedirectPage);

		Assert.assertTrue(trashedPage.isInTrashExplicitly());
		Assert.assertTrue(trashedChildPage.isInTrashImplicitly());
		Assert.assertEquals(
			trashedChildPage.getParentTitle(), trashedPage.getTitle());
		Assert.assertTrue(trashedRedirectPage.isInTrashExplicitly());
		Assert.assertEquals(
			trashedRedirectPage.getRedirectTitle(), trashedPage.getTitle());
	}

	@Test
	public void testMoveInitialParentPageToTrash() throws Exception {
		int initialBaseModelsCount =
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node);
		int initialTrashEntriesCount =
			TrashEntryLocalServiceUtil.getEntriesCount(_group.getGroupId());

		WikiPage[] pages = addPageWithChangedParentPage(
			_group.getGroupId(), _node.getNodeId());

		WikiPage page = pages[0];
		WikiPage finalParentPage = pages[1];
		WikiPage initialParentPage = pages[2];

		WikiPageLocalServiceUtil.movePageToTrash(
			TestPropsValues.getUserId(), initialParentPage);

		page = WikiPageLocalServiceUtil.getPage(page.getResourcePrimKey());
		finalParentPage = WikiPageLocalServiceUtil.getPage(
			finalParentPage.getResourcePrimKey());
		initialParentPage = WikiPageLocalServiceUtil.getPage(
			initialParentPage.getResourcePrimKey());

		Assert.assertFalse(page.isInTrash());
		Assert.assertFalse(finalParentPage.isInTrash());
		Assert.assertTrue(initialParentPage.isInTrashExplicitly());
		Assert.assertEquals(finalParentPage.getTitle(), page.getParentTitle());
		Assert.assertEquals(
			initialBaseModelsCount + 2,
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node));
		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			TrashEntryLocalServiceUtil.getEntriesCount(_group.getGroupId()));
		Assert.assertEquals(page.getParentTitle(), finalParentPage.getTitle());
	}

	@Test
	public void testMovePageWithRedirectPageToTrash() throws Exception {
		WikiPage[] pages = WikiTestUtil.addRenamedTrashedPage(
			_group.getGroupId(), _node.getNodeId(), false);

		WikiPage page = pages[0];
		WikiPage redirectPage = pages[1];

		Assert.assertTrue(page.isInTrashExplicitly());
		Assert.assertTrue(redirectPage.isInTrashImplicitly());
		Assert.assertEquals(redirectPage.getRedirectTitle(), page.getTitle());
	}

	@Test
	public void testMoveParentPageToTrash() throws Exception {
		WikiPage[] pages = WikiTestUtil.addTrashedPageWithChildPage(
			_group.getGroupId(), _node.getNodeId(), false);

		WikiPage parentPage = pages[0];
		WikiPage childPage = pages[1];

		Assert.assertTrue(parentPage.isInTrashExplicitly());
		Assert.assertTrue(childPage.isInTrashImplicitly());
		Assert.assertEquals(childPage.getParentTitle(), parentPage.getTitle());
	}

	@Test
	public void
			testMoveParentPageWithRedirectAndChildPageAndgrandchildPageToTrash()
		throws Exception {

		int initialBaseModelsCount =
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node);
		int initialTrashEntriesCount =
			TrashEntryLocalServiceUtil.getEntriesCount(_group.getGroupId());

		WikiPage[] pages = addRenamedParentPageWithChildPageAndGrandchildPage(
			_group.getGroupId(), _node.getNodeId());

		WikiPage parentPage = pages[0];
		WikiPage redirectPage = pages[1];
		WikiPage childPage = pages[2];
		WikiPage grandchildPage = pages[3];

		WikiPageTrashHandlerTestUtil.moveBaseModelToTrash(
			redirectPage.getPrimaryKey());

		parentPage = WikiPageLocalServiceUtil.getPage(
			parentPage.getResourcePrimKey());
		redirectPage = WikiPageLocalServiceUtil.getPage(
			redirectPage.getResourcePrimKey());
		childPage = WikiPageLocalServiceUtil.getPage(
			childPage.getResourcePrimKey());
		grandchildPage = WikiPageLocalServiceUtil.getPage(
			grandchildPage.getResourcePrimKey());

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
			initialBaseModelsCount + 3,
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node));
		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			TrashEntryLocalServiceUtil.getEntriesCount(_group.getGroupId()));
	}

	@Test
	public void testMoveParentPageWithRedirectPageToTrash() throws Exception {
		WikiPages pages = givenPageWithChildAndRedirectPage(
		);

		WikiPage trashedPage = movePageToTrash(pages.getPage());

		WikiPage trashedChildPage = getUpdatedPage(pages.getChildPage());
		WikiPage trashedRedirectPage = getUpdatedPage(pages.getRedirectPage());

		Assert.assertTrue(trashedPage.isInTrashExplicitly());
		Assert.assertTrue(trashedChildPage.isInTrashImplicitly());
		Assert.assertEquals(
			trashedChildPage.getParentTitle(), trashedPage.getTitle());
		Assert.assertTrue(trashedRedirectPage.isInTrashImplicitly());
		Assert.assertEquals(
			trashedRedirectPage.getRedirectTitle(), trashedPage.getTitle());
	}

	@Test
	public void
			testRestoreExplicitlyTrashedChildPageAndParentPageWithRedirectPageFromTrash()
		throws Exception {

		WikiPages pages = givenPageWithChildPage(
		);

		WikiPage trashedChildPage = movePageToTrash(pages.getChildPage());
		WikiPage trashedPage = movePageToTrash(pages.getPage());

		WikiPage restoredPage = restoreFromTrash(trashedPage);
		WikiPage restoredChildPage = restoreFromTrash(trashedChildPage);

		Assert.assertFalse(restoredChildPage.isInTrash());
		Assert.assertEquals(
			restoredChildPage.getParentTitle(), restoredPage.getTitle());
	}

	@Test
	public void testRestoreExplicitlyTrashedChildPageWithChildPageFromTrash()
		throws Exception {

		int initialBaseModelsCount =
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node);
		int initialTrashEntriesCount =
			TrashEntryLocalServiceUtil.getEntriesCount(_group.getGroupId());

		WikiPage[] pages = addTrashedParentPageWithChildPageAndGrandchildPage(
			_group.getGroupId(), _node.getNodeId(), true, false);

		WikiPage parentPage = pages[0];
		WikiPage childPage = pages[1];
		WikiPage grandchildPage = pages[2];

		restoreFromTrash(childPage);

		parentPage = WikiPageLocalServiceUtil.getPage(
			parentPage.getResourcePrimKey());
		childPage = WikiPageLocalServiceUtil.getPage(
			childPage.getResourcePrimKey());
		grandchildPage = WikiPageLocalServiceUtil.getPage(
			grandchildPage.getResourcePrimKey());

		Assert.assertFalse(parentPage.isInTrash());
		Assert.assertFalse(childPage.isInTrash());
		Assert.assertEquals(parentPage.getTitle(), childPage.getParentTitle());
		Assert.assertFalse(grandchildPage.isInTrash());
		Assert.assertEquals(
			childPage.getTitle(), grandchildPage.getParentTitle());
		Assert.assertEquals(
			initialBaseModelsCount + 3,
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node));
		Assert.assertEquals(
			initialTrashEntriesCount,
			TrashEntryLocalServiceUtil.getEntriesCount(_group.getGroupId()));
	}

	@Test
	public void
			testRestoreExplicitlyTrashedChildPageWithTrashedParentFromTrash()
		throws Exception {

		int initialBaseModelsCount =
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node);
		int initialTrashEntriesCount =
			TrashEntryLocalServiceUtil.getEntriesCount(_group.getGroupId());

		WikiPage[] pages = addTrashedParentPageWithChildPageAndGrandchildPage(
			_group.getGroupId(), _node.getNodeId(), true, true);

		WikiPage parentPage = pages[0];
		WikiPage childPage = pages[1];
		WikiPage grandchildPage = pages[2];

		restoreFromTrash(childPage);

		parentPage = WikiPageLocalServiceUtil.getPage(
			parentPage.getResourcePrimKey());
		childPage = WikiPageLocalServiceUtil.getPage(
			childPage.getResourcePrimKey());
		grandchildPage = WikiPageLocalServiceUtil.getPage(
			grandchildPage.getResourcePrimKey());

		Assert.assertTrue(parentPage.isInTrashExplicitly());
		Assert.assertFalse(childPage.isInTrash());
		Assert.assertEquals(StringPool.BLANK, childPage.getParentTitle());
		Assert.assertFalse(grandchildPage.isInTrash());
		Assert.assertEquals(
			childPage.getTitle(), grandchildPage.getParentTitle());
		Assert.assertEquals(
			initialBaseModelsCount + 2,
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node));
		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			TrashEntryLocalServiceUtil.getEntriesCount(_group.getGroupId()));
	}

	@Test
	public void testRestoreExplicitlyTrashedPageWithRedirectPageFromTrash()
		throws Exception {

		WikiPage[] pages = WikiTestUtil.addRenamedTrashedPage(
			_group.getGroupId(), _node.getNodeId(), true);

		WikiPage page = pages[0];
		WikiPage redirectPage = pages[1];

		restoreFromTrash(page);

		page = WikiPageLocalServiceUtil.getPage(page.getResourcePrimKey());
		redirectPage = WikiPageLocalServiceUtil.getPage(
			redirectPage.getResourcePrimKey());

		Assert.assertFalse(page.isInTrash());
		Assert.assertTrue(redirectPage.isInTrashExplicitly());
		Assert.assertEquals(redirectPage.getRedirectTitle(), page.getTitle());
	}

	@Test
	public void
			testRestoreExplicitlyTrashedParentPageAndChildPageAndRedirectPageFromTrash()
		throws Exception {

		WikiPages pages = givenPageWithChildAndRedirectPage();

		WikiPage trashedChildPage = movePageToTrash(pages.getChildPage());
		WikiPage trashedRedirectPage = movePageToTrash(pages.getRedirectPage());
		WikiPage trashedPage = movePageToTrash(pages.getPage());

		WikiPage restoredPage = restoreFromTrash(trashedPage);
		WikiPage restoredChildPage = restoreFromTrash(trashedChildPage);
		WikiPage restoredRedirectPage = restoreFromTrash(trashedRedirectPage);

		Assert.assertFalse(restoredChildPage.isInTrash());
		Assert.assertEquals(
			restoredChildPage.getParentTitle(), restoredPage.getTitle());
		Assert.assertFalse(restoredRedirectPage.isInTrash());
		Assert.assertEquals(
			restoredRedirectPage.getRedirectTitle(), restoredPage.getTitle());
	}

	@Test
	public void testRestoreExplicitlyTrashedParentPageAndChildPageFromTrash()
		throws Exception {

		WikiPage[] pages = WikiTestUtil.addTrashedPageWithChildPage(
			_group.getGroupId(), _node.getNodeId(), true);

		WikiPage page = pages[0];
		WikiPage childPage = pages[1];

		restoreFromTrash(page);
		restoreFromTrash(childPage);

		page = WikiPageLocalServiceUtil.getPage(page.getResourcePrimKey());
		childPage = WikiPageLocalServiceUtil.getPage(
			childPage.getResourcePrimKey());

		Assert.assertFalse(childPage.isInTrash());
		Assert.assertEquals(childPage.getParentTitle(), page.getTitle());
	}

	@Test
	public void testRestoreExplicitlyTrashedParentPageAndRedirectFromTrash()
		throws Exception {

		WikiPages pages = givenPageWithRedirectPage(
		);

		WikiPage trashedRedirectPage = movePageToTrash(pages.getRedirectPage());
		WikiPage trashedPage = movePageToTrash(pages.getPage());

		WikiPage restoredPage = restoreFromTrash(trashedPage);
		WikiPage restoredRedirectPage = restoreFromTrash(trashedRedirectPage);

		Assert.assertFalse(restoredRedirectPage.isInTrash());
		Assert.assertEquals(
			restoredRedirectPage.getRedirectTitle(), restoredPage.getTitle());
	}

	@Test
	public void testRestoreExplicitlyTrashedParentPageFromTrash()
		throws Exception {

		WikiPage[] pages = WikiTestUtil.addTrashedPageWithChildPage(
			_group.getGroupId(), _node.getNodeId(), false);

		WikiPage parentPage = pages[0];
		WikiPage childPage = pages[1];

		restoreFromTrash(parentPage);

		parentPage = WikiPageLocalServiceUtil.getPage(
			parentPage.getResourcePrimKey());
		childPage = WikiPageLocalServiceUtil.getPage(
			childPage.getResourcePrimKey());

		Assert.assertFalse(parentPage.isInTrash());
		Assert.assertFalse(childPage.isInTrash());
		Assert.assertEquals(childPage.getParentTitle(), parentPage.getTitle());
	}

	@Test
	public void
			testRestoreExplicitlyTrashedParentPageWitExplicitlyTrashedChildPageFromTrash()
		throws Exception {

		WikiPage[] pages = WikiTestUtil.addTrashedPageWithChildPage(
			_group.getGroupId(), _node.getNodeId(), true);

		WikiPage parentPage = pages[0];
		WikiPage childPage = pages[1];

		restoreFromTrash(parentPage);

		parentPage = WikiPageLocalServiceUtil.getPage(
			parentPage.getResourcePrimKey());
		childPage = WikiPageLocalServiceUtil.getPage(
			childPage.getResourcePrimKey());

		Assert.assertFalse(parentPage.isInTrash());
		Assert.assertTrue(childPage.isInTrashExplicitly());
		Assert.assertEquals(childPage.getParentTitle(), parentPage.getTitle());
	}

	@Test
	public void
			testRestoreExplicitlyTrashedParentPageWithChildPageAndgrandchildPageFromTrash()
		throws Exception {

		int initialBaseModelsCount =
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node);
		int initialTrashEntriesCount =
			TrashEntryLocalServiceUtil.getEntriesCount(_group.getGroupId());

		WikiPage[] pages = addTrashedParentPageWithChildPageAndGrandchildPage(
			_group.getGroupId(), _node.getNodeId(), false, true);

		WikiPage parentPage = pages[0];
		WikiPage childPage = pages[1];
		WikiPage grandchildPage = pages[2];

		restoreFromTrash(parentPage);

		parentPage = WikiPageLocalServiceUtil.getPage(
			parentPage.getResourcePrimKey());
		childPage = WikiPageLocalServiceUtil.getPage(
			childPage.getResourcePrimKey());
		grandchildPage = WikiPageLocalServiceUtil.getPage(
			grandchildPage.getResourcePrimKey());

		Assert.assertFalse(parentPage.isInTrash());
		Assert.assertFalse(childPage.isInTrash());
		Assert.assertEquals(parentPage.getTitle(), childPage.getParentTitle());
		Assert.assertFalse(grandchildPage.isInTrash());
		Assert.assertEquals(
			childPage.getTitle(), grandchildPage.getParentTitle());
		Assert.assertEquals(
			initialBaseModelsCount + 3,
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node));
		Assert.assertEquals(
			initialTrashEntriesCount,
			TrashEntryLocalServiceUtil.getEntriesCount(_group.getGroupId()));
	}

	@Test
	public void
			testRestoreExplicitlyTrashedParentPageWithRedirectPageFromTrash()
		throws Exception {

		WikiPages pages = givenPageWithChildAndRedirectPage(
		);

		WikiPage trashedRedirectPage = movePageToTrash(pages.getRedirectPage());
		WikiPage trashedPage = movePageToTrash(pages.getPage());

		WikiPage trashedChildPage = getUpdatedPage(pages.getChildPage());

		WikiPage restoredPage = restoreFromTrash(trashedPage);

		WikiPage restoredChildPage = getUpdatedPage(trashedChildPage);
		trashedRedirectPage = getUpdatedPage(trashedRedirectPage);

		Assert.assertFalse(restoredPage.isInTrash());
		Assert.assertFalse(restoredChildPage.isInTrash());
		Assert.assertEquals(
			restoredChildPage.getParentTitle(), restoredPage.getTitle());
		Assert.assertTrue(trashedRedirectPage.isInTrashExplicitly());
		Assert.assertEquals(
			trashedRedirectPage.getRedirectTitle(), restoredPage.getTitle());

		WikiPage restoredRedirectPage = restoreFromTrash(trashedRedirectPage);

		Assert.assertFalse(restoredRedirectPage.isInTrash());
		Assert.assertEquals(
			restoredRedirectPage.getRedirectTitle(), restoredPage.getTitle());
	}

	@Test
	public void testRestoreExplicitlyTrashedParentPageWithRedirectPageToTrash()
		throws Exception {

		WikiPages pages = givenPageWithChildAndRedirectPage(
		);

		WikiPage trashedChildPage = movePageToTrash(pages.getChildPage());
		WikiPage trashedRedirectPage = movePageToTrash(pages.getRedirectPage());
		WikiPage trashedPage = movePageToTrash(pages.getPage());

		WikiPage restoredPage = restoreFromTrash(trashedPage);

		trashedChildPage = getUpdatedPage(trashedChildPage);
		trashedRedirectPage = getUpdatedPage(trashedRedirectPage);

		Assert.assertFalse(restoredPage.isInTrash());
		Assert.assertTrue(trashedChildPage.isInTrashExplicitly());
		Assert.assertEquals(
			trashedChildPage.getParentTitle(), restoredPage.getTitle());
		Assert.assertTrue(trashedRedirectPage.isInTrashExplicitly());
		Assert.assertEquals(
			trashedRedirectPage.getRedirectTitle(), restoredPage.getTitle());
	}

	@Test
	public void
			testRestoreExplicitlyTrashedRedirectPageWithRestoredPageFromTrash()
		throws Exception {

		WikiPage[] pages = WikiTestUtil.addRenamedTrashedPage(
			_group.getGroupId(), _node.getNodeId(), true);

		WikiPage page = pages[0];
		WikiPage redirectPage = pages[1];

		restoreFromTrash(page);
		restoreFromTrash(redirectPage);

		page = WikiPageLocalServiceUtil.getPage(page.getResourcePrimKey());
		redirectPage = WikiPageLocalServiceUtil.getPage(
			redirectPage.getResourcePrimKey());

		Assert.assertFalse(redirectPage.isInTrash());
		Assert.assertEquals(redirectPage.getRedirectTitle(), page.getTitle());
	}

	@Test
	public void testRestorePageWithParentPageInTrash() throws Exception {
		WikiPage[] pages = WikiTestUtil.addTrashedPageWithChildPage(
			_group.getGroupId(), _node.getNodeId(), false);

		WikiPage childPage = pages[1];

		WikiPage newParentPage = WikiTestUtil.addPage(
			_group.getGroupId(), _node.getNodeId(), true);

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			WikiPage.class.getName());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		trashHandler.moveEntry(
			TestPropsValues.getUserId(), childPage.getResourcePrimKey(),
			newParentPage.getResourcePrimKey(), serviceContext);

		childPage = WikiPageLocalServiceUtil.getPage(
			childPage.getResourcePrimKey());

		Assert.assertTrue(childPage.isApproved());
		Assert.assertEquals(
			newParentPage.getTitle(), childPage.getParentTitle());
	}

	@Test
	public void
			testRestoreParentPageWithExplicitlyTrashedRedirectPageFromTrash()
		throws Exception {

		WikiPages pages = givenPageWithChildAndRedirectPage(
		);

		WikiPage trashedChildPage = movePageToTrash(pages.getChildPage());
		WikiPage trashedPage = movePageToTrash(pages.getPage());

		WikiPage trashedRedirectPage = getUpdatedPage(pages.getRedirectPage());

		WikiPage restoredPage = restoreFromTrash(trashedPage);

		trashedChildPage = getUpdatedPage(trashedChildPage);
		WikiPage restoredRedirectPage = getUpdatedPage(trashedRedirectPage);

		Assert.assertFalse(restoredPage.isInTrash());
		Assert.assertTrue(trashedChildPage.isInTrashExplicitly());
		Assert.assertEquals(
			trashedChildPage.getParentTitle(), restoredPage.getTitle());
		Assert.assertFalse(restoredRedirectPage.isInTrash());
		Assert.assertEquals(
			restoredRedirectPage.getRedirectTitle(), restoredPage.getTitle());
	}

	@Test
	public void testRestoreRedirectPageWithParentPageFromTrash()
		throws Exception {

		int initialBaseModelsCount =
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node);
		int initialTrashEntriesCount =
			TrashEntryLocalServiceUtil.getEntriesCount(_group.getGroupId());

		WikiPage[] pages = addRenamedParentPageWithChildPageAndGrandchildPage(
			_group.getGroupId(), _node.getNodeId());

		WikiPage parentPage = pages[0];
		WikiPage redirectPage = pages[1];
		WikiPage childPage = pages[2];
		WikiPage grandchildPage = pages[3];

		WikiPageTrashHandlerTestUtil.moveBaseModelToTrash(
			redirectPage.getPrimaryKey());

		restoreFromTrash(redirectPage);

		parentPage = WikiPageLocalServiceUtil.getPage(
			parentPage.getResourcePrimKey());
		redirectPage = WikiPageLocalServiceUtil.getPage(
			redirectPage.getResourcePrimKey());
		childPage = WikiPageLocalServiceUtil.getPage(
			childPage.getResourcePrimKey());
		grandchildPage = WikiPageLocalServiceUtil.getPage(
			grandchildPage.getResourcePrimKey());

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
			initialBaseModelsCount + 4,
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node));
		Assert.assertEquals(
			initialTrashEntriesCount,
			TrashEntryLocalServiceUtil.getEntriesCount(_group.getGroupId()));
	}

	@Test
	public void testTrashVersionCreationWhenMovingToTrash() throws Exception {
		int initialTrashVersionsCount =
			TrashVersionLocalServiceUtil.getTrashVersionsCount();

		WikiPages pages = givenPageWithChildAndRedirectPage(
		);

		movePageToTrash(pages.getPage());

		Assert.assertEquals(
			initialTrashVersionsCount + 3,
			TrashVersionLocalServiceUtil.getTrashVersionsCount());
	}

	@Test
	public void testTrashVersionDeletionWhenRestoringFromTrash()
		throws Exception {

		int initialTrashVersionCount =
			TrashVersionLocalServiceUtil.getTrashVersionsCount();

		givenPageWithChildAndRedirectPage(
		);

		WikiPage page = WikiPageLocalServiceUtil.movePageToTrash(
			TestPropsValues.getUserId(), _node.getNodeId(), "RenamedPage");

		restoreFromTrash(page);

		Assert.assertEquals(
			initialTrashVersionCount,
			TrashVersionLocalServiceUtil.getTrashVersionsCount());
	}

	protected WikiPage[] addPageWithChangedParentPage(long groupId, long nodeId)
		throws Exception {

		WikiPage initialParentPage = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), groupId, nodeId,
			RandomTestUtil.randomString(), true);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		WikiPage childPage = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), nodeId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), initialParentPage.getTitle(), true,
			serviceContext);

		WikiPage finalParentPage =  WikiTestUtil.addPage(
			TestPropsValues.getUserId(), groupId, nodeId,
			RandomTestUtil.randomString(), true);

		WikiPageLocalServiceUtil.changeParent(
			TestPropsValues.getUserId(), nodeId, childPage.getTitle(),
			finalParentPage.getTitle(), serviceContext);

		childPage = WikiPageLocalServiceUtil.getPage(
			nodeId, childPage.getTitle());
		initialParentPage =  WikiPageLocalServiceUtil.getPage(
			initialParentPage.getResourcePrimKey());
		finalParentPage =  WikiPageLocalServiceUtil.getPage(
			finalParentPage.getResourcePrimKey());

		return new WikiPage[] {childPage, finalParentPage, initialParentPage};
	}

	protected WikiPage[] addRenamedParentPageWithChildPageAndGrandchildPage(
			long groupId, long nodeId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		WikiTestUtil.addPage(
			TestPropsValues.getUserId(), groupId, nodeId, "InitialNamePage",
			true);

		WikiPage childPage = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), nodeId, "ChildPage",
			RandomTestUtil.randomString(), "InitialNamePage", true,
			serviceContext);

		WikiPage grandchildPage = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), nodeId, "GrandChildPage",
			RandomTestUtil.randomString(), "ChildPage", true, serviceContext);

		WikiPageLocalServiceUtil.renamePage(
			TestPropsValues.getUserId(), nodeId, "InitialNamePage",
			"RenamedPage", serviceContext);

		WikiPage page = WikiPageLocalServiceUtil.getPage(nodeId, "RenamedPage");
		WikiPage redirectPage = WikiPageLocalServiceUtil.getPage(
			nodeId, "InitialNamePage");
		childPage = WikiPageLocalServiceUtil.getPage(
			childPage.getResourcePrimKey());
		grandchildPage = WikiPageLocalServiceUtil.getPage(
			grandchildPage.getResourcePrimKey());

		return new WikiPage[] {
			page, redirectPage, childPage, grandchildPage};
	}

	protected WikiPage[] addTrashedParentPageWithChildPageAndGrandchildPage(
			long groupId, long nodeId, boolean explicitMoveChildToTrash,
			boolean explicitMoveParentToTrash)
		throws Exception {

		WikiPage parentPage = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), groupId, nodeId,
			RandomTestUtil.randomString(), true);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		WikiPage childPage = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), nodeId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), parentPage.getTitle(), true,
			serviceContext);

		WikiPage grandchildPage = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), nodeId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), childPage.getTitle(), true,
			serviceContext);

		if (explicitMoveChildToTrash) {
			WikiPageLocalServiceUtil.movePageToTrash(
				TestPropsValues.getUserId(), childPage);
		}

		if (explicitMoveParentToTrash) {
			WikiPageLocalServiceUtil.movePageToTrash(
				TestPropsValues.getUserId(), parentPage);
		}

		parentPage = WikiPageLocalServiceUtil.getPage(
			parentPage.getResourcePrimKey());
		childPage = WikiPageLocalServiceUtil.getPage(
			childPage.getResourcePrimKey());
		grandchildPage = WikiPageLocalServiceUtil.getPage(
			grandchildPage.getResourcePrimKey());

		return new WikiPage[] {parentPage, childPage, grandchildPage};
	}

	protected WikiPage getUpdatedPage(WikiPage page) throws PortalException {
		return WikiPageLocalServiceUtil.getPage(page.getResourcePrimKey());
	}

	protected WikiPages givenPageWithChildAndRedirectPage()
		throws Exception {

		long nodeId = _node.getNodeId();
		long groupId = _group.getGroupId();

		WikiTestUtil.addPage(
			TestPropsValues.getUserId(), groupId, nodeId, "InitialNamePage",
			true);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		WikiPageLocalServiceUtil.renamePage(
			TestPropsValues.getUserId(), nodeId, "InitialNamePage",
			"RenamedPage", serviceContext);

		WikiTestUtil.addPage(
			TestPropsValues.getUserId(), nodeId, "ChildPage",
			RandomTestUtil.randomString(), "RenamedPage", true, serviceContext);

		WikiPage page = WikiPageLocalServiceUtil.getPage(nodeId, "RenamedPage");
		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			nodeId, "ChildPage");
		WikiPage redirectPage = WikiPageLocalServiceUtil.getPage(
			nodeId, "InitialNamePage");

		return new WikiPages(page, childPage, redirectPage);
	}

	protected WikiPages givenPageWithChildPage()
		throws Exception {

		long nodeId = _node.getNodeId();
		long groupId = _group.getGroupId();

		WikiPage page = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), groupId, nodeId, "Page", true);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		WikiPage childPage = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), nodeId, "ChildPage",
			RandomTestUtil.randomString(), "Page", true, serviceContext);

		return new WikiPages(page, childPage);
	}

	protected WikiPages givenPageWithRedirectPage()
		throws Exception {

		long nodeId = _node.getNodeId();
		long groupId = _group.getGroupId();

		WikiTestUtil.addPage(
			TestPropsValues.getUserId(), groupId, nodeId, "InitialNamePage",
			true);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		WikiPageLocalServiceUtil.renamePage(
			TestPropsValues.getUserId(), nodeId, "InitialNamePage",
			"RenamedPage", serviceContext);

		WikiPage page = WikiPageLocalServiceUtil.getPage(nodeId, "RenamedPage");
		WikiPage redirectPage = WikiPageLocalServiceUtil.getPage(
			nodeId, "InitialNamePage");

		return new WikiPages(page, redirectPage);
	}

	protected WikiPage movePageToTrash(WikiPage page)
		throws com.liferay.portal.kernel.exception.PortalException {

		WikiPageLocalServiceUtil.movePageToTrash(
			TestPropsValues.getUserId(), _node.getNodeId(), page.getTitle());

		return getUpdatedPage(page);
	}

	protected WikiPage restoreFromTrash(WikiPage page) throws Exception {
		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			WikiPage.class.getName());

		trashHandler.restoreTrashEntry(
			TestPropsValues.getUserId(), page.getResourcePrimKey());

		return getUpdatedPage(page);
	}

	private Group _group;
	private WikiNode _node;

	private class WikiPages {

		public WikiPages(WikiPage page, WikiPage dependentPage)
			throws PortalException {

			_page = page;

			if (Validator.isNotNull(dependentPage.getParentTitle())) {
				_childPage = dependentPage;
			}
			else {
				_redirectPage = dependentPage;
			}
		}

		public WikiPages(
			WikiPage page, WikiPage childPage, WikiPage redirectPage) {

			_page = page;
			_childPage = childPage;
			_redirectPage = redirectPage;
		}

		public WikiPage getChildPage() {
			return _childPage;
		}

		public WikiPage getPage() {
			return _page;
		}

		public WikiPage getRedirectPage() {
			return _redirectPage;
		}
		private WikiPage _childPage;
		private WikiPage _page;
		private WikiPage _redirectPage;
	}

}