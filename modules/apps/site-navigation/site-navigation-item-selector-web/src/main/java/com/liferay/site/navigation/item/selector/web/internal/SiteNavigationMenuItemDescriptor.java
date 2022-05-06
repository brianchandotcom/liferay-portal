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

package com.liferay.site.navigation.item.selector.web.internal;

import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.navigation.model.SiteNavigationMenu;

import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class SiteNavigationMenuItemDescriptor
	implements ItemSelectorViewDescriptor.ItemDescriptor {

	public SiteNavigationMenuItemDescriptor(
		SiteNavigationMenu siteNavigationMenu,
		HttpServletRequest httpServletRequest) {

		_siteNavigationMenu = siteNavigationMenu;
		_httpServletRequest = httpServletRequest;
	}

	@Override
	public String getIcon() {
		return "pages-tree";
	}

	@Override
	public String getImageURL() {
		return null;
	}

	@Override
	public Date getModifiedDate() {
		return _siteNavigationMenu.getModifiedDate();
	}

	@Override
	public String getPayload() {
		return JSONUtil.put(
			"id", _siteNavigationMenu.getSiteNavigationMenuId()
		).put(
			"name", _getName()
		).toString();
	}

	@Override
	public String getSubtitle(Locale locale) {
		return StringPool.BLANK;
	}

	@Override
	public String getTitle(Locale locale) {
		return _getName();
	}

	@Override
	public long getUserId() {
		return _siteNavigationMenu.getUserId();
	}

	@Override
	public String getUserName() {
		return _siteNavigationMenu.getUserName();
	}

	@Override
	public boolean isCompact() {
		return true;
	}

	private String _getName() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (_siteNavigationMenu.getGroupId() ==
				themeDisplay.getScopeGroupId()) {

			return _siteNavigationMenu.getName();
		}

		Group group = GroupLocalServiceUtil.fetchGroup(
			_siteNavigationMenu.getGroupId());

		if (group == null) {
			return _siteNavigationMenu.getName();
		}

		try {
			return StringUtil.appendParentheticalSuffix(
				_siteNavigationMenu.getName(),
				group.getDescriptiveName(themeDisplay.getLocale()));
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		return _siteNavigationMenu.getName();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SiteNavigationMenuItemDescriptor.class);

	private final HttpServletRequest _httpServletRequest;
	private final SiteNavigationMenu _siteNavigationMenu;

}