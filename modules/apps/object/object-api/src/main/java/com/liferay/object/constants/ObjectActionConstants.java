/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.constants;

import com.liferay.petra.string.StringPool;

/**
 * @author Carolina Barbosa
 */
public class ObjectActionConstants {

	public static final int STATUS_FAILED = 2;

	public static final int STATUS_NEVER_RAN = 0;

	public static final int STATUS_SUCCESS = 1;

	public static String getStatusLabel(int status) {
		if (status == STATUS_FAILED) {
			return "failed";
		}
		else if (status == STATUS_NEVER_RAN) {
			return "never-ran";
		}
		else if (status == STATUS_SUCCESS) {
			return "success";
		}

		return StringPool.BLANK;
	}

}