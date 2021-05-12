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

package com.liferay.object.rest.internal.manager.v1_0;

import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.internal.dto.v1_0.converter.ObjectEntryDTOConverter;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier de Arcos
 */
@Component(immediate = true, service = ObjectEntryManager.class)
public class ObjectEntryManager {

	public ObjectEntry addObjectEntry(
			DTOConverterContext dtoConverterContext, long userId, long groupId,
			long objectDefinitionId, ObjectEntry objectEntry)
		throws Exception {

		return _objectEntryDTOConverter.toDTO(
			dtoConverterContext,
			_objectEntryLocalService.addObjectEntry(
				userId, groupId, objectDefinitionId,
				(Map)objectEntry.getProperties(), new ServiceContext()));
	}

	public void deleteObjectEntry(long objectEntryId) throws Exception {
		_objectEntryLocalService.deleteObjectEntry(objectEntryId);
	}

	public Page<ObjectEntry> getObjectEntries(
			long companyId, long objectDefinitionId, Aggregation aggregation,
			DTOConverterContext dtoConverterContext, Filter filter,
			Pagination pagination, String search, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			new HashMap<>(),
			booleanQuery -> {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				booleanFilter.add(
					new TermFilter(
						"objectDefinitionId",
						String.valueOf(objectDefinitionId)),
					BooleanClauseOccur.MUST);
			},
			filter, com.liferay.object.model.ObjectEntry.class, search,
			pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.addVulcanAggregation(aggregation);
				searchContext.setAttribute(
					"objectDefinitionId", objectDefinitionId);
				searchContext.setCompanyId(companyId);
			},
			sorts,
			document -> getObjectEntry(
				dtoConverterContext,
				GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK))));
	}

	public ObjectEntry getObjectEntry(
			DTOConverterContext dtoConverterContext, long objectEntryId)
		throws Exception {

		return _objectEntryDTOConverter.toDTO(
			dtoConverterContext,
			_objectEntryLocalService.getObjectEntry(objectEntryId));
	}

	public ObjectEntry updateObjectEntry(
			DTOConverterContext dtoConverterContext, long userId,
			long objectEntryId, ObjectEntry objectEntry)
		throws Exception {

		return _objectEntryDTOConverter.toDTO(
			dtoConverterContext,
			_objectEntryLocalService.updateObjectEntry(
				userId, objectEntryId, (Map)objectEntry.getProperties(),
				new ServiceContext()));
	}

	@Reference
	private ObjectEntryDTOConverter _objectEntryDTOConverter;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

}