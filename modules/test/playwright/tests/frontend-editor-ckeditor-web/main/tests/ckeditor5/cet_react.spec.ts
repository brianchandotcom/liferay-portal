/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../../../fixtures/loginTest';
import {reactPlusCETClassicPageTest} from '../../../../frontend-editor-ckeditor5-sample-web/fixtures/classicPageTest';

export const test = mergeTests(
	reactPlusCETClassicPageTest,
	featureFlagsTest({
		'LPD-11235': {enabled: false},
		'LPS-178052': {enabled: true},
	}),
	loginTest()
);

test(
	'Editor configuration is applied, merging existing and client extension customizations',
	{tag: '@LPD-11235'},
	async ({classicPage}) => {
		const expectedButtons = [
			'Accessibility help',
			'Undo',
			'Redo',
			'Text alignment',
			'Text formatting',
			'Lists',
			'Bookmark',
			'Timestamp',
			'Enter fullscreen mode',
			'Hello',
		];

		const availableButtons =
			await classicPage.toolbar.buttonLabels.allInnerTexts();

		expect(availableButtons).toEqual(expectedButtons);
	}
);

test(
	'WordCount plugin loaded via CX tracks word and character counts',
	{tag: '@LPD-89734'},
	async ({classicPage}) => {
		await test.step('Word count is visible with initial content', async () => {
			await expect(classicPage.wordCountLabel).toBeVisible();

			const text = await classicPage.wordCountLabel.innerText();

			const words = Number(text.match(/Words: (\d+)/)?.[1]);
			const chars = Number(text.match(/Characters: (\d+)/)?.[1]);

			expect(words).toBeGreaterThan(0);
			expect(chars).toBeGreaterThan(0);
		});
	}
);
