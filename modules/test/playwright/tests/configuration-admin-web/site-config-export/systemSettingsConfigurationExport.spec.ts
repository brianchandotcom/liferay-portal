/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {siteSettingsPagesTest} from '../../../fixtures/siteSettingsPagesTest';
import {virtualInstancesPagesTest} from '../../../fixtures/virtualInstancesPagesTest';
import performLogin from '../../../utils/performLogin';
import {PORTLET_URLS} from '../../../utils/portletUrls';
import {waitForSPAToBeLoaded} from '../../../utils/waitForSPAToBeLoaded';

export const test = mergeTests(
	apiHelpersTest,
	loginTest(),
	featureFlagsTest({
		'LPS-155284': {enabled: true},
		'LPS-178052': {enabled: true},
	}),
	siteSettingsPagesTest,
	isolatedSiteTest,
	virtualInstancesPagesTest
);

const DEFAULT_VIRTUAL_INSTANCE_NAME = 'www.able.com';

test('Check that Site OSGi configurations can be used across different systems.', async ({
	browser,
	page,
	site,
	siteSettingsPage,
	virtualInstancesPage,
}) => {
	const firstAllowedAccountType = page
		.getByLabel('Allowed Account Types')
		.first()
		.getByRole('combobox');

	let virtualInstancePage: Page;

	async function expectExportedConfigurationToHaveSiteName(
		systemUrl: string,
		siteName: string
	) {
		expect(await siteSettingsPage.getExportedConfiguration()).toContain(
			`groupKey="${systemUrl}--${siteName}"`
		);
	}

	async function goToAccountsConfiguration(systemPage: Page, site?: Site) {
		systemPage.goto(
			`/group${site?.friendlyUrlPath || '/guest'}${PORTLET_URLS.siteSettings}`
		);

		await systemPage
			.getByRole('link', {
				exact: true,
				name: 'Accounts',
			})
			.click();

		await systemPage
			.getByRole('menuitem', {
				exact: true,
				name: 'Accounts',
			})
			.click();

		await waitForSPAToBeLoaded(systemPage);
	}

	await test.step('Assert default configuration is applied', async () => {
		await goToAccountsConfiguration(page, site);

		await expect(siteSettingsPage.defaultValuesAlert).toBeVisible();
		await expect(firstAllowedAccountType).toHaveText('Business');

		await siteSettingsPage.saveConfiguration();

		await expect(siteSettingsPage.defaultValuesAlert).toBeHidden();
		await expectExportedConfigurationToHaveSiteName(
			'liferay.com',
			site.name
		);
	});

	try {
		await test.step('Create new virtual instance and login to add the new site', async () => {
			test.slow();

			await virtualInstancesPage.addNewVirtualInstance(
				DEFAULT_VIRTUAL_INSTANCE_NAME
			);

			virtualInstancePage = await browser.newPage({
				baseURL: `http://${DEFAULT_VIRTUAL_INSTANCE_NAME}:8080`,
			});

			await performLogin(
				virtualInstancePage,
				'test',
				'',
				`@${DEFAULT_VIRTUAL_INSTANCE_NAME}.com`
			);
		});

		await test.step('Assert custom configuration is applied and can be restored to default', async () => {
			await goToAccountsConfiguration(virtualInstancePage);

			await expect(siteSettingsPage.defaultValuesAlert).toBeHidden();

			await expect(firstAllowedAccountType).not.toHaveText('Business');

			await expectExportedConfigurationToHaveSiteName(
				DEFAULT_VIRTUAL_INSTANCE_NAME,
				'Guest'
			);

			await siteSettingsPage.resetToDefaultValues();

			await expect(siteSettingsPage.defaultValuesAlert).toBeVisible();
			await expect(firstAllowedAccountType).toHaveText('Business');
		});
	}
	finally {
		if (virtualInstancePage) {
			await virtualInstancePage.close();
		}

		await virtualInstancesPage.deleteVirtualInstance(
			DEFAULT_VIRTUAL_INSTANCE_NAME
		);
	}
});
