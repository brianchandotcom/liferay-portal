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

package com.liferay.document.library.internal.upgrade.v3_2_5;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.dynamic.data.mapping.security.permission.DDMPermissionSupport;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Matthias Bläsing
 */
public class SyncDLFileEntryTypesDDMStructurePermissions extends UpgradeProcess {

	public SyncDLFileEntryTypesDDMStructurePermissions(
                DDMPermissionSupport ddmPermissionSupport,
                ResourceActionLocalService resourceActionLocalService,
		ResourcePermissionLocalService resourcePermissionLocalService) {

                _ddmPermissionSupport = ddmPermissionSupport;
                _resourceActionLocalService = resourceActionLocalService;
		_resourcePermissionLocalService = resourcePermissionLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
                String metadataResourceName = _ddmPermissionSupport
                        .getStructureModelResourceName(DLFileEntryMetadata.class.getName());

                List<ResourceAction> fileEntryTypeActions = _resourceActionLocalService
                        .getResourceActions(DLFileEntryType.class.getName());

                Set<String> fileEntryMetadataActions = _resourceActionLocalService
                        .getResourceActions(metadataResourceName)
                        .stream()
                        .map(ra -> ra.getActionId())
                        .collect(Collectors.toSet());

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select companyId, fileEntryTypeId, dataDefinitionId from DLFileEntryType");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long companyId = resultSet.getLong("companyId");
				long fileEntryTypeId = resultSet.getLong("fileEntryTypeId");
				long dataDefinitionId = resultSet.getLong("dataDefinitionId");

                                List<ResourcePermission> permissions = _resourcePermissionLocalService.getResourcePermissions(
                                        companyId,
                                        DLFileEntryType.class.getName(),
                                        ResourceConstants.SCOPE_INDIVIDUAL,
                                        Long.toString(fileEntryTypeId)
                                );

                                Map<Long,String[]> roleIdsToActionIds = new HashMap<>();

                                for(ResourcePermission permission: permissions) {
                                        long actionIds = permission.getActionIds();
                                        List<String> actionIdList = new ArrayList<>();
                                        for(ResourceAction ra: fileEntryTypeActions) {
                                            String actionId = ra.getActionId();
                                            if((actionIds & ra.getBitwiseValue()) == ra.getBitwiseValue() && fileEntryMetadataActions.contains(actionId)) {
                                                actionIdList.add(actionId);
                                            }
                                        }
                                        roleIdsToActionIds.put(permission.getRoleId(), actionIdList.toArray(new String[0]));
                                }

                                try {
                                    _resourcePermissionLocalService.setResourcePermissions(
                                            companyId,
                                            metadataResourceName,
                                            ResourceConstants.SCOPE_INDIVIDUAL,
                                            Long.toString(dataDefinitionId),
                                            roleIdsToActionIds
                                    );
                                }
                                catch (PortalException portalException) {
                                        ReflectionUtil.throwException(portalException);
                                }
			}
		}
	}

	private final ResourceActionLocalService _resourceActionLocalService;

	private final ResourcePermissionLocalService _resourcePermissionLocalService;

        private final DDMPermissionSupport _ddmPermissionSupport;
}