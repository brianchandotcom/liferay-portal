/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {semanticSearchConfigurationPageTest} from '../../../fixtures/semanticSearchConfigurationPageTest';

const testWithBYOLLMDisabled = mergeTests(
	loginTest(),
	featureFlagsTest({'LPD-11319': {enabled: false}}),
	semanticSearchConfigurationPageTest
);

const testWithBYOLLMEnabled = mergeTests(
	loginTest(),
	featureFlagsTest({'LPD-11319': {enabled: true}}),
	semanticSearchConfigurationPageTest
);

testWithBYOLLMDisabled(
	'Hides the BYO-LLM capability alert when the LPD-11319 feature flag is off',
	{tag: '@LPD-90488'},
	async ({semanticSearchConfigurationPage}) => {
		await semanticSearchConfigurationPage.goto();

		await expect(
			semanticSearchConfigurationPage.bringYourOwnLLMCapabilityAlert
		).toHaveCount(0);

		await expect(
			semanticSearchConfigurationPage.bringYourOwnLLMEnabledCheckbox
		).toHaveCount(0);
	}
);

testWithBYOLLMEnabled(
	'Shows the BYO-LLM capability alert when the LPD-11319 feature flag is on and the capability is unavailable',
	{tag: '@LPD-90488'},
	async ({semanticSearchConfigurationPage}) => {
		await semanticSearchConfigurationPage.goto();

		await expect(
			semanticSearchConfigurationPage.bringYourOwnLLMCapabilityAlert
		).toContainText(
			'Bring your own LLM via Elasticsearch inference endpoints is unavailable.'
		);

		await expect(
			semanticSearchConfigurationPage.bringYourOwnLLMEnabledCheckbox
		).toHaveCount(0);
	}
);
