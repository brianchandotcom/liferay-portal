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

test.afterEach(async ({formsPage}) => {
	await formsPage.goTo();

	await deleteItems(formsPage);
});

test.beforeEach(({page}) => {
	page.setViewportSize({height: 1080, width: 1920});
});

test('can interact with a large list of fields on the form entries page', async ({
	formBuilderPage,
	formBuilderSidePanelPage,
	formsPage,
	page,
}) => {
	await formBuilderPage.goToNew();

	for (const index of Array.from(Array(30).keys())) {
		await formBuilderSidePanelPage.addFieldByDoubleClick('Text');

		await formBuilderSidePanelPage.label.fill(`Text ${index}`);

		await formBuilderSidePanelPage.clickBackButton();
	}

	await formBuilderPage.clickPublishFormButton();

	const formSubmissionURL = await formBuilderPage.getFormSubmissionURL();

	await page.goto(formSubmissionURL, {waitUntil: 'networkidle'});

	const formEntry = getRandomString();

	await page.getByLabel('Text 29').fill(formEntry);

	await page.getByRole('button', {name: 'Submit'}).click();

	await expect(
		page.getByText(
			'Your information was successfully received. Thank you for filling out the form.'
		)
	).toBeVisible();

	await formsPage.goTo();

	await formsPage.openForm('Untitled Form');

	await formBuilderPage.entriesTab.click();

	await page.locator('a').filter({hasText: 'Text 29'}).click();

	await expect(page.getByText(formEntry)).toBeVisible();
});
