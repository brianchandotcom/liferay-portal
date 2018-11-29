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

package com.liferay.journal.web.internal.display.context;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.web.internal.portlet.action.ActionUtil;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class EditArticleDisplayPageDisplayContext {

	public EditArticleDisplayPageDisplayContext(HttpServletRequest request) {
		_request = request;
	}

	public long getClassNameId() {
		return PortalUtil.getClassNameId(JournalArticle.class);
	}

	public long getClassPK() throws PortalException {
		JournalArticle article = _getArticle();

		if (article != null) {
			return article.getResourcePrimKey();
		}

		return 0;
	}

	public long getDDMStructureId() throws PortalException {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long ddmStructureId = ParamUtil.getLong(_request, "ddmStructureId");

		if (ddmStructureId > 0) {
			return ddmStructureId;
		}

		String ddmStructureKey = ParamUtil.getString(
			_request, "ddmStructureKey");

		DDMStructure ddmStructure = DDMStructureLocalServiceUtil.fetchStructure(
			themeDisplay.getSiteGroupId(),
			PortalUtil.getClassNameId(JournalArticle.class), ddmStructureKey,
			true);

		if (ddmStructure == null) {
			JournalArticle article = _getArticle();

			ddmStructure = DDMStructureLocalServiceUtil.fetchStructure(
				themeDisplay.getSiteGroupId(),
				PortalUtil.getClassNameId(JournalArticle.class),
				article.getDDMStructureKey(), true);
		}

		return ddmStructure.getStructureId();
	}

	public long getGroupId() throws PortalException {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return BeanParamUtil.getLong(
			_getArticle(), _request, "groupId", themeDisplay.getScopeGroupId());
	}

	private JournalArticle _getArticle() throws PortalException {
		if (_article != null) {
			return _article;
		}

		_article = ActionUtil.getArticle(_request);

		return _article;
	}

	private JournalArticle _article;
	private final HttpServletRequest _request;

}