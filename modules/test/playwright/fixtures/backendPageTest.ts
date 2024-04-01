/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, test} from '@playwright/test';

import createTempFile, {
	TempFileMissingError,
	readTempFile,
} from '../utils/createTempFile';
import performLogin from '../utils/performLogin';

export interface BackendPage {
	backendPage: Page;
}

/**
 * Obtain a logged in page with enough privileges to be able to manipulate the backend via UI
 * or Liferay.fetch() invocations, for example.
 *
 * The provided `backendPage` is guaranteed to be at the home page.
 */
const backendPageTest = test.extend<BackendPage>({
	backendPage: async ({browser, page}, use) => {
		await page.goto('/');

		const backendContext = await browser.newContext();
		const backendPage = await backendContext.newPage();
		const tempFile = `backendPageTest.json`;

		try {
			const {cookies} = JSON.parse(readTempFile(tempFile));

			backendContext.addCookies(cookies);

			await backendPage.goto('/');
		}
		catch (error) {
			if (!(error instanceof TempFileMissingError)) {
				throw error;
			}

			const cookies = await performLogin(backendPage, 'test');

			createTempFile(tempFile, JSON.stringify({cookies}));
		}

		try {
			await use(backendPage);
		}
		finally {
			await backendContext.close();
		}
	},
});

export {backendPageTest};
