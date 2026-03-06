/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../../../fixtures/loginTest';
import {basicClassicPageTest} from '../../../../frontend-editor-ckeditor-sample-web/fixtures/ckeditor5/classicPageTest';

export const test = mergeTests(
	basicClassicPageTest,
	featureFlagsTest({
		'LPD-11235': {enabled: false},
		'LPS-178052': {enabled: true},
	}),
	loginTest()
);

test(
	'Editor configuration is applied',
	{tag: '@LPD-11235'},
	async ({classicPage, page}) => {
		await test.step('Initial data is set', async () => {
			await expect(
				page.getByText('Lorem ipsum dolor sit amet')
			).toBeVisible();
		});

		await test.step('Toolbar contains basic preset controls', async () => {
			const basicPresetControlLabels = [
				'Accessibility help',
				'Undo',
				'Redo',
				'Bold',
				'Italic',
				'Underline',
				'Numbered List',
				'Bulleted List',
				'Link',
			];

			const controlLabels =
				await classicPage.toolbar.buttonLabels.allInnerTexts();

			expect(controlLabels).toEqual(basicPresetControlLabels);
		});
	}
);
