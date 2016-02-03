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

package com.liferay.wiki.trash.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.trash.kernel.service.TrashEntryLocalServiceUtil;
import com.liferay.trash.kernel.service.TrashVersionLocalServiceUtil;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.model.WikiPageResource;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.wiki.service.WikiPageResourceLocalServiceUtil;
import com.liferay.wiki.util.test.WikiPageTrashHandlerTestUtil;
import com.liferay.wiki.util.test.WikiTestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Roberto Díaz
 */
@RunWith(Arquillian.class)
@Sync
public class WikiPageDependentsTrashHandlerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		ServiceTestUtil.setUser(TestPropsValues.getUser());

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
			testMoveExplicitlyChildPageAndParentPageWithRedirectorPageToTrash()
		throws Exception {

		RelatedPages relatedPages = buildRelatedPages();

		movePageToTrash(relatedPages.getChildPage());
		movePageToTrash(relatedPages.getRedirectorPage());
		movePageToTrash(relatedPages.getPage());

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());
		WikiPage redirectorPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getRedirectorPageResourcePrimKey());

		Assert.assertTrue(page.isInTrashExplicitly());
		Assert.assertTrue(childPage.isInTrashExplicitly());
		Assert.assertTrue(redirectorPage.isInTrashExplicitly());
		Assert.assertEquals(page.getTitle(), childPage.getParentTitle());
		Assert.assertEquals(page.getTitle(), redirectorPage.getRedirectTitle());
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
	public void testMoveExplicitlyPageAndRedirectorPageToTrash()
		throws Exception {

		RelatedPages relatedPages = buildRelatedPages();

		movePageToTrash(relatedPages.getRedirectorPage());
		movePageToTrash(relatedPages.getPage());

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage redirectorPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getRedirectorPageResourcePrimKey());

		Assert.assertTrue(page.isInTrashExplicitly());
		Assert.assertTrue(redirectorPage.isInTrashExplicitly());
		Assert.assertEquals(page.getTitle(), redirectorPage.getRedirectTitle());
	}

	@Test
	public void testMoveExplicitlyParentPageAndChildPageAndRedirectorPageToTrash()
		throws Exception {

		RelatedPages relatedPages = buildRelatedPages();

		movePageToTrash(relatedPages.getChildPage());
		movePageToTrash(relatedPages.getPage());

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());
		WikiPage redirectorPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getRedirectorPageResourcePrimKey());

		Assert.assertTrue(page.isInTrashExplicitly());
		Assert.assertTrue(childPage.isInTrashExplicitly());
		Assert.assertTrue(redirectorPage.isInTrashImplicitly());
		Assert.assertEquals(page.getTitle(), childPage.getParentTitle());
		Assert.assertEquals(page.getTitle(), redirectorPage.getRedirectTitle());
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
	public void testMoveExplicitlyParentPageAndRedirectorPageToTrash()
		throws Exception {

		RelatedPages relatedPages = buildRelatedPages();

		movePageToTrash(relatedPages.getRedirectorPage());
		movePageToTrash(relatedPages.getPage());

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());
		WikiPage redirectorPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getRedirectorPageResourcePrimKey());

		Assert.assertTrue(childPage.isInTrashImplicitly());
		Assert.assertTrue(redirectorPage.isInTrashExplicitly());
		Assert.assertTrue(page.isInTrashExplicitly());
		Assert.assertEquals(page.getTitle(), childPage.getParentTitle());
		Assert.assertEquals(page.getTitle(), redirectorPage.getRedirectTitle());
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

		WikiPage newParentPage = WikiTestUtil.addPage(
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
			initialBaseModelsCount + 5,
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node));
		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			TrashEntryLocalServiceUtil.getEntriesCount(_group.getGroupId()));
	}

	@Test
	public void testMovePageWithRedirectorPageToTrash() throws Exception {
		RelatedPages relatedPages = buildRelatedPages();

		movePageToTrash(relatedPages.getPage());

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage redirectorPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getRedirectorPageResourcePrimKey());

		Assert.assertTrue(page.isInTrashExplicitly());
		Assert.assertTrue(redirectorPage.isInTrashImplicitly());
		Assert.assertEquals(page.getTitle(), redirectorPage.getRedirectTitle());
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
	public void testMoveParentPageWithRedirectorPageToTrash() throws Exception {
		RelatedPages relatedPages = buildRelatedPages();

		movePageToTrash(relatedPages.getPage());

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());
		WikiPage redirectorPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getRedirectorPageResourcePrimKey());
		WikiPage childRedirectorPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildRedirectorPageResourcePrimKey());

		Assert.assertTrue(page.isInTrashExplicitly());
		Assert.assertTrue(childPage.isInTrashImplicitly());
		Assert.assertTrue(redirectorPage.isInTrashImplicitly());
		Assert.assertTrue(childRedirectorPage.isInTrashImplicitly());
		Assert.assertEquals(page.getTitle(), childPage.getParentTitle());
		Assert.assertEquals(page.getTitle(), childPage.getParentTitle());
		Assert.assertEquals(
			page.getTitle(), childRedirectorPage.getParentTitle());
		Assert.assertEquals(
			childPage.getTitle(), childRedirectorPage.getRedirectTitle());
	}

	@Test
	public void testMoveRedirectorPageToTrash() throws Exception {
		int initialBaseModelsCount =
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node);
		int initialTrashEntriesCount =
			TrashEntryLocalServiceUtil.getEntriesCount(_group.getGroupId());

		RelatedPages relatedPages = buildRelatedPages();

		movePageToTrash(relatedPages.getRedirectorPage());

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());
		WikiPage grandchildPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getGrandchildPageResourcePrimKey());
		WikiPage redirectorPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getRedirectorPageResourcePrimKey());

		Assert.assertFalse(childPage.isInTrash());
		Assert.assertFalse(page.isInTrash());
		Assert.assertFalse(grandchildPage.isInTrash());
		Assert.assertTrue(redirectorPage.isInTrashExplicitly());
		Assert.assertEquals(
			childPage.getTitle(), grandchildPage.getParentTitle());
		Assert.assertEquals(page.getTitle(), childPage.getParentTitle());
		Assert.assertEquals(page.getTitle(), redirectorPage.getRedirectTitle());
		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			TrashEntryLocalServiceUtil.getEntriesCount(_group.getGroupId()));
		Assert.assertEquals(
			initialBaseModelsCount + 5,
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node));
	}

	@Test
	public void
			testRestoreExplicitlyTrashedChildPageAndParentPageWithRedirectorPageFromTrash()
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
			initialBaseModelsCount + 6,
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
			initialBaseModelsCount + 4,
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node));
		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			TrashEntryLocalServiceUtil.getEntriesCount(_group.getGroupId()));
	}

	@Test
	public void testRestoreExplicitlyTrashedPageWithRedirectorPageFromTrash()
		throws Exception {

		RelatedPages relatedPages = buildRelatedPages();

		movePageToTrash(relatedPages.getRedirectorPage());
		movePageToTrash(relatedPages.getPage());

		restoreFromTrash(relatedPages.getPage());

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage redirectorPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getRedirectorPageResourcePrimKey());

		Assert.assertFalse(page.isInTrash());
		Assert.assertTrue(redirectorPage.isInTrashExplicitly());
		Assert.assertEquals(page.getTitle(), redirectorPage.getRedirectTitle());
	}

	@Test
	public void
			testRestoreExplicitlyTrashedParentPageAndChildPageAndRedirectorPageFromTrash()
		throws Exception {

		RelatedPages relatedPages = buildRelatedPages();

		movePageToTrash(relatedPages.getChildPage());
		movePageToTrash(relatedPages.getRedirectorPage());
		movePageToTrash(relatedPages.getPage());

		restoreFromTrash(relatedPages.getPage());
		restoreFromTrash(relatedPages.getChildPage());
		restoreFromTrash(relatedPages.getRedirectorPage());

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());
		WikiPage redirectorPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getRedirectorPageResourcePrimKey());

		Assert.assertFalse(childPage.isInTrash());
		Assert.assertEquals(page.getTitle(), childPage.getParentTitle());
		Assert.assertEquals(page.getTitle(), redirectorPage.getRedirectTitle());
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
	public void testRestoreExplicitlyTrashedParentPageAndRedirectorFromTrash()
		throws Exception {

		RelatedPages relatedPages = buildRelatedPages();

		movePageToTrash(relatedPages.getRedirectorPage());
		movePageToTrash(relatedPages.getPage());

		restoreFromTrash(relatedPages.getPage());
		restoreFromTrash(relatedPages.getRedirectorPage());

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage redirectorPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getRedirectorPageResourcePrimKey());

		Assert.assertFalse(redirectorPage.isInTrash());
		Assert.assertEquals(page.getTitle(), redirectorPage.getRedirectTitle());
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
			initialBaseModelsCount + 6,
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node));
	}

	@Test
	public void
			testRestoreExplicitlyTrashedParentPageWithRedirectorPageFromTrash()
		throws Exception {

		RelatedPages relatedPages = buildRelatedPages();

		movePageToTrash(relatedPages.getRedirectorPage());
		movePageToTrash(relatedPages.getPage());

		restoreFromTrash(relatedPages.getPage());

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());
		WikiPage redirectorPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getRedirectorPageResourcePrimKey());

		Assert.assertFalse(page.isInTrash());
		Assert.assertFalse(childPage.isInTrash());
		Assert.assertTrue(redirectorPage.isInTrashExplicitly());
		Assert.assertEquals(page.getTitle(), childPage.getParentTitle());
		Assert.assertEquals(page.getTitle(), redirectorPage.getRedirectTitle());

		restoreFromTrash(relatedPages.getRedirectorPage());

		redirectorPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getRedirectorPageResourcePrimKey());

		Assert.assertFalse(redirectorPage.isInTrash());
		Assert.assertEquals(page.getTitle(), redirectorPage.getRedirectTitle());
	}

	@Test
	public void testRestoreExplicitlyTrashedParentPageWithRedirectorPageToTrash()
		throws Exception {

		RelatedPages relatedPages = buildRelatedPages();

		movePageToTrash(relatedPages.getChildPage());
		movePageToTrash(relatedPages.getRedirectorPage());
		movePageToTrash(relatedPages.getPage());

		restoreFromTrash(relatedPages.getPage());

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());
		WikiPage redirectorPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getRedirectorPageResourcePrimKey());

		Assert.assertFalse(page.isInTrash());
		Assert.assertTrue(childPage.isInTrashExplicitly());
		Assert.assertTrue(redirectorPage.isInTrashExplicitly());
		Assert.assertEquals(page.getTitle(), childPage.getParentTitle());
		Assert.assertEquals(page.getTitle(), redirectorPage.getRedirectTitle());
	}

	@Test
	public void
			testRestoreExplicitlyTrashedRedirectorPageWithRestoredPageFromTrash()
		throws Exception {

		RelatedPages relatedPages = buildRelatedPages();

		movePageToTrash(relatedPages.getRedirectorPage());
		movePageToTrash(relatedPages.getPage());

		restoreFromTrash(relatedPages.getPage());
		restoreFromTrash(relatedPages.getRedirectorPage());

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage redirectorPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getRedirectorPageResourcePrimKey());

		Assert.assertFalse(redirectorPage.isInTrash());
		Assert.assertEquals(page.getTitle(), redirectorPage.getRedirectTitle());
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
		WikiPage redirectorPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getRedirectorPageResourcePrimKey());

		Assert.assertEquals(_node.getNodeId(), page.getNodeId());
		Assert.assertEquals(newNode.getNodeId(), childPage.getNodeId());
		Assert.assertEquals(_node.getNodeId(), redirectorPage.getNodeId());
		Assert.assertNull(childPage.getParentPage());

		WikiPageResource pageResource =
			WikiPageResourceLocalServiceUtil.getWikiPageResource(
				page.getResourcePrimKey());
		WikiPageResource childPageResource =
			WikiPageResourceLocalServiceUtil.getWikiPageResource(
				childPage.getResourcePrimKey());
		WikiPageResource redirectorPageResource =
			WikiPageResourceLocalServiceUtil.getWikiPageResource(
				redirectorPage.getResourcePrimKey());

		Assert.assertEquals(_node.getNodeId(), pageResource.getNodeId());
		Assert.assertEquals(newNode.getNodeId(), childPageResource.getNodeId());
		Assert.assertEquals(
			_node.getNodeId(), redirectorPageResource.getNodeId());
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
		WikiPage redirectorPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getRedirectorPageResourcePrimKey());

		Assert.assertEquals(_node.getNodeId(), page.getNodeId());
		Assert.assertEquals(newNode.getNodeId(), childPage.getNodeId());
		Assert.assertEquals(_node.getNodeId(), redirectorPage.getNodeId());
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
			testRestoreParentPageWithExplicitlyTrashedRedirectorPageFromTrash()
		throws Exception {

		RelatedPages relatedPages = buildRelatedPages();

		movePageToTrash(relatedPages.getChildPage());
		movePageToTrash(relatedPages.getPage());

		restoreFromTrash(relatedPages.getPage());

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());
		WikiPage redirectorPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getRedirectorPageResourcePrimKey());

		Assert.assertFalse(page.isInTrash());
		Assert.assertTrue(childPage.isInTrashExplicitly());
		Assert.assertFalse(redirectorPage.isInTrash());
		Assert.assertEquals(page.getTitle(), childPage.getParentTitle());
		Assert.assertEquals(page.getTitle(), redirectorPage.getRedirectTitle());
	}

	@Test
	public void testRestoreRedirectorPageWithParentPageFromTrash()
		throws Exception {

		int initialBaseModelsCount =
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node);
		int initialTrashEntriesCount =
			TrashEntryLocalServiceUtil.getEntriesCount(_group.getGroupId());

		RelatedPages relatedPages = buildRelatedPages();

		movePageToTrash(relatedPages.getRedirectorPage());

		restoreFromTrash(relatedPages.getRedirectorPage());

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			relatedPages.getPageResourcePrimKey());
		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getChildPageResourcePrimKey());
		WikiPage grandchildPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getGrandchildPageResourcePrimKey());
		WikiPage redirectorPage = WikiPageLocalServiceUtil.getPage(
			relatedPages.getRedirectorPageResourcePrimKey());

		Assert.assertFalse(page.isInTrash());
		Assert.assertFalse(childPage.isInTrash());
		Assert.assertFalse(grandchildPage.isInTrash());
		Assert.assertFalse(redirectorPage.isInTrashExplicitly());
		Assert.assertEquals(page.getTitle(), childPage.getParentTitle());
		Assert.assertEquals(
			childPage.getTitle(), grandchildPage.getParentTitle());
		Assert.assertEquals(page.getTitle(), redirectorPage.getRedirectTitle());
		Assert.assertEquals(
			initialTrashEntriesCount,
			TrashEntryLocalServiceUtil.getEntriesCount(_group.getGroupId()));
		Assert.assertEquals(
			initialBaseModelsCount + 6,
			WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(_node));
	}

	@Test
	public void testTrashVersionCreationWhenMovingToTrash() throws Exception {
		int initialTrashVersionsCount =
			TrashVersionLocalServiceUtil.getTrashVersionsCount();

		RelatedPages relatedPages = buildRelatedPages();

		movePageToTrash(relatedPages.getPage());

		Assert.assertEquals(
			initialTrashVersionsCount + 5,
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
		WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _group.getGroupId(), _node.getNodeId(),
			_PARENT_PAGE_TITLE, true);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		createRedirectorAndRedirectPage(
			_PARENT_PAGE_TITLE, _REDIRECTOR_PAGE_TITLE, _PAGE_TITLE,
			serviceContext);

		createRedirectorAndRedirectPage(
			_PAGE_TITLE, _CHILD_REDIRECTOR_PAGE_TITLE, _CHILD_PAGE_TITLE,
			serviceContext);

		WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _node.getNodeId(),
			_GRANDCHILD_PAGE_TITLE, RandomTestUtil.randomString(),
			_CHILD_PAGE_TITLE, true, serviceContext);

		return new RelatedPages(
			WikiPageLocalServiceUtil.getPage(
				_node.getNodeId(), _PARENT_PAGE_TITLE),
			WikiPageLocalServiceUtil.getPage(_node.getNodeId(), _PAGE_TITLE),
			WikiPageLocalServiceUtil.getPage(
				_node.getNodeId(), _CHILD_PAGE_TITLE),
			WikiPageLocalServiceUtil.getPage(
				_node.getNodeId(), _GRANDCHILD_PAGE_TITLE),
			WikiPageLocalServiceUtil.getPage(
				_node.getNodeId(), _REDIRECTOR_PAGE_TITLE),
			WikiPageLocalServiceUtil.getPage(
				_node.getNodeId(), _CHILD_REDIRECTOR_PAGE_TITLE));
	}

	protected void createRedirectorAndRedirectPage(
			String parentPageTitle, String oldTitle, String newTitle,
			ServiceContext serviceContext)
		throws Exception {

		WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _node.getNodeId(), oldTitle,
			RandomTestUtil.randomString(), parentPageTitle, true,
			serviceContext);

		WikiPageLocalServiceUtil.renamePage(
			TestPropsValues.getUserId(), _node.getNodeId(), oldTitle, newTitle,
			serviceContext);
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

	private static final String _CHILD_PAGE_TITLE = "ChildPage";

	private static final String _CHILD_REDIRECTOR_PAGE_TITLE =
		"ChildRedirectorPage";

	private static final String _GRANDCHILD_PAGE_TITLE = "GrandchildPage";

	private static final String _PAGE_TITLE = "Page";

	private static final String _PARENT_PAGE_TITLE = "ParentPage";

	private static final String _REDIRECTOR_PAGE_TITLE = "RedirectorPage";

	@DeleteAfterTestRun
	private Group _group;

	private WikiNode _node;

	private static class RelatedPages {

		public RelatedPages(
			WikiPage parentPage, WikiPage page, WikiPage childPage,
			WikiPage grandchildPage, WikiPage redirectorPage,
			WikiPage childRedirectorPage) {

			_parentPage = parentPage;
			_page = page;
			_grandchildPage = grandchildPage;
			_childPage = childPage;
			_redirectorPage = redirectorPage;
			_childRedirectorPage = childRedirectorPage;
		}

		public WikiPage getChildPage() {
			return _childPage;
		}

		public long getChildPageResourcePrimKey() {
			return _childPage.getResourcePrimKey();
		}

		public long getChildRedirectorPageResourcePrimKey() {
			return _childRedirectorPage.getResourcePrimKey();
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

		public WikiPage getRedirectorPage() {
			return _redirectorPage;
		}

		public long getRedirectorPageResourcePrimKey() {
			return _redirectorPage.getResourcePrimKey();
		}

		private final WikiPage _childPage;
		private final WikiPage _childRedirectorPage;
		private final WikiPage _grandchildPage;
		private final WikiPage _page;
		private final WikiPage _parentPage;
		private final WikiPage _redirectorPage;

	}

}