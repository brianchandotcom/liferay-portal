/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect} from '@playwright/test';

import {PORTLET_URLS} from './portletUrls';

type Props = {
	page: Page;
	title: string;
	type: 'Beta' | 'Deprecation' | 'Developer' | 'Release';
};

const URLS: Record<Props['type'], string> = {
	Beta: PORTLET_URLS.systemFeatureFlagBeta,
	Deprecation: PORTLET_URLS.systemFeatureFlagDeprecation,
	Developer: PORTLET_URLS.systemFeatureFlagDeveloper,
	Release: PORTLET_URLS.systemFeatureFlagRelease,
};

export async function enableSystemFeatureFlag({page, title, type}: Props) {
	const url = URLS[type];

	await expect(async () => {
		await page.goto(PORTLET_URLS.systemSettings);

		await page.goto(url);

		await expect(page.getByLabel(title)).toBeVisible({timeout: 3000});
	}).toPass();

	// Return if it's already enabled

	if (await page.getByLabel(title).isChecked()) {
		return;
	}

	// Enable it

	await expect(async () => {
		const promise = page.waitForEvent(
			'request',
			(req) => req.method() === 'POST'
		);

		await page.getByLabel(title).click({timeout: 1000});

		const request = await promise;

		const response = await request.response();

		const responseBody = await response?.json();

		expect(responseBody).toHaveProperty('dependentFeatureFlags');
	}).toPass();
}

export async function disableSystemFeatureFlag({page, title, type}: Props) {
	const url = URLS[type];

	await expect(async () => {
		await page.goto(PORTLET_URLS.systemSettings);

		await page.goto(url);

		await expect(page.getByLabel(title)).toBeVisible({timeout: 3000});
	}).toPass();

	// Disable it only if it's enabled

	if (page.getByLabel(title).isChecked()) {
		await expect(async () => {
			const promise = page.waitForEvent(
				'request',
				(req) => req.method() === 'POST'
			);

			await page.getByLabel(title).click({timeout: 1000});

			const request = await promise;

			const response = await request.response();

			const responseBody = await response?.json();

			expect(responseBody).toHaveProperty('dependentFeatureFlags');
		}).toPass();
	}
}
