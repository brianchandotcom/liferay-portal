/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.field.setting.contributor;

import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.service.ObjectStateTransitionLocalService;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 */
@Component(
	property = "object.field.setting.type.key=" + ObjectFieldSettingConstants.NAME_STATE_FLOW,
	service = ObjectFieldSettingContributor.class
)
public class StateFlowObjectFieldSettingContributor
	implements ObjectFieldSettingContributor {

	@Override
	public void updateObjectFieldSetting(
			long oldObjectFieldSettingId,
			ObjectFieldSetting newObjectFieldSetting)
		throws PortalException {

		_objectStateTransitionLocalService.updateObjectStateTransitions(
			newObjectFieldSetting.getObjectStateFlow());
	}

	@Reference
	private ObjectStateTransitionLocalService
		_objectStateTransitionLocalService;

}