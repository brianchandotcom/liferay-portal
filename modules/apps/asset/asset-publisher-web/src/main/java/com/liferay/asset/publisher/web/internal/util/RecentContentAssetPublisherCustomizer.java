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

package com.liferay.asset.publisher.web.internal.util;

import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.util.PropsValues;

import javax.portlet.PortletPreferences;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pavel Savinov
 */
@Component(
	configurationPid = "com.liferay.asset.publisher.web.internal.configuration.AssetPublisherWebConfiguration",
	immediate = true, service = AssetPublisherCustomizer.class
)
public class RecentContentAssetPublisherCustomizer
	extends DefaultAssetPublisherCustomizer {

	@Override
	public Integer getDelta(HttpServletRequest request) {
		PortletPreferences portletPreferences = getPortletPreferences(request);

		return GetterUtil.getInteger(
			portletPreferences.getValue("delta", null),
			PropsValues.RECENT_CONTENT_MAX_DISPLAY_ITEMS);
	}

	@Override
	public String getPortletId() {
		return AssetPublisherPortletKeys.RECENT_CONTENT;
	}

	@Override
	public boolean isEnablePermissions(HttpServletRequest request) {
		return true;
	}

	@Override
	public boolean isOrderingAndGroupingEnabled(HttpServletRequest request) {
		return true;
	}

	@Override
	public boolean isOrderingByTitleEnabled(HttpServletRequest request) {
		if (!assetPublisherWebConfiguration.searchWithIndex()) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isShowSubtypeFieldsFilter(HttpServletRequest request) {
		return true;
	}

}