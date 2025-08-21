/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';
import {cmsPagesTest} from './fixtures/cmsPagesTest';

const test = mergeTests(
	cmsPagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-17564': {enabled: true},
		'LPS-179669': {enabled: true},
	}),
	loginTest()
);

test(
	'Can view Share modal for added content',
	{tag: '@LPD-62554'},
	async ({apiHelpers, assetsPage, page}) => {
		const applicationName = 'cms/knowledge-bases';
		const spaceName = 'Default';

		const file1Title = `title ${getRandomString()}`;

		const objectEntry1 = await apiHelpers.objectEntry.postObjectEntry(
			{
				objectEntryFolderExternalReferenceCode: 'L_CONTENTS',
				title: file1Title,
			},
			applicationName,
			spaceName
		);

		await assetsPage.gotoAll();

		await assetsPage.execItemAction({
			action: 'Share',
			filter: file1Title,
		});

		await expect(page.locator('.modal-title')).toContainText(file1Title);

		await apiHelpers.objectEntry.deleteObjectEntry(
			applicationName,
			String(objectEntry1.id)
		);
	}
);

test(
	'Can view Delete confirmation modal for added content',
	{tag: '@LPD-62554'},
	async ({apiHelpers, assetsPage, page}) => {
		const applicationName = 'cms/knowledge-bases';
		const spaceName = 'Default';
		let objectEntry1;

		const file1Title = `title ${getRandomString()}`;

		try {
			objectEntry1 = await apiHelpers.objectEntry.postObjectEntry(
				{
					objectEntryFolderExternalReferenceCode: 'L_CONTENTS',
					title: file1Title,
				},
				applicationName,
				spaceName
			);

			await assetsPage.gotoAll();

			await assetsPage.table.bodyRows
				.filter({hasText: file1Title})
				.locator('input[title="Select Item"]')
				.check();

			await assetsPage.execBulkItemAction('Delete');

			await expect(page.locator('.modal-title')).toContainText(
				'Delete Entry'
			);
		}
		finally {
			await apiHelpers.objectEntry.deleteObjectEntry(
				applicationName,
				String(objectEntry1.id)
			);
		}
	}
);
