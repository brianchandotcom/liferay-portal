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

package com.liferay.portlet.wiki.trash;

import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.trash.BaseTrashHandlerTestCase;
import com.liferay.portlet.wiki.asset.WikiPageAssetRenderer;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;

import org.junit.Assert;
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
public class WikiPageTrashHandlerTest extends BaseTrashHandlerTestCase {

	public void testTrashAndDelete() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	public void testTrashAndRestoreDraft() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	@Override
	protected BaseModel<?> addBaseModel(
			BaseModel<?> parentBaseModel, boolean approved,
			ServiceContext serviceContext)
		throws Exception {

		serviceContext = (ServiceContext)serviceContext.clone();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		WikiPage wikiPage = WikiPageLocalServiceUtil.addPage(
			TestPropsValues.getUserId(),
			(Long)parentBaseModel.getPrimaryKeyObj(), getSearchKeywords(),
			ServiceTestUtil.randomString(), ServiceTestUtil.randomString(),
			true, serviceContext);

		WikiPageLocalServiceUtil.updateStatus(
			TestPropsValues.getUserId(), wikiPage.getResourcePrimKey(),
			WorkflowConstants.STATUS_APPROVED, serviceContext);

		return wikiPage;
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
	protected int getBaseModelsNotInTrashCount(BaseModel<?> parentBaseModel)
		throws Exception {

		return WikiPageLocalServiceUtil.getPagesCount(
			(Long)parentBaseModel.getPrimaryKeyObj(),
			WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	protected BaseModel<?> getParentBaseModel(
			Group group, ServiceContext serviceContext)
		throws Exception {

		serviceContext = (ServiceContext)serviceContext.clone();

		serviceContext.setWorkflowAction(WorkflowConstants.STATUS_APPROVED);

		return WikiNodeLocalServiceUtil.addNode(
			TestPropsValues.getUserId(), ServiceTestUtil.randomString(),
			ServiceTestUtil.randomString(), serviceContext);
	}

	@Override
	protected String getSearchKeywords() {
		return "Title";
	}

	@Override
	protected Long getTrashClassPK(ClassedModel classedModel) {
		return ((WikiPage)classedModel).getResourcePrimKey();
	}

	@Override
	protected void moveBaseModelToTrash(long primaryKey) throws Exception {
		WikiPage wikiPage = WikiPageLocalServiceUtil.getPageByPageId(
			primaryKey);

		WikiPageLocalServiceUtil.movePageToTrash(
			TestPropsValues.getUserId(), wikiPage.getNodeId(),
			wikiPage.getTitle());
	}

}