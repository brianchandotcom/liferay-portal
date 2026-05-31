/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {faroConfig} from '../tests/osb-faro-web/main/faro.config';
import {performAnalyticsCloudLoginViaApi} from '../utils/performLogin';

export interface Login {
	password: string;
	sessionId: string;
	user: string;
}

function loginAnalyticsCloudTest() {
	return test.extend<{
		loginAnalyticsCloud: Login;
	}>({
		loginAnalyticsCloud: [
			async ({page}, use) => {
				const cookies = await performAnalyticsCloudLoginViaApi(page);

				await use({
					password: faroConfig.user.password,
					sessionId: cookies.find(
						(cookie) => cookie.name === 'JSESSIONID'
					).value,
					user: faroConfig.user.login,
				});
			},
			{auto: true},
		],
	});
}

export {loginAnalyticsCloudTest};
