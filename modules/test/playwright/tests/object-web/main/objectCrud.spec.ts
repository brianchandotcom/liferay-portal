/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import {generateObjectFields} from '../utils/generateObjectFields';

const test = mergeTests(
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	objectPagesTest
);

test('Verify it is possible to view the custom object after restarting portal', async ({
	apiHelpers,
	page,
	viewObjectDefinitionsPage,
}) => {
	const objectFields = generateObjectFields({
		objectFieldBusinessTypes: ['Text'],
	});

	const objectDefinition =
		await apiHelpers.objectAdmin.postRandomObjectDefinition({
			objectFields,
			status: {code: 0},
		});

	apiHelpers.data.push({
		id: objectDefinition.id,
		type: 'objectDefinition',
	});

	const applicationName = 'c/' + objectDefinition.name.toLowerCase() + 's';
	const fieldName = objectFields[0].name!;

	await apiHelpers.objectEntry.postObjectEntry(
		{[fieldName]: 'Entry Test'},
		applicationName
	);

	// Simulate "restart" by navigating away and back

	await page.goto('/');

	await viewObjectDefinitionsPage.goto();

	await expect(
		viewObjectDefinitionsPage.frontendDataSetEntries.filter({
			hasText: objectDefinition.label['en_US'],
		})
	).toBeVisible();
});

