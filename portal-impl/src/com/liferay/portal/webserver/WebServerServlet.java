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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.freemarker.FreeMarkerContext;
import com.liferay.portal.kernel.freemarker.FreeMarkerEngineUtil;
import com.liferay.portal.kernel.image.ImageBag;
import com.liferay.portal.kernel.image.ImageProcessorUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.RepositoryException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.PortalSessionThreadLocal;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.Validator_IW;
import com.liferay.portal.kernel.webdav.WebDAVUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.ImageConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.ImageImpl;
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
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryServiceUtil;
import com.liferay.portlet.documentlibrary.util.AudioProcessor;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.documentlibrary.util.DocumentConversionUtil;
import com.liferay.portlet.documentlibrary.util.ImageProcessor;
import com.liferay.portlet.documentlibrary.util.PDFProcessor;
import com.liferay.portlet.documentlibrary.util.VideoProcessor;

import java.awt.image.RenderedImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.text.Format;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);

		_lastModified = GetterUtil.getBoolean(
			servletConfig.getInitParameter("last_modified"), true);
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
		catch (PrincipalException pe) {
			processPrincipalException(pe, user, request, response);
		}
		catch (Exception e) {
			PortalUtil.sendError(e, request, response);
		}
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

			StringBundler sb = new StringBundler(9);

			sb.append("/documents/");
			sb.append(dlFileEntry.getGroupId());
			sb.append(StringPool.SLASH);
			sb.append(dlFileEntry.getFolderId());
			sb.append(StringPool.SLASH);
			sb.append(
				HttpUtil.encodeURL(HtmlUtil.unescape(dlFileEntry.getTitle())));
			sb.append("?version=");
			sb.append(dlFileEntry.getVersion());

			if (imageId == dlFileEntry.getSmallImageId()) {
				sb.append("&imageThumbnail=1");
			}
			else if (imageId == dlFileEntry.getSmallImageId()) {
				sb.append("&imageThumbnail=2");
			}
			else if (imageId == dlFileEntry.getSmallImageId()) {
				sb.append("&imageThumbnail=3");
			}

			response.setHeader(HttpHeaders.LOCATION, sb.toString());
			response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);

			return true;
		}
		catch (Exception e) {
		}

		return false;
	}

	protected Image getDefaultImage(HttpServletRequest request, long imageId) {
		String path = GetterUtil.getString(request.getPathInfo());

		if (path.startsWith("/company_logo") ||
			path.startsWith("/layout_set_logo") || path.startsWith("/logo")) {

			return ImageLocalServiceUtil.getDefaultCompanyLogo();
		}
		else if (path.startsWith("/organization_logo")) {
			return ImageLocalServiceUtil.getDefaultOrganizationLogo();
		}
		else if (path.startsWith("/user_female_portrait")) {
			return ImageLocalServiceUtil.getDefaultUserFemalePortrait();
		}
		else if (path.startsWith("/user_male_portrait")) {
			return ImageLocalServiceUtil.getDefaultUserMalePortrait();
		}
		else if (path.startsWith("/user_portrait")) {
			return ImageLocalServiceUtil.getDefaultUserMalePortrait();
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
		else {
			long groupId = GetterUtil.getLong(pathArray[0]);
			long folderId = GetterUtil.getLong(pathArray[1]);

			String fileName = HttpUtil.decodeURL(pathArray[2], true);

			if (fileName.contains(StringPool.QUESTION)) {
				fileName = fileName.substring(
					0, fileName.indexOf(StringPool.QUESTION));
			}

			return DLAppServiceUtil.getFileEntry(groupId, folderId, fileName);
		}
	}

	protected Image getImage(
			HttpServletRequest request, boolean getDefault)
		throws PortalException, SystemException {

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

	protected Image convertFileEntry(boolean smallImage, FileEntry fileEntry)
		throws PortalException, SystemException {

		try {
			Image image = new ImageImpl();

			image.setModifiedDate(fileEntry.getModifiedDate());

			InputStream is = null;

			if (smallImage) {
				is = ImageProcessor.getThumbnailAsStream(
					fileEntry.getFileVersion());
			}
			else {
				is = fileEntry.getContentStream();
			}

			byte[] bytes = FileUtil.getBytes(is);

			image.setTextObj(bytes);

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

	protected byte[] getImageBytes(HttpServletRequest request, Image image) {
		try {
			if (!PropsValues.IMAGE_AUTO_SCALE) {
				return image.getTextObj();
			}

			ImageBag imageBag = null;

			if (image.getImageId() == 0) {
				imageBag = ImageProcessorUtil.read(image.getTextObj());

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
				imageBag = ImageProcessorUtil.read(image.getTextObj());
			}

			RenderedImage renderedImage = ImageProcessorUtil.scale(
				imageBag.getRenderedImage(), height, width);

			return ImageProcessorUtil.getBytes(
				renderedImage, imageBag.getType());
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

		if (imageId <= 0) {
			long companyId = ParamUtil.getLong(request, "companyId");
			String screenName = ParamUtil.getString(request, "screenName");

			try {
				if ((companyId > 0) && Validator.isNotNull(screenName)) {
					User user = UserLocalServiceUtil.getUserByScreenName(
						companyId, screenName);

					imageId = user.getPortraitId();
				}
			}
			catch (Exception e) {
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
		throws PortalException, SystemException {

		if (image == null) {
			return null;
		}

		if ((image.getHeight() > PropsValues.USERS_IMAGE_MAX_HEIGHT) ||
			(image.getWidth() > PropsValues.USERS_IMAGE_MAX_WIDTH)) {

			User user = UserLocalServiceUtil.getUserByPortraitId(imageId);

			UserLocalServiceUtil.updatePortrait(
				user.getUserId(), image.getTextObj());

			return ImageLocalServiceUtil.getImage(imageId);
		}

		return image;
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
		}

		String tempFileId = DLUtil.getTempFileId(
			fileEntry.getFileEntryId(), version);

		FileVersion fileVersion = fileEntry.getFileVersion(version);

		String fileName = fileVersion.getTitle();

		String extension = fileVersion.getExtension();

		if (!fileName.endsWith(StringPool.PERIOD + extension)) {
			fileName += StringPool.PERIOD + extension;
		}

		boolean converted = false;

		String targetExtension = ParamUtil.getString(
			request, "targetExtension");
		int imageThumbnail = ParamUtil.getInteger(request, "imageThumbnail");
		boolean documentThumbnail = ParamUtil.getBoolean(
			request, "documentThumbnail");
		int previewFileIndex = ParamUtil.getInteger(
			request, "previewFileIndex");
		boolean audioPreview = ParamUtil.getBoolean(request, "audioPreview");
		boolean videoPreview = ParamUtil.getBoolean(request, "videoPreview");
		boolean videoThumbnail = ParamUtil.getBoolean(
			request, "videoThumbnail");

		InputStream inputStream = null;
		long contentLength = 0;

		if ((imageThumbnail > 0) && (imageThumbnail < 3)) {
			fileName = FileUtil.stripExtension(fileName).concat(
				StringPool.PERIOD).concat(fileVersion.getExtension());

			if (imageThumbnail == 1) {
				inputStream = ImageProcessor.getThumbnailAsStream(fileVersion);
				contentLength = ImageProcessor.getThumbnailFileSize(
					fileVersion);
			}
			else if (imageThumbnail == 2) {
				inputStream = ImageProcessor.getCustom1AsStream(fileVersion);
				contentLength = ImageProcessor.getCustom1FileSize(fileVersion);
			}
			else if (imageThumbnail == 3) {
				inputStream = ImageProcessor.getCustom2AsStream(fileVersion);
				contentLength = ImageProcessor.getCustom2FileSize(fileVersion);
			}

			converted = true;
		}
		else if (documentThumbnail) {
			fileName = FileUtil.stripExtension(fileName).concat(
				StringPool.PERIOD).concat(PDFProcessor.THUMBNAIL_TYPE);
			inputStream = PDFProcessor.getThumbnailAsStream(fileVersion);
			contentLength = PDFProcessor.getThumbnailFileSize(fileVersion);

			converted = true;
		}
		else if (previewFileIndex > 0) {
			fileName = FileUtil.stripExtension(fileName).concat(
				StringPool.PERIOD).concat(PDFProcessor.PREVIEW_TYPE);
			inputStream = PDFProcessor.getPreviewAsStream(
				fileVersion, previewFileIndex);
			contentLength = PDFProcessor.getPreviewFileSize(
				fileVersion, previewFileIndex);

			converted = true;
		}
		else if (audioPreview) {
			fileName = FileUtil.stripExtension(fileName).concat(
				StringPool.PERIOD).concat(AudioProcessor.PREVIEW_TYPE);
			inputStream = AudioProcessor.getPreviewAsStream(fileVersion);
			contentLength = AudioProcessor.getPreviewFileSize(fileVersion);

			converted = true;
		}
		else if (videoPreview) {
			fileName = FileUtil.stripExtension(fileName).concat(
				StringPool.PERIOD).concat(VideoProcessor.PREVIEW_TYPE);
			inputStream = VideoProcessor.getPreviewAsStream(fileVersion);
			contentLength = VideoProcessor.getPreviewFileSize(fileVersion);

			converted = true;
		}
		else if (videoThumbnail) {
			fileName = FileUtil.stripExtension(fileName).concat(
				StringPool.PERIOD).concat(VideoProcessor.THUMBNAIL_TYPE);
			inputStream = VideoProcessor.getThumbnailAsStream(fileVersion);
			contentLength = VideoProcessor.getThumbnailFileSize(fileVersion);

			converted = true;
		}
		else {
			inputStream = fileVersion.getContentStream(true);
			contentLength = fileVersion.getSize();

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
		}

		String contentType = null;

		if (converted) {
			contentType = MimeTypesUtil.getContentType(fileName);
		}
		else {
			contentType = fileVersion.getMimeType();
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

	protected void writeImage(
		Image image, HttpServletRequest request, HttpServletResponse response) {

		if (image == null) {
			return;
		}

		String contentType = null;

		String type = image.getType();

		if (!type.equals(ImageConstants.TYPE_NOT_AVAILABLE)) {
			contentType = MimeTypesUtil.getContentType("A." + type);

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

	private static Log _log = LogFactoryUtil.getLog(WebServerServlet.class);

	private boolean _lastModified = true;

}