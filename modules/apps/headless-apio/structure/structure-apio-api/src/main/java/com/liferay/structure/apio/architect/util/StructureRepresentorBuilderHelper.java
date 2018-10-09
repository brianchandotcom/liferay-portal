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

import com.liferay.apio.architect.representor.NestedRepresentor;
import com.liferay.apio.architect.representor.Representor;
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
 * Provides the information necessary to expose structure resources through a
 * web API. The resources are mapped from the internal model {@code
 * DDMStructure}.
 *
 * @author Javier Gamarra
 */
public interface StructureRepresentorBuilderHelper {

	public default NestedRepresentor.FirstStep<DDMFormField>
		buildDDMFormFieldFirstStep(
			NestedRepresentor.Builder<DDMFormField> builder) {

		return null;
	}

	public Representor.FirstStep<DDMStructure> buildDDMStructureFirstStep(
		Representor.Builder<DDMStructure, Long> builder);

	public default NestedRepresentor.FirstStep<FormLayoutPage>
		buildFormLayoutPageFirstStep(
			NestedRepresentor.Builder<FormLayoutPage> builder) {

		return null;
	}

	public default <T> Function<DDMFormField, T>
		getDDMFormFieldPropertyFunction(
			Function<Object, T> parseFunction, String key) {

		return null;
	}

	public default List<FormLayoutPage> getFormLayoutPages(
		DDMStructure ddmStructure) {

		return null;
	}

	public default BiFunction<DDMFormField, Locale, String>
		getLocalizedStringBiFunction(String key) {

		return null;
	}

	public default Function
		<DDMFormField, List<Map.Entry<String, LocalizedValue>>>
			getLocalizedValueEntriesFunction(
				Function<DDMFormField, DDMFormFieldOptions> function) {

		return null;
	}

	public default Function
		<DDMFormField, List<Map.Entry<String, LocalizedValue>>>
			getLocalizedValueEntriesFunction(String key) {

		return null;
	}

}