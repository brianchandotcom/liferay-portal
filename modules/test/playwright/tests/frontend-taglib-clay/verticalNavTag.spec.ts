/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {claySamplePageTest} from './fixtures/claySamplePageTest';

export const test = mergeTests(
	apiHelpersTest,
	claySamplePageTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	loginTest()
);

test(
	'Label is being escaped',
	{tag: '@LPD-30368'},
	async ({apiHelpers, claySamplePage, page, site}) => {
		await test.step('Create a content site and the clay sample widget', async () => {
			await claySamplePage.setupClaySampleWidget({apiHelpers, site});
		});

		await test.step('Select Vertical Nav tab', async () => {
			await claySamplePage.selectTab('Vertical Nav');
		});

		await test.step('Check that alert did not pop up', async () => {
			let alertText = '';

			page.on('dialog', (dialog) => {
				alertText = dialog.message();
				dialog.dismiss();
			});

			expect(alertText).toEqual('');
		});
	}
);
