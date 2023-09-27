/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.utility.page.converter;

import com.liferay.layout.utility.page.kernel.constants.LayoutUtilityPageEntryConstants;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Jürgen Kappler
 */
public class LayoutUtilityPageEntryTypeConverter {

	public static String convertToExternalValue(String value) {
		Set<String> externalValues = _externalToInternalValuesMap.keySet();

		for (String externalValue : externalValues) {
			if (Objects.equals(
					value, _externalToInternalValuesMap.get(externalValue))) {

				return externalValue;
			}
		}

		return null;
	}

	public static String convertToInternalValue(String label) {
		return _externalToInternalValuesMap.get(label);
	}

	private static final Map<String, String> _externalToInternalValuesMap =
		HashMapBuilder.put(
			"Error", LayoutUtilityPageEntryConstants.TYPE_STATUS
		).put(
			"ErrorCode404", LayoutUtilityPageEntryConstants.TYPE_SC_NOT_FOUND
		).put(
			"ErrorCode500",
			LayoutUtilityPageEntryConstants.TYPE_SC_INTERNAL_SERVER_ERROR
		).put(
			"TermsOfUse", LayoutUtilityPageEntryConstants.TYPE_TERMS_OF_USE
		).build();

}