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
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.structure.apio.architect.model.FormLayoutPage;

/**
 * Provides the information necessary to expose Structure resources through a
 * web API. The resources are mapped from the internal model {@code
 * DDMStructure}.
 *
 * @author Javier Gamarra
 * @review
 */
public interface StructureRepresentorBuilderHelper {

	public NestedRepresentor.FirstStep<DDMFormField> buildDDMFormFieldFirstStep(
		NestedRepresentor.Builder<DDMFormField> builder);

	public Representor.FirstStep<DDMStructure> buildDDMStructureFirstStep(
		Representor.Builder<DDMStructure, Long> builder);

	public NestedRepresentor.FirstStep<FormLayoutPage>
		buildFormLayoutPageFirstStep(
			NestedRepresentor.Builder<FormLayoutPage> builder);

}