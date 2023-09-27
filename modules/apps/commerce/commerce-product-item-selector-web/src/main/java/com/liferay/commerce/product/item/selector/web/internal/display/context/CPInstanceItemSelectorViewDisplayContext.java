/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.item.selector.web.internal.display.context;

import com.liferay.commerce.product.item.selector.web.internal.CPInstanceItemSelectorView;
import com.liferay.commerce.product.item.selector.web.internal.search.CPInstanceItemSelectorChecker;
import com.liferay.commerce.product.item.selector.web.internal.util.CPItemSelectorViewUtil;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

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
	public PortletURL getPortletURL() {
		return PortletURLBuilder.create(
			super.getPortletURL()
		).setParameter(
			"checkedCPInstanceIds", _getCheckedCPInstanceIds()
		).setParameter(
			"commerceCatalogGroupId", _getGroupId()
		).buildPortletURL();
	}

	@Override
	public SearchContainer<CPInstance> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		searchContainer = new SearchContainer<>(
			liferayPortletRequest, getPortletURL(), null, "no-skus-were-found");

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(
			CPItemSelectorViewUtil.getCPInstanceOrderByComparator(
				getOrderByCol(), getOrderByType()));
		searchContainer.setOrderByType(getOrderByType());

		Sort sort = CPItemSelectorViewUtil.getCPInstanceSort(
			getOrderByCol(), getOrderByType());

		BaseModelSearchResult<CPInstance> cpInstanceBaseModelSearchResult;

		if (_getGroupId() > 0) {
			cpInstanceBaseModelSearchResult =
				_cpInstanceService.searchCPInstances(
					cpRequestHelper.getCompanyId(), _getGroupId(),
					getKeywords(), WorkflowConstants.STATUS_APPROVED,
					searchContainer.getStart(), searchContainer.getEnd(), sort);
		}
		else {
			cpInstanceBaseModelSearchResult =
				_cpInstanceService.searchCPInstances(
					cpRequestHelper.getCompanyId(), getKeywords(),
					WorkflowConstants.STATUS_APPROVED,
					searchContainer.getStart(), searchContainer.getEnd(), sort);
		}

		searchContainer.setResultsAndTotal(cpInstanceBaseModelSearchResult);
		searchContainer.setRowChecker(
			new CPInstanceItemSelectorChecker(
				cpRequestHelper.getRenderResponse(),
				_getCheckedCPInstanceIds()));

		return searchContainer;
	}

	private long[] _getCheckedCPInstanceIds() {
		return ParamUtil.getLongValues(
			httpServletRequest, "checkedCPInstanceIds");
	}

	private long _getGroupId() {
		return ParamUtil.getLong(httpServletRequest, "commerceCatalogGroupId");
	}

	private final CPInstanceService _cpInstanceService;

}