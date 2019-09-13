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

package com.liferay.document.library.web.internal.portlet.action;

import com.liferay.document.library.kernel.document.conversion.DocumentConversionUtil;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.document.library.web.internal.security.permission.resource.DLFileEntryPermission;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.flash.FlashMagicBytesUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Iván Zaera
 */
public class GetFileActionHelper {

	public void processRequest(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		try {
			long fileEntryId = ParamUtil.getLong(
				httpServletRequest, "fileEntryId");

			long folderId = ParamUtil.getLong(httpServletRequest, "folderId");
			String name = ParamUtil.getString(httpServletRequest, "name");
			String title = ParamUtil.getString(httpServletRequest, "title");
			String version = ParamUtil.getString(httpServletRequest, "version");

			long fileShortcutId = ParamUtil.getLong(
				httpServletRequest, "fileShortcutId");

			String uuid = ParamUtil.getString(httpServletRequest, "uuid");

			String targetExtension = ParamUtil.getString(
				httpServletRequest, "targetExtension");

			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			long groupId = ParamUtil.getLong(
				httpServletRequest, "groupId", themeDisplay.getScopeGroupId());

			_getFile(
				fileEntryId, folderId, name, title, version, fileShortcutId,
				uuid, groupId, targetExtension, httpServletRequest,
				httpServletResponse);
		}
		catch (NoSuchFileEntryException nsfee) {
			PortalUtil.sendError(
				HttpServletResponse.SC_NOT_FOUND, nsfee, httpServletRequest,
				httpServletResponse);
		}
		catch (PrincipalException pe) {
			_processPrincipalException(
				pe, httpServletRequest, httpServletResponse);
		}
		catch (Exception e) {
			PortalUtil.sendError(e, httpServletRequest, httpServletResponse);
		}
	}

	private void _getFile(
			long fileEntryId, long folderId, String name, String title,
			String version, long fileShortcutId, String uuid, long groupId,
			String targetExtension, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, PortalException {

		if (name.startsWith("DLFE-")) {
			name = name.substring(5);
		}

		name = FileUtil.stripExtension(name);

		FileEntry fileEntry = null;

		if (Validator.isNotNull(uuid) && (groupId > 0)) {
			fileEntry = DLAppServiceUtil.getFileEntryByUuidAndGroupId(
				uuid, groupId);

			folderId = fileEntry.getFolderId();
		}

		if (fileEntryId > 0) {
			fileEntry = DLAppServiceUtil.getFileEntry(fileEntryId);
		}
		else if (fileShortcutId <= 0) {
			if (Validator.isNotNull(title)) {
				fileEntry = DLAppServiceUtil.getFileEntry(
					groupId, folderId, title);
			}
			else if (Validator.isNotNull(name)) {
				DLFileEntry dlFileEntry =
					DLFileEntryLocalServiceUtil.fetchFileEntryByName(
						groupId, folderId, name);

				if (dlFileEntry == null) {

					// LPS-30374

					List<DLFileEntry> dlFileEntries =
						DLFileEntryLocalServiceUtil.getFileEntries(
							folderId, name);

					if (!dlFileEntries.isEmpty()) {
						dlFileEntry = dlFileEntries.get(0);
					}
				}

				if (dlFileEntry != null) {
					ThemeDisplay themeDisplay =
						(ThemeDisplay)httpServletRequest.getAttribute(
							WebKeys.THEME_DISPLAY);

					DLFileEntryPermission.check(
						themeDisplay.getPermissionChecker(), dlFileEntry,
						ActionKeys.VIEW);

					fileEntry = new LiferayFileEntry(dlFileEntry);
				}
			}
		}
		else {
			FileShortcut fileShortcut = DLAppServiceUtil.getFileShortcut(
				fileShortcutId);

			fileEntryId = fileShortcut.getToFileEntryId();

			fileEntry = DLAppServiceUtil.getFileEntry(fileEntryId);
		}

		if (Validator.isNull(version)) {
			if ((fileEntry != null) &&
				Validator.isNotNull(fileEntry.getVersion())) {

				version = fileEntry.getVersion();
			}
			else {
				throw new NoSuchFileEntryException(
					"{fileEntryId=" + fileEntryId + "}");
			}
		}

		FileVersion fileVersion = fileEntry.getFileVersion(version);

		InputStream is = fileVersion.getContentStream(true);

		String fileName = fileVersion.getTitle();

		String sourceExtension = fileVersion.getExtension();

		if (Validator.isNotNull(sourceExtension) &&
			!fileName.endsWith(StringPool.PERIOD + sourceExtension)) {

			fileName += StringPool.PERIOD + sourceExtension;
		}

		long contentLength = fileVersion.getSize();
		String contentType = fileVersion.getMimeType();

		if (Validator.isNotNull(targetExtension)) {
			String id = DLUtil.getTempFileId(
				fileEntry.getFileEntryId(), version);

			File convertedFile = DocumentConversionUtil.convert(
				id, is, sourceExtension, targetExtension);

			if (convertedFile != null) {
				fileName = FileUtil.stripExtension(
					fileName
				).concat(
					StringPool.PERIOD
				).concat(
					targetExtension
				);
				is = new FileInputStream(convertedFile);
				contentLength = convertedFile.length();
				contentType = MimeTypesUtil.getContentType(fileName);
			}
		}

		FlashMagicBytesUtil.Result flashMagicBytesUtilResult =
			FlashMagicBytesUtil.check(is);

		if (flashMagicBytesUtilResult.isFlash()) {
			fileName = FileUtil.stripExtension(fileName) + ".swf";
		}

		is = flashMagicBytesUtilResult.getInputStream();

		ServletResponseUtil.sendFile(
			httpServletRequest, httpServletResponse, fileName, is,
			contentLength, contentType);
	}

	private void _processPrincipalException(
			Throwable t, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		User user = permissionChecker.getUser();

		if ((user != null) && !user.isDefaultUser()) {
			PortalUtil.sendError(
				HttpServletResponse.SC_UNAUTHORIZED, (Exception)t,
				httpServletRequest, httpServletResponse);

			return;
		}

		String redirect = PortalUtil.getPathMain() + "/portal/login";

		redirect = HttpUtil.addParameter(
			redirect, "redirect", PortalUtil.getCurrentURL(httpServletRequest));

		httpServletResponse.sendRedirect(redirect);
	}

}