/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {applicationsMenuPageTest} from '../../../fixtures/applicationsMenuPageTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {pageTemplatesPagesTest} from '../../../fixtures/pageTemplatesPagesTest';
import {pageViewModePagesTest} from '../../../fixtures/pageViewModePagesTest';
import {pagesAdminPagesTest} from '../../../fixtures/pagesAdminPagesTest';
import {productMenuPageTest} from '../../../fixtures/productMenuPageTest';
import {sitesPageTest} from '../../../fixtures/sitesPageTest';
import getRandomString from '../../../utils/getRandomString';
import createSiteTemplate from './utils/createSiteTemplate';

export const test = mergeTests(
	dataApiHelpersTest,
	applicationsMenuPageTest,
	loginTest(),
	featureFlagsTest({
		'LPD-39304': {enabled: true},
	}),
	pagesAdminPagesTest,
	pageEditorPagesTest,
	pageTemplatesPagesTest,
	productMenuPageTest,
	sitesPageTest,
	pageViewModePagesTest
);

test('User is able to propagate pages separately on site templates', async ({
	apiHelpers,
	applicationsMenuPage,
	page,
	pageEditorPage,
	pagesAdminPage,
	productMenuPage,
	sitesPage,
	widgetPagePage,
}) => {
	const homePageName: string = 'Home';
	const pageName: string = 'Page-' + getRandomString();
	const siteTemplateName: string = 'Template-' + getRandomString();
	const siteName: string = 'Site-' + getRandomString();

	const layoutSetPrototype = await createSiteTemplate({
		apiHelpers,
		layoutsUpdateable: false,
		page,
		productMenuPage,
		templateName: siteTemplateName,
	});

	await productMenuPage.goToPages();

	await pagesAdminPage.createNewPage({
		draft: true,
		name: pageName,
		template: 'Widget Page',
	});

	await productMenuPage.goToPages();
	await page.getByText(pageName).click();
	await widgetPagePage.addPortlet('Asset Publisher', 'Content Management');

	await applicationsMenuPage.goToSites();

	const siteId = await sitesPage.createSite({
		isCustom: true,
		siteName,
		templateName: siteTemplateName,
	});

	apiHelpers.data.push({id: siteId, type: 'site'});

	let homePageData = await apiHelpers.headlessDelivery.getSitePage(
		homePageName,
		siteId
	);

	let widgetPageData = await apiHelpers.headlessDelivery.getSitePage(
		pageName,
		siteId
	);

	const homePageModificationDateBefore = homePageData.dateModified;
	const widgetPageModificationDateBefore = widgetPageData.dateModified;

	await page.goto(
		`/group/template-${layoutSetPrototype.layoutSetPrototypeId}${widgetPageData.friendlyUrlPath}`
	);

	await widgetPagePage.addPortlet('Web Content Display');

	await page.goto(`/web/${siteName}${widgetPageData.friendlyUrlPath}`);

	widgetPageData = await apiHelpers.headlessDelivery.getSitePage(
		pageName,
		siteId
	);

	const widgetPageModificationDateAfter = widgetPageData.dateModified;

	await page.goto(
		`/group/template-${layoutSetPrototype.layoutSetPrototypeId}`
	);

	await productMenuPage.goToPages();
	await page.getByText(homePageName).click();
	await pageEditorPage.addFragment('Basic Components', 'Button');

	await applicationsMenuPage.goToSites();
	await page.getByText(siteName).click();

	await page.goto(
		`/group/template-${layoutSetPrototype.layoutSetPrototypeId}${widgetPageData.friendlyUrlPath}`
	);

	homePageData = await apiHelpers.headlessDelivery.getSitePage(
		homePageName,
		siteId
	);

	const homePageModificationDateAfter = homePageData.dateModified;

	expect(
		widgetPageModificationDateBefore !== widgetPageModificationDateAfter &&
			!homePageModificationDateAfter !== homePageModificationDateBefore
	).toEqual(true);
});
