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


