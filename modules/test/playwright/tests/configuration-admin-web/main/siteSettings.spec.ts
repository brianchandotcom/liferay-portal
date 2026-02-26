/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {accessibilityMenuPagesTest} from '../../../fixtures/accessibilityMenuPagesTest';
import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {instanceSettingsPagesTest} from '../../../fixtures/instanceSettingsPagesTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {siteSettingsPagesTest} from '../../../fixtures/siteSettingsPagesTest';
import {getExportedConfiguration} from '../../../utils/getExportedConfiguration';

const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	siteSettingsPagesTest,
	accessibilityMenuPagesTest,
	instanceSettingsPagesTest,
	featureFlagsTest({
		'LPD-36105': {enabled: true},
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
		expect(await getExportedConfiguration(page)).toContain(
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

test('LPD-38043 Assert that a configuration at the site scope can override a configuration from the instance scope', async ({
	accessibilityMenuPage,
	instanceSettingsPage,
	page,
	site,
	siteSettingsPage,
}) => {
	await siteSettingsPage.goto(site.friendlyUrlPath);

	if (await accessibilityMenuPage.isAccessibilityMenuAttached()) {
		await test.step('Disable the instance scoped accessibility menu configuration', async () => {
			await instanceSettingsPage.goToInstanceSetting(
				'Accessibility',
				'Accessibility Menu'
			);

			await accessibilityMenuPage.enableAccessibilityMenuCheckbox.uncheck();

			await instanceSettingsPage.saveAndWaitForAlert();
		});
	}

	await test.step('Check that the accessibility menu is not accessible in the site scope', async () => {
		await siteSettingsPage.goToSiteSetting(
			'Accessibility',
			'Accessibility Menu',
			site.friendlyUrlPath
		);

		await page.waitForLoadState();

		await expect(
			accessibilityMenuPage.openAccessibilityMenuButton
		).not.toBeAttached();
	});

	await test.step('Enable the site accessibility menu configuration', async () => {
		await accessibilityMenuPage.enableAccessibilityMenu();
	});

	await test.step('Check that the accessibility menu is accessible in the site scope', async () => {
		await expect(
			accessibilityMenuPage.openAccessibilityMenuButton
		).toBeAttached();
	});

	await test.step('Check that the accessibility menu is not accessible in the instance scope', async () => {
		await instanceSettingsPage.goToInstanceSetting(
			'Accessibility',
			'Accessibility Menu'
		);

		await page.waitForLoadState();

		await expect(
			accessibilityMenuPage.openAccessibilityMenuButton
		).not.toBeAttached();
	});
});
