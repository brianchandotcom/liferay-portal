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

package com.liferay.portal.search.groupby;

import com.liferay.portal.kernel.search.Hits;

import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Bryan Engler
 * @author Michael C. Han
 */
@ProviderType
public interface GroupByResponse {

	public String getField();

	public Hits getHits(String term);

	public Map<String, Hits> getHitsMap();

	public void putHits(String term, Hits hits);

	public void setField(String field);

}