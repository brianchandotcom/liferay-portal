/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {changeTrackingPagesTest} from '../../../fixtures/changeTrackingPagesTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {pagesAdminPagesTest} from '../../../fixtures/pagesAdminPagesTest';

export const test = mergeTests(
	apiHelpersTest,
	changeTrackingPagesTest,
	pagesAdminPagesTest,
	pageEditorPagesTest
);

test('LPD-79951 Can view correct layout preview', async ({
	changeTrackingPage,
	ctCollection,
	page,
	pageEditorPage,
}) => {
	await changeTrackingPage.workOnPublication(ctCollection);

	await test.step('Go to home edit page', async () => {
		await page.goto(`/web/guest/home?p_l_mode=edit`);
	});

	const headingId = await pageEditorPage.getFragmentId('Paragraph');

	await pageEditorPage.editTextEditable(headingId, 'element-text', 'Edited');

	await pageEditorPage.publishPage();

	await changeTrackingPage.goToReviewChanges(ctCollection.body.name);

	await changeTrackingPage.reviewChange('Home');

	await page.locator('.btn-outline-secondary').click();

	await page.getByRole('menuitem', {name: ctCollection.body.name}).click();

	const previewContent = page.locator('.publications-render-view-content');

	await expect(previewContent.getByText('Edited')).toBeVisible();

	await page.locator('.btn-outline-secondary').click();

	await page.getByRole('menuitem', {name: 'Production'}).click();

	await expect(previewContent.getByText('Welcome to Liferay')).toBeVisible();
});
