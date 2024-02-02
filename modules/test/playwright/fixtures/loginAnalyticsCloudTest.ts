/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, test} from '@playwright/test';

import {faroConfig} from '../tests/osb-faro-web/faro.config';
import createTempFile, {readTempFile} from '../utils/createTempFile';
export interface Login {
	password: string;
	sessionId: string;
	user: string;
}

let loggedIn = false;

function loginAnalyticsCloudTest() {
	const fixtureImpl = test.extend<{
		loginAnalyticsCloud: Login;
	}>({
		loginAnalyticsCloud: [
			async ({page}, use) => {
				const user = faroConfig.user.login;
				const password = faroConfig.user.password;

				if (!loggedIn) {
					const storageStatePath = createTempFile(
						'analyticsCloudStorageState.json'
					);

					await page.goto(faroConfig.environment.baseUrl);

					await page.getByRole('button', {name: 'Sign In'}).click();

					await page.getByLabel('Email Address').fill(user);
					await page.getByLabel('Password').fill(password);
					await page.getByLabel('Remember Me').check();

					await page.getByRole('button', {name: 'Sign In'}).click();

					await expect(page.getByText('Your Workspaces')).toBeVisible(
						{
							timeout: 100 * 1000,
						}
					);

					await page.context().storageState({path: storageStatePath});

					loggedIn = true;
				}
				else {
					const {cookies} = JSON.parse(
						readTempFile('analyticsCloudStorageState.json')
					);

					page.context().addCookies(cookies);
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

export {loginAnalyticsCloudTest};
