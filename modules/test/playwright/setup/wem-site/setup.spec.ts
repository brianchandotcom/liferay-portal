/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';
import {resolve} from 'path';

import {backendPageTest} from '../../fixtures/backendPageTest';
import {ApiHelpers} from '../../helpers/ApiHelpers';
import {WEM_SITE_ERC} from './constants';

export const test = mergeTests(backendPageTest);

test('Setup: Create site with required data for Web Experience tests', async ({
	backendPage,
}) => {
	const apiHelpers = new ApiHelpers(backendPage);

	const site = await apiHelpers.headlessSite.createSiteFromZip(
		{
			externalReferenceCode: WEM_SITE_ERC,
			name: 'Web Experience Site',
		},
		resolve(__dirname, 'site-initializer')
	);

	expect(site).toHaveProperty('externalReferenceCode', WEM_SITE_ERC);
});
