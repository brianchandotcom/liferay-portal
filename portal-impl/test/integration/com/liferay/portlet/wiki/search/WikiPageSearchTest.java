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

package com.liferay.portlet.wiki.search;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.search.BaseSearchTestCase;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.wiki.asset.WikiPageAssetRenderer;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiPageServiceUtil;
import com.liferay.portlet.wiki.util.WikiTestUtil;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class WikiPageSearchTest extends BaseSearchTestCase {

	@Ignore()
	@Override
	@Test
	public void testSearchByDDMStructureField() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testSearchByKeywordsInsideParentBaseModel() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testSearchMyEntries() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testSearchRecentEntries() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testSearchStatus() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testSearchWithinDDMStructure() throws Exception {
	}

	@Override
	protected void addAttachment(ClassedModel classedModel) throws Exception {
		WikiPage page = (WikiPage)classedModel;

		WikiTestUtil.addWikiAttachment(
			TestPropsValues.getUserId(), page.getNodeId(), page.getTitle(),
			getClass());
	}

	@Override
	protected BaseModel<?> addBaseModelWithWorkflow(
			BaseModel<?> parentBaseModel, boolean approved, String keywords,
			ServiceContext serviceContext)
		throws Exception {

		return WikiTestUtil.addPage(
			TestPropsValues.getUserId(),
			(Long)parentBaseModel.getPrimaryKeyObj(),
			ServiceTestUtil.randomString(), keywords, approved, serviceContext);
	}

	@Override
	protected void expireBaseModelVersions(
			BaseModel<?> baseModel, boolean expireAllVersions,
			ServiceContext serviceContext)
		throws Exception {

		WikiPage page = (WikiPage)baseModel;

		if (expireAllVersions) {
			WikiPageServiceUtil.deletePage(page.getNodeId(), page.getTitle());
		}
		else {
			List<WikiPage> pages = WikiPageServiceUtil.getPages(
				page.getGroupId(), page.getNodeId(), false,
				WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

			WikiPage previousPage = pages.get(0);

			WikiPageServiceUtil.revertPage(
				page.getNodeId(), page.getTitle(), previousPage.getVersion(),
				serviceContext);
		}
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return WikiPage.class;
	}

	@Override
	protected Long getBaseModelClassPK(ClassedModel classedModel) {
		return WikiPageAssetRenderer.getClassPK((WikiPage)classedModel);
	}

	@Override
	protected BaseModel<?> getParentBaseModel(
			Group group, ServiceContext serviceContext)
		throws Exception {

		serviceContext = (ServiceContext)serviceContext.clone();

		serviceContext.setWorkflowAction(WorkflowConstants.STATUS_APPROVED);

		return WikiNodeLocalServiceUtil.addNode(
			TestPropsValues.getUserId(), getSearchKeywords(),
			getSearchKeywords(), serviceContext);
	}

	@Override
	protected String getSearchKeywords() {
		return "Title";
	}

	@Override
	protected boolean isExpirableAllVersions() {
		return true;
	}

	@Override
	protected BaseModel<?> updateBaseModel(
			BaseModel<?> baseModel, String keywords,
			ServiceContext serviceContext)
		throws Exception {

		WikiPage page = (WikiPage)baseModel;

		return WikiTestUtil.updatePage(
			page, TestPropsValues.getUserId(), keywords, serviceContext);
	}

}