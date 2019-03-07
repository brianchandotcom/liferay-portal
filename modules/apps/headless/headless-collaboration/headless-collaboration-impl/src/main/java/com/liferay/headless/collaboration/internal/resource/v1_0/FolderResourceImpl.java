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

package com.liferay.headless.collaboration.internal.resource.v1_0;

import com.liferay.headless.collaboration.dto.v1_0.Folder;
import com.liferay.headless.collaboration.internal.dto.v1_0.util.ParentFolderUtil;
import com.liferay.headless.collaboration.resource.v1_0.FolderResource;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.service.KBArticleService;
import com.liferay.knowledge.base.service.KBFolderService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.service.ClassNameService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/folder.properties",
	scope = ServiceScope.PROTOTYPE, service = FolderResource.class
)
public class FolderResourceImpl extends BaseFolderResourceImpl {

	@Override
	public boolean deleteFolder(Long folderId) throws Exception {
		_kbFolderService.deleteKBFolder(folderId);

		return true;
	}

	@Override
	public Page<Folder> getContentSpaceFoldersPage(
			Long contentSpaceId, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_kbFolderService.getKBFolders(
					contentSpaceId, 0, pagination.getStartPosition(),
					pagination.getEndPosition()),
				this::_toFolder),
			pagination, _kbFolderService.getKBFoldersCount(contentSpaceId, 0));
	}

	@Override
	public Folder getFolder(Long folderId) throws Exception {
		return _toFolder(_kbFolderService.getKBFolder(folderId));
	}

	@Override
	public Page<Folder> getFolderFoldersPage(
			Long folderId, Pagination pagination)
		throws Exception {

		KBFolder kbFolder = _kbFolderService.getKBFolder(folderId);

		return Page.of(
			transform(
				_kbFolderService.getKBFolders(
					kbFolder.getGroupId(), folderId,
					pagination.getStartPosition(), pagination.getEndPosition()),
				this::_toFolder),
			pagination,
			_kbFolderService.getKBFoldersCount(
				kbFolder.getGroupId(), folderId));
	}

	@Override
	public Folder postContentSpaceFolder(Long contentSpaceId, Folder folder)
		throws Exception {

		return _toFolder(
			_kbFolderService.addKBFolder(
				contentSpaceId, _getClassNameId(), 0, folder.getName(),
				folder.getDescription(), new ServiceContext()));
	}

	@Override
	public Folder postFolderFolder(Long folderId, Folder folder)
		throws Exception {

		KBFolder kbFolder = _kbFolderService.getKBFolder(folderId);

		return _toFolder(
			_kbFolderService.addKBFolder(
				kbFolder.getGroupId(), _getClassNameId(), folderId,
				folder.getName(), folder.getDescription(),
				new ServiceContext()));
	}

	@Override
	public Folder putFolder(Long folderId, Folder folder) throws Exception {
		Long parentFolderId = folder.getParentFolderId();

		if (parentFolderId == null) {
			parentFolderId = 0L;
		}

		return _toFolder(
			_kbFolderService.updateKBFolder(
				_getClassNameId(), parentFolderId, folderId, folder.getName(),
				folder.getDescription(), new ServiceContext()));
	}

	private long _getClassNameId() {
		ClassName className = _classNameService.fetchClassName(
			KBFolder.class.getName());

		return className.getClassNameId();
	}

	private Folder _toFolder(KBFolder kbFolder) throws PortalException {
		if (kbFolder == null) {
			return null;
		}

		return new Folder() {
			{
				dateCreated = kbFolder.getCreateDate();
				dateModified = kbFolder.getModifiedDate();
				description = kbFolder.getDescription();
				id = kbFolder.getKbFolderId();
				name = kbFolder.getName();
				parentFolder = ParentFolderUtil.toParentFolder(
					kbFolder.getParentKBFolder());

				setHasFolders(
					() -> {
						int count = _kbFolderService.getKBFoldersCount(
							kbFolder.getGroupId(), kbFolder.getKbFolderId());

						return count > 0;
					});
				setHasKnowledgeBaseArticles(
					() -> {
						int count = _kbArticleService.getKBArticlesCount(
							kbFolder.getGroupId(), kbFolder.getKbFolderId(), 0);

						return count > 0;
					});
			}
		};
	}

	@Reference
	private ClassNameService _classNameService;

	@Reference
	private KBArticleService _kbArticleService;

	@Reference
	private KBFolderService _kbFolderService;

}