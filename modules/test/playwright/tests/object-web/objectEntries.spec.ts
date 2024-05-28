/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {collectionsPagesTest} from '../../fixtures/CollectionsPageTest';
import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {objectPagesTest} from '../../fixtures/objectPagesTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {getRandomInt} from '../../utils/getRandomInt';
import getRandomString from '../../utils/getRandomString';
import getCollectionDefinition from '../layout-content-page-editor-web/utils/getCollectionDefinition';
import getFragmentDefinition from '../layout-content-page-editor-web/utils/getFragmentDefinition';
import getPageDefinition from '../layout-content-page-editor-web/utils/getPageDefinition';

export const test = mergeTests(
	apiHelpersTest,
	collectionsPagesTest,
	isolatedSiteTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	loginTest(),
	objectPagesTest,
	pageEditorPagesTest
);

test.describe('Manage object entries through page templates', () => {
	test('can view all entries related to an object in the relationship field', async ({
		apiHelpers,
		page,
		viewObjectEntriesPage,
	}) => {
		const objectDefinition1 =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFolderExternalReferenceCode: 'default',
				status: {code: 0},
			});

		const objectDefinition2 =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFolderExternalReferenceCode: 'default',
				status: {code: 0},
			});

		const objectRelationshipLabel =
			'objectRelationshipLabel' + getRandomInt();
		const objectRelationshipName =
			'objectRelationshipName' + Math.floor(Math.random() * 99);

		const objectRelationshipData: Partial<ObjectRelationship> = {
			label: {
				en_US: objectRelationshipLabel,
			},
			name: objectRelationshipName,
			objectDefinitionExternalReferenceCode1:
				objectDefinition1.externalReferenceCode,
			objectDefinitionExternalReferenceCode2:
				objectDefinition2.externalReferenceCode,
			objectDefinitionId1: objectDefinition1.id,
			objectDefinitionId2: objectDefinition2.id,
			objectDefinitionName2: objectDefinition2.name,
			type: 'oneToMany' as ObjectRelationshipType,
		};

		await apiHelpers.objectAdmin.postObjectRelationship(
			objectRelationshipData
		);

		const applicationName =
			'c/' + objectDefinition1.name.toLowerCase() + 's';

		const textObjectEntry = {
			textField: 'entry',
		};

		const objectEntries = [];

		for (let i = 0; i <= 15; i++) {
			const objectEntry = await apiHelpers.objectEntry.postObjectEntry(
				textObjectEntry,
				applicationName
			);

			objectEntries.push(objectEntry.id);
		}

		await viewObjectEntriesPage.goto(objectDefinition2.id);
		await viewObjectEntriesPage.clickAddObjectEntry();
		await page.getByPlaceholder('Search', {exact: true}).click();

		objectEntries.forEach((objectEntryId) => {
			expect(
				page.getByRole('menuitem', {name: objectEntryId})
			).toBeVisible();
		});

		// Clean up

		await apiHelpers.objectAdmin.deleteObjectDefinition(
			objectDefinition1.id
		);

		await apiHelpers.objectAdmin.deleteObjectDefinition(
			objectDefinition2.id
		);
	});

	test('filters object definition entries of boolean type on an collection display page', async ({
		apiHelpers,
		page,
		pageEditorPage,
		site,
	}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postObjectDefinition({
				active: true,
				externalReferenceCode: 'customObjectERC',
				label: {
					en_US: 'customobject',
				},
				name: 'CustomObject',
				objectFields: [
					{
						DBType: 'Boolean',
						businessType: 'Boolean',
						externalReferenceCode: 'customBoolean',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {en_US: 'customBoolean'},
						listTypeDefinitionId: 0,
						name: 'customBoolean',
						required: false,
						system: false,
						type: 'Boolean',
					},
				],
				pluralLabel: {
					en_US: 'customobjects',
				},
				portlet: true,
				scope: 'company',
				status: {
					code: 0,
				},
			});

		const trueObjectEntry = {
			customBoolean: true,
		};

		const falseObjectEntry = {
			customBoolean: false,
		};

		await apiHelpers.objectEntry.postObjectEntry(
			trueObjectEntry,
			'c/customobjects'
		);

		await apiHelpers.objectEntry.postObjectEntry(
			falseObjectEntry,
			'c/customobjects'
		);

		const collectionDefinition = getCollectionDefinition({
			id: getRandomString(),
		});

		const headingDefinition = getFragmentDefinition(
			getRandomString(),
			'BASIC_COMPONENT-heading'
		);

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([
				collectionDefinition,
				headingDefinition,
			]),
			siteId: site.id,
			title: 'Collection Display filtered by boolean type',
		});

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		await pageEditorPage.selectFragment(collectionDefinition.id);

		await pageEditorPage.goToConfigurationTab('General');

		await pageEditorPage.chooseCollectionDisplayOption(
			'Collection Providers',
			'customobjects customobject'
		);

		await pageEditorPage.chooseCollectionFilterOption(
			'customBoolean',
			'true'
		);

		await pageEditorPage.goToSidebarTab('Browser');

		await pageEditorPage.selectFragment(collectionDefinition.id);

		await pageEditorPage.selectFragment(headingDefinition.id);

		await page
			.getByLabel('Select Heading')
			.dragTo(page.getByLabel('Collection Item', {exact: true}));

		await page.getByLabel('Select element-text').click();

		await page
			.getByLabel('Field')
			.selectOption('ObjectField_customBoolean');

		await pageEditorPage.publishPage();

		await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`);

		await expect(page.getByText('true')).toBeVisible();

		await expect(page.getByText('false')).toBeHidden();

		// Clean up

		await apiHelpers.jsonWebServicesLayout.deleteLayout(layout.id);

		await apiHelpers.objectAdmin.deleteObjectDefinition(
			objectDefinition.id
		);
	});
});

test.describe('Manage object entries through View Object Entries', () => {
	test('can add an entry with all object fields', async ({
		apiHelpers,
		page,
		viewObjectEntriesPage,
	}) => {
		const picklist = await apiHelpers.post(
			'/o/headless-admin-list-type/v1.0/list-type-definitions',
			{
				data: {
					externalReferenceCode: 'picklistERC',
					name: 'picklist',
					name_i18n: {
						en_US: 'picklist',
					},
				},
			}
		);

		await apiHelpers.post(
			`/o/headless-admin-list-type/v1.0/list-type-definitions/${picklist.id}/list-type-entries`,
			{
				data: {
					key: 'item1',
					name: 'item1',
					name_i18n: {
						en_US: 'item1',
					},
				},
			}
		);

		const objectDefinition =
			await apiHelpers.objectAdmin.postObjectDefinition({
				active: true,
				externalReferenceCode: 'NewObjectERC',
				label: {
					en_US: 'NewObject',
				},
				name: 'NewObject',
				objectFields: [
					{
						DBType: 'String',
						businessType: 'Text',
						externalReferenceCode: 'text',
						indexed: true,
						indexedAsKeyword: true,
						label: {
							en_US: 'text',
						},
						name: 'text',
						required: false,
						system: false,
						type: 'String',
					},
					{
						DBType: 'Long',
						businessType: 'LongInteger',
						externalReferenceCode: 'longInteger',
						id: 33573,
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {
							en_US: 'longInteger',
						},
						listTypeDefinitionId: 0,
						localized: false,
						name: 'longInteger',
						required: false,
						system: false,
						type: 'Long',
					},
					{
						DBType: 'Date',
						businessType: 'Date',
						externalReferenceCode: 'date',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {
							en_US: 'date',
						},
						listTypeDefinitionId: 0,
						localized: false,
						name: 'date',
						required: false,
						system: false,
						type: 'Date',
					},
					{
						DBType: 'Integer',
						businessType: 'Integer',
						externalReferenceCode: 'integer',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {
							en_US: 'integer',
						},
						listTypeDefinitionId: 0,
						localized: false,
						name: 'integer',
						required: false,
						system: false,
						type: 'Integer',
					},
					{
						DBType: 'Double',
						businessType: 'Decimal',
						externalReferenceCode: 'decimal',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {
							en_US: 'decimal',
						},
						listTypeDefinitionId: 0,
						localized: false,
						name: 'decimal',
						required: false,
						system: false,
						type: 'Double',
					},
					{
						DBType: 'Clob',
						businessType: 'RichText',
						externalReferenceCode: 'richText',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: 'en_US',
						label: {
							en_US: 'richText',
						},
						listTypeDefinitionId: 0,
						localized: false,
						name: 'richText',
						required: false,
						system: false,
						type: 'Clob',
					},
					{
						DBType: 'Boolean',
						businessType: 'Boolean',
						externalReferenceCode: 'boolean',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {en_US: 'boolean'},
						listTypeDefinitionId: 0,
						name: 'boolean',
						required: false,
						system: false,
						type: 'Boolean',
					},
					{
						DBType: 'Clob',
						businessType: 'LongText',
						externalReferenceCode: 'longText',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {en_US: 'longText'},
						listTypeDefinitionId: 0,
						name: 'longText',
						required: false,
						system: false,
						type: 'Clob',
					},
					{
						DBType: 'BigDecimal',
						businessType: 'PrecisionDecimal',
						externalReferenceCode: 'precisionDecimal',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {en_US: 'precisionDecimal'},
						listTypeDefinitionId: 0,
						name: 'precisionDecimal',
						required: false,
						system: false,
						type: 'BigDecimal',
					},
					{
						DBType: 'String',
						businessType: 'Picklist',
						externalReferenceCode: 'picklist',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: 'en_US',
						label: {
							en_US: 'picklist',
						},
						listTypeDefinitionExternalReferenceCode: 'picklistERC',
						name: 'picklist',
						required: false,
						state: false,
					},
					{
						DBType: 'Long',
						businessType: 'Attachment',
						indexed: true,
						indexedAsKeyword: false,
						label: {
							en_US: 'attachment',
						},
						name: 'attachment',
						objectFieldSettings: [
							{
								name: 'acceptedFileExtensions',
								value: 'jpeg, jpg, pdf, png',
							},
							{
								name: 'fileSource',
								value: 'documentsAndMedia',
							},
							{
								name: 'maximumFileSize',
								value: '100',
							},
						],
						required: false,
						type: 'Long',
					},
				],
				panelCategoryKey: 'control_panel.object',
				pluralLabel: {
					en_US: 'NewObject',
				},
				portlet: true,
				scope: 'company',
				status: {
					code: 0,
				},
			});

		await viewObjectEntriesPage.goto(objectDefinition.id);

		await viewObjectEntriesPage.clickAddObjectEntry();

		await viewObjectEntriesPage.selectFileFromDocumentsAndMedia();

		await page.getByLabel('boolean').check();

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldName: 'date',
			objectFieldValue: '05/14/2024',
		});

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldName: 'decimal',
			objectFieldValue: '12.34',
		});

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldName: 'integer',
			objectFieldValue: '1234',
		});

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldName: 'longInteger',
			objectFieldValue: '1122334455',
		});

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldName: 'longText',
			objectFieldValue: 'Text written on long text',
		});

		await viewObjectEntriesPage.selectDropdownItem('picklist', 'item1');

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldName: 'precisionDecimal',
			objectFieldValue: '1.5',
		});

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldBusinessType: 'RichText',
			objectFieldValue: 'Text written on rich text',
		});

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldName: 'text',
			objectFieldValue: 'Text written on simple text',
		});

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await expect(viewObjectEntriesPage.successMessage).toBeVisible();

		await viewObjectEntriesPage.backButton.click();

		const objectEntries = [
			'Yes',
			'12.34',
			'1234',
			'1122334455',
			'1.5',
			'Text written on long text',
			'Text written on simple text',
			'Text written on rich text',
			'item1',
			'astronaut.png',
		];

		for (let i = 0; i < objectEntries.length; i++) {
			const entry = objectEntries[i];

			await expect(page.getByText(entry)).toBeVisible();
		}

		// Clean up

		await apiHelpers.objectAdmin.deleteObjectDefinition(
			objectDefinition.id
		);

		await apiHelpers.listTypeAdmin.deleteListTypeDefinition(picklist.id);
	});
});
