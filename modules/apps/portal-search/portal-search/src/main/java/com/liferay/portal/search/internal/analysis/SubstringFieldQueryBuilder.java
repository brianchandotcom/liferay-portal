/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.analysis;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryTerm;
import com.liferay.portal.kernel.search.WildcardQuery;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.analysis.FieldQueryBuilder;
import com.liferay.portal.search.analysis.KeywordTokenizer;

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
		BooleanQuery booleanQuery = new BooleanQuery();

		List<String> tokens = keywordTokenizer.tokenize(keywords);

		for (String token : tokens) {
			booleanQuery.add(
				_createQuery(field, token), BooleanClauseOccur.SHOULD);
		}

		return booleanQuery;
	}

	@Reference
	protected KeywordTokenizer keywordTokenizer;

	private Query _createQuery(String field, String value) {
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

		return new WildcardQuery(new QueryTerm(field, value));
	}

}