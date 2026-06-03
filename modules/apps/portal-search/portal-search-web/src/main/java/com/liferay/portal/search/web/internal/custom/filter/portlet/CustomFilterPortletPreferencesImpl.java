/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.custom.filter.portlet;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.web.internal.portlet.preferences.BasePortletPreferences;
import com.liferay.portal.search.web.internal.seo.SEOPortletPreferences;

import jakarta.portlet.PortletPreferences;

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
	public String getSEOParameterName() {
		String parameterName = getParameterName();

		if (Validator.isNull(parameterName)) {
			return getFilterField();
		}

		return parameterName;
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

	@Override
	public boolean isWebCrawlerIndexingEnabled() {
		return getBoolean(
			SEOPortletPreferences.PREFERENCE_KEY_WEB_CRAWLER_INDEXING_ENABLED,
			true);
	}

}