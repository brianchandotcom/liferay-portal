/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect} from '@playwright/test';

import getRandomString from '../../../../utils/getRandomString';
import {faroConfig} from '../faro.config';

export async function acceptsCookiesBanner(page: Page) {
	const cookiesBannerButton = page.getByRole('button', {name: 'Accept All'});

	if (await cookiesBannerButton.isVisible()) {
		await cookiesBannerButton.click();
	}
}

export async function createProject({page}: {page: Page}) {
	await page.goto(faroConfig.environment.baseUrl);

	await page.getByRole('link', {name: 'Start Free Trial'}).click();

	const workspaceName = 'Workspace ' + getRandomString();

	await page.getByLabel('Workspace Name').fill(workspaceName);

	await page.getByRole('textbox').nth(4).fill('test@liferay.com');

	await page.getByLabel('I Agree').check();

	await page.getByRole('button', {name: 'Finish Setup'}).click();

	await expect(page.getByText('Success:Success')).toBeVisible();

	await page.waitForTimeout(1000);

	expect(page.getByText('Welcome to Analytics Cloud')).toBeVisible();

	expect(
		page.getByText('Just a few more steps to set up your workspace.')
	).toBeVisible();
}
