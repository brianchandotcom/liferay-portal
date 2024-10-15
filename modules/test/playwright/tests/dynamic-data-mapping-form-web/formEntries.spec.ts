/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {formsPagesTest} from '../../fixtures/formsPagesTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';
import {deleteItems} from './utils/deleteItems';

export const test = mergeTests(loginTest(), formsPagesTest);

test.afterEach(async ({formsPage, page}) => {
	await formsPage.goTo();

	await deleteItems(formsPage, page);
});

test.beforeEach(({page}) => {
	page.setViewportSize({height: 1080, width: 1920});
});

test('can interact with a large list of fields on the form entries page', async ({
	context,
	formBuilderPage,
	formBuilderSidePanelPage,
	page,
}) => {
	await formBuilderPage.goToNew();

	for (const index of Array.from(Array(30).keys())) {
		await formBuilderSidePanelPage.addFieldByDoubleClick('Text');

		await formBuilderSidePanelPage.label.fill(`Text ${index}`);

		await formBuilderSidePanelPage.clickBackButton();
	}

	await formBuilderPage.publishButton.click();

	const pagePromise = context.waitForEvent('page');

	await formBuilderPage.openFormSubmission();

	const formSubmissionPage = await pagePromise;

	const formEntry = getRandomString();

	await formSubmissionPage.getByLabel('Text 29').fill(formEntry);

	await formSubmissionPage.getByRole('button', {name: 'Submit'}).click();

	await expect(
		formSubmissionPage.getByText(
			'Your information was successfully received. Thank you for filling out the form.'
		)
	).toBeVisible();

	await formSubmissionPage.close();

	await formBuilderPage.entriesTab.click();

	await page.locator('a').filter({hasText: 'Text 29'}).click();

	await expect(page.getByText(formEntry)).toBeVisible();
});
