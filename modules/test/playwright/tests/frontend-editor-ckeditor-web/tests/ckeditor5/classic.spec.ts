/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../../fixtures/loginTest';
import {ckeditorSamplePageTest} from '../../fixtures/ckeditorSamplePageTest';

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
	await ckeditorSamplePage.selectTab('Classic');
});

test(
	'Assert editor is rendered and EditorConfigContributor is applied',
	{tag: '@LPD-11235'},
	async ({page}) => {
		expect(page.getByText('Lorem ipsum dolor sit amet')).toBeVisible();

		expect(
			page.locator(
				'p[data-placeholder="This placeholder is set from EditorConfigContributor."]'
			)
		).toBeAttached();
	}
);

test(
	'Assert editor is rendered with the default plugins configuration',
	{tag: '@LPD-11235'},
	async ({page}) => {
		const editorToolbar = page.getByLabel('Editor toolbar');
		const expectedButtons = ['Undo', 'Redo', 'Bold', 'Italic', 'Underline'];

		await expect(editorToolbar).toBeVisible();

		await expect(editorToolbar.getByRole('button')).toHaveCount(5);

		const availableButtons = await editorToolbar
			.getByRole('button')
			.locator('.ck-button__label')
			.allInnerTexts();

		expect(availableButtons).toEqual(expectedButtons);
	}
);
