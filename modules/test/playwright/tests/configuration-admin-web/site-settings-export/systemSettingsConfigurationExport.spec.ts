/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {siteSettingsPagesTest} from '../../../fixtures/siteSettingsPagesTest';
import {virtualInstancesPagesTest} from '../../../fixtures/virtualInstancesPagesTest';
import {getExportedConfiguration} from '../../../utils/getExportedConfiguration';
import performLogin from '../../../utils/performLogin';
import {PORTLET_URLS} from '../../../utils/portletUrls';
import {waitForAlert} from '../../../utils/waitForAlert';
import {waitForSPAToBeLoaded} from '../../../utils/waitForSPAToBeLoaded';

export const test = mergeTests(
	apiHelpersTest,
	loginTest(),
	featureFlagsTest({
		'LPD-36105': {enabled: true},
		'LPS-155284': {enabled: true},
		'LPS-178052': {enabled: true},
	}),
	siteSettingsPagesTest,
	virtualInstancesPagesTest
);

const DEFAULT_VIRTUAL_INSTANCE_NAME = 'www.able.com';
const GUEST_SITE_NAME = 'Guest';

test('Check that Site OSGI configurations can be used across different systems.', async ({
	browser,
	page,
	siteSettingsPage,
	virtualInstancesPage,
}) => {
	let virtualInstancePage: Page;

	const firstAllowedAccountType = (targetPage: Page = page) =>
		targetPage
			.getByLabel('Allowed Account Types')
			.first()
			.getByRole('combobox');

	async function expectExportedConfigurationToHaveSiteName(
		systemUrl: string,
		siteName: string,
		targetPage?: Page
	) {
		expect(await getExportedConfiguration(targetPage || page)).toContain(
			`groupKey="${systemUrl}--${siteName}"`
		);
	}

	async function goToAccountsConfiguration(targetPage: Page) {
		await targetPage.goto(`/group/guest${PORTLET_URLS.siteSettings}`);

		await targetPage
			.getByRole('link', {
				exact: true,
				name: 'Accounts',
			})
			.click();

		await targetPage
			.getByRole('menuitem', {
				exact: true,
				name: 'Accounts',
			})
			.click();

		await waitForSPAToBeLoaded(targetPage);
	}

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

		await test.step('Assert default configuration is applied', async () => {
			const defaultValuesAlertVirtualInstance = virtualInstancePage
				.getByRole('alert')
				.first()
				.getByText(
					'Info:This configuration is not saved yet. The values shown are the default.'
				);

			await goToAccountsConfiguration(virtualInstancePage);

			await expect(defaultValuesAlertVirtualInstance).toBeVisible();

			await expect(
				firstAllowedAccountType(virtualInstancePage)
			).toHaveText('Business');

			await virtualInstancePage
				.getByRole('button', {name: 'Save'})
				.or(virtualInstancePage.getByRole('button', {name: 'Update'}))
				.click();

			await waitForAlert(virtualInstancePage);

			await expect(defaultValuesAlertVirtualInstance).toBeHidden();
			await expectExportedConfigurationToHaveSiteName(
				DEFAULT_VIRTUAL_INSTANCE_NAME,
				GUEST_SITE_NAME,
				virtualInstancePage
			);
		});

		await test.step('Assert custom configuration is applied and can be restored to default', async () => {
			await goToAccountsConfiguration(page);

			await expect(siteSettingsPage.defaultValuesAlert).toBeHidden();

			await expect(firstAllowedAccountType()).not.toHaveText('Business');

			await expectExportedConfigurationToHaveSiteName(
				'liferay.com',
				GUEST_SITE_NAME
			);

			await siteSettingsPage.resetToDefaultValues();

			await expect(siteSettingsPage.defaultValuesAlert).toBeVisible();
			await expect(firstAllowedAccountType()).toHaveText('Business');
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
