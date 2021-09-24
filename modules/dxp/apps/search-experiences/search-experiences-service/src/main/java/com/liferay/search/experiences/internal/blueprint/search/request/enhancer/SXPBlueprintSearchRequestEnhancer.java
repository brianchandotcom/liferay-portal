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

package com.liferay.search.experiences.internal.blueprint.search.request.enhancer;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.metrics.CardinalityAggregation;
import com.liferay.portal.search.filter.ComplexQueryPartBuilderFactory;
import com.liferay.portal.search.geolocation.GeoBuilders;
import com.liferay.portal.search.highlight.FieldConfigBuilderFactory;
import com.liferay.portal.search.highlight.HighlightBuilderFactory;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.script.Scripts;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.search.experiences.blueprint.parameter.SXPParameter;
import com.liferay.search.experiences.internal.blueprint.parameter.SXPParameterData;
import com.liferay.search.experiences.internal.blueprint.parameter.SXPParameterDataCreator;
import com.liferay.search.experiences.internal.blueprint.search.request.body.contributor.AggsSXPSearchRequestBodyContributor;
import com.liferay.search.experiences.internal.blueprint.search.request.body.contributor.HighlightSXPSearchRequestBodyContributor;
import com.liferay.search.experiences.internal.blueprint.search.request.body.contributor.QuerySXPSearchRequestBodyContributor;
import com.liferay.search.experiences.internal.blueprint.search.request.body.contributor.SXPSearchRequestBodyContributor;
import com.liferay.search.experiences.internal.blueprint.search.request.body.contributor.SortSXPSearchRequestBodyContributor;
import com.liferay.search.experiences.internal.blueprint.search.request.body.contributor.SuggestSXPSearchRequestBodyContributor;
import com.liferay.search.experiences.rest.dto.v1_0.Aggregation;
import com.liferay.search.experiences.rest.dto.v1_0.Avg;
import com.liferay.search.experiences.rest.dto.v1_0.Cardinality;
import com.liferay.search.experiences.rest.dto.v1_0.Clause;
import com.liferay.search.experiences.rest.dto.v1_0.Configuration;
import com.liferay.search.experiences.rest.dto.v1_0.General;
import com.liferay.search.experiences.rest.dto.v1_0.Query;
import com.liferay.search.experiences.rest.dto.v1_0.SXPBlueprint;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = SXPBlueprintSearchRequestEnhancer.class)
public class SXPBlueprintSearchRequestEnhancer {

	public void enhance(
		SearchRequestBuilder searchRequestBuilder, SXPBlueprint sxpBlueprint) {

		SXPParameterData sxpParameterData = _sxpParameterDataCreator.create(
			searchRequestBuilder.withSearchContextGet(
				searchContext -> searchContext),
			sxpBlueprint);

		// TODO From and size

		searchRequestBuilder.emptySearchEnabled(
			true
		).excludeContributors(
			"com.liferay.search.experiences.blueprint"
		).explain(
			_isExplain(sxpParameterData)
		).includeResponseString(
			_isIncludeResponseString(sxpParameterData)
		);

		Configuration configuration = sxpBlueprint.getConfiguration();

		_processAggregations(
			configuration.getAggregations(), searchRequestBuilder);
		_processGeneral(configuration.getGeneral(), searchRequestBuilder);
		_processQueries(configuration.getQueries(), searchRequestBuilder);

		_contributeSXPSearchRequestBodyContributors(
			searchRequestBuilder, sxpBlueprint, sxpParameterData);
	}

	@Activate
	protected void activate() {
		_sxpSearchRequestBodyContributors = Arrays.asList(
			new AggsSXPSearchRequestBodyContributor(),
			new HighlightSXPSearchRequestBodyContributor(
				_fieldConfigBuilderFactory, _highlightBuilderFactory),
			new QuerySXPSearchRequestBodyContributor(),
			new SuggestSXPSearchRequestBodyContributor(),
			new SortSXPSearchRequestBodyContributor(
				_geoBuilders, _queries, _scripts, _sorts));
	}

	private void _contributeSXPSearchRequestBodyContributors(
		SearchRequestBuilder searchRequestBuilder, SXPBlueprint sxpBlueprint,
		SXPParameterData sxpParameterData) {

		SXPParameter sxpParameter = sxpParameterData.getSXPParameterByName(
			"system.excluded_search_request_body_contributors");

		String[] names = null;

		if (sxpParameter != null) {
			names = (String[])sxpParameter.getValue();
		}

		if (ListUtil.isEmpty(_sxpSearchRequestBodyContributors)) {
			return;
		}

		for (SXPSearchRequestBodyContributor sxpSearchRequestBodyContributor :
				_sxpSearchRequestBodyContributors) {

			if (!ArrayUtil.contains(
					names, sxpSearchRequestBodyContributor.getName())) {

				sxpSearchRequestBodyContributor.contribute(
					searchRequestBuilder, sxpBlueprint, sxpParameterData);
			}
		}
	}

	private boolean _isExplain(SXPParameterData sxpParameterData) {
		SXPParameter sxpParameter = sxpParameterData.getSXPParameterByName(
			"system.explain");

		if (sxpParameter == null) {
			return false;
		}

		return GetterUtil.getBoolean(sxpParameter.getValue());
	}

	private boolean _isIncludeResponseString(
		SXPParameterData sxpParameterData) {

		SXPParameter sxpParameter = sxpParameterData.getSXPParameterByName(
			"system.include_response_string");

		if (sxpParameter == null) {
			return false;
		}

		return GetterUtil.getBoolean(sxpParameter.getValue());
	}

	private void _processAggregations(
		Map<String, Aggregation> aggregations,
		SearchRequestBuilder searchRequestBuilder) {

		if (MapUtil.isEmpty(aggregations)) {
			return;
		}

		for (Map.Entry<String, Aggregation> entry : aggregations.entrySet()) {
			searchRequestBuilder.addAggregation(
				_toPortalSearchAggregation(entry.getValue(), entry.getKey()));
		}
	}

	private void _processClause(
		Clause clause, SearchRequestBuilder searchRequestBuilder) {

		com.liferay.portal.search.query.Query query = _queries.wrapper(
			clause.getQueryJSON());

		if (query != null) {
			searchRequestBuilder.addComplexQueryPart(
				_complexQueryPartBuilderFactory.builder(
				).occur(
					clause.getOccur()
				).query(
					query
				).build());
		}
	}

	private void _processGeneral(
		General general, SearchRequestBuilder searchRequestBuilder) {

		if (general == null) {
			return;
		}

		if (general.getApplyIndexerClauses() != null) {
			searchRequestBuilder.withSearchContext(
				searchContext -> searchContext.setAttribute(
					"search.full.query.suppress.indexer.provided.clauses",
					!general.getApplyIndexerClauses()));
		}
	}

	private void _processQueries(
		Query[] queries, SearchRequestBuilder searchRequestBuilder) {

		if (ArrayUtil.isEmpty(queries)) {
			return;
		}

		for (Query query : queries) {
			if (!GetterUtil.getBoolean(query.getEnabled())) {
				continue;
			}

			Clause[] clauses = query.getClauses();

			if (ArrayUtil.isEmpty(clauses)) {
				continue;
			}

			for (Clause clause : clauses) {
				_processClause(clause, searchRequestBuilder);
			}
		}
	}

	private com.liferay.portal.search.aggregation.Aggregation
		_toPortalSearchAggregation(Aggregation aggregation1, String name1) {

		com.liferay.portal.search.aggregation.Aggregation
			portalSearchAggregation = null;

		if (aggregation1.getAvg() != null) {
			Avg avg = aggregation1.getAvg();

			portalSearchAggregation = _aggregations.avg(name1, avg.getField());
		}
		else if (aggregation1.getCardinality() != null) {
			Cardinality cardinality = aggregation1.getCardinality();

			CardinalityAggregation cardinalityAggregation =
				_aggregations.cardinality(name1, cardinality.getField());

			cardinalityAggregation.setPrecisionThreshold(
				cardinality.getPrecision_threshold());

			portalSearchAggregation = cardinalityAggregation;
		}
		else {
			throw new IllegalArgumentException();
		}

		if (MapUtil.isEmpty(aggregation1.getAggs())) {
			return portalSearchAggregation;
		}

		Map<String, Aggregation> aggs = aggregation1.getAggs();

		for (Map.Entry<String, Aggregation> entry : aggs.entrySet()) {
			portalSearchAggregation.addChildAggregation(
				_toPortalSearchAggregation(entry.getValue(), entry.getKey()));
		}

		return portalSearchAggregation;
	}

	@Reference
	private Aggregations _aggregations;

	@Reference
	private ComplexQueryPartBuilderFactory _complexQueryPartBuilderFactory;

	@Reference
	private FieldConfigBuilderFactory _fieldConfigBuilderFactory;

	@Reference
	private GeoBuilders _geoBuilders;

	@Reference
	private HighlightBuilderFactory _highlightBuilderFactory;

	@Reference
	private Queries _queries;

	@Reference
	private Scripts _scripts;

	@Reference
	private Sorts _sorts;

	@Reference
	private SXPParameterDataCreator _sxpParameterDataCreator;

	private List<SXPSearchRequestBodyContributor>
		_sxpSearchRequestBodyContributors;

}