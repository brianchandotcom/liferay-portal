/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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