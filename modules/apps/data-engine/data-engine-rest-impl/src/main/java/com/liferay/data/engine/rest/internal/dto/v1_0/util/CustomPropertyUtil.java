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

package com.liferay.data.engine.rest.internal.dto.v1_0.util;

import com.liferay.data.engine.rest.dto.v1_0.CustomProperty;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * @author Marcela Cunha
 */
public class CustomPropertyUtil {

	public static CustomProperty[] add(
		CustomProperty[] customProperties, String key, Object value) {

		CustomProperty customProperty = new CustomProperty();

		customProperty.setKey(key);
		customProperty.setValue(value);

		return ArrayUtil.append(customProperties, customProperty);
	}

	public static Boolean getBoolean(
		CustomProperty[] customProperties, String key) {

		return getBoolean(customProperties, key, false);
	}

	public static Boolean getBoolean(
		CustomProperty[] customProperties, String key, boolean defaultValue) {

		for (CustomProperty customProperty : customProperties) {
			if (StringUtils.equals(key, customProperty.getKey())) {
				return GetterUtil.getBoolean(customProperty.getValue());
			}
		}

		return defaultValue;
	}

	public static String getString(
		CustomProperty[] customProperties, String key) {

		return getString(customProperties, key, StringPool.BLANK);
	}

	public static String getString(
		CustomProperty[] customProperties, String key, String defaultValue) {

		for (CustomProperty customProperty : customProperties) {
			if (StringUtils.equals(key, customProperty.getKey())) {
				return GetterUtil.getString(customProperty.getValue());
			}
		}

		return defaultValue;
	}

}