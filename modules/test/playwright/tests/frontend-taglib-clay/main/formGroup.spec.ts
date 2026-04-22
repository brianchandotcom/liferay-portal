/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect} from '@playwright/test';

import {claySamplePageTest} from './fixtures/claySamplePageTest';
import {TabName} from './pages/ClaySamplePage';

const test = claySamplePageTest;

test.beforeEach(async ({claySamplePage}) => {
	await claySamplePage.selectTab(TabName.FORM_ELEMENTS);
});

test('Default variant', async ({page}) => {
	await test.step('label should have correct font-size, line-height, font-weight and margin-bottom', async () => {
		const label = page.locator('label[for="inputDefault"]');

		await expect(label).toHaveCSS('font-size', '14px');

		await expect(label).toHaveCSS('line-height', '21px');

		await expect(label).toHaveCSS('font-weight', '600');

		await expect(label).toHaveCSS('margin-bottom', '4px');
	});

	await test.step('margin-bottom should be 24px', async () => {
		await expect(page.locator('#formGroupDefault')).toHaveCSS(
			'margin-bottom',
			'24px'
		);
	});

	await test.step('textarea label should have the same styles as input label', async () => {
		const label = page.locator('label[for="textareaDefault"]');

		await expect(label).toHaveCSS('font-size', '14px');
		await expect(label).toHaveCSS('margin-bottom', '4px');
	});

	await test.step('select label should have the same styles as input label', async () => {
		const label = page.locator('label[for="selectDefault"]');

		await expect(label).toHaveCSS('font-size', '14px');
		await expect(label).toHaveCSS('margin-bottom', '4px');
	});
});

test('Small variant', async ({page}) => {
	await test.step('label should have scaled font-size, line-height and margin-bottom', async () => {
		const label = page.locator('label[for="inputSm"]');

		await expect(label).toHaveCSS('font-size', '12px');

		await expect(label).toHaveCSS('line-height', '18px');

		await expect(label).toHaveCSS('margin-bottom', '2px');
	});

	await test.step('margin-bottom should be 16px', async () => {
		await expect(page.locator('#formGroupSm')).toHaveCSS(
			'margin-bottom',
			'16px'
		);
	});

	await test.step('textarea label should have the same scaled styles as input label', async () => {
		const label = page.locator('label[for="textareaSm"]');

		await expect(label).toHaveCSS('font-size', '12px');
		await expect(label).toHaveCSS('line-height', '18px');
		await expect(label).toHaveCSS('margin-bottom', '2px');
	});

	await test.step('select label should have the same scaled styles as input label', async () => {
		const label = page.locator('label[for="selectSm"]');

		await expect(label).toHaveCSS('font-size', '12px');
		await expect(label).toHaveCSS('line-height', '18px');
		await expect(label).toHaveCSS('margin-bottom', '2px');
	});
});
