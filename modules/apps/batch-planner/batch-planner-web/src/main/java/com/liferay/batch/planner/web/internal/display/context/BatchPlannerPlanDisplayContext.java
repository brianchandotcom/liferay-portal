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

package com.liferay.batch.planner.web.internal.display.context;

import com.liferay.batch.planner.model.BatchPlannerPlan;
import com.liferay.batch.planner.service.BatchPlannerPlanServiceUtil;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Objects;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Matija Petanjek
 */
public class BatchPlannerPlanDisplayContext extends BaseDisplayContext {

	public BatchPlannerPlanDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		super(renderRequest, renderResponse);

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public PortletURL getPortletURL() {
		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setTabs1(
			"batch-planner-plans"
		).setParameter(
			"delta", () -> ParamUtil.getString(_renderRequest, "delta")
		).buildPortletURL();
	}

	public SearchContainer<BatchPlannerPlan> getSearchContainer() {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer<>(
			_renderRequest, getPortletURL(), null, "no-items-were-found");

		String orderByCol = ParamUtil.getString(
			_renderRequest, SearchContainer.DEFAULT_ORDER_BY_COL_PARAM,
			"modifiedDate");

		_searchContainer.setOrderByCol(orderByCol);

		String orderByType = ParamUtil.getString(
			_renderRequest, SearchContainer.DEFAULT_ORDER_BY_TYPE_PARAM,
			"desc");

		_searchContainer.setOrderByType(orderByType);

		_searchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));

		long companyId = PortalUtil.getCompanyId(_renderRequest);

		String navigation = ParamUtil.getString(
			_renderRequest, "navigation", "all");

		if (navigation.equals("all")) {
			_searchContainer.setResults(
				BatchPlannerPlanServiceUtil.getBatchPlannerPlans(
					companyId, _searchContainer.getStart(),
					_searchContainer.getEnd(),
					OrderByComparatorFactoryUtil.create(
						"BatchPlannerPlan", orderByCol,
						orderByType.equals("asc"))));
			_searchContainer.setTotal(
				BatchPlannerPlanServiceUtil.getBatchPlannerPlansCount(
					companyId));
		}
		else {
			boolean export = _isExport(navigation);

			_searchContainer.setResults(
				BatchPlannerPlanServiceUtil.getBatchPlannerPlans(
					companyId, export, _searchContainer.getStart(),
					_searchContainer.getEnd(),
					OrderByComparatorFactoryUtil.create(
						"BatchPlannerPlan", orderByCol,
						orderByType.equals("asc"))));
			_searchContainer.setTotal(
				BatchPlannerPlanServiceUtil.getBatchPlannerPlansCount(
					companyId, export));
		}

		return _searchContainer;
	}

	private boolean _isExport(String navigation) {
		return Objects.equals(navigation, "export");
	}

	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private SearchContainer<BatchPlannerPlan> _searchContainer;

}