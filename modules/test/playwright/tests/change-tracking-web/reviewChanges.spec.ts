/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';
import {createReadStream} from 'fs';
import path from 'path';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {changeTrackingPagesTest} from '../../fixtures/changeTrackingPagesTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import getRandomString from '../../utils/getRandomString';
import {PORTLET_URLS} from '../../utils/portletUrls';

export const test = mergeTests(
	apiHelpersTest,
	changeTrackingPagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-20131': true,
	})
);

test('LPD-28276 Assert tag data persists in parent tab', async ({
	changeTrackingPage,
	ctCollection,
	page,
}) => {
	await page.goto(`/group/guest${PORTLET_URLS.tagsAdmin}`);

	await page.getByRole('link', {name: 'Add Tag'}).click();

	const tagName = getRandomString();

	await page.getByPlaceholder('Name').fill(tagName);

	await page.getByRole('button', {name: 'Save'}).click();

	await changeTrackingPage.goToReviewChanges(ctCollection.name);

	await changeTrackingPage.reviewChange(tagName);

	await changeTrackingPage.selectTab('Parents');

	await page.waitForTimeout(3000);

	await expect(page.getByText('Guest', {exact: true})).toBeVisible();
});

test('LPD-29088 Assert Publication Overview panel empty', async ({
	changeTrackingPage,
	ctCollection,
	page,
}) => {
	await changeTrackingPage.goToReviewChanges(ctCollection.name);

	const publicationOverviewPanel = page.getByRole('button', {
		name: 'Publication Overview',
	});

	await expect(publicationOverviewPanel).toBeVisible();
	await expect(page.getByText('No changes were found.')).toBeVisible();

	await publicationOverviewPanel.click();
	await expect(page.getByText('No changes were found.')).not.toBeVisible();
});

test('LPD-29088 Assert Publication Overview panel is visible', async ({
	apiHelpers,
	changeTrackingPage,
	ctCollection,
	page,
}) => {
	await changeTrackingPage.workOnProduction();

	const site1 = await apiHelpers.headlessSite.createSite({
		name: getRandomString(),
	});
	apiHelpers.data.push({id: site1.id, type: 'site'});

	const site2 = await apiHelpers.headlessSite.createSite({
		name: getRandomString(),
	});
	apiHelpers.data.push({id: site2.id, type: 'site'});

	await changeTrackingPage.workOnPublication(ctCollection);

	await page.goto(`/group/guest${PORTLET_URLS.tagsAdmin}`);
	await page.getByRole('link', {name: 'Add Tag'}).click();
	await page.getByPlaceholder('Name').fill(getRandomString());
	await page.getByRole('button', {name: 'Save'}).click();

	await apiHelpers.headlessDelivery.postMessageBoardThread({
		articleBody: getRandomString(),
		headline: getRandomString(),
		siteId: site1.id,
	});

	for (let i = 0; i < 3; i++) {
		await apiHelpers.headlessDelivery.postDocument(
			site1.id,
			createReadStream(
				path.join(__dirname, '/dependencies/attachment.txt')
			)
		);

		await apiHelpers.headlessDelivery.postBlog(site2.id);
	}

	await changeTrackingPage.goToReviewChanges(ctCollection.name);

	await expect(page.getByText('Liferay DXP (1): Tag (1)')).toBeVisible();
	await expect(
		page.getByText(
			site1.name +
				' (5): Document (3) Message Boards Message (1) Message Boards Thread (1)'
		)
	).toBeVisible();
	await expect(
		page.getByText(site2.name + ' (3):  Blogs Entry (3)')
	).toBeVisible();

	await apiHelpers.headlessChangeTracking.publishCTCollection(
		ctCollection.id
	);

	await changeTrackingPage.goToReviewChangesHistory(ctCollection.name);

	await expect(page.getByText('Liferay DXP (1): Tag (1)')).toBeVisible();
	await expect(
		page.getByText(
			site1.name +
				' (5): Document (3) Message Boards Message (1) Message Boards Thread (1)'
		)
	).toBeVisible();
	await expect(
		page.getByText(site2.name + ' (3):   Blogs Entry (3)')
	).toBeVisible();
});

test('LPD-29089 Assert Publication Overview filter', async ({
	apiHelpers,
	changeTrackingPage,
	ctCollection,
	page,
}) => {
	const site =
		await apiHelpers.headlessAdminUser.getSiteByFriendlyUrlPath('guest');

	await apiHelpers.headlessDelivery.postBlog(site.id);

	await apiHelpers.headlessDelivery.postWikiNode(site.id);

	await changeTrackingPage.goToReviewChanges(ctCollection.name);

	await page.getByRole('link', {name: 'Blogs Entry (1)'}).click();

	await expect(
		changeTrackingPage.frontendDataSetEntries.getByText('Blogs Entry')
	).toBeVisible();

	await expect(
		changeTrackingPage.frontendDataSetEntries.getByText('Wiki Node')
	).toBeHidden();
});
