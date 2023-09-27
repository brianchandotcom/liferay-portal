/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.users.admin.internal.search.analysis;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.MatchQuery;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.analysis.FieldQueryBuilder;
import com.liferay.portal.search.analysis.KeywordTokenizer;
import com.liferay.portal.search.engine.SearchEngineInformation;

import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	property = "query.builder.type=userName", service = FieldQueryBuilder.class
)
public class UserNameFieldQueryBuilder implements FieldQueryBuilder {

	@Override
	public Query build(String field, String keywords) {
		BooleanQueryImpl booleanQueryImpl = new BooleanQueryImpl();

		List<String> tokens = _keywordTokenizer.tokenize(keywords);

		for (String token : tokens) {
			token = StringUtil.removeChar(token, CharPool.PERCENT);

			if (token.isEmpty() &&
				!Objects.equals(
					_searchEngineInformation.getVendorString(), "Solr")) {

				continue;
			}

			booleanQueryImpl.add(
				_getMatchQuery("userName.text", token),
				BooleanClauseOccur.SHOULD);
		}

		return booleanQueryImpl;
	}

	private MatchQuery _getMatchQuery(String field, String value) {
		MatchQuery matchQuery = new MatchQuery(field, value);

		matchQuery.setType(MatchQuery.Type.PHRASE_PREFIX);

		return matchQuery;
	}

	@Reference
	private KeywordTokenizer _keywordTokenizer;

	@Reference
	private SearchEngineInformation _searchEngineInformation;

}