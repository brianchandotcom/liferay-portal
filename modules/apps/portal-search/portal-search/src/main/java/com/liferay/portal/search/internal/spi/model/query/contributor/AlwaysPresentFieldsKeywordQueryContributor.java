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

package com.liferay.portal.search.internal.spi.model.query.contributor;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.query.FieldQueryFactory;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.spi.model.query.contributor.KeywordQueryContributor;
import com.liferay.portal.search.spi.model.query.contributor.helper.KeywordQueryContributorHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = KeywordQueryContributor.class)
public class AlwaysPresentFieldsKeywordQueryContributor
	implements KeywordQueryContributor {

	@Override
	public void contribute(
		String keywords, BooleanQuery booleanQuery,
		KeywordQueryContributorHelper keywordQueryContributorHelper) {

		if (Validator.isBlank(keywords)) {
			return;
		}

		for (String field : _ALWAYS_PRESENT_FIELDS) {
			Query query = fieldQueryFactory.createQuery(
				field, keywords, false, false);

			try {
				booleanQuery.add(query, BooleanClauseOccur.SHOULD);
			}
			catch (ParseException parseException) {
				throw new SystemException(parseException);
			}
		}
	}

	@Reference
	protected FieldQueryFactory fieldQueryFactory;

	private static final String[] _ALWAYS_PRESENT_FIELDS = {
		Field.COMMENTS, Field.CONTENT, Field.DESCRIPTION, Field.TITLE,
		Field.URL, Field.USER_NAME
	};

}