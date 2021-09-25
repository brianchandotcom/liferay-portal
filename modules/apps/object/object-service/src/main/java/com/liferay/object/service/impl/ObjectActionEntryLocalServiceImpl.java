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

import com.liferay.object.model.ObjectActionEntry;
import com.liferay.object.service.base.ObjectActionEntryLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 */
@Component(
	property = "model.class.name=com.liferay.object.model.ObjectActionEntry",
	service = AopService.class
)
public class ObjectActionEntryLocalServiceImpl
	extends ObjectActionEntryLocalServiceBaseImpl {

	@Override
	public List<ObjectActionEntry> getObjectActionEntries(
		long objectDefinitionId, String triggerName) {

		return objectActionEntryPersistence.findByO_A_T(
			objectDefinitionId, true, triggerName);
	}

}