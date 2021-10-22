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
import com.liferay.list.type.exception.ListTypeEntryNameException;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.base.ListTypeEntryLocalServiceBaseImpl;
import com.liferay.list.type.service.persistence.ListTypeDefinitionPersistence;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
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

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ListTypeEntry addListTypeEntry(
			long userId, long listTypeDefinitionId, String key,
			Map<Locale, String> nameMap)
		throws PortalException {

		_validateKey(listTypeDefinitionId, key);
		_validateName(nameMap);

		ListTypeEntry listTypeEntry = listTypeEntryPersistence.create(
			counterLocalService.increment());

		User user = _userLocalService.getUser(userId);

		listTypeEntry.setCompanyId(user.getCompanyId());
		listTypeEntry.setUserId(user.getUserId());
		listTypeEntry.setUserName(user.getFullName());

		listTypeEntry.setListTypeDefinitionId(listTypeDefinitionId);
		listTypeEntry.setKey(key);
		listTypeEntry.setNameMap(nameMap);

		return listTypeEntryPersistence.update(listTypeEntry);
	}

	@Override
	public ListTypeEntry fetchListTypeEntry(
		long listTypeDefinitionId, String key) {

		return listTypeEntryPersistence.fetchByLTDI_K(
			listTypeDefinitionId, key);
	}

	@Override
	public List<ListTypeEntry> getListTypeEntries(long listTypeDefinitionId) {
		return listTypeEntryPersistence.findByListTypeDefinitionId(
			listTypeDefinitionId);
	}

	@Override
	public List<ListTypeEntry> getListTypeEntries(
		long listTypeDefinitionId, int start, int end) {

		return listTypeEntryPersistence.findByListTypeDefinitionId(
			listTypeDefinitionId, start, end);
	}

	@Override
	public int getListTypeEntriesCount(long listTypeDefinitionId) {
		return listTypeEntryPersistence.countByListTypeDefinitionId(
			listTypeDefinitionId);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ListTypeEntry updateListTypeEntry(
			long listTypeEntryId, Map<Locale, String> nameMap)
		throws PortalException {

		_validateName(nameMap);

		ListTypeEntry listTypeEntry = listTypeEntryPersistence.findByPrimaryKey(
			listTypeEntryId);

		listTypeEntry.setNameMap(nameMap);

		return listTypeEntryPersistence.update(listTypeEntry);
	}

	private void _validateKey(long listTypeDefinitionId, String key)
		throws PortalException {

		_listTypeDefinitionPersistence.findByPrimaryKey(listTypeDefinitionId);

		if (Validator.isNull(key)) {
			throw new ListTypeEntryKeyException("Key is null");
		}

		char[] keyCharArray = key.toCharArray();

		for (char c : keyCharArray) {
			if (!Validator.isChar(c) && !Validator.isDigit(c)) {
				throw new ListTypeEntryKeyException(
					"Key must only contain letters and digits");
			}
		}

		ListTypeEntry listTypeEntry = listTypeEntryPersistence.fetchByLTDI_K(
			listTypeDefinitionId, key);

		if (listTypeEntry != null) {
			throw new DuplicateListTypeEntryException("Duplicate key " + key);
		}
	}

	private void _validateName(Map<Locale, String> nameMap)
		throws PortalException {

		Locale locale = LocaleUtil.getSiteDefault();

		if ((nameMap == null) || Validator.isNull(nameMap.get(locale))) {
			throw new ListTypeEntryNameException(
				"Name is null for locale " + locale.getDisplayName());
		}
	}

	@Reference
	private ListTypeDefinitionPersistence _listTypeDefinitionPersistence;

	@Reference
	private UserLocalService _userLocalService;

}