/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {loginTest} from '../../fixtures/loginTest';
import {objectPagesTest} from '../../fixtures/objectPagesTest';
import {
	ObjectAdminV10ObjectDefinitionService,
	ObjectAdminV10ObjectFolderService,
	ObjectAdminV10ObjectRelationshipService,
} from '../../utils/headless';
import {getRandomInt} from '../../utils/util';

export const test = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	loginTest,
	objectPagesTest
);

async function postRandomObjectDefinition(
	objectFolderExternalReferenceCode: string
) {
	const objectDefinitionExternalReferenceCode1 =
		'ObjectDefinition' + getRandomInt();

	const objectDefinition1 =
		await ObjectAdminV10ObjectDefinitionService.objectAdminV10PostObjectDefinition(
			{
				externalReferenceCode: objectDefinitionExternalReferenceCode1,
				label: {
					en_US: objectDefinitionExternalReferenceCode1,
				},
				name: objectDefinitionExternalReferenceCode1,
				objectFolderExternalReferenceCode,
				pluralLabel: {
					en_US: objectDefinitionExternalReferenceCode1,
				},
				scope: 'company',
			}
		);

	return objectDefinition1;
}

test('can create relationship by dragging node handles', async ({
	apiHelpers,
	modelBuilderPage,
	objectDefinitionsPage,
}) => {
	await apiHelpers.featureFlag.updateFeatureFlag('LPS-148856', true);

	const objectFolderExternalReferenceCode = 'objectFolder' + getRandomInt();

	const objectFolder =
		await ObjectAdminV10ObjectFolderService.objectAdminV10PostObjectFolder({
			externalReferenceCode: objectFolderExternalReferenceCode,
			label: {
				en_US: objectFolderExternalReferenceCode,
			},
			name: objectFolderExternalReferenceCode,
		});

	const objectDefinition1 = await postRandomObjectDefinition(
		objectFolderExternalReferenceCode
	);

	const objectDefinition2 = await postRandomObjectDefinition(
		objectFolderExternalReferenceCode
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
		`${objectDefinition1.id}`,
		`${objectDefinition2.id}`,
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

	await ObjectAdminV10ObjectRelationshipService.objectAdminV10DeleteObjectRelationship(
		objectRelationship.id
	);

	await ObjectAdminV10ObjectDefinitionService.objectAdminV10DeleteObjectDefinition(
		`${objectDefinition1.id}`
	);
	await ObjectAdminV10ObjectDefinitionService.objectAdminV10DeleteObjectDefinition(
		`${objectDefinition2.id}`
	);

	await ObjectAdminV10ObjectFolderService.objectAdminV10DeleteObjectFolder(
		`${objectFolder.id}`
	);
});
