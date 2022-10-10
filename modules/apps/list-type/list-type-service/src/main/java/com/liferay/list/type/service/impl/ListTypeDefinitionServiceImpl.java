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
	public ListTypeDefinition addListTypeDefinition(Map<Locale, String> nameMap)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			ListTypeActionKeys.ADD_LIST_TYPE_DEFINITION);

		return listTypeDefinitionLocalService.addListTypeDefinition(
			getUserId(), nameMap);
	}

	@Override
	public ListTypeDefinition deleteListTypeDefinition(
			ListTypeDefinition listTypeDefinition)
		throws PortalException {

		_listTypeDefinitionModelResourcePermission.check(
			getPermissionChecker(),
			listTypeDefinition.getListTypeDefinitionId(), ActionKeys.DELETE);

		return listTypeDefinitionLocalService.deleteListTypeDefinition(
			listTypeDefinition);
	}

	@Override
	public ListTypeDefinition deleteListTypeDefinition(
			long listTypeDefinitionId)
		throws PortalException {

		_listTypeDefinitionModelResourcePermission.check(
			getPermissionChecker(), listTypeDefinitionId, ActionKeys.DELETE);

		return listTypeDefinitionLocalService.deleteListTypeDefinition(
			listTypeDefinitionId);
	}

	@Override
	public ListTypeDefinition getListTypeDefinition(long listTypeDefinitionId)
		throws PortalException {

		_listTypeDefinitionModelResourcePermission.check(
			getPermissionChecker(), listTypeDefinitionId, ActionKeys.VIEW);

		return listTypeDefinitionLocalService.getListTypeDefinition(
			listTypeDefinitionId);
	}

	@Override
	public List<ListTypeDefinition> getListTypeDefinitions(int start, int end) {
		return listTypeDefinitionLocalService.getListTypeDefinitions(
			start, end);
	}

	@Override
	public int getListTypeDefinitionsCount() {
		return listTypeDefinitionLocalService.getListTypeDefinitionsCount();
	}

	@Override
	public ListTypeDefinition updateListTypeDefinition(
			String externalReferenceCode, long listTypeDefinitionId,
			Map<Locale, String> nameMap)
		throws PortalException {

		_listTypeDefinitionModelResourcePermission.check(
			getPermissionChecker(), listTypeDefinitionId, ActionKeys.UPDATE);

		return listTypeDefinitionLocalService.updateListTypeDefinition(
			externalReferenceCode, listTypeDefinitionId, nameMap);
	}

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