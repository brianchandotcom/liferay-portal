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

	public static String decodeURL(String url) {
		url = url.replace("%28", "(");
		url = url.replace("%29", ")");
		url = url.replace("%5B", "[");
		url = url.replace("%5D", "]");

		return url;
	}

	public static String formatBuildURL(String buildURL) {
		buildURL = decodeURL(buildURL);

		Matcher buildURLMatcher = _buildURLPattern.matcher(buildURL);

		if (!buildURLMatcher.find()) {
			throw new IllegalArgumentException("Invalid build URL");
		}

		String masterURL = buildURLMatcher.group("masterURL");
		String name = buildURLMatcher.group("name");
		int number = Integer.parseInt(buildURLMatcher.group("number"));

		return getBuildURL(masterURL, name, number);
	}

	public String getBuildURL() {
		if (!status.equals("running") && !status.equals("completed")) {
			throw new IllegalStateException(
				"Staring job does not have build URL");
		}

		return getBuildURL(masterURL, name, number);
	}

	public String getJobURL() {
		StringBuilder sb = new StringBuilder();

		sb.append(masterURL);
		sb.append("/job/");
		sb.append(name);

		return sb.toString();
	}

	public String getMasterURL() {
		return masterURL;
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

	public void setBuildURL(String buildURL) {
		if (status.equals("starting") || status.equals("queued")) {
			buildURL = formatBuildURL(buildURL);

			Matcher buildURLMatcher = _buildURLPattern.matcher(buildURL);

			buildURLMatcher.find();

			number = Integer.parseInt(buildURLMatcher.group("number"));

			status = "running";
		}
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
		masterURL = "";
		name = "";
		status = "starting";
	}

	protected BaseJob(String buildURL) {
		buildURL = formatBuildURL(buildURL);

		Matcher buildURLMatcher = _buildURLPattern.matcher(buildURL);

		buildURLMatcher.find();

		this.masterURL = buildURLMatcher.group("masterURL");
		this.name = buildURLMatcher.group("name");
		this.number = Integer.parseInt(buildURLMatcher.group("number"));
		this.status = "running";
	}

	protected String masterURL;
	protected String name;
	protected int number;
	protected String result;
	protected String status;

	private static String getBuildURL(
		String masterURL, String name, int number) {

		StringBuilder sb = new StringBuilder();

		sb.append(masterURL);
		sb.append("/job/");
		sb.append(name);
		sb.append("/");
		sb.append(number);

		return sb.toString();
	}

	private static final Pattern _buildURLPattern = Pattern.compile(
		"(?<masterURL>\\w+://[^/]+)/+job/+(?<name>[^/]+).*/(?<number>\\d+)/?");

}