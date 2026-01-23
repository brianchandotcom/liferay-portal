/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {ApiHelpers} from '../../../helpers/ApiHelpers';
import getRandomString from '../../../utils/getRandomString';
import getBasicWebContentStructureId from '../../../utils/structured-content/getBasicWebContentStructureId';
import {waitForAlert} from '../../../utils/waitForAlert';
import {journalPagesTest} from '../../journal-web/main/fixtures/journalPagesTest';
import {FilterBy} from '../../journal-web/main/pages/JournalPage';

export const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	journalPagesTest,
	loginTest()
);

async function createWebContents(
	apiHelpers: ApiHelpers,
	count: number,
	groupId: string
) {
	const titles = [];

	for (let i = 0; i < count; i++) {
		titles.push(getRandomString());
	}

	await test.step('Create 6 web content articles', async () => {
		const structuredContentId =
			await getBasicWebContentStructureId(apiHelpers);

		for (const title of titles) {
			await apiHelpers.jsonWebServicesJournal.addWebContent({
				ddmStructureId: structuredContentId,
				descriptionMap: {en_US: getRandomString()},
				groupId,
				titleMap: {en_US: title},
			});
		}
	});

	return titles;
}

test(
	'Confirm that changing filters does not deselect selected items',
	{tag: '@LPS-172764'},
	async ({apiHelpers, journalPage, page, site}) => {
		await createWebContents(apiHelpers, 6, site.id);
		await journalPage.goto(site.friendlyUrlPath);

		await test.step('Filter by recent', async () => {
			await journalPage.setFilterBy(FilterBy.RECENT);

			await expect(
				page.getByText('Results Found With Filters')
			).toBeVisible();
		});

		await test.step('Select one article in page 1', async () => {
			await journalPage.selectItem(0);

			await expect(page.getByText('1 of 6 Items Selected')).toBeVisible();
		});

		await test.step('Select another article in page 2', async () => {
			await journalPage.selectPage(1);

			await expect(
				page.getByText('Showing 5 to 6 of 6 entries.')
			).toBeVisible();
			await journalPage.selectItem(0);

			await expect(page.getByText('2 of 6 Items Selected')).toBeVisible();
		});

		await test.step('Clear the filters', async () => {
			await journalPage.clearFilters();
		});

		await test.step('Check that selections are kept', async () => {
			await expect(page.getByText('2 of 6 Items Selected')).toBeVisible();
		});
	}
);

test(
	'Confirm that selection is lost when you leave the page',
	{tag: '@LPS-172764'},
	async ({apiHelpers, journalPage, page, site}) => {
		await createWebContents(apiHelpers, 6, site.id);
		await journalPage.goto(site.friendlyUrlPath);

		await test.step('Filter by recent', async () => {
			await journalPage.setFilterBy(FilterBy.RECENT);
		});

		await test.step('Select one article per page', async () => {
			await journalPage.selectItem(0);
			await journalPage.selectPage(1);
			await journalPage.selectItem(0);
			await expect(page.getByText('2 of 6 Items Selected')).toBeVisible();
		});

		await test.step('Exit page and get back', async () => {
			await page.goto('/');
			await journalPage.goto(site.friendlyUrlPath);
		});

		await test.step('Check that selections are cleared', async () => {
			await expect(
				page.getByText('0 of 6 Items Selected')
			).toBeAttached();
		});
	}
);

test(
	'Confirm that two selected items in different pages can be deleted',
	{tag: '@LPS-172764'},
	async ({apiHelpers, journalPage, page, site}) => {
		await createWebContents(apiHelpers, 6, site.id);
		await journalPage.goto(site.friendlyUrlPath);

		await test.step('Select one article per page', async () => {
			await journalPage.selectItem(0);
			await journalPage.selectPage(1);
			await journalPage.selectItem(0);
			await expect(page.getByText('2 of 6 Items Selected')).toBeVisible();
		});

		await journalPage.deleteSelection();

		await waitForAlert(
			page,
			'Success: 2 items were moved to the Recycle Bin.'
		);
	}
);

test(
	'Confirm that selection is displayed at the top of all pages',
	{tag: '@LPS-172764'},
	async ({apiHelpers, journalPage, page, site}) => {
		await createWebContents(apiHelpers, 6, site.id);
		await journalPage.goto(site.friendlyUrlPath);

		await test.step('Select one article per page', async () => {
			await journalPage.selectItem(0);
			await journalPage.selectPage(1);
			await journalPage.selectItem(0);
			await expect(page.getByText('2 of 6 Items Selected')).toBeVisible();
		});

		await journalPage.selectPage(0);
		await expect(page.getByText('2 of 6 Items Selected')).toBeVisible();

		await journalPage.selectPage(1);
		await expect(page.getByText('2 of 6 Items Selected')).toBeVisible();
	}
);
