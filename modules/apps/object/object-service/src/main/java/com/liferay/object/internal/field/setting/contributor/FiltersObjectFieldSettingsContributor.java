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

import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.model.ObjectFilter;
import com.liferay.object.service.ObjectFilterLocalService;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Albuquerque
 */
@Component(
	property = "object.field.setting.type.key=" + ObjectFieldSettingConstants.NAME_FILTERS,
	service = ObjectFieldSettingContributor.class
)
public class FiltersObjectFieldSettingsContributor
	implements ObjectFieldSettingContributor {

	@Override
	public void addObjectFieldSetting(
			long userId, long objectFieldId,
			ObjectFieldSetting newObjectFieldSetting)
		throws PortalException {

		_objectFilterLocalService.deleteObjectFieldObjectFilter(objectFieldId);

		for (ObjectFilter objectFilter :
				newObjectFieldSetting.getObjectFilters()) {

			_objectFilterLocalService.addObjectFilter(
				userId, objectFieldId, objectFilter.getFilterBy(),
				objectFilter.getFilterType(), objectFilter.getJSON());
		}
	}

	@Reference
	private ObjectFilterLocalService _objectFilterLocalService;

}