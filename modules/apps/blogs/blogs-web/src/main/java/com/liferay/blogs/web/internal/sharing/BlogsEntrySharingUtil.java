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

package com.liferay.blogs.web.internal.sharing;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.sharing.configuration.SharingConfiguration;
import com.liferay.sharing.configuration.SharingConfigurationFactory;
import com.liferay.sharing.display.context.util.SharingDropdownItemFactory;
import com.liferay.sharing.display.context.util.SharingMenuItemFactory;
import com.liferay.sharing.security.permission.SharingPermission;
import com.liferay.sharing.service.SharingEntryLocalService;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true, service = {})
public class BlogsEntrySharingUtil {

	public static boolean containsManageCollaboratorsPermission(
		PermissionChecker permissionChecker, BlogsEntry blogsEntry) {

		try {
			long classNameId = PortalUtil.getClassNameId(BlogsEntry.class);

			int count = _sharingEntryLocalService.getSharingEntriesCount(
				classNameId, blogsEntry.getEntryId());

			if (count == 0) {
				return false;
			}

			return _sharingPermission.containsManageCollaboratorsPermission(
				permissionChecker, classNameId, blogsEntry.getEntryId(),
				blogsEntry.getGroupId());
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	public static boolean containsSharePermission(
		PermissionChecker permissionChecker, BlogsEntry blogsEntry) {

		try {
			return _sharingPermission.containsSharePermission(
				permissionChecker, PortalUtil.getClassNameId(BlogsEntry.class),
				blogsEntry.getEntryId(), blogsEntry.getGroupId());
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	public static DropdownItem createManageCollaboratorsDropdownItem(
		BlogsEntry blogsEntry, HttpServletRequest httpServletRequest) {

		try {
			return _sharingDropdownItemFactory.
				createManageCollaboratorsDropdownItem(
					BlogsEntry.class.getName(), blogsEntry.getEntryId(),
					httpServletRequest);
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	public static MenuItem createManageCollaboratorsMenuItem(
		BlogsEntry blogsEntry, HttpServletRequest httpServletRequest) {

		try {
			return _sharingMenuItemFactory.createManageCollaboratorsMenuItem(
				BlogsEntry.class.getName(), blogsEntry.getEntryId(),
				httpServletRequest);
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	public static DropdownItem createShareDropdownItem(
		BlogsEntry blogsEntry, HttpServletRequest httpServletRequest) {

		try {
			return _sharingDropdownItemFactory.createShareDropdownItem(
				BlogsEntry.class.getName(), blogsEntry.getEntryId(),
				httpServletRequest);
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	public static MenuItem createShareMenuItem(
		BlogsEntry blogsEntry, HttpServletRequest httpServletRequest) {

		try {
			return _sharingMenuItemFactory.createShareMenuItem(
				BlogsEntry.class.getName(), blogsEntry.getEntryId(),
				httpServletRequest);
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	public static boolean isSharingEnabled(long groupId)
		throws PortalException {

		SharingConfiguration groupSharingConfiguration =
			_sharingConfigurationFactory.getGroupSharingConfiguration(
				_groupLocalService.getGroup(groupId));

		return groupSharingConfiguration.isEnabled();
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setSharingConfigurationFactory(
		SharingConfigurationFactory sharingConfigurationFactory) {

		_sharingConfigurationFactory = sharingConfigurationFactory;
	}

	@Reference(unbind = "-")
	protected void setSharingDropdownItemFactory(
		SharingDropdownItemFactory sharingDropdownItemFactory) {

		_sharingDropdownItemFactory = sharingDropdownItemFactory;
	}

	@Reference(unbind = "-")
	protected void setSharingEntryLocalService(
		SharingEntryLocalService sharingEntryLocalService) {

		_sharingEntryLocalService = sharingEntryLocalService;
	}

	@Reference(unbind = "-")
	protected void setSharingMenuItemFactory(
		SharingMenuItemFactory sharingMenuItemFactory) {

		_sharingMenuItemFactory = sharingMenuItemFactory;
	}

	@Reference(unbind = "-")
	protected void setSharingPermission(SharingPermission sharingPermission) {
		_sharingPermission = sharingPermission;
	}

	private static GroupLocalService _groupLocalService;
	private static SharingConfigurationFactory _sharingConfigurationFactory;
	private static SharingDropdownItemFactory _sharingDropdownItemFactory;
	private static SharingEntryLocalService _sharingEntryLocalService;
	private static SharingMenuItemFactory _sharingMenuItemFactory;
	private static SharingPermission _sharingPermission;

}