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

package com.liferay.portal.webserver;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.image.ImageBag;
import com.liferay.portal.kernel.image.ImageToolUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.RepositoryException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.PortalSessionThreadLocal;
import com.liferay.portal.kernel.servlet.Range;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.URLTemplateResource;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.Validator_IW;
import com.liferay.portal.kernel.webdav.WebDAVUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.ImageConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.ImageImpl;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileVersion;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.service.ImageServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryServiceUtil;
import com.liferay.portlet.documentlibrary.util.AudioProcessorUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.documentlibrary.util.DocumentConversionUtil;
import com.liferay.portlet.documentlibrary.util.ImageProcessorUtil;
import com.liferay.portlet.documentlibrary.util.PDFProcessor;
import com.liferay.portlet.documentlibrary.util.PDFProcessorUtil;
import com.liferay.portlet.documentlibrary.util.VideoProcessor;
import com.liferay.portlet.documentlibrary.util.VideoProcessorUtil;
import com.liferay.portlet.trash.util.TrashUtil;

import java.awt.image.RenderedImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.text.Format;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletConfig;
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

	public static final String PATH_PORTLET_FILE_ENTRY = "portlet_file_entry";

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
			else if (PATH_PORTLET_FILE_ENTRY.equals(pathArray[0])) {
				FileEntry fileEntry = getPortletFileEntry(request, pathArray);

				if (fileEntry != null) {
					return true;
				}
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
						if (i != (pathArray.length - 1)) {
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
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);

		_lastModified = GetterUtil.getBoolean(
			servletConfig.getInitParameter("last_modified"), true);

		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		String templateId =
			"com/liferay/portal/webserver/dependencies/template.ftl";

		URL url = classLoader.getResource(templateId);

		_templateResource = new URLTemplateResource(templateId, url);
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
				PermissionCheckerFactoryUtil.create(user);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			if (_lastModified) {
				long lastModified = getLastModified(request);

				if (lastModified > 0) {
					long ifModifiedSince = request.getDateHeader(
						HttpHeaders.IF_MODIFIED_SINCE);

					if ((ifModifiedSince > 0) &&
						(ifModifiedSince == lastModified)) {

						response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);

						return;
					}
				}

				if (lastModified > 0) {
					response.setDateHeader(
						HttpHeaders.LAST_MODIFIED, lastModified);
				}
			}

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
				else if (PATH_PORTLET_FILE_ENTRY.equals(pathArray[0])) {
					sendPortletFileEntry(request, response, pathArray);
				}
				else {
					if (isLegacyImageGalleryImageId(request, response)) {
						return;
					}

					Image image = getImage(request, true);

					if (image != null) {
						writeImage(image, request, response);
					}
					else {
						sendDocumentLibrary(
							request, response, user,
							request.getServletPath() + StringPool.SLASH + path,
							pathArray);
					}
				}
			}
		}
		catch (NoSuchFileEntryException nsfee) {
			PortalUtil.sendError(
				HttpServletResponse.SC_NOT_FOUND, nsfee, request, response);
		}
		catch (NoSuchFolderException nsfe) {
			PortalUtil.sendError(
				HttpServletResponse.SC_NOT_FOUND, nsfe, request, response);
		}
		catch (PrincipalException pe) {
			processPrincipalException(pe, user, request, response);
		}
		catch (Exception e) {
			PortalUtil.sendError(e, request, response);
		}
	}

	protected static FileEntry getPortletFileEntry(
			HttpServletRequest request, String[] pathArray)
		throws Exception {

		long groupId = GetterUtil.getLong(pathArray[1]);
		String uuid = pathArray[3];

		FileEntry fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
			uuid, groupId);

		DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

		int status = ParamUtil.getInteger(
			request, "status", WorkflowConstants.STATUS_APPROVED);

		if ((status != WorkflowConstants.STATUS_IN_TRASH) &&
			dlFileEntry.isInTrash()) {

			return null;
		}

		return fileEntry;
	}

	protected Image convertFileEntry(boolean smallImage, FileEntry fileEntry)
		throws PortalException {

		try {
			Image image = new ImageImpl();

			image.setModifiedDate(fileEntry.getModifiedDate());

			InputStream is = null;

			if (smallImage) {
				is = ImageProcessorUtil.getThumbnailAsStream(
					fileEntry.getFileVersion(), 0);
			}
			else {
				is = fileEntry.getContentStream();
			}

			byte[] bytes = FileUtil.getBytes(is);

			image.setTextObj(bytes);

			image.setType(fileEntry.getExtension());

			return image;
		}
		catch (PortalException pe) {
			throw pe;
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	protected Image getDefaultImage(HttpServletRequest request, long imageId) {
		String path = GetterUtil.getString(request.getPathInfo());

		if (path.startsWith("/company_logo") ||
			path.startsWith("/layout_set_logo") || path.startsWith("/logo")) {

			return ImageToolUtil.getDefaultCompanyLogo();
		}
		else if (path.startsWith("/organization_logo")) {
			return ImageToolUtil.getDefaultOrganizationLogo();
		}
		else if (path.startsWith("/user_female_portrait")) {
			return ImageToolUtil.getDefaultUserFemalePortrait();
		}
		else if (path.startsWith("/user_male_portrait")) {
			return ImageToolUtil.getDefaultUserMalePortrait();
		}
		else if (path.startsWith("/user_portrait")) {
			return ImageToolUtil.getDefaultUserMalePortrait();
		}
		else {
			return null;
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
		else if (pathArray.length == 3) {
			long groupId = GetterUtil.getLong(pathArray[0]);
			long folderId = GetterUtil.getLong(pathArray[1]);
			String fileName = HttpUtil.decodeURL(pathArray[2]);

			if (fileName.contains(StringPool.QUESTION)) {
				fileName = fileName.substring(
					0, fileName.indexOf(StringPool.QUESTION));
			}

			return DLAppServiceUtil.getFileEntry(groupId, folderId, fileName);
		}
		else {
			long groupId = GetterUtil.getLong(pathArray[0]);

			String uuid = pathArray[3];

			return DLAppServiceUtil.getFileEntryByUuidAndGroupId(uuid, groupId);
		}
	}

	protected Image getImage(HttpServletRequest request, boolean getDefault)
		throws PortalException {

		Image image = null;

		long imageId = getImageId(request);

		if (imageId > 0) {
			image = ImageServiceUtil.getImage(imageId);

			String path = GetterUtil.getString(request.getPathInfo());

			if (path.startsWith("/user_female_portrait") ||
				path.startsWith("/user_male_portrait") ||
				path.startsWith("/user_portrait")) {

				image = getUserPortraitImageResized(image, imageId);
			}
		}
		else {
			String uuid = ParamUtil.getString(request, "uuid");
			long groupId = ParamUtil.getLong(request, "groupId");
			boolean igSmallImage = ParamUtil.getBoolean(
				request, "igSmallImage");

			if (Validator.isNotNull(uuid) && (groupId > 0)) {
				try {
					FileEntry fileEntry =
						DLAppServiceUtil.getFileEntryByUuidAndGroupId(
							uuid, groupId);

					image = convertFileEntry(igSmallImage, fileEntry);
				}
				catch (Exception e) {
				}
			}
		}

		if (getDefault) {
			if (image == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("Get a default image for " + imageId);
				}

				image = getDefaultImage(request, imageId);
			}
		}

		return image;
	}

	protected byte[] getImageBytes(HttpServletRequest request, Image image) {
		try {
			if (!PropsValues.IMAGE_AUTO_SCALE) {
				return image.getTextObj();
			}

			ImageBag imageBag = null;

			if (image.getImageId() == 0) {
				imageBag = ImageToolUtil.read(image.getTextObj());

				RenderedImage renderedImage = imageBag.getRenderedImage();

				image.setHeight(renderedImage.getHeight());
				image.setWidth(renderedImage.getWidth());
			}

			int height = ParamUtil.getInteger(
				request, "height", image.getHeight());
			int width = ParamUtil.getInteger(
				request, "width", image.getWidth());

			if ((height >= image.getHeight()) && (width >= image.getWidth())) {
				return image.getTextObj();
			}

			if (image.getImageId() != 0) {
				imageBag = ImageToolUtil.read(image.getTextObj());
			}

			RenderedImage renderedImage = ImageToolUtil.scale(
				imageBag.getRenderedImage(), height, width);

			return ImageToolUtil.getBytes(renderedImage, imageBag.getType());
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Error scaling image " + image.getImageId(), e);
			}
		}

		return image.getTextObj();
	}

	protected long getImageId(HttpServletRequest request) {

		// The image id may be passed in as image_id, img_id, or i_id

		long imageId = ParamUtil.getLong(request, "image_id");

		if (imageId <= 0) {
			imageId = ParamUtil.getLong(request, "img_id");
		}

		if (imageId <= 0) {
			imageId = ParamUtil.getLong(request, "i_id");
		}

		User user = null;

		if (imageId <= 0) {
			long companyId = ParamUtil.getLong(request, "companyId");
			String screenName = ParamUtil.getString(request, "screenName");

			if ((companyId > 0) && Validator.isNotNull(screenName)) {
				user = UserLocalServiceUtil.fetchUserByScreenName(
					companyId, screenName);

				if (user != null) {
					imageId = user.getPortraitId();
				}
			}
		}

		if (PropsValues.USERS_IMAGE_CHECK_TOKEN && (imageId > 0)) {
			String imageIdToken = ParamUtil.getString(request, "img_id_token");

			if (user == null) {
				user = UserLocalServiceUtil.fetchUserByPortraitId(imageId);
			}

			if ((user != null) &&
				!imageIdToken.equals(DigesterUtil.digest(user.getUserUuid()))) {

				return 0;
			}
		}

		return imageId;
	}

	@Override
	protected long getLastModified(HttpServletRequest request) {
		try {
			Date modifiedDate = null;

			Image image = getImage(request, true);

			if (image != null) {
				modifiedDate = image.getModifiedDate();
			}
			else {
				String path = HttpUtil.fixPath(request.getPathInfo());

				String[] pathArray = StringUtil.split(path, CharPool.SLASH);

				if (pathArray.length == 0) {
					return -1;
				}

				if (pathArray[0].equals("language")) {
					return -1;
				}

				FileEntry fileEntry = null;

				try {
					fileEntry = getFileEntry(pathArray);
				}
				catch (Exception e) {
				}

				if (fileEntry == null) {
					return -1;
				}

				String version = ParamUtil.getString(request, "version");

				if (Validator.isNotNull(version)) {
					FileVersion fileVersion = fileEntry.getFileVersion(version);

					modifiedDate = fileVersion.getModifiedDate();
				}
				else {
					modifiedDate = fileEntry.getModifiedDate();
				}
			}

			if (modifiedDate == null) {
				modifiedDate = PortalUtil.getUptime();
			}

			// Round down and remove milliseconds

			return (modifiedDate.getTime() / 1000) * 1000;
		}
		catch (PrincipalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(pe, pe);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return -1;
	}

	protected Image getUserPortraitImageResized(Image image, long imageId)
		throws PortalException {

		if (image == null) {
			return null;
		}

		if (((PropsValues.USERS_IMAGE_MAX_HEIGHT > 0) &&
			 (image.getHeight() > PropsValues.USERS_IMAGE_MAX_HEIGHT)) ||
			((PropsValues.USERS_IMAGE_MAX_WIDTH > 0) &&
			 (image.getWidth() > PropsValues.USERS_IMAGE_MAX_WIDTH))) {

			User user = UserLocalServiceUtil.getUserByPortraitId(imageId);

			UserLocalServiceUtil.updatePortrait(
				user.getUserId(), image.getTextObj());

			return ImageLocalServiceUtil.getImage(imageId);
		}

		return image;
	}

	protected boolean isLegacyImageGalleryImageId(
		HttpServletRequest request, HttpServletResponse response) {

		try {
			long imageId = getImageId(request);

			if (imageId == 0) {
				return false;
			}

			DLFileEntry dlFileEntry =
				DLFileEntryServiceUtil.fetchFileEntryByImageId(imageId);

			if (dlFileEntry == null) {
				return false;
			}

			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			String queryString = StringPool.BLANK;

			if (imageId == dlFileEntry.getSmallImageId()) {
				queryString = "&imageThumbnail=1";
			}
			else if (imageId == dlFileEntry.getCustom1ImageId()) {
				queryString = "&imageThumbnail=2";
			}
			else if (imageId == dlFileEntry.getCustom2ImageId()) {
				queryString = "&imageThumbnail=3";
			}

			FileEntry fileEntry = new LiferayFileEntry(dlFileEntry);

			FileVersion fileVersion = new LiferayFileVersion(
				dlFileEntry.getFileVersion());

			if (PropsValues.DL_FILE_ENTRY_IG_THUMBNAIL_GENERATION &&
				Validator.isNotNull(queryString)) {

				ImageProcessorUtil.hasImages(fileVersion);
			}

			String url = DLUtil.getPreviewURL(
				fileEntry, fileVersion, themeDisplay, queryString);

			response.setHeader(HttpHeaders.LOCATION, url);
			response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);

			return true;
		}
		catch (Exception e) {
		}

		return false;
	}

	protected boolean isSupportsRangeHeader(String contentType) {
		return _acceptRangesMimeTypes.contains(contentType);
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

		String redirect = PortalUtil.getPathMain() + "/portal/login";

		String currentURL = PortalUtil.getCurrentURL(request);

		redirect = HttpUtil.addParameter(redirect, "redirect", currentURL);

		response.sendRedirect(redirect);
	}

	protected void sendDocumentLibrary(
			HttpServletRequest request, HttpServletResponse response, User user,
			String path, String[] pathArray)
		throws Exception {

		long groupId = _getGroupId(user.getCompanyId(), pathArray[0]);

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		UnicodeProperties typeSettingsProperties =
			group.getTypeSettingsProperties();

		boolean directoryIndexingEnabled = GetterUtil.getBoolean(
			typeSettingsProperties.getProperty("directoryIndexingEnabled"),
			PropsValues.WEB_SERVER_SERVLET_DIRECTORY_INDEXING_ENABLED);

		if (!directoryIndexingEnabled) {
			throw new NoSuchFolderException();
		}

		long folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		for (int i = 1; i < pathArray.length; i++) {
			String name = pathArray[i];

			try {
				Folder folder = DLAppServiceUtil.getFolder(
					groupId, folderId, name);

				folderId = folder.getFolderId();
			}
			catch (NoSuchFolderException nsfe) {
				if (i != (pathArray.length - 1)) {
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
			HttpServletRequest request, HttpServletResponse response, User user,
			String[] pathArray)
		throws Exception {

		// Retrieve file details

		FileEntry fileEntry = getFileEntry(pathArray);

		if (fileEntry == null) {
			throw new NoSuchFileEntryException();
		}

		String version = ParamUtil.getString(request, "version");

		if (Validator.isNull(version)) {
			if (Validator.isNotNull(fileEntry.getVersion())) {
				version = fileEntry.getVersion();
			}
		}

		String tempFileId = DLUtil.getTempFileId(
			fileEntry.getFileEntryId(), version);

		if (fileEntry.isInTrash()) {
			int status = ParamUtil.getInteger(
				request, "status", WorkflowConstants.STATUS_APPROVED);

			if (status != WorkflowConstants.STATUS_IN_TRASH) {
				throw new NoSuchFileEntryException();
			}

			PermissionChecker permissionChecker =
				PermissionThreadLocal.getPermissionChecker();

			if (!PortletPermissionUtil.hasControlPanelAccessPermission(
					permissionChecker, fileEntry.getGroupId(),
					PortletKeys.TRASH)) {

				throw new PrincipalException();
			}
		}

		FileVersion fileVersion = fileEntry.getFileVersion(version);

		if ((ParamUtil.getInteger(request, "height") > 0) ||
			(ParamUtil.getInteger(request, "width") > 0)) {

			InputStream inputStream = fileVersion.getContentStream(true);

			Image image = ImageToolUtil.getImage(inputStream);

			writeImage(image, request, response);

			return;
		}

		String fileName = fileVersion.getFileName();

		// Handle requested conversion

		boolean converted = false;

		String targetExtension = ParamUtil.getString(
			request, "targetExtension");
		int imageThumbnail = ParamUtil.getInteger(request, "imageThumbnail");
		int documentThumbnail = ParamUtil.getInteger(
			request, "documentThumbnail");
		int previewFileIndex = ParamUtil.getInteger(
			request, "previewFileIndex");
		boolean audioPreview = ParamUtil.getBoolean(request, "audioPreview");
		boolean imagePreview = ParamUtil.getBoolean(request, "imagePreview");
		boolean videoPreview = ParamUtil.getBoolean(request, "videoPreview");
		int videoThumbnail = ParamUtil.getInteger(request, "videoThumbnail");

		InputStream inputStream = null;
		long contentLength = 0;

		if ((imageThumbnail > 0) && (imageThumbnail <= 3)) {
			fileName = FileUtil.stripExtension(fileName).concat(
				StringPool.PERIOD).concat(
					ImageProcessorUtil.getThumbnailType(fileVersion));

			int thumbnailIndex = imageThumbnail - 1;

			inputStream = ImageProcessorUtil.getThumbnailAsStream(
				fileVersion, thumbnailIndex);
			contentLength = ImageProcessorUtil.getThumbnailFileSize(
				fileVersion, thumbnailIndex);

			converted = true;
		}
		else if ((documentThumbnail > 0) && (documentThumbnail <= 3)) {
			fileName = FileUtil.stripExtension(fileName).concat(
				StringPool.PERIOD).concat(PDFProcessor.THUMBNAIL_TYPE);

			int thumbnailIndex = documentThumbnail - 1;

			inputStream = PDFProcessorUtil.getThumbnailAsStream(
				fileVersion, thumbnailIndex);
			contentLength = PDFProcessorUtil.getThumbnailFileSize(
				fileVersion, thumbnailIndex);

			converted = true;
		}
		else if (previewFileIndex > 0) {
			fileName = FileUtil.stripExtension(fileName).concat(
				StringPool.PERIOD).concat(PDFProcessor.PREVIEW_TYPE);
			inputStream = PDFProcessorUtil.getPreviewAsStream(
				fileVersion, previewFileIndex);
			contentLength = PDFProcessorUtil.getPreviewFileSize(
				fileVersion, previewFileIndex);

			converted = true;
		}
		else if (audioPreview || videoPreview) {
			String type = ParamUtil.getString(request, "type");

			fileName = FileUtil.stripExtension(fileName).concat(
				StringPool.PERIOD).concat(type);

			if (audioPreview) {
				inputStream = AudioProcessorUtil.getPreviewAsStream(
					fileVersion, type);
				contentLength = AudioProcessorUtil.getPreviewFileSize(
					fileVersion, type);
			}
			else {
				inputStream = VideoProcessorUtil.getPreviewAsStream(
					fileVersion, type);
				contentLength = VideoProcessorUtil.getPreviewFileSize(
					fileVersion, type);
			}

			converted = true;
		}
		else if (imagePreview) {
			String type = ImageProcessorUtil.getPreviewType(fileVersion);

			fileName = FileUtil.stripExtension(fileName).concat(
				StringPool.PERIOD).concat(type);

			inputStream = ImageProcessorUtil.getPreviewAsStream(fileVersion);

			contentLength = ImageProcessorUtil.getPreviewFileSize(fileVersion);

			converted = true;
		}
		else if ((videoThumbnail > 0) && (videoThumbnail <= 3)) {
			fileName = FileUtil.stripExtension(fileName).concat(
				StringPool.PERIOD).concat(VideoProcessor.THUMBNAIL_TYPE);

			int thumbnailIndex = videoThumbnail - 1;

			inputStream = VideoProcessorUtil.getThumbnailAsStream(
				fileVersion, thumbnailIndex);
			contentLength = VideoProcessorUtil.getThumbnailFileSize(
				fileVersion, thumbnailIndex);

			converted = true;
		}
		else {
			inputStream = fileVersion.getContentStream(true);
			contentLength = fileVersion.getSize();

			if (Validator.isNotNull(targetExtension)) {
				File convertedFile = DocumentConversionUtil.convert(
					tempFileId, inputStream, fileVersion.getExtension(),
					targetExtension);

				if (convertedFile != null) {
					fileName = FileUtil.stripExtension(fileName).concat(
						StringPool.PERIOD).concat(targetExtension);
					inputStream = new FileInputStream(convertedFile);
					contentLength = convertedFile.length();

					converted = true;
				}
			}
		}

		// Determine proper content type

		String contentType = null;

		if (converted) {
			contentType = MimeTypesUtil.getContentType(fileName);
		}
		else {
			contentType = fileVersion.getMimeType();
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Content type set to " + contentType);
		}

		// Send file

		if (isSupportsRangeHeader(contentType)) {
			sendFileWithRangeHeader(
				request, response, fileName, inputStream, contentLength,
				contentType);
		}
		else {
			boolean download = ParamUtil.getBoolean(request, "download");

			if (download) {
				ServletResponseUtil.sendFile(
					request, response, fileName, inputStream, contentLength,
					contentType, HttpHeaders.CONTENT_DISPOSITION_ATTACHMENT);
			}
			else {
				ServletResponseUtil.sendFile(
					request, response, fileName, inputStream, contentLength,
					contentType);
			}
		}
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

		ServletResponseUtil.write(response, inputStream, fileEntry.getSize());
	}

	protected void sendFileWithRangeHeader(
			HttpServletRequest request, HttpServletResponse response,
			String fileName, InputStream inputStream, long contentLength,
			String contentType)
		throws IOException {

		if (_log.isDebugEnabled()) {
			_log.debug("Accepting ranges for the file " + fileName);
		}

		response.setHeader(
			HttpHeaders.ACCEPT_RANGES, HttpHeaders.ACCEPT_RANGES_BYTES_VALUE);

		List<Range> ranges = null;

		try {
			ranges = ServletResponseUtil.getRanges(
				request, response, contentLength);
		}
		catch (IOException ioe) {
			if (_log.isErrorEnabled()) {
				_log.error(ioe);
			}

			response.setHeader(
				HttpHeaders.CONTENT_RANGE, "bytes */" + contentLength);

			response.sendError(
				HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);

			return;
		}

		if ((ranges == null) || ranges.isEmpty()) {
			ServletResponseUtil.sendFile(
				request, response, fileName, inputStream, contentLength,
				contentType);
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Request has range header " +
						request.getHeader(HttpHeaders.RANGE));
			}

			ServletResponseUtil.write(
				request, response, fileName, ranges, inputStream, contentLength,
				contentType);
		}
	}

	protected void sendGroups(
			HttpServletResponse response, User user, String path)
		throws Exception {

		if (!PropsValues.WEB_SERVER_SERVLET_DIRECTORY_INDEXING_ENABLED) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);

			return;
		}

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

		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_FTL, _templateResource, true);

		template.put("dateFormat", _dateFormat);
		template.put("entries", webServerEntries);
		template.put("path", HttpUtil.encodePath(path));

		if (_WEB_SERVER_SERVLET_VERSION_VERBOSITY_DEFAULT) {
		}
		else if (_WEB_SERVER_SERVLET_VERSION_VERBOSITY_PARTIAL) {
			template.put("releaseInfo", ReleaseInfo.getName());
		}
		else {
			template.put("releaseInfo", ReleaseInfo.getReleaseInfo());
		}

		template.put("validator", Validator_IW.getInstance());

		response.setContentType(ContentTypes.TEXT_HTML_UTF8);

		template.processTemplate(response.getWriter());
	}

	protected void sendPortletFileEntry(
			HttpServletRequest request, HttpServletResponse response,
			String[] pathArray)
		throws Exception {

		FileEntry fileEntry = getPortletFileEntry(request, pathArray);

		if (fileEntry == null) {
			return;
		}

		String fileName = HttpUtil.decodeURL(HtmlUtil.escape(pathArray[2]));

		if (fileEntry.isInTrash()) {
			fileName = TrashUtil.getOriginalTitle(fileName);
		}

		boolean download = ParamUtil.getBoolean(request, "download");

		if (download) {
			ServletResponseUtil.sendFile(
				request, response, fileName, fileEntry.getContentStream(),
				fileEntry.getSize(), fileEntry.getMimeType(),
				HttpHeaders.CONTENT_DISPOSITION_ATTACHMENT);
		}
		else {
			ServletResponseUtil.sendFile(
				request, response, fileName, fileEntry.getContentStream(),
				fileEntry.getSize(), fileEntry.getMimeType());
		}
	}

	protected void writeImage(
		Image image, HttpServletRequest request, HttpServletResponse response) {

		if (image == null) {
			return;
		}

		String contentType = null;

		String type = image.getType();

		if (!type.equals(ImageConstants.TYPE_NOT_AVAILABLE)) {
			contentType = MimeTypesUtil.getExtensionContentType(type);

			response.setContentType(contentType);
		}

		String fileName = ParamUtil.getString(request, "fileName");

		try {
			byte[] bytes = getImageBytes(request, image);

			if (Validator.isNotNull(fileName)) {
				ServletResponseUtil.sendFile(
					request, response, fileName, bytes, contentType);
			}
			else {
				ServletResponseUtil.write(response, bytes);
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}
	}

	private static void _checkFileEntry(String[] pathArray) throws Exception {
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
		else if (pathArray.length == 3) {
			long groupId = GetterUtil.getLong(pathArray[0]);
			long folderId = GetterUtil.getLong(pathArray[1]);
			String fileName = HttpUtil.decodeURL(pathArray[2]);

			try {
				DLAppLocalServiceUtil.getFileEntry(groupId, folderId, fileName);
			}
			catch (RepositoryException re) {
			}
		}
		else {
			long groupId = GetterUtil.getLong(pathArray[0]);

			String uuid = pathArray[3];

			try {
				DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(
					uuid, groupId);
			}
			catch (RepositoryException re) {
			}
		}
	}

	private static long _getGroupId(long companyId, String name)
		throws Exception {

		Group group = GroupLocalServiceUtil.fetchFriendlyURLGroup(
			companyId, StringPool.SLASH + name);

		if (group != null) {
			return group.getGroupId();
		}

		User user = UserLocalServiceUtil.getUserByScreenName(companyId, name);

		group = user.getGroup();

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

	private static final boolean _WEB_SERVER_SERVLET_VERSION_VERBOSITY_DEFAULT =
		StringUtil.equalsIgnoreCase(
			PropsValues.WEB_SERVER_SERVLET_VERSION_VERBOSITY,
			ReleaseInfo.getName());

	private static final boolean _WEB_SERVER_SERVLET_VERSION_VERBOSITY_PARTIAL =
		StringUtil.equalsIgnoreCase(
			PropsValues.WEB_SERVER_SERVLET_VERSION_VERBOSITY, "partial");

	private static Log _log = LogFactoryUtil.getLog(WebServerServlet.class);

	private static Set<String> _acceptRangesMimeTypes = SetUtil.fromArray(
		PropsValues.WEB_SERVER_SERVLET_ACCEPT_RANGES_MIME_TYPES);
	private static Format _dateFormat =
		FastDateFormatFactoryUtil.getSimpleDateFormat("d MMM yyyy HH:mm z");

	private boolean _lastModified = true;
	private TemplateResource _templateResource;

}