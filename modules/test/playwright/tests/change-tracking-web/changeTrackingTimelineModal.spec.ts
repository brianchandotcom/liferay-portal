/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {changeTrackingPagesTest} from '../../fixtures/changeTrackingPagesTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {getRandomInt} from '../../utils/getRandomInt';
import getRandomString from '../../utils/getRandomString';
import {waitForSuccessAlert} from '../../utils/waitForSuccessAlert';
import {journalPagesTest} from '../journal-web/fixtures/journalPagesTest';

export const test = mergeTests(
	apiHelpersTest,
	changeTrackingPagesTest,
	featureFlagsTest({
		'LPD-20556': true,
	}),
	journalPagesTest
);

const publicationCount = 8;
const ctCollections = [];
let articleTitle: string;

test.beforeEach(
	async ({
		apiHelpers,
		changeTrackingPage,
		journalEditArticlePage,
		journalPage,
		page,
	}) => {
		await changeTrackingPage.workOnProduction();

		articleTitle = 'Test ' + getRandomInt() + ' WC Article';
		await journalEditArticlePage.goto();
		await journalEditArticlePage.fillTitle(articleTitle);
		await page.getByRole('button', {name: 'Publish'}).click();
		await waitForSuccessAlert(
			page,
			`Success:${articleTitle} was created successfully.`
		);

		const ctCollectionNamePrefix = getRandomString();
		for (let i = 0; i < publicationCount; i++) {
			const ctCollectionName = ctCollectionNamePrefix + ' ' + i;

			const newCTCollection =
				await apiHelpers.headlessChangeTracking.createCTCollection(
					ctCollectionName
				);

			ctCollections.push(newCTCollection);

			await changeTrackingPage.workOnPublication(newCTCollection);

			if (i !== publicationCount - 1) {
				await journalPage.goto();
				const webContentHeader = page.getByRole('heading', {
					name: 'Web Content',
				});
				await expect(webContentHeader).toBeVisible();
				await journalPage.goToJournalArticleAction(
					'Delete',
					articleTitle
				);
				await waitForSuccessAlert(
					page,
					`Success: The element ${articleTitle} was moved to the Recycle Bin.`
				);
			}
		}
	}
);

test.afterEach(async ({apiHelpers, changeTrackingPage, journalPage, page}) => {
	for (let i = 0; i < ctCollections.length; i++) {
		await apiHelpers.headlessChangeTracking.deleteCTCollection(
			ctCollections[i].id
		);
	}

	await changeTrackingPage.workOnProduction();

	await journalPage.goto();
	await page.getByRole('heading', {name: 'Web Content'}).isVisible();
	await journalPage.goToJournalArticleAction('Delete', articleTitle);
});

test('LPD-22759 Allow users to view the entire history of an entity in a popup modal', async ({
	journalPage,
	page,
}) => {
	await journalPage.goto();
	await page.getByRole('heading', {name: 'Web Content'}).isVisible();
	await journalPage.goToJournalArticleAction('Edit', articleTitle);
	await page.getByRole('tab', {name: 'Properties'}).waitFor();

	await page.locator('.change-tracking-timeline-button').click();
	await page
		.locator('.publication-timeline')
		.getByText('Modified')
		.first()
		.isVisible();
	await page
		.locator('.publication-timeline')
		.getByRole('button', {name: 'View More'})
		.click();
	await page
		.locator('.entity-history-modal')
		.getByRole('heading', {name: 'View All History'})
		.isVisible();

	for (let i = 0; i < ctCollections.length; i++) {
		if (i !== ctCollections.length - 1) {
			await expect(
				page
					.locator('.entity-history-modal')
					.getByText(ctCollections[i].name)
			).toBeVisible();
		}
	}
});
