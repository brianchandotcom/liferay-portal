/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {collectionsPagesTest} from '../../../fixtures/collectionsPagesTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import getRandomString from '../../../utils/getRandomString';
import {waitForAlert} from '../../../utils/waitForAlert';
import {generateObjectFields} from './utils/generateObjectFields';

const test = mergeTests(
	collectionsPagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	objectPagesTest
);

test(
	'LPD-78504 Can search for object entry on search experience in collection providers',
	{tag: '@LPD-78504'},
	async ({apiHelpers, collectionsPage, page, site}) => {
		// Corresponds to Poshi test: CanSearchForObjectEntryOnSearchExperience

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

		// Create an entry via API

		const applicationName =
			'c/' + objectDefinition.name.toLowerCase() + 's';
		const fieldName = objectFields[0].name!;
		const fieldValue = getRandomString();

		await apiHelpers.objectEntry.postObjectEntry(
			{[fieldName]: fieldValue},
			applicationName
		);

		// Navigate to the Collection Providers page for the site

		await collectionsPage.goto(site.friendlyUrlPath);

		await page.getByRole('link', {name: 'Collection Providers'}).click();

		// Verify the object appears as a collection provider

		await expect(
			page.getByText(objectDefinition.name).first()
		).toBeVisible();
	}
);
