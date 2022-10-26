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

package com.liferay.layout.internal.headless.delivery.dto.v1_0.converter;

import com.liferay.layout.utility.page.constants.LayoutUtilityPageEntryConstants;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Víctor Galán
 */
public class UtilityPageTemplateTypeConverter {

	public static final Map<String, Integer> externalToInternalValuesMap =
		HashMapBuilder.put(
			"Error", LayoutUtilityPageEntryConstants.Type.ERROR_404.getType()
		).put(
			"TermsOfUse",
			LayoutUtilityPageEntryConstants.Type.TERMS_OF_USE.getType()
		).build();

	public static String convertToExternalValue(int value) {
		Set<String> externalValues = externalToInternalValuesMap.keySet();

		for (String externalValue : externalValues) {
			if (Objects.equals(
					value, externalToInternalValuesMap.get(externalValue))) {

				return externalValue;
			}
		}

		return null;
	}

	public static int convertToInternalValue(String label) {
		return externalToInternalValuesMap.get(label);
	}

}