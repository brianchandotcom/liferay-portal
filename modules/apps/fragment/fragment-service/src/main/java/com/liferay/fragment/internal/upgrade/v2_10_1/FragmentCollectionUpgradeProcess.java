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

package com.liferay.fragment.internal.upgrade.v2_10_1;

import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;

/**
 * @author Jürgen Kappler
 */
public class FragmentCollectionUpgradeProcess extends UpgradeProcess {

	public FragmentCollectionUpgradeProcess(
		DLFolderLocalService dlFolderLocalService,
		GroupLocalService groupLocalService) {

		_dlFolderLocalService = dlFolderLocalService;
		_groupLocalService = groupLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeFragmentCollectionResourceFolder();
	}

	private void _upgradeFragmentCollectionResourceFolder() throws Exception {
		List<Group> groups = _groupLocalService.getGroups(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (Group group : groups) {
			Repository repository =
				PortletFileRepositoryUtil.fetchPortletRepository(
					group.getGroupId(), FragmentPortletKeys.FRAGMENT);

			if (repository == null) {
				continue;
			}

			try (PreparedStatement preparedStatement1 =
					connection.prepareStatement(
						"select fragmentCollectionId, fragmentCollectionKey " +
							"from FragmentCollection where groupId = ?")) {

				preparedStatement1.setLong(1, group.getGroupId());

				ResultSet resultSet = preparedStatement1.executeQuery();

				while (resultSet.next()) {
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
							DLFolder dlFolder =
								(DLFolder)portletFolder.getModel();

							dlFolder.setName(fragmentCollectionKey);

							_dlFolderLocalService.updateDLFolder(dlFolder);
						}
					}
					catch (Exception exception) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to get portlet folder", exception);
						}
					}
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentCollectionUpgradeProcess.class);

	private final DLFolderLocalService _dlFolderLocalService;
	private final GroupLocalService _groupLocalService;

}