/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../fixtures/loginTest';
import {getRandomInt} from '../../utils/getRandomInt';
import {cmsPagesTest} from './fixtures/cmsPagesTest';

const test = mergeTests(cmsPagesTest, loginTest());

test('Structures can be saved and published', async ({
	page,
	structureBuilderPage,
}) => {

	// Go to the Structure Builder

	await structureBuilderPage.goto();

	// Change label

	const label = `Structure ${getRandomInt()}`;

	await structureBuilderPage.changeStructureLabel(label);

	// Save structure

	const {id} = await structureBuilderPage.saveStructure();

	await expect(page.locator('.alert-danger')).not.toBeVisible();

	// Check we can't publish without adding a field

	await expect(async () => {
		await structureBuilderPage.publishStructure();
	}).not.toPass();

	// Add two fields

	await structureBuilderPage.addField('Text');
	await structureBuilderPage.addField('Text');

	// Remove a field

	await structureBuilderPage.deleteField({label: 'Text', nth: 1});

	// Save it again and publish it

	await structureBuilderPage.saveStructure();
	await structureBuilderPage.publishStructure();

	// Delete structure

	await structureBuilderPage.deleteStructure(id);
});
