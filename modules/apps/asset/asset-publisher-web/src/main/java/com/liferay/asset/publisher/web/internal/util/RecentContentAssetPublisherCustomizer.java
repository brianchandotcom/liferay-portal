/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	service = AssetPublisherCustomizer.class
)
public class RecentContentAssetPublisherCustomizer
	extends DefaultAssetPublisherCustomizer {

	@Override
	public Integer getDelta(HttpServletRequest httpServletRequest) {
		PortletPreferences portletPreferences = getPortletPreferences(
			httpServletRequest);

		return GetterUtil.getInteger(
			portletPreferences.getValue("delta", null),
			PropsValues.RECENT_CONTENT_MAX_DISPLAY_ITEMS);
	}

	@Override
	public String getPortletId() {
		return AssetPublisherPortletKeys.RECENT_CONTENT;
	}

	@Override
	public boolean isEnablePermissions(HttpServletRequest httpServletRequest) {
		return true;
	}

	@Override
	public boolean isOrderingAndGroupingEnabled(
		HttpServletRequest httpServletRequest) {

		return true;
	}

	@Override
	public boolean isOrderingByTitleEnabled(
		HttpServletRequest httpServletRequest) {

		if (!assetPublisherWebConfiguration.searchWithIndex()) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isShowSubtypeFieldsFilter(
		HttpServletRequest httpServletRequest) {

		return true;
	}

}