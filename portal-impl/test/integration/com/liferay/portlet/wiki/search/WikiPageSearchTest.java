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

package com.liferay.portlet.wiki.search;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.search.lucene.BaseSearchTestCase;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.wiki.asset.WikiPageAssetRenderer;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;

import java.io.File;

import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@ExecutionTestListeners(listeners = {MainServletExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class WikiPageSearchTest extends BaseSearchTestCase {

	@Override
	protected void addAttachment(ClassedModel classedModel) throws Exception {
		WikiPage page = (WikiPage)classedModel;

		Class<?> clazz = getClass();

		byte[] fileBytes = FileUtil.getBytes(
			clazz.getResourceAsStream("dependencies/OSX_Test.docx"));

		File file = null;

		if ((fileBytes != null) && (fileBytes.length > 0)) {
			file = FileUtil.createTempFile(fileBytes);
		}

		String fileName = ServiceTestUtil.randomString() + ".txt";

		WikiPageLocalServiceUtil.addPageAttachment(
			TestPropsValues.getUserId(), page.getNodeId(), page.getTitle(),
			fileName, file);
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

		WikiPage page = WikiPageLocalServiceUtil.addPage(
			TestPropsValues.getUserId(),
			(Long)parentBaseModel.getPrimaryKeyObj(), title,
			ServiceTestUtil.randomString(), ServiceTestUtil.randomString(),
			true, serviceContext);

		page = WikiPageLocalServiceUtil.updateStatus(
			TestPropsValues.getUserId(), page.getResourcePrimKey(),
			WorkflowConstants.STATUS_APPROVED, serviceContext);

		return page;
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
			TestPropsValues.getUserId(),
			ServiceTestUtil.randomString(_NODE_NAME_MAX_LENGTH),
			ServiceTestUtil.randomString(), serviceContext);
	}

	@Override
	protected String getSearchKeywords() {
		return "Title";
	}

	private static final int _NODE_NAME_MAX_LENGTH = 75;

	private static final int _PAGE_TITLE_MAX_LENGTH = 255;

}