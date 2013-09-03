/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
 */
@ExecutionTestListeners(listeners = {
	MainServletExecutionTestListener.class,
	SynchronousDestinationExecutionTestListener.class
})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class WikiPageTrashHandlerTest extends BaseTrashHandlerTestCase {

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

	@Test
	@Transactional
	public void testWikiPageChildren() throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		WikiNode node = (WikiNode)getParentBaseModel(group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(node);
		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		WikiPage wikiPage1 = (WikiPage)addBaseModel(node, true, serviceContext);

		WikiPage childPage = addChildBaseModel(
			node.getNodeId(), wikiPage1.getTitle(), true, serviceContext);

		Assert.assertEquals(childPage.getParentTitle(), wikiPage1.getTitle());

		WikiPage wikiPage2 = (WikiPage)addBaseModel(node, true, serviceContext);

		WikiPageLocalServiceUtil.changeParent(
			TestPropsValues.getUserId(), node.getNodeId(), childPage.getTitle(),
			wikiPage2.getTitle(), serviceContext);

		childPage = WikiPageLocalServiceUtil.getPage(
			node.getNodeId(), childPage.getTitle());

		Assert.assertEquals(childPage.getParentTitle(), wikiPage2.getTitle());

		Assert.assertEquals(
			initialBaseModelsCount + 3, getNotInTrashBaseModelsCount(node));
		Assert.assertEquals(
			initialTrashEntriesCount, getTrashEntriesCount(group.getGroupId()));

		moveBaseModelToTrash(wikiPage1.getPrimaryKey());

		Assert.assertEquals(
			initialBaseModelsCount + 2, getNotInTrashBaseModelsCount(node));
		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			getTrashEntriesCount(group.getGroupId()));
	}

	@Test
	@Transactional
	public void testWikiPageChildren1() throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		WikiNode node = (WikiNode)getParentBaseModel(group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(node);
		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		WikiPage wikiPage = (WikiPage)addBaseModel(node, true, serviceContext);

		WikiPage childPage = addChildBaseModel(
			node.getNodeId(), wikiPage.getTitle(), true, serviceContext);

		addChildBaseModel(
			node.getNodeId(), wikiPage.getTitle(), true, serviceContext);

		addChildBaseModel(
			node.getNodeId(), childPage.getTitle(), true, serviceContext);

		Assert.assertEquals(
			initialBaseModelsCount + 4, getNotInTrashBaseModelsCount(node));
		Assert.assertEquals(
			initialTrashEntriesCount, getTrashEntriesCount(group.getGroupId()));

		moveBaseModelToTrash(wikiPage.getPrimaryKey());

		Assert.assertEquals(
			initialBaseModelsCount, getNotInTrashBaseModelsCount(node));
		Assert.assertEquals(
			initialTrashEntriesCount + 4,
			getTrashEntriesCount(group.getGroupId()));

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		trashHandler.restoreTrashEntry(
			TestPropsValues.getUserId(), getTrashEntryClassPK(wikiPage));

		Assert.assertEquals(
			initialBaseModelsCount + 4, getNotInTrashBaseModelsCount(node));
		Assert.assertEquals(
			initialTrashEntriesCount, getTrashEntriesCount(group.getGroupId()));
	}

	@Test
	@Transactional
	public void testWikiPageChildren2() throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		WikiNode node = (WikiNode)getParentBaseModel(group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(node);
		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		WikiPage wikiPage = (WikiPage)addBaseModel(node, true, serviceContext);

		WikiPage childPage = addChildBaseModel(
			node.getNodeId(), wikiPage.getTitle(), true, serviceContext);

		addChildBaseModel(
			node.getNodeId(), wikiPage.getTitle(), true, serviceContext);

		addChildBaseModel(
			node.getNodeId(), childPage.getTitle(), true, serviceContext);

		Assert.assertEquals(
			initialBaseModelsCount + 4, getNotInTrashBaseModelsCount(node));
		Assert.assertEquals(
			initialTrashEntriesCount, getTrashEntriesCount(group.getGroupId()));

		moveBaseModelToTrash(childPage.getPrimaryKey());

		Assert.assertEquals(
			initialBaseModelsCount + 2, getNotInTrashBaseModelsCount(node));
		Assert.assertEquals(
			initialTrashEntriesCount + 2,
			getTrashEntriesCount(group.getGroupId()));

		moveBaseModelToTrash(wikiPage.getPrimaryKey());

		Assert.assertEquals(
			initialBaseModelsCount, getNotInTrashBaseModelsCount(node));
		Assert.assertEquals(
			initialTrashEntriesCount + 4,
			getTrashEntriesCount(group.getGroupId()));

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		trashHandler.restoreTrashEntry(
			TestPropsValues.getUserId(), getTrashEntryClassPK(wikiPage));

		Assert.assertEquals(
			initialBaseModelsCount + 2, getNotInTrashBaseModelsCount(node));
		Assert.assertEquals(
			initialTrashEntriesCount + 2,
			getTrashEntriesCount(group.getGroupId()));
	}

	@Test
	@Transactional
	public void testWikiPageChildren3() throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		WikiNode node = (WikiNode)getParentBaseModel(group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(node);
		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		WikiPage wikiPage = (WikiPage)addBaseModel(node, true, serviceContext);

		WikiPage childPage = addChildBaseModel(
			node.getNodeId(), wikiPage.getTitle(), true, serviceContext);

		addChildBaseModel(
			node.getNodeId(), wikiPage.getTitle(), true, serviceContext);

		addChildBaseModel(
			node.getNodeId(), childPage.getTitle(), true, serviceContext);

		Assert.assertEquals(
			initialBaseModelsCount + 4, getNotInTrashBaseModelsCount(node));
		Assert.assertEquals(
			initialTrashEntriesCount, getTrashEntriesCount(group.getGroupId()));

		moveBaseModelToTrash(wikiPage.getPrimaryKey());

		Assert.assertEquals(
			initialBaseModelsCount, getNotInTrashBaseModelsCount(node));
		Assert.assertEquals(
			initialTrashEntriesCount + 4,
			getTrashEntriesCount(group.getGroupId()));

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		trashHandler.restoreTrashEntry(
			TestPropsValues.getUserId(), getTrashEntryClassPK(childPage));

		Assert.assertEquals(
			initialBaseModelsCount + 2, getNotInTrashBaseModelsCount(node));
		Assert.assertEquals(
			initialTrashEntriesCount + 2,
			getTrashEntriesCount(group.getGroupId()));

		childPage = (WikiPage)getBaseModel(wikiPage.getPrimaryKey());

		Assert.assertEquals(childPage.getParentTitle(), StringPool.BLANK);
	}

	@Test
	@Transactional
	public void testWikiPageMovePage1() throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		WikiNode node = (WikiNode)getParentBaseModel(group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(node);
		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		WikiPage wikiPage = (WikiPage)addBaseModel(node, true, serviceContext);

		WikiPage childPage = addChildBaseModel(
			node.getNodeId(), wikiPage.getTitle(), true, serviceContext);

		addChildBaseModel(
			node.getNodeId(), wikiPage.getTitle(), true, serviceContext);

		addChildBaseModel(
			node.getNodeId(), childPage.getTitle(), true, serviceContext);

		Assert.assertEquals(
			initialBaseModelsCount + 4, getNotInTrashBaseModelsCount(node));
		Assert.assertEquals(
			initialTrashEntriesCount, getTrashEntriesCount(group.getGroupId()));

		String newTitle = ServiceTestUtil.randomString();
		String oldTitle = wikiPage.getTitle();

		WikiPageLocalServiceUtil.movePage(
			TestPropsValues.getUserId(), node.getNodeId(), wikiPage.getTitle(),
			newTitle, serviceContext);

		Assert.assertEquals(
			initialBaseModelsCount + 5, getNotInTrashBaseModelsCount(node));
		Assert.assertEquals(
			initialTrashEntriesCount, getTrashEntriesCount(group.getGroupId()));

		WikiPage oldWikiPage = WikiPageLocalServiceUtil.getPage(
			node.getNodeId(), oldTitle);

		moveBaseModelToTrash(oldWikiPage.getPrimaryKey());

		Assert.assertEquals(
			initialBaseModelsCount + 4, getNotInTrashBaseModelsCount(node));
		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			getTrashEntriesCount(group.getGroupId()));

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		trashHandler.restoreTrashEntry(
			TestPropsValues.getUserId(), getTrashEntryClassPK(oldWikiPage));

		Assert.assertEquals(
			initialBaseModelsCount + 5, getNotInTrashBaseModelsCount(node));
		Assert.assertEquals(
			initialTrashEntriesCount, getTrashEntriesCount(group.getGroupId()));
	}

	@Test
	@Transactional
	public void testWikiPageMovePage2() throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		WikiNode node = (WikiNode)getParentBaseModel(group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(node);
		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		WikiPage wikiPage = (WikiPage)addBaseModel(node, true, serviceContext);

		WikiPage childPage = addChildBaseModel(
			node.getNodeId(), wikiPage.getTitle(), true, serviceContext);

		addChildBaseModel(
			node.getNodeId(), wikiPage.getTitle(), true, serviceContext);

		addChildBaseModel(
			node.getNodeId(), childPage.getTitle(), true, serviceContext);

		Assert.assertEquals(
			initialBaseModelsCount + 4, getNotInTrashBaseModelsCount(node));
		Assert.assertEquals(
			initialTrashEntriesCount, getTrashEntriesCount(group.getGroupId()));

		String newTitle = ServiceTestUtil.randomString();
		String oldTitle = wikiPage.getTitle();

		WikiPageLocalServiceUtil.movePage(
			TestPropsValues.getUserId(), node.getNodeId(), wikiPage.getTitle(),
			newTitle, serviceContext);

		Assert.assertEquals(
			initialBaseModelsCount + 5, getNotInTrashBaseModelsCount(node));
		Assert.assertEquals(
			initialTrashEntriesCount, getTrashEntriesCount(group.getGroupId()));

		WikiPage oldWikiPage = WikiPageLocalServiceUtil.getPage(
			node.getNodeId(), oldTitle);

		moveBaseModelToTrash(wikiPage.getPrimaryKey());

		Assert.assertEquals(
			initialBaseModelsCount, getNotInTrashBaseModelsCount(node));
		Assert.assertEquals(
			initialTrashEntriesCount + 5,
			getTrashEntriesCount(group.getGroupId()));

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		trashHandler.restoreTrashEntry(
			TestPropsValues.getUserId(), getTrashEntryClassPK(oldWikiPage));

		Assert.assertEquals(
			initialBaseModelsCount + 1, getNotInTrashBaseModelsCount(node));
		Assert.assertEquals(
			initialTrashEntriesCount + 4,
			getTrashEntriesCount(group.getGroupId()));

		oldWikiPage = (WikiPage)getBaseModel(oldWikiPage.getPrimaryKey());

		Assert.assertEquals(oldWikiPage.getRedirectTitle(), StringPool.BLANK);
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

	protected WikiPage addChildBaseModel(
			long nodeId, String parentTitle, boolean approved,
			ServiceContext serviceContext)
		throws Exception {

		serviceContext = (ServiceContext)serviceContext.clone();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		return WikiTestUtil.addPage(
			TestPropsValues.getUserId(), nodeId, ServiceTestUtil.randomString(),
			ServiceTestUtil.randomString(), parentTitle, approved,
			serviceContext);
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

	@Override
	protected BaseModel<?> updateBaseModel(
			long primaryKey, ServiceContext serviceContext)
		throws Exception {

		WikiPage page = WikiPageLocalServiceUtil.getPageByPageId(primaryKey);

		serviceContext = (ServiceContext)serviceContext.clone();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		return WikiPageLocalServiceUtil.updatePage(
			TestPropsValues.getUserId(), page.getNodeId(), page.getTitle(),
			page.getVersion(), ServiceTestUtil.randomString(),
			ServiceTestUtil.randomString(), false, page.getFormat(),
			page.getParentTitle(), page.getRedirectTitle(), serviceContext);
	}

	private static final int _NODE_NAME_MAX_LENGTH = 75;

	private static final int _PAGE_TITLE_MAX_LENGTH = 255;

}