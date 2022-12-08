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

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

/**
 * @author Charlotte Wong
 */
public class JIRAUtil {

	// this class should be statelsss

	// move generate comment into job --

	// get issues should be able to be called from PullRequest object

	// cache issues with timestamp maybe -- low prio

	// add JIRAIssue class(?) might make adding timestamp easier -- lowest prio

	//  delete from cache on update -- low prio

	// forced refresh of cache -- low prio

	// issue number should be issueId

	// allowed projects should be a property

	public static Issue getIssue(String issueId) {
		if (_issueCache == null) {
			_issueCache = new ArrayList<>();
		}

		if (_timestampCache == null) {
			_timestampCache = new ArrayList<>();
		}

		Date date = new Date();

		long currentTime = date.getTime();

		for (int i = 0; i < _issueCache.size(); i++) {
			Issue issue = _issueCache.get(i);

			String id = issue.getKey();

			if (id.equals(issueId)) {
				long timestamp = _timestampCache.get(i);

				if ((currentTime - timestamp) > 300000) {
					return issue;
				}

				break;
			}
		}

		if (_issueRestClient == null) {
			_initRestClient();
		}

		Promise<Issue> promise = _issueRestClient.getIssue(issueId);

		Issue issue = promise.claim();

		_issueCache.add(issue);

		_timestampCache.add(currentTime);

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

			for (int i = 0; i < _issueCache.size(); i++) {
				Issue cachedIssue = _issueCache.get(i);

				if (cachedIssue == issue) {
					_issueCache.remove(i);
					_timestampCache.remove(i);

					break;
				}
			}
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

			for (int i = 0; i < _issueCache.size(); i++) {
				Issue cachedIssue = _issueCache.get(i);

				if (cachedIssue == issue) {
					_issueCache.remove(i);
					_timestampCache.remove(i);

					break;
				}
			}
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

	private static List<Issue> _issueCache;
	private static IssueRestClient _issueRestClient;
	private static Properties _jenkinsBuildProperties;
	private static String _jiraAdminPassword;
	private static String _jiraAdminUsername;
	private static JiraRestClient _jiraRestClient;
	private static JiraRestClientFactory _jiraRestClientFactory;
	private static List<Long> _timestampCache;
	private static Iterable<Transition> _transitions;

}