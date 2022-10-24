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

package com.liferay.object.util;

import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;

import java.util.Objects;

/**
 * @author Carolina Barbosa
 */
public class ObjectFieldSettingValueUtil {

	public static String getObjectFieldSettingValue(
		ObjectField objectField, String objectFieldSettingName) {

		for (ObjectFieldSetting objectFieldSetting :
				objectField.getObjectFieldSettings()) {

			if (Objects.equals(
					objectFieldSetting.getName(), objectFieldSettingName)) {

				return objectFieldSetting.getValue();
			}
		}

		return null;
	}

}