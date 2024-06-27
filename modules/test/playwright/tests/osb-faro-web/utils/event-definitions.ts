/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {searchByTerm} from './utils';

export async function changeEventDisplayName({
	eventName,
	newEventName,
	page,
}: {
	eventName: string;
	newEventName: string;
	page: Page;
}) {
	await searchByTerm({
		page,
		searchTerm: eventName,
	});
	await page.waitForSelector(`text=${eventName}`);

	await page.getByRole('link', {name: eventName}).click();
	await page.getByRole('button', {name: 'Edit'}).click();
	await page.getByLabel('Display Name').click();
	await page.getByLabel('Display Name').fill(newEventName);
	await page.getByRole('button', {name: 'Save'}).click();

	await page.waitForSelector('div.alert-success', {state: 'visible'});
}
