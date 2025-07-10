/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../../../fixtures/loginTest';
import {waitForEditor} from '../../../../../utils/waitFor';
import {ckeditorSamplePageTest} from './../../fixtures/ckeditorSamplePageTest';
import {classicPageTest} from './fixtures/classicPageTest';

export const test = mergeTests(
	apiHelpersTest,
	ckeditorSamplePageTest,
	classicPageTest,
	featureFlagsTest({
		'LPD-11235': {enabled: true},
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest()
);

test.beforeEach(async ({ckeditorSamplePage, page, site}) => {
	await ckeditorSamplePage.createAndGotoSitePage({site});

	await ckeditorSamplePage.selectTab('CKEditor 5');
	await ckeditorSamplePage.selectTab('React + CET');

	await waitForEditor({page});
});

test(
	'Assert editor is rendered with features based on default configuration and Client Extension additions',
	{tag: '@LPD-11235'},
	async ({classicPage, page}) => {
		await expect(
			page.getByText('Lorem ipsum dolor sit amet')
		).toBeVisible();

		const expectedButtons = [
			'Accessibility help',
			'Undo',
			'Redo',
			'A dropdown with a custom icon',
			'Text alignment',
			'Lists',
		];

		const availableButtons =
			await classicPage.toolbar.buttonLabels.allInnerTexts();

		expect(availableButtons).toEqual(expectedButtons);
	}
);
