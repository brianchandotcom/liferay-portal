/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {ObjectAdminRestClient} from '../../../../apps/object/object-admin-rest-client-js/src/main/resources/META-INF/resources/node';
import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {loginTest} from '../../fixtures/loginTest';
import {objectPagesTest} from '../../fixtures/objectPagesTest';
import {getRandomInt} from '../../utils/util';

export const test = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	loginTest,
	objectPagesTest
);

async function postRandomObjectDefinition(
	objectAdminRestClient: ObjectAdminRestClient,
	objectFolderExternalReferenceCode: string
) {
	const objectDefinitionExternalReferenceCode1 =
		'ObjectDefinition' + getRandomInt();

	const objectDefinition1 =
		await objectAdminRestClient.objectDefinition.postObjectDefinition({
			requestBody: {
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
			},
		});

	return objectDefinition1;
}

test('can create relationship by dragging node handles', async ({
	apiHelpers,
	authenticate,
	modelBuilderPage,
	objectDefinitionsPage,
}) => {
	await apiHelpers.featureFlag.updateFeatureFlag('LPS-148856', true);

	const objectFolderExternalReferenceCode = 'objectFolder' + getRandomInt();

	const objectFolder = await authenticate(
		ObjectAdminRestClient
	).objectFolder.postObjectFolder({
		requestBody: {
			externalReferenceCode: objectFolderExternalReferenceCode,
			label: {
				en_US: objectFolderExternalReferenceCode,
			},
			name: objectFolderExternalReferenceCode,
		},
	});

	const objectDefinition1 = await postRandomObjectDefinition(
		authenticate(ObjectAdminRestClient),
		objectFolderExternalReferenceCode
	);

	const objectDefinition2 = await postRandomObjectDefinition(
		authenticate(ObjectAdminRestClient),
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

	await authenticate(
		ObjectAdminRestClient
	).objectRelationship.deleteObjectRelationship({
		objectRelationshipId: objectRelationship.id,
	});
	await authenticate(
		ObjectAdminRestClient
	).objectDefinition.deleteObjectDefinition({
		objectDefinitionId: objectDefinition1.id,
	});
	await authenticate(
		ObjectAdminRestClient
	).objectDefinition.deleteObjectDefinition({
		objectDefinitionId: objectDefinition2.id,
	});
	await authenticate(ObjectAdminRestClient).objectFolder.deleteObjectFolder({
		objectFolderId: objectFolder.id,
	});
});
