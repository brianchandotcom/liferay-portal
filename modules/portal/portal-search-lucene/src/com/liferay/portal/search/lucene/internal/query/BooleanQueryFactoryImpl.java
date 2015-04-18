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

package com.liferay.portal.search.lucene.internal.query;

import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactory;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = BooleanQueryFactoryImpl.class)
public class BooleanQueryFactoryImpl implements BooleanQueryFactory {

	@Override
	public BooleanQuery create() {
		return new BooleanQueryImpl(_luceneQueryHelper, _queryTranslator);
	}

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void setLuceneQueryHelper(LuceneQueryHelper luceneQueryHelper) {
		_luceneQueryHelper = luceneQueryHelper;
	}

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void setQueryTranslator(QueryTranslator<?> queryTranslator) {
		_queryTranslator = queryTranslator;
	}

	protected void unsetLuceneQueryHelper(LuceneQueryHelper luceneQueryHelper) {
		_luceneQueryHelper = null;
	}

	protected void unsetQueryTranslator(QueryTranslator<?> queryTranslator) {
		_queryTranslator = null;
	}

	private LuceneQueryHelper _luceneQueryHelper;
	private QueryTranslator<?> _queryTranslator;

}