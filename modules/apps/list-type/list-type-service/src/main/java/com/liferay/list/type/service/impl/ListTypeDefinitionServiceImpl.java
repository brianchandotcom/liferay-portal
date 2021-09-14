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
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.list.type.service.base.ListTypeDefinitionServiceBaseImpl;
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
		"json.web.service.context.path=ListTypeDefinition"
	},
	service = AopService.class
)
public class ListTypeDefinitionServiceImpl
	extends ListTypeDefinitionServiceBaseImpl {

	@Override
	public ListTypeDefinition addListTypeDefinition(
			long userId, Map<Locale, String> nameMap)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			ListTypeActionKeys.ADD_LIST_TYPE_DEFINITION);

		return _listTypeDefinitionLocalService.addListTypeDefinition(
			userId, nameMap);
	}

	@Override
	public ListTypeDefinition deleteListTypeDefinition(
			ListTypeDefinition listTypeDefinition)
		throws PortalException {

		_listTypeDefinitionModelResourcePermission.check(
			getPermissionChecker(),
			listTypeDefinition.getListTypeDefinitionId(), ActionKeys.DELETE);

		return _listTypeDefinitionLocalService.deleteListTypeDefinition(
			listTypeDefinition);
	}

	@Override
	public ListTypeDefinition deleteListTypeDefinition(
			long listTypeDefinitionId)
		throws PortalException {

		_listTypeDefinitionModelResourcePermission.check(
			getPermissionChecker(), listTypeDefinitionId, ActionKeys.DELETE);

		return _listTypeDefinitionLocalService.deleteListTypeDefinition(
			listTypeDefinitionId);
	}

	@Override
	public ListTypeDefinition getListTypeDefinition(long listTypeDefinitionId)
		throws PortalException {

		_listTypeDefinitionModelResourcePermission.check(
			getPermissionChecker(), listTypeDefinitionId, ActionKeys.VIEW);

		return _listTypeDefinitionLocalService.getListTypeDefinition(
			listTypeDefinitionId);
	}

	@Override
	public List<ListTypeDefinition> getListTypeDefinitions(int start, int end) {
		return _listTypeDefinitionLocalService.getListTypeDefinitions(
			start, end);
	}

	@Override
	public int getListTypeDefinitionsCount() {
		return _listTypeDefinitionLocalService.getListTypeDefinitionsCount();
	}

	@Override
	public ListTypeDefinition updateListTypeDefinition(
			long listTypeDefinitionId, Map<Locale, String> nameMap)
		throws PortalException {

		_listTypeDefinitionModelResourcePermission.check(
			getPermissionChecker(), listTypeDefinitionId, ActionKeys.UPDATE);

		return _listTypeDefinitionLocalService.updateListTypeDefinition(
			listTypeDefinitionId, nameMap);
	}

	@Reference
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.list.type.model.ListTypeDefinition)"
	)
	private ModelResourcePermission<ListTypeDefinition>
		_listTypeDefinitionModelResourcePermission;

	@Reference(
		target = "(resource.name=" + ListTypeConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}