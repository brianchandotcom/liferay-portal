/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Michael C. Han
 */
public class IndexAdminHelperUtil {

	public static void backup(long companyId, String backupName)
		throws SearchException {

		_indexAdminHelper.backup(companyId, backupName);
	}

	public static String backup(
			long companyId, String searchEngineId, String backupName)
		throws SearchException {

		return _indexAdminHelper.backup(companyId, searchEngineId, backupName);
	}

	public static void backup(String backupName) throws SearchException {
		_indexAdminHelper.backup(backupName);
	}

	public static void removeBackup(long companyId, String backupName)
		throws SearchException {

		_indexAdminHelper.removeBackup(companyId, backupName);
	}

	public static void removeBackup(String backupName) throws SearchException {
		_indexAdminHelper.removeBackup(backupName);
	}

	public static void restore(long companyId, String backupName)
		throws SearchException {

		_indexAdminHelper.restore(companyId, backupName);
	}

	public static void restore(String backupName) throws SearchException {
		_indexAdminHelper.restore(backupName);
	}

	private static volatile IndexAdminHelper _indexAdminHelper =
		ServiceProxyFactory.newServiceTrackedInstance(
			IndexAdminHelper.class, IndexAdminHelperUtil.class,
			"_indexAdminHelper", false);

}