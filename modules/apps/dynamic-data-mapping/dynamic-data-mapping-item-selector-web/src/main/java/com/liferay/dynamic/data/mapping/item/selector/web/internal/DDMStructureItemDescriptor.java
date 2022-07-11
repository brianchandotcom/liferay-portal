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

package com.liferay.dynamic.data.mapping.item.selector.web.internal;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class DDMStructureItemDescriptor
	implements ItemSelectorViewDescriptor.ItemDescriptor {

	public DDMStructureItemDescriptor(
		DDMStructure ddmStructure, HttpServletRequest httpServletRequest) {

		_ddmStructure = ddmStructure;
		_httpServletRequest = httpServletRequest;
	}

	@Override
	public String getIcon() {
		return "forms";
	}

	@Override
	public String getImageURL() {
		return null;
	}

	@Override
	public Date getModifiedDate() {
		return _ddmStructure.getModifiedDate();
	}

	@Override
	public String getPayload() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return JSONUtil.put(
			"ddmstructureid", _ddmStructure.getStructureId()
		).put(
			"ddmstructurekey", _ddmStructure.getStructureKey()
		).put(
			"name", _ddmStructure.getName(themeDisplay.getLocale())
		).toString();
	}

	@Override
	public String getSubtitle(Locale locale) {
		return _ddmStructure.getDescription(locale);
	}

	@Override
	public String getTitle(Locale locale) {
		return _ddmStructure.getName(locale);
	}

	@Override
	public long getUserId() {
		return _ddmStructure.getUserId();
	}

	@Override
	public String getUserName() {
		return _ddmStructure.getUserName();
	}

	@Override
	public boolean isCompact() {
		return true;
	}

	private final DDMStructure _ddmStructure;
	private final HttpServletRequest _httpServletRequest;

}