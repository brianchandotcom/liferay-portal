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
public class DLSyncEventUpgradeCompanyId implements UpgradeCompanyIdInTable {

	@Override
	public String getTableName() {
		return "DLSyncEvent";
	}

	@Override
	public void upgradeProcess() throws Exception {

		// DLFileEntries

		String select =
			"(select dlfe.companyId, dlse.syncEventId from DLFileEntry dlfe, " +
				"DLSyncEvent dlse where dlse.type_='file' and " +
				"dlfe.fileEntryId=dlse.typePK) UNION (select re.companyId, " +
				"dlse.syncEventId from DLSyncEvent dlse, RepositoryEntry re " +
				"where dlse.type_='file' and dlse.typePK=re.repositoryId)";

		String update =
			"update DLSyncEvent set companyId = ? where syncEventId = ?";

		String[] columnNames = {"companyId", "syncEventId"};

		UpgradeCompanyIdUtil.updateCompanyColumnOnTable(
			"DLSyncEvent", select, update, columnNames);

		// DLFolders

		select =
			"(select dlf.companyId, dlse.syncEventId from DLFolder dlf, " +
				"DLSyncEvent dlse where dlse.type_='folder' and " +
				"dlf.folderId=dlse.typePK) UNION (select re.companyId, " +
				"dlse.syncEventId from DLSyncEvent dlse, RepositoryEntry re " +
				"where dlse.type_='folder' and dlse.typePK=re.repositoryId)";

		update = "update DLSyncEvent set companyId = ? where syncEventId = ?";

		UpgradeCompanyIdUtil.updateCompanyColumnOnTable(
			"DLSyncEvent", select, update, "companyId", "syncEventId");
	}

}