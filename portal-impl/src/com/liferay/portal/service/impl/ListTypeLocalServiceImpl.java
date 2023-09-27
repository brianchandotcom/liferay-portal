/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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