/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {pagesAdminPagesTest} from '../../../fixtures/pagesAdminPagesTest';
import {compareScreenshots} from '../../../utils/compareScreenshots';
import getRandomString from '../../../utils/getRandomString';
import {exportImportPagesTest} from '../../export-import-web/main/fixtures/exportImportPagesTest';

const test = mergeTests(
	exportImportPagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-35443': {enabled: true},
		'LPD-35914': {enabled: true},
		'LPD-41367': {enabled: true},
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest,
	pagesAdminPagesTest
);

test(
	'Compare a page when exporting/importing a site',
	{tag: '@LPD-74680'},
	async ({
		apiHelpers,
		exportImportPage,
		pageEditorPage,
		pagesAdminPage,
		site: siteA,
	}) => {

		// Create a page in the Site A through the UI and take screenshots in view mode and edit mode

		const layoutName = getRandomString();

		await pagesAdminPage.goto(siteA.friendlyUrlPath);

		await pagesAdminPage.createNewPage({
			draft: true,
			name: layoutName,
			template: 'Blank',
		});

		await pageEditorPage.addFragment('Basic Components', 'Heading');

		await pageEditorPage.publishPage();

		const viewModeScreenshotA = await pageEditorPage.captureScreenshot({
			layoutName,
			name: 'viewModeScreenshotA.png',
			siteUrl: siteA.friendlyUrlPath,
		});

		const editModeScreenshotA = await pageEditorPage.captureScreenshot({
			layoutName,
			layoutOptions: {editMode: true},
			name: 'editModeScreenshotA.png',
			siteUrl: siteA.friendlyUrlPath,
		});

		// Export a site A

		await exportImportPage.goToExport(siteA.friendlyUrlPath);

		const exportFilePath = await exportImportPage.export();

		// Create a site B

		const siteB = await apiHelpers.headlessSite.createSite({
			name: getRandomString(),
		});

		apiHelpers.data.push({id: siteB.id, type: 'site'});

		// Import the site A into the site B

		await exportImportPage.goToImport(siteB.friendlyUrlPath);

		await exportImportPage.import({filePath: exportFilePath});

		// Take screenshots in the Site B

		const viewModeScreenshotB = await pageEditorPage.captureScreenshot({
			layoutName,
			name: 'viewModeScreenshotB.png',
			siteUrl: siteB.friendlyUrlPath,
		});

		const editModeScreenshotB = await pageEditorPage.captureScreenshot({
			layoutName,
			layoutOptions: {editMode: true},
			name: 'editModeScreenshotB.png',
			siteUrl: siteB.friendlyUrlPath,
		});

		// Compare screenshots

		compareScreenshots(viewModeScreenshotA, viewModeScreenshotB);
		compareScreenshots(editModeScreenshotA, editModeScreenshotB);
	}
);
