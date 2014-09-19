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
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.test.listeners.MainServletExecutionTestListener;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portlet.trash.service.TrashEntryLocalServiceUtil;
import com.liferay.portlet.trash.service.TrashVersionLocalServiceUtil;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.model.WikiPageResource;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiPageResourceLocalServiceUtil;
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

		_node = WikiTestUtil.addNode(_group.getGroupId());
	}

	@Test
	public void testAddPageWithSameTitleAsImplicitlyDeletedPageVersion()
		throws Exception {

		WikiPage[] pages = WikiTestUtil.addPageWithChangedParentPage(
			_group.getGroupId(), _node.getNodeId());

		WikiPage childPage = pages[0];
		WikiPage parentPage = pages[1];

		String childPageTitle = childPage.getTitle();

		WikiPageLocalServiceUtil.movePageToTrash(
			TestPropsValues.getUserId(), parentPage);

		WikiPage page = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _group.getGroupId(), _node.getNodeId(),
			childPageTitle, true);

		Assert.assertNotNull(page);
	}

	@Test
	public void
			testMoveExplicitlyChildPageAndParentPageWithRedirectPageToTrash()
		throws Exception {

		WikiPage[] pages = WikiTestUtil.addRenamedTrashedParentPage(
			_group.getGroupId(), _node.getNodeId(), true, false);

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
	public void testMoveExplicitlyChildPageWithChildPageAndParentPageToTrash()
		throws Exception {

		int initialBaseModelsCount =
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node);
		int initialTrashEntriesCount =
			TrashEntryLocalServiceUtil.getEntriesCount(_group.getGroupId());

		WikiPage[] pages =
			WikiTestUtil.addTrashedParentPageWithChildPageAndGrandchildPage(
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
			initialBaseModelsCount,
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node));
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

		WikiPage[] pages =
			WikiTestUtil.addTrashedParentPageWithChildPageAndGrandchildPage(
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

		WikiPage[] pages = WikiTestUtil.addRenamedTrashedParentPage(
			_group.getGroupId(), _node.getNodeId(), true, true);

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
	public void testMoveExplicitlyParentPageAndChildPagePageWithChildToTrash()
		throws Exception {

		int initialBaseModelsCount =
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node);
		int initialTrashEntriesCount =
			TrashEntryLocalServiceUtil.getEntriesCount(_group.getGroupId());

		WikiPage[] pages =
			WikiTestUtil.addTrashedParentPageWithChildPageAndGrandchildPage(
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

		WikiPage[] pages =
			WikiTestUtil.addRenamedTrashedParentPage(
				_group.getGroupId(), _node.getNodeId(), false, true);

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
	public void testMoveInitialParentPageToTrash() throws Exception {
		int initialBaseModelsCount =
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node);
		int initialTrashEntriesCount =
			TrashEntryLocalServiceUtil.getEntriesCount(_group.getGroupId());

		WikiPage[] pages = WikiTestUtil.addPageWithChangedParentPage(
			_group.getGroupId(), _node.getNodeId());

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

		WikiPage[] pages =
			WikiTestUtil.addRenamedParentPageWithChildPageAndGrandchildPage(
				_group.getGroupId(), _node.getNodeId());

		WikiPage parentPage = pages[0];
		WikiPage redirectPage = pages[1];
		WikiPage childPage = pages[2];
		WikiPage grandchildPage = pages[3];

		WikiPageTrashHandlerTestUtil.moveBaseModelToTrash(redirectPage.getPrimaryKey());

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
			initialBaseModelsCount + 3,
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node));
		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			TrashEntryLocalServiceUtil.getEntriesCount(_group.getGroupId()));
	}

	@Test
	public void testMoveParentPageWithRedirectPageToTrash() throws Exception {
		WikiPage[] pages = WikiTestUtil.addRenamedTrashedParentPage(
			_group.getGroupId(), _node.getNodeId(), false, false);

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
	public void
			testRestoreExplicitlyTrashedChildPageAndPageWithRedirectFromTrash()
		throws Exception {

		WikiPage[] pages = WikiTestUtil.addRenamedTrashedParentPage(
			_group.getGroupId(), _node.getNodeId(), true, false);

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
	public void
			testRestoreExplicitlyTrashedChildPageAndParentPageWithRedirectPageFromTrash()
		throws Exception {

		WikiPage[] pages = WikiTestUtil.addRenamedTrashedParentPage(
			_group.getGroupId(), _node.getNodeId(), true, true);

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
	public void testRestoreExplicitlyTrashedChildPageWithChildPageFromTrash()
		throws Exception {

		int initialBaseModelsCount =
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node);
		int initialTrashEntriesCount =
			TrashEntryLocalServiceUtil.getEntriesCount(_group.getGroupId());

		WikiPage[] pages =
			WikiTestUtil.addTrashedParentPageWithChildPageAndGrandchildPage(
				_group.getGroupId(), _node.getNodeId(), true, false);

		WikiPage parentPage = pages[0];
		WikiPage childPage = pages[1];
		WikiPage grandchildPage = pages[2];

		restoreTrashEntry(childPage);

		parentPage = (WikiPage)WikiPageTrashHandlerTestUtil.getBaseModel(
			parentPage.getPrimaryKey());
		childPage = (WikiPage)WikiPageTrashHandlerTestUtil.getBaseModel(
			childPage.getPrimaryKey());
		grandchildPage = (WikiPage)WikiPageTrashHandlerTestUtil.getBaseModel(
			grandchildPage.getPrimaryKey());

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

		WikiPage[] pages =
			WikiTestUtil.addTrashedParentPageWithChildPageAndGrandchildPage(
				_group.getGroupId(), _node.getNodeId(), true, true);

		WikiPage parentPage = pages[0];
		WikiPage childPage = pages[1];
		WikiPage grandchildPage = pages[2];

		restoreTrashEntry(childPage);

		parentPage = (WikiPage)WikiPageTrashHandlerTestUtil.getBaseModel(
			parentPage.getPrimaryKey());
		childPage = (WikiPage)WikiPageTrashHandlerTestUtil.getBaseModel(
			childPage.getPrimaryKey());
		grandchildPage = (WikiPage)WikiPageTrashHandlerTestUtil.getBaseModel(
			grandchildPage.getPrimaryKey());

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

		restoreTrashEntry(page);

		page = WikiPageLocalServiceUtil.getPageByPageId(page.getPageId());
		redirectPage = WikiPageLocalServiceUtil.getPageByPageId(
			redirectPage.getPageId());

		Assert.assertFalse(page.isInTrash());
		Assert.assertTrue(redirectPage.isInTrashExplicitly());
		Assert.assertEquals(redirectPage.getRedirectTitle(), page.getTitle());
	}

	@Test
	public void
			testRestoreExplicitlyTrashedParentPageAndChildPageAndRedirectPageFromTrash()
		throws Exception {

		WikiPage[] pages = WikiTestUtil.addRenamedTrashedParentPage(
			_group.getGroupId(), _node.getNodeId(), true, true);

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
	public void testRestoreExplicitlyTrashedParentPageAndChildPageFromTrash()
		throws Exception {

		WikiPage[] pages = WikiTestUtil.addTrashedPageWithChildPage(
			_group.getGroupId(), _node.getNodeId(), true);

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
	public void testRestoreExplicitlyTrashedParentPageAndRedirectFromTrash()
		throws Exception {

		WikiPage[] pages = WikiTestUtil.addRenamedTrashedParentPage(
			_group.getGroupId(), _node.getNodeId(), false, true);

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
	public void testRestoreExplicitlyTrashedParentPageFromTrash()
		throws Exception {

		WikiPage[] pages = WikiTestUtil.addTrashedPageWithChildPage(
			_group.getGroupId(), _node.getNodeId(), false);

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
	public void
			testRestoreExplicitlyTrashedParentPageWitExplicitlyTrashedChildPageFromTrash()
		throws Exception {

		WikiPage[] pages = WikiTestUtil.addTrashedPageWithChildPage(
			_group.getGroupId(), _node.getNodeId(), true);

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
	public void
			testRestoreExplicitlyTrashedParentPageWithChildPageAndgrandchildPageFromTrash()
		throws Exception {

		int initialBaseModelsCount =
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node);
		int initialTrashEntriesCount =
			TrashEntryLocalServiceUtil.getEntriesCount(_group.getGroupId());

		WikiPage[] pages =
			WikiTestUtil.addTrashedParentPageWithChildPageAndGrandchildPage(
				_group.getGroupId(), _node.getNodeId(), false, true);

		WikiPage parentPage = pages[0];
		WikiPage childPage = pages[1];
		WikiPage grandchildPage = pages[2];

		restoreTrashEntry(parentPage);

		parentPage = (WikiPage)WikiPageTrashHandlerTestUtil.getBaseModel(
			parentPage.getPrimaryKey());
		childPage = (WikiPage)WikiPageTrashHandlerTestUtil.getBaseModel(
			childPage.getPrimaryKey());
		grandchildPage = (WikiPage)WikiPageTrashHandlerTestUtil.getBaseModel(
			grandchildPage.getPrimaryKey());

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

		WikiPage[] pages = WikiTestUtil.addRenamedTrashedParentPage(
			_group.getGroupId(), _node.getNodeId(), false, true);

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
	public void testRestoreExplicitlyTrashedParentPageWithRedirectPageToTrash()
		throws Exception {

		WikiPage[] pages = WikiTestUtil.addRenamedTrashedParentPage(
			_group.getGroupId(), _node.getNodeId(), true, true);

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
	public void
			testRestoreExplicitlyTrashedRedirectPageWithRestoredPageFromTrash()
		throws Exception {

		WikiPage[] pages = WikiTestUtil.addRenamedTrashedPage(
			_group.getGroupId(), _node.getNodeId(), true);

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
	public void testRestorePageToADifferentNode() throws Exception {
		WikiPage[] pages = WikiTestUtil.addPageWithChildPageAndRedirectPage(
			_group.getGroupId(), _node.getNodeId());

		WikiPage page = pages[0];
		WikiPage childPage = pages[1];
		WikiPage redirectPage = pages[2];

		WikiPageTrashHandlerTestUtil.moveParentBaseModelToTrash(
			_node.getNodeId());

		WikiNode newNode = WikiTestUtil.addNode(_group.getGroupId());

		moveTrashEntry(childPage.getResourcePrimKey(), newNode.getNodeId());

		page = WikiPageLocalServiceUtil.getPage(page.getResourcePrimKey());
		redirectPage = WikiPageLocalServiceUtil.getPage(
			redirectPage.getResourcePrimKey());
		childPage = WikiPageLocalServiceUtil.getPage(
			childPage.getResourcePrimKey());

		Assert.assertEquals(_node.getNodeId(), page.getNodeId());
		Assert.assertEquals(newNode.getNodeId(), childPage.getNodeId());
		Assert.assertEquals(_node.getNodeId(), redirectPage.getNodeId());

		Assert.assertNull(childPage.getParentPage());

		WikiPageResource pageResource =
			WikiPageResourceLocalServiceUtil.getWikiPageResource(
				page.getResourcePrimKey());
		WikiPageResource childPageResource =
			WikiPageResourceLocalServiceUtil.getWikiPageResource(
				childPage.getResourcePrimKey());
		WikiPageResource redirectPageResource =
			WikiPageResourceLocalServiceUtil.getWikiPageResource(
				redirectPage.getResourcePrimKey());

		Assert.assertEquals(_node.getNodeId(), pageResource.getNodeId());
		Assert.assertEquals(newNode.getNodeId(), childPageResource.getNodeId());
		Assert.assertEquals(
			_node.getNodeId(), redirectPageResource.getNodeId());
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
	public void testRestoreParentPageToADifferentNode() throws Exception {
		WikiPage[] pages = WikiTestUtil.addPageWithChildPageAndRedirectPage(
			_group.getGroupId(), _node.getNodeId());

		WikiPage page = pages[0];
		WikiPage childPage = pages[1];
		WikiPage redirectPage = pages[2];

		WikiPageTrashHandlerTestUtil.moveParentBaseModelToTrash(
			_node.getNodeId());

		WikiNode newNode = WikiTestUtil.addNode(_group.getGroupId());

		WikiPage newParentPage = WikiTestUtil.addPage(
			_group.getGroupId(), newNode.getNodeId(), true);

		moveTrashEntry(
			childPage.getResourcePrimKey(), newParentPage.getResourcePrimKey());

		page = WikiPageLocalServiceUtil.getPage(page.getResourcePrimKey());
		childPage = WikiPageLocalServiceUtil.getPage(
			childPage.getResourcePrimKey());
		redirectPage = WikiPageLocalServiceUtil.getPage(
			redirectPage.getResourcePrimKey());

		Assert.assertEquals(_node.getNodeId(), page.getNodeId());
		Assert.assertEquals(newNode.getNodeId(), childPage.getNodeId());
		Assert.assertEquals(_node.getNodeId(), redirectPage.getNodeId());
		Assert.assertEquals(
			newParentPage.getTitle(), childPage.getParentTitle());
	}

	@Test
	public void
			testRestoreParentPageWithExplicitlyTrashedRrdirectPageFromTrash()
		throws Exception {

		WikiPage[] pages = WikiTestUtil.addRenamedTrashedParentPage(
			_group.getGroupId(), _node.getNodeId(), true, false);

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
	public void testRestoreRedirectPageWithParentPageFromTrash()
		throws Exception {

		int initialBaseModelsCount =
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node);
		int initialTrashEntriesCount =
			TrashEntryLocalServiceUtil.getEntriesCount(_group.getGroupId());

		WikiPage[] pages =
			WikiTestUtil.addRenamedParentPageWithChildPageAndGrandchildPage(
				_group.getGroupId(), _node.getNodeId());

		WikiPage parentPage = pages[0];
		WikiPage redirectPage = pages[1];
		WikiPage childPage = pages[2];
		WikiPage grandchildPage = pages[3];

		WikiPageTrashHandlerTestUtil.moveBaseModelToTrash(
			redirectPage.getPrimaryKey());

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

		WikiTestUtil.addRenamedTrashedParentPage(
			_group.getGroupId(), _node.getNodeId(), false, false);

		Assert.assertEquals(
			initialTrashVersionsCount + 3,
			TrashVersionLocalServiceUtil.getTrashVersionsCount());
	}

	@Test
	public void testTrashVersionDeletionWhenRestoringFromTrash()
		throws Exception {

		int initialTrashVersionCount =
			TrashVersionLocalServiceUtil.getTrashVersionsCount();

		WikiPage[] pages = WikiTestUtil.addRenamedTrashedParentPage(
			_group.getGroupId(), _node.getNodeId(), false, false);

		WikiPage parentPage = pages[0];

		restoreTrashEntry(parentPage);

		Assert.assertEquals(
			initialTrashVersionCount,
			TrashVersionLocalServiceUtil.getTrashVersionsCount());
	}

	protected void moveTrashEntry(long classPK, long newContainerId)
		throws Exception {

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			WikiPage.class.getName());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		trashHandler.moveTrashEntry(
			TestPropsValues.getUserId(), classPK, newContainerId,
			serviceContext);
	}

	protected void restoreTrashEntry(WikiPage page) throws Exception {
		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			WikiPage.class.getName());

		trashHandler.restoreTrashEntry(
			TestPropsValues.getUserId(),
			WikiPageTrashHandlerTestUtil.getTrashEntryClassPK(page));
	}

	@DeleteAfterTestRun
	private Group _group;

	private WikiNode _node;

}