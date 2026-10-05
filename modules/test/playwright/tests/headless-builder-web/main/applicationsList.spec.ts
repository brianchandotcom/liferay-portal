/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../fixtures/loginTest';
import {applyFDSSelectionFilter} from '../../../utils/applyFDSSelectionFilter';
import getRandomString from '../../../utils/getRandomString';
import {setItemsPerPage} from '../../../utils/pagination';
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

		const failedResponses: string[] = [];

		page.on('response', (response) => {
			if (response.status() >= 400 && response.url().includes('/o/')) {
				failedResponses.push(`${response.status()} ${response.url()}`);
			}
		});

		await headlessBuilderPage.goto();

		await setItemsPerPage(page, 60);

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

		await applyFDSSelectionFilter(page, {
			exclude: true,
			filter: 'Status',
			multiple: false,
			value: 'Published',
		});

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

		expect(failedResponses).toEqual([]);
	}
);
