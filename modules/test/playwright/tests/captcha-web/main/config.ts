/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {devices} from '@playwright/test';

export const config = {
	name: 'captcha-web',
	testDir: 'tests/captcha-web',
	use: {
		...devices['Desktop Chrome'],
	},
};

export const reCaptchaConfig = {
	privateKey: '6LeIxAcTAAAAAGG-vFI1TnRWxMZNFuojJ4WifJWe',
	publicKey: '6LeIxAcTAAAAAJcZVRqyHh71UMIEGNQ_MXjiZKhI',
};
