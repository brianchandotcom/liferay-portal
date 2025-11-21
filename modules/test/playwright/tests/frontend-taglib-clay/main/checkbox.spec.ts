/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, expect, mergeTests} from '@playwright/test';

import {claySamplePageTest} from './fixtures/claySamplePageTest';
import {TabName} from './pages/ClaySamplePage';

async function expectToBeIndeterminate(checkbox: Locator) {
	await expect(checkbox).toHaveJSProperty('indeterminate', true);
}

const test = mergeTests(claySamplePageTest);

test.beforeEach(async ({claySamplePage}) => {
	await claySamplePage.selectTab(TabName.FORM_ELEMENTS);
});

test('Asserts checkboxes are working correctly', async ({page}) => {
	function getCheckboxByLabel(name: string) {
		return page.getByRole('checkbox', {exact: true, name});
	}

	await test.step('Assert default values can be configured', async () => {
		await expect(getCheckboxByLabel('On')).toBeChecked();
		await expect(getCheckboxByLabel('Off')).not.toBeChecked();

		const checkboxWithCustomClassAndId = getCheckboxByLabel(
			'With custom class and id'
		);

		await expect(checkboxWithCustomClassAndId).toBeEditable();
		await expect(checkboxWithCustomClassAndId).toBeChecked();
		await expect(checkboxWithCustomClassAndId).toHaveClass(
			/custom-css-class/
		);

		await checkboxWithCustomClassAndId.uncheck();

		await expect(checkboxWithCustomClassAndId).not.toBeChecked();
	});

	await test.step('Assert label can be configured', async () => {
		const checkboxWithLabel = getCheckboxByLabel('Checkbox with Label');

		await expect(checkboxWithLabel).toBeEditable();
		await expect(checkboxWithLabel).not.toBeChecked();
	});

	await test.step('Assert checkboxes can be disabled', async () => {
		const checkedCheckbox = getCheckboxByLabel('On disabled');

		await expect(checkedCheckbox).toBeDisabled();
		await expect(checkedCheckbox).not.toBeEditable();

		const uncheckedCheckbox = getCheckboxByLabel('Off disabled');

		await expect(uncheckedCheckbox).toBeDisabled();
		await expect(uncheckedCheckbox).not.toBeEditable();
	});

	await test.step('Assert checkboxes can be in indeterminate state', async () => {
		const indeterminateCheckbox = getCheckboxByLabel('Indeterminate');

		await expect(indeterminateCheckbox).toBeEnabled();
		await expect(indeterminateCheckbox).toBeEditable();
		await expectToBeIndeterminate(indeterminateCheckbox);

		await indeterminateCheckbox.click();

		await expect(indeterminateCheckbox).toBeChecked();
	});

	await test.step('TreeView parent must be checked if all children are checked', async () => {
		const root = getCheckboxByLabel('Liferay DXP');

		await expectToBeIndeterminate(root);

		await getCheckboxByLabel('Content & Data').click({clickCount: 2});
		await getCheckboxByLabel('Categorization').click();

		await expect(root).toBeChecked();
	});

	await test.step('TreeView parent must be indeterminate if some children are checked', async () => {
		const root = getCheckboxByLabel('Design');

		await expect(root).toBeChecked();

		await getCheckboxByLabel('Fragments').uncheck();

		await expectToBeIndeterminate(root);
	});

	await test.step('TreeView parent must be unchecked if no children are checked', async () => {
		const root = getCheckboxByLabel('Design');

		await getCheckboxByLabel('Style Books').uncheck();

		await expect(root).not.toBeChecked();
	});
});
