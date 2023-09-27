/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.github.webhook;

import org.json.JSONObject;

/**
 * @author Peter Yoo
 */
public class PayloadFactory {

	public static Payload newPayload(JSONObject jsonObject) {
		if (jsonObject.has("pull_request")) {
			return new PullRequestPayload(jsonObject);
		}

		if (jsonObject.has("issue")) {
			return new PullRequestCommentPayload(jsonObject);
		}

		if (jsonObject.has("pusher")) {
			return new PushEventPayload(jsonObject);
		}

		throw new RuntimeException("Unknown payload JSON");
	}

}