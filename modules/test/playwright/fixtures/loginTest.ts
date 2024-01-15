/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, test} from '@playwright/test';

import {liferayConfig} from '../liferay.config';
import createTempFile, {readTempFile} from '../utils/createTempFile';

export interface Login {
	password: string;
	sessionId: string;
	user: string;
}

interface HeadlessClientConfig {
	BASE: string;
	PASSWORD: string;
	USERNAME: string;
}

let loggedIn = false;

const loginTest = test.extend<{
	authenticate: <
		T extends new (config: HeadlessClientConfig) => InstanceType<T>
	>(
		HeadlessClient: T
	) => InstanceType<T>;
	login: Login;
}>({
	authenticate: async ({login}, use) => {
		await use(
			(HeadlessClient) =>
				new HeadlessClient({
					BASE: liferayConfig.environment.baseUrl + '/o',
					PASSWORD: login.password,
					USERNAME: login.user,
				})
		);
	},
	login: [
		async ({page}, use) => {
			const user = liferayConfig.user.login;
			const password = liferayConfig.user.password;

			if (!loggedIn) {
				const storageStatePath = createTempFile('storageState.json');

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
					page.getByLabel('Open Applications MenuCtrl+')
				).toBeVisible({
					timeout: 30 * 1000,
				});

				await page.context().storageState({path: storageStatePath});

				loggedIn = true;
			}
			else {
				const {cookies} = JSON.parse(readTempFile('storageState.json'));

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

export {loginTest};
