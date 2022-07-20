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

package com.liferay.content.dashboard.blogs.internal.item.action.provider;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.content.dashboard.blogs.internal.item.action.SharingCollaboratorsBlogsEntryContentDashboardItemAction;
import com.liferay.content.dashboard.item.action.ContentDashboardItemAction;
import com.liferay.content.dashboard.item.action.provider.ContentDashboardItemActionProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.sharing.configuration.SharingConfiguration;
import com.liferay.sharing.configuration.SharingConfigurationFactory;
import com.liferay.sharing.security.permission.SharingPermission;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(service = ContentDashboardItemActionProvider.class)
public class SharingCollaboratorsBlogsEntryContentDashboardItemActionProvider
	implements ContentDashboardItemActionProvider<BlogsEntry> {

	@Override
	public ContentDashboardItemAction getContentDashboardItemAction(
		BlogsEntry blogsEntry, HttpServletRequest httpServletRequest) {

		if (!isShow(blogsEntry, httpServletRequest)) {
			return null;
		}

		return new SharingCollaboratorsBlogsEntryContentDashboardItemAction(
			blogsEntry, httpServletRequest, _language, _portal);
	}

	@Override
	public String getKey() {
		return "share";
	}

	@Override
	public ContentDashboardItemAction.Type getType() {
		return ContentDashboardItemAction.Type.SHARING_COLLABORATORS;
	}

	@Override
	public boolean isShow(
		BlogsEntry blogsEntry, HttpServletRequest httpServletRequest) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		try {
			if (_isSharingEnabled(themeDisplay.getScopeGroupId()) &&
				_sharingPermission.containsSharePermission(
					themeDisplay.getPermissionChecker(),
					_classNameLocalService.getClassNameId(
						BlogsEntry.class.getName()),
					blogsEntry.getEntryId(), themeDisplay.getScopeGroupId())) {

				return true;
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException);

			return false;
		}

		return false;
	}

	private boolean _isSharingEnabled(long groupId) throws PortalException {
		SharingConfiguration sharingConfiguration =
			_sharingConfigurationFactory.getGroupSharingConfiguration(
				_groupLocalService.getGroup(groupId));

		return sharingConfiguration.isEnabled();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SharingCollaboratorsBlogsEntryContentDashboardItemActionProvider.class);

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Language _language;

	@Reference(target = "(model.class.name=com.liferay.blogs.model.BlogsEntry)")
	private ModelResourcePermission<BlogsEntry> _modelResourcePermission;

	@Reference
	private Portal _portal;

	@Reference
	private SharingConfigurationFactory _sharingConfigurationFactory;

	@Reference
	private SharingPermission _sharingPermission;

}