/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';
import {getTempDir} from '../../../utils/temp';
import {exportImportPagesTest} from './fixtures/exportImportPagesTest';

export const test = mergeTests(
	dataApiHelpersTest,
	exportImportPagesTest,
	isolatedSiteTest,
	loginTest()
);

test(
	'Can export at site level with a custom task name',
	{tag: '@LPD-57655'},
	async ({exportImportPage, site}) => {
		await exportImportPage.goToExport(site.friendlyUrlPath);

		const name = `MyExport-${getRandomString()}`;

		await exportImportPage.export(name);

		await expect(exportImportPage.taskStatusLabel(name)).toBeVisible();

		expect(await exportImportPage.download(name)).toBe(
			`${getTempDir()}${name}.lar`
		);
	}
);

test(
	'Can select comments and ratings at site level',
	{tag: '@LPD-57655'},
	async ({
		apiHelpers,
		exportImportDataSelectionPage,
		exportImportPage,
		page,
		site,
	}) => {

		// Add a blog so the Content & Data section lists it

		await apiHelpers.headlessDelivery.postBlog(site.id);

		await exportImportPage.goToExport(site.friendlyUrlPath);

		await exportImportPage.clickNew();

		await exportImportDataSelectionPage.expandSection('Content & Data');

		await expect(page.getByText('Comments and Ratings')).toBeVisible();
	}
);

test(
	'Data sections are checked by default and can be toggled',
	{tag: '@LPD-57655'},
	async ({exportImportDataSelectionPage, exportImportPage, site}) => {
		await exportImportPage.goToExport(site.friendlyUrlPath);

		await exportImportPage.clickNew();

		await expect(
			exportImportDataSelectionPage.sectionCheckbox('Site Builder')
		).toBeChecked();

		await exportImportDataSelectionPage.unselectSection('Site Builder');

		await expect(
			exportImportDataSelectionPage.sectionCheckbox('Site Builder')
		).not.toBeChecked();

		await exportImportDataSelectionPage.selectSection('Site Builder');

		await expect(
			exportImportDataSelectionPage.sectionCheckbox('Site Builder')
		).toBeChecked();
	}
);
