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

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.NoSuchListTypeException;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.model.ClassName;
import com.liferay.portal.model.ListType;
import com.liferay.portal.service.base.ListTypeServiceBaseImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ListTypeServiceImpl extends ListTypeServiceBaseImpl {

	public ListType addListType(String type, String name) {
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(ListType.class);

		query.add(PropertyFactoryUtil.forName("type").eq(type));
		query.add(PropertyFactoryUtil.forName("name").eq(name));

		List<ListType> list = listTypePersistence.findWithDynamicQuery(query);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		int listTypeId = (int)CounterLocalServiceUtil.increment(
			ListType.class.getName());

		ListType listType = listTypePersistence.create(listTypeId);

		listType.setType(type);
		listType.setName(name);
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