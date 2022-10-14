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
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.RoleLocalService;

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
	property = "batch.engine.task.item.delegate.name=role-analytics-dxp-entities",
	service = BatchEngineTaskItemDelegate.class
)
public class RoleAnalyticsDXPEntityBatchEngineTaskItemDelegate
	extends BaseAnalyticsDXPEntityBatchEngineTaskItemDelegate<DXPEntity> {

	@Override
	public Page<DXPEntity> read(
			Filter filter, Pagination pagination, Sort[] sorts,
			Map<String, Serializable> parameters, String search)
		throws Exception {

		List<DXPEntity> dxpEntities = new ArrayList<>();

		DynamicQuery dynamicQuery = _buildDynamicQuery(
			contextCompany.getCompanyId(), filter);

		List<Role> roles = _roleLocalService.dynamicQuery(
			dynamicQuery, pagination.getStartPosition(),
			pagination.getEndPosition());

		for (Role role : roles) {
			dxpEntities.add(_dxpEntityDTOConverter.toDTO(role));
		}

		return Page.of(
			dxpEntities, pagination,
			_roleLocalService.dynamicQueryCount(dynamicQuery));
	}

	private DynamicQuery _buildDynamicQuery(long companyId, Filter filter) {
		DynamicQuery dynamicQuery = _roleLocalService.dynamicQuery();

		Property nameProperty = PropertyFactoryUtil.forName("name");

		dynamicQuery.add(
			nameProperty.ne(RoleConstants.ANALYTICS_ADMINISTRATOR));

		Property typeProperty = PropertyFactoryUtil.forName("type");

		dynamicQuery.add(typeProperty.eq(RoleConstants.TYPE_REGULAR));

		return buildDynamicQuery(companyId, dynamicQuery, filter);
	}

	@Reference
	private DXPEntityDTOConverter _dxpEntityDTOConverter;

	@Reference
	private RoleLocalService _roleLocalService;

}