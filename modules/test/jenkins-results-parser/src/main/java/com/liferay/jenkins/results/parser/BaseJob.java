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
public abstract class BaseJob {

	protected static String decodeURL(String url) {
		url = url.replace("%28", "(");
		url = url.replace("%29", ")");
		url = url.replace("%5B", "[");
		url = url.replace("%5D", "]");

		return url;
	}

	public String getURL() {
		if ((master == null) || (master.length() == 0)) {
			return null;
		}

		StringBuilder sb = new StringBuilder();

		sb.append("http://");
		sb.append(master);
		sb.append("/job/");
		sb.append(name);
		sb.append("/");
		sb.append(number);

		return sb.toString();
	}

	public String getMaster() {
		return master;
	}

	public String getName() {
		return name;
	}

	public String getResult() {
		if (!status.equals("completed")) {
			throw new IllegalStateException("Build not completed");
		}

		return result;
	}

	public String getStatus() {
		return status;
	}

	public void setURL(String url) {
		url = decodeURL(url);

		Matcher buildURLMatcher = _buildURLPattern.matcher(url);

		if (!buildURLMatcher.find()) {
			throw new IllegalArgumentException("Invalid build URL. " + url);
		}

		master = buildURLMatcher.group("master");
		name = buildURLMatcher.group("name");
		number = Integer.parseInt(buildURLMatcher.group("number"));

		status = "running";
	}

	public void setCompleted(String result) {
		this.result = result;
		this.status = "completed";
	}

	public void setNumber(int number) {
		if (status.equals("starting") || status.equals("queued")) {
			this.number = number;

			status = "running";
		}
	}

	public abstract void update() throws Exception;

	protected BaseJob() {
		master = "";
		name = "";
		status = "starting";
	}

	protected BaseJob(String buildURL) {
		setURL(buildURL);
	}

	protected String master;
	protected String name;
	protected int number;
	protected String result;
	protected String status;


	private static final Pattern _buildURLPattern = Pattern.compile(
		"\\w+://(?<master>[^/]+)/+job/+(?<name>[^/]+).*/(?<number>\\d+)/?");

}