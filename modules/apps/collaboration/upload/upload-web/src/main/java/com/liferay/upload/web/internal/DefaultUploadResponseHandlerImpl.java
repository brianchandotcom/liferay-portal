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
import com.liferay.portal.kernel.editor.EditorConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.ServletResponseConstants;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadRequestSizeException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.upload.DefaultUploadResponseHandler;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component
public class DefaultUploadResponseHandlerImpl
	implements DefaultUploadResponseHandler {

	@Override
	public JSONObject onFailure(
			PortletRequest portletRequest, PortalException pe)
		throws PortalException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

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

		return jsonObject;
	}

	@Override
	public JSONObject onSuccess(
			PortletRequest portletRequest, FileEntry fileEntry)
		throws PortalException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		JSONObject imageJSONObject = JSONFactoryUtil.createJSONObject();

		String randomId = ParamUtil.getString(portletRequest, "randomId");

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String url = PortletFileRepositoryUtil.getPortletFileEntryURL(
			themeDisplay, fileEntry, StringPool.BLANK);

		imageJSONObject.put(
			"attributeDataImageId", EditorConstants.ATTRIBUTE_DATA_IMAGE_ID);
		imageJSONObject.put("fileEntryId", fileEntry.getFileEntryId());
		imageJSONObject.put("groupId", fileEntry.getGroupId());
		imageJSONObject.put("randomId", randomId);
		imageJSONObject.put("title", fileEntry.getTitle());
		imageJSONObject.put("type", "document");
		imageJSONObject.put("url", url);
		imageJSONObject.put("uuid", fileEntry.getUuid());

		jsonObject.put("file", imageJSONObject);

		jsonObject.put("success", Boolean.TRUE);

		return jsonObject;
	}

}