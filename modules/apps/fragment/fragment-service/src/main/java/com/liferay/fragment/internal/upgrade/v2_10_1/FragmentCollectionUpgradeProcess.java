/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.upgrade.v2_10_1;

import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Jürgen Kappler
 */
public class FragmentCollectionUpgradeProcess extends UpgradeProcess {

	public FragmentCollectionUpgradeProcess(
		DLFolderLocalService dlFolderLocalService) {

		_dlFolderLocalService = dlFolderLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeFragmentCollectionResourceFolder();
	}

	private void _upgradeFragmentCollectionResourceFolder() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select groupId, fragmentCollectionId, fragmentCollectionKey " +
					"from FragmentCollection")) {

			ResultSet resultSet = preparedStatement1.executeQuery();

			while (resultSet.next()) {
				long groupId = resultSet.getLong("groupId");

				Repository repository =
					PortletFileRepositoryUtil.fetchPortletRepository(
						groupId, FragmentPortletKeys.FRAGMENT);

				if (repository == null) {
					continue;
				}

				long fragmentCollectionId = resultSet.getLong(
					"fragmentCollectionId");
				String fragmentCollectionKey = resultSet.getString(
					"fragmentCollectionKey");

				try {
					Folder portletFolder =
						PortletFileRepositoryUtil.getPortletFolder(
							repository.getRepositoryId(),
							repository.getDlFolderId(),
							String.valueOf(fragmentCollectionId));

					if (portletFolder.getModel() instanceof DLFolder) {
						DLFolder dlFolder = (DLFolder)portletFolder.getModel();

						dlFolder.setName(fragmentCollectionKey);

						_dlFolderLocalService.updateDLFolder(dlFolder);
					}
				}
				catch (Exception exception) {
					if (_log.isWarnEnabled()) {
						_log.warn("Unable to get portlet folder", exception);
					}
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentCollectionUpgradeProcess.class);

	private final DLFolderLocalService _dlFolderLocalService;

}