/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../../../fixtures/loginTest';
import {ckeditorSamplePageTest} from '../../../../frontend-editor-ckeditor-sample-web/fixtures/ckeditorSamplePageTest';
import {
	SubTabName,
	TabName,
} from '../../../../frontend-editor-ckeditor-sample-web/pages/CKEditorSamplePage';

export const test = mergeTests(
	ckeditorSamplePageTest,
	featureFlagsTest({
		'LPD-11235': {enabled: true},
		'LPS-178052': {enabled: true},
	}),
	loginTest()
);

test.beforeEach(async ({ckeditorSamplePage}) => {
	await ckeditorSamplePage.gotoTab(TabName.CK_EDITOR_4, SubTabName.LEGACY);
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
