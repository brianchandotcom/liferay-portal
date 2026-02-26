/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {globalMenuPagesTest} from '../../../fixtures/globalMenuPagesTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {pagesAdminPagesTest} from '../../../fixtures/pagesAdminPagesTest';
import {productMenuPageTest} from '../../../fixtures/productMenuPageTest';
import {sitesPageTest} from '../../../fixtures/sitesPageTest';
import {uiElementsPageTest} from '../../../fixtures/uiElementsTest';
import getRandomString from '../../../utils/getRandomString';
import {reloadUntilVisible} from '../../../utils/reloadUntilVisible';
import createSiteTemplate from './utils/createSiteTemplate';

export const test = mergeTests(
	globalMenuPagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-36105': {enabled: true},
	}),
	loginTest(),
	pageEditorPagesTest,
	pagesAdminPagesTest,
	productMenuPageTest,
	sitesPageTest,
	uiElementsPageTest
);

test(
	'Can not edit Pages created with Site Templates if the checkbox of allowing edition is disabled',
	{tag: ['@LPD-49053', '@LPS-131903', '@LPS-132256']},
	async ({
		apiHelpers,
		globalMenuPage,
		page,
		pageEditorPage,
		pagesAdminPage,
		productMenuPage,
		sitesPage,
		uiElementsPage,
	}) => {

		// Create a site template with the checkbox of allowing edition (layoutsUpdateable) disabled

		const siteTemplateName: string = 'SiteTemplate-' + getRandomString();

		const layoutSetPrototype = await createSiteTemplate({
			apiHelpers,
			layoutsUpdateable: false,
			page,
			productMenuPage,
			templateName: siteTemplateName,
		});

		apiHelpers.data.push({
			id: layoutSetPrototype.layoutSetPrototypeId,
			type: 'layoutSetPrototype',
		});

		// Create and publish a page in the site template

		const pageName: string = 'Page-' + getRandomString();

		await productMenuPage.goToPages();
		await pagesAdminPage.clickNewButtonAndWaitForBlankTemplate();
		await pagesAdminPage.addPage({
			name: pageName,
		});

		await uiElementsPage.publishButton.click();

		// Create a site using the site template

		await globalMenuPage.goToControlPanel('Sites');

		const siteName: string = 'Site-' + getRandomString();

		const siteId = await sitesPage.createSite({
			isCustom: true,
			siteName,
			templateName: siteTemplateName,
		});

		apiHelpers.data.push({id: siteId, type: 'site'});

		// Check the Edit button of the dropdown is not visible

		await productMenuPage.goToPages();

		const dropdown = page
			.locator('.miller-columns-item', {
				hasText: pageName,
			})
			.locator('button.dropdown-toggle');

		await reloadUntilVisible({
			myLocator: dropdown,
			page,
		});

		await dropdown.click();

		await expect(page.getByRole('menuitem', {name: 'Edit'})).toBeHidden();

		// Check the Page link opens the view mode

		const href = await page
			.getByLabel(pageName, {exact: true})
			.getAttribute('href');

		await page.goto(href);

		expect(await page.title()).toBe(
			`${pageName} - ${siteName} - Liferay DXP`
		);

		await page
			.locator('.control-menu-nav-item')
			.getByRole('button', {name: 'Additional Information'})
			.click();

		await expect(
			page
				.locator('.popover-body')
				.getByText(
					'This page is linked to a site template which does not allow modifications to it.',
					{exact: true}
				)
		).toBeVisible();

		// Check the Edit button is not visible in the view mode

		await expect(
			page
				.locator('.control-menu-nav-item')
				.getByLabel('Edit', {exact: true})
		).toBeHidden();

		// Check going to the edit mode redirects to the view mode

		const layout = await apiHelpers.headlessDelivery.getSitePage(
			pageName,
			siteId
		);

		await pageEditorPage.goto(layout, `/${siteName.toLowerCase()}`);

		expect(await page.title()).toBe(
			`${pageName} - ${siteName} - Liferay DXP`
		);
	}
);

test(
	'User cannot create child pages for pages derived from a site template',
	{tag: '@LPD-70284'},
	async ({
		apiHelpers,
		globalMenuPage,
		page,
		pagesAdminPage,
		productMenuPage,
		sitesPage,
		uiElementsPage,
	}) => {

		// Create site template

		const siteTemplateName = 'SiteTemplate-' + getRandomString();

		const layoutSetPrototype = await createSiteTemplate({
			apiHelpers,
			layoutsUpdateable: false,
			page,
			productMenuPage,
			templateName: siteTemplateName,
		});

		apiHelpers.data.push({
			id: layoutSetPrototype.layoutSetPrototypeId,
			type: 'layoutSetPrototype',
		});

		// Add new template page

		const templatePageName = 'Test Page-' + getRandomString();
		await productMenuPage.goToPages();
		await pagesAdminPage.newTemplatePageButton.click();
		await pagesAdminPage.addPage({name: templatePageName});
		await uiElementsPage.publishButton.click();
		await expect(
			page.getByLabel(templatePageName, {exact: true})
		).toBeVisible();

		// Create site based on that template

		await globalMenuPage.goToControlPanel('Sites');

		const siteName = 'Site-' + getRandomString();

		const siteId = await sitesPage.createSite({
			isCustom: true,
			siteName,
			templateName: siteTemplateName,
		});

		apiHelpers.data.push({id: siteId, type: 'site'});

		// Add new page on created site

		await expect(async () => {
			await productMenuPage.goToPages();

			await expect(
				page.getByLabel(templatePageName, {exact: true})
			).toBeVisible();
		}).toPass();

		const pageName = 'Test Page-' + getRandomString();
		await pagesAdminPage.clickNewButtonAndWaitForBlankTemplate();
		await pagesAdminPage.addPage({
			name: pageName,
		});

		await uiElementsPage.publishButton.click();

		// Check that for the page created on template child pages cannot be added

		await productMenuPage.goToPages();

		const pageCreatedOnTemplate =
			pagesAdminPage.getPageMenuItem(templatePageName);

		await expect(pageCreatedOnTemplate).toBeVisible();
		await expect(
			pageCreatedOnTemplate.getByLabel('Add Child Page')
		).toHaveCount(0);

		// Check that for the page created on site child pages can be added

		await expect(page.getByLabel(pageName, {exact: true})).toBeVisible();

		const childPageName = 'Child Page-' + getRandomString();

		await pagesAdminPage.createNewPage({
			name: childPageName,
			parent: pageName,
		});

		await productMenuPage.goToPages();

		await pagesAdminPage.showChildPages(pageName);

		await expect(
			pagesAdminPage.getPageMenuItem(childPageName)
		).toBeVisible();
	}
);
