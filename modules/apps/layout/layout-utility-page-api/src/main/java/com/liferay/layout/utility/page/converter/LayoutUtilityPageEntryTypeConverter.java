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