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

package com.liferay.commerce.product.item.selector.web.internal.display.context;

import com.liferay.commerce.product.constants.CPField;
import com.liferay.commerce.product.item.selector.web.internal.CPDefinitionItemSelectorView;
import com.liferay.commerce.product.item.selector.web.internal.search.CPDefinitionItemSelectorChecker;
import com.liferay.commerce.product.item.selector.web.internal.util.CPItemSelectorViewUtil;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.type.CPType;
import com.liferay.commerce.product.type.CPTypeServicesTracker;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionItemSelectorViewDisplayContext
	extends BaseCPItemSelectorViewDisplayContext<CPDefinition> {

	public CPDefinitionItemSelectorViewDisplayContext(
		HttpServletRequest httpServletRequest, PortletURL portletURL,
		String itemSelectedEventName, CPDefinitionService cpDefinitionService,
		CPTypeServicesTracker cpTypeServicesTracker) {

		super(
			httpServletRequest, portletURL, itemSelectedEventName,
			CPDefinitionItemSelectorView.class.getSimpleName());

		_cpDefinitionService = cpDefinitionService;
		_cpTypeServicesTracker = cpTypeServicesTracker;

		setDefaultOrderByCol("name");
	}

	public long getCPDefinitionId() {
		return ParamUtil.getLong(httpServletRequest, "cpDefinitionId");
	}

	public CPType getCPType(String name) {
		return _cpTypeServicesTracker.getCPType(name);
	}

	@Override
	public PortletURL getPortletURL() {
		PortletURL portletURL = super.getPortletURL();

		long cpDefinitionId = getCPDefinitionId();

		if (cpDefinitionId > 0) {
			portletURL.setParameter(
				"cpDefinitionId", String.valueOf(cpDefinitionId));
			portletURL.setParameter(
				"checkedCPDefinitionIds",
				ParamUtil.getString(
					httpServletRequest, "checkedCPDefinitionIds"));
		}

		portletURL.setParameter(
			"singleSelection", Boolean.toString(isSingleSelection()));

		return portletURL;
	}

	@Override
	public SearchContainer<CPDefinition> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		searchContainer = new SearchContainer<>(
			liferayPortletRequest, getPortletURL(), null, null);

		searchContainer.setEmptyResultsMessage("no-products-were-found");

		OrderByComparator<CPDefinition> orderByComparator =
			CPItemSelectorViewUtil.getCPDefinitionOrderByComparator(
				getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());

		if (!isSingleSelection()) {
			RowChecker rowChecker = new CPDefinitionItemSelectorChecker(
				cpRequestHelper.getRenderResponse(),
				_getCheckedCPDefinitionIds(), getCPDefinitionId());

			searchContainer.setRowChecker(rowChecker);
		}

		Sort sort = CPItemSelectorViewUtil.getCPDefinitionSort(
			getOrderByCol(), getOrderByType());

		BaseModelSearchResult<CPDefinition> cpDefinitionBaseModelSearchResult;

		long commerceChannelGroupId = ParamUtil.getLong(
			httpServletRequest, CPField.COMMERCE_CHANNEL_GROUP_ID);

		if (commerceChannelGroupId != 0) {
			cpDefinitionBaseModelSearchResult =
				_cpDefinitionService.searchCPDefinitionsByChannelGroupId(
					cpRequestHelper.getCompanyId(), commerceChannelGroupId,
					getKeywords(), WorkflowConstants.STATUS_APPROVED,
					searchContainer.getStart(), searchContainer.getEnd(), sort);
		}
		else {
			cpDefinitionBaseModelSearchResult =
				_cpDefinitionService.searchCPDefinitions(
					cpRequestHelper.getCompanyId(), getKeywords(),
					WorkflowConstants.STATUS_APPROVED,
					searchContainer.getStart(), searchContainer.getEnd(), sort);
		}

		searchContainer.setTotal(cpDefinitionBaseModelSearchResult.getLength());
		searchContainer.setResults(
			cpDefinitionBaseModelSearchResult.getBaseModels());

		return searchContainer;
	}

	public String getSku(CPDefinition cpDefinition, Locale locale) {
		List<CPInstance> cpInstances = cpDefinition.getCPInstances();

		if (cpInstances.size() > 1) {
			return LanguageUtil.get(locale, "multiple-skus");
		}

		if (cpInstances.isEmpty()) {
			return StringPool.BLANK;
		}

		CPInstance cpInstance = cpInstances.get(0);

		return cpInstance.getSku();
	}

	public boolean isSingleSelection() {
		return ParamUtil.getBoolean(httpServletRequest, "singleSelection");
	}

	private long[] _getCheckedCPDefinitionIds() {
		return ParamUtil.getLongValues(
			httpServletRequest, "checkedCPDefinitionIds");
	}

	private long[] _getDisabledCPDefinitionIds() {
		return ParamUtil.getLongValues(
			httpServletRequest, "disabledCPDefinitionIds");
	}

	private final CPDefinitionService _cpDefinitionService;
	private final CPTypeServicesTracker _cpTypeServicesTracker;

}