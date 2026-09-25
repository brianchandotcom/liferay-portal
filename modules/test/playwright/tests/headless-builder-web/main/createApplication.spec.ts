/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';
import {headlessBuilderPagesTest} from './fixtures/headlessBuilderPagesTest';

const test = mergeTests(
	dataApiHelpersTest,
	headlessBuilderPagesTest({}),
	loginTest()
);

test(
	'cannot create an API application with an existing base URL or title',
	{tag: '@LPD-106934'},
	async ({apiHelpers, headlessBuilderPage, page}) => {

		// Add an application

		const application = await apiHelpers.objectEntry.postObjectEntry(
			{
				applicationStatus: 'unpublished',
				baseURL: `test-${getRandomString()}`,
				title: getRandomString(),
			},
			'headless-builder/applications'
		);

		apiHelpers.data.push({id: application.id, type: 'apiApplication'});

		// Try to create an application with the existing base URL

		await headlessBuilderPage.goto();
		await headlessBuilderPage.addNewApplicationButton.click();

		await headlessBuilderPage.newApplicationTitleBox.fill(
			getRandomString()
		);
		await headlessBuilderPage.newApplicationURLBox.fill(
			application.baseURL
		);
		await headlessBuilderPage.createApplicationButton.click();

		await expect(
			page.getByText(
				'The Base URL is already in use. Please enter a unique Base URL.'
			)
		).toBeVisible();

		const applicationsWithBaseURL =
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
				'headless-builder/applications',
				new URLSearchParams({
					filter: `baseURL eq '${application.baseURL}'`,
				})
			);

		expect(applicationsWithBaseURL.totalCount).toBe(1);

		// Try to create an application with the existing title

		await headlessBuilderPage.newApplicationTitleBox.fill(
			application.title
		);
		await headlessBuilderPage.newApplicationURLBox.fill(
			`test-${getRandomString()}`
		);
		await headlessBuilderPage.createApplicationButton.click();

		await expect(
			page.getByText(
				'The Title is already in use. Please enter a unique Title.'
			)
		).toBeVisible();

		const applicationsWithTitle =
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
				'headless-builder/applications',
				new URLSearchParams({
					filter: `title eq '${application.title}'`,
				})
			);

		expect(applicationsWithTitle.totalCount).toBe(1);
	}
);

test(
	'can see the API URL host in the new and edit application forms',
	{tag: '@LPD-106934'},
	async ({apiHelpers, headlessBuilderPage, page}) => {

		// Add an application

		const application = await apiHelpers.objectEntry.postObjectEntry(
			{
				applicationStatus: 'unpublished',
				baseURL: `test-${getRandomString()}`,
				title: getRandomString(),
			},
			'headless-builder/applications'
		);

		apiHelpers.data.push({id: application.id, type: 'apiApplication'});

		// Check the URL host in the new application form

		await headlessBuilderPage.goto();
		await headlessBuilderPage.addNewApplicationButton.click();

		const hostURL = `${new URL(page.url()).origin}/o/c/`;

		await expect(page.locator('#hostTextPreview')).toHaveText(hostURL);

		await page.getByRole('button', {exact: true, name: 'Cancel'}).click();

		// Check the URL host in the edit application form

		await headlessBuilderPage.goToEditApplication(application.title);

		await expect(page.locator('#hostTextPreview')).toHaveText(hostURL);
	}
);
