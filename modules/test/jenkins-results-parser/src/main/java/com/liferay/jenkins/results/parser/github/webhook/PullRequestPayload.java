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
public class PullRequestPayload extends Payload {

	public PullRequestPayload(JSONObject jsonObject) {
		super(jsonObject);
	}

	public PullRequest getPullRequest() {
		if (pullRequest == null) {
			pullRequest = PullRequestFactory.newPullRequest(
				get("pull_request/_links/html/href"));
		}

		return pullRequest;
	}

	protected PullRequest pullRequest;

}