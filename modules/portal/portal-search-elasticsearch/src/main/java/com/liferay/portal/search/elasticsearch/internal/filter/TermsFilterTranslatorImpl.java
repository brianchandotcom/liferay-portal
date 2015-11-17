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

import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.search.elasticsearch.filter.TermsFilterTranslator;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermsQueryBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = TermsFilterTranslator.class)
public class TermsFilterTranslatorImpl implements TermsFilterTranslator {

	@Override
	public QueryBuilder translate(TermsFilter termsFilter) {
		TermsQueryBuilder termsQueryBuilder = QueryBuilders.termsQuery(
			termsFilter.getField(), termsFilter.getValues());

		if (termsFilter.getExecution() == TermsFilter.Execution.AND) {
			termsQueryBuilder.execution("and");
		}
		else if (termsFilter.getExecution() == TermsFilter.Execution.BOOL) {
			termsQueryBuilder.execution("bool");
		}
		else if (termsFilter.getExecution() ==
					TermsFilter.Execution.FIELD_DATA) {

			termsQueryBuilder.execution("fielddata");
		}
		else if (termsFilter.getExecution() == TermsFilter.Execution.OR) {
			termsQueryBuilder.execution("or");
		}

		return termsQueryBuilder;
	}

}