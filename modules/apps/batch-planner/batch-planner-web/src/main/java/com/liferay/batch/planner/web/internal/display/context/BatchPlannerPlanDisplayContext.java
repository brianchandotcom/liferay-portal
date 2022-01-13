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

import com.liferay.batch.planner.constants.BatchPlannerPortletKeys;
import com.liferay.batch.planner.model.BatchPlannerPlan;
import com.liferay.batch.planner.service.BatchPlannerPlanServiceUtil;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

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
	}

	public PortletURL getPortletURL() {
		return PortletURLBuilder.createRenderURL(
			renderResponse
		).setMVCRenderCommandName(
			"/batch_planner/view_batch_planner_plans"
		).setNavigation(
			ParamUtil.getString(renderRequest, "navigation", "all")
		).setTabs1(
			"batch-planner-plans"
		).setParameter(
			"delta", () -> ParamUtil.getString(renderRequest, "delta")
		).buildPortletURL();
	}

	public SearchContainer<BatchPlannerPlan> getSearchContainer() {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer<>(
			renderRequest, getPortletURL(), null, "no-items-were-found");

		_searchContainer.setId("batchPlannerPlanSearchContainer");
		_searchContainer.setOrderByCol(_getOrderByCol());
		_searchContainer.setOrderByType(_getOrderByType());

		long companyId = PortalUtil.getCompanyId(renderRequest);

		String navigation = ParamUtil.getString(
			renderRequest, "navigation", "all");

		if (navigation.equals("all")) {
			_searchContainer.setResultsAndTotal(
				() -> BatchPlannerPlanServiceUtil.getBatchPlannerPlans(
					companyId, true, _searchContainer.getStart(),
					_searchContainer.getEnd(),
					OrderByComparatorFactoryUtil.create(
						"BatchPlannerPlan", _searchContainer.getOrderByCol(),
						Objects.equals(
							_searchContainer.getOrderByType(), "asc"))),
				BatchPlannerPlanServiceUtil.getBatchPlannerPlansCount(
					companyId, true));
		}
		else {
			boolean export = isExport(navigation);

			_searchContainer.setResultsAndTotal(
				() -> BatchPlannerPlanServiceUtil.getBatchPlannerPlans(
					companyId, export, true, _searchContainer.getStart(),
					_searchContainer.getEnd(),
					OrderByComparatorFactoryUtil.create(
						"BatchPlannerPlan", _searchContainer.getOrderByCol(),
						Objects.equals(
							_searchContainer.getOrderByType(), "asc"))),
				BatchPlannerPlanServiceUtil.getBatchPlannerPlansCount(
					companyId, export, true));
		}

		_searchContainer.setRowChecker(
			new EmptyOnClickRowChecker(renderResponse));

		return _searchContainer;
	}

	private String _getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = SearchOrderByUtil.getOrderByCol(
			httpServletRequest, BatchPlannerPortletKeys.BATCH_PLANNER,
			"plan-order-by-col", "modifiedDate");

		return _orderByCol;
	}

	private String _getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = SearchOrderByUtil.getOrderByType(
			httpServletRequest, BatchPlannerPortletKeys.BATCH_PLANNER,
			"paln-order-by-type", "desc");

		return _orderByType;
	}

	private String _orderByCol;
	private String _orderByType;
	private SearchContainer<BatchPlannerPlan> _searchContainer;

}