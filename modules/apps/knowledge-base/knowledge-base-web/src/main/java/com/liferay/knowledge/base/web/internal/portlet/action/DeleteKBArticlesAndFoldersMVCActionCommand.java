/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.web.internal.portlet.action;

import com.liferay.knowledge.base.constants.KBPortletKeys;
import com.liferay.knowledge.base.service.KBArticleService;
import com.liferay.knowledge.base.service.KBFolderService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = {
		"javax.portlet.name=" + KBPortletKeys.KNOWLEDGE_BASE_ADMIN,
		"mvc.command.name=/knowledge_base/delete_kb_articles_and_folders"
	},
	service = MVCActionCommand.class
)
public class DeleteKBArticlesAndFoldersMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long[] deleteKBArticleResourcePrimKeys = ParamUtil.getLongValues(
			actionRequest, "rowIdsKBArticle");

		for (long deleteKBArticleResourcePrimKey :
				deleteKBArticleResourcePrimKeys) {

			_kbArticleService.deleteKBArticle(deleteKBArticleResourcePrimKey);
		}

		long[] deleteKBFolderIds = ParamUtil.getLongValues(
			actionRequest, "rowIdsKBFolder");

		for (long deleteKBFolderId : deleteKBFolderIds) {
			_kbFolderService.deleteKBFolder(deleteKBFolderId);
		}
	}

	@Reference
	private KBArticleService _kbArticleService;

	@Reference
	private KBFolderService _kbFolderService;

}