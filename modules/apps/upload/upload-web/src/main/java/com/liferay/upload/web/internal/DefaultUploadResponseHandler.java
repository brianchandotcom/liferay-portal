/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.upload.web.internal;

import com.liferay.document.library.configuration.DLConfiguration;
import com.liferay.document.library.kernel.antivirus.AntivirusScannerException;
import com.liferay.document.library.kernel.exception.FileExtensionException;
import com.liferay.document.library.kernel.exception.FileNameException;
import com.liferay.document.library.kernel.exception.FileSizeException;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.editor.constants.EditorConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.ServletResponseConstants;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.upload.UploadRequestSizeException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.upload.UploadResponseHandler;

import java.util.Map;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Alejandro Tardín
 */
@Component(
	configurationPid = "com.liferay.document.library.configuration.DLConfiguration",
	property = "upload.response.handler.system.default=true",
	service = UploadResponseHandler.class
)
public class DefaultUploadResponseHandler implements UploadResponseHandler {

	@Override
	public JSONObject onFailure(
			PortletRequest portletRequest, PortalException portalException)
		throws PortalException {

		return JSONUtil.put(
			"error",
			() -> {
				if (!(portalException instanceof AntivirusScannerException) &&
					!(portalException instanceof FileExtensionException) &&
					!(portalException instanceof FileNameException) &&
					!(portalException instanceof FileSizeException) &&
					!(portalException instanceof UploadRequestSizeException)) {

					return null;
				}

				String errorMessage = StringPool.BLANK;
				int errorType = 0;

				if (portalException instanceof AntivirusScannerException) {
					errorType =
						ServletResponseConstants.SC_FILE_ANTIVIRUS_EXCEPTION;
					AntivirusScannerException antivirusScannerException =
						(AntivirusScannerException)portalException;

					ThemeDisplay themeDisplay =
						(ThemeDisplay)portletRequest.getAttribute(
							WebKeys.THEME_DISPLAY);

					errorMessage = themeDisplay.translate(
						antivirusScannerException.getMessageKey());
				}
				else if (portalException instanceof FileExtensionException) {
					errorType =
						ServletResponseConstants.SC_FILE_EXTENSION_EXCEPTION;

					errorMessage = _getAllowedFileExtensions();
				}
				else if (portalException instanceof FileNameException) {
					errorType = ServletResponseConstants.SC_FILE_NAME_EXCEPTION;
				}
				else if (portalException instanceof FileSizeException) {
					errorType = ServletResponseConstants.SC_FILE_SIZE_EXCEPTION;
				}
				else if (portalException instanceof
							UploadRequestSizeException) {

					errorType =
						ServletResponseConstants.
							SC_UPLOAD_REQUEST_SIZE_EXCEPTION;
				}

				return JSONUtil.put(
					"errorType", errorType
				).put(
					"message", errorMessage
				);
			}
		).put(
			"success", Boolean.FALSE
		);
	}

	@Override
	public JSONObject onSuccess(
			UploadPortletRequest uploadPortletRequest, FileEntry fileEntry)
		throws PortalException {

		return JSONUtil.put(
			"file",
			JSONUtil.put(
				"attributeDataImageId", EditorConstants.ATTRIBUTE_DATA_IMAGE_ID
			).put(
				"fileEntryId", fileEntry.getFileEntryId()
			).put(
				"groupId", fileEntry.getGroupId()
			).put(
				"mimeType", fileEntry.getMimeType()
			).put(
				"randomId",
				ParamUtil.getString(uploadPortletRequest, "randomId")
			).put(
				"title", fileEntry.getTitle()
			).put(
				"type", "document"
			).put(
				"url",
				() -> {
					ThemeDisplay themeDisplay =
						(ThemeDisplay)uploadPortletRequest.getAttribute(
							WebKeys.THEME_DISPLAY);

					return PortletFileRepositoryUtil.getPortletFileEntryURL(
						themeDisplay, fileEntry, StringPool.BLANK);
				}
			).put(
				"uuid", fileEntry.getUuid()
			)
		).put(
			"success", Boolean.TRUE
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

}