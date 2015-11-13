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

import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.TermsFilterBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = TermsFilterTranslator.class)
public class TermsFilterTranslatorImpl implements TermsFilterTranslator {

	@Override
	public FilterBuilder translate(TermsFilter termsFilter) {
		TermsFilterBuilder termsFilterBuilder = FilterBuilders.termsFilter(
			termsFilter.getField(), termsFilter.getValues());

		if (termsFilter.isCached() != null) {
			termsFilterBuilder.cache(termsFilter.isCached());
		}

		if (termsFilter.getExecution() == TermsFilter.Execution.AND) {
			termsFilterBuilder.execution("and");
		}
		else if (termsFilter.getExecution() == TermsFilter.Execution.BOOL) {
			termsFilterBuilder.execution("bool");
		}
		else if (termsFilter.getExecution() ==
					TermsFilter.Execution.FIELD_DATA) {

			termsFilterBuilder.execution("fielddata");
		}
		else if (termsFilter.getExecution() == TermsFilter.Execution.OR) {
			termsFilterBuilder.execution("or");
		}

		return termsFilterBuilder;
	}

}