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

package com.liferay.list.type.service.impl;

import com.liferay.list.type.constants.ListTypeActionKeys;
import com.liferay.list.type.constants.ListTypeConstants;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.list.type.service.base.ListTypeEntryServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Albuquerque
 */
@Component(
	property = {
		"json.web.service.context.name=listtype",
		"json.web.service.context.path=ListTypeEntry"
	},
	service = AopService.class
)
public class ListTypeEntryServiceImpl extends ListTypeEntryServiceBaseImpl {

	@Override
	public ListTypeEntry addListTypeEntry(
			long userId, long listTypeDefinitionId, String key,
			Map<Locale, String> nameMap)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			ListTypeActionKeys.ADD_LIST_TYPE_DEFINITION);

		return _listTypeEntryLocalService.addListTypeEntry(
			userId, listTypeDefinitionId, key, nameMap);
	}

	@Override
	public ListTypeEntry deleteListTypeEntry(long listTypeEntryId)
		throws PortalException {

		ListTypeEntry listTypeEntry = listTypeEntryPersistence.findByPrimaryKey(
			listTypeEntryId);

		_listTypeDefinitionModelResourcePermission.check(
			getPermissionChecker(), listTypeEntry.getListTypeDefinitionId(),
			ActionKeys.DELETE);

		return _listTypeEntryLocalService.deleteListTypeEntry(listTypeEntryId);
	}

	@Override
	public List<ListTypeEntry> getListTypeEntries(
		long listTypeDefinitionId, int start, int end) {

		return _listTypeEntryLocalService.getListTypeEntries(
			listTypeDefinitionId, start, end);
	}

	@Override
	public int getListTypeEntriesCount(long listTypeDefinitionId) {
		return _listTypeEntryLocalService.getListTypeEntriesCount(
			listTypeDefinitionId);
	}

	@Override
	public ListTypeEntry getListTypeEntry(long listTypeEntryId)
		throws PortalException {

		ListTypeEntry listTypeEntry = listTypeEntryPersistence.findByPrimaryKey(
			listTypeEntryId);

		_listTypeDefinitionModelResourcePermission.check(
			getPermissionChecker(), listTypeEntry.getListTypeDefinitionId(),
			ActionKeys.VIEW);

		return _listTypeEntryLocalService.getListTypeEntry(listTypeEntryId);
	}

	@Override
	public ListTypeEntry updateListTypeEntry(
			long listTypeEntryId, Map<Locale, String> nameMap)
		throws PortalException {

		ListTypeEntry listTypeEntry = listTypeEntryPersistence.findByPrimaryKey(
			listTypeEntryId);

		_listTypeDefinitionModelResourcePermission.check(
			getPermissionChecker(), listTypeEntry.getListTypeDefinitionId(),
			ActionKeys.UPDATE);

		return _listTypeEntryLocalService.updateListTypeEntry(
			listTypeEntryId, nameMap);
	}

	@Reference(
		target = "(model.class.name=com.liferay.list.type.model.ListTypeDefinition)"
	)
	private ModelResourcePermission<ListTypeDefinition>
		_listTypeDefinitionModelResourcePermission;

	@Reference
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	@Reference(
		target = "(resource.name=" + ListTypeConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}