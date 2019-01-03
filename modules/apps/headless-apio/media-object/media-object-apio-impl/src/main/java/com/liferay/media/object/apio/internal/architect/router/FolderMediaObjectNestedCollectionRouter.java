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

package com.liferay.media.object.apio.internal.architect.router;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.router.NestedCollectionRouter;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.folder.apio.architect.identifier.FolderIdentifier;
import com.liferay.media.object.apio.architect.identifier.MediaObjectIdentifier;
import com.liferay.media.object.apio.internal.architect.form.MediaObjectCreatorForm;
import com.liferay.media.object.apio.internal.helper.MediaObjectHelper;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose the <a
 * href="http://schema.org/MediaObject">MediaObject</a> resources contained
 * inside a <a href="http://schema.org/Folder">Folder</a> through a web API. The
 * resources are mapped from the internal model {@link FileEntry}.
 *
 * @author Eduardo Pérez
 */
@Component(immediate = true, service = NestedCollectionRouter.class)
public class FolderMediaObjectNestedCollectionRouter
	implements NestedCollectionRouter
		<FileEntry, Long, MediaObjectIdentifier, Long, FolderIdentifier> {

	@Override
	public NestedCollectionRoutes<FileEntry, Long, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<FileEntry, Long, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
		).addCreator(
			this::_getFileEntry,
			_hasPermission.forAddingIn(FolderIdentifier.class),
			MediaObjectCreatorForm::buildForm
		).build();
	}

	private FileEntry _getFileEntry(
			long folderId, MediaObjectCreatorForm mediaObjectCreatorForm)
		throws Exception {

		Folder folder = _dlAppService.getFolder(folderId);

		return _mediaObjectHelper.addFileEntry(
			folder.getRepositoryId(), folderId, mediaObjectCreatorForm);
	}

	private PageItems<FileEntry> _getPageItems(
			Pagination pagination, long folderId)
		throws PortalException {

		Folder folder = _dlAppService.getFolder(folderId);

		List<FileEntry> fileEntries = _dlAppService.getFileEntries(
			folder.getGroupId(), folderId, pagination.getStartPosition(),
			pagination.getEndPosition(), null);
		int count = _dlAppService.getFileEntriesCount(
			folder.getGroupId(), folderId);

		return new PageItems<>(fileEntries, count);
	}

	@Reference
	private DLAppService _dlAppService;

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.repository.model.FileEntry)"
	)
	private HasPermission<Long> _hasPermission;

	@Reference
	private MediaObjectHelper _mediaObjectHelper;

}