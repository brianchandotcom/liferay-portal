/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {ObjectAdminRestClient} from '../../../../apps/object/object-admin-rest-client-js/src/main/resources/META-INF/resources/node';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import {objectPagesTest} from '../../fixtures/objectPagesTest';
import {getRandomInt} from '../../utils/getRandomInt';
import getRandomString from '../../utils/getRandomString';

export const test = mergeTests(
	dataApiHelpersTest,
	loginTest(),
	objectPagesTest
);

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

	apiHelpers.data.push({id: objectDefinition1.id, type: 'objectDefinition'});

	apiHelpers.data.push({id: objectDefinition2.id, type: 'objectDefinition'});

	const objectRelationshipLabel = 'objectRelationshipLabel' + getRandomInt();
	const objectRelationshipName =
		'objectRelationshipName' + Math.floor(Math.random() * 99);

	const objectAdminRestClient = await apiHelpers.buildRestClient(
		ObjectAdminRestClient
	);

	await objectAdminRestClient.objectRelationship.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
		{
			externalReferenceCode: objectDefinition1.externalReferenceCode,
			requestBody: {
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
