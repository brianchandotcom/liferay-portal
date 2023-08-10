/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fetcher from '../fetcher';
import {Liferay} from '../liferay';
import {TestrayRequirement} from './types';

class JiraClientExtensionRest {
	public fetcher = fetcher;
	public headers = {'liferay-user-id': Liferay.ThemeDisplay.getUserId()};

	public oAuth2Client = Liferay.OAuth2Client.FromUserAgentApplication(
		'liferay-testray-etc-jira-node-oauth-application-user-agent'
	);

	public async preauthorize() {
		return this.oAuth2Client.fetch(
			`/jira/preauthorize/${Liferay.ThemeDisplay.getUserId()}`
		);
	}

	public async resyncWithJira(
		testrayRequirement: TestrayRequirement
	): Promise<void> {
		await this.oAuth2Client.fetch(`/jira/resync`, {
			body: JSON.stringify({
				objectEntry: {
					...testrayRequirement,
					statusByUserId: Liferay.ThemeDisplay.getUserId(),
					values: testrayRequirement,
				},
			}),
			method: 'POST',
		});
	}
}

export const JiraClientExtensionRestImpl = new JiraClientExtensionRest();
