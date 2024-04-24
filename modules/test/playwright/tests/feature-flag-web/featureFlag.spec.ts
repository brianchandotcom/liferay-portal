/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../fixtures/loginTest';
import {featureFlagPagesTest} from './fixtures/featureFlagPagesTest';

const DEPENDENCY_FEATURE_FLAG = 'LPS-162766';
const DEPENDENT_FEATURE_FLAG = 'LPS-162765';

export const test = mergeTests(featureFlagPagesTest, loginTest());

test('LPS-167698 - Asserts that a feature flag can be enabled and disabled.', async ({
	featureFlagsInstanceSettingsPage,
}) => {
	await featureFlagsInstanceSettingsPage.goto();

	await featureFlagsInstanceSettingsPage.updateFeatureFlag(
		'LPS-162766',
		true
	);

	const featureFlagToggle =
		await featureFlagsInstanceSettingsPage.getFeatureFlagToggle(
			'LPS-162766'
		);

	await expect(featureFlagToggle).toBeChecked();

	await featureFlagsInstanceSettingsPage.updateFeatureFlag(
		'LPS-162766',
		false
	);

	await expect(featureFlagToggle).toBeChecked({checked: false});
});

test('LPS-167698 - This tests asserts that a feature flag can be enabled with dependencies.', async ({
	featureFlagsInstanceSettingsPage,
}) => {
	await featureFlagsInstanceSettingsPage.goto();

	// Enable feature flag dependency

	await featureFlagsInstanceSettingsPage.updateFeatureFlag(
		DEPENDENCY_FEATURE_FLAG,
		true
	);

	const dependencyFeatureFlagToggle =
		await featureFlagsInstanceSettingsPage.getFeatureFlagToggle(
			DEPENDENCY_FEATURE_FLAG
		);

	await expect(dependencyFeatureFlagToggle).toBeChecked();

	// Enable feature flag that has a dependency

	await featureFlagsInstanceSettingsPage.updateFeatureFlag(
		DEPENDENT_FEATURE_FLAG,
		true
	);

	const dependentFeatureFlagToggle =
		await featureFlagsInstanceSettingsPage.getFeatureFlagToggle(
			DEPENDENT_FEATURE_FLAG
		);

	await expect(dependentFeatureFlagToggle).toBeChecked();

	// Clean up

	await featureFlagsInstanceSettingsPage.updateFeatureFlag(
		DEPENDENT_FEATURE_FLAG,
		false
	);

	await featureFlagsInstanceSettingsPage.updateFeatureFlag(
		DEPENDENCY_FEATURE_FLAG,
		false
	);
});

test('LPS-167698 - This tests asserts that a feature flag cannot be enabled by dependencies.', async ({
	featureFlagsInstanceSettingsPage,
}) => {
	await featureFlagsInstanceSettingsPage.goto();

	const dependencyFeatureFlagToggle =
		await featureFlagsInstanceSettingsPage.getFeatureFlagToggle(
			DEPENDENCY_FEATURE_FLAG
		);

	// Check that feature flag dependency is not enabled

	await expect(dependencyFeatureFlagToggle).toBeChecked({checked: false});

	const dependentFeatureFlagToggle =
		await featureFlagsInstanceSettingsPage.getFeatureFlagToggle(
			DEPENDENT_FEATURE_FLAG
		);

	// Check that feature flag that has dependencies that are not enabled is disabled

	await expect(dependentFeatureFlagToggle).toBeDisabled();
});
