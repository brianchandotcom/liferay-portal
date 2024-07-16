/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {wemSiteTest} from '../../fixtures/wemSiteTest';
import getRandomString from '../../utils/getRandomString';
import {displayPageTemplatesTest} from './fixtures/displayTemplatePagesTest';

const test = mergeTests(
	displayPageTemplatesTest,
	featureFlagsTest({
		'LPD-20213': true,
	}),
	pageEditorPagesTest,
	loginTest(),
	wemSiteTest
);

test('Allow mapping editables to fields of related object', async ({
	displayPageTemplatesPage,
	pageEditorPage,
	wemSite,
}) => {

	// Create DPT for Lemon

	await displayPageTemplatesPage.goto(wemSite.friendlyUrlPath);

	const displayPageTemplateName = getRandomString();

	await displayPageTemplatesPage.publishNewTemplate({
		contentType: 'Lemon',
		name: displayPageTemplateName,
	});

	// Add fragment and select editable

	await displayPageTemplatesPage.editTemplate(displayPageTemplateName);

	await pageEditorPage.addFragment('Basic Components', 'Heading');

	const headingId = await pageEditorPage.getFragmentId('Heading');

	await pageEditorPage.selectEditable(headingId, 'element-text');

	// Map to field from related Lemon Basket object

	await pageEditorPage.setMappingConfiguration({
		mapping: {
			field: 'Lemon Basket Color',
		},
		relationship: 'Lemon Basket',
		source: 'relationship',
	});

	// Check editable is mapped

	const editable = pageEditorPage.getEditable(headingId, 'element-text');

	await expect(editable).toHaveClass(/page-editor__editable--mapped/);
});
