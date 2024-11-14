/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {
	ObjectAdminRestClient,
	ObjectRelationship,
} from '../../../../apps/object/object-admin-rest-client-js/src/main/resources/META-INF/resources/node';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {objectPagesTest} from '../../fixtures/objectPagesTest';
import {getRandomInt} from '../../utils/getRandomInt';

export const test = mergeTests(
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-187142': true,
	}),
	loginTest(),
	objectPagesTest
);

test.describe('Manage root models elements through Objects Admin', () => {
	test('cannot delete an object definition with inheritance enabled on its relationship', async ({
		apiHelpers,
		page,
		viewObjectDefinitionsPage,
	}) => {
		const objectRelationships: ObjectRelationship[] = [];

		try {
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

			apiHelpers.data.push({
				id: objectDefinition1.id,
				type: 'objectDefinition',
			});
			apiHelpers.data.push({
				id: objectDefinition2.id,
				type: 'objectDefinition',
			});

			const objectRelationshipLabel =
				'objectRelationshipLabel' + getRandomInt();
			const objectRelationshipName =
				'objectRelationshipName' + Math.floor(Math.random() * 99);

			const objectAdminRestClient = await apiHelpers.buildRestClient(
				ObjectAdminRestClient
			);

			const objectRelationship =
				await objectAdminRestClient.objectRelationship.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
					{
						externalReferenceCode:
							objectDefinition1.externalReferenceCode,
						requestBody: {
							edge: true,
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
						},
					}
				);

			objectRelationships.push(objectRelationship);

			apiHelpers.data.push({
				id: objectRelationship.id,
				type: 'objectRelationship',
			});

			await viewObjectDefinitionsPage.goto();

			await viewObjectDefinitionsPage.actionsButton.first().waitFor();

			await viewObjectDefinitionsPage.actionsButton.last().click();

			await viewObjectDefinitionsPage.deleteObjectDefinitionOption.click();

			await expect(page.getByText('Deletion Not Allowed')).toBeVisible();
			await expect(
				page.getByText(
					'To delete this object, you must first disable inheritance and delete its relationships.'
				)
			).toBeVisible();

			await page.getByRole('button', {name: 'Done'}).click();
		}
		finally {
			const objectAdminRestClient = await apiHelpers.buildRestClient(
				ObjectAdminRestClient
			);

			for (const objectRelationship of objectRelationships) {
				await objectAdminRestClient.objectRelationship.putObjectRelationship(
					{
						objectRelationshipId: objectRelationship.id,
						requestBody: {
							...objectRelationship,
							edge: false,
						},
					}
				);
			}
		}
	});

	test('cannot delete an object relationship with inheritance enabled', async ({
		apiHelpers,
		objectRelationshipsPage,
		page,
	}) => {
		const objectRelationships: ObjectRelationship[] = [];

		try {
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

			apiHelpers.data.push({
				id: objectDefinition1.id,
				type: 'objectDefinition',
			});
			apiHelpers.data.push({
				id: objectDefinition2.id,
				type: 'objectDefinition',
			});

			const objectRelationshipLabel =
				'objectRelationshipLabel' + getRandomInt();
			const objectRelationshipName =
				'objectRelationshipName' + Math.floor(Math.random() * 99);

			const objectAdminRestClient = await apiHelpers.buildRestClient(
				ObjectAdminRestClient
			);

			const objectRelationship =
				await objectAdminRestClient.objectRelationship.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
					{
						externalReferenceCode:
							objectDefinition1.externalReferenceCode,
						requestBody: {
							edge: true,
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
						},
					}
				);

			objectRelationships.push(objectRelationship);

			apiHelpers.data.push({
				id: objectRelationship.id,
				type: 'objectRelationship',
			});

			await objectRelationshipsPage.goto(
				objectDefinition1.label['en_US']
			);

			await objectRelationshipsPage.actionsButton.click();

			await objectRelationshipsPage.deleteObjectRelationshipOption.click();

			await expect(page.getByText('Deletion Not Allowed')).toBeVisible();
			await page
				.getByText(
					'You cannot delete a relationship with inheritance enabled. Disable inheritance before deleting the relationship.'
				)
				.click();

			await page.getByRole('button', {name: 'Done'}).click();
		}
		finally {
			const objectAdminRestClient = await apiHelpers.buildRestClient(
				ObjectAdminRestClient
			);

			for (const objectRelationship of objectRelationships) {
				await objectAdminRestClient.objectRelationship.putObjectRelationship(
					{
						objectRelationshipId: objectRelationship.id,
						requestBody: {
							...objectRelationship,
							edge: false,
						},
					}
				);
			}
		}
	});
});
