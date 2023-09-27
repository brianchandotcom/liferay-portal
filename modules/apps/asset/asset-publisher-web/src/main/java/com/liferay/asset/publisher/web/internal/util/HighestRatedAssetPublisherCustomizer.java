/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.publisher.web.internal.util;

import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.portal.kernel.util.GetterUtil;

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
public class HighestRatedAssetPublisherCustomizer
	extends DefaultAssetPublisherCustomizer {

	@Override
	public String getPortletId() {
		return AssetPublisherPortletKeys.HIGHEST_RATED_ASSETS;
	}

	@Override
	public boolean isEnablePermissions(HttpServletRequest httpServletRequest) {
		if (!assetPublisherWebConfiguration.permissionCheckingConfigurable()) {
			return true;
		}

		PortletPreferences portletPreferences = getPortletPreferences(
			httpServletRequest);

		return GetterUtil.getBoolean(
			portletPreferences.getValue("enablePermissions", null), true);
	}

	@Override
	public boolean isOrderingAndGroupingEnabled(
		HttpServletRequest httpServletRequest) {

		return false;
	}

	@Override
	public boolean isOrderingByTitleEnabled(
		HttpServletRequest httpServletRequest) {

		return true;
	}

	@Override
	public boolean isSelectionStyleEnabled(
		HttpServletRequest httpServletRequest) {

		return false;
	}

	@Override
	public boolean isShowEnableAddContentButton(
		HttpServletRequest httpServletRequest) {

		return false;
	}

	@Override
	public boolean isShowSubtypeFieldsFilter(
		HttpServletRequest httpServletRequest) {

		if (!assetPublisherWebConfiguration.searchWithIndex()) {
			return false;
		}

		return true;
	}

}