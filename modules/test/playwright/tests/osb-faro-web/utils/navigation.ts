/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import { Page } from '@playwright/test';
import {faroConfig} from '../faro.config';

export async function navigateToACPage(page: Page) {
	await page.goto(faroConfig.environment.baseUrl);

	await page
		.getByRole('link', {
			name: 'FARO-DEV-liferay Liferay Demo Enterprise Plan',
		})
		.click();
}

export async function navigateToACPageViaURL(page: Page, projectID: number, channelID: number) {
	await page.goto(
		`${faroConfig.environment.baseUrl}/workspace/${projectID}/${channelID}/sites`
	);
}

export async function navigateTo(page: Page, pageName: string) {
	await page.getByRole('link', { name: pageName }).first().click();

}
