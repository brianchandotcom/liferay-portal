/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search;

/**
 * @author Michael C. Han
 */
public interface IndexAdminHelper {

	public String backup(long companyId, String backupName)
		throws SearchException;

	public void backup(String backupName) throws SearchException;

	public void removeBackup(long companyId, String backupName)
		throws SearchException;

	public void removeBackup(String backupName) throws SearchException;

	public void restore(long companyId, String backupName)
		throws SearchException;

	public void restore(String backupName) throws SearchException;

}