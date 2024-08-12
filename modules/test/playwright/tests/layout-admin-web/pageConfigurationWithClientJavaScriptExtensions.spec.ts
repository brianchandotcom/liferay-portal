/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pagesAdminPagesTest} from '../../fixtures/pagesAdminPagesTest';
import getRandomString from '../../utils/getRandomString';
import {pagesPagesTest} from './fixtures/pagesPagesTest';

const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	loginTest(),
	pagesAdminPagesTest,
	pagesPagesTest
);

test(
	'Inherited JS extensions from pages should be read-only mode',
	{
		tag: '@LPS-153658',
	},
	async ({apiHelpers, page, pageConfigurationPage, pagesAdminPage, site}) => {

		// Create a new JS client extension with a script element attribute

		const clientExtensionName = getRandomString();

		const clientExtension =
			await apiHelpers.jsonWebServicesClientExtension.addClientExtension({
				name: clientExtensionName,
				type: 'globalJS',
				url: 'https://www.example.com/script.js',
			});

		// Apply JS client extension to all pages

		await pagesAdminPage.selectJavaScriptClientExtension(
			clientExtensionName,
			site.friendlyUrlPath
		);

		// Create a layout

		const layoutTitle = getRandomString();

		await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			title: layoutTitle,
		});

		// Check inherited JS client extension is read-only

		await pagesAdminPage.goto(site.friendlyUrlPath);

		await pageConfigurationPage.goToSection(layoutTitle, 'Design');

		await pagesAdminPage.clickOnJavaScriptClientExtensionsTab();

		await expect(
			page
				.locator('.global-js-cets-configuration .table tbody .disabled')
				.getByText('From Pages')
		).toBeVisible();

		// Clean up

		await apiHelpers.jsonWebServicesClientExtension.deleteClientExtension(
			clientExtension.clientExtensionEntryId
		);
	}
);
