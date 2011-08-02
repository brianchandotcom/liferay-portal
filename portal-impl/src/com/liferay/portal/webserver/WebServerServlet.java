/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.webserver;

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.freemarker.FreeMarkerUtil;
import com.liferay.portal.kernel.freemarker.FreeMarkerContext;
import com.liferay.portal.kernel.freemarker.FreeMarkerEngineUtil;
import com.liferay.portal.kernel.repository.RepositoryException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.servlet.PortalSessionThreadLocal;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.Validator_IW;
import com.liferay.portal.kernel.webdav.WebDAVUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.util.AudioProcessor;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.documentlibrary.util.DocumentConversionUtil;
import com.liferay.portlet.documentlibrary.util.PDFProcessor;
import com.liferay.portlet.documentlibrary.util.VideoProcessor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.text.Format;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Alexander Chow
 * @author Brian Wing Shun Chan
 */
public class WebServerServlet extends HttpServlet {

	/**
	 * @see com.liferay.portal.servlet.filters.virtualhost.VirtualHostFilter
	 */
	public static boolean hasFiles(HttpServletRequest request) {
		try {

			// Do not use permission checking since this may be called from
			// other contexts that are also managing the principal

			User user = _getUser(request);

			String path = HttpUtil.fixPath(request.getPathInfo());

			String[] pathArray = StringUtil.split(path, CharPool.SLASH);

			if (pathArray.length == 0) {
				return true;
			}
			else if (Validator.isNumber(pathArray[0])) {
				_checkFileEntry(pathArray);
			}
			else {
				long groupId = _getGroupId(user.getCompanyId(), pathArray[0]);
				long folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

				for (int i = 1; i < pathArray.length; i++) {
					try {
						Folder folder = DLAppLocalServiceUtil.getFolder(
							groupId, folderId, pathArray[i]);

						folderId = folder.getFolderId();
					}
					catch (NoSuchFolderException nsfe) {
						if (i != pathArray.length - 1) {
							return false;
						}

						pathArray = new String[] {
							String.valueOf(groupId), String.valueOf(folderId),
							pathArray[i]
						};

						_checkFileEntry(pathArray);
					}
				}
			}
		}
		catch (Exception e) {
			return false;
		}

		return true;
	}

	@Override
	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		User user = null;

		try {
			user = _getUser(request);

			PrincipalThreadLocal.setName(user.getUserId());
			PrincipalThreadLocal.setPassword(
				PortalUtil.getUserPassword(request));

			PermissionChecker permissionChecker =
				PermissionCheckerFactoryUtil.create(user, true);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			String path = HttpUtil.fixPath(request.getPathInfo());
			String[] pathArray = StringUtil.split(path, CharPool.SLASH);

			if (pathArray.length == 0) {
				sendGroups(
					response, user,
					request.getServletPath() + StringPool.SLASH + path);
			}
			else {
				if (Validator.isNumber(pathArray[0])) {
					sendFile(request, response, user, pathArray);
				}
				else {
					sendDocumentLibrary(
						request, response, user,
						request.getServletPath() + StringPool.SLASH + path,
						pathArray);
				}
			}
		}
		catch (NoSuchFileEntryException nsfee) {
			PortalUtil.sendError(
				HttpServletResponse.SC_NOT_FOUND, nsfee, request, response);
		}
		catch (PrincipalException pe) {
			processPrincipalException(pe, user, request, response);
		}
		catch (Exception e) {
			PortalUtil.sendError(e, request, response);
		}
	}

	protected FileEntry getFileEntry(String[] pathArray) throws Exception {
		if (pathArray.length == 1) {
			long dlFileShortcutId = GetterUtil.getLong(pathArray[0]);

			DLFileShortcut dlFileShortcut = DLAppServiceUtil.getFileShortcut(
				dlFileShortcutId);

			return DLAppServiceUtil.getFileEntry(
				dlFileShortcut.getToFileEntryId());
		}
		else if (pathArray.length == 2) {
			long groupId = GetterUtil.getLong(pathArray[0]);

			return DLAppServiceUtil.getFileEntryByUuidAndGroupId(
				pathArray[1], groupId);
		}
		else {
			long groupId = GetterUtil.getLong(pathArray[0]);
			long folderId = GetterUtil.getLong(pathArray[1]);
			String fileName = HttpUtil.decodeURL(pathArray[2], true);

			return DLAppServiceUtil.getFileEntry(groupId, folderId, fileName);
		}
	}

	protected void processPrincipalException(
			Throwable t, User user, HttpServletRequest request,
			HttpServletResponse response)
		throws IOException, ServletException {

		if (!user.isDefaultUser()) {
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

	protected void sendDocumentLibrary(
			HttpServletRequest request, HttpServletResponse response, User user,
			String path, String[] pathArray)
		throws Exception {

		long groupId = _getGroupId(user.getCompanyId(), pathArray[0]);
		long folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		for (int i = 1; i < pathArray.length; i++) {
			String name = pathArray[i];

			try {
				Folder folder = DLAppServiceUtil.getFolder(
					groupId, folderId, name);

				folderId = folder.getFolderId();
			}
			catch (NoSuchFolderException nsfe) {
				if (i != pathArray.length - 1) {
					throw nsfe;
				}

				String title = name;

				sendFile(response, user, groupId, folderId, title);

				return;
			}
		}

		try {
			sendFile(response, user, groupId, folderId, "index.html");

			return;
		}
		catch (Exception e) {
			if ((e instanceof NoSuchFileEntryException) ||
				(e instanceof PrincipalException)) {

				try {
					sendFile(response, user, groupId, folderId, "index.htm");

					return;
				}
				catch (NoSuchFileEntryException nsfee) {
				}
				catch (PrincipalException pe) {
				}
			}
			else {
				throw e;
			}
		}

		List<WebServerEntry> webServerEntries = new ArrayList<WebServerEntry>();

		webServerEntries.add(new WebServerEntry(path, "../"));

		List<Folder> folders = DLAppServiceUtil.getFolders(groupId, folderId);

		for (Folder folder : folders) {
			WebServerEntry webServerEntry = new WebServerEntry(
				path, folder.getName() + StringPool.SLASH,
				folder.getCreateDate(), folder.getModifiedDate(),
				folder.getDescription(), 0);

			webServerEntries.add(webServerEntry);
		}

		List<FileEntry> fileEntries = DLAppServiceUtil.getFileEntries(
			groupId, folderId);

		for (FileEntry fileEntry : fileEntries) {
			WebServerEntry webServerEntry = new WebServerEntry(
				path, fileEntry.getTitle(), fileEntry.getCreateDate(),
				fileEntry.getModifiedDate(), fileEntry.getDescription(),
				fileEntry.getSize());

			webServerEntries.add(webServerEntry);
		}

		sendHTML(response, path, webServerEntries);
	}

	protected void sendFile(
			HttpServletRequest request, HttpServletResponse response,
			User user, String[] pathArray)
		throws Exception {

		FileEntry fileEntry = getFileEntry(pathArray);

		if (fileEntry == null) {
			throw new NoSuchFileEntryException();
		}

		String version = ParamUtil.getString(request, "version");

		if (Validator.isNull(version)) {
			if (Validator.isNotNull(fileEntry.getVersion())) {
				version = fileEntry.getVersion();
			}
			else {
				throw new NoSuchFileEntryException();
			}
		}

		String tempFileId = DLUtil.getTempFileId(
			fileEntry.getFileEntryId(), version);

		InputStream inputStream = fileEntry.getContentStream(version);
		long contentLength = 0;

		FileVersion fileVersion = fileEntry.getFileVersion(version);

		String fileName = fileVersion.getTitle();

		String extension = GetterUtil.getString(
			FileUtil.getExtension(fileName));

		if (Validator.isNull(extension) ||
			!extension.equals(fileVersion.getExtension())) {

			fileName += StringPool.PERIOD + fileVersion.getExtension();
		}

		boolean converted = false;

		String targetExtension = ParamUtil.getString(
			request, "targetExtension");
		boolean documentThumbnail = ParamUtil.getBoolean(
			request, "documentThumbnail");
		int previewFileIndex = ParamUtil.getInteger(
			request, "previewFileIndex");
		boolean audioPreview = ParamUtil.getBoolean(request, "audioPreview");
		boolean videoPreview = ParamUtil.getBoolean(request, "videoPreview");
		boolean videoThumbnail = ParamUtil.getBoolean(
			request, "videoThumbnail");

		if (Validator.isNotNull(targetExtension)) {
			File convertedFile = DocumentConversionUtil.convert(
				tempFileId, inputStream, extension, targetExtension);

			if (convertedFile != null) {
				fileName = FileUtil.stripExtension(fileName).concat(
					StringPool.PERIOD).concat(targetExtension);
				inputStream = new FileInputStream(convertedFile);
				contentLength = convertedFile.length();

				converted = true;
			}
		}
		else if (documentThumbnail) {
			fileName = FileUtil.stripExtension(fileName).concat(
				StringPool.PERIOD).concat(PDFProcessor.THUMBNAIL_TYPE);

			File thumbnailFile = PDFProcessor.getThumbnailFile(tempFileId);

			inputStream = new FileInputStream(thumbnailFile);
			contentLength = thumbnailFile.length();

			converted = true;
		}
		else if (previewFileIndex > 0) {
			fileName = FileUtil.stripExtension(fileName).concat(
				StringPool.PERIOD).concat(PDFProcessor.PREVIEW_TYPE);

			File previewFile = PDFProcessor.getPreviewFile(
				tempFileId, previewFileIndex);

			inputStream = new FileInputStream(previewFile);
			contentLength = previewFile.length();

			converted = true;
		}
		else if (audioPreview) {
			fileName = FileUtil.stripExtension(fileName).concat(
				StringPool.PERIOD).concat(AudioProcessor.PREVIEW_TYPE);

			File previewFile = AudioProcessor.getPreviewFile(tempFileId);

			inputStream = new FileInputStream(previewFile);
			contentLength = previewFile.length();

			converted = true;
		}
		else if (videoPreview) {
			fileName = FileUtil.stripExtension(fileName).concat(
				StringPool.PERIOD).concat(VideoProcessor.PREVIEW_TYPE);

			File previewFile = VideoProcessor.getPreviewFile(tempFileId);

			inputStream = new FileInputStream(previewFile);
			contentLength = previewFile.length();

			converted = true;
		}
		else if (videoThumbnail) {
			fileName = FileUtil.stripExtension(fileName).concat(
				StringPool.PERIOD).concat(VideoProcessor.THUMBNAIL_TYPE);

			File thumbnailFile = VideoProcessor.getThumbnailFile(
				tempFileId);

			inputStream = new FileInputStream(thumbnailFile);
			contentLength = thumbnailFile.length();

			converted = true;
		}

		String contentType = fileEntry.getMimeType(version);

		if (!converted) {
			if (DLUtil.compareVersions(version, fileEntry.getVersion()) >= 0) {
				contentLength = fileEntry.getSize();
			}
			else {
				contentLength = fileVersion.getSize();
			}
		}
		else {
			contentType = MimeTypesUtil.getContentType(fileName);
		}

		ServletResponseUtil.sendFile(
			request, response, fileName, inputStream, contentLength,
			contentType);
	}

	protected void sendFile(
			HttpServletResponse response, User user, long groupId,
			long folderId, String title)
		throws Exception {

		FileEntry fileEntry = DLAppServiceUtil.getFileEntry(
			groupId, folderId, title);

		String contentType = fileEntry.getMimeType();

		response.setContentType(contentType);

		InputStream inputStream = fileEntry.getContentStream();

		ServletResponseUtil.write(response, inputStream);
	}

	protected void sendGroups(
			HttpServletResponse response, User user, String path)
		throws Exception {

		List<WebServerEntry> webServerEntries = new ArrayList<WebServerEntry>();

		List<Group> groups = WebDAVUtil.getGroups(user);

		for (Group group : groups) {
			String name = HttpUtil.fixPath(group.getFriendlyURL());

			WebServerEntry webServerEntry = new WebServerEntry(
				path, name + StringPool.SLASH, null, null,
				group.getDescription(), 0);

			webServerEntries.add(webServerEntry);
		}

		sendHTML(response, path, webServerEntries);
	}

	protected void sendHTML(
			HttpServletResponse response, String path,
			List<WebServerEntry> webServerEntries)
		throws Exception {

		FreeMarkerContext freeMarkerContext =
			FreeMarkerEngineUtil.getWrappedRestrictedToolsContext();

		freeMarkerContext.put("dateFormat", _dateFormat);
		freeMarkerContext.put("entries", webServerEntries);
		freeMarkerContext.put("path", HttpUtil.encodePath(path));
		freeMarkerContext.put("serverInfo", ReleaseInfo.getServerInfo());
		freeMarkerContext.put("validator", Validator_IW.getInstance());

		String html = FreeMarkerUtil.process(_TPL_TEMPLATE, freeMarkerContext);

		response.setContentType(ContentTypes.TEXT_HTML_UTF8);

		ServletResponseUtil.write(response, html);
	}

	private static void _checkFileEntry(String[] pathArray)
		throws Exception {

		if (pathArray.length == 1) {
			long dlFileShortcutId = GetterUtil.getLong(pathArray[0]);

			DLFileShortcut dlFileShortcut =
				DLAppLocalServiceUtil.getFileShortcut(dlFileShortcutId);

			DLAppLocalServiceUtil.getFileEntry(
				dlFileShortcut.getToFileEntryId());
		}
		else if (pathArray.length == 2) {

			// Unable to check with UUID because of multiple repositories

		}
		else {
			long groupId = GetterUtil.getLong(pathArray[0]);
			long folderId = GetterUtil.getLong(pathArray[1]);
			String fileName = HttpUtil.decodeURL(pathArray[2], true);

			try {
				DLAppLocalServiceUtil.getFileEntry(groupId, folderId, fileName);
			}
			catch (RepositoryException re) {
			}
		}
	}

	private static long _getGroupId(long companyId, String name)
		throws Exception {

		try {
			Group group = GroupLocalServiceUtil.getFriendlyURLGroup(
				companyId, StringPool.SLASH + name);

			return group.getGroupId();
		}
		catch (NoSuchGroupException nsge) {
		}

		User user = UserLocalServiceUtil.getUserByScreenName(companyId, name);

		Group group = user.getGroup();

		return group.getGroupId();
	}

	private static User _getUser(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();

		if (PortalSessionThreadLocal.getHttpSession() == null) {
			PortalSessionThreadLocal.setHttpSession(session);
		}

		User user = PortalUtil.getUser(request);

		if (user != null) {
			return user;
		}

		String userIdString = (String)session.getAttribute("j_username");
		String password = (String)session.getAttribute("j_password");

		if ((userIdString != null) && (password != null)) {
			long userId = GetterUtil.getLong(userIdString);

			user = UserLocalServiceUtil.getUser(userId);
		}
		else {
			long companyId = PortalUtil.getCompanyId(request);

			Company company = CompanyLocalServiceUtil.getCompany(companyId);

			user = company.getDefaultUser();
		}

		return user;
	}

	private static final String _DATE_FORMAT_PATTERN = "d MMM yyyy HH:mm z";

	private static final String _TPL_TEMPLATE =
		"com/liferay/portal/webserver/dependencies/template.ftl";

	private static Format _dateFormat =
		FastDateFormatFactoryUtil.getSimpleDateFormat(_DATE_FORMAT_PATTERN);

}