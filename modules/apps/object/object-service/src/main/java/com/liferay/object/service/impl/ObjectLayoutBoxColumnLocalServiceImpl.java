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

package com.liferay.object.service.impl;

import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectLayoutBoxColumn;
import com.liferay.object.model.ObjectLayoutBoxRow;
import com.liferay.object.service.base.ObjectLayoutBoxColumnLocalServiceBaseImpl;
import com.liferay.object.service.persistence.ObjectFieldPersistence;
import com.liferay.object.service.persistence.ObjectLayoutBoxRowPersistence;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "model.class.name=com.liferay.object.model.ObjectLayoutBoxColumn",
	service = AopService.class
)
public class ObjectLayoutBoxColumnLocalServiceImpl
	extends ObjectLayoutBoxColumnLocalServiceBaseImpl {

	@Override
	public ObjectLayoutBoxColumn addObjectLayoutBoxColumn(
			long userId, long objectFieldId, long objectLayoutBoxRowId,
			int priority)
		throws PortalException {

		ObjectField objectField = _objectFieldPersistence.findByPrimaryKey(
			objectFieldId);

		ObjectLayoutBoxRow objectLayoutBoxRow =
			_objectLayoutBoxRowPersistence.findByPrimaryKey(
				objectLayoutBoxRowId);

		ObjectLayoutBoxColumn objectLayoutBoxColumn =
			objectLayoutBoxColumnPersistence.create(
				counterLocalService.increment());

		User user = _userLocalService.getUser(userId);

		objectLayoutBoxColumn.setCompanyId(user.getCompanyId());
		objectLayoutBoxColumn.setUserId(user.getUserId());
		objectLayoutBoxColumn.setUserName(user.getFullName());

		objectLayoutBoxColumn.setObjectFieldId(objectField.getObjectFieldId());
		objectLayoutBoxColumn.setObjectLayoutBoxRowId(
			objectLayoutBoxRow.getObjectLayoutBoxRowId());
		objectLayoutBoxColumn.setPriority(priority);

		return objectLayoutBoxColumnPersistence.update(objectLayoutBoxColumn);
	}

	@Override
	public List<ObjectLayoutBoxColumn> getObjectLayoutBoxColumns(
		long objectLayoutBoxRowId) {

		return objectLayoutBoxColumnPersistence.findByObjectLayoutBoxRowId(
			objectLayoutBoxRowId);
	}

	@Reference
	private ObjectFieldPersistence _objectFieldPersistence;

	@Reference
	private ObjectLayoutBoxRowPersistence _objectLayoutBoxRowPersistence;

	@Reference
	private UserLocalService _userLocalService;

}