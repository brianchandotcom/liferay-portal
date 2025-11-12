/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {changeTrackingPagesTest} from '../../../fixtures/changeTrackingPagesTest';
import getRandomString from '../../../utils/getRandomString';
import {waitForAlert} from '../../../utils/waitForAlert';

export const test = mergeTests(changeTrackingPagesTest);

test('Can go to create a new publication after deleting default template', async ({
	changeTrackingPage,
	changeTrackingTemplatesPage,
}) => {
	await changeTrackingTemplatesPage.gotoCreateTemplate();

	const templateName = getRandomString();

	await changeTrackingTemplatesPage.addTemplate(templateName, true);

	await changeTrackingTemplatesPage.deleteTemplate(templateName);

	await changeTrackingPage.goToAddPublication();
});

test('LPD-67882 Assert Publication Templates dropdown menu', async ({
	changeTrackingTemplatesPage,
	page,
}) => {
	await changeTrackingTemplatesPage.gotoCreateTemplate();

	const templateName = getRandomString();

	await changeTrackingTemplatesPage.addTemplate(templateName);

	await waitForAlert(page, 'Success:Successfully added the template.');

	await page
		.getByRole('row', {name: templateName})
		.getByRole('button')
		.click();

	const dropdownMenu = page.getByRole('menu');

	await expect(dropdownMenu).toBeVisible();

	const dropdownMenuItems = await dropdownMenu
		.locator('li')
		.allTextContents();

	const expectedItems = ['Edit', 'Permissions', 'Delete'];

	expect(dropdownMenuItems.filter(Boolean)).toEqual(expectedItems);
});
