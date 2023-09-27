/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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