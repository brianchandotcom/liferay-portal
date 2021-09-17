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

package com.liferay.search.experiences.internal.parameter;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.search.experiences.attributes.SXPAttributes;
import com.liferay.search.experiences.internal.attributes.util.SXPAttributeValueHelper;
import com.liferay.search.experiences.internal.parameter.builder.SXPParameterBuilder;
import com.liferay.search.experiences.internal.problem.ProblemUtil;
import com.liferay.search.experiences.keyword.KeywordProcessor;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.parameter.IntegerParameter;
import com.liferay.search.experiences.parameter.SXPParameter;
import com.liferay.search.experiences.parameter.SXPParameterContributor;
import com.liferay.search.experiences.parameter.SXPParameterData;
import com.liferay.search.experiences.parameter.SXPParameterDataBuilder;
import com.liferay.search.experiences.parameter.SXPParameterDataCreator;
import com.liferay.search.experiences.parameter.SXPParameterDefinition;
import com.liferay.search.experiences.parameter.StringParameter;
import com.liferay.search.experiences.util.SXPBlueprintConfigurationsJSONHelper;

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
	public SXPParameterData create(
		SXPBlueprint sxpBlueprint, SXPAttributes sxpAttributes) {

		Optional<JSONObject> optional =
			sxpBlueprintConfigurationsJSONHelper.
				getParameterConfigurationOptional(sxpBlueprint);

		if (!optional.isPresent()) {
			return new SXPParameterDataImpl(
				StringPool.BLANK, new ArrayList<>());
		}

		SXPParameterDataBuilder sxpParameterDataBuilder =
			new SXPParameterDataBuilderImpl();

		JSONObject jsonObject = optional.get();

		_addKeywordParameter(
			sxpParameterDataBuilder, sxpBlueprint, sxpAttributes);

		_addPagingParameter(
			sxpParameterDataBuilder, sxpAttributes,
			jsonObject.getJSONObject("page"));

		_addSizeParameter(
			sxpParameterDataBuilder, sxpAttributes,
			jsonObject.getJSONObject("size"));

		_addSortParameters(
			sxpParameterDataBuilder, sxpBlueprint, sxpAttributes);

		_executeParameterContributors(
			sxpParameterDataBuilder, sxpBlueprint, sxpAttributes);

		_addCustomParameters(
			sxpParameterDataBuilder, jsonObject.getJSONArray("custom"),
			sxpAttributes);

		SXPParameterData sxpParameterData = sxpParameterDataBuilder.build();

		_logParameters(sxpParameterData);

		return sxpParameterData;
	}

	@Override
	public Map<String, List<SXPParameterDefinition>>
		getContributedParameterDefinitions() {

		Map<String, List<SXPParameterDefinition>>
			contributedParameterDefinitions = new HashMap<>();

		Set<String> keySet = _sxpParameterContributorServiceTrackerMap.keySet();

		keySet.forEach(
			name -> {
				SXPParameterContributor parameterContributor =
					_sxpParameterContributorServiceTrackerMap.getService(name);

				contributedParameterDefinitions.put(
					parameterContributor.getCategoryNameKey(),
					parameterContributor.getParameterDefinitions());
			});

		return contributedParameterDefinitions;
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
		SXPParameterDataBuilder sxpParameterDataBuilder, JSONObject jsonObject,
		SXPAttributes sxpAttributes) {

		String type = jsonObject.getString("type");

		try {
			SXPParameterBuilder sxpPrameterBuilder =
				_sxpParameterBuilderFactory.getBuilder(type);

			Optional<SXPParameter> optional = sxpPrameterBuilder.build(
				sxpAttributes, jsonObject);

			if (optional.isPresent()) {
				sxpParameterDataBuilder.addParameter(optional.get());
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
		SXPParameterDataBuilder sxpParameterDataBuilder,
		JSONArray configurationJSONArray, SXPAttributes sxpAttributes) {

		if ((configurationJSONArray == null) ||
			(configurationJSONArray.length() == 0)) {

			return;
		}

		for (int i = 0; i < configurationJSONArray.length(); i++) {
			_addCustomParameter(
				sxpParameterDataBuilder,
				configurationJSONArray.getJSONObject(i), sxpAttributes);
		}
	}

	private void _addKeywordParameter(
		SXPParameterDataBuilder sxpParameterDataBuilder,
		SXPBlueprint sxpBlueprint, SXPAttributes sxpAttributes) {

		String keywords = GetterUtil.getString(sxpAttributes.getKeywords());

		sxpParameterDataBuilder.addParameter(
			new StringParameter("keywords.raw", "${keywords.raw}", keywords));

		keywords = _executeKeywordProcessors(
			keywords, sxpBlueprint, sxpAttributes);

		sxpParameterDataBuilder.addParameter(
			new StringParameter("keywords", "${keywords}", keywords));

		sxpParameterDataBuilder.keywords(keywords);
	}

	private void _addPagingParameter(
		SXPParameterDataBuilder sxpParameterDataBuilder,
		SXPAttributes sxpAttributes, JSONObject jsonObject) {

		String parameterName = jsonObject.getString("parameter_name");

		Optional<Integer> optional =
			_sxpAttributeValuesHelper.getIntegerOptional(
				sxpAttributes, parameterName);

		if (optional.isPresent()) {
			sxpParameterDataBuilder.addParameter(
				new IntegerParameter("page", "${page}", optional.get()));
		}
	}

	private void _addSizeParameter(
		SXPParameterDataBuilder sxpParameterDataBuilder,
		SXPAttributes sxpAttributes, JSONObject jsonObject) {

		SXPParameterBuilder sxpParameterBuilder =
			_sxpParameterBuilderFactory.getBuilder("integer");

		Optional<SXPParameter> optional = sxpParameterBuilder.build(
			sxpAttributes, jsonObject);

		if (optional.isPresent()) {
			SXPParameter sxpParameter = optional.get();

			sxpParameterDataBuilder.addParameter(
				new IntegerParameter(
					"size", "${size}",
					GetterUtil.getInteger(sxpParameter.getValue())));
		}
	}

	private void _addSortParameters(
		SXPParameterDataBuilder sxpParameterDataBuilder,
		SXPBlueprint sxpBlueprint, SXPAttributes sxpAttributes) {

		Optional<JSONObject> jsonObjectOptional =
			sxpBlueprintConfigurationsJSONHelper.
				getSortParameterConfigurationOptional(sxpBlueprint);

		if (!jsonObjectOptional.isPresent()) {
			return;
		}

		JSONObject jsonObject = jsonObjectOptional.get();

		Set<String> keySet = jsonObject.keySet();

		keySet.forEach(
			key -> {
				JSONObject sortJSONObject = jsonObject.getJSONObject(key);

				SXPParameterBuilder sxpParameterBuilder =
					_sxpParameterBuilderFactory.getBuilder("string");

				Optional<SXPParameter> parameterOptional =
					sxpParameterBuilder.build(sxpAttributes, sortJSONObject);

				if (parameterOptional.isPresent()) {
					sxpParameterDataBuilder.addParameter(
						parameterOptional.get());
				}
			});
	}

	private String _executeKeywordProcessors(
		String keywords, SXPBlueprint sxpBlueprint,
		SXPAttributes sxpAttributes) {

		Set<String> keySet = _keywordProcessorServiceTrackerMap.keySet();

		for (String name : keySet) {
			KeywordProcessor keywordsProcessor =
				_keywordProcessorServiceTrackerMap.getService(name);

			keywords = keywordsProcessor.process(keywords, sxpAttributes);
		}

		return keywords;
	}

	private void _executeParameterContributors(
		SXPParameterDataBuilder sxpParameterDataBuilder,
		SXPBlueprint sxpBlueprint, SXPAttributes sxpAttributes) {

		Set<String> keySet = _sxpParameterContributorServiceTrackerMap.keySet();

		keySet.forEach(
			name -> {
				SXPParameterContributor parameterContributor =
					_sxpParameterContributorServiceTrackerMap.getService(name);

				parameterContributor.contribute(
					sxpParameterDataBuilder, sxpAttributes, sxpBlueprint);
			});
	}

	private void _logParameters(SXPParameterData sxpParameterData) {
		if (_log.isDebugEnabled()) {
			_log.debug(
				"Available template variables after parameter contributions:");

			List<SXPParameter> parameters = sxpParameterData.getParameters();

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
	private SXPAttributeValueHelper _sxpAttributeValuesHelper;

	@Reference
	private SXPParameterBuilderFactory _sxpParameterBuilderFactory;

	private ServiceTrackerMap<String, SXPParameterContributor>
		_sxpParameterContributorServiceTrackerMap;

	@Reference
	private SXPBlueprintConfigurationsJSONHelper
		sxpBlueprintConfigurationsJSONHelper;

}