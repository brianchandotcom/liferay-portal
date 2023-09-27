/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.query;

import com.liferay.portal.search.query.PrefixQuery;

import org.elasticsearch.index.query.PrefixQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = PrefixQueryTranslator.class)
public class PrefixQueryTranslatorImpl implements PrefixQueryTranslator {

	@Override
	public QueryBuilder translate(PrefixQuery prefixQuery) {
		PrefixQueryBuilder prefixQueryBuilder = QueryBuilders.prefixQuery(
			prefixQuery.getField(), prefixQuery.getPrefix());

		if (prefixQuery.getRewrite() != null) {
			prefixQueryBuilder.rewrite(prefixQuery.getRewrite());
		}

		return prefixQueryBuilder;
	}

}