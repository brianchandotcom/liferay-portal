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

package com.liferay.portlet.documentlibrary.action;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.documentlibrary.util.DocumentConversionUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Charles May
 * @author Bruno Farache
 */
public class GetFileAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);
		HttpServletResponse response = PortalUtil.getHttpServletResponse(
			actionResponse);

		try {
			long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");

			long folderId = ParamUtil.getLong(actionRequest, "folderId");
			String name = ParamUtil.getString(actionRequest, "name");
			String title = ParamUtil.getString(actionRequest, "title");
			String version = ParamUtil.getString(actionRequest, "version");

			long fileShortcutId = ParamUtil.getLong(
				actionRequest, "fileShortcutId");

			String uuid = ParamUtil.getString(actionRequest, "uuid");

			String targetExtension = ParamUtil.getString(
				actionRequest, "targetExtension");

			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			long groupId = ParamUtil.getLong(
				actionRequest, "groupId", themeDisplay.getScopeGroupId());

			getFile(
				fileEntryId, folderId, name, title, version, fileShortcutId,
				uuid, groupId, targetExtension, request, response);

			setForward(actionRequest, ActionConstants.COMMON_NULL);
		}
		catch (NoSuchFileEntryException nsfee) {
			PortalUtil.sendError(
				HttpServletResponse.SC_NOT_FOUND, nsfee, actionRequest,
				actionResponse);
		}
		catch (PrincipalException pe) {
			processPrincipalException(pe, request, response);
		}
		catch (Exception e) {
			PortalUtil.sendError(e, actionRequest, actionResponse);
		}
	}

	@Override
	public ActionForward strutsExecute(
			ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		try {
			long fileEntryId = ParamUtil.getLong(request, "fileEntryId");

			long folderId = ParamUtil.getLong(request, "folderId");
			String name = ParamUtil.getString(request, "name");
			String title = ParamUtil.getString(request, "title");
			String version = ParamUtil.getString(request, "version");

			long fileShortcutId = ParamUtil.getLong(request, "fileShortcutId");

			String uuid = ParamUtil.getString(request, "uuid");

			String targetExtension = ParamUtil.getString(
				request, "targetExtension");

			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			long groupId = ParamUtil.getLong(
				request, "groupId", themeDisplay.getScopeGroupId());

			getFile(
				fileEntryId, folderId, name, title, version, fileShortcutId,
				uuid, groupId, targetExtension, request, response);

			return null;
		}
		catch (PrincipalException pe) {
			processPrincipalException(pe, request, response);

			return null;
		}
		catch (Exception e) {
			PortalUtil.sendError(e, request, response);

			return null;
		}
	}

	protected void getFile(
			long fileEntryId, long folderId, String name, String title,
			String version, long fileShortcutId, String uuid, long groupId,
			String targetExtension, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

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
						(ThemeDisplay)request.getAttribute(
							WebKeys.THEME_DISPLAY);

					PermissionChecker permissionChecker =
						themeDisplay.getPermissionChecker();

					DLFileEntryPermission.check(
						permissionChecker, dlFileEntry, ActionKeys.VIEW);

					fileEntry = new LiferayFileEntry(dlFileEntry);
				}
			}
		}
		else {
			DLFileShortcut fileShortcut = DLAppServiceUtil.getFileShortcut(
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
				throw new NoSuchFileEntryException();
			}
		}

		FileVersion fileVersion = fileEntry.getFileVersion(version);

		InputStream is = fileVersion.getContentStream(true);
		String fileName = fileVersion.getTitle();
		long contentLength = fileVersion.getSize();
		String contentType = fileVersion.getMimeType();

		if (Validator.isNotNull(targetExtension)) {
			String id = DLUtil.getTempFileId(
				fileEntry.getFileEntryId(), version);

			String sourceExtension = fileVersion.getExtension();

			if (!fileName.endsWith(StringPool.PERIOD + sourceExtension)) {
				fileName += StringPool.PERIOD + sourceExtension;
			}

			File convertedFile = DocumentConversionUtil.convert(
				id, is, sourceExtension, targetExtension);

			if (convertedFile != null) {
				fileName = FileUtil.stripExtension(fileName).concat(
					StringPool.PERIOD).concat(targetExtension);
				is = new FileInputStream(convertedFile);
				contentLength = convertedFile.length();
				contentType = MimeTypesUtil.getContentType(fileName);
			}
		}

		ServletResponseUtil.sendFile(
			request, response, fileName, is, contentLength, contentType);
	}

	@Override
	protected boolean isCheckMethodOnProcessAction() {
		return _CHECK_METHOD_ON_PROCESS_ACTION;
	}

	protected void processPrincipalException(
			Throwable t, HttpServletRequest request,
			HttpServletResponse response)
		throws IOException, ServletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		User user = permissionChecker.getUser();

		if ((user != null) && !user.isDefaultUser()) {
			PortalUtil.sendError(
				HttpServletResponse.SC_UNAUTHORIZED, (Exception)t, request,
				response);

			return;
		}

		String redirect =
			request.getContextPath() + Portal.PATH_MAIN + "/portal/login";

		String currentURL = PortalUtil.getCurrentURL(request);

		redirect = HttpUtil.addParameter(redirect, "redirect", currentURL);

		response.sendRedirect(redirect);
	}

	private static final boolean _CHECK_METHOD_ON_PROCESS_ACTION = false;

}