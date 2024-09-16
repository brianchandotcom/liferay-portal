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

test('LPD-30368 Label is being escaped', async ({apiHelpers, page, site}) => {
	let layout: Layout;

	await test.step('Create a content site and the frontend taglib clay widget', async () => {
		const widgetDefinition = getWidgetDefinition({
			id: getRandomString(),
			widgetName: 'com_liferay_clay_sample_web_portlet_ClaySamplePortlet',
		});

		layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([widgetDefinition]),
			siteId: site.id,
			title: getRandomString(),
		});
	});

	await test.step('Check that alert did not pop up', async () => {
		await page.goto(
			`${liferayConfig.environment.baseUrl}/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
		);

		const verticalNavTab = page
			.getByRole('tablist')
			.getByText('Vertical Nav');

		await verticalNavTab.waitFor({state: 'visible'});

		let alertText = '';

		page.on('dialog', (dialog) => {
			alertText = dialog.message();
			dialog.dismiss();
		});

		expect(alertText).toEqual('');
	});
});
