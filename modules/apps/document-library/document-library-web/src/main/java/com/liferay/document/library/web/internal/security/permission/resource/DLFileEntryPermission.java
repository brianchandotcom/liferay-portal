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

package com.liferay.document.library.web.internal.security.permission.resource;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = {})
public class DLFileEntryPermission {

	public static void check(
			PermissionChecker permissionChecker, DLFileEntry fileEntry,
			String actionId)
		throws PortalException {

		_dlFileEntryModelResourcePermission.check(
			permissionChecker, fileEntry, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, DLFileEntry dlFileEntry,
			String actionId)
		throws PortalException {

		return _dlFileEntryModelResourcePermission.contains(
			permissionChecker, dlFileEntry, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, FileEntry fileEntry,
			String actionId)
		throws PortalException {

		return _fileEntryModelResourcePermission.contains(
			permissionChecker, fileEntry, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long fileEntryId,
			String actionId)
		throws PortalException {

		return _fileEntryModelResourcePermission.contains(
			permissionChecker, fileEntryId, actionId);
	}

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(model.class.name=com.liferay.document.library.kernel.model.DLFileEntry)"
	)
	protected void setDLFileEntryModelResourcePermission(
		ModelResourcePermission<DLFileEntry> modelResourcePermission) {

		_dlFileEntryModelResourcePermission = modelResourcePermission;
	}

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.repository.model.FileEntry)",
		unbind = "-"
	)
	protected void setFileEntryModelResourcePermission(
		ModelResourcePermission<FileEntry> modelResourcePermission) {

		_fileEntryModelResourcePermission = modelResourcePermission;
	}

	protected void unsetDLFileEntryModelResourcePermission(
		ModelResourcePermission<DLFileEntry> modelResourcePermission) {
	}

	private static ModelResourcePermission<DLFileEntry>
		_dlFileEntryModelResourcePermission;
	private static ModelResourcePermission<FileEntry>
		_fileEntryModelResourcePermission;

}