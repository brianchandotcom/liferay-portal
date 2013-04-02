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

package com.liferay.portlet.wiki.util;

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;

import java.io.File;

/**
 * @author Julio Camarero
 */
public class WikiTestUtil {

	public static WikiNode addNode(
			long userId, long groupId, String name, String description)
		throws Exception {

		WorkflowThreadLocal.setEnabled(true);

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			groupId);

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		serviceContext = (ServiceContext)serviceContext.clone();

		WikiNode node = WikiNodeLocalServiceUtil.addNode(
			userId, name, description, serviceContext);

		return node;
	}

	public static WikiPage addPage(
			long userId, long groupId, long nodeId, String title,
			boolean approved)
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			groupId);

		return addPage(
			userId, nodeId, title, "content", approved, serviceContext);
	}

	public static WikiPage addPage(
			long userId, long nodeId, String title, String content,
			boolean approved, ServiceContext serviceContext)
		throws Exception {

		boolean workflowEnabled = WorkflowThreadLocal.isEnabled();

		try {
			WorkflowThreadLocal.setEnabled(true);

			serviceContext = (ServiceContext)serviceContext.clone();

			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);

			WikiPage page = WikiPageLocalServiceUtil.addPage(
				userId, nodeId, title, content, "Summary", true,
				serviceContext);

			if (approved) {
				page = WikiPageLocalServiceUtil.updateStatus(
					userId, page.getResourcePrimKey(),
					WorkflowConstants.STATUS_APPROVED, serviceContext);
			}

			return page;
		}
		finally {
			WorkflowThreadLocal.setEnabled(workflowEnabled);
		}
	}

	public static File addWikiAttachment(
			long userId, long nodeId, String title, Class<?> clazz)
		throws Exception {

		String fileName = ServiceTestUtil.randomString() + ".docx";

		byte[] fileBytes = FileUtil.getBytes(
			clazz.getResourceAsStream("dependencies/OSX_Test.docx"));

		File file = null;

		if ((fileBytes != null) && (fileBytes.length > 0)) {
			file = FileUtil.createTempFile(fileBytes);
		}

		String mimeType = MimeTypesUtil.getExtensionContentType("docx");

		WikiPageLocalServiceUtil.addPageAttachment(
			userId, nodeId, title, fileName, file, mimeType);

		return file;
	}

	public static WikiPage updatePage(
			WikiPage page, long userId, String content,
			ServiceContext serviceContext)
		throws Exception {

		return WikiPageLocalServiceUtil.updatePage(
			userId, page.getNodeId(), page.getTitle(), page.getVersion(),
			content, page.getSummary(), false, page.getFormat(),
			page.getParentTitle(), page.getRedirectTitle(), serviceContext);
	}

}