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

package com.liferay.analytics.dxp.entity.internal.retriever;

import com.liferay.analytics.dxp.entity.dto.v1_0.DXPEntity;
import com.liferay.analytics.dxp.entity.retriever.DXPEntityRetriever;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcos Martins
 */
@Component(
	immediate = true,
	property = "dxp.entity.retriever.class.name=com.liferay.portal.kernel.model.Group",
	service = DXPEntityRetriever.class
)
public class GroupDXPEntityRetriever implements DXPEntityRetriever {

	@Override
	public Page<DXPEntity> getDXPEntitiesPage(
			long companyId, Pagination pagination,
			UnsafeFunction<BaseModel<?>, DXPEntity, Exception>
				transformUnsafeFunction)
		throws Exception {

		List<DXPEntity> dxpEntities = new ArrayList<>();

		List<Group> groups = _groupLocalService.search(
			companyId,
			LinkedHashMapBuilder.<String, Object>put(
				"active", true
			).put(
				"site", true
			).build(),
			pagination.getStartPosition(), pagination.getEndPosition());

		for (Group group : groups) {
			dxpEntities.add(transformUnsafeFunction.apply(group));
		}

		return Page.of(
			dxpEntities, pagination,
			_groupLocalService.getActiveGroupsCount(companyId, true));
	}

	@Reference
	private GroupLocalService _groupLocalService;

}