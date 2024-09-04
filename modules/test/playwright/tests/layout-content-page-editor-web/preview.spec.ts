/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../utils/getRandomString';
import getPageDefinition from './utils/getPageDefinition';

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest
);

test(
	'The page creator could preview changes in a new tab',
	{
		tag: '@LPS-153367',
	},
	async ({apiHelpers, context, page, pageEditorPage, site}) => {

		// Create page and go to edit mode

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition(),
			siteId: site.id,
			title: getRandomString(),
		});

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		// Add heading fragment

		await pageEditorPage.addFragment('Basic Components', 'Heading');

		const headingId = await pageEditorPage.getFragmentId('Heading');

		await pageEditorPage.editTextEditable(
			headingId,
			'element-text',
			'New editable fragment text'
		);

		await expect(
			page.getByText('New editable fragment text')
		).toBeVisible();

		// Preview in a new tab

		const pagePromise = context.waitForEvent('page');

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('menuitem', {
				name: 'Preview in a New Tab',
			}),
			trigger: page
				.locator('.control-menu-nav-item')
				.getByLabel('Options', {exact: true}),
		});

		const newPage = await pagePromise;

		await expect(
			newPage.getByText('New editable fragment text')
		).toBeVisible();
	}
);
