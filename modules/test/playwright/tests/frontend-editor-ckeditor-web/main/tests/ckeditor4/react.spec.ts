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
	await ckeditorSamplePage.gotoTab(TabName.CK_EDITOR_4, SubTabName.REACT);
});

test(
	'Editor configuration object must not be mutated',
	{tag: '@LPD-37782'},
	async ({page}) => {
		const iframe = page.frameLocator(
			'iframe[title$="sampleReactClassicEditor"]'
		);

		expect(
			iframe.getByText('Editor configuration object was not mutated.')
		).toBeVisible();
	}
);
