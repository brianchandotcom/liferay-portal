/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.query;

import com.liferay.portal.search.query.RegexQuery;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RegexpQueryBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = RegexQueryTranslator.class)
public class RegexQueryTranslatorImpl implements RegexQueryTranslator {

	@Override
	public QueryBuilder translate(RegexQuery regexQuery) {
		RegexpQueryBuilder regexpQueryBuilder = QueryBuilders.regexpQuery(
			regexQuery.getField(), regexQuery.getRegex());

		if (regexQuery.getMaxDeterminedStates() != null) {
			regexpQueryBuilder.maxDeterminizedStates(
				regexQuery.getMaxDeterminedStates());
		}

		if (regexQuery.getRegexFlags() != null) {
			regexpQueryBuilder.flags(regexQuery.getRegexFlags());
		}

		if (regexQuery.getRewrite() != null) {
			regexpQueryBuilder.rewrite(regexQuery.getRewrite());
		}

		return regexpQueryBuilder;
	}

}