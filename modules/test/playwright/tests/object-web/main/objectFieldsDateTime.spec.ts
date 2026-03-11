/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import {waitForAlert} from '../../../utils/waitForAlert';
import {getObjectEntryUIDateTimeFormat} from './utils/dateFormat';
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
	'LPD-78504 Can add a custom object entry with a date and time field',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['DateTime'],
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

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		const date = new Date();

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldLabel: objectFields[0].label['en_US'],
			objectFieldValue: getObjectEntryUIDateTimeFormat(date),
		});

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);

		await viewObjectEntriesPage.backButton.click();

		await expect(
			viewObjectEntriesPage.frontendDatasetItems.first()
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can update a custom object entry with a date and time field',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['DateTime'],
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
			{[fieldName]: '2025-01-15T10:00:00Z'},
			applicationName
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.frontendDatasetItems.first().click();

		const newDate = new Date();

		const objectFieldLabel = page.getByLabel(
			objectFields[0].label['en_US']
		);

		await objectFieldLabel.clear();

		await objectFieldLabel.fill(
			getObjectEntryUIDateTimeFormat(newDate)
		);

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);

		await viewObjectEntriesPage.backButton.click();

		await expect(
			viewObjectEntriesPage.frontendDatasetItems.first()
		).toBeVisible();
	}
);
