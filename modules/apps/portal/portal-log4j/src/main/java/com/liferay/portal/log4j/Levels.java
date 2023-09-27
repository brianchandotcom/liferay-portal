/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.log4j;

import org.apache.log4j.Level;

/**
 * @author Brian Wing Shun Chan
 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
 */
@Deprecated
public class Levels {

	public static final Level[] ALL_LEVELS = {
		Level.OFF, Level.FATAL, Level.ERROR, Level.WARN, Level.INFO,
		Level.DEBUG, Level.TRACE, Level.ALL
	};

}