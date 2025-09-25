/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket;

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