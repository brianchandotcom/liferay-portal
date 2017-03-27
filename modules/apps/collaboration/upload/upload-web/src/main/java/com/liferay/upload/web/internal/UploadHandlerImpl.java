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

package com.liferay.upload.web.internal;

import com.liferay.document.library.kernel.antivirus.AntivirusScannerException;
import com.liferay.document.library.kernel.exception.FileNameException;
import com.liferay.document.library.kernel.exception.FileSizeException;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.editor.EditorConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.ServletResponseConstants;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.LiferayFileItemException;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.upload.UploadRequestSizeException;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.upload.UploadFileEntryHandler;
import com.liferay.upload.UploadFileEntryResponseCustomizer;
import com.liferay.upload.UploadHandler;

import java.io.IOException;
import java.io.InputStream;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Sergio González
 * @author Adolfo Pérez
 * @author Roberto Díaz
 * @author Alejandro Tardín
 */
@Component
public class UploadHandlerImpl implements UploadHandler {

	@Override
	public void upload(
			UploadFileEntryHandler uploadFileEntryHandler,
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws PortalException {

		UploadPortletRequest uploadPortletRequest =
			PortalUtil.getUploadPortletRequest(portletRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		uploadFileEntryHandler.checkPermission(
			themeDisplay.getScopeGroupId(),
			uploadFileEntryHandler.getFolderId(uploadPortletRequest),
			themeDisplay.getPermissionChecker());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			UploadException uploadException =
				(UploadException)portletRequest.getAttribute(
					WebKeys.UPLOAD_EXCEPTION);

			if (uploadException != null) {
				Throwable cause = uploadException.getCause();

				if (uploadException.isExceededFileSizeLimit()) {
					throw new FileSizeException(cause);
				}

				if (uploadException.isExceededLiferayFileItemSizeLimit()) {
					throw new LiferayFileItemException(cause);
				}

				if (uploadException.isExceededUploadRequestSizeLimit()) {
					throw new UploadRequestSizeException(cause);
				}

				throw new PortalException(cause);
			}

			JSONObject imageJSONObject = _getImageJSONObject(
				uploadFileEntryHandler, portletRequest);

			String randomId = ParamUtil.getString(
				uploadPortletRequest, "randomId");

			imageJSONObject.put("randomId", randomId);

			jsonObject.put("file", imageJSONObject);

			jsonObject.put("success", Boolean.TRUE);

			JSONPortletResponseUtil.writeJSON(
				portletRequest, portletResponse, jsonObject);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
		catch (PortalException pe) {
			_handleUploadException(
				uploadFileEntryHandler, portletRequest, portletResponse, pe,
				jsonObject);
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, UploadFileEntryResponseCustomizer.class, "source");
	}

	private void _customizeFileJSONObject(
			UploadFileEntryHandler fileEntryHandler,
			PortletRequest portletRequest, FileEntry fileEntry,
			JSONObject jsonObject)
		throws IOException {

		fileEntryHandler.customizeFileJSONObject(jsonObject);

		String source = ParamUtil.getString(
			portletRequest, "source", StringPool.BLANK);

		UploadFileEntryResponseCustomizer uploadFileEntryResponseCustomizer =
			_serviceTrackerMap.getService(source);

		if (uploadFileEntryResponseCustomizer != null) {
			uploadFileEntryResponseCustomizer.customize(
				fileEntry, jsonObject, portletRequest);
		}
	}

	private JSONObject _getImageJSONObject(
			UploadFileEntryHandler fileEntryHandler,
			PortletRequest portletRequest)
		throws PortalException {

		UploadPortletRequest uploadPortletRequest =
			PortalUtil.getUploadPortletRequest(portletRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONObject imageJSONObject = JSONFactoryUtil.createJSONObject();

		InputStream inputStream = null;

		try {
			imageJSONObject.put(
				"attributeDataImageId",
				EditorConstants.ATTRIBUTE_DATA_IMAGE_ID);

			String parameterName = fileEntryHandler.getParameterName();

			String fileName = uploadPortletRequest.getFileName(parameterName);
			String contentType = uploadPortletRequest.getContentType(
				parameterName);
			long size = uploadPortletRequest.getSize(parameterName);

			fileEntryHandler.validateFile(fileName, contentType, size);

			long folderId = fileEntryHandler.getFolderId(uploadPortletRequest);

			String uniqueFileName = _getUniqueFileName(
				fileEntryHandler, themeDisplay, fileName, folderId);

			inputStream = uploadPortletRequest.getFileAsStream(parameterName);

			FileEntry fileEntry = fileEntryHandler.addFileEntry(
				themeDisplay.getUserId(), themeDisplay.getScopeGroupId(),
				folderId, uniqueFileName, contentType, inputStream, size,
				fileEntryHandler.getServiceContext(uploadPortletRequest));

			imageJSONObject.put("fileEntryId", fileEntry.getFileEntryId());
			imageJSONObject.put("groupId", fileEntry.getGroupId());
			imageJSONObject.put("title", fileEntry.getTitle());

			imageJSONObject.put("type", "document");
			imageJSONObject.put(
				"url", fileEntryHandler.getURL(fileEntry, themeDisplay));
			imageJSONObject.put("uuid", fileEntry.getUuid());

			_customizeFileJSONObject(
				fileEntryHandler, portletRequest, fileEntry, imageJSONObject);

			return imageJSONObject;
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}
	}

	private String _getUniqueFileName(
			UploadFileEntryHandler fileEntryHandler, ThemeDisplay themeDisplay,
			String fileName, long folderId)
		throws PortalException {

		FileEntry fileEntry = fileEntryHandler.fetchFileEntry(
			themeDisplay.getUserId(), themeDisplay.getScopeGroupId(), folderId,
			fileName);

		if (fileEntry == null) {
			return fileName;
		}

		int suffix = 1;

		for (int i = 0; i < _UNIQUE_FILE_NAME_TRIES; i++) {
			String curFileName = FileUtil.appendParentheticalSuffix(
				fileName, String.valueOf(suffix));

			fileEntry = fileEntryHandler.fetchFileEntry(
				themeDisplay.getUserId(), themeDisplay.getScopeGroupId(),
				folderId, curFileName);

			if (fileEntry == null) {
				return curFileName;
			}

			suffix++;
		}

		throw new PortalException(
			"Unable to get a unique file name for " + fileName);
	}

	private void _handleUploadException(
			UploadFileEntryHandler fileEntryHandler,
			PortletRequest portletRequest, PortletResponse portletResponse,
			PortalException pe, JSONObject jsonObject)
		throws PortalException {

		jsonObject.put("success", Boolean.FALSE);

		if (pe instanceof AntivirusScannerException ||
			pe instanceof FileNameException ||
			pe instanceof FileSizeException ||
			pe instanceof UploadRequestSizeException) {

			String errorMessage = StringPool.BLANK;
			int errorType = 0;

			ThemeDisplay themeDisplay =
				(ThemeDisplay)portletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			if (pe instanceof AntivirusScannerException) {
				errorType =
					ServletResponseConstants.SC_FILE_ANTIVIRUS_EXCEPTION;
				AntivirusScannerException ase = (AntivirusScannerException)pe;

				errorMessage = themeDisplay.translate(ase.getMessageKey());
			}
			else if (pe instanceof FileNameException) {
				errorType = ServletResponseConstants.SC_FILE_NAME_EXCEPTION;
			}
			else if (pe instanceof FileSizeException) {
				errorType = ServletResponseConstants.SC_FILE_SIZE_EXCEPTION;
			}
			else if (pe instanceof UploadRequestSizeException) {
				errorType =
					ServletResponseConstants.SC_UPLOAD_REQUEST_SIZE_EXCEPTION;
			}

			JSONObject errorJSONObject = JSONFactoryUtil.createJSONObject();

			errorJSONObject.put("errorType", errorType);
			errorJSONObject.put("message", errorMessage);

			jsonObject.put("error", errorJSONObject);
		}
		else {
			fileEntryHandler.doHandleUploadException(
				portletRequest, portletResponse, pe, jsonObject);
		}

		try {
			JSONPortletResponseUtil.writeJSON(
				portletRequest, portletResponse, jsonObject);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	private static final int _UNIQUE_FILE_NAME_TRIES = 50;

	private ServiceTrackerMap<String, UploadFileEntryResponseCustomizer>
		_serviceTrackerMap;

}