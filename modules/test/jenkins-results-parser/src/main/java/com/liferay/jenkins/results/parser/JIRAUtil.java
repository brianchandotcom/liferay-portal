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

import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.api.domain.Comment;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.Transition;
import com.atlassian.jira.rest.client.api.domain.input.TransitionInput;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;

import io.atlassian.util.concurrent.Promise;

import java.io.IOException;

import java.net.URI;

import java.util.Collections;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

/**
 * @author Charlotte Wong
 */
public class JIRAUtil {

	public static void addIssue(String issueNumber) {
		Issue issue = _getIssue(issueNumber);

		if (_issueSet == null) {
			_issueSet = Collections.emptySet();
		}

		Set<Issue> issueSet = Collections.singleton(issue);

		issueSet.addAll(_issueSet);

		_issueSet = issueSet;
	}

	public static String generateComment(PullRequest pullRequest) {
		StringBuilder sb = new StringBuilder();

		sb.append(
			"The following tickets were automatically submitted for review:");

		sb.append("\n");

		for (Issue issue : _issueSet) {
			String issueKey = issue.getKey();

			sb.append("https://issues.liferay.com/browse/");

			sb.append(issueKey);

			sb.append("\n");
		}

		sb.append("\n");

		sb.append("Pull request: ");

		sb.append(
			pullRequest.getURL(
				pullRequest.getOwnerUsername(),
				pullRequest.getGitHubRemoteGitRepositoryName(),
				pullRequest.getNumber()));

		return sb.toString();
	}

	public static Set<Issue> getIssues() {
		if (_issueSet == null) {
			_issueSet = Collections.emptySet();
		}

		if (_issueSet.isEmpty()) {
			throw new IllegalStateException(
				"Unable to find any valid issues within pull request");
		}

		return _issueSet;
	}

	public static void transition(
		String comment, Issue issue, int transitionId) {

		if (_issueRestClient == null) {
			_initRestClient();
		}

		TransitionInput transitionInput = new TransitionInput(
			transitionId, Comment.valueOf(comment));

		try {
			Promise<Void> transition = _issueRestClient.transition(
				issue, transitionInput);

			transition.get();
		}
		catch (Exception exception) {
			System.out.println(
				"JIRA rest client process workflow action error. cause: " +
					exception.getMessage());
		}
	}

	public static void transition(
		String comment, Issue issue, String transitionName) {

		if (_issueRestClient == null) {
			_initRestClient();
		}

		int transitionId = _getTransitions(issue, transitionName);

		if (transitionId == -1) {
			System.out.println(
				"No valid transition with provided transitionName:" +
					transitionName);
		}

		TransitionInput transitionInput = new TransitionInput(
			transitionId, Comment.valueOf(comment));

		try {
			Promise<Void> transition = _issueRestClient.transition(
				issue, transitionInput);

			transition.get();
		}
		catch (Exception exception) {
			System.out.println(
				"JIRA rest client process workflow action error. cause: " +
					exception.getMessage());
		}
	}

	private static Issue _getIssue(String issueNumber) {
		if (_issueRestClient == null) {
			_initRestClient();
		}

		Promise<Issue> promise = _issueRestClient.getIssue(issueNumber);

		return promise.claim();
	}

	private static void _getProperties() {
		try {
			_jenkinsBuildProperties =
				JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to get build properties", ioException);
		}

		_jiraAdminPassword = _jenkinsBuildProperties.getProperty(
			"ci.jira.admin.password");
		_jiraAdminUsername = _jenkinsBuildProperties.getProperty(
			"ci.jira.admin.username");
	}

	private static int _getTransitions(Issue issue, String transitionName) {
		if (_issueRestClient == null) {
			_initRestClient();
		}

		if (_transitions == null) {
			Promise<Iterable<Transition>> promise =
				_issueRestClient.getTransitions(issue);

			_transitions = promise.claim();
		}

		int transitionId = -1;

		for (Iterator<Transition> iterator = _transitions.iterator();
			 iterator.hasNext();) {

			Transition transition = iterator.next();

			String name = transition.getName();

			if (name.equals(transitionName)) {
				transitionId = transition.getId();
			}
		}

		return transitionId;
	}

	private static void _initRestClient() {
		_getProperties();

		_jiraRestClientFactory = new AsynchronousJiraRestClientFactory();

		_jiraRestClient =
			_jiraRestClientFactory.createWithBasicHttpAuthentication(
				_URI, _jiraAdminUsername, _jiraAdminPassword);

		_issueRestClient = _jiraRestClient.getIssueClient();
	}

	private static final URI _URI = URI.create("https://issues.liferay.com");

	private static IssueRestClient _issueRestClient;
	private static Set<Issue> _issueSet;
	private static Properties _jenkinsBuildProperties;
	private static String _jiraAdminPassword;
	private static String _jiraAdminUsername;
	private static JiraRestClient _jiraRestClient;
	private static JiraRestClientFactory _jiraRestClientFactory;
	private static Iterable<Transition> _transitions;

}