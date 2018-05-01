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