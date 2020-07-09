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

import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.style.book.constants.StyleBookPortletKeys;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryService;

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
		"mvc.command.name=/style_book/update_style_book_entry_preview"
	},
	service = MVCActionCommand.class
)
public class UpdateStyleBookEntryPreviewMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long styleBookEntryId = ParamUtil.getLong(
			actionRequest, "styleBookEntryId");

		long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");

		FileEntry fileEntry = _dlAppLocalService.getFileEntry(fileEntryId);

		FileEntry tempFileEntry = fileEntry;

		Repository repository =
			PortletFileRepositoryUtil.fetchPortletRepository(
				themeDisplay.getScopeGroupId(),
				StyleBookPortletKeys.STYLE_BOOK);

		if (repository == null) {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAddGroupPermissions(true);
			serviceContext.setAddGuestPermissions(true);

			repository = PortletFileRepositoryUtil.addPortletRepository(
				themeDisplay.getScopeGroupId(), StyleBookPortletKeys.STYLE_BOOK,
				serviceContext);
		}

		String fileName =
			styleBookEntryId + "_preview." + fileEntry.getExtension();

		FileEntry oldFileEntry =
			PortletFileRepositoryUtil.fetchPortletFileEntry(
				themeDisplay.getScopeGroupId(), repository.getDlFolderId(),
				fileName);

		if (oldFileEntry != null) {
			PortletFileRepositoryUtil.deletePortletFileEntry(
				oldFileEntry.getFileEntryId());
		}

		fileEntry = PortletFileRepositoryUtil.addPortletFileEntry(
			themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
			StyleBookEntry.class.getName(), styleBookEntryId,
			StyleBookPortletKeys.STYLE_BOOK, repository.getDlFolderId(),
			fileEntry.getContentStream(), fileName, fileEntry.getMimeType(),
			false);

		_styleBookEntryService.updatePreviewFileEntryId(
			styleBookEntryId, fileEntry.getFileEntryId());

		TempFileEntryUtil.deleteTempFileEntry(tempFileEntry.getFileEntryId());

		sendRedirect(actionRequest, actionResponse);
	}

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private StyleBookEntryService _styleBookEntryService;

}