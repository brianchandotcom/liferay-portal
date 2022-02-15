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

package com.liferay.object.admin.rest.internal.resource.v1_0;

import com.liferay.object.admin.rest.dto.v1_0.ObjectFieldSetting;
import com.liferay.object.admin.rest.internal.dto.v1_0.util.ObjectFieldSettingUtil;
import com.liferay.object.admin.rest.resource.v1_0.ObjectFieldSettingResource;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectFieldSettingService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/object-field-setting.properties",
	scope = ServiceScope.PROTOTYPE, service = ObjectFieldSettingResource.class
)
public class ObjectFieldSettingResourceImpl
	extends BaseObjectFieldSettingResourceImpl {

	@Override
	public void deleteObjectFieldSetting(Long objectFieldSettingId)
		throws Exception {

		_objectFieldSettingService.deleteObjectFieldSetting(
			objectFieldSettingId);
	}

	@Override
	public ObjectFieldSetting getObjectFieldSetting(Long objectFieldSettingId)
		throws Exception {

		return ObjectFieldSettingUtil.toObjectFieldSetting(
			_objectFieldSettingService.getObjectFieldSetting(
				objectFieldSettingId));
	}

	@Override
	public ObjectFieldSetting postObjectFieldObjectFieldSetting(
			Long objectFieldId, ObjectFieldSetting objectFieldSetting)
		throws Exception {

		return ObjectFieldSettingUtil.toObjectFieldSetting(
			_objectFieldSettingService.addObjectFieldSetting(
				objectFieldId, objectFieldSetting.getName(),
				objectFieldSetting.getRequired(),
				objectFieldSetting.getValue()));
	}

	@Override
	public ObjectFieldSetting putObjectFieldSetting(
			Long objectFieldSettingId, ObjectFieldSetting objectFieldSetting)
		throws Exception {

		return ObjectFieldSettingUtil.toObjectFieldSetting(
			_objectFieldSettingService.updateObjectFieldSetting(
				objectFieldSettingId, objectFieldSetting.getValue()));
	}

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectFieldSettingService _objectFieldSettingService;

}