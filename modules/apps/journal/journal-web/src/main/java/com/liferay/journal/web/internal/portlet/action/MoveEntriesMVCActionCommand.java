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
import com.liferay.journal.exception.InvalidDDMStructureException;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.journal.service.JournalFolderService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
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
		"javax.portlet.name=" + JournalPortletKeys.JOURNAL,
		"mvc.command.name=/journal/move_entries"
	},
	service = MVCActionCommand.class
)
public class MoveEntriesMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long newFolderId = ParamUtil.getLong(actionRequest, "newFolderId");

		long[] folderIds = ParamUtil.getLongValues(
			actionRequest, "rowIdsJournalFolder");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			JournalArticle.class.getName(), actionRequest);

		for (long folderId : folderIds) {
			_journalFolderService.moveFolder(
				folderId, newFolderId, serviceContext);
		}

		List<String> invalidArticleIds = new ArrayList<>();

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String[] articleIds = ParamUtil.getStringValues(
			actionRequest, "rowIdsJournalArticle");

		for (String articleId : articleIds) {
			try {
				_journalArticleService.moveArticle(
					themeDisplay.getScopeGroupId(),
					HtmlUtil.unescape(articleId), newFolderId, serviceContext);
			}
			catch (InvalidDDMStructureException iddmse) {
				if (_log.isWarnEnabled()) {
					_log.warn(iddmse.getMessage());
				}

				invalidArticleIds.add(articleId);
			}
		}

		if (!invalidArticleIds.isEmpty()) {
			StringBundler sb = new StringBundler(4);

			sb.append("Folder ");
			sb.append(newFolderId);
			sb.append(" does not allow the structures for articles: ");
			sb.append(StringUtil.merge(invalidArticleIds));

			throw new InvalidDDMStructureException(sb.toString());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MoveEntriesMVCActionCommand.class);

	@Reference
	private JournalArticleService _journalArticleService;

	@Reference
	private JournalFolderService _journalFolderService;

}