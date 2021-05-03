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

package com.liferay.portal.search.tuning.blueprints.engine.internal.aggregation.translator.bucket;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.aggregation.Aggregation;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.bucket.CollectionMode;
import com.liferay.portal.search.aggregation.bucket.IncludeExcludeClause;
import com.liferay.portal.search.aggregation.bucket.Order;
import com.liferay.portal.search.aggregation.bucket.TermsAggregation;
import com.liferay.portal.search.script.Scripts;
import com.liferay.portal.search.tuning.blueprints.constants.json.keys.aggregation.bucket.TermsAggregationBodyConfigurationKeys;
import com.liferay.portal.search.tuning.blueprints.engine.internal.aggregation.translator.BaseAggregationTranslator;
import com.liferay.portal.search.tuning.blueprints.engine.internal.util.ScriptHelper;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.ParameterData;
import com.liferay.portal.search.tuning.blueprints.engine.spi.aggregation.AggregationTranslator;
import com.liferay.portal.search.tuning.blueprints.engine.spi.aggregation.AggregationTranslatorFactory;
import com.liferay.portal.search.tuning.blueprints.message.Message;
import com.liferay.portal.search.tuning.blueprints.message.Messages;
import com.liferay.portal.search.tuning.blueprints.message.Severity;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "name=terms",
	service = AggregationTranslator.class
)
public class TermsAggregationTranslator
	extends BaseAggregationTranslator implements AggregationTranslator {

	@Override
	public Optional<Aggregation> translate(
		String aggregationName, JSONObject configurationJSONObject,
		ParameterData parameterData, Messages messages,
		AggregationTranslatorFactory aggregationTranslatorFactory) {

		if (!validateField(configurationJSONObject, messages)) {
			return Optional.empty();
		}

		String field = configurationJSONObject.getString(
			TermsAggregationBodyConfigurationKeys.FIELD.getJsonKey());

		TermsAggregation aggregation = _aggregations.terms(
			aggregationName, field);

		setChildAggregations(
			aggregation, aggregationTranslatorFactory, parameterData,
			configurationJSONObject, messages);

		_setCollectMode(aggregation, configurationJSONObject, messages);

		_setExecutionHint(aggregation, configurationJSONObject);

		_setIncludeExcludeClause(aggregation, configurationJSONObject);

		_setMinDocCount(aggregation, configurationJSONObject);

		setMissing(aggregation, configurationJSONObject);

		_setOrders(aggregation, messages, configurationJSONObject);

		setScript(
			aggregation, _aggregationScriptHelper, configurationJSONObject,
			messages);

		_setShardMinDocCount(aggregation, configurationJSONObject);

		_setShardSize(aggregation, configurationJSONObject);

		_setShowTermDocCountError(aggregation, configurationJSONObject);

		_setSize(aggregation, configurationJSONObject);

		return Optional.of(aggregation);
	}

	private Order _getOrder(JSONObject orderJSONObject, Messages messages) {
		String orderMetric;

		try {
			orderMetric = orderJSONObject.keys(
			).next();
		}
		catch (NoSuchElementException noSuchElementException) {
			messages.addMessage(
				new Message.Builder().className(
					getClass().getName()
				).localizationKey(
					"core.error.invalid-aggregation-order-syntax"
				).msg(
					noSuchElementException.getMessage()
				).rootObject(
					orderJSONObject
				).rootProperty(
					TermsAggregationBodyConfigurationKeys.ORDER.getJsonKey()
				).severity(
					Severity.ERROR
				).throwable(
					noSuchElementException
				).build());

			if (_log.isWarnEnabled()) {
				_log.warn(
					noSuchElementException.getMessage(),
					noSuchElementException);
			}

			return null;
		}

		String orderDirection = orderJSONObject.getString(orderMetric);

		Order order;

		if (Order.COUNT_METRIC_NAME.equals(orderMetric) ||
			Order.KEY_METRIC_NAME.equals(orderMetric)) {

			order = new Order(null);

			order.setMetricName(orderMetric);
		}
		else {
			String[] arr = orderMetric.split(".");

			if (arr.length != 2) {
				messages.addMessage(
					new Message.Builder().className(
						getClass().getName()
					).localizationKey(
						"core.error.invalid-aggregation-order-syntax"
					).msg(
						"Invalid aggregation order syntax"
					).rootObject(
						orderJSONObject
					).rootProperty(
						TermsAggregationBodyConfigurationKeys.ORDER.getJsonKey()
					).rootValue(
						orderMetric
					).severity(
						Severity.ERROR
					).build());

				return null;
			}

			String path = arr[0];
			String metric = arr[1];

			order = new Order(path);

			order.setMetricName(metric);
		}

		order.setAscending(StringUtil.equalsIgnoreCase(orderDirection, "asc"));

		return order;
	}

	private void _setCollectMode(
		TermsAggregation aggregation, JSONObject configurationJSONObject,
		Messages messages) {

		if (configurationJSONObject.isNull(
				TermsAggregationBodyConfigurationKeys.COLLECT_MODE.
					getJsonKey())) {

			return;
		}

		String collectModeString = configurationJSONObject.getString(
			TermsAggregationBodyConfigurationKeys.COLLECT_MODE.getJsonKey());

		try {
			CollectionMode collectMode = CollectionMode.valueOf(
				StringUtil.toUpperCase(collectModeString));

			aggregation.setCollectionMode(collectMode);
		}
		catch (IllegalArgumentException illegalArgumentException) {
			messages.addMessage(
				new Message.Builder().className(
					getClass().getName()
				).localizationKey(
					"core.error.invalid-aggregation-collect-mode"
				).msg(
					illegalArgumentException.getMessage()
				).rootObject(
					configurationJSONObject
				).rootProperty(
					TermsAggregationBodyConfigurationKeys.COLLECT_MODE.
						getJsonKey()
				).rootValue(
					collectModeString
				).severity(
					Severity.ERROR
				).throwable(
					illegalArgumentException
				).build());

			if (_log.isWarnEnabled()) {
				_log.warn(
					illegalArgumentException.getMessage(),
					illegalArgumentException);
			}
		}
	}

	private void _setExecutionHint(
		TermsAggregation aggregation, JSONObject configurationJSONObject) {

		if (configurationJSONObject.isNull(
				TermsAggregationBodyConfigurationKeys.EXECUTION_HINT.
					getJsonKey())) {

			return;
		}

		aggregation.setExecutionHint(
			configurationJSONObject.getString(
				TermsAggregationBodyConfigurationKeys.EXECUTION_HINT.
					getJsonKey()));
	}

	private void _setIncludeExcludeClause(
		TermsAggregation aggregation, JSONObject configurationJSONObject) {

		Object excludeObject = configurationJSONObject.get(
			TermsAggregationBodyConfigurationKeys.EXCLUDE.getJsonKey());
		Object includeObject = configurationJSONObject.get(
			TermsAggregationBodyConfigurationKeys.INCLUDE.getJsonKey());

		if (Validator.isNotNull(excludeObject) ||
			Validator.isNotNull(includeObject)) {

			IncludeExcludeClause includeExcludeClause = null;

			if ((excludeObject instanceof JSONArray) ||
				(includeObject instanceof JSONArray)) {

				String[] excludeArray = JSONUtil.toStringArray(
					(JSONArray)excludeObject);
				String[] includeArray = JSONUtil.toStringArray(
					(JSONArray)includeObject);

				includeExcludeClause = new IncludeExcludeClause() {

					@Override
					public String[] getExcludedValues() {
						return excludeArray;
					}

					@Override
					public String getExcludeRegex() {
						return null;
					}

					@Override
					public String[] getIncludedValues() {
						return includeArray;
					}

					@Override
					public String getIncludeRegex() {
						return null;
					}

				};
			}
			else if ((excludeObject instanceof String) ||
					 (includeObject instanceof String)) {

				includeExcludeClause = new IncludeExcludeClause() {

					@Override
					public String[] getExcludedValues() {
						return null;
					}

					@Override
					public String getExcludeRegex() {
						return (String)excludeObject;
					}

					@Override
					public String[] getIncludedValues() {
						return null;
					}

					@Override
					public String getIncludeRegex() {
						return (String)includeObject;
					}

				};
			}

			aggregation.setIncludeExcludeClause(includeExcludeClause);
		}
	}

	private void _setMinDocCount(
		TermsAggregation aggregation, JSONObject configurationJSONObject) {

		if (configurationJSONObject.isNull(
				TermsAggregationBodyConfigurationKeys.MIN_DOC_COUNT.
					getJsonKey())) {

			return;
		}

		aggregation.setMinDocCount(
			configurationJSONObject.getInt(
				TermsAggregationBodyConfigurationKeys.MIN_DOC_COUNT.
					getJsonKey()));
	}

	private void _setOrders(
		TermsAggregation aggregation, Messages messages,
		JSONObject configurationJSONObject) {

		if (configurationJSONObject.isNull(
				TermsAggregationBodyConfigurationKeys.ORDER.getJsonKey())) {

			return;
		}

		Object orderObject = configurationJSONObject.get(
			TermsAggregationBodyConfigurationKeys.ORDER.getJsonKey());

		List<Order> orders = new ArrayList<>();

		if (orderObject instanceof JSONArray) {
			JSONArray jsonArray = (JSONArray)orderObject;

			jsonArray.forEach(
				object -> {
					JSONObject orderJSONObject = (JSONObject)object;

					orders.add(_getOrder(orderJSONObject, messages));
				});
		}
		else if (orderObject instanceof JSONObject) {
			JSONObject orderJSONObject = (JSONObject)orderObject;

			orders.add(_getOrder(orderJSONObject, messages));
		}

		Stream<Order> stream = orders.stream();

		aggregation.addOrders(stream.toArray(Order[]::new));
	}

	private void _setShardMinDocCount(
		TermsAggregation aggregation, JSONObject configurationJSONObject) {

		if (configurationJSONObject.isNull(
				TermsAggregationBodyConfigurationKeys.SHARD_MIN_DOC_COUNT.
					getJsonKey())) {

			return;
		}

		aggregation.setShardMinDocCount(
			configurationJSONObject.getInt(
				TermsAggregationBodyConfigurationKeys.SHARD_MIN_DOC_COUNT.
					getJsonKey()));
	}

	private void _setShardSize(
		TermsAggregation aggregation, JSONObject configurationJSONObject) {

		if (configurationJSONObject.isNull(
				TermsAggregationBodyConfigurationKeys.SHARD_SIZE.
					getJsonKey())) {

			return;
		}

		aggregation.setShardSize(
			configurationJSONObject.getInt(
				TermsAggregationBodyConfigurationKeys.SHARD_SIZE.getJsonKey()));
	}

	private void _setShowTermDocCountError(
		TermsAggregation aggregation, JSONObject configurationJSONObject) {

		if (configurationJSONObject.isNull(
				TermsAggregationBodyConfigurationKeys.SHOW_TERM_DOC_COUNT_ERROR.
					getJsonKey())) {

			return;
		}

		aggregation.setShowTermDocCountError(
			configurationJSONObject.getBoolean(
				TermsAggregationBodyConfigurationKeys.SHOW_TERM_DOC_COUNT_ERROR.
					getJsonKey()));
	}

	private void _setSize(
		TermsAggregation aggregation, JSONObject configurationJSONObject) {

		if (configurationJSONObject.isNull(
				TermsAggregationBodyConfigurationKeys.SIZE.getJsonKey())) {

			return;
		}

		aggregation.setSize(
			configurationJSONObject.getInt(
				TermsAggregationBodyConfigurationKeys.SIZE.getJsonKey()));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TermsAggregationTranslator.class);

	@Reference
	private Aggregations _aggregations;

	@Reference
	private ScriptHelper _aggregationScriptHelper;

	@Reference
	private Scripts _scripts;

}