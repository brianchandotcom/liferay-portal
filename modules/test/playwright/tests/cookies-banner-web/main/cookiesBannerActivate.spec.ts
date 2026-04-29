/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {accountSettingsPagesTest} from '../../../fixtures/accountSettingsPagesTest';
import {consentManagerConfigurationPageTest} from '../../../fixtures/consentManagerConfigurationPageTest';
import {loginTest} from '../../../fixtures/loginTest';
import {systemSettingsPageTest} from '../../../fixtures/systemSettingsPageTest';
import {ConsentManagerConfigurationPage} from '../../../pages/cookies-banner-web/ConsentManagerConfigurationPage';
import {waitForAlert} from '../../../utils/waitForAlert';
import {
	clearConsentCookies,
	resetConsentManagerConfiguration,
	updateConsentManagerConfiguration,
} from './utils/consentManagerConfigurationHelper';

export const test = mergeTests(
	accountSettingsPagesTest,
	consentManagerConfigurationPageTest,
	loginTest(),
	systemSettingsPageTest
);

async function toggleActivedAndWait(
	consentManagerConfigurationPage: ConsentManagerConfigurationPage
) {
	const {toggleActivateButton, toggleDeactivateButton} =
		consentManagerConfigurationPage;

	if (await toggleDeactivateButton.isVisible()) {
		await toggleDeactivateButton.click();
	}
	else {
		await toggleActivateButton.click();
	}

	await waitForAlert(consentManagerConfigurationPage.page);
}

test.afterEach(async ({systemSettingsPage}) => {
	await test.step('Reset Consent Manager Configuration', async () => {
		await resetConsentManagerConfiguration(systemSettingsPage);
	});

	await test.step('Clear Consent Cookies if present', async () => {
		await clearConsentCookies(systemSettingsPage.page);
	});
});

test(
	'Enabling alone does not render the Cookies Banner',
	{tag: '@LPD-87281'},
	async ({
		browser,
		consentManagerConfigurationPage,
		page,
		systemSettingsPage,
	}) => {
		await test.step('Enable Consent Manager leaving actived off', async () => {
			await updateConsentManagerConfiguration(page, {
				actived: false,
				enabled: true,
				forceReload: true,
			});
		});

		await test.step('Verify the Cookies Banner is not rendered for a guest user', async () => {
			const guestPage = await browser.newPage();

			await guestPage.goto('/');

			await expect(
				guestPage.getByRole('dialog', {name: 'banner cookies'})
			).not.toBeVisible();

			await guestPage.close();
		});

		await test.step('Verify the Activate button is visible on the Consent Manager tab', async () => {
			await consentManagerConfigurationPage.goTo();

			await expect(
				consentManagerConfigurationPage.toggleActivateButton
			).toBeVisible();
		});

		await test.step('Verify Cookie Banner and Cookie Panel sub-tabs are visible under Privacy', async () => {
			await systemSettingsPage.goToSystemSetting(
				'Privacy',
				'Consent Manager'
			);

			const privacyMenu =
				systemSettingsPage.page.locator('#main-content');

			await expect(
				privacyMenu.getByRole('menuitem', {
					exact: true,
					name: 'Cookie Banner',
				})
			).toBeVisible();
			await expect(
				privacyMenu.getByRole('menuitem', {
					exact: true,
					name: 'Cookie Panel',
				})
			).toBeVisible();
		});
	}
);

test(
	'Clicking Activate renders the Cookies Banner and shows Data And Privacy',
	{tag: '@LPD-87281'},
	async ({
		accountSettingsPage,
		browser,
		consentManagerConfigurationPage,
		page,
	}) => {
		await test.step('Enable Consent Manager leaving actived off', async () => {
			await updateConsentManagerConfiguration(page, {
				actived: false,
				enabled: true,
				forceReload: true,
			});
		});

		await test.step('Click Activate and verify the button now reads Deactivate', async () => {
			await toggleActivedAndWait(consentManagerConfigurationPage);

			await expect(
				consentManagerConfigurationPage.toggleDeactivateButton
			).toBeVisible();
		});

		await test.step('Verify the Cookies Banner and floating icon render for a guest user', async () => {
			const guestPage = await browser.newPage();

			await guestPage.goto('/');

			await expect(
				guestPage.getByRole('dialog', {name: 'banner cookies'})
			).toBeVisible();

			await guestPage.close();
		});

		await test.step('Verify the Data And Privacy tab is visible for end users', async () => {
			await accountSettingsPage.goToAccountSettings();

			await expect(
				page.locator('.nav-link', {
					hasText: 'Data And Privacy',
				})
			).toBeVisible();
		});
	}
);

test(
	'Clicking Deactivate hides the Cookies Banner and Data And Privacy while keeping sub-tabs visible',
	{tag: '@LPD-87281'},
	async ({
		accountSettingsPage,
		browser,
		consentManagerConfigurationPage,
		page,
		systemSettingsPage,
	}) => {
		await test.step('Enable and activate Consent Manager', async () => {
			await updateConsentManagerConfiguration(page, {
				enabled: true,
				forceReload: true,
			});

			await expect(
				consentManagerConfigurationPage.toggleDeactivateButton
			).toBeVisible();
		});

		await test.step('Click Deactivate and verify the button now reads Activate', async () => {
			await toggleActivedAndWait(consentManagerConfigurationPage);

			await expect(
				consentManagerConfigurationPage.toggleActivateButton
			).toBeVisible();
		});

		await test.step('Verify the Cookies Banner no longer renders for a guest user', async () => {
			const guestPage = await browser.newPage();

			await guestPage.goto('/');

			await expect(
				guestPage.getByRole('dialog', {name: 'banner cookies'})
			).not.toBeVisible();

			await guestPage.close();
		});

		await test.step('Verify Cookie Banner and Cookie Panel sub-tabs remain visible', async () => {
			await systemSettingsPage.goToSystemSetting(
				'Privacy',
				'Consent Manager'
			);

			const privacyMenu =
				systemSettingsPage.page.locator('#main-content');

			await expect(
				privacyMenu.getByRole('menuitem', {
					exact: true,
					name: 'Cookie Banner',
				})
			).toBeVisible();
			await expect(
				privacyMenu.getByRole('menuitem', {
					exact: true,
					name: 'Cookie Panel',
				})
			).toBeVisible();
		});

		await test.step('Verify the Data And Privacy tab is hidden again for end users', async () => {
			await accountSettingsPage.goToAccountSettings();

			await expect(
				page.locator('.nav-link', {
					hasText: 'Data And Privacy',
				})
			).not.toBeVisible();
		});
	}
);

test(
	'Saving while active shows a confirmation modal that can be cancelled or confirmed',
	{tag: '@LPD-87281'},
	async ({consentManagerConfigurationPage, page}) => {
		await test.step('Enable and activate Consent Manager', async () => {
			await updateConsentManagerConfiguration(page, {
				enabled: true,
				forceReload: true,
				storeConsent: false,
			});

			await expect(
				consentManagerConfigurationPage.toggleDeactivateButton
			).toBeVisible();
		});

		await test.step('Decline cookies banner so it stops covering the form', async () => {
			const banner = page.getByRole('dialog', {
				name: 'banner cookies',
			});

			await banner.waitFor({state: 'visible'});

			await banner.getByRole('button', {name: 'Decline All'}).click();

			await banner.waitFor({state: 'hidden'});
		});

		await test.step('Cancel the confirmation modal and verify Store Consent is not persisted', async () => {
			await consentManagerConfigurationPage.storeConsentCheckbox.setChecked(
				true
			);

			await consentManagerConfigurationPage.updateButton.click();

			const modal = page.getByRole('alertdialog');

			await expect(modal).toContainText(
				'These changes will take effect immediately'
			);

			await modal.getByRole('button', {name: 'Cancel'}).click();

			await modal.waitFor({state: 'hidden'});

			await consentManagerConfigurationPage.goTo();

			await expect(
				consentManagerConfigurationPage.storeConsentCheckbox
			).not.toBeChecked();
		});

		await test.step('Confirm the modal with the force re-consent checkbox marked and verify Store Consent is persisted', async () => {
			await consentManagerConfigurationPage.storeConsentCheckbox.setChecked(
				true
			);

			await consentManagerConfigurationPage.updateButton.click();

			const modal = page.getByRole('alertdialog');

			await modal
				.getByRole('checkbox', {name: /force re-consent/i})
				.check();

			await modal.getByRole('button', {name: 'OK'}).click();

			await waitForAlert(page);

			await consentManagerConfigurationPage.goTo();

			await expect(
				consentManagerConfigurationPage.storeConsentCheckbox
			).toBeChecked();
		});

		await test.step('Verify the cookies banner is shown again after force re-consent', async () => {
			await page.goto('/');

			await expect(
				page.getByRole('dialog', {name: 'banner cookies'})
			).toBeVisible();
		});
	}
);
