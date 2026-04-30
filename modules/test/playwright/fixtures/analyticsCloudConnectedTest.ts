/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, mergeTests} from '@playwright/test';

import {backendPageTest} from './backendPageTest';
import {FeatureFlagsOptions, featureFlagsTest} from './featureFlagsTest';

const ANALYTICS_CONFIGURATION_PID =
	'com.liferay.analytics.settings.configuration.AnalyticsConfiguration';

// Forces Analytics Cloud into a "connected" state by stubbing
// AnalyticsConfiguration and restores the original configuration after use.

function analyticsCloudConnectedTest(
	extraFeatureFlags: FeatureFlagsOptions = {}
) {
	return mergeTests(
		backendPageTest,
		featureFlagsTest({
			...extraFeatureFlags,
			'LPD-65399': {enabled: true},
			'LPS-155284': {enabled: true},
		})
	).extend<{analyticsCloudConnected: void}>({
		analyticsCloudConnected: [
			async ({backendPage}, use) => {
				const originalProperties = await backendPage.evaluate(
					async (pid) => {
						const response = await Liferay.Util.fetch(
							`/o/headless-admin-configuration/v1.0/instance-configurations/${pid}`
						);

						if (!response?.ok) {
							return {};
						}

						const json = await response.json();

						return json.properties ?? {};
					},
					ANALYTICS_CONFIGURATION_PID
				);

				try {
					await _putInstanceConfigurationProperties(backendPage, {
						liferayAnalyticsDataSourceId:
							'playwright-stub-data-source',
						liferayAnalyticsFaroBackendSecuritySignature:
							'playwright-stub-signature',
						liferayAnalyticsFaroBackendURL:
							'http://playwright-stub.invalid',
					});

					await use();
				}
				finally {
					await _putInstanceConfigurationProperties(
						backendPage,
						originalProperties
					);
				}
			},
			{auto: true},
		],
	});
}

async function _putInstanceConfigurationProperties(
	backendPage: Page,
	properties: Record<string, unknown>
): Promise<void> {
	await backendPage.evaluate(
		async ({pid, properties}) => {
			const response = await Liferay.Util.fetch(
				`/o/headless-admin-configuration/v1.0/instance-configurations/${pid}`,
				{
					body: JSON.stringify({
						externalReferenceCode: pid,
						properties,
					}),
					headers: {
						'Content-Type': 'application/json',
					},
					method: 'PUT',
				}
			);

			if (!response?.ok) {
				throw new Error(
					`Failed to PUT ${pid}: ${response?.status} ${await response?.text()}`
				);
			}
		},
		{pid: ANALYTICS_CONFIGURATION_PID, properties}
	);
}

export {analyticsCloudConnectedTest};
