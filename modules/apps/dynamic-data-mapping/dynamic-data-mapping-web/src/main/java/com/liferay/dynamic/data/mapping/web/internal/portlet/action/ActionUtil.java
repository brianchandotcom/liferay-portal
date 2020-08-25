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

package com.liferay.dynamic.data.mapping.web.internal.portlet.action;

import com.liferay.dynamic.data.mapping.exception.InvalidStructureFieldNameException;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;

import java.util.Map;

/**
 * @author Rodrigo Paulino
 */
public class ActionUtil {

	public static void validateFieldNames(DDMForm ddmForm) throws Exception {
		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(true);

		for (String reservedFieldName : _RESERVED_FIELD_NAMES) {
			if (ddmFormFieldsMap.containsKey(reservedFieldName)) {
				throw new InvalidStructureFieldNameException(
					"Dynamic data mapping structure field name " +
						reservedFieldName + " is a reserved name",
					reservedFieldName);
			}
		}
	}

	private static final String[] _RESERVED_FIELD_NAMES = {
		"attributes", "data", "name", "options", "optionsMap", "type"
	};

}