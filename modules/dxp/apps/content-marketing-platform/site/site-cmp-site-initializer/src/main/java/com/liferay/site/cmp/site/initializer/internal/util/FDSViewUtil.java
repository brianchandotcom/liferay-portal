/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.util;

import com.liferay.frontend.data.set.view.table.DateFDSTableSchemaField;
import com.liferay.portal.kernel.json.JSONUtil;

/**
 * @author Pedro Leite
 */
public class FDSViewUtil {

	public static DateFDSTableSchemaField getDateFDSTableSchemaField(
		String fieldName, String label) {

		DateFDSTableSchemaField dateFDSTableSchemaField =
			new DateFDSTableSchemaField();

		dateFDSTableSchemaField.setFieldName(fieldName);
		dateFDSTableSchemaField.setFormat(
			JSONUtil.put(
				"day", "numeric"
			).put(
				"month", "numeric"
			).put(
				"year", "numeric"
			));
		dateFDSTableSchemaField.setLabel(
			label
		).setLocalizeLabel(
			true
		);

		return dateFDSTableSchemaField;
	}

}