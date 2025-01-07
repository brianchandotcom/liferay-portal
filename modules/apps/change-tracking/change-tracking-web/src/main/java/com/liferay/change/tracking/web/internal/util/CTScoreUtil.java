/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.util;

/**
 * @author David Truong
 */
public class CTScoreUtil {

	public static String getSizeClassification(int score) {
		if (score > _LARGE_THRESHOLD) {
			return "large";
		}
		else if (score > _MEDIUM_THRESHOLD) {
			return "medium";
		}

		return "small";
	}

	private static final int _LARGE_THRESHOLD = 20000;

	private static final int _MEDIUM_THRESHOLD = 10000;

}