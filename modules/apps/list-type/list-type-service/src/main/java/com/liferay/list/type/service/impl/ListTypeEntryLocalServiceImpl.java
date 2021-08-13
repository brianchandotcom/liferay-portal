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
import com.liferay.list.type.exception.ListTypeEntryNameException;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.base.ListTypeEntryLocalServiceBaseImpl;
import com.liferay.list.type.service.persistence.ListTypeDefinitionPersistence;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
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
			long companyId, long listTypeDefinitionId,
			Map<Locale, String> labelMap, String name)
		throws PortalException {

		_validateName(listTypeDefinitionId, name);

		ListTypeEntry listTypeEntry = listTypeEntryPersistence.create(
			counterLocalService.increment());

		listTypeEntry.setCompanyId(companyId);

		ListTypeDefinition listTypeDefinition =
			_listTypeDefinitionPersistence.findByPrimaryKey(
				listTypeDefinitionId);

		listTypeEntry.setListTypeDefinitionId(
			listTypeDefinition.getListTypeDefinitionId());

		listTypeEntry.setLabelMap(labelMap);
		listTypeEntry.setName(name);

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

	private void _validateName(long listTypeDefinitionId, String name)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new ListTypeEntryNameException("Name is null");
		}

		char[] nameCharArray = name.toCharArray();

		for (char c : nameCharArray) {
			if (!Validator.isChar(c) && !Validator.isDigit(c)) {
				throw new ListTypeEntryNameException(
					"Name must only contain letters and digits");
			}
		}

		if (nameCharArray.length > 41) {
			throw new ListTypeEntryNameException(
				"Names must be less than 41 characters");
		}

		ListTypeEntry listTypeEntry = listTypeEntryPersistence.fetchByLTDI_N(
			listTypeDefinitionId, name);

		if (listTypeEntry != null) {
			throw new DuplicateListTypeEntryException(
				StringBundler.concat(
					"Duplicate name ", name,
					" for the same ListTypeDefinitionId ",
					String.valueOf(listTypeDefinitionId)));
		}
	}

	@Reference
	private ListTypeDefinitionPersistence _listTypeDefinitionPersistence;

}