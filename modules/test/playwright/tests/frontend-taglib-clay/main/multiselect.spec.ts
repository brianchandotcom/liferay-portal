/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {claySamplePageTest} from './fixtures/claySamplePageTest';
import {TabName} from './pages/ClaySamplePage';

type Role = Parameters<Page['getByRole']>[0];
type TestCase = {
	label: string;
	menuRole: Role;
	optionRole: Role;
	optionValue: string;
};

const test = mergeTests(claySamplePageTest);

test.beforeEach(async ({claySamplePage}) => {
	await claySamplePage.selectTab(TabName.FORM_ELEMENTS);
});

test('Asserts Multiselect is working correctly', async ({page}) => {
	await test.step('Items are added and removed correctly', async () => {
		const testCases: Array<TestCase> = [
			{
				label: 'Multiselect 1',
				menuRole: 'listbox',
				optionRole: 'option',
				optionValue: 'three',
			},
			{
				label: 'Multiselect with Custom Menu Renderer',
				menuRole: 'menu',
				optionRole: 'menuitem',
				optionValue: 'four',
			},
		];

		for (const {label, menuRole, optionRole, optionValue} of testCases) {
			await page.getByRole('combobox', {name: label}).fill(optionValue);

			const menu = page.getByRole(menuRole);

			const menuOption = menu.getByRole(optionRole, {name: optionValue});

			const selectedOption = page.getByRole('gridcell', {
				exact: true,
				name: optionValue,
			});

			await expect(menu).toBeVisible();
			await expect(menuOption).toBeVisible();
			await expect(selectedOption).toBeHidden();

			await menuOption.click();

			await expect(selectedOption).toBeVisible();

			const selectedOptionRemove = page.getByRole('gridcell', {
				exact: true,
				name: `Remove ${optionValue}`,
			});

			await selectedOptionRemove.click();

			await expect(selectedOption).toBeHidden();
		}
	});
});
