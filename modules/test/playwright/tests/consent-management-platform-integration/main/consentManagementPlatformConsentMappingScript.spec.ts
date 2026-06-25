/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../../fixtures/loginTest';
import {SiteSettingsPage} from '../../../pages/site-admin-web/SiteSettingsPage';
import performLogin, {performLogout} from '../../../utils/performLogin';
import {waitForAlert} from '../../../utils/waitForAlert';

const test = mergeTests(loginTest());

test.afterEach(async ({page}) => {
	if (await page.getByRole('button', {name: 'Sign In'}).isVisible()) {
		await performLogin(page, 'test');
	}

	await setCMPEnabled(page, false);
});

test(
	'Sign-in does not set the REMEMBER_ME cookie when functional consent is denied via the third-party CMP',
	{tag: '@LPD-88765'},
	async ({page}) => {
		await setCMPEnabled(page, true);

		await signInWithConsentState(page, {CONSENT_TYPE_FUNCTIONAL: false});

		const cookies = await page.context().cookies();

		expect(
			cookies.find((cookie) => cookie.name === 'REMEMBER_ME')
		).toBeUndefined();
	}
);

test(
	'Sign-in sets the REMEMBER_ME cookie when functional consent is granted via the third-party CMP',
	{tag: '@LPD-88765'},
	async ({page}) => {
		await setCMPEnabled(page, true);

		await signInWithConsentState(page, {CONSENT_TYPE_FUNCTIONAL: true});

		const cookies = await page.context().cookies();

		expect(
			cookies.find((cookie) => cookie.name === 'REMEMBER_ME')
		).toBeDefined();
	}
);

test(
	'Necessary consent is honored even when the third-party CMP denies all consent',
	{tag: '@LPD-88765'},
	async ({page}) => {
		await setCMPEnabled(page, true);

		await signInWithConsentState(page, {
			CONSENT_TYPE_FUNCTIONAL: false,
			CONSENT_TYPE_NECESSARY: false,
			CONSENT_TYPE_PERFORMANCE: false,
			CONSENT_TYPE_PERSONALIZATION: false,
		});

		const cookies = await page.context().cookies();

		expect(
			cookies.find((cookie) => cookie.name === 'COMPANY_ID')
		).toBeDefined();

		expect(
			cookies.find((cookie) => cookie.name === 'REMEMBER_ME')
		).toBeUndefined();
	}
);

test(
	'Consent is granted when the third-party CMP sets no consent-state cookie',
	{tag: '@LPD-88765'},
	async ({page}) => {
		await setCMPEnabled(page, true);

		await signInWithConsentState(page, null);

		const cookies = await page.context().cookies();

		expect(
			cookies.find((cookie) => cookie.name === 'REMEMBER_ME')
		).toBeDefined();
	}
);

test(
	'Consent is granted when the consent type is absent from the consent state',
	{tag: '@LPD-88765'},
	async ({page}) => {
		await setCMPEnabled(page, true);

		await signInWithConsentState(page, {CONSENT_TYPE_NECESSARY: true});

		const cookies = await page.context().cookies();

		expect(
			cookies.find((cookie) => cookie.name === 'REMEMBER_ME')
		).toBeDefined();
	}
);

test(
	'Consent is granted when the consent state is malformed',
	{tag: '@LPD-88765'},
	async ({page}) => {
		await setCMPEnabled(page, true);

		await signInWithConsentState(page, 'not-json');

		const cookies = await page.context().cookies();

		expect(
			cookies.find((cookie) => cookie.name === 'REMEMBER_ME')
		).toBeDefined();
	}
);

async function setCMPEnabled(page: Page, enabled: boolean) {
	const siteSettingsPage = new SiteSettingsPage(page);

	await siteSettingsPage.goToSiteSetting(
		'Privacy',
		'Third Party Consent Management Platform'
	);

	await page
		.getByLabel('Consent Management Platform Provider Name', {exact: true})
		.fill('Playwright Mock CMP');

	await page
		.getByLabel('Script Tag', {exact: true})
		.fill('<script>/* mock CMP */</script>');

	await page.getByLabel('Enabled', {exact: true}).setChecked(enabled);

	await siteSettingsPage.saveButton.click();

	await waitForAlert(page);
}

type ConsentState = Record<string, boolean>;

async function signInWithConsentState(
	page: Page,
	consentState: ConsentState | string | null
) {
	await performLogout(page);

	await page.context().clearCookies({name: 'CONSENT_STATE'});

	if (consentState !== null) {
		await page.context().addCookies([
			{
				domain: new URL(page.url()).hostname,
				name: 'CONSENT_STATE',
				path: '/',
				value:
					typeof consentState === 'string'
						? consentState
						: encodeURIComponent(JSON.stringify(consentState)),
			},
		]);
	}

	await performLogin(page, 'test', '/');
}
