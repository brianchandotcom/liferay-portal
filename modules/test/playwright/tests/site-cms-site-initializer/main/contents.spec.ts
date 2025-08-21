/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {getRandomInt} from '../../../utils/getRandomInt';
import getRandomString from '../../../utils/getRandomString';
import {structureBuilderPagesTest} from '../structure-builder/fixtures/structureBuilderPagesTest';
import {cmsPagesTest} from './fixtures/cmsPagesTest';

const test = mergeTests(
	cmsPagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-17564': {enabled: true},
		'LPD-32050': {enabled: true},
		'LPS-179669': {enabled: true},
	}),
	loginTest(),
	pageEditorPagesTest,
	structureBuilderPagesTest
);

let structureIds = [];

test.beforeEach(() => {
	structureIds = [];
});

test.afterEach(async ({structureBuilderPage}) => {
	for (const id of structureIds) {
		await structureBuilderPage.deleteStructure(Number(id));
	}
});

test(
	'Custom structure takes title as name field',
	{tag: '@LPD-62896'},
	async ({contentsPage, page, structureBuilderPage}) => {

		// Create structure

		const structureLabel = `Structure${getRandomInt()}`;
		const structureERC = getRandomString();

		await structureBuilderPage.createStructureFromData({
			erc: structureERC,
			label: structureLabel,
			name: structureLabel,
			page: structureBuilderPage,
			structureIds,
		});

		// Go to CMS Contents

		await contentsPage.goto();

		// Create new content for recently created structure

		await contentsPage.createContent(structureLabel);

		// Fill data and save

		const title = getRandomString();

		await page.getByLabel('Title').fill(title);

		await contentsPage.saveContent();

		// Edit the content again and check title is taken as name field

		await contentsPage.editContent(title);

		await expect(page.getByText(`Edit ${title}`)).toBeVisible();

		// Delete content

		await contentsPage.goto();

		await contentsPage.deleteContent(title);
	}
);
