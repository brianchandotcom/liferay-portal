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

package com.liferay.portal.upgrade.v7_0_0.util.companyId;

import com.liferay.portal.kernel.upgrade.util.UpgradeCompanyIdInTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeCompanyIdUtil;

/**
 * @author Cristina González
 */
public class DLFileEntryTypesDLFoldersUpgradeCompanyId
	implements UpgradeCompanyIdInTable {

	@Override
	public String getTableName() {
		return "DLFileEntryTypes_DLFolders";
	}

	@Override
	public void upgradeProcess() throws Exception {
		String select =
			"select dlfet.companyId, dlfetdlf.fileEntryTypeId, " +
				"dlfetdlf.folderId from DLFileEntryTypes_DLFolders dlfetdlf, " +
				"DLFileEntryType dlfet, DLFolder dlf " +
				"where dlf.companyId=dlfet.companyId and " +
				"dlfet.fileEntryTypeId=dlfetdlf.fileEntryTypeId and " +
				"dlf.folderId=dlfetdlf.folderId";

		String update =
			"update DLFileEntryTypes_DLFolders set companyId = ? " +
				"where fileEntryTypeId = ? and folderId = ?";

		UpgradeCompanyIdUtil.updateCompanyColumnOnTable(
			"DLFileEntryTypes_DLFolders", select, update, "companyId",
			"fileEntryTypeId", "folderId");
	}

}