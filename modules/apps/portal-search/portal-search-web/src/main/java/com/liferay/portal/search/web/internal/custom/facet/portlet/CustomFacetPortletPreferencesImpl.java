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
import com.liferay.portal.search.web.internal.util.PortletPreferencesHelper;

import java.util.Optional;

import javax.portlet.PortletPreferences;

/**
 * @author Wade Cao
 */
public class CustomFacetPortletPreferencesImpl
	implements CustomFacetPortletPreferences {

	public CustomFacetPortletPreferencesImpl(
		Optional<PortletPreferences> portletPreferencesOptional) {

		_portletPreferencesHelper = new PortletPreferencesHelper(
			portletPreferencesOptional);
	}

	@Override
	public Optional<String> getAggregationFieldOptional() {
		return _portletPreferencesHelper.getString(
			CustomFacetPortletPreferences.PREFERENCE_KEY_AGGREGATION_FIELD);
	}

	@Override
	public String getAggregationFieldString() {
		Optional<String> optional = getAggregationFieldOptional();

		return optional.orElse(StringPool.BLANK);
	}

	@Override
	public Optional<String> getCustomHeadingOptional() {
		return _portletPreferencesHelper.getString(
			CustomFacetPortletPreferences.PREFERENCE_KEY_CUSTOM_HEADING);
	}

	@Override
	public String getCustomHeadingString() {
		Optional<String> optional = getCustomHeadingOptional();

		return optional.orElse(StringPool.BLANK);
	}

	@Override
	public Optional<String> getFederatedSearchKeyOptional() {
		return _portletPreferencesHelper.getString(
			CustomFacetPortletPreferences.PREFERENCE_KEY_FEDERATED_SEARCH_KEY);
	}

	@Override
	public String getFederatedSearchKeyString() {
		return getFederatedSearchKeyOptional().orElse(StringPool.BLANK);
	}

	@Override
	public int getFrequencyThreshold() {
		return _portletPreferencesHelper.getInteger(
			CustomFacetPortletPreferences.PREFERENCE_KEY_FREQUENCY_THRESHOLD,
			1);
	}

	@Override
	public int getMaxTerms() {
		return _portletPreferencesHelper.getInteger(
			CustomFacetPortletPreferences.PREFERENCE_KEY_MAX_TERMS, 10);
	}

	@Override
	public Optional<String> getParameterNameOptional() {
		return _portletPreferencesHelper.getString(
			CustomFacetPortletPreferences.PREFERENCE_KEY_PARAMETER_NAME);
	}

	@Override
	public String getParameterNameString() {
		Optional<String> optional = getParameterNameOptional();

		return optional.orElse(StringPool.BLANK);
	}

	@Override
	public boolean isFrequenciesVisible() {
		return _portletPreferencesHelper.getBoolean(
			CustomFacetPortletPreferences.PREFERENCE_KEY_FREQUENCIES_VISIBLE,
			true);
	}

	private final PortletPreferencesHelper _portletPreferencesHelper;

}