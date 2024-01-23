/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {objectPagesTest} from '../../fixtures/objectPagesTest';
import {getRandomInt} from '../../utils/util';

export const test = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	featureFlagsTest({
		'LPS-148856': true,
	}),
	loginTest,
	objectPagesTest
);

test('can create relationship by dragging node handles', async ({
	apiHelpers,
	modelBuilderPage,
	objectDefinitionsPage,
}) => {
	const objectFolder = await apiHelpers.objectAdmin.postRandomObjectFolder();

	const objectDefinition1 =
		await apiHelpers.objectAdmin.postRandomObjectDefinition(
			objectFolder.externalReferenceCode
		);
	const objectDefinition2 =
		await apiHelpers.objectAdmin.postRandomObjectDefinition(
			objectFolder.externalReferenceCode
		);

	await objectDefinitionsPage.goto();

	await objectDefinitionsPage.openObjectFolder(
		objectFolder.externalReferenceCode
	);

	await objectDefinitionsPage.viewInModelBuilder();

	await modelBuilderPage.clickToggleSidebarsButton();

	await modelBuilderPage.clickFitViewButton();

	const objectRelationshipLabel = 'objectRelationship' + getRandomInt();

	const objectRelationship = await modelBuilderPage.createObjectRelationship(
		objectDefinition1.id,
		objectDefinition2.id,
		objectRelationshipLabel,
		'One to Many'
	);

	await expect(
		modelBuilderPage.objectRelationshipEdges.filter({
			hasText: objectRelationshipLabel,
		})
	).toBeVisible();

	await modelBuilderPage.clickObjectDefinitionShowAllFieldsButton(
		objectDefinition2.name
	);

	await modelBuilderPage.clickFitViewButton();

	await expect(
		modelBuilderPage.objectDefinitionNodes
			.filter({hasText: objectDefinition2.name})
			.getByText(objectRelationshipLabel)
	).toBeVisible();

	// Clean up

	await apiHelpers.objectAdmin.deleteObjectRelationship(
		objectRelationship.id
	);

	await apiHelpers.objectAdmin.deleteObjectDefinition(objectDefinition1.id);
	await apiHelpers.objectAdmin.deleteObjectDefinition(objectDefinition2.id);

	await apiHelpers.objectAdmin.deleteObjectFolder(objectFolder.id);
});
