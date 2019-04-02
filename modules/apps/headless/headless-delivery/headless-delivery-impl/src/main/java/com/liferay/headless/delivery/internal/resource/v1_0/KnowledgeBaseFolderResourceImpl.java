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

package com.liferay.headless.delivery.internal.resource.v1_0;

import com.liferay.headless.delivery.dto.v1_0.KnowledgeBaseFolder;
import com.liferay.headless.delivery.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.ParentKnowledgeBaseFolderUtil;
import com.liferay.headless.delivery.resource.v1_0.KnowledgeBaseFolderResource;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.service.KBArticleService;
import com.liferay.knowledge.base.service.KBFolderService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/knowledge-base-folder.properties",
	scope = ServiceScope.PROTOTYPE, service = KnowledgeBaseFolderResource.class
)
public class KnowledgeBaseFolderResourceImpl
	extends BaseKnowledgeBaseFolderResourceImpl {

	@Override
	public void deleteKnowledgeBaseFolder(Long knowledgeBaseFolderId)
		throws Exception {

		_kbFolderService.deleteKBFolder(knowledgeBaseFolderId);
	}

	@Override
	public Page<KnowledgeBaseFolder> getContentSpaceKnowledgeBaseFoldersPage(
			Long contentSpaceId, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_kbFolderService.getKBFolders(
					contentSpaceId, 0, pagination.getStartPosition(),
					pagination.getEndPosition()),
				this::_toKnowledgeBaseFolder),
			pagination, _kbFolderService.getKBFoldersCount(contentSpaceId, 0));
	}

	@Override
	public KnowledgeBaseFolder getKnowledgeBaseFolder(
			Long knowledgeBaseFolderId)
		throws Exception {

		return _toKnowledgeBaseFolder(
			_kbFolderService.getKBFolder(knowledgeBaseFolderId));
	}

	@Override
	public Page<KnowledgeBaseFolder>
			getKnowledgeBaseFolderKnowledgeBaseFoldersPage(
				Long knowledgeBaseFolderId, Pagination pagination)
		throws Exception {

		KBFolder kbFolder = _kbFolderService.getKBFolder(knowledgeBaseFolderId);

		return Page.of(
			transform(
				_kbFolderService.getKBFolders(
					kbFolder.getGroupId(), knowledgeBaseFolderId,
					pagination.getStartPosition(), pagination.getEndPosition()),
				this::_toKnowledgeBaseFolder),
			pagination,
			_kbFolderService.getKBFoldersCount(
				kbFolder.getGroupId(), knowledgeBaseFolderId));
	}

	@Override
	public KnowledgeBaseFolder postContentSpaceKnowledgeBaseFolder(
			Long contentSpaceId, KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		return _toKnowledgeBaseFolder(
			_kbFolderService.addKBFolder(
				contentSpaceId, _getClassNameId(), 0,
				knowledgeBaseFolder.getName(),
				knowledgeBaseFolder.getDescription(), new ServiceContext()));
	}

	@Override
	public KnowledgeBaseFolder postKnowledgeBaseFolderKnowledgeBaseFolder(
			Long knowledgeBaseFolderId, KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		KBFolder kbFolder = _kbFolderService.getKBFolder(knowledgeBaseFolderId);

		return _toKnowledgeBaseFolder(
			_kbFolderService.addKBFolder(
				kbFolder.getGroupId(), _getClassNameId(), knowledgeBaseFolderId,
				knowledgeBaseFolder.getName(),
				knowledgeBaseFolder.getDescription(), new ServiceContext()));
	}

	@Override
	public KnowledgeBaseFolder putKnowledgeBaseFolder(
			Long knowledgeBaseFolderId, KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		Long parentKnowledgeBaseFolderId =
			knowledgeBaseFolder.getParentKnowledgeBaseFolderId();

		if (parentKnowledgeBaseFolderId == null) {
			parentKnowledgeBaseFolderId = 0L;
		}

		return _toKnowledgeBaseFolder(
			_kbFolderService.updateKBFolder(
				_getClassNameId(), parentKnowledgeBaseFolderId,
				knowledgeBaseFolderId, knowledgeBaseFolder.getName(),
				knowledgeBaseFolder.getDescription(), new ServiceContext()));
	}

	private long _getClassNameId() {
		return _portal.getClassNameId(KBFolder.class.getName());
	}

	private KnowledgeBaseFolder _toKnowledgeBaseFolder(KBFolder kbFolder)
		throws Exception {

		if (kbFolder == null) {
			return null;
		}

		return new KnowledgeBaseFolder() {
			{
				creator = CreatorUtil.toCreator(
					_portal, _userLocalService.getUser(kbFolder.getUserId()));
				dateCreated = kbFolder.getCreateDate();
				dateModified = kbFolder.getModifiedDate();
				description = kbFolder.getDescription();
				id = kbFolder.getKbFolderId();
				name = kbFolder.getName();
				numberOfKnowledgeBaseArticles =
					_kbArticleService.getKBArticlesCount(
						kbFolder.getGroupId(), kbFolder.getKbFolderId(), 0);
				numberOfKnowledgeBaseFolders =
					_kbFolderService.getKBFoldersCount(
						kbFolder.getGroupId(), kbFolder.getKbFolderId());
				parentKnowledgeBaseFolder =
					ParentKnowledgeBaseFolderUtil.toParentKnowledgeBaseFolder(
						kbFolder.getParentKBFolder());
			}
		};
	}

	@Reference
	private KBArticleService _kbArticleService;

	@Reference
	private KBFolderService _kbFolderService;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}