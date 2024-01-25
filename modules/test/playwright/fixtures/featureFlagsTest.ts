/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, mergeTests, test} from '@playwright/test';

import {loginTest} from './loginTest';

export type FeatureFlagsOptions = {
	[key: string]: boolean;
};

export type FeatureFlags = FeatureFlag[];

export interface FeatureFlag {
	readonly enabled: boolean;
	readonly key: string;
}

function featureFlagsTest(options: FeatureFlagsOptions) {
	const fixtureImpl = test.extend<{
		featureFlags: FeatureFlags;
	}>({
		featureFlags: [
			async ({page}, use) => {

				// Save original state of FFs

				const originalFeatureFlags: FeatureFlagResult[] = [];

				for (const key of Object.keys(options)) {
					const result = await isEnabled(key, page);

					originalFeatureFlags.push(result.featureFlag);
					originalFeatureFlags.push(...result.dependentFeatureFlags);
				}

				try {

					// Set requested state of FFs

					for (const [key, enabled] of Object.entries(options)) {
						await setEnabled(enabled, key, page);
					}

					// Reload page to account for FF changes (eg: update Liferay.FeatureFlags)

					await page.reload();

					// Run test

					await use(
						Object.entries(options).reduce(
							(featureFlags: FeatureFlags, [key, enabled]) => {
								featureFlags.push({
									enabled,
									key,
								});

								return featureFlags;
							},
							[]
						)
					);
				}
				finally {

					// Restore original state of FFs

					originalFeatureFlags.reverse();

					for (const featureFlag of originalFeatureFlags) {
						await setEnabled(
							featureFlag.enabled,
							featureFlag.key,
							page
						);
					}
				}
			},
			{auto: true},
		],
	});

	return mergeTests(loginTest, fixtureImpl);
}

interface IsEnabledResult {
	dependentFeatureFlags: FeatureFlagResult[];
	featureFlag: FeatureFlagResult;
}

interface SetEnabledResult {
	dependentFeatureFlags: FeatureFlagResult[];
}

interface FeatureFlagResult {
	companyId: number;
	dependenciesFulfilled: boolean;
	dependencyKeys: string[];
	description: string;
	enabled: boolean;
	featureFlagType: 'BETA' | 'DEPRECATION' | 'DEV' | 'RELEASE';
	key: string;
	title: string;
}

async function isEnabled(key: string, page: Page): Promise<IsEnabledResult> {
	return invokeServerWithRetry(
		page,
		'/o/com-liferay-feature-flag-web/is-enabled',
		{key}
	);
}

async function setEnabled(
	enabled: boolean,
	key: string,
	page: Page
): Promise<SetEnabledResult> {
	return invokeServerWithRetry(
		page,
		'/o/com-liferay-feature-flag-web/set-enabled',
		{enabled, key}
	);
}

async function invokeServerWithRetry<T>(
	page: Page,
	url: string,
	partialBody: object
): Promise<T> {
	let result: T;

	try {
		result = await invokeServer(page, url, partialBody);
	}
	catch (error) {
		await page.goto('/');

		result = await invokeServer(page, url, partialBody);
	}

	return result;
}

async function invokeServer<T>(
	page: Page,
	url: string,
	partialBody: object
): Promise<T> {
	return await page.evaluate(
		async ({partialBody, url}) =>
			new Promise((resolve, reject) => {
				Liferay.Util.fetch(url, {
					body: Liferay.Util.objectToFormData({
						companyId: Liferay.ThemeDisplay.getCompanyId(),
						...partialBody,
					}),
					method: 'POST',
				})
					.then((response) => response.text())
					.then(JSON.parse)
					.then(resolve)
					.catch(reject);
			}),
		{
			partialBody,
			url,
		}
	);
}

export {featureFlagsTest};
