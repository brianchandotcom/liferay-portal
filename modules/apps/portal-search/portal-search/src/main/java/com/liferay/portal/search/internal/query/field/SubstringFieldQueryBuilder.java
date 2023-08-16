/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(
	property = "query.builder.type=substring", service = FieldQueryBuilder.class
)
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

		value = StringUtil.replace(value, CharPool.PERCENT, StringPool.BLANK);

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