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

import com.google.common.collect.Lists;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil.HttpRequestMethod;

import java.io.IOException;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Peter Yoo
 */
public class GitHubRemoteGitRepository extends BaseRemoteGitRepository {

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

		String labelsRequestURL = getLabelRequestURL();

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
		String labelRequestURL = getLabelRequestURL();

		if (_labelsLists.containsKey(labelRequestURL)) {
			return _labelsLists.get(labelRequestURL);
		}

		JSONArray labelsJSONArray;

		Set<Label> labels = new HashSet<>();

		int page = 1;

		while (page <= _PAGES_LABEL_PAGES_SIZE_MAX) {
			try {
				labelsJSONArray = JenkinsResultsParserUtil.toJSONArray(
					JenkinsResultsParserUtil.combine(
						labelRequestURL, "?page=", String.valueOf(page)),
					false);
			}
			catch (IOException ioe) {
				throw new RuntimeException(
					JenkinsResultsParserUtil.combine(
						"Unable to get labels for ", getName(),
						" Git repository"),
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

		_labelsLists.put(labelRequestURL, Lists.newArrayList(labels));

		return Lists.newArrayList(labels);
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
					" Git repository"));
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
			getLabelRequestURL(), "/", oldLabel.getName());

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

			_labelsLists.remove(getLabelRequestURL());
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
			GitHubRemoteGitRepository gitHubRemoteGitRepository) {

			_jsonObject = jsonObject;
			_gitHubRemoteGitRepository = gitHubRemoteGitRepository;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}

			if (!(obj instanceof Label)) {
				return false;
			}

			Label label = (Label)obj;

			if (Objects.equals(getColor(), label.getColor()) &&
				Objects.equals(getName(), label.getName())) {

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

		public GitHubRemoteGitRepository getGitHubRemoteGitRepository() {
			return _gitHubRemoteGitRepository;
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

		private final GitHubRemoteGitRepository _gitHubRemoteGitRepository;
		private final JSONObject _jsonObject;

	}

	protected GitHubRemoteGitRepository(GitRemote gitRemote) {
		super(gitRemote);

		String hostname = getHostname();

		if (!hostname.equals("github.com")) {
			throw new IllegalArgumentException(
				getName() + " is not a GitHub repository");
		}
	}

	protected GitHubRemoteGitRepository(
		String gitHubRemoteGitRepositoryName, String username) {

		super("github.com", gitHubRemoteGitRepositoryName, username);
	}

	protected String getLabelRequestURL() {
		if (_labelRequestURL != null) {
			return _labelRequestURL;
		}

		_labelRequestURL = JenkinsResultsParserUtil.getGitHubApiUrl(
			getName(), getUsername(), "/labels");

		return _labelRequestURL;
	}

	protected void setLabelRequestURL(String labelRequestURL) {
		_labelRequestURL = labelRequestURL;
	}

	private static final int _PAGES_LABEL_PAGES_SIZE_MAX = 10;

	private static final Map<String, List<Label>> _labelsLists =
		new ConcurrentHashMap<>();

	private String _labelRequestURL;

}