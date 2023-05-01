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

package com.liferay.portal.search.web.internal.site.facet.portlet;

import com.liferay.portal.search.web.internal.portlet.preferences.BasePortletPreferences;

import javax.portlet.PortletPreferences;

/**
 * @author André de Oliveira
 */
public class SiteFacetPortletPreferencesImpl
	extends BasePortletPreferences implements SiteFacetPortletPreferences {

	public SiteFacetPortletPreferencesImpl(
		PortletPreferences portletPreferences) {

		super(portletPreferences);
	}

	@Override
	public int getFrequencyThreshold() {
		return getInteger(
			SiteFacetPortletPreferences.PREFERENCE_KEY_FREQUENCY_THRESHOLD, 1);
	}

	@Override
	public int getMaxTerms() {
		return getInteger(
			SiteFacetPortletPreferences.PREFERENCE_KEY_MAX_TERMS, 10);
	}

	@Override
	public String getOrder() {
		return getString(
			SiteFacetPortletPreferences.PREFERENCE_KEY_ORDER, "count:desc");
	}

	@Override
	public String getParameterName() {
		return getString(
			SiteFacetPortletPreferences.PREFERENCE_KEY_PARAMETER_NAME, "site");
	}

	@Override
	public boolean isFrequenciesVisible() {
		return getBoolean(
			SiteFacetPortletPreferences.PREFERENCE_KEY_FREQUENCIES_VISIBLE,
			true);
	}

}