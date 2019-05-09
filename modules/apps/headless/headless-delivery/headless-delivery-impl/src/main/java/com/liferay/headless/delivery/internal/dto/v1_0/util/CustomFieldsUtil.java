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
import com.liferay.headless.delivery.dto.v1_0.AggregateRating;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.ratings.kernel.model.RatingsStats;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Javier Gamarra
 */
public class CustomFieldsUtil {

	public static HashMap<String, Object> toCustomFields(
		ExpandoBridge expandoBridge, Locale locale) {

		Map<String, Serializable> attributes = expandoBridge.getAttributes();

		return new HashMap<String, Object>() {
			{
				for (Map.Entry<String, Serializable> entry : attributes.entrySet()) {

					UnicodeProperties properties = expandoBridge.getAttributeProperties(
						entry.getKey());

					if (GetterUtil.getBoolean(
						properties.get(ExpandoColumnConstants.PROPERTY_HIDDEN))) {
						continue;
					}

					int attributeType = expandoBridge.getAttributeType(
						entry.getKey());

					String value = String.valueOf(entry.getValue());

					if (attributeType ==
						ExpandoColumnConstants.STRING_LOCALIZED) {

						Map map = (Map) entry.getValue();

						value = String.valueOf(
							map.get(locale));
					}
					else if (attributeType ==
							 ExpandoColumnConstants.STRING_ARRAY) {

						value = String.join(
							",", (String[]) entry.getValue());
					}
					put(entry.getKey(), value);
				}
			}
		};
	}

}