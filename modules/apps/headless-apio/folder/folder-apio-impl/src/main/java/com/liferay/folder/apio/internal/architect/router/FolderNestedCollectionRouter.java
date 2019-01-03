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

package com.liferay.folder.apio.internal.architect.router;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.router.NestedCollectionRouter;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.folder.apio.architect.identifier.FolderIdentifier;
import com.liferay.folder.apio.internal.architect.form.FolderForm;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose the <a
 * href="http://schema.org/Folder">Folder</a> resources contained inside a <a
 * href="http://schema.org/Folder">Folder</a> through a web API. The resources
 * are mapped from the internal model {@link Folder}.
 *
 * @author Eduardo Pérez
 */
@Component(immediate = true, service = NestedCollectionRouter.class)
public class FolderNestedCollectionRouter
	implements NestedCollectionRouter
		<Folder, Long, FolderIdentifier, Long, FolderIdentifier> {

	@Override
	public NestedCollectionRoutes<Folder, Long, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<Folder, Long, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
		).addCreator(
			this::_addFolder,
			_hasPermission.forAddingIn(FolderIdentifier.class),
			FolderForm::buildForm
		).build();
	}

	private Folder _addFolder(long parentFolderId, FolderForm folderForm)
		throws PortalException {

		Folder folder = _dlAppLocalService.getFolder(parentFolderId);

		return _dlAppService.addFolder(
			folder.getGroupId(), parentFolderId, folderForm.getName(),
			folderForm.getDescription(), new ServiceContext());
	}

	private PageItems<Folder> _getPageItems(
			Pagination pagination, long parentFolderId)
		throws PortalException {

		Folder parentFolder = _dlAppService.getFolder(parentFolderId);

		long groupId = parentFolder.getGroupId();

		List<Folder> folders = _dlAppService.getFolders(
			groupId, parentFolderId, pagination.getStartPosition(),
			pagination.getEndPosition(), null);
		int count = _dlAppService.getFoldersCount(groupId, parentFolderId);

		return new PageItems<>(folders, count);
	}

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private DLAppService _dlAppService;

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.repository.model.Folder)"
	)
	private HasPermission<Long> _hasPermission;

}