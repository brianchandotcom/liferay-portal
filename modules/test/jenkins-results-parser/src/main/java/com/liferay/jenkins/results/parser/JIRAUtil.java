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

	public static Issue getIssue(String issueKey) {
		if (_issueMap.containsKey(issueKey)) {
			CachedIssue cachedIssue = _issueMap.get(issueKey);

			if (!cachedIssue.isExpired()) {
				return cachedIssue.issue;
			}
		}

		Promise<Issue> promise = _issueRestClient.getIssue(issueKey);

		Issue issue = promise.claim();

		_issueMap.put(issueKey, new CachedIssue(issue));

		return issue;
	}

	public static void transition(
		String comment, Issue issue, int transitionId) {

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

	private static int _getTransitions(Issue issue, String transitionName) {
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

	private static IssueRestClient _initIssueRestClient() {
		Properties buildProperties = null;

		try {
			buildProperties = JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to get build properties", ioException);
		}

		String jiraAdminPassword = buildProperties.getProperty(
			"ci.jira.admin.password");
		String jiraAdminUsername = buildProperties.getProperty(
			"ci.jira.admin.username");

		JiraRestClientFactory jiraRestClientFactory =
			new AsynchronousJiraRestClientFactory();

		JiraRestClient jiraRestClient =
			jiraRestClientFactory.createWithBasicHttpAuthentication(
				_URI, jiraAdminUsername, jiraAdminPassword);

		return jiraRestClient.getIssueClient();
	}

	private static final URI _URI = URI.create("https://issues.liferay.com");

	private static final Map<String, CachedIssue> _issueMap =
		new ConcurrentHashMap<>();
	private static final IssueRestClient _issueRestClient =
		_initIssueRestClient();
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