/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {displayPageTemplatesPagesTest} from '../../../fixtures/displayPageTemplatesPagesTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {globalMenuPagesTest} from '../../../fixtures/globalMenuPagesTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {pagesAdminPagesTest} from '../../../fixtures/pagesAdminPagesTest';
import getRandomString from '../../../utils/getRandomString';
import {layoutSetPrototypePageTest} from './fixtures/layoutSetPrototypePageTest';

const test = mergeTests(
	displayPageTemplatesPagesTest,
	featureFlagsTest({
		'LPD-36105': {enabled: true},
	}),
	globalMenuPagesTest,
	layoutSetPrototypePageTest,
	pageEditorPagesTest,
	loginTest(),
	pagesAdminPagesTest
);

test(
	'If you set a layout-set-prototypes theme, then both private and public layoutSets theme settings is the same',
	{
		tag: '@LPD-44632',
	},
	async ({
		displayPageTemplatesPage,
		globalMenuPage,
		layoutSetPrototypePage,
		page,
		pagesAdminPage,
	}) => {

		// Create Site Template

		const siteTemplateName: string = getRandomString();

		await globalMenuPage.goToControlPanel('Site Templates');
		await layoutSetPrototypePage.addSiteTemplate(siteTemplateName);
		await globalMenuPage.goToControlPanel('Site Templates');

		// Go to Site Template Configuration and configure a new theme for public pages

		const siteTemplateFriendlyUrlPath =
			'/' +
			new URL(
				await layoutSetPrototypePage.getSiteTemplateUrl(
					siteTemplateName
				)
			).pathname
				.split('/')
				.at(-1);

		await pagesAdminPage.gotoPagesConfiguration(
			siteTemplateFriendlyUrlPath
		);

		await pagesAdminPage.page
			.getByRole('button', {name: 'Change Current Theme'})
			.click();

		await page
			.frameLocator('iframe[title="Available Themes"]')
			.getByRole('button', {name: 'Select Speedwell By Liferay,'})
			.click();

		await page
			.getByRole('button', {
				exact: true,
				name: 'Save',
			})
			.click();

		// Assert that the theme is applied to private pages too

		await displayPageTemplatesPage.goto(siteTemplateFriendlyUrlPath);

		const displayPageTemplateName = getRandomString();

		await displayPageTemplatesPage.createTemplate({
			contentSubtype: 'Basic Web Content',
			contentType: 'Web Content Article',
			name: displayPageTemplateName,
		});

		await page.getByRole('link', {name: displayPageTemplateName}).click();

		await expect(page.getByText('Terms and Conditions')).toBeVisible();
	}
);
