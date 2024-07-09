/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {pageViewModePagesTest} from '../../fixtures/pageViewModePagesTest';
import {pagesAdminPagesTest} from '../../fixtures/pagesAdminPagesTest';
import {productMenuPageTest} from '../../fixtures/productMenuPageTest';
import {serverAdministrationPageTest} from '../../fixtures/serverAdministrationPageTest';
import {sitesPageTest} from '../../fixtures/sitesPageTest';
import {systemSettingsPageTest} from '../../fixtures/systemSettingsPageTest';
import {uiElementsPageTest} from '../../fixtures/uiElementsTest';
import {webContentDisplayPageTest} from '../../fixtures/webContentDisplayPageTest';
import {ApiHelpers} from '../../helpers/ApiHelpers';
import {LayoutSetPrototype} from '../../helpers/json-web-services/JSONWebServicesLayoutSetPrototypeApiHelper';
import {WebContentDisplayPage} from '../../pages/journal-content-web/WebContentDisplayPage';
import {PagesAdminPage} from '../../pages/layout-admin-web/PagesAdminPage';
import {WidgetPagePage} from '../../pages/layout-admin-web/WidgetPagePage';
import {PageEditorPage} from '../../pages/layout-content-page-editor-web/PageEditorPage';
import {ApplicationsMenuPage} from '../../pages/product-navigation-applications-menu/ApplicationsMenuPage';
import {ProductMenuPage} from '../../pages/product-navigation-control-menu-web/ProductMenuPage';
import {UIElementsPage} from '../../pages/uielements/UIElementsPage';
import getRandomString from '../../utils/getRandomString';
import {journalPagesTest} from '../journal-web/fixtures/journalPagesTest';
import {JournalPage} from '../journal-web/pages/JournalPage';
import {pagesPagesTest} from '../layout-admin-web/fixtures/pagesPagesTest';
import {layoutSetPrototypePageTest} from './fixtures/layoutSetPrototypePageTest';
import {LayoutSetPrototypePage} from './pages/LayoutSetPrototypePage';

export const test = mergeTests(
	applicationsMenuPageTest,
	journalPagesTest,
	apiHelpersTest,
	layoutSetPrototypePageTest,
	productMenuPageTest,
	uiElementsPageTest,
	pagesPagesTest,
	pageViewModePagesTest,
	webContentDisplayPageTest,
	pageEditorPagesTest,
	serverAdministrationPageTest,
	sitesPageTest,
	systemSettingsPageTest,
	loginTest(),
	pagesAdminPagesTest
);

const webContentName1: string = getRandomString();
const webContentName2: string = getRandomString();
const webContentText1: string = getRandomString();
const webContentText2: string = getRandomString();

test('Can switch template with web content on widget page.', async ({
	apiHelpers,
	applicationsMenuPage,
	journalPage,
	layoutSetPrototypePage,
	page,
	pagesAdminPage,
	productMenuPage,
	serverAdministrationPage,
	sitesPage,
	systemSettingsPage,
	uiElementsPage,
	webContentDisplayPage,
	widgetPagePage,
}) => {
	const widgetTemplateName1: string = getRandomString();
	const widgetTemplateName2: string = getRandomString();
	const siteName: string = getRandomString();

	await systemSettingsPage.disablePrivatePages();

	await createSiteTemplateWithWebContentOnWidgetPage({
		applicationsMenuPage,
		journalPage,
		layoutSetPrototypePage,
		page,
		pagesAdminPage,
		productMenuPage,
		templateName: widgetTemplateName1,
		text: `${webContentText1} `,
		uiElementsPage,
		webContentDisplayPage,
		webContentName: `${webContentName1} `,
		widgetPagePage,
	});

	await createSiteTemplateWithWebContentOnWidgetPage({
		applicationsMenuPage,
		journalPage,
		layoutSetPrototypePage,
		page,
		pagesAdminPage,
		productMenuPage,
		templateName: widgetTemplateName2,
		text: `${webContentText2} `,
		uiElementsPage,
		webContentDisplayPage,
		webContentName: `${webContentName2} `,
		widgetPagePage,
	});
	const layoutSetPrototypes: LayoutSetPrototype[] =
		await apiHelpers.jsonWebServicesLayoutSetPrototype.getLayoutSetPrototypes();
	const layoutSetPrototype1 = await getLayoutTemplateByName(
		layoutSetPrototypes,
		widgetTemplateName1
	);
	const layoutSetPrototype2 = await getLayoutTemplateByName(
		layoutSetPrototypes,
		widgetTemplateName2
	);
	await applicationsMenuPage.goToSites();
	const siteId = await sitesPage.createSiteFromTemplate(
		widgetTemplateName1,
		siteName
	);

	await applicationsMenuPage.goToServerAdministration();

	const script = `
    import com.liferay.portal.kernel.service.LayoutSetLocalServiceUtil;
    String siteTemplateUUID = "${layoutSetPrototype2.uuid}";
    long siteId = ${siteId};
    LayoutSetLocalServiceUtil.updateLayoutSetPrototypeLinkEnabled(siteId, true, true, siteTemplateUUID);
    `;
	await serverAdministrationPage.executeScript(script);

	await applicationsMenuPage.goToSites();

	await pagesAdminPage.checkIfWebContentAdded(
		siteName,
		widgetTemplateName2,
		webContentText2
	);

	// tearDown

	await deleteSiteAndLayoutSetPrototypes(
		apiHelpers,
		siteId,
		layoutSetPrototype1.layoutSetPrototypeId.toString(),
		layoutSetPrototype2.layoutSetPrototypeId.toString()
	);
});

test('Can switch template with web content on content page.', async ({
	apiHelpers,
	applicationsMenuPage,
	journalPage,
	layoutSetPrototypePage,
	page,
	pageEditorPage,
	pagesAdminPage,
	productMenuPage,
	serverAdministrationPage,
	sitesPage,
	systemSettingsPage,
	uiElementsPage,
	webContentDisplayPage,
}) => {
	await systemSettingsPage.disablePrivatePages();

	const contentTemplateName1: string = getRandomString();
	const contentTemplateName2: string = getRandomString();
	const siteName: string = getRandomString();

	await createSiteTemplateWithWebContentOnContentPage({
		applicationsMenuPage,
		journalPage,
		layoutSetPrototypePage,
		page,
		pageEditorPage,
		pagesAdminPage,
		productMenuPage,
		templateName: contentTemplateName1,
		text: `${webContentText1} `,
		uiElementsPage,
		webContentDisplayPage,
		webContentName: `${webContentName1} `,
	});

	await createSiteTemplateWithWebContentOnContentPage({
		applicationsMenuPage,
		journalPage,
		layoutSetPrototypePage,
		page,
		pageEditorPage,
		pagesAdminPage,
		productMenuPage,
		templateName: contentTemplateName2,
		text: `${webContentText2} `,
		uiElementsPage,
		webContentDisplayPage,
		webContentName: `${webContentName2} `,
	});

	const layoutSetPrototypes: LayoutSetPrototype[] =
		await apiHelpers.jsonWebServicesLayoutSetPrototype.getLayoutSetPrototypes();
	const layoutSetPrototype1 = await getLayoutTemplateByName(
		layoutSetPrototypes,
		contentTemplateName1
	);
	const layoutSetPrototype2 = await getLayoutTemplateByName(
		layoutSetPrototypes,
		contentTemplateName2
	);

	await applicationsMenuPage.goToSites();
	const siteId = await sitesPage.createSiteFromTemplate(
		contentTemplateName1,
		siteName
	);
	await applicationsMenuPage.goToSites();
	await pagesAdminPage.checkIfWebContentAdded(
		siteName,
		contentTemplateName1,
		webContentText1
	);

	await applicationsMenuPage.goToServerAdministration();

	const script = `
    import com.liferay.portal.kernel.service.LayoutSetLocalServiceUtil;
    String siteTemplateUUID = "${layoutSetPrototype2.uuid}";
    long siteId = ${siteId};
    LayoutSetLocalServiceUtil.updateLayoutSetPrototypeLinkEnabled(siteId, true, true, siteTemplateUUID);
    `;
	await serverAdministrationPage.executeScript(script);

	await pagesAdminPage.checkIfWebContentAdded(
		siteName,
		contentTemplateName2,
		webContentText2
	);

	// tearDown

	await deleteSiteAndLayoutSetPrototypes(
		apiHelpers,
		siteId,
		layoutSetPrototype1.layoutSetPrototypeId.toString(),
		layoutSetPrototype2.layoutSetPrototypeId.toString()
	);
});

test('Can switch template with web content on home page.', async ({
	apiHelpers,
	applicationsMenuPage,
	journalPage,
	layoutSetPrototypePage,
	page,
	pageEditorPage,
	pagesAdminPage,
	productMenuPage,
	serverAdministrationPage,
	sitesPage,
	systemSettingsPage,
	uiElementsPage,
	webContentDisplayPage,
}) => {
	await systemSettingsPage.disablePrivatePages();

	const contentTemplateName1: string = getRandomString();
	const contentTemplateName2: string = getRandomString();
	const siteName: string = getRandomString();

	await createSiteTemplateWithWebContentOnHomePage({
		applicationsMenuPage,
		journalPage,
		layoutSetPrototypePage,
		page,
		pageEditorPage,
		pagesAdminPage,
		productMenuPage,
		templateName: contentTemplateName1,
		text: `${webContentText1} `,
		uiElementsPage,
		webContentDisplayPage,
		webContentName: `${webContentName1} `,
	});

	await createSiteTemplateWithWebContentOnHomePage({
		applicationsMenuPage,
		journalPage,
		layoutSetPrototypePage,
		page,
		pageEditorPage,
		pagesAdminPage,
		productMenuPage,
		templateName: contentTemplateName2,
		text: `${webContentText2} `,
		uiElementsPage,
		webContentDisplayPage,
		webContentName: `${webContentName2} `,
	});

	const layoutSetPrototypes: LayoutSetPrototype[] =
		await apiHelpers.jsonWebServicesLayoutSetPrototype.getLayoutSetPrototypes();
	const layoutSetPrototype1 = await getLayoutTemplateByName(
		layoutSetPrototypes,
		contentTemplateName1
	);
	const layoutSetPrototype2 = await getLayoutTemplateByName(
		layoutSetPrototypes,
		contentTemplateName2
	);

	await applicationsMenuPage.goToSites();
	const siteId = await sitesPage.createSiteFromTemplate(
		contentTemplateName1,
		siteName
	);
	await applicationsMenuPage.goToSites();
	await pagesAdminPage.checkIfWebContentAddedToHome(
		siteName,
		webContentText1
	);

	await applicationsMenuPage.goToServerAdministration();

	const script = `
    import com.liferay.portal.kernel.service.LayoutSetLocalServiceUtil;
    String siteTemplateUUID = "${layoutSetPrototype2.uuid}";
    long siteId = ${siteId};
    LayoutSetLocalServiceUtil.updateLayoutSetPrototypeLinkEnabled(siteId, true, true, siteTemplateUUID);
    `;
	await serverAdministrationPage.executeScript(script);

	await pagesAdminPage.checkIfWebContentAddedToHome(
		siteName,
		webContentText1
	);

	// tearDown

	await deleteSiteAndLayoutSetPrototypes(
		apiHelpers,
		siteId,
		layoutSetPrototype1.layoutSetPrototypeId.toString(),
		layoutSetPrototype2.layoutSetPrototypeId.toString()
	);
});

async function deleteSiteAndLayoutSetPrototypes(
	apiHelpers: ApiHelpers,
	siteId: string,
	...layoutSetPrototypeIds: string[]
) {
	let response = await apiHelpers.headlessSite.deleteSite(siteId);
	if (!response.ok()) {
		response = await apiHelpers.headlessSite.deleteSite(siteId);
	}
	expect(response.ok()).toBe(true);
	for (const prototypeId of layoutSetPrototypeIds) {
		await apiHelpers.jsonWebServicesLayoutSetPrototype.deleteLayoutSetPrototypes(
			prototypeId
		);
	}
}

async function getLayoutTemplateByName(
	layoutSetPrototypes: LayoutSetPrototype[],
	targetName: string
): Promise<LayoutSetPrototype> {
	const targetLayout = layoutSetPrototypes.find(
		(layoutSetPrototype) =>
			layoutSetPrototype.nameCurrentValue === targetName
	);

	if (targetLayout) {
		return {
			layoutSetPrototypeId: targetLayout.layoutSetPrototypeId,
			nameCurrentValue: targetLayout.nameCurrentValue,
			uuid: targetLayout.uuid,
		};
	}
	else {
		return {
			layoutSetPrototypeId: undefined,
			nameCurrentValue: undefined,
			uuid: undefined,
		};
	}
}

async function createSiteTemplateWithWebContentOnWidgetPage({
	applicationsMenuPage,
	journalPage,
	layoutSetPrototypePage,
	page,
	pagesAdminPage,
	productMenuPage,
	templateName,
	text,
	uiElementsPage,
	webContentDisplayPage,
	webContentName,
	widgetPagePage,
}: {
	applicationsMenuPage: ApplicationsMenuPage;
	journalPage: JournalPage;
	layoutSetPrototypePage: LayoutSetPrototypePage;
	page: Page;
	pagesAdminPage: PagesAdminPage;
	productMenuPage: ProductMenuPage;
	templateName: string;
	text: string;
	uiElementsPage: UIElementsPage;
	webContentDisplayPage: WebContentDisplayPage;
	webContentName: string;
	widgetPagePage: WidgetPagePage;
}): Promise<void> {
	await applicationsMenuPage.goToSiteTemplates();
	await layoutSetPrototypePage.addSiteTemplate(templateName);
	await applicationsMenuPage.goToSiteTemplates();
	const siteTemplateUrl =
		await layoutSetPrototypePage.getSiteTemplateUrl(templateName);
	await page.goto(siteTemplateUrl);
	await productMenuPage.checkIfAdecuateProductMenu(templateName);
	await productMenuPage.openProductMenuIfClosed();
	await productMenuPage.goToWebContent();
	await journalPage.goToCreateArticle();
	await journalPage.fillArticleData(webContentName, text);
	await journalPage.publishArticle();

	await productMenuPage.goToPages();
	await uiElementsPage.clickNewButton();
	if (!pagesAdminPage.addTemplatePageButton.isVisible) {
		await uiElementsPage.clickNewButton();
	}
	await pagesAdminPage.addTemplatePageButton.waitFor({state: 'visible'});
	await pagesAdminPage.addTemplatePageButton.click();
	await pagesAdminPage.addWidgetPage(templateName);

	await productMenuPage.clickSpecificPage(templateName);
	await widgetPagePage.addButton.click();
	await webContentDisplayPage.addWebContentWithWidget();
	await uiElementsPage.setupUpdatedAlert.waitFor({state: 'hidden'});
	await uiElementsPage.closeClickable.click();
	await webContentDisplayPage.webContentDisplayWidget.waitFor({
		state: 'visible',
	});
}

async function createSiteTemplateWithWebContentOnContentPage({
	applicationsMenuPage,
	journalPage,
	layoutSetPrototypePage,
	page,
	pageEditorPage,
	pagesAdminPage,
	productMenuPage,
	templateName,
	text,
	uiElementsPage,
	webContentDisplayPage,
	webContentName,
}: {
	applicationsMenuPage: ApplicationsMenuPage;
	journalPage: JournalPage;
	layoutSetPrototypePage: LayoutSetPrototypePage;
	page: Page;
	pageEditorPage: PageEditorPage;
	pagesAdminPage: PagesAdminPage;
	productMenuPage: ProductMenuPage;
	templateName: string;
	text: string;
	uiElementsPage: UIElementsPage;
	webContentDisplayPage: WebContentDisplayPage;
	webContentName: string;
}): Promise<void> {
	await applicationsMenuPage.goToSiteTemplates();
	await layoutSetPrototypePage.addSiteTemplate(templateName);
	await applicationsMenuPage.goToSiteTemplates();
	const siteTemplateUrl =
		await layoutSetPrototypePage.getSiteTemplateUrl(templateName);

	await page.goto(siteTemplateUrl);
	await productMenuPage.checkIfAdecuateProductMenu(templateName);
	await productMenuPage.openProductMenuIfClosed();
	await productMenuPage.goToWebContent();
	await journalPage.goToCreateArticle();
	await journalPage.fillArticleData(webContentName, text);
	await journalPage.publishArticle();

	await productMenuPage.goToPages();
	await uiElementsPage.clickNewButton();
	await pagesAdminPage.addTemplatePageButton.waitFor({state: 'visible'});
	await pagesAdminPage.addTemplatePageButton.click();
	await pagesAdminPage.addContentPage(templateName);

	await pageEditorPage.addWidget('Content Management', 'Web Content Display');
	await webContentDisplayPage.addWebContentWithDisplay();
	await uiElementsPage.publishButton.click();
}

async function createSiteTemplateWithWebContentOnHomePage({
	applicationsMenuPage,
	journalPage,
	layoutSetPrototypePage,
	page,
	pageEditorPage,
	pagesAdminPage,
	productMenuPage,
	templateName,
	text,
	uiElementsPage,
	webContentDisplayPage,
	webContentName,
}: {
	applicationsMenuPage: ApplicationsMenuPage;
	journalPage: JournalPage;
	layoutSetPrototypePage: LayoutSetPrototypePage;
	page: Page;
	pageEditorPage: PageEditorPage;
	pagesAdminPage: PagesAdminPage;
	productMenuPage: ProductMenuPage;
	templateName: string;
	text: string;
	uiElementsPage: UIElementsPage;
	webContentDisplayPage: WebContentDisplayPage;
	webContentName: string;
}): Promise<void> {
	await applicationsMenuPage.goToSiteTemplates();
	await layoutSetPrototypePage.addSiteTemplate(templateName);
	await applicationsMenuPage.goToSiteTemplates();
	const siteTemplateUrl =
		await layoutSetPrototypePage.getSiteTemplateUrl(templateName);

	await page.goto(siteTemplateUrl);
	await productMenuPage.checkIfAdecuateProductMenu(templateName);
	await productMenuPage.openProductMenuIfClosed();
	await productMenuPage.goToWebContent();
	await journalPage.goToCreateArticle();
	await journalPage.fillArticleData(webContentName, text);
	await journalPage.publishArticle();

	await productMenuPage.goToPages();
	await pagesAdminPage.homePageLink.click();
	await pageEditorPage.addWidget('Content Management', 'Web Content Display');
	await webContentDisplayPage.addWebContentWithDisplay();
	await uiElementsPage.publishButton.click();
}
