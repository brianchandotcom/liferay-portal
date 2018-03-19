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

package com.liferay.commerce.product.options.web.internal.display.context;

import com.liferay.commerce.product.model.CPOption;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Marco Leo
 */
public class CPOptionDisplayContext {

	public CPOptionDisplayContext(
			CPOption cpOption,
			DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker)
		throws PortalException {

		_cpOption = cpOption;
		_ddmFormFieldTypeServicesTracker = ddmFormFieldTypeServicesTracker;
	}

	public CPOption getCPOption() {
		return _cpOption;
	}

	public long getCPOptionId() {
		if (_cpOption == null) {
			return 0;
		}

		return _cpOption.getCPOptionId();
	}

	public List<DDMFormFieldType> getDDMFormFieldTypes() {
		List<DDMFormFieldType> ddmFormFieldTypes =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldTypes();

		Stream<DDMFormFieldType> stream = ddmFormFieldTypes.stream();

		return stream.filter(
			fieldType -> {
				Map<String, Object> properties =
					_ddmFormFieldTypeServicesTracker.
						getDDMFormFieldTypeProperties(fieldType.getName());

				return !MapUtil.getBoolean(
					properties, "ddm.form.field.type.system");
			}
		).collect(
			Collectors.toList()
		);
	}

	private CPOption _cpOption;
	private final DDMFormFieldTypeServicesTracker
		_ddmFormFieldTypeServicesTracker;

}