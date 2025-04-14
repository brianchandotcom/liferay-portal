/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {changeTrackingPagesTest} from '../../fixtures/changeTrackingPagesTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';

export const test = mergeTests(
	featureFlagsTest({
		'COMMERCE-8087': {enabled: true},
	}),
	changeTrackingPagesTest,
	applicationsMenuPageTest
);

test('LPD-24076 Popover is displayed on Data Migration Center page', async ({
	applicationsMenuPage,
	changeTrackingPage,
	ctCollection,
	page,
}) => {
	await changeTrackingPage.workOnPublication(ctCollection);

	await applicationsMenuPage.goToDataMigrationCenter();

	await expect(
		page.getByText('Cannot Save Changes to Publication', {exact: true})
	).toBeVisible();
});

test('LPD-24076 Popover button directs to Production', async ({
	applicationsMenuPage,
	changeTrackingPage,
	ctCollection,
	page,
}) => {
	await changeTrackingPage.workOnPublication(ctCollection);

	await applicationsMenuPage.goToDataMigrationCenter();

	page.on('dialog', (dialog) => dialog.accept());

	await page.getByRole('button', {name: 'Work on Production'}).click();

	await expect(
		page.getByRole('button', {exact: true, name: 'Production'})
	).toBeVisible();
});
