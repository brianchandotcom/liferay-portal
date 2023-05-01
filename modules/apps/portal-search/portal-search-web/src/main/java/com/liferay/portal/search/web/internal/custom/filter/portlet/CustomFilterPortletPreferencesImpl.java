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
import com.liferay.portal.search.web.internal.portlet.preferences.BasePortletPreferences;

import javax.portlet.PortletPreferences;

/**
 * @author Igor Nazar
 * @author Luan Maoski
 */
public class CustomFilterPortletPreferencesImpl
	extends BasePortletPreferences implements CustomFilterPortletPreferences {

	public CustomFilterPortletPreferencesImpl(
		PortletPreferences portletPreferences) {

		super(portletPreferences);
	}

	@Override
	public String getBoost() {
		return getString(
			CustomFilterPortletPreferences.PREFERENCE_KEY_BOOST,
			StringPool.BLANK);
	}

	@Override
	public String getCustomHeading() {
		return getString(
			CustomFilterPortletPreferences.PREFERENCE_KEY_CUSTOM_HEADING,
			StringPool.BLANK);
	}

	@Override
	public String getFederatedSearchKey() {
		return getString(
			CustomFilterPortletPreferences.PREFERENCE_KEY_FEDERATED_SEARCH_KEY,
			StringPool.BLANK);
	}

	@Override
	public String getFilterField() {
		return getString(
			CustomFilterPortletPreferences.PREFERENCE_KEY_FILTER_FIELD,
			StringPool.BLANK);
	}

	@Override
	public String getFilterQueryType() {
		return getString(
			CustomFilterPortletPreferences.PREFERENCE_KEY_FILTER_QUERY_TYPE,
			"match");
	}

	@Override
	public String getFilterValue() {
		return getString(
			CustomFilterPortletPreferences.PREFERENCE_KEY_FILTER_VALUE,
			StringPool.BLANK);
	}

	@Override
	public String getOccur() {
		return getString(
			CustomFilterPortletPreferences.PREFERENCE_KEY_OCCUR, "filter");
	}

	@Override
	public String getParameterName() {
		return getString(
			CustomFilterPortletPreferences.PREFERENCE_KEY_PARAMETER_NAME,
			StringPool.BLANK);
	}

	@Override
	public String getParentQueryName() {
		return getString(
			CustomFilterPortletPreferences.PREFERENCE_KEY_PARENT_QUERY_NAME,
			StringPool.BLANK);
	}

	@Override
	public String getQueryName() {
		return getString(
			CustomFilterPortletPreferences.PREFERENCE_KEY_QUERY_NAME,
			StringPool.BLANK);
	}

	@Override
	public boolean isDisabled() {
		return getBoolean(
			CustomFilterPortletPreferences.PREFERENCE_KEY_DISABLED, false);
	}

	@Override
	public boolean isImmutable() {
		return getBoolean(
			CustomFilterPortletPreferences.PREFERENCE_KEY_IMMUTABLE, false);
	}

	@Override
	public boolean isInvisible() {
		return getBoolean(
			CustomFilterPortletPreferences.PREFERENCE_KEY_INVISIBLE, false);
	}

}