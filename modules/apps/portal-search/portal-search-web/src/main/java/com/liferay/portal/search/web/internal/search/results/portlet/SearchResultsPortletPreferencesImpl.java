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

package com.liferay.portal.search.web.internal.search.results.portlet;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.search.web.internal.portlet.preferences.BasePortletPreferences;
import com.liferay.portal.util.PropsUtil;

import javax.portlet.PortletPreferences;

/**
 * @author Lino Alves
 */
public class SearchResultsPortletPreferencesImpl
	extends BasePortletPreferences implements SearchResultsPortletPreferences {

	public SearchResultsPortletPreferencesImpl(
		PortletPreferences portletPreferences) {

		super(portletPreferences);
	}

	@Override
	public String getFederatedSearchKey() {
		return getString(
			SearchResultsPortletPreferences.PREFERENCE_KEY_FEDERATED_SEARCH_KEY,
			StringPool.BLANK);
	}

	@Override
	public String getFieldsToDisplay() {
		return getString(
			SearchResultsPortletPreferences.PREFERENCE_KEY_FIELDS_TO_DISPLAY,
			StringPool.BLANK);
	}

	@Override
	public int getPaginationDelta() {
		return getInteger(
			SearchResultsPortletPreferences.PREFERENCE_KEY_PAGINATION_DELTA,
			GetterUtil.getInteger(
				PropsUtil.get(PropsKeys.SEARCH_CONTAINER_PAGE_DEFAULT_DELTA),
				20));
	}

	@Override
	public String getPaginationDeltaParameterName() {
		return getString(
			SearchResultsPortletPreferences.
				PREFERENCE_KEY_PAGINATION_DELTA_PARAMETER_NAME,
			"delta");
	}

	@Override
	public String getPaginationStartParameterName() {
		return getString(
			SearchResultsPortletPreferences.
				PREFERENCE_KEY_PAGINATION_START_PARAMETER_NAME,
			"start");
	}

	@Override
	public boolean isDisplayInDocumentForm() {
		return getBoolean(
			SearchResultsPortletPreferences.
				PREFERENCE_KEY_DISPLAY_IN_DOCUMENT_FORM,
			false);
	}

	@Override
	public boolean isHighlightEnabled() {
		return getBoolean(
			SearchResultsPortletPreferences.PREFERENCE_KEY_HIGHLIGHT_ENABLED,
			true);
	}

	@Override
	public boolean isShowEmptyResultMessage() {
		return getBoolean(
			SearchResultsPortletPreferences.
				PREFERENCE_KEY_SHOW_EMPTY_RESULT_MESSAGE,
			true);
	}

	@Override
	public boolean isShowPagination() {
		return getBoolean(
			SearchResultsPortletPreferences.PREFERENCE_KEY_SHOW_PAGINATION,
			true);
	}

	@Override
	public boolean isViewInContext() {
		return getBoolean(
			SearchResultsPortletPreferences.PREFERENCE_KEY_VIEW_IN_CONTEXT,
			true);
	}

}