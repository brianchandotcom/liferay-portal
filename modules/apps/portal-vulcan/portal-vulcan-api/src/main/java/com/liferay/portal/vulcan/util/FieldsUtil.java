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

		List<String> list = new ArrayList<>();

		String pending = fieldName;

		while (!pending.equals("")) {
			list.add(pending);

			if (pending.contains(".")) {
				pending = pending.substring(0, pending.lastIndexOf("."));
			}
			else {
				pending = "";
			}
		}

		return list;
	}

}