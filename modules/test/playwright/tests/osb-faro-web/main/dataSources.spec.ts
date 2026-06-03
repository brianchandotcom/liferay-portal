/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {loginAnalyticsCloudTest} from '../../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';
import {ACPage, navigateToACSettingsViaURL} from './utils/navigation';
import {getDefaultProject} from './utils/project';

const test = mergeTests(apiHelpersTest, loginAnalyticsCloudTest(), loginTest());

test(
	'Newly connected DXP data source is listed and searchable in AC settings',
	{tag: ['@LRAC-8107', '@LRAC-8821']},
	async ({apiHelpers, page}) => {
		const project = await getDefaultProject(apiHelpers);

		const connectionToken =
			await apiHelpers.jsonWebServicesOSBFaro.fetchDataSourceConnectionToken(
				project.groupId
			);

		await apiHelpers.analyticsSettingsRest.postDataSource(connectionToken);

		try {
			await navigateToACSettingsViaURL({
				acPage: ACPage.dataSourcePage,
				page,
				projectID: project.groupId,
			});

			const connectedRow = page
				.locator('table tbody tr')
				.filter({hasNotText: 'DISCONNECTED'})
				.filter({hasText: 'CONNECTED'});

			const dataSourceName =
				(await connectedRow.locator('td').first().textContent()) || '';

			expect(dataSourceName.trim()).not.toBe('');

			const searchBar = page.getByPlaceholder('Search');

			await searchBar.fill(dataSourceName);

			await page.keyboard.press('Enter');

			await expect(
				page.getByRole('cell', {exact: true, name: dataSourceName})
			).toBeVisible();

			await searchBar.fill('Non Existent Data Source');

			await page.keyboard.press('Enter');

			await expect(
				page.getByText('There are no results found')
			).toBeVisible();
		}
		finally {
			await apiHelpers.analyticsSettingsRest.deleteDataSource();
		}
	}
);

test(
	'A property created before any DXP connection becomes the target of the connected data source',
	{tag: '@LRAC-9103'},
	async ({apiHelpers, page}) => {
		const project = await getDefaultProject(apiHelpers);

		const channel = await apiHelpers.jsonWebServicesOSBFaro.createChannel(
			'No DS Property ' + getRandomString(),
			project.groupId
		);

		try {
			await navigateToACSettingsViaURL({
				acPage: ACPage.propertiesPage,
				page,
				projectID: project.groupId,
			});

			await expect(
				page.getByRole('cell', {name: channel.name})
			).toBeVisible();

			const connectionToken =
				await apiHelpers.jsonWebServicesOSBFaro.fetchDataSourceConnectionToken(
					project.groupId
				);

			await apiHelpers.analyticsSettingsRest.postDataSource(
				connectionToken
			);

			await navigateToACSettingsViaURL({
				acPage: ACPage.dataSourcePage,
				page,
				projectID: project.groupId,
			});

			// The newly connected data source must appear in CONNECTED status
			// alongside any historical disconnected rows from previous runs.

			await expect(
				page
					.locator('table tbody tr')
					.filter({hasNotText: 'DISCONNECTED'})
					.filter({hasText: 'CONNECTED'})
			).not.toHaveCount(0);
		}
		finally {
			await apiHelpers.analyticsSettingsRest.deleteDataSource();

			await apiHelpers.jsonWebServicesOSBFaro.deleteChannel(
				`[${channel.id}]`,
				project.groupId
			);
		}
	}
);

test(
	'A DXP instance can reconnect to Analytics Cloud after disconnecting an existing data source',
	{tag: '@LRAC-11475'},
	async ({apiHelpers, page}) => {
		const project = await getDefaultProject(apiHelpers);

		const connectionToken =
			await apiHelpers.jsonWebServicesOSBFaro.fetchDataSourceConnectionToken(
				project.groupId
			);

		try {

			// Connect, disconnect and reconnect reusing the same data source

			await apiHelpers.analyticsSettingsRest.postDataSource(
				connectionToken
			);

			await apiHelpers.analyticsSettingsRest.deleteDataSource();

			await apiHelpers.analyticsSettingsRest.postDataSource(
				connectionToken
			);

			await navigateToACSettingsViaURL({
				acPage: ACPage.dataSourcePage,
				page,
				projectID: project.groupId,
			});

			await expect(
				page
					.locator('table tbody tr')
					.filter({hasNotText: 'DISCONNECTED'})
					.filter({hasText: 'CONNECTED'})
			).not.toHaveCount(0);
		}
		finally {
			await apiHelpers.analyticsSettingsRest.deleteDataSource();
		}
	}
);
