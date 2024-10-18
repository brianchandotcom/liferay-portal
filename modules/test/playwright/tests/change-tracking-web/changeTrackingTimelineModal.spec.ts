/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';
import moment from 'moment';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {changeTrackingPagesTest} from '../../fixtures/changeTrackingPagesTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {getRandomInt} from '../../utils/getRandomInt';
import getRandomString from '../../utils/getRandomString';
import {waitForAlert} from '../../utils/waitForAlert';
import {journalPagesTest} from '../journal-web/fixtures/journalPagesTest';
import {JournalPage} from '../journal-web/pages/JournalPage';

export const test = mergeTests(
	apiHelpersTest,
	changeTrackingPagesTest,
	featureFlagsTest({
		'LPD-20556': true,
	}),
	journalPagesTest
);

const publicationCount = 6;
let ctCollections = [];
let articleTitle: string;
let date;

test.beforeEach(
	async ({apiHelpers, changeTrackingPage, journalEditArticlePage, page}) => {
		const ctCollectionNamePrefix = getRandomString();
		ctCollections = [];

		for (let i = 0; i <= publicationCount; i++) {
			const ctCollectionName = ctCollectionNamePrefix + ' ' + i;

			const newCTCollection =
				await apiHelpers.headlessChangeTracking.createCTCollection(
					ctCollectionName
				);

			ctCollections.push(newCTCollection);

			await changeTrackingPage.workOnPublication(newCTCollection);

			if (i !== publicationCount) {
				articleTitle = 'Test ' + getRandomInt() + ' WC Article';
				await journalEditArticlePage.goto();
				await page.locator('div[data-qa-id="content"]').waitFor();
				await journalEditArticlePage.fillTitle(articleTitle);
				await page.getByRole('button', {name: 'Publish'}).click();
				await waitForAlert(
					page,
					`Success:${articleTitle} was created successfully.`
				);
			}
		}
	}
);

test.afterEach(async ({apiHelpers}) => {
	for (let i = 0; i < ctCollections.length; i++) {
		await apiHelpers.headlessChangeTracking.deleteCTCollection(
			ctCollections[i].id
		);
	}
});

const getEntityHistoryTableLocator = (page: Page) => {
	return page.frameLocator(
		'iframe[title="View Entity Modification History"]'
	);
};

const getPublicationTimelineLocator = (page: Page) => {
	return page.locator('.publication-timeline');
};

const getPublicationTimelineButton = (page: Page) => {
	return page.locator('.change-tracking-timeline-button');
};

const goToPublicationTimelineModal = async (
	page: Page,
	journalPage: JournalPage
) => {
	await journalPage.goto();
	await page.getByRole('heading', {name: 'Web Content'}).waitFor();

	const timelineButton = getPublicationTimelineButton(page);
	await timelineButton.waitFor();
	await timelineButton.click();

	const publicationTimelineLocator = getPublicationTimelineLocator(page);
	await publicationTimelineLocator
		.locator('li .dropdown-item')
		.first()
		.waitFor();

	const timelineViewMoreButton = publicationTimelineLocator.getByRole(
		'button',
		{name: 'View More'}
	);
	await timelineViewMoreButton.waitFor();
	await timelineViewMoreButton.click();

	await page
		.locator(
			'#_com_liferay_change_tracking_web_portlet_PublicationsPortlet_publication-timeline-history-modal'
		)
		.getByRole('heading', {name: 'View Entity Modification History'})
		.waitFor();
};

test('LPD-22759 Allow users to view the entire history of an entity in a popup modal', async ({
	journalPage,
	page,
}) => {
	await goToPublicationTimelineModal(page, journalPage);

	const entityHistoryModalLocator = getEntityHistoryTableLocator(page);
	await entityHistoryModalLocator.getByText(ctCollections[0].name).waitFor();

	for (let i = 0; i < ctCollections.length; i++) {
		if (i !== ctCollections.length - 1) {
			await expect(
				entityHistoryModalLocator.getByText(ctCollections[i].name)
			).toBeVisible();
		}
	}
});

test('LPD-22768 Add options to interact with the same entity in other publications via a popup modal', async ({
	journalPage,
	page,
}) => {
	await goToPublicationTimelineModal(page, journalPage);

	const entityHistoryModalLocator = getEntityHistoryTableLocator(page);
	await entityHistoryModalLocator.getByText(ctCollections[0].name).waitFor();

	const firstDropdown = entityHistoryModalLocator
		.locator('.item-actions .dropdown svg.lexicon-icon-ellipsis-v')
		.first();
	await firstDropdown.waitFor();
	await firstDropdown.click();

	await entityHistoryModalLocator
		.locator('div.dropdown-menu.show li')
		.first()
		.waitFor();

	await expect(
		entityHistoryModalLocator.getByRole('menuitem', {name: 'Discard'})
	).toBeVisible();

	await expect(
		entityHistoryModalLocator.getByRole('menuitem', {
			name: 'Edit in Publication',
		})
	).toBeVisible();

	await expect(
		entityHistoryModalLocator.getByRole('menuitem', {name: 'Move Changes'})
	).toBeVisible();

	await expect(
		entityHistoryModalLocator.getByRole('menuitem', {name: 'Review Change'})
	).toBeVisible();
});

test('LPD-38392 Assert View Entity Modification History sorting', async ({
	apiHelpers,
	changeTrackingPage,
	journalPage,
	page,
}) => {
	await apiHelpers.headlessChangeTracking.publishCTCollection(
		ctCollections[0].id
	);

	date = moment().format('ll');

	await changeTrackingPage.workOnProduction();
	await goToPublicationTimelineModal(page, journalPage);
	const entityHistoryModalLocator = getEntityHistoryTableLocator(page);

	const statusColumnHeader = entityHistoryModalLocator.getByRole('button', {
		name: 'Status',
	});
	await statusColumnHeader.click();
	await expect(statusColumnHeader).toBeVisible();

	await expect(
		entityHistoryModalLocator
			.locator('div:nth-child(1) > div:nth-child(4)')
			.filter({hasText: 'Approved'})
	).toBeVisible();
	await expect(
		entityHistoryModalLocator
			.locator('div:nth-child(1) > div:nth-child(5)')
			.filter({hasText: 'Test Test'})
	).toBeVisible();
	await expect(
		entityHistoryModalLocator
			.locator('div:nth-child(1) > div:nth-child(6)')
			.filter({hasText: date})
	).toBeVisible();

	await clickAndExpectToBeVisible({
		autoClick: true,
		target: entityHistoryModalLocator
			.locator('div:nth-child(6) > div:nth-child(4)')
			.filter({hasText: 'Approved'}),
		trigger: statusColumnHeader,
	});

	await expect(
		entityHistoryModalLocator
			.locator('div:nth-child(6) > div:nth-child(4)')
			.filter({hasText: 'Approved'})
	).toBeVisible();
	await expect(
		entityHistoryModalLocator
			.locator('div:nth-child(6) > div:nth-child(5)')
			.filter({hasText: 'Test Test'})
	).toBeVisible();
	await expect(
		entityHistoryModalLocator
			.locator('div:nth-child(6) > div:nth-child(6)')
			.filter({hasText: date})
	).toBeVisible();

	await journalPage.goto();
	await page.locator('label > .custom-checkbox').setChecked(true);
	await page.getByRole('button', {name: 'Delete'}).click();
});
