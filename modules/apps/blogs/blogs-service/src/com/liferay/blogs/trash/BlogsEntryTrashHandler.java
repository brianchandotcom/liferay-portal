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

package com.liferay.blogs.trash;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.trash.BaseTrashHandler;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.blogs.service.permission.BlogsEntryPermission;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;

/**
 * Implements trash handling for the blogs entry entity.
 *
 * @author Zsolt Berentey
 */
@Component(
	property = {"model.class.name=com.liferay.portlet.blogs.model.BlogsEntry"},
	service = TrashHandler.class
)
public class BlogsEntryTrashHandler extends BaseTrashHandler {

	@Override
	public void deleteTrashEntry(long classPK) throws PortalException {
		BlogsEntryLocalServiceUtil.deleteEntry(classPK);
	}

	@Override
	public String getClassName() {
		return BlogsEntry.class.getName();
	}

	@Override
	public String getRestoreContainedModelLink(
			PortletRequest portletRequest, long classPK)
		throws PortalException {

		BlogsEntry entry = BlogsEntryLocalServiceUtil.getEntry(classPK);

		PortletURL portletURL = getRestoreURL(portletRequest, classPK, false);

		portletURL.setParameter("entryId", String.valueOf(entry.getEntryId()));
		portletURL.setParameter("urlTitle", entry.getUrlTitle());

		return portletURL.toString();
	}

	@Override
	public String getRestoreContainerModelLink(
			PortletRequest portletRequest, long classPK)
		throws PortalException {

		PortletURL portletURL = getRestoreURL(portletRequest, classPK, true);

		return portletURL.toString();
	}

	@Override
	public String getRestoreMessage(
		PortletRequest portletRequest, long classPK) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return themeDisplay.translate("blogs");
	}

	@Override
	public boolean isInTrash(long classPK) throws PortalException {
		BlogsEntry entry = BlogsEntryLocalServiceUtil.getEntry(classPK);

		return entry.isInTrash();
	}

	@Override
	public void restoreTrashEntry(long userId, long classPK)
		throws PortalException {

		BlogsEntryLocalServiceUtil.restoreEntryFromTrash(userId, classPK);
	}

	protected PortletURL getRestoreURL(
			PortletRequest portletRequest, long classPK,
			boolean isContainerModel)
		throws PortalException {

		String portletId = PortletKeys.BLOGS;

		BlogsEntry entry = BlogsEntryLocalServiceUtil.getEntry(classPK);

		long plid = PortalUtil.getPlidFromPortletId(
			entry.getGroupId(), PortletKeys.BLOGS);

		if (plid == LayoutConstants.DEFAULT_PLID) {
			portletId = PortletKeys.BLOGS_ADMIN;

			plid = PortalUtil.getControlPanelPlid(portletRequest);
		}

		PortletURL portletURL = PortletURLFactoryUtil.create(
			portletRequest, portletId, plid, PortletRequest.RENDER_PHASE);

		if (!isContainerModel) {
			portletURL.setParameter(
				"mvcRenderCommandName", "/blogs/view_entry");
		}

		return portletURL;
	}

	@Override
	protected boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException {

		return BlogsEntryPermission.contains(
			permissionChecker, classPK, actionId);
	}

}