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
			SearchResultsPortletPreferences.PREFERENCE_KEY_PAGINATION_DELTA,
			20);
	}

	@Override
	public String getPaginationDeltaParameterName() {
		return _portletPreferencesHelper.getString(
			SearchResultsPortletPreferences.
				PREFERENCE_KEY_PAGINATION_DELTA_PARAMETER_NAME,
			"delta");
	}

	@Override
	public String getPaginationStartParameterName() {
		return _portletPreferencesHelper.getString(
			SearchResultsPortletPreferences.
				PREFERENCE_KEY_PAGINATION_START_PARAMETER_NAME,
			"start");
	}

	@Override
	public boolean isDisplayInDocumentForm() {
		return _portletPreferencesHelper.getBoolean(
			SearchResultsPortletPreferences.
				PREFERENCE_KEY_DISPLAY_IN_DOCUMENT_FORM,
			false);
	}

	@Override
	public boolean isHighlightEnabled() {
		return _portletPreferencesHelper.getBoolean(
			SearchResultsPortletPreferences.PREFERENCE_KEY_HIGHLIGHT_ENABLED,
			true);
	}

	@Override
	public boolean isViewInContext() {
		return _portletPreferencesHelper.getBoolean(
			SearchResultsPortletPreferences.PREFERENCE_KEY_VIEW_IN_CONTEXT,
			true);
	}

	private final PortletPreferencesHelper _portletPreferencesHelper;

}