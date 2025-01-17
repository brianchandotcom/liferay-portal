/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';
import {createReadStream} from 'fs';
import path from 'path';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {changeTrackingPagesTest} from '../../fixtures/changeTrackingPagesTest';
import getRandomString from '../../utils/getRandomString';
import getBasicWebContentStructureId from '../../utils/structured-content/getBasicWebContentStructureId';
import {featureFlagPagesTest} from '../feature-flag-web/fixtures/featureFlagPagesTest';

export const test = mergeTests(
	apiHelpersTest,
	changeTrackingPagesTest,
	featureFlagPagesTest
);

let file;
let journalArticleTitle;

test.beforeEach(async ({apiHelpers}) => {
	const site =
		await apiHelpers.headlessAdminUser.getSiteByFriendlyUrlPath('guest');

	file = await apiHelpers.headlessDelivery.postDocument(
		site.id,
		createReadStream(path.join(__dirname, '/dependencies/attachment.txt'))
	);

	const basicWebContentStructureId =
		await getBasicWebContentStructureId(apiHelpers);

	journalArticleTitle = getRandomString();

	await apiHelpers.jsonWebServicesJournal.addWebContent({
		ddmStructureId: basicWebContentStructureId,
		groupId: site.id,
		titleMap: {en_US: journalArticleTitle},
	});
});

test('LPD-26363 Can delete ctEntries in bulk', async ({
	changeTrackingPage,
	ctCollection,
	page,
}) => {
	await changeTrackingPage.goToReviewChanges(ctCollection.body.name);

	await expect(
		changeTrackingPage.frontendDataSetEntries.getByText(file.title)
	).toBeVisible();

	await page.getByTitle('Select Items').check();

	await expect(page.getByText('Items Selected')).toBeVisible();

	await expect(changeTrackingPage.bulkDeleteButton).toBeVisible();

	await changeTrackingPage.bulkDeleteButton.click();

	await expect(
		page.getByRole('heading', {name: 'Discarded Changes'})
	).toBeVisible();

	await page.getByRole('button', {name: 'Discard'}).click();

	await changeTrackingPage.goToReviewChanges(ctCollection.body.name);

	await expect(page.getByText('No Results Found')).toBeVisible();
});
