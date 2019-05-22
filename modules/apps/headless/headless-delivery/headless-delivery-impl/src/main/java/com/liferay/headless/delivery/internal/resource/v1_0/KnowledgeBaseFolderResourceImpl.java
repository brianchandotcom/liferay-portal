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

import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.headless.common.spi.service.context.ServiceContextUtil;
import com.liferay.headless.delivery.dto.v1_0.KnowledgeBaseFolder;
import com.liferay.headless.delivery.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.CustomFieldsUtil;
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

		KBFolder kbFolder = _kbFolderService.addKBFolder(
			parentKBFolder.getGroupId(), _getClassNameId(),
			parentKnowledgeBaseFolderId, knowledgeBaseFolder.getName(),
			knowledgeBaseFolder.getDescription(),
			ServiceContextUtil.createServiceContext(
				parentKBFolder.getGroupId(),
				knowledgeBaseFolder.getViewableByAsString()));

		CustomFieldsUtil.addCustomFields(
			kbFolder.getCompanyId(), KBFolder.class, kbFolder.getKbFolderId(),
			knowledgeBaseFolder.getCustomFields(),
			contextAcceptLanguage.getPreferredLocale());

		return _toKnowledgeBaseFolder(kbFolder);
	}

	@Override
	public KnowledgeBaseFolder postSiteKnowledgeBaseFolder(
			Long siteId, KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		KBFolder kbFolder = _kbFolderService.addKBFolder(
			siteId, _getClassNameId(), 0, knowledgeBaseFolder.getName(),
			knowledgeBaseFolder.getDescription(),
			ServiceContextUtil.createServiceContext(
				siteId, knowledgeBaseFolder.getViewableByAsString()));

		CustomFieldsUtil.addCustomFields(
			kbFolder.getCompanyId(), KBFolder.class, kbFolder.getKbFolderId(),
			knowledgeBaseFolder.getCustomFields(),
			contextAcceptLanguage.getPreferredLocale());

		return _toKnowledgeBaseFolder(kbFolder);
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

		KBFolder kbFolder = _kbFolderService.updateKBFolder(
			_getClassNameId(), parentKnowledgeBaseFolderId,
			knowledgeBaseFolderId, knowledgeBaseFolder.getName(),
			knowledgeBaseFolder.getDescription(), new ServiceContext());

		CustomFieldsUtil.addCustomFields(
			kbFolder.getCompanyId(), KBFolder.class, kbFolder.getKbFolderId(),
			knowledgeBaseFolder.getCustomFields(),
			contextAcceptLanguage.getPreferredLocale());

		return _toKnowledgeBaseFolder(kbFolder);
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
				customFields = CustomFieldsUtil.toCustomFields(
					ExpandoBridgeFactoryUtil.getExpandoBridge(
						kbFolder.getCompanyId(), KBFolder.class.getName(),
						kbFolder.getKbFolderId()),
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