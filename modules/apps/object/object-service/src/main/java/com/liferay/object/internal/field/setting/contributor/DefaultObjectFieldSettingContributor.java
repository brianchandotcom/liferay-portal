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

package com.liferay.object.internal.field.setting.contributor;

import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 */
@Component(
	property = "object.field.setting.type.key=default",
	service = ObjectFieldSettingContributor.class
)
public class DefaultObjectFieldSettingContributor
	implements ObjectFieldSettingContributor {

	@Override
	public void addObjectFieldSetting(
			long userId, long objectFieldId,
			ObjectFieldSetting objectFieldSetting)
		throws PortalException {

		_objectFieldSettingLocalService.addObjectFieldSetting(
			userId, objectFieldId, objectFieldSetting.getName(),
			objectFieldSetting.getValue());
	}

	@Override
	public void updateObjectFieldSetting(
			long objectFieldSettingId, ObjectFieldSetting objectFieldSetting)
		throws PortalException {

		_objectFieldSettingLocalService.updateObjectFieldSetting(
			objectFieldSettingId, objectFieldSetting.getValue());
	}

	@Reference
	private ObjectFieldSettingLocalService _objectFieldSettingLocalService;

}