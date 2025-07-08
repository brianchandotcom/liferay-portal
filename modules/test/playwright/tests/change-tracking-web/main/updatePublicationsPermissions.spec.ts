/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {changeTrackingPagesTest} from '../../../fixtures/changeTrackingPagesTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import getRandomString from '../../../utils/getRandomString';
import {performLoginViaApi, performLogout} from '../../../utils/performLogin';

export const test = mergeTests(
	apiHelpersTest,
	changeTrackingPagesTest,
	isolatedSiteTest
);

const actionNames = [
	'Delete',
	'Permissions',
	'Invite Users',
	'Update',
	'Publish',
	'View',
];

let user;
let ctCollection;

test.beforeEach(async ({changeTrackingPage, page}) => {
	user = await changeTrackingPage.addUserWithPublicationsUserRole();

	await changeTrackingPage.gotoPublicationsPermissions();

	for (const actionName of actionNames) {
		await expect(
			page.getByRole('cell', {exact: true, name: actionName})
		).toBeVisible();
	}
});

test.afterEach(async ({apiHelpers, changeTrackingPage, page}) => {
	try {
		await performLogout(page);
	}
	finally {
		await performLoginViaApi({page, screenName: 'test'});
	}

	await apiHelpers.headlessAdminUser.deleteUserAccount(Number(user.id));

	await changeTrackingPage.gotoPublicationsPermissions();

	const ownerRole = page.locator('tr').filter({hasText: 'Owner'});

	for (let i = 1; i < 6; i++) {
		const checkbox = ownerRole
			.locator('td')
			.nth(i)
			.locator('.custom-control > label > .custom-control-input');

		await checkbox.check();
	}

	const publicationsUserRole = page
		.locator('tr')
		.filter({hasText: 'Publications User'});

	const publicationsUserRoleDeletePermission = publicationsUserRole
		.locator('td')
		.nth(1)
		.locator('.custom-control > label > .custom-control-input');

	await publicationsUserRoleDeletePermission.uncheck();

	await page.getByRole('button', {name: 'Save'}).click();

	await apiHelpers.headlessChangeTracking.deleteCTCollection(
		ctCollection.body.id
	);
});

test('LPD-53946 Update Permissions for Owner Role', async ({
	apiHelpers,
	changeTrackingPage,
	page,
}) => {
	const ownerRole = page.locator('tr').filter({hasText: 'Owner'});

	for (let i = 1; i < 7; i++) {
		const checkbox = ownerRole
			.locator('td')
			.nth(i)
			.locator('.custom-control > label > .custom-control-input');

		await expect(checkbox).toBeChecked();
	}

	await expect(
		ownerRole
			.locator('td')
			.nth(6)
			.locator('.custom-control > label > .custom-control-input')
	).toBeDisabled();

	const ownerRolePublishPermission = ownerRole
		.locator('td')
		.nth(5)
		.locator('.custom-control > label > .custom-control-input');

	await ownerRolePublishPermission.uncheck();

	const saveButton = page.getByRole('button', {name: 'Save'});

	await saveButton.click();

	await performLogout(page);

	await performLoginViaApi({page, screenName: user.alternateName});

	ctCollection =
		await apiHelpers.headlessChangeTracking.createCTCollection(
			getRandomString()
		);

	await changeTrackingPage.goToReviewChanges(ctCollection.body.name);

	const publishLink = page.getByRole('link', {name: 'Publish'});

	await expect(publishLink).toBeHidden();

	await performLogout(page);

	await performLoginViaApi({page, screenName: 'test'});

	await changeTrackingPage.goToReviewChanges(ctCollection.body.name);

	await expect(publishLink).toBeVisible();
});

test('LPD-53946 Update Permissions for Regular Role', async ({
	apiHelpers,
	changeTrackingPage,
	page,
}) => {
	const ownerRole = page.locator('tr').filter({hasText: 'Owner'});

	for (let i = 1; i < 6; i++) {
		const checkbox = ownerRole
			.locator('td')
			.nth(i)
			.locator('.custom-control > label > .custom-control-input');

		await checkbox.uncheck();
	}

	const publicationsUserRole = page
		.locator('tr')
		.filter({hasText: 'Publications User'});

	const publicationsUserRoleDeletePermission = publicationsUserRole
		.locator('td')
		.nth(1)
		.locator('.custom-control > label > .custom-control-input');

	await publicationsUserRoleDeletePermission.check();

	const saveButton = page.getByRole('button', {name: 'Save'});

	await saveButton.click();

	await performLogout(page);

	await performLoginViaApi({page, screenName: user.alternateName});

	ctCollection =
		await apiHelpers.headlessChangeTracking.createCTCollection(
			getRandomString()
		);

	await changeTrackingPage.goto();

	await page.getByRole('button', {name: 'Actions'}).click();

	await expect(page.getByRole('menuitem', {name: 'Edit'})).toBeHidden();

	page.on('dialog', (dialog) => dialog.accept());

	await page.getByRole('menuitem', {name: 'Delete'}).click();

	await page.reload();

	await expect(page.getByText(ctCollection.body.name)).toBeHidden();
});
