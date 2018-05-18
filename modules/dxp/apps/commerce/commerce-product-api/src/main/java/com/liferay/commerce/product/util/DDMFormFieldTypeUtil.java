/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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