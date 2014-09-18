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

		RelatedPages relatedPages = givenAPageWithChildPage();

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

		RelatedPages relatedPages = givenAPageWithChildAndRedirectPage();

		movePageToTrash(relatedPages.getChildPage());
		movePageToTrash(relatedPages.getRedirectPage());
		movePageToTrash(relatedPages.getPage());

		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());
		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage redirectPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getRedirectPageResourcePrimKey());

		Assert.assertTrue(childPage.isInTrashExplicitly());
		Assert.assertTrue(page.isInTrashExplicitly());
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

		RelatedPages relatedPages = givenAPageWithChildAndGrandchildPage();

		movePageToTrash(relatedPages.getChildPage());
		movePageToTrash(relatedPages.getPage());

		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());
		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage grandchildPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getGrandchildPageResourcePrimKey());

		Assert.assertTrue(childPage.isInTrashExplicitly());
		Assert.assertTrue(grandchildPage.isInTrashImplicitly());
		Assert.assertTrue(page.isInTrashExplicitly());
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

		RelatedPages relatedPages = givenAPageWithChildAndGrandchildPage();

		movePageToTrash(relatedPages.getChildPage());

		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());
		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage grandchildPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getGrandchildPageResourcePrimKey());

		Assert.assertFalse(page.isInTrash());
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

		RelatedPages relatedPages = givenAPageWithRedirectPage();

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

		RelatedPages relatedPages = givenAPageWithChildAndRedirectPage();

		movePageToTrash(relatedPages.getChildPage());
		movePageToTrash(relatedPages.getPage());

		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());
		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage redirectPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getRedirectPageResourcePrimKey());

		Assert.assertTrue(childPage.isInTrashExplicitly());
		Assert.assertTrue(page.isInTrashExplicitly());
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

		RelatedPages relatedPages = givenAPageWithChildAndGrandchildPage();

		movePageToTrash(relatedPages.getPage());

		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());
		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage grandchildPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getGrandchildPageResourcePrimKey());

		Assert.assertTrue(page.isInTrashExplicitly());
		Assert.assertTrue(childPage.isInTrashImplicitly());
		Assert.assertTrue(grandchildPage.isInTrashImplicitly());
		Assert.assertEquals(page.getTitle(), childPage.getParentTitle());
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

		RelatedPages relatedPages = givenAPageWithChildPage();

		movePageToTrash(relatedPages.getChildPage());
		movePageToTrash(relatedPages.getPage());

		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());
		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());

		Assert.assertTrue(page.isInTrashExplicitly());
		Assert.assertTrue(childPage.isInTrashExplicitly());
		Assert.assertEquals(page.getTitle(), childPage.getParentTitle());
	}

	@Test
	public void testMoveExplicitlyParentPageAndRedirectPageToTrash()
		throws Exception {

		RelatedPages relatedPages = givenAPageWithChildAndRedirectPage();

		movePageToTrash(relatedPages.getRedirectPage());
		movePageToTrash(relatedPages.getPage());

		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());
		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
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

		RelatedPages relatedPages = givenAPageWithChangedParent();

		movePageToTrash(relatedPages.getInitialParentPage());

		WikiPage initialParentPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getInitialParentPageResourcePrimKey());
		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage parentPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getParentPageResourcePrimKey());

		Assert.assertFalse(page.isInTrash());
		Assert.assertFalse(parentPage.isInTrash());
		Assert.assertTrue(initialParentPage.isInTrashExplicitly());
		Assert.assertEquals(page.getParentTitle(), parentPage.getTitle());
		Assert.assertEquals(parentPage.getTitle(), page.getParentTitle());
		Assert.assertEquals(
			initialBaseModelsCount + 2,
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node));
		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			TrashEntryLocalServiceUtil.getEntriesCount(_group.getGroupId()));
	}

	@Test
	public void testMovePageWithRedirectPageToTrash() throws Exception {
		RelatedPages relatedPages = givenAPageWithRedirectPage();

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
		RelatedPages relatedPages = givenAPageWithChildPage();

		movePageToTrash(relatedPages.getPage());

		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());
		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());

		Assert.assertTrue(page.isInTrashExplicitly());
		Assert.assertTrue(childPage.isInTrashImplicitly());
		Assert.assertEquals(page.getTitle(), childPage.getParentTitle());
	}

	@Test
	public void
			testMoveParentPageWithRedirectAndChildPageAndgrandchildPageToTrash()
		throws Exception {

		int initialBaseModelsCount =
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node);
		int initialTrashEntriesCount =
			TrashEntryLocalServiceUtil.getEntriesCount(_group.getGroupId());

		RelatedPages relatedPages =
			givenAPageWithChildAndGrandchildAndRedirectPage();

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
			initialBaseModelsCount + 3,
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node));
	}

	@Test
	public void testMoveParentPageWithRedirectPageToTrash() throws Exception {
		RelatedPages relatedPages = givenAPageWithChildAndRedirectPage();

		movePageToTrash(relatedPages.getPage());

		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());
		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage redirectPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getRedirectPageResourcePrimKey());

		Assert.assertTrue(childPage.isInTrashImplicitly());
		Assert.assertTrue(page.isInTrashExplicitly());
		Assert.assertTrue(redirectPage.isInTrashImplicitly());
		Assert.assertEquals(page.getTitle(), childPage.getParentTitle());
		Assert.assertEquals(page.getTitle(), redirectPage.getRedirectTitle());
	}

	@Test
	public void
			testRestoreExplicitlyTrashedChildPageAndParentPageWithRedirectPageFromTrash()
		throws Exception {

		RelatedPages relatedPages = givenAPageWithChildPage();

		movePageToTrash(relatedPages.getChildPage());
		movePageToTrash(relatedPages.getPage());

		restoreFromTrash(relatedPages.getPage());
		restoreFromTrash(relatedPages.getChildPage());

		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());
		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());

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

		RelatedPages relatedPages = givenAPageWithChildAndGrandchildPage();

		movePageToTrash(relatedPages.getChildPage());

		restoreFromTrash(relatedPages.getChildPage());

		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());
		WikiPage grandchildPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getGrandchildPageResourcePrimKey());
		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());

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
			initialBaseModelsCount + 3,
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

		RelatedPages relatedPages = givenAPageWithChildAndGrandchildPage();

		movePageToTrash(relatedPages.getChildPage());
		movePageToTrash(relatedPages.getPage());

		restoreFromTrash(relatedPages.getChildPage());

		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());
		WikiPage grandchildPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getGrandchildPageResourcePrimKey());
		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());

		Assert.assertFalse(childPage.isInTrash());
		Assert.assertFalse(grandchildPage.isInTrash());
		Assert.assertTrue(page.isInTrashExplicitly());
		Assert.assertEquals(StringPool.BLANK, childPage.getParentTitle());
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

		RelatedPages relatedPages = givenAPageWithRedirectPage();

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

		RelatedPages relatedPages = givenAPageWithChildAndRedirectPage();

		movePageToTrash(relatedPages.getChildPage());
		movePageToTrash(relatedPages.getRedirectPage());
		movePageToTrash(relatedPages.getPage());

		restoreFromTrash(relatedPages.getPage());
		restoreFromTrash(relatedPages.getChildPage());
		restoreFromTrash(relatedPages.getRedirectPage());

		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());
		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage redirectPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getRedirectPageResourcePrimKey());

		Assert.assertFalse(childPage.isInTrash());
		Assert.assertEquals(page.getTitle(), childPage.getParentTitle());
		Assert.assertEquals(page.getTitle(), redirectPage.getRedirectTitle());
	}

	@Test
	public void testRestoreExplicitlyTrashedParentPageAndChildPageFromTrash()
		throws Exception {

		RelatedPages relatedPages = givenAPageWithChildPage();

		movePageToTrash(relatedPages.getChildPage());
		movePageToTrash(relatedPages.getPage());

		restoreFromTrash(relatedPages.getPage());
		restoreFromTrash(relatedPages.getChildPage());

		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());
		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());

		Assert.assertFalse(childPage.isInTrash());
		Assert.assertEquals(page.getTitle(), childPage.getParentTitle());
	}

	@Test
	public void testRestoreExplicitlyTrashedParentPageAndRedirectFromTrash()
		throws Exception {

		RelatedPages relatedPages = givenAPageWithRedirectPage();

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

		RelatedPages relatedPages = givenAPageWithChildPage();

		movePageToTrash(relatedPages.getPage());

		restoreFromTrash(relatedPages.getPage());

		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());
		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());

		Assert.assertFalse(childPage.isInTrash());
		Assert.assertFalse(page.isInTrash());
		Assert.assertEquals(page.getTitle(), childPage.getParentTitle());
	}

	@Test
	public void
			testRestoreExplicitlyTrashedParentPageWitExplicitlyTrashedChildPageFromTrash()
		throws Exception {

		RelatedPages relatedPages = givenAPageWithChildPage();

		movePageToTrash(relatedPages.getChildPage());
		movePageToTrash(relatedPages.getPage());

		restoreFromTrash(relatedPages.getPage());

		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());
		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());

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

		RelatedPages relatedPages = givenAPageWithChildAndGrandchildPage();

		movePageToTrash(relatedPages.getPage());

		restoreFromTrash(relatedPages.getPage());

		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());
		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage grandchildPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getGrandchildPageResourcePrimKey());

		Assert.assertFalse(childPage.isInTrash());
		Assert.assertFalse(grandchildPage.isInTrash());
		Assert.assertFalse(page.isInTrash());
		Assert.assertEquals(childPage.getTitle(), grandchildPage.getParentTitle());
		Assert.assertEquals(page.getTitle(), childPage.getParentTitle());
		Assert.assertEquals(
			initialTrashEntriesCount,
			TrashEntryLocalServiceUtil.getEntriesCount(_group.getGroupId()));
		Assert.assertEquals(
			initialBaseModelsCount + 3,
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node));
	}

	@Test
	public void
			testRestoreExplicitlyTrashedParentPageWithRedirectPageFromTrash()
		throws Exception {

		RelatedPages relatedPages = givenAPageWithChildAndRedirectPage();

		movePageToTrash(relatedPages.getRedirectPage());
		movePageToTrash(relatedPages.getPage());

		restoreFromTrash(relatedPages.getPage());

		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());
		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage redirectPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getRedirectPageResourcePrimKey());

		Assert.assertFalse(childPage.isInTrash());
		Assert.assertFalse(page.isInTrash());
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

		RelatedPages relatedPages = givenAPageWithChildAndRedirectPage();

		movePageToTrash(relatedPages.getChildPage());
		movePageToTrash(relatedPages.getRedirectPage());
		movePageToTrash(relatedPages.getPage());

		restoreFromTrash(relatedPages.getPage());

		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());
		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
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

		RelatedPages relatedPages = givenAPageWithRedirectPage();

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
	public void testRestorePageWithParentPageInTrash() throws Exception {
		RelatedPages relatedPages = givenAPageWithParentPage();

		movePageToTrash(relatedPages.getInitialParentPage());

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

		RelatedPages relatedPages = givenAPageWithChildAndRedirectPage();

		movePageToTrash(relatedPages.getChildPage());
		movePageToTrash(relatedPages.getPage());

		restoreFromTrash(relatedPages.getPage());

		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());
		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage redirectPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getRedirectPageResourcePrimKey());

		Assert.assertFalse(page.isInTrash());
		Assert.assertFalse(redirectPage.isInTrash());
		Assert.assertTrue(childPage.isInTrashExplicitly());
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

		RelatedPages relatedPages =
			givenAPageWithChildAndGrandchildAndRedirectPage();

		movePageToTrash(relatedPages.getRedirectPage());

		restoreFromTrash(relatedPages.getRedirectPage());

		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());
		WikiPage grandchildPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getGrandchildPageResourcePrimKey());
		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage redirectPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getRedirectPageResourcePrimKey());

		Assert.assertFalse(childPage.isInTrash());
		Assert.assertFalse(grandchildPage.isInTrash());
		Assert.assertFalse(page.isInTrash());
		Assert.assertFalse(redirectPage.isInTrashExplicitly());
		Assert.assertEquals(
			childPage.getTitle(), grandchildPage.getParentTitle());
		Assert.assertEquals(page.getTitle(), childPage.getParentTitle());
		Assert.assertEquals(page.getTitle(), redirectPage.getRedirectTitle());
		Assert.assertEquals(
			initialTrashEntriesCount,
			TrashEntryLocalServiceUtil.getEntriesCount(_group.getGroupId()));
		Assert.assertEquals(
			initialBaseModelsCount + 4,
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node));
	}

	@Test
	public void testTrashVersionCreationWhenMovingToTrash() throws Exception {
		int initialTrashVersionsCount =
			TrashVersionLocalServiceUtil.getTrashVersionsCount();

		RelatedPages relatedPages = givenAPageWithChildAndRedirectPage();

		movePageToTrash(relatedPages.getPage());

		Assert.assertEquals(
			initialTrashVersionsCount + 3,
			TrashVersionLocalServiceUtil.getTrashVersionsCount());
	}

	@Test
	public void testTrashVersionDeletionWhenRestoringFromTrash()
		throws Exception {

		int initialTrashVersionCount =
			TrashVersionLocalServiceUtil.getTrashVersionsCount();

		givenAPageWithChildAndRedirectPage();

		WikiPage page = WikiPageLocalServiceUtil.movePageToTrash(
			TestPropsValues.getUserId(), _node.getNodeId(), "RenamedPage");

		restoreFromTrash(page);

		Assert.assertEquals(
			initialTrashVersionCount,
			TrashVersionLocalServiceUtil.getTrashVersionsCount());
	}

	protected RelatedPages givenAPageWithChangedParent() throws Exception {
		RelatedPages relatedPages = new RelatedPages();

		WikiPage initialParentPage = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _group.getGroupId(), _node.getNodeId(),
			RandomTestUtil.randomString(), true);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		WikiPage page = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _node.getNodeId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			initialParentPage.getTitle(), true, serviceContext);

		WikiPage parentPage =  WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _group.getGroupId(), _node.getNodeId(),
			RandomTestUtil.randomString(), true);

		WikiPageLocalServiceUtil.changeParent(
			TestPropsValues.getUserId(), _node.getNodeId(), page.getTitle(),
			parentPage.getTitle(), serviceContext);

		relatedPages.setInitialParentPage(
			WikiPageLocalServiceUtil.getPage(
				initialParentPage.getResourcePrimKey()));
		relatedPages.setPage(
			WikiPageLocalServiceUtil.getPage(page.getResourcePrimKey()));
		relatedPages.setParentPage(
			WikiPageLocalServiceUtil.getPage(parentPage.getResourcePrimKey()));

		return relatedPages;
	}

	protected RelatedPages givenAPageWithChildAndGrandchildAndRedirectPage()
		throws Exception {

		RelatedPages relatedPages = new RelatedPages();

		WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _group.getGroupId(), _node.getNodeId(),
			"InitialNamePage", true);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		WikiPage childPage = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _node.getNodeId(), "ChildPage",
			RandomTestUtil.randomString(), "InitialNamePage", true,
			serviceContext);

		WikiPage grandchildPage = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _node.getNodeId(), "GrandChildPage",
			RandomTestUtil.randomString(), "ChildPage", true, serviceContext);

		WikiPageLocalServiceUtil.renamePage(
			TestPropsValues.getUserId(), _node.getNodeId(), "InitialNamePage",
			"RenamedPage", serviceContext);

		relatedPages.setChildPage(
			WikiPageLocalServiceUtil.getPage(childPage.getResourcePrimKey()));
		relatedPages.setGrandchildPage(
			WikiPageLocalServiceUtil.getPage(
				grandchildPage.getResourcePrimKey()));

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			_node.getNodeId(), "RenamedPage");

		relatedPages.setPage(page);

		WikiPage redirectPage = WikiPageLocalServiceUtil.getPage(
			_node.getNodeId(), "InitialNamePage");

		relatedPages.setRedirectPage(redirectPage);

		return relatedPages;
	}

	protected RelatedPages givenAPageWithChildAndGrandchildPage()
		throws Exception {

		RelatedPages relatedPages = new RelatedPages();

		WikiPage page = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _group.getGroupId(), _node.getNodeId(),
			RandomTestUtil.randomString(), true);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		WikiPage childPage = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _node.getNodeId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			page.getTitle(), true, serviceContext);

		relatedPages.setChildPage(childPage);

		relatedPages.setPage(page);

		WikiPage grandchildPage = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _node.getNodeId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			childPage.getTitle(), true, serviceContext);

		relatedPages.setGrandchildPage(grandchildPage);

		return relatedPages;
	}

	protected RelatedPages givenAPageWithChildAndRedirectPage()
		throws Exception {

		RelatedPages relatedPages = new RelatedPages();

		WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _group.getGroupId(), _node.getNodeId(),
			"InitialNamePage", true);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		WikiPageLocalServiceUtil.renamePage(
			TestPropsValues.getUserId(), _node.getNodeId(), "InitialNamePage",
			"RenamedPage", serviceContext);

		WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _node.getNodeId(), "ChildPage",
			RandomTestUtil.randomString(), "RenamedPage", true, serviceContext);

		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			_node.getNodeId(), "ChildPage");

		relatedPages.setChildPage(childPage);

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			_node.getNodeId(), "RenamedPage");

		relatedPages.setPage(page);

		WikiPage redirectPage = WikiPageLocalServiceUtil.getPage(
			_node.getNodeId(), "InitialNamePage");

		relatedPages.setRedirectPage(redirectPage);

		return relatedPages;
	}

	protected RelatedPages givenAPageWithChildPage() throws Exception {
		RelatedPages relatedPages = new RelatedPages();

		WikiPage page = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _group.getGroupId(), _node.getNodeId(),
			"Page", true);

		relatedPages.setPage(page);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		WikiPage childPage = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _node.getNodeId(), "ChildPage",
			RandomTestUtil.randomString(), "Page", true, serviceContext);

		relatedPages.setChildPage(childPage);

		return relatedPages;
	}

	protected RelatedPages givenAPageWithParentPage() throws Exception {
		RelatedPages relatedPages = new RelatedPages();

		WikiPage initialParentPage = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _group.getGroupId(), _node.getNodeId(),
			"ParentPage", true);

		relatedPages.setInitialParentPage(initialParentPage);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		WikiPage page = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _node.getNodeId(), "Page",
			RandomTestUtil.randomString(), "ParentPage", true, serviceContext);

		relatedPages.setPage(page);

		return relatedPages;
	}

	protected RelatedPages givenAPageWithRedirectPage() throws Exception {
		RelatedPages relatedPages = new RelatedPages();

		WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _group.getGroupId(), _node.getNodeId(),
			"InitialNamePage", true);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		WikiPageLocalServiceUtil.renamePage(
			TestPropsValues.getUserId(), _node.getNodeId(), "InitialNamePage",
			"RenamedPage", serviceContext);

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			_node.getNodeId(), "RenamedPage");

		relatedPages.setPage(page);

		WikiPage redirectPage = WikiPageLocalServiceUtil.getPage(
			_node.getNodeId(), "InitialNamePage");

		relatedPages.setRedirectPage(redirectPage);

		return relatedPages;
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

	protected void movePageToTrash(WikiPage page)
		throws com.liferay.portal.kernel.exception.PortalException {

		WikiPageLocalServiceUtil.movePageToTrash(
			TestPropsValues.getUserId(), _node.getNodeId(), page.getTitle());
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

		public WikiPage getChildPage() {
			return _childPage;
		}

		public long getChildPageResourcePrimKey() {
			return _childPage.getResourcePrimKey();
		}

		public long getGrandchildPageResourcePrimKey() {
			return _grandchildPage.getResourcePrimKey();
		}

		public WikiPage getInitialParentPage() {
			return _initialParentPage;
		}

		public long getInitialParentPageResourcePrimKey() {
			return _initialParentPage.getResourcePrimKey();
		}

		public WikiPage getPage() {
			return _page;
		}

		public long getPageResourcePrimKey() {
			return _page.getResourcePrimKey();
		}

		public long getParentPageResourcePrimKey() {
			return _parentPage.getResourcePrimKey();
		}

		public WikiPage getRedirectPage() {
			return _redirectPage;
		}

		public long getRedirectPageResourcePrimKey() {
			return _redirectPage.getResourcePrimKey();
		}

		public void setChildPage(WikiPage childPage) {
			_childPage = childPage;
		}

		public void setGrandchildPage(WikiPage grandchildPage) {
			_grandchildPage = grandchildPage;
		}

		public void setInitialParentPage(WikiPage initialParentPage) {
			_initialParentPage = initialParentPage;
		}

		public void setPage(WikiPage page) {
			_page = page;
		}

		public void setParentPage(WikiPage parentPage) {
			_parentPage = parentPage;
		}

		public void setRedirectPage(WikiPage redirectPage) {
			_redirectPage = redirectPage;
		}

		private WikiPage _childPage;
		private WikiPage _grandchildPage;
		private WikiPage _initialParentPage;
		private WikiPage _page;
		private WikiPage _parentPage;
		private WikiPage _redirectPage;
	}

}