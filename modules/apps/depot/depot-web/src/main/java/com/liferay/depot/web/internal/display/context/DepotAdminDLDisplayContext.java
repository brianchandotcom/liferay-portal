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

package com.liferay.depot.web.internal.display.context;

import com.liferay.depot.model.DepotEntry;
import com.liferay.document.library.configuration.DLSizeLimitConfigurationProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class DepotAdminDLDisplayContext {

	public DepotAdminDLDisplayContext(
		DepotEntry depotEntry,
		DLSizeLimitConfigurationProvider dlSizeLimitConfigurationProvider,
		HttpServletRequest httpServletRequest) {

		_depotEntry = depotEntry;
		_dlSizeLimitConfigurationProvider = dlSizeLimitConfigurationProvider;

		_httpServletRequest = httpServletRequest;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public DepotEntry getDepotEntry() {
		return _depotEntry;
	}

	public Map<String, Object> getFileSizePerMimeTypeData() {
		List<Map<String, Object>> sizeList = new ArrayList<>();

		Map<String, Long> groupMimeTypeSizeLimit =
			_dlSizeLimitConfigurationProvider.getGroupMimeTypeSizeLimit(
				_depotEntry.getGroupId());

		groupMimeTypeSizeLimit.forEach(
			(mimeType, size) -> sizeList.add(
				HashMapBuilder.<String, Object>put(
					"mimeType", mimeType
				).put(
					"size", size
				).build()));

		return HashMapBuilder.<String, Object>put(
			"sizeList", sizeList
		).build();
	}

	public String getGroupDLFriendlyURL() throws PortalException {
		Group group = _depotEntry.getGroup();

		return _themeDisplay.getPortalURL() + "/documents" +
			group.getFriendlyURL();
	}

	public String getGroupName() throws PortalException {
		Group group = _depotEntry.getGroup();

		return HtmlUtil.escape(
			group.getDescriptiveName(_themeDisplay.getLocale()));
	}

	public boolean isDirectoryIndexingEnabled() throws PortalException {
		Group group = _depotEntry.getGroup();

		UnicodeProperties unicodeProperties = group.getTypeSettingsProperties();

		return PropertiesParamUtil.getBoolean(
			unicodeProperties, _httpServletRequest, "directoryIndexingEnabled");
	}

	public boolean isShowFileSizePerMimeType() {
		if (GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-114786"))) {
			return true;
		}

		return false;
	}

	private final DepotEntry _depotEntry;
	private final DLSizeLimitConfigurationProvider
		_dlSizeLimitConfigurationProvider;
	private final HttpServletRequest _httpServletRequest;
	private final ThemeDisplay _themeDisplay;

}