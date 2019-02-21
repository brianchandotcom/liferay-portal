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

package com.liferay.sharing.document.library.internal.security.permission;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.sharing.document.library.internal.security.permission.resource.SharingEntryDLFileEntryModelResourcePermissionRegistrar;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.service.SharingEntryLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = SharingPermissionHelper.class)
public class SharingPermissionHelper {

	public boolean isShareable(
		PermissionChecker permissionChecker, long fileEntryId) {

		DLFileEntry dlFileEntry = _dlFileEntryLocalService.fetchDLFileEntry(
			fileEntryId);

		if (dlFileEntry == null) {
			return false;
		}

		if (permissionChecker.isOmniadmin() ||
			permissionChecker.isCompanyAdmin() ||
			permissionChecker.isGroupAdmin(dlFileEntry.getGroupId()) ||
			permissionChecker.hasOwnerPermission(
				dlFileEntry.getCompanyId(), DLFileEntryConstants.getClassName(),
				fileEntryId, dlFileEntry.getUserId(), ActionKeys.VIEW)) {

			return true;
		}

		List<SharingEntry> sharingEntries =
			_sharingEntryLocalService.getToUserClassPKSharingEntries(
				permissionChecker.getUserId(),
				_classNameLocalService.getClassNameId(
					DLFileEntryConstants.getClassName()),
				fileEntryId);

		for (SharingEntry sharingEntry : sharingEntries) {
			if (sharingEntry.isShareable()) {
				return true;
			}
		}

		return false;
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference(
		target = "(&(model.class.name=com.liferay.document.library.kernel.model.DLFileEntry)(!(component.name=" + SharingEntryDLFileEntryModelResourcePermissionRegistrar.COMPONENT_NAME + ")))"
	)
	private ModelResourcePermission<DLFileEntry>
		_dlFileEntryModelResourcePermission;

	@Reference
	private SharingEntryLocalService _sharingEntryLocalService;

}