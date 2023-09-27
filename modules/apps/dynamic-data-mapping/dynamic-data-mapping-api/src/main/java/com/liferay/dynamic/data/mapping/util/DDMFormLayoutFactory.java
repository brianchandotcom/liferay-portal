/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.util;

import java.lang.annotation.Annotation;

/**
 * @author Leonardo Barros
 */
public class DDMFormLayoutFactory {

	public static com.liferay.dynamic.data.mapping.model.DDMFormLayout create(
		Class<?> clazz) {

		if (!clazz.isAnnotationPresent(_DDM_FORM_LAYOUT_ANNOTATION)) {
			throw new IllegalArgumentException(
				"Unsupported class " + clazz.getName());
		}

		DDMFormLayoutFactoryHelper ddmFormLayoutFactoryHelper =
			new DDMFormLayoutFactoryHelper(clazz);

		return ddmFormLayoutFactoryHelper.createDDMFormLayout();
	}

	private static final Class<? extends Annotation>
		_DDM_FORM_LAYOUT_ANNOTATION =
			com.liferay.dynamic.data.mapping.annotations.DDMFormLayout.class;

}