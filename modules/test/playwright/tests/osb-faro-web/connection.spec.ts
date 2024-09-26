/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginAnalyticsCloudTest} from '../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../fixtures/loginTest';
import {createChannel} from './utils/channel';
import {ACPage, navigateToACSettingsViaURL} from './utils/navigation';
import {
	selectPaginationItemsPerPage,
	selectPaginationPageNumber,
	viewNameNotPresentOnTableList,
	viewNameOnTableList,
	viewPaginationResults,
} from './utils/utils';

export const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	loginAnalyticsCloudTest(),
	loginTest()
);

test(
	'Can paginate the list of properties.',

	{
		tag: '@LRAC-12019',
	},

	async ({apiHelpers, page}) => {
		const propertiesList = [
			'AC Property Pagination 1',
			'AC Property Pagination 2',
			'AC Property Pagination 3',
			'AC Property Pagination 4',
			'AC Property Pagination 5',
			'AC Property Pagination 6',
			'AC Property Pagination 7',
			'AC Property Pagination 8',
		];

		for (const channelName of propertiesList) {
			await createChannel({
				apiHelpers,
				channelName,
			});
		}

		const projects = await apiHelpers.jsonWebServicesOSBFaro.getProjects();

		const project = projects.find(({name}) => name === 'FARO-DEV-liferay');

		await test.step('go to AC Properties Page', async () => {
			await navigateToACSettingsViaURL({
				acPage: ACPage.propertiesPage,
				page,
				projectID: project.groupId,
			});
		});

		await test.step('Check that 4 properties appear on the first page', async () => {
			await selectPaginationItemsPerPage({
				itemsPerPage: '4',
				page,
			});
		});

		await test.step('Check that 4 properties appear on the first page', async () => {
			const firstPropertiesList = [
				'AC Property Pagination 8',
				'AC Property Pagination 7',
				'AC Property Pagination 6',
				'AC Property Pagination 5',
			];

			await viewNameOnTableList({
				itemNames: firstPropertiesList,
				page,
			});

			const secondPropertiesList = [
				'AC Property Pagination 4',
				'AC Property Pagination 3',
				'AC Property Pagination 2',
				'AC Property Pagination 1',
			];

			await viewNameNotPresentOnTableList({
				itemNames: secondPropertiesList,
				page,
			});

			await viewPaginationResults({
				page,
				paginationResults: 'Showing 1 to 4 of',
			});

			await selectPaginationPageNumber({
				page,
				paginationPageNumber: '2',
			});

			await viewNameOnTableList({
				itemNames: secondPropertiesList,
				page,
			});

			await viewNameNotPresentOnTableList({
				itemNames: firstPropertiesList,
				page,
			});
		});

		await test.step('Check that 8 properties appear on the first page', async () => {
			await selectPaginationItemsPerPage({
				itemsPerPage: '8',
				page,
			});
		});

		await test.step('Check that 4 properties appear on the first page', async () => {
			await viewNameOnTableList({
				itemNames: propertiesList,
				page,
			});

			await viewPaginationResults({
				page,
				paginationResults: 'Showing 1 to 8 of',
			});
		});
	}
);
