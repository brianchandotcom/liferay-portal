/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import org.apache.commons.lang3.StringUtils;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseFailureReport implements FailureReport {

	@Override
	public BuildReport getBuildReport() {
		return _buildReport;
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject failureReportJSONObject = new JSONObject();

		failureReportJSONObject.put("message", getMessage());

		return failureReportJSONObject;
	}

	@Override
	public String getNormalizedMessage() {
		if (_normalizedMessage != null) {
			return _normalizedMessage;
		}

		String normalizedMessage = getMessage();

		if (normalizedMessage == null) {
			normalizedMessage = "";
		}

		normalizedMessage = normalizedMessage.replaceAll("\\d+", "#");
		normalizedMessage = normalizedMessage.replaceAll("\\s+", " ");

		normalizedMessage = normalizedMessage.trim();

		int fuzzyCompareLengthMax = _getCompareLengthMax();

		if (normalizedMessage.length() > fuzzyCompareLengthMax) {
			normalizedMessage = normalizedMessage.substring(
				0, fuzzyCompareLengthMax);
		}

		_normalizedMessage = normalizedMessage;

		return _normalizedMessage;
	}

	@Override
	public boolean isSimilar(FailureReport failureReport) {
		if (getClass() != failureReport.getClass()) {
			return false;
		}

		String normalizedMessage1 = getNormalizedMessage();
		String normalizedMessage2 = failureReport.getNormalizedMessage();

		if (JenkinsResultsParserUtil.isNullOrEmpty(normalizedMessage1) ||
			JenkinsResultsParserUtil.isNullOrEmpty(normalizedMessage2)) {

			return false;
		}

		int maxLength = Math.max(
			normalizedMessage1.length(), normalizedMessage2.length());

		if (maxLength == 0) {
			return false;
		}

		int editDistance = StringUtils.getLevenshteinDistance(
			normalizedMessage1, normalizedMessage2);

		double similarity = 1.0 - ((double)editDistance / maxLength);

		if (similarity < _getSimilarityThreshold()) {
			return false;
		}

		return true;
	}

	protected BaseFailureReport(BuildReport buildReport) {
		_buildReport = buildReport;
	}

	private int _getCompareLengthMax() {
		if (_compareLengthMax != null) {
			return _compareLengthMax;
		}

		try {
			String compareLengthMax = JenkinsResultsParserUtil.getBuildProperty(
				"failure.report.compare.length.max");

			if (JenkinsResultsParserUtil.isInteger(compareLengthMax)) {
				_compareLengthMax = Integer.parseInt(compareLengthMax);

				return _compareLengthMax;
			}
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}

		_compareLengthMax = _COMPARE_LENGTH_MAX_DEFAULT;

		return _compareLengthMax;
	}

	private double _getSimilarityThreshold() {
		if (_similarityThreshold != null) {
			return _similarityThreshold;
		}

		try {
			String similarityThreshold =
				JenkinsResultsParserUtil.getBuildProperty(
					"failure.report.similarity.threshold");

			if (JenkinsResultsParserUtil.isDouble(similarityThreshold)) {
				_similarityThreshold = Double.parseDouble(similarityThreshold);

				return _similarityThreshold;
			}
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}

		_similarityThreshold = _SIMILARITY_THRESHOLD_DEFAULT;

		return _similarityThreshold;
	}

	private static final int _COMPARE_LENGTH_MAX_DEFAULT = 500;

	private static final double _SIMILARITY_THRESHOLD_DEFAULT = 0.8;

	private static Integer _compareLengthMax;
	private static Double _similarityThreshold;

	private final BuildReport _buildReport;
	private String _normalizedMessage;

}