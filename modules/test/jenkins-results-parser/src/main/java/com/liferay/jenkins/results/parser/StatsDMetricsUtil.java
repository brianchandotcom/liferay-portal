/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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