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

package com.liferay.portal.search.elasticsearch6.internal.aggregation.bucket;

import com.liferay.portal.search.aggregation.bucket.IncludeExcludeClause;

import org.elasticsearch.search.aggregations.bucket.terms.IncludeExclude;

/**
 * @author Michael C. Han
 */
public class IncludeExcludeTranslator {

	public IncludeExclude translate(IncludeExcludeClause includeExcludeClause) {
		IncludeExclude includeExclude = null;

		if ((includeExcludeClause.getExcludeRegex() != null) ||
			(includeExcludeClause.getIncludeRegex() != null)) {

			includeExclude = new IncludeExclude(
				includeExcludeClause.getIncludeRegex(),
				includeExcludeClause.getExcludeRegex());
		}
		else {
			includeExclude = new IncludeExclude(
				includeExcludeClause.getIncludedValues(),
				includeExcludeClause.getExcludedValues());
		}

		return includeExclude;
	}

}