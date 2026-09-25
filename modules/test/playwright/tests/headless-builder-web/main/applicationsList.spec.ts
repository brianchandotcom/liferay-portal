/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../fixtures/loginTest';
import {applyFDSSelectionFilter} from '../../../utils/applyFDSSelectionFilter';
import getRandomString from '../../../utils/getRandomString';
import {headlessBuilderPagesTest} from './fixtures/headlessBuilderPagesTest';

const test = mergeTests(
	dataApiHelpersTest,
	headlessBuilderPagesTest({}),
	loginTest()
);

test(
	'can list API applications and filter them by excluding a status',
	{tag: '@LPD-106934'},
	async ({apiHelpers, headlessBuilderPage, page}) => {

		// Add applications

		const applications = [];

		for (const applicationStatus of [
			'published',
			'unpublished',
			'unpublished',
		]) {
			const application = await apiHelpers.objectEntry.postObjectEntry(
				{
					applicationStatus,
					baseURL: `test-${getRandomString()}`,
					title: getRandomString(),
				},
				'headless-builder/applications'
			);

			apiHelpers.data.push({id: application.id, type: 'apiApplication'});

			applications.push(application);
		}

		// Check that the applications are listed with their status

		await headlessBuilderPage.goto();

		const [publishedApplication, ...unpublishedApplications] = applications;

		await expect(
			page.locator('.fds tbody tr', {
				hasText: publishedApplication.title,
			})
		).toContainText('Published');

		for (const unpublishedApplication of unpublishedApplications) {
			await expect(
				page.locator('.fds tbody tr', {
					hasText: unpublishedApplication.title,
				})
			).toContainText('Unpublished');
		}

		// Exclude the published status

		await applyFDSSelectionFilter(page, {
			exclude: true,
			filter: 'Status',
			multiple: false,
			value: 'Published',
		});

		// Check that only the unpublished applications are listed

		for (const unpublishedApplication of unpublishedApplications) {
			await expect(
				page.locator('.fds tbody tr', {
					hasText: unpublishedApplication.title,
				})
			).toContainText('Unpublished');
		}

		await expect(
			page.locator('.fds tbody tr', {
				hasText: publishedApplication.title,
			})
		).toHaveCount(0);
	}
);
