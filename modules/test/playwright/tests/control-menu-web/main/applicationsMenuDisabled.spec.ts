/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {instanceSettingsPagesTest} from '../../../fixtures/instanceSettingsPagesTest';
import {loginTest} from '../../../fixtures/loginTest';
import {productMenuPageTest} from '../../../fixtures/productMenuPageTest';
import {InstanceSettingsPage} from '../../../pages/configuration-admin-web/InstanceSettingsPage';

async function goToApplicationsMenuSetting(
	instanceSettingsPage: InstanceSettingsPage
) {
	await instanceSettingsPage.goto();

	await instanceSettingsPage.goToInstanceSetting(
		'Navigation',
		'Applications Menu'
	);
}

const test = mergeTests(
	instanceSettingsPagesTest,
	productMenuPageTest,
	loginTest()
);

test.beforeEach(async ({instanceSettingsPage, page, productMenuPage}) => {
	await goToApplicationsMenuSetting(instanceSettingsPage);

	const checkbox = page.getByRole('checkbox', {
		name: 'Enable Applications Menu',
	});

	if (await checkbox.isChecked()) {
		await checkbox.uncheck();
	}

	await instanceSettingsPage.saveAndWaitForAlert();

	await productMenuPage.openProductMenuIfClosed();
});

test.afterEach(async ({instanceSettingsPage}) => {
	await goToApplicationsMenuSetting(instanceSettingsPage);

	await instanceSettingsPage.resetInstanceSetting();
});

test(
	'The Product Menu replaces the Applications Menu when it is disabled (default instance)',
	{tag: '@LPD-66980'},
	async ({page, productMenuPage}) => {
		const testPages = [
			{
				category: 'Content',
				name: 'Asset Libraries',
				panel: 'Applications',
			},
			{category: 'Order Management', name: 'Orders', panel: 'Commerce'},
			{category: 'Sites', name: 'Sites', panel: 'Control Panel'},
		];

		for (const {category, name, panel} of testPages) {
			await test.step(`Navigate to ${panel} > ${category} > ${name} via Product Menu`, async () => {
				await productMenuPage.goToPortlet(panel, category, name);

				await expect(
					page.getByRole('heading', {exact: true, name})
				).toBeVisible();
			});
		}
	}
);
