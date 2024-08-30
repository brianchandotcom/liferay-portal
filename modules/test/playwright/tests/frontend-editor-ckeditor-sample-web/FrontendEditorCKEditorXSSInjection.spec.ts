/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {liferayConfig} from '../../liferay.config';
import getRandomString from '../../utils/getRandomString';
import getPageDefinition from '../layout-content-page-editor-web/utils/getPageDefinition';
import getWidgetDefinition from '../layout-content-page-editor-web/utils/getWidgetDefinition';

export const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	loginTest()
);

test.describe('CKEditor Sample Web', () => {
	test('XSS injection doesnt get invoked', async ({
		apiHelpers,
		page,
		site,
	}) => {
		let layout: Layout;

		await test.step('Create a content site and the CKEditor sample widget', async () => {
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

		await test.step('Click on the "Go to XSS" button', async () => {
			await page.goto(
				`${liferayConfig.environment.baseUrl}/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
			);

			const gotToXSSViewButton = page.getByText('Go to XSS View');

			await expect(gotToXSSViewButton).toBeInViewport();

			gotToXSSViewButton.click();
		});

		await test.step('Check that XSS was not executed', async () => {
			const sampleEditorContainer = page.locator(
				'[id="\\<\\/script\\>\\<scrIpt\\>alert\\(12451\\)\\;\\<\\/scRipt\\>\\<script\\>sampleLegacyEditorContainer"]'
			);

			await expect(sampleEditorContainer).toBeInViewport();

			await page.waitForTimeout(500);

			await expect(page.locator('body')).not.toHaveText('12451');
		});
	});
});
