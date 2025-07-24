/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Daniel Raposo
 */
public class FieldsUtil {

	public static List<String> expand(String fieldName) {
		if (!fieldName.contains(".")) {
			return Collections.singletonList(fieldName);
		}

		List<String> fieldNames = new ArrayList<>();

		String pendingFieldName = fieldName;

		while (!pendingFieldName.equals("")) {
			fieldNames.add(pendingFieldName);

			if (pendingFieldName.contains(".")) {
				pendingFieldName = pendingFieldName.substring(
					0, pendingFieldName.lastIndexOf("."));
			}
			else {
				pendingFieldName = "";
			}
		}

		return fieldNames;
	}

}