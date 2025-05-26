/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {applicationsMenuPageTest} from '../../../fixtures/applicationsMenuPageTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {masterPagesPagesTest} from '../../../fixtures/masterPagesPagesTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {pageTemplatesPagesTest} from '../../../fixtures/pageTemplatesPagesTest';
import {pageViewModePagesTest} from '../../../fixtures/pageViewModePagesTest';
import {pagesAdminPagesTest} from '../../../fixtures/pagesAdminPagesTest';
import {productMenuPageTest} from '../../../fixtures/productMenuPageTest';
import {sitesPageTest} from '../../../fixtures/sitesPageTest';
import getRandomString from '../../../utils/getRandomString';
import createSiteTemplate from './utils/createSiteTemplate';

export const test = mergeTests(
	applicationsMenuPageTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-39304': {enabled: true},
	}),
	loginTest(),
	masterPagesPagesTest,
	pagesAdminPagesTest,
	pageEditorPagesTest,
	pageTemplatesPagesTest,
	pageViewModePagesTest,
	productMenuPageTest,
	sitesPageTest
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

test(
	'Guest view permission is not lost when a page generated from Master Page change is propagated to the site of a Site Template',
	{tag: ['@LPD-54068']},
	async ({
		apiHelpers,
		applicationsMenuPage,
		page,
		pageEditorPage,
		pagesAdminPage,
		productMenuPage,
		sitesPage,
	}) => {
		const siteTemplateName: string = 'template-' + getRandomString();

		const layoutSetPrototype = await createSiteTemplate({
			apiHelpers,
			layoutsUpdateable: true,
			page,
			productMenuPage,
			templateName: siteTemplateName,
		});

		await apiHelpers.data.push({
			id: layoutSetPrototype.layoutSetPrototypeId,
			type: 'layoutSetPrototype',
		});

		const layoutSetPrototypeGroup =
			await apiHelpers.jsonWebServicesGroup.getGroupByKey(
				layoutSetPrototype.companyId,
				layoutSetPrototype.layoutSetPrototypeId
			);

		const masterPageName: string = 'masterPage-' + getRandomString();
		await apiHelpers.jsonWebServicesLayoutPageTemplateEntry.addLayoutPageTemplateEntry(
			{
				groupId: layoutSetPrototypeGroup.groupId,
				name: masterPageName,
				type: 'master-layout',
			}
		);

		await productMenuPage.goToPages();

		const pageName: string = 'page-' + getRandomString();
		await pagesAdminPage.createNewPage({
			draft: true,
			name: pageName,
			template: masterPageName,
		});

		await pageEditorPage.publishPage();

		await applicationsMenuPage.goToSites();

		const siteName: string = 'site-' + getRandomString();
		const siteId = await sitesPage.createSite({
			isCustom: true,
			siteName,
			templateName: siteTemplateName,
		});

		await apiHelpers.data.push({id: siteId, type: 'site'});

		const newSiteURL = `/web/${siteName}`;

		// Go to the site when it is available

		await expect
			.poll(
				async () => {
					await page.goto(newSiteURL);

					return page.getByText('Page Not Found Go Back').isVisible();
				},
				{
					timeout: 6000,
				}
			)
			.toBe(false);

		await productMenuPage.goToPages();

		await page
			.getByRole('menuitem', {
				name: `Move ${pageName} Select ${pageName}`,
			})
			.getByLabel('Open Page Options Menu')
			.click();
		await page.getByRole('menuitem', {name: 'Permissions'}).click();

		const permissionsFrameLocator = await page.frameLocator(
			'iframe[title="Permissions"]'
		);

		const guestViewPermissionCheckbox =
			permissionsFrameLocator.locator('#guest_ACTION_VIEW');

		await expect(guestViewPermissionCheckbox).toBeChecked();

		const pageData = await apiHelpers.headlessDelivery.getSitePage(
			pageName,
			siteId
		);

		await page.goto(
			`/group/template-${layoutSetPrototype.layoutSetPrototypeId}`
		);

		await productMenuPage.goToPages();
		await page.getByText(pageName).click();
		await pageEditorPage.addFragment('Basic Components', 'Button');
		await pageEditorPage.publishPage();

		// Force the propagation of the chganges

		await applicationsMenuPage.goto();

		await page.goto(newSiteURL);

		await productMenuPage.goToPages();

		await page
			.getByRole('menuitem', {
				name: `Move ${pageName} Select ${pageName}`,
			})
			.getByLabel('Open Page Options Menu')
			.click();
		await page.getByRole('menuitem', {name: 'Permissions'}).click();

		await expect(guestViewPermissionCheckbox).toBeChecked();
	}
);
