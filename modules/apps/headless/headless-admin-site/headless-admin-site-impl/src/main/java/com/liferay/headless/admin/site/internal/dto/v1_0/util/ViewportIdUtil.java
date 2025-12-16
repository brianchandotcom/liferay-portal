/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.util;

import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;
import java.util.Objects;

/**
 * @author Mikel Lorza
 */
public class ViewportIdUtil {

	public static final Map<String, String> externalToInternalValuesMap =
		HashMapBuilder.put(
			"Desktop", "desktop"
		).put(
			"LandscapeMobile", "landscapeMobile"
		).put(
			"PortraitMobile", "portraitMobile"
		).put(
			"Tablet", "tablet"
		).build();

	public static String toExternalType(String internalType) {
		for (Map.Entry<String, String> entry :
				externalToInternalValuesMap.entrySet()) {

			if (Objects.equals(internalType, entry.getValue())) {
				return entry.getKey();
			}
		}

		return null;
	}

	public static String toInternalValue(String label) {
		return externalToInternalValuesMap.get(label);
	}

}