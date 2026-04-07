/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ObjectFieldAPI} from '@liferay/object-admin-rest-client-js';
import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import {waitForAlert} from '../../../utils/waitForAlert';
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

test.describe('Object Entries Page', () => {
	test(
		'Verify it is possible to add a field after the Object is published and submit entries to it',
		{tag: '@LPS-135635'},
		async ({apiHelpers, page, viewObjectEntriesPage}) => {
			const objectFields = generateObjectFields({
				objectFieldBusinessTypes: ['Text'],
			});

			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields,
					panelCategoryKey: 'control_panel.object',
					scope: 'company',
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			const newField = generateObjectFields({
				objectFieldBusinessTypes: ['Text'],
			});

			const objectFieldAPIClient =
				await apiHelpers.buildRestClient(ObjectFieldAPI);

			await objectFieldAPIClient.postObjectDefinitionObjectField(
				objectDefinition.id!,
				newField[0]
			);

			await viewObjectEntriesPage.goto(objectDefinition.name!);

			await viewObjectEntriesPage.clickAddObjectEntry(
				objectDefinition.label['en_US']
			);

			await viewObjectEntriesPage.fillObjectEntry({
				objectFieldValue: 'String Entry',
			});

			await viewObjectEntriesPage.saveObjectEntryButton.click();

			await waitForAlert(page);
		}
	);

	test(
		'Verify that when the Object is scoped by Site each site will have its own entries',
		{tag: '@LPS-135551'},
		async ({apiHelpers, page, site, viewObjectEntriesPage}) => {
			const objectFields = generateObjectFields({
				objectFieldBusinessTypes: ['Text'],
			});

			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields,
					panelCategoryKey: 'site_administration.content',
					scope: 'site',
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			await apiHelpers.objectEntry.postObjectEntry(
				{[objectFields[0].name!]: 'Site1Entry'},
				'c/' + objectDefinition.name!.toLowerCase() + 's',
				String(site.id)
			);

			await viewObjectEntriesPage.goto(
				objectDefinition.className,
				'en',
				site.friendlyUrlPath
			);

			await expect(page.getByText('Site1Entry')).toBeVisible();
		}
	);
});
