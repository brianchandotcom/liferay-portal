/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../../../fixtures/loginTest';
import {ckeditorSamplePageTest} from '../../fixtures/ckeditorSamplePageTest';
import {SubTabName, TabName} from "../../pages/CKEditorSamplePage";
import {classicPageTest} from './fixtures/classicPageTest';

export const test = mergeTests(
	ckeditorSamplePageTest,
	classicPageTest,
	featureFlagsTest({
		'LPD-11235': {enabled: true},
		'LPS-178052': {enabled: true},
	}),
	loginTest()
);

test.beforeEach(async ({ckeditorSamplePage}) => {
	await ckeditorSamplePage.goto();
	await ckeditorSamplePage.selectTab(
		TabName.CK_EDITOR_5,
		SubTabName.REACT_PLUS_CET
	);
});

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
