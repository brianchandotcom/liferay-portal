/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, test} from '@playwright/test';

import {liferayConfig} from '../liferay.config';
import {performLoginViaApi} from '../utils/performLogin';

/**
 * Obtain a logged in page with enough privileges to be able to manipulate the remotePage via UI
 * or Liferay.fetch() invocations, for example.
 *
 * The provided `remotePage` is guaranteed to be at the home page.
 */
function remotePageTest(port: string) {
	return test.extend<{
		remotePage: Page;
	}>({
		remotePage: async ({browser, page}, use) => {
			await page.goto('/');

			const remoteUrl = liferayConfig.environment.baseUrl.replace(
				'8080',
				port
			);

			const remoteContext = await browser.newContext({
				baseURL: remoteUrl,
			});

			const remotePage = await remoteContext.newPage();

			await performLoginViaApi({
				loginUrl: remoteUrl,
				page: remotePage,
				screenName: 'test',
			});

			try {
				await use(remotePage);
			}
			finally {
				await remoteContext.close();
			}
		},
	});
}

export {remotePageTest};
