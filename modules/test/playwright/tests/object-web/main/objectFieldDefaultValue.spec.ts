/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import {waitForAlert} from '../../../utils/waitForAlert';
import {generateObjectFields} from './utils/generateObjectFields';
import {postListTypeDefinitionListTypeEntries} from './utils/postListTypeDefinitionListTypeEntries';

const test = mergeTests(
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	objectPagesTest
);

test(
	'LPD-78504 Can verify updating default value configuration for picklist field only affects new entries',
	{tag: '@LPD-78504'},
	async ({apiHelpers, objectFieldsPage, page, viewObjectEntriesPage}) => {
		const {listTypeDefinition, listTypeEntries} =
			await postListTypeDefinitionListTypeEntries({
				apiHelpers,
				listTypeEntriesLength: 2,
			});

		const objectFields = generateObjectFields({
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			objectFieldBusinessTypes: ['Picklist'],
		});

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const firstItemName = listTypeEntries[0].name;
		const secondItemName = listTypeEntries[1].name;

		await objectFieldsPage.goto(objectDefinition.label['en_US']);

		await objectFieldsPage.setDefaultValue({
			defaultValue: firstItemName,
			objectFieldBusinessType: 'Picklist',
			objectFieldName: objectFields[0].label['en_US'],
		});

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);

		await viewObjectEntriesPage.backButton.click();

		await expect(
			page.locator('td').getByText(firstItemName, {exact: true})
		).toBeVisible();

		await objectFieldsPage.goto(objectDefinition.label['en_US']);

		await objectFieldsPage.setDefaultValue({
			defaultValue: secondItemName,
			objectFieldBusinessType: 'Picklist',
			objectFieldName: objectFields[0].label['en_US'],
		});

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);

		await viewObjectEntriesPage.backButton.click();

		await expect(
			page.locator('td').getByText(firstItemName, {exact: true})
		).toBeVisible();

		await expect(
			page.locator('td').getByText(secondItemName, {exact: true})
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can set picklist default value via expression builder',
	{tag: '@LPD-78504'},
	async ({apiHelpers, objectFieldsPage, page, viewObjectEntriesPage}) => {
		const {listTypeDefinition, listTypeEntries} =
			await postListTypeDefinitionListTypeEntries({
				apiHelpers,
				listTypeEntriesLength: 5,
			});

		const objectFields = generateObjectFields({
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			objectFieldBusinessTypes: ['Picklist', 'Text'],
		});

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const picklistFieldName = objectFields[0].label['en_US'];
		const textFieldName = objectFields[1].name;

		await objectFieldsPage.goto(objectDefinition.label['en_US']);

		await objectFieldsPage.openObjectField(picklistFieldName);

		await objectFieldsPage.advancedTab.click();

		await objectFieldsPage.useDefaultValueToggle.check({timeout: 1000});

		await objectFieldsPage.iframeLocator
			.getByRole('button', {name: 'Expression Builder'})
			.click();

		await objectFieldsPage.iframeLocator
			.getByPlaceholder('Create an expression.')
			.fill(textFieldName);

		await objectFieldsPage.editFieldSaveButton.click();

		await waitForAlert(
			page,
			'The object field was updated successfully'
		);

		const firstItemKey = listTypeEntries[0].key;
		const firstItemName = listTypeEntries[0].name;
		const applicationName =
			'c/' + objectDefinition.name.toLowerCase() + 's';

		await apiHelpers.objectEntry.postObjectEntry(
			{[textFieldName]: firstItemKey},
			applicationName
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(
			page.locator('td').getByText(firstItemName, {exact: true}).first()
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can set picklist default value via input as value',
	{tag: '@LPD-78504'},
	async ({apiHelpers, objectFieldsPage, page, viewObjectEntriesPage}) => {
		const {listTypeDefinition, listTypeEntries} =
			await postListTypeDefinitionListTypeEntries({
				apiHelpers,
				listTypeEntriesLength: 2,
			});

		const objectFields = generateObjectFields({
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			objectFieldBusinessTypes: ['Picklist'],
		});

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const defaultItemName = listTypeEntries[0].name;

		await objectFieldsPage.goto(objectDefinition.label['en_US']);

		await objectFieldsPage.setDefaultValue({
			defaultValue: defaultItemName,
			objectFieldBusinessType: 'Picklist',
			objectFieldName: objectFields[0].label['en_US'],
		});

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);

		await viewObjectEntriesPage.backButton.click();

		await expect(
			page.locator('td').getByText(defaultItemName, {exact: true})
		).toBeVisible();
	}
);
