/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {calendarPagesTest} from '../../fixtures/calendarPagesTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../utils/getRandomString';
import getPageDefinition from '../layout-content-page-editor-web/utils/getPageDefinition';
import getWidgetDefinition from '../layout-content-page-editor-web/utils/getWidgetDefinition';

export const test = mergeTests(
	apiHelpersTest,
	calendarPagesTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest
);

let layout: Layout;

test.afterEach(async ({page, pageEditorPage, site}) => {
	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	await clickAndExpectToBeVisible({
		autoClick: true,
		target: page
			.locator('.dropdown-menu')
			.getByRole('menuitem', {name: 'Delete'}),
		trigger: page
			.locator('.control-menu-nav-item')
			.getByLabel('Options', {exact: true}),
	});

	await page.getByRole('button', {name: 'Delete'}).click();
});

test.beforeEach(async ({apiHelpers, page, pageEditorPage, site}) => {
	layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([
			getWidgetDefinition({
				id: getRandomString(),
				widgetName: 'com_liferay_calendar_web_portlet_CalendarPortlet',
			}),
		]),
		siteId: site.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	await pageEditorPage.publishPage();

	await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`);
});

test('can move between mini calendar gridcell child buttons using arrow keys', async ({
	calendarWidgetPage,
	page,
}) => {
	await calendarWidgetPage.miniCalendarGrid.focus();

	const gridMoviments = [
		{date: '1', key: 'ArrowRight'},
		{date: '2', key: 'ArrowRight'},
		{date: '9', key: 'ArrowDown'},
		{date: '8', key: 'ArrowLeft'},
	];

	for (const {date, key} of gridMoviments) {
		await page.keyboard.press(key);

		await expect(
			page.getByRole('button', {exact: true, name: date})
		).toBeFocused();

		await expect(
			page.getByRole('button', {exact: true, name: date})
		).toHaveAttribute('tabIndex', '0');
	}
});

test('ensure that mini calendar accessibility properties are maintained after changing months', async ({
	calendarWidgetPage,
}) => {
	await calendarWidgetPage.miniCalendarNextMonthButton.click();

	await expect(calendarWidgetPage.miniCalendarBase).toHaveAttribute(
		'role',
		'dialog'
	);

	await expect(calendarWidgetPage.miniCalendarHeaderLabel).toHaveAttribute(
		'role',
		'paragraph'
	);

	await expect(calendarWidgetPage.miniCalendarHeaderLabel).toHaveAttribute(
		'aria-live',
		'polite'
	);
});
