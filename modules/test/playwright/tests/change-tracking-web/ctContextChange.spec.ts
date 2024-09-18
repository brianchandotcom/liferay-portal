/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {changeTrackingPagesTest} from '../../fixtures/changeTrackingPagesTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';
import {journalPagesTest} from '../journal-web/fixtures/journalPagesTest';

export const test = mergeTests(
	apiHelpersTest,
	changeTrackingPagesTest,
	featureFlagsTest({
		'LPD-20131': true,
	}),
	journalPagesTest,
	loginTest()
);

test('LPD-29562 Assert popover only appears when context is changed', async ({
	apiHelpers,
	journalPage,
	page,
}) => {
	await journalPage.goto();

	await expect(
		page.getByText('Keep working in this publication?', {exact: true})
	).toBeHidden();

	const site = await apiHelpers.headlessSite.createSite({
		name: getRandomString(),
	});

	await journalPage.goto(site.friendlyUrlPath);

	await expect(
		page.getByText('Keep working in this publication?', {exact: true})
	).toBeVisible();

	await expect(
		page.getByText(
			'You just switched contexts. Do you want to keep working in this publication?',
			{exact: true}
		)
	).toBeVisible();
});

test('LPD-33582 Assert context change popover buttons behavior', async ({
	apiHelpers,
	changeTrackingPage,
	ctCollection,
	journalPage,
	page,
}) => {
	const site1 = await apiHelpers.headlessSite.createSite({
		name: getRandomString(),
	});

	await journalPage.goto(site1.friendlyUrlPath);

	page.on('dialog', (dialog) => dialog.accept());

	await page.getByRole('button', {name: 'Work on Production'}).click();

	await expect(
		page.getByRole('button', {exact: true, name: 'Production'})
	).toBeVisible();

	await changeTrackingPage.workOnPublication(ctCollection);

	const site2 = await apiHelpers.headlessSite.createSite({
		name: getRandomString(),
	});

	await journalPage.goto(site2.friendlyUrlPath);

	await page
		.getByRole('button', {name: 'Stay in Current Publication'})
		.click();

	await expect(
		page.getByText('Keep working in this publication?', {exact: true})
	).toBeHidden();

	await journalPage.goto(site1.friendlyUrlPath);

	const ctCollection2 =
		await apiHelpers.headlessChangeTracking.createCTCollection(
			getRandomString()
		);

	await page.getByRole('button', {name: 'Select a Publication'}).click();

	await page.getByText(ctCollection2.name).click();

	await expect(
		page.getByRole('button', {name: ctCollection2.name})
	).toBeVisible();

	await apiHelpers.headlessChangeTracking.deleteCTCollection(
		ctCollection2.id
	);
});

test('LPD-29693, LPD-29294 Assert silence context change popover behavior', async ({
	apiHelpers,
	changeTrackingPage,
	journalPage,
	page,
}) => {
	const site1 = await apiHelpers.headlessSite.createSite({
		name: getRandomString(),
	});

	await journalPage.goto(site1.friendlyUrlPath);

	const popoverCheckbox = page.getByLabel('Do not show this message');

	await popoverCheckbox.check();

	await page
		.getByTitle('hideContextChangeWarningDuration')
		.selectOption({label: 'Forever'});

	await page
		.getByRole('button', {name: 'Stay in Current Publication'})
		.click();

	const site2 = await apiHelpers.headlessSite.createSite({
		name: getRandomString(),
	});

	await journalPage.goto(site2.friendlyUrlPath);

	const popoverTitle = page.getByText('Keep working in this publication?', {
		exact: true,
	});

	await expect(popoverTitle).toBeHidden();

	await changeTrackingPage.goto();

	const optionsDropdown = page.getByLabel('Options');

	await optionsDropdown.click();

	const notificationsOption = page.getByRole('menuitem', {
		name: 'Notifications',
	});

	await notificationsOption.click();

	await expect(
		page.getByRole('heading', {name: 'Notifications'})
	).toBeVisible();

	await page.getByRole('button', {name: 'Cancel'}).click();

	await optionsDropdown.click();

	await notificationsOption.click();

	await page.getByLabel('Hide warning when changing').uncheck();

	await page.getByRole('button', {name: 'Save'}).click();

	await page.reload();

	await journalPage.goto(site1.friendlyUrlPath);

	await expect(popoverCheckbox).toBeVisible();

	await popoverCheckbox.check();

	await page.getByTestId('applicationsMenu').click();

	await journalPage.goto(site2.friendlyUrlPath);

	await expect(popoverTitle).toBeHidden();
});
