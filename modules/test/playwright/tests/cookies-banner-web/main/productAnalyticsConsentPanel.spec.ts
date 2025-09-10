/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {instanceSettingsPagesTest} from '../../../fixtures/instanceSettingsPagesTest';
import {loginTest} from '../../../fixtures/loginTest';
import {productAnalyticsPagesTest} from '../../../fixtures/productAnalyticsPagesTest';
import {siteSettingsPagesTest} from '../../../fixtures/siteSettingsPagesTest';
import {systemSettingsPageTest} from '../../../fixtures/systemSettingsPageTest';
import {
	clearProductAnalyticsCookies,
	expectAllCookiesAccepted,
	expectAllCookiesDeclined,
} from './utils/cookies';

export const test = mergeTests(
	featureFlagsTest({
		'LPD-51356': {enabled: true},
	}),
	instanceSettingsPagesTest,
	loginTest(),
	productAnalyticsPagesTest,
	siteSettingsPagesTest,
	systemSettingsPageTest
);

export enum OptionalProductAnalyticsCookieTypes {
	Functional = 'PRODUCT_ANALYTICS_CONSENT_TYPE_FUNCTIONAL',
	Performance = 'PRODUCT_ANALYTICS_CONSENT_TYPE_PERFORMANCE',
	Personalization = 'PRODUCT_ANALYTICS_CONSENT_TYPE_PERSONALIZATION',
	ProductAnalytics = 'PRODUCT_ANALYTICS_CONSENT_TYPE_PRODUCT_ANALYTICS',
}

export type ProductAnalyticsCookie = {
	name: string;
	value: boolean;
};

export enum RequiredProductAnalyticsCookieTypes {
	Necessary = 'PRODUCT_ANALYTICS_CONSENT_TYPE_NECESSARY',
}

test.afterEach(async ({page}) => {
	await test.step('Clear Product Analytics cookies if present', async () => {
		await clearProductAnalyticsCookies(page);
	});
});

test(
	'Verify Product Analytics Consent Panel is present after clicking the Customize button on the Product Analytics Banner',
	{tag: '@LPD-60006'},
	async ({page, productAnalyticsBannerPage}) => {
		await test.step('AC1: Verify Customize button displays Consent Panel', async () => {
			await productAnalyticsBannerPage.customizeButton.click();

			await expect(
				await page.getByRole('heading', {
					name: 'Liferay Platform Consent Preferences',
				})
			).toBeVisible();
		});
	}
);

test(
	'Verify specific Consent types can be configured from the Consent Panel',
	{tag: '@LPD-60006'},
	async ({
		page,
		productAnalyticsBannerPage,
		productAnalyticsConsentPanelPage,
	}) => {
		await productAnalyticsBannerPage.customizeButton.click();

		await test.step('AC2: Verify Consent Panel displays all cookie types', async () => {
			for (const optionalProductAnalyticsCookieType of Object.values(
				OptionalProductAnalyticsCookieTypes
			)) {
				await expect(
					await productAnalyticsConsentPanelPage.getCookieTypeToggle(
						optionalProductAnalyticsCookieType
					)
				).toBeVisible();
			}
		});

		await test.step('AC3: Verify "Accept Selected" sets toggled cookies', async () => {
			for (const optionalProductAnalyticsCookieType of Object.values(
				OptionalProductAnalyticsCookieTypes
			)) {
				const toggle =
					await productAnalyticsConsentPanelPage.getCookieTypeToggle(
						optionalProductAnalyticsCookieType
					);

				await toggle.check();
			}

			await productAnalyticsConsentPanelPage.acceptSelectedButton.click();

			await expectAllCookiesAccepted(page);

			await clearProductAnalyticsCookies(page);
		});

		await test.step('AC3: Verify "Accept Selected" does not set untoggled cookies', async () => {
			await productAnalyticsBannerPage.customizeButton.click();

			for (const optionalProductAnalyticsCookieType of Object.values(
				OptionalProductAnalyticsCookieTypes
			)) {
				const toggle =
					await productAnalyticsConsentPanelPage.getCookieTypeToggle(
						optionalProductAnalyticsCookieType
					);

				await toggle.uncheck();
			}

			await productAnalyticsConsentPanelPage.acceptSelectedButton.click();

			await expectAllCookiesDeclined(page);

			await clearProductAnalyticsCookies(page);
		});

		await test.step('AC4: Verify "Accept All" sets all cookie types', async () => {
			await productAnalyticsBannerPage.customizeButton.click();

			await productAnalyticsConsentPanelPage.acceptAllButton.click();

			await expectAllCookiesAccepted(page);

			await clearProductAnalyticsCookies(page);
		});

		await test.step('AC5: Verify "Use Necessary Cookies Only" disables all cookie types except necessary cookies', async () => {
			await productAnalyticsBannerPage.customizeButton.click();

			await productAnalyticsConsentPanelPage.useNecessaryCookiesOnlyButton.click();

			await expectAllCookiesDeclined(page);
		});
	}
);
