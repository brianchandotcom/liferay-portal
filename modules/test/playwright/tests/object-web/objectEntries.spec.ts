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
		await viewObjectEntriesPage.addObjectEntryButton.click();
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
