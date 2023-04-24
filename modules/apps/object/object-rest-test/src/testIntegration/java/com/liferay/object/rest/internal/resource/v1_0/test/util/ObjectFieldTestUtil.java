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

package com.liferay.object.rest.internal.resource.v1_0.test.util;

import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.service.ObjectFieldLocalServiceUtil;
import com.liferay.object.service.ObjectFieldSettingLocalServiceUtil;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Carlos Correa
 */
public class ObjectFieldTestUtil {

	public static ObjectField addCustomObjectField(
			long userId, String businessType, String dbType,
			ObjectDefinition objectDefinition, String objectFieldName)
		throws Exception {

		List<ObjectFieldSetting> objectFieldSettings = null;

		if (Objects.equals(
				businessType, ObjectFieldConstants.BUSINESS_TYPE_LONG_TEXT) ||
			Objects.equals(
				businessType, ObjectFieldConstants.BUSINESS_TYPE_TEXT)) {

			ObjectFieldSetting objectFieldSetting =
				ObjectFieldSettingLocalServiceUtil.createObjectFieldSetting(0);

			objectFieldSetting.setName("showCounter");
			objectFieldSetting.setValue("false");

			objectFieldSettings = Collections.singletonList(objectFieldSetting);
		}

		return ObjectFieldLocalServiceUtil.addCustomObjectField(
			null, userId, 0, objectDefinition.getObjectDefinitionId(),
			businessType, dbType, false, true, "",
			LocalizedMapUtil.getLocalizedMap(objectFieldName), false,
			objectFieldName, false, false, objectFieldSettings);
	}

}