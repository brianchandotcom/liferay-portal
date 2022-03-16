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

import com.liferay.batch.engine.BatchEngineTaskExecuteStatus;
import com.liferay.batch.engine.model.BatchEngineExportTask;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.batch.engine.service.BatchEngineExportTaskLocalServiceUtil;
import com.liferay.batch.engine.service.BatchEngineImportTaskLocalServiceUtil;
import com.liferay.batch.planner.constants.BatchPlannerLogConstants;
import com.liferay.batch.planner.constants.BatchPlannerPortletKeys;
import com.liferay.batch.planner.model.BatchPlannerLog;
import com.liferay.batch.planner.model.BatchPlannerPlan;
import com.liferay.batch.planner.service.BatchPlannerLogServiceUtil;
import com.liferay.batch.planner.service.BatchPlannerPlanServiceUtil;
import com.liferay.batch.planner.web.internal.display.BatchPlannerLogDisplay;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.Objects;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Matija Petanjek
 */
public class BatchPlannerLogDisplayContext extends BaseDisplayContext {

	public BatchPlannerLogDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		super(renderRequest, renderResponse);
	}

	public PortletURL getPortletURL() {
		return PortletURLBuilder.createRenderURL(
			renderResponse
		).setNavigation(
			ParamUtil.getString(renderRequest, "navigation", "all")
		).setTabs1(
			"batch-planner-logs"
		).setParameter(
			"delta", () -> ParamUtil.getString(renderRequest, "delta")
		).buildPortletURL();
	}

	public SearchContainer<BatchPlannerLogDisplay> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer<>(
			renderRequest, getPortletURL(), null, "no-items-were-found");

		_searchContainer.setOrderByCol(_getOrderByCol());
		_searchContainer.setOrderByType(_getOrderByType());

		long companyId = PortalUtil.getCompanyId(renderRequest);

		String navigation = ParamUtil.getString(
			renderRequest, "navigation", "all");

		String searchByField = ParamUtil.getString(
			renderRequest, "searchByField", "name");

		String searchByKeyword = ParamUtil.getString(
			renderRequest, "keywords", "");

		if (navigation.equals("all")) {
			_searchContainer.setResultsAndTotal(
				() -> TransformUtil.transform(
					BatchPlannerLogServiceUtil.getCompanyBatchPlannerLogs(
						companyId, searchByField, searchByKeyword,
						_searchContainer.getStart(), _searchContainer.getEnd(),
						OrderByComparatorFactoryUtil.create(
							"BatchPlannerLog", _searchContainer.getOrderByCol(),
							Objects.equals(
								_searchContainer.getOrderByType(), "asc"))),
					this::_toBatchPlannerLogDisplay),
				BatchPlannerLogServiceUtil.getCompanyBatchPlannerLogsCount(
					companyId, searchByField, searchByKeyword));
		}
		else {
			_searchContainer.setResultsAndTotal(
				() -> TransformUtil.transform(
					BatchPlannerLogServiceUtil.getCompanyBatchPlannerLogs(
						companyId, isExport(navigation), searchByField,
						searchByKeyword, _searchContainer.getStart(),
						_searchContainer.getEnd(),
						OrderByComparatorFactoryUtil.create(
							"BatchPlannerLog", _searchContainer.getOrderByCol(),
							Objects.equals(
								_searchContainer.getOrderByType(), "asc"))),
					this::_toBatchPlannerLogDisplay),
				BatchPlannerLogServiceUtil.getCompanyBatchPlannerLogsCount(
					companyId, isExport(navigation), searchByField,
					searchByKeyword));
		}

		return _searchContainer;
	}

	private String _getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = SearchOrderByUtil.getOrderByCol(
			httpServletRequest, BatchPlannerPortletKeys.BATCH_PLANNER,
			"log-order-by-col", "modifiedDate");

		return _orderByCol;
	}

	private String _getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = SearchOrderByUtil.getOrderByType(
			httpServletRequest, BatchPlannerPortletKeys.BATCH_PLANNER,
			"log-order-by-type", "desc");

		return _orderByType;
	}

	private BatchPlannerLogDisplay _toBatchPlannerLogDisplay(
			BatchPlannerLog batchPlannerLog)
		throws PortalException {

		BatchPlannerPlan batchPlannerPlan =
			BatchPlannerPlanServiceUtil.getBatchPlannerPlan(
				batchPlannerLog.getBatchPlannerPlanId());

		BatchPlannerLogDisplay.Builder builder =
			new BatchPlannerLogDisplay.Builder();

		builder.batchPlannerLogId(
			batchPlannerLog.getBatchPlannerLogId()
		).createDate(
			batchPlannerLog.getCreateDate()
		).internalClassName(
			batchPlannerPlan.getInternalClassName()
		).title(
			batchPlannerPlan.getName()
		).userId(
			batchPlannerLog.getUserId()
		);

		if (batchPlannerPlan.isExport()) {
			BatchEngineExportTask batchEngineExportTask =
				BatchEngineExportTaskLocalServiceUtil.getBatchEngineExportTask(
					Long.valueOf(
						batchPlannerLog.getBatchEngineExportTaskERC()));

			builder.action(
				LanguageUtil.get(httpServletRequest, "export")
			).status(
				BatchPlannerLogConstants.getStatus(
					BatchEngineTaskExecuteStatus.valueOf(
						batchEngineExportTask.getExecuteStatus()))
			);
		}
		else {
			BatchEngineImportTask batchEngineImportTask =
				BatchEngineImportTaskLocalServiceUtil.getBatchEngineImportTask(
					Long.valueOf(
						batchPlannerLog.getBatchEngineImportTaskERC()));

			builder.action(
				LanguageUtil.get(httpServletRequest, "import")
			).processedItemsCount(
				batchEngineImportTask.getProcessedItemsCount()
			).status(
				BatchPlannerLogConstants.getStatus(
					BatchEngineTaskExecuteStatus.valueOf(
						batchEngineImportTask.getExecuteStatus()))
			).totalItemsCount(
				batchEngineImportTask.getTotalItemsCount()
			);
		}

		return builder.build();
	}

	private String _orderByCol;
	private String _orderByType;
	private SearchContainer<BatchPlannerLogDisplay> _searchContainer;

}