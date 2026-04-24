/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {commercePagesTest} from '../../../../fixtures/commercePagesTest';
import {dataApiHelpersTest} from '../../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../../fixtures/loginTest';
import {getRandomInt} from '../../../../utils/getRandomInt';
import getRandomString from '../../../../utils/getRandomString';
import {waitForAlert} from '../../../../utils/waitForAlert';

export const test = mergeTests(
	commercePagesTest,
	dataApiHelpersTest,
	loginTest()
);

test(
	'Cancel the creation of a new entry',
	{tag: ['@COMMERCE-8286', '@LPD-85008']},
	async ({termsAndConditionsPage}) => {
		const name = `Test Term ${getRandomString()}`;

		await termsAndConditionsPage.goto();

		await termsAndConditionsPage.addButton.click();

		await termsAndConditionsPage.modalNameInput.fill(name);
		await termsAndConditionsPage.modalTypeSelect.selectOption({
			label: 'Payment Terms',
		});
		await termsAndConditionsPage.modalCancelButton.click();

		await expect(termsAndConditionsPage.modalNameInput).not.toBeVisible();
		await expect(termsAndConditionsPage.entryLink(name)).not.toBeVisible();
	}
);

test(
	'Delete a payment term',
	{tag: ['@COMMERCE-8281', '@LPD-85008']},
	async ({apiHelpers, page, termsAndConditionsPage}) => {
		page.on('dialog', (dialog) => dialog.accept());

		const name = `Test Term ${getRandomString()}`;

		await apiHelpers.headlessCommerceAdminOrder.postTerm({
			active: true,
			label: {en_US: name},
			name,
			priority: 1,
			type: 'standard-payment-term',
		});

		await termsAndConditionsPage.goto();

		await expect(termsAndConditionsPage.entryLink(name)).toBeVisible();

		await expect(async () => {
			await termsAndConditionsPage
				.entryRowActionsButton(name)
				.click({force: true});

			await expect(termsAndConditionsPage.menuDeleteItem).toBeVisible({
				timeout: 100,
			});
		}).toPass({timeout: 5000});

		await termsAndConditionsPage.menuDeleteItem.click();

		await waitForAlert(page);

		await expect(termsAndConditionsPage.entryLink(name)).not.toBeVisible();
	}
);

test(
	'Add and Edit a payment term',
	{tag: ['@COMMERCE-8282', '@LPD-85008']},
	async ({page, termsAndConditionDetailsPage, termsAndConditionsPage}) => {
		await termsAndConditionsPage.goto();

		const name = `Test Term ${getRandomString()}`;

		await termsAndConditionsPage.addEntry({
			name,
			priority: getRandomInt(),
			type: 'Payment Terms',
		});

		await termsAndConditionDetailsPage.backButton.click();

		await termsAndConditionsPage.entryLink(name).click();

		const newName = `${name} Edited`;

		await termsAndConditionDetailsPage.nameInput.fill(newName);
		await termsAndConditionDetailsPage.publishButton.click();

		await waitForAlert(page);

		await termsAndConditionsPage.goto();

		await expect(termsAndConditionsPage.entryLink(newName)).toBeVisible();

		await termsAndConditionsPage.entryLink(newName).click();

		await expect(termsAndConditionDetailsPage.nameInput).toHaveValue(
			newName
		);

		await termsAndConditionDetailsPage.backButton.click();

		await expect(async () => {
			await termsAndConditionsPage
				.entryRowActionsButton(name)
				.click({force: true});

			await expect(termsAndConditionsPage.menuDeleteItem).toBeVisible({
				timeout: 100,
			});
		}).toPass({timeout: 5000});

		await termsAndConditionsPage.menuDeleteItem.click();

		await waitForAlert(page);
	}
);

test(
	'Search for a specific entry',
	{tag: ['@COMMERCE-8291', '@LPD-85008']},
	async ({apiHelpers, termsAndConditionsPage}) => {
		const name1 = `Terms1 ${getRandomString()}`;
		const name2 = `Terms2 ${getRandomString()}`;

		await apiHelpers.headlessCommerceAdminOrder.postTerm({
			active: true,
			label: {en_US: name1},
			name: name1,
			priority: 1,
			type: 'standard-payment-term',
		});
		await apiHelpers.headlessCommerceAdminOrder.postTerm({
			active: true,
			label: {en_US: name2},
			name: name2,
			priority: 2,
			type: 'standard-payment-term',
		});

		await termsAndConditionsPage.goto();

		await termsAndConditionsPage.searchEntry(name1);

		await expect(termsAndConditionsPage.entryLink(name1)).toBeVisible();
		await expect(termsAndConditionsPage.entryLink(name2)).not.toBeVisible();

		await termsAndConditionsPage.searchEntry(name2);

		await expect(termsAndConditionsPage.entryLink(name1)).not.toBeVisible();
		await expect(termsAndConditionsPage.entryLink(name2)).toBeVisible();

		await termsAndConditionsPage.searchEntry(getRandomString());

		await expect(termsAndConditionsPage.entryLink(name1)).not.toBeVisible();
		await expect(termsAndConditionsPage.entryLink(name2)).not.toBeVisible();

		await termsAndConditionsPage.searchEntry('');

		await expect(termsAndConditionsPage.entryLink(name1)).toBeVisible();
		await expect(termsAndConditionsPage.entryLink(name2)).toBeVisible();
	}
);

test(
	'Use localization on a payment term',
	{tag: ['@COMMERCE-8284', '@LPD-85008']},
	async ({
		apiHelpers,
		page,
		termsAndConditionDetailsPage,
		termsAndConditionsPage,
	}) => {
		const name = `Test Term ${getRandomString()}`;

		await apiHelpers.headlessCommerceAdminOrder.postTerm({
			active: true,
			label: {en_US: name},
			name,
			priority: 1,
			type: 'standard-payment-term',
		});

		await termsAndConditionsPage.goto();

		await termsAndConditionsPage.entryLink(name).click();

		const localizedName = '测试条款和条件';

		await termsAndConditionDetailsPage.changeNameInputLocale('Chinese');
		await termsAndConditionDetailsPage.nameInput.fill(localizedName);
		await termsAndConditionDetailsPage.publishButton.click();

		await waitForAlert(page);

		await termsAndConditionDetailsPage.changeNameInputLocale('Default');

		await expect(termsAndConditionDetailsPage.nameInput).toHaveValue(name);

		await termsAndConditionDetailsPage.changeNameInputLocale('Chinese');

		await expect(termsAndConditionDetailsPage.nameInput).toHaveValue(
			localizedName
		);
	}
);

test(
	'Change the status of a payment term',
	{tag: ['@LPD-85008']},
	async ({
		apiHelpers,
		page,
		termsAndConditionDetailsPage,
		termsAndConditionsPage,
	}) => {
		const name = `Test Term ${getRandomString()}`;

		await apiHelpers.headlessCommerceAdminOrder.postTerm({
			active: true,
			label: {en_US: name},
			name,
			priority: 1,
			type: 'standard-payment-term',
		});

		await termsAndConditionsPage.goto();

		await termsAndConditionsPage.entryLink(name).click();

		await termsAndConditionDetailsPage.setActive(false);
		await termsAndConditionDetailsPage.publishButton.click();

		await waitForAlert(page);

		await termsAndConditionsPage.goto();

		await expect(
			termsAndConditionsPage.entryRowStatusText(name)
		).toContainText('No');

		await termsAndConditionsPage.entryLink(name).click();

		await termsAndConditionDetailsPage.setActive(true);
		await termsAndConditionDetailsPage.publishButton.click();

		await waitForAlert(page);

		await termsAndConditionsPage.goto();

		await expect(
			termsAndConditionsPage.entryRowStatusText(name)
		).toContainText('Yes');
	}
);
