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

package com.liferay.document.library.internal.upgrade.v3_2_6;

import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Adolfo Pérez
 */
public class DeleteStalePWCVersionsUpgradeProcess extends UpgradeProcess {

	public DeleteStalePWCVersionsUpgradeProcess(Store store) {
		_store = store;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select DLFileEntry.companyId, DLFileEntry.repositoryId, ",
					"DLFileEntry.name from DLFileEntry where ? not in (",
					"select version from DLFileVersion where ",
					"DLFileVersion.fileEntryId = DLFileEntry.fileEntryId)"))) {

			preparedStatement.setString(
				1, DLFileEntryConstants.PRIVATE_WORKING_COPY_VERSION);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					_store.deleteFile(
						resultSet.getLong(1), resultSet.getLong(2),
						resultSet.getString(3),
						DLFileEntryConstants.PRIVATE_WORKING_COPY_VERSION);
				}
			}
		}
	}

	private final Store _store;

}