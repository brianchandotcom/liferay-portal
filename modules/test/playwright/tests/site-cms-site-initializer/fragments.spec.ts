/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import getRandomString from '../../utils/getRandomString';
import {cmsPagesTest} from './fixtures/cmsPagesTest';

const test = mergeTests(
	apiHelpersTest,
	cmsPagesTest,
	loginTest(),
	pageEditorPagesTest,
	dataApiHelpersTest
);

test.describe('Space List Fragment CMS', () => {
	test(
		'Check the functionality of the Space List fragment CMS',
		{tag: ['@LPD-52223']},
		async ({apiHelpers, page, pageEditorPage}) => {

			// Create site using CMS template

			const site = await apiHelpers.headlessSite.createSite({
				name: getRandomString(),
				templateKey: 'com.liferay.site.initializer.cms',
				templateType: 'site-initializer',
			});

			apiHelpers.data.push({id: site.id, type: 'site'});

			// Create a content page and go to edit mode

			const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
				groupId: site.id,
				options: {type: 'content'},
				title: getRandomString(),
			});

			await pageEditorPage.goto(layout, site.friendlyUrlPath);

			// Add a Space List fragment

			await pageEditorPage.addFragment(
				'CMS Fragments',
				'Space List',
				page.getByText('Drag and drop fragments or widgets here.', {
					exact: true,
				})
			);

			// Check the default Space List fragment configuration

			await expect(page.locator('.space-list-fragment')).toBeVisible();

			await expect(page.locator('.space-list-title-text')).toHaveText(
				'Space'
			);

			await expect(
				page.locator('.space-list-name .sticker-overlay')
			).toHaveText('S');

			await expect(
				page.locator('.space-list-name').locator('span').last()
			).toHaveText('Space Name');

			// Configure Space List fragment name

			const spaceListId =
				await pageEditorPage.getFragmentId('Space List');

			await pageEditorPage.changeFragmentConfiguration({
				fieldLabel: 'space-name',
				fragmentId: spaceListId,
				tab: 'General',
				value: 'Updated Name',
			});

			await expect(
				page.locator('.space-list-name .sticker-overlay')
			).toHaveText('U');

			await expect(
				page.locator('.space-list-name').locator('span').last()
			).toHaveText('Updated Name');
		}
	);
});
