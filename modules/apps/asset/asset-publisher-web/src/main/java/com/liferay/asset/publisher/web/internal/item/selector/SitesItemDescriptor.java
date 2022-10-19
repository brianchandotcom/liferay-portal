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

package com.liferay.asset.publisher.web.internal.item.selector;

import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class SitesItemDescriptor
	implements ItemSelectorViewDescriptor.ItemDescriptor {

	public SitesItemDescriptor(
		Group group, HttpServletRequest httpServletRequest) {

		_group = group;
		_httpServletRequest = httpServletRequest;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public String getIcon() {
		return "sites";
	}

	@Override
	public String getImageURL() {
		return _group.getLogoURL(_themeDisplay, false);
	}

	@Override
	public String getPayload() {
		return JSONUtil.put(
			"groupDescriptiveName",
			_getDescriptiveName(_themeDisplay.getLocale())
		).put(
			"groupId", _group.getGroupId()
		).put(
			"groupScopeLabel",
			LanguageUtil.get(
				_httpServletRequest, _group.getScopeLabel(_themeDisplay))
		).put(
			"groupType",
			LanguageUtil.get(_httpServletRequest, _group.getTypeLabel())
		).put(
			"url", _group.getDisplayURL(_themeDisplay)
		).put(
			"uuid", _group.getUuid()
		).toString();
	}

	@Override
	public String getSubtitle(Locale locale) {
		return LanguageUtil.get(locale, _group.getScopeLabel(_themeDisplay));
	}

	@Override
	public String getTitle(Locale locale) {
		return _getDescriptiveName(locale);
	}

	private String _getDescriptiveName(Locale locale) {
		try {
			return _group.getDescriptiveName(locale);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		return _group.getName(locale);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SitesItemDescriptor.class);

	private final Group _group;
	private final HttpServletRequest _httpServletRequest;
	private final ThemeDisplay _themeDisplay;

}