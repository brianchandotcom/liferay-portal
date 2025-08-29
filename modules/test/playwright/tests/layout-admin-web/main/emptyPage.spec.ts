/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../../fixtures/applicationsMenuPageTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {pagesAdminPagesTest} from '../../../fixtures/pagesAdminPagesTest';
import {serverAdministrationPageTest} from '../../../fixtures/serverAdministrationPageTest';
import {getRandomInt} from '../../../utils/getRandomInt';
import {openProductMenu} from '../../../utils/productMenu';
import {pagesPagesTest} from './fixtures/pagesPagesTest';

const test = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest,
	pagesAdminPagesTest,
	pagesPagesTest,
	serverAdministrationPageTest
);

const getGroovyScript = (companyId, pageName, siteId, userId) => `
    import com.liferay.portal.kernel.model.Group
    import com.liferay.portal.kernel.model.Layout
    import com.liferay.portal.kernel.service.GroupLocalServiceUtil
    import com.liferay.portal.kernel.service.LayoutLocalServiceUtil
    import com.liferay.portal.kernel.model.LayoutConstants
    import com.liferay.portal.kernel.service.ServiceContext
    import com.liferay.portal.kernel.service.ServiceContextThreadLocal
    import com.liferay.portal.kernel.util.PortalUtil
    
    def userId = ${userId}
    
    def serviceContext = new ServiceContext()
    def companyGroupId = ${siteId}
    serviceContext.setScopeGroupId(companyGroupId)
    serviceContext.setCompanyId(${companyId})
    serviceContext.setAttribute("layout.instanceable.allowed", Boolean.TRUE);
    
    try {
        out.println(
            LayoutLocalServiceUtil.addLayout(
                null, userId, companyGroupId, false,
                LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
                "${pageName}", "${pageName}", "", LayoutConstants.TYPE_EMPTY, true, "", serviceContext));
    } catch (Exception e) {
        out.println("An error occurred: " + e.getMessage())
        e.printStackTrace()
    }
`;

let emptyLayoutJSON;
let emptyLayoutName;

test.beforeEach(
	'Create an empty page using a Groovy script in the Server Administration Script tab',
	async ({
		apiHelpers,
		applicationsMenuPage,
		page,
		serverAdministrationPage,
		site,
	}) => {
		await applicationsMenuPage.goToServerAdministration();

		const companyId = await page.evaluate(() => {
			return Liferay.ThemeDisplay.getCompanyId();
		});

		const user =
			await apiHelpers.headlessAdminUser.getUserAccountByEmailAddress(
				'test@liferay.com'
			);

		emptyLayoutName = 'empty' + getRandomInt();

		await serverAdministrationPage.executeScript(
			getGroovyScript(companyId, emptyLayoutName, site.id, user.id)
		);

		await expect(await page.getByText('"type": "empty"')).toBeVisible();

		const output = await page
			.locator("//b[text() = 'Output']/following-sibling::pre")
			.textContent();

		emptyLayoutJSON = JSON.parse(output);
	}
);

test.afterEach(
	'Delete the empty page',
	async ({apiHelpers, page, pageTreePage}) => {
		try {
			await openProductMenu(page);

			await pageTreePage.close();
		}
		catch (error) {
			throw new Error(error);
		}
		finally {
			await apiHelpers.jsonWebServicesLayout.deleteLayout(
				emptyLayoutJSON.plid
			);
		}
	}
);

test('Assert the Empty Status Label is present in the UI', async ({
	page,
	pageTreePage,
	pagesAdminPage,
	site,
}) => {
	await page.goto(`/web/${site.name}`);

	// Assert label is in Control Menu Bar

	await expect(
		page.locator(
			"//div[@class='control-menu-nav-item']/span[contains(@class, 'label-warning')]/span[text()='Empty']"
		)
	).toBeVisible();

	// Assert label is in Product Menu's Page Tree

	await openProductMenu(page);

	await pageTreePage.open();

	await expect(page.getByRole('link', {name: emptyLayoutName})).toBeVisible();

	await expect(
		page.locator(
			`//span[text()='${emptyLayoutName}']/span[contains(@class, 'label-warning')]/span[text()='Empty']`
		)
	).toBeVisible();

	// Assert label is in Group Pages Portlet Miller Columns

	await pagesAdminPage.goto(site.friendlyUrlPath);

	const emptyLayoutLocator = page.locator(
		`//a[@aria-label='${emptyLayoutName} Empty']/parent::li`
	);

	await expect(emptyLayoutLocator).toBeVisible();

	await expect(
		emptyLayoutLocator.locator(
			"//span[contains(@class, 'label-warning')]/span[text()='Empty']"
		)
	).toBeVisible();
});

test('Viewing an empty page via Page Tree shows a dummy page with an alert', async ({
	page,
	pageTreePage,
	site,
}) => {
	await page.goto(`/web/${site.name}`);

	await openProductMenu(page);

	await pageTreePage.open();

	await expect(page.getByRole('link', {name: emptyLayoutName})).toBeVisible();

	await page.getByRole('link', {name: emptyLayoutName}).click();

	await expect(
		page.getByText(
			'This page was automatically generated during the import process to ensure the correct hierarchy of imported elements. Edit the page to configure.'
		)
	).toBeVisible();
});

test('Viewing an empty page via Friendly URL shows a dummy page with an alert', async ({
	page,
	site,
}) => {
	await page.goto(`/web/${site.name}${emptyLayoutJSON.friendlyURL}`);

	await expect(
		page.getByText(
			'This page was automatically generated during the import process to ensure the correct hierarchy of imported elements. Edit the page to configure.'
		)
	).toBeVisible();
});
