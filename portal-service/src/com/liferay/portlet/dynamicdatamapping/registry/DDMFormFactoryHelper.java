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

package com.liferay.portlet.dynamicdatamapping.registry;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;
import com.liferay.portlet.dynamicdatamapping.registry.annotations.DDMFormField;

import java.lang.reflect.Method;

/**
 * @author Marcellus Tavares
 */
public class DDMFormFactoryHelper {

	public DDMFormFactoryHelper(Method method) {
		_method = method;

		_ddmFormField = method.getAnnotation(DDMFormField.class);
	}

	public String getDDMFormFieldName() {
		if (Validator.isNotNull(_ddmFormField.name())) {
			return _ddmFormField.name();
		}

		return _method.getName();
	}

	public String getDDMFormFieldType() {
		if (Validator.isNotNull(_ddmFormField.type())) {
			return _ddmFormField.type();
		}

		Class<?> returnType = _method.getReturnType();

		if (returnType.isAssignableFrom(boolean.class) ||
			returnType.isAssignableFrom(Boolean.class)) {

			return "checkbox";
		}

		return "text";
	}

	public String getDDMFormFieldVisibilityExpression() {
		if (Validator.isNotNull(_ddmFormField.visibilityExpression())) {
			return _ddmFormField.visibilityExpression();
		}

		return StringPool.TRUE;
	}

	public boolean isDDMFormFieldLocalizable(Method method) {
		Class<?> returnType = _method.getReturnType();

		if (returnType.isAssignableFrom(LocalizedValue.class)) {
			return true;
		}

		return false;
	}

	protected String getDDMFormFieldDataType() {
		if (Validator.isNotNull(_ddmFormField.dataType())) {
			return _ddmFormField.dataType();
		}

		Class<?> returnType = _method.getReturnType();

		if (returnType.isAssignableFrom(boolean.class) ||
			returnType.isAssignableFrom(Boolean.class)) {

			return "boolean";
		}

		return "string";
	}

	private final DDMFormField _ddmFormField;
	private final Method _method;

}