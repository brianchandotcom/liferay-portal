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

package com.liferay.site.browser.web.internal.servlet.taglib.clay;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.VerticalCard;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.browser.web.internal.display.context.SiteBrowserDisplayContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class SiteVerticalCard implements VerticalCard {

	public SiteVerticalCard(
		Group group, RenderRequest renderRequest,
		SiteBrowserDisplayContext siteBrowserDisplayContext) {

		_group = group;
		_siteBrowserDisplayContext = siteBrowserDisplayContext;

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);
		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public Map<String, String> getData() {
		if (!_siteBrowserDisplayContext.isShowLink(_group)) {
			return null;
		}

		try {
			Map<String, String> data = new HashMap<>();

			data.put("entityid", String.valueOf(_group.getGroupId()));
			data.put(
				"entityname",
				_group.getDescriptiveName(_themeDisplay.getLocale()));
			data.put("grouptarget", _siteBrowserDisplayContext.getTarget());
			data.put(
				"grouptype",
				LanguageUtil.get(_httpServletRequest, _group.getTypeLabel()));
			data.put("url", _group.getDisplayURL(_themeDisplay));

			return data;
		}
		catch (Exception e) {
		}

		return null;
	}

	@Override
	public String getElementClasses() {
		if (_siteBrowserDisplayContext.isShowLink(_group)) {
			return "card-interactive card-interactive-secondary " +
				"selector-button";
		}

		return null;
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
		if (_group.isCompany()) {
			return StringPool.DASH;
		}

		List<Group> childSites = _group.getChildren(true);

		return LanguageUtil.format(
			_httpServletRequest, "x-child-sites", childSites.size());
	}

	@Override
	public String getTitle() {
		try {
			return HtmlUtil.escape(
				_group.getDescriptiveName(_themeDisplay.getLocale()));
		}
		catch (Exception e) {
		}

		return HtmlUtil.escape(_group.getName(_themeDisplay.getLocale()));
	}

	@Override
	public boolean isSelectable() {
		return false;
	}

	private final Group _group;
	private final HttpServletRequest _httpServletRequest;
	private final SiteBrowserDisplayContext _siteBrowserDisplayContext;
	private final ThemeDisplay _themeDisplay;

}