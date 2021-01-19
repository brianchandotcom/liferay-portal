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
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.style.book.model.StyleBookEntry;

import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.RenderRequest;

/**
 * @author Víctor Galán
 */
public class DefaultStylebookLayoutVerticalCard implements VerticalCard {

	public DefaultStylebookLayoutVerticalCard(
		String name, StyleBookEntry styleBookEntry,
		RenderRequest renderRequest) {

		_name = name;
		_styleBookEntry = styleBookEntry;

		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", _themeDisplay.getLocale(), getClass());
	}

	@Override
	public String getCssClass() {
		return "select-master-layout-option card-interactive " +
			"card-interactive-secondary";
	}

	@Override
	public Map<String, String> getDynamicAttributes() {
		return HashMapBuilder.put(
			"data-name", _name
		).put(
			"data-styleBookEntryId", "0"
		).build();
	}

	@Override
	public String getIcon() {
		return "magic";
	}

	@Override
	public String getImageSrc() {
		if (_styleBookEntry != null) {
			return _styleBookEntry.getImagePreviewURL(_themeDisplay);
		}

		return null;
	}

	@Override
	public String getSubtitle() {
		if (_styleBookEntry != null) {
			return _styleBookEntry.getName();
		}

		return LanguageUtil.get(_resourceBundle, "provided-by-theme");
	}

	@Override
	public String getTitle() {
		return _name;
	}

	@Override
	public boolean isSelectable() {
		return false;
	}

	@Override
	public Boolean isStickerShown() {
		return false;
	}

	private final String _name;
	private final ResourceBundle _resourceBundle;
	private final StyleBookEntry _styleBookEntry;
	private final ThemeDisplay _themeDisplay;

}