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
import com.liferay.portal.search.web.internal.helper.PortletPreferencesHelper;

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
	public String getBoost() {
		Optional<String> optional = _portletPreferencesHelper.getString(
			CustomFilterPortletPreferences.PREFERENCE_KEY_BOOST);

		return optional.orElse(StringPool.BLANK);
	}

	@Override
	public String getCustomHeading() {
		Optional<String> optional = _portletPreferencesHelper.getString(
			CustomFilterPortletPreferences.PREFERENCE_KEY_CUSTOM_HEADING);

		return optional.orElse(StringPool.BLANK);
	}

	@Override
	public String getFederatedSearchKey() {
		return _portletPreferencesHelper.getString(
			CustomFilterPortletPreferences.PREFERENCE_KEY_FEDERATED_SEARCH_KEY,
			StringPool.BLANK);
	}

	@Override
	public String getFilterField() {
		Optional<String> optional = _portletPreferencesHelper.getString(
			CustomFilterPortletPreferences.PREFERENCE_KEY_FILTER_FIELD);

		return optional.orElse(StringPool.BLANK);
	}

	@Override
	public String getFilterQueryType() {
		return _portletPreferencesHelper.getString(
			CustomFilterPortletPreferences.PREFERENCE_KEY_FILTER_QUERY_TYPE,
			"match");
	}

	@Override
	public String getFilterValue() {
		Optional<String> optional = _portletPreferencesHelper.getString(
			CustomFilterPortletPreferences.PREFERENCE_KEY_FILTER_VALUE);

		return optional.orElse(StringPool.BLANK);
	}

	@Override
	public String getOccur() {
		return _portletPreferencesHelper.getString(
			CustomFilterPortletPreferences.PREFERENCE_KEY_OCCUR, "filter");
	}

	@Override
	public String getParameterName() {
		Optional<String> optional = _portletPreferencesHelper.getString(
			CustomFilterPortletPreferences.PREFERENCE_KEY_PARAMETER_NAME);

		return optional.orElse(StringPool.BLANK);
	}

	@Override
	public String getParentQueryName() {
		Optional<String> optional = _portletPreferencesHelper.getString(
			CustomFilterPortletPreferences.PREFERENCE_KEY_PARENT_QUERY_NAME);

		return optional.orElse(StringPool.BLANK);
	}

	@Override
	public String getQueryName() {
		Optional<String> optional = _portletPreferencesHelper.getString(
			CustomFilterPortletPreferences.PREFERENCE_KEY_QUERY_NAME);

		return optional.orElse(StringPool.BLANK);
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

	private final PortletPreferencesHelper _portletPreferencesHelper;

}