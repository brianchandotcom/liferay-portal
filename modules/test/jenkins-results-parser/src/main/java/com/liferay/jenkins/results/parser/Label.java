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

import java.io.IOException;

import java.util.Random;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class Label {

	public static boolean isValidLabelsURL(String labelsURL) {
		if (labelsURL.matches(
				"https://api.github.com/repos/[^/]+/[^/]+/labels")) {

			return true;
		}

		return false;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		if (!(o instanceof Label)) {
			return false;
		}

		Label label = (Label)o;

		String name = getName();

		if (name.equals(label.getName())) {
			return true;
		}

		return false;
	}

	public String getColor() {
		return _jsonObject.getString("color");
	}

	public String getName() {
		return _jsonObject.getString("name");
	}

	@Override
	public int hashCode() {
		String name = getName();

		return name.hashCode();
	}

	public void setColor(String color) {
		if (color == null) {
			throw new IllegalArgumentException("Color is required");
		}

		if (!color.matches("[a-f0-9]{6}")) {
			throw new RuntimeException("Invalid color " + color);
		}

		if (color.equals(getColor())) {
			return;
		}

		_jsonObject.put("color", color);

		_updateGithub();
	}

	protected Label(JSONObject jsonObject) {
		_jsonObject = jsonObject;
	}

	protected Label(String labelsURL, String name, String color) {
		if (!isValidLabelsURL(labelsURL)) {
			throw new IllegalArgumentException("Invalid url " + labelsURL);
		}

		if (color == null) {
			color = _getRandomColor();
		}

		if (name == null) {
			throw new IllegalArgumentException("Invalid name " + name);
		}

		_jsonObject = _getJSONObject(labelsURL, name, color);
	}

	private JSONObject _getJSONObject(
		String labelsURL, String name, String color) {

		try {
			JSONObject colorJSONObject = new JSONObject();

			colorJSONObject.put("color", color);

			JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
				JenkinsResultsParserUtil.fixURL(
					JenkinsResultsParserUtil.combine(labelsURL, "/", name)),
				colorJSONObject.toString());

			return jsonObject;
		}
		catch (IOException ioe) {
			ioe.printStackTrace();

			System.out.println("Creating missing label " + name);
		}

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("color", color);
		jsonObject.put("name", name);

		try {
			return JenkinsResultsParserUtil.toJSONObject(
				JenkinsResultsParserUtil.fixURL(labelsURL),
				jsonObject.toString());
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	private String _getRandomColor() {
		Random random = new Random();

		return String.format("%06x", random.nextInt(256 * 256 * 256));
	}

	private void _updateGithub() {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("color", getColor());
		jsonObject.put("name", getName());

		try {
			JenkinsResultsParserUtil.toJSONObject(
				_jsonObject.getString("url"), jsonObject.toString());
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	private final JSONObject _jsonObject;

}