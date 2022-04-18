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

package com.liferay.object.test.util;

import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.service.ObjectFieldSettingLocalServiceUtil;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Carolina Barbosa
 */
public class ObjectFieldSettingTestUtil {

	public static ObjectFieldSetting createObjectFieldSetting(
		String name, String value) {

		ObjectFieldSetting objectFieldSetting =
			ObjectFieldSettingLocalServiceUtil.createObjectFieldSetting(0);

		objectFieldSetting.setName(name);
		objectFieldSetting.setValue(value);

		return objectFieldSetting;
	}

	public static List<ObjectFieldSetting> getObjectFieldSettings(
		String businessType) {

		if (Objects.equals(
				businessType, ObjectFieldConstants.BUSINESS_TYPE_LONG_TEXT) ||
			Objects.equals(
				businessType, ObjectFieldConstants.BUSINESS_TYPE_TEXT)) {

			return Collections.singletonList(
				createObjectFieldSetting("showCounter", "false"));
		}

		return Collections.emptyList();
	}

}