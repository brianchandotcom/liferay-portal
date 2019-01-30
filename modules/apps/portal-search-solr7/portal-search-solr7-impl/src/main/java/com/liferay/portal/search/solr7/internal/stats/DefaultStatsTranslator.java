/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.search.solr7.internal.stats;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.search.stats.StatsRequest;
import com.liferay.portal.search.stats.StatsResponse;
import com.liferay.portal.search.stats.StatsResponseBuilder;
import com.liferay.portal.search.stats.StatsResponseBuilderFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.FieldStatsInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
@Component(immediate = true, service = StatsTranslator.class)
public class DefaultStatsTranslator implements StatsTranslator {

	@Override
	public void populateRequest(
		SolrQuery solrQuery, StatsRequest statsRequest) {

		List<String> solrStats = new ArrayList<>(9);

		if (statsRequest.isCardinality()) {
			solrStats.add("cardinality");
		}

		if (statsRequest.isCount()) {
			solrStats.add("count");
		}

		if (statsRequest.isMax()) {
			solrStats.add("max");
		}

		if (statsRequest.isMean()) {
			solrStats.add("mean");
		}

		if (statsRequest.isMin()) {
			solrStats.add("min");
		}

		if (statsRequest.isMissing()) {
			solrStats.add("missing");
		}

		if (statsRequest.isStandardDeviation()) {
			solrStats.add("stddev");
		}

		if (statsRequest.isSum()) {
			solrStats.add("sum");
		}

		if (statsRequest.isSumOfSquares()) {
			solrStats.add("sumOfSquares");
		}

		if (solrStats.isEmpty()) {
			return;
		}

		String fieldStatistics = buildField(statsRequest.getField(), solrStats);

		solrQuery.setGetFieldStatistics(fieldStatistics);
	}

	@Override
	public StatsResponse translateResponse(FieldStatsInfo fieldStatsInfo) {
		StatsResponseBuilder statsResponseBuilder =
			_statsResponseBuilderFactory.getStatsResponseBuilder();

		String field = fieldStatsInfo.getName();

		statsResponseBuilder.field(field);

		copy(fieldStatsInfo::getCardinality, statsResponseBuilder::cardinality);
		copy(fieldStatsInfo::getCount, statsResponseBuilder::count);
		copy(fieldStatsInfo::getMissing, statsResponseBuilder::missing);
		copy(
			fieldStatsInfo::getStddev, statsResponseBuilder::standardDeviation);
		copy(
			fieldStatsInfo::getSumOfSquares,
			statsResponseBuilder::sumOfSquares);

		copyDouble(fieldStatsInfo::getMax, statsResponseBuilder::max);
		copyDouble(fieldStatsInfo::getMean, statsResponseBuilder::mean);
		copyDouble(fieldStatsInfo::getMin, statsResponseBuilder::min);
		copyDouble(fieldStatsInfo::getSum, statsResponseBuilder::sum);

		return statsResponseBuilder.build();
	}

	protected static <T> void copy(Supplier<T> from, Consumer<T> to) {
		T t = from.get();

		if (t != null) {
			to.accept(t);
		}
	}

	protected static void copyDouble(Supplier<?> from, Consumer<Double> to) {
		Object t = from.get();

		if (t != null) {
			to.accept(toDouble(t));
		}
	}

	protected static double toDouble(Object value) {
		if (value instanceof Number) {
			Number number = (Number)value;

			return number.doubleValue();
		}

		throw new IllegalArgumentException("Only numeric fields are supported");
	}

	protected String buildField(String field, List<String> solrStats) {
		if (solrStats.isEmpty()) {
			return field;
		}

		StringBundler sb = new StringBundler(solrStats.size() * 3 + 3);

		sb.append("{!");

		for (int i = 0; i < solrStats.size(); i++) {
			if (i > 0) {
				sb.append(' ');
			}

			sb.append(solrStats.get(i));
			sb.append("=true");
		}

		sb.append("}");
		sb.append(field);

		return sb.toString();
	}

	@Reference(unbind = "-")
	protected void setStatsResponseBuilderFactory(
		StatsResponseBuilderFactory statsResponseBuilderFactory) {

		_statsResponseBuilderFactory = statsResponseBuilderFactory;
	}

	private StatsResponseBuilderFactory _statsResponseBuilderFactory;

}