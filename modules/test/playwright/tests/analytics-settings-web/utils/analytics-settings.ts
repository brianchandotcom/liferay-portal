/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect} from '@playwright/test';

import {ApiHelpers} from '../../../helpers/ApiHelpers';
import {liferayConfig} from '../../../liferay.config';
import {createChannel} from '../../osb-faro-web/utils/channel';
import {createDataSource} from '../../osb-faro-web/utils/data-source';
import {acceptsCookiesBanner} from '../../osb-faro-web/utils/portal';

export const PROPERTY_COMMERCE_CHANNEL_COLUMN_INDEX = 1;
export const PROPERTY_SITE_COLUMN_INDEX = 2;

async function switchToTab({page, tabName}: {page: Page; tabName: string}) {
	const sitesTab = await page.getByRole('tab', {name: tabName});

	await sitesTab.click();

	await page.waitForTimeout(3000);
}

export async function connectToAnalyticsCloud(page: Page) {
	await page.getByPlaceholder('Paste token here.').click();

	await page.keyboard.press('Control+V');

	await page.getByRole('button', {name: 'Connect'}).click();
}

export async function connectToAnalyticsCloudWithNoSiteSynced(page: Page) {
	await createDataSource(page);

	await goToAnalyticsCloudInstanceSettings(page);

	await acceptsCookiesBanner(page);

	await disconnectFromAnalyticsCloud(page);

	await connectToAnalyticsCloud(page);

	await goNextStep(page);

	await goNextStep(page);

	await page.getByRole('button', {name: 'Finish'}).click();
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

export async function enableCommerceChannel({
	channelName,
	page,
}: {
	channelName: string;
	page: Page;
}) {
	const channel = await findChannel({channelName, page});

	const commerceChannelSwitchButton = channel.locator('.toggle-switch-check');

	await commerceChannelSwitchButton.click();
}

export async function expectPropertyColumn({
	channelName,
	expectedValue,
	index,
	page,
}: {
	channelName: string;
	expectedValue: string;
	index: number;
	page: Page;
}) {
	const channel = await findChannel({channelName, page});

	const cellContents = await channel.locator('td').allTextContents();

	expect(cellContents[index]).toBe(expectedValue);
}

export async function findChannel({
	channelName,
	page,
}: {
	channelName: string;
	page: Page;
}): Promise<any> {
	await page.waitForSelector('[data-testid="properties"]');

	await page.getByPlaceholder('Search').first().fill(channelName);

	await page.getByRole('button', {name: 'Search'}).first().click();

	await expect(page.getByRole('cell', {name: channelName})).toBeVisible({
		timeout: 100 * 1000,
	});

	return await page.locator('table.table tbody tr:first-child');
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

export async function goToSettingsStep({
	page,
	stepName,
}: {
	page: Page;
	stepName: string;
}) {
	await goToAnalyticsCloudInstanceSettings(page);

	const menuBar = await page.locator('.menubar');

	await menuBar.getByText(stepName).click();
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
}

export async function syncAnalyticsCloud({
	apiHelpers,
	channelName,
	commerceChannelName,
	organizationName,
	page,
	siteName,
	userGroupName,
}: {
	apiHelpers: ApiHelpers;
	channelName: string;
	commerceChannelName?: string;
	organizationName?: string;
	page: Page;
	siteName?: string;
	userGroupName?: string;
}): Promise<{
	channel: any;
	project: any;
}> {
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

	if (commerceChannelName) {
		await enableCommerceChannel({channelName, page});

		await syncCommerce({channelName, commerceChannelName, page});
	}

	await goNextStep(page);

	if (userGroupName || organizationName) {
		await syncContactsData({
			organizationName,
			page,
			userGroupName,
		});
	}
	else {
		await syncAllContacts(page);
	}

	await goNextStep(page);

	await goNextStep(page);

	await page.getByRole('button', {name: 'Finish'}).click();

	return {
		channel,
		project,
	};
}

export async function syncContactsData({
	organizationName,
	page,
	userGroupName,
}: {
	organizationName?: string;
	page: Page;
	userGroupName?: string;
}) {
	const selectContactsCollapsed = page
		.locator('[aria-expanded="false"]')
		.getByText('Select Contacts');

	if (await selectContactsCollapsed.isVisible()) {
		await page.getByRole('button', {name: 'Select Contacts'}).click();
	}

	if (userGroupName) {
		await page.getByText('User Groups').click();

		await page
			.locator(`[data-testid="${userGroupName}"]`)
			.locator('[type="checkbox"]')
			.check();

		await page.getByRole('button', {name: 'Add'}).click();
	}
	else if (organizationName) {
		await page.getByText('Organizations', {exact: true}).click();

		await page
			.locator(`[data-testid="${organizationName}"]`)
			.locator('[type="checkbox"]')
			.check();

		await page.getByRole('button', {name: 'Add'}).click();
	}
}

export async function syncCommerce({
	channelName,
	commerceChannelName,
	page,
}: {
	channelName: string;
	commerceChannelName: string;
	page: Page;
}) {
	const channel = await findChannel({channelName, page});

	const assignButton = await channel.locator('button');

	await assignButton.click();

	await switchToTab({page, tabName: 'Channel'});

	await page
		.locator('.active')
		.getByPlaceholder('Search')
		.fill(commerceChannelName);

	await page.locator('.active').getByRole('button', {name: 'Search'}).click();

	await expect(page.locator('span[data-testid="loading"]')).toBeHidden();

	const channelTable = await page.locator('[data-testid="channel"]');

	expect(channelTable).toBeVisible();

	const checkbox = channelTable.locator(
		'tbody tr:first-child input[type="checkbox"]'
	);

	await checkbox.check();

	const submitButton = await page.$(
		'.modal .modal-item-last button.btn-primary'
	);

	await submitButton.click();

	await expect(
		page.getByText('Success:Properties settings have been saved.')
	).toBeVisible();
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
	const channel = await findChannel({channelName, page});

	const assignButton = await channel.locator('button');

	await assignButton.click();

	await switchToTab({page, tabName: 'Sites'});

	await page.locator('.active').getByPlaceholder('Search').fill(siteName);

	await page.locator('.active').getByRole('button', {name: 'Search'}).click();

	await expect(page.locator('span[data-testid="loading"]')).toBeHidden();

	const sitesTable = await page.locator('[data-testid="sites"]');

	expect(sitesTable).toBeVisible();

	const checkbox = sitesTable.locator(
		'tbody tr:first-child input[type="checkbox"]'
	);

	await checkbox.check();

	const submitButton = await page.$(
		'.modal .modal-item-last button.btn-primary'
	);

	await submitButton.click();

	await expect(
		page.getByText('Success:Properties settings have been saved.')
	).toBeVisible();
}

export async function goNextStep(page) {
	await page.getByRole('button', {exact: true, name: 'Next'}).click();
}
