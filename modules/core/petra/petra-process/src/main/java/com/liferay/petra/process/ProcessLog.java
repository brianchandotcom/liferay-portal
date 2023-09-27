/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.process;

/**
 * @author Shuyang Zhou
 */
public interface ProcessLog {

	public Level getLevel();

	public String getMessage();

	public Throwable getThrowable();

	public static enum Level {

		// Don't sort, order matters.

		DEBUG, INFO, WARN, ERROR

	}

}