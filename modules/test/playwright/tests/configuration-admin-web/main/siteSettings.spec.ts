/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {instanceSettingsPagesTest} from '../../../fixtures/instanceSettingsPagesTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {siteSettingsPagesTest} from '../../../fixtures/siteSettingsPagesTest';

const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	siteSettingsPagesTest,
	instanceSettingsPagesTest,
	featureFlagsTest({
		'LPS-155284': {enabled: true},
		'LPS-178052': {enabled: true},
	}),
	loginTest()
);

test('Asserts that site scoped OSGi configurations can be used across different sites', async ({
	page,
	site,
	siteSettingsPage,
}) => {
	const firstAllowedAccountType = page
		.getByLabel('Allowed Account Types')
		.first()
		.getByRole('combobox');

	async function expectExportedConfigurationToHaveSiteName(siteName: string) {
		expect(await siteSettingsPage.getExportedConfiguration()).toContain(
			`groupKey="liferay.com--${siteName}"`
		);
	}

	async function goToAccountsConfiguration(site?: Site) {
		await siteSettingsPage.goToSiteSetting(
			'Accounts',
			'Accounts',
			site?.friendlyUrlPath
		);
	}

	await test.step('Assert default configuration is applied', async () => {
		await goToAccountsConfiguration(site);

		await expect(siteSettingsPage.defaultValuesAlert).toBeVisible();
		await expect(firstAllowedAccountType).toHaveText('Business');

		await siteSettingsPage.saveConfiguration();

		await expect(siteSettingsPage.defaultValuesAlert).toBeHidden();
		await expectExportedConfigurationToHaveSiteName(site.name);
	});

	await test.step('Assert custom configuration is applied and can be restored to default', async () => {
		await goToAccountsConfiguration();

		await expect(siteSettingsPage.defaultValuesAlert).toBeHidden();
		await expect(firstAllowedAccountType).not.toHaveText('Business');
		await expectExportedConfigurationToHaveSiteName('Guest');

		await siteSettingsPage.resetToDefaultValues();

		await expect(siteSettingsPage.defaultValuesAlert).toBeVisible();
		await expect(firstAllowedAccountType).toHaveText('Business');
	});
});
