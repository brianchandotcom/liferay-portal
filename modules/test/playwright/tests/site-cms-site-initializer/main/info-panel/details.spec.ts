/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../../fixtures/loginTest';
import {getRandomInt} from '../../../../utils/getRandomInt';
import getRandomString from '../../../../utils/getRandomString';
import {structureBuilderPagesTest} from '../../structure-builder/fixtures/structureBuilderPagesTest';
import {cmsPagesTest} from '../fixtures/cmsPagesTest';

const test = mergeTests(
	cmsPagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-11235': {enabled: false},
		'LPD-17564': {enabled: true},
		'LPD-34594': {enabled: true},
	}),
	loginTest(),
	structureBuilderPagesTest
);

test(
	'Info panel shows title with content structure',
	{tag: ['@LPD-69788', '@LPD-76513']},
	async ({
		assetsPage,
		contentsPage,
		infoPanelPage,
		page,
		structureBuilderPage,
	}) => {
		const structureLabel = `StructureName${getRandomInt()}`;
		const title = getRandomString();

		await test.step('Create a content structure', async () => {
			await structureBuilderPage.createStructureFromData({
				label: structureLabel,
				page: structureBuilderPage,
			});
		});

		await test.step('Navigate to All Assets and create a new content', async () => {
			await assetsPage.gotoAll();

			await contentsPage.createContent(structureLabel);

			await expect(
				page.getByRole('heading', {name: `New ${structureLabel}`})
			).toBeVisible();

			await page.getByPlaceholder(`New ${structureLabel}`).fill(title);

			await contentsPage.saveContent();
		});

		await test.step('Open Info Panel and assert that title is not empty', async () => {
			await assetsPage.execItemAction({
				action: 'Show Details',
				filter: title,
			});

			await expect(
				page.getByRole('heading', {name: title})
			).toBeVisible();
		});

		await test.step('Assert that all tabs are visible', async () => {
			await expect(infoPanelPage.selectTab('Performance')).toBeVisible();

			await expect(infoPanelPage.selectTab('More')).toBeVisible();

			await infoPanelPage.selectTab('Categorization').click();

			await expect(page.getByPlaceholder('Add tag')).toBeVisible();
			await expect(page.getByPlaceholder('Add category')).toBeVisible();
		});
	}
);
