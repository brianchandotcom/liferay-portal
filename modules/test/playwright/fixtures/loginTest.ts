/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Cookie, test} from '@playwright/test';

import createTempFile, {
	TempFileMissingError,
	readTempFile,
} from '../utils/createTempFile';
import performLogin, {LoginScreenName} from '../utils/performLogin';

export interface LoginOptions {
	screenName?: LoginScreenName;
}

export interface Login {
	login: {
		screenName: LoginScreenName;
		sessionId: string;
	};
}

function loginTest(options: LoginOptions = {}) {
	const fixtureImpl = test.extend<Login>({
		login: [
			async ({page}, use) => {
				const screenName = options.screenName || 'test';
				const tempFile = `loginTest-${screenName}.json`;

				let cookies: Cookie[];

				try {
					const json = JSON.parse(readTempFile(tempFile));

					cookies = json.cookies;

					page.context().addCookies(cookies);

					await page.goto('/');
				}
				catch (error) {
					if (!(error instanceof TempFileMissingError)) {
						throw error;
					}

					cookies = await performLogin(page, screenName);

					createTempFile(tempFile, JSON.stringify({cookies}));
				}

				await use({
					screenName,
					sessionId: cookies.find(
						(cookie) => cookie.name === 'JSESSIONID'
					).value,
				});
			},
			{auto: true},
		],
	});

	return fixtureImpl;
}

export {loginTest};
