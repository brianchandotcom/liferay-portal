/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ObjectDefinition} from '@liferay/object-admin-rest-client-js';
import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {waitForAlert} from '../../utils/waitForAlert';
import {cmsPagesTest} from './fixtures/cmsPagesTest';

const test = mergeTests(
	cmsPagesTest,
	apiHelpersTest,
	featureFlagsTest({
		'LPD-11232': {enabled: true},
		'LPD-17564': {enabled: true},
		'LPD-31149': {enabled: true},
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
		const stucctureName = objectDefinition.name;

		await structuresPage.goto();

		const row = structuresPage.getItem(stucctureName);
		await row.waitFor();

		await structuresPage.execItemAction({
			action: 'Delete',
			filter: stucctureName,
		});
		await waitForAlert(page, `${stucctureName} was deleted successfully`, {
			type: 'success',
		});

		await expect(row).toBeHidden();
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
		const stucctureName = objectDefinition.name;

		await structuresPage.goto();

		const row = structuresPage.getItem(stucctureName);
		await row.waitFor();

		await structuresPage.execItemAction({
			action: 'Delete',
			filter: stucctureName,
		});

		await page
			.getByPlaceholder('Confirm Structure Name')
			.fill(stucctureName);
		await page.getByRole('button', {name: 'Delete'}).click();

		await waitForAlert(page, `${stucctureName} was deleted successfully`, {
			type: 'success',
		});

		await expect(row).toBeHidden();
	}
);
