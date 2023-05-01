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

package com.liferay.portal.search.web.internal.custom.facet.portlet;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.search.web.internal.portlet.preferences.BasePortletPreferences;

import javax.portlet.PortletPreferences;

/**
 * @author Wade Cao
 */
public class CustomFacetPortletPreferencesImpl
	extends BasePortletPreferences implements CustomFacetPortletPreferences {

	public CustomFacetPortletPreferencesImpl(
		PortletPreferences portletPreferences) {

		super(portletPreferences);
	}

	@Override
	public String getAggregationField() {
		return getString(
			CustomFacetPortletPreferences.PREFERENCE_KEY_AGGREGATION_FIELD,
			StringPool.BLANK);
	}

	@Override
	public String getCustomHeading() {
		return getString(
			CustomFacetPortletPreferences.PREFERENCE_KEY_CUSTOM_HEADING,
			StringPool.BLANK);
	}

	@Override
	public String getFederatedSearchKey() {
		return getString(
			CustomFacetPortletPreferences.PREFERENCE_KEY_FEDERATED_SEARCH_KEY,
			StringPool.BLANK);
	}

	@Override
	public int getFrequencyThreshold() {
		return getInteger(
			CustomFacetPortletPreferences.PREFERENCE_KEY_FREQUENCY_THRESHOLD,
			1);
	}

	@Override
	public int getMaxTerms() {
		return getInteger(
			CustomFacetPortletPreferences.PREFERENCE_KEY_MAX_TERMS, 10);
	}

	@Override
	public String getOrder() {
		return getString(
			CustomFacetPortletPreferencesImpl.PREFERENCE_KEY_ORDER,
			"count:desc");
	}

	@Override
	public String getParameterName() {
		return getString(
			CustomFacetPortletPreferences.PREFERENCE_KEY_PARAMETER_NAME,
			StringPool.BLANK);
	}

	@Override
	public boolean isFrequenciesVisible() {
		return getBoolean(
			CustomFacetPortletPreferences.PREFERENCE_KEY_FREQUENCIES_VISIBLE,
			true);
	}

}