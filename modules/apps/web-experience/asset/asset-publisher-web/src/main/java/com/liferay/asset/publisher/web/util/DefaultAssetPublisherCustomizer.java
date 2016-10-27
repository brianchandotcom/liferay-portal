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

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.asset.kernel.util.AssetEntryQueryProcessor;
import com.liferay.asset.publisher.web.constants.AssetPublisherPortletKeys;
import com.liferay.asset.publisher.web.internal.configuration.AssetPublisherWebConfigurationValues;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;

import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = AssetPublisherCustomizer.class)
public class DefaultAssetPublisherCustomizer
	implements AssetPublisherCustomizer {

	public Integer getDelta(HttpServletRequest request) {
		String portletName = getPortletName(request);

		PortletPreferences portletPreferences = getPortletPreferences(request);

		Integer delta = GetterUtil.getInteger(
			portletPreferences.getValue("delta", null),
			SearchContainer.DEFAULT_DELTA);

		if (portletName.equals(AssetPublisherPortletKeys.RECENT_CONTENT)) {
			delta = PropsValues.RECENT_CONTENT_MAX_DISPLAY_ITEMS;
		}

		return delta;
	}

	public String getPortletId() {
		return AssetPublisherPortletKeys.ASSET_PUBLISHER;
	}

	public boolean isEnablePermissions(HttpServletRequest request) {
		String portletName = getPortletName(request);

		if (!portletName.equals(
				AssetPublisherPortletKeys.HIGHEST_RATED_ASSETS) &&
			!portletName.equals(AssetPublisherPortletKeys.MOST_VIEWED_ASSETS) &&
			AssetPublisherWebConfigurationValues.SEARCH_WITH_INDEX) {

			return true;
		}

		if (!AssetPublisherWebConfigurationValues.
				PERMISSION_CHECKING_CONFIGURABLE) {

			return true;
		}

		PortletPreferences portletPreferences = getPortletPreferences(request);

		Boolean enablePermissions = GetterUtil.getBoolean(
			portletPreferences.getValue("enablePermissions", null), true);

		return enablePermissions;
	}

	public boolean isOrderingAndGroupingEnabled(HttpServletRequest request) {
		String rootPortletId = PortletConstants.getRootPortletId(
			ParamUtil.getString(request, "portletResource"));

		if (rootPortletId.equals(
				AssetPublisherPortletKeys.HIGHEST_RATED_ASSETS) ||
			rootPortletId.equals(
				AssetPublisherPortletKeys.MOST_VIEWED_ASSETS)) {

			return false;
		}

		return true;
	}

	public boolean isOrderingByTitleEnabled(HttpServletRequest request) {
		String rootPortletId = PortletConstants.getRootPortletId(
			ParamUtil.getString(request, "portletResource"));

		if (!AssetPublisherWebConfigurationValues.SEARCH_WITH_INDEX ||
			rootPortletId.equals(AssetPublisherPortletKeys.RELATED_ASSETS)) {

			return false;
		}

		return true;
	}

	public boolean isSelectionStyleEnabled(HttpServletRequest request) {
		String rootPortletId = PortletConstants.getRootPortletId(
			ParamUtil.getString(request, "portletResource"));

		if (rootPortletId.equals(
				AssetPublisherPortletKeys.HIGHEST_RATED_ASSETS) ||
			rootPortletId.equals(
				AssetPublisherPortletKeys.MOST_VIEWED_ASSETS) ||
			rootPortletId.equals(AssetPublisherPortletKeys.RELATED_ASSETS)) {

			return false;
		}

		return true;
	}

	public boolean isShowAssetEntryQueryProcessor(
		AssetEntryQueryProcessor assetEntryQueryProcessor) {

		return true;
	}

	public boolean isShowEnableAddContentButton(HttpServletRequest request) {
		String rootPortletId = PortletConstants.getRootPortletId(
			ParamUtil.getString(request, "portletResource"));

		if (rootPortletId.equals(
				AssetPublisherPortletKeys.HIGHEST_RATED_ASSETS) ||
			rootPortletId.equals(
				AssetPublisherPortletKeys.MOST_VIEWED_ASSETS)) {

			return false;
		}

		return true;
	}

	public boolean isShowEnableRelatedAssets(HttpServletRequest request) {
		String rootPortletId = PortletConstants.getRootPortletId(
			ParamUtil.getString(request, "portletResource"));

		if (rootPortletId.equals(AssetPublisherPortletKeys.RELATED_ASSETS)) {
			return false;
		}

		return true;
	}

	public boolean isShowScopeSelector(HttpServletRequest request) {
		String rootPortletId = PortletConstants.getRootPortletId(
			ParamUtil.getString(request, "portletResource"));

		if (rootPortletId.equals(AssetPublisherPortletKeys.RELATED_ASSETS)) {
			return false;
		}

		return true;
	}

	public boolean isShowSubtypeFieldsFilter(HttpServletRequest request) {
		String rootPortletId = PortletConstants.getRootPortletId(
			ParamUtil.getString(request, "portletResource"));

		if (!AssetPublisherWebConfigurationValues.SEARCH_WITH_INDEX ||
			rootPortletId.equals(AssetPublisherPortletKeys.RELATED_ASSETS)) {

			return false;
		}

		return true;
	}

	public void setAssetEntryQueryOptions(
		AssetEntryQuery assetEntryQuery, HttpServletRequest request) {

		String portletName = getPortletName(request);

		if (!portletName.equals(AssetPublisherPortletKeys.RELATED_ASSETS)) {
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			PortletPreferences portletPreferences = getPortletPreferences(
				request);

			long[] groupIds = AssetPublisherUtil.getGroupIds(
				portletPreferences, themeDisplay.getScopeGroupId(),
				themeDisplay.getLayout());

			assetEntryQuery.setGroupIds(groupIds);
		}

		if (portletName.equals(AssetPublisherPortletKeys.RELATED_ASSETS)) {
			AssetEntry layoutAssetEntry = (AssetEntry)request.getAttribute(
				WebKeys.LAYOUT_ASSET_ENTRY);

			if (layoutAssetEntry != null) {
				assetEntryQuery.setLinkedAssetEntryId(
					layoutAssetEntry.getEntryId());
			}
		}
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

}