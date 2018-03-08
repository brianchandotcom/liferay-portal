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

package com.liferay.fragment.web.internal.portlet.action;

import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.fragment.web.internal.constatns.ExportImportConstants;
import com.liferay.fragment.web.internal.portlet.util.ImportUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipReaderFactoryUtil;
import com.liferay.upload.UploadResponseHandler;

import java.io.InputStream;

import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + FragmentPortletKeys.FRAGMENT,
		"mvc.command.name=/fragment/import_fragment_entries"
	},
	service = MVCActionCommand.class
)
public class ImportFragmentEntriesMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (Objects.equals(cmd, Constants.ADD_TEMP)) {
			_addTempFile(actionRequest, actionResponse);

			hideDefaultSuccessMessage(actionRequest);
		}
		else if (Objects.equals(cmd, Constants.IMPORT)) {
			_importFragmentEntries(actionRequest, actionResponse);
		}
	}

	private void _addTempFile(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			_portal.getUploadPortletRequest(actionRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String sourceFileName = uploadPortletRequest.getFileName("file");

		try (InputStream inputStream =
				uploadPortletRequest.getFileAsStream("file")) {

			String tempFileName = TempFileEntryUtil.getTempFileName(
				sourceFileName);

			String mimeType = uploadPortletRequest.getContentType("file");

			FileEntry fileEntry = TempFileEntryUtil.addTempFileEntry(
				themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
				ExportImportConstants.FRAGMENT_ENTRY_TEMP_FOLDER_NAME,
				tempFileName, inputStream, mimeType);

			JSONObject jsonObject = _multipleUploadResponseHandler.onSuccess(
				uploadPortletRequest, fileEntry);

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse, jsonObject);
		}
	}

	private void _importFragmentEntries(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			_portal.getUploadPortletRequest(actionRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long fragmentCollectionId = ParamUtil.getLong(
			uploadPortletRequest, "fragmentCollectionId");

		boolean overwrite = ParamUtil.getBoolean(
			actionRequest, "overwrite", true);

		try {
			String[] selectedFileNames = ParamUtil.getParameterValues(
				actionRequest, "selectedFileName", new String[0], false);

			for (String selectedFileName : selectedFileNames) {
				FileEntry tempFileEntry = TempFileEntryUtil.getTempFileEntry(
					themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
					ExportImportConstants.FRAGMENT_ENTRY_TEMP_FOLDER_NAME,
					selectedFileName);

				ZipReader zipReader = ZipReaderFactoryUtil.getZipReader(
					tempFileEntry.getContentStream());

				_importUtil.importFragmentEntries(
					actionRequest, zipReader, fragmentCollectionId,
					StringPool.BLANK, overwrite);

				TempFileEntryUtil.deleteTempFileEntry(
					tempFileEntry.getFileEntryId());
			}

			String portletResource = ParamUtil.getString(
				actionRequest, "portletResource");

			SessionMessages.add(
				actionRequest, portletResource + "filesImported");

			addSuccessMessage(actionRequest, actionResponse);
		}
		catch (Exception e) {
			SessionErrors.add(actionRequest, e.getClass(), e);
		}

		sendRedirect(actionRequest, actionResponse);
	}

	@Reference
	private ImportUtil _importUtil;

	@Reference(target = "(upload.response.handler=multiple)")
	private UploadResponseHandler _multipleUploadResponseHandler;

	@Reference
	private Portal _portal;

}