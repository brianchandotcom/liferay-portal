/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.github.webhook;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.PullRequest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.net.URLEncoder;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Peter Yoo
 */
public class PullRequestTesterParameters extends HashMap<String, String> {

	public PullRequestTesterParameters(PullRequest pullRequest) {
		_pullRequest = pullRequest;

		Properties jenkinsBuildProperties;

		try {
			jenkinsBuildProperties =
				JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to get build properties", ioException);
		}

		put(_KEY_CI_TEST_SUITE_NAME, "default");

		put(
			_KEY_JENKINS_AUTHENTICATION_TOKEN,
			jenkinsBuildProperties.getProperty("jenkins.authentication.token"));

		String ciForwardReceiverUsername = jenkinsBuildProperties.getProperty(
			"pull.request.forward.default.receiver.username");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(
				ciForwardReceiverUsername)) {

			setCiForwardReceiverUsername(ciForwardReceiverUsername);
		}
	}

	public String getCiForwardReceiverUsername() {
		return get(_KEY_CI_FORWARD_RECEIVER_USERNAME);
	}

	public String getCiReevaluateBuildId() {
		return get(_KEY_CI_REEVALUATE_BUILD_ID);
	}

	public String getCiTestSuiteName() {
		return get(_KEY_CI_TEST_SUITE_NAME);
	}

	public PullRequest getPullRequest() {
		return _pullRequest;
	}

	@Override
	public String put(String key, String value) {
		if (JenkinsResultsParserUtil.isNullOrEmpty(value) && containsKey(key)) {
			return remove(key);
		}

		return super.put(key, value);
	}

	public void setCiForwardReceiverUsername(String ciForwardReceiverUsername) {
		put(_KEY_CI_FORWARD_RECEIVER_USERNAME, ciForwardReceiverUsername);
	}

	public void setCiReevaluateBuildId(String ciReevaluateBuildId) {
		put(_KEY_CI_REEVALUATE_BUILD_ID, ciReevaluateBuildId);
	}

	public void setCiTestSuiteName(String ciTestSuiteName) {
		if (JenkinsResultsParserUtil.isNullOrEmpty(ciTestSuiteName)) {
			ciTestSuiteName = "default";
		}

		put(_KEY_CI_TEST_SUITE_NAME, ciTestSuiteName);
	}

	public void setPortalBundlesDistURL(String portalBundlesDistURL) {
		put(_KEY_PORTAL_BUNDLES_DIST_URL, portalBundlesDistURL);
	}

	public void setUpstreamBranchSHA(String upstreamBranchSHA) {
		put(_KEY_GITHUB_UPSTREAM_BRANCH_SHA, upstreamBranchSHA);
	}

	public String toQueryString() {
		StringBuilder sb = new StringBuilder();

		for (Map.Entry<String, String> entry : entrySet()) {
			_appendParameter(sb, entry.getKey(), entry.getValue());
		}

		_appendParameter(
			sb, _KEY_PULL_REQUEST_NUMBER, _pullRequest.getNumber());
		_appendParameter(
			sb, "GITHUB_ORIGIN_NAME",
			_encode(_pullRequest.getSenderUsername()));
		_appendParameter(
			sb, "GITHUB_RECEIVER_USERNAME", _pullRequest.getReceiverUsername());
		_appendParameter(
			sb, "GITHUB_SENDER_BRANCH_NAME",
			_pullRequest.getSenderBranchName());
		_appendParameter(
			sb, "GITHUB_SENDER_BRANCH_SHA", _pullRequest.getSenderSHA());
		_appendParameter(
			sb, "GITHUB_SENDER_USERNAME",
			_encode(_pullRequest.getSenderUsername()));
		_appendParameter(
			sb, "GITHUB_UPSTREAM_BRANCH_NAME",
			_pullRequest.getUpstreamRemoteGitBranchName());

		if (!containsKey(_KEY_GITHUB_UPSTREAM_BRANCH_SHA)) {
			_appendParameter(
				sb, _KEY_GITHUB_UPSTREAM_BRANCH_SHA,
				_pullRequest.getUpstreamBranchSHA());
		}

		_appendParameter(sb, "PULL_REQUEST_URL", _pullRequest.getHtmlURL());
		_appendParameter(
			sb, "REPOSITORY_NAME", _pullRequest.getGitRepositoryName());

		return sb.toString();
	}

	private void _appendParameter(StringBuilder sb, String name, String value) {
		if (JenkinsResultsParserUtil.isNullOrEmpty(value)) {
			return;
		}

		if (sb.length() > 0) {
			sb.append("&");
		}

		sb.append(name);
		sb.append("=");
		sb.append(value);
	}

	private String _encode(String string) {
		try {
			return URLEncoder.encode(string, "UTF-8");
		}
		catch (UnsupportedEncodingException unsupportedEncodingException) {
			throw new RuntimeException(
				"Unsupported Encoding: UTF-8", unsupportedEncodingException);
		}
	}

	private static final String _KEY_CI_FORWARD_RECEIVER_USERNAME =
		"CI_FORWARD_RECEIVER_USERNAME";

	private static final String _KEY_CI_REEVALUATE_BUILD_ID = "BUILD_ID";

	private static final String _KEY_CI_TEST_SUITE_NAME = "CI_TEST_SUITE";

	private static final String _KEY_GITHUB_UPSTREAM_BRANCH_SHA =
		"GITHUB_UPSTREAM_BRANCH_SHA";

	private static final String _KEY_JENKINS_AUTHENTICATION_TOKEN = "token";

	private static final String _KEY_PORTAL_BUNDLES_DIST_URL =
		"PORTAL_BUNDLES_DIST_URL";

	private static final String _KEY_PULL_REQUEST_NUMBER =
		"GITHUB_PULL_REQUEST_NUMBER";

	private final PullRequest _pullRequest;

}