/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.form.renderer.constants.DDMFormRendererConstants;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Marcos Martins
 * @author Rodrigo Paulino
 */
public class DDMFormFieldParameterNameUtil {

	public static final int DDM_FORM_FIELD_INDEX_INDEX = 2;

	public static final int DDM_FORM_FIELD_INSTANCE_ID_INDEX = 1;

	public static final int DDM_FORM_FIELD_NAME_INDEX = 0;

	public static String[] getLastDDMFormFieldParameterNameParts(
		String ddmFormFieldParameterName) {

		String ddmFormFieldParameterNameParts = StringUtil.extractLast(
			ddmFormFieldParameterName,
			DDMFormRendererConstants.DDM_FORM_FIELDS_SEPARATOR);

		if (ddmFormFieldParameterNameParts == null) {
			ddmFormFieldParameterNameParts = ddmFormFieldParameterName;
		}

		return StringUtil.split(
			ddmFormFieldParameterNameParts,
			DDMFormRendererConstants.DDM_FORM_FIELD_PARTS_SEPARATOR);
	}

}