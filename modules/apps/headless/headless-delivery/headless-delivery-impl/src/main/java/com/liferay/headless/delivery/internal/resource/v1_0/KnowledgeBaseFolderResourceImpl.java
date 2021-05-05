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

import com.liferay.headless.common.spi.service.context.ServiceContextRequestUtil;
import com.liferay.headless.delivery.dto.v1_0.KnowledgeBaseFolder;
import com.liferay.headless.delivery.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.delivery.dto.v1_0.util.CustomFieldsUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.ParentKnowledgeBaseFolderUtil;
import com.liferay.headless.delivery.resource.v1_0.KnowledgeBaseFolderResource;
import com.liferay.knowledge.base.constants.KBActionKeys;
import com.liferay.knowledge.base.constants.KBConstants;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.service.KBArticleService;
import com.liferay.knowledge.base.service.KBFolderService;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.io.Serializable;

import java.util.Map;
import java.util.Optional;

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
	public KnowledgeBaseFolder getKnowledgeBaseFolder(
			Long knowledgeBaseFolderId)
		throws Exception {

		return _toKnowledgeBaseFolder(
			_kbFolderService.getKBFolder(knowledgeBaseFolderId));
	}

	@Override
	public Page<KnowledgeBaseFolder>
			getKnowledgeBaseFolderKnowledgeBaseFoldersPage(
				Long parentKnowledgeBaseFolderId, Pagination pagination)
		throws Exception {

		KBFolder kbFolder = _kbFolderService.getKBFolder(
			parentKnowledgeBaseFolderId);

		return Page.of(
			HashMapBuilder.put(
				"create",
				addAction(
					KBActionKeys.ADD_KB_FOLDER,
					"postKnowledgeBaseFolderKnowledgeBaseFolder",
					KBConstants.RESOURCE_NAME_ADMIN, kbFolder.getGroupId())
			).put(
				"get",
				addAction(
					ActionKeys.VIEW,
					"getKnowledgeBaseFolderKnowledgeBaseFoldersPage",
					KBConstants.RESOURCE_NAME_ADMIN, kbFolder.getGroupId())
			).build(),
			transform(
				_kbFolderService.getKBFolders(
					kbFolder.getGroupId(), parentKnowledgeBaseFolderId,
					pagination.getStartPosition(), pagination.getEndPosition()),
				this::_toKnowledgeBaseFolder),
			pagination,
			_kbFolderService.getKBFoldersCount(
				kbFolder.getGroupId(), parentKnowledgeBaseFolderId));
	}

	@Override
	public Page<KnowledgeBaseFolder> getSiteKnowledgeBaseFoldersPage(
			Long siteId, Pagination pagination)
		throws Exception {

		return Page.of(
			HashMapBuilder.put(
				"create",
				addAction(
					KBActionKeys.ADD_KB_FOLDER, "postSiteKnowledgeBaseFolder",
					KBConstants.RESOURCE_NAME_ADMIN, siteId)
			).put(
				"get",
				addAction(
					ActionKeys.VIEW, "getSiteKnowledgeBaseFoldersPage",
					KBConstants.RESOURCE_NAME_ADMIN, siteId)
			).build(),
			transform(
				_kbFolderService.getKBFolders(
					siteId, 0, pagination.getStartPosition(),
					pagination.getEndPosition()),
				this::_toKnowledgeBaseFolder),
			pagination, _kbFolderService.getKBFoldersCount(siteId, 0));
	}

	@Override
	public KnowledgeBaseFolder postKnowledgeBaseFolderKnowledgeBaseFolder(
			Long parentKnowledgeBaseFolderId,
			KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		KBFolder parentKBFolder = _kbFolderService.getKBFolder(
			parentKnowledgeBaseFolderId);

		return _toKnowledgeBaseFolder(
			_kbFolderService.addKBFolder(
				parentKBFolder.getGroupId(), _getClassNameId(),
				parentKnowledgeBaseFolderId, knowledgeBaseFolder.getName(),
				knowledgeBaseFolder.getDescription(),
				ServiceContextRequestUtil.createServiceContext(
					_getExpandoBridgeAttributes(knowledgeBaseFolder),
					parentKBFolder.getGroupId(), contextHttpServletRequest,
					knowledgeBaseFolder.getViewableByAsString())));
	}

	@Override
	public KnowledgeBaseFolder postSiteKnowledgeBaseFolder(
			Long siteId, KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		return _toKnowledgeBaseFolder(
			_kbFolderService.addKBFolder(
				siteId, _getClassNameId(), 0, knowledgeBaseFolder.getName(),
				knowledgeBaseFolder.getDescription(),
				ServiceContextRequestUtil.createServiceContext(
					_getExpandoBridgeAttributes(knowledgeBaseFolder), siteId,
					contextHttpServletRequest,
					knowledgeBaseFolder.getViewableByAsString())));
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
				knowledgeBaseFolder.getDescription(),
				ServiceContextRequestUtil.createServiceContext(
					_getExpandoBridgeAttributes(knowledgeBaseFolder), 0,
					contextHttpServletRequest, null)));
	}

	@Override
	protected Long getPermissionCheckerGroupId(Object id) throws Exception {
		KBFolder kbFolder = _kbFolderService.getKBFolder((Long)id);

		return kbFolder.getGroupId();
	}

	@Override
	protected String getPermissionCheckerPortletName(Object id) {
		return KBConstants.RESOURCE_NAME_ADMIN;
	}

	@Override
	protected String getPermissionCheckerResourceName(Object id) {
		return KBFolder.class.getName();
	}

	private long _getClassNameId() {
		return _portal.getClassNameId(KBFolder.class.getName());
	}

	private Map<String, Serializable> _getExpandoBridgeAttributes(
		KnowledgeBaseFolder knowledgeBaseFolder) {

		return CustomFieldsUtil.toMap(
			KBFolder.class.getName(), contextCompany.getCompanyId(),
			knowledgeBaseFolder.getCustomFields(),
			contextAcceptLanguage.getPreferredLocale());
	}

	private KnowledgeBaseFolder _toKnowledgeBaseFolder(KBFolder kbFolder)
		throws Exception {

		if (kbFolder == null) {
			return null;
		}

		return new KnowledgeBaseFolder() {
			{
				actions = HashMapBuilder.put(
					"delete",
					addAction(
						ActionKeys.DELETE, kbFolder,
						"deleteKnowledgeBaseFolder")
				).put(
					"get",
					addAction(
						ActionKeys.VIEW, kbFolder, "getKnowledgeBaseFolder")
				).put(
					"replace",
					addAction(
						ActionKeys.UPDATE, kbFolder, "putKnowledgeBaseFolder")
				).build();
				creator = CreatorUtil.toCreator(
					_portal, Optional.of(contextUriInfo),
					_userLocalService.fetchUser(kbFolder.getUserId()));
				customFields = CustomFieldsUtil.toCustomFields(
					contextAcceptLanguage.isAcceptAllLanguages(),
					KBFolder.class.getName(), kbFolder.getKbFolderId(),
					kbFolder.getCompanyId(),
					contextAcceptLanguage.getPreferredLocale());
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
				siteId = kbFolder.getGroupId();
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