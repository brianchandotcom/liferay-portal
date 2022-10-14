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
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.ExpandoTableConstants;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.ClassNameLocalService;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
@Component(
	property = "batch.engine.task.item.delegate.name=expando-column-analytics-dxp-entities",
	service = BatchEngineTaskItemDelegate.class
)
public class ExpandoColumnAnalyticsDXPEntityBatchEngineTaskItemDelegate
	extends BaseAnalyticsDXPEntityBatchEngineTaskItemDelegate<DXPEntity> {

	@Override
	public Page<DXPEntity> read(
			Filter filter, Pagination pagination, Sort[] sorts,
			Map<String, Serializable> parameters, String search)
		throws Exception {

		DynamicQuery dynamicQuery = _buildDynamicQuery(
			contextCompany.getCompanyId(), filter);

		if (dynamicQuery == null) {
			return Page.of(Collections.emptyList(), pagination, 0);
		}

		List<DXPEntity> dxpEntities = new ArrayList<>();

		List<ExpandoColumn> expandoColumns =
			_expandoColumnLocalService.dynamicQuery(
				dynamicQuery, pagination.getStartPosition(),
				pagination.getEndPosition());

		for (ExpandoColumn expandoColumn : expandoColumns) {
			dxpEntities.add(_dxpEntityDTOConverter.toDTO(expandoColumn));
		}

		return Page.of(
			dxpEntities, pagination,
			_expandoColumnLocalService.dynamicQueryCount(dynamicQuery));
	}

	private DynamicQuery _buildDynamicQuery(long companyId, Filter filter) {
		ExpandoTable organizationExpandoTable =
			_expandoTableLocalService.fetchTable(
				companyId,
				_classNameLocalService.getClassNameId(
					Organization.class.getName()),
				ExpandoTableConstants.DEFAULT_TABLE_NAME);
		ExpandoTable userExpandoTable = _expandoTableLocalService.fetchTable(
			companyId,
			_classNameLocalService.getClassNameId(User.class.getName()),
			ExpandoTableConstants.DEFAULT_TABLE_NAME);

		if ((organizationExpandoTable == null) && (userExpandoTable == null)) {
			return null;
		}

		DynamicQuery dynamicQuery = _expandoColumnLocalService.dynamicQuery();

		Property tableIdProperty = PropertyFactoryUtil.forName("tableId");

		if ((organizationExpandoTable != null) && (userExpandoTable != null)) {
			dynamicQuery.add(
				RestrictionsFactoryUtil.or(
					tableIdProperty.eq(organizationExpandoTable.getTableId()),
					tableIdProperty.eq(userExpandoTable.getTableId())));
		}
		else if (organizationExpandoTable != null) {
			dynamicQuery.add(
				tableIdProperty.eq(organizationExpandoTable.getTableId()));
		}
		else {
			dynamicQuery.add(tableIdProperty.eq(userExpandoTable.getTableId()));
		}

		return buildDynamicQuery(companyId, dynamicQuery, filter);
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private DXPEntityDTOConverter _dxpEntityDTOConverter;

	@Reference
	private ExpandoColumnLocalService _expandoColumnLocalService;

	@Reference
	private ExpandoTableLocalService _expandoTableLocalService;

}