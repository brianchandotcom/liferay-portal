/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../../../fixtures/loginTest';
import {liferayConfig} from '../../../../../liferay.config';
import getRandomString from '../../../../../utils/getRandomString';
import getPageDefinition from '../../../../layout-content-page-editor-web/main/utils/getPageDefinition';
import getWidgetDefinition from '../../../../layout-content-page-editor-web/main/utils/getWidgetDefinition';

export const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest()
);

test('Check toolbar button is added to Alloy Editor', async ({
	apiHelpers,
	page,
	site,
}) => {
	let layout: Layout;

	await test.step('Create page with CKEditor sample widget', async () => {
		const widgetDefinition = getWidgetDefinition({
			id: getRandomString(),
			widgetName:
				'com_liferay_editor_ckeditor_sample_web_internal_portlet_CKEditorSamplePortlet',
		});

		layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([widgetDefinition]),
			siteId: site.id,
			title: getRandomString(),
		});
	});

	await test.step('Check Client Extension is added to Alloy Editor sample ', async () => {
		await page.goto(
			`${liferayConfig.environment.baseUrl}/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
		);

		await page.getByRole('link', {name: 'CKEditor 4'}).click();

		await page.getByRole('link', {name: 'Alloy'}).click();

		await page.getByText('Lorem ipsum').selectText();

		await expect(page.getByTitle('Insert Video')).toBeInViewport();
	});
});
