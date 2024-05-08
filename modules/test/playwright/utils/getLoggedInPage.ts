/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {BrowserContext, Page} from '@playwright/test';

import createTempFile, {
	TempFileMissingError,
	readTempFile,
} from './createTempFile';
import performLogin, {LoginScreenName} from './performLogin';

/**
 * Obtain a logged in page.
 *
 * The provided `loggedInPage` is guaranteed to be at the home page.
 *
 * @param browserContext a BrowserContext instance
 * @param screenName the screen name to use for performing the login
 */
export default async function getLoggedInPage(
	browserContext: BrowserContext,
	screenName: LoginScreenName
): Promise<Page> {
	const loggedInPage = await browserContext.newPage();
	const tempFile = `loggedInPageTest-${screenName}.json`;

	try {
		const {cookies} = JSON.parse(readTempFile(tempFile));

		browserContext.addCookies(cookies);

		await loggedInPage.goto('/');
	}
	catch (error) {
		if (!(error instanceof TempFileMissingError)) {
			throw error;
		}

		const cookies = await performLogin(loggedInPage, screenName);

		createTempFile(tempFile, JSON.stringify({cookies}));
	}

	return loggedInPage;
}
