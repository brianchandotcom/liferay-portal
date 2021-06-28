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

package com.liferay.style.book.web.internal.portlet.action;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.style.book.constants.StyleBookPortletKeys;
import com.liferay.style.book.zip.processor.StyleBookEntryZipProcessor;
import com.liferay.style.book.zip.processor.StyleBookEntryZipProcessorImportResultEntry;

import java.io.File;

import java.util.List;

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
		"javax.portlet.name=" + StyleBookPortletKeys.STYLE_BOOK,
		"mvc.command.name=/style_book/import_style_book_entries"
	},
	service = MVCActionCommand.class
)
public class ImportStyleBookEntriesMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void addSuccessMessage(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		String successMessage = LanguageUtil.get(
			_portal.getHttpServletRequest(actionRequest),
			"the-files-were-imported-correctly");

		SessionMessages.add(actionRequest, "requestProcessed", successMessage);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		UploadPortletRequest uploadPortletRequest =
			_portal.getUploadPortletRequest(actionRequest);

		File file = uploadPortletRequest.getFile("file");

		boolean overwrite = ParamUtil.getBoolean(
			actionRequest, "overwrite", true);

		try {
			List<StyleBookEntryZipProcessorImportResultEntry>
				styleBookEntryZipProcessorImportResultEntries =
					_importStyleBookEntries(
						themeDisplay.getUserId(),
						themeDisplay.getScopeGroupId(), file, overwrite);

			if (ListUtil.isNotEmpty(
					styleBookEntryZipProcessorImportResultEntries)) {

				SessionMessages.add(
					actionRequest,
					"styleBookEntryZipProcessorImportResultEntries",
					styleBookEntryZipProcessorImportResultEntries);
			}

			SessionMessages.add(actionRequest, "success");
		}
		catch (Exception exception) {
			SessionErrors.add(actionRequest, exception.getClass(), exception);
		}

		sendRedirect(actionRequest, actionResponse);
	}

	private List<StyleBookEntryZipProcessorImportResultEntry>
			_importStyleBookEntries(
				long userId, long groupId, File file, boolean overwrite)
		throws Exception {

		return _styleBookEntryZipProcessor.importStyleBookEntries(
			userId, groupId, file, overwrite);
	}

	@Reference
	private Portal _portal;

	@Reference
	private StyleBookEntryZipProcessor _styleBookEntryZipProcessor;

}