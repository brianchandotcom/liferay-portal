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

package com.liferay.commerce.product.content.search.web.internal.portlet;

import com.liferay.commerce.product.content.search.web.internal.helper.PortletPreferencesHelper;

import java.util.Optional;

import javax.portlet.PortletPreferences;

/**
 * @author Crescenzo Rega
 */
public class CPSpecificationOptionFacetPortletPreferences {

	public static final String PREFERENCE_KEY_DISPLAY_STYLE =
		"cpspecificationoptionFacetDisplayStyle";

	public static final String PREFERENCE_KEY_FREQUENCIES_VISIBLE =
		"frequenciesVisible";

	public static final String PREFERENCE_KEY_FREQUENCY_THRESHOLD =
		"frequencyThreshold";

	public static final String PREFERENCE_KEY_MAX_TERMS = "maxTerms";

	public CPSpecificationOptionFacetPortletPreferences(
		Optional<PortletPreferences> portletPreferencesOptional) {

		_portletPreferencesHelper = new PortletPreferencesHelper(
			portletPreferencesOptional);
	}

	public String getDisplayStyle() {
		return _portletPreferencesHelper.getString(
			CPSpecificationOptionFacetPortletPreferences.
				PREFERENCE_KEY_DISPLAY_STYLE,
			"cloud");
	}

	public int getFrequencyThreshold() {
		return _portletPreferencesHelper.getInteger(
			CPSpecificationOptionFacetPortletPreferences.
				PREFERENCE_KEY_FREQUENCY_THRESHOLD,
			1);
	}

	public int getMaxTerms() {
		return _portletPreferencesHelper.getInteger(
			CPSpecificationOptionFacetPortletPreferences.
				PREFERENCE_KEY_MAX_TERMS,
			10);
	}

	public boolean isFrequenciesVisible() {
		return _portletPreferencesHelper.getBoolean(
			CPSpecificationOptionFacetPortletPreferences.
				PREFERENCE_KEY_FREQUENCIES_VISIBLE,
			true);
	}

	private final PortletPreferencesHelper _portletPreferencesHelper;

}