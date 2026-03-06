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
import {waitForAlert} from '../../../utils/waitForAlert';
import getPageDefinition from '../../layout-content-page-editor-web/main/utils/getPageDefinition';
import getWidgetDefinition from '../../layout-content-page-editor-web/main/utils/getWidgetDefinition';

export const test = mergeTests(
	apiHelpersTest,
	calendarPagesTest,
	featureFlagsTest({
		'LPD-11235': {enabled: false},
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

		await pageEditorPage.publishPage();

		await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`);

		await calendarWidgetPage.unhideSidebar();

		await calendarWidgetPage.openCalendarActionsDropdownMenu('Test Test');

		await page.getByRole('menuitem', {name: 'Calendar Settings'}).click();
	}
);

test('Notification Templates', async ({calendarWidgetPage, page}) => {
	await test.step('Navigate to Notification Templates tab', async () => {
		await page
			.frameLocator('iframe')
			.getByRole('link', {name: 'Notification Templates'})
			.click();
	});

	await test.step('Assert Body editor values persist', async () => {
		const bodyContent = 'Testing body content';

		await test.step('Fill in "Testing body content" in Body editor', async () => {
			await calendarWidgetPage.calendarSettingsNotificationTemplates.bodyEditor.fill(
				bodyContent
			);
		});

		await test.step('Save notification template', async () => {
			await page
				.frameLocator('iframe')
				.getByRole('button', {name: 'Save'})
				.click();

			await waitForAlert(
				page.frameLocator('iframe'),
				`Success:Your request completed successfully.`
			);
		});

		await test.step('Check that "Testing body content" is still present in Body editor', async () => {
			await expect(
				calendarWidgetPage.calendarSettingsNotificationTemplates
					.bodyEditor
			).toHaveText(bodyContent);
		});
	});
});
