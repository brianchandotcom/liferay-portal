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

import com.liferay.document.library.configuration.DLConfiguration;
import com.liferay.document.library.kernel.antivirus.AntivirusScannerException;
import com.liferay.document.library.kernel.exception.DuplicateFileEntryException;
import com.liferay.document.library.kernel.exception.FileExtensionException;
import com.liferay.document.library.kernel.exception.FileNameException;
import com.liferay.document.library.kernel.exception.FileSizeException;
import com.liferay.document.library.kernel.util.DLValidator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.ServletResponseConstants;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.upload.UploadRequestSizeException;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.upload.UploadResponseHandler;

import java.util.Map;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	configurationPid = "com.liferay.document.library.configuration.DLConfiguration",
	property = "upload.response.handler=multiple",
	service = UploadResponseHandler.class
)
public class MultipleUploadResponseHandler implements UploadResponseHandler {

	@Override
	public JSONObject onFailure(
			PortletRequest portletRequest, PortalException pe)
		throws PortalException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (pe instanceof AntivirusScannerException ||
			pe instanceof DuplicateFileEntryException ||
			pe instanceof FileExtensionException ||
			pe instanceof FileNameException ||
			pe instanceof FileSizeException ||
			pe instanceof UploadRequestSizeException) {

			String errorMessage = StringPool.BLANK;
			int errorType = 0;

			ThemeDisplay themeDisplay =
				(ThemeDisplay)portletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			if (pe instanceof AntivirusScannerException) {
				AntivirusScannerException ase = (AntivirusScannerException)pe;

				errorMessage = themeDisplay.translate(ase.getMessageKey());

				errorType =
					ServletResponseConstants.SC_FILE_ANTIVIRUS_EXCEPTION;
			}

			if (pe instanceof DuplicateFileEntryException) {
				errorMessage = themeDisplay.translate(
					"please-enter-a-unique-document-name");
				errorType =
					ServletResponseConstants.SC_DUPLICATE_FILE_EXCEPTION;
			}
			else if (pe instanceof FileExtensionException) {
				errorMessage = themeDisplay.translate(
					"please-enter-a-file-with-a-valid-extension-x",
					_getAllowedFileExtensions());
				errorType =
					ServletResponseConstants.SC_FILE_EXTENSION_EXCEPTION;
			}
			else if (pe instanceof FileNameException) {
				errorMessage = themeDisplay.translate(
					"please-enter-a-file-with-a-valid-file-name");
			}
			else if (pe instanceof FileSizeException) {
				errorMessage = themeDisplay.translate(
					"please-enter-a-file-with-a-valid-file-size-no-larger-" +
						"than-x",
					TextFormatter.formatStorageSize(
						_dlValidator.getMaxAllowableSize(),
						themeDisplay.getLocale()));
			}
			else if (pe instanceof UploadRequestSizeException) {
				errorType =
					ServletResponseConstants.SC_UPLOAD_REQUEST_SIZE_EXCEPTION;
			}

			jsonObject.put(
				"message", errorMessage
			).put(
				"status", errorType
			);
		}

		return jsonObject;
	}

	@Override
	public JSONObject onSuccess(
			UploadPortletRequest uploadPortletRequest, FileEntry fileEntry)
		throws PortalException {

		String sourceFileName = uploadPortletRequest.getFileName("file");

		return JSONUtil.put(
			"groupId", fileEntry.getGroupId()
		).put(
			"name", fileEntry.getTitle()
		).put(
			"title", sourceFileName
		).put(
			"uuid", fileEntry.getUuid()
		);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_dlConfiguration = ConfigurableUtil.createConfigurable(
			DLConfiguration.class, properties);
	}

	private String _getAllowedFileExtensions() {
		String allowedFileExtensionsString = StringPool.BLANK;

		String[] allowedFileExtensions = _dlConfiguration.fileExtensions();

		allowedFileExtensionsString = StringUtil.merge(
			allowedFileExtensions, StringPool.COMMA_AND_SPACE);

		return allowedFileExtensionsString;
	}

	private volatile DLConfiguration _dlConfiguration;

	@Reference
	private DLValidator _dlValidator;

}