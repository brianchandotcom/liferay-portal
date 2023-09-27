/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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