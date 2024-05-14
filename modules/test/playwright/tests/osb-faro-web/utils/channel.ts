/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {faroConfig} from '../faro.config';

export async function createChannel(page, name) {
	await page.goto(faroConfig.environment.baseUrl);

	await page
		.getByRole('link', {
			name: 'FARO-DEV-liferay Liferay Demo Enterprise Plan',
		})
		.click();

	await page.getByRole('link', {name: 'Settings'}).click();

	await page.getByRole('link', {name: 'Properties'}).click();

	await page.getByTestId('addproperty-button').click();

	await page.getByLabel('Property Name').fill(name);

	await page.getByRole('button', {name: 'Save'}).click();
}
