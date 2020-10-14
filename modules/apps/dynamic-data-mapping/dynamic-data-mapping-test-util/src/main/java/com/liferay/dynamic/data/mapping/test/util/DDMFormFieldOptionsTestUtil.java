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

package com.liferay.dynamic.data.mapping.test.util;

import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Map;

/**
 * @author Carolina Barbosa
 */
public class DDMFormFieldOptionsTestUtil {

	public static DDMFormFieldOptions createDDMFormFieldOptions() {
		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		ddmFormFieldOptions.addOptionLabel("value 1", LocaleUtil.US, "Label 1");
		ddmFormFieldOptions.addOptionLabel("value 2", LocaleUtil.US, "Label 2");
		ddmFormFieldOptions.addOptionLabel("value 3", LocaleUtil.US, "Label 3");

		ddmFormFieldOptions.addOptionReference("value 1", "Reference 1");
		ddmFormFieldOptions.addOptionReference("value 2", "Reference 2");
		ddmFormFieldOptions.addOptionReference("value 3", "Reference 3");

		return ddmFormFieldOptions;
	}

	public static Map<String, String> createOption(
		String label, String reference, String value) {

		return HashMapBuilder.put(
			"label", label
		).put(
			"reference", reference
		).put(
			"value", value
		).build();
	}

}