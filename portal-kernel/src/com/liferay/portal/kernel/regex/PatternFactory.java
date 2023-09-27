/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.regex;

import java.util.regex.Pattern;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class PatternFactory {

	public static Pattern[] compile(String[] regex) {
		Pattern[] patterns = new Pattern[regex.length];

		for (int i = 0; i < regex.length; i++) {
			patterns[i] = Pattern.compile(regex[i]);
		}

		return patterns;
	}

}