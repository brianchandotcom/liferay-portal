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

package com.liferay.portal.search.solr7.internal.filter;

import com.liferay.portal.kernel.search.filter.GeoBoundingBoxFilter;

import org.apache.lucene.search.Query;

/**
 * @author Michael C. Han
 */
public interface GeoBoundingBoxFilterTranslator {

	public Query translate(GeoBoundingBoxFilter geoBoundingBoxFilter);

}