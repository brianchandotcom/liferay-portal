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

package com.liferay.analytics.dxp.entity.internal.batch;

import com.liferay.analytics.dxp.entity.internal.helper.AnalyticsDXPEntityBatchEngineTaskItemDelegateHelper;
import com.liferay.analytics.dxp.entity.internal.odata.entity.AnalyticsDXPEntityEntityModel;
import com.liferay.analytics.dxp.entity.rest.dto.v1_0.DXPEntity;
import com.liferay.batch.engine.BaseBatchEngineTaskItemDelegate;
import com.liferay.batch.engine.BatchEngineTaskItemDelegate;
import com.liferay.batch.engine.pagination.Page;
import com.liferay.batch.engine.pagination.Pagination;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.odata.entity.EntityModel;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcos Martins
 */
@Component(
	immediate = true,
	property = "batch.engine.task.item.delegate.name=user-group-dxp-entities",
	service = BatchEngineTaskItemDelegate.class
)
public class UserGroupAnalyticsDXPEntityBatchEngineTaskItemDelegate
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

		return _analyticsDXPEntityBatchEngineTaskItemDelegateHelper.
			getDXPEntities(
				UserGroup.class.getName(), contextCompany.getCompanyId(),
				filter, pagination, sorts, parameters, search);
	}

	@Reference
	private AnalyticsDXPEntityBatchEngineTaskItemDelegateHelper
		_analyticsDXPEntityBatchEngineTaskItemDelegateHelper;

}