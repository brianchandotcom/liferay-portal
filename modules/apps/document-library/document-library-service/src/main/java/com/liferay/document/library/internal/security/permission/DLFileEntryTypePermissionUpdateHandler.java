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

package com.liferay.document.library.internal.security.permission;

import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.dynamic.data.mapping.security.permission.DDMPermissionSupport;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.security.permission.PermissionUpdateHandler;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	property = "model.class.name=com.liferay.document.library.kernel.model.DLFileEntryType",
	service = PermissionUpdateHandler.class
)
public class DLFileEntryTypePermissionUpdateHandler
	implements PermissionUpdateHandler {

	@Override
	public void updatedPermission(String primKey) {
		try {
			DLFileEntryType dlFileEntryType =
				_dLFileEntryTypeLocalService.fetchDLFileEntryType(
					GetterUtil.getLong(primKey));

			if (dlFileEntryType == null) {
				return;
			}

			dlFileEntryType.setModifiedDate(new Date());

			_dLFileEntryTypeLocalService.updateDLFileEntryType(dlFileEntryType);

			String dlFileEntryMetadataResourceName =
				_ddmPermissionSupport.getStructureModelResourceName(
					DLFileEntryMetadata.class.getName());

			List<ResourceAction> dlFileEntryTypeResourceActions =
				_resourceActionLocalService.getResourceActions(
					DLFileEntryType.class.getName());

			List<ResourceAction> dlFileEntryMetadataResourceActions =
				_resourceActionLocalService.getResourceActions(
					dlFileEntryMetadataResourceName);

			Set<String> dlFileEntryMetadataActionIds = new HashSet<>();

			for (ResourceAction resourceAction :
					dlFileEntryMetadataResourceActions) {

				dlFileEntryMetadataActionIds.add(resourceAction.getActionId());
			}

			List<ResourcePermission> resourcePermissions =
				_resourcePermissionLocalService.getResourcePermissions(
					dlFileEntryType.getCompanyId(),
					DLFileEntryType.class.getName(),
					ResourceConstants.SCOPE_INDIVIDUAL,
					String.valueOf(dlFileEntryType.getFileEntryTypeId()));

			Map<Long, String[]> roleIdsToActionIds = new HashMap<>();

			for (ResourcePermission resourcePermission : resourcePermissions) {
				long actionIds = resourcePermission.getActionIds();

				List<String> resourcePermissionActionIds = new ArrayList<>();

				for (ResourceAction resourceAction :
						dlFileEntryTypeResourceActions) {

					String actionId = resourceAction.getActionId();

					if (((actionIds & resourceAction.getBitwiseValue()) ==
							resourceAction.getBitwiseValue()) &&
						dlFileEntryMetadataActionIds.contains(actionId)) {

						resourcePermissionActionIds.add(actionId);
					}
				}

				roleIdsToActionIds.put(
					resourcePermission.getRoleId(),
					resourcePermissionActionIds.toArray(new String[0]));
			}

			_resourcePermissionLocalService.setResourcePermissions(
				dlFileEntryType.getCompanyId(), dlFileEntryMetadataResourceName,
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(dlFileEntryType.getDataDefinitionId()),
				roleIdsToActionIds);
		}
		catch (PortalException portalException) {
			ReflectionUtil.throwException(portalException);
		}
	}

	@Reference
	private DDMPermissionSupport _ddmPermissionSupport;

	@Reference
	private DLFileEntryTypeLocalService _dLFileEntryTypeLocalService;

	@Reference
	private ResourceActionLocalService _resourceActionLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

}