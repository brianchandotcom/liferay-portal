/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ObjectDefinitionApi} from '@liferay/object-admin-rest-client-js';
import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {editObjectDefinitionPagesTest} from '../../fixtures/editObjectDefinitionPagesTest';
import {loginTest} from '../../fixtures/loginTest';
import {objectPagesTest} from '../../fixtures/objectPagesTest';
import getRandomString from '../../utils/getRandomString';
import getRandomObjectFieldText from './utils/getRandomObjectFieldText';

export const test = mergeTests(
	apiHelpersTest,
	editObjectDefinitionPagesTest,
	loginTest(),
	objectPagesTest
);

test.describe('manage Object Layouts through the Object Layout tab', () => {
	test('can view all fields of an object when creating its layout', async ({
		apiHelpers,
		objectLayoutsPage,
		page,
	}) => {
		const objectFields = getRandomObjectFieldText({
			objectFieldsQuantity: 20,
		});

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition(
				{code: 0},
				objectFields
			);

		const objectLayoutBlockName = getRandomString();

		const objectLayoutName = getRandomString();

		const objectLayoutTabName = getRandomString();

		await objectLayoutsPage.goto(objectDefinition.name);

		await objectLayoutsPage.createObjectLayout(objectLayoutName);

		await objectLayoutsPage.createObjectLayoutContent(
			objectLayoutBlockName,
			objectLayoutName,
			objectLayoutTabName
		);

		objectFields.forEach(({label}) => {
			expect(
				page
					.frameLocator('iframe')
					.getByRole('option', {name: label.en_US})
			).toBeVisible();
		});

		// Clean up

		const objectDefinitionAPIClient =
			await apiHelpers.buildRestClient(ObjectDefinitionApi);

		await objectDefinitionAPIClient.deleteObjectDefinition(
			objectDefinition.id
		);
	});
});
