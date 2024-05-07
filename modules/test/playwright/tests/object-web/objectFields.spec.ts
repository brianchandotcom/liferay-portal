/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import {objectPagesTest} from '../../fixtures/objectPagesTest';
import {getRandomInt} from '../../utils/getRandomInt';

export const test = mergeTests(apiHelpersTest, loginTest(), objectPagesTest);

test.describe('Manage object fields through Model Builder', () => {
	test('can add picklist object field to object definition node', async ({
		apiHelpers,
		modelBuilderPage,
		page,
		viewObjectDefinitionsPage,
	}) => {
		await page.goto('/');

		const ListTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFolderExternalReferenceCode: 'default',
				status: {code: 0},
			});

		await viewObjectDefinitionsPage.goto();

		await viewObjectDefinitionsPage.openObjectFolder('default');

		await viewObjectDefinitionsPage.viewInModelBuilder();

		const objectFieldLabel = 'objectFieldLabel' + getRandomInt();

		await modelBuilderPage.createObjectField({
			listTypeDefinitionName: ListTypeDefinition.name,
			mandatory: false,
			objectDefinitionName: objectDefinition.name,
			objectFieldBusinessType: 'Picklist',
			objectFieldLabel,
		});

		await expect(
			modelBuilderPage.objectDefinitionNodes
				.filter({hasText: objectDefinition.label['en_US']})
				.getByText(objectFieldLabel)
		).toBeVisible();

		// Clean up

		await apiHelpers.objectAdmin.deleteObjectDefinition(
			objectDefinition.id
		);

		await apiHelpers.listTypeAdmin.deleteListTypeDefinition(
			ListTypeDefinition.id
		);
	});

	test('can delete object field', async ({apiHelpers, modelBuilderPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		await apiHelpers.objectAdmin.postObjectFieldByExternalReferenceCode(
			objectDefinition.externalReferenceCode,
			{
				DBType: 'Integer',
				label: {
					en_US: 'intField',
				},

				listTypeDefinitionId: 0,
				localized: false,
				name: 'intField',
				objectFieldSettings: [],
				readOnly: 'false',
				readOnlyConditionExpression: '',
				required: false,
				state: false,
				system: false,
			}
		);

		await modelBuilderPage.goto({objectFolderName: 'Default'});

		await modelBuilderPage.leftSidebarItems
			.filter({hasText: objectDefinition.name})
			.click();

		await modelBuilderPage.clickObjectDefinitionShowAllFieldsButton(
			objectDefinition.name
		);

		await modelBuilderPage.objectDefinitionNodes
			.filter({hasText: objectDefinition.name})
			.getByText('integer', {exact: true})
			.click();

		await modelBuilderPage.deleteTrashButton.click();

		await modelBuilderPage.modalDeleteObjectDefinitionConfirmationButton.click();

		await expect(
			modelBuilderPage.objectDefinitionNodes
				.filter({hasText: objectDefinition.name})
				.getByText('intField')
		).toBeHidden();

		// Clean up

		await apiHelpers.objectAdmin.deleteObjectDefinition(
			objectDefinition.id
		);
	});
});
