/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {ckeditor4SamplePageTest} from '../../frontend-editor-ckeditor4-sample-web/fixtures/ckeditor4SamplePageTest';
import {TabName} from '../../frontend-editor-ckeditor4-sample-web/pages/CKEditor4SamplePage';

export const test = mergeTests(
	ckeditor4SamplePageTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	loginTest()
);

test.beforeEach(async ({ckeditor4SamplePage}) => {
	await ckeditor4SamplePage.gotoTab(TabName.ALLOY);
});

test(
	'Content is rendered in source editor when using full screen mode',
	{tag: ['@LPD-62410']},
	async ({page}) => {
		await test.step('Go to source mode and edit in full screen mode', async () => {
			await page.locator('.ae-editable').click();

			const switchModeButton = page.locator('[id$="AlloyEditorSwitch"]');

			await switchModeButton.waitFor({state: 'visible', timeout: 10000});

			switchModeButton.click();

			const fullScreenButton = page.locator(
				'[id$="AlloyEditorFullscreen"]'
			);

			await fullScreenButton.waitFor({state: 'visible', timeout: 10000});

			fullScreenButton.click();

			const fullScreenModal = page.locator(
				'.lfr-fullscreen-source-editor-dialog'
			);

			await fullScreenModal.waitFor({state: 'visible', timeout: 10000});

			await expect(fullScreenModal).toContainText('Lorem ipsum');
		});
	}
);
