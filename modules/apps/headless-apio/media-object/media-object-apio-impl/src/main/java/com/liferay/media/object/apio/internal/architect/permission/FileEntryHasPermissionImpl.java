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

package com.liferay.media.object.apio.internal.architect.permission;

import com.liferay.apio.architect.alias.routes.permission.HasNestedAddingPermissionFunction;
import com.liferay.apio.architect.credentials.Credentials;
import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.folder.apio.architect.identifier.FolderIdentifier;
import com.liferay.folder.apio.architect.identifier.RootFolderIdentifier;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
@Component(
	property = "model.class.name=com.liferay.portal.kernel.repository.model.FileEntry"
)
public class FileEntryHasPermissionImpl implements HasPermission<Long> {

	@Override
	public <S> HasNestedAddingPermissionFunction<S> forAddingIn(
		Class<? extends Identifier<S>> identifierClass) {

		if (identifierClass.equals(FolderIdentifier.class)) {
			return (credentials, folderId) ->
				_folderModelResourcePermission.contains(
					(PermissionChecker)credentials.get(), (Long)folderId,
					ActionKeys.ADD_DOCUMENT);
		}

		if (identifierClass.equals(RootFolderIdentifier.class)) {
			return (credentials, groupId) ->
				ModelResourcePermissionHelper.contains(
					_folderModelResourcePermission,
					(PermissionChecker)credentials.get(), (Long)groupId, 0,
					ActionKeys.ADD_DOCUMENT);
		}

		return (credentials, s) -> false;
	}

	@Override
	public Boolean forDeleting(Credentials credentials, Long fileEntryId)
		throws PortalException {

		return _fileEntryModelResourcePermission.contains(
			(PermissionChecker)credentials.get(), fileEntryId,
			ActionKeys.DELETE);
	}

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.repository.model.FileEntry)"
	)
	private ModelResourcePermission _fileEntryModelResourcePermission;

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.repository.model.Folder)"
	)
	private ModelResourcePermission _folderModelResourcePermission;

}