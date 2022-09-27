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