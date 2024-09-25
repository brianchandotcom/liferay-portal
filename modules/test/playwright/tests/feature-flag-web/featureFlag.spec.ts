/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {checkAccessibility} from '../../utils/checkAccessibility';
import {featureFlagPagesTest} from './fixtures/featureFlagPagesTest';

const DEPENDENCY_FEATURE_FLAG = 'LPD-00000';
const DEPENDENT_FEATURE_FLAG = 'LPD-00001';

export const test = mergeTests(
	featureFlagPagesTest,
	featureFlagsTest({
		[DEPENDENCY_FEATURE_FLAG]: false,
		[DEPENDENT_FEATURE_FLAG]: false,
	}),
	loginTest()
);

test('LPS-167698 - Assert that a feature flag can be enabled and disabled.', async ({
	featureFlagsInstanceSettingsPage,
}) => {
	await featureFlagsInstanceSettingsPage.goto();

	await checkAccessibility({page: featureFlagsInstanceSettingsPage.page});

	await featureFlagsInstanceSettingsPage.updateFeatureFlag(
		DEPENDENCY_FEATURE_FLAG,
		true
	);

	const featureFlagToggle =
		await featureFlagsInstanceSettingsPage.getFeatureFlagToggle(
			DEPENDENCY_FEATURE_FLAG
		);

	await expect(featureFlagToggle).toBeChecked();

	await featureFlagsInstanceSettingsPage.updateFeatureFlag(
		DEPENDENCY_FEATURE_FLAG,
		false
	);

	await expect(featureFlagToggle).toBeChecked({checked: false});
});

test('LPS-167698 - Assert that a feature flag with dependencies can be enabled when the dependencies are enabled.', async ({
	featureFlagsInstanceSettingsPage,
}) => {
	await featureFlagsInstanceSettingsPage.goto();

	await featureFlagsInstanceSettingsPage.search('LPD-0000');

	// Enable feature flag dependency

	await featureFlagsInstanceSettingsPage.updateFeatureFlag(
		DEPENDENCY_FEATURE_FLAG,
		true,
		false
	);

	const dependencyFeatureFlagToggle =
		await featureFlagsInstanceSettingsPage.getFeatureFlagToggle(
			DEPENDENCY_FEATURE_FLAG,
			false
		);

	await expect(dependencyFeatureFlagToggle).toBeChecked();

	// Enable feature flag that has a dependency

	await featureFlagsInstanceSettingsPage.updateFeatureFlag(
		DEPENDENT_FEATURE_FLAG,
		true,
		false
	);

	const dependentFeatureFlagToggle =
		await featureFlagsInstanceSettingsPage.getFeatureFlagToggle(
			DEPENDENT_FEATURE_FLAG,
			false
		);

	await expect(dependentFeatureFlagToggle).toBeChecked();

	// Clean up

	await featureFlagsInstanceSettingsPage.updateFeatureFlag(
		DEPENDENT_FEATURE_FLAG,
		false,
		false
	);

	await featureFlagsInstanceSettingsPage.updateFeatureFlag(
		DEPENDENCY_FEATURE_FLAG,
		false,
		false
	);
});

test('LPS-167698 - Assert that a feature flag with dependencies cannot be enabled when the dependencies are not enabled.', async ({
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
