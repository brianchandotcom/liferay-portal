/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';
import {createReadStream} from 'fs';
import path from 'path';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {changeTrackingPagesTest} from '../../fixtures/changeTrackingPagesTest';
import {documentLibraryPagesTest} from '../../fixtures/documentLibraryPages.fixtures';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {waitForAlert} from '../../utils/waitForAlert';

const test = mergeTests(
	apiHelpersTest,
	documentLibraryPagesTest,
	isolatedSiteTest,
	changeTrackingPagesTest
);

test('Checking out a document is done in production', async ({
	apiHelpers,
	changeTrackingPage,
	ctCollection,
	documentLibraryPage,
	page,
	site,
}) => {
	await changeTrackingPage.workOnProduction();

	await apiHelpers.headlessDelivery.postDocument(
		site.id,
		createReadStream(path.join(__dirname, '/dependencies/attachment.txt'))
	);

	await changeTrackingPage.workOnPublication(ctCollection);

	await documentLibraryPage.goto(site.friendlyUrlPath);

	const contextPopup = await page.getByRole('button', {
		name: 'Stay in Current Publication',
	});

	if (await contextPopup.isVisible()) {
		await contextPopup.click();
	}

	await clickAndExpectToBeVisible({
		autoClick: true,
		target: page.getByRole('menuitem', {name: 'Checkout'}),
		trigger: page.getByLabel('Actions', {exact: true}),
	});

	await waitForAlert(page, 'Success:Your request completed successfully.');

	await changeTrackingPage.workOnProduction();

	await clickAndExpectToBeVisible({
		autoClick: true,
		target: page.getByRole('menuitem', {name: 'Cancel Checkout'}),
		trigger: page.getByLabel('Actions', {exact: true}),
	});

	await waitForAlert(page, 'Success:Your request completed successfully.');

	await clickAndExpectToBeVisible({
		autoClick: true,
		target: page.getByRole('menuitem', {name: 'Checkout'}),
		trigger: page.getByLabel('Actions', {exact: true}),
	});

	await waitForAlert(page, 'Success:Your request completed successfully.');

	await changeTrackingPage.workOnPublication(ctCollection);

	await clickAndExpectToBeVisible({
		autoClick: false,
		target: page.getByRole('menuitem', {name: 'Cancel Checkout'}),
		trigger: page.getByLabel('Actions', {exact: true}),
	});
});
