/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.release.util.internal.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Drew Brokke
 */
public class StringUtil {

	public static final String COMMA = ",";

	public static final String FORWARD_SLASH = "/";

	public static List<String> split(String s) {
		if ((s == null) || s.isEmpty()) {
			return Collections.emptyList();
		}

		s = s.trim();

		if (s.isEmpty()) {
			return Collections.emptyList();
		}

		return Arrays.asList(s.split(COMMA));
	}

}