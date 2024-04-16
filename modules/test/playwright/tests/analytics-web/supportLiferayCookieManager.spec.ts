/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginAnalyticsCloudTest} from '../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../fixtures/loginTest';
import {liferayConfig} from '../../liferay.config';
import {
	connectToAnalyticsCloud,
	disconnectFromAnalyticsCloud,
	goToAnalyticsCloudInstanceSettings,
	syncAllContacts,
	syncSite,
} from '../analytics-settings-web/utils/analyticsSettings';
import {createDataSource} from '../osb-faro-web/utils/dataSource';

export const test = mergeTests(
	loginAnalyticsCloudTest(),
	loginTest(),
	featureFlagsTest({
		'LPD-10588': true,
	})
);

async function changeCookiePreference(
	page,
	{enableCookieBanner, enableExplicitCookieConsentMode}
) {
	await page.getByLabel('Open Applications MenuCtrl+Alt+A').click();

	await page.getByRole('tab', {name: 'Control Panel'}).click();

	await page.getByRole('menuitem', {name: 'Instance Settings'}).click();

	await page.getByRole('link', {name: 'Cookies'}).click();

	if (enableCookieBanner) {
		await page.getByLabel('Enabled').check();
	}
	else {
		await page.getByLabel('Enabled').uncheck();
	}

	if (enableExplicitCookieConsentMode) {
		await page.getByLabel('Explicit Cookie Consent Mode').check();
	}
	else {
		await page.getByLabel('Explicit Cookie Consent Mode').uncheck();
	}

	const submitButton = await page.$(
		'button[data-qa-id="submitConfiguration"]'
	);

	await submitButton.click();

	await page.waitForTimeout(3000);
}

async function connectACToDXP(page) {
	await createDataSource(page);

	await goToAnalyticsCloudInstanceSettings(page);

	const cookieBannerElement = await page.$('div.portlet-cookies-banner');

	if (cookieBannerElement) {
		await cookieBannerElement.evaluate((div) => {
			div.style.display = 'none';
		});
	}

	await disconnectFromAnalyticsCloud(page);

	await connectToAnalyticsCloud(page);

	await syncSite(page);

	await syncAllContacts(page);

	await page.getByRole('button', {name: 'Finish'}).click();
}

async function checkAnalyticsInstance(page) {
	return await page.evaluate(() => {

		// @ts-ignore

		return !!window.Analytics && !window.Analytics._disposed;
	});
}

async function cleanVMCache(page) {
	await page.goto(liferayConfig.environment.baseUrl);

	await page.getByLabel('Open Applications MenuCtrl+Alt+A').click();

	await page.getByRole('tab', {name: 'Control Panel'}).click();

	await page.getByRole('menuitem', {name: 'Server Administration'}).click();

	await page.waitForTimeout(3000);

	const cleanVMCacheButton = await page.$('button[data-cmd="cacheSingle"]');

	if (cleanVMCacheButton) {
		await cleanVMCacheButton.click();

		await page.waitForTimeout(3000);

		await page.getByText('Success:Your request completed successfully.');
	}
}

test.describe('LPD-6540 Support Liferay Cookie Manager', () => {
	test.beforeEach(async ({page}) => {
		await cleanVMCache(page);

		await connectACToDXP(page);
	});

	test('When Cookie Preference Handling and Explicit Cookie Consent Mode are both Enabled, AC tracking should be enabled as soon the user accepts the performance cookies', async ({
		page,
	}) => {
		await changeCookiePreference(page, {
			enableCookieBanner: true,
			enableExplicitCookieConsentMode: true,
		});

		await page.goto(liferayConfig.environment.baseUrl);

		await page.getByRole('button', {name: 'Accept All'}).click();

		await page.waitForTimeout(3000);

		expect(await checkAnalyticsInstance(page)).toBeTruthy();
	});

	test('When Cookie Preference Handling and Explicit Cookie Consent Mode are both Enabled, AC tracking should be disabled if end user did not accept the perfomance cookies', async ({
		page,
	}) => {
		await changeCookiePreference(page, {
			enableCookieBanner: true,
			enableExplicitCookieConsentMode: true,
		});

		await page.goto(liferayConfig.environment.baseUrl);

		await page.getByRole('button', {name: 'Decline All'}).click();

		await page.waitForTimeout(3000);

		expect(await checkAnalyticsInstance(page)).toBeFalsy();
	});

	test('When Cookie Preference Handling is Enabled and Explicit Cookie Consent Mode is not Enabled, AC tracking should be enabled by default ', async ({
		page,
	}) => {
		await changeCookiePreference(page, {
			enableCookieBanner: true,
			enableExplicitCookieConsentMode: false,
		});

		await page.goto(liferayConfig.environment.baseUrl);

		expect(await checkAnalyticsInstance(page)).toBeTruthy();
	});

	test('When Cookie Preference Handling is Enabled and Explicit Cookie Consent Mode is not Enabled, AC tracking should be enabled until the user rejects the performance cookies', async ({
		page,
	}) => {
		await changeCookiePreference(page, {
			enableCookieBanner: true,
			enableExplicitCookieConsentMode: false,
		});

		await page.goto(liferayConfig.environment.baseUrl);

		expect(await checkAnalyticsInstance(page)).toBeTruthy();

		await page.getByRole('button', {name: 'Decline All'}).click();

		await page.waitForTimeout(3000);

		expect(await checkAnalyticsInstance(page)).toBeFalsy();
	});

	test('When Cookie Preference Handling is not Enabled, AC tracking should be enabled by default', async ({
		page,
	}) => {
		await changeCookiePreference(page, {
			enableCookieBanner: false,
			enableExplicitCookieConsentMode: false,
		});

		await page.goto(liferayConfig.environment.baseUrl);

		expect(await checkAnalyticsInstance(page)).toBeTruthy();
	});
});
