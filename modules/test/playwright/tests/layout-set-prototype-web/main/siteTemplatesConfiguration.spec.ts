/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';

const test = mergeTests(apiHelpersTest, loginTest());

const testWithSiteTemplateSync = mergeTests(
	test,
	featureFlagsTest({
		'LPD-82107': {enabled: true},
	})
);

test(
	'Show the legacy propagation help message when LPD-82107 is disabled',
	{tag: '@LPD-87021'},
	async ({apiHelpers, page}) => {
		const layoutSetPrototype =
			await apiHelpers.jsonWebServicesLayoutSetPrototype.addLayoutSetPrototypes(
				{name: 'SiteTemplate-' + getRandomString()}
			);

		try {
			await page.goto(
				`/group/template-${layoutSetPrototype.layoutSetPrototypeId}/~/control_panel/manage?p_p_id=com_liferay_layout_set_prototype_web_portlet_SiteTemplateSettingsPortlet`
			);

			await expect(
				page
					.locator('label', {
						has: page.locator('input[name$="layoutsUpdateable"]'),
					})
					.locator('.taglib-icon-help')
			).toHaveAttribute(
				'title',
				'Set this to allow site administrators to add, remove, or configure applications and change page properties. If site administrators modify a page, changes to the original site template page are no longer propagated. If this option is set, it is possible to disallow modifications from specific pages through the page management tool.'
			);
		}
		finally {
			await apiHelpers.jsonWebServicesLayoutSetPrototype.deleteLayoutSetPrototypes(
				layoutSetPrototype.layoutSetPrototypeId
			);
		}
	}
);

testWithSiteTemplateSync(
	'Show the sync help message when LPD-82107 is enabled',
	{tag: '@LPD-87021'},
	async ({apiHelpers, page}) => {
		const layoutSetPrototype =
			await apiHelpers.jsonWebServicesLayoutSetPrototype.addLayoutSetPrototypes(
				{name: 'SiteTemplate-' + getRandomString()}
			);

		try {
			await page.goto(
				`/group/template-${layoutSetPrototype.layoutSetPrototypeId}/~/control_panel/manage?p_p_id=com_liferay_layout_set_prototype_web_portlet_SiteTemplateSettingsPortlet`
			);

			await expect(
				page
					.locator('label', {
						has: page.locator('input[name$="layoutsUpdateable"]'),
					})
					.locator('.taglib-icon-help')
			).toHaveAttribute(
				'title',
				'Set this to allow site administrators to add, remove, or configure applications and change page properties. If site administrators modify a page, the changes will be overwritten the next time the site template is synced.'
			);
		}
		finally {
			await apiHelpers.jsonWebServicesLayoutSetPrototype.deleteLayoutSetPrototypes(
				layoutSetPrototype.layoutSetPrototypeId
			);
		}
	}
);
