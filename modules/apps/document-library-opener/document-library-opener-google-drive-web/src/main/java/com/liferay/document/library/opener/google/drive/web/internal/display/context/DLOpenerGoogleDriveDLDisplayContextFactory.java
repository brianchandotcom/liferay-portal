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

package com.liferay.document.library.opener.google.drive.web.internal.display.context;

import com.liferay.document.library.display.context.BaseDLDisplayContextFactory;
import com.liferay.document.library.display.context.DLDisplayContextFactory;
import com.liferay.document.library.display.context.DLViewFileVersionDisplayContext;
import com.liferay.document.library.opener.google.drive.web.internal.DLOpenerGoogleDriveManager;
import com.liferay.document.library.opener.service.DLOpenerFileEntryReferenceLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = DLDisplayContextFactory.class)
public class DLOpenerGoogleDriveDLDisplayContextFactory
	extends BaseDLDisplayContextFactory {

	@Override
	public DLViewFileVersionDisplayContext getDLViewFileVersionDisplayContext(
		DLViewFileVersionDisplayContext parentDLViewFileVersionDisplayContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, FileShortcut fileShortcut) {

		try {
			return getDLViewFileVersionDisplayContext(
				parentDLViewFileVersionDisplayContext, httpServletRequest,
				httpServletResponse, fileShortcut.getFileVersion());
		}
		catch (PortalException portalException) {
			throw new SystemException(
				StringBundler.concat(
					"Unable to build ",
					DLOpenerGoogleDriveDLViewFileVersionDisplayContext.class.
						getSimpleName(),
					" for shortcut ", fileShortcut.getPrimaryKey()),
				portalException);
		}
	}

	@Override
	public DLViewFileVersionDisplayContext getDLViewFileVersionDisplayContext(
		DLViewFileVersionDisplayContext parentDLViewFileVersionDisplayContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, FileVersion fileVersion) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return new DLOpenerGoogleDriveDLViewFileVersionDisplayContext(
			parentDLViewFileVersionDisplayContext, httpServletRequest,
			httpServletResponse, fileVersion,
			ResourceBundleUtil.getBundle(
				themeDisplay.getLocale(),
				DLOpenerGoogleDriveDLDisplayContextFactory.class),
			_fileEntryModelResourcePermission,
			_dlOpenerFileEntryReferenceLocalService,
			_dlOpenerGoogleDriveManager, _portal);
	}

	@Reference
	private DLOpenerFileEntryReferenceLocalService
		_dlOpenerFileEntryReferenceLocalService;

	@Reference
	private DLOpenerGoogleDriveManager _dlOpenerGoogleDriveManager;

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.repository.model.FileEntry)"
	)
	private ModelResourcePermission<FileEntry>
		_fileEntryModelResourcePermission;

	@Reference
	private Portal _portal;

}