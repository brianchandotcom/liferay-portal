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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Kevin Yen
 */
public abstract class BaseBuild implements Build {

	@Override
	public int getBuildNumber() {
		return buildNumber;
	}

	@Override
	public String getBuildURL() {
		if ((master == null) || (master.length() == 0)) {
			return null;
		}

		StringBuilder sb = new StringBuilder();

		sb.append(getJobURL());
		sb.append(buildNumber);
		sb.append("/");

		return sb.toString();
	}

	@Override
	public String getJobName() {
		return jobName;
	}

	@Override
	public String getJobURL() {
		StringBuilder sb = new StringBuilder();

		sb.append("http://");
		sb.append(master);
		sb.append("/job/");
		sb.append(jobName);
		sb.append("/");

		return sb.toString();
	}

	@Override
	public String getMaster() {
		return master;
	}

	@Override
	public String getResult() {
		if (!_status.equals("completed")) {
			throw new IllegalStateException("Build not completed");
		}

		return result;
	}

	@Override
	public String getStatus() {
		return _status;
	}

	@Override
	public long getStatusAge() {
		return System.currentTimeMillis() - statusModifiedTime;
	}

	protected static String decodeURL(String url) {
		url = url.replace("%28", "(");
		url = url.replace("%29", ")");
		url = url.replace("%5B", "[");
		url = url.replace("%5D", "]");

		return url;
	}

	protected BaseBuild() {
		master = "";
		jobName = "";

		setStatus("starting");
	}

	protected BaseBuild(String buildURL) throws Exception {
		setBuildURL(buildURL);
	}

	protected void setBuildURL(String buildURL) throws Exception {
		buildURL = decodeURL(buildURL);

		Matcher matcher = _buildURLPattern.matcher(buildURL);

		if (!matcher.find()) {
			throw new IllegalArgumentException("Invalid build URL " + buildURL);
		}

		buildNumber = Integer.parseInt(matcher.group("buildNumber"));
		jobName = matcher.group("jobName");
		master = matcher.group("master");

		update();
	}

	protected void setStatus(String status) {
		if (((status == null) && (_status != null)) ||
			!status.equals(_status)) {

			_status = status;

			statusModifiedTime = System.currentTimeMillis();
		}
	}

	protected int buildNumber;
	protected String jobName;
	protected String master;
	protected String result;
	protected long statusModifiedTime;

	private static final Pattern _buildURLPattern = Pattern.compile(
		"\\w+://(?<master>[^/]+)/+job/+(?<jobName>[^/]+).*/" +
			"(?<buildNumber>\\d+)/?");

	private String _status;

}