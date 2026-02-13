/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {systemSettingsPageTest} from '../../../fixtures/systemSettingsPageTest';
import {waitForAlert} from '../../../utils/waitForAlert';
import {
	clearConsentCookies,
	resetConsentManagerConfiguration,
	updateConsentManagerConfiguration,
} from './utils/consentManagerConfigurationHelper';

const cookieKeys = [
	'CONSENT_TYPE_FUNCTIONAL',
	'CONSENT_TYPE_NECESSARY',
	'CONSENT_TYPE_PERFORMANCE',
	'CONSENT_TYPE_PERSONALIZATION',
	'USER_CONSENT_CONFIGURED',
	'USER_CONSENT_CONFIGURED_DATE',
]; //

export const test = mergeTests(
	featureFlagsTest({
		'LPD-75032': {enabled: true},
	}),
	loginTest(),
	systemSettingsPageTest
);

test.afterEach(async ({systemSettingsPage}) => {
	await test.step('Reset Consent Manager Configuration', async () => {
		await resetConsentManagerConfiguration(systemSettingsPage);
	});

	await test.step('Clear Consent Cookies if present', async () => {
		await clearConsentCookies(systemSettingsPage);
	});
});

test.beforeEach(async ({page}) => {
	await test.step('Enable Consent Manager', async () => {
		await updateConsentManagerConfiguration(page, {
			enabled: true,
			forceReload: true,
		});
	});

	await test.step('Verify Cookies Banner appears, then Accept All cookies', async () => {
		const cookiesBanner = await page.locator(
			'#p_p_id_com_liferay_cookies_banner_web_portlet_CookiesBannerPortlet_'
		);

		await expect(cookiesBanner).toBeVisible();

		await page.getByRole('button', {name: 'Accept All'}).click();
	});
});

test(
	'Consent Renewal Period configuration field validation',
	{tag: '@LPD-68505'},
	async ({page}) => {
		const consentRenewalPeriodField = await page.getByLabel(
			'Consent Renewal Period'
		);

		await test.step('Validate Consent Renewal Period field', async () => {
			await test.step('Validate default value of 12 months', async () => {
				await expect(await consentRenewalPeriodField).toHaveValue('12');
			});

			await test.step('Validate value cannot be less than 1', async () => {
				await expect(await consentRenewalPeriodField).toHaveAttribute(
					'min',
					'1'
				);
				await validateConsentRenewalPeriodValue('0', page, false);
			});

			await test.step('Validate value cannot be more than 12', async () => {
				await expect(await consentRenewalPeriodField).toHaveAttribute(
					'max',
					'12'
				);
				await validateConsentRenewalPeriodValue('13', page, false);
			});

			await test.step('Validate value cannot be null', async () => {
				await validateConsentRenewalPeriodValue('', page, false);
			});

			await test.step('Validate value must be a number', async () => {
				await expect(await consentRenewalPeriodField).toHaveAttribute(
					'type',
					'number'
				);
			});
		});

		await test.step('Verify alert appears if changing the value', async () => {
			page.once('dialog', async (dialogWindow) => {
				await expect(dialogWindow.message()).toContain(
					'You are about to change the consent renewal period'
				);

				await dialogWindow.dismiss();
			});
		});

		await test.step('Verify dismissing the dialog does not change configuration value', async () => {
			await validateConsentRenewalPeriodValue('1', page, false);
		});

		await test.step('Verify accepting dialog updates configuration value and Cookies Banner appears again', async () => {
			page.once('dialog', async (dialogWindow) => {
				await dialogWindow.accept();
			});
			await validateConsentRenewalPeriodValue('1', page, true);
		});
	}
);

test(
	'Verify Consent Manager can be saved with Enabled set to false',
	{tag: '@LPD-78627'},
	async ({page}) => {
		await test.step('Disable Consent Manager and save configuration', async () => {
			await updateConsentManagerConfiguration(page, {
				enabled: false,
			});

			await expect(page.getByLabel('Enabled')).not.toBeChecked();
		});
	}
);

test(
	'Verify Consent Renewal Period correctly sets cookie expiration',
	{tag: '@LPD-68505'},
	async ({page}) => {
		const dateBeforeCookiesSet = new Date().getTime();

		await test.step('Set Consent Renewal Period to 1 month', async () => {
			page.once('dialog', async (dialogWindow) => {
				await dialogWindow.accept();
			});
			await validateConsentRenewalPeriodValue('1', page, true);
		});

		const cookies = await page.context().cookies();

		let userConsentConfiguredDate;

		await test.step('Verify USER_CONSENT_CONFIGURED_DATE cookie value is now', async () => {
			const cookie = await cookies.find(
				(cookie) => cookie.name === 'USER_CONSENT_CONFIGURED_DATE'
			);

			await expect(cookie).toBeDefined();

			userConsentConfiguredDate = Number(cookie.value);

			await expect(userConsentConfiguredDate).toBeGreaterThanOrEqual(
				dateBeforeCookiesSet
			);
			await expect(userConsentConfiguredDate).toBeLessThanOrEqual(
				new Date().getTime()
			);
		});

		await test.step('Verify Consent Cookies expire in 1 month', async () => {
			const oneMonthFromNowInSeconds =
				userConsentConfiguredDate / 1000 +
				60 * 60 * 24 * 365 * (1 / 12);

			for (const cookieKey of cookieKeys) {
				const cookie = await cookies.find(
					(cookie) => cookie.name === cookieKey
				);

				await expect(cookie).toBeDefined();

				// Normalize cookie.expires by removing millis

				const cookieExpiration = Number(cookie.expires.toFixed());

				// Expect expiration within +/- 1 second

				await expect(cookieExpiration).toBeGreaterThanOrEqual(
					oneMonthFromNowInSeconds - 1
				);
				await expect(cookieExpiration).toBeLessThanOrEqual(
					oneMonthFromNowInSeconds + 1
				);
			}
		});
	}
);

test(
	'Verify updating Consent Renewal Period removes consent cookies',
	{tag: '@LPD-68505'},
	async ({page}) => {
		await test.step('Verify all consent cookies are set', async () => {
			const cookies = await page.context().cookies();

			for (const cookieKey of cookieKeys) {
				const cookie = await cookies.find(
					(cookie) => cookie.name === cookieKey
				);

				await expect(cookie).toBeDefined();
			}
		});

		await test.step('Update Consent Renewal Period and expect cookies banner to appear', async () => {
			await updateConsentManagerConfiguration(page, {
				consentRenewalPeriod: '2',
			});

			await expect(
				await page.locator(
					'#p_p_id_com_liferay_cookies_banner_web_portlet_CookiesBannerPortlet_'
				)
			).toBeVisible();
		});

		await test.step('Verify no consent cookies are set', async () => {
			await page.reload();

			await page.waitForLoadState();

			const cookies = await page.context().cookies();

			for (const cookieKey of cookieKeys) {
				const cookie = await cookies.find(
					(cookie) => cookie.name === cookieKey
				);

				await expect(cookie).toBeUndefined();
			}
		});
	}
);

async function validateConsentRenewalPeriodValue(
	newValue: string,
	page,
	saveSuccessful: boolean
) {
	const consentRenewalPeriodField = await page.getByLabel(
		'Consent Renewal Period'
	);

	let expectedValue = await consentRenewalPeriodField.getAttribute('value');

	await consentRenewalPeriodField.fill(newValue);

	await page.getByRole('button', {name: 'Update'}).click();

	if (saveSuccessful) {
		expectedValue = newValue;

		await waitForAlert(page);

		await expect(
			await page.locator(
				'#p_p_id_com_liferay_cookies_banner_web_portlet_CookiesBannerPortlet_'
			)
		).toBeVisible();

		await page.getByRole('button', {name: 'Accept All'}).click();
	}
	else {
		await page.reload();

		await page.waitForLoadState();
	}

	await expect(await consentRenewalPeriodField).toHaveValue(expectedValue);
}
