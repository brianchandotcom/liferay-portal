/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {changeTrackingPagesTest} from '../../fixtures/changeTrackingPagesTest';
import getRandomString from '../../utils/getRandomString';
import {blogsPagesTest} from '../blogs-web/fixtures/blogsPagesTest';

export const test = mergeTests(changeTrackingPagesTest, blogsPagesTest);

test('Cannot publish empty ctCollection', async ({
	blogsEditBlogEntryPage,
	changeTrackingPage,
	ctCollection,
	page,
}) => {
	await changeTrackingPage.workOnProduction();

	await blogsEditBlogEntryPage.goto();

	let title = getRandomString();
	let content = getRandomString();

	await blogsEditBlogEntryPage.editBlogEntry({
		content,
		publish: true,
		title,
	});

	await changeTrackingPage.workOnPublication(ctCollection);

	await page.getByRole('link', {name: title}).click();

	title = getRandomString();
	content = getRandomString();

	await blogsEditBlogEntryPage.editBlogEntry({
		content,
		publish: true,
		title,
	});

	await page.reload();

	await changeTrackingPage.workOnProduction();

	await page.getByLabel('More actions').click();

	await page.getByRole('menuitem', {name: 'Delete'}).click();

	await page.waitForLoadState();

	await changeTrackingPage.goToReviewChanges(ctCollection.name);

	await page.getByRole('link', {name: 'Publish'}).click();

	await expect(page.getByText('Publish: ' + ctCollection.name)).toBeVisible();

	await page
		.locator('li')
		.filter({hasText: 'Test Test modified a Asset'})
		.getByRole('button')
		.click();

	const discardMenuItem = page.getByRole('menuitem', {
		name: 'Discard Change',
	});

	await discardMenuItem.click();

	const discardButton = page.getByRole('button', {name: 'Discard'});

	await discardButton.click();

	await page.getByRole('group').locator('div').getByRole('button').click();

	await discardMenuItem.click();

	await discardButton.click();

	await expect(page.getByText('Checking changes')).toBeVisible();

	await expect(page.getByRole('button', {name: 'Publish'})).toBeDisabled();
});
