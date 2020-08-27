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

package com.liferay.commerce.product.util;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Alessio Antonio Rendina
 */
public class DDMFormFieldTypeUtil {

	public static List<DDMFormFieldType> getDDMFormFieldTypesAllowed(
		List<DDMFormFieldType> ddmFormFieldTypes,
		String[] ddmFormFieldTypesAllowed) {

		Stream<DDMFormFieldType> stream = ddmFormFieldTypes.stream();

		return stream.filter(
			fieldType -> ArrayUtil.contains(
				ddmFormFieldTypesAllowed, fieldType.getName())
		).collect(
			Collectors.toList()
		);
	}

}