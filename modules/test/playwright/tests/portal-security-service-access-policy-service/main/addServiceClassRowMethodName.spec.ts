/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../../fixtures/loginTest';
import {serviceAccessPolicyPageTest} from '../../../fixtures/serviceAccessPolicyPageTest';
import getRandomString from '../../../utils/getRandomString';

export const test = mergeTests(loginTest(), serviceAccessPolicyPageTest);

test('LPP-63887 Method Name accepts input after typing a Service Class in a newly added row', async ({
	editServiceAccessPolicyPage,
	page,
	serviceAccessPolicyPage,
}) => {
	const policyName = getRandomString();
	const serviceClassName = `com.example.${getRandomString()}`;
	const methodName = getRandomString();

	await serviceAccessPolicyPage.goto();

	await serviceAccessPolicyPage.newConfiguration();

	await editServiceAccessPolicyPage.nameField.fill(policyName);
	await editServiceAccessPolicyPage.titleField.fill(policyName);
	await editServiceAccessPolicyPage
		.serviceClassField(0)
		.fill('com.example.InitialService');

	await editServiceAccessPolicyPage.saveButton.click();

	await expect(editServiceAccessPolicyPage.successMessage).toBeVisible();

	try {
		await serviceAccessPolicyPage.goto();

		await page.getByRole('link', {name: policyName}).click();

		await editServiceAccessPolicyPage.addRowButton.click();

		await expect(
			editServiceAccessPolicyPage.methodNameField(1)
		).toBeDisabled();

		await editServiceAccessPolicyPage
			.serviceClassField(1)
			.fill(serviceClassName);

		await expect(
			editServiceAccessPolicyPage.methodNameField(1)
		).toBeEnabled();

		await editServiceAccessPolicyPage.methodNameField(1).fill(methodName);

		await editServiceAccessPolicyPage.saveButton.click();

		await expect(editServiceAccessPolicyPage.successMessage).toBeVisible();
	}
	finally {
		await serviceAccessPolicyPage.goto();

		const link = page.getByRole('link', {name: policyName});

		if ((await link.count()) > 0) {
			await link
				.locator('xpath=ancestor::tr[1]')
				.locator('.component-action.dropdown-toggle')
				.click();

			page.once('dialog', (dialog) => {
				dialog.accept();
			});

			await page.getByRole('link', {name: 'Delete'}).click();
		}
	}
});
