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

package com.liferay.knowledge.base.web.internal.portlet.action;

import com.liferay.knowledge.base.constants.KBArticleConstants;
import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.constants.KBPortletKeys;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.service.KBArticleService;
import com.liferay.knowledge.base.service.KBFolderService;
import com.liferay.knowledge.base.util.comparator.KBArticlePriorityComparator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.IOException;

import java.util.List;

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
		"javax.portlet.name=" + KBPortletKeys.KNOWLEDGE_BASE_ARTICLE,
		"javax.portlet.name=" + KBPortletKeys.KNOWLEDGE_BASE_DISPLAY,
		"javax.portlet.name=" + KBPortletKeys.KNOWLEDGE_BASE_SEARCH,
		"javax.portlet.name=" + KBPortletKeys.KNOWLEDGE_BASE_SECTION,
		"mvc.command.name=/knowledge_base/move_kb_object"
	},
	service = MVCActionCommand.class
)
public class MoveKBObjectMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		boolean dragAndDrop = ParamUtil.getBoolean(
			actionRequest, "dragAndDrop");

		try {
			long resourceClassNameId = ParamUtil.getLong(
				actionRequest, "resourceClassNameId");
			long resourcePrimKey = ParamUtil.getLong(
				actionRequest, "resourcePrimKey");
			long parentResourceClassNameId = ParamUtil.getLong(
				actionRequest, "parentResourceClassNameId",
				_portal.getClassNameId(KBFolderConstants.getClassName()));
			long parentResourcePrimKey = ParamUtil.getLong(
				actionRequest, "parentResourcePrimKey",
				KBFolderConstants.DEFAULT_PARENT_FOLDER_ID);

			long kbArticleClassNameId = _portal.getClassNameId(
				KBArticleConstants.getClassName());

			if (resourceClassNameId == kbArticleClassNameId) {
				if (!_isDragAndDrop(dragAndDrop)) {
					double priority = ParamUtil.getDouble(
						actionRequest, "priority");

					_kbArticleService.moveKBArticle(
						resourcePrimKey, parentResourceClassNameId,
						parentResourcePrimKey, priority);
				}
				else {
					KBArticle kbArticle = _kbArticleService.getLatestKBArticle(
						resourcePrimKey, WorkflowConstants.STATUS_ANY);
					int position = ParamUtil.getInteger(
						actionRequest, "position");

					if ((kbArticle.getParentResourcePrimKey() !=
							parentResourcePrimKey) ||
						(position != -1)) {

						_kbArticleService.moveKBArticle(
							resourcePrimKey, parentResourceClassNameId,
							parentResourcePrimKey,
							_getPriority(
								kbArticle, parentResourcePrimKey, position));
					}
				}
			}
			else {
				if (!_isDragAndDrop(dragAndDrop)) {
					_kbFolderService.moveKBFolder(
						resourcePrimKey, parentResourcePrimKey);
				}
				else {
					if (parentResourceClassNameId == kbArticleClassNameId) {
						_errorMessage(
							actionRequest, actionResponse,
							_language.get(
								_portal.getHttpServletRequest(actionRequest),
								"folders-cannot-be-moved-into-articles"));

						return;
					}

					KBFolder kbFolder = _kbFolderService.getKBFolder(
						resourcePrimKey);

					if (kbFolder.getParentKBFolderId() !=
							parentResourcePrimKey) {

						_kbFolderService.moveKBFolder(
							resourcePrimKey, parentResourcePrimKey);
					}
				}
			}

			if (_isDragAndDrop(dragAndDrop)) {
				hideDefaultSuccessMessage(actionRequest);

				JSONObject jsonObject = JSONUtil.put("success", Boolean.TRUE);

				JSONPortletResponseUtil.writeJSON(
					actionRequest, actionResponse, jsonObject);
			}
		}
		catch (PortalException portalException) {
			if (!_isDragAndDrop(dragAndDrop)) {
				throw portalException;
			}

			_errorMessage(
				actionRequest, actionResponse,
				_language.get(
					_portal.getHttpServletRequest(actionRequest),
					"your-request-failed-to-complete"));
		}
	}

	private void _errorMessage(
			ActionRequest actionRequest, ActionResponse actionResponse,
			String message)
		throws IOException {

		hideDefaultErrorMessage(actionRequest);

		JSONObject jsonObject = JSONUtil.put("errorMessage", message);

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);
	}

	private double _getNearestPriority(
		double nextKBArticlePriority, double previousKBArticlePriority) {

		int ceil = (int)Math.ceil(nextKBArticlePriority);
		int floor = (int)Math.floor(nextKBArticlePriority);

		if ((ceil == floor) &&
			((nextKBArticlePriority - 1) > previousKBArticlePriority)) {

			return nextKBArticlePriority - 1;
		}
		else if ((ceil != floor) && (floor > previousKBArticlePriority)) {
			return floor;
		}
		else {
			return (previousKBArticlePriority + nextKBArticlePriority) / 2;
		}
	}

	private double _getPriority(
			KBArticle kbArticle, long parentResourcePrimKey, int position)
		throws PortalException {

		int kbFoldersCount = _kbFolderService.getKBFoldersCount(
			kbArticle.getGroupId(), parentResourcePrimKey);

		position = position - kbFoldersCount;

		List<KBArticle> kbArticles = _kbArticleService.getKBArticles(
			kbArticle.getGroupId(), parentResourcePrimKey,
			WorkflowConstants.STATUS_ANY, position - 1, position + 1,
			new KBArticlePriorityComparator(true));

		if (ListUtil.isEmpty(kbArticles)) {
			return kbArticle.getPriority();
		}

		KBArticle nextKBArticle = kbArticles.get(kbArticles.size() - 1);

		if (position == 0) {
			return _getNearestPriority(nextKBArticle.getPriority(), 0);
		}
		else if (kbArticles.size() == 1) {
			return _getNearestPriority(
				nextKBArticle.getPriority() + 2, nextKBArticle.getPriority());
		}
		else {
			KBArticle previousKBArticle = kbArticles.get(kbArticles.size() - 2);

			return _getNearestPriority(
				nextKBArticle.getPriority(), previousKBArticle.getPriority());
		}
	}

	private boolean _isDragAndDrop(boolean dragAndDrop) {
		if (GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-156421"))) {
			return dragAndDrop;
		}

		return false;
	}

	@Reference
	private KBArticleService _kbArticleService;

	@Reference
	private KBFolderService _kbFolderService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}