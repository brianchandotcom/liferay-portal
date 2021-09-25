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

package com.liferay.object.internal.action;

import com.liferay.object.action.ObjectAction;
import com.liferay.object.action.ObjectActionRequest;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.io.Serializable;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(service = ObjectAction.class)
public class UpdateObjectEntryFieldObjectActionImpl implements ObjectAction {

	@Override
	public void execute(ObjectActionRequest objectActionRequest)
		throws Exception {

		Map<String, Serializable> properties =
			objectActionRequest.getProperties();

		long classPK = GetterUtil.getLong(properties.get("classPK"));

		_objectEntryLocalService.updateObjectEntry(
			objectActionRequest.getUserId(), classPK,
			HashMapBuilder.put(
				GetterUtil.getString(properties.get("fieldName")),
				properties.get("fieldName")
			).build(),
			new ServiceContext() {
				{
					setUserId(objectActionRequest.getUserId());
				}
			});
	}

	@Override
	public String getName() {
		return "update-entry";
	}

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

}