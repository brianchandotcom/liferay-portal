/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {backendPageTest} from '../../fixtures/backendPageTest';
import {ApiHelpers} from '../../helpers/ApiHelpers';
import {WEM_SITE_ERC} from './constants';

export const test = mergeTests(backendPageTest);

test('Teardown: Delete site with required data for Web Experience tests', async ({
	backendPage,
}) => {
	const apiHelpers = new ApiHelpers(backendPage);

	const response = await apiHelpers.headlessSite.deleteSiteByERC(
		WEM_SITE_ERC
	);

	await expect(response).toBeOK();
});
