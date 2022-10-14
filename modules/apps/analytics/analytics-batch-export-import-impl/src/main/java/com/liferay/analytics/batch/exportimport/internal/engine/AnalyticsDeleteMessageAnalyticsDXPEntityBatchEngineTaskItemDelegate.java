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

package com.liferay.analytics.batch.exportimport.internal.engine;

import com.liferay.analytics.batch.exportimport.internal.odata.entity.AnalyticsDXPEntityEntityModel;
import com.liferay.analytics.dxp.entity.rest.dto.v1_0.DXPEntity;
import com.liferay.analytics.dxp.entity.rest.dto.v1_0.converter.DXPEntityDTOConverter;
import com.liferay.analytics.message.storage.model.AnalyticsDeleteMessage;
import com.liferay.analytics.message.storage.service.AnalyticsDeleteMessageLocalService;
import com.liferay.batch.engine.BaseBatchEngineTaskItemDelegate;
import com.liferay.batch.engine.BatchEngineTaskItemDelegate;
import com.liferay.batch.engine.pagination.Page;
import com.liferay.batch.engine.pagination.Pagination;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.TermRangeQuery;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityModel;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcos Martins
 */
@Component(
	property = "batch.engine.task.item.delegate.name=analytics-delete-message-analytics-dxp-entities",
	service = BatchEngineTaskItemDelegate.class
)
public class AnalyticsDeleteMessageAnalyticsDXPEntityBatchEngineTaskItemDelegate
	extends BaseBatchEngineTaskItemDelegate<DXPEntity> {

	@Override
	public EntityModel getEntityModel(Map<String, List<String>> multivaluedMap)
		throws Exception {

		return new AnalyticsDXPEntityEntityModel();
	}

	@Override
	public Page<DXPEntity> read(
			Filter filter, Pagination pagination, Sort[] sorts,
			Map<String, Serializable> parameters, String search)
		throws Exception {

		List<AnalyticsDeleteMessage> analyticsDeleteMessages = null;
		int totalCount = 0;

		Date modifiedDate = _getModifiedDate(filter);

		if (modifiedDate != null) {
			analyticsDeleteMessages =
				_analyticsDeleteMessageLocalService.getAnalyticsDeleteMessages(
					contextCompany.getCompanyId(), modifiedDate,
					pagination.getStartPosition(), pagination.getEndPosition());
			totalCount =
				_analyticsDeleteMessageLocalService.
					getAnalyticsDeleteMessagesCount(
						contextCompany.getCompanyId(), modifiedDate);
		}
		else {
			analyticsDeleteMessages =
				_analyticsDeleteMessageLocalService.getAnalyticsDeleteMessages(
					contextCompany.getCompanyId(),
					pagination.getStartPosition(), pagination.getEndPosition());
			totalCount =
				_analyticsDeleteMessageLocalService.
					getAnalyticsDeleteMessagesCount(
						contextCompany.getCompanyId());
		}

		if (ListUtil.isEmpty(analyticsDeleteMessages)) {
			return Page.of(Collections.emptyList());
		}

		List<DXPEntity> dxpEntities = new ArrayList<>();

		for (AnalyticsDeleteMessage analyticsDeleteMessage :
				analyticsDeleteMessages) {

			dxpEntities.add(
				_dxpEntityDTOConverter.toDTO(analyticsDeleteMessage));
		}

		return Page.of(dxpEntities, pagination, totalCount);
	}

	private Date _getModifiedDate(Filter filter) {
		if (!(filter instanceof QueryFilter)) {
			return null;
		}

		QueryFilter queryFilter = (QueryFilter)filter;

		Query query = queryFilter.getQuery();

		if (!(query instanceof TermRangeQuery)) {
			return null;
		}

		TermRangeQuery termRangeQuery = (TermRangeQuery)query;

		if (!StringUtil.startsWith(termRangeQuery.getField(), "modified")) {
			return null;
		}

		String lowerTerm = termRangeQuery.getLowerTerm();

		return new Date(GetterUtil.getLong(lowerTerm));
	}

	@Reference
	private AnalyticsDeleteMessageLocalService
		_analyticsDeleteMessageLocalService;

	@Reference
	private DXPEntityDTOConverter _dxpEntityDTOConverter;

}