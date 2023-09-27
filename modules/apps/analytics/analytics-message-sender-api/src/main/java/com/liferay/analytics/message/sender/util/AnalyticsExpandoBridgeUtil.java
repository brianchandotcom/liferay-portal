/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.message.sender.util;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Rachael Koestartyo
 */
public class AnalyticsExpandoBridgeUtil {

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getAttributes(ExpandoBridge, List)}
	 */
	@Deprecated
	public static Map<String, Serializable> getAttributes(
		ExpandoBridge expandoBridge) {

		return null;
	}

	public static Map<String, Serializable> getAttributes(
		ExpandoBridge expandoBridge, List<String> includeAttributeNames) {

		Map<String, Serializable> newAttributes = new HashMap<>();

		Map<String, Serializable> attributes = expandoBridge.getAttributes(
			false);

		for (Map.Entry<String, Serializable> entry : attributes.entrySet()) {
			if (ListUtil.isNotEmpty(includeAttributeNames) &&
				!includeAttributeNames.contains(entry.getKey())) {

				continue;
			}

			String dataType = ExpandoColumnConstants.getDataType(
				expandoBridge.getAttributeType(entry.getKey()));

			if (Validator.isBlank(dataType)) {
				dataType = ExpandoColumnConstants.DATA_TYPE_TEXT;
			}

			newAttributes.put(
				entry.getKey() + "-" + dataType, entry.getValue());
		}

		return newAttributes;
	}

}