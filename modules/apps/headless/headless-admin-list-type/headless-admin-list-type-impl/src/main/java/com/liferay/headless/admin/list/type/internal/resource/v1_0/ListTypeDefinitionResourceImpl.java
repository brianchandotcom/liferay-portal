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
import com.liferay.headless.admin.list.type.resource.v1_0.ListTypeDefinitionResource;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
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
	properties = "OSGI-INF/liferay/rest/v1_0/list-type-definition.properties",
	scope = ServiceScope.PROTOTYPE, service = ListTypeDefinitionResource.class
)
public class ListTypeDefinitionResourceImpl
	extends BaseListTypeDefinitionResourceImpl {

	@Override
	public void deleteListTypeDefinition(Long listTypeDefinitionId)
		throws Exception {

		_listTypeDefinitionLocalService.deleteListTypeDefinition(
			listTypeDefinitionId);
	}

	@Override
	public ListTypeDefinition getListTypeDefinition(Long listTypeDefinitionId)
		throws Exception {

		return _toListTypeDefinition(
			_listTypeDefinitionLocalService.getListTypeDefinition(
				listTypeDefinitionId));
	}

	@Override
	public Page<ListTypeDefinition> getListTypeDefinitionsPage(
		Pagination pagination) {

		return Page.of(
			transform(
				_listTypeDefinitionLocalService.getListTypeDefinitions(
					pagination.getStartPosition(), pagination.getEndPosition()),
				this::_toListTypeDefinition),
			pagination,
			_listTypeDefinitionLocalService.getListTypeDefinitionsCount());
	}

	@Override
	public ListTypeDefinition postListTypeDefinition(
			ListTypeDefinition listTypeDefinition)
		throws Exception {

		return _toListTypeDefinition(
			_listTypeDefinitionLocalService.addListTypeDefinition(
				contextUser.getUserId(),
				LocalizedMapUtil.getLocalizedMap(
					listTypeDefinition.getName_i18n())));
	}

	@Override
	public ListTypeDefinition putListTypeDefinition(
			Long listTypeDefinitionId, ListTypeDefinition listTypeDefinition)
		throws Exception {

		return _toListTypeDefinition(
			_listTypeDefinitionLocalService.updateListTypeDefinition(
				listTypeDefinitionId,
				LocalizedMapUtil.getLocalizedMap(
					listTypeDefinition.getName_i18n())));
	}

	private ListTypeDefinition _toListTypeDefinition(
		com.liferay.list.type.model.ListTypeDefinition
			serviceBuilderListTypeEntry) {

		return new ListTypeDefinition() {
			{
				dateCreated = serviceBuilderListTypeEntry.getCreateDate();
				dateModified = serviceBuilderListTypeEntry.getModifiedDate();
				id = serviceBuilderListTypeEntry.getListTypeDefinitionId();
				listTypeEntries = transformToArray(
					_listTypeEntryLocalService.getListTypeEntries(
						serviceBuilderListTypeEntry.getListTypeDefinitionId(),
						QueryUtil.ALL_POS, QueryUtil.ALL_POS),
					listTypeEntry -> ListTypeEntryUtil.toListTypeEntry(
						contextAcceptLanguage.getPreferredLocale(),
						listTypeEntry),
					ListTypeEntry.class);
				name = serviceBuilderListTypeEntry.getName(
					contextAcceptLanguage.getPreferredLocale());
				name_i18n = LocalizedMapUtil.getI18nMap(
					serviceBuilderListTypeEntry.getNameMap());
			}
		};
	}

	@Reference
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	@Reference
	private ListTypeEntryLocalService _listTypeEntryLocalService;

}