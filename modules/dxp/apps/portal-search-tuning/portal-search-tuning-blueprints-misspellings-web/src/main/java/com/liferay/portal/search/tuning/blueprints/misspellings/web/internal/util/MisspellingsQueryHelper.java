/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.blueprints.misspellings.web.internal.util;

import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.tuning.blueprints.misspellings.web.internal.index.MisspellingSetFields;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = MisspellingsQueryHelper.class)
public class MisspellingsQueryHelper {

	public void addGroupFilterClause(BooleanQuery booleanQuery, long groupId) {
		BooleanQuery groupQuery = _queries.booleanQuery();

		groupQuery.addShouldQueryClauses(
			_queries.term(MisspellingSetFields.GROUP_ID, groupId));

		groupQuery.addShouldQueryClauses(
			_queries.term(MisspellingSetFields.GROUP_ID, "*"));

		booleanQuery.addFilterQueryClauses(groupQuery);
	}

	public void addLanguageFilterClause(
		BooleanQuery booleanQuery, String languageId) {

		BooleanQuery languageQuery = _queries.booleanQuery();

		languageQuery.addShouldQueryClauses(
			_queries.term(MisspellingSetFields.LANGUAGE_ID, languageId));

		languageQuery.addShouldQueryClauses(
			_queries.term(MisspellingSetFields.LANGUAGE_ID, "*"));

		booleanQuery.addFilterQueryClauses(languageQuery);
	}

	@Reference
	private Queries _queries;

}