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

import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.service.base.ListTypeDefinitionLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Albuquerque
 */
@Component(
	property = "model.class.name=com.liferay.list.type.model.ListTypeDefinition",
	service = AopService.class
)
public class ListTypeDefinitionLocalServiceImpl
	extends ListTypeDefinitionLocalServiceBaseImpl {

	@Override
	public ListTypeDefinition addListTypeDefinition(
			long userId, Map<Locale, String> nameMap)
		throws PortalException {

		ListTypeDefinition listTypeDefinition =
			listTypeDefinitionPersistence.create(
				counterLocalService.increment());

		User user = _userLocalService.getUser(userId);

		listTypeDefinition.setCompanyId(user.getCompanyId());
		listTypeDefinition.setUserId(user.getUserId());
		listTypeDefinition.setUserName(user.getFullName());

		listTypeDefinition.setNameMap(nameMap);

		return listTypeDefinitionPersistence.update(listTypeDefinition);
	}

	@Override
	public ListTypeDefinition updateListTypeDefinition(
			long listTypeDefinitionId, Map<Locale, String> nameMap)
		throws PortalException {

		ListTypeDefinition listTypeDefinition =
			listTypeDefinitionPersistence.findByPrimaryKey(
				listTypeDefinitionId);

		listTypeDefinition.setNameMap(nameMap);

		return listTypeDefinitionPersistence.update(listTypeDefinition);
	}

	@Reference
	private UserLocalService _userLocalService;

}