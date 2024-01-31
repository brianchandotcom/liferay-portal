/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, test} from '@playwright/test';

import createTempFile, {
	TempFileMissingError,
	readTempFile,
} from '../utils/createTempFile';

export interface LoginOptions {
	screenName?:
		| 'default-company-admin'
		| 'test'
		| 'test-organization-owner'
		| 'unprivileged';
}

export interface Login {
	password: string;
	sessionId: string;
	user: string;
}

/**
 * This fixture performs a login in the default test page for the user with the given screen name
 * and leaves it in the home page.
 *
 * This fixture needs some pre-cooked users in the test DXP instance. They are created by deploying
 * the `modules/test/playwright-setup` OSGi bundle before Playwright is run.
 *
 * That is automatically done by the CI. In local environments, you need to run
 * `npm run test:setup <name of the test>` before running Playwright to achieve the same results.
 *
 * @param options the screen name to use for performing the login
 *
 * @example
 * export const test = mergeTests(
 *   loginTest('unprivileged'),
 *   ...
 * );
 *
 * test('something', ...);
 */
function loginTest(options: LoginOptions = {}) {
	const fixtureImpl = test.extend<{
		login: Login;
	}>({
		login: [
			async ({page}, use) => {
				const screenName = options.screenName || 'test';
				const user = `${screenName}@liferay.com`;
				const password = 'test';
				const tempFile = `loginTest-${screenName}.json`;

				try {
					const {cookies} = JSON.parse(readTempFile(tempFile));

					page.context().addCookies(cookies);
				}
				catch (error) {
					if (!(error instanceof TempFileMissingError)) {
						throw error;
					}

					const storageStatePath = createTempFile(tempFile);

					await page.goto('/');

					await page.getByRole('button', {name: 'Sign In'}).click();

					await page.getByLabel('Email Address').fill(user);
					await page.getByLabel('Password').fill(password);
					await page.getByLabel('Remember Me').check();

					await page
						.getByLabel('Sign In- Loading')
						.getByRole('button', {name: 'Sign In'})
						.click();

					await expect(
						page.getByLabel(
							`${screenName} ${screenName} User Profile`
						)
					).toBeVisible({
						timeout: 30 * 1000,
					});

					await page.context().storageState({path: storageStatePath});
				}

				const cookies = await page.context().cookies();

				await use({
					password,
					sessionId: cookies.find(
						(cookie) => cookie.name === 'JSESSIONID'
					).value,
					user,
				});
			},
			{auto: true},
		],
	});

	return fixtureImpl;
}

export {loginTest};
