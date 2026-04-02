/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {consentManagerConfigurationPageTest} from '../../../fixtures/consentManagerConfigurationPageTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {systemSettingsPageTest} from '../../../fixtures/systemSettingsPageTest';
import {ConsentManagerConfigurationPage} from '../../../pages/cookies-banner-web/ConsentManagerConfigurationPage';
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
];

export const test = mergeTests(
	consentManagerConfigurationPageTest,
	featureFlagsTest({
		'LPD-36105': {enabled: true},
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
		await clearConsentCookies(systemSettingsPage.page);
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
		const cookiesBanner = page.getByRole('dialog', {
			name: 'banner cookies',
		});

		await expect(cookiesBanner).toBeVisible();

		await page.getByRole('button', {name: 'Accept All'}).click();
	});
});

test(
	'Consent Renewal Period configuration field validation',
	{tag: '@LPD-68505'},
	async ({consentManagerConfigurationPage}) => {
		await test.step('Validate Consent Renewal Period field', async () => {
			await test.step('Validate default value of 12 months', async () => {
				await expect(
					consentManagerConfigurationPage.consentRenewalPeriodInput
				).toHaveValue('12');
			});

			await test.step('Validate value cannot be less than 1', async () => {
				await expect(
					consentManagerConfigurationPage.consentRenewalPeriodInput
				).toHaveAttribute('min', '1');
				await validateConsentRenewalPeriodValue(
					consentManagerConfigurationPage,
					false,
					'0',
					false
				);
			});

			await test.step('Validate value cannot be more than 12', async () => {
				await expect(
					consentManagerConfigurationPage.consentRenewalPeriodInput
				).toHaveAttribute('max', '12');
				await validateConsentRenewalPeriodValue(
					consentManagerConfigurationPage,
					false,
					'13',
					false
				);
			});

			await test.step('Validate value cannot be null', async () => {
				await validateConsentRenewalPeriodValue(
					consentManagerConfigurationPage,
					false,
					'',
					false
				);
			});

			await test.step('Validate value must be a number', async () => {
				await expect(
					consentManagerConfigurationPage.consentRenewalPeriodInput
				).toHaveAttribute('type', 'number');
			});
		});

		await test.step('Verify alert appears if changing the value', async () => {
			consentManagerConfigurationPage.page.once(
				'dialog',
				async (dialogWindow) => {
					expect(dialogWindow.message()).toContain(
						'You are about to change the consent renewal period'
					);

					await dialogWindow.dismiss();
				}
			);
		});

		await test.step('Verify dismissing the dialog does not change configuration value', async () => {
			await validateConsentRenewalPeriodValue(
				consentManagerConfigurationPage,
				false,
				'1',
				false
			);
		});

		await test.step('Verify accepting dialog updates configuration value and Cookies Banner appears again', async () => {
			consentManagerConfigurationPage.page.once(
				'dialog',
				async (dialogWindow) => {
					await dialogWindow.accept();
				}
			);
			await validateConsentRenewalPeriodValue(
				consentManagerConfigurationPage,
				false,
				'1',
				true
			);
		});
	}
);

test(
	'Verify alert only appears if changing the value',
	{tag: '@LPD-79710'},
	async ({consentManagerConfigurationPage, page}) => {
		await consentManagerConfigurationPage.enabledCheckbox.setChecked(true);

		await consentManagerConfigurationPage.consentRenewalPeriodInput.fill(
			'12'
		);

		await consentManagerConfigurationPage.updateButton.click();

		await page.waitForTimeout(1000);

		const cookiesBanner = page.getByRole('dialog', {
			name: 'banner cookies',
		});

		await expect(cookiesBanner).not.toBeVisible();

		await consentManagerConfigurationPage.consentRenewalPeriodInput.fill(
			'11'
		);

		page.once('dialog', async (dialogWindow) => {
			expect(dialogWindow.message()).toContain(
				'You are about to change the consent renewal period'
			);

			await dialogWindow.accept();
		});

		await consentManagerConfigurationPage.updateButton.click();

		await expect(cookiesBanner).toBeVisible();
	}
);

test(
	'Verify Consent Manager can be saved with Enabled set to false',
	{tag: '@LPD-78627'},
	async ({consentManagerConfigurationPage, page}) => {
		await test.step('Disable Consent Manager and save configuration', async () => {
			await updateConsentManagerConfiguration(page, {
				enabled: false,
			});

			await expect(
				consentManagerConfigurationPage.enabledCheckbox
			).not.toBeChecked();
		});
	}
);

test(
	'Verify Consent Renewal Period can be changed immediately after checking Enabled',
	{tag: '@LPD-79710'},
	async ({consentManagerConfigurationPage}) => {
		await consentManagerConfigurationPage.enabledCheckbox.setChecked(false);

		await expect(
			consentManagerConfigurationPage.consentRenewalPeriodInput
		).not.toBeEnabled();

		await consentManagerConfigurationPage.enabledCheckbox.setChecked(true);

		await expect(
			consentManagerConfigurationPage.consentRenewalPeriodInput
		).toBeEnabled();
	}
);

test(
	'Verify Consent Renewal Period correctly sets cookie expiration',
	{tag: '@LPD-68505'},
	async ({consentManagerConfigurationPage}) => {
		await validateConsentRenewalPeriodCookieExpiration(
			consentManagerConfigurationPage,
			false
		);
	}
);

test(
	'Verify Consent Renewal Period for Dissent correctly sets cookie expiration',
	{tag: '@LPD-80057'},
	async ({consentManagerConfigurationPage}) => {
		await validateConsentRenewalPeriodCookieExpiration(
			consentManagerConfigurationPage,
			true
		);
	}
);

test(
	'Verify Consent Renewal Period for Dissent correctly sets cookie expiration after 1 day',
	{tag: '@LPD-84142'},
	async ({consentManagerConfigurationPage}) => {
		await validateConsentRenewalPeriodCookieExpiration(
			consentManagerConfigurationPage,
			true,
			'1',
			'days'
		);
	}
);

test(
	'Verify Consent Renewal Period for Dissent correctly sets cookie expiration after 1 week',
	{tag: '@LPD-84142'},
	async ({consentManagerConfigurationPage}) => {
		await validateConsentRenewalPeriodCookieExpiration(
			consentManagerConfigurationPage,
			true,
			'1',
			'weeks'
		);
	}
);

test(
	'Verify updating Consent Renewal Period removes consent cookies',
	{tag: '@LPD-68505'},
	async ({page}) => {
		await test.step('Verify all consent cookies are set', async () => {
			const cookies = await page.context().cookies();

			for (const cookieKey of cookieKeys) {
				const cookie = cookies.find(
					(cookie) => cookie.name === cookieKey
				);

				expect(cookie).toBeDefined();
			}
		});

		await test.step('Update Consent Renewal Period and expect cookies banner to appear', async () => {
			await updateConsentManagerConfiguration(page, {
				consentRenewalPeriod: '2',
			});

			await page
				.getByRole('dialog', {name: 'banner cookies'})
				.waitFor({state: 'visible'});
		});

		await test.step('Verify no consent cookies are set', async () => {
			await page.reload();

			await page.waitForLoadState();

			const cookies = await page.context().cookies();

			for (const cookieKey of cookieKeys) {
				const cookie = cookies.find(
					(cookie) => cookie.name === cookieKey
				);

				expect(cookie).toBeUndefined();
			}
		});
	}
);

async function validateConsentRenewalPeriodCookieExpiration(
	consentManagerConfigurationPage: ConsentManagerConfigurationPage,
	dissent: boolean,
	newValue = '1',
	unit: 'days' | 'weeks' | 'months' = 'months'
) {
	const dateBeforeCookiesSet = new Date().getTime();

	await test.step(`Set Consent Renewal Period to ${newValue} ${unit}`, async () => {
		consentManagerConfigurationPage.page.once(
			'dialog',
			async (dialogWindow) => {
				await dialogWindow.accept();
			}
		);
		await validateConsentRenewalPeriodValue(
			consentManagerConfigurationPage,
			dissent,
			newValue,
			true,
			unit
		);
	});

	const cookies = await consentManagerConfigurationPage.page
		.context()
		.cookies();

	let userConsentConfiguredDate;

	await test.step('Verify USER_CONSENT_CONFIGURED_DATE cookie value is now', async () => {
		const cookie = cookies.find(
			(cookie) => cookie.name === 'USER_CONSENT_CONFIGURED_DATE'
		);

		expect(cookie).toBeDefined();

		userConsentConfiguredDate = Number(cookie.value);

		expect(userConsentConfiguredDate).toBeGreaterThanOrEqual(
			dateBeforeCookiesSet
		);
		expect(userConsentConfiguredDate).toBeLessThanOrEqual(
			new Date().getTime()
		);
	});

	await test.step(`Verify Consent Cookies expire in ${newValue} ${unit}`, async () => {
		const period = Number(newValue);
		const secondsInDay = 60 * 60 * 24;

		let expirationOffsetInSeconds = secondsInDay * 365 * (period / 12);

		if (unit === 'days') {
			expirationOffsetInSeconds = secondsInDay * period;
		}
		else if (unit === 'weeks') {
			expirationOffsetInSeconds = secondsInDay * 7 * period;
		}

		const expectedExpiration = Math.floor(
			userConsentConfiguredDate / 1000 + expirationOffsetInSeconds
		);

		for (const cookieKey of cookieKeys) {
			const cookie = cookies.find((cookie) => cookie.name === cookieKey);

			expect(cookie).toBeDefined();

			// Normalize cookie.expires by removing millis

			const cookieExpiration = Number(cookie.expires.toFixed());

			// Expect expiration within +/- 1 second

			expect(cookieExpiration).toBeGreaterThanOrEqual(
				expectedExpiration - 1
			);
			expect(cookieExpiration).toBeLessThanOrEqual(
				expectedExpiration + 1
			);
		}
	});
}

async function validateConsentRenewalPeriodValue(
	consentManagerConfigurationPage: ConsentManagerConfigurationPage,
	dissent: boolean,
	newValue: string,
	saveSuccessful: boolean,
	unit: 'days' | 'weeks' | 'months' = 'months'
) {
	let consentRenewalPeriodField =
		consentManagerConfigurationPage.consentRenewalPeriodInput;

	let expectedValue = await consentRenewalPeriodField.getAttribute('value');

	if (dissent) {
		consentRenewalPeriodField =
			consentManagerConfigurationPage.consentRenewalPeriodForDissentInput;

		await consentManagerConfigurationPage.dissentRenewalPeriodUnitSelect.selectOption(
			unit
		);
	}

	await consentRenewalPeriodField.fill(newValue);

	await consentManagerConfigurationPage.updateButton.click();

	if (saveSuccessful) {
		expectedValue = newValue;

		await waitForAlert(consentManagerConfigurationPage.page);

		await consentManagerConfigurationPage.page
			.getByRole('dialog', {name: 'banner cookies'})
			.waitFor({state: 'visible'});

		if (dissent) {
			await consentManagerConfigurationPage.page
				.getByRole('button', {name: 'Decline All'})
				.click();
		}
		else {
			await consentManagerConfigurationPage.page
				.getByRole('button', {name: 'Accept All'})
				.click();
		}
	}
	else {
		await consentManagerConfigurationPage.page.reload();

		await consentManagerConfigurationPage.page.waitForLoadState();
	}

	await expect(consentRenewalPeriodField).toHaveValue(expectedValue);
}
