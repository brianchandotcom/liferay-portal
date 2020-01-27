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

package com.liferay.portal.search.internal.query.field;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.analysis.KeywordTokenizer;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.query.field.FieldQueryBuilder;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 * @author Rodrigo Paulino
 */
@Component(service = SubstringFieldQueryBuilder.class)
public class SubstringFieldQueryBuilder implements FieldQueryBuilder {

	@Override
	public Query build(String field, String keywords) {
		BooleanQuery booleanQuery = queries.booleanQuery();

		List<String> tokens = keywordTokenizer.tokenize(keywords);

		for (String token : tokens) {
			booleanQuery.addShouldQueryClauses(createQuery(field, token));
		}

		return booleanQuery;
	}

	protected Query createQuery(String field, String value) {
		if (StringUtil.startsWith(value, CharPool.QUOTE)) {
			value = StringUtil.unquote(value);
		}

		value = StringUtil.removeSubstring(value, CharPool.PERCENT);

		if (value.isEmpty()) {
			value = StringPool.STAR;
		}
		else {
			value = StringUtil.quote(
				StringUtil.toLowerCase(value), StringPool.STAR);
		}

		return queries.wildcard(field, value);
	}

	@Reference
	protected KeywordTokenizer keywordTokenizer;

	@Reference
	protected Queries queries;

}