/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {contentPagesTest} from '../../fixtures/contentPagesTest';
import {loginTest} from '../../fixtures/loginTest';
import {productMenuPageTest} from '../../fixtures/productMenuPageTest';
import {serverAdministrationPageTest} from '../../fixtures/serverAdministrationPageTest';
import {sitesPageTest} from '../../fixtures/sitesPageTest';
import {systemSettingsPageTest} from '../../fixtures/systemSettingsPageTest';
import {uiElementsPageTest} from '../../fixtures/uiElementsTest';
import {webContentDisplayPageTest} from '../../fixtures/webContentDisplayPageTest';
import {widgetPagesTest} from '../../fixtures/widgetPagesTest';
import {ApiHelpers} from '../../helpers/ApiHelpers';
import {LayoutSetPrototype} from '../../helpers/json-web-services/JSONWebServicesLayoutSetPrototypeApiHelper';
import {WebContentDisplayPage} from '../../pages/journal-content-web/WebContentDisplayPage';
import {ContentPage} from '../../pages/layout-admin-web/ContentPage';
import {WidgetPage} from '../../pages/layout-admin-web/WidgetPage';
import {ApplicationsMenuPage} from '../../pages/product-navigation-applications-menu/ApplicationsMenuPage';
import {ProductMenuPage} from '../../pages/product-navigation-control-menu-web/ProductMenuPage';
import {UIElementsPage} from '../../pages/uielements/UIElementsPage';
import getRandomString from '../../utils/getRandomString';
import {journalPagesTest} from '../journal-web/fixtures/journalPagesTest';
import {JournalPage} from '../journal-web/pages/JournalPage';
import {pagesPagesTest} from '../layout-admin-web/fixtures/pagesPagesTest';
import {StaticPagesPage} from '../layout-admin-web/pages/StaticPagesPage';
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
	widgetPagesTest,
	webContentDisplayPageTest,
	contentPagesTest,
	serverAdministrationPageTest,
	sitesPageTest,
	systemSettingsPageTest,
	loginTest()
);

const webContentName1: string = getRandomString();
const webContentName2: string = getRandomString();
const webContentText1: string = getRandomString();
const webContentText2: string = getRandomString();

test('can switch template with web content on widget page', async ({
	apiHelpers,
	applicationsMenuPage,
	journalPage,
	layoutSetPrototypePage,
	page,
	productMenuPage,
	serverAdministrationPage,
	sitesPage,
	staticPagesPage,
	systemSettingsPage,
	uiElementsPage,
	webContentDisplayPage,
	widgetPage,
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
		productMenuPage,
		staticPagesPage,
		templateName: widgetTemplateName1,
		text: `${webContentText1} `,
		uiElementsPage,
		webContentDisplayPage,
		webContentName: `${webContentName1} `,
		widgetPage,
	});

	await createSiteTemplateWithWebContentOnWidgetPage({
		applicationsMenuPage,
		journalPage,
		layoutSetPrototypePage,
		page,
		productMenuPage,
		staticPagesPage,
		templateName: widgetTemplateName2,
		text: `${webContentText2} `,
		uiElementsPage,
		webContentDisplayPage,
		webContentName: `${webContentName2} `,
		widgetPage,
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

	await staticPagesPage.checkIfWebContentAdded(
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

test('can switch template with web content on content page', async ({
	apiHelpers,
	applicationsMenuPage,
	contentPage,
	journalPage,
	layoutSetPrototypePage,
	page,
	productMenuPage,
	serverAdministrationPage,
	sitesPage,
	staticPagesPage,
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
		contentPage,
		journalPage,
		layoutSetPrototypePage,
		page,
		productMenuPage,
		staticPagesPage,
		templateName: contentTemplateName1,
		text: `${webContentText1} `,
		uiElementsPage,
		webContentDisplayPage,
		webContentName: `${webContentName1} `,
	});

	await createSiteTemplateWithWebContentOnContentPage({
		applicationsMenuPage,
		contentPage,
		journalPage,
		layoutSetPrototypePage,
		page,
		productMenuPage,
		staticPagesPage,
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
	await staticPagesPage.checkIfWebContentAdded(
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

	await staticPagesPage.checkIfWebContentAdded(
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
	productMenuPage,
	staticPagesPage,
	templateName,
	text,
	uiElementsPage,
	webContentDisplayPage,
	webContentName,
	widgetPage,
}: {
	applicationsMenuPage: ApplicationsMenuPage;
	journalPage: JournalPage;
	layoutSetPrototypePage: LayoutSetPrototypePage;
	page: Page;
	productMenuPage: ProductMenuPage;
	staticPagesPage: StaticPagesPage;
	templateName: string;
	text: string;
	uiElementsPage: UIElementsPage;
	webContentDisplayPage: WebContentDisplayPage;
	webContentName: string;
	widgetPage: WidgetPage;
}): Promise<void> {
	await applicationsMenuPage.goToSiteTemplates();
	await layoutSetPrototypePage.addSiteTemplate(templateName);
	await applicationsMenuPage.goToSiteTemplates();
	const siteTemplateUrl = await layoutSetPrototypePage.getSiteTemplateUrl(
		templateName
	);
	await page.goto(siteTemplateUrl);
	await productMenuPage.checkIfAdecuateProductMenu(templateName);
	await productMenuPage.openProductMenuIfClosed();
	await productMenuPage.goToWebContent();
	await journalPage.goToCreateArticle();
	await journalPage.createBasicArticle(webContentName, text);

	await productMenuPage.goToPages();
	await uiElementsPage.clickNewButton();
	if (!staticPagesPage.addTemplatePageButton.isVisible) {
		await uiElementsPage.clickNewButton();
	}
	await staticPagesPage.addTemplatePageButton.waitFor({state: 'visible'});
	await staticPagesPage.addTemplatePageButton.click();
	await staticPagesPage.addWidgetPage(templateName);

	await productMenuPage.clickSpecificPage(templateName);
	await widgetPage.clickToAddApplication();
	await webContentDisplayPage.addWebContentWithWidget();
	await uiElementsPage.setupUpdatedAlert.waitFor({state: 'hidden'});
	await uiElementsPage.closeClickable.click();
	await webContentDisplayPage.webContentDisplayWidget.waitFor({
		state: 'visible',
	});
}

async function createSiteTemplateWithWebContentOnContentPage({
	applicationsMenuPage,
	contentPage,
	journalPage,
	layoutSetPrototypePage,
	page,
	productMenuPage,
	staticPagesPage,
	templateName,
	text,
	uiElementsPage,
	webContentDisplayPage,
	webContentName,
}: {
	applicationsMenuPage: ApplicationsMenuPage;
	contentPage: ContentPage;
	journalPage: JournalPage;
	layoutSetPrototypePage: LayoutSetPrototypePage;
	page: Page;
	productMenuPage: ProductMenuPage;
	staticPagesPage: StaticPagesPage;
	templateName: string;
	text: string;
	uiElementsPage: UIElementsPage;
	webContentDisplayPage: WebContentDisplayPage;
	webContentName: string;
}): Promise<void> {
	await applicationsMenuPage.goToSiteTemplates();
	await layoutSetPrototypePage.addSiteTemplate(templateName);
	await applicationsMenuPage.goToSiteTemplates();
	const siteTemplateUrl = await layoutSetPrototypePage.getSiteTemplateUrl(
		templateName
	);

	await page.goto(siteTemplateUrl);
	await productMenuPage.checkIfAdecuateProductMenu(templateName);
	await productMenuPage.openProductMenuIfClosed();
	await productMenuPage.goToWebContent();
	await journalPage.goToCreateArticle();
	await journalPage.createBasicArticle(webContentName, text);

	await productMenuPage.goToPages();
	await uiElementsPage.clickNewButton();
	await staticPagesPage.addTemplatePageButton.waitFor({state: 'visible'});
	await staticPagesPage.addTemplatePageButton.click();
	await staticPagesPage.addContentPage(templateName);

	await contentPage.addWebContentDisplayToPage();
	await webContentDisplayPage.addWebContentWithDisplay();
	await uiElementsPage.publishButton.click();
}
