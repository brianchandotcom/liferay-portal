/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.field.setting.contributor;

import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.service.ObjectStateTransitionLocalService;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Feliphe Marinho
 */
public class StateFlowObjectFieldSettingContributor
	implements ObjectFieldSettingContributor {

	public StateFlowObjectFieldSettingContributor(
		ObjectStateTransitionLocalService objectStateTransitionLocalService) {

		_objectStateTransitionLocalService = objectStateTransitionLocalService;
	}

	@Override
	public void updateObjectFieldSetting(
			long oldObjectFieldSettingId,
			ObjectFieldSetting newObjectFieldSetting)
		throws PortalException {

		_objectStateTransitionLocalService.updateObjectStateTransitions(
			newObjectFieldSetting.getObjectStateFlow());
	}

	private final ObjectStateTransitionLocalService
		_objectStateTransitionLocalService;

}