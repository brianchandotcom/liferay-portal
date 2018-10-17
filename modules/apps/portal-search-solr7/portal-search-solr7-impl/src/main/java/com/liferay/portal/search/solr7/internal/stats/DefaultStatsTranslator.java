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
import com.liferay.portal.kernel.search.Stats;
import com.liferay.portal.kernel.search.StatsResults;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.FieldStatsInfo;

import org.osgi.service.component.annotations.Component;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
@Component(immediate = true, service = StatsTranslator.class)
public class DefaultStatsTranslator implements StatsTranslator {

	@Override
	public StatsResults translate(FieldStatsInfo fieldStatsInfo, Stats stats) {
		String field = stats.getField();

		StatsResults statsResults = new StatsResults(field);

		if (stats.isCount()) {
			Long count = fieldStatsInfo.getCount();

			if (count != null) {
				statsResults.setCount(count);
			}
		}

		if (stats.isMax()) {
			Object max = fieldStatsInfo.getMax();

			if (max != null) {
				statsResults.setMax(toDouble(max));
			}
		}

		if (stats.isMean()) {
			Object mean = fieldStatsInfo.getMean();

			if (mean != null) {
				statsResults.setMean(toDouble(mean));
			}
		}

		if (stats.isMin()) {
			Object min = fieldStatsInfo.getMin();

			if (min != null) {
				statsResults.setMin(toDouble(min));
			}
		}

		if (stats.isMissing()) {
			Long missing = fieldStatsInfo.getMissing();

			if (missing != null) {
				statsResults.setMissing(missing.intValue());
			}
		}

		if (stats.isStandardDeviation()) {
			Double stddev = fieldStatsInfo.getStddev();

			if (stddev != null) {
				statsResults.setStandardDeviation(stddev);
			}
		}

		if (stats.isSum()) {
			Object sum = fieldStatsInfo.getSum();

			if (sum != null) {
				statsResults.setSum(toDouble(sum));
			}
		}

		if (stats.isSumOfSquares()) {
			Double sumOfSquares = fieldStatsInfo.getSumOfSquares();

			if (sumOfSquares != null) {
				statsResults.setSumOfSquares(sumOfSquares);
			}
		}

		return statsResults;
	}

	@Override
	public void translate(SolrQuery solrQuery, Stats stats) {
		if (!stats.isEnabled()) {
			return;
		}

		List<String> solrStats = new ArrayList<>(8);

		if (stats.isCount()) {
			solrStats.add("count");
		}

		if (stats.isMax()) {
			solrStats.add("max");
		}

		if (stats.isMean()) {
			solrStats.add("mean");
		}

		if (stats.isMin()) {
			solrStats.add("min");
		}

		if (stats.isMissing()) {
			solrStats.add("missing");
		}

		if (stats.isStandardDeviation()) {
			solrStats.add("stddev");
		}

		if (stats.isSum()) {
			solrStats.add("sum");
		}

		if (stats.isSumOfSquares()) {
			solrStats.add("sumOfSquares");
		}

		String fieldStatistics = buildField(stats.getField(), solrStats);

		solrQuery.setGetFieldStatistics(fieldStatistics);
	}

	protected String buildField(String field, List<String> solrStats) {
		if (solrStats.isEmpty()) {
			return field;
		}

		StringBundler sb = new StringBundler(solrStats.size() * 3 + 2);

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

	protected double toDouble(Object value) {
		if (value instanceof Number) {
			Number number = (Number)value;

			return number.doubleValue();
		}

		throw new IllegalArgumentException("Only numeric fields are supported");
	}

}