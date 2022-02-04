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

package com.liferay.layout.admin.web.internal.display.context;

import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalServiceUtil;
import com.liferay.style.book.util.comparator.StyleBookEntryNameComparator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Víctor Galán
 */
public class SelectStyleBookEntryDisplayContext {

	public SelectStyleBookEntryDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_httpServletRequest = httpServletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public Map<String, Object> getContext() {
		return HashMapBuilder.<String, Object>put(
			"eventName",
			() -> HtmlUtil.escape(
				ParamUtil.getString(
					_httpServletRequest, "eventName",
					_liferayPortletResponse.getNamespace() + "selectStyleBook"))
		).put(
			"selector", ".select-style-book-option"
		).build();
	}

	public List<StyleBookEntry> getStyleBookEntries() {
		List<StyleBookEntry> styleBookEntries = new ArrayList<>();

		StyleBookEntry styleFromThemeStyleBookEntry =
			StyleBookEntryLocalServiceUtil.create();

		styleFromThemeStyleBookEntry.setStyleBookEntryId(0);
		styleFromThemeStyleBookEntry.setName(
			LanguageUtil.get(_httpServletRequest, "styles-from-theme"));

		styleBookEntries.add(styleFromThemeStyleBookEntry);

		styleBookEntries.addAll(
			StyleBookEntryLocalServiceUtil.getStyleBookEntries(
				StagingUtil.getLiveGroupId(_themeDisplay.getScopeGroupId()),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StyleBookEntryNameComparator(true)));

		return styleBookEntries;
	}

	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final ThemeDisplay _themeDisplay;

}