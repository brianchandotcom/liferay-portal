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

package com.liferay.analytics.message.sender.util;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Rachael Koestartyo
 */
public class AnalyticsExpandoBridgeUtil {

	public static Map<String, Serializable> getAttributes(
		ExpandoBridge expandoBridge) {

		Map<String, Serializable> newAttributes = new HashMap<>();

		Map<String, Serializable> attributes = expandoBridge.getAttributes(
			false);

		for (Map.Entry<String, Serializable> entry : attributes.entrySet()) {
			String dataType = ExpandoColumnUtil.getDataType(
				expandoBridge.getAttributeType(entry.getKey()));

			if (Validator.isBlank(dataType)) {
				dataType = ExpandoColumnUtil.DATA_TYPE_TEXT;
			}

			newAttributes.put(
				entry.getKey() + "-" + dataType, entry.getValue());
		}

		return newAttributes;
	}

}