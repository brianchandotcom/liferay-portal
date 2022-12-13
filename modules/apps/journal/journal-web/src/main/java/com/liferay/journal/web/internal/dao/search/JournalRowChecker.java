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

package com.liferay.journal.web.internal.dao.search;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class JournalRowChecker extends EmptyOnClickRowChecker {

	public JournalRowChecker(PortletResponse portletResponse) {
		super(portletResponse);

		_portletResponse = portletResponse;
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

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		JournalArticle article = JournalArticleLocalServiceUtil.fetchArticle(
			themeDisplay.getScopeGroupId(), primaryKey);

		if (article == null) {
			return StringPool.BLANK;
		}

		return super.getRowCheckBox(
			httpServletRequest, checked, disabled,
			StringBundler.concat(
				_portletResponse.getNamespace(), RowChecker.ROW_IDS,
				JournalArticle.class.getSimpleName(), StringPool.BLANK),
			primaryKey,
			StringBundler.concat(
				_portletResponse.getNamespace(), RowChecker.ROW_IDS,
				JournalArticle.class.getSimpleName(), "']"),
			"'#" + getAllRowIds() + "'", StringPool.BLANK);
	}

	private final PortletResponse _portletResponse;

}