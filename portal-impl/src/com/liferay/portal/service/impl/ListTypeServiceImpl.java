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

package com.liferay.portal.service.impl;

import com.liferay.portal.NoSuchListTypeException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.model.ClassName;
import com.liferay.portal.model.ListType;
import com.liferay.portal.service.base.ListTypeServiceBaseImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ListTypeServiceImpl extends ListTypeServiceBaseImpl {

	@Override
	public ListType addListType(String name, String type) {
		ListType listType = listTypePersistence.fetchByN_T(name, type);

		if (listType != null) {
			return listType;
		}

		int listTypeId = (int)counterLocalService.increment(
			ListType.class.getName());

		listType = listTypePersistence.create(listTypeId);

		listType.setName(name);
		listType.setType(type);
		listType.setNew(true);

		listTypePersistence.update(listType);

		return listType;
	}

	@Override
	public ListType getListType(int listTypeId) throws PortalException {
		return listTypePersistence.findByPrimaryKey(listTypeId);
	}

	@Override
	public List<ListType> getListTypes(String type) {
		return listTypePersistence.findByType(type);
	}

	@Override
	public void validate(int listTypeId, long classNameId, String type)
		throws PortalException {

		ClassName className = classNameLocalService.getClassName(classNameId);

		validate(listTypeId, className.getValue() + type);
	}

	@Override
	public void validate(int listTypeId, String type) throws PortalException {
		ListType listType = listTypePersistence.fetchByPrimaryKey(listTypeId);

		if ((listType == null) || !listType.getType().equals(type)) {
			NoSuchListTypeException nslte = new NoSuchListTypeException();

			nslte.setType(type);

			throw nslte;
		}
	}

}