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

package com.liferay.search.experiences.internal.blueprint.aggregation.util;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.aggregation.Aggregation;
import com.liferay.portal.search.aggregation.FieldAggregation;
import com.liferay.portal.search.aggregation.bucket.IncludeExcludeClause;
import com.liferay.portal.search.aggregation.bucket.Order;
import com.liferay.portal.search.aggregation.bucket.Range;
import com.liferay.portal.search.aggregation.pipeline.GapPolicy;
import com.liferay.portal.search.aggregation.pipeline.PipelineAggregation;
import com.liferay.portal.search.geolocation.DistanceUnit;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.script.Script;
import com.liferay.portal.search.significance.SignificanceHeuristic;
import com.liferay.portal.search.significance.SignificanceHeuristics;
import com.liferay.search.experiences.internal.blueprint.aggregation.AggregationWrapper;
import com.liferay.search.experiences.internal.blueprint.clause.util.ClauseHelper;
import com.liferay.search.experiences.internal.blueprint.parameter.SXPParameterData;
import com.liferay.search.experiences.internal.blueprint.util.ScriptHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = AggregationHelper.class)
public class AggregationHelper {

	public Map<String, String> getBucketsPaths(JSONObject bodyJSONObject) {
		Map<String, String> bucketsPathMap = new HashMap<>();

		String propertyName = "buckets_path";

		Object object = bodyJSONObject.get(propertyName);

		if (Objects.isNull(object)) {
			return bucketsPathMap;
		}

		if (object instanceof JSONObject) {
			JSONObject bucketsPathJSONObject = (JSONObject)object;

			Set<String> keySet = bucketsPathJSONObject.keySet();

			Stream<String> stream = keySet.stream();

			stream.forEach(
				key -> bucketsPathMap.put(
					key, bucketsPathJSONObject.getString(key)));
		}

		return bucketsPathMap;
	}

	public DistanceUnit getDistanceUnit(String s) {
		s = StringUtil.toLowerCase(s);

		for (DistanceUnit distanceUnit : DistanceUnit.values()) {
			String unit = distanceUnit.getUnit();

			if (unit.equals(s)) {
				return distanceUnit;
			}
		}

		return null;
	}

	public GapPolicy getGapPolicy(JSONObject bodyJSONObject) {
		String gapPolicy = bodyJSONObject.getString("gap_policy");

		if (Validator.isBlank(gapPolicy)) {
			return null;
		}

		return GapPolicy.valueOf(StringUtil.toUpperCase(gapPolicy));
	}

	public Integer getHdrSignificantValueDigits(JSONObject jsonObject) {
		JSONObject hdrJSONObject = jsonObject.getJSONObject("hdr");

		if (hdrJSONObject == null) {
			return null;
		}

		return hdrJSONObject.getInt("number_of_significant_value_digits");
	}

	public Optional<Script> getScript(Object object) {
		return _scriptHelper.getScript(object);
	}

	public SignificanceHeuristic getSignificanceHeuristics(
		JSONObject bodyJSONObject) {

		if (bodyJSONObject.has("chi_square")) {
			JSONObject jsonObject = bodyJSONObject.getJSONObject("chi_square");

			if (jsonObject != null) {
				return _significanceHeuristics.chiSquare(
					_isBackgroundIsSuperset(jsonObject),
					_isIncludeNegatives(jsonObject));
			}
		}

		if (bodyJSONObject.has("gnd")) {
			JSONObject jsonObject = bodyJSONObject.getJSONObject("gnd");

			if (jsonObject != null) {
				return _significanceHeuristics.gnd(
					_isBackgroundIsSuperset(jsonObject));
			}
		}

		if (bodyJSONObject.has("jlh")) {
			return _significanceHeuristics.jlhScore();
		}

		if (bodyJSONObject.has("mutual_information")) {
			JSONObject jsonObject = bodyJSONObject.getJSONObject(
				"mutual_information");

			if (jsonObject != null) {
				_significanceHeuristics.mutualInformation(
					_isBackgroundIsSuperset(jsonObject),
					_isIncludeNegatives(jsonObject));
			}
		}

		if (bodyJSONObject.has("percentage")) {
			return _significanceHeuristics.percentageScore();
		}

		if (bodyJSONObject.has("script_heuristic")) {
			JSONObject jsonObject = bodyJSONObject.getJSONObject(
				"script_heuristic");

			if (jsonObject != null) {
				Optional<Script> optional = getScript(jsonObject);

				if (optional.isPresent()) {
					return _significanceHeuristics.script(optional.get());
				}
			}
		}

		return null;
	}

	public Integer getTDigestCompression(JSONObject jsonObject) {
		JSONObject tDigestJSONObject = jsonObject.getJSONObject("tdigest");

		if (tDigestJSONObject == null) {
			return null;
		}

		return tDigestJSONObject.getInt("compression");
	}

	public void setBackgroundFilter(
		JSONObject bodyJSONObject, Consumer<Query> setter,
		SXPParameterData sxpParameterData) {

		JSONObject backgroundFilterJSONObject = bodyJSONObject.getJSONObject(
			"background_filter");

		if (backgroundFilterJSONObject == null) {
			return;
		}

		Optional<Query> optional = _clauseHelper.getQueryOptional(
			backgroundFilterJSONObject, sxpParameterData);

		if (optional.isPresent()) {
			setter.accept(optional.get());
		}
	}

	public void setBucketPaths(
		JSONObject bodyJSONObject, BiConsumer<String, String> setter) {

		Map<String, String> bucketsPathMap = getBucketsPaths(bodyJSONObject);

		if (bucketsPathMap.isEmpty()) {
			return;
		}

		Set<Map.Entry<String, String>> entrySet = bucketsPathMap.entrySet();

		Stream<Map.Entry<String, String>> stream = entrySet.stream();

		stream.forEach(
			entry -> setter.accept(entry.getKey(), entry.getValue()));
	}

	public void setDoubleBounds(
		JSONObject jsonObject, BiConsumer<Double, Double> setter) {

		JSONObject extendedBoundsJSONObject = jsonObject.getJSONObject(
			"extended_bounds");

		if (extendedBoundsJSONObject != null) {
			_setDoubleBoundValues(extendedBoundsJSONObject, setter);
		}
		else {
			JSONObject hardBoundsJSONObject = jsonObject.getJSONObject(
				"hard_bounds");

			if (hardBoundsJSONObject != null) {
				_setDoubleBoundValues(hardBoundsJSONObject, setter);
			}
		}
	}

	public void setGapPolicy(
		JSONObject bodyJSONObject, Consumer<GapPolicy> setter) {

		GapPolicy gapPolicy = getGapPolicy(bodyJSONObject);

		if (gapPolicy != null) {
			setter.accept(gapPolicy);
		}
	}

	public void setIncludeExcludeClause(
		JSONObject jsonObject, Consumer<IncludeExcludeClause> setter) {

		Object excludeObject = jsonObject.get("exclude");
		Object includeObject = jsonObject.get("include");

		if (Objects.isNull(excludeObject) && Objects.isNull(includeObject)) {
			return;
		}

		String[] excludeArray = null;

		String excludeString = null;

		String[] includeArray = null;

		String includeString = null;

		if (!Objects.isNull(excludeObject)) {
			if (excludeObject instanceof JSONArray) {
				excludeArray = JSONUtil.toStringArray((JSONArray)excludeObject);
			}
			else {
				excludeString = GetterUtil.getString(excludeObject);
			}
		}

		if (!Objects.isNull(includeObject)) {
			if (includeObject instanceof JSONArray) {
				includeArray = JSONUtil.toStringArray((JSONArray)includeObject);
			}
			else {
				includeString = GetterUtil.getString(includeObject);
			}
		}

		IncludeExcludeClause includeExcludeClause = _getIncludeExcludeClause(
			excludeArray, excludeString, includeArray, includeString);

		setter.accept(includeExcludeClause);
	}

	public void setLongBounds(
		JSONObject jsonObject, BiConsumer<Long, Long> setter) {

		JSONObject extendedBoundsJSONObject = jsonObject.getJSONObject(
			"extended_bounds");

		if (extendedBoundsJSONObject != null) {
			_setLongBoundValues(extendedBoundsJSONObject, setter);
		}
		else {
			JSONObject hardBoundsJSONObject = jsonObject.getJSONObject(
				"hard_bounds");

			if (hardBoundsJSONObject != null) {
				_setLongBoundValues(hardBoundsJSONObject, setter);
			}
		}
	}

	public void setMissing(
		FieldAggregation fieldAggregation, JSONObject bodyJSONObject) {

		String missing = bodyJSONObject.getString("missing");

		if (Validator.isBlank(missing)) {
			return;
		}

		fieldAggregation.setMissing(bodyJSONObject.getString("missing"));
	}

	public void setOrders(JSONObject jsonObject, Consumer<Order[]> setter) {
		JSONObject orderJSONObject = jsonObject.getJSONObject("order");

		if (orderJSONObject == null) {
			return;
		}

		List<Order> orders = new ArrayList<>();

		Set<String> keySet = orderJSONObject.keySet();

		Stream<String> keyStream = keySet.stream();

		keyStream.forEach(
			key -> orders.add(_getOrder(key, orderJSONObject.getString(key))));

		Stream<Order> ordersStream = orders.stream();

		setter.accept(ordersStream.toArray(Order[]::new));
	}

	public void setRanges(JSONObject jsonObject, Consumer<Range> setter) {
		JSONArray rangesJSONArray = jsonObject.getJSONArray("ranges");

		if (rangesJSONArray == null) {
			return;
		}

		for (int i = 0; i < rangesJSONArray.length(); i++) {
			JSONObject rangeJSONObject = rangesJSONArray.getJSONObject(i);

			Range range = null;

			String key = rangeJSONObject.getString("key");

			if (!Validator.isBlank(key)) {
				range = new Range(
					key, rangeJSONObject.getString("from", null),
					rangeJSONObject.getString("to", null));
			}
			else {
				range = new Range(
					rangeJSONObject.getString("from", null),
					rangeJSONObject.getString("to", null));
			}

			setter.accept(range);
		}
	}

	public void setScript(JSONObject jsonObject, Consumer<Script> setter) {
		Object scriptObject = jsonObject.get("script");

		if (Objects.isNull(scriptObject)) {
			return;
		}

		Optional<Script> scriptOptional = getScript(scriptObject);

		if (scriptOptional.isPresent()) {
			setter.accept(scriptOptional.get());
		}
	}

	public void setScript(
		JSONObject jsonObject, String scriptKey, Consumer<Script> setter) {

		if (!jsonObject.has(scriptKey)) {
			return;
		}

		Optional<Script> scriptOptional = getScript(jsonObject.get(scriptKey));

		if (scriptOptional.isPresent()) {
			setter.accept(scriptOptional.get());
		}
	}

	public void setSignificanceHeuristics(
		JSONObject bodyJSONObject, Consumer<SignificanceHeuristic> setter) {

		SignificanceHeuristic significanceHeuristic = getSignificanceHeuristics(
			bodyJSONObject);

		if (significanceHeuristic != null) {
			setter.accept(significanceHeuristic);
		}
	}

	public Optional<AggregationWrapper> wrap(Aggregation aggregation) {
		return Optional.of(new AggregationWrapper(aggregation));
	}

	public Optional<AggregationWrapper> wrap(
		PipelineAggregation pipelineAggregation) {

		return Optional.of(new AggregationWrapper(pipelineAggregation));
	}

	private IncludeExcludeClause _getIncludeExcludeClause(
		String[] excludeArray, String excludeString, String[] includeArray,
		String includeString) {

		return new IncludeExcludeClause() {

			@Override
			public String[] getExcludedValues() {
				return excludeArray;
			}

			@Override
			public String getExcludeRegex() {
				return excludeString;
			}

			@Override
			public String[] getIncludedValues() {
				return includeArray;
			}

			@Override
			public String getIncludeRegex() {
				return includeString;
			}

		};
	}

	private Order _getOrder(String key, String value) {
		boolean ascending = StringUtil.equalsIgnoreCase(value, "asc");

		if (Order.COUNT_METRIC_NAME.equals(key)) {
			return Order.count(ascending);
		}
		else if (Order.KEY_METRIC_NAME.equals(key)) {
			return Order.key(ascending);
		}
		else {
			Order order = new Order(key);

			order.setAscending(ascending);

			return order;
		}
	}

	private boolean _isBackgroundIsSuperset(JSONObject jsonObject) {
		return jsonObject.getBoolean("background_is_superset", true);
	}

	private boolean _isIncludeNegatives(JSONObject jsonObject) {
		return jsonObject.getBoolean("include_negatives", true);
	}

	private void _setDoubleBoundValues(
		JSONObject jsonObject, BiConsumer<Double, Double> setter) {

		setter.accept(
			jsonObject.getDouble("min", Double.MIN_VALUE),
			jsonObject.getDouble("max", Double.MAX_VALUE));
	}

	private void _setLongBoundValues(
		JSONObject jsonObject, BiConsumer<Long, Long> setter) {

		setter.accept(jsonObject.getLong("min"), jsonObject.getLong("max"));
	}

	@Reference
	private ClauseHelper _clauseHelper;

	@Reference
	private ScriptHelper _scriptHelper;

	@Reference
	private SignificanceHeuristics _significanceHeuristics;

}