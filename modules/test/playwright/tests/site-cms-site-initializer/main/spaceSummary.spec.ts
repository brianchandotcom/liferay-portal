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
	'Can access to View All Files page',
	{tag: '@LPD-62706'},
	async ({page, spaceSummaryPage}) => {
		const spaceName = 'Default';

		await spaceSummaryPage.goto(spaceName);
		await spaceSummaryPage.viewAllFilesLink.click();

		expect(page.getByRole('link', {name: spaceName})).toBeVisible();
		expect(page.getByRole('link', {name: 'Files'})).toBeVisible();
		expect(page.getByText('No Files Yet')).toBeVisible();
	}
);

test(
	'Can view added files in the space summary page',
	{tag: '@LPD-62706'},
	async ({apiHelpers, page, spaceSummaryPage}) => {
		const applicationName = 'cms/basic-documents';
		const spaceName = 'Default';

		const file1Title = `title ${getRandomString()}`;

		const objectEntry1 = await apiHelpers.objectEntry.postObjectEntry(
			{
				file: {
					fileBase64: 'R0lGODlhAQABAAAAACw=',
					name: `file_${getRandomString()}.png`,
				},
				objectEntryFolderExternalReferenceCode: 'L_FILES',
				title: file1Title,
			},
			applicationName,
			spaceName
		);

		await spaceSummaryPage.goto(spaceName);

		expect(page.getByText(file1Title)).toBeVisible();

		await apiHelpers.objectEntry.deleteObjectEntry(
			applicationName,
			String(objectEntry1.id)
		);
	}
);

test(
	'Can access to View All Content page',
	{tag: '@LPD-62706'},
	async ({page, spaceSummaryPage}) => {
		const spaceName = 'Default';

		await spaceSummaryPage.goto(spaceName);
		await spaceSummaryPage.viewAllContentLink.click();

		expect(page.getByRole('link', {name: spaceName})).toBeVisible();
		expect(page.getByRole('link', {name: 'Contents'})).toBeVisible();
		expect(page.getByText('No Content Yet')).toBeVisible();
	}
);

test(
	'Can view added content in the space summary page',
	{tag: '@LPD-62706'},
	async ({apiHelpers, page, spaceSummaryPage}) => {
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

		await spaceSummaryPage.goto(spaceName);

		expect(page.getByText(file1Title)).toBeVisible();

		await apiHelpers.objectEntry.deleteObjectEntry(
			applicationName,
			String(objectEntry1.id)
		);
	}
);
