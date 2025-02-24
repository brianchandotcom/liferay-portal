/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ObjectDefinitionApi} from '@liferay/object-admin-rest-client-js';
import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import {getRandomInt} from '../../utils/getRandomInt';
import {cmsPagesTest} from './fixtures/cmsPagesTest';

const test = mergeTests(apiHelpersTest, cmsPagesTest, loginTest());

test('Structures can be saved and published', async ({
	apiHelpers,
	page,
	structureBuilderPage,
}) => {

	// Go to the Structure Builder

	await structureBuilderPage.goto();

	// Check invalid labels don't work

	await structureBuilderPage.changeStructureLabel('1');

	await expect(async () => {
		await structureBuilderPage.saveStructure();
	}).not.toPass();

	// Put valid label

	const label = `Structure ${getRandomInt()}`;

	await structureBuilderPage.changeStructureLabel(label);

	// Save structure and capture object definition id

	const [response] = await Promise.all([
		page.waitForResponse(
			(response) =>
				response.url().includes('object-definitions') &&
				response.status() === 200
		),
		await structureBuilderPage.saveStructure(),
	]);

	const {id} = await response.json();

	await expect(page.locator('.alert-danger')).not.toBeVisible();

	// Check we can't publish without adding a field

	await expect(async () => {
		await structureBuilderPage.publishStructure();
	}).not.toPass();

	// Add a field

	await structureBuilderPage.addField('Text');

	// Save it again and publish it

	await structureBuilderPage.saveStructure();
	await structureBuilderPage.publishStructure();

	// Delete structure

	const APIClient = await apiHelpers.buildRestClient(ObjectDefinitionApi);

	const {
		response: {statusCode},
	} = await APIClient.deleteObjectDefinition(id);

	expect(statusCode).toBe(204);
});
