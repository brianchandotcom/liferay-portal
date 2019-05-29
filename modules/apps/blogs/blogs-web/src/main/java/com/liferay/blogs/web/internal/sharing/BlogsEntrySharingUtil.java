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
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.sharing.display.context.util.SharingDropdownItemFactory;
import com.liferay.sharing.display.context.util.SharingMenuItemFactory;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true, service = {})
public class BlogsEntrySharingUtil {

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

	@Reference(unbind = "-")
	protected void setSharingDropdownItemFactory(
		SharingDropdownItemFactory sharingDropdownItemFactory) {

		_sharingDropdownItemFactory = sharingDropdownItemFactory;
	}

	@Reference(unbind = "-")
	protected void setSharingMenuItemFactory(
		SharingMenuItemFactory sharingMenuItemFactory) {

		_sharingMenuItemFactory = sharingMenuItemFactory;
	}

	private static SharingDropdownItemFactory _sharingDropdownItemFactory;
	private static SharingMenuItemFactory _sharingMenuItemFactory;

}