/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.servlet;

import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectEntryFolderService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactory;
import com.liferay.portal.util.PropsValues;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(
	property = {
		"osgi.http.whiteboard.servlet.name=com.liferay.site.cms.site.initializer.internal.servlet.DownloadObjectEntryFolderServlet",
		"osgi.http.whiteboard.servlet.pattern=/cms/download-folder/*",
		"servlet.init.httpMethods=GET"
	},
	service = Servlet.class
)
public class DownloadObjectEntryFolderServlet extends HttpServlet {

	@Override
	public void service(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		_createContext(httpServletRequest, httpServletResponse);

		super.service(httpServletRequest, httpServletResponse);
	}

	@Override
	protected void doGet(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		try {
			_downloadObjectEntryFolder(httpServletRequest, httpServletResponse);
		}
		catch (PortalException portalException) {
			throw new ServletException(portalException);
		}
	}

	private void _createContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		try {
			EventsProcessorUtil.process(
				PropsKeys.SERVLET_SERVICE_EVENTS_PRE,
				PropsValues.SERVLET_SERVICE_EVENTS_PRE, httpServletRequest,
				httpServletResponse);
		}
		catch (ActionException actionException) {
			if (_log.isDebugEnabled()) {
				_log.debug(actionException);
			}
		}
	}

	private void _downloadObjectEntryFolder(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, PortalException {

		String[] pathArray = StringUtil.split(
			_getObjectEntryFolderPath(httpServletRequest), StringPool.SLASH);

		long classNameId = _portal.getClassNameId(ObjectEntryFolder.class);

		if (classNameId != GetterUtil.getLong(pathArray[0])) {
			throw new RuntimeException();
		}

		long objectEntryFolderId = GetterUtil.getLong(pathArray[1]);

		ZipWriter zipWriter = _zipWriterFactory.getZipWriter();

		try {
			ObjectEntryFolder objectEntryFolder =
				_objectEntryFolderService.getObjectEntryFolder(
					objectEntryFolderId);

			String zipFileName = objectEntryFolder.getName() + ".zip";

			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			_zipObjectEntryFolder(
				objectEntryFolder.getGroupId(), objectEntryFolderId,
				objectEntryFolder.getName(), themeDisplay, zipWriter);

			try (InputStream inputStream = new FileInputStream(
					zipWriter.getFile())) {

				httpServletResponse.addHeader(
					HttpHeaders.CACHE_CONTROL,
					HttpHeaders.CACHE_CONTROL_NO_CACHE_VALUE);

				ServletResponseUtil.sendFile(
					httpServletRequest, httpServletResponse, zipFileName,
					inputStream, 0, ContentTypes.APPLICATION_ZIP,
					HttpHeaders.CONTENT_DISPOSITION_ATTACHMENT);
			}
		}
		finally {
			File file = zipWriter.getFile();

			file.delete();
		}
	}

	private String _getObjectEntryFolderPath(
		HttpServletRequest httpServletRequest) {

		String requestURI = httpServletRequest.getRequestURI();

		String path =
			httpServletRequest.getContextPath() +
				httpServletRequest.getServletPath();

		return requestURI.substring(path.length() + 1);
	}

	private void _zipObjectEntry(
			ObjectEntry objectEntry, String path,
			PermissionChecker permissionChecker, ZipWriter zipWriter)
		throws IOException, PortalException {

		Map<String, Serializable> values = objectEntry.getValues();

		List<ObjectField> objectFields =
			_objectFieldLocalService.getObjectFields(
				objectEntry.getObjectDefinitionId());

		String objectFieldName = StringPool.SLASH;

		for (ObjectField objectField : objectFields) {
			if (objectField.compareBusinessType(
					ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT)) {

				objectFieldName = objectField.getName();
			}
		}

		Serializable serializable = values.get(objectFieldName);

		long fileEntryId = GetterUtil.getLong(serializable);

		if (fileEntryId == 0) {
			return;
		}

		FileEntry fileEntry = _dlAppLocalService.getFileEntry(fileEntryId);

		if (fileEntry.containsPermission(
				permissionChecker, ActionKeys.DOWNLOAD)) {

			zipWriter.addEntry(
				path + StringPool.SLASH + fileEntry.getFileName(),
				fileEntry.getContentStream());
		}
	}

	private void _zipObjectEntryFolder(
			long groupId, long objectEntryFolderId, String path,
			ThemeDisplay themeDisplay, ZipWriter zipWriter)
		throws IOException, PortalException {

		List<ObjectEntry> objectEntryFolderObjectEntries =
			_objectEntryLocalService.getObjectEntryFolderObjectEntries(
				groupId, objectEntryFolderId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		for (ObjectEntry objectEntry : objectEntryFolderObjectEntries) {
			_zipObjectEntry(
				objectEntry, path, themeDisplay.getPermissionChecker(),
				zipWriter);
		}

		List<ObjectEntryFolder> objectEntryFolders =
			_objectEntryFolderService.getObjectEntryFolders(
				groupId, themeDisplay.getCompanyId(), objectEntryFolderId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (ObjectEntryFolder objectEntryFolder : objectEntryFolders) {
			_zipObjectEntryFolder(
				groupId, objectEntryFolder.getObjectEntryFolderId(),
				StringBundler.concat(
					path, StringPool.SLASH, objectEntryFolder.getName()),
				themeDisplay, zipWriter);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DownloadObjectEntryFolderServlet.class);

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private ObjectEntryFolderService _objectEntryFolderService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private ZipWriterFactory _zipWriterFactory;

}