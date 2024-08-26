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

const recurrence = {
	frequency: 'WEEKLY',
	ocurrences: '2',
	repeatDays: ['Wednesday'],
} as Recurrence;

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

test('can create all-day calendar event with different time zone', async ({
	calendarWidgetPage,
}) => {
	await calendarWidgetPage.addEvent(true);

	const endTime = await calendarWidgetPage.endTime.inputValue();
	const startTime = await calendarWidgetPage.startTime.inputValue();

	await expect(endTime).toEqual(startTime);
});

test('can create an all-day calendar event in a different time zone, ensuring that the recurrence link remains consistent', async ({
	calendarWidgetPage,
}) => {
	await calendarWidgetPage.fillEventWithRecurrence(true, recurrence);

	const {frequency, ocurrences, repeatDays} = recurrence;

	const expectedLink =
		frequency +
		', on ' +
		repeatDays.join(',') +
		', ' +
		ocurrences +
		' Times';

	await expect(
		calendarWidgetPage.page.frameLocator('iframe').getByRole('link', {
			name: expectedLink,
		})
	).toBeVisible();

	await calendarWidgetPage.publishEvent();

	await expect(
		calendarWidgetPage.page.frameLocator('iframe').getByRole('link', {
			name: expectedLink,
		})
	).toBeVisible();
});
