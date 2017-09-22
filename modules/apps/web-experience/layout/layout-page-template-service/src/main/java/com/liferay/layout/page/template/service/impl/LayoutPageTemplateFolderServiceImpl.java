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

package com.liferay.layout.page.template.service.impl;

import com.liferay.layout.page.template.constants.LayoutPageTemplateActionKeys;
import com.liferay.layout.page.template.model.LayoutPageTemplateFolder;
import com.liferay.layout.page.template.service.base.LayoutPageTemplateFolderServiceBaseImpl;
import com.liferay.layout.page.template.service.permission.LayoutPageTemplateFolderPermission;
import com.liferay.layout.page.template.service.permission.LayoutPageTemplateResourcePermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jürgen Kappler
 */
public class LayoutPageTemplateFolderServiceImpl
	extends LayoutPageTemplateFolderServiceBaseImpl {

	@Override
	public LayoutPageTemplateFolder addLayoutPageTemplateFolder(
			long groupId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		LayoutPageTemplateResourcePermission.check(
			getPermissionChecker(), groupId,
			LayoutPageTemplateActionKeys.ADD_LAYOUT_PAGE_TEMPLATE_FOLDER);

		return layoutPageTemplateFolderLocalService.addLayoutPageTemplateFolder(
			groupId, getUserId(), name, description, serviceContext);
	}

	@Override
	public LayoutPageTemplateFolder deleteLayoutPageTemplateFolder(
			long layoutPageTemplateFolderId)
		throws PortalException {

		LayoutPageTemplateFolderPermission.check(
			getPermissionChecker(), layoutPageTemplateFolderId,
			ActionKeys.DELETE);

		return layoutPageTemplateFolderLocalService.
			deleteLayoutPageTemplateFolder(layoutPageTemplateFolderId);
	}

	@Override
	public List<LayoutPageTemplateFolder> deleteLayoutPageTemplateFolders(
			long[] layoutPageTemplateFolderIds)
		throws PortalException {

		List<LayoutPageTemplateFolder> undeletableLayoutPageTemplateFolders =
			new ArrayList<>();

		for (long layoutPageTemplateFolderId : layoutPageTemplateFolderIds) {
			try {
				LayoutPageTemplateFolderPermission.check(
					getPermissionChecker(), layoutPageTemplateFolderId,
					ActionKeys.DELETE);

				layoutPageTemplateFolderLocalService.
					deleteLayoutPageTemplateFolder(layoutPageTemplateFolderId);
			}
			catch (PortalException pe) {
				if (_log.isDebugEnabled()) {
					_log.debug(pe, pe);
				}

				LayoutPageTemplateFolder layoutPageTemplateFolder =
					layoutPageTemplateFolderPersistence.fetchByPrimaryKey(
						layoutPageTemplateFolderId);

				undeletableLayoutPageTemplateFolders.add(
					layoutPageTemplateFolder);
			}
		}

		return undeletableLayoutPageTemplateFolders;
	}

	@Override
	public LayoutPageTemplateFolder fetchLayoutPageTemplateFolder(
			long layoutPageTemplateFolderId)
		throws PortalException {

		LayoutPageTemplateFolder layoutPageTemplateFolder =
			layoutPageTemplateFolderLocalService.fetchLayoutPageTemplateFolder(
				layoutPageTemplateFolderId);

		if (layoutPageTemplateFolder != null) {
			LayoutPageTemplateFolderPermission.check(
				getPermissionChecker(), layoutPageTemplateFolder,
				ActionKeys.VIEW);
		}

		return layoutPageTemplateFolder;
	}

	@Override
	public List<LayoutPageTemplateFolder> getLayoutPageTemplateFolders(
			long groupId, int start, int end)
		throws PortalException {

		return layoutPageTemplateFolderPersistence.filterFindByGroupId(
			groupId, start, end);
	}

	@Override
	public List<LayoutPageTemplateFolder> getLayoutPageTemplateFolders(
			long groupId, int start, int end,
			OrderByComparator<LayoutPageTemplateFolder> orderByComparator)
		throws PortalException {

		return layoutPageTemplateFolderPersistence.filterFindByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public List<LayoutPageTemplateFolder> getLayoutPageTemplateFolders(
			long groupId, String name, int start, int end,
			OrderByComparator<LayoutPageTemplateFolder> orderByComparator)
		throws PortalException {

		return layoutPageTemplateFolderPersistence.filterFindByG_LikeN(
			groupId, name, start, end, orderByComparator);
	}

	@Override
	public int getLayoutPageTemplateFoldersCount(long groupId) {
		return layoutPageTemplateFolderPersistence.filterCountByGroupId(
			groupId);
	}

	@Override
	public int getLayoutPageTemplateFoldersCount(long groupId, String name) {
		return layoutPageTemplateFolderPersistence.filterCountByG_LikeN(
			groupId, name);
	}

	@Override
	public LayoutPageTemplateFolder updateLayoutPageTemplateFolder(
			long layoutPageTemplateFolderId, String name, String description)
		throws PortalException {

		LayoutPageTemplateFolderPermission.check(
			getPermissionChecker(), layoutPageTemplateFolderId,
			ActionKeys.UPDATE);

		return layoutPageTemplateFolderLocalService.
			updateLayoutPageTemplateFolder(
				layoutPageTemplateFolderId, name, description);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutPageTemplateFolderServiceImpl.class);

}