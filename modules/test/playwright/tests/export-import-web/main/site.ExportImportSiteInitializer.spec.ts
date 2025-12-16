/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ObjectDefinition,
	ObjectRelationship,
	ObjectRelationshipAPI,
} from '@liferay/object-admin-rest-client-js';
import {Page, expect, mergeTests} from '@playwright/test';
import fs from 'fs/promises';
import * as path from 'path';
import {getComparator} from 'playwright-core/lib/utils';

import {applicationsMenuPageTest} from '../../../fixtures/applicationsMenuPageTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {styleBookPageTest} from '../../../fixtures/styleBookPageTest';
import {uiElementsPageTest} from '../../../fixtures/uiElementsTest';
import {getRandomInt} from '../../../utils/getRandomInt';
import getRandomString from '../../../utils/getRandomString';
import {getSiteHomePageScreenshot} from '../../../utils/getSiteHomePageScreenshot';
import {getTempDir} from '../../../utils/temp';
import {companyExportImportPageTest} from './fixtures/companyExportImportPagesTest';
import {exportImportPagesTest} from './fixtures/exportImportPagesTest';
import {stagingPageTest} from './fixtures/stagingPageTest';

const test = mergeTests(
	applicationsMenuPageTest,
	companyExportImportPageTest,
	exportImportPagesTest,
	dataApiHelpersTest,
	loginTest(),
	stagingPageTest,
	styleBookPageTest,
	uiElementsPageTest
);

const testWithExportImportAtInstanceLevelFF = mergeTests(
	test,
	featureFlagsTest({
		'LPD-35443': {enabled: true},
		'LPD-35914': {enabled: true},
		'LPD-45276': {enabled: true},
	})
);

[
	{name: 'com.liferay.site.initializer.masterclass', shouldFail: true},
	{name: 'com.liferay.site.initializer.welcome'},
].forEach(({name, shouldFail}) => {
	test(`Local Staging can be enabled with site initializer ${name}`, async ({
		apiHelpers,
		page,
		stagingPage,
	}, testInfo) => {
		testInfo.fail(shouldFail);

		const site = await apiHelpers.headlessSite.createSite({
			name,
			templateKey: name,
			templateType: 'site-initializer',
		});

		expect(site.name).toBeDefined();

		apiHelpers.data.push({id: site.id, type: 'site'});

		await stagingPage.goto(site.name);

		await stagingPage.enableLocalStaging();

		const comparator = getComparator('image/png');

		const buffer = comparator(
			await getSiteHomePageScreenshot(
				page,
				site.name,
				{staging: false},
				page.getByTestId('notificationsCount')
			),
			await getSiteHomePageScreenshot(
				page,
				site.name,
				{staging: true},
				page.getByTestId('notificationsCount')
			)
		);

		if (buffer !== null && buffer.diff !== undefined) {
			const diffPath = path.join(getTempDir(), `${site.name}-diff.png`);
			await fs.writeFile(diffPath, buffer.diff);
			throw new Error(
				`The live and staging pages differ. Check the screenshot diff at "${diffPath}".`
			);
		}
	});
});

[
	{name: 'com.liferay.site.initializer.masterclass'},
	{
		mask: (page: Page) => page.locator('.user-personal-bar'),
		name: 'com.liferay.site.initializer.welcome',
	},
].forEach(({mask, name}) => {
	testWithExportImportAtInstanceLevelFF(
		`Can export and import a site created with the ${name} site initializer`,
		async ({apiHelpers, exportImportPage, page}) => {
			let exportFilePath: string;
			let exportName: string;
			let exportableItems1: Map<string, number>;
			let exportableItems2: Map<string, number>;
			let site1: Site;
			let site2: Site;

			await testWithExportImportAtInstanceLevelFF.step(
				'Create the site 1 from the template',
				async () => {
					site1 = await apiHelpers.headlessSite.createSite({
						name: getRandomString(),
						templateKey: name,
						templateType: 'site-initializer',
					});

					apiHelpers.data.push({id: site1.id, type: 'site'});
				}
			);

			await testWithExportImportAtInstanceLevelFF.step(
				'Export the site 1',
				async () => {
					await exportImportPage.goToExport(site1.friendlyUrlPath);

					exportableItems1 =
						await exportImportPage.getExportableItems();

					exportName = `MyExport-${getRandomString()}`;

					await exportImportPage.exportAll(exportName);

					await exportImportPage
						.taskSuccessLabel(exportName)
						.waitFor();

					exportFilePath =
						await exportImportPage.downloadExportProcess(
							exportName
						);
				}
			);

			await testWithExportImportAtInstanceLevelFF.step(
				'Create the site 2',
				async () => {
					site2 = await apiHelpers.headlessSite.createSite({
						name: getRandomString(),
					});

					apiHelpers.data.push({id: site2.id, type: 'site'});
				}
			);

			await testWithExportImportAtInstanceLevelFF.step(
				'Import the site 1 into site 2',
				async () => {
					await exportImportPage.goToImport(site2.friendlyUrlPath);

					await exportImportPage.import(exportFilePath);

					await exportImportPage
						.taskSuccessLabel(exportName)
						.waitFor();
				}
			);

			await testWithExportImportAtInstanceLevelFF.step(
				'Assert the exportable items from site 1 and site 2 are equal',
				async () => {
					await exportImportPage.goToExport(site2.friendlyUrlPath);

					exportableItems2 =
						await exportImportPage.getExportableItems();

					expect(exportableItems1).toEqual(exportableItems2);
				}
			);

			await test.step('Assert the home page screenshots from site 1 and site 2 are equal', async () => {
				const comparator = getComparator('image/png');

				const buffer = comparator(
					await getSiteHomePageScreenshot(
						page,
						site1.name,
						{staging: false},
						mask?.(page)
					),
					await getSiteHomePageScreenshot(
						page,
						site2.name,
						{staging: false},
						mask?.(page)
					)
				);

				if (buffer !== null && buffer.diff !== undefined) {
					const diffPath = path.join(
						getTempDir(),
						`${site1.name}-diff.png`
					);
					await fs.writeFile(diffPath, buffer.diff);
					throw new Error(
						`The site 1 and site 2 home pages differ. Check the screenshot diff at "${diffPath}".`
					);
				}
			});
		}
	);
});

testWithExportImportAtInstanceLevelFF(
	'Can export and import a site created with the Clarity site initializer including all exportable items',
	{tag: '@LPD-64056'},
	async ({
		apiHelpers,
		exportImportPage,
		page,
		styleBooksPage,
		uploadServletRequestSystemSettingsPage,
	}) => {
		testWithExportImportAtInstanceLevelFF.setTimeout(300000);

		let exportFilePath: string;
		let exportName: string;
		let exportableItems1: Map<string, number>;
		let exportableItems2: Map<string, number>;
		let objectDefinition1: ObjectDefinition;
		let objectDefinition2: ObjectDefinition;
		let objectRelationship: ObjectRelationship;
		let originalOverallMaximumUploadRequestSize: string;
		let site1: Site;
		let site2: Site;

		await testWithExportImportAtInstanceLevelFF.step(
			'Increase the maximum upload request size',
			async () => {
				await uploadServletRequestSystemSettingsPage.goto();

				originalOverallMaximumUploadRequestSize =
					await uploadServletRequestSystemSettingsPage.getOverallMaximumUploadRequestSize();

				await uploadServletRequestSystemSettingsPage.setOverallMaximumUploadRequestSize(
					{
						size: '200000000',
					}
				);
			}
		);

		try {
			await testWithExportImportAtInstanceLevelFF.step(
				'Create the Object definitions with 1-M relationship',
				async () => {
					const objectFolder =
						await apiHelpers.objectAdmin.postRandomObjectFolder();

					apiHelpers.data.push({
						id: objectFolder.id,
						type: 'objectFolder',
					});

					objectDefinition1 =
						await apiHelpers.objectAdmin.postRandomObjectDefinition(
							{
								objectFolderExternalReferenceCode:
									objectFolder.externalReferenceCode,
								scope: 'site',
								status: {code: 0},
							}
						);

					apiHelpers.data.push({
						id: objectDefinition1.id,
						type: 'objectDefinition',
					});

					objectDefinition2 =
						await apiHelpers.objectAdmin.postRandomObjectDefinition(
							{
								objectFolderExternalReferenceCode:
									objectFolder.externalReferenceCode,
								scope: 'site',
								status: {code: 0},
							}
						);

					apiHelpers.data.push({
						id: objectDefinition2.id,
						type: 'objectDefinition',
					});

					const objectRelationshipAPIClient =
						await apiHelpers.buildRestClient(ObjectRelationshipAPI);

					({body: objectRelationship} =
						await objectRelationshipAPIClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
							objectDefinition1.externalReferenceCode,
							{
								label: {
									en_US: `objectRelationshipLabel${getRandomInt()}`,
								},
								name: `objectRelationshipName${getRandomInt()}`,
								objectDefinitionExternalReferenceCode1:
									objectDefinition1.externalReferenceCode,
								objectDefinitionExternalReferenceCode2:
									objectDefinition2.externalReferenceCode,
								objectDefinitionId1: objectDefinition1.id,
								objectDefinitionId2: objectDefinition2.id,
								objectDefinitionName2: objectDefinition2.name,
								type: 'oneToMany',
							}
						));
				}
			);

			await testWithExportImportAtInstanceLevelFF.step(
				'Create the site 1 from the template',
				async () => {
					site1 = await apiHelpers.headlessSite.createSite({
						name: getRandomString(),
						templateKey:
							'com.liferay.site.initializer.teaser.showcase',
						templateType: 'site-initializer',
					});

					apiHelpers.data.push({id: site1.id, type: 'site'});
				}
			);

			await testWithExportImportAtInstanceLevelFF.step(
				'Add Object entry to the site 1',
				async () => {
					await apiHelpers.objectEntry.postObjectEntry(
						{
							textField: getRandomString(),
							[objectRelationship.name]: [
								{
									textField: getRandomString(),
								},
							],
						},
						`c/${objectDefinition1.name.toLowerCase()}s/scopes/${site1.name}`
					);
				}
			);

			await testWithExportImportAtInstanceLevelFF.step(
				'Add a Style Book on the site 1',
				async () => {
					await styleBooksPage.goto(site1.friendlyUrlPath);

					await styleBooksPage.create(getRandomString());
				}
			);

			await testWithExportImportAtInstanceLevelFF.step(
				'Export the site 1',
				async () => {
					await exportImportPage.goToExport(site1.friendlyUrlPath);

					exportableItems1 =
						await exportImportPage.getExportableItems();

					expect(exportableItems1.has(objectDefinition1.name)).toBe(
						true
					);

					expect(exportableItems1.has(objectDefinition2.name)).toBe(
						true
					);

					expect(exportableItems1.has('Style Books')).toBe(true);

					exportName = `MyExport-${getRandomString()}`;

					await exportImportPage.exportAll(exportName);

					await expect(
						exportImportPage.taskSuccessLabel(exportName)
					).toBeVisible({timeout: 60000});

					exportFilePath =
						await exportImportPage.downloadExportProcess(
							exportName
						);
				}
			);

			await testWithExportImportAtInstanceLevelFF.step(
				'Create the site 2',
				async () => {
					site2 = await apiHelpers.headlessSite.createSite({
						name: getRandomString(),
					});

					apiHelpers.data.push({id: site2.id, type: 'site'});
				}
			);

			await testWithExportImportAtInstanceLevelFF.step(
				'Import the site 1 into site 2',
				async () => {
					await exportImportPage.goToImport(site2.friendlyUrlPath);

					await exportImportPage.import(exportFilePath);

					await expect(
						exportImportPage.taskSuccessLabel(exportName)
					).toBeVisible({timeout: 60000});
				}
			);

			await testWithExportImportAtInstanceLevelFF.step(
				'Assert the exportable items from site 1 and site 2 are equal',
				async () => {
					await exportImportPage.goToExport(site2.friendlyUrlPath);

					exportableItems2 =
						await exportImportPage.getExportableItems();

					expect(exportableItems2.size).toEqual(
						exportableItems1.size
					);

					for (const [name, count] of exportableItems1.entries()) {
						if (name === 'Calendar' || name === 'Categories') {

							// TODO LPD-64899, LPD-65749

							expect(exportableItems2.get(name)).toBe(count);
						}
						else if (name === 'Style Books') {

							// TODO LPD-64905

							expect(exportableItems2.get(name)).toBe(count);
						}
						else {
							expect(exportableItems2.get(name)).toBe(count);
						}
					}
				}
			);

			await test.step('Assert the home page screenshots from site 1 and site 2 are equal', async () => {
				const comparator = getComparator('image/png');

				const buffer = comparator(
					await getSiteHomePageScreenshot(page, site1.name, {
						staging: false,
					}),
					await getSiteHomePageScreenshot(page, site2.name, {
						staging: false,
					})
				);

				if (buffer !== null && buffer.diff !== undefined) {
					const diffPath = path.join(
						getTempDir(),
						`${site1.name}-diff.png`
					);
					await fs.writeFile(diffPath, buffer.diff);
					throw new Error(
						`The site 1 and site 2 home pages differ. Check the screenshot diff at "${diffPath}".`
					);
				}
			});
		}
		finally {
			await testWithExportImportAtInstanceLevelFF.step(
				'Restore the initial maximum upload request size',
				async () => {
					await uploadServletRequestSystemSettingsPage.goto();

					await uploadServletRequestSystemSettingsPage.setOverallMaximumUploadRequestSize(
						{
							size: originalOverallMaximumUploadRequestSize,
						}
					);
				}
			);
		}
	}
);
