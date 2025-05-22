/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {changeTrackingPagesTest} from '../../../fixtures/changeTrackingPagesTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {masterPagesPagesTest} from '../../../fixtures/masterPagesPagesTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {pageTemplatesPagesTest} from '../../../fixtures/pageTemplatesPagesTest';
import getRandomString from '../../../utils/getRandomString';

export const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	changeTrackingPagesTest,
	isolatedSiteTest,
	masterPagesPagesTest,
	pageEditorPagesTest,
	pageTemplatesPagesTest
);

test(
	'Keyboard interactions',
	{tag: '@LPD-55650'},
	async ({apiHelpers, page, pageEditorPage, site}) => {
		const dropdownHTML =
			'<div id="sampleDropdownContainer">\n' +
			'    <button class="dropdown-toggle" data-toggle="liferay-dropdown">\n' +
			'        Sample Menu\n' +
			'    </button>\n' +
			'    <ul class="dropdown-menu">\n' +
			'        <li>\n' +
			'           <a class="dropdown-item" href="#">\n' +
			'                Sample Item 1\n' +
			'           </a>\n' +
			'        </li>\n' +
			'        <li>\n' +
			'           <div>\n' +
			'               <a class="dropdown-item" href="#">\n' +
			'                    Sample Item 2\n' +
			'               </a>\n' +
			'           </div>\n' +
			'        </li>\n' +
			'        <li>\n' +
			'           <a class="dropdown-item" href="#">\n' +
			'                Sample Item 3\n' +
			'           </a>\n' +
			'        </li>\n' +
			'    </ul>\n' +
			'    <a href="#">\n' +
			'        Next Interactable Element \n' +
			'    </a>' +
			'</div>';

		await test.step('Prepare HTML example of dropdown markup', async () => {
			const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
				groupId: site.id,
				options: {type: 'content'},
				title: getRandomString(),
			});

			await pageEditorPage.goto(layout, site.friendlyUrlPath);

			await pageEditorPage.addFragment('Basic Components', 'HTML');

			const htmlFragmentId = await pageEditorPage.getFragmentId('HTML');

			await pageEditorPage.selectEditable(htmlFragmentId, 'element-html');

			await page.getByText('HTML Example').click();

			const htmlEditor = page.getByText('<h1>HTML Example</h1>');

			await htmlEditor.click();

			await page.keyboard.press('Control+A');

			await page.keyboard.press('Backspace');

			await page.keyboard.insertText(dropdownHTML);

			await page.getByRole('button', {name: 'Save'}).click();

			await pageEditorPage.waitForChangesSaved();

			await pageEditorPage.publishPage();

			await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyURL}`);
		});

		const container = page.locator('#sampleDropdownContainer');

		const trigger = container.getByRole('button', {name: 'Sample Menu'});
		const menu = container.locator('.dropdown-menu');
		const firstMenuItem = container.getByRole('link', {
			name: 'Sample Item 1',
		});
		const secondMenuItem = container.getByRole('link', {
			name: 'Sample Item 2',
		});
		const thirdMenuItem = container.getByRole('link', {
			name: 'Sample Item 3',
		});
		const nextInteractableElement = container.getByText(
			'Next Interactable Element'
		);

		await test.step('Open menu with ArrowDown key', async () => {
			await trigger.focus();

			await page.keyboard.press('ArrowDown');

			await expect(menu).toBeVisible();

			await expect(firstMenuItem).toBeFocused();
		});

		await test.step('Close menu with Escape key', async () => {
			await page.keyboard.press('Escape');

			await expect(menu).toBeHidden();

			await expect(trigger).toBeFocused();
		});

		await test.step('Open menu with Space key', async () => {
			await trigger.focus();

			await page.keyboard.press('Space');

			await expect(menu).toBeVisible();

			await expect(firstMenuItem).toBeFocused();
		});

		await test.step('Navigate menu items with Arrow keys', async () => {
			await page.keyboard.press('ArrowDown');

			await expect(secondMenuItem).toBeFocused();

			await page.keyboard.press('ArrowDown');

			await expect(thirdMenuItem).toBeFocused();

			await page.keyboard.press('ArrowDown');

			await expect(firstMenuItem).toBeFocused();

			await page.keyboard.press('ArrowUp');

			await expect(thirdMenuItem).toBeFocused();

			await page.keyboard.press('ArrowUp');

			await expect(secondMenuItem).toBeFocused();

			await page.keyboard.press('ArrowUp');

			await expect(firstMenuItem).toBeFocused();
		});

		await test.step('Close menu and navigate away with Tab key', async () => {
			await page.keyboard.press('Tab');

			await expect(nextInteractableElement).toBeFocused();

			await expect(menu).toBeHidden();
		});
	}
);
