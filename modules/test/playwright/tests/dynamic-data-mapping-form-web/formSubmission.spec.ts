/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';
import path from 'path';

import {formsPagesTest} from '../../fixtures/formsPagesTest';
import {loginTest} from '../../fixtures/loginTest';
import {deleteItems} from './utils/deleteItems';

export const test = mergeTests(loginTest(), formsPagesTest);

let hasDataProvider: boolean = false;

test.afterEach(async ({formsPage, page}) => {
	await formsPage.goTo();

	await deleteItems(formsPage, page);

	if (hasDataProvider) {
		await page.waitForLoadState();

		await formsPage.dataProvidersTab.click();

		await deleteItems(formsPage, page);

		hasDataProvider = false;
	}
});

test.describe('Manage forms through submission page', () => {
	test('can submit manual entry while using data provider autofill rule', async ({
		context,
		formBuilderPage,
		formsPage,
		page,
	}) => {
		hasDataProvider = true;

		await formsPage.goTo();

		await formsPage.importForm(
			path.join(
				__dirname,
				'dependencies',
				'form-with-data-provider.portlet.lar'
			)
		);

		await formsPage.openForm('Form with data provider');

		const pagePromise = context.waitForEvent('page');

		await formBuilderPage.openFormSubmission();

		const formSubmissionPage = await pagePromise;

		await formSubmissionPage.getByLabel('Population').fill('123456');

		await formSubmissionPage.getByRole('button', {name: 'Submit'}).click();

		await expect(
			formSubmissionPage.getByText(
				'Your information was successfully received. Thank you for filling out the form.'
			)
		).toBeVisible();

		await formSubmissionPage.close();

		await formBuilderPage.entriesTab.click();

		await expect(page.getByText('123456')).toBeVisible();
	});
});
