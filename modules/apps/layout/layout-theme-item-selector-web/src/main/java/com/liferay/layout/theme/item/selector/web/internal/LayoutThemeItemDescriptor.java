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

package com.liferay.layout.theme.item.selector.web.internal;

import com.liferay.frontend.taglib.clay.servlet.taglib.VerticalCard;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.layout.theme.item.selector.web.internal.taglib.SelectThemeVerticalCard;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.plugin.PluginPackage;

import java.util.Locale;

import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Stefan Tanasie
 */
public class LayoutThemeItemDescriptor
	implements ItemSelectorViewDescriptor.ItemDescriptor {

	public LayoutThemeItemDescriptor(
		Theme theme, HttpServletRequest httpServletRequest) {

		_theme = theme;
		_httpServletRequest = httpServletRequest;
	}

	@Override
	public String getIcon() {
		return null;
	}

	@Override
	public String getImageURL() {
		return _theme.getStaticResourcePath() + _theme.getImagesPath() +
			"/thumbnail.png";
	}

	@Override
	public String getPayload() {
		return JSONUtil.put(
			"themeId", _theme.getThemeId()
		).toString();
	}

	@Override
	public String getSubtitle(Locale locale) {
		return LanguageUtil.format(
			_httpServletRequest, "by-x", _getPluginPackage().getAuthor());
	}

	@Override
	public String getTitle(Locale locale) {
		return _theme.getName();
	}

	@Override
	public VerticalCard getVerticalCard(
		RenderRequest renderRequest, RowChecker rowChecker) {

		return new SelectThemeVerticalCard(_theme, renderRequest);
	}

	private PluginPackage _getPluginPackage() {
		return _theme.getPluginPackage();
	}

	private final HttpServletRequest _httpServletRequest;
	private final Theme _theme;

}