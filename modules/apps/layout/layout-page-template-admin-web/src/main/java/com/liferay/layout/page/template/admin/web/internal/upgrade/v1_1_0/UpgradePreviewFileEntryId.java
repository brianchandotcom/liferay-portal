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

package com.liferay.layout.page.template.admin.web.internal.upgrade.v1_1_0;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.store.DLStoreUtil;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.io.InputStream;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Rubén Pulido
 */
public class UpgradePreviewFileEntryId extends UpgradeProcess {

	public UpgradePreviewFileEntryId(
		DLFileEntryLocalService dlFileEntryLocalService,
		LayoutPageTemplateEntryLocalService
			layoutPageTemplateEntryLocalService) {

		_dlFileEntryLocalService = dlFileEntryLocalService;
		_layoutPageTemplateEntryLocalService =
			layoutPageTemplateEntryLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try {
			ActionableDynamicQuery actionableDynamicQuery =
				_layoutPageTemplateEntryLocalService.
					getActionableDynamicQuery();

			actionableDynamicQuery.setAddCriteriaMethod(
				dynamicQuery -> dynamicQuery.add(
					RestrictionsFactoryUtil.gt("previewFileEntryId", 0L)));

			actionableDynamicQuery.setPerformActionMethod(
				(LayoutPageTemplateEntry layoutPageTemplateEntry) ->
					_upgradePreviewFileEntry(layoutPageTemplateEntry));

			actionableDynamicQuery.performActions();
		}
		catch (PortalException pe) {
			throw new UpgradeException(pe);
		}
	}

	private Folder _getOrCreateFolder(
			Repository repository, ServiceContext serviceContext)
		throws PortalException {

		Folder folder = _repositoryFolders.get(repository.getRepositoryId());

		if (folder != null) {
			return folder;
		}

		try {
			folder = PortletFileRepositoryUtil.getPortletFolder(
				repository.getRepositoryId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, _PORTLET_NAME);
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			folder = PortletFileRepositoryUtil.addPortletFolder(
				repository.getUserId(), repository.getRepositoryId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, _PORTLET_NAME,
				serviceContext);
		}

		_repositoryFolders.put(repository.getRepositoryId(), folder);

		return folder;
	}

	private Repository _getOrCreateRepository(
			long groupId, ServiceContext serviceContext)
		throws PortalException {

		Repository repository = _groupRepositories.get(groupId);

		if (repository != null) {
			return repository;
		}

		repository = PortletFileRepositoryUtil.fetchPortletRepository(
			groupId, _PORTLET_NAME);

		if (repository == null) {
			repository = PortletFileRepositoryUtil.addPortletRepository(
				groupId, _PORTLET_NAME, serviceContext);
		}

		_groupRepositories.put(groupId, repository);

		return repository;
	}

	private void _upgradePreviewFileEntry(
			LayoutPageTemplateEntry layoutPageTemplateEntry)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		DLFileEntry originalDLFileEntry =
			_dlFileEntryLocalService.getDLFileEntry(
				layoutPageTemplateEntry.getPreviewFileEntryId());

		Repository repository = _getOrCreateRepository(
			originalDLFileEntry.getGroupId(), serviceContext);

		Folder folder = _getOrCreateFolder(repository, serviceContext);

		serviceContext.setAttribute(
			"className", originalDLFileEntry.getClassName());
		serviceContext.setAttribute(
			"classPK", originalDLFileEntry.getClassPK());

		InputStream inputStream = DLStoreUtil.getFileAsStream(
			originalDLFileEntry.getCompanyId(),
			originalDLFileEntry.getDataRepositoryId(),
			originalDLFileEntry.getName());

		DLFileEntry newDLFileEntry = _dlFileEntryLocalService.addFileEntry(
			originalDLFileEntry.getUserId(), originalDLFileEntry.getGroupId(),
			repository.getRepositoryId(), folder.getFolderId(),
			originalDLFileEntry.getFileName(),
			originalDLFileEntry.getMimeType(), originalDLFileEntry.getTitle(),
			originalDLFileEntry.getDescription(), null,
			originalDLFileEntry.getFileEntryTypeId(), null, null, inputStream,
			originalDLFileEntry.getSize(), serviceContext);

		layoutPageTemplateEntry.setPreviewFileEntryId(
			newDLFileEntry.getFileEntryId());

		_layoutPageTemplateEntryLocalService.updateLayoutPageTemplateEntry(
			layoutPageTemplateEntry);

		_dlFileEntryLocalService.deleteDLFileEntry(originalDLFileEntry);
	}

	private static final String _PORTLET_NAME =
		"com_liferay_layout_page_template_admin_web_portlet_" +
			"LayoutPageTemplatesPortlet";

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradePreviewFileEntryId.class);

	private static final Map<Long, Repository> _groupRepositories =
		new HashMap<>();
	private static final Map<Long, Folder> _repositoryFolders = new HashMap<>();

	private final DLFileEntryLocalService _dlFileEntryLocalService;
	private final LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

}