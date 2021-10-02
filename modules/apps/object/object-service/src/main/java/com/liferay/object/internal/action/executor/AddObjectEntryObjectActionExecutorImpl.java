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

package com.liferay.object.internal.action.executor;

import com.liferay.object.action.executor.ObjectActionExecutor;
import com.liferay.object.action.request.ObjectActionRequest;
import com.liferay.object.constants.ObjectActionExecutorConstants;
import com.liferay.object.internal.action.settings.AddObjectEntryObjectActionSettings;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;

import java.io.Serializable;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(enabled = false, service = ObjectActionExecutor.class)
public class AddObjectEntryObjectActionExecutorImpl
	implements ObjectActionExecutor {

	@Override
	public void execute(ObjectActionRequest objectActionRequest)
		throws Exception {

		_objectEntryLocalService.addObjectEntry(
			objectActionRequest.getUserId(),
			GetterUtil.getLong(
				objectActionRequest.getParameterValue("groupId")),
			GetterUtil.getLong(
				objectActionRequest.getParameterValue("objectDefinitionId")),
			(Map<String, Serializable>)objectActionRequest.getParameterValue(
				"values"),
			new ServiceContext() {
				{
					setUserId(objectActionRequest.getUserId());
				}
			});
	}

	@Override
	public String getKey() {
		return ObjectActionExecutorConstants.KEY_ADD_OBJECT_ENTRY;
	}

	@Override
	public Class<?> getSettings() {
		return AddObjectEntryObjectActionSettings.class;
	}

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

}