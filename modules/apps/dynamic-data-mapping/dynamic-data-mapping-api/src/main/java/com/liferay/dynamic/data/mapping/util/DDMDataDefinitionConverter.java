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

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;

/**
 * @author Eudaldo Alonso
 */
public interface DDMDataDefinitionConverter {

	public DDMForm convertDDMFormDataDefinition(
		DDMForm ddmForm, long parentStructureId, long parentStructureLayoutId);

	public String convertDDMFormDataDefinition(
			String dataDefinition, long parentStructureId,
			long parentStructureLayoutId)
		throws Exception;

	public String convertDDMFormDataDefinition(
			String dataDefinition, long groupId, long parentStructureId,
			long parentStructureLayoutId, long structureId)
		throws Exception;

	public DDMFormLayout convertDDMFormLayoutDataDefinition(
		DDMForm ddmForm, DDMFormLayout ddmFormLayout);

	public String convertDDMFormLayoutDataDefinition(
			long groupId, long structureId,
			String structureLayoutDataDefinition, long structureLayoutId,
			String structureVersionDataDefinition)
		throws Exception;

}