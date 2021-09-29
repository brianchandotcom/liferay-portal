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

package com.liferay.search.experiences.internal.blueprint.search.request.body.contributor;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.filter.ComplexQueryPartBuilderFactory;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.search.experiences.rest.dto.v1_0.Clause;
import com.liferay.search.experiences.rest.dto.v1_0.Configuration;
import com.liferay.search.experiences.rest.dto.v1_0.QueryConfiguration;
import com.liferay.search.experiences.rest.dto.v1_0.QueryEntry;
import com.liferay.search.experiences.rest.dto.v1_0.SXPBlueprint;

import java.util.function.Consumer;

/**
 * @author Petteri Karttunen
 */
public class QuerySXPSearchRequestBodyContributor
	implements SXPSearchRequestBodyContributor {

	public QuerySXPSearchRequestBodyContributor(
		ComplexQueryPartBuilderFactory complexQueryPartBuilderFactory,
		Queries queries) {

		_complexQueryPartBuilderFactory = complexQueryPartBuilderFactory;
		_queries = queries;
	}

	@Override
	public void contribute(
		SearchRequestBuilder searchRequestBuilder, SXPBlueprint sxpBlueprint) {

		Configuration configuration = sxpBlueprint.getConfiguration();

		QueryConfiguration queryConfiguration =
			configuration.getQueryConfiguration();

		if (queryConfiguration == null) {
			return;
		}

		_isNotEmptyForEach(
			queryConfiguration.getQueryEntries(),
			queryEntry -> _processQueryEntry(queryEntry, searchRequestBuilder));

		if (queryConfiguration.getApplyIndexerClauses() != null) {
			searchRequestBuilder.withSearchContext(
				searchContext -> searchContext.setAttribute(
					"search.full.query.suppress.indexer.provided.clauses",
					!queryConfiguration.getApplyIndexerClauses()));
		}
	}

	@Override
	public String getName() {
		return "query";
	}

	private <T> void _isNotEmptyForEach(T[] array, Consumer<T> consumer) {
		if (ArrayUtil.isNotEmpty(array)) {
			for (T t : array) {
				consumer.accept(t);
			}
		}
	}

	private void _processClause(
		Clause clause, SearchRequestBuilder searchRequestBuilder) {

		searchRequestBuilder.addComplexQueryPart(
			_complexQueryPartBuilderFactory.builder(
			).occur(
				clause.getOccur()
			).query(
				_queries.wrapper(
					JSONUtil.toString((JSONObject)clause.getQuery()))
			).build());
	}

	private void _processQueryEntry(
		QueryEntry queryEntry, SearchRequestBuilder searchRequestBuilder) {

		if (!GetterUtil.getBoolean(queryEntry.getEnabled())) {
			return;
		}

		_isNotEmptyForEach(
			queryEntry.getClauses(),
			clause -> _processClause(clause, searchRequestBuilder));
	}

	private final ComplexQueryPartBuilderFactory
		_complexQueryPartBuilderFactory;
	private final Queries _queries;

}