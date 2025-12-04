/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ObjectDefinition} from '@liferay/object-admin-rest-client-js';
import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {clickAndExpectToBeHidden} from '../../../utils/clickAndExpectToBeHidden';
import {getRandomInt} from '../../../utils/getRandomInt';
import getRandomString from '../../../utils/getRandomString';
import {waitForAlert} from '../../../utils/waitForAlert';
import {structureBuilderPagesTest} from '../structure-builder/fixtures/structureBuilderPagesTest';
import {cmsPagesTest} from './fixtures/cmsPagesTest';

const test = mergeTests(
	cmsPagesTest,
	structureBuilderPagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-17564': {enabled: true},
	}),
	loginTest()
);

test(
	'Structure can be deleted without confirmation if it does not have an approved status',
	{tag: '@LPD-51516'},
	async ({apiHelpers, page, structuresPage}) => {
		const objectDefinition =
			(await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFolderExternalReferenceCode: 'L_CMS_FILE_TYPES',
				status: {code: 2},
			})) as ObjectDefinition;
		const structureName = objectDefinition.name;

		await structuresPage.goto();

		await structuresPage.execItemAction({
			action: 'Delete',
			filter: structureName,
		});
		await waitForAlert(page, `${structureName} was deleted successfully`, {
			type: 'success',
		});

		await expect(structuresPage.getItem(structureName)).toBeHidden();
	}
);

test(
	'Structure can be deleted after manual confirmation if it has an approved status',
	{tag: '@LPD-51516'},
	async ({apiHelpers, page, structuresPage}) => {
		const objectDefinition =
			(await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFolderExternalReferenceCode: 'L_CMS_FILE_TYPES',
				status: {code: 0},
			})) as ObjectDefinition;
		const structureName = objectDefinition.name;

		await structuresPage.goto();

		await structuresPage.execItemAction({
			action: 'Delete',
			filter: structureName,
		});

		await page
			.getByPlaceholder('Confirm Content Structure Name')
			.fill(structureName);
		await page.getByRole('button', {name: 'Delete'}).click();

		await waitForAlert(page, `${structureName} was deleted successfully`, {
			type: 'success',
		});

		await expect(structuresPage.getItem(structureName)).toBeHidden();
	}
);

test(
	'Structures cannot be deleted if they have a relation',
	{tag: '@LPD-51516'},
	async ({page, structureBuilderPage, structuresPage}) => {

		// Create structures and relate them

		const structureLabel = getRandomString();

		await structureBuilderPage.createStructureFromData({
			label: structureLabel,
			name: `StructureName${getRandomInt()}`,
			page: structureBuilderPage,
		});

		const structure2Label = getRandomString();

		await structureBuilderPage.createStructureFromData({
			label: structure2Label,
			name: `StructureName2${getRandomInt()}`,
			page: structureBuilderPage,
		});

		await structureBuilderPage.addReferencedStructures([structureLabel]);

		await structureBuilderPage.publishStructure();

		// Try to delete them

		await structuresPage.goto();

		await structuresPage.execItemAction({
			action: 'Delete',
			filter: structureLabel,
		});

		await expect(
			page.getByRole('heading', {name: 'Deletion Not Allowed'})
		).toBeVisible();

		await clickAndExpectToBeHidden({
			target: page.getByRole('button', {name: 'OK'}),
			trigger: page.getByRole('button', {name: 'OK'}),
		});

		await structuresPage.execItemAction({
			action: 'Delete',
			filter: structure2Label,
		});

		await expect(
			page.getByRole('heading', {name: 'Deletion Not Allowed'})
		).toBeVisible();
	}
);

test(
	'Some actions are only allowed to non-system structures',
	{tag: '@LPD-51405'},
	async ({apiHelpers, page, structuresPage}) => {
		await structuresPage.goto();

		await page
			.getByRole('row', {name: 'Basic Document'})
			.locator('.dropdown-toggle')
			.click();

		expect(
			page.getByRole('menuitem', {exact: true, name: 'Edit'})
		).toBeVisible();
		expect(
			page.getByRole('menuitem', {exact: true, name: 'View Usages'})
		).toBeVisible();
		expect(
			page.getByRole('menuitem', {exact: true, name: 'Export as JSON'})
		).toBeVisible();
		expect(
			page.getByRole('menuitem', {
				exact: true,
				name: 'Import and Override',
			})
		).toBeVisible();
		expect(
			page.getByRole('menuitem', {exact: true, name: 'Permissions'})
		).toBeVisible();

		expect(
			page.getByRole('menuitem', {exact: true, name: 'Delete'})
		).toBeHidden();

		const objectDefinition =
			(await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFolderExternalReferenceCode: 'L_CMS_FILE_TYPES',
				status: {code: 0},
			})) as ObjectDefinition;

		await structuresPage.goto();

		await page
			.getByRole('row', {name: objectDefinition.name})
			.locator('.dropdown-toggle')
			.click();

		expect(
			page.getByRole('menuitem', {exact: true, name: 'Edit'})
		).toBeVisible();
		expect(
			page.getByRole('menuitem', {exact: true, name: 'View Usages'})
		).toBeVisible();
		expect(
			page.getByRole('menuitem', {exact: true, name: 'Export as JSON'})
		).toBeVisible();
		expect(
			page.getByRole('menuitem', {
				exact: true,
				name: 'Import and Override',
			})
		).toBeVisible();
		expect(
			page.getByRole('menuitem', {exact: true, name: 'Permissions'})
		).toBeVisible();
		expect(
			page.getByRole('menuitem', {exact: true, name: 'Delete'})
		).toBeVisible();
	}
);

test(
	'There is no checkbox to select structures',
	{tag: '@LPD-69431'},
	async ({page, structuresPage}) => {
		await structuresPage.goto();

		await expect(page.getByRole('checkbox')).toHaveCount(0);
	}
);
