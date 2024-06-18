/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {changeTrackingPagesTest} from '../../fixtures/changeTrackingPagesTest';
import getRandomString from '../../utils/getRandomString';
import {PORTLET_URLS} from '../../utils/portletUrls';

export const test = mergeTests(changeTrackingPagesTest);

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
