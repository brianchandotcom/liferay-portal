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

package com.liferay.object.admin.rest.internal.dto.v1_0.util;

import com.liferay.object.admin.rest.dto.v1_0.ObjectFieldSetting;
import com.liferay.object.admin.rest.dto.v1_0.ObjectStateFlow;
import com.liferay.object.admin.rest.dto.v1_0.util.ObjectStateFlowUtil;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.object.service.ObjectStateFlowLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import java.util.Objects;

/**
 * @author Carolina Barbosa
 */
public class ObjectFieldSettingUtil {

	public static com.liferay.object.model.ObjectFieldSetting
			toObjectFieldSetting(
				ObjectFieldSetting objectFieldSetting,
				ObjectFieldSettingLocalService objectFieldSettingLocalService)
		throws PortalException {

		com.liferay.object.model.ObjectFieldSetting
			serviceBuilderObjectFieldSetting =
				objectFieldSettingLocalService.createObjectFieldSetting(0L);

		serviceBuilderObjectFieldSetting.setName(objectFieldSetting.getName());
		serviceBuilderObjectFieldSetting.setValue(
			objectFieldSetting.getValue());

		if (Objects.equals(
				ObjectFieldSettingConstants.NAME_STATE_FLOW,
				objectFieldSetting.getName())) {

			serviceBuilderObjectFieldSetting.setObjectStateFlow(
				com.liferay.object.admin.rest.internal.dto.v1_0.util.
					ObjectStateFlowUtil.toObjectStateFlow(
						objectFieldSetting.getObjectFieldId(),
						ObjectMapperUtil.readValue(
							ObjectStateFlow.class,
							objectFieldSetting.getValue())));
		}

		return serviceBuilderObjectFieldSetting;
	}

	public static ObjectFieldSetting toObjectFieldSetting(
		com.liferay.object.model.ObjectFieldSetting
			serviceBuilderObjectFieldSetting) {

		if (serviceBuilderObjectFieldSetting == null) {
			return null;
		}

		ObjectFieldSetting objectFieldSetting = new ObjectFieldSetting() {
			{
				id = serviceBuilderObjectFieldSetting.getObjectFieldId();
				name = serviceBuilderObjectFieldSetting.getName();
				objectFieldId =
					serviceBuilderObjectFieldSetting.getObjectFieldId();
				value = serviceBuilderObjectFieldSetting.getValue();
			}
		};

		if (Objects.equals(
				ObjectFieldSettingConstants.NAME_STATE_FLOW,
				objectFieldSetting.getName())) {

			objectFieldSetting.setValue(
				ObjectStateFlowUtil.parse(
					ObjectStateFlowLocalServiceUtil.fetchObjectStateFlow(
						GetterUtil.getLong(
							serviceBuilderObjectFieldSetting.getValue()))));
		}

		return objectFieldSetting;
	}

}