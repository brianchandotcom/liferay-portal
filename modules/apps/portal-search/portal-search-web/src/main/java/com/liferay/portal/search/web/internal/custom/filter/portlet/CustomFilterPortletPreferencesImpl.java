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

package com.liferay.portal.search.web.internal.custom.filter.portlet;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.search.web.internal.util.PortletPreferencesHelper;

import java.util.Optional;

import javax.portlet.PortletPreferences;

/**
 * @author Igor Nazar
 * @author Luan Maoski
 */
public class CustomFilterPortletPreferencesImpl
	implements CustomFilterPortletPreferences {

	public CustomFilterPortletPreferencesImpl(
		Optional<PortletPreferences> portletPreferencesOptional) {

		_portletPreferencesHelper = new PortletPreferencesHelper(
			portletPreferencesOptional);
	}

	@Override
	public Optional<String> getBoostOptional() {
		return _portletPreferencesHelper.getString(
			CustomFilterPortletPreferences.PREFERENCE_KEY_BOOST);
	}

	@Override
	public String getBoostString() {
		return getString(getBoostOptional());
	}

	@Override
	public Optional<String> getCustomHeadingOptional() {
		return _portletPreferencesHelper.getString(
			CustomFilterPortletPreferences.PREFERENCE_KEY_CUSTOM_HEADING);
	}

	@Override
	public String getCustomHeadingString() {
		return getString(getCustomHeadingOptional());
	}

	@Override
	public Optional<String> getFederatedSearchKeyOptional() {
		return _portletPreferencesHelper.getString(
			CustomFilterPortletPreferences.PREFERENCE_KEY_FEDERATED_SEARCH_KEY);
	}

	@Override
	public String getFederatedSearchKeyString() {
		return getFederatedSearchKeyOptional().orElse(StringPool.BLANK);
	}

	@Override
	public Optional<String> getFilterFieldOptional() {
		return _portletPreferencesHelper.getString(
			CustomFilterPortletPreferences.PREFERENCE_KEY_FILTER_FIELD);
	}

	@Override
	public String getFilterFieldString() {
		return getString(getFilterFieldOptional());
	}

	@Override
	public String getFilterQueryType() {
		return _portletPreferencesHelper.getString(
			CustomFilterPortletPreferences.PREFERENCE_KEY_FILTER_QUERY_TYPE,
			"match");
	}

	@Override
	public Optional<String> getFilterValueOptional() {
		return _portletPreferencesHelper.getString(
			CustomFilterPortletPreferences.PREFERENCE_KEY_FILTER_VALUE);
	}

	@Override
	public String getFilterValueString() {
		return getString(getFilterValueOptional());
	}

	@Override
	public String getOccur() {
		return _portletPreferencesHelper.getString(
			CustomFilterPortletPreferences.PREFERENCE_KEY_OCCUR, "filter");
	}

	@Override
	public Optional<String> getParameterNameOptional() {
		return _portletPreferencesHelper.getString(
			CustomFilterPortletPreferences.PREFERENCE_KEY_PARAMETER_NAME);
	}

	@Override
	public String getParameterNameString() {
		return getString(getParameterNameOptional());
	}

	@Override
	public Optional<String> getParentQueryNameOptional() {
		return _portletPreferencesHelper.getString(
			CustomFilterPortletPreferences.PREFERENCE_KEY_PARENT_QUERY_NAME);
	}

	@Override
	public String getParentQueryNameString() {
		return getString(getParentQueryNameOptional());
	}

	@Override
	public Optional<String> getQueryNameOptional() {
		return _portletPreferencesHelper.getString(
			CustomFilterPortletPreferences.PREFERENCE_KEY_QUERY_NAME);
	}

	@Override
	public String getQueryNameString() {
		return getString(getQueryNameOptional());
	}

	@Override
	public boolean isDisabled() {
		return _portletPreferencesHelper.getBoolean(
			CustomFilterPortletPreferences.PREFERENCE_KEY_DISABLED, false);
	}

	@Override
	public boolean isImmutable() {
		return _portletPreferencesHelper.getBoolean(
			CustomFilterPortletPreferences.PREFERENCE_KEY_IMMUTABLE, false);
	}

	@Override
	public boolean isInvisible() {
		return _portletPreferencesHelper.getBoolean(
			CustomFilterPortletPreferences.PREFERENCE_KEY_INVISIBLE, false);
	}

	protected String getString(Optional<String> optional) {
		return optional.orElse(StringPool.BLANK);
	}

	private final PortletPreferencesHelper _portletPreferencesHelper;

}