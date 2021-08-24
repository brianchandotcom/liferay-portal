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

import com.liferay.list.type.exception.DuplicateListTypeEntryException;
import com.liferay.list.type.exception.ListTypeEntryKeyException;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.base.ListTypeEntryLocalServiceBaseImpl;
import com.liferay.list.type.service.persistence.ListTypeDefinitionPersistence;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

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
			long userId, long listTypeDefinitionId, String key,
			Map<Locale, String> nameMap)
		throws PortalException {

		_validateKey(listTypeDefinitionId, key);

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

		listTypeEntry.setKey(key);
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

	private void _validateKey(long listTypeDefinitionId, String key)
		throws PortalException {

		if (Validator.isNull(key)) {
			throw new ListTypeEntryKeyException("Key is null");
		}

		char[] nameCharArray = key.toCharArray();

		for (char c : nameCharArray) {
			if (!Validator.isChar(c) && !Validator.isDigit(c)) {
				throw new ListTypeEntryKeyException(
					"Key must only contain letters and digits");
			}
		}

		if (nameCharArray.length > 41) {
			throw new ListTypeEntryKeyException(
				"Keys must be less than 41 characters");
		}

		ListTypeEntry listTypeEntry = listTypeEntryPersistence.fetchByLTDI_K(
			listTypeDefinitionId, key);

		if (listTypeEntry != null) {
			throw new DuplicateListTypeEntryException(
				StringBundler.concat(
					"Duplicate key ", key,
					" for the same ListTypeDefinitionId ",
					String.valueOf(listTypeDefinitionId)));
		}
	}

	@Reference
	private ListTypeDefinitionPersistence _listTypeDefinitionPersistence;

	@Reference
	private UserLocalService _userLocalService;

}