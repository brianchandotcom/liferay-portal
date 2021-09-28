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
import com.liferay.list.type.service.ListTypeEntryService;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Gabriel Albuquerque
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/list-type-entry.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {ListTypeEntryResource.class, NestedFieldSupport.class}
)
public class ListTypeEntryResourceImpl
	extends BaseListTypeEntryResourceImpl implements NestedFieldSupport {

	@Override
	public void deleteListTypeEntry(Long listTypeEntryId) throws Exception {
		_listTypeEntryService.deleteListTypeEntry(listTypeEntryId);
	}

	@NestedField(
		parentClass = ListTypeDefinition.class, value = "listTypeEntries"
	)
	@Override
	public Page<ListTypeEntry> getListTypeDefinitionListTypeEntriesPage(
			Long listTypeDefinitionId, String search, Pagination pagination)
		throws Exception {

		return SearchUtil.search(
			HashMapBuilder.put(
				"create",
				addAction(
					ActionKeys.UPDATE, "postListTypeDefinitionListTypeEntry",
					com.liferay.list.type.model.ListTypeDefinition.class.
						getName(),
					listTypeDefinitionId)
			).put(
				"get",
				addAction(
					ActionKeys.VIEW, "getListTypeDefinitionListTypeEntriesPage",
					com.liferay.list.type.model.ListTypeDefinition.class.
						getName(),
					listTypeDefinitionId)
			).build(),
			booleanQuery -> {
			},
			null, com.liferay.list.type.model.ListTypeEntry.class.getName(),
			search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setAttribute(Field.NAME, search);
				searchContext.setAttribute("key", search);
				searchContext.setAttribute(
					"listTypeDefinitionId", listTypeDefinitionId);
				searchContext.setCompanyId(contextCompany.getCompanyId());
			},
			null,
			document -> {
				com.liferay.list.type.model.ListTypeEntry listTypeEntry =
					_listTypeEntryService.getListTypeEntry(
						GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)));

				return ListTypeEntryUtil.toListTypeEntry(
					_getActions(listTypeEntry),
					contextAcceptLanguage.getPreferredLocale(), listTypeEntry);
			});
	}

	@Override
	public ListTypeEntry getListTypeEntry(Long listTypeEntryId)
		throws Exception {

		return ListTypeEntryUtil.toListTypeEntry(
			null, contextAcceptLanguage.getPreferredLocale(),
			_listTypeEntryService.getListTypeEntry(listTypeEntryId));
	}

	@Override
	public ListTypeEntry postListTypeDefinitionListTypeEntry(
			Long listTypeDefinitionId, ListTypeEntry listTypeEntry)
		throws Exception {

		return ListTypeEntryUtil.toListTypeEntry(
			null, contextAcceptLanguage.getPreferredLocale(),
			_listTypeEntryService.addListTypeEntry(
				listTypeDefinitionId, listTypeEntry.getKey(),
				LocalizedMapUtil.getLocalizedMap(
					listTypeEntry.getName_i18n())));
	}

	@Override
	public ListTypeEntry putListTypeEntry(
			Long listTypeEntryId, ListTypeEntry listTypeEntry)
		throws Exception {

		return ListTypeEntryUtil.toListTypeEntry(
			null, contextAcceptLanguage.getPreferredLocale(),
			_listTypeEntryService.updateListTypeEntry(
				listTypeEntryId,
				LocalizedMapUtil.getLocalizedMap(
					listTypeEntry.getName_i18n())));
	}

	private Map<String, Map<String, String>> _getActions(
		com.liferay.list.type.model.ListTypeEntry serviceBuilderListTypeEntry) {

		return HashMapBuilder.<String, Map<String, String>>put(
			"delete",
			addAction(
				ActionKeys.DELETE, "deleteListTypeEntry",
				com.liferay.list.type.model.ListTypeDefinition.class.getName(),
				serviceBuilderListTypeEntry.getListTypeDefinitionId())
		).put(
			"get",
			addAction(
				ActionKeys.VIEW, "getListTypeEntry",
				com.liferay.list.type.model.ListTypeDefinition.class.getName(),
				serviceBuilderListTypeEntry.getListTypeDefinitionId())
		).put(
			"update",
			addAction(
				ActionKeys.UPDATE, "putListTypeEntry",
				com.liferay.list.type.model.ListTypeDefinition.class.getName(),
				serviceBuilderListTypeEntry.getListTypeDefinitionId())
		).build();
	}

	@Reference
	private ListTypeEntryService _listTypeEntryService;

}