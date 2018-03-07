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

package com.liferay.portal.search.web.internal.suggestions.display.context;

/**
 * @author Adam Brandizzi
 */
public class SuggestionDisplayContext {

	public String getSuggestedKeywordsFormatted() {
		return _suggestedKeywordsFormatted;
	}

	public String getURL() {
		return _url;
	}

	public void setSuggestedKeywordsFormatted(
		String suggestedKeywordsFormatted) {

		_suggestedKeywordsFormatted = suggestedKeywordsFormatted;
	}

	public void setURL(String url) {
		_url = url;
	}

	private String _suggestedKeywordsFormatted;
	private String _url;

}