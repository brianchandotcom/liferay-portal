/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {changeTrackingPagesTest} from '../../fixtures/changeTrackingPagesTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';

export const test = mergeTests(
	apiHelpersTest,
	changeTrackingPagesTest,
	isolatedSiteTest
);

test('LPD-29823 Provide a shortcut to Data Removal scripts from Publications Admin', async ({
	changeTrackingPage,
	page,
}) => {
	await changeTrackingPage.goToPublicationHistory();

	const optionsMenuItem = page.getByLabel('Options');

	await expect(optionsMenuItem).toBeVisible();

	await optionsMenuItem.click();

	const dataRemovalMenuItem = page.getByText('Data Removal');

	await expect(dataRemovalMenuItem).toBeVisible();
});

test('LPD-29837 Add option to delete history from Publications Admin', async ({
	apiHelpers,
	changeTrackingPage,
	ctCollection,
	page,
	site,
}) => {
	await changeTrackingPage.workOnPublication(ctCollection);

	await apiHelpers.jsonWebServicesJournal.addFolder({
		groupId: site.id,
	});

	await apiHelpers.headlessChangeTracking.publishCTCollection(
		ctCollection.id
	);

	await page.reload();

	await changeTrackingPage.goToPublicationHistory();

	const collectionRowItem = page
		.locator('.dnd-tr')
		.filter({hasText: ctCollection.name})
		.first();

	await expect(collectionRowItem).toBeVisible();

	const actionsButton = collectionRowItem.getByRole('button', {
		name: 'Actions',
	});

	await expect(actionsButton).toBeVisible();

	await actionsButton.click();

	const deleteMenuItem = page.getByRole('menuitem', {name: 'Delete'});

	await expect(deleteMenuItem).toBeVisible();

	page.on('dialog', (dialog) => dialog.accept());

	await deleteMenuItem.click();

	const collectionHeaderItem = page
		.locator('.dnd-th')
		.filter({hasText: 'Publication'})
		.first();

	await expect(collectionHeaderItem).toBeVisible();

	await expect(collectionRowItem).toBeHidden();
});
