/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.blogs.trash;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.trash.BaseTrashHandler;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.blogs.service.BlogsEntryServiceUtil;
import com.liferay.portlet.blogs.service.permission.BlogsEntryPermission;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * Implements trash handling for the blogs entry entity.
 *
 * @author Zsolt Berentey
 */
public class BlogsEntryTrashHandler extends BaseTrashHandler {

	public static final String CLASS_NAME = BlogsEntry.class.getName();

	public void deleteTrashEntries(long[] classPKs, boolean checkPermission)
		throws PortalException, SystemException {

		for (long classPK : classPKs) {
			if (checkPermission) {
				BlogsEntryServiceUtil.deleteEntry(classPK);
			}
			else {
				BlogsEntryLocalServiceUtil.deleteEntry(classPK);
			}
		}
	}

	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public String getRestoreLink(PortletRequest portletRequest, long classPK)
		throws PortalException, SystemException {

		BlogsEntry blogsEntry = BlogsEntryLocalServiceUtil.getBlogsEntry(
			classPK);

		String portletName = PortletKeys.BLOGS;

		long plid = PortalUtil.getPlidFromPortletId(
			blogsEntry.getGroupId(), PortletKeys.BLOGS);

		if (plid == LayoutConstants.DEFAULT_PLID) {
			plid = PortalUtil.getControlPanelPlid(portletRequest);

			portletName = PortletKeys.BLOGS_ADMIN;
		}

		PortletURL portletURL = PortletURLFactoryUtil.create(
			portletRequest, portletName, plid, PortletRequest.RENDER_PHASE);

		return portletURL.toString();
	}

	@Override
	public String getRestoreMessage(PortletRequest portletRequest, long classPK)
		throws PortalException, SystemException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return themeDisplay.translate("blogs");
	}

	public boolean isInTrash(long classPK)
		throws PortalException, SystemException {

		BlogsEntry entry = BlogsEntryServiceUtil.getEntry(classPK);

		return entry.isInTrash();
	}

	public void restoreTrashEntries(long[] classPKs)
		throws PortalException, SystemException {

		for (long classPK : classPKs) {
			BlogsEntryServiceUtil.restoreEntryFromTrash(classPK);
		}
	}

	@Override
	protected boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException, SystemException {

		return BlogsEntryPermission.contains(
			permissionChecker, classPK, actionId);
	}

}