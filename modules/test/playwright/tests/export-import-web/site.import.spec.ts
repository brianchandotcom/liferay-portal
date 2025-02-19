/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ObjectDefinitionApi} from '@liferay/object-admin-rest-client-js';
import {Page, expect, mergeTests} from '@playwright/test';
import fs from 'fs/promises';
import * as path from 'path';
import {getComparator} from 'playwright-core/lib/utils';

import {accountSettingsPagesTest} from '../../fixtures/accountSettingsPagesTest';
import {accountsPagesTest} from '../../fixtures/accountsPagesTest';
import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {depotAdminPageTest} from '../../fixtures/depotAdminPageTest';
import {documentLibraryPagesTest} from '../../fixtures/documentLibraryPages.fixtures';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {objectPagesTest} from '../../fixtures/objectPagesTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {pageTemplatesPagesTest} from '../../fixtures/pageTemplatesPagesTest';
import {productMenuPageTest} from '../../fixtures/productMenuPageTest';
import {usersAndOrganizationsPagesTest} from '../../fixtures/usersAndOrganizationsPagesTest';
import {wikiPagesTest} from '../../fixtures/wikiPagesTest';
import getRandomString from '../../utils/getRandomString';
import performLogin, {performLogout, userData} from '../../utils/performLogin';
import {getTempDir} from '../../utils/temp';
import {waitForAlert} from '../../utils/waitForAlert';
import {readFileFromZip} from '../../utils/zip';
import {companyExportImportPageTest} from './fixtures/companyExportImportPagesTest';
import {exportImportPagesTest} from './fixtures/exportImportPagesTest';
import {stagingPageTest} from './fixtures/stagingPageTest';
import {openImportFieldset} from './utils/openImportFieldset';

export const test = mergeTests(
	accountSettingsPagesTest,
	accountsPagesTest,
	applicationsMenuPageTest,
	companyExportImportPageTest,
	dataApiHelpersTest,
	depotAdminPageTest,
	documentLibraryPagesTest,
	exportImportPagesTest,
	featureFlagsTest({
		'LPD-35013': {enabled: true},
		'LPD-35914': {enabled: false, system: true},
	}),
	isolatedSiteTest,
	loginTest(),
	objectPagesTest,
	pageEditorPagesTest,
	pageTemplatesPagesTest,
	productMenuPageTest,
	stagingPageTest,
	usersAndOrganizationsPagesTest,
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
					DBType: 'String',
					businessType: 'Text',
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

	await expect(
		exportImportPage.page.getByLabel('Delete Application Data')
	).toBeVisible();

	await openImportFieldset({
		name: 'Update Data',
		page: exportImportPage.page,
	});

	await expect(
		exportImportPage.page.getByText(
			'Mirror: All data and content inside the imported LAR is created as new the first time while maintaining a reference to the source. Subsequent imports from the same source update the entries instead of creating new entries.'
		)
	).toBeVisible();

	await expect(
		exportImportPage.page.getByText('Mirror with overwriting:')
	).toBeVisible();

	await expect(exportImportPage.page.getByText('Copy as New:')).toBeVisible();
});

test(
	'can import custom object entries with original creator, and creator user does exist in the current environment',
	{
		tag: '@LPD-43217',
	},
	async ({
		apiHelpers,
		applicationsMenuPage,
		companyExportImportPage,
		editUserPage,
		page,
		usersAndOrganizationsPage,
		viewObjectDefinitionsPage,
	}) => {
		const newObjectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFolderExternalReferenceCode: 'default',
				status: {code: 0},
			});

		const user = await apiHelpers.headlessAdminUser.postUserAccount();

		userData[user.alternateName] = {
			name: user.givenName,
			password: 'test',
			surname: user.familyName,
		};

		await applicationsMenuPage.goToUsersAndOrganizations();
		await usersAndOrganizationsPage.goToUser(user.name);
		await editUserPage.rolesLink.click();

		await editUserPage.selectRegularRolesButton.click();

		await editUserPage
			.selectRegularRolesChooseButton('Administrator')
			.click();

		await editUserPage.selectRegularRolesButton.click();

		await editUserPage.selectRegularRolesChooseButton('Power User').click();

		await editUserPage.saveButton.click();

		await performLogout(page);

		await performLogin(page, user.alternateName);

		await applicationsMenuPage.goToObjects();
		await viewObjectDefinitionsPage.clickEditObjectDefinitionLink(
			newObjectDefinition.name
		);
		await page.getByLabel('Panel Link', {exact: true}).click();
		await page.getByRole('option', {name: 'Object'}).click();
		await page.getByRole('button', {name: 'Save'}).click();
		await applicationsMenuPage.goToObjectDefinition(
			newObjectDefinition.name
		);
		await page.getByText('Add ' + newObjectDefinition.name).click();
		await page.getByLabel('textField').fill('testText');
		await page.getByRole('button', {name: 'Save'}).click();
		await waitForAlert(
			page,
			'Success:Your request completed successfully.'
		);

		const exportFilePath = await companyExportImportPage.export(
			newObjectDefinition.name + ' 1 Items'
		);

		await applicationsMenuPage.goToObjectDefinition(
			newObjectDefinition.name
		);
		await page.getByRole('button', {name: 'Actions'}).click();
		await page.getByRole('menuitem', {name: 'Delete'}).click();
		await page.getByRole('button', {name: 'Delete'}).click();
		await waitForAlert(
			page,
			'Success:Your request completed successfully.'
		);

		await performLogout(page);

		await performLogin(page, 'test');

		await companyExportImportPage.import(exportFilePath);

		await applicationsMenuPage.goToObjectDefinition(
			newObjectDefinition.name
		);
		await expect(
			page.getByRole('cell', {
				name: user.givenName + ' ' + user.familyName,
			})
		).toBeVisible();

		await viewObjectDefinitionsPage.goto();

		await viewObjectDefinitionsPage.clickObjectDefinitionActionButton(
			newObjectDefinition.label['en_US']
		);

		await viewObjectDefinitionsPage.deleteObjectDefinitionOption.click();
		await viewObjectDefinitionsPage.page
			.getByPlaceholder('Confirm Object Definition Name')
			.fill(newObjectDefinition.name);
		await viewObjectDefinitionsPage.page
			.getByRole('button', {name: 'Delete'})
			.click();
	}
);

test(
	'can import custom object entries with original creator, but creator user does not exist in the current environment',
	{
		tag: '@LPD-43217',
	},
	async ({
		apiHelpers,
		applicationsMenuPage,
		companyExportImportPage,
		editUserPage,
		page,
		usersAndOrganizationsPage,
		viewObjectDefinitionsPage,
	}) => {
		const newObjectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFolderExternalReferenceCode: 'default',
				status: {code: 0},
			});

		const user = await apiHelpers.headlessAdminUser.postUserAccount();

		userData[user.alternateName] = {
			name: user.givenName,
			password: 'test',
			surname: user.familyName,
		};

		await applicationsMenuPage.goToUsersAndOrganizations();
		await usersAndOrganizationsPage.goToUser(user.name);
		await editUserPage.rolesLink.click();

		await editUserPage.selectRegularRolesButton.click();

		await editUserPage
			.selectRegularRolesChooseButton('Administrator')
			.click();

		await editUserPage.selectRegularRolesButton.click();

		await editUserPage.selectRegularRolesChooseButton('Power User').click();

		await editUserPage.saveButton.click();

		await performLogout(page);

		await performLogin(page, user.alternateName);

		await applicationsMenuPage.goToObjects();
		await viewObjectDefinitionsPage.clickEditObjectDefinitionLink(
			newObjectDefinition.name
		);
		await page.getByLabel('Panel Link', {exact: true}).click();
		await page.getByRole('option', {name: 'Object'}).click();
		await page.getByRole('button', {name: 'Save'}).click();
		await applicationsMenuPage.goToObjectDefinition(
			newObjectDefinition.name
		);
		await page.getByText('Add ' + newObjectDefinition.name).click();
		await page.getByLabel('textField').fill('testText');
		await page.getByRole('button', {name: 'Save'}).click();
		await waitForAlert(
			page,
			'Success:Your request completed successfully.'
		);

		const exportFilePath = await companyExportImportPage.export(
			newObjectDefinition.name + ' 1 Items'
		);

		await applicationsMenuPage.goToObjectDefinition(
			newObjectDefinition.name
		);
		await page.getByRole('button', {name: 'Actions'}).click();
		await page.getByRole('menuitem', {name: 'Delete'}).click();
		await page.getByRole('button', {name: 'Delete'}).click();
		await waitForAlert(
			page,
			'Success:Your request completed successfully.'
		);

		await performLogout(page);
		await performLogin(page, 'test');
		await apiHelpers.headlessAdminUser.deleteUserAccount(Number(user.id));

		await companyExportImportPage.import(exportFilePath);

		await applicationsMenuPage.goToObjectDefinition(
			newObjectDefinition.name
		);
		await expect(page.getByRole('cell', {name: 'Test Test'})).toBeVisible();

		await viewObjectDefinitionsPage.goto();

		await viewObjectDefinitionsPage.clickObjectDefinitionActionButton(
			newObjectDefinition.label['en_US']
		);

		await viewObjectDefinitionsPage.deleteObjectDefinitionOption.click();
		await viewObjectDefinitionsPage.page
			.getByPlaceholder('Confirm Object Definition Name')
			.fill(newObjectDefinition.name);
		await viewObjectDefinitionsPage.page
			.getByRole('button', {name: 'Delete'})
			.click();
	}
);

test(
	'can import custom object entries with current user as creator',
	{
		tag: '@LPD-43217',
	},
	async ({
		apiHelpers,
		applicationsMenuPage,
		companyExportImportPage,
		editUserPage,
		page,
		usersAndOrganizationsPage,
		viewObjectDefinitionsPage,
	}) => {
		const newObjectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFolderExternalReferenceCode: 'default',
				status: {code: 0},
			});

		const user = await apiHelpers.headlessAdminUser.postUserAccount();

		userData[user.alternateName] = {
			name: user.givenName,
			password: 'test',
			surname: user.familyName,
		};

		await applicationsMenuPage.goToUsersAndOrganizations();
		await usersAndOrganizationsPage.goToUser(user.name);
		await editUserPage.rolesLink.click();

		await editUserPage.selectRegularRolesButton.click();

		await editUserPage
			.selectRegularRolesChooseButton('Administrator')
			.click();

		await editUserPage.selectRegularRolesButton.click();

		await editUserPage.selectRegularRolesChooseButton('Power User').click();

		await editUserPage.saveButton.click();

		await performLogout(page);

		await performLogin(page, user.alternateName);

		await applicationsMenuPage.goToObjects();
		await viewObjectDefinitionsPage.clickEditObjectDefinitionLink(
			newObjectDefinition.name
		);
		await page.getByLabel('Panel Link', {exact: true}).click();
		await page.getByRole('option', {name: 'Object'}).click();
		await page.getByRole('button', {name: 'Save'}).click();
		await applicationsMenuPage.goToObjectDefinition(
			newObjectDefinition.name
		);
		await page.getByText('Add ' + newObjectDefinition.name).click();
		await page.getByLabel('textField').fill('testText');
		await page.getByRole('button', {name: 'Save'}).click();
		await waitForAlert(
			page,
			'Success:Your request completed successfully.'
		);

		const exportFilePath = await companyExportImportPage.export(
			newObjectDefinition.name + ' 1 Items'
		);

		await applicationsMenuPage.goToObjectDefinition(
			newObjectDefinition.name
		);
		await page.getByRole('button', {name: 'Actions'}).click();
		await page.getByRole('menuitem', {name: 'Delete'}).click();
		await page.getByRole('button', {name: 'Delete'}).click();
		await waitForAlert(
			page,
			'Success:Your request completed successfully.'
		);

		await performLogout(page);

		await performLogin(page, 'test');

		await companyExportImportPage.import(exportFilePath, false, true);

		await applicationsMenuPage.goToObjectDefinition(
			newObjectDefinition.name
		);
		await expect(page.getByRole('cell', {name: 'Test Test'})).toBeVisible();

		await viewObjectDefinitionsPage.goto();

		await viewObjectDefinitionsPage.clickObjectDefinitionActionButton(
			newObjectDefinition.label['en_US']
		);

		await viewObjectDefinitionsPage.deleteObjectDefinitionOption.click();
		await viewObjectDefinitionsPage.page
			.getByPlaceholder('Confirm Object Definition Name')
			.fill(newObjectDefinition.name);
		await viewObjectDefinitionsPage.page
			.getByRole('button', {name: 'Delete'})
			.click();
	}
);
