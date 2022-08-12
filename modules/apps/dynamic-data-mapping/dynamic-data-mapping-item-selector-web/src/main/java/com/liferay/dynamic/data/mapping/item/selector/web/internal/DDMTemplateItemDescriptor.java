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

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class DDMTemplateItemDescriptor
	implements ItemSelectorViewDescriptor.ItemDescriptor {

	public DDMTemplateItemDescriptor(
		DDMTemplate ddmTemplate, HttpServletRequest httpServletRequest) {

		_ddmTemplate = ddmTemplate;
		_httpServletRequest = httpServletRequest;
	}

	@Override
	public String getIcon() {
		return "page-template";
	}

	@Override
	public String getImageURL() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return HtmlUtil.escapeAttribute(
			_ddmTemplate.getTemplateImageURL(themeDisplay));
	}

	@Override
	public Date getModifiedDate() {
		return _ddmTemplate.getModifiedDate();
	}

	@Override
	public String getPayload() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return JSONUtil.put(
			"ddmtemplateid", _ddmTemplate.getTemplateId()
		).put(
			"ddmtemplatekey", _ddmTemplate.getTemplateKey()
		).put(
			"description", _ddmTemplate.getDescription(themeDisplay.getLocale())
		).put(
			"imageurl", _ddmTemplate.getTemplateImageURL(themeDisplay)
		).put(
			"name", _ddmTemplate.getName(themeDisplay.getLocale())
		).toString();
	}

	@Override
	public String getSubtitle(Locale locale) {
		return _ddmTemplate.getDescription(locale);
	}

	@Override
	public String getTitle(Locale locale) {
		return _ddmTemplate.getName(locale);
	}

	@Override
	public long getUserId() {
		return _ddmTemplate.getUserId();
	}

	@Override
	public String getUserName() {
		return _ddmTemplate.getUserName();
	}

	private final DDMTemplate _ddmTemplate;
	private final HttpServletRequest _httpServletRequest;

}