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
import com.liferay.object.model.ObjectLayoutTab;
import com.liferay.object.service.base.ObjectLayoutBoxLocalServiceBaseImpl;
import com.liferay.object.service.persistence.ObjectLayoutTabPersistence;
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
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.object.model.ObjectLayoutBox",
	service = AopService.class
)
public class ObjectLayoutBoxLocalServiceImpl
	extends ObjectLayoutBoxLocalServiceBaseImpl {

	@Override
	public ObjectLayoutBox addObjectLayoutBox(
			long userId, long objectLayoutTabId, boolean collapsable,
			Map<Locale, String> nameMap, int priority)
		throws PortalException {

		ObjectLayoutTab objectLayoutTab =
			_objectLayoutTabPersistence.findByPrimaryKey(objectLayoutTabId);

		ObjectLayoutBox objectLayoutBox = objectLayoutBoxPersistence.create(
			counterLocalService.increment());

		User user = _userLocalService.getUser(userId);

		objectLayoutBox.setCompanyId(user.getCompanyId());
		objectLayoutBox.setUserId(user.getUserId());
		objectLayoutBox.setUserName(user.getFullName());

		objectLayoutBox.setObjectLayoutTabId(
			objectLayoutTab.getObjectLayoutTabId());
		objectLayoutBox.setCollapsable(collapsable);
		objectLayoutBox.setNameMap(nameMap);
		objectLayoutBox.setPriority(priority);

		return objectLayoutBoxPersistence.update(objectLayoutBox);
	}

	@Override
	public List<ObjectLayoutBox> getObjectLayoutBoxes(long objectLayoutTabId) {
		return objectLayoutBoxPersistence.findByObjectLayoutTabId(
			objectLayoutTabId);
	}

	@Reference
	private ObjectLayoutTabPersistence _objectLayoutTabPersistence;

	@Reference
	private UserLocalService _userLocalService;

}