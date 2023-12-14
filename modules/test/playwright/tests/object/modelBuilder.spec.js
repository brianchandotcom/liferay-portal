/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {test as apiHelpersTest} from '../../fixtures/apiHelpers.fixture';
import {test as applicationsMenuPageTest} from '../../fixtures/applicationsMenuPages.fixture';
import {test as objectPagesTest} from '../../fixtures/objectPages.fixture';
import {getRandomInt} from '../../utils/util';

export const test = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	objectPagesTest
);

test('can create relationship by dragging node handles', async ({
	_apiHelpers,
	_modelBuilderPage,
	_objectDefinitionsPage,
}) => {
	await _apiHelpers.featureFlag.updateFeatureFlag('LPS-148856', 'true');

	const objectFolder = await _apiHelpers.objectAdmin.postRandomObjectFolder();

	const objectDefinition1 = await _apiHelpers.objectAdmin.postRandomObjectDefinition(
		objectFolder.externalReferenceCode
	);
	const objectDefinition2 = await _apiHelpers.objectAdmin.postRandomObjectDefinition(
		objectFolder.externalReferenceCode
	);

	await _objectDefinitionsPage.goto();

	await _objectDefinitionsPage.openObjectFolder(
		objectFolder.externalReferenceCode
	);

	await _objectDefinitionsPage.viewInModelBuilder();

	const objectRelationshipLabel = 'objectRelationship' + getRandomInt();

	const objectRelationship = await _modelBuilderPage.createObjectRelationship(
		objectDefinition1.id,
		objectDefinition2.id,
		objectRelationshipLabel,
		'One to Many'
	);

	await expect(
		_modelBuilderPage.objectRelationshipEdges.filter({
			hasText: objectRelationshipLabel,
		})
	).toBeVisible();

	await _modelBuilderPage.clickObjectDefinitionShowAllFieldsButton(
		objectDefinition2.name
	);

	await expect(
		_modelBuilderPage.objectDefinitionNodes
			.filter({hasText: objectDefinition2.name})
			.getByText(objectRelationshipLabel)
	).toBeVisible();

	// Clean up

	await _apiHelpers.objectAdmin.deleteObjectRelationship(
		objectRelationship.id
	);

	await _apiHelpers.objectAdmin.deleteObjectDefinition(objectDefinition1.id);
	await _apiHelpers.objectAdmin.deleteObjectDefinition(objectDefinition2.id);

	await _apiHelpers.objectAdmin.deleteObjectFolder(objectFolder.id);
});
