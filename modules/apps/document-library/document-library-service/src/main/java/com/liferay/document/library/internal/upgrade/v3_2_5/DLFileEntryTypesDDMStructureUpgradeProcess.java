/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.upgrade.v3_2_5;

import com.liferay.document.library.internal.util.DLFileEntryTypePermissionUtil;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.dynamic.data.mapping.security.permission.DDMPermissionSupport;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Matthias Bläsing
 */
public class DLFileEntryTypesDDMStructureUpgradeProcess extends UpgradeProcess {

	public DLFileEntryTypesDDMStructureUpgradeProcess(
		DDMPermissionSupport ddmPermissionSupport,
		ResourceActionLocalService resourceActionLocalService,
		ResourcePermissionLocalService resourcePermissionLocalService) {

		_ddmPermissionSupport = ddmPermissionSupport;
		_resourceActionLocalService = resourceActionLocalService;
		_resourcePermissionLocalService = resourcePermissionLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		String dlFileEntryMetadataResourceName =
			_ddmPermissionSupport.getStructureModelResourceName(
				DLFileEntryMetadata.class.getName());

		List<ResourceAction> dlFileEntryMetadataResourceAction =
			_resourceActionLocalService.getResourceActions(
				dlFileEntryMetadataResourceName);

		Set<String> dlFileEntryMetadataActionIds = new HashSet<>();

		for (ResourceAction resourceAction :
				dlFileEntryMetadataResourceAction) {

			dlFileEntryMetadataActionIds.add(resourceAction.getActionId());
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select companyId, fileEntryTypeId, dataDefinitionId from " +
					"DLFileEntryType");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long companyId = resultSet.getLong("companyId");
				long fileEntryTypeId = resultSet.getLong("fileEntryTypeId");
				long dataDefinitionId = resultSet.getLong("dataDefinitionId");

				List<ResourcePermission> resourcePermissions =
					_resourcePermissionLocalService.getResourcePermissions(
						companyId, DLFileEntryType.class.getName(),
						ResourceConstants.SCOPE_INDIVIDUAL,
						String.valueOf(fileEntryTypeId));

				_resourcePermissionLocalService.setResourcePermissions(
					companyId, dlFileEntryMetadataResourceName,
					ResourceConstants.SCOPE_INDIVIDUAL,
					String.valueOf(dataDefinitionId),
					DLFileEntryTypePermissionUtil.getRoleIdsToActionIds(
						_resourceActionLocalService.getResourceActions(
							DLFileEntryType.class.getName()),
						resourcePermissions,
						dlFileEntryMetadataActionIds::contains));
			}
		}
	}

	private final DDMPermissionSupport _ddmPermissionSupport;
	private final ResourceActionLocalService _resourceActionLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;

}