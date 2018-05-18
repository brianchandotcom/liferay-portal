/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.product.item.selector.web.internal.display.context;

import com.liferay.commerce.product.item.selector.web.internal.CPInstanceItemSelectorView;
import com.liferay.commerce.product.item.selector.web.internal.search.CPInstanceItemSelectorChecker;
import com.liferay.commerce.product.item.selector.web.internal.util.CPItemSelectorViewUtil;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CPInstanceItemSelectorViewDisplayContext
	extends BaseCPItemSelectorViewDisplayContext<CPInstance> {

	public CPInstanceItemSelectorViewDisplayContext(
		HttpServletRequest httpServletRequest, PortletURL portletURL,
		String itemSelectedEventName, CPInstanceService cpInstanceService) {

		super(
			httpServletRequest, portletURL, itemSelectedEventName,
			CPInstanceItemSelectorView.class.getSimpleName());

		_cpInstanceService = cpInstanceService;

		setDefaultOrderByCol("sku");
	}

	@Override
	public SearchContainer<CPInstance> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		searchContainer = new SearchContainer<>(
			liferayPortletRequest, getPortletURL(), null, null);

		searchContainer.setEmptyResultsMessage("no-skus-were-found");

		OrderByComparator<CPInstance> orderByComparator =
			CPItemSelectorViewUtil.getCPInstanceOrderByComparator(
				getOrderByCol(), getOrderByType());

		RowChecker rowChecker = new CPInstanceItemSelectorChecker(
			cpRequestHelper.getRenderResponse(), getCheckedCPInstanceIds());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(rowChecker);

		if (isSearch()) {
			Sort sort = CPItemSelectorViewUtil.getCPInstanceSort(
				getOrderByCol(), getOrderByType());

			BaseModelSearchResult<CPInstance> cpInstanceBaseModelSearchResult =
				_cpInstanceService.searchCPInstances(
					themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),
					getKeywords(), WorkflowConstants.STATUS_APPROVED,
					searchContainer.getStart(), searchContainer.getEnd(), sort);

			searchContainer.setTotal(
				cpInstanceBaseModelSearchResult.getLength());
			searchContainer.setResults(
				cpInstanceBaseModelSearchResult.getBaseModels());
		}
		else {
			int total = _cpInstanceService.getCPInstancesCount(
				getScopeGroupId(), WorkflowConstants.STATUS_APPROVED);

			searchContainer.setTotal(total);

			List<CPInstance> results = _cpInstanceService.getCPInstances(
				getScopeGroupId(), WorkflowConstants.STATUS_APPROVED,
				searchContainer.getStart(), searchContainer.getEnd(),
				orderByComparator);

			searchContainer.setResults(results);
		}

		return searchContainer;
	}

	protected long[] getCheckedCPInstanceIds() {
		return ParamUtil.getLongValues(
			httpServletRequest, "checkedCPInstanceIds");
	}

	private final CPInstanceService _cpInstanceService;

}