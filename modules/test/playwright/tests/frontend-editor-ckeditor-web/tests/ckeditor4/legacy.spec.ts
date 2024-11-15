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
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	loginTest()
);

test.beforeEach(async ({ckeditorSamplePage, site}) => {
	await ckeditorSamplePage.createAndGotoSitePage({site});

	await ckeditorSamplePage.selectTab('CKEditor 4');
	await ckeditorSamplePage.selectTab('Legacy');
});

test('XSS injection doesnt get invoked', async ({page}) => {
	await test.step('Click on the "Go to XSS" button', async () => {
		const gotToXSSViewButton = page.getByText('Go to XSS View');

		await expect(gotToXSSViewButton).toBeInViewport();

		gotToXSSViewButton.click();
	});

	await test.step('Check that XSS was not executed', async () => {
		const sampleEditorContainer = page.locator(
			'[id="\\<\\/script\\>\\<scrIpt\\>alert\\(12451\\)\\;\\<\\/scRipt\\>\\<script\\>sampleXSSEditorContainer"]'
		);

		await expect(sampleEditorContainer).toBeInViewport();

		await expect(page.locator('body')).not.toHaveText('12451');
	});
});
