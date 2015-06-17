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

import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author Marcellus Tavares
 */
public class DDMFormFactory {

	public static DDMForm create(Class<?> clazz) {
		if (!clazz.isAnnotationPresent(_DDM_FORM_ANNOTATION)) {
			throw new IllegalArgumentException(
				"Unsupported class " + clazz.getName());
		}

		DDMForm ddmForm = new DDMForm();

		addDDMFormFields(clazz, ddmForm);

		return ddmForm;
	}

	protected static void addDDMFormFields(Class<?> clazz, DDMForm ddmForm) {
		for (Class<?> interfaceClass : clazz.getInterfaces()) {
			addDDMFormFields(interfaceClass, ddmForm);
		}

		for (Method method : clazz.getDeclaredMethods()) {
			if (!method.isAnnotationPresent(_DDM_FORM_FIELD_ANNOTATION)) {
				continue;
			}

			DDMFormField ddmFormField = createDDMFormField(method);

			ddmForm.addDDMFormField(ddmFormField);
		}
	}

	protected static DDMFormField createDDMFormField(Method method) {
		DDMFormFactoryHelper ddmFormFactoryHelper = new DDMFormFactoryHelper(
			method);

		String name = ddmFormFactoryHelper.getDDMFormFieldName();
		String type = ddmFormFactoryHelper.getDDMFormFieldType();

		DDMFormField ddmFormField = new DDMFormField(name, type);

		ddmFormField.setDataType(
			ddmFormFactoryHelper.getDDMFormFieldDataType());
		ddmFormField.setLocalizable(
			ddmFormFactoryHelper.isDDMFormFieldLocalizable(method));
		ddmFormField.setVisibilityExpression(
			ddmFormFactoryHelper.getDDMFormFieldVisibilityExpression());

		return ddmFormField;
	}

	private static final Class<? extends Annotation> _DDM_FORM_ANNOTATION =
		com.liferay.portlet.dynamicdatamapping.registry.annotations.
			DDMForm.class;

	private static final Class<? extends Annotation>
		_DDM_FORM_FIELD_ANNOTATION =
			com.liferay.portlet.dynamicdatamapping.registry.annotations.
				DDMFormField.class;

}