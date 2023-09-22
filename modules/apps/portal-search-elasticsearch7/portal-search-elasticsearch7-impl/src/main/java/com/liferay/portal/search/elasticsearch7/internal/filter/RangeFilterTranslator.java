/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.filter;

import com.liferay.portal.search.filter.RangeFilter;

import org.elasticsearch.index.query.QueryBuilder;

/**
 * @author Petteri Karttunen
 */
public interface RangeFilterTranslator {

	public QueryBuilder translate(RangeFilter rangeFilter);

}