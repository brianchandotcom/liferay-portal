/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {isolatedLayoutTest} from '../../../../fixtures/isolatedLayoutTest';
import {isolatedSiteTest} from '../../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../../fixtures/loginTest';
import {pageViewModePagesTest} from '../../../../fixtures/pageViewModePagesTest';
import {questionsPagesTest} from './questionsPagesTest';

const questionsTest = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPD-82301': {enabled: true},
	}),
	isolatedLayoutTest({publish: false, type: 'portlet'}),
	isolatedSiteTest,
	loginTest(),
	questionsPagesTest,
	pageViewModePagesTest
);

export {questionsTest};
