/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, test} from '@playwright/test';

import {liferayConfig} from '../liferay.config';
import {performLoginViaApi} from '../utils/performLogin';

export interface RemotePage {
	remotePage: Page;
}

/**
 * Obtain a logged in page with enough privileges to be able to manipulate the backend via UI
 * or Liferay.fetch() invocations, for example.
 *
 * The provided `backendPage` is guaranteed to be at the home page.
 */
const remotePageTest = test.extend<RemotePage>({
	remotePage: async ({browser, page}, use) => {
		await page.goto('/');

		const remoteContext = await browser.newContext({
			baseURL: liferayConfig.environment.baseUrl.replace('8080', '9080'),
		});

		const remotePage = await remoteContext.newPage();

		await performLoginViaApi({
			loginUrl: liferayConfig.environment.baseUrl.replace('8080', '9080'),
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

export {remotePageTest};
