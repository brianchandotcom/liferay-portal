/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {contentSecurityPolicyPagesTest} from '../../fixtures/contentSecurityPolicyPagesTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';

export const test = mergeTests(
	contentSecurityPolicyPagesTest,
	featureFlagsTest({
		'LPS-134060': {enabled: true},
	}),
	loginTest()
);

test('Content Security Policy Smoke Test', async ({
	contentSecurityPolicyPage,
	page,
}) => {
	await contentSecurityPolicyPage.goTo();

	await contentSecurityPolicyPage.enableCSP();

	await contentSecurityPolicyPage.setPolicy('test');

	await contentSecurityPolicyPage.addExcludedPaths('test1');
	await contentSecurityPolicyPage.addExcludedPaths('test2');
	await contentSecurityPolicyPage.addExcludedPaths('test3');

	await contentSecurityPolicyPage.removeExcludedPaths('test1');
	await contentSecurityPolicyPage.removeExcludedPaths('test2');

	await contentSecurityPolicyPage.saveConfiguration();

	await contentSecurityPolicyPage.resetCSPConfiguration();

	await expect(
		page.locator('textarea[id*="excludedPaths"]', {hasText: 'test3'})
	).not.toBeVisible();
});
