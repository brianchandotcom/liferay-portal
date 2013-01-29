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

package com.liferay.portlet.wiki.util;

import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;

/**
 * @author Julio Camarero
 */
public class WikiTestUtil {

	public static WikiPage addWikiPage(
			long userId, long groupId, long nodeId, String title,
			boolean approved)
		throws Exception {

		boolean workflowEnabled = WorkflowThreadLocal.isEnabled();

		try {
			WorkflowThreadLocal.setEnabled(true);

			String content = "Content";
			String summary = "Summary";

			ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
				groupId);

			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);

			serviceContext = (ServiceContext)serviceContext.clone();

			WikiPage page = WikiPageLocalServiceUtil.addPage(
				userId, nodeId, title, content, summary, true, serviceContext);

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

}