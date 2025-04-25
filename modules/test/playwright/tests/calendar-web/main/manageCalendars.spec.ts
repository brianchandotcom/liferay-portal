/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {calendarPagesTest} from '../../../fixtures/calendarPagesTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import getRandomString from '../../../utils/getRandomString';
import getPageDefinition from '../../layout-content-page-editor-web/main/utils/getPageDefinition';
import getWidgetDefinition from '../../layout-content-page-editor-web/main/utils/getWidgetDefinition';

export const test = mergeTests(
	apiHelpersTest,
	calendarPagesTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest
);

test.beforeEach(
	async ({apiHelpers, calendarWidgetPage, page, pageEditorPage, site}) => {
		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([
				getWidgetDefinition({
					id: getRandomString(),
					widgetName:
						'com_liferay_calendar_web_portlet_CalendarPortlet',
				}),
			]),
			siteId: site.id,
			title: getRandomString(),
		});

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		await calendarWidgetPage.setCalendarWidgetConfiguration(
			'Europe/Paris',
			false
		);

		await pageEditorPage.publishPage();

		await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`);
	}
);

test('color column in manage calendar page is updated when the user changes the calendar color', async ({
	calendarWidgetPage,
}) => {
	await calendarWidgetPage.unhideSidebar();

	await calendarWidgetPage.openCalendarActionsDropdownMenu('Test Test');

	await calendarWidgetPage.clickCalendarColor('#E0C240');

	await calendarWidgetPage.openCalendarGroupActionsDropdownMenu(
		'My Calendars'
	);
	await calendarWidgetPage.manageCalendarsMenuItem.click();

	await expect(
		calendarWidgetPage.page.locator('.calendar-portlet-color-box')
	).toHaveCSS('background-color', 'rgb(224, 194, 64)');
});
