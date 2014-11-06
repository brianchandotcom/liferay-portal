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
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.DeleteAfterTestRun;
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

		RelatedPages relatedPages = buildRelatedPages();

		WikiPage childPage = relatedPages.getChildPage();

		String childPageOriginalTitle = childPage.getTitle();

		movePageToTrash(relatedPages.getPage());

		childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());

		Assert.assertNotEquals(childPageOriginalTitle, childPage.getTitle());

		WikiPage newPage = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _group.getGroupId(), _node.getNodeId(),
			childPageOriginalTitle, true);

		Assert.assertNotNull(newPage);
	}

	@Test
	public void
			testMoveExplicitlyChildPageAndParentPageWithRedirectPageToTrash()
		throws Exception {

		RelatedPages relatedPages = buildRelatedPages();

		movePageToTrash(relatedPages.getChildPage());
		movePageToTrash(relatedPages.getRedirectPage());
		movePageToTrash(relatedPages.getPage());

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());
		WikiPage redirectPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getRedirectPageResourcePrimKey());

		Assert.assertTrue(page.isInTrashExplicitly());
		Assert.assertTrue(childPage.isInTrashExplicitly());
		Assert.assertTrue(redirectPage.isInTrashExplicitly());
		Assert.assertEquals(page.getTitle(), childPage.getParentTitle());
		Assert.assertEquals(page.getTitle(), redirectPage.getRedirectTitle());
	}

	@Test
	public void testMoveExplicitlyChildPageWithChildPageAndParentPageToTrash()
		throws Exception {

		int initialBaseModelsCount =
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node);
		int initialTrashEntriesCount =
			TrashEntryLocalServiceUtil.getEntriesCount(_group.getGroupId());

		RelatedPages relatedPages = buildRelatedPages();

		movePageToTrash(relatedPages.getChildPage());
		movePageToTrash(relatedPages.getPage());

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());
		WikiPage grandchildPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getGrandchildPageResourcePrimKey());

		Assert.assertTrue(page.isInTrashExplicitly());
		Assert.assertTrue(childPage.isInTrashExplicitly());
		Assert.assertTrue(grandchildPage.isInTrashImplicitly());
		Assert.assertEquals(
			childPage.getTitle(), grandchildPage.getParentTitle());
		Assert.assertEquals(
			initialBaseModelsCount + 1,
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

		RelatedPages relatedPages = buildRelatedPages();

		movePageToTrash(relatedPages.getChildPage());

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());
		WikiPage grandchildPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getGrandchildPageResourcePrimKey());

		Assert.assertFalse(page.isInTrash());
		Assert.assertTrue(childPage.isInTrashExplicitly());
		Assert.assertTrue(grandchildPage.isInTrashImplicitly());
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
	public void testMoveExplicitlyPageAndRedirectPageToTrash()
		throws Exception {

		RelatedPages relatedPages = buildRelatedPages();

		movePageToTrash(relatedPages.getRedirectPage());
		movePageToTrash(relatedPages.getPage());

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage redirectPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getRedirectPageResourcePrimKey());

		Assert.assertTrue(page.isInTrashExplicitly());
		Assert.assertTrue(redirectPage.isInTrashExplicitly());
		Assert.assertEquals(page.getTitle(), redirectPage.getRedirectTitle());
	}

	@Test
	public void testMoveExplicitlyParentPageAndChildPageAndRedirectPageToTrash()
		throws Exception {

		RelatedPages relatedPages = buildRelatedPages();

		movePageToTrash(relatedPages.getChildPage());
		movePageToTrash(relatedPages.getPage());

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());
		WikiPage redirectPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getRedirectPageResourcePrimKey());

		Assert.assertTrue(page.isInTrashExplicitly());
		Assert.assertTrue(childPage.isInTrashExplicitly());
		Assert.assertTrue(redirectPage.isInTrashImplicitly());
		Assert.assertEquals(page.getTitle(), childPage.getParentTitle());
		Assert.assertEquals(page.getTitle(), redirectPage.getRedirectTitle());
	}

	@Test
	public void testMoveExplicitlyParentPageAndChildPagePageWithChildToTrash()
		throws Exception {

		int initialBaseModelsCount =
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node);
		int initialTrashEntriesCount =
			TrashEntryLocalServiceUtil.getEntriesCount(_group.getGroupId());

		RelatedPages relatedPages = buildRelatedPages();

		movePageToTrash(relatedPages.getPage());

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());
		WikiPage grandchildPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getGrandchildPageResourcePrimKey());

		Assert.assertTrue(page.isInTrashExplicitly());
		Assert.assertTrue(childPage.isInTrashImplicitly());
		Assert.assertTrue(grandchildPage.isInTrashImplicitly());
		Assert.assertEquals(page.getTitle(), childPage.getParentTitle());
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
	public void testMoveExplicitlyParentPageAndChildPageToTrash()
		throws Exception {

		RelatedPages relatedPages = buildRelatedPages();

		movePageToTrash(relatedPages.getChildPage());
		movePageToTrash(relatedPages.getPage());

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());

		Assert.assertTrue(page.isInTrashExplicitly());
		Assert.assertTrue(childPage.isInTrashExplicitly());
		Assert.assertEquals(page.getTitle(), childPage.getParentTitle());
	}

	@Test
	public void testMoveExplicitlyParentPageAndRedirectPageToTrash()
		throws Exception {

		RelatedPages relatedPages = buildRelatedPages();

		movePageToTrash(relatedPages.getRedirectPage());
		movePageToTrash(relatedPages.getPage());

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());
		WikiPage redirectPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getRedirectPageResourcePrimKey());

		Assert.assertTrue(childPage.isInTrashImplicitly());
		Assert.assertTrue(redirectPage.isInTrashExplicitly());
		Assert.assertTrue(page.isInTrashExplicitly());
		Assert.assertEquals(page.getTitle(), childPage.getParentTitle());
		Assert.assertEquals(page.getTitle(), redirectPage.getRedirectTitle());
	}

	@Test
	public void testMoveInitialParentPageToTrash() throws Exception {
		int initialBaseModelsCount =
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node);
		int initialTrashEntriesCount =
			TrashEntryLocalServiceUtil.getEntriesCount(_group.getGroupId());

		RelatedPages relatedPages = buildRelatedPages();

		WikiPage parentPage = relatedPages.getParentPage();
		WikiPage page = relatedPages.getPage();

		WikiPage newParentPage =  WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _group.getGroupId(), _node.getNodeId(),
			RandomTestUtil.randomString(), true);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		WikiPageLocalServiceUtil.changeParent(
			TestPropsValues.getUserId(), _node.getNodeId(), page.getTitle(),
			newParentPage.getTitle(), serviceContext);

		movePageToTrash(parentPage);

		newParentPage = WikiPageLocalServiceUtil.getPage(
			newParentPage.getResourcePrimKey());
		page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		parentPage = WikiPageLocalServiceUtil.getPage(
			parentPage.getResourcePrimKey());

		Assert.assertFalse(page.isInTrash());
		Assert.assertFalse(newParentPage.isInTrash());
		Assert.assertTrue(parentPage.isInTrashExplicitly());
		Assert.assertEquals(page.getParentTitle(), newParentPage.getTitle());
		Assert.assertEquals(newParentPage.getTitle(), page.getParentTitle());
		Assert.assertEquals(
			initialBaseModelsCount + 4,
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node));
		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			TrashEntryLocalServiceUtil.getEntriesCount(_group.getGroupId()));
	}

	@Test
	public void testMovePageWithRedirectPageToTrash() throws Exception {
		RelatedPages relatedPages = buildRelatedPages();

		movePageToTrash(relatedPages.getPage());

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage redirectPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getRedirectPageResourcePrimKey());

		Assert.assertTrue(page.isInTrashExplicitly());
		Assert.assertTrue(redirectPage.isInTrashImplicitly());
		Assert.assertEquals(page.getTitle(), redirectPage.getRedirectTitle());
	}

	@Test
	public void testMoveParentPageToTrash() throws Exception {
		RelatedPages relatedPages = buildRelatedPages();

		movePageToTrash(relatedPages.getPage());

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());

		Assert.assertTrue(page.isInTrashExplicitly());
		Assert.assertTrue(childPage.isInTrashImplicitly());
		Assert.assertEquals(page.getTitle(), childPage.getParentTitle());
	}

	@Test
	public void
			testMoveParentPageWithRedirectAndChildPageAndGrandchildPageToTrash()
		throws Exception {

		int initialBaseModelsCount =
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node);
		int initialTrashEntriesCount =
			TrashEntryLocalServiceUtil.getEntriesCount(_group.getGroupId());

		RelatedPages relatedPages = buildRelatedPages();

		movePageToTrash(relatedPages.getRedirectPage());

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());
		WikiPage grandchildPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getGrandchildPageResourcePrimKey());
		WikiPage redirectPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getRedirectPageResourcePrimKey());

		Assert.assertFalse(childPage.isInTrash());
		Assert.assertFalse(page.isInTrash());
		Assert.assertFalse(grandchildPage.isInTrash());
		Assert.assertTrue(redirectPage.isInTrashExplicitly());
		Assert.assertEquals(
			childPage.getTitle(), grandchildPage.getParentTitle());
		Assert.assertEquals(page.getTitle(), childPage.getParentTitle());
		Assert.assertEquals(page.getTitle(), redirectPage.getRedirectTitle());
		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			TrashEntryLocalServiceUtil.getEntriesCount(_group.getGroupId()));
		Assert.assertEquals(
			initialBaseModelsCount + 4,
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node));
	}

	@Test
	public void testMoveParentPageWithRedirectPageToTrash() throws Exception {
		RelatedPages relatedPages = buildRelatedPages();

		movePageToTrash(relatedPages.getPage());

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());
		WikiPage redirectPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getRedirectPageResourcePrimKey());

		Assert.assertTrue(page.isInTrashExplicitly());
		Assert.assertTrue(childPage.isInTrashImplicitly());
		Assert.assertTrue(redirectPage.isInTrashImplicitly());
		Assert.assertEquals(page.getTitle(), childPage.getParentTitle());
		Assert.assertEquals(page.getTitle(), redirectPage.getRedirectTitle());
	}

	@Test
	public void
			testRestoreExplicitlyTrashedChildPageAndParentPageWithRedirectPageFromTrash()
		throws Exception {

		RelatedPages relatedPages = buildRelatedPages();

		movePageToTrash(relatedPages.getChildPage());
		movePageToTrash(relatedPages.getPage());

		restoreFromTrash(relatedPages.getPage());
		restoreFromTrash(relatedPages.getChildPage());

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());

		Assert.assertFalse(childPage.isInTrash());
		Assert.assertEquals(page.getTitle(), childPage.getParentTitle());
	}

	@Test
	public void testRestoreExplicitlyTrashedChildPageWithChildPageFromTrash()
		throws Exception {

		int initialBaseModelsCount =
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node);
		int initialTrashEntriesCount =
			TrashEntryLocalServiceUtil.getEntriesCount(_group.getGroupId());

		RelatedPages relatedPages = buildRelatedPages();

		movePageToTrash(relatedPages.getChildPage());

		restoreFromTrash(relatedPages.getChildPage());

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());
		WikiPage grandchildPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getGrandchildPageResourcePrimKey());

		Assert.assertFalse(page.isInTrash());
		Assert.assertFalse(childPage.isInTrash());
		Assert.assertFalse(grandchildPage.isInTrash());
		Assert.assertEquals(page.getTitle(), childPage.getParentTitle());
		Assert.assertEquals(
			childPage.getTitle(), grandchildPage.getParentTitle());
		Assert.assertEquals(
			initialTrashEntriesCount,
			TrashEntryLocalServiceUtil.getEntriesCount(_group.getGroupId()));
		Assert.assertEquals(
			initialBaseModelsCount + 5,
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node));
	}

	@Test
	public void
			testRestoreExplicitlyTrashedChildPageWithTrashedParentFromTrash()
		throws Exception {

		int initialBaseModelsCount =
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node);
		int initialTrashEntriesCount =
			TrashEntryLocalServiceUtil.getEntriesCount(_group.getGroupId());

		RelatedPages relatedPages = buildRelatedPages();

		movePageToTrash(relatedPages.getChildPage());
		movePageToTrash(relatedPages.getPage());

		restoreFromTrash(relatedPages.getChildPage());

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());
		WikiPage grandchildPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getGrandchildPageResourcePrimKey());

		Assert.assertTrue(page.isInTrashExplicitly());
		Assert.assertFalse(childPage.isInTrash());
		Assert.assertFalse(grandchildPage.isInTrash());
		Assert.assertEquals(StringPool.BLANK, childPage.getParentTitle());
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
	public void testRestoreExplicitlyTrashedPageWithRedirectPageFromTrash()
		throws Exception {

		RelatedPages relatedPages = buildRelatedPages();

		movePageToTrash(relatedPages.getRedirectPage());
		movePageToTrash(relatedPages.getPage());

		restoreFromTrash(relatedPages.getPage());

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage redirectPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getRedirectPageResourcePrimKey());

		Assert.assertFalse(page.isInTrash());
		Assert.assertTrue(redirectPage.isInTrashExplicitly());
		Assert.assertEquals(page.getTitle(), redirectPage.getRedirectTitle());
	}

	@Test
	public void
			testRestoreExplicitlyTrashedParentPageAndChildPageAndRedirectPageFromTrash()
		throws Exception {

		RelatedPages relatedPages = buildRelatedPages();

		movePageToTrash(relatedPages.getChildPage());
		movePageToTrash(relatedPages.getRedirectPage());
		movePageToTrash(relatedPages.getPage());

		restoreFromTrash(relatedPages.getPage());
		restoreFromTrash(relatedPages.getChildPage());
		restoreFromTrash(relatedPages.getRedirectPage());

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());
		WikiPage redirectPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getRedirectPageResourcePrimKey());

		Assert.assertFalse(childPage.isInTrash());
		Assert.assertEquals(page.getTitle(), childPage.getParentTitle());
		Assert.assertEquals(page.getTitle(), redirectPage.getRedirectTitle());
	}

	@Test
	public void testRestoreExplicitlyTrashedParentPageAndChildPageFromTrash()
		throws Exception {

		RelatedPages relatedPages = buildRelatedPages();

		movePageToTrash(relatedPages.getChildPage());
		movePageToTrash(relatedPages.getPage());

		restoreFromTrash(relatedPages.getPage());
		restoreFromTrash(relatedPages.getChildPage());

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());

		Assert.assertFalse(childPage.isInTrash());
		Assert.assertEquals(page.getTitle(), childPage.getParentTitle());
	}

	@Test
	public void testRestoreExplicitlyTrashedParentPageAndRedirectFromTrash()
		throws Exception {

		RelatedPages relatedPages = buildRelatedPages();

		movePageToTrash(relatedPages.getRedirectPage());
		movePageToTrash(relatedPages.getPage());

		restoreFromTrash(relatedPages.getPage());
		restoreFromTrash(relatedPages.getRedirectPage());

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage redirectPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getRedirectPageResourcePrimKey());

		Assert.assertFalse(redirectPage.isInTrash());
		Assert.assertEquals(page.getTitle(), redirectPage.getRedirectTitle());
	}

	@Test
	public void testRestoreExplicitlyTrashedParentPageFromTrash()
		throws Exception {

		RelatedPages relatedPages = buildRelatedPages();

		movePageToTrash(relatedPages.getPage());

		restoreFromTrash(relatedPages.getPage());

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());

		Assert.assertFalse(childPage.isInTrash());
		Assert.assertFalse(page.isInTrash());
		Assert.assertEquals(page.getTitle(), childPage.getParentTitle());
	}

	@Test
	public void
			testRestoreExplicitlyTrashedParentPageWitExplicitlyTrashedChildPageFromTrash()
		throws Exception {

		RelatedPages relatedPages = buildRelatedPages();

		movePageToTrash(relatedPages.getChildPage());
		movePageToTrash(relatedPages.getPage());

		restoreFromTrash(relatedPages.getPage());

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());

		Assert.assertFalse(page.isInTrash());
		Assert.assertTrue(childPage.isInTrashExplicitly());
		Assert.assertEquals(page.getTitle(), childPage.getParentTitle());
	}

	@Test
	public void
			testRestoreExplicitlyTrashedParentPageWithChildPageAndgrandchildPageFromTrash()
		throws Exception {

		int initialBaseModelsCount =
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node);
		int initialTrashEntriesCount =
			TrashEntryLocalServiceUtil.getEntriesCount(_group.getGroupId());

		RelatedPages relatedPages = buildRelatedPages();

		movePageToTrash(relatedPages.getPage());

		restoreFromTrash(relatedPages.getPage());

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());
		WikiPage grandchildPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getGrandchildPageResourcePrimKey());

		Assert.assertFalse(page.isInTrash());
		Assert.assertFalse(childPage.isInTrash());
		Assert.assertFalse(grandchildPage.isInTrash());
		Assert.assertEquals(
			childPage.getTitle(), grandchildPage.getParentTitle());
		Assert.assertEquals(page.getTitle(), childPage.getParentTitle());
		Assert.assertEquals(
			initialTrashEntriesCount,
			TrashEntryLocalServiceUtil.getEntriesCount(_group.getGroupId()));
		Assert.assertEquals(
			initialBaseModelsCount + 5,
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node));
	}

	@Test
	public void
			testRestoreExplicitlyTrashedParentPageWithRedirectPageFromTrash()
		throws Exception {

		RelatedPages relatedPages = buildRelatedPages();

		movePageToTrash(relatedPages.getRedirectPage());
		movePageToTrash(relatedPages.getPage());

		restoreFromTrash(relatedPages.getPage());

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());
		WikiPage redirectPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getRedirectPageResourcePrimKey());

		Assert.assertFalse(page.isInTrash());
		Assert.assertFalse(childPage.isInTrash());
		Assert.assertTrue(redirectPage.isInTrashExplicitly());
		Assert.assertEquals(page.getTitle(), childPage.getParentTitle());
		Assert.assertEquals(page.getTitle(), redirectPage.getRedirectTitle());

		restoreFromTrash(relatedPages.getRedirectPage());

		redirectPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getRedirectPageResourcePrimKey());

		Assert.assertFalse(redirectPage.isInTrash());
		Assert.assertEquals(page.getTitle(), redirectPage.getRedirectTitle());
	}

	@Test
	public void testRestoreExplicitlyTrashedParentPageWithRedirectPageToTrash()
		throws Exception {

		RelatedPages relatedPages = buildRelatedPages();

		movePageToTrash(relatedPages.getChildPage());
		movePageToTrash(relatedPages.getRedirectPage());
		movePageToTrash(relatedPages.getPage());

		restoreFromTrash(relatedPages.getPage());

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());
		WikiPage redirectPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getRedirectPageResourcePrimKey());

		Assert.assertFalse(page.isInTrash());
		Assert.assertTrue(childPage.isInTrashExplicitly());
		Assert.assertTrue(redirectPage.isInTrashExplicitly());
		Assert.assertEquals(page.getTitle(), childPage.getParentTitle());
		Assert.assertEquals(page.getTitle(), redirectPage.getRedirectTitle());
	}

	@Test
	public void
			testRestoreExplicitlyTrashedRedirectPageWithRestoredPageFromTrash()
		throws Exception {

		RelatedPages relatedPages = buildRelatedPages();

		movePageToTrash(relatedPages.getRedirectPage());
		movePageToTrash(relatedPages.getPage());

		restoreFromTrash(relatedPages.getPage());
		restoreFromTrash(relatedPages.getRedirectPage());

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage redirectPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getRedirectPageResourcePrimKey());

		Assert.assertFalse(redirectPage.isInTrash());
		Assert.assertEquals(page.getTitle(), redirectPage.getRedirectTitle());
	}

	@Test
	public void testRestorePageToADifferentNode() throws Exception {
		RelatedPages relatedPages = buildRelatedPages();

		WikiPageTrashHandlerTestUtil.moveParentBaseModelToTrash(
			_node.getNodeId());

		WikiNode newNode = WikiTestUtil.addNode(_group.getGroupId());

		moveTrashEntry(
			relatedPages.getChildPageResourcePrimKey(), newNode.getNodeId());

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());
		WikiPage redirectPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getRedirectPageResourcePrimKey());

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
	public void testRestorePageToADifferentNodeAndParent() throws Exception {
		RelatedPages relatedPages = buildRelatedPages();

		WikiPageTrashHandlerTestUtil.moveParentBaseModelToTrash(
			_node.getNodeId());

		WikiNode newNode = WikiTestUtil.addNode(_group.getGroupId());

		WikiPage newParentPage = WikiTestUtil.addPage(
			_group.getGroupId(), newNode.getNodeId(), true);

		moveTrashEntry(
			relatedPages.getChildPageResourcePrimKey(),
			newParentPage.getResourcePrimKey());

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());
		WikiPage redirectPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getRedirectPageResourcePrimKey());

		Assert.assertEquals(_node.getNodeId(), page.getNodeId());
		Assert.assertEquals(newNode.getNodeId(), childPage.getNodeId());
		Assert.assertEquals(_node.getNodeId(), redirectPage.getNodeId());
		Assert.assertEquals(
			newParentPage.getTitle(), childPage.getParentTitle());
	}

	@Test
	public void testRestorePageWithParentPageInTrash() throws Exception {
		RelatedPages relatedPages = buildRelatedPages();

		movePageToTrash(relatedPages.getParentPage());

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());

		WikiPage newParentPage = WikiTestUtil.addPage(
			_group.getGroupId(), _node.getNodeId(), true);

		movePage(page, newParentPage);

		page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());

		Assert.assertFalse(page.isInTrash());
		Assert.assertEquals(newParentPage.getTitle(), page.getParentTitle());
	}

	@Test
	public void
			testRestoreParentPageWithExplicitlyTrashedRedirectPageFromTrash()
		throws Exception {

		RelatedPages relatedPages = buildRelatedPages();

		movePageToTrash(relatedPages.getChildPage());
		movePageToTrash(relatedPages.getPage());

		restoreFromTrash(relatedPages.getPage());

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());
		WikiPage redirectPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getRedirectPageResourcePrimKey());

		Assert.assertFalse(page.isInTrash());
		Assert.assertTrue(childPage.isInTrashExplicitly());
		Assert.assertFalse(redirectPage.isInTrash());
		Assert.assertEquals(page.getTitle(), childPage.getParentTitle());
		Assert.assertEquals(page.getTitle(), redirectPage.getRedirectTitle());
	}

	@Test
	public void testRestoreRedirectPageWithParentPageFromTrash()
		throws Exception {

		int initialBaseModelsCount =
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node);
		int initialTrashEntriesCount =
			TrashEntryLocalServiceUtil.getEntriesCount(_group.getGroupId());

		RelatedPages relatedPages = buildRelatedPages();

		movePageToTrash(relatedPages.getRedirectPage());

		restoreFromTrash(relatedPages.getRedirectPage());

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());
		WikiPage grandchildPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getGrandchildPageResourcePrimKey());
		WikiPage redirectPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getRedirectPageResourcePrimKey());

		Assert.assertFalse(page.isInTrash());
		Assert.assertFalse(childPage.isInTrash());
		Assert.assertFalse(grandchildPage.isInTrash());
		Assert.assertFalse(redirectPage.isInTrashExplicitly());
		Assert.assertEquals(page.getTitle(), childPage.getParentTitle());
		Assert.assertEquals(
			childPage.getTitle(), grandchildPage.getParentTitle());
		Assert.assertEquals(page.getTitle(), redirectPage.getRedirectTitle());
		Assert.assertEquals(
			initialTrashEntriesCount,
			TrashEntryLocalServiceUtil.getEntriesCount(_group.getGroupId()));
		Assert.assertEquals(
			initialBaseModelsCount + 5,
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node));
	}

	@Test
	public void testTrashVersionCreationWhenMovingToTrash() throws Exception {
		int initialTrashVersionsCount =
			TrashVersionLocalServiceUtil.getTrashVersionsCount();

		RelatedPages relatedPages = buildRelatedPages();

		movePageToTrash(relatedPages.getPage());

		Assert.assertEquals(
			initialTrashVersionsCount + 4,
			TrashVersionLocalServiceUtil.getTrashVersionsCount());
	}

	@Test
	public void testTrashVersionDeletionWhenRestoringFromTrash()
		throws Exception {

		int initialTrashVersionCount =
			TrashVersionLocalServiceUtil.getTrashVersionsCount();

		RelatedPages relatedPages = buildRelatedPages();

		WikiPage page = relatedPages.getPage();

		page = WikiPageLocalServiceUtil.movePageToTrash(
			TestPropsValues.getUserId(), _node.getNodeId(), page.getTitle());

		restoreFromTrash(page);

		Assert.assertEquals(
			initialTrashVersionCount,
			TrashVersionLocalServiceUtil.getTrashVersionsCount());
	}

	protected RelatedPages buildRelatedPages() throws Exception {
		WikiPage parentPage = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _group.getGroupId(), _node.getNodeId(),
			RandomTestUtil.randomString(), true);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		WikiPage page = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _node.getNodeId(), "InitialPageName",
			RandomTestUtil.randomString(), parentPage.getTitle(), true,
			serviceContext);

		WikiPage childPage = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _node.getNodeId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			page.getTitle(), true, serviceContext);

		WikiPage grandChildPage = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _node.getNodeId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			childPage.getTitle(), true, serviceContext);

		WikiPageLocalServiceUtil.renamePage(
			TestPropsValues.getUserId(), _node.getNodeId(), "InitialPageName",
			"RenamedPage", serviceContext);

		page = WikiPageLocalServiceUtil.getPage(
			_node.getNodeId(), "RenamedPage");

		WikiPage redirectPage = WikiPageLocalServiceUtil.getPage(
			_node.getNodeId(), "InitialPageName");

		return new RelatedPages(
			parentPage, page, childPage, grandChildPage, redirectPage);
	}

	protected void movePage(WikiPage trashedPage, WikiPage newParentPage)
		throws PortalException {

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			WikiPage.class.getName());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		trashHandler.moveEntry(
			TestPropsValues.getUserId(), trashedPage.getResourcePrimKey(),
			newParentPage.getResourcePrimKey(), serviceContext);
	}

	protected void movePageToTrash(WikiPage page) throws PortalException {
		WikiPageLocalServiceUtil.movePageToTrash(
			TestPropsValues.getUserId(), _node.getNodeId(), page.getTitle());
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

	protected void restoreFromTrash(WikiPage page) throws Exception {
		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			WikiPage.class.getName());

		trashHandler.restoreTrashEntry(
			TestPropsValues.getUserId(), page.getResourcePrimKey());
	}

	@DeleteAfterTestRun
	private Group _group;

	private WikiNode _node;

	private class RelatedPages {

		public RelatedPages(
			WikiPage parentPage, WikiPage page, WikiPage childPage,
			WikiPage grandchildPage, WikiPage redirectPage) {

			_parentPage = parentPage;
			_page = page;
			_grandchildPage = grandchildPage;
			_childPage = childPage;
			_redirectPage = redirectPage;
		}

		public WikiPage getChildPage() {
			return _childPage;
		}

		public long getChildPageResourcePrimKey() {
			return _childPage.getResourcePrimKey();
		}

		public long getGrandchildPageResourcePrimKey() {
			return _grandchildPage.getResourcePrimKey();
		}

		public WikiPage getPage() {
			return _page;
		}

		public long getPageResourcePrimKey() {
			return _page.getResourcePrimKey();
		}

		public WikiPage getParentPage() {
			return _parentPage;
		}

		public WikiPage getRedirectPage() {
			return _redirectPage;
		}

		public long getRedirectPageResourcePrimKey() {
			return _redirectPage.getResourcePrimKey();
		}

		private final WikiPage _childPage;
		private final WikiPage _grandchildPage;
		private final WikiPage _page;
		private final WikiPage _parentPage;
		private final WikiPage _redirectPage;

	}

}