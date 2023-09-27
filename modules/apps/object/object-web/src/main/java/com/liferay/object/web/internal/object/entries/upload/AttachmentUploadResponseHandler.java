/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.object.entries.upload;

import com.liferay.document.library.kernel.exception.FileExtensionException;
import com.liferay.document.library.kernel.exception.FileSizeException;
import com.liferay.document.library.kernel.exception.InvalidFileException;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.object.web.internal.object.entries.upload.util.AttachmentValidator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.upload.UploadResponseHandler;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carolina Barbosa
 */
@Component(service = AttachmentUploadResponseHandler.class)
public class AttachmentUploadResponseHandler implements UploadResponseHandler {

	@Override
	public JSONObject onFailure(
			PortletRequest portletRequest, PortalException portalException)
		throws PortalException {

		JSONObject jsonObject = _defaultUploadResponseHandler.onFailure(
			portletRequest, portalException);

		String errorMessage = StringPool.BLANK;

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (portalException instanceof FileExtensionException) {
			errorMessage = themeDisplay.translate(
				"please-enter-a-file-with-a-valid-extension-x",
				StringUtil.merge(
					_attachmentValidator.getAcceptedFileExtensions(
						ParamUtil.getLong(portletRequest, "objectFieldId")),
					StringPool.COMMA_AND_SPACE));
		}
		else if (portalException instanceof FileSizeException) {
			errorMessage = themeDisplay.translate(
				"please-enter-a-file-with-a-valid-file-size-no-larger-than-x",
				_language.formatStorageSize(
					_attachmentValidator.getMaximumFileSize(
						ParamUtil.getLong(portletRequest, "objectFieldId"),
						themeDisplay.isSignedIn()),
					themeDisplay.getLocale()));
		}
		else if (portalException instanceof InvalidFileException) {
			errorMessage = themeDisplay.translate("please-enter-a-valid-file");
		}
		else {
			errorMessage = themeDisplay.translate(
				"an-unexpected-error-occurred-while-uploading-your-file");
		}

		return jsonObject.put("error", JSONUtil.put("message", errorMessage));
	}

	@Override
	public JSONObject onSuccess(
			UploadPortletRequest uploadPortletRequest, FileEntry fileEntry)
		throws PortalException {

		return JSONUtil.put(
			"file",
			JSONUtil.put(
				"contentURL",
				_dlURLHelper.getPreviewURL(
					fileEntry, fileEntry.getFileVersion(), null, "")
			).put(
				"fileEntryId", fileEntry.getFileEntryId()
			).put(
				"title",
				TempFileEntryUtil.getOriginalTempFileName(
					fileEntry.getFileName())
			)
		).put(
			"success", Boolean.TRUE
		);
	}

	@Reference
	private AttachmentValidator _attachmentValidator;

	@Reference(target = "(upload.response.handler.system.default=true)")
	private UploadResponseHandler _defaultUploadResponseHandler;

	@Reference
	private DLURLHelper _dlURLHelper;

	@Reference
	private Language _language;

}