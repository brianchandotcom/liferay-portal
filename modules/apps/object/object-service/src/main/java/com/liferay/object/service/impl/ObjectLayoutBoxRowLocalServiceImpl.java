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

import com.liferay.object.model.ObjectLayoutBox;
import com.liferay.object.model.ObjectLayoutBoxRow;
import com.liferay.object.service.base.ObjectLayoutBoxRowLocalServiceBaseImpl;
import com.liferay.object.service.persistence.ObjectLayoutBoxPersistence;
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
	property = "model.class.name=com.liferay.object.model.ObjectLayoutBoxRow",
	service = AopService.class
)
public class ObjectLayoutBoxRowLocalServiceImpl
	extends ObjectLayoutBoxRowLocalServiceBaseImpl {

	@Override
	public ObjectLayoutBoxRow addObjectLayoutBoxRow(
			long userId, long objectLayoutBoxId, int priority)
		throws PortalException {

		ObjectLayoutBox objectLayoutBox =
			_objectLayoutBoxPersistence.findByPrimaryKey(objectLayoutBoxId);

		ObjectLayoutBoxRow objectLayoutBoxRow =
			objectLayoutBoxRowPersistence.create(
				counterLocalService.increment());

		User user = _userLocalService.getUser(userId);

		objectLayoutBoxRow.setCompanyId(user.getCompanyId());
		objectLayoutBoxRow.setUserId(user.getUserId());
		objectLayoutBoxRow.setUserName(user.getFullName());

		objectLayoutBoxRow.setObjectLayoutBoxId(
			objectLayoutBox.getObjectLayoutBoxId());
		objectLayoutBoxRow.setPriority(priority);

		return objectLayoutBoxRowPersistence.update(objectLayoutBoxRow);
	}

	@Override
	public List<ObjectLayoutBoxRow> getObjectLayoutBoxRows(
		long objectLayoutBoxId) {

		return objectLayoutBoxRowPersistence.findByObjectLayoutBoxId(
			objectLayoutBoxId);
	}

	@Reference
	private ObjectLayoutBoxPersistence _objectLayoutBoxPersistence;

	@Reference
	private UserLocalService _userLocalService;

}