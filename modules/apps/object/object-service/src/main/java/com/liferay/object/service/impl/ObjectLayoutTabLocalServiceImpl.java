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

import com.liferay.object.model.ObjectLayoutTab;
import com.liferay.object.service.base.ObjectLayoutTabLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.model.User;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Feliphe Marinho
 */
@Component(
	property = "model.class.name=com.liferay.object.model.ObjectLayoutTab",
	service = AopService.class
)
public class ObjectLayoutTabLocalServiceImpl
	extends ObjectLayoutTabLocalServiceBaseImpl {

	@Override
	public ObjectLayoutTab addObjectLayoutTab(
		User user, long objectLayoutId, long objectRelationshipId,
		Map<Locale, String> nameMap, int priority) {

		ObjectLayoutTab objectLayoutTab = objectLayoutTabPersistence.create(
			counterLocalService.increment());

		objectLayoutTab.setCompanyId(user.getCompanyId());
		objectLayoutTab.setUserId(user.getUserId());
		objectLayoutTab.setUserName(user.getFullName());
		objectLayoutTab.setObjectLayoutId(objectLayoutId);
		objectLayoutTab.setObjectRelationshipId(objectRelationshipId);
		objectLayoutTab.setNameMap(nameMap);
		objectLayoutTab.setPriority(priority);

		return objectLayoutTabPersistence.update(objectLayoutTab);
	}

	@Override
	public void deleteObjectLayoutObjectLayoutTabs(long objectLayoutId) {
		objectLayoutTabPersistence.removeByObjectLayoutId(objectLayoutId);
	}

	@Override
	public List<ObjectLayoutTab> getObjectLayoutObjectLayoutTabs(
		long objectLayoutId) {

		return objectLayoutTabPersistence.findByObjectLayoutId(objectLayoutId);
	}

}