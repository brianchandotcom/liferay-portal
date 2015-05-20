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

package com.liferay.workflow.instance.web.context;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.workflow.WorkflowHandlerUtil;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManagerUtil;
import com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactoryUtil;

import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Marcellus Tavares
 */
public class MyWorkflowInstanceViewDisplayContext
	extends WorkflowInstanceViewDisplayContext {

	public MyWorkflowInstanceViewDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		super(renderRequest, renderResponse);
	}

	@Override
	public String getSearchContainerEmptyResultsMessage() {
		if (isShowCompletedInstances()) {
			return "there-are-no-completed-instances-started-by-me";
		}
		else {
			return "there-are-no-pending-instances-started-by-me";
		}
	}

	@Override
	public List<WorkflowInstance> getSearchContainerResults(int start, int end)
		throws PortalException {

		return WorkflowInstanceManagerUtil.getWorkflowInstances(
			workflowInstanceRequestHelper.getCompanyId(),
			workflowInstanceRequestHelper.getUserId(),
			WorkflowHandlerUtil.getSearchableAssetTypes(),
			isShowCompletedInstances(), start, end,
			WorkflowComparatorFactoryUtil.getInstanceStartDateComparator());
	}

	@Override
	public int getSearchContainerTotal() throws PortalException {
		return WorkflowInstanceManagerUtil.getWorkflowInstanceCount(
			workflowInstanceRequestHelper.getCompanyId(),
			workflowInstanceRequestHelper.getUserId(),
			WorkflowHandlerUtil.getSearchableAssetTypes(),
			isShowCompletedInstances());
	}

}