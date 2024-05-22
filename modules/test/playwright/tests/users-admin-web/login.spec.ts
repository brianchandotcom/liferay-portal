/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect} from '@playwright/test';

import {LoginOptions, loginTest} from '../../fixtures/loginTest';

const sessionIds = [];

for (const screenName of [
	'demo.company.admin',
	'demo.organization.owner',
	'demo.unprivileged',
	'test',
]) {
	const test = loginTest({screenName} as LoginOptions);

	test(`Login with screen name '${screenName}' works`, async ({login}) => {

		// Check correct login

		expect(login.screenName).toBe(screenName);

		// Check session id is not reused

		expect(sessionIds).not.toContain(login.sessionId);

		sessionIds.push(login.sessionId);
	});
}
