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

package com.liferay.jenkins.results.parser;

import java.util.Map;

/**
 * @author Leslie Wong
 */
public class StatsDMetricsUtil {

	public static String generateCountMetric(
		String metricName, int metricValue, Map<String, String> labels) {

		if (metricValue < 0) {
			System.out.println("Count metric values must not be negative");

			return null;
		}

		return JenkinsResultsParserUtil.combine(
			metricName, ":", String.valueOf(metricValue), "|c",
			generateMetricLabels(labels));
	}

	public static String generateGaugeDeltaMetric(
		String metricName, int metricValue, Map<String, String> labels) {

		if (metricValue == 0) {
			System.out.println("Gauge metric values must not be zero");

			return null;
		}

		StringBuilder sb = new StringBuilder();

		sb.append(metricName);
		sb.append(":");

		if (metricValue < 0) {
			sb.append("-");
		}
		else {
			sb.append("+");
		}

		sb.append(Math.abs(metricValue));

		sb.append("|g");
		sb.append(generateMetricLabels(labels));

		return sb.toString();
	}

	public static String generateMetricLabels(Map<String, String> labels) {
		if ((labels == null) || labels.isEmpty()) {
			return "";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("|#");

		for (Map.Entry<String, String> label : labels.entrySet()) {
			sb.append(label.getKey());
			sb.append(":");
			sb.append(label.getValue());
			sb.append(",");
		}

		sb.setLength(sb.length() - 1);

		return sb.toString();
	}

	public static String generateTimerMetric(
		String metricName, long metricValue, Map<String, String> labels) {

		if (metricValue <= 0) {
			System.out.println("Timer metric values must greater than zero");

			return null;
		}

		return JenkinsResultsParserUtil.combine(
			metricName, ":", String.valueOf(metricValue), "|c",
			generateMetricLabels(labels));
	}

}