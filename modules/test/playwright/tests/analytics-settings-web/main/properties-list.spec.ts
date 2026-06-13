/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {loginAnalyticsCloudTest} from '../../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';
import {acceptsCookiesBanner} from '../../osb-faro-web/main/utils/portal';
import {getDefaultProject} from '../../osb-faro-web/main/utils/project';
import {
	connectToAnalyticsCloud,
	disconnectFromAnalyticsCloud,
	goToAnalyticsCloudInstanceSettings,
} from './utils/analytics-settings';

const test = mergeTests(apiHelpersTest, loginAnalyticsCloudTest(), loginTest());

// Disconnect any leftover connection so every test starts from a clean wizard

test.beforeEach(async ({page}) => {
	await goToAnalyticsCloudInstanceSettings(page);

	await acceptsCookiesBanner(page);

	await disconnectFromAnalyticsCloud(page);
});

test(
	'Deleted AC properties do not appear in the DXP Properties wizard step',
	{tag: ['@LRAC-11055', '@LRAC-12572']},
	async ({apiHelpers, page}) => {
		const project = await getDefaultProject(apiHelpers);

		const keptName = 'KeptProperty ' + getRandomString();
		const deletedName = 'DeletedProperty ' + getRandomString();

		const keptChannel =
			await apiHelpers.jsonWebServicesOSBFaro.createChannel(
				keptName,
				project.groupId
			);

		const deletedChannel =
			await apiHelpers.jsonWebServicesOSBFaro.createChannel(
				deletedName,
				project.groupId
			);

		await apiHelpers.jsonWebServicesOSBFaro.deleteChannel(
			`[${deletedChannel.id}]`,
			project.groupId
		);

		const connectionToken =
			await apiHelpers.jsonWebServicesOSBFaro.fetchDataSourceConnectionToken(
				project.groupId
			);

		try {
			await connectToAnalyticsCloud(page, {token: connectionToken});

			const searchBar = page.locator('.management-bar').filter({
				has: page.locator(
					'input[placeholder="Search"]:not([disabled])'
				),
			});

			// Search for the kept property and assert it appears in the list

			await searchBar.getByPlaceholder('Search').fill(keptName);

			await searchBar.getByRole('button', {name: 'Search'}).click();

			await expect(
				page.getByRole('cell', {name: keptName})
			).toBeVisible();

			// Search for the deleted property and assert it is not in the list

			await searchBar.getByPlaceholder('Search').fill(deletedName);

			await searchBar.getByRole('button', {name: 'Search'}).click();

			await expect(
				page.getByRole('cell', {name: deletedName})
			).toHaveCount(0);
		}
		finally {
			await apiHelpers.analyticsSettingsRest.deleteDataSource();

			await apiHelpers.jsonWebServicesOSBFaro.deleteChannel(
				`[${keptChannel.id}]`,
				project.groupId
			);
		}
	}
);

test(
	'AC property search in the DXP Properties wizard step matches by name and handles the empty state',
	{tag: '@LRAC-12585'},
	async ({apiHelpers, page}) => {
		const project = await getDefaultProject(apiHelpers);

		const foundName = 'AC Search Property ' + getRandomString();
		const otherName1 = 'Other Property ' + getRandomString();
		const otherName2 = 'Other Property ' + getRandomString();

		const channels = await Promise.all(
			[foundName, otherName1, otherName2].map((name) =>
				apiHelpers.jsonWebServicesOSBFaro.createChannel(
					name,
					project.groupId
				)
			)
		);

		const connectionToken =
			await apiHelpers.jsonWebServicesOSBFaro.fetchDataSourceConnectionToken(
				project.groupId
			);

		try {
			await connectToAnalyticsCloud(page, {token: connectionToken});

			const searchBar = page.locator('.management-bar').filter({
				has: page.locator(
					'input[placeholder="Search"]:not([disabled])'
				),
			});

			// A matching property appears alone

			await searchBar.getByPlaceholder('Search').fill(foundName);

			await searchBar.getByRole('button', {name: 'Search'}).click();

			await expect(
				page.getByRole('cell', {name: foundName})
			).toBeVisible();

			await expect(
				page.getByRole('cell', {name: otherName1})
			).toHaveCount(0);

			await expect(
				page.getByRole('cell', {name: otherName2})
			).toHaveCount(0);

			// A non-existent term shows the empty state

			await searchBar
				.getByPlaceholder('Search')
				.fill('NoSuchProperty' + getRandomString());

			await searchBar.getByRole('button', {name: 'Search'}).click();

			await expect(
				page.getByText('No properties were found.')
			).toBeVisible();
		}
		finally {
			await apiHelpers.analyticsSettingsRest.deleteDataSource();

			for (const channel of channels) {
				await apiHelpers.jsonWebServicesOSBFaro.deleteChannel(
					`[${channel.id}]`,
					project.groupId
				);
			}
		}
	}
);

test(
	'AC properties can be sorted by create date in the DXP Properties wizard step',
	{tag: '@LRAC-12586'},
	async ({apiHelpers, page}) => {
		const project = await getDefaultProject(apiHelpers);

		const token = getRandomString();

		// Create the properties out of alphabetical order so a create-date sort
		// is distinguishable from a name sort

		const names = [
			`Zulu Sort ${token}`,
			`Alfa Sort ${token}`,
			`Mike Sort ${token}`,
		];

		const channels = [];

		for (const name of names) {
			channels.push(
				await apiHelpers.jsonWebServicesOSBFaro.createChannel(
					name,
					project.groupId
				)
			);
		}

		const connectionToken =
			await apiHelpers.jsonWebServicesOSBFaro.fetchDataSourceConnectionToken(
				project.groupId
			);

		try {
			await connectToAnalyticsCloud(page, {token: connectionToken});

			const searchBar = page.locator('.management-bar').filter({
				has: page.locator(
					'input[placeholder="Search"]:not([disabled])'
				),
			});

			await searchBar.getByPlaceholder('Search').fill(`Sort ${token}`);

			await searchBar.getByRole('button', {name: 'Search'}).click();

			// The list defaults to create date descending (newest first)

			await expect(page.getByRole('cell', {name: token})).toHaveText([
				`Mike Sort ${token}`,
				`Alfa Sort ${token}`,
				`Zulu Sort ${token}`,
			]);

			// Reverse to ascending (oldest first matches creation order)

			await page.getByRole('button', {name: 'sort'}).click();

			await expect(page.getByRole('cell', {name: token})).toHaveText([
				`Zulu Sort ${token}`,
				`Alfa Sort ${token}`,
				`Mike Sort ${token}`,
			]);
		}
		finally {
			await apiHelpers.analyticsSettingsRest.deleteDataSource();

			for (const channel of channels) {
				await apiHelpers.jsonWebServicesOSBFaro.deleteChannel(
					`[${channel.id}]`,
					project.groupId
				);
			}
		}
	}
);
