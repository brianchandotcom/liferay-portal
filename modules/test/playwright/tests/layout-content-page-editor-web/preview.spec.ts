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
import getFragmentDefinition from './utils/getFragmentDefinition';
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

		// Create page with a Heading fragment and go to edit mode

		const headingId = getRandomString();

		const headingFragment = getFragmentDefinition({
			id: headingId,
			key: 'BASIC_COMPONENT-heading',
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([headingFragment]),
			siteId: site.id,
			title: getRandomString(),
		});

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		// Edit Heading text

		await pageEditorPage.editTextEditable(
			headingId,
			'element-text',
			'New editable fragment text'
		);

		await expect(
			page.getByText('New editable fragment text')
		).toBeVisible();

		await pageEditorPage.waitForChangesSaved();

		// Preview in a new tab

		const pagePromise = context.waitForEvent('page');

		const previewButton = page.getByRole('menuitem', {
			name: 'Preview in a New Tab',
		});

		await expect(async () => {
			await clickAndExpectToBeVisible({
				target: previewButton,
				trigger: page
					.locator('.control-menu-nav-item')
					.getByLabel('Options', {exact: true}),
			});

			if (await previewButton.isVisible()) {
				await previewButton.click();
			}

			const newPage = await pagePromise;

			await expect(
				newPage.getByText('New editable fragment text')
			).toBeVisible({timeout: 100});
		}).toPass();
	}
);

test(
	'The page creator could preview localized changes in a new tab',
	{
		tag: ['@LPS-139064', '@LPS-153367'],
	},
	async ({apiHelpers, context, page, pageEditorPage, site}) => {

		// Create a page with a Heading fragment

		const headingId = getRandomString();

		const headingDefinition = getFragmentDefinition({
			id: headingId,
			key: 'BASIC_COMPONENT-heading',
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([headingDefinition]),
			siteId: site.id,
			title: getRandomString(),
		});

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		// Localize heading fragment

		await pageEditorPage.switchLanguage('es-ES');

		await pageEditorPage.editTextEditable(
			headingId,
			'element-text',
			'Texto Editado'
		);

		await expect(page.getByText('Texto Editado')).toBeVisible();

		await pageEditorPage.waitForChangesSaved();

		// Preview in a new tab

		const pagePromise = context.waitForEvent('page');

		const previewButton = page.getByRole('menuitem', {
			name: 'Preview in a New Tab',
		});

		await expect(async () => {
			await clickAndExpectToBeVisible({
				target: previewButton,
				trigger: page
					.locator('.control-menu-nav-item')
					.getByLabel('Options', {exact: true}),
			});

			if (await previewButton.isVisible()) {
				await previewButton.click();
			}

			const newPage = await pagePromise;

			await expect(newPage.getByText('Texto')).toBeVisible({
				timeout: 100,
			});
		}).toPass();
	}
);
