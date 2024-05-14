/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect} from '@playwright/test';

import {liferayConfig} from '../../../liferay.config';
import {createDataSource} from '../../osb-faro-web/utils/dataSource';

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

	await page.getByRole('button', {exact: true, name: 'Next'}).click();
}

export async function syncAnalyticsCloud(page) {
	await createDataSource(page);

	await goToAnalyticsCloudInstanceSettings(page);

	await acceptsCookiesBanner(page);

	await disconnectFromAnalyticsCloud(page);

	await connectToAnalyticsCloud(page);

	await syncSite(page);

	await syncAllContacts(page);

	await page.getByRole('button', {name: 'Finish'}).click();
}

export async function syncSite(page) {
	await expect(
		page.getByRole('heading', {name: 'Property Assignment'})
	).toBeVisible({
		timeout: 100 * 1000,
	});

	const wizard = page.getByTestId('VIEW_WIZARD_MODE');

	await expect(wizard.getByText('Available Properties')).toBeVisible({
		timeout: 100 * 1000,
	});

	const assignButton = await page.$(
		'table.table tbody tr:first-child button'
	);

	await assignButton.click();

	await page.getByRole('tab', {name: 'Sites'}).click();

	await page.waitForTimeout(3000);

	const checkbox = await page.$(
		'.modal table.table tbody tr:first-child input[type="checkbox"]'
	);

	await checkbox.check();

	const submitButton = await page.$(
		'.modal .modal-item-last button.btn-primary'
	);

	await submitButton.click();

	await expect(
		page.getByText('Success:Properties settings have been saved.')
	).toBeVisible();

	await page.getByRole('button', {exact: true, name: 'Next'}).click();
}
