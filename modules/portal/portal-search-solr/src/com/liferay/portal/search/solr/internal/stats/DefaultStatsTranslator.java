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

package com.liferay.portal.search.solr.internal.stats;

import com.liferay.portal.kernel.search.Stats;
import com.liferay.portal.kernel.search.StatsResults;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.search.solr.stats.StatsTranslator;

import java.util.ArrayList;

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
				statsResults.setStandardDeviation(stddev.doubleValue());
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
				statsResults.setSumOfSquares(sumOfSquares.doubleValue());
			}
		}

		return statsResults;
	}

	@Override
	public void translate(SolrQuery solrQuery, Stats stats) {
		if (!stats.isEnabled()) {
			return;
		}

		ArrayList<String> solrStats = new ArrayList<>(8);

		add("count", stats.isCount(), solrStats);
		add("max", stats.isMax(), solrStats);
		add("mean", stats.isMean(), solrStats);
		add("min", stats.isMin(), solrStats);
		add("missing", stats.isMissing(), solrStats);
		add("stddev", stats.isStandardDeviation(), solrStats);
		add("sum", stats.isSum(), solrStats);
		add("sumOfSquares", stats.isSumOfSquares(), solrStats);

		solrQuery.setGetFieldStatistics(
			buildField(solrStats, stats.getField()));
	}

	protected static void add(
		String stat, boolean enabled, ArrayList<String> solrStats) {

		if (enabled) {
			solrStats.add(stat);
		}
	}

	protected static String buildField(
		ArrayList<String> solrStats, String field) {

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

	protected static double toDouble(Object value) {
		if (value instanceof Number) {
			return ((Number)value).doubleValue();
		}

		throw new IllegalArgumentException("Only numeric fields are supported");
	}

}