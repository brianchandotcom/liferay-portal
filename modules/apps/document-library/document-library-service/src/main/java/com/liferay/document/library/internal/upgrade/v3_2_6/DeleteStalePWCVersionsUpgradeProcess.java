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

/**
 * @author Adolfo Pérez
 */
public class DeleteStalePWCVersionsUpgradeProcess extends UpgradeProcess {

	public DeleteStalePWCVersionsUpgradeProcess(Store store) {
		_store = store;
	}

	@Override
	protected void doUpgrade() throws Exception {
		processConcurrently(
			StringBundler.concat(
				"select DLFileEntry.companyId, DLFileEntry.repositoryId, ",
				"DLFileEntry.name from DLFileEntry where '",
				DLFileEntryConstants.PRIVATE_WORKING_COPY_VERSION,
				"' not in (select version from DLFileVersion where ",
				"DLFileVersion.fileEntryId = DLFileEntry.fileEntryId)"),
			resultSet -> new Object[] {
				resultSet.getLong(1), resultSet.getLong(2),
				resultSet.getString(3)
			},
			columns -> _store.deleteFile(
				(long)columns[0], (long)columns[1], (String)columns[2],
				DLFileEntryConstants.PRIVATE_WORKING_COPY_VERSION),
			"Unable to delete PWC version data in the store");
	}

	private final Store _store;

}