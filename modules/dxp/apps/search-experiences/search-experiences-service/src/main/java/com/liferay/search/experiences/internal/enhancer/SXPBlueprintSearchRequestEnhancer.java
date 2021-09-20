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

package com.liferay.search.experiences.internal.enhancer;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.filter.ComplexQueryPartBuilderFactory;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.search.experiences.rest.dto.v1_0.Claus;
import com.liferay.search.experiences.rest.dto.v1_0.Configuration;
import com.liferay.search.experiences.rest.dto.v1_0.General;
import com.liferay.search.experiences.rest.dto.v1_0.Query;
import com.liferay.search.experiences.rest.dto.v1_0.SXPBlueprint;

/**
 * @author Petteri Karttunen
 */
public class SXPBlueprintSearchRequestEnhancer {

	public SXPBlueprintSearchRequestEnhancer(
		SearchRequestBuilder searchRequestBuilder, SXPBlueprint sxpBlueprint,
		ComplexQueryPartBuilderFactory complexQueryPartBuilderFactory,
		Queries queries) {

		_searchRequestBuilder = searchRequestBuilder;
		_complexQueryPartBuilderFactory = complexQueryPartBuilderFactory;
		_queries = queries;

		_configuration = sxpBlueprint.getConfiguration();
	}

	public void enhance() {
		processGeneral(_configuration.getGeneral());
		processQueries(_configuration.getQueries());
	}

	protected void processClause(Claus claus) {
		com.liferay.portal.search.query.Query query = toQuery(
			claus.getQueryJSON());

		if (query != null) {
			_searchRequestBuilder.addComplexQueryPart(
				_complexQueryPartBuilderFactory.builder(
				).query(
					query
				).occur(
					claus.getOccur()
				).build());
		}
	}

	protected void processClauses(Claus[] clauses) {
		for (Claus claus : clauses) {
			processClause(claus);
		}
	}

	protected void processGeneral(General general) {
		if (general.getApplyIndexerClauses() != null) {
			_searchRequestBuilder.withSearchContext(
				searchContext -> searchContext.setAttribute(
					"search.full.query.suppress.indexer.provided.clauses",
					!general.getApplyIndexerClauses()));
		}
	}

	protected void processQueries(Query[] queries) {
		for (Query query : queries) {
			processQuery(query);
		}
	}

	protected void processQuery(Query query) {
		if (GetterUtil.getBoolean(query.getEnabled())) {
			processClauses(query.getClauses());
		}
	}

	protected com.liferay.portal.search.query.Query toQuery(String queryJSON) {
		return _queries.wrapper(queryJSON);
	}

	private final ComplexQueryPartBuilderFactory
		_complexQueryPartBuilderFactory;
	private final Configuration _configuration;
	private final Queries _queries;
	private final SearchRequestBuilder _searchRequestBuilder;

}