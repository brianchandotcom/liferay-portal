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

import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.search.elasticsearch.filter.TermFilterTranslator;

import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.TermFilterBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = TermFilterTranslator.class)
public class TermFilterTranslatorImpl implements TermFilterTranslator {

	@Override
	public FilterBuilder translate(TermFilter termFilter) {
		TermFilterBuilder termFilterBuilder = FilterBuilders.termFilter(
			termFilter.getField(), termFilter.getValue());

		if (termFilter.isCached() != null) {
			termFilterBuilder.cache(termFilter.isCached());
		}

		return termFilterBuilder;
	}

}