/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect} from '@playwright/test';

import {liferayConfig} from '../../../liferay.config';

export async function acceptsCookiesBanner(page) {
	const cookiesBannerButton = page.getByRole('button', {name: 'Accept All'});

	if (await cookiesBannerButton.isVisible()) {
		await cookiesBannerButton.click();
	}
}

export async function connectToAnalyticsCloud(page) {
	await page.getByTestId('input-token', {name: 'input-token'}).click();

	await page.keyboard.press('Control+V');

	await page.getByRole('button', {name: 'Connect'}).click();
}

export async function disconnectFromAnalyticsCloud(page) {
	const disconnectButton = page.getByRole('button', {name: 'Disconnect'});

	if (await disconnectButton.isVisible()) {
		await disconnectButton.click();

		const diconnectConfirmationModal = page.getByLabel(
			'Disconnecting Data Source'
		);

		const diconnectConfirmationButton =
			diconnectConfirmationModal.getByRole('button', {
				name: 'Disconnect',
			});

		await diconnectConfirmationButton.click();
	}
}

export async function goToAnalyticsCloudInstanceSettings(page) {
	await page.goto(liferayConfig.environment.baseUrl);

	await page.getByLabel('Open Applications MenuCtrl+Alt+A').click();

	await page.getByRole('tab', {name: 'Control Panel'}).click();

	await page.getByRole('menuitem', {name: 'Instance Settings'}).click();

	await page.getByRole('link', {name: 'Analytics Cloud'}).click();

	await expect(page.getByText('Analytics Cloud Token')).toBeVisible({
		timeout: 100 * 1000,
	});
}

export async function syncAllContacts(page) {
	const wizard = page.getByTestId('VIEW_WIZARD_MODE');

	await expect(wizard.getByText('Sync People')).toBeVisible({
		timeout: 100 * 1000,
	});

	const syncContactsButton = page.getByTestId(
		'sync-all-contacts-and-accounts__false'
	);

	if (await syncContactsButton.isVisible()) {
		await syncContactsButton.click();
	}

	await page.getByRole('button', {name: 'Next'}).click();
}

export async function syncSite(page) {
	await expect(
		page.getByRole('heading', {name: 'Property Assignment'})
	).toBeVisible({
		timeout: 100 * 1000,
	});

	// Known issue. See https://liferay.atlassian.net/browse/LRAC-13481

	const tryAgainButton = page.getByRole('button', {name: 'Try Again'});

	if (await tryAgainButton.isVisible()) {
		await page.getByRole('button', {name: 'Previous'}).click();

		await page.getByRole('button', {name: 'Next'}).click();
	}

	const wizard = page.getByTestId('VIEW_WIZARD_MODE');

	await expect(wizard.getByText('Available Properties')).toBeVisible({
		timeout: 100 * 1000,
	});

	await page
		.getByTestId('Liferay DXP')
		.getByRole('button', {name: 'Assign'})
		.click();

	await page.getByRole('tab', {name: 'Sites'}).click();

	await page
		.getByTestId('1')
		.getByTestId('Liferay DXP')
		.getByLabel('')
		.check();

	await page
		.getByLabel('Assign to Liferay DXP')
		.getByRole('button', {name: 'Assign'})
		.click();

	await expect(page.getByLabel('Assign to Liferay DXP')).toBeHidden({
		timeout: 100 * 1000,
	});

	await page.getByRole('button', {name: 'Next'}).click();
}
