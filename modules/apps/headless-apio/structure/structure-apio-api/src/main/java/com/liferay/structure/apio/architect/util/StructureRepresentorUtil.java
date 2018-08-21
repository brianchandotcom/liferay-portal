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

package com.liferay.structure.apio.architect.util;

import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.structure.apio.architect.model.FormLayoutPage;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author Paulo Cruz
 */
public interface StructureRepresentorUtil {

	public Function<DDMFormField,
		List<Map.Entry<String, LocalizedValue>>> getFieldOptions(
			Function<DDMFormField, DDMFormFieldOptions> function);

	public Function<DDMFormField,
		List<Map.Entry<String, LocalizedValue>>> getFieldOptions(String key);

	public <T> Function<DDMFormField, T> getFieldProperty(
		Function<Object, T> parseFunction, String key);

	public List<FormLayoutPage> getFormLayoutPages(DDMStructure ddmStructure);

	public BiFunction<DDMFormField, Locale, String> getLocalizedString(
		String key);

}