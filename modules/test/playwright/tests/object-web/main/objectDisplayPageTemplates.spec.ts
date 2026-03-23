/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {displayPageTemplatesPagesTest} from '../../../fixtures/displayPageTemplatesPagesTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import {getRandomInt} from '../../../utils/getRandomInt';

const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	displayPageTemplatesPagesTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	objectPagesTest
);

test(
	'LPD-78504 Cannot select unpublished object for a display page template',
	{tag: '@LPD-78504'},
	async ({apiHelpers, displayPageTemplatesPage, page}) => {
		const objectName = 'CustomObject' + getRandomInt();

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectDefinitionExternalReferenceCode: objectName,
				status: {code: 2},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await test.step('Create a new blank display page template', async () => {
			await displayPageTemplatesPage.goto();

			await page
				.getByRole('button', {name: 'New'})
				.click();

			await page
				.getByRole('menuitem', {name: 'Display Page Template'})
				.click();

			await page.getByRole('button', {name: 'Blank'}).click();
		});

		await test.step('Verify unpublished object is not in content type options', async () => {
			const contentTypeSelect = page.getByLabel('Content Type');

			await expect(contentTypeSelect).toBeVisible();

			const options = contentTypeSelect.locator('option');

			const optionTexts = await options.allTextContents();

			expect(
				optionTexts.some(
					(text) =>
						text === objectDefinition.label['en_US']
				)
			).toBe(false);
		});
	}
);
