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

package com.liferay.site.taglib.internal.servlet.taglib.clay;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.VerticalCard;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class SiteVerticalCard implements VerticalCard {

	public SiteVerticalCard(
		Group group, RenderRequest renderRequest, long[] selectedGroupIds) {

		_group = group;
		_selectedGroupIds = selectedGroupIds;

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);
		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public Map<String, String> getData() {
		Map<String, String> data = new HashMap<>();

		try {
			data.put(
				"groupdescriptivename",
				_group.getDescriptiveName(_themeDisplay.getLocale()));
			data.put("groupid", String.valueOf(_group.getGroupId()));
			data.put("groupscopelabel", _group.getScopeLabel(_themeDisplay));
			data.put(
				"grouptype",
				LanguageUtil.get(_httpServletRequest, _group.getTypeLabel()));
			data.put("url", _group.getDisplayURL(_themeDisplay));
			data.put("uuid", _group.getUuid());
		}
		catch (Exception e) {
		}

		return data;
	}

	@Override
	public String getElementClasses() {
		if (ArrayUtil.contains(_selectedGroupIds, _group.getGroupId())) {
			return "text-muted";
		}

		return "card-interactive card-interactive-secondary selector-button";
	}

	@Override
	public String getIcon() {
		return "sites";
	}

	@Override
	public String getImageSrc() {
		return _group.getLogoURL(_themeDisplay, false);
	}

	@Override
	public String getSubtitle() {
		return null;
	}

	@Override
	public String getTitle() {
		try {
			return HtmlUtil.escape(
				_group.getDescriptiveName(_themeDisplay.getLocale()));
		}
		catch (Exception e) {
		}

		return null;
	}

	@Override
	public boolean isSelectable() {
		return false;
	}

	private final Group _group;
	private final HttpServletRequest _httpServletRequest;
	private final long[] _selectedGroupIds;
	private final ThemeDisplay _themeDisplay;

}