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

import com.liferay.analytics.dxp.entity.rest.dto.v1_0.DXPEntity;
import com.liferay.analytics.dxp.entity.rest.dto.v1_0.converter.DXPEntityDTOConverter;
import com.liferay.batch.engine.BatchEngineTaskItemDelegate;
import com.liferay.batch.engine.pagination.Page;
import com.liferay.batch.engine.pagination.Pagination;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.TeamLocalService;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcos Martins
 */
@Component(
	property = "batch.engine.task.item.delegate.name=team-analytics-dxp-entities",
	service = BatchEngineTaskItemDelegate.class
)
public class TeamAnalyticsDXPEntityBatchEngineTaskItemDelegate
	extends BaseAnalyticsDXPEntityBatchEngineTaskItemDelegate<DXPEntity> {

	@Override
	public Page<DXPEntity> read(
			Filter filter, Pagination pagination, Sort[] sorts,
			Map<String, Serializable> parameters, String search)
		throws Exception {

		List<DXPEntity> dxpEntities = new ArrayList<>();

		DynamicQuery dynamicQuery = buildDynamicQuery(
			contextCompany.getCompanyId(), _teamLocalService.dynamicQuery(),
			filter);

		List<Team> teams = _teamLocalService.dynamicQuery(
			dynamicQuery, pagination.getStartPosition(),
			pagination.getEndPosition());

		for (Team team : teams) {
			dxpEntities.add(_dxpEntityDTOConverter.toDTO(team));
		}

		return Page.of(
			dxpEntities, pagination,
			_teamLocalService.dynamicQueryCount(dynamicQuery));
	}

	@Reference
	private DXPEntityDTOConverter _dxpEntityDTOConverter;

	@Reference
	private TeamLocalService _teamLocalService;

}