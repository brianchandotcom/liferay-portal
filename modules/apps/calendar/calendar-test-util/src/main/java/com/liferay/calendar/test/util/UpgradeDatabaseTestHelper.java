/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.test.util;

/**
 * @author Adam Brandizzi
 */
public interface UpgradeDatabaseTestHelper extends AutoCloseable {

	public void dropColumn(
			String tableClassName, String tableName, String columnName)
		throws Exception;

	public boolean hasColumn(String tableName, String columnName)
		throws Exception;

}