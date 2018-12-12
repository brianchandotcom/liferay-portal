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

package com.liferay.portal.search.searcher;

import aQute.bnd.annotation.ProviderType;

/**
 * Holds various outcomes returned when a search is performed.
 *
 * @author André de Oliveira
 *
 * @review
 */
@ProviderType
public interface SearchResponse {

	/**
	 * Returns the request string submitted to the search engine.
	 *
	 * @return the request string in search engine form
	 *
	 * @review
	 */
	public String getRequestString();

	/**
	 * Returns the response string returned by the search engine. Can be large
	 * depending on number of results. Must be enabled with
	 * {@link SearchRequest#isIncludeResponseString()}.
	 *
	 * @return the response string in search engine form, or blank if disabled
	 *
	 * @review
	 */
	public String getResponseString();

}