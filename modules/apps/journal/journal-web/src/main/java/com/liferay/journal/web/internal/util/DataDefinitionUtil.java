/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.util;

import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinitionField;
import com.liferay.data.engine.rest.dto.v2_0.DataLayout;
import com.liferay.data.engine.rest.dto.v2_0.DataLayoutColumn;
import com.liferay.data.engine.rest.dto.v2_0.DataLayoutPage;
import com.liferay.data.engine.rest.dto.v2_0.DataLayoutRow;
import com.liferay.dynamic.data.mapping.util.DDMFormFieldUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Attila Bakay
 */
public class DataDefinitionUtil {

	public static void validateDefinitionFields(DataDefinition dataDefinition) {
		for (DataDefinitionField dataDefinitionField :
				dataDefinition.getDataDefinitionFields()) {

			String oldFieldName = dataDefinitionField.getName();

			String newFieldName = _getFieldName(oldFieldName);

			if (StringUtil.equals(newFieldName, oldFieldName)) {
				continue;
			}

			dataDefinitionField.setName(() -> newFieldName);

			DataLayout dataLayout = dataDefinition.getDefaultDataLayout();

			_updateDataLayoutFieldName(dataLayout, newFieldName, oldFieldName);
		}
	}

	private static String _getFieldName(String fieldName) {
		int index = fieldName.length() - 8;

		if ((index >= 0) && Validator.isNumber(fieldName.substring(index))) {
			return fieldName;
		}

		return DDMFormFieldUtil.getDDMFormFieldName(fieldName);
	}

	private static void _updateDataLayoutFieldName(
		DataLayout dataLayout, String newFieldName, String oldFieldName) {

		for (DataLayoutPage dataLayoutPage : dataLayout.getDataLayoutPages()) {
			for (DataLayoutRow dataLayoutRow :
					dataLayoutPage.getDataLayoutRows()) {

				for (DataLayoutColumn dataLayoutColumn :
						dataLayoutRow.getDataLayoutColumns()) {

					String[] dataLayoutColumnFieldNames =
						dataLayoutColumn.getFieldNames();

					for (int i = 0; i < dataLayoutColumnFieldNames.length;
						 i++) {

						if (dataLayoutColumnFieldNames[i].equals(
								oldFieldName)) {

							dataLayoutColumnFieldNames[i] = newFieldName;

							return;
						}
					}
				}
			}
		}
	}

}