/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../../fixtures/applicationsMenuPageTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageViewModePagesTest} from '../../../fixtures/pageViewModePagesTest';
import getRandomString from '../../../utils/getRandomString';
import {navigationMenusPagesTest} from './../../site-navigation-admin-web/main/fixtures/navigationMenusPagesTest';

export const test = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	isolatedSiteTest,
	loginTest(),
	navigationMenusPagesTest,
	pageViewModePagesTest
);

test(
	'Select page as root menu item for Navigation Menu widget',
	{
		tag: '@LPD-50258',
	},
	async ({apiHelpers, page, site, widgetPagePage}) => {
		const parentLayout = await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			title: getRandomString(),
		});

		const childLayout = await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			parentLayoutId: parentLayout.layoutId,
			title: getRandomString(),
		});

		await page.goto(
			`/web${site.friendlyUrlPath}${parentLayout.friendlyURL}`
		);

		await widgetPagePage.clickOnAction('Menu Display', 'Configuration');

		const configurationIFrame = page.frameLocator(
			'iframe[title*="Menu Display"]'
		);

		await configurationIFrame
			.getByLabel('Start with Menu Items In')
			.selectOption('Select Parent');

		await configurationIFrame
			.getByRole('button', {name: 'Menu Item'})
			.click();

		await configurationIFrame
			.frameLocator('iframe[title="Select Site Navigation Menu Item"]')
			.getByText('Pages Hierarchy')
			.click();
		await configurationIFrame
			.frameLocator('iframe[title="Select Site Navigation Menu Item"]')
			.getByText(parentLayout.nameCurrentValue)
			.click();

		await widgetPagePage.saveAndClose('Menu Display');

		await expect(
			page.getByRole('menuitem', {name: childLayout.nameCurrentValue})
		).toBeVisible();

		await widgetPagePage.clickOnAction('Menu Display', 'Configuration');

		await expect(
			configurationIFrame.getByText(parentLayout.nameCurrentValue)
		).toBeVisible();
	}
);

test(
	'Add URL type Navigation Menu Item with "open in a new tab" checkbox unchecked',
	{
		tag: '@LPD-50258',
	},
	async ({apiHelpers, navigationMenusPage, page, site, widgetPagePage}) => {
		const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			title: getRandomString(),
		});

		await navigationMenusPage.goto(site.friendlyUrlPath);

		const navigationMenuName = getRandomString();

		await navigationMenusPage.createNavigationMenu(navigationMenuName);

		const urlItemName = getRandomString();

		await navigationMenusPage.addURLItem(urlItemName);

		await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyURL}`);

		await page.waitForTimeout(300);

		await widgetPagePage.clickOnAction('Menu Display', 'Configuration');

		const configurationIFrame = page.frameLocator(
			'iframe[title*="Menu Display"]'
		);

		await page.waitForTimeout(300);

		await configurationIFrame.getByLabel('Choose Menu').check();

		await configurationIFrame.getByRole('button', {name: 'Select'}).click();

		await page.waitForTimeout(300);

		await configurationIFrame
			.frameLocator('iframe[title="Select Site Navigation Menu"]')
			.getByRole('cell', {name: navigationMenuName})
			.click();

		await configurationIFrame.getByRole('button', {name: 'Save'}).click();

		await widgetPagePage.saveAndClose('Menu Display');

		await page.getByText(urlItemName).click();

		const currentURL = page.url();

		expect(currentURL).toContain('https://www.liferay.com');
	}
);

test(
	'Add URL type Navigation Menu Item with "open in a new tab" checkbox checked',
	{
		tag: '@LPD-50258',
	},
	async ({apiHelpers, navigationMenusPage, page, site, widgetPagePage}) => {
		const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			title: getRandomString(),
		});

		await navigationMenusPage.goto(site.friendlyUrlPath);

		const navigationMenuName = getRandomString();

		await navigationMenusPage.createNavigationMenu(navigationMenuName);

		const urlItemName = getRandomString();

		await navigationMenusPage.addURLItem(urlItemName, undefined, true);

		await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyURL}`);

		await page.waitForTimeout(300);

		await widgetPagePage.clickOnAction('Menu Display', 'Configuration');

		const configurationIFrame = page.frameLocator(
			'iframe[title*="Menu Display"]'
		);

		await page.waitForTimeout(300);

		await configurationIFrame.getByLabel('Choose Menu').check();

		await configurationIFrame.getByRole('button', {name: 'Select'}).click();

		await page.waitForTimeout(300);

		await configurationIFrame
			.frameLocator('iframe[title="Select Site Navigation Menu"]')
			.getByRole('cell', {name: navigationMenuName})
			.click();

		await configurationIFrame.getByRole('button', {name: 'Save'}).click();

		await widgetPagePage.saveAndClose('Menu Display');

		await page.getByText(urlItemName).click();

		const [newPage] = await Promise.all([
			await page.context().waitForEvent('page'),
			await page.getByText(urlItemName).click(),
		]);

		await newPage.waitForLoadState();

		const newTabURL = newPage.url();

		expect(newTabURL).toContain('https://www.liferay.com');
	}
);
