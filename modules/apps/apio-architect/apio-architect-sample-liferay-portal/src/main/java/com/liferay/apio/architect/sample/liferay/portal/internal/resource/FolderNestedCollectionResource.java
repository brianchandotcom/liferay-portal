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

package com.liferay.apio.architect.sample.liferay.portal.internal.resource;

import static com.liferay.apio.architect.sample.liferay.portal.internal.idempotent.Idempotent.idempotent;

import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.apio.architect.sample.liferay.portal.internal.form.FolderForm;
import com.liferay.apio.architect.sample.liferay.portal.internal.identifier.FolderIdentifier;
import com.liferay.apio.architect.sample.liferay.portal.internal.identifier.WebSiteIdentifier;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;

import javax.ws.rs.BadRequestException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose folder resources through a web
 * API. The resources are mapped from the internal model {@code Folder}.
 *
 * @author Javier Gamarra
 */
@Component(immediate = true)
public class FolderNestedCollectionResource
	implements NestedCollectionResource<Folder, Long, FolderIdentifier,
		Long, WebSiteIdentifier> {

	@Override
	public NestedCollectionRoutes<Folder, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<Folder, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
		).addCreator(
			this::_addFolder, (credentials, groupId) -> true,
			FolderForm::buildForm
		).build();
	}

	@Override
	public String getName() {
		return "folders";
	}

	@Override
	public ItemRoutes<Folder, Long> itemRoutes(
		ItemRoutes.Builder<Folder, Long> builder) {

		return builder.addGetter(
			_dlAppService::getFolder
		).addRemover(
			idempotent(_dlAppService::deleteFolder),
			(credentials, folderId) -> true
		).addUpdater(
			this::_updateFolder, (credentials, folderId) -> true,
			FolderForm::buildForm
		).build();
	}

	@Override
	public Representor<Folder, Long> representor(
		Representor.Builder<Folder, Long> builder) {

		return builder.types(
			"Folder"
		).identifier(
			Folder::getFolderId
		).addBidirectionalModel(
			"interactionService", "folder", WebSiteIdentifier.class,
			Folder::getGroupId
		).addDate(
			"dateCreated", Folder::getCreateDate
		).addDate(
			"dateModified", Folder::getCreateDate
		).addDate(
			"datePublished", Folder::getCreateDate
		).addString(
			"name", Folder::getName
		).build();
	}

	private Folder _addFolder(Long groupId, FolderForm folderForm)
		throws PortalException {

		long parentFolderId = 0;

		Try<Folder> folderTry = Try.fromFallible(
			() -> _dlAppService.getFolder(
				groupId, parentFolderId, folderForm.getName()));

		if (folderTry.isSuccess()) {
			throw new BadRequestException(
				"A folder with that name already exists");
		}

		return _dlAppService.addFolder(
			groupId, parentFolderId, folderForm.getName(),
			folderForm.getDescription(), new ServiceContext());
	}

	private PageItems<Folder> _getPageItems(Pagination pagination, Long groupId)
		throws PortalException {

		List<Folder> folders = _dlAppService.getFolders(
			groupId, 0, pagination.getStartPosition(),
			pagination.getEndPosition(), null);
		int count = _dlAppService.getFoldersCount(groupId, 0);

		return new PageItems<>(folders, count);
	}

	private Folder _updateFolder(Long folderId, FolderForm folderForm)
		throws PortalException {

		return _dlAppService.updateFolder(
			folderId, folderForm.getName(), folderForm.getDescription(),
			new ServiceContext());
	}

	@Reference
	private DLAppService _dlAppService;

}