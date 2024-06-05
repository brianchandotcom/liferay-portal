/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import {objectPagesTest} from '../../fixtures/objectPagesTest';
import {getRandomInt} from '../../utils/getRandomInt';
import getRandomString from '../../utils/getRandomString';

export const test = mergeTests(apiHelpersTest, loginTest(), objectPagesTest);

const objectDefinitions: ObjectDefinition[] = [];

test.afterEach(async ({apiHelpers}) => {
	for (const objectDefinition of objectDefinitions) {
		await apiHelpers.objectAdmin.deleteObjectDefinition(
			objectDefinition.id
		);
	}
});

test('can create an object custom view using object relationship entry', async ({
	apiHelpers,
	editObjectViewPage,
	objectViewPage,
	page,
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

	objectDefinitions.push(objectDefinition1);
	objectDefinitions.push(objectDefinition2);

	const objectRelationshipLabel = 'objectRelationshipLabel' + getRandomInt();
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

	await apiHelpers.objectAdmin.postObjectRelationship(objectRelationshipData);

	const applicationName = 'c/' + objectDefinition1.name.toLowerCase() + 's';

	const textObjectEntry = {
		textField: 'entry',
	};

	const objectEntryResponse = await apiHelpers.objectEntry.postObjectEntry(
		textObjectEntry,
		applicationName
	);

	const objectViewName = getRandomString();

	await objectViewPage.goto(objectDefinition2.label['en_US']);

	await objectViewPage.createObjectView(objectViewName);

	await page.getByRole('link', {name: objectViewName}).click();

	editObjectViewPage.createFilter(
		'Includes',
		objectRelationshipLabel,
		`${objectEntryResponse.id}`
	);

	await expect(
		editObjectViewPage.sidePanel.getByText(`${objectRelationshipLabel}`)
	).toBeVisible();

	await expect(
		editObjectViewPage.sidePanel.getByText('Relationship', {exact: true})
	).toBeVisible();

	await expect(
		editObjectViewPage.sidePanel.getByText(`${objectEntryResponse.id}`)
	).toBeVisible();
});
