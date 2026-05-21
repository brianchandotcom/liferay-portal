/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ObjectFieldAPI,
	ObjectRelationshipAPI,
} from '@liferay/object-admin-rest-client-js';
import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {globalMenuPagesTest} from '../../../fixtures/globalMenuPagesTest';
import {loginTest} from '../../../fixtures/loginTest';
import {DataApiHelpers} from '../../../helpers/ApiHelpers';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import {getRandomInt} from '../../../utils/getRandomInt';
import {normalizeRestPath} from '../../../utils/normalizeRestPath';
import {companyExportImportPageTest} from './fixtures/companyExportImportPagesTest';
import {exportImportPagesTest} from './fixtures/exportImportPagesTest';

export const test = mergeTests(
	dataApiHelpersTest,
	exportImportPagesTest,
	companyExportImportPageTest,
	globalMenuPagesTest,
	loginTest()
);

async function setupImportReportScenario(apiHelpers: DataApiHelpers) {
	const objectDefinitionA = await _createObjectDefinition(
		apiHelpers,
		'A',
		'site'
	);
	const objectDefinitionB = await _createObjectDefinition(
		apiHelpers,
		'B',
		'site'
	);
	const objectDefinitionC = await _createObjectDefinition(
		apiHelpers,
		'C',
		'company'
	);
	const objectDefinitionD = await _createObjectDefinition(
		apiHelpers,
		'D',
		'site'
	);
	const objectDefinitionZ = await _createObjectDefinition(
		apiHelpers,
		'Z',
		'site'
	);

	const relationshipName = 'rel' + getRandomInt();

	await _createOneToManyRelationship(
		apiHelpers,
		objectDefinitionC,
		objectDefinitionD,
		relationshipName
	);

	await apiHelpers.objectEntry.postObjectEntry(
		{},
		`${normalizeRestPath(objectDefinitionA.restContextPath)}/scopes/Guest`
	);

	await apiHelpers.objectEntry.postObjectEntry(
		{},
		`${normalizeRestPath(objectDefinitionB.restContextPath)}/scopes/Guest`
	);

	await apiHelpers.objectEntry.postObjectEntry(
		{},
		`${normalizeRestPath(objectDefinitionZ.restContextPath)}/scopes/Guest`
	);

	const objectEntryC = await apiHelpers.objectEntry.postObjectEntry(
		{},
		normalizeRestPath(objectDefinitionC.restContextPath)
	);

	await apiHelpers.objectEntry.postObjectEntry(
		{
			externalReferenceCode: `ERC-D-${getRandomInt()}`,
			[`r_${relationshipName}_c_${objectDefinitionC.name[0].toLowerCase() + objectDefinitionC.name.substring(1)}Id`]:
				objectEntryC.id,
		},
		`${normalizeRestPath(objectDefinitionD.restContextPath)}/scopes/Guest`
	);

	return {
		objectDefinitionA,
		objectDefinitionB,
		objectDefinitionC,
		objectDefinitionD,
		objectDefinitionZ,
		objectEntryC,
	};
}

async function _createObjectDefinition(
	apiHelpers: DataApiHelpers,
	prefix: string,
	scope: 'site' | 'company'
) {
	const objectDefinition =
		await apiHelpers.objectAdmin.postRandomObjectDefinition({
			objectDefinitionExternalReferenceCode:
				`${prefix}ObjectDefinition` + getRandomInt(),
			scope: scope === 'site' ? 'site' : undefined,
			status: {code: 0},
		});

	apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

	return objectDefinition;
}

async function _createOneToManyRelationship(
	apiHelpers: DataApiHelpers,
	objectDefinition1: any,
	objectDefinition2: any,
	name: string
) {
	const objectRelationshipAPIClient = await apiHelpers.buildRestClient(
		ObjectRelationshipAPI
	);

	await objectRelationshipAPIClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
		objectDefinition1.externalReferenceCode,
		{
			deletionType: 'disassociate',
			label: {en_US: 'Relationship'},
			name,
			objectDefinitionExternalReferenceCode1:
				objectDefinition1.externalReferenceCode,
			objectDefinitionExternalReferenceCode2:
				objectDefinition2.externalReferenceCode,
			objectDefinitionId1: objectDefinition1.id,
			objectDefinitionId2: objectDefinition2.id,
			objectDefinitionName2: objectDefinition2.name,
			type: 'oneToMany',
		}
	);
}

test('Can see error report and details', async ({
	apiHelpers,
	companyExportImportPage,
	exportImportPage,
	globalMenuPage,
}) => {
	const objectDefinition =
		await apiHelpers.objectAdmin.postRandomObjectDefinition({
			status: {code: 0},
		});

	apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

	const objectEntry = await apiHelpers.objectEntry.postObjectEntry(
		{externalReferenceCode: '', name: objectDefinition.name},
		normalizeRestPath(`${objectDefinition.restContextPath}`)
	);

	await globalMenuPage.goToApplications('Export');

	const exportFilePath = await exportImportPage.export({
		portletLabels: [`${objectDefinition.name} 1 Items`],
	});

	const objectFieldAPIClient =
		await apiHelpers.buildRestClient(ObjectFieldAPI);

	await objectFieldAPIClient.postObjectDefinitionObjectField(
		objectDefinition.id,
		{
			DBType: 'String',
			businessType: 'Text',
			label: {en_US: 'mandatoryField'},
			name: 'mandatoryField',
			required: true,
		}
	);

	await companyExportImportPage.import({
		filePath: exportFilePath,
		includePermissions: true,
		taskStatus: 'completedWithErrors',
	});

	const exportName = exportFilePath.slice(
		exportFilePath.lastIndexOf('/') + 1
	);

	await clickAndExpectToBeVisible({
		target: exportImportPage.clearMenuItem,
		trigger: exportImportPage.taskActionsMenu(exportName),
	});

	await expect(exportImportPage.viewReportEntriesMenuItem).toBeVisible();

	await expect(exportImportPage.exportReportEntriesMenuItem).toBeVisible();

	await exportImportPage.goToImportDetails(exportName);

	await expect(
		exportImportPage.page.getByRole('cell', {
			name: objectEntry.externalReferenceCode,
		})
	).toBeVisible();

	await exportImportPage.goToImportReportEntryDetails(
		objectEntry.externalReferenceCode
	);

	await expect(
		exportImportPage.page.getByText(
			'No value was provided for required object field'
		)
	).toBeVisible();

	await expect(exportImportPage.page.getByText('ScopeCompany')).toBeVisible();

	await expect(
		exportImportPage.page.getByText('SiteLiferay DXP')
	).not.toBeVisible();

	await expect(
		exportImportPage.page.getByText(objectEntry.externalReferenceCode)
	).toBeVisible();
});

test(
	'Can filter, search and sort errors report entries',
	{tag: '@LPD-68461'},
	async ({apiHelpers, exportImportPage}) => {
		const {
			objectDefinitionA,
			objectDefinitionB,
			objectDefinitionC,
			objectDefinitionD,
			objectDefinitionZ,
			objectEntryC,
		} = await setupImportReportScenario(apiHelpers);

		await exportImportPage.goToExport();

		const exportFilePath = await exportImportPage.export({
			portletLabels: [
				`${objectDefinitionA.name} 1 Items`,
				`${objectDefinitionB.name} 1 Items`,
				`${objectDefinitionZ.name} 1 Items`,
				`${objectDefinitionD.name} 1 Items`,
			],
		});

		const objectFieldAPIClient =
			await apiHelpers.buildRestClient(ObjectFieldAPI);

		for (const objectDefinition of [
			objectDefinitionA,
			objectDefinitionB,
			objectDefinitionZ,
		]) {
			await objectFieldAPIClient.postObjectDefinitionObjectField(
				objectDefinition.id,
				{
					DBType: 'String',
					businessType: 'Text',
					label: {en_US: 'mandatoryField'},
					name: 'mandatoryField',
					required: true,
				}
			);
		}

		await apiHelpers.objectEntry.deleteObjectEntry(
			normalizeRestPath(objectDefinitionC.restContextPath),
			String(objectEntryC.id)
		);

		const newSite = await apiHelpers.headlessAdminSite.postSite({
			name: 'TestSite' + getRandomInt(),
		});

		await exportImportPage.goToImport(newSite.friendlyUrlPath);

		await exportImportPage.import({
			filePath: exportFilePath,
			taskStatus: 'completedWithErrors',
		});

		const exportName = exportFilePath.slice(
			exportFilePath.lastIndexOf('/') + 1
		);

		await exportImportPage.goToImportDetails(exportName);

		// Sort by Entity Type

		await exportImportPage.sortReportBy('Entity Type');
		let values =
			await exportImportPage.getReportColumnValues('Entity Type');
		expect(values).toEqual([...values].sort((a, b) => a.localeCompare(b)));

		await exportImportPage.sortReportBy('Entity Type');
		values = await exportImportPage.getReportColumnValues('Entity Type');
		expect(values).toEqual([...values].sort((a, b) => b.localeCompare(a)));

		// Sort by External Reference Code

		await exportImportPage.sortReportBy('External Reference Code');
		values = await exportImportPage.getReportColumnValues(
			'External Reference Code'
		);
		expect(values).toEqual([...values].sort((a, b) => a.localeCompare(b)));

		await exportImportPage.sortReportBy('External Reference Code');
		values = await exportImportPage.getReportColumnValues(
			'External Reference Code'
		);
		expect(values).toEqual([...values].sort((a, b) => b.localeCompare(a)));

		// Search by Entity Type name

		await exportImportPage.searchReportEntries(objectDefinitionA.name);
		values = await exportImportPage.getReportColumnValues('Entity Type');
		expect(values).toEqual([objectDefinitionA.name]);

		await exportImportPage.clearReportSearch();

		// Search by External Reference Code

		const ercValues = await exportImportPage.getReportColumnValues(
			'External Reference Code'
		);
		const searchERC = ercValues[0];

		await exportImportPage.searchReportEntries(searchERC);
		values = await exportImportPage.getReportColumnValues(
			'External Reference Code'
		);
		expect(values).toEqual([searchERC]);

		await exportImportPage.clearReportSearch();

		// Filter by Entity Type

		await exportImportPage.filterReportBy(
			'Entity Type',
			objectDefinitionA.name
		);
		values = await exportImportPage.getReportColumnValues('Entity Type');
		expect(values).toEqual([objectDefinitionA.name]);

		await exportImportPage.removeReportFilter();

		// Filter by External Reference Code

		await exportImportPage.filterReportBy(
			'External Reference Code',
			searchERC
		);
		values = await exportImportPage.getReportColumnValues(
			'External Reference Code'
		);
		expect(values).toEqual([searchERC]);

		await exportImportPage.removeReportFilter();

		// Filter by Type

		await exportImportPage.filterReportBy('Type', 'Empty');
		values = await exportImportPage.getReportColumnValues('Type');
		expect(values).toEqual(['Empty']);

		await exportImportPage.excludeReportFilter();
		values = await exportImportPage.getReportColumnValues('Type');
		expect(values).toEqual(['Error', 'Error', 'Error']);

		await exportImportPage.removeReportFilter();
	}
);

test('Report entries actions are not visible for a successful import', async ({
	apiHelpers,
	companyExportImportPage,
	exportImportPage,
	globalMenuPage,
}) => {
	const objectDefinition =
		await apiHelpers.objectAdmin.postRandomObjectDefinition({
			status: {code: 0},
		});

	apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

	await apiHelpers.objectEntry.postObjectEntry(
		{externalReferenceCode: '', name: objectDefinition.name},
		normalizeRestPath(`${objectDefinition.restContextPath}`)
	);

	await globalMenuPage.goToApplications('Export');

	const exportFilePath = await exportImportPage.export({
		portletLabels: [`${objectDefinition.name} 1 Items`],
	});

	await companyExportImportPage.import({
		filePath: exportFilePath,
		includePermissions: true,
	});

	const exportName = exportFilePath.slice(
		exportFilePath.lastIndexOf('/') + 1
	);

	await clickAndExpectToBeVisible({
		target: exportImportPage.clearMenuItem,
		trigger: exportImportPage.taskActionsMenu(exportName),
	});

	await expect(exportImportPage.viewReportEntriesMenuItem).not.toBeVisible();

	await expect(
		exportImportPage.exportReportEntriesMenuItem
	).not.toBeVisible();
});
