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

import com.liferay.batch.engine.model.BatchEngineExportTask;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.batch.engine.service.BatchEngineExportTaskLocalServiceUtil;
import com.liferay.batch.engine.service.BatchEngineImportTaskLocalServiceUtil;
import com.liferay.batch.planner.model.BatchPlannerLog;
import com.liferay.batch.planner.model.BatchPlannerPlan;
import com.liferay.batch.planner.service.BatchPlannerLogServiceUtil;
import com.liferay.batch.planner.service.BatchPlannerPlanServiceUtil;
import com.liferay.batch.planner.web.internal.display.BatchPlannerLogDisplay;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.vulcan.util.TransformUtil;

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

		String orderByCol = ParamUtil.getString(
			renderRequest, SearchContainer.DEFAULT_ORDER_BY_COL_PARAM,
			"modifiedDate");

		_searchContainer.setOrderByCol(orderByCol);

		String orderByType = ParamUtil.getString(
			renderRequest, SearchContainer.DEFAULT_ORDER_BY_TYPE_PARAM, "desc");

		_searchContainer.setOrderByType(orderByType);

		long companyId = PortalUtil.getCompanyId(renderRequest);

		String navigation = ParamUtil.getString(
			renderRequest, "navigation", "all");

		if (navigation.equals("all")) {
			_searchContainer.setResults(
				TransformUtil.transform(
					BatchPlannerLogServiceUtil.getCompanyBatchPlannerLogs(
						companyId, _searchContainer.getStart(),
						_searchContainer.getEnd(),
						OrderByComparatorFactoryUtil.create(
							"BatchPlannerLog", orderByCol,
							orderByType.equals("asc"))),
					this::_toBatchPlannerLogDisplay));
			_searchContainer.setTotal(
				BatchPlannerLogServiceUtil.getCompanyBatchPlannerLogsCount(
					companyId));
		}
		else {
			_searchContainer.setResults(
				TransformUtil.transform(
					BatchPlannerLogServiceUtil.getCompanyBatchPlannerLogs(
						companyId, isExport(navigation),
						_searchContainer.getStart(), _searchContainer.getEnd(),
						OrderByComparatorFactoryUtil.create(
							"BatchPlannerLog", orderByCol,
							orderByType.equals("asc"))),
					this::_toBatchPlannerLogDisplay));
			_searchContainer.setTotal(
				BatchPlannerLogServiceUtil.getCompanyBatchPlannerLogsCount(
					companyId, isExport(navigation)));
		}

		return _searchContainer;
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
				batchEngineExportTask.getExecuteStatus()
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
				batchEngineImportTask.getExecuteStatus()
			).totalItemsCount(
				batchEngineImportTask.getTotalItemsCount()
			);
		}

		return builder.build();
	}

	private SearchContainer<BatchPlannerLogDisplay> _searchContainer;

}