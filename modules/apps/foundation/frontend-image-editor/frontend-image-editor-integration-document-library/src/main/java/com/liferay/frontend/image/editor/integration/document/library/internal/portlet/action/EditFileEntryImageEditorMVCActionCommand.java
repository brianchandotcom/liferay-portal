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

package com.liferay.frontend.image.editor.integration.document.library.internal.portlet.action;

import com.liferay.document.library.kernel.exception.FileSizeException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.web.constants.DLPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.LiferayFileItemException;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.upload.UploadRequestSizeException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.InputStream;

import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ambrin Chaudhary
 */
@Component(
	property = {
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
		"javax.portlet.name=" + DLPortletKeys.MEDIA_GALLERY_DISPLAY,
		"mvc.command.name=/document_library/edit_file_entry_with_image_editor"
	},
	service = MVCActionCommand.class
)
public class EditFileEntryImageEditorMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			UploadException uploadException =
				(UploadException)actionRequest.getAttribute(
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

			updateFileEntry(actionRequest, actionResponse);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
		catch (PortalException pe) {
			handleUploadException(actionRequest, actionResponse);
		}
	}

	protected void handleUploadException(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws PortalException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("success", Boolean.FALSE);

		try {
			JSONPortletResponseUtil.writeJSON(
				portletRequest, portletResponse, jsonObject);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	protected FileEntry updateFileEntry(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		UploadPortletRequest uploadPortletRequest =
			PortalUtil.getUploadPortletRequest(actionRequest);

		long fileEntryId = ParamUtil.getLong(
			uploadPortletRequest, "fileEntryId");

		String sourceFileName = uploadPortletRequest.getFileName(
			"imageEditorFileName");

		FileEntry fileEntry = _dlAppLocalService.getFileEntry(fileEntryId);

		InputStream inputStream = uploadPortletRequest.getFileAsStream(
			"imageEditorFileName");
		String contentType = uploadPortletRequest.getContentType(
			"imageEditorFileName");
		long size = uploadPortletRequest.getSize("imageEditorFileName");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DLFileEntry.class.getName(), uploadPortletRequest);

		fileEntry = _dlAppService.updateFileEntry(
			fileEntryId, sourceFileName, contentType, fileEntry.getTitle(),
			fileEntry.getDescription(), StringPool.BLANK, false, inputStream,
			size, serviceContext);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("success", Boolean.TRUE);

		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(
				LanguageUtil.getLanguageId(themeDisplay.getLocale()));

		SessionMessages.add(
			actionRequest, "requestProcessed",
			LanguageUtil.get(
				resourceBundle, "the-image-was-edited-successfully"));

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);

		return fileEntry;
	}

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private DLAppService _dlAppService;

	@Reference(
		target = "(bundle.symbolic.name=com.liferay.frontend.image.editor.integration.document.library)",
		unbind = "-"
	)
	private ResourceBundleLoader _resourceBundleLoader;

}