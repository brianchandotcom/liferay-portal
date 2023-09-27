/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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