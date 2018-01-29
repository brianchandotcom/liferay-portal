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

package com.liferay.asset.publisher.web.util;

import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.asset.publisher.web.configuration.AssetPublisherWebConfiguration;
import com.liferay.asset.util.AssetEntryQueryProcessor;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Map;

import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Pavel Savinov
 */
@Component(
	configurationPid = "com.liferay.asset.publisher.web.configuration.AssetPublisherWebConfiguration",
	immediate = true, service = AssetPublisherCustomizer.class
)
public class DefaultAssetPublisherCustomizer
	implements AssetPublisherCustomizer {

	@Activate
	@Modified
	public void activate(Map<String, Object> properties) {
		assetPublisherWebConfiguration = ConfigurableUtil.createConfigurable(
			AssetPublisherWebConfiguration.class, properties);
	}

	@Override
	public Integer getDelta(HttpServletRequest request) {
		PortletPreferences portletPreferences = getPortletPreferences(request);

		Integer delta = GetterUtil.getInteger(
			portletPreferences.getValue("delta", null),
			SearchContainer.DEFAULT_DELTA);

		return delta;
	}

	@Override
	public String getPortletId() {
		return AssetPublisherPortletKeys.ASSET_PUBLISHER;
	}

	@Override
	public boolean isEnablePermissions(HttpServletRequest request) {
		if (assetPublisherWebConfiguration.searchWithIndex()) {
			return true;
		}

		if (!assetPublisherWebConfiguration.permissionCheckingConfigurable()) {
			return true;
		}

		PortletPreferences portletPreferences = getPortletPreferences(request);

		Boolean enablePermissions = GetterUtil.getBoolean(
			portletPreferences.getValue("enablePermissions", null), true);

		return enablePermissions;
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
	public boolean isSelectionStyleEnabled(HttpServletRequest request) {
		return true;
	}

	@Override
	public boolean isShowAssetEntryQueryProcessor(
		AssetEntryQueryProcessor assetEntryQueryProcessor) {

		return true;
	}

	@Override
	public boolean isShowEnableAddContentButton(HttpServletRequest request) {
		return true;
	}

	@Override
	public boolean isShowEnableRelatedAssets(HttpServletRequest request) {
		return true;
	}

	@Override
	public boolean isShowScopeSelector(HttpServletRequest request) {
		return true;
	}

	@Override
	public boolean isShowSubtypeFieldsFilter(HttpServletRequest request) {
		if (!assetPublisherWebConfiguration.searchWithIndex()) {
			return false;
		}

		return true;
	}

	@Override
	public void setAssetEntryQueryOptions(
		AssetEntryQuery assetEntryQuery, HttpServletRequest request) {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletPreferences portletPreferences = getPortletPreferences(request);

		long[] groupIds = AssetPublisherUtil.getGroupIds(
			portletPreferences, themeDisplay.getScopeGroupId(),
			themeDisplay.getLayout());

		assetEntryQuery.setGroupIds(groupIds);
	}

	protected String getPortletName(HttpServletRequest request) {
		PortletConfig portletConfig = (PortletConfig)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_CONFIG);

		if (portletConfig == null) {
			return StringPool.BLANK;
		}

		return portletConfig.getPortletName();
	}

	protected PortletPreferences getPortletPreferences(
		HttpServletRequest request) {

		PortletRequest portletRequest = (PortletRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		if (portletRequest != null) {
			return portletRequest.getPreferences();
		}

		return null;
	}

	protected AssetPublisherWebConfiguration assetPublisherWebConfiguration;

}