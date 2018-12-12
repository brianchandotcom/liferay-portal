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
 * Builds a search response with the results of a search.
 *
 * <p/>
 *
 * Use is intended for the Liferay Search Framework only.
 *
 * @author André de Oliveira
 *
 * @review
 */
@ProviderType
public interface SearchResponseBuilder {

	/**
	 * Builds the search response.
	 *
	 * @return the search response
	 *
	 * @review
	 */
	public SearchResponse build();

	/**
	 * Sets the request string submitted to the search engine.
	 *
	 * @param requestString the request string in search engine form
	 * @return the same builder
	 *
	 * @review
	 */
	public SearchResponseBuilder requestString(String requestString);

	/**
	 * Returns the response string returned by the search engine.
	 *
	 * @param responseString the response string in search engine form
	 * @return the same builder
	 *
	 * @review
	 */
	public SearchResponseBuilder responseString(String responseString);

}