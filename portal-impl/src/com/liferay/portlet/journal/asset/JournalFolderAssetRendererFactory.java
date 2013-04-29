/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.asset;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.BaseAssetRendererFactory;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.portlet.journal.service.permission.JournalFolderPermission;

/**
 * @author Alexander Chow
 */
public class JournalFolderAssetRendererFactory
	extends BaseAssetRendererFactory {

	public static final String TYPE = "folder";

	public AssetRenderer getAssetRenderer(long classPK, int type)
		throws PortalException, SystemException {

		JournalFolder folder = JournalFolderLocalServiceUtil.getFolder(classPK);

		JournalFolderAssetRenderer journalFolderAssetRenderer =
			new JournalFolderAssetRenderer(folder);

		journalFolderAssetRenderer.setAssetRendererType(type);

		return journalFolderAssetRenderer;
	}

	public String getClassName() {
		return JournalFolder.class.getName();
	}

	public String getType() {
		return TYPE;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws Exception {

		JournalFolder folder = JournalFolderLocalServiceUtil.getFolder(classPK);

		return JournalFolderPermission.contains(
			permissionChecker, folder, actionId);
	}

	@Override
	public boolean isCategorizable() {
		return _CATEGORIZABLE;
	}

	@Override
	public boolean isLinkable() {
		return _LINKABLE;
	}

	@Override
	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/folder.png";
	}

	private static final boolean _CATEGORIZABLE = false;

	private static final boolean _LINKABLE = false;

}