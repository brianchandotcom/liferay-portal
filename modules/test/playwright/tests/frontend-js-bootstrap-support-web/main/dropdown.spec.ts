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
	'Check dropdown disappears after tabbing through',
	{tag: '@LPD-55650'},
	async ({apiHelpers, page, pageEditorPage, site}) => {
		const dropdownHTML =
			'<div class="row">\n' +
			'    <div class="col">\n' +
			'        <button aria-expanded="false" aria-haspopup="true" class="mr-5 dropdown-toggle nav-link"\n' +
			'            data-toggle="liferay-dropdown" id="t_dropdown_menu" type="button">\n' +
			'            <span class="font-weight-bold ml-1">Menu 1</span>\n' +
			'        </button>\n' +
			'\n' +
			'        <ul aria-labelledby="t_dropdown_menu" class="dropdown-menu right">\n' +
			'            <li>\n' +
			'                <a class="dropdown-item" href="javascript:void(0)">\n' +
			'                    My Learning\n' +
			'                </a>\n' +
			'            </li>\n' +
			'        </ul>\n' +
			'    </div>\n' +
			'    <div class="col">\n' +
			'        <button aria-expanded="false" aria-haspopup="true" class="dropdown-toggle nav-link"\n' +
			'            data-toggle="liferay-dropdown" id="t_dropdown_menu" type="button">\n' +
			'            <span class="font-weight-bold ml-1">Menu 2</span>\n' +
			'        </button>\n' +
			'        <ul aria-labelledby="t_dropdown_menu" class="dropdown-menu right">\n' +
			'            <li>\n' +
			'                <a class="dropdown-item" href="javascript:void(0)">\n' +
			'                    My Learning\n' +
			'                </a>\n' +
			'            </li>\n' +
			'        </ul>\n' +
			'    </div>\n' +
			'</div>';

		const layoutTitle = getRandomString();

		const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			options: {type: 'content'},
			title: layoutTitle,
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

		const menu1Button = page.getByRole('button', {name: 'Menu 1'});

		await menu1Button.click();

		await page.keyboard.press('Tab');

		const myLearningDropdown = page.getByRole('link', {
			name: 'My Learning',
		});

		await expect(myLearningDropdown).toBeVisible();

		await page.keyboard.press('Tab');

		await expect(myLearningDropdown).not.toBeVisible();
	}
);
