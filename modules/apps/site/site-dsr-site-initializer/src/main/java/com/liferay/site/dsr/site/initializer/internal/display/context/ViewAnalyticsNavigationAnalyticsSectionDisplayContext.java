/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.dsr.site.initializer.internal.display.context;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortalUtil;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

/**
 * @author Gianmarco Brunialti Masera
 */
public class ViewAnalyticsNavigationAnalyticsSectionDisplayContext
	extends BaseAnalyticsSectionDisplayContext {

	public ViewAnalyticsNavigationAnalyticsSectionDisplayContext(
		JSONObject configurationJSONObject,
		FragmentEntryConfigurationParser fragmentEntryConfigurationParser,
		FragmentEntryLink fragmentEntryLink,
		HttpServletRequest httpServletRequest,
		ObjectDefinition objectDefinition) {

		super(
			configurationJSONObject, fragmentEntryConfigurationParser,
			fragmentEntryLink, httpServletRequest, objectDefinition);
	}

	public String getActiveTab() {
		String currentURL = PortalUtil.getCurrentURL(httpServletRequest);

		if (currentURL.contains("view-overview")) {
			return "overview";
		}
		else if (currentURL.contains("view-timeline")) {
			return "timeline";
		}

		return StringPool.BLANK;
	}

	public JSONObject getFilterSettingsJSONObject() {
		return JSONUtil.put(
			"disabled",
			GetterUtil.getBoolean(getConfigurationValue("disableFilters"))
		).put(
			"interactable",
			GetterUtil.getBoolean(
				getConfigurationValue("userControlledFilters"))
		).put(
			"persisted",
			GetterUtil.getBoolean(getConfigurationValue("persistFilters"))
		);
	}

	@Override
	public Map<String, Object> getProps() {
		return HashMapBuilder.<String, Object>putAll(
			super.getProps()
		).put(
			"activeTab", getActiveTab()
		).put(
			"filterSettings", getFilterSettingsJSONObject()
		).put(
			"filtersJSONString", getAnalyticsStoreFilters()
		).build();
	}

}