/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, expect, mergeTests} from '@playwright/test';

import {accountSettingsPagesTest} from '../../../fixtures/accountSettingsPagesTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {systemSettingsPageTest} from '../../../fixtures/systemSettingsPageTest';
import {waitForAlert} from '../../../utils/waitForAlert';
import {
	clearConsentCookies,
	resetAllConsentManagerConfigurations,
} from './utils/consentManagerAfterEach';

const cookieHeadingNames = [
	'Functional Cookies',
	'Performance Cookies',
	'Personalization Cookies',
];

const cookieKeys = [
	'CONSENT_TYPE_FUNCTIONAL',
	'CONSENT_TYPE_PERFORMANCE',
	'CONSENT_TYPE_PERSONALIZATION',
];

export const test = mergeTests(
	accountSettingsPagesTest,
	featureFlagsTest({
		'LPD-75032': {enabled: true},
	}),
	loginTest(),
	systemSettingsPageTest
);

test.afterEach(async ({systemSettingsPage}) => {
	await test.step('Reset All Consent Manager Configurations', async () => {
		await resetAllConsentManagerConfigurations(systemSettingsPage);
	});

	await test.step('Clear Consent Cookies if present', async () => {
		await clearConsentCookies(systemSettingsPage);
	});
});

test.beforeEach(async ({systemSettingsPage}) => {
	await test.step('Enable Preference Handling Cookies', async () => {
		await systemSettingsPage.goToSystemSetting(
			'Privacy',
			'Consent Manager'
		);

		const enabledButton = systemSettingsPage.page.getByLabel('Enabled');

		await enabledButton.waitFor({state: 'visible'});

		await systemSettingsPage.page.waitForTimeout(3000);

		const isChecked = await enabledButton.isChecked();

		if (!isChecked) {
			await enabledButton.click();

			await systemSettingsPage.page
				.getByRole('button', {name: 'Save'})
				.click();

			await waitForAlert(systemSettingsPage.page);
		}

		await expect(enabledButton).toBeChecked();
	});
});

test(
	'Verify Cookie Banner Consent Panel buttons',
	{tag: '@LPD-67119'},
	async ({page}) => {
		await test.step('Open the Cookie Banner Consent Panel', async () => {
			await page.goto('/');

			const cookiesBanner = await page.locator(
				'#p_p_id_com_liferay_cookies_banner_web_portlet_CookiesBannerPortlet_'
			);

			await cookiesBanner.waitFor();

			await cookiesBanner
				.getByRole('button', {name: 'Configuration'})
				.click();
		});

		await test.step('Verify all button names and ordering', async () => {
			const consentPanelFooter = await page.locator(
				'[class="modal-footer"]'
			);

			await expectCookieConsentPanelButtons(await consentPanelFooter);
		});
	}
);

test(
	'Verify Consent Manager buttons',
	{tag: '@LPD-67119'},
	async ({accountSettingsPage}) => {
		await test.step('Go to Consent Manager Account Settings page', async () => {
			await accountSettingsPage.goToDataAndPrivacy();

			await accountSettingsPage.page
				.getByText('Consent Manager')
				.first()
				.waitFor();

			if (await accountSettingsPage.consentManagerMenuItem.isVisible()) {
				await accountSettingsPage.consentManagerMenuItem.click();
			}
		});

		await test.step('Verify all button names and ordering', async () => {
			const cookiesManagerConsentPanel =
				await accountSettingsPage.page.locator(
					'[id="_com_liferay_my_account_web_portlet_MyAccountPortlet_cookiesBannerConfigurationForm"]'
				);

			await expectCookieConsentPanelButtons(
				await cookiesManagerConsentPanel
			);
		});
	}
);

test(
	'Verify Consent Manager can be accessed from the Data And Privacy Account Settings tab',
	{tag: '@LPD-60007'},
	async ({accountSettingsPage, page}) => {
		await test.step('AC1: Verify Data And Privacy tab exists within Account Settings', async () => {
			await accountSettingsPage.goToAccountSettings();

			const dataAndPrivacyTab = await accountSettingsPage.page.locator(
				'.nav-link',
				{
					hasText: 'Data And Privacy',
				}
			);

			await expect(await dataAndPrivacyTab).toBeVisible();
		});

		await test.step('AC2: Verify Consent Manager panel is visible from Data and Privacy tab', async () => {
			await accountSettingsPage.goToDataAndPrivacy();

			await accountSettingsPage.page
				.getByText('Consent Manager')
				.first()
				.waitFor();

			if (await accountSettingsPage.consentManagerMenuItem.isVisible()) {
				await accountSettingsPage.consentManagerMenuItem.click();
			}

			for (const cookieHeadingName of cookieHeadingNames) {
				const cookieHeading = await page.getByRole('heading', {
					name: cookieHeadingName,
				});

				await expect(await cookieHeading).toBeVisible();
			}
		});
	}
);

test(
	'Verify Cookie Preferences can be saved from the new Consent Manager page',
	{tag: '@LPD-60007'},
	async ({accountSettingsPage, page}) => {
		await test.step('Enable all cookie types', async () => {
			await accountSettingsPage.goToDataAndPrivacy();

			await accountSettingsPage.page
				.getByText('Consent Manager')
				.first()
				.waitFor();

			if (await accountSettingsPage.consentManagerMenuItem.isVisible()) {
				await accountSettingsPage.consentManagerMenuItem.click();
			}

			await accountSettingsPage.page
				.getByRole('button', {name: 'Accept All'})
				.click();

			await accountSettingsPage.page.waitForTimeout(1000);
		});

		await test.step('Verify all cookie types have been enabled', async () => {
			const actualCookies = await page.context().cookies();

			for (const cookieKey of cookieKeys) {
				const cookieKeyToggle = await page.locator(
					`[data-cookie-key="${cookieKey}"]`
				);

				await expect(await cookieKeyToggle).toBeChecked();

				const actualCookie = await actualCookies.find(
					(actualCookie) => actualCookie.name === cookieKey
				);

				await expect(actualCookie).toBeDefined();
				await expect(actualCookie.value).toEqual('true');
			}
		});
	}
);

test(
	'Escape texts in Cookie Banner to avoid XSS injections',
	{tag: '@LPD-69398'},
	async ({page, systemSettingsPage}) => {
		const script = '<script>alert("Hello world!")</script>';

		page.on('dialog', async (dialog) => {
			if (dialog.type() === 'alert') {
				throw new Error('XSS detected');
			}
		});

		await systemSettingsPage.goToSystemSetting('Privacy', 'Cookie Banner');

		await page.getByLabel('Title', {exact: true}).fill(script);
		await page.getByLabel('Content', {exact: true}).fill(script);
		await page.getByLabel('Privacy Policy Link').fill(script);
		await page.getByLabel('Link Display Text', {exact: true}).fill(script);

		await page.getByRole('button', {name: 'Save'}).dispatchEvent('click');

		await waitForAlert(page);
	}
);

test(
	'Escape texts in Cookie Panel to avoid XSS injections',
	{tag: '@LPD-69399'},
	async ({page, systemSettingsPage}) => {
		const script = '<script>alert("Hello world!")</script>';

		page.on('dialog', async (dialog) => {
			if (dialog.type() === 'alert') {
				throw new Error('XSS detected');
			}
		});

		await systemSettingsPage.goToSystemSetting('Privacy', 'Cookie Panel');

		await page.getByLabel('Title', {exact: true}).waitFor();

		await page.getByLabel('Title', {exact: true}).fill(script);
		await page.getByLabel('Description', {exact: true}).fill(script);
		await page.getByLabel('Cookie Policy Link').fill(script);
		await page.getByLabel('Link Display Text', {exact: true}).fill(script);
		await page
			.getByLabel('Strictly Necessary Cookies Description', {exact: true})
			.fill(script);
		await page
			.getByLabel('Functional Cookies Description', {exact: true})
			.fill(script);
		await page
			.getByLabel('Performance Cookies Description', {exact: true})
			.fill(script);
		await page
			.getByLabel('Personalization Cookies Description', {exact: true})
			.fill(script);

		await page.getByRole('button', {name: 'Save'}).dispatchEvent('click');

		await waitForAlert(page);

		const cookiesBanner = await page.locator(
			'#p_p_id_com_liferay_cookies_banner_web_portlet_CookiesBannerPortlet_'
		);

		await cookiesBanner.waitFor();

		await cookiesBanner
			.getByRole('button', {name: 'Configuration'})
			.click();
	}
);

async function expectCookieConsentPanelButtons(locator: Locator) {
	await locator.waitFor();

	const buttons = await locator.getByRole('button').all();

	expect(buttons).toHaveLength(3);
	await expect(await buttons[0]).toContainText('Use Necessary Cookies Only');
	await expect(await buttons[1]).toContainText('Accept Selected');
	await expect(await buttons[2]).toContainText('Accept All');
}
