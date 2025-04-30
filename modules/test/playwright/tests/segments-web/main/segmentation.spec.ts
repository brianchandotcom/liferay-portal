/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../fixtures/loginTest';
import {productMenuPageTest} from '../../../fixtures/productMenuPageTest';
import {liferayConfig} from '../../../liferay.config';
import getRandomString from '../../../utils/getRandomString';

export const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	productMenuPageTest,
	loginTest()
);

const randomString = getRandomString();

const siteName = 'My Site ' + randomString;

let site;

test.beforeEach(async ({apiHelpers}) => {
	site = await apiHelpers.headlessSite.createSite({
		name: siteName,
	});
});

test.afterEach(async ({apiHelpers, page}) => {
	await test.step('Delete site on the DXP side', async () => {
		await page.goto(liferayConfig.environment.baseUrl);

		await apiHelpers.headlessSite.deleteSite(String(site.id));
	});
});

test(
	'Can understand the actions of keyboard from screen reader.',

	{
		tag: '@LPS-198108',
	},

	async ({page, productMenuPage}) => {
		await test.step('Given a segment designer accesses to the segment editor', async () => {
			await productMenuPage.openProductMenuIfClosed();

			await productMenuPage.goToSegments();

			await page
				.getByRole('link', {name: 'Add New User Segment'})
				.click();
		});

		const searchButton = page.getByTestId('search-button');
		const userTab = page.getByRole('button', {exact: true, name: 'User'});

		await test.step('When the segment designer focuses on the sidebar item via keyboard', async () => {
			await searchButton.focus();

			await searchButton.press('Tab');

			await expect(userTab).toBeFocused();
		});

		const categoryField = page
			.getByRole('application')
			.getByText('Category');
		const categoryPropertie = page.getByRole('menuitem', {
			name: 'Drag Category',
		});
		const dateModifiedField = page
			.getByRole('application')
			.getByText('Date Modified');
		const dateModifiedPropertie = page.getByRole('menuitem', {
			name: 'Drag Date Modified',
		});

		await test.step('When the segment designer drags the sidebar itens via keyboard', async () => {
			await userTab.press('ArrowDown');

			await expect(categoryPropertie).toBeFocused();

			await categoryPropertie.press('Enter');
			await categoryPropertie.press('Enter');

			await expect(
				page.getByText(
					'Category placed on middle of item 0 of group root.'
				)
			).toBeHidden();
			await expect(categoryField).toBeVisible();

			await categoryPropertie.focus();

			await categoryPropertie.press('ArrowDown');

			await expect(dateModifiedPropertie).toBeFocused();

			await dateModifiedPropertie.press('Enter');
			await dateModifiedPropertie.press('Enter');

			await expect(
				page.getByText(
					'Date Modified placed on bottom of item 0 of group 3.'
				)
			).toBeHidden();
			await expect(dateModifiedField).toBeVisible();
		});

		const viewMembersButton = page.getByRole('button', {
			name: 'View Members',
		});
		const firstDragIcon = page.locator('.drag-icon').first();

		await test.step('When the segment designer drags the sidebar itens via keyboard', async () => {
			await viewMembersButton.focus();

			await viewMembersButton.press('Tab');

			await expect(firstDragIcon).toBeFocused();

			const categoryFieldBox1 = await categoryField.boundingBox();
			const dateModifiedFieldBox1 = await dateModifiedField.boundingBox();

			await firstDragIcon.press('Enter');

			await firstDragIcon.press('ArrowDown');

			await expect(
				page.getByText('Targeting bottom of item 1 of group 3.')
			).toBeHidden();

			await firstDragIcon.press('Enter');

			await expect(
				page.getByText(
					'Category placed on middle of item 1 of group 3.'
				)
			).toBeHidden();

			const categoryFieldBox2 = await categoryField.boundingBox();
			const dateModifiedFieldBox2 = await dateModifiedField.boundingBox();

			expect(categoryFieldBox1).not.toBe(categoryFieldBox2);
			expect(dateModifiedFieldBox1).not.toBe(dateModifiedFieldBox2);
		});
	}
);
