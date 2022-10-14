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
import com.liferay.analytics.message.storage.model.AnalyticsAssociation;
import com.liferay.analytics.message.storage.service.AnalyticsAssociationLocalService;
import com.liferay.batch.engine.BaseBatchEngineTaskItemDelegate;
import com.liferay.batch.engine.BatchEngineTaskItemDelegate;
import com.liferay.batch.engine.pagination.Page;
import com.liferay.batch.engine.pagination.Pagination;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.TermRangeQuery;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.service.UserLocalService;
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
	property = "batch.engine.task.item.delegate.name=analytics-association-analytics-dxp-entities",
	service = BatchEngineTaskItemDelegate.class
)
public class AnalyticsAssociationAnalyticsDXPEntityBatchEngineTaskItemDelegate
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

		List<AnalyticsAssociation> analyticsAssociations = null;
		int totalCount = 0;

		Date modifiedDate = _getModifiedDate(filter);

		if (modifiedDate != null) {
			analyticsAssociations =
				_analyticsAssociationLocalService.getAnalyticsAssociations(
					contextCompany.getCompanyId(), modifiedDate,
					User.class.getName(), pagination.getStartPosition(),
					pagination.getEndPosition());
			totalCount =
				_analyticsAssociationLocalService.getAnalyticsAssociationsCount(
					contextCompany.getCompanyId(), modifiedDate,
					User.class.getName());
		}
		else {
			analyticsAssociations =
				_analyticsAssociationLocalService.getAnalyticsAssociations(
					contextCompany.getCompanyId(), User.class.getName(),
					pagination.getStartPosition(), pagination.getEndPosition());
			totalCount =
				_analyticsAssociationLocalService.getAnalyticsAssociationsCount(
					contextCompany.getCompanyId(), User.class.getName());
		}

		if (ListUtil.isEmpty(analyticsAssociations)) {
			return Page.of(Collections.emptyList());
		}

		List<DXPEntity> dxpEntities = new ArrayList<>();

		for (AnalyticsAssociation analyticsAssociation :
				analyticsAssociations) {

			User user = _userLocalService.getUser(
				analyticsAssociation.getAssociationClassPK());

			user.setModifiedDate(analyticsAssociation.getModifiedDate());

			dxpEntities.add(_dxpEntityDTOConverter.toDTO(user));
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
	private AnalyticsAssociationLocalService _analyticsAssociationLocalService;

	@Reference
	private DXPEntityDTOConverter _dxpEntityDTOConverter;

	@Reference
	private UserLocalService _userLocalService;

}