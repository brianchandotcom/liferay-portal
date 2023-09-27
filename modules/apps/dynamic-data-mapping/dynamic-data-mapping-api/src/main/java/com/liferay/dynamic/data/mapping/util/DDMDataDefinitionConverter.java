/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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