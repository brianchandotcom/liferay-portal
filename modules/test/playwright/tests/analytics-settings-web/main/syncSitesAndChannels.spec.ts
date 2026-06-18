/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedChannelTest} from '../../../fixtures/isolatedChannelTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginAnalyticsCloudTest} from '../../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../../fixtures/loginTest';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../../utils/getRandomString';
import {
	PROPERTY_COMMERCE_CHANNEL_COLUMN_INDEX,
	PROPERTY_SITE_COLUMN_INDEX,
	enableCommerceChannel,
	expectPropertyColumn,
	findChannel,
	goToSettingsStep,
	syncAnalyticsCloud,
	syncCommerce,
	toggleSiteSync,
} from './utils/analytics-settings';

export const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPD-20640': {enabled: true},
		'LPS-178052': {enabled: true},
	}),
	isolatedChannelTest,
	isolatedSiteTest,
	loginAnalyticsCloudTest(),
	loginTest()
);

// Opens the "Assign Sites and Channels" modal for the given property

async function openAssignModal(page: Page, propertyName: string) {
	const propertyRow = await findChannel({channelName: propertyName, page});

	await clickAndExpectToBeVisible({
		target: page.getByRole('dialog'),
		trigger: propertyRow.locator("[role='assign-button']"),
	});
}

// Searches the active tab of the assign modal for the given term

async function searchAssignModal(page: Page, searchTerm: string) {
	const activePane = page.locator('.modal .tab-pane.active');

	// The tab list loads asynchronously; wait for it before searching

	await expect(activePane.locator('.pagination-results')).toBeVisible();

	const searchField = activePane.getByPlaceholder('Search');

	await searchField.fill(searchTerm);

	await searchField.press('Enter');
}

test(
	'Channels and sites can be searched in the assign property modal',
	{
		tag: '@LRAC-12579',
	},
	async ({analyticsChannel: channel, apiHelpers, page, project, site}) => {

		// Single-token names so the modal search (which tokenizes on spaces) matches exactly one

		const foundChannel =
			await apiHelpers.headlessCommerceAdminChannel.postChannel({
				name: 'found' + getRandomString(),
				siteGroupId: site.id,
			});

		const otherChannel =
			await apiHelpers.headlessCommerceAdminChannel.postChannel({
				name: 'other' + getRandomString(),
				siteGroupId: site.id,
			});

		try {
			await syncAnalyticsCloud({
				apiHelpers,
				channel,
				page,
				project,
				siteName: site.name,
			});

			await goToSettingsStep({page, stepName: 'Properties'});

			await enableCommerceChannel({channelName: channel.name, page});

			await openAssignModal(page, channel.name);

			const resultsMessage = page.locator(
				'.modal .tab-pane.active .pagination-results'
			);

			// Search the channel list

			await page.getByRole('tab', {name: 'Channel'}).click();

			await searchAssignModal(page, foundChannel.name);

			await expect(resultsMessage).toHaveText(
				'Showing 1 to 1 of 1 entries.'
			);

			await expect(
				page.locator(
					`.modal .tab-pane.active tr[data-testid="${foundChannel.name}"]`
				)
			).toBeVisible();

			await expect(
				page.locator(
					`.modal .tab-pane.active tr[data-testid="${otherChannel.name}"]`
				)
			).toHaveCount(0);

			// A search with no match shows the empty message

			await searchAssignModal(page, 'ZZ' + getRandomString());

			await expect(
				page.getByText('No channels were found.')
			).toBeVisible();

			// Search the site list

			await page.getByRole('tab', {name: 'Sites'}).click();

			await searchAssignModal(page, site.name);

			await expect(resultsMessage).toHaveText(
				'Showing 1 to 1 of 1 entries.'
			);

			await expect(
				page.locator(
					`.modal .tab-pane.active tr[data-testid="${site.name}"]`
				)
			).toBeVisible();
		}
		finally {
			for (const commerceChannel of [foundChannel, otherChannel]) {
				await apiHelpers.headlessCommerceAdminChannel
					.deleteChannel(commerceChannel.id)
					.catch(() => {});
			}
		}
	}
);

test(
	'A property with commerce disabled shows a dash for channels and counts its sites',
	{
		tag: '@LRAC-12575',
	},
	async ({analyticsChannel: channel, apiHelpers, page, project, site}) => {
		await syncAnalyticsCloud({
			apiHelpers,
			channel,
			page,
			project,
			siteName: site.name,
		});

		await goToSettingsStep({page, stepName: 'Properties'});

		// With the commerce toggle disabled the channels column shows a dash

		await expectPropertyColumn({
			channelName: channel.name,
			expectedValue: '-',
			index: PROPERTY_COMMERCE_CHANNEL_COLUMN_INDEX,
			page,
		});

		// The synced site is counted

		await expectPropertyColumn({
			channelName: channel.name,
			expectedValue: '1',
			index: PROPERTY_SITE_COLUMN_INDEX,
			page,
		});
	}
);

test(
	'Assigned channels cannot be edited once the property commerce toggle is disabled',
	{
		tag: '@LRAC-12578',
	},
	async ({analyticsChannel: channel, apiHelpers, page, project, site}) => {
		const commerceChannel =
			await apiHelpers.headlessCommerceAdminChannel.postChannel({
				name: 'commerce' + getRandomString(),
				siteGroupId: site.id,
			});

		try {
			await syncAnalyticsCloud({
				apiHelpers,
				channel,
				page,
				project,
				siteName: site.name,
			});

			await goToSettingsStep({page, stepName: 'Properties'});

			// Enable commerce and assign a channel to the property

			await enableCommerceChannel({channelName: channel.name, page});

			await syncCommerce({
				channelName: channel.name,
				commerceChannelName: commerceChannel.name,
				page,
			});

			await expectPropertyColumn({
				channelName: channel.name,
				expectedValue: '1',
				index: PROPERTY_COMMERCE_CHANNEL_COLUMN_INDEX,
				page,
			});

			// Disable the commerce toggle

			const propertyRow = await findChannel({
				channelName: channel.name,
				page,
			});

			await propertyRow.locator('.toggle-switch-check').click();

			// The channel list can no longer be edited

			await openAssignModal(page, channel.name);

			await page.getByRole('tab', {name: 'Channel'}).click();

			const channelPane = page.locator('.modal .tab-pane.active');

			await expect(
				channelPane.locator('.pagination-results')
			).toBeVisible();

			// The select-all control and every row checkbox are disabled

			await expect(
				channelPane.locator('[data-testid="globalCheckbox"]')
			).toBeDisabled();

			await expect(
				channelPane.locator(
					'tbody input[type="checkbox"]:not([disabled])'
				)
			).toHaveCount(0);

			await expect(
				channelPane.locator('tbody input[type="checkbox"]').first()
			).toBeVisible();
		}
		finally {
			await apiHelpers.headlessCommerceAdminChannel
				.deleteChannel(commerceChannel.id)
				.catch(() => {});
		}
	}
);

test(
	'A site and a commerce channel assigned to one property are unavailable to another property',
	{
		tag: '@LRAC-12577',
	},
	async ({analyticsChannel: channel, apiHelpers, page, project, site}) => {
		const propertyB = await apiHelpers.jsonWebServicesOSBFaro.createChannel(
			'propertyB' + getRandomString(),
			project.groupId
		);

		const commerceChannel =
			await apiHelpers.headlessCommerceAdminChannel.postChannel({
				name: 'commerce' + getRandomString(),
				siteGroupId: site.id,
			});

		try {
			await syncAnalyticsCloud({
				apiHelpers,
				channel,
				page,
				project,
				siteName: site.name,
			});

			await goToSettingsStep({page, stepName: 'Properties'});

			// Confirm the second property (an AC channel) surfaces as an
			// assignable DXP property before relying on it

			await page
				.getByRole('textbox', {name: 'Search'})
				.first()
				.fill(propertyB.name);

			await page.getByRole('button', {name: 'Search'}).first().click();

			await expect(
				page.getByRole('cell', {name: propertyB.name})
			).toBeVisible();

			// Assign the commerce channel to the first property (the site is
			// already synced to it by syncAnalyticsCloud)

			await enableCommerceChannel({channelName: channel.name, page});

			await syncCommerce({
				channelName: channel.name,
				commerceChannelName: commerceChannel.name,
				page,
			});

			// Open the second property's assign modal with commerce enabled

			await enableCommerceChannel({channelName: propertyB.name, page});

			await openAssignModal(page, propertyB.name);

			// The channel already used by the first property cannot be selected

			await page.getByRole('tab', {name: 'Channel'}).click();

			await searchAssignModal(page, commerceChannel.name);

			await expect(
				page.locator(
					`.modal .tab-pane.active tr[data-testid="${commerceChannel.name}"] input[type="checkbox"]`
				)
			).toBeDisabled();

			// The site already used by the first property cannot be selected

			await page.getByRole('tab', {name: 'Sites'}).click();

			await searchAssignModal(page, site.name);

			await expect(
				page.locator(
					`.modal .tab-pane.active tr[data-testid="${site.name}"] input[type="checkbox"]`
				)
			).toBeDisabled();
		}
		finally {
			await apiHelpers.headlessCommerceAdminChannel
				.deleteChannel(commerceChannel.id)
				.catch(() => {});

			await apiHelpers.jsonWebServicesOSBFaro
				.deleteChannel(`[${propertyB.id}]`, project.groupId)
				.catch(() => {});
		}
	}
);

test(
	'Assigned channels and sites are counted on the property after syncing',
	{
		tag: '@LRAC-12574',
	},
	async ({analyticsChannel: channel, apiHelpers, page, project, site}) => {
		const commerceChannel =
			await apiHelpers.headlessCommerceAdminChannel.postChannel({
				name: 'commerce' + getRandomString(),
				siteGroupId: site.id,
			});

		const siteB = await apiHelpers.headlessAdminSite.postSite({
			name: 'siteB' + getRandomString(),
		});

		try {
			await syncAnalyticsCloud({
				apiHelpers,
				channel,
				page,
				project,
				siteName: site.name,
			});

			await goToSettingsStep({page, stepName: 'Properties'});

			// Assign a second site and a commerce channel to the property

			await toggleSiteSync({
				channelName: channel.name,
				page,
				siteName: siteB.name,
			});

			await enableCommerceChannel({channelName: channel.name, page});

			await syncCommerce({
				channelName: channel.name,
				commerceChannelName: commerceChannel.name,
				page,
			});

			// One channel and two sites are counted

			await expectPropertyColumn({
				channelName: channel.name,
				expectedValue: '1',
				index: PROPERTY_COMMERCE_CHANNEL_COLUMN_INDEX,
				page,
			});

			await expectPropertyColumn({
				channelName: channel.name,
				expectedValue: '2',
				index: PROPERTY_SITE_COLUMN_INDEX,
				page,
			});
		}
		finally {
			await apiHelpers.headlessCommerceAdminChannel
				.deleteChannel(commerceChannel.id)
				.catch(() => {});

			await apiHelpers.headlessAdminSite
				.deleteSite(siteB.externalReferenceCode)
				.catch(() => {});
		}
	}
);
