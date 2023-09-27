/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.filter;

import com.liferay.portal.kernel.search.filter.PrefixFilter;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = PrefixFilterTranslator.class)
public class PrefixFilterTranslatorImpl implements PrefixFilterTranslator {

	@Override
	public QueryBuilder translate(PrefixFilter prefixFilter) {
		return QueryBuilders.prefixQuery(
			prefixFilter.getField(), prefixFilter.getPrefix());
	}

}