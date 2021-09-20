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

package com.liferay.search.experiences.internal.blueprint.parameter;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.search.experiences.blueprint.keyword.KeywordProcessor;
import com.liferay.search.experiences.blueprint.parameter.IntegerSXPParameter;
import com.liferay.search.experiences.blueprint.parameter.SXPParameter;
import com.liferay.search.experiences.blueprint.parameter.SXPParameterContributionDefinition;
import com.liferay.search.experiences.blueprint.parameter.SXPParameterContributor;
import com.liferay.search.experiences.blueprint.parameter.SXPParameterData;
import com.liferay.search.experiences.blueprint.parameter.SXPParameterDataBuilder;
import com.liferay.search.experiences.blueprint.parameter.SXPParameterDataCreator;
import com.liferay.search.experiences.blueprint.parameter.StringSXPParameter;
import com.liferay.search.experiences.internal.blueprint.parameter.builder.SXPParameterBuilder;
import com.liferay.search.experiences.internal.blueprint.util.SearchContextUtil;
import com.liferay.search.experiences.internal.problem.ProblemUtil;
import com.liferay.search.experiences.model.SXPBlueprint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = SXPParameterDataCreator.class)
public class SXPParameterDataCreatorImpl implements SXPParameterDataCreator {

	@Override
	public SXPParameterData create(SearchRequestBuilder searchRequestBuilder, SXPBlueprint sxpBlueprint) {
 
		// TODO Placeholder for Parameter configuration DTO
		
		Optional<JSONObject> optional = Optional.empty();

		if (!optional.isPresent()) {
			return new SXPParameterDataImpl(
				StringPool.BLANK, new ArrayList<>());
		}

		SXPParameterDataBuilder sxpParameterDataBuilder =
			new SXPParameterDataBuilderImpl();

		JSONObject jsonObject = optional.get();

		_addKeywordParameter(
			searchRequestBuilder, sxpBlueprint, sxpParameterDataBuilder);

		_addPagingParameter(jsonObject.getJSONObject("page"), searchRequestBuilder, sxpBlueprint, sxpParameterDataBuilder);

		_addSizeParameter(jsonObject.getJSONObject("size"), searchRequestBuilder, sxpBlueprint, sxpParameterDataBuilder);

		_addSortParameters(searchRequestBuilder, sxpBlueprint, sxpParameterDataBuilder);

		_executeParameterContributors(searchRequestBuilder, sxpBlueprint, sxpParameterDataBuilder);

		_addCustomParameters(jsonObject.getJSONArray("custom"), searchRequestBuilder, sxpParameterDataBuilder);

		SXPParameterData sxpParameterData = sxpParameterDataBuilder.build();

		_logParameters(sxpParameterData);

		return sxpParameterData;
	}

	@Override
	public Map<String, List<SXPParameterContributionDefinition>> getSXPParameterContributionDefinitions() {

		Map<String, List<SXPParameterContributionDefinition>>
			parameterContributionDefinitions = new HashMap<>();

		Set<String> keySet = _sxpParameterContributorServiceTrackerMap.keySet();

		keySet.forEach(
			name -> {
				SXPParameterContributor sxpParameterContributor =
					_sxpParameterContributorServiceTrackerMap.getService(name);

				parameterContributionDefinitions.put(
						sxpParameterContributor.getCategoryNameKey(),
						sxpParameterContributor.getSXPParameterContributionDefinitions());
			});

		return parameterContributionDefinitions;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_keywordProcessorServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, KeywordProcessor.class, "name");

		_sxpParameterContributorServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, SXPParameterContributor.class, "name");
	}

	@Deactivate
	protected void deactivate() {
		_keywordProcessorServiceTrackerMap.close();

		_sxpParameterContributorServiceTrackerMap.close();
	}

	private void _addCustomParameter(
		JSONObject jsonObject,
			SearchRequestBuilder searchRequestBuilder,
		SXPParameterDataBuilder sxpParameterDataBuilder) {

		String type = jsonObject.getString("type");

		try {
			SXPParameterBuilder sxpPrameterBuilder =
				_sxpParameterBuilderFactory.getBuilder(type);

			Optional<SXPParameter> optional = sxpPrameterBuilder.build(jsonObject, searchRequestBuilder);

			if (optional.isPresent()) {
				sxpParameterDataBuilder.addSXPParameter(optional.get());
			}
		}
		catch (IllegalArgumentException illegalArgumentException) {
			_log.error(illegalArgumentException);

			ProblemUtil.addInvalidConfigurationValueError(
				getClass().getName(), jsonObject, "type", type,
				illegalArgumentException);
		}
	}

	private void _addCustomParameters(
		JSONArray jsonArray,
		SearchRequestBuilder searchRequestBuilder, 
		SXPParameterDataBuilder sxpParameterDataBuilder
		) {

		if ((jsonArray == null) ||
			(jsonArray.length() == 0)) {

			return;
		}

		for (int i = 0; i < jsonArray.length(); i++) {
			_addCustomParameter(jsonArray.getJSONObject(i), searchRequestBuilder, sxpParameterDataBuilder);
		}
	}


	private void _addKeywordParameter(
			SearchRequestBuilder searchRequestBuilder, SXPBlueprint sxpBlueprint, 
			SXPParameterDataBuilder sxpParameterDataBuilder) {

		String keywords = SearchContextUtil.getKeywords(searchRequestBuilder);

		sxpParameterDataBuilder.addSXPParameter(
			new StringSXPParameter("keywords.raw", true, keywords));

		keywords = _executeKeywordProcessors(keywords, searchRequestBuilder, sxpBlueprint);

		sxpParameterDataBuilder.addSXPParameter(
			new StringSXPParameter("keywords", true, keywords));

		sxpParameterDataBuilder.keywords(keywords);
	}

	private void _addPagingParameter(
			JSONObject jsonObject,
			SearchRequestBuilder searchRequestBuilder, SXPBlueprint sxpBlueprint, 
			SXPParameterDataBuilder sxpParameterDataBuilder) {

		String parameterName = jsonObject.getString("parameter_name");

		Integer page = SearchContextUtil.getIntegerAttribute(
					parameterName,
					searchRequestBuilder);

		if (Validator.isNotNull(page)) {
			sxpParameterDataBuilder.addSXPParameter(
				new IntegerSXPParameter(parameterName, true, page));
		}
	}

	private void _addSizeParameter(
			JSONObject jsonObject, SearchRequestBuilder searchRequestBuilder, SXPBlueprint sxpBlueprint, 
			SXPParameterDataBuilder sxpParameterDataBuilder) {

		SXPParameterBuilder sxpParameterBuilder =
			_sxpParameterBuilderFactory.getBuilder("integer");

		Optional<SXPParameter> optional = sxpParameterBuilder.build(jsonObject, searchRequestBuilder);

		if (!optional.isPresent()) {
			return;
		}

		SXPParameter sxpParameter = optional.get();
		
		int size = GetterUtil.getInteger(sxpParameter.getValue());

		sxpParameterDataBuilder.addSXPParameter(
			new IntegerSXPParameter(
				"size", true,
				size));
	}

	private void _addSortParameters(
		SearchRequestBuilder searchRequestBuilder,
		
		SXPBlueprint sxpBlueprint, SXPParameterDataBuilder sxpParameterDataBuilder) {
		
		// TODO Placeholder for Sort configuration DTO
		
		Optional<JSONObject> jsonObjectOptional = Optional.empty();

		if (!jsonObjectOptional.isPresent()) {
			return;
		}

		JSONObject jsonObject = jsonObjectOptional.get();

		Set<String> keySet = jsonObject.keySet();

		keySet.forEach(
			key -> {
				SXPParameterBuilder sxpParameterBuilder =
					_sxpParameterBuilderFactory.getBuilder("string");

				Optional<SXPParameter> parameterOptional =
					sxpParameterBuilder.build(jsonObject.getJSONObject(key), searchRequestBuilder);

				if (parameterOptional.isPresent()) {
					sxpParameterDataBuilder.addSXPParameter(
						parameterOptional.get());
				}
			});
	}

	private String _executeKeywordProcessors(
		String keywords, SearchRequestBuilder searchRequestBuilder, 
		SXPBlueprint sxpBlueprint) {

		Set<String> keySet = _keywordProcessorServiceTrackerMap.keySet();

		for (String name : keySet) {
			KeywordProcessor keywordsProcessor =
				_keywordProcessorServiceTrackerMap.getService(name);

			keywords = keywordsProcessor.process(keywords, searchRequestBuilder);
		}

		return keywords;
	}

	private void _executeParameterContributors(
		SearchRequestBuilder searchRequestBuilder, SXPBlueprint sxpBlueprint, SXPParameterDataBuilder sxpParameterDataBuilder) {

		Set<String> keySet = _sxpParameterContributorServiceTrackerMap.keySet();

		keySet.forEach(
			name -> {
				SXPParameterContributor parameterContributor =
					_sxpParameterContributorServiceTrackerMap.getService(name);

				parameterContributor.contribute(
					searchRequestBuilder, sxpBlueprint, sxpParameterDataBuilder);
			});
	}

	private void _logParameters(SXPParameterData sxpParameterData) {
		if (_log.isDebugEnabled()) {
			_log.debug(
				"Available template variables after parameter contributions:");

			List<SXPParameter> parameters = sxpParameterData.getSXPParameters();

			if (!parameters.isEmpty()) {
				for (SXPParameter parameter : parameters) {
					_log.debug(parameter.toString());
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SXPParameterDataCreatorImpl.class);

	private ServiceTrackerMap<String, KeywordProcessor>
		_keywordProcessorServiceTrackerMap;

	@Reference
	private SXPParameterBuilderFactory _sxpParameterBuilderFactory;

	private ServiceTrackerMap<String, SXPParameterContributor>
		_sxpParameterContributorServiceTrackerMap;

}