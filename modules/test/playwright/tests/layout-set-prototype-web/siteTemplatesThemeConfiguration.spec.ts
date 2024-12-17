/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {displayPageTemplatesPagesTest} from '../../fixtures/displayPageTemplatesPagesTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {pageViewModePagesTest} from '../../fixtures/pageViewModePagesTest';
import {pagesAdminPagesTest} from '../../fixtures/pagesAdminPagesTest';
import {productMenuPageTest} from '../../fixtures/productMenuPageTest';
import {serverAdministrationPageTest} from '../../fixtures/serverAdministrationPageTest';
import {sitesPageTest} from '../../fixtures/sitesPageTest';
import {uiElementsPageTest} from '../../fixtures/uiElementsTest';
import {webContentDisplayPageTest} from '../../fixtures/webContentDisplayPageTest';
import {PagesAdminPage} from '../../pages/layout-admin-web/PagesAdminPage';
import {PageEditorPage} from '../../pages/layout-content-page-editor-web/PageEditorPage';
import {ApplicationsMenuPage} from '../../pages/product-navigation-applications-menu/ApplicationsMenuPage';
import {ProductMenuPage} from '../../pages/product-navigation-control-menu-web/ProductMenuPage';
import {UIElementsPage} from '../../pages/uielements/UIElementsPage';
import getRandomString from '../../utils/getRandomString';
import {journalPagesTest} from '../journal-web/fixtures/journalPagesTest';
import {pagesPagesTest} from '../layout-admin-web/fixtures/pagesPagesTest';
import {layoutSetPrototypePageTest} from './fixtures/layoutSetPrototypePageTest';
import {LayoutSetPrototypePage} from './pages/LayoutSetPrototypePage';

const test = mergeTests(
	applicationsMenuPageTest,
	journalPagesTest,
	isolatedSiteTest,
	layoutSetPrototypePageTest,
	productMenuPageTest,
	uiElementsPageTest,
	pagesPagesTest,
	pageViewModePagesTest,
	webContentDisplayPageTest,
	pageEditorPagesTest,
	displayPageTemplatesPagesTest,
	sitesPageTest,
	serverAdministrationPageTest,
	loginTest(),
	featureFlagsTest({
		'LPD-39304': true,
	}),
	pagesAdminPagesTest
);

test(
	'If you set a layout-set-prototypes theme, then both private and public layoutSets theme settings is the same',
	{
		tag: '@LPD-44632',
	},
	async ({
		applicationsMenuPage,
		displayPageTemplatesPage,
		layoutSetPrototypePage,
		page,
		pageEditorPage,
		pagesAdminPage,
		productMenuPage,
		uiElementsPage,
	}) => {
		const siteTemplateName: string = getRandomString();
		await createSiteTemplateAndConfigureTheirLayoutSetsTheme({
			applicationsMenuPage,
			layoutSetPrototypePage,
			page,
			pageEditorPage,
			pagesAdminPage,
			productMenuPage,
			templateName: siteTemplateName,
			uiElementsPage,
		});

		const siteTemplateUrl = page.url().match(/template-\d+/);

		await displayPageTemplatesPage.goto('/' + siteTemplateUrl[0]);

		const displayPageTemplateName = getRandomString();

		await displayPageTemplatesPage.createTemplate({
			contentSubtype: 'Basic Web Content',
			contentType: 'Web Content Article',
			name: displayPageTemplateName,
		});

		await displayPageTemplatesPage.page
			.getByRole('link', {name: displayPageTemplateName})
			.click();

		await expect(page.getByText('Terms and Conditions')).toBeVisible();
	}
);

async function createSiteTemplateAndConfigureTheirLayoutSetsTheme({
	applicationsMenuPage,
	layoutSetPrototypePage,
	page,
	pageEditorPage,
	pagesAdminPage,
	productMenuPage,
	templateName,
}: {
	applicationsMenuPage: ApplicationsMenuPage;
	layoutSetPrototypePage: LayoutSetPrototypePage;
	page: Page;
	pageEditorPage: PageEditorPage;
	pagesAdminPage: PagesAdminPage;
	productMenuPage: ProductMenuPage;
	templateName: string;
	uiElementsPage: UIElementsPage;
}): Promise<void> {
	await applicationsMenuPage.goToSiteTemplates();
	await layoutSetPrototypePage.addSiteTemplate(templateName);
	await applicationsMenuPage.goToSiteTemplates();
	const siteTemplateUrl =
		await layoutSetPrototypePage.getSiteTemplateUrl(templateName);

	await page.goto(siteTemplateUrl);
	await productMenuPage.checkIfAdecuateProductMenu(templateName);
	await productMenuPage.openProductMenuIfClosed();

	await productMenuPage.goToPages();
	await pagesAdminPage.newButton.click();
	await layoutSetPrototypePage.addTemplatePageButton.waitFor({
		state: 'visible',
	});
	await layoutSetPrototypePage.addTemplatePageButton.click();
	await pagesAdminPage.addPage({
		name: templateName,
	});

	await pageEditorPage.publishPage();
	await pagesAdminPage.page.getByLabel('Options', {exact: true}).click();
	await pagesAdminPage.page
		.getByRole('menuitem', {name: 'Configuration'})
		.click();
	await pagesAdminPage.page
		.getByRole('button', {name: 'Change Current Theme'})
		.click();
	await pagesAdminPage.page
		.frameLocator('iframe[title="Available Themes"]')
		.getByRole('button', {name: 'Select Speedwell By Liferay,'})
		.click();
	await pagesAdminPage.page.getByRole('button', {name: 'Save'}).click();

	await expect(
		page.getByText('Success:Your request completed successfully.')
	).toBeVisible();
}
