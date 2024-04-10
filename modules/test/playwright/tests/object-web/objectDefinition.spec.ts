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

test.describe('Manage object definitions through Model Builder', () => {
	test('can create an object definition inside a folder and see if it renders correctly in the model builder', async ({
		addObjectDefinitionModalPage,
		apiHelpers,
		modelBuilderPage,
		objectDefinitionsPage,
		page,
	}) => {
		await objectDefinitionsPage.goto();

		const objectDefinitionLabel = 'ObjectDefinitionLabel' + getRandomInt();

		const objectDefinition =
			await addObjectDefinitionModalPage.createObjectDefinition(
				objectDefinitionsPage.createObjectDefinitionButton,
				objectDefinitionLabel
			);

		expect(page.getByText(objectDefinitionLabel)).toBeVisible();

		await objectDefinitionsPage.viewInModelBuilder();

		await expect(
			modelBuilderPage.objectDefinitionNodes.filter({
				hasText: objectDefinition.name,
			})
		).toBeVisible();

		await expect(
			modelBuilderPage.leftSidebarItems.filter({
				hasText: objectDefinition.name,
			})
		).toBeVisible();

		// Clean up

		await apiHelpers.objectAdmin.deleteObjectDefinition(
			objectDefinition.id
		);
	});

	test('can create an object definition by model builder', async ({
		addObjectDefinitionModalPage,
		apiHelpers,
		modelBuilderPage,
	}) => {
		await modelBuilderPage.goto();

		const objectDefinitionLabel = 'ObjectDefinitionLabel' + getRandomInt();

		const objectDefinition =
			await addObjectDefinitionModalPage.createObjectDefinition(
				modelBuilderPage.createNewObjectDefinitionButton,
				objectDefinitionLabel
			);

		await expect(
			modelBuilderPage.objectDefinitionNodes.filter({
				hasText: objectDefinition.name,
			})
		).toBeVisible();

		await expect(
			modelBuilderPage.leftSidebarItems.filter({
				hasText: objectDefinition.name,
			})
		).toBeVisible();

		// Clean up

		await apiHelpers.objectAdmin.deleteObjectDefinition(
			objectDefinition.id
		);
	});
});
