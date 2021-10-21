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
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.filter.ComplexQueryPartBuilderFactory;
import com.liferay.portal.search.geolocation.GeoBuilders;
import com.liferay.portal.search.highlight.FieldConfigBuilderFactory;
import com.liferay.portal.search.highlight.HighlightBuilderFactory;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.rescore.RescoreBuilderFactory;
import com.liferay.portal.search.script.Scripts;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.significance.SignificanceHeuristics;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.search.experiences.blueprint.parameter.SXPParameter;
import com.liferay.search.experiences.blueprint.search.request.enhancer.SXPBlueprintSearchRequestEnhancer;
import com.liferay.search.experiences.internal.blueprint.highlight.HighlightConverter;
import com.liferay.search.experiences.internal.blueprint.parameter.SXPParameterData;
import com.liferay.search.experiences.internal.blueprint.parameter.SXPParameterDataCreator;
import com.liferay.search.experiences.internal.blueprint.parameter.SXPParameterParser;
import com.liferay.search.experiences.internal.blueprint.query.QueryConverter;
import com.liferay.search.experiences.internal.blueprint.script.ScriptConverter;
import com.liferay.search.experiences.internal.blueprint.search.request.body.contributor.AggsSXPSearchRequestBodyContributor;
import com.liferay.search.experiences.internal.blueprint.search.request.body.contributor.GeneralSXPSearchRequestBodyContributor;
import com.liferay.search.experiences.internal.blueprint.search.request.body.contributor.HighlightSXPSearchRequestBodyContributor;
import com.liferay.search.experiences.internal.blueprint.search.request.body.contributor.QuerySXPSearchRequestBodyContributor;
import com.liferay.search.experiences.internal.blueprint.search.request.body.contributor.SXPSearchRequestBodyContributor;
import com.liferay.search.experiences.internal.blueprint.search.request.body.contributor.SortSXPSearchRequestBodyContributor;
import com.liferay.search.experiences.internal.blueprint.search.request.body.contributor.SuggestSXPSearchRequestBodyContributor;
import com.liferay.search.experiences.rest.dto.v1_0.SXPBlueprint;
import com.liferay.search.experiences.rest.dto.v1_0.util.ConfigurationUtil;
import com.liferay.search.experiences.rest.dto.v1_0.util.SXPBlueprintUtil;

import java.util.Arrays;
import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = SXPBlueprintSearchRequestEnhancer.class)
public class SXPBlueprintSearchRequestEnhancerImpl
	implements SXPBlueprintSearchRequestEnhancer {

	@Override
	public void enhance(
		SearchRequestBuilder searchRequestBuilder,
		com.liferay.search.experiences.model.SXPBlueprint sxpBlueprint) {

		_enhance(
			searchRequestBuilder,
			SXPBlueprintUtil.toSXPBlueprint(sxpBlueprint));
	}

	@Override
	public void enhance(
		SearchRequestBuilder searchRequestBuilder, String sxpBlueprintJSON) {

		_enhance(
			searchRequestBuilder,
			SXPBlueprintUtil.toSXPBlueprint(sxpBlueprintJSON));
	}

	@Activate
	protected void activate() {
		HighlightConverter highlightConverter = new HighlightConverter(
			_fieldConfigBuilderFactory, _highlightBuilderFactory);
		QueryConverter queryConverter = new QueryConverter(_queries);
		ScriptConverter scriptConverter = new ScriptConverter(_scripts);

		_sxpSearchRequestBodyContributors = Arrays.asList(
			new AggsSXPSearchRequestBodyContributor(
				_aggregations, _geoBuilders, highlightConverter, queryConverter,
				scriptConverter, _significanceHeuristics, _sorts),
			new GeneralSXPSearchRequestBodyContributor(),
			new HighlightSXPSearchRequestBodyContributor(highlightConverter),
			new QuerySXPSearchRequestBodyContributor(
				_complexQueryPartBuilderFactory, queryConverter,
				_rescoreBuilderFactory),
			new SuggestSXPSearchRequestBodyContributor(),
			new SortSXPSearchRequestBodyContributor(
				_geoBuilders, queryConverter, scriptConverter, _sorts));
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

		RuntimeException runtimeException = new RuntimeException();

		for (SXPSearchRequestBodyContributor sxpSearchRequestBodyContributor :
				_sxpSearchRequestBodyContributors) {

			if (ArrayUtil.contains(
					names, sxpSearchRequestBodyContributor.getName())) {

				continue;
			}

			try {
				sxpSearchRequestBodyContributor.contribute(
					searchRequestBuilder, sxpBlueprint, sxpParameterData);
			}
			catch (Exception exception) {
				runtimeException.addSuppressed(exception);
			}
		}

		if (ArrayUtil.isNotEmpty(runtimeException.getSuppressed())) {
			throw runtimeException;
		}
	}

	private void _enhance(
		SearchRequestBuilder searchRequestBuilder, SXPBlueprint sxpBlueprint) {

		if (sxpBlueprint.getConfiguration() == null) {
			return;
		}

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

		_contributeSXPSearchRequestBodyContributors(
			searchRequestBuilder, _expand(sxpBlueprint, sxpParameterData),
			sxpParameterData);
	}

	private SXPBlueprint _expand(
		SXPBlueprint sxpBlueprint1, SXPParameterData sxpParameterData) {

		SXPBlueprint sxpBlueprint2 = SXPBlueprint.toDTO(
			String.valueOf(sxpBlueprint1));

		sxpBlueprint2.setConfiguration(
			ConfigurationUtil.toConfiguration(
				SXPParameterParser.parse(
					String.valueOf(sxpBlueprint1.getConfiguration()),
					sxpParameterData)));

		return sxpBlueprint2;
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
	private RescoreBuilderFactory _rescoreBuilderFactory;

	@Reference
	private Scripts _scripts;

	@Reference
	private SignificanceHeuristics _significanceHeuristics;

	@Reference
	private Sorts _sorts;

	@Reference
	private SXPParameterDataCreator _sxpParameterDataCreator;

	private List<SXPSearchRequestBodyContributor>
		_sxpSearchRequestBodyContributors;

}