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

package com.liferay.portal.search.web.internal.search.bar.portlet.display.context;

/**
 * @author Petteri Karttunen
 */
public class SearchBarPortletInstanceConfigurationDisplayContext {

	public String getDisplayStyle() {
		return _displayStyle;
	}

	public long getDisplayStyleGroupId() {
		return _displayStyleGroupId;
	}

	public String[] getSuggestionsContributorConfigurations() {
		return _suggestionsContributorConfigurations;
	}

	public int getSuggestionsDisplayThreshold() {
		return _suggestionsDisplayThreshold;
	}

	public boolean isEnableSuggestions() {
		return _enableSuggestions;
	}

	public boolean isSuggestionsConfigurationVisible() {
		return _suggestionsConfigurationVisible;
	}

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	public void setDisplayStyleGroupId(long displayStyleGroupId) {
		_displayStyleGroupId = displayStyleGroupId;
	}

	public void setEnableSuggestions(boolean enableSuggestions) {
		_enableSuggestions = enableSuggestions;
	}

	public void setSuggestionsConfigurationVisible(
		boolean suggestionsConfigurationVisible) {

		_suggestionsConfigurationVisible = suggestionsConfigurationVisible;
	}

	public void setSuggestionsContributorConfigurations(
		String[] suggestionsContributorConfigurations) {

		_suggestionsContributorConfigurations =
			suggestionsContributorConfigurations;
	}

	public void setSuggestionsDisplayThreshold(
		int suggestionsDisplayThreshold) {

		_suggestionsDisplayThreshold = suggestionsDisplayThreshold;
	}

	private String _displayStyle;
	private long _displayStyleGroupId;
	private boolean _enableSuggestions;
	private boolean _suggestionsConfigurationVisible;
	private String[] _suggestionsContributorConfigurations;
	private int _suggestionsDisplayThreshold;

}