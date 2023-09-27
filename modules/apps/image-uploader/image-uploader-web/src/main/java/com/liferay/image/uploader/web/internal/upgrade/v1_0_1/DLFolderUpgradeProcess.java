/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.image.uploader.web.internal.upgrade.v1_0_1;

import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.image.uploader.web.internal.util.UploadImageUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Lily Chi
 */
public class DLFolderUpgradeProcess extends UpgradeProcess {

	public DLFolderUpgradeProcess(DLFolderLocalService dlFolderLocalService) {
		_dlFolderLocalService = dlFolderLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select DLFolder.folderId from DLFolder inner join ",
					"Repository on DLFolder.repositoryId = Repository.",
					"repositoryId inner join Group_ on Repository.groupId =  ",
					"Group_.groupId inner join User_ on Group_.classPK = ",
					"User_.userId where DLFolder.name = ?"))) {

			preparedStatement.setString(1, "java.lang.Class");

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				DLFolder dlFolder = _dlFolderLocalService.getDLFolder(
					resultSet.getLong("folderId"));

				dlFolder.setName(UploadImageUtil.TEMP_IMAGE_FOLDER_NAME);

				_dlFolderLocalService.updateDLFolder(dlFolder);
			}
		}
	}

	private final DLFolderLocalService _dlFolderLocalService;

}