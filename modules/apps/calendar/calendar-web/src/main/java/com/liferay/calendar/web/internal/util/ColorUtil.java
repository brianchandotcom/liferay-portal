/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.web.internal.util;

import com.liferay.petra.string.StringPool;

/**
 * @author Fabio Pezzutto
 * @author Andrea Di Giorgi
 * @author Eduardo Lundgren
 */
public class ColorUtil {

	public static String toHexString(int color) {
		return StringPool.POUND.concat(String.format("%06X", 0xFFFFFF & color));
	}

}