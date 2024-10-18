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
	'Check role and aria-orientation is removed',
	{tag: '@LPD-30368'},
	async ({apiHelpers, claySamplePage, page, site}) => {
		await test.step('Create a content site and the clay sample widget', async () => {
			await claySamplePage.setupClaySampleWidget({apiHelpers, site});
		});

		await test.step('Select Panel tab', async () => {
			await claySamplePage.selectTab('Panel');
		});

		await test.step('Check that role and aria-orientation is not present', async () => {
			const panelGroup = page.getByRole('heading', {name: 'PANEL GROUP'});

			await panelGroup.waitFor({state: 'visible'});

			const panelGroupElement = page.locator('.panel-group');

			await expect(panelGroupElement).not.toHaveAttribute(
				'role',
				'tablist'
			);

			await expect(panelGroupElement).not.toHaveAttribute(
				'aria-orientation',
				'vertical'
			);
		});
	}
);
