/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import getRandomString from '../../../utils/getRandomString';
import getPageDefinition from '../../layout-content-page-editor-web/main/utils/getPageDefinition';
import getWidgetDefinition from '../../layout-content-page-editor-web/main/utils/getWidgetDefinition';

export const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest
);

test('calendar name with special characters is displayed correctly', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {
	const layout = await apiHelpers.headlessDelivery.createSitePage({
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

	const {classNameId} =
		await apiHelpers.jsonWebServicesClassName.fetchClassName(
			'com.liferay.portal.kernel.model.Group'
		);

	const {calendarResourceId} =
		await apiHelpers.jsonWebServicesCalendarResource.fetchCalendarResource({
			classNameId,
			classPK: site.id,
		});

	const calendarName = getRandomString() + '&' + getRandomString();

	await apiHelpers.jsonWebServicesCalendar.addCalendar({
		calendarResourceId,
		descriptionMap: `{"en_US": "${getRandomString()}"}`,
		groupId: site.id,
		nameMap: `{"en_US": "${calendarName}"}`,
	});

	await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`);

	if (!(await page.locator('[id$="siteCalendarList"]').isVisible())) {
		await page.locator('[id$="columnToggler"]').click();
	}

	await expect(
		page.locator('[id$="siteCalendarList"]').getByLabel(calendarName)
	).toBeVisible();
});
