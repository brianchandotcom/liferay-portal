/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.query.translator;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.query.WildcardQuery;

import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BoostQuery;
import org.apache.lucene.search.Query;

/**
 * @author Petteri Karttunen
 */
public class WildcardQueryTranslatorImpl {

	public Query translate(WildcardQuery wildcardQuery) {
		Query query = new org.apache.lucene.search.WildcardQuery(
			new Term(
				_escapeSpaces(wildcardQuery.getField()),
				_escape(wildcardQuery.getValue())));

		if (wildcardQuery.getBoost() != null) {
			return new BoostQuery(query, wildcardQuery.getBoost());
		}

		return query;
	}

	private String _escape(String value) {
		int x = 0;
		int y = 0;

		int length = value.length();

		StringBuilder sb = new StringBuilder(length * 2);

		while (y < length) {
			char c = value.charAt(y);

			if ((c == CharPool.QUESTION) || (c == CharPool.SPACE) ||
				(c == CharPool.STAR)) {

				sb.append(QueryParser.escape(value.substring(x, y)));

				if (c == CharPool.SPACE) {
					sb.append(CharPool.BACK_SLASH);
				}

				sb.append(c);

				x = y + 1;
			}

			y++;
		}

		sb.append(QueryParser.escape(value.substring(x)));

		return sb.toString();
	}

	private String _escapeSpaces(String value) {
		return StringUtil.replace(
			value, CharPool.SPACE, StringPool.BACK_SLASH + StringPool.SPACE);
	}

}