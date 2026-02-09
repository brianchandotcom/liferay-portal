/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageViewModePagesTest} from '../../../fixtures/pageViewModePagesTest';
import {pagesAdminPagesTest} from '../../../fixtures/pagesAdminPagesTest';
import {liferayConfig} from '../../../liferay.config';
import getRandomString from '../../../utils/getRandomString';
import {closeProductMenu, openProductMenu} from '../../../utils/productMenu';
import {pagesPagesTest} from '../../layout-admin-web/main/fixtures/pagesPagesTest';

const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	pagesAdminPagesTest,
	pagesPagesTest,
	loginTest(),
	pageViewModePagesTest
);

test.describe('UI Infrastructure WidgetPage Use Cases', () => {
	const CLAY_PORTLET_NAME = 'Clay Sample';

	function getPortletByName({
		page,
		portletName,
	}: {
		page: Page;
		portletName: string;
	}) {
		return page.getByRole('heading', {
			name: portletName,
		});
	}

	async function expectPortletInColumn({
		columnNumber,
		page,
		portletName,
	}: {
		columnNumber: number;
		page: Page;
		portletName: string;
	}) {
		await expect(
			page
				.getByRole('main')
				.locator('.portlet-column')
				.filter({has: getPortletByName({page, portletName})})
		).toHaveId(`column-${columnNumber}`);
	}

	test.beforeEach(
		async ({
			apiHelpers,
			page,
			pageConfigurationPage,
			pagesAdminPage,
			site,
		}) => {
			const layoutTitle = getRandomString();

			const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
				groupId: site.id,
				title: layoutTitle,
			});

			await pagesAdminPage.goto(site.friendlyUrlPath);

			await pageConfigurationPage.goToSection(layoutTitle, 'General');

			await page.getByTitle('3 Columns', {exact: true}).click();

			const card = page.locator('.card.card-interactive').first();

			await expect(card).toHaveClass(/active/);

			await pageConfigurationPage.save();

			await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyURL}`);
		}
	);

	test('Portlet can be dragged and dropped', async ({
		page,
		widgetPagePage,
	}) => {
		await test.step('Add portlet to the first column in the page layout', async () => {
			await widgetPagePage.addPortlet(CLAY_PORTLET_NAME);

			await expectPortletInColumn({
				columnNumber: 1,
				page,
				portletName: CLAY_PORTLET_NAME,
			});
		});

		await test.step('Drag and drop portlet to another column in page', async () => {
			await widgetPagePage.dragPortlet({
				portletName: CLAY_PORTLET_NAME,
				target: page
					.getByRole('main')
					.locator('.portlet-column .portlet-dropzone.empty')
					.first(),
				topperSelector: '.portlet .portlet-topper',
			});

			await expectPortletInColumn({
				columnNumber: 2,
				page,
				portletName: CLAY_PORTLET_NAME,
			});
		});
	});

	test('Portlet can be removed', async ({page, widgetPagePage}) => {
		await test.step('Add portlet to the page layout', async () => {
			await widgetPagePage.addPortlet(CLAY_PORTLET_NAME);

			expect(
				getPortletByName({page, portletName: CLAY_PORTLET_NAME})
			).toBeVisible();
		});

		await test.step('Delete portlet from the page', async () => {
			await widgetPagePage.deletePortlet(CLAY_PORTLET_NAME);

			expect(
				getPortletByName({page, portletName: CLAY_PORTLET_NAME})
			).toBeHidden();
		});
	});

	test('Portlets have defined limits in the 3-column page layout.', async ({
		page,
		widgetPagePage,
	}) => {
		const MESSAGE_BOARDS_PORTLET_NAME = 'Message Boards';
		const DOCUMENTS_AND_MEDIA_PORTLET_NAME = 'Documents and Media';

		await test.step('Add portlet to the third column in the page layout', async () => {
			await widgetPagePage.addPortlet(DOCUMENTS_AND_MEDIA_PORTLET_NAME);

			await widgetPagePage.dragPortlet({
				portletName: DOCUMENTS_AND_MEDIA_PORTLET_NAME,
				target: page.locator('#layout-column_column-3'),
				topperSelector: '.portlet .portlet-topper',
			});
		});

		await test.step('Add portlet to the second column', async () => {
			await widgetPagePage.addPortlet(MESSAGE_BOARDS_PORTLET_NAME);

			await widgetPagePage.dragPortlet({
				portletName: MESSAGE_BOARDS_PORTLET_NAME,
				target: page.locator('#layout-column_column-2'),
				topperSelector: '.portlet .portlet-topper',
			});
		});

		await test.step('Add portlet to the first column', async () => {
			await widgetPagePage.addPortlet(CLAY_PORTLET_NAME);
		});

		await test.step('Verify that all portlets are in the correct column', async () => {
			for (const [index, portlet] of [
				CLAY_PORTLET_NAME,
				MESSAGE_BOARDS_PORTLET_NAME,
				DOCUMENTS_AND_MEDIA_PORTLET_NAME,
			].entries()) {
				await expectPortletInColumn({
					columnNumber: index + 1,
					page,
					portletName: portlet,
				});
			}
		});
	});
});

test('Control menu on home page is styled correctly after refreshing page.', async ({
	page,
}) => {
	const expectControlMenuStyles = async () => {
		const controlMenu = page.locator('#controlMenu');
		await expect(controlMenu).toHaveCSS('display', 'flex');
		await expect(controlMenu).toHaveCSS('list-style', 'outside none none');
	};

	await test.step('Navigate to home page and close product menu', async () => {
		await page.goto(liferayConfig.environment.baseUrl);
		await closeProductMenu(page);
	});

	await test.step('Verify styles after refresh with menu closed', async () => {
		await page.reload();
		await expectControlMenuStyles();
	});

	await test.step('Open product menu and verify styles after refresh', async () => {
		await openProductMenu(page);
		await page.reload();
		await expectControlMenuStyles();
	});
});
