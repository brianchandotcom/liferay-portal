/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../../fixtures/loginTest';
import {ckeditorSamplePageTest} from './../../fixtures/ckeditorSamplePageTest';

export const test = mergeTests(
	apiHelpersTest,
	ckeditorSamplePageTest,
	featureFlagsTest({
		'LPD-11235': {enabled: true},
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest()
);

test.beforeEach(async ({ckeditorSamplePage, site}) => {
	await ckeditorSamplePage.createAndGotoSitePage({site});

	await ckeditorSamplePage.selectTab('CKEditor 5');
	await ckeditorSamplePage.selectTab('React');
});

test('Assert editor is rendered', {tag: '@LPD-11235'}, async ({page}) => {
	expect(page.getByText('Lorem ipsum dolor sit amet')).toBeVisible();
});

test(
	'Assert editor is rendered with a custom list of plugins configuration',
	{tag: '@LPD-11235'},
	async ({page}) => {
		const editorToolbar = page.getByLabel('Editor toolbar');
		const expectedButtons = [
			'Undo',
			'Redo',
			'Styles',
			'Normal',
			'Bold',
			'Italic',
			'Underline',
			'Strikethrough',
			'Font Color',
			'Font Background Color',
			'Remove Format',
			'Numbered List',
			'Bulleted List',
			'Increase indent',
			'Decrease indent',
			'Block quote',
			'Link',
			'Insert table',
			'Insert media',
			'Horizontal line',
			'Text alignment',
			'Source',
		];

		await expect(editorToolbar).toBeVisible();

		const availableButtons = await editorToolbar
			.getByRole('button')
			.locator('.ck-button__label')
			.allInnerTexts();

		expect(availableButtons).toEqual(expectedButtons);
	}
);
