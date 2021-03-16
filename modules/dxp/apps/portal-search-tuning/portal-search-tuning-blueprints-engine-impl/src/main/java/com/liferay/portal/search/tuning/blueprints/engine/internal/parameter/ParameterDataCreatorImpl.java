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

package com.liferay.portal.search.tuning.blueprints.engine.internal.parameter;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.tuning.blueprints.attributes.BlueprintsAttributes;
import com.liferay.portal.search.tuning.blueprints.constants.json.keys.parameter.CustomParameterConfigurationKeys;
import com.liferay.portal.search.tuning.blueprints.constants.json.keys.parameter.PageConfigurationKeys;
import com.liferay.portal.search.tuning.blueprints.constants.json.keys.parameter.ParameterConfigurationKeys;
import com.liferay.portal.search.tuning.blueprints.constants.json.keys.sort.SortConfigurationKeys;
import com.liferay.portal.search.tuning.blueprints.engine.component.ServiceComponentReference;
import com.liferay.portal.search.tuning.blueprints.engine.constants.ReservedParameterNames;
import com.liferay.portal.search.tuning.blueprints.engine.internal.parameter.builder.ParameterBuilder;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.BooleanParameter;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.IntegerParameter;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.Parameter;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.ParameterData;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.ParameterDataBuilder;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.ParameterDataCreator;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.ParameterDefinition;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.StringParameter;
import com.liferay.portal.search.tuning.blueprints.engine.spi.keywords.KeywordsProcessor;
import com.liferay.portal.search.tuning.blueprints.engine.spi.parameter.ParameterContributor;
import com.liferay.portal.search.tuning.blueprints.message.Message;
import com.liferay.portal.search.tuning.blueprints.message.Messages;
import com.liferay.portal.search.tuning.blueprints.message.Severity;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;
import com.liferay.portal.search.tuning.blueprints.util.BlueprintHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = ParameterDataCreator.class)
public class ParameterDataCreatorImpl implements ParameterDataCreator {

	@Override
	public ParameterData create(
		Blueprint blueprint, BlueprintsAttributes blueprintsAttributes,
		Messages messages) {

		Optional<JSONObject> parameterConfigurationJSONObjectOptional =
			_blueprintHelper.getParameterConfigurationOptional(blueprint);

		if (!parameterConfigurationJSONObjectOptional.isPresent()) {
			return new ParameterDataImpl(StringPool.BLANK, new ArrayList<>());
		}

		ParameterDataBuilder parameterDataBuilder =
			new ParameterDataBuilderImpl();

		JSONObject parameterConfigurationJSONObject =
			parameterConfigurationJSONObjectOptional.get();

		_addExplainParameter(parameterDataBuilder, blueprintsAttributes);

		_addKeywordParameter(
			parameterDataBuilder, blueprint, blueprintsAttributes, messages);

		_addPagingParameter(
			parameterDataBuilder, blueprintsAttributes,
			parameterConfigurationJSONObject.getJSONObject(
				ParameterConfigurationKeys.PAGE.getJsonKey()));

		_addSizeParameter(
			parameterDataBuilder, blueprintsAttributes,
			parameterConfigurationJSONObject.getJSONObject(
				ParameterConfigurationKeys.SIZE.getJsonKey()),
			messages);

		_addSortParameters(
			parameterDataBuilder, blueprint, blueprintsAttributes, messages);

		_addCustomParameters(
			parameterDataBuilder, blueprintsAttributes,
			parameterConfigurationJSONObject.getJSONArray(
				ParameterConfigurationKeys.CUSTOM.getJsonKey()),
			messages);

		_executeParameterContributors(
			parameterDataBuilder, blueprint, blueprintsAttributes, messages);

		ParameterData parameterData = parameterDataBuilder.build();

		_logParameters(parameterData);

		return parameterData;
	}

	@Override
	public Map<String, List<ParameterDefinition>>
		getContributedParameterDefinitions() {

		Map<String, List<ParameterDefinition>> contributedParameterDefinitions =
			new HashMap<>();

		for (Map.Entry<String, ServiceComponentReference<ParameterContributor>>
				entry : _parameterContributors.entrySet()) {

			ServiceComponentReference<ParameterContributor> value =
				entry.getValue();

			ParameterContributor parameterContributor =
				value.getServiceComponent();

			contributedParameterDefinitions.put(
				parameterContributor.getCategoryNameKey(),
				parameterContributor.getParameterDefinitions());
		}

		return contributedParameterDefinitions;
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void registerKeywordsProcessor(
		KeywordsProcessor keywordsProcessor, Map<String, Object> properties) {

		String name = (String)properties.get("name");

		if (Validator.isBlank(name)) {
			if (_log.isWarnEnabled()) {
				Class<?> clazz = keywordsProcessor.getClass();

				_log.warn(
					"Unable to register keywords processor " + clazz.getName() +
						". Name property empty.");
			}

			return;
		}

		int serviceRanking = GetterUtil.get(
			properties.get("service.ranking"), 0);

		ServiceComponentReference<KeywordsProcessor> serviceComponentReference =
			new ServiceComponentReference<>(keywordsProcessor, serviceRanking);

		if (_keywordsProcessors.containsKey(name)) {
			ServiceComponentReference<KeywordsProcessor> previousReference =
				_keywordsProcessors.get(name);

			if (previousReference.compareTo(serviceComponentReference) < 0) {
				_keywordsProcessors.put(name, serviceComponentReference);
			}
		}
		else {
			_keywordsProcessors.put(name, serviceComponentReference);
		}
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void registerParameterContributor(
		ParameterContributor parameterContributor,
		Map<String, Object> properties) {

		String name = (String)properties.get("name");

		if (Validator.isBlank(name)) {
			if (_log.isWarnEnabled()) {
				Class<?> clazz = parameterContributor.getClass();

				_log.warn(
					"Unable to register parameter contributor " +
						clazz.getName() + ". Name property empty.");
			}

			return;
		}

		int serviceRanking = GetterUtil.get(
			properties.get("service.ranking"), 0);

		ServiceComponentReference<ParameterContributor>
			serviceComponentReference = new ServiceComponentReference<>(
				parameterContributor, serviceRanking);

		if (_parameterContributors.containsKey(name)) {
			ServiceComponentReference<ParameterContributor> previousReference =
				_parameterContributors.get(name);

			if (previousReference.compareTo(serviceComponentReference) < 0) {
				_parameterContributors.put(name, serviceComponentReference);
			}
		}
		else {
			_parameterContributors.put(name, serviceComponentReference);
		}
	}

	protected void unregisterKeywordsProcessor(
		KeywordsProcessor keywordsProcessor, Map<String, Object> properties) {

		String name = (String)properties.get("name");

		if (Validator.isBlank(name)) {
			return;
		}

		_keywordsProcessors.remove(name);
	}

	protected void unregisterParameterContributor(
		ParameterContributor parameterContributor,
		Map<String, Object> properties) {

		String name = (String)properties.get("name");

		if (Validator.isBlank(name)) {
			return;
		}

		_parameterContributors.remove(name);
	}

	private void _addCustomParameter(
		ParameterDataBuilder parameterDataBuilder,
		BlueprintsAttributes blueprintsAttributes,
		JSONObject configurationJSONObject, Messages messages) {

		String type = configurationJSONObject.getString(
			CustomParameterConfigurationKeys.TYPE.getJsonKey());

		try {
			ParameterBuilder parameterBuilder =
				_parameterBuilderFactory.getBuilder(type);

			Optional<Parameter> optional = parameterBuilder.build(
				blueprintsAttributes, configurationJSONObject, messages);

			if (optional.isPresent()) {
				parameterDataBuilder.addParameter(optional.get());
			}
		}
		catch (IllegalArgumentException illegalArgumentException) {
			messages.addMessage(
				new Message.Builder().className(
					getClass().getName()
				).localizationKey(
					"core.error.unknown-parameter-type"
				).msg(
					illegalArgumentException.getMessage()
				).rootObject(
					configurationJSONObject
				).rootProperty(
					CustomParameterConfigurationKeys.TYPE.getJsonKey()
				).severity(
					Severity.ERROR
				).throwable(
					illegalArgumentException
				).build());

			_log.error(
				illegalArgumentException.getMessage(),
				illegalArgumentException);
		}
	}

	private void _addCustomParameters(
		ParameterDataBuilder parameterDataBuilder,
		BlueprintsAttributes blueprintsAttributes,
		JSONArray configurationJSONArray, Messages messages) {

		if ((configurationJSONArray == null) ||
			(configurationJSONArray.length() == 0)) {

			return;
		}

		for (int i = 0; i < configurationJSONArray.length(); i++) {
			JSONObject configurationJSONObject =
				configurationJSONArray.getJSONObject(i);

			if (_validateCustomParameterConfiguration(
					configurationJSONObject, messages)) {

				_addCustomParameter(
					parameterDataBuilder, blueprintsAttributes,
					configurationJSONObject, messages);
			}
		}
	}

	private void _addExplainParameter(
		ParameterDataBuilder parameterDataBuilder,
		BlueprintsAttributes blueprintsAttributes) {

		Optional<Object> optional = blueprintsAttributes.getAttributeOptional(
			ReservedParameterNames.EXPLAIN.getKey());

		if (optional.isPresent()) {
			parameterDataBuilder.addParameter(
				new BooleanParameter(
					ReservedParameterNames.EXPLAIN.getKey(), "${explain}",
					GetterUtil.getBoolean(optional.get())));
		}
	}

	private void _addKeywordParameter(
		ParameterDataBuilder parameterDataBuilder, Blueprint blueprint,
		BlueprintsAttributes blueprintsAttributes, Messages messages) {

		String keywords = GetterUtil.getString(
			blueprintsAttributes.getKeywords());

		parameterDataBuilder.addParameter(
			new StringParameter("keywords.raw", "${keywords.raw}", keywords));

		keywords = _executeKeywordsProcessors(
			keywords, blueprint, blueprintsAttributes, messages);

		parameterDataBuilder.addParameter(
			new StringParameter("keywords", "${keywords}", keywords));

		parameterDataBuilder.keywords(keywords);
	}

	private void _addPagingParameter(
		ParameterDataBuilder parameterDataBuilder,
		BlueprintsAttributes blueprintsAttributes,
		JSONObject configurationJSONObject) {

		String parameterName = configurationJSONObject.getString(
			PageConfigurationKeys.PARAMETER_NAME.getJsonKey());

		Optional<Object> optional = blueprintsAttributes.getAttributeOptional(
			parameterName);

		if (optional.isPresent()) {
			parameterDataBuilder.addParameter(
				new IntegerParameter(
					"page", "${page}",
					GetterUtil.getInteger(optional.orElse(1))));
		}
	}

	private void _addSizeParameter(
		ParameterDataBuilder parameterDataBuilder,
		BlueprintsAttributes blueprintsAttributes,
		JSONObject configurationJSONObject, Messages messages) {

		ParameterBuilder parameterBuilder = _parameterBuilderFactory.getBuilder(
			"integer");

		Optional<Parameter> optional = parameterBuilder.build(
			blueprintsAttributes, configurationJSONObject, messages);

		if (optional.isPresent()) {
			Parameter parameter = optional.get();

			parameterDataBuilder.addParameter(
				new IntegerParameter(
					"size", "${size}",
					GetterUtil.getInteger(parameter.getValue())));
		}
	}

	private void _addSortParameters(
		ParameterDataBuilder parameterDataBuilder, Blueprint blueprint,
		BlueprintsAttributes blueprintsAttributes, Messages messages) {

		Optional<JSONArray> jsonArrayOptional =
			_blueprintHelper.getSortParameterConfigurationOptional(blueprint);

		if (!jsonArrayOptional.isPresent()) {
			return;
		}

		JSONArray jsonArray = jsonArrayOptional.get();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			if (_validateSortParameterConfiguration(jsonObject, messages)) {
				ParameterBuilder parameterBuilder =
					_parameterBuilderFactory.getBuilder("string");

				Optional<Parameter> parameterOptional = parameterBuilder.build(
					blueprintsAttributes, jsonObject, messages);

				if (parameterOptional.isPresent()) {
					parameterDataBuilder.addParameter(parameterOptional.get());
				}
			}
		}
	}

	private String _executeKeywordsProcessors(
		String keywords, Blueprint blueprint,
		BlueprintsAttributes blueprintsAttributes, Messages messages) {

		for (Map.Entry<String, ServiceComponentReference<KeywordsProcessor>>
				entry : _keywordsProcessors.entrySet()) {

			ServiceComponentReference<KeywordsProcessor> value =
				entry.getValue();

			KeywordsProcessor keywordsProcessor = value.getServiceComponent();

			keywords = keywordsProcessor.process(
				keywords, blueprint, blueprintsAttributes, messages);
		}

		return keywords;
	}

	private void _executeParameterContributors(
		ParameterDataBuilder parameterDataBuilder, Blueprint blueprint,
		BlueprintsAttributes blueprintsAttributes, Messages messages) {

		for (Map.Entry<String, ServiceComponentReference<ParameterContributor>>
				entry : _parameterContributors.entrySet()) {

			ServiceComponentReference<ParameterContributor> value =
				entry.getValue();

			ParameterContributor parameterContributor =
				value.getServiceComponent();

			parameterContributor.contribute(
				parameterDataBuilder, blueprint, blueprintsAttributes,
				messages);
		}
	}

	private void _logParameters(ParameterData parameterData) {
		if (_log.isDebugEnabled()) {
			_log.debug(
				"Available template variables after parameter contributions:");

			List<Parameter> parameters = parameterData.getParameters();

			if (!parameters.isEmpty()) {
				for (Parameter parameter : parameters) {
					_log.debug(parameter.toString());
				}
			}
		}
	}

	private boolean _validateCustomParameterConfiguration(
		JSONObject configurationJSONObject, Messages messages) {

		boolean valid = true;

		if (configurationJSONObject.isNull(
				CustomParameterConfigurationKeys.PARAMETER_NAME.getJsonKey())) {

			messages.addMessage(
				new Message.Builder().className(
					getClass().getName()
				).localizationKey(
					"core.error.undefined-parameter-name"
				).msg(
					"Parameter name is not defined"
				).rootObject(
					configurationJSONObject
				).rootProperty(
					CustomParameterConfigurationKeys.PARAMETER_NAME.getJsonKey()
				).severity(
					Severity.ERROR
				).build());

			valid = false;

			if (_log.isWarnEnabled()) {
				_log.warn(
					"Parameter name is not defined [ " +
						configurationJSONObject + " ].");
			}
		}

		if (configurationJSONObject.isNull(
				CustomParameterConfigurationKeys.TYPE.getJsonKey())) {

			messages.addMessage(
				new Message.Builder().className(
					getClass().getName()
				).localizationKey(
					"core.error.undefined-parameter-type"
				).msg(
					"Parameter type is not defined"
				).rootObject(
					configurationJSONObject
				).rootProperty(
					CustomParameterConfigurationKeys.TYPE.getJsonKey()
				).severity(
					Severity.ERROR
				).build());

			valid = false;

			if (_log.isWarnEnabled()) {
				_log.warn(
					"Parameter type is not defined [ " +
						configurationJSONObject + " ].");
			}
		}

		return valid;
	}

	private boolean _validateSortParameterConfiguration(
		JSONObject configurationJSONObject, Messages messages) {

		boolean valid = true;

		if (configurationJSONObject.isNull(
				SortConfigurationKeys.PARAMETER_NAME.getJsonKey())) {

			messages.addMessage(
				new Message.Builder().className(
					getClass().getName()
				).localizationKey(
					"core.error.undefined-parameter-name"
				).msg(
					"Parameter name is not defined"
				).rootObject(
					configurationJSONObject
				).rootProperty(
					SortConfigurationKeys.PARAMETER_NAME.getJsonKey()
				).severity(
					Severity.ERROR
				).build());

			valid = false;

			if (_log.isWarnEnabled()) {
				_log.warn(
					"Parameter name is not defined [ " +
						configurationJSONObject + " ].");
			}
		}

		if (configurationJSONObject.isNull(
				SortConfigurationKeys.FIELD.getJsonKey())) {

			messages.addMessage(
				new Message.Builder().className(
					getClass().getName()
				).localizationKey(
					"core.error.undefined-field"
				).msg(
					"Field is not defined"
				).rootObject(
					configurationJSONObject
				).rootProperty(
					SortConfigurationKeys.FIELD.getJsonKey()
				).severity(
					Severity.ERROR
				).build());

			valid = false;

			if (_log.isWarnEnabled()) {
				_log.warn(
					"Field is not defined [ " + configurationJSONObject +
						" ].");
			}
		}

		return valid;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ParameterDataCreatorImpl.class);

	@Reference
	private BlueprintHelper _blueprintHelper;

	private volatile Map<String, ServiceComponentReference<KeywordsProcessor>>
		_keywordsProcessors = new ConcurrentHashMap<>();

	@Reference
	private ParameterBuilderFactory _parameterBuilderFactory;

	private volatile Map
		<String, ServiceComponentReference<ParameterContributor>>
			_parameterContributors = new ConcurrentHashMap<>();

}