/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.query;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.NestedQuery;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.TermQuery;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.query.NestedFieldQueryHelper;

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

			BooleanQuery booleanQuery = new BooleanQuery();

			String[] parts = StringUtil.split(field, StringPool.POUND);

			String fieldName = parts[1];

			String valueFieldName = parts[0];

			booleanQuery.add(
				queryFunction.apply(valueFieldName), BooleanClauseOccur.MUST);

			booleanQuery.add(
				new TermQuery("nestedFieldArray.fieldName", fieldName),
				BooleanClauseOccur.MUST);

			return new NestedQuery("nestedFieldArray", booleanQuery);
		}

		return queryFunction.apply(field);
	}

}