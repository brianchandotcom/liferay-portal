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

package com.liferay.analytics.dxp.entities.exporter.internal.retriever;

import com.liferay.analytics.dxp.entities.exporter.dto.v1_0.DXPEntity;
import com.liferay.analytics.dxp.entities.exporter.retriever.DXPEntityRetriever;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcos Martins
 */
@Component(
	immediate = true,
	property = "dxp.entity.retriever.class.name=com.liferay.portal.kernel.model.UserGroup",
	service = DXPEntityRetriever.class
)
public class UserGroupDXPEntityRetriever implements DXPEntityRetriever {

	@Override
	public Page<DXPEntity> getDXPEntitiesPage(
			long companyId, Pagination pagination,
			UnsafeFunction<BaseModel<?>, DXPEntity, Exception>
				transformUnsafeFunction)
		throws Exception {

		return SearchUtil.search(
			null, booleanQuery -> booleanQuery.getPreBooleanFilter(), null,
			UserGroup.class.getName(), null, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> searchContext.setCompanyId(companyId), null,
			document -> transformUnsafeFunction.apply(
				_userGroupLocalService.getUserGroup(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	@Reference
	private UserGroupLocalService _userGroupLocalService;

}