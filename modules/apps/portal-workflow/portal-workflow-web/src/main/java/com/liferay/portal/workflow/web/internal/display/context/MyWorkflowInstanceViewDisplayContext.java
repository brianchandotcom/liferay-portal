/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.web.internal.display.context;

import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManagerUtil;
import com.liferay.portal.kernel.workflow.search.WorkflowModelSearchResult;
import com.liferay.portal.workflow.comparator.WorkflowComparatorFactory;
import com.liferay.portal.workflow.manager.WorkflowLogManager;
import com.liferay.portal.workflow.web.internal.search.WorkflowInstanceSearch;

import java.util.Objects;

/**
 * @author Marcellus Tavares
 */
public class MyWorkflowInstanceViewDisplayContext
	extends WorkflowInstanceViewDisplayContext {

	public MyWorkflowInstanceViewDisplayContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			WorkflowComparatorFactory workflowComparatorFactory,
			WorkflowLogManager workflowLogManager)
		throws PortalException {

		super(
			liferayPortletRequest, liferayPortletResponse,
			workflowComparatorFactory, workflowLogManager);
	}

	@Override
	public String getHeaderTitle() {
		return "my-submissions";
	}

	@Override
	protected WorkflowModelSearchResult<WorkflowInstance>
			getWorkflowModelSearchResult(
				int start, int end,
				OrderByComparator<WorkflowInstance> orderByComparator)
		throws PortalException {

		if (Objects.nonNull(workflowModelSearchResult)) {
			return workflowModelSearchResult;
		}

		workflowModelSearchResult =
			WorkflowInstanceManagerUtil.searchWorkflowInstances(
				workflowInstanceRequestHelper.getCompanyId(),
				workflowInstanceRequestHelper.getUserId(), true,
				getAssetType(getKeywords()), getKeywords(), getKeywords(),
				getKeywords(), getKeywords(), getCompleted(), true, start, end,
				orderByComparator);

		return workflowModelSearchResult;
	}

	@Override
	protected void setSearchContainerEmptyResultsMessage(
		WorkflowInstanceSearch searchContainer) {

		DisplayTerms searchTerms = searchContainer.getDisplayTerms();

		if (isNavigationAll()) {
			searchContainer.setEmptyResultsMessage(
				"there-are-no-instances-started-by-me");
		}
		else if (isNavigationPending()) {
			searchContainer.setEmptyResultsMessage(
				"there-are-no-pending-instances-started-by-me");
		}
		else {
			searchContainer.setEmptyResultsMessage(
				"there-are-no-completed-instances-started-by-me");
		}

		if (Validator.isNotNull(searchTerms.getKeywords())) {
			searchContainer.setEmptyResultsMessage(
				searchContainer.getEmptyResultsMessage() +
					"-with-the-specified-search-criteria");
		}
	}

}