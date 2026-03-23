/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, test} from '@playwright/test';

import {getHeader} from '../helpers/ApiHelpers';
import {faroConfig} from '../tests/osb-faro-web/main/faro.config';

export interface Login {
	password: string;
	sessionId: string;
	user: string;
}

function loginAnalyticsCloudTest() {
	const fixtureImpl = test.extend<{
		loginAnalyticsCloud: Login;
	}>({
		loginAnalyticsCloud: [
			async ({page}, use) => {
				const loginUrl = faroConfig.environment.baseUrl;
				const password = faroConfig.user.password;
				const user = faroConfig.user.login;

				const params = new URLSearchParams({
					login: faroConfig.user.login,
					password,
					rememberMe: 'true',
				});

				try {
					await page.goto(loginUrl);

					const url = `${loginUrl}/c/portal/login`;

					await expect
						.poll(async () => {
							const response = await page.request.post(url, {
								data: params.toString(),
								headers: await getHeader(
									page,
									'application/x-www-form-urlencoded'
								),
							});

							return response.status();
						})
						.toBe(200);

					await page.goto(loginUrl);
				}
				catch (error) {
					error.message = `Login via API failed\n\n${error.message}`;

					throw error;
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
