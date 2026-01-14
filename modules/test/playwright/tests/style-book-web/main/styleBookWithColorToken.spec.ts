/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {pagesAdminPagesTest} from '../../../fixtures/pagesAdminPagesTest';
import getRandomString from '../../../utils/getRandomString';

const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest,
	pagesAdminPagesTest
);

test(
	'The reference will be autocompleted as 6 digit characters, if the user fill out 3 digit characters when clicking on the tab, intro or outside the output.',
	{tag: '@LPS-141568'},
	async ({page, pageEditorPage, pagesAdminPage, site}) => {
		const pageName = getRandomString();
		const backgroundColorSection = page.getByLabel('Background Color', {
			exact: true,
		});
		const backgroundColorInput =
			backgroundColorSection.getByRole('textbox');

		await test.step('Create a content page and add a container', async () => {
			await pagesAdminPage.goto(site.friendlyUrlPath);

			await pagesAdminPage.createNewPage({
				draft: true,
				name: pageName,
			});

			await pageEditorPage.addFragment('Layout Elements', 'Container');
		});

		await test.step('Change the background color and type a 3 digits color reference', async () => {
			await pageEditorPage.goToConfigurationTab('Styles');

			await backgroundColorInput.fill('#03C');
		});

		await test.step('Check if the color reference is autocompleted after trigger auto save', async () => {
			await pageEditorPage.goToConfigurationTab('Styles');

			await pageEditorPage.waitForChangesSaved();

			await expect(backgroundColorInput).toHaveValue('#0033CC');
		});

		await test.step('Assert the background color of the container', async () => {
			await expect(
				pageEditorPage.getFragment(
					await pageEditorPage.getFragmentId('Container')
				)
			).toHaveCSS('background-color', 'rgb(0, 51, 204)');
		});
	}
);
