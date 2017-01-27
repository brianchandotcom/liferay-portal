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

import com.liferay.portal.search.web.internal.util.PortletPreferencesHelper;

import java.util.Optional;

import javax.portlet.PortletPreferences;

/**
 * @author Lino Alves
 */
public class SearchResultsPortletPreferencesImpl
	implements SearchResultsPortletPreferences {

	public SearchResultsPortletPreferencesImpl(
		Optional<PortletPreferences> portletPreferencesOptional) {

		_portletPreferencesHelper = new PortletPreferencesHelper(
			portletPreferencesOptional);
	}

	@Override
	public int getPaginationDelta() {
		return _portletPreferencesHelper.getInteger(
			SearchResultsPortletPreferences.PAGINATION_DELTA_PREFERENCE_KEY,
			20);
	}

	@Override
	public String getPaginationDeltaParameterName() {
		return _portletPreferencesHelper.getString(
			SearchResultsPortletPreferences.
				PAGINATION_DELTA_PARAMETER_NAME_PREFERENCE_KEY,
			"delta");
	}

	@Override
	public String getPaginationStartParameterName() {
		return _portletPreferencesHelper.getString(
			SearchResultsPortletPreferences.
				PAGINATION_START_PARAMETER_NAME_PREFERENCE_KEY,
			"start");
	}

	@Override
	public boolean isDisplayInDocumentForm() {
		return _portletPreferencesHelper.getBoolean(
			SearchResultsPortletPreferences.
				DISPLAY_IN_DOCUMENT_FORM_PREFERENCE_KEY,
			false);
	}

	@Override
	public boolean isHighlightEnabled() {
		return _portletPreferencesHelper.getBoolean(
			SearchResultsPortletPreferences.HIGHLIGHT_ENABLED_PREFERENCE_KEY,
			true);
	}

	@Override
	public boolean isViewInContext() {
		return _portletPreferencesHelper.getBoolean(
			SearchResultsPortletPreferences.VIEW_IN_CONTEXT_PREFERENCE_KEY,
			true);
	}

	private final PortletPreferencesHelper _portletPreferencesHelper;

}