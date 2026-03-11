/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import {generateObjectFields} from './utils/generateObjectFields';

const test = mergeTests(
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	objectPagesTest
);

test(
	'LPD-78504 Can prevent duplicate integer value when editing an existing entry with unique values',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: [
				{
					businessType: 'Integer',
					unique: true,
				},
			],
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

		const applicationName =
			'c/' + objectDefinition.name.toLowerCase() + 's';
		const fieldName = objectFields[0].name;

		await apiHelpers.objectEntry.postObjectEntry(
			{[fieldName]: 100},
			applicationName
		);

		await apiHelpers.objectEntry.postObjectEntry(
			{[fieldName]: 200},
			applicationName
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.frontendDatasetItems.first().click();

		await page.getByLabel(objectFields[0].label['en_US']).clear();

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldLabel: objectFields[0].label['en_US'],
			objectFieldValue: '200',
		});

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await viewObjectEntriesPage.assertErrorWithDuplicateEntryValue();
	}
);

test(
	'LPD-78504 Can prevent duplicate text value when creating an entry with unique values',
	{tag: '@LPD-78504'},
	async ({apiHelpers, viewObjectEntriesPage}) => {
		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: [
				{
					businessType: 'Text',
					unique: true,
				},
			],
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

		const applicationName =
			'c/' + objectDefinition.name.toLowerCase() + 's';
		const fieldName = objectFields[0].name;

		await apiHelpers.objectEntry.postObjectEntry(
			{[fieldName]: 'UniqueTestValue'},
			applicationName
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldLabel: objectFields[0].label['en_US'],
			objectFieldValue: 'UniqueTestValue',
		});

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await viewObjectEntriesPage.assertErrorWithDuplicateEntryValue();
	}
);
