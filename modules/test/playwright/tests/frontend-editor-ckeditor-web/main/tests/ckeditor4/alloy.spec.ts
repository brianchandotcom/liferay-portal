/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../../../fixtures/loginTest';
import {alloyPageTest} from '../../../../frontend-editor-ckeditor-sample-web/fixtures/ckeditor4/alloyPageTest';

export const test = mergeTests(
	alloyPageTest,
	featureFlagsTest({
		'LPD-11235': {enabled: true},
		'LPS-178052': {enabled: true},
	}),
	loginTest()
);

test('Add a toolbar button to an Alloy Editor @LPD-11056', async ({
	alloyPage,
}) => {
	await expect(
		alloyPage.alloyEditorContainer.getByText('Lorem ipsum')
	).toBeInViewport();

	await alloyPage.alloyEditorContainer.getByText('Lorem ipsum').selectText();

	await expect(
		alloyPage.alloyEditorToolbarContainer.getByTitle('Insert Video')
	).toBeInViewport();
});
