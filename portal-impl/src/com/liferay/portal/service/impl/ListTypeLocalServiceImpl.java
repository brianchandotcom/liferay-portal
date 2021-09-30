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

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.NoSuchListTypeException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.service.base.ListTypeLocalServiceBaseImpl;

import java.util.List;
import java.util.Objects;

/**
 * @author Brian Wing Shun Chan
 */
public class ListTypeLocalServiceImpl extends ListTypeLocalServiceBaseImpl {

	@Override
	public ListType addListType(String name, String type) {
		ListType listType = listTypePersistence.fetchByN_T(name, type);

		if (listType != null) {
			return listType;
		}

		long listTypeId = counterLocalService.increment(
			ListType.class.getName());

		listType = listTypePersistence.create(listTypeId);

		listType.setName(name);
		listType.setType(type);

		return listTypePersistence.update(listType);
	}

	@Override
	public ListType getListType(long listTypeId) throws PortalException {
		return listTypePersistence.findByPrimaryKey(listTypeId);
	}

	@Override
	public ListType getListType(String name, String type) {
		return listTypePersistence.fetchByN_T(name, type);
	}

	@Override
	public List<ListType> getListTypes(String type) {
		return listTypePersistence.findByType(type);
	}

	@Override
	@Transactional(readOnly = true)
	public void validate(long listTypeId, long classNameId, String type)
		throws PortalException {

		ClassName className = _classNameLocalService.getClassName(classNameId);

		validate(listTypeId, className.getValue() + type);
	}

	@Override
	@Transactional(readOnly = true)
	public void validate(long listTypeId, String type) throws PortalException {
		ListType listType = listTypePersistence.fetchByPrimaryKey(listTypeId);

		if ((listType == null) || !Objects.equals(listType.getType(), type)) {
			NoSuchListTypeException noSuchListTypeException =
				new NoSuchListTypeException();

			noSuchListTypeException.setType(type);

			throw noSuchListTypeException;
		}
	}

	@BeanReference(type = ClassNameLocalService.class)
	private ClassNameLocalService _classNameLocalService;

}