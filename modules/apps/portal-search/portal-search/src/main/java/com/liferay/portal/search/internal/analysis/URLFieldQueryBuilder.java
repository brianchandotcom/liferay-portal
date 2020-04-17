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

package com.liferay.portal.search.internal.analysis;

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.search.analysis.FieldQueryBuilder;
import com.liferay.portal.search.analysis.KeywordTokenizer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true, service = URLFieldQueryBuilder.class)
public class URLFieldQueryBuilder implements FieldQueryBuilder {

	@Override
	public Query build(String field, String keywords) {
		BooleanQueryImpl booleanQueryImpl = new BooleanQueryImpl();

		FullTextQueryBuilder fullTextQueryBuilder = new FullTextQueryBuilder(
			keywordTokenizer);

		fullTextQueryBuilder.setMaxExpansions(_maxExpansions);

		booleanQueryImpl.add(
			fullTextQueryBuilder.createPhraseQuery(field, keywords),
			BooleanClauseOccur.SHOULD);

		booleanQueryImpl.add(
			substringFieldQueryBuilder.build(field, keywords),
			BooleanClauseOccur.SHOULD);

		booleanQueryImpl.add(
			fullTextQueryBuilder.createAutocompleteQuery(field, keywords),
			BooleanClauseOccur.SHOULD);

		return booleanQueryImpl;
	}

	@Reference
	protected KeywordTokenizer keywordTokenizer;

	@Reference
	protected SubstringFieldQueryBuilder substringFieldQueryBuilder;

	private volatile int _maxExpansions = 300;

}