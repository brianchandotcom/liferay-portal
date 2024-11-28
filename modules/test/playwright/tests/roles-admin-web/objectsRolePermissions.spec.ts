/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ObjectDefinition,
	ObjectDefinitionApi,
	ObjectField,
	ObjectRelationship,
	ObjectRelationshipApi,
} from '@liferay/object-admin-rest-client-js';
import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {rolesPagesTest} from '../../fixtures/rolesPagesTest';
import {getRandomInt} from '../../utils/getRandomInt';

export const test = mergeTests(
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-187142': true,
	}),
	loginTest(),
	rolesPagesTest
);

const generateRandomObjectDefinition = ({
	objectDefinitionName,
	objectRelationships = [],
	panelCategoryKey,
	portlet = true,
	rootObjectDefinitionExternalReferenceCode,
	statusCode = 0,
}: {
	objectDefinitionName: string;
	objectRelationships?: any[];
	panelCategoryKey?: string;
	portlet?: boolean;
	rootObjectDefinitionExternalReferenceCode?: string;
	statusCode?: number;
}): ObjectDefinition => {
	return {
		active: true,
		externalReferenceCode: objectDefinitionName,
		label: {
			en_US: objectDefinitionName,
		},
		name: objectDefinitionName,
		objectFields: [
			{
				DBType: ObjectField.DBTypeEnum.String,
				businessType: ObjectField.BusinessTypeEnum.Text,
				externalReferenceCode: 'textField',
				indexed: true,
				indexedAsKeyword: false,
				indexedLanguageId: '',
				label: {en_US: 'textField'},
				listTypeDefinitionId: 0,
				name: 'textField',
				required: false,
				system: false,
				type: ObjectField.TypeEnum.String,
			},
		],
		objectRelationships,
		panelCategoryKey,
		pluralLabel: {
			en_US: objectDefinitionName,
		},
		portlet,
		rootObjectDefinitionExternalReferenceCode,
		scope: 'company',
		status: {
			code: statusCode,
		},
	};
};

test.skip('LPD-26733 Show object in role permissions page', async ({
	apiHelpers,

	roleDefinePermissionsPage,
	rolePage,
	rolesPage,
}) => {
	test.setTimeout(120000);

	const objectDefinitionAPIClient =
		await apiHelpers.buildRestClient(ObjectDefinitionApi);

	const {body: objectDefinition1} =
		await objectDefinitionAPIClient.postObjectDefinition(
			generateRandomObjectDefinition({
				objectDefinitionName: `ObjectDefinition${getRandomInt()}`,
			})
		);

	apiHelpers.data.push({id: objectDefinition1.id, type: 'objectDefinition'});

	const {body: objectDefinition2} =
		await objectDefinitionAPIClient.postObjectDefinition(
			generateRandomObjectDefinition({
				objectDefinitionName: `ObjectDefinition${getRandomInt()}`,
				portlet: false,
			})
		);

	apiHelpers.data.push({id: objectDefinition2.id, type: 'objectDefinition'});

	const {body: objectDefinition3} =
		await objectDefinitionAPIClient.postObjectDefinition(
			generateRandomObjectDefinition({
				objectDefinitionName: `ObjectDefinition${getRandomInt()}`,
				panelCategoryKey: 'control_panel.users',
			})
		);

	apiHelpers.data.push({id: objectDefinition3.id, type: 'objectDefinition'});

	const {body: objectDefinition4} =
		await objectDefinitionAPIClient.postObjectDefinition(
			generateRandomObjectDefinition({
				objectDefinitionName: `ObjectDefinition${getRandomInt()}`,
				panelCategoryKey: 'control_panel.users',
				portlet: false,
			})
		);

	apiHelpers.data.push({id: objectDefinition4.id, type: 'objectDefinition'});

	const objectDefinition5Name = `ObjectDefinition${getRandomInt()}`;

	const {body: objectDefinition5} =
		await objectDefinitionAPIClient.postObjectDefinition(
			generateRandomObjectDefinition({
				objectDefinitionName: objectDefinition5Name,
				rootObjectDefinitionExternalReferenceCode:
					objectDefinition5Name,
				statusCode: 2,
			})
		);

	apiHelpers.data.push({id: objectDefinition5.id, type: 'objectDefinition'});

	const {body: objectDefinition6} =
		await objectDefinitionAPIClient.postObjectDefinition(
			generateRandomObjectDefinition({
				objectDefinitionName: `ObjectDefinition${getRandomInt()}`,
				rootObjectDefinitionExternalReferenceCode:
					objectDefinition5.externalReferenceCode,
				statusCode: 2,
			})
		);

	apiHelpers.data.push({id: objectDefinition6.id, type: 'objectDefinition'});

	const objectRelationshipApiClient = await apiHelpers.buildRestClient(
		ObjectRelationshipApi
	);

	const {body: objectRelationship} =
		await objectRelationshipApiClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
			objectDefinition5.externalReferenceCode,
			{
				deletionType: ObjectRelationship.DeletionTypeEnum.Cascade,
				edge: true,
				label: {
					en_US: objectDefinition6.name,
				},
				name: `rel${getRandomInt()}`,
				objectDefinitionExternalReferenceCode1:
					objectDefinition5.externalReferenceCode,
				objectDefinitionExternalReferenceCode2:
					objectDefinition6.externalReferenceCode,
				objectDefinitionId1: objectDefinition5.id,
				objectDefinitionId2: objectDefinition6.id,
				objectDefinitionName2: objectDefinition6.name,
				reverse: false,
				system: false,
				type: ObjectRelationship.TypeEnum.OneToMany,
			}
		);

	await objectRelationshipApiClient.putObjectRelationship(
		objectRelationship.id,
		{
			...objectRelationship,
			edge: true,
		}
	);
	await objectDefinitionAPIClient.postObjectDefinitionPublish(
		objectDefinition5.id
	);

	await rolesPage.goto();

	await rolesPage.userLink.click();
	await rolePage.definePermissionsLink.click();
	await roleDefinePermissionsPage.searchInput.click();
	await roleDefinePermissionsPage.searchInput.fill('object');

	await expect(roleDefinePermissionsPage.menuItem('Objects')).toBeVisible();
	await expect(
		roleDefinePermissionsPage.menuItemByTestId(
			`object_${objectDefinition1.id}`
		)
	).toBeVisible();
	await expect(
		roleDefinePermissionsPage.menuItemByTestId(
			`object_${objectDefinition1.id}`
		)
	).toHaveText(objectDefinition1.name);
	await expect(
		roleDefinePermissionsPage.menuItemByTestId(
			`object_${objectDefinition2.id}`
		)
	).toBeVisible();
	await expect(
		roleDefinePermissionsPage.menuItemByTestId(
			`object_${objectDefinition2.id}`
		)
	).toHaveText(objectDefinition2.name);
	await expect(
		roleDefinePermissionsPage.menuItemByTestId(
			`object_${objectDefinition3.id}`
		)
	).toHaveCount(0);
	await expect(
		roleDefinePermissionsPage.menuItemByTestId(
			`object_${objectDefinition4.id}`
		)
	).toHaveCount(0);
	await expect(
		roleDefinePermissionsPage.menuItemByTestId(
			`object_${objectDefinition5.id}`
		)
	).toBeVisible();
	await expect(
		roleDefinePermissionsPage.menuItemByTestId(
			`object_${objectDefinition5.id}`
		)
	).toHaveText(objectDefinition5.name);
	await expect(
		roleDefinePermissionsPage.menuItemByTestId(
			`object_${objectDefinition6.id}`
		)
	).toHaveCount(0);

	await roleDefinePermissionsPage
		.menuItemByTestId(`object_${objectDefinition1.id}`)
		.click();

	await expect(roleDefinePermissionsPage.loading).toHaveCount(0);
	await expect(roleDefinePermissionsPage.portletResourceLabel).toHaveText(
		objectDefinition1.name
	);
	await expect(roleDefinePermissionsPage.accessInControlPanel).toHaveCount(0);
	await expect(roleDefinePermissionsPage.addToPage).toBeVisible();

	await roleDefinePermissionsPage
		.menuItemByTestId(`object_${objectDefinition2.id}`)
		.click();

	await expect(roleDefinePermissionsPage.loading).toHaveCount(0);
	await expect(roleDefinePermissionsPage.portletResourceLabel).toHaveText(
		objectDefinition2.name
	);
	await expect(roleDefinePermissionsPage.accessInControlPanel).toHaveCount(0);
	await expect(roleDefinePermissionsPage.addToPage).toHaveCount(0);

	await roleDefinePermissionsPage
		.menuItemByTestId(`object_${objectDefinition5.id}`)
		.click();

	await expect(roleDefinePermissionsPage.loading).toHaveCount(0);
	await expect(roleDefinePermissionsPage.portletResourceLabel).toHaveText(
		objectDefinition5.name
	);
	await expect(roleDefinePermissionsPage.accessInControlPanel).toHaveCount(0);
	await expect(roleDefinePermissionsPage.addToPage).toBeVisible();
	await expect(
		roleDefinePermissionsPage.resourceSection(objectDefinition6.name)
	).toBeVisible();
});
