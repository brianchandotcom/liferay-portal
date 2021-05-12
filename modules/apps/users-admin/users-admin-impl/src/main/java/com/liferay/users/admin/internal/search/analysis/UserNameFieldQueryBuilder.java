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

package com.liferay.users.admin.internal.search.analysis;

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.MatchQuery;
import com.liferay.portal.search.analysis.FieldQueryBuilder;
import com.liferay.portal.search.analysis.KeywordTokenizer;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(immediate = true, service = UserNameFieldQueryBuilder.class)
public class UserNameFieldQueryBuilder implements FieldQueryBuilder {

	@Override
	public Query build(String field, String keywords) {
		BooleanQueryImpl booleanQueryImpl = new BooleanQueryImpl();

		List<String> tokens = _keywordTokenizer.tokenize(keywords);

		for (String token : tokens) {
			booleanQueryImpl.add(
				_getMatchPhrasePrefixQuery("userName.text", token),
				BooleanClauseOccur.SHOULD);
		}

		return booleanQueryImpl;
	}

	private MatchQuery _getMatchPhrasePrefixQuery(String field, String value) {
		MatchQuery matchQuery = new MatchQuery(field, value);

		matchQuery.setType(MatchQuery.Type.PHRASE_PREFIX);

		return matchQuery;
	}

	@Reference
	private KeywordTokenizer _keywordTokenizer;

}