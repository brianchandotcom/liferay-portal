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

package com.liferay.layout.admin.web.internal.servlet.taglib.clay;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.VerticalCard;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.util.DefaultStyleBookEntryUtil;

import java.util.Map;
import java.util.Objects;

import javax.portlet.RenderRequest;

/**
 * @author Víctor Galán
 */
public class SelectStylebookLayoutVerticalCard implements VerticalCard {

	public SelectStylebookLayoutVerticalCard(
		RenderRequest renderRequest, Layout selLayout,
		StyleBookEntry styleBookEntry) {

		_renderRequest = renderRequest;
		_selLayout = selLayout;
		_styleBookEntry = styleBookEntry;

		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public String getCssClass() {
		String cssClass =
			"select-style-book-option card-interactive " +
				"card-interactive-secondary";

		if (_isSelected()) {
			cssClass += " active";
		}

		return cssClass;
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

	@Override
	public Map<String, String> getDynamicAttributes() {
		return HashMapBuilder.put(
			"data-name", _styleBookEntry.getName()
		).put(
			"data-styleBookEntryId",
			String.valueOf(_styleBookEntry.getStyleBookEntryId())
		).put(
			"role", "button"
		).put(
			"tabIndex", "0"
		).build();
	}

	@Override
	public String getIcon() {
		return "magic";
	}

	@Override
	public String getImageSrc() {
		return _styleBookEntry.getImagePreviewURL(_themeDisplay);
	}

	@Override
	public String getStickerCssClass() {
		return "select-style-book-option-sticker sticker-primary";
	}

	@Override
	public String getStickerIcon() {
		return "check-circle";
	}

	@Override
	public String getSubtitle() {
		StyleBookEntry defaultStyleBookEntry = getDefaultStyleBookEntry();

		if ((_styleBookEntry.getStyleBookEntryId() <= 0) &&
			(defaultStyleBookEntry != null)) {

			return defaultStyleBookEntry.getName();
		}

		return StringPool.DASH;
	}

	@Override
	public String getTitle() {
		if (_styleBookEntry.getStyleBookEntryId() > 0) {
			return _styleBookEntry.getName();
		}

		StyleBookEntry defaultStyleBookEntry = getDefaultStyleBookEntry();

		if (defaultStyleBookEntry == null) {
			return LanguageUtil.get(
				_themeDisplay.getLocale(), "styles-from-theme");
		}

		if (ParamUtil.getBoolean(_renderRequest, "editableMasterLayout") &&
			(_selLayout.getMasterLayoutPlid() > 0)) {

			return LanguageUtil.get(
				_themeDisplay.getLocale(), "styles-from-master");
		}

		return LanguageUtil.get(_themeDisplay.getLocale(), "styles-by-default");
	}

	@Override
	public boolean isSelectable() {
		return false;
	}

	private boolean _isSelected() {
		long styleBookEntryId = ParamUtil.getLong(
			_renderRequest, "styleBookEntryId");

		if (Objects.equals(
				styleBookEntryId, _styleBookEntry.getStyleBookEntryId())) {

			return true;
		}

		return false;
	}

	private StyleBookEntry _defaultStyleBookEntry;
	private final RenderRequest _renderRequest;
	private final Layout _selLayout;
	private final StyleBookEntry _styleBookEntry;
	private final ThemeDisplay _themeDisplay;

}