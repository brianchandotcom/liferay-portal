/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {documentLibraryPagesTest} from '../../../fixtures/documentLibraryPages.fixtures';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';

const test = mergeTests(
	documentLibraryPagesTest,
	isolatedSiteTest,
	loginTest()
);

test(
	'DM Preview page has not a fixed navbar',
	{tag: '@LPD-26290'},
	async ({documentLibraryEditFilePage, documentLibraryPage, page, site}) => {
		const title = getRandomString();

		await documentLibraryEditFilePage.publishNewBasicFileEntry(
			title,
			site.friendlyUrlPath
		);

		await page.getByRole('link', {name: title}).click();

		const navItem = await page.locator(
			'nav.component-tbar.subnav-tbar-light'
		);

		const navItemPosition = await navItem.evaluate((element) => {
			return window
				.getComputedStyle(element)
				.getPropertyValue('position');
		});

		await expect(navItemPosition).not.toBe('fixed');

		await documentLibraryPage.goto(site.friendlyUrlPath);
	}
);

test(
	'Verify location link from file info panel',
	{tag: '@LPD-85940'},
	async ({documentLibraryEditFilePage, page, site}) => {
		const title = getRandomString();

		await documentLibraryEditFilePage.publishNewBasicFileEntry(
			title,
			site.friendlyUrlPath
		);

		await page.getByRole('link', {name: title}).first().click();

		const infoButton = page.locator('[data-qa-id="infoButton"]');

		await expect(infoButton).toBeVisible();
		await infoButton.click();

		const sidebar = page.locator(
			'#_com_liferay_document_library_web_portlet_DLAdminPortlet_ContextualSidebar.contextual-sidebar-visible'
		);

		await expect(sidebar).toBeVisible();

		const locationLink = sidebar.locator('dt:has-text("Location") + dd a');

		await expect(locationLink).toBeVisible();

		await expect(locationLink).toHaveAttribute(
			'href',
			new RegExp(`/group${site.friendlyUrlPath}/~/control_panel/manage`)
		);
	}
);
