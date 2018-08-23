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

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil.HttpRequestMethod;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Peter Yoo
 */
public class GitHubRemoteRepository extends RemoteRepository {

	public boolean addLabel(String color, String description, String name) {
		if (hasLabel(name)) {
			return true;
		}

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("color", color);
		jsonObject.put("name", name);

		if ((description != null) && !description.isEmpty()) {
			jsonObject.put("description", description);
		}

		String labelsRequestURL = _getLabelRequestURL();

		try {
			JenkinsResultsParserUtil.toString(
				labelsRequestURL, jsonObject.toString());

			_labelsLists.remove(labelsRequestURL);
		}
		catch (IOException ioe) {
			System.out.println("Unable to add label " + name);

			ioe.printStackTrace();

			return false;
		}

		return true;
	}

	public void deleteLabel(Label oldLabel) {
		updateLabel(null, null, null, oldLabel);
	}

	public Label getLabel(String name) {
		for (Label label : getLabels()) {
			if (name.equals(label.getName())) {
				return label;
			}
		}

		return null;
	}

	public List<Label> getLabels() {
		String labelRequestURL = _getLabelRequestURL();

		if (_labelsLists.containsKey(labelRequestURL)) {
			return _labelsLists.get(labelRequestURL);
		}

		JSONArray labelsJSONArray;

		List<Label> labels = new ArrayList<>();

		int page = 1;

		while (true) {
			try {
				labelsJSONArray = JenkinsResultsParserUtil.toJSONArray(
					JenkinsResultsParserUtil.combine(
						labelRequestURL, "?page=", String.valueOf(page)),
					false);
			}
			catch (IOException ioe) {
				throw new RuntimeException(
					JenkinsResultsParserUtil.combine(
						"Unable to get labels for ", getName(), " repository"),
					ioe);
			}

			if (labelsJSONArray.length() == 0) {
				break;
			}

			for (int i = 0; i < labelsJSONArray.length(); i++) {
				labels.add(new Label((JSONObject)labelsJSONArray.get(i), this));
			}

			page++;
		}

		_labelsLists.put(labelRequestURL, labels);

		return labels;
	}

	public boolean hasLabel(String name) {
		if (getLabel(name) == null) {
			return false;
		}

		return true;
	}

	public void updateLabel(
		String color, String description, String name, Label oldLabel) {

		if (!hasLabel(oldLabel.getName())) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to update or delete label ", oldLabel.getName(),
					" because it does not exist in the ", getName(),
					" repository"));
		}

		JSONObject jsonObject = null;

		if (name != null) {
			jsonObject = new JSONObject();

			jsonObject.put("color", color);
			jsonObject.put("name", name);

			if ((description != null) && !description.isEmpty()) {
				jsonObject.put("description", description);
			}
		}

		String labelRequestURL = JenkinsResultsParserUtil.combine(
			_getLabelRequestURL(), "/", oldLabel.getName());

		try {
			if (jsonObject == null) {
				JenkinsResultsParserUtil.toString(
					labelRequestURL, false, HttpRequestMethod.DELETE);
			}
			else {
				JenkinsResultsParserUtil.toString(
					labelRequestURL, HttpRequestMethod.PATCH,
					jsonObject.toString());
			}

			_labelsLists.remove(_getLabelRequestURL());
		}
		catch (IOException ioe) {
			if (jsonObject == null) {
				System.out.println(
					"Unable to delete label " + oldLabel.getName());
			}
			else {
				System.out.println(
					"Unable to update label " + oldLabel.getName());
			}

			ioe.printStackTrace();
		}
	}

	public static class Label {

		public Label(
			JSONObject jsonObject,
			GitHubRemoteRepository gitHubRemoteRepository) {

			_jsonObject = jsonObject;
			_gitHubRemoteRepository = gitHubRemoteRepository;
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

			String color = getColor();
			String name = getName();

			if (color.equals(label.getColor()) &&
				name.equals(label.getName())) {

				return true;
			}

			return false;
		}

		public String getColor() {
			return _jsonObject.getString("color");
		}

		public String getDescription() {
			return _jsonObject.optString("description");
		}

		public GitHubRemoteRepository getGitHubRemoteRepository() {
			return _gitHubRemoteRepository;
		}

		public String getName() {
			return _jsonObject.getString("name");
		}

		@Override
		public int hashCode() {
			String name = getName();

			return name.hashCode();
		}

		@Override
		public String toString() {
			return _jsonObject.toString(4);
		}

		private final GitHubRemoteRepository _gitHubRemoteRepository;
		private final JSONObject _jsonObject;

	}

	protected GitHubRemoteRepository(BaseGitRemote baseGitRemote) {
		super(baseGitRemote);

		if (!hostname.equals("github.com")) {
			throw new IllegalArgumentException(
				name + " is not a GitHub repository");
		}
	}

	protected GitHubRemoteRepository(
		String gitHubRemoteRepositoryName, String username) {

		super("github.com", gitHubRemoteRepositoryName, username);
	}

	private String _getLabelRequestURL() {
		return JenkinsResultsParserUtil.getGitHubApiUrl(
			getName(), getUsername(), "/labels");
	}

	private static final Map<String, List<Label>> _labelsLists =
		new ConcurrentHashMap<>();

}