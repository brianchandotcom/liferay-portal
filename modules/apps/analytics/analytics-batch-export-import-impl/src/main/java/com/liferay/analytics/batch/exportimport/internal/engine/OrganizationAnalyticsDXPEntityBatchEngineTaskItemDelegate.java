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
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.io.Serializable;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcos Martins
 */
@Component(
	property = "batch.engine.task.item.delegate.name=organization-analytics-dxp-entities",
	service = BatchEngineTaskItemDelegate.class
)
public class OrganizationAnalyticsDXPEntityBatchEngineTaskItemDelegate
	extends BaseAnalyticsDXPEntityBatchEngineTaskItemDelegate<DXPEntity> {

	@Override
	public Page<DXPEntity> read(
			Filter filter, Pagination pagination, Sort[] sorts,
			Map<String, Serializable> parameters, String search)
		throws Exception {

		com.liferay.portal.vulcan.pagination.Pagination vulcanPagination =
			com.liferay.portal.vulcan.pagination.Pagination.of(
				pagination.getPage(), pagination.getPageSize());

		com.liferay.portal.vulcan.pagination.Page<DXPEntity> page =
			SearchUtil.search(
				null, booleanQuery -> booleanQuery.getPreBooleanFilter(),
				filter, Organization.class.getName(), null, vulcanPagination,
				queryConfig -> queryConfig.setSelectedFieldNames(
					Field.ENTRY_CLASS_PK),
				searchContext -> {
					searchContext.setCompanyId(contextCompany.getCompanyId());
					searchContext.setUserId(0);
				},
				null,
				document -> _dxpEntityDTOConverter.toDTO(
					_organizationLocalService.getOrganization(
						GetterUtil.getLong(
							document.get(Field.ENTRY_CLASS_PK)))));

		return Page.of(
			page.getItems(),
			Pagination.of(pagination.getPage(), pagination.getPageSize()),
			page.getTotalCount());
	}

	@Reference
	private DXPEntityDTOConverter _dxpEntityDTOConverter;

	@Reference
	private OrganizationLocalService _organizationLocalService;

}