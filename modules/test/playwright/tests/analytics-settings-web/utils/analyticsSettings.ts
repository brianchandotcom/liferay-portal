/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect} from '@playwright/test';

import {ApiHelpers} from '../../../helpers/ApiHelpers';
import {liferayConfig} from '../../../liferay.config';
import {createChannel} from '../../osb-faro-web/utils/channel';
import {createDataSource} from '../../osb-faro-web/utils/dataSource';
import {acceptsCookiesBanner} from '../../osb-faro-web/utils/portal';

export async function connectToAnalyticsCloud(page: Page) {
	await page.getByPlaceholder('Paste token here.').click();

	await page.keyboard.press('Control+V');

	await page.getByRole('button', {name: 'Connect'}).click();
}

export async function disconnectFromAnalyticsCloud(page: Page) {
	const disconnectButton = page.getByRole('button', {name: 'Disconnect'});

	if (await disconnectButton.isVisible()) {
		await disconnectButton.click();

		const confirmationModal = page.getByLabel('Disconnecting Data Source');

		const confirmationButton = confirmationModal.getByRole('button', {
			name: 'Disconnect',
		});

		await confirmationButton.click();
	}
}

export async function goToAnalyticsCloudInstanceSettings(page: Page) {
	await page.goto(liferayConfig.environment.baseUrl);

	await page.getByLabel('Open Applications MenuCtrl+Alt+A').click();

	await page.getByRole('tab', {name: 'Control Panel'}).click();

	await page.getByRole('menuitem', {name: 'Instance Settings'}).click();

	await page.getByRole('link', {name: 'Analytics Cloud'}).click();

	await expect(page.getByText('Analytics Cloud Token')).toBeVisible({
		timeout: 100 * 1000,
	});
}

export async function navigateToSitePage({
	page,
	pageName,
	siteName,
}: {
	page: Page;
	pageName: string;
	siteName?: string;
}) {
	const pageNameURL = pageName.replace(/ /g, '-').toLowerCase();

	if (siteName) {
		const siteNameURL = siteName.replace(/ /g, '-').toLowerCase();

		await page.goto(
			`${liferayConfig.environment.baseUrl}/web/${siteNameURL}/` +
				`${pageNameURL}`
		);
	}
	else {
		await page.goto(
			`${liferayConfig.environment.baseUrl}/web/guest/${pageNameURL}`
		);
	}
}

export async function syncAllContacts(page: Page) {
	const wizard = page.locator('[data-testid="VIEW_WIZARD_MODE"]');

	await expect(wizard.getByText('Sync People')).toBeVisible({
		timeout: 100 * 1000,
	});

	const syncContactsButton = page.locator(
		'[data-testid="sync-all-contacts-and-accounts__false"]'
	);

	if (await syncContactsButton.isVisible()) {
		await syncContactsButton.click();
	}

	await page.getByRole('button', {exact: true, name: 'Next'}).click();
}

export async function syncAnalyticsCloud({
	apiHelpers,
	channelName,
	page,
	siteName,
}: {
	apiHelpers: ApiHelpers;
	channelName: string;
	page: Page;
	siteName?: string;
}) {
	const {channel, project} = await createChannel({
		apiHelpers,
		channelName,
	});

	await createDataSource(page);

	await goToAnalyticsCloudInstanceSettings(page);

	await acceptsCookiesBanner(page);

	await disconnectFromAnalyticsCloud(page);

	await connectToAnalyticsCloud(page);

	await syncSite({
		channelName,
		page,
		siteName,
	});

	await syncAllContacts(page);

	await page.getByRole('button', {name: 'Finish'}).click();

	return {
		channel,
		project,
	};
}

export async function syncSite({
	channelName,
	page,
	siteName = 'Liferay DXP',
}: {
	channelName: string;
	page: Page;
	siteName?: string;
}) {
	await expect(
		page.getByRole('heading', {name: 'Property Assignment'})
	).toBeVisible({
		timeout: 100 * 1000,
	});

	const wizard = page.locator('[data-testid="VIEW_WIZARD_MODE"]');

	await expect(wizard.getByText('Available Properties')).toBeVisible({
		timeout: 100 * 1000,
	});

	await page.getByPlaceholder('Search').fill(channelName);

	await page.getByRole('button', {name: 'Search'}).click();

	await expect(page.getByRole('cell', {name: channelName})).toBeVisible({
		timeout: 100 * 1000,
	});

	const assignButton = await page.$(
		'table.table tbody tr:first-child button'
	);

	await assignButton.click();

	await page.getByRole('tab', {name: 'Sites'}).click();

	await page.waitForSelector('div[aria-modal="true"] tbody');

	await page.locator('.active').getByPlaceholder('Search').fill(siteName);

	await page.locator('.active').getByRole('button', {name: 'Search'}).click();

	await expect(page.locator('span[data-testid="loading"]')).toBeHidden();

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
