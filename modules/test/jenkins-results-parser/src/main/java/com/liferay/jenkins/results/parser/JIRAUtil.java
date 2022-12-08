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

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Charlotte Wong
 */
public class JIRAUtil {

	public static Issue getIssue(String issueId) {
		if (_issueMap.containsKey(issueId)) {
			CachedIssue cachedIssue = _issueMap.get(issueId);

			if (!cachedIssue.isExpired()) {
				return cachedIssue.issue;
			}
		}

		if (_issueRestClient == null) {
			_initRestClient();
		}

		Promise<Issue> promise = _issueRestClient.getIssue(issueId);

		Issue issue = promise.claim();

		_issueMap.put(issueId, new CachedIssue(issue));

		return issue;
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

			_issueMap.remove(issue.getKey());
		}
		catch (Exception exception) {
			System.out.println(
				"Unable to execute transition " + exception.getMessage());
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
				"Unable to find transition with name: " + transitionName);
		}

		TransitionInput transitionInput = new TransitionInput(
			transitionId, Comment.valueOf(comment));

		try {
			Promise<Void> transition = _issueRestClient.transition(
				issue, transitionInput);

			transition.get();

			_issueMap.remove(issue.getKey());
		}
		catch (Exception exception) {
			System.out.println(
				"Unable to execute transition " + exception.getMessage());
		}
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

		System.out.println(_transitions);

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

	private static final Map<String, CachedIssue> _issueMap =
		new ConcurrentHashMap<>();
	private static IssueRestClient _issueRestClient;
	private static Properties _jenkinsBuildProperties;
	private static String _jiraAdminPassword;
	private static String _jiraAdminUsername;
	private static JiraRestClient _jiraRestClient;
	private static JiraRestClientFactory _jiraRestClientFactory;
	private static Iterable<Transition> _transitions;

	private static class CachedIssue {

		public CachedIssue(Issue issue) {
			this.issue = issue;

			timestamp = System.currentTimeMillis();
		}

		public boolean isExpired() {
			if ((System.currentTimeMillis() - timestamp) > _MAX_ISSUE_AGE) {
				return true;
			}

			return false;
		}

		public final Issue issue;
		public final Long timestamp;

		private static final long _MAX_ISSUE_AGE = 1000 * 60 * 5;

	}

}