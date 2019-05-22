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

package com.liferay.headless.delivery.internal.dto.v1_0.util;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.io.Serializable;

import java.lang.reflect.Array;

import java.text.DateFormat;
import java.text.ParseException;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Javier Gamarra
 */
public class CustomFieldsUtil {

	public static void addCustomFields(
			long companyId, Class<?> clazz, long classPK,
			Map<String, Object> expandoAttributes, Locale locale)
		throws ParseException {

		if (expandoAttributes == null) {
			return;
		}

		ExpandoBridge expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(
			companyId, clazz.getName(), classPK);

		for (Map.Entry<String, Object> entry : expandoAttributes.entrySet()) {
			String key = entry.getKey();

			int attributeType = expandoBridge.getAttributeType(key);

			if (ExpandoColumnConstants.STRING_LOCALIZED == attributeType) {
				expandoBridge.setAttribute(
					key,
					(Serializable)LocalizedMapUtil.merge(
						(Map)expandoBridge.getAttribute(key),
						new AbstractMap.SimpleEntry<>(
							locale,
							String.valueOf(expandoAttributes.get(key)))));
			}
			else if (ExpandoColumnConstants.DATE == attributeType) {
				DateFormat dateFormat =
					DateFormatFactoryUtil.getSimpleDateFormat(
						"yyyy-MM-dd'T'HH:mm:ss'Z'");

				expandoBridge.setAttribute(
					key, dateFormat.parse((String)entry.getValue()));
			}
			else {
				expandoBridge.setAttribute(key, (Serializable)entry.getValue());
			}
		}
	}

	public static Map<String, Object> toCustomFields(
		ExpandoBridge expandoBridge, Locale locale) {

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

		if ((clazz.isArray() && (Array.getLength(value) == 0)) ||
			((value instanceof Map) && ((Map)value).isEmpty())) {

			return true;
		}

		return false;
	}

}