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

package com.liferay.headless.admin.list.type.internal.resource.v1_0;

import com.liferay.headless.admin.list.type.dto.v1_0.ListTypeDefinition;
import com.liferay.headless.admin.list.type.dto.v1_0.ListTypeEntry;
import com.liferay.headless.admin.list.type.internal.dto.v1_0.util.ListTypeEntryUtil;
import com.liferay.headless.admin.list.type.resource.v1_0.ListTypeEntryResource;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Gabriel Albuquerque
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/list-type-entry.properties",
	scope = ServiceScope.PROTOTYPE, service = ListTypeEntryResource.class
)
public class ListTypeEntryResourceImpl extends BaseListTypeEntryResourceImpl {

	@NestedField(
		parentClass = ListTypeDefinition.class, value = "listTypeEntries"
	)
	@Override
	public Page<ListTypeEntry> getListTypeDefinitionListTypeEntriesPage(
		Long listTypeDefinitionId, Pagination pagination) {

		return Page.of(
			transform(
				_listTypeEntryLocalService.getListTypeEntries(
					listTypeDefinitionId, pagination.getStartPosition(),
					pagination.getEndPosition()),
				ListTypeEntryUtil::toListTypeEntry),
			pagination,
			_listTypeEntryLocalService.getListTypeEntriesCount(
				listTypeDefinitionId));
	}

	@Override
	public ListTypeEntry postListTypeDefinitionListTypeEntry(
			Long listTypeDefinitionId, ListTypeEntry listTypeEntry)
		throws Exception {

		return ListTypeEntryUtil.toListTypeEntry(
			_listTypeEntryLocalService.addListTypeEntry(
				contextUser.getUserId(), listTypeDefinitionId,
				listTypeEntry.getKey(),
				LocalizedMapUtil.getLocalizedMap(listTypeEntry.getName())));
	}

	@Reference
	private ListTypeEntryLocalService _listTypeEntryLocalService;

}