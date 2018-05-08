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

package com.liferay.portal.search.web.internal.tag.facet.portlet;

import com.liferay.portal.search.web.internal.util.PortletPreferencesHelper;

import java.util.Optional;

import javax.portlet.PortletPreferences;

/**
 * @author Lino Alves
 */
public class TagFacetPortletPreferencesImpl
	implements TagFacetPortletPreferences {

	public TagFacetPortletPreferencesImpl(
		Optional<PortletPreferences> portletPreferencesOptional) {

		_portletPreferencesHelper = new PortletPreferencesHelper(
			portletPreferencesOptional);
	}

	@Override
	public String getDisplayStyle() {
		return _portletPreferencesHelper.getString(
			TagFacetPortletPreferences.PREFERENCE_KEY_DISPLAY_STYLE, "cloud");
	}

	@Override
	public int getFrequencyThreshold() {
		return _portletPreferencesHelper.getInteger(
			TagFacetPortletPreferences.PREFERENCE_KEY_FREQUENCY_THRESHOLD, 1);
	}

	@Override
	public int getMaxTerms() {
		return _portletPreferencesHelper.getInteger(
			TagFacetPortletPreferences.PREFERENCE_KEY_MAX_TERMS, 10);
	}

	@Override
	public String getParameterName() {
		return _portletPreferencesHelper.getString(
			TagFacetPortletPreferences.PREFERENCE_KEY_PARAMETER_NAME, "tag");
	}

	@Override
	public boolean isDisplayStyleCloud() {
		String displayStyle = getDisplayStyle();

		return displayStyle.equals("cloud");
	}

	@Override
	public boolean isDisplayStyleList() {
		String displayStyle = getDisplayStyle();

		return displayStyle.equals("list");
	}

	@Override
	public boolean isFrequenciesVisible() {
		return _portletPreferencesHelper.getBoolean(
			TagFacetPortletPreferences.PREFERENCE_KEY_FREQUENCIES_VISIBLE,
			true);
	}

	private final PortletPreferencesHelper _portletPreferencesHelper;

}