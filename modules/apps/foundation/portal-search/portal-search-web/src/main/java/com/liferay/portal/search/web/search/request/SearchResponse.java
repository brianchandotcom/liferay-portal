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

package com.liferay.portal.search.web.search.request;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.facet.Facet;

import java.util.List;
import java.util.Optional;

/**
 * @author Rodrigo Paulino
 * @author André de Oliveira
 */
@ProviderType
public interface SearchResponse {

	public List<Document> getDocuments();

	public Facet getFacet(String fieldName);

	public String[] getHighlights();

	public Optional<String> getKeywords();

	public int getPaginationDelta();

	public int getPaginationStart();

	public String getQueryString();

	public int getTotalHits();

}