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

package com.liferay.portal.search.internal.query.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.NestedQuery;
import com.liferay.portal.kernel.search.generic.TermQueryImpl;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.query.util.NestedFieldQueryHelper;

import java.util.function.Function;

import org.osgi.service.component.annotations.Component;

/**
 * @author Bryan Engler
 */
@Component(service = NestedFieldQueryHelper.class)
public class NestedFieldQueryHelperImpl implements NestedFieldQueryHelper {

	@Override
	public Query getQuery(String field, Function<String, Query> queryFunction) {
		if (field.startsWith("nestedFieldArray.") &&
			field.contains(StringPool.POUND)) {

			BooleanQuery booleanQuery = new BooleanQueryImpl();

			String[] parts = StringUtil.split(field, StringPool.POUND);

			String fieldName = parts[1];
			String valueFieldName = parts[0];

			try {
				booleanQuery.add(
					queryFunction.apply(valueFieldName),
					BooleanClauseOccur.MUST);

				booleanQuery.add(
					new TermQueryImpl("nestedFieldArray.fieldName", fieldName),
					BooleanClauseOccur.MUST);
			}
			catch (ParseException parseException) {
				throw new SystemException(parseException);
			}

			return new NestedQuery("nestedFieldArray", booleanQuery);
		}

		return queryFunction.apply(field);
	}

}