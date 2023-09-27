/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.rest.strategy.util;

import com.liferay.dynamic.data.mapping.form.renderer.constants.DDMFormRendererConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Marcos Martins
 * @author Rodrigo Paulino
 */
public class DataRecordValueKeyUtil {

	public static String createDataRecordValueKey(
		String fieldName, String instanceId, String parentDataRecordValueKey,
		int repeatableIndex) {

		if (Validator.isNotNull(parentDataRecordValueKey)) {
			parentDataRecordValueKey =
				parentDataRecordValueKey +
					DDMFormRendererConstants.DDM_FORM_FIELDS_SEPARATOR;
		}

		return StringBundler.concat(
			parentDataRecordValueKey, fieldName,
			DDMFormRendererConstants.DDM_FORM_FIELD_PARTS_SEPARATOR, instanceId,
			DDMFormRendererConstants.DDM_FORM_FIELD_PARTS_SEPARATOR,
			repeatableIndex);
	}

}