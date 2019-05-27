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

package com.liferay.headless.common.spi.util;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.io.Serializable;

import java.lang.reflect.Array;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Javier Gamarra
 */
public class CustomFieldsUtil {

	public static Map<String, Object> toCustomFields(
		long companyId, long classPK, Class<?> clazz, Locale locale) {

		ExpandoBridge expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(
			companyId, clazz.getName(), classPK);

		Map<String, Serializable> attributes = expandoBridge.getAttributes();

		return new HashMap<String, Object>() {
			{
				for (Entry<String, Serializable> entry :
						attributes.entrySet()) {

					String key = entry.getKey();

					UnicodeProperties unicodeProperties =
						expandoBridge.getAttributeProperties(key);

					boolean hidden = GetterUtil.getBoolean(
						unicodeProperties.getProperty(
							ExpandoColumnConstants.PROPERTY_HIDDEN));

					if (hidden) {
						continue;
					}

					Object value = entry.getValue();

					if (_isEmpty(value)) {
						put(key, expandoBridge.getAttributeDefault(key));
					}
					else if (ExpandoColumnConstants.STRING_LOCALIZED ==
								expandoBridge.getAttributeType(key)) {

						put(key, ((Map)value).get(locale));
					}
					else {
						put(key, value);
					}
				}
			}
		};
	}

	private static boolean _isEmpty(Object value) {
		if (value == null) {
			return true;
		}

		Class<?> clazz = value.getClass();

		if (clazz.isArray() && (Array.getLength(value) == 0)) {
			return true;
		}

		if (value instanceof Map) {
			Map<?, ?> map = (Map<?, ?>)value;

			if (map.isEmpty()) {
				return true;
			}
		}

		return false;
	}

}