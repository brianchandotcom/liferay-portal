/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {loginAnalyticsCloudTest} from '../../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';
import {getDefaultProject} from '../../osb-faro-web/main/utils/project';
import {goToSettingsStep} from './utils/analytics-settings';

const test = mergeTests(apiHelpersTest, loginAnalyticsCloudTest(), loginTest());

test(
	'Deleted AC properties do not appear in the DXP Properties wizard step',
	{tag: '@LRAC-11055'},
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

		await apiHelpers.analyticsSettingsRest.postDataSource(connectionToken);

		try {
			await goToSettingsStep({page, stepName: 'Properties'});

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
