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

package com.liferay.document.library.web.internal.servlet;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jaime León
 */
@Component(
	property = {
		"osgi.http.whiteboard.servlet.name=com.liferay.document.library.web.internal.servlet.CopyDLObjectServlet",
		"osgi.http.whiteboard.servlet.pattern=/copy_dl_object",
		"servlet.init.httpMethods=POST"
	},
	service = Servlet.class
)
public class CopyDLObjectServlet extends HttpServlet {

	@Override
	protected void doPost(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws ServletException {

		try {
			String entryType = ParamUtil.getString(
				httpServletRequest, "entryType");

			if (entryType.equals(DLFolder.class.getSimpleName())) {
				_copyFolder(httpServletRequest, httpServletResponse);
			}
			else if (entryType.equals(DLFileEntry.class.getSimpleName())) {
				long fileShortcutId = ParamUtil.getLong(
					httpServletRequest, "fileShortcutId");

				if (fileShortcutId > 0) {
					_copyFileShortcut(httpServletRequest, httpServletResponse);
				}
				else {
					_copyFileEntry(httpServletRequest, httpServletResponse);
				}
			}
		}
		catch (IOException ioException) {
			_log.error(ioException);

			throw new ServletException(ioException);
		}
	}

	@Override
	protected void service(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		try {
			User user = _portal.getUser(httpServletRequest);

			if ((user == null) || user.isGuestUser()) {
				throw new PrincipalException.MustBeAuthenticated(
					StringPool.BLANK);
			}

			PrincipalThreadLocal.setName(user.getUserId());

			PermissionThreadLocal.setPermissionChecker(
				_permissionCheckerFactory.create(user));

			super.service(httpServletRequest, httpServletResponse);
		}
		catch (IOException | PortalException | ServletException exception) {
			_log.error(exception);

			_sendResponse(
				httpServletResponse,
				HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null, null);
		}
	}

	private void _checkDestinationRepository(long repositoryId)
		throws PortalException {

		Group group = _groupLocalService.fetchGroup(repositoryId);

		if ((group != null) && group.isStaged() && !group.isStagingGroup()) {
			throw new PortalException(
				"cannot-copy-folders-to-the-live-version-of-a-group");
		}
	}

	private void _copyFileEntry(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		long fileEntryId = ParamUtil.getLong(httpServletRequest, "fileEntryId");
		long destinationFolderId = ParamUtil.getLong(
			httpServletRequest, "destinationFolderId");
		long destinationRepositoryId = ParamUtil.getLong(
			httpServletRequest, "destinationRepositoryId");

		try {
			_checkDestinationRepository(destinationRepositoryId);

			_dlAppService.copyFileEntry(
				fileEntryId, destinationFolderId, destinationRepositoryId,
				ServiceContextFactory.getInstance(
					DLFileEntry.class.getName(), httpServletRequest));

			_sendResponse(
				httpServletResponse, HttpServletResponse.SC_OK,
				_language.get(
					httpServletRequest, "your-request-completed-successfully"),
				null);
		}
		catch (PortalException portalException) {
			_sendResponse(
				httpServletResponse, HttpServletResponse.SC_METHOD_NOT_ALLOWED,
				null, portalException.getMessage());
		}
	}

	private void _copyFileShortcut(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		long fileShortcutId = ParamUtil.getLong(
			httpServletRequest, "fileShortcutId");
		long destinationFolderId = ParamUtil.getLong(
			httpServletRequest, "destinationFolderId");
		long destinationRepositoryId = ParamUtil.getLong(
			httpServletRequest, "destinationRepositoryId");

		try {
			_checkDestinationRepository(destinationRepositoryId);

			_dlAppService.copyFileShortcut(
				fileShortcutId, destinationFolderId, destinationRepositoryId,
				ServiceContextFactory.getInstance(
					DLFileShortcut.class.getName(), httpServletRequest));

			_sendResponse(
				httpServletResponse, HttpServletResponse.SC_OK,
				_language.get(
					httpServletRequest, "your-request-completed-successfully"),
				null);
		}
		catch (PortalException portalException) {
			_sendResponse(
				httpServletResponse, HttpServletResponse.SC_METHOD_NOT_ALLOWED,
				null, portalException.getMessage());
		}
	}

	private void _copyFolder(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		long sourceRepositoryId = ParamUtil.getLong(
			httpServletRequest, "sourceRepositoryId");
		long sourceFolderId = ParamUtil.getLong(
			httpServletRequest, "sourceFolderId");
		long destinationRepositoryId = ParamUtil.getLong(
			httpServletRequest, "destinationRepositoryId");
		long destinationParentFolderId = ParamUtil.getLong(
			httpServletRequest, "destinationParentFolderId");

		try {
			_checkDestinationRepository(destinationRepositoryId);

			_dlAppService.copyFolder(
				sourceRepositoryId, sourceFolderId, destinationRepositoryId,
				destinationParentFolderId,
				ServiceContextFactory.getInstance(
					DLFolder.class.getName(), httpServletRequest));

			_sendResponse(
				httpServletResponse, HttpServletResponse.SC_OK,
				_language.get(
					httpServletRequest, "your-request-completed-successfully"),
				null);
		}
		catch (PortalException portalException) {
			_sendResponse(
				httpServletResponse, HttpServletResponse.SC_METHOD_NOT_ALLOWED,
				null, portalException.getMessage());
		}
	}

	private void _sendResponse(
			HttpServletResponse httpServletResponse, int status,
			String successMessage, String errorMessage)
		throws IOException {

		httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);
		httpServletResponse.setStatus(status);

		boolean success = false;

		if (status == HttpServletResponse.SC_OK) {
			success = true;
		}

		ServletResponseUtil.write(
			httpServletResponse,
			JSONUtil.put(
				"errorMessage", errorMessage
			).put(
				"success", success
			).put(
				"successMessage", successMessage
			).toString());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CopyDLObjectServlet.class);

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Reference
	private Portal _portal;

}