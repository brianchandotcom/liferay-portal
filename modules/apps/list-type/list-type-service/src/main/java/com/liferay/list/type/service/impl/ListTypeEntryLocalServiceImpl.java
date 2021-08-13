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
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.base.ListTypeEntryLocalServiceBaseImpl;
import com.liferay.list.type.service.persistence.ListTypeDefinitionPersistence;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Albuquerque
 */
@Component(
	property = "model.class.name=com.liferay.list.type.model.ListTypeEntry",
	service = AopService.class
)
public class ListTypeEntryLocalServiceImpl
	extends ListTypeEntryLocalServiceBaseImpl {

	@Override
	public ListTypeEntry addListTypeEntry(
			long userId, long listTypeDefinitionId, Map<Locale, String> nameMap)
		throws PortalException {

		ListTypeEntry listTypeEntry = listTypeEntryPersistence.create(
			counterLocalService.increment());

		User user = _userLocalService.getUser(userId);

		listTypeEntry.setCompanyId(user.getCompanyId());
		listTypeEntry.setUserId(user.getUserId());
		listTypeEntry.setUserName(user.getFullName());

		ListTypeDefinition listTypeDefinition =
			_listTypeDefinitionPersistence.findByPrimaryKey(
				listTypeDefinitionId);

		listTypeEntry.setListTypeDefinitionId(
			listTypeDefinition.getListTypeDefinitionId());

		listTypeEntry.setNameMap(nameMap);

		return listTypeEntryPersistence.update(listTypeEntry);
	}

	@Override
	public List<ListTypeEntry> getListTypeEntries(long listTypeDefinitionId) {
		return listTypeEntryPersistence.findByListTypeDefinitionId(
			listTypeDefinitionId);
	}

	@Override
	public int getListTypeEntriesCount(long listTypeDefinitionId) {
		return listTypeEntryPersistence.countByListTypeDefinitionId(
			listTypeDefinitionId);
	}

	@Reference
	private ListTypeDefinitionPersistence _listTypeDefinitionPersistence;

	@Reference
	private UserLocalService _userLocalService;

}