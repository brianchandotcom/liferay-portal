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

package com.liferay.portal.search.elasticsearch.internal.filter;

import com.liferay.portal.kernel.search.QueryPreProcessConfiguration;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.elasticsearch.filter.TermFilterTranslator;

import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryFilterBuilder;
import org.elasticsearch.index.query.TermFilterBuilder;
import org.elasticsearch.index.query.WildcardQueryBuilder;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = TermFilterTranslator.class)
public class TermFilterTranslatorImpl implements TermFilterTranslator {

	@Override
	public FilterBuilder translate(TermFilter termFilter) {
		String field = termFilter.getField();
		String value = termFilter.getValue();

		FilterBuilder filterBuilder = null;

		if ((_queryPreProcessConfiguration != null) &&
			_queryPreProcessConfiguration.isSubstringSearchAlways(field)) {

			WildcardQueryBuilder wildcardQueryBuilder =
				toCaseInsensitiveSubstringQuery(field, value);

			QueryFilterBuilder queryFilterBuilder = FilterBuilders.queryFilter(
				wildcardQueryBuilder);

			queryFilterBuilder.cache(termFilter.isCached());

			filterBuilder = queryFilterBuilder;
		}
		else {
			TermFilterBuilder termFilterBuilder = FilterBuilders.termFilter(
				field, value);

			termFilterBuilder.cache(termFilter.isCached());

			filterBuilder = termFilterBuilder;
		}

		return filterBuilder;
	}

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void setQueryPreProcessConfiguration(
		QueryPreProcessConfiguration queryPreProcessConfiguration) {

		_queryPreProcessConfiguration = queryPreProcessConfiguration;
	}

	protected WildcardQueryBuilder toCaseInsensitiveSubstringQuery(
		String field, String value) {

		value = StringUtil.replace(value, StringPool.PERCENT, StringPool.BLANK);
		value = StringUtil.toLowerCase(value);
		value = StringPool.STAR + value + StringPool.STAR;

		return QueryBuilders.wildcardQuery(field, value);
	}

	protected void unsetQueryPreProcessConfiguration(
		QueryPreProcessConfiguration queryPreProcessConfiguration) {

		_queryPreProcessConfiguration = null;
	}

	private QueryPreProcessConfiguration _queryPreProcessConfiguration;

}