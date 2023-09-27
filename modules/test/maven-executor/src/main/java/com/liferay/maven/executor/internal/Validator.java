/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.maven.executor.internal;

/**
 * @author Brian Wing Shun Chan
 * @author Andrea Di Giorgi
 */
public class Validator {

	public static boolean isNotNull(String s) {
		return !isNull(s);
	}

	public static boolean isNull(String s) {
		if (s == null) {
			return true;
		}

		int counter = 0;

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (Character.isWhitespace(c)) {
				continue;
			}
			else if (counter > 3) {
				return false;
			}

			if (counter == 0) {
				if (c != 'n') {
					return false;
				}
			}
			else if (counter == 1) {
				if (c != 'u') {
					return false;
				}
			}
			else if ((counter == 2) || (counter == 3)) {
				if (c != 'l') {
					return false;
				}
			}

			counter++;
		}

		if ((counter == 0) || (counter == 4)) {
			return true;
		}

		return false;
	}

}