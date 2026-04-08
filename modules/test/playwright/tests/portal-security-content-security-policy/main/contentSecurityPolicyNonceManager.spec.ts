/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {contentSecurityPolicyPagesTest} from '../../../fixtures/contentSecurityPolicyPagesTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {virtualInstancesPagesTest} from '../../../fixtures/virtualInstancesPagesTest';
import performLogin, {performLogout} from '../../../utils/performLogin';

export const test = mergeTests(
	apiHelpersTest,
	contentSecurityPolicyPagesTest,
	featureFlagsTest({
		'LPD-36105': {enabled: true},
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest,
	virtualInstancesPagesTest
);

test('No CSP nonce errors when logging in', async ({
	contentSecurityPolicyPage,
	page,
}) => {
	await contentSecurityPolicyPage.gotoAndConfigurePolicy(
		`style-src '[$NONCE$]'; script-src '[$NONCE$]';`
	);

	const errors = [];

	page.on('console', (msg) => {
		if (
			msg.type() === 'error' &&
			msg
				.text()
				.includes('Content Security Policy directive: "script-src')
		) {
			errors.push({text: msg.text(), type: msg.type()});
		}
	});

	await performLogout(page);

	await performLogin(page, 'test');

	expect(errors).toHaveLength(0);
});
