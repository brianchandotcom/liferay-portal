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
import com.liferay.document.library.exception.DLStorageQuotaExceededException;
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
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.ServletResponseConstants;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.upload.UploadRequestSizeException;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;
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
			PortletRequest portletRequest, PortalException portalException)
		throws PortalException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (portalException instanceof AntivirusScannerException ||
			portalException instanceof DLStorageQuotaExceededException ||
			portalException instanceof DuplicateFileEntryException ||
			portalException instanceof FileExtensionException ||
			portalException instanceof FileNameException ||
			portalException instanceof FileSizeException ||
			portalException instanceof UploadRequestSizeException) {

			String errorMessage = StringPool.BLANK;
			int errorType = 0;

			ThemeDisplay themeDisplay =
				(ThemeDisplay)portletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			if (portalException instanceof AntivirusScannerException) {
				AntivirusScannerException antivirusScannerException =
					(AntivirusScannerException)portalException;

				errorMessage = themeDisplay.translate(
					antivirusScannerException.getMessageKey());

				errorType =
					ServletResponseConstants.SC_FILE_ANTIVIRUS_EXCEPTION;
			}

			if (portalException instanceof DLStorageQuotaExceededException) {
				errorMessage = themeDisplay.translate(
					"you-have-exceeded-the-x-storage-quota-for-this-instance",
					_language.formatStorageSize(
						PropsValues.DATA_LIMIT_DL_STORAGE_MAX_SIZE,
						themeDisplay.getLocale()));
				errorType = ServletResponseConstants.SC_FILE_SIZE_EXCEPTION;
			}
			else if (portalException instanceof DuplicateFileEntryException) {
				errorMessage = themeDisplay.translate(
					"please-enter-a-unique-document-name");
				errorType =
					ServletResponseConstants.SC_DUPLICATE_FILE_EXCEPTION;
			}
			else if (portalException instanceof FileExtensionException) {
				errorMessage = themeDisplay.translate(
					"please-enter-a-file-with-a-valid-extension-x",
					_getAllowedFileExtensions());
				errorType =
					ServletResponseConstants.SC_FILE_EXTENSION_EXCEPTION;
			}
			else if (portalException instanceof FileNameException) {
				errorMessage = themeDisplay.translate(
					"please-enter-a-file-with-a-valid-file-name");
			}
			else if (portalException instanceof FileSizeException) {
				FileSizeException fileSizeException =
					(FileSizeException)portalException;

				errorMessage = themeDisplay.translate(
					"please-enter-a-file-with-a-valid-file-size-no-larger-" +
						"than-x",
					_language.formatStorageSize(
						fileSizeException.getMaxSize(),
						themeDisplay.getLocale()));
			}
			else if (portalException instanceof UploadRequestSizeException) {
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

		return JSONUtil.put(
			"groupId", fileEntry.getGroupId()
		).put(
			"name", fileEntry.getTitle()
		).put(
			"title", uploadPortletRequest.getFileName("file")
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
		String[] allowedFileExtensions = _dlConfiguration.fileExtensions();

		return StringUtil.merge(
			allowedFileExtensions, StringPool.COMMA_AND_SPACE);
	}

	private volatile DLConfiguration _dlConfiguration;

	@Reference
	private DLValidator _dlValidator;

	@Reference
	private Language _language;

}