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

package com.liferay.sync.internal.servlet;

import com.liferay.document.library.kernel.exception.DuplicateFileException;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.exception.NoSuchFileVersionException;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFileVersionLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NoSuchImageException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.model.ImageConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ImageLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.PortalSessionThreadLocal;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.sync.SyncSiteUnavailableException;
import com.liferay.sync.model.SyncDLFileVersionDiff;
import com.liferay.sync.model.SyncDevice;
import com.liferay.sync.service.SyncDLFileVersionDiffLocalServiceUtil;
import com.liferay.sync.service.SyncDeviceLocalServiceUtil;
import com.liferay.sync.service.internal.configuration.SyncServiceConfigurationValues;
import com.liferay.sync.util.SyncHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dennis Ju
 */
@Component(
	immediate = true,
	property = {
		"osgi.http.whiteboard.context.path=/sync",
		"osgi.http.whiteboard.servlet.name=com.liferay.sync.internal.servlet.SyncDownloadServlet",
		"osgi.http.whiteboard.servlet.pattern=/sync/download/*"
	},
	service = Servlet.class
)
public class SyncDownloadServlet extends HttpServlet {

	@Override
	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		try {
			HttpSession session = request.getSession();

			if (PortalSessionThreadLocal.getHttpSession() == null) {
				PortalSessionThreadLocal.setHttpSession(session);
			}

			User user = _portal.getUser(request);

			String syncUuid = request.getHeader("Sync-UUID");

			if (syncUuid != null) {
				SyncDevice syncDevice =
					SyncDeviceLocalServiceUtil.
						fetchSyncDeviceByUuidAndCompanyId(
							syncUuid, user.getCompanyId());

				if (syncDevice != null) {
					syncDevice.checkStatus();
				}
			}

			PermissionChecker permissionChecker =
				PermissionCheckerFactoryUtil.create(user);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			String path = _http.fixPath(request.getPathInfo());

			String[] pathArray = StringUtil.split(path, CharPool.SLASH);

			if (pathArray[0].equals("image")) {
				long imageId = GetterUtil.getLong(pathArray[1]);

				sendImage(request, response, imageId);
			}
			else if (pathArray[0].equals("zip")) {
				String zipFileIds = ParamUtil.get(
					request, "zipFileIds", StringPool.BLANK);

				if (Validator.isNull(zipFileIds)) {
					throw new IllegalArgumentException(
						"Missing parameter zipFileIds");
				}

				JSONArray zipFileIdsJSONArray = JSONFactoryUtil.createJSONArray(
					zipFileIds);

				sendZipFile(response, user.getUserId(), zipFileIdsJSONArray);
			}
			else if (pathArray[0].equals("zipfolder")) {
				long repositoryId = ParamUtil.getLong(request, "repositoryId");
				long folderId = ParamUtil.getLong(request, "folderId");

				if (repositoryId == 0) {
					throw new IllegalArgumentException(
						"Missing parameter repositoryId");
				}
				else if (folderId == 0) {
					throw new IllegalArgumentException(
						"Missing parameter folderId");
				}

				sendZipFolder(
					response, user.getUserId(), repositoryId, folderId);
			}
			else {
				long groupId = GetterUtil.getLong(pathArray[0]);
				String fileUuid = pathArray[1];

				Group group = _groupLocalService.fetchGroup(groupId);

				if ((group == null) || !_syncHelper.isSyncEnabled(group)) {
					response.setHeader(
						_ERROR_HEADER,
						SyncSiteUnavailableException.class.getName());

					ServletResponseUtil.write(response, new byte[0]);

					return;
				}

				boolean patch = ParamUtil.getBoolean(request, "patch");

				if (patch) {
					sendPatch(
						request, response, user.getUserId(), groupId, fileUuid);
				}
				else {
					sendFile(
						request, response, user.getUserId(), groupId, fileUuid);
				}
			}
		}
		catch (NoSuchFileEntryException nsfee) {
			_portal.sendError(
				HttpServletResponse.SC_NOT_FOUND, nsfee, request, response);
		}
		catch (NoSuchFileVersionException nsfve) {
			_portal.sendError(
				HttpServletResponse.SC_NOT_FOUND, nsfve, request, response);
		}
		catch (Exception e) {
			_portal.sendError(e, request, response);
		}
	}

	protected void addZipFolderEntry(
			long userId, long repositoryId, long folderId, String folderPath,
			ZipWriter zipWriter)
		throws Exception {

		List<FileEntry> fileEntries = _dlAppService.getFileEntries(
			repositoryId, folderId);

		for (FileEntry fileEntry : fileEntries) {
			try (InputStream inputStream =
					_dlFileEntryLocalService.getFileAsStream(
						userId, fileEntry.getFileEntryId(),
						fileEntry.getVersion(), false)) {

				String filePath = folderPath + fileEntry.getTitle();

				zipWriter.addEntry(filePath, inputStream);
			}
		}

		List<Folder> childFolders = _dlAppService.getFolders(
			repositoryId, folderId);

		for (Folder childFolder : childFolders) {
			String childFolderPath =
				folderPath + childFolder.getName() + StringPool.FORWARD_SLASH;

			addZipFolderEntry(
				userId, repositoryId, childFolder.getFolderId(),
				childFolderPath, zipWriter);
		}
	}

	protected File getDeltaFile(
			long userId, long fileEntryId, long sourceVersionId,
			long targetVersionId)
		throws Exception {

		DLFileVersion sourceDLFileVersion =
			_dlFileVersionLocalService.getDLFileVersion(sourceVersionId);

		File sourceFile = _dlFileEntryLocalService.getFile(
			fileEntryId, sourceDLFileVersion.getVersion(), false);

		DLFileVersion targetDLFileVersion =
			_dlFileVersionLocalService.getDLFileVersion(targetVersionId);

		File targetFile = _dlFileEntryLocalService.getFile(
			fileEntryId, targetDLFileVersion.getVersion(), false);

		return _syncHelper.getFileDelta(sourceFile, targetFile);
	}

	protected DownloadServletInputStream getFileDownloadServletInputStream(
			long userId, long groupId, String uuid, String version,
			long versionId)
		throws Exception {

		FileEntry fileEntry = _dlAppService.getFileEntryByUuidAndGroupId(
			uuid, groupId);

		if (fileEntry.isInTrash()) {
			throw new NoSuchFileEntryException();
		}

		if (Validator.isNull(version)) {
			InputStream inputStream = _dlFileEntryLocalService.getFileAsStream(
				userId, fileEntry.getFileEntryId(), fileEntry.getVersion(),
				false);

			return new DownloadServletInputStream(
				inputStream, fileEntry.getFileName(), fileEntry.getMimeType(),
				fileEntry.getSize());
		}
		else {
			if (versionId > 0) {
				DLFileVersion dlFileVersion =
					_dlFileVersionLocalService.fetchDLFileVersion(versionId);

				return new DownloadServletInputStream(
					dlFileVersion.getContentStream(false),
					dlFileVersion.getFileName(), dlFileVersion.getMimeType(),
					dlFileVersion.getSize());
			}
			else {
				FileVersion fileVersion = fileEntry.getFileVersion(version);

				return new DownloadServletInputStream(
					fileVersion.getContentStream(false),
					fileVersion.getFileName(), fileVersion.getMimeType(),
					fileVersion.getSize());
			}
		}
	}

	protected DownloadServletInputStream getPatchDownloadServletInputStream(
			long userId, long groupId, String uuid, long sourceVersionId,
			long targetVersionId)
		throws Exception {

		FileEntry fileEntry = _dlAppService.getFileEntryByUuidAndGroupId(
			uuid, groupId);

		if (fileEntry.isInTrash()) {
			throw new NoSuchFileEntryException();
		}

		if (!SyncServiceConfigurationValues.SYNC_FILE_DIFF_CACHE_ENABLED) {
			File deltaFile = null;

			try {
				deltaFile = getDeltaFile(
					userId, fileEntry.getFileEntryId(), sourceVersionId,
					targetVersionId);

				return new DownloadServletInputStream(
					new FileInputStream(deltaFile), deltaFile.length());
			}
			finally {
				FileUtil.delete(deltaFile);
			}
		}

		SyncDLFileVersionDiff syncDLFileVersionDiff =
			SyncDLFileVersionDiffLocalServiceUtil.fetchSyncDLFileVersionDiff(
				fileEntry.getFileEntryId(), sourceVersionId, targetVersionId);

		if (syncDLFileVersionDiff != null) {
			SyncDLFileVersionDiffLocalServiceUtil.refreshExpirationDate(
				syncDLFileVersionDiff.getSyncDLFileVersionDiffId());

			FileEntry dataFileEntry =
				PortletFileRepositoryUtil.getPortletFileEntry(
					syncDLFileVersionDiff.getDataFileEntryId());

			return new DownloadServletInputStream(
				dataFileEntry.getContentStream(), dataFileEntry.getSize());
		}
		else {
			File deltaFile = null;

			try {
				deltaFile = getDeltaFile(
					userId, fileEntry.getFileEntryId(), sourceVersionId,
					targetVersionId);

				try {
					SyncDLFileVersionDiffLocalServiceUtil.
						addSyncDLFileVersionDiff(
							fileEntry.getFileEntryId(), sourceVersionId,
							targetVersionId, deltaFile);
				}
				catch (DuplicateFileException dfe) {

					// LPS-52675

					if (_log.isDebugEnabled()) {
						_log.debug(dfe, dfe);
					}
				}

				return new DownloadServletInputStream(
					new FileInputStream(deltaFile), deltaFile.length());
			}
			finally {
				FileUtil.delete(deltaFile);
			}
		}
	}

	protected void processException(
		String zipFileId, String exception, JSONObject errorsJSONObject) {

		JSONObject exceptionJSONObject = JSONFactoryUtil.createJSONObject();

		exceptionJSONObject.put("exception", exception);

		errorsJSONObject.put(zipFileId, exceptionJSONObject);
	}

	protected void sendFile(
			HttpServletRequest request, HttpServletResponse response,
			long userId, long groupId, String uuid)
		throws Exception {

		String version = ParamUtil.getString(request, "version");
		long versionId = ParamUtil.getLong(request, "versionId");

		DownloadServletInputStream downloadServletInputStream =
			getFileDownloadServletInputStream(
				userId, groupId, uuid, version, versionId);

		if (request.getHeader(HttpHeaders.RANGE) != null) {
			ServletResponseUtil.sendFileWithRangeHeader(
				request, response, downloadServletInputStream.getFileName(),
				downloadServletInputStream,
				downloadServletInputStream.getSize(),
				downloadServletInputStream.getMimeType());
		}
		else {
			ServletResponseUtil.write(
				response, downloadServletInputStream,
				downloadServletInputStream.getSize());
		}
	}

	protected void sendImage(
			HttpServletRequest request, HttpServletResponse response,
			long imageId)
		throws Exception {

		User user = _userLocalService.fetchUser(imageId);

		if (user != null) {
			imageId = user.getPortraitId();
		}

		Image image = _imageLocalService.fetchImage(imageId);

		if (image == null) {
			_portal.sendError(
				HttpServletResponse.SC_NOT_FOUND, new NoSuchImageException(),
				request, response);

			return;
		}

		String type = image.getType();

		if (!type.equals(ImageConstants.TYPE_NOT_AVAILABLE)) {
			String contentType = MimeTypesUtil.getExtensionContentType(type);

			response.setContentType(contentType);
		}

		ServletResponseUtil.write(response, image.getTextObj());
	}

	protected void sendPatch(
			HttpServletRequest request, HttpServletResponse response,
			long userId, long groupId, String uuid)
		throws Exception {

		long sourceVersionId = ParamUtil.getLong(request, "sourceVersionId", 0);
		long targetVersionId = ParamUtil.getLong(request, "targetVersionId", 0);

		DownloadServletInputStream downloadServletInputStream =
			getPatchDownloadServletInputStream(
				userId, groupId, uuid, sourceVersionId, targetVersionId);

		ServletResponseUtil.write(
			response, downloadServletInputStream,
			downloadServletInputStream.getSize());
	}

	protected void sendZipFile(
			HttpServletResponse response, long userId,
			JSONArray zipFileIdsJSONArray)
		throws Exception {

		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		JSONObject errorsJSONObject = JSONFactoryUtil.createJSONObject();

		for (int i = 0; i < zipFileIdsJSONArray.length(); i++) {
			JSONObject zipObjectJSONObject = zipFileIdsJSONArray.getJSONObject(
				i);

			long groupId = zipObjectJSONObject.getLong("groupId");
			String zipFileId = zipObjectJSONObject.getString("zipFileId");

			Group group = _groupLocalService.fetchGroup(groupId);

			if ((group == null) || !_syncHelper.isSyncEnabled(group)) {
				processException(
					zipFileId, SyncSiteUnavailableException.class.getName(),
					errorsJSONObject);

				continue;
			}

			try (InputStream inputStream = _getZipFileInputStream(
					zipObjectJSONObject, userId, groupId)) {

				zipWriter.addEntry(zipFileId, inputStream);
			}
			catch (Exception e) {
				Class<?> clazz = e.getClass();

				processException(zipFileId, clazz.getName(), errorsJSONObject);
			}
		}

		zipWriter.addEntry("errors.json", errorsJSONObject.toString());

		File file = zipWriter.getFile();

		ServletResponseUtil.write(
			response, new FileInputStream(file), file.length());
	}

	protected void sendZipFolder(
			HttpServletResponse response, long userId, long repositoryId,
			long folderId)
		throws Exception {

		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		addZipFolderEntry(
			userId, repositoryId, folderId, StringPool.BLANK, zipWriter);

		File file = zipWriter.getFile();

		ServletResponseUtil.write(
			response, new FileInputStream(file), file.length());
	}

	@Reference(unbind = "-")
	protected void setDLAppService(DLAppService dlAppService) {
		_dlAppService = dlAppService;
	}

	@Reference(unbind = "-")
	protected void setDlFileEntryLocalService(
		DLFileEntryLocalService dlFileEntryLocalService) {

		_dlFileEntryLocalService = dlFileEntryLocalService;
	}

	@Reference(unbind = "-")
	protected void setDlFileVersionLocalService(
		DLFileVersionLocalService dlFileVersionLocalService) {

		_dlFileVersionLocalService = dlFileVersionLocalService;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setImageLocalService(ImageLocalService imageLocalService) {
		_imageLocalService = imageLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private InputStream _getZipFileInputStream(
			JSONObject zipObjectJSONObject, long userId, long groupId)
		throws Exception {

		String uuid = zipObjectJSONObject.getString("uuid");

		if (zipObjectJSONObject.getBoolean("patch")) {
			long sourceVersionId = zipObjectJSONObject.getLong(
				"sourceVersionId", 0);
			long targetVersionId = zipObjectJSONObject.getLong(
				"targetVersionId", 0);

			return getPatchDownloadServletInputStream(
				userId, groupId, uuid, sourceVersionId, targetVersionId);
		}

		return getFileDownloadServletInputStream(
			userId, groupId, uuid, zipObjectJSONObject.getString("version"),
			zipObjectJSONObject.getLong("versionId"));
	}

	private static final String _ERROR_HEADER = "Sync-Error";

	private static final Log _log = LogFactoryUtil.getLog(
		SyncDownloadServlet.class);

	private DLAppService _dlAppService;
	private DLFileEntryLocalService _dlFileEntryLocalService;
	private DLFileVersionLocalService _dlFileVersionLocalService;
	private GroupLocalService _groupLocalService;

	@Reference
	private Http _http;

	private ImageLocalService _imageLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private SyncHelper _syncHelper;

	private UserLocalService _userLocalService;

}