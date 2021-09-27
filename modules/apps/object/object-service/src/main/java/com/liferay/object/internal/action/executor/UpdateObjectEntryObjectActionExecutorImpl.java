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
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(service = ObjectActionExecutor.class)
public class UpdateObjectEntryObjectActionExecutorImpl
	implements ObjectActionExecutor {

	@Override
	public void execute(ObjectActionRequest objectActionRequest)
		throws Exception {

		_objectEntryLocalService.updateObjectEntry(
			objectActionRequest.getUserId(),
			GetterUtil.getLong(
				objectActionRequest.getParameterValue("classPK")),
			HashMapBuilder.put(
				String.valueOf(
					objectActionRequest.getParameterValue("objectFieldName")),
				objectActionRequest.getParameterValue("objectFieldValue")
			).build(),
			new ServiceContext() {
				{
					setUserId(objectActionRequest.getUserId());
				}
			});
	}

	@Override
	public String getKey() {
		return "updateObjectEntry";
	}

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

}