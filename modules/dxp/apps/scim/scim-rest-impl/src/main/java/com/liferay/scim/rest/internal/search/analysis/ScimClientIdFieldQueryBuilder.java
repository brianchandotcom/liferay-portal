/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.rest.internal.search.analysis;

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.MatchQuery;
import com.liferay.portal.search.analysis.FieldQueryBuilder;
import com.liferay.portal.search.analysis.KeywordTokenizer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pedro Victor Silvestre
 */
@Component(
	property = "query.builder.type=scimClientId",
	service = FieldQueryBuilder.class
)
public class ScimClientIdFieldQueryBuilder implements FieldQueryBuilder {

	@Override
	public Query build(String field, String keywords) {
		BooleanQueryImpl booleanQueryImpl = new BooleanQueryImpl();

		for (String token : keywordTokenizer.tokenize(keywords)) {
			booleanQueryImpl.add(
				new MatchQuery(field, token), BooleanClauseOccur.SHOULD);
		}

		return booleanQueryImpl;
	}

	@Reference
	protected KeywordTokenizer keywordTokenizer;

}