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

import java.net.URL;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONObject;

/**
 * @author Kevin Yen
 */
public class TopLevelBuild extends BaseBuild {

	@Override
	public String getStatusReport(int indentSize) {
		String statusReport = super.getStatusReport(indentSize);

		if (getDownstreamBuildCount(null) > 0) {
			while (statusReport.endsWith("\n")) {
				statusReport = statusReport.substring(
					0, statusReport.length() - 1);
			}

			statusReport += " / ";
		}

		return statusReport + "Update took " + _updateDuration +
			" milliseconds.\n";
	}

	@Override
	public void update() {
		long start = System.currentTimeMillis();

		super.update();

		_updateDuration = System.currentTimeMillis() - start;
	}

	protected TopLevelBuild(String url) throws Exception {
		this(url, null);
	}

	protected TopLevelBuild(String url, TopLevelBuild topLevelBuild)
		throws Exception {

		super(url, topLevelBuild);
	}

	@Override
	protected ExecutorService getExecutorService() {
		return Executors.newFixedThreadPool(100);
	}

	protected String getJSONMapURL(Build targetBuild) {
		StringBuilder sb = new StringBuilder();

		sb.append(getMaster());
		sb.append("/");
		sb.append(getJobName());
		sb.append("/");
		sb.append(getBuildNumber());
		sb.append("/");
		sb.append(targetBuild.getJobName());
		sb.append("/");
		String jobVariant = targetBuild.getParameterValue("JOB_VARIANT");

		if ((jobVariant != null) && !jobVariant.isEmpty()) {
			sb.append(jobVariant);
			sb.append("/");
		}

		return sb.toString();
	}

	@Override
	protected Map<String, String> getStartProperties(Build targetBuild) {
		return getTempMap("start.properties", targetBuild);
	}

	@Override
	protected Map<String, String> getStopProperties(Build targetBuild) {
		return getTempMap("stop.properties", targetBuild);
	}

	protected Map<String, String> getTempMap(
		String mapName, Build targetBuild) {

		StringBuilder sb = new StringBuilder();

		sb.append("http://cloud-10-0-0-31/osb-jenkins-web/map/");
		sb.append(getJSONMapURL(targetBuild));
		sb.append(mapName);

		try {
			URL url = JenkinsResultsParserUtil.encode(new URL(sb.toString()));

			JSONObject tempMapJSONObject =
				JenkinsResultsParserUtil.toJSONObject(url.toString());

			Set<?> keyset = tempMapJSONObject.keySet();

			Map<String, String> tempMap = new HashMap<>(keyset.size());

			for (Object key : tempMapJSONObject.keySet()) {
				String value = tempMapJSONObject.optString(key.toString());

				if ((value != null) && !value.isEmpty()) {
					tempMap.put(key.toString(), value);
				}
			}

			return tempMap;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private long _updateDuration;

}