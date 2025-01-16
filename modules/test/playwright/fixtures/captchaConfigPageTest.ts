/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {CaptchaConfigPage} from '../pages/captcha-web/CaptchaConfigPage';

const captchaConfigPageTest = test.extend<{
	captchaConfigPage: CaptchaConfigPage;
}>({
	captchaConfigPage: async ({page}, use) => {
		await use(new CaptchaConfigPage(page));
	},
});

export {captchaConfigPageTest};
