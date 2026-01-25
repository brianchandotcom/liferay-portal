/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {pagesAdminPagesTest} from '../../../fixtures/pagesAdminPagesTest';
import {captureScreenshot} from '../../../utils/captureScreenshot';
import {compareScreenshots} from '../../../utils/compareScreenshots';
import getRandomString from '../../../utils/getRandomString';
import {exportImportPagesTest} from '../../export-import-web/main/fixtures/exportImportPagesTest';
import getContainerDefinition from '../../layout-content-page-editor-web/main/utils/getContainerDefinition';
import getFragmentDefinition from '../../layout-content-page-editor-web/main/utils/getFragmentDefinition';
import getGridDefinition from '../../layout-content-page-editor-web/main/utils/getGridDefinition';
import getPageDefinition from '../../layout-content-page-editor-web/main/utils/getPageDefinition';

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

test(
	'Compare fragment configurations when exporting/importing a site',
	{tag: '@LPD-74680'},
	async ({
		apiHelpers,
		exportImportPage,
		page,
		pageEditorPage,
		site: siteA,
	}) => {
		const getFragmentConfigurationScreenshots = async ({screenshots}) => {
			const configurationPanel = page.getByLabel('Configuration Panel', {
				exact: true,
			});
			const treeNodes = page.locator(
				'.page-editor__page-structure__tree-node__name'
			);

			const getConfigurationScreenshot = async (
				configurationTabName: ConfigurationTab
			) => {
				await pageEditorPage.goToConfigurationTab(configurationTabName);

				await expect(
					page.getByRole('tabpanel', {name: configurationTabName})
				).toHaveClass(/active/);

				return await captureScreenshot({
					locator: configurationPanel,
					name: `${configurationTabName}_${getRandomString()}`,
				});
			};

			let clicked = 0;

			while (true) {
				const count = await treeNodes.count();

				if (clicked === count) {
					break;
				}

				for (let i = clicked; i < count; i++) {
					await treeNodes.nth(i).scrollIntoViewIfNeeded();
					await treeNodes.nth(i).click({force: true});

					const text = await treeNodes.nth(i).innerText();

					if (text.includes('element-text')) {
						screenshots.push(
							await getConfigurationScreenshot('Mapping')
						);

						screenshots.push(
							await getConfigurationScreenshot('Link')
						);
					}
					else if (!text.includes('Module')) {
						screenshots.push(
							await getConfigurationScreenshot('General')
						);

						screenshots.push(
							await getConfigurationScreenshot('Styles')
						);

						screenshots.push(
							await getConfigurationScreenshot('Advanced')
						);
					}
				}

				clicked = count;
			}
		};

		// Change the viewport size to capture all the configuration content in the screenshot

		await page.setViewportSize({height: 1525, width: 1437});

		// Create a page in the Site A with a Container, a Grid and a Heading

		const containerDefinition = getContainerDefinition({
			id: getRandomString(),
		});

		const headingDefinition = getFragmentDefinition({
			id: getRandomString(),
			key: 'BASIC_COMPONENT-heading',
		});

		const gridDefinition = getGridDefinition({
			columns: [
				{pageElements: [headingDefinition], size: 4},
				{size: 4},
				{size: 4},
			],
			id: getRandomString(),
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([
				containerDefinition,
				gridDefinition,
			]),
			siteId: siteA.id,
			title: getRandomString(),
		});

		await pageEditorPage.goto(layout, siteA.friendlyUrlPath);

		// Go to the Browser and take screenshoots of each configuration for each fragment in the Site A

		await pageEditorPage.goToSidebarTab('Browser');

		const screenshotsA = [];

		await getFragmentConfigurationScreenshots({screenshots: screenshotsA});

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

		// Go to the Browser and take screenshoots of each configuration for each fragment in the Site B

		await pageEditorPage.goto(layout, siteB.friendlyUrlPath);

		await pageEditorPage.goToSidebarTab('Browser');

		const screenshotsB = [];

		await getFragmentConfigurationScreenshots({screenshots: screenshotsB});

		// Compare screenshots

		for (let i = 0; i < screenshotsA.length; i++) {
			compareScreenshots(screenshotsA[i], screenshotsB[i]);
		}
	}
);
