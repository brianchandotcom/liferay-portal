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
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalServiceUtil;
import com.liferay.style.book.util.DefaultStyleBookEntryUtil;
import com.liferay.style.book.util.comparator.StyleBookEntryNameComparator;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Víctor Galán
 */
public class SelectStyleBookEntryDisplayContext {

	public SelectStyleBookEntryDisplayContext(
		HttpServletRequest httpServletRequest, Layout selLayout,
		LiferayPortletResponse liferayPortletResponse) {

		_httpServletRequest = httpServletRequest;
		_selLayout = selLayout;
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

	public StyleBookEntry getDefaultStyleBookEntry() {
		if (_defaultStyleBookEntry != null) {
			return _defaultStyleBookEntry;
		}

		_defaultStyleBookEntry =
			DefaultStyleBookEntryUtil.getDefaultMasterStyleBookEntry(
				_selLayout);

		return _defaultStyleBookEntry;
	}

	public String getDefaultStyleBookLabel() {
		StyleBookEntry defaultStyleBookEntry = getDefaultStyleBookEntry();

		if (defaultStyleBookEntry == null) {
			return LanguageUtil.get(_httpServletRequest, "styles-from-theme");
		}

		if (hasEditableMasterLayout() &&
			(_selLayout.getMasterLayoutPlid() > 0)) {

			return LanguageUtil.get(_httpServletRequest, "styles-from-master");
		}

		return LanguageUtil.get(_httpServletRequest, "styles-by-default");
	}

	public List<StyleBookEntry> getStyleBookEntries() {
		return StyleBookEntryLocalServiceUtil.getStyleBookEntries(
			StagingUtil.getLiveGroupId(_themeDisplay.getScopeGroupId()),
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StyleBookEntryNameComparator(true));
	}

	public long getStyleBookEntryId() {
		if (_styleBookEntryId != null) {
			return _styleBookEntryId;
		}

		_styleBookEntryId = ParamUtil.getLong(
			_httpServletRequest, "styleBookEntryId");

		return _styleBookEntryId;
	}

	public boolean hasEditableMasterLayout() {
		if (_hasEditableMasterLayout != null) {
			return _hasEditableMasterLayout;
		}

		_hasEditableMasterLayout = ParamUtil.getBoolean(
			_httpServletRequest, "editableMasterLayout");

		return _hasEditableMasterLayout;
	}

	private StyleBookEntry _defaultStyleBookEntry;
	private Boolean _hasEditableMasterLayout;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final Layout _selLayout;
	private Long _styleBookEntryId;
	private final ThemeDisplay _themeDisplay;

}