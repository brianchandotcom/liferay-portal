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

package com.liferay.knowledge.base.web.internal.search;

import com.liferay.knowledge.base.exception.NoSuchArticleException;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.service.KBArticleServiceUtil;
import com.liferay.knowledge.base.service.KBFolderServiceUtil;
import com.liferay.knowledge.base.web.internal.security.permission.resource.KBArticlePermission;
import com.liferay.knowledge.base.web.internal.security.permission.resource.KBFolderPermission;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Roberto Díaz
 */
public class EntriesChecker extends EmptyOnClickRowChecker {

	public EntriesChecker(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		super(liferayPortletResponse);

		_liferayPortletResponse = liferayPortletResponse;

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		_permissionChecker = themeDisplay.getPermissionChecker();
	}

	@Override
	public String getAllRowsCheckBox() {
		return null;
	}

	@Override
	public String getAllRowsCheckBox(HttpServletRequest httpServletRequest) {
		return null;
	}

	@Override
	public String getRowCheckBox(
		HttpServletRequest httpServletRequest, boolean checked,
		boolean disabled, String primaryKey) {

		try {
			KBArticle kbArticle = null;
			KBFolder kbFolder = null;

			long entryId = GetterUtil.getLong(primaryKey);

			try {
				kbArticle = KBArticleServiceUtil.getLatestKBArticle(
					entryId, WorkflowConstants.STATUS_ANY);
			}
			catch (NoSuchArticleException noSuchArticleException) {
				kbFolder = KBFolderServiceUtil.getKBFolder(entryId);
			}

			String name = null;

			if (kbArticle != null) {
				name = KBArticle.class.getSimpleName();

				if (KBArticlePermission.contains(
						_permissionChecker, kbArticle, ActionKeys.DELETE)) {

					return StringPool.BLANK;
				}
			}
			else {
				name = KBFolder.class.getSimpleName();

				if (KBFolderPermission.contains(
						_permissionChecker, kbFolder, ActionKeys.DELETE)) {

					return StringPool.BLANK;
				}
			}

			String checkBoxRowIds = _getEntryRowIds();
			String checkBoxAllRowIds = "'#" + getAllRowIds() + "'";
			String checkBoxPostOnClick =
				_liferayPortletResponse.getNamespace() +
					"toggleActionsButton();";

			return getRowCheckBox(
				httpServletRequest, checked, disabled,
				_liferayPortletResponse.getNamespace() + RowChecker.ROW_IDS +
					name,
				primaryKey, checkBoxRowIds, checkBoxAllRowIds,
				checkBoxPostOnClick);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return StringPool.BLANK;
		}
	}

	private String _getEntryRowIds() {
		return StringBundler.concat(
			"['", _liferayPortletResponse.getNamespace(), RowChecker.ROW_IDS,
			KBArticle.class.getSimpleName(), "', '",
			_liferayPortletResponse.getNamespace(), RowChecker.ROW_IDS,
			KBFolder.class.getSimpleName(), "']");
	}

	private static final Log _log = LogFactoryUtil.getLog(EntriesChecker.class);

	private final LiferayPortletResponse _liferayPortletResponse;
	private final PermissionChecker _permissionChecker;

}