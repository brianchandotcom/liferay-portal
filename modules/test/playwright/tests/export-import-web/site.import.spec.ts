/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ObjectDefinitionApi,
	ObjectField,
} from '@liferay/object-admin-rest-client-js';
import {Page, expect, mergeTests} from '@playwright/test';
import fs from 'fs/promises';
import * as path from 'path';
import {getComparator} from 'playwright-core/lib/utils';

import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {depotAdminPageTest} from '../../fixtures/depotAdminPageTest';
import {documentLibraryPagesTest} from '../../fixtures/documentLibraryPages.fixtures';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {pageTemplatesPagesTest} from '../../fixtures/pageTemplatesPagesTest';
import {productMenuPageTest} from '../../fixtures/productMenuPageTest';
import {wikiPagesTest} from '../../fixtures/wikiPagesTest';
import getRandomString from '../../utils/getRandomString';
import {getTempDir} from '../../utils/temp';
import {readFileFromZip} from '../../utils/zip';
import {companyExportImportPageTest} from './fixtures/companyExportImportPagesTest';
import {exportImportPagesTest} from './fixtures/exportImportPagesTest';
import {stagingPageTest} from './fixtures/stagingPageTest';

export const test = mergeTests(
	companyExportImportPageTest,
	dataApiHelpersTest,
	depotAdminPageTest,
	documentLibraryPagesTest,
	featureFlagsTest({
		'LPD-35013': {enabled: true},
		'LPD-35914': {enabled: false, system: true},
	}),
	exportImportPagesTest,
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest,
	pageTemplatesPagesTest,
	productMenuPageTest,
	stagingPageTest,
	wikiPagesTest
);

async function getSiteHomePageScreenshot(
	page: Page,
	siteKey: string,
	{staging}: {staging: boolean}
) {
	await page.goto(`/web/${siteKey}${staging ? '-staging' : ''}`);

	const url = page.url();

	await page.goto(`${url}?p_l_mode=preview`, {waitUntil: 'load'});

	await page.waitForFunction(() => document.fonts.ready);

	const screenshot = await page.screenshot({
		fullPage: true,
		mask: [page.getByTestId('notificationsCount')],
		path: path.join(
			getTempDir(),
			`${siteKey}-${staging ? 'staging' : 'live'}.png`
		),
	});

	await page.goto(url);

	return screenshot;
}

test(
	'Make sure we do not export-import wikiNodes if they are not selected in the export configuration screen',
	{tag: '@LPD-40988'},
	async ({
		exportImportPage,
		page,
		pageEditorPage,
		pageTemplatesPage,
		site,
		wikiPage,
	}) => {
		await wikiPage.goto(site.friendlyUrlPath);

		await wikiPage.createNewWikiNode('Wiki Node Title');

		await pageTemplatesPage.goto(site.friendlyUrlPath);

		// Create page template collection

		const pageTemplateCollectionName = getRandomString();

		await pageTemplatesPage.addPageTemplateCollection(
			pageTemplateCollectionName
		);

		await expect(
			page.getByRole('menuitem', {
				exact: true,
				name: pageTemplateCollectionName,
			})
		).toBeVisible();

		// Create widget page template

		const pageTemplateName = getRandomString();

		await pageTemplatesPage.addWidgetPageTemplate(pageTemplateName);

		await pageTemplatesPage.page.getByLabel('Add', {exact: true}).click();

		await pageEditorPage.addWidgetToWidgetPageTemplate(
			'Content Management',
			'Web Content Display'
		);

		await wikiPage.goto(site.friendlyUrlPath);

		await exportImportPage.goToExport();

		const exportName = 'MyExport-' + getRandomString();

		await exportImportPage.createNewExportProcess(exportName);

		await expect(
			exportImportPage.page
				.locator('//h2[span[normalize-space()="' + exportName + '"]]')
				.first()
				.locator('../..')
				.getByText('Successful')
		).toBeVisible();

		const exportFilePath =
			await exportImportPage.downloadExportProcess(exportName);

		await exportImportPage.goToImport();

		await exportImportPage.checkItemInNewlyCreatedImportProcess(
			exportFilePath,
			'Wiki'
		);
	}
);

test(
	'can XSS with `searchContainerId` in Asset Libraries import',
	{tag: '@LPS-195766'},
	async ({apiHelpers, depotAdminPage, page}) => {
		const depotName = getRandomString();

		await apiHelpers.jsonWebServicesDepot.addDepotEntry(depotName);

		await depotAdminPage.goToDepotByName(depotName);

		await depotAdminPage.gotoImport();

		const paramName =
			'_com_liferay_exportimport_web_portlet_ImportPortlet_searchContainerId';

		const requestPromise = page.waitForRequest(
			(request) =>
				request.method() === 'GET' && request.url().includes(paramName)
		);

		const request = await requestPromise;

		const insertString = '%22%3E%3Cimg%20src=1%20onerror=alert(123)%3E';

		const [urlBase, urlParam] = request.url().split(`${paramName}=`);

		const newUrl = `${urlBase}${paramName}=${urlParam.replace(/([^&]+)/, `$1${insertString}`)}`;

		let alertTriggered = false;

		page.on('dialog', async (dialog) => {
			if (dialog.type() === 'alert') {
				alertTriggered = true;
				await dialog.dismiss();
			}
		});

		await page.goto(newUrl);

		expect(alertTriggered).toBe(false);
	}
);

test('can import a folder with document type restrictions and workflow', async ({
	apiHelpers,
	documentLibraryEditFolderPage,
	documentLibraryPage,
	exportImportFramePage,
}) => {
	await documentLibraryPage.goto();
	await documentLibraryPage.openOptionsMenu();
	await documentLibraryPage.exportImportOptionsMenuItem.click();
	await exportImportFramePage.importLARFile(
		path.join(__dirname, 'dependencies', 'folder.portlet.lar')
	);
	await exportImportFramePage.close();
	await documentLibraryPage.goToEditFolder('LPS-205933');

	expect(
		await documentLibraryEditFolderPage.getSelectedWorkflowDefinition()
	).toBe('Single Approver@1');

	await apiHelpers.headlessDelivery.deleteSiteDocumentsFolderByExternalReferenceCode(
		'LPS-205933'
	);
});

test('can import a lar file selecting some items to import', async ({
	exportImportPage,
}) => {
	await exportImportPage.goToExport();

	const exportName = 'MyExport-' + getRandomString();

	await exportImportPage.createNewExportProcess(exportName);

	await expect(
		exportImportPage.page
			.locator('//h2[span[normalize-space()="' + exportName + '"]]')
			.first()
			.locator('../..')
			.getByText('Successful')
	).toBeVisible();

	const exportFilePath =
		await exportImportPage.downloadExportProcess(exportName);

	await exportImportPage.goToImport();

	await exportImportPage.createNewImportProcess(exportFilePath);

	await expect(
		exportImportPage.page
			.getByText(exportName)
			.locator('../../..')
			.getByText('Successful')
	).toBeVisible();
});

[
	{name: 'com.liferay.site.initializer.masterclass', shouldFail: true},
	{name: 'com.liferay.site.initializer.welcome'},
].forEach(({name, shouldFail}) => {
	test(`site initializer ${name} can be exported and imported`, async ({
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
			await getSiteHomePageScreenshot(page, site.name, {staging: false}),
			await getSiteHomePageScreenshot(page, site.name, {staging: true})
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

test('can export and import custom object entries at instance level', async ({
	apiHelpers,
	companyExportImportPage,
}) => {
	const objectActionApiClient =
		await apiHelpers.buildRestClient(ObjectDefinitionApi);

	const {body: objectDefinition} =
		await objectActionApiClient.postObjectDefinition({
			active: true,
			externalReferenceCode: 'test',
			label: {
				en_US: 'Test',
			},
			name: 'Test',
			objectFields: [
				{
					DBType: ObjectField.DBTypeEnum.String,
					businessType: ObjectField.BusinessTypeEnum.Text,
					indexed: true,
					indexedAsKeyword: true,
					label: {
						en_US: 'Name',
					},
					name: 'name',
					required: true,
				},
			],
			pluralLabel: {
				en_US: 'Tests',
			},
			portlet: true,
			scope: 'company',
			status: {
				code: 0,
			},
		});

	apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

	const objectEntry = await apiHelpers.objectEntry.postObjectEntry(
		{externalReferenceCode: '', name: 'test'},
		'c/tests'
	);

	const exportFilePath =
		await companyExportImportPage.export('Tests 1 Items');

	const content = await readFileFromZip('C_Test.json', exportFilePath);

	const json = JSON.parse(content);

	expect(json.length).toBe(1);
	expect(json[0]).not.toHaveProperty('permissions');

	await apiHelpers.delete(`${apiHelpers.baseUrl}c/tests/${objectEntry.id}`);

	expect(
		await apiHelpers.get(
			`${apiHelpers.baseUrl}c/tests/by-external-reference-code/${objectEntry.externalReferenceCode}`
		)
	).toEqual({status: 'NOT_FOUND'});

	await companyExportImportPage.import(exportFilePath);

	expect(
		await apiHelpers.get(
			`${apiHelpers.baseUrl}c/tests/by-external-reference-code/${objectEntry.externalReferenceCode}`
		)
	).toEqual(
		expect.objectContaining({
			externalReferenceCode: objectEntry.externalReferenceCode,
			name: objectEntry.name,
		})
	);
});

test('can import custom object entries at instance level with or without permissions based on selection', async ({
	apiHelpers,
	companyExportImportPage,
}) => {
	const objectActionApiClient =
		await apiHelpers.buildRestClient(ObjectDefinitionApi);

	const {body: objectDefinition} =
		await objectActionApiClient.postObjectDefinition({
			active: true,
			externalReferenceCode: 'test',
			label: {
				en_US: 'Test',
			},
			name: 'Test',
			objectFields: [
				{
					DBType: ObjectField.DBTypeEnum.String,
					businessType: ObjectField.BusinessTypeEnum.Text,
					indexed: true,
					indexedAsKeyword: true,
					label: {
						en_US: 'Name',
					},
					name: 'name',
					required: true,
				},
			],
			pluralLabel: {
				en_US: 'Tests',
			},
			portlet: true,
			scope: 'company',
			status: {
				code: 0,
			},
		});

	apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

	let objectEntry = await apiHelpers.objectEntry.postObjectEntry(
		{
			externalReferenceCode: '',
			name: 'test',
			permissions: [
				{
					actionIds: ['VIEW'],
					roleName: 'Guest',
				},
			],
		},
		'c/tests'
	);

	// Export with permissions

	const exportFilePath = await companyExportImportPage.export(
		'Tests 1 Items',
		false,
		true
	);

	// Import with permissions

	await apiHelpers.delete(`${apiHelpers.baseUrl}c/tests/${objectEntry.id}`);

	expect(
		await apiHelpers.objectEntry.getObjectEntryByExternalReferenceCode(
			'c/tests',
			objectEntry.externalReferenceCode
		)
	).toEqual({status: 'NOT_FOUND'});

	await companyExportImportPage.import(exportFilePath, true);

	objectEntry = await apiHelpers.get(
		`${apiHelpers.baseUrl}c/tests/by-external-reference-code/${objectEntry.externalReferenceCode}/?nestedFields=permissions`
	);

	expect(objectEntry).toEqual(
		expect.objectContaining({
			permissions: [
				{
					actionIds: ['VIEW'],
					roleName: 'Guest',
				},
			],
		})
	);

	// Import without permissions

	await apiHelpers.delete(`${apiHelpers.baseUrl}c/tests/${objectEntry.id}`);

	expect(
		await apiHelpers.objectEntry.getObjectEntryByExternalReferenceCode(
			'c/tests',
			objectEntry.externalReferenceCode
		)
	).toEqual({status: 'NOT_FOUND'});

	await companyExportImportPage.import(exportFilePath);

	objectEntry = await apiHelpers.get(
		`${apiHelpers.baseUrl}c/tests/by-external-reference-code/${objectEntry.externalReferenceCode}/?nestedFields=permissions`
	);

	expect(objectEntry).not.toEqual(
		expect.objectContaining({
			permissions: [
				{
					actionIds: ['VIEW'],
					roleName: 'Guest',
				},
			],
		})
	);
});

test('can see corresponding elements at instance level', async ({
	apiHelpers,
	companyExportImportPage,
}) => {
	const objectActionApiClient =
		await apiHelpers.buildRestClient(ObjectDefinitionApi);

	const {body: objectDefinition} =
		await objectActionApiClient.postObjectDefinition({
			active: true,
			externalReferenceCode: 'test',
			label: {
				en_US: 'Test',
			},
			name: 'Test',
			objectFields: [
				{
					DBType: ObjectField.DBTypeEnum.String,
					businessType: ObjectField.BusinessTypeEnum.Text,
					indexed: true,
					indexedAsKeyword: true,
					label: {
						en_US: 'Name',
					},
					name: 'name',
					required: true,
				},
			],
			pluralLabel: {
				en_US: 'Tests',
			},
			portlet: true,
			scope: 'company',
			status: {
				code: 0,
			},
		});

	apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

	const exportFilePath =
		await companyExportImportPage.export('Tests 1 Items');

	await companyExportImportPage.page.goto('/');

	await companyExportImportPage.goToImportOptions(exportFilePath);

	await expect(
		companyExportImportPage.page.getByRole('group', {name: 'Pages'})
	).not.toBeVisible();

	await expect(
		companyExportImportPage.page.getByText('Comments, Ratings')
	).not.toBeVisible();
});

test('can see corresponding elements at site level', async ({
	apiHelpers,
	exportImportPage,
}) => {
	const objectActionApiClient =
		await apiHelpers.buildRestClient(ObjectDefinitionApi);

	const {body: objectDefinition} =
		await objectActionApiClient.postObjectDefinition({
			active: true,
			externalReferenceCode: 'test',
			label: {
				en_US: 'Test',
			},
			name: 'Test',
			objectFields: [
				{
					DBType: ObjectField.DBTypeEnum.String,
					businessType: ObjectField.BusinessTypeEnum.Text,
					indexed: true,
					indexedAsKeyword: true,
					label: {
						en_US: 'Name',
					},
					name: 'name',
					required: true,
				},
			],
			pluralLabel: {
				en_US: 'Tests',
			},
			portlet: true,
			scope: 'company',
			status: {
				code: 0,
			},
		});

	apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

	await exportImportPage.goToExport();

	const exportName = 'MyExport-' + getRandomString();

	await exportImportPage.createNewExportProcess(exportName);

	await expect(
		exportImportPage.page
			.getByText(exportName)
			.locator('../..')
			.getByText('Successful')
	).toBeVisible();

	const exportFilePath =
		await exportImportPage.downloadExportProcess(exportName);

	await exportImportPage.goToImport();

	await exportImportPage.goToImportOptions(exportFilePath);

	await expect(
		exportImportPage.page.getByText('Comments, Ratings')
	).toBeVisible();

	await expect(
		exportImportPage.page.getByRole('group', {name: 'Pages'})
	).toBeVisible();
});
