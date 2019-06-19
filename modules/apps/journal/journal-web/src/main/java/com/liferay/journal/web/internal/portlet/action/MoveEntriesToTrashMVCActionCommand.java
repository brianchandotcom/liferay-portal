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

package com.liferay.journal.web.internal.portlet.action;

import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.journal.service.JournalFolderService;
import com.liferay.portal.kernel.model.TrashedModel;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		"javax.portlet.name=" + JournalPortletKeys.JOURNAL,
		"mvc.command.name=/journal/move_entries_to_trash"
	},
	service = MVCActionCommand.class
)
public class MoveEntriesToTrashMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		List<TrashedModel> trashedModels = new ArrayList<>();

		long[] deleteFolderIds = ParamUtil.getLongValues(
			actionRequest, "rowIdsJournalFolder");

		for (long deleteFolderId : deleteFolderIds) {
			JournalFolder folder = _journalFolderService.moveFolderToTrash(
				deleteFolderId);

			trashedModels.add(folder);
		}

		String[] deleteArticleIds = ParamUtil.getStringValues(
			actionRequest, "rowIdsJournalArticle");

		for (String deleteArticleId : deleteArticleIds) {
			JournalArticle article = _journalArticleService.moveArticleToTrash(
				themeDisplay.getScopeGroupId(),
				HtmlUtil.unescape(deleteArticleId));

			trashedModels.add(article);
		}

		if (trashedModels.isEmpty()) {
			return;
		}

		Map<String, Object> data = new HashMap<>();

		data.put("trashedModels", trashedModels);

		SessionMessages.add(
			actionRequest,
			_portal.getPortletId(actionRequest) +
				SessionMessages.KEY_SUFFIX_DELETE_SUCCESS_DATA,
			data);

		hideDefaultSuccessMessage(actionRequest);
	}

	@Reference
	private JournalArticleService _journalArticleService;

	@Reference
	private JournalFolderService _journalFolderService;

	@Reference
	private Portal _portal;

}