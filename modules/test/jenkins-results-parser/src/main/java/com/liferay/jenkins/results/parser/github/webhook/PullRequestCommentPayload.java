/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.github.webhook;

import com.liferay.jenkins.results.parser.PullRequest;
import com.liferay.jenkins.results.parser.PullRequestFactory;

import org.json.JSONObject;

/**
 * @author Peter Yoo
 */
public class PullRequestCommentPayload extends PullRequestPayload {

	public PullRequestCommentPayload(JSONObject jsonObject) {
		super(jsonObject);

		comment = new PullRequest.Comment(jsonObject.getJSONObject("comment"));
	}

	public PullRequest.Comment getComment() {
		return comment;
	}

	@Override
	public PullRequest getPullRequest() {
		if (pullRequest == null) {
			pullRequest = PullRequestFactory.newPullRequest(
				get("issue/pull_request/html_url"));
		}

		return pullRequest;
	}

	protected PullRequest.Comment comment;

}