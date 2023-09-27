/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;

/**
 * @author Leonardo Barros
 */
public interface DDMBeanTranslator {

	public DDMForm translate(
		com.liferay.dynamic.data.mapping.kernel.DDMForm ddmForm);

	public com.liferay.dynamic.data.mapping.kernel.DDMForm translate(
		DDMForm ddmForm);

	public DDMFormField translate(
		com.liferay.dynamic.data.mapping.kernel.DDMFormField ddmFormField);

	public com.liferay.dynamic.data.mapping.kernel.DDMFormField translate(
		DDMFormField ddmFormField);

	public DDMFormValues translate(
		com.liferay.dynamic.data.mapping.kernel.DDMFormValues ddmFormValues);

	public com.liferay.dynamic.data.mapping.kernel.DDMFormValues translate(
		DDMFormValues ddmFormValues);

}