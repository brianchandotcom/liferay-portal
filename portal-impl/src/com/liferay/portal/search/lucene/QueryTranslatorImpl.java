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

package com.liferay.portal.search.lucene;

import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.StringQueryImpl;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.StringPool;

import org.apache.lucene.queryParser.QueryParser;

/**
 * @author Brian Wing Shun Chan
 */
@DoPrivileged
public class QueryTranslatorImpl implements QueryTranslator<Object> {

	public void setLuceneHelper(LuceneHelper luceneHelper) {
		_luceneHelper = luceneHelper;
	}

	@Override
	public Object translate(Query query) throws ParseException {
		if (query instanceof BooleanQueryImpl) {
			return ((BooleanQueryImpl)query).getBooleanQuery();
		}
		else if (query instanceof LuceneQueryImpl) {
			return ((LuceneQueryImpl)query).getQuery();
		}
		else if (query instanceof StringQueryImpl) {
			QueryParser queryParser = new QueryParser(
				_luceneHelper.getVersion(), StringPool.BLANK,
				_luceneHelper.getAnalyzer());

			try {
				return queryParser.parse(query.toString());
			}
			catch (org.apache.lucene.queryParser.ParseException pe) {
				throw new ParseException(pe);
			}
		}
		else if (query instanceof TermQueryImpl) {
			return ((TermQueryImpl)query).getTermQuery();
		}
		else if (query instanceof TermRangeQueryImpl) {
			return ((TermRangeQueryImpl)query).getTermRangeQuery();
		}
		else {
			return null;
		}
	}

	private LuceneHelper _luceneHelper;

}