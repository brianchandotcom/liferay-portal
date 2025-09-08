/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.co25
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {ProductAnalyticsBannerPage} from '../pages/cookies-banner-web/ProductAnalyticsBannerPage';

const productAnalyticsPagesTest = test.extend<{
	productAnalyticsBannerPage: ProductAnalyticsBannerPage;
}>({
	productAnalyticsBannerPage: async ({page}, use) => {
		await use(new ProductAnalyticsBannerPage(page));
	},
});

export {productAnalyticsPagesTest};
